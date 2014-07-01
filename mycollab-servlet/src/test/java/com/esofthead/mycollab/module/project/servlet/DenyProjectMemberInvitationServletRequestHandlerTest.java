package com.esofthead.mycollab.module.project.servlet;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.esofthead.mycollab.common.service.RelayEmailNotificationService;
import com.esofthead.mycollab.core.MyCollabException;
import com.esofthead.mycollab.core.ResourceNotFoundException;
import com.esofthead.mycollab.module.project.ProjectLinkGenerator;
import com.esofthead.mycollab.module.project.domain.SimpleProject;
import com.esofthead.mycollab.module.project.domain.SimpleProjectMember;
import com.esofthead.mycollab.module.project.service.ProjectMemberService;
import com.esofthead.mycollab.module.project.service.ProjectService;
import com.esofthead.mycollab.module.servlet.GenericServletTest;

@RunWith(MockitoJUnitRunner.class)
public class DenyProjectMemberInvitationServletRequestHandlerTest extends
		GenericServletTest {

	@InjectMocks
	@Spy
	private DenyProjectMemberInvitationServletRequestHandler denyProjectMemberRequestHandler;

	@Mock
	private ProjectMemberService projectMemberService;

	@Mock
	private RelayEmailNotificationService relayEmailService;

	@Mock
	private ProjectService projectService;

	@Test
	public void testCannotFindProject() throws ServletException, IOException {
		String pathInfo = ProjectLinkGenerator.generateDenyInvitationParams(1,
				1, 1, "hainguyen@esofthead.com", "hainguyen@esofthead.com");
		when(request.getPathInfo()).thenReturn(pathInfo);
		when(response.getWriter()).thenReturn(mock(PrintWriter.class));

		denyProjectMemberRequestHandler.onHandleRequest(request, response);

		ArgumentCaptor<String> strArgument = ArgumentCaptor
				.forClass(String.class);

		ArgumentCaptor<Map> mapArgument = ArgumentCaptor.forClass(Map.class);

		verify(denyProjectMemberRequestHandler).generatePageByTemplate(
				strArgument.capture(), mapArgument.capture());
		Assert.assertEquals(
				DenyProjectMemberInvitationServletRequestHandler.PROJECT_NOT_AVAILABLE_TEMPLATE,
				strArgument.getValue());
	}

	@Test
	public void testDenyWithProjectMember() throws ServletException,
			IOException {
		String pathInfo = ProjectLinkGenerator.generateDenyInvitationParams(1,
				1, 1, "hainguyen@esofthead.com", "hainguyen@esofthead.com");
		when(request.getPathInfo()).thenReturn(pathInfo);
		when(response.getWriter()).thenReturn(mock(PrintWriter.class));

		when(projectService.findById(1, 1)).thenReturn(new SimpleProject());
		when(projectMemberService.findById(1, 1)).thenReturn(
				new SimpleProjectMember());

		denyProjectMemberRequestHandler.onHandleRequest(request, response);

		ArgumentCaptor<String> strArgument = ArgumentCaptor
				.forClass(String.class);

		ArgumentCaptor<Map> mapArgument = ArgumentCaptor.forClass(Map.class);

		verify(denyProjectMemberRequestHandler).generatePageByTemplate(
				strArgument.capture(), mapArgument.capture());
		Assert.assertEquals(
				DenyProjectMemberInvitationServletRequestHandler.REFUSE_MEMBER_DENY_TEMPLATE,
				strArgument.getValue());
	}

	@Test
	public void testDenyNotFoundProjectMember() throws ServletException,
			IOException {
		String pathInfo = ProjectLinkGenerator.generateDenyInvitationParams(1,
				1, 1, "hainguyen@esofthead.com", "hainguyen@esofthead.com");
		when(request.getPathInfo()).thenReturn(pathInfo);
		when(response.getWriter()).thenReturn(mock(PrintWriter.class));

		when(projectService.findById(1, 1)).thenReturn(new SimpleProject());

		denyProjectMemberRequestHandler.onHandleRequest(request, response);

		ArgumentCaptor<String> strArgument = ArgumentCaptor
				.forClass(String.class);

		ArgumentCaptor<Map> mapArgument = ArgumentCaptor.forClass(Map.class);

		verify(denyProjectMemberRequestHandler).generatePageByTemplate(
				strArgument.capture(), mapArgument.capture());
		Assert.assertEquals(
				DenyProjectMemberInvitationServletRequestHandler.DENY_FEEDBACK_TEMPLATE,
				strArgument.getValue());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testNullParam() throws ServletException, IOException {
		when(request.getPathInfo()).thenReturn(null);
		denyProjectMemberRequestHandler.onHandleRequest(request, response);
	}

	@Test(expected = MyCollabException.class)
	public void testInvalidParams() throws ServletException, IOException {
		when(request.getPathInfo()).thenReturn("1");
		denyProjectMemberRequestHandler.onHandleRequest(request, response);
	}
}
/**
 * This file is part of mycollab-mobile.
 *
 * mycollab-mobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-mobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-mobile.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.mobile.module.crm.view.account;

import com.esofthead.mycollab.eventmanager.EventBusFactory;
import com.esofthead.mycollab.mobile.form.view.DynaFormLayout;
import com.esofthead.mycollab.mobile.module.crm.events.AccountEvent;
import com.esofthead.mycollab.mobile.module.crm.ui.CrmPreviewFormControlsGenerator;
import com.esofthead.mycollab.mobile.module.crm.ui.CrmRelatedItemsScreenData;
import com.esofthead.mycollab.mobile.module.crm.ui.NotesList;
import com.esofthead.mycollab.mobile.module.crm.view.activity.ActivityRelatedItemView;
import com.esofthead.mycollab.mobile.ui.AbstractBeanFieldGroupViewFieldFactory;
import com.esofthead.mycollab.mobile.ui.AbstractPreviewItemComp;
import com.esofthead.mycollab.mobile.ui.AdvancedPreviewBeanForm;
import com.esofthead.mycollab.mobile.ui.IconConstants;
import com.esofthead.mycollab.module.crm.CrmTypeConstants;
import com.esofthead.mycollab.module.crm.domain.SimpleAccount;
import com.esofthead.mycollab.module.crm.domain.SimpleActivity;
import com.esofthead.mycollab.module.crm.domain.SimpleCase;
import com.esofthead.mycollab.module.crm.domain.SimpleContact;
import com.esofthead.mycollab.module.crm.domain.SimpleLead;
import com.esofthead.mycollab.module.crm.domain.SimpleOpportunity;
import com.esofthead.mycollab.module.crm.i18n.CrmCommonI18nEnum;
import com.esofthead.mycollab.security.RolePermissionCollections;
import com.esofthead.mycollab.vaadin.AppContext;
import com.esofthead.mycollab.vaadin.events.HasPreviewFormHandlers;
import com.esofthead.mycollab.vaadin.mvp.ViewComponent;
import com.esofthead.mycollab.vaadin.ui.IFormLayoutFactory;
import com.esofthead.mycollab.vaadin.ui.IRelatedListHandlers;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;

/**
 * 
 * @author MyCollab Ltd.
 * @since 4.0
 * 
 */
@ViewComponent
public class AccountReadViewImpl extends AbstractPreviewItemComp<SimpleAccount>
		implements AccountReadView {

	private static final long serialVersionUID = -5987636662071328512L;

	protected NotesList associateNotes;
	protected AccountRelatedContactView associateContacts;
	protected AccountRelatedCaseView associateCases;
	protected ActivityRelatedItemView associateActivities;
	protected AccountRelatedLeadView associateLeads;
	protected AccountRelatedOpportunityView associateOpportunities;

	@Override
	protected void afterPreviewItem() {
		associateNotes.showNotes(CrmTypeConstants.ACCOUNT, beanItem.getId());
		associateContacts.displayContacts(beanItem);
		associateCases.displayCases(beanItem);
		associateActivities.displayActivity(beanItem.getId());
		associateLeads.displayLeads(beanItem);
		associateOpportunities.displayOpportunities(beanItem);
	}

	@Override
	protected void initRelatedComponents() {
		associateNotes = new NotesList(
				AppContext.getMessage(CrmCommonI18nEnum.M_TITLE_RELATED_NOTES));
		associateContacts = new AccountRelatedContactView();
		associateCases = new AccountRelatedCaseView();
		associateActivities = new ActivityRelatedItemView(
				CrmTypeConstants.ACCOUNT);
		associateLeads = new AccountRelatedLeadView();
		associateOpportunities = new AccountRelatedOpportunityView();
	}

	@Override
	protected String initFormTitle() {
		return beanItem.getAccountname();
	}

	@Override
	protected AdvancedPreviewBeanForm<SimpleAccount> initPreviewForm() {
		return new AdvancedPreviewBeanForm<SimpleAccount>();
	}

	@Override
	protected IFormLayoutFactory initFormLayoutFactory() {
		return new DynaFormLayout(CrmTypeConstants.ACCOUNT,
				AccountDefaultDynaFormLayoutFactory.getForm());
	}

	@Override
	protected AbstractBeanFieldGroupViewFieldFactory<SimpleAccount> initBeanFormFieldFactory() {
		return new AccountReadFormFieldFactory(previewForm);
	}

	@Override
	public HasPreviewFormHandlers<SimpleAccount> getPreviewFormHandlers() {
		return this.previewForm;
	}

	@Override
	protected ComponentContainer createButtonControls() {
		return new CrmPreviewFormControlsGenerator<SimpleAccount>(previewForm)
				.createButtonControls(RolePermissionCollections.CRM_ACCOUNT);
	}

	@Override
	protected ComponentContainer createBottomPanel() {
		HorizontalLayout toolbarLayout = new HorizontalLayout();
		toolbarLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		toolbarLayout.setSpacing(true);

		Button relatedContacts = new Button();
		relatedContacts.setCaption("<span aria-hidden=\"true\" data-icon=\""
				+ IconConstants.CRM_CONTACT
				+ "\"></span><div class=\"screen-reader-text\">"
				+ AppContext.getMessage(CrmCommonI18nEnum.TAB_CONTACT)
				+ "</div>");
		relatedContacts.setHtmlContentAllowed(true);
		relatedContacts.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7589415773039335559L;

			@Override
			public void buttonClick(ClickEvent arg0) {
				EventBusFactory
						.getInstance()
						.post(new AccountEvent.GoToRelatedItems(
								this,
								new CrmRelatedItemsScreenData(associateContacts)));
			}
		});
		toolbarLayout.addComponent(relatedContacts);

		Button relatedOpportunities = new Button();
		relatedOpportunities
				.setCaption("<span aria-hidden=\"true\" data-icon=\""
						+ IconConstants.CRM_OPPORTUNITY
						+ "\"></span><div class=\"screen-reader-text\">"
						+ AppContext
								.getMessage(CrmCommonI18nEnum.TAB_OPPORTUNITY)
						+ "</div>");
		relatedOpportunities.setHtmlContentAllowed(true);
		relatedOpportunities.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7589415773039335559L;

			@Override
			public void buttonClick(ClickEvent arg0) {
				EventBusFactory.getInstance().post(
						new AccountEvent.GoToRelatedItems(this,
								new CrmRelatedItemsScreenData(
										associateOpportunities)));
			}
		});
		toolbarLayout.addComponent(relatedOpportunities);

		Button relatedLeads = new Button();
		relatedLeads.setCaption("<span aria-hidden=\"true\" data-icon=\""
				+ IconConstants.CRM_LEAD
				+ "\"></span><div class=\"screen-reader-text\">"
				+ AppContext.getMessage(CrmCommonI18nEnum.TAB_LEAD) + "</div>");
		relatedLeads.setHtmlContentAllowed(true);
		relatedLeads.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7589415773039335559L;

			@Override
			public void buttonClick(ClickEvent arg0) {
				EventBusFactory.getInstance().post(
						new AccountEvent.GoToRelatedItems(this,
								new CrmRelatedItemsScreenData(associateLeads)));
			}
		});
		toolbarLayout.addComponent(relatedLeads);

		Button relatedNotes = new Button();
		relatedNotes.setCaption("<span aria-hidden=\"true\" data-icon=\""
				+ IconConstants.CRM_DOCUMENT
				+ "\"></span><div class=\"screen-reader-text\">"
				+ AppContext.getMessage(CrmCommonI18nEnum.TAB_NOTE) + "</div>");
		relatedNotes.setHtmlContentAllowed(true);
		relatedNotes.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7589415773039335559L;

			@Override
			public void buttonClick(ClickEvent arg0) {
				EventBusFactory.getInstance().post(
						new AccountEvent.GoToRelatedItems(this,
								new CrmRelatedItemsScreenData(associateNotes)));
			}
		});
		toolbarLayout.addComponent(relatedNotes);

		Button relatedActivities = new Button();
		relatedActivities.setCaption("<span aria-hidden=\"true\" data-icon=\""
				+ IconConstants.CRM_ACTIVITY
				+ "\"></span><div class=\"screen-reader-text\">"
				+ AppContext.getMessage(CrmCommonI18nEnum.TAB_ACTIVITY)
				+ "</div>");
		relatedActivities.setHtmlContentAllowed(true);
		relatedActivities.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 7589415773039335559L;

			@Override
			public void buttonClick(ClickEvent arg0) {
				EventBusFactory.getInstance().post(
						new AccountEvent.GoToRelatedItems(this,
								new CrmRelatedItemsScreenData(
										associateActivities)));
			}
		});
		toolbarLayout.addComponent(relatedActivities);

		return toolbarLayout;
	}

	@Override
	public IRelatedListHandlers<SimpleContact> getRelatedContactHandlers() {
		return associateContacts;
	}

	@Override
	public IRelatedListHandlers<SimpleOpportunity> getRelatedOpportunityHandlers() {
		return associateOpportunities;
	}

	@Override
	public IRelatedListHandlers<SimpleLead> getRelatedLeadHandlers() {
		return associateLeads;
	}

	@Override
	public IRelatedListHandlers<SimpleCase> getRelatedCaseHandlers() {
		return associateCases;
	}

	@Override
	public IRelatedListHandlers<SimpleActivity> getRelatedActivityHandlers() {
		return associateActivities;
	}

}

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="height:100%;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="icon" href="https://www.mycollab.com/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="https://www.mycollab.com/favicon.ico" type="image/x-icon">
<link rel="stylesheet" type="text/css" href="${defaultUrls.app_url}assets/css/cssVelocityPage.css">
<style media="screen" type="text/css">
#container {
    background-image: url('${defaultUrls.cdn_url}footer-clouds.png');  background-repeat: no-repeat;  background-position: bottom right;
}

</style>
<title>Page not found</title>
</head>
<body style="height: 100%; margin: 0; padding: 0; width: 100%;">
	<div id="container" style="height:100%;">
		#parse("templates/page/pageHeader.mt")
		<div id="body" >
			<div id="spacing"></div>
			<div id="mainBody">
				<div id="title">
					<h1>Error</h1>
				</div>
				<hr size="1">
				<div id="content" style="padding-top: 20px">
					<div id="content_left">
						<div id="exclamation_mark">&#33;</div>
						<div id="error_display">
							<div id="error_code">500</div>
							<div id="error_brief">Something's wrong</div>
						</div>
						<div class="clear"></div>
					</div>
					<div id="content_right">
						<div id="error_excuse">
							An unexpected error has occurred. We apologize for the inconvenience. Our team has been notified and will investigate the issue right away. 
						</div>
						<div id="back_to_home" style="padding-left:30px;padding-top:20px;">
							<a class="v-button v-button-orangebtn" style="text-decoration : none;" href="https://www.mycollab.com">Return to the home page</a>
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
				</div>
				#parse("templates/page/pageFooter_en_US.mt")
				</div>
		</div>
	</div>
</body>
</html>

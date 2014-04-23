<%@ page language="java" pageEncoding="utf-8"
	contentType="text/html;charset=utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + path + "/ui/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>DaaS</title>
<link rel="stylesheet" href="assets/lib/bootstrap/css/bootstrap.css">
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet"
	href="assets/lib/Font-Awesome/css/font-awesome.css" />
<link rel="stylesheet" href="assets/css/theme.css">
<link rel="stylesheet" href="assets/css/bootstrap-select.css"
	type="text/css">
<!-- <link rel="stylesheet" href="assets/css/jquery.easy-pie-chart.css" type="text/css"> -->
<script src="assets/lib/jquery-1.10.2.min.js"></script>
<script src="assets/lib/bootstrap/js/bootstrap.js"></script>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),

m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-1669764-16', 'onokumus.com');
  ga('send', 'pageview');
</script>

<script src="assets/lib/modernizr-2.6.2-respond-1.1.0.min.js"></script>
<style type="text/css">
.welcome-user {
	color: #ccc;
	line-height: 30px;
}
</style>
</head>
<body>
	<div id="wrap">
		<div id="top" class="navbar-fixed-top">
			<nav class="navbar navbar-inverse">
				<!-- Brand and toggle get grouped for better mobile display -->
				<header class="navbar-header">
					<a href="index.jsp" class="navbar-brand"> <img
						src="assets/img/logo.png"
						style="margin-left: 20px; margin-top: 2px;">
					</a>
				</header>

				<div class="topnav">
					<div class="btn-toolbar">
						<div class="btn-group welcome-user">Welcome,&nbsp;${sessionScope.username }</div>
						<div class="btn-group">
							<a data-placement="bottom"
								data-original-title="Show / Hide Sidebar" data-toggle="tooltip"
								class="btn btn-success btn-sm" id="changeSidebarPos"> <i
								class="icon-resize-horizontal"></i>
							</a>
						</div>
						<div class="btn-group">
							<a href="logout" data-toggle="tooltip"
								data-original-title="Logout" data-placement="bottom"
								class="btn btn-metis-1 btn-sm"> <i class="icon-off"></i>
							</a>
						</div>
					</div>
				</div>

				<!-- /.topnav -->
				<div class="collapse navbar-collapse navbar-ex1-collapse">
					<!-- .nav -->
				</div>
				<header class="head">
					<div class="search-bar">
						<div class="btn-group">
							<a href="#" class="btn btn-success" data-toggle="dropdown"
								style="border-top-right-radius: 4px; border-bottom-right-radius: 4px; width: 160px; text-align: left"
								id="dropdown-show"> <!-- <i class="icon-apple"></i> -->
								Loading...</i>
							</a>
							<ul class="dropdown-menu">

							</ul>
						</div>
					</div>
					<!-- ."main-bar -->
					<div class="main-bar">
						<a href="#" class="btn btn-default" id="btn-create-new-app"><i
							class="icon-plus"></i> Create an New App</a>
					</div>
					<!-- /.main-bar -->
				</header>
				<!-- end header.head -->
			</nav>
			<!-- /.navbar -->
		</div>
		<div id="left" data-spy="affix" data-offset-top="0">
			<!-- #menu -->
			<ul id="menu" class="accordion collapse">
				<li class="active"><a href="#" name="home"> <i
						class="icon-home"></i> Home <span class="selected"></span>
				</a></li>
				<li><a href="#" name="model_management"> <i
						class="icon-dashboard"></i> Model Management
				</a></li>
				<li><a href="#" name="data_management"> <i
						class="icon-search"></i> Data Management
				</a></li>
				<li><a href="#" name="sdk_download"> <i
						class="icon-download-alt"></i> SDK Download
				</a></li>
				<li><a href="#" name="app_setting"> <i class="icon-key"></i>
						App Setting & Keys
				</a></li>
				<li><a href="#" name="user_setting"> <i class="icon-cogs"></i>
						User Setting
				</a></li>
				<li><a href="http://daashome1.w3.bluemix.net/" target="_help">
						<i class="icon-book"></i> Help
				</a></li>
			</ul>
			<!-- /#menu -->
		</div>

		<div id="content">
			<div class="inner">
				<!-- content here-->
			</div>
		</div>
	</div>
	<!-- /#wrap -->

	<div id="footer">
		<p>2013 &copy; Data as a Service</p>
	</div>
	<script src="assets/js/main.js"></script>
	<script>
			$("ul#menu li").click(function(event){
				var $target = $(event.target);
				if(!$target.children("span").is("span") && $target.attr("target")!="_help"){
					$("ul#menu li.active").removeAttr("class");
					$("span.selected").remove();
					$target.parent().attr("class","active");
					$target.append("<span class=\"selected\"></span>");
					$(".inner").empty();
					$(".inner").load($target.attr("name")+".jsp");
				}
			});
		</script>
	<script type="text/javascript">
		$(function() {
			var menu = $("div.btn-group ul.dropdown-menu");
			menu.empty();
			$.ajax({
				url: "<%=basePath%>apps",
				type: "get",
				dataType: "json",
				success: function(data) {
					if (data.length > 0) {
						for (idx in data) {
							menu.append("<li id='drop_btn_" + idx + "'><a href='#'>" + data[idx]['appName'] + "</a></li>");
							$("li#drop_btn_" + idx).click(function() {
								$("div.btn-group a#dropdown-show").html("<i class=\"icon-angle-down\">&nbsp;&nbsp;" + $(this).text());
							});
						}
						$("div.btn-group a#dropdown-show").html("<i class=\"icon-angle-down\">&nbsp;&nbsp;" + data[0]['appName']);
					} else {
						$("div.btn-group a#dropdown-show").text("NO APP NOW!");
					}
					$(".inner").load("home.jsp");
				}
			});
		});
	</script>
	<script type="text/javascript">
		$(function() {
			$("a#btn-create-new-app").click(function() {
				$("ul#menu li.active").removeAttr("class");
				$("span.selected").remove();
				$(".inner").empty();
				$(".inner").load("app_creation_wizard.jsp");
			});
		});
	</script>
</body>
</html>

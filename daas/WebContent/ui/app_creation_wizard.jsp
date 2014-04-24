<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8"%>
<%
	String path = request.getContextPath();
	String uiRoot = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + path + "/ui/";
%>

<div class="box">
	<header>
		<h5 class="text-primary">Create an New Application</h5>
	</header>
	<div class="body">
		<form class="form-horizontal wizardForm" id="create_app" action=""
			method="post">
			<div class="form-group">
				<label for="server_host" class="col-lg-6"> 3-32
					alphanumeric characters, underscores ok. </label>
				<div class="col-lg-10">
					<input type="text" name="app_name" id="app_name"
						class="form-control">
				</div>
			</div>

			<div class="form-actions">
				<input class="navigation_button btn btn-primary" value="Create"
					type="submit" />
			</div>
		</form>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		$("form#create_app").submit(function() {
			var app_name = $.trim($("input#app_name").val());
			if (app_name.length < 3) {
				alert("Error: App name must be at least 3 characters!")
			} else if (app_name.length > 32) {
				alert("Error: App name must be at most 32 characters!");
			} else if (!/^\w+/.test(app_name)) {
				alert("Error: Only alphanumeric characters and underscores are allowed!");
			} else {
				$.ajax({
					url: "<%=uiRoot%>create_app",
					dataType: "json",
					type: "post",
					data: "app_name=" + app_name,
					success: function(data, status) {
						if (data['ok'] == 0) {
							alert("Error: " + data['msg']);
						} else {
							alert("Success!");
							location.href = "index.jsp";
						}
					}
				});
			}
			return false;
		});
	});
</script>

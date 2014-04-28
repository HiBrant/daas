<%
	String path = request.getContextPath();
	String uiRoot = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + path + "/ui/";
%>
<div class="box">
	<header>
		<div class="icons">
			<i class="icon-building"></i>
		</div>
		<h5>User Setting</h5>
	</header>
	<div id="collapse4" class="body">
		<table id="password_table"
			class="table table-bordered table-condensed table-hover table-striped sortableTable">
			<tr>
				<th width="20%">Username</th>
				<td>${sessionScope.username }</td>
			</tr>
			<tr>
				<th>Old Password</th>
				<td><input type="password" /></td>
			</tr>
			<tr>
				<th>New Password</th>
				<td><input type="password" /></td>
			</tr>
			<tr>
				<th>Confirm Password</th>
				<td><input type="password" /></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: right;">
					<input class="navigation_button btn btn-primary" value="Submit" type="submit" />
				</td>
			</tr>
		</table>
	</div>
</div>

<div class="box">
	<header>
		<div class="icons">
			<i class="icon-building"></i>
		</div>
		<h5>App Setting</h5>
	</header>
	<div id="collapse4" class="body">
		<table id="app_table"
			class="table table-bordered table-condensed table-hover table-striped sortableTable">
			<thead>
				<tr>
					<th width="21%">App Name</th>
					<th width="27%">App ID</th>
					<th width="27%">API Key</th>
					<th>Operations</th>
				</tr>
			</thead>
			<tbody>
				<!-- insert data here -->
				<tr>
					<td>${sessionScope.app.appName }</td>
					<td>${sessionScope.app._id }</td>
					<td>${sessionScope.app.apiKey }</td>
					<td>
						<a href="javascript:void(0);"><i class="icon-cogs">Regenerate</i></a>
						<a href="javascript:void(0);" style="margin-left: 25px;"><i class="icon-remove">Delete</i></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

<script type="text/javascript" src="assets/js/jquery.animate-colors-min.js"></script>
<script type="text/javascript">
$(function() {
	$("#password_table input[type='submit']").click(function() {
		var pwdInputs = $("#password_table input[type='password']");
		var oldPwd = $.trim(pwdInputs.eq(0).val());
		var newPwd = $.trim(pwdInputs.eq(1).val());
		var confirmPwd = $.trim(pwdInputs.eq(2).val());
		if (oldPwd == "") {
			alert("Old password cannot be null!");
			return;
		}
		if (newPwd == "") {
			alert("New password cannot be null!");
			return;
		}
		if (confirmPwd != newPwd) {
			alert("The passwords you typed do not match!");
			return;
		}
		
		$.ajax({
			url: "<%=uiRoot%>change_pwd",
			type: "post",
			dataType: "json",
			data: "oldPwd=" + oldPwd + "&newPwd=" + newPwd,
			beforeSend: function() {
				$("#password_table input[type='submit']").val("Loading");
				$("#password_table input[type='submit']").attr("disabled", true);
			},
			success: function(data) {
				if (data.ok == 0) {
					alert(data.msg);
				} else {
					alert(data.msg);
					pwdInputs.val("");
				}
			},
			complete: function() {
				$("#password_table input[type='submit']").val("Submit");
				$("#password_table input[type='submit']").attr("disabled", false);
			}
		});
	});
	
	$("#app_table a").eq(1).click(function() {
		if (confirm("All the data contained in this app will be removed and cannot be resumed!\nAre you sure to delete it?")) {
			if (confirm("This operation cannot be cancelled.\nPlease confirm again!")) {
				$.ajax({
					url: "<%=uiRoot%>delete_app",
					type: "post",
					data: "appid=" + $("#app_table tbody td").eq(1).text(),
					success: function() {
						location.href = "<%=uiRoot%>index.jsp";
					}
				});
			}
		}
	});
	
	$("#app_table a").eq(0).click(function() {
		if (confirm("After regenerating a new api key, your app may fail to pass the authentication!\nAre you sure to continue?")) {
			$.ajax({
				url: "<%=uiRoot%>regenerate_key",
				type: "post",
				data: "appid=" + $("#app_table tbody td").eq(1).text(),
				success: function(data) {
					if (data.ok == 1) {
						var pos = $("#app_table tbody td").eq(2);
						pos.css("color", "#f00");
						pos.animate({color: "#333"}, 5000);
						pos.text(data.key);
					}
				}
			});
		}
	});
});
</script>
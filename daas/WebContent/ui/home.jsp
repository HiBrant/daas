
<div class="box">
	<header>
		<div class="icons">
			<i class="icon-building"></i>
		</div>
		<h5>Current Application</h5>
	</header>
	<div id="collapse4" class="body">
		<table id="dataTable"
			class="table table-bordered table-condensed table-hover table-striped sortableTable">
			<thead>
				<tr>
					<th width="30%">App Name</th>
					<th width="35%">App ID</th>
					<th>API Key</th>
				</tr>
			</thead>
			<tbody>
				<!-- insert data here -->
				<tr>
					<td>${sessionScope.app.appName }</td>
					<td>${sessionScope.app._id }</td>
					<td><a id="btn-show-key" href="javascript:void(0);">Show</a></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript">
$(function() {
	$("a#btn-show-key").click(function() {
		$(this).parent().text("${sessionScope.app.apiKey }");
	});
});
</script>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + path + "/";
%>
<div class="box">
	<header>
		<div class="icons">
			<i class="icon-building"></i>
		</div>
		<h5>All Data Models</h5>
		<div class="toolbar">
			<button class="btn btn-info btn-sm btn-grad">Create New
				Model</button>
		</div>
	</header>
	<div id="collapse4" class="body">
		<table id="dataTable"
			class="table table-bordered table-condensed table-hover table-striped sortableTable">
			<thead>
				<tr>
					<th width="30%">_id</th>
					<th width="25%">Name</th>
					<th width="10%">Semantics</th>
					<th width="10%">Version</th>
					<th>Operations</th>
				</tr>
			</thead>
			<tbody>
				<!-- insert data here -->
			</tbody>
		</table>
	</div>
</div>
<script src="assets/lib/datatables/jquery.dataTables.js"></script>
<script src="assets/lib/datatables/DT_bootstrap.js"></script>
<script src="assets/lib/tablesorter/js/jquery.tablesorter.min.js"></script>
<script src="assets/lib/touch-punch/jquery.ui.touch-punch.min.js"></script>
<script>
$(function() {
	$.ajax({
		dataType:'json',
        url: "<%=basePath%>__model",
		type: 'get',
		headers: {
			"daas-app-id": "${sessionScope.app._id}",
			"daas-api-key": "${sessionScope.app.apiKey}"
		},
		success: function(json) {
			var data = json.list;
			for (var i = 0; i < data.length; i++) {
				var tr = $("<tr></tr>");
				tr.appendTo($("tbody"));
				$("<td>" + data[i]._id + "</td>").appendTo(tr);
				$("<td>" + data[i].modelName + "</td>").appendTo(tr);
				$("<td>" + data[i].semantics + "</td>").appendTo(tr);
				$("<td>" + data[i].version + "</td>").appendTo(tr);
				var opTd = $("<td></td>");
				opTd.appendTo(tr);
				if (data[i].discard) {
					opTd.text("DISCARDED");
					opTd.css("color", "#f00");
				} else {
					var detailBtn = $("<a href='javascript:void(0);' title='Detail'><i class='icon-search'></i>Detail</a>");
					var discardBtn = $("<a href='javascript:void(0);' title='Discard' style='margin-left: 25px;'><i class='icon-remove'></i>Discard</a>");
					detailBtn.appendTo(opTd);
					discardBtn.appendTo(opTd);
				}
			}
			$(".sortableTable").tablesorter();
			$('#dataTable').dataTable({
		        "sDom": "t<'row'<'col-lg-6'f><'col-lg-6 dt_pagination'p>>",
		        "sPaginationType": "bootstrap",
		        "bLengthChange": false,
		        "iDisplayLength": 20
		    });
		},
		error: function(request, textStatus, errorThrown) {
			alert("Error!");
		}
	});
});	
</script>
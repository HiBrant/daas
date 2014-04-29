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
		<table id="model_table"
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
<script src="assets/js/json-format.js"></script>
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
			var details = "";
			for (var i = 0; i < data.length; i++) {
				var tr = $("<tr></tr>");
				tr.appendTo($("#model_table tbody"));
				$("<td>" + data[i]._id + "</td>").appendTo(tr);
				$("<td>" + data[i].modelName + "</td>").appendTo(tr);
				$("<td>" + data[i].semantics + "</td>").appendTo(tr);
				$("<td>" + data[i].version + "</td>").appendTo(tr);
				var opTd = $("<td name='" + data[i]._id + "'></td>");
				opTd.appendTo(tr);
				if (data[i].discard) {
					opTd.text("DISCARDED");
					opTd.css("color", "#f00");
				} else {
					var detailBtn = $("<a href='javascript:void(0);' class='model_detail' title='Show'><i class='icon-search'></i>Detail</a>");
					var discardBtn = $("<a href='javascript:void(0);' class='model_discard' style='margin-left: 25px;' title='Discard'><i class='icon-remove'></i>Discard</a>");
					detailBtn.appendTo(opTd);
					discardBtn.appendTo(opTd);
					
					details += "<pre class='pre_detail' id='detail_" + data[i]._id + "'>" + JsonUti.convertToString(data[i]) + "</pre>";
				}
			}
			$(".sortableTable").tablesorter();
			$('#model_table').dataTable({
		        "sDom": "t<'row'<'col-lg-6'f><'col-lg-6 dt_pagination'p>>",
		        "sPaginationType": "bootstrap",
		        "bLengthChange": false,
		        "iDisplayLength": 20
		    });
			
			$("table").after(details);
			$("pre.pre_detail").hide();
			$("tbody a.model_detail").click(function() {
				if ($(this).attr("title") == "Show") {
					$("pre.pre_detail").hide();
					$("pre#detail_" + $(this).parent().attr('name')).show();
					$("tbody a.model_detail").attr("title", "Show");
					$(this).attr("title", "Hide");
				} else {
					$("pre.pre_detail").hide();
					$("tbody a.model_detail").attr("title", "Show");
				}
			});
			
			$("tbody a.model_discard").click(function() {
				var modelId = $(this).parent().attr('name');
				if (confirm("All the data contained in this model will be removed and cannot be resumed!\nAre you sure to discard it?")) {
					if (confirm("This operation cannot be cancelled.\nPlease confirm again!")) {
						$.ajax({
							url: "<%=basePath%>__model/" + modelId,
							headers: {
								"daas-app-id": "${sessionScope.app._id}",
								"daas-api-key": "${sessionScope.app.apiKey}"
							},
							type: "delete",
							success: function() {
								$(".inner").empty();
								$(".inner").load("model_management.jsp");
							}
						});
					}
				}
			});
		},
		error: function(request, textStatus, errorThrown) {
			alert("Error!");
		}
	});
	
	$("header button").click(function() {
		$(".inner").empty();
		$(".inner").load("create_model.jsp");
	});
});	
</script>
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
		<h5>Data Object Instances</h5>
		<div class="toolbar">
			<select style="margin: 7px 10px 0 0;">
				<option value=0>Please choose...</option>
			</select>
		</div>
	</header>
	<div id="collapse4" class="body">
		<table id="data_table"
			class="table table-bordered table-condensed table-hover table-striped sortableTable">
			<thead>
				<tr>
					<!-- insert title here -->
				</tr>
			</thead>
			<tbody>
				<!-- insert data here -->
				<tr><td>Please choose a model to query its data object instances.</td></tr>
			</tbody>
		</table>
	</div>
</div>
<script src="assets/lib/datatables/jquery.dataTables.js"></script>
<script src="assets/lib/datatables/DT_bootstrap.js"></script>
<script src="assets/lib/tablesorter/js/jquery.tablesorter.min.js"></script>
<script src="assets/lib/touch-punch/jquery.ui.touch-punch.min.js"></script>
<script src="assets/js/json-format.js"></script>
<script type="text/javascript">
$(function() {
	var select = $("div.toolbar select");
	
	$.ajax({
		dataType:'json',
        url: "<%=basePath%>__model",
		type: 'get',
		headers: {
			"daas-app-id": "${sessionScope.app._id}",
			"daas-api-key": "${sessionScope.app.apiKey}"
		},
		success: function(data) {
			if (data.count > 0) {
				$.each(data.list, function(i, model) {
					if (!model.discard) {
						var option = $("<option></option>");
						option.val(model.modelName + "," + model.version);
						option.text(model.modelName + ", ver." + model.version);
						option.appendTo(select);
						select.appendTo($("div.toolbar"));
					}
				});
				
				$("div.toolbar select").change(function() {
					if ($(this).val() == 0) {
						return;
					}
					var modelname = $(this).val().split(",")[0];
					var version = $(this).val().split(",")[1];
					
					$.ajax({
						url: "<%=basePath%>__data/" + modelname + "/" + version + "/all",
						type: "get",
						dataType: "json",
						headers: {
							"daas-app-id": "${sessionScope.app._id}",
							"daas-api-key": "${sessionScope.app.apiKey}"
						},
						success: function(data) {
							var tbody = $("#data_table tbody");
							var thead = $("#data_table thead tr");
							tbody.empty();
							thead.empty();
							if (data.count > 0) {
								$.each(data.list, function(n, obj) {
									if (n == 0) {
										$("<th>_id</th>").appendTo(thead);
										$.map(obj, function(value, key) {
											if (key != '_id') {
												$("<th>"+ key +"</th>").appendTo(thead);
											}
										});
										$("<th>operations</th>").appendTo(thead);
									}
									var tr = $("<tr></tr>");
									$("<td>"+ obj['_id'] +"</td>").appendTo(tr);
									$.map(obj, function(value, key) {
										if (key != '_id') {
											$("<td>"+ value +"</td>").appendTo(tr);
										}
									});
									var td = $("<td></td>");
									td.appendTo(tr);
									var deleteBtn = $("<a href='javascript:void(0);'><i class='icon-remove'></i>Delete</a>");
									deleteBtn.appendTo(td);
									tr.appendTo(tbody);
								});
								
							} else {
								$("<tr><td>No data available in this model.</td></tr>").appendTo(tbody);
							}
							
							$("#data_table tbody a").click(function() {
								var tr = $(this).parent().parent();
								var _id = tr.children("td").eq(0).text();
								tr.remove();
								$.ajax({
									url: "<%=basePath%>__data/" + modelname + "/" + version + "/" + _id,
									type: "delete",
									dataType: "json",
									headers: {
										"daas-app-id": "${sessionScope.app._id}",
										"daas-api-key": "${sessionScope.app.apiKey}"
									},
									success: function(data) {
										if (data.code) {
											alert("Error\nCode: " + data.code + "\n" + data.msg);
										} else {
											alert("Success!");
										}
									}
								});
							});
						}
					});
				});
			}
		}
	});
});
</script>
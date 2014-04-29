<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + 
			":" + request.getServerPort() + path + "/";
%>
<style type="text/css">
table td i.warning {
	color: #f00;
}
</style>
<div class="box">
	<header>
		<div class="icons">
			<i class="icon-building"></i>
		</div>
		<h5>Create New Model</h5>
	</header>
	<div id="collapse4" class="body">
		<table class="table table-bordered table-condensed table-hover table-striped sortableTable" id="model_info_table">
			<tr>
				<th colspan=3>Name &amp; Type</th>
			</tr>
			<tr>
				<td width="20%">Model Name</td>
				<td><input type="text" /></td>
				<td><i class="warning">Required. 3-64 alphanumeric characters.</i></td>
			</tr>
			<tr>
				<td>Semantics</td>
				<td><select>
					<option value="ACID">ACID (MySQL)</option>
					<option value="BASE">BASE (MongoDB)</option>
				</select></td>
				<td><i class="warning">Required.</i></td>
			</tr>
			<tr>
				<td>Version</td>
				<td><input type="text" /></td>
				<td><i class="warning">Not Required. Default value is 1. Numbers only.</i></td>
			</tr>
		</table>
		
		<table class="table table-bordered table-condensed table-hover table-striped sortableTable" id="field_table">
			<tr>
				<th colspan=3>Fields</th>
			</tr>
			<tr>
				<td><b>Name</b><i class="warning">(1-128 alphanumeric characters.)</i></td>
				<td><b>Type</b></td>
				<td><b>Operation</b></td>
			</tr>
			<tr>
				<td><input type="text" /></td>
				<td><select>
					<option value="string">String</option>
					<option value="int">Integer</option>
					<option value="double">Double</option>
					<option value="bool">Boolean</option>
				</select></td>
				<td><a href="javascript:void(0);" onclick='$(this).parent().parent().remove();'><i class="icon-minus-sign"></i>Remove</a></td>
			</tr>
			<tr id="add_field_tr">
				<td colspan=3 align="right"><a href='javascript:void(0);' class="add_field"><i class="icon-plus-sign"></i>Add one more field</a></td>
			</tr>
		</table>
		
		<div style="text-align: right;">
			<input class="navigation_button btn btn-primary" value="Submit" type="submit" />
		</div>
	</div>
</div>
<script type="text/javascript">
$(function() {
	$("a.add_field").click(function() {
		var field = $("<tr><td><input type='text' /></td><td><select><option value='string'>String</option><option value='int'>Integer</option><option value='double'>Double</option><option value='bool'>Boolean</option></select></td><td><a onclick='$(this).parent().parent().remove();' href='javascript:void(0);'><i class='icon-minus-sign'></i>Remove</a></td></tr>");
		$("tr#add_field_tr").before(field);
	});
	
	$("input[type='submit']").click(function() {
		var modelname = $("#model_info_table input").eq(0).val();
		var semantics = $("#model_info_table select").val();
		var version = $("#model_info_table input").eq(1).val();
		var names = new Array();
		var types = new Array();
		if (modelname.length < 3 || modelname.length > 64 || !/^[a-zA-Z0-9]+$/.test(modelname)) {
			alert("Model name doesn't match its requirement!");
			return;
		}
		if (version != "" && isNaN(version)) {
			alert("Version must be a number!");
			return;
		}
		if ($.trim(version) == "") {
			version = 1;
		}
		
		var fieldNames = $("#field_table input");
		for (var i = 0; i < fieldNames.size(); i++) {
			var name = fieldNames.eq(i).val();
			if (name < 1 || name > 128 || !/^[a-zA-Z0-9]+$/.test(name)) {
				fieldNames.eq(i).parent().parent().remove();
			}
		}
		if (confirm("Are you sure to submit?")) {
			fieldNames = $("#field_table input");
			if (fieldNames.size() == 0) {
				alert("There must be one field at least!");
				return;
			}
			var fieldTypes = $("#field_table select");
			for (var i = 0; i < fieldNames.size(); i++) {
				names[i] = fieldNames.eq(i).val();
				types[i] = fieldTypes.eq(i).val();
			}
			
			var json = "{modelName: '"+ modelname +"', semantics: '"+ semantics +"', version: "+ version +", __fields: [";
			for (var i = 0; i < names.length; i++) {
				json += "{name: '"+ names[i] +"', type: '"+ types[i] +"'}";
				if (i != names.length - 1) {
					json += ", ";
				}
			}
			json += "]}";
			
			$.ajax({
				url: "<%=basePath%>__model",
				headers: {
					"daas-app-id": "${sessionScope.app._id}",
					"daas-api-key": "${sessionScope.app.apiKey}"
				},
				type: "post",
				contentType: "application/json",
				dataType: "json",
				data: json,
				success: function(data) {
					if (data.code) {
						alert("Error\nCode: " + data.code + "\n" + data.msg);
					} else {
						$(".inner").empty();
						$(".inner").load("model_management.jsp");
					}
				},
				beforeSend: function() {
					$("input[type='submit']").attr("disabled", true);
					$("input[type='submit']").val("Loading...");
				},
				complete: function() {
					$("input[type='submit']").attr("disabled", false);
					$("input[type='submit']").val("Submit");
				}
			});
		}
	});
});
</script>
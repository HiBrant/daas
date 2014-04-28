<style type="text/css">
table td i {
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
		<table class="table table-bordered table-condensed table-hover table-striped sortableTable">
			<tr>
				<th colspan=3>Name &amp; Type</th>
			</tr>
			<tr>
				<td width="20%">Model Name</td>
				<td><input type="text" /></td>
				<td><i>Required. 3-32 alphanumeric characters, underscores ok.</i></td>
			</tr>
			<tr>
				<td>Semantics</td>
				<td><select>
					<option value="ACID">ACID (MySQL)</option>
					<option value="BASE">BASE (MongoDB)</option>
				</select></td>
				<td><i>Required.</i></td>
			</tr>
			<tr>
				<td>Version</td>
				<td><input type="text" /></td>
				<td><i>Not Required. Default value is 1. Numbers only.</i></td>
			</tr>
		</table>
		
		<table class="table table-bordered table-condensed table-hover table-striped sortableTable">
			<tr>
				<th colspan=2>Fields</th>
			</tr>
			<tr>
				<th width="60%">Name</th>
				<th>Type</th>
			</tr>
			<tr>
				<td><input type="text" /></td>
				<td></td>
			</tr>
		</table>
		
		<div style="text-align: right;">
			<input class="navigation_button btn btn-primary" value="Submit" type="submit" />
		</div>
	</div>
</div>
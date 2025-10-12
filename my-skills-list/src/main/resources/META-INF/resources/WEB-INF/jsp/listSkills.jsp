<%@ include file="common/header.jspf" %>
<%@ include file="common/navigation.jspf" %>

<div class="container">
	<h1>Your Skills are</h1>
	<table class="table">
		<thead>
			<tr>
				<th>Description</th>
				<th>Target Date</th>
				<th>Is Done?</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${skills}" var="skill">
				<tr>
					<td>${skill.description}</td>
					<td>${skill.targetDate}</td>
					<td>${skill.done}</td>
					<td><a href="update-skill?id=${skill.id}" class="btn btn-success">UPDATE</a></td>
					<td> <a href="delete-skill?id=${skill.id}" class="btn btn-warning">DELETE</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<a href="add-skill" class="btn btn-success">Add Skill</a> 	
</div>

<%@ include file="common/footer.jspf" %>
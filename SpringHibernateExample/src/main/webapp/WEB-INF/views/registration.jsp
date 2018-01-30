<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Customer Registration Form</title>

<style>

	.error {
		color: #ff0000;
	}
</style>

</head>

<body>

	<h2>Registration Form</h2>
 
	<form:form method="POST" modelAttribute="customer">
		<form:input type="hidden" path="custId" id="custId"/>
		<table>
			<tr>
				<td><label for="custName">Name: </label> </td>
				<td><form:input path="custName" id="custName"/></td>
				<td><form:errors path="custName" cssClass="error"/></td>
		    </tr>
	    
			<tr>
				<td><label for="custUserName">User Name: </label> </td>
				<td><form:input path="custUserName" id="custUserName"/></td>
				<td><form:errors path="custUserName" cssClass="error"/></td>
		    </tr>
			<tr>
				<td><label for="emailId">emailId: </label> </td>
				<td><form:input path="emailId" id="emailId"/></td>
				<td><form:errors path="emailId" cssClass="error"/></td>
		    </tr>
		    <tr>
				<td><label for="contactNo">contactNo: </label> </td>
				<td><form:input path="contactNo" id="contactNo"/></td>
				<td><form:errors path="contactNo" cssClass="error"/></td>
		    </tr>
		    <tr>
				<td><label for="resAddress">Address: </label> </td>
				<td><form:input path="resAddress" id="resAddress"/></td>
				<td><form:errors path="resAddress" cssClass="error"/></td>
		    </tr>
			<tr>
				<td><label for="password">Password: </label> </td>
				<td><form:input path="password" id="password"/></td>
				<td><form:errors path="password" cssClass="error"/></td>
		    </tr>
		    <tr>
				<td colspan="3">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update"/>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Register"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</form:form>
	<br/>
	<br/>
	Go back to <a href="<c:url value='/list' />">List of All Employees</a>
</body>
</html>
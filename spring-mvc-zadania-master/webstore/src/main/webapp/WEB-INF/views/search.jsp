<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Search Book</title>
</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Search book</h1>
				<p>This page allows search books</p>
				<p class="text-right" style="padding-right: 15px">
					<a href="<spring:url value="login" />" class="btn btn-primary"
						value="login"> <span class="glyphicon-info-sign glyphicon" /></span>
						Login
					</a>
				</p>
				<p class="text-right" style="padding-right: 15px">
					<a href="<c:url value="/j_spring_security_logout" />"
						class="btn btn-danger" value="logout"> <span
						class="glyphicon-info-sign glyphicon" /></span> Logout
					</a>
				</p>
			</div>
		</div>
	</section>
	<section class="container">
		<div class="row">
			<div class="col-sm-6 col-md-3" style="padding-bottom: 15px">
				<div class="thumbnail">
					<div class="caption">
						<h3>Search book</h3>
						<p>Search by title</p>
						<form method="get"
							action="<spring:url value="/books/search/results" />">
							<input type="text" name="title" size="10" /><br /> <br /> <input
								type="submit" value="Submit" class="btn btn-primary" />
						</form>
						<p>Search by authors</p>
						<form method="get"
							action="<spring:url value="/books/search/results" />">
							<input type="text" name="author" size="10" /><br /> <br /> <input
								type="submit" value="Submit" class="btn btn-primary" />
						</form>
					</div>
				</div>
			</div>
		</div>
		<div>
			<p>
				<a href="<spring:url value="/" />" class="btn btn-info"> <span
					class="glyphicon-hand-left glyphicon"></span> back
				</a>

			</p>
		</div>
	</section>
</body>
</html>

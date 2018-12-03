<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <!-- <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1"> -->
        
        <link href="<c:url value="/css/jquery-ui.css" />" rel="stylesheet">
        <Script src="<c:url value="/js/jquery.js" />"></script>
        <Script src="<c:url value="/js/jquery-ui.js" />"></script>

        <link href="<c:url value="/css/bootstrap.css" />" rel="stylesheet">	
		<Script src="<c:url value="/js/bootstrap.js" />"></script>	
		
		<link href="<c:url value="/css/star-rating.css" />" rel="stylesheet">	
		<Script src="<c:url value="/js/star-rating.js" />"></script>
		
    </head>
    <body>
        <div class="container">
            <tiles:insertAttribute name="header" />
            <tiles:insertAttribute name="content" />
            <tiles:insertAttribute name="footer" />
        </div>
    </body>
</html>
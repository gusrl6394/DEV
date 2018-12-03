<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
$(document).ready(function() {
	var param = {
		id:1234,
      	name:"Sehong Park"
	}
	$.ajax({
		type : 'get',		
		dataType: 'JSON',
        data: param,
        url:"<c:url value='/test/ajax'/>",
        success : function(data) {
         var str = JSON.stringify(data, null, 2);
		 alert(str);
        },
        complete : function(data) {
        },
        error : function(xhr, status, error) {
         alert("에러발생");
        }
    });

});
</script>
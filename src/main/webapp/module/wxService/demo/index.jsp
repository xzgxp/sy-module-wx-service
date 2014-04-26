<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" />
<title>Insert title here</title>
</head>
<body>
	
	<ul>
		<%
			Enumeration names = request.getSession().getAttributeNames();
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				System.out.println(name);
		%>
				<li><%=name %> ： <%=request.getSession().getAttribute(name) %></li>
		<%
			}
		%>
	</ul>


</body>
</html>
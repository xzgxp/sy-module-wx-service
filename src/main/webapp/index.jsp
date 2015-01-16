<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test</title>
</head>
<body>

	
	<script src="http://libs.baidu.com/jquery/1.9.0/jquery.js" type="text/javascript" ></script>
	<script src="http://libs.baidu.com/json/json2/json2.js"></script>
	<script src="wx.js" type="text/javascript" ></script>
	<script type="text/javascript" >
			$(function() {
					window.support.share(
					'title', 
					'desc', 
					'http://ico.ooopic.com/iconset01/signs-icons/512/143463.png', 
					'http://www.baidu.com', 
					function(type, result, parmar) {
							alert("type:"+type+",result:"+result+",parmar:"+parmar);
						});
				});
	</script>

</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	<meta name="decorator" content="blank"/>
	<style type="text/css">
    	.header	{
    		height:80px;
    		padding-top:20px;
    	} 
      	.alert	{
      		position:relative;
      		width:300px;
      		margin:0 auto;
      		*padding-bottom:0px;
      	}
      	label.error	{
      		background:none;
      		width:270px;
      		font-weight:normal;
      		color:inherit;
      		margin:0;
      	}
      	div.wrap {
      		position:fixed;
      		width:100%;
      		height:100%;
      		top;0;
      		left:0;
      	}
		div.subwarp {
			position: absolute;
			top:0;
			left:0;
			bottom:0;
			right:0;
			width:50%;
			height:95%;
			margin:auto;
		}
    </style>
</head>
<body>
	<div class="wrap">
	    <div class="subwarp">
	    	<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}">
	    		<button data-dismiss="alert" class="close">×</button>
				<label id="loginError" class="error">${message}</label>
			</div>
			<div>
		    	<font size="6" style="position: relative;top:200px;text-align: center;">
		    		欢迎<font style="color:orange;">${fns:getUser().name}</font>登录${fns:getConfig('productName')}
		    	</font>
			</div>
	    </div>
	</div>
</body>
</html>
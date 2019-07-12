
<%@page import="com.ecity.java.json.JSONObject"%>
<%@page import="com.ecity.java.web.service.ArticleService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String ID = request.getParameter("ID") == null ? "-1" : (String) (request.getParameter("ID"));
ArticleService svc=new ArticleService();
JSONObject json=svc.GetArticle(ID, false);

if (!json.has("Data")){
  %>
  <div>文章信息不存在！</div>
  <%
  return;
}

JSONObject Data=json.getJSONObject("Data");

%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><%=Data.getString("Caption")%></title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="keywords" content="<%=Data.getString("KeyWord")%>">
<meta name="description" content="<%=Data.getString("Abstract")%>">
<link href="../umeditor/themes/default/css/umeditor.min.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="../assets/style/index.css" media="all">


<script src="/Res/js/jquery.min.js"></script>
<script>
/*
        var mobileAgent = new Array("iphone", "ipod", "android", "mobile", "blackberry", "webos", "incognito",
            "webmate", "bada", "nokia", "lg", "ucweb", "skyfire");
        var browser = navigator.userAgent.toLowerCase();
        var isMobile = false;
        for(var i = 0; i < mobileAgent.length; i++) {
            if(browser.indexOf(mobileAgent[i]) != -1) {
                isMobile = true;
            }
        }
        if(isMobile){
            console.log("手机端");
            window.location.href = 'Mobileindex.html';
        }
*/
    </script>
<link rel="stylesheet" href="../assets/layui/css/layui.css" media="all">
<style type="text/css">
.main-layout-side .m-logo {
  width: 100%;
  height: 40px;
  background: #00b5f9;
  text-align: center;
  line-height: 40px;
  font-weight: 800;
  font-size: 20px;
  color: white;
  font-style: oblique;
}
</style>

<script type="text/javascript" src="https://s5.cnzz.com/z_stat.php?id=1277228488&web_id=1277228488"></script>


</head>

<body class="layui-layout-body">
  <div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
      <div class="layui-header">
        <!-- 
        <div class="layui-logo" lay-hrefX="">
          <img src="../image/logo.png" alt="大巅" style="width: 40px; height: 40px;"> <span style="font-size: 1.5em; color: #fff;">大巅</span>
        </div>
        -->
        <!-- 
        <ul class="layui-nav layui-layout-left">
          <li class="layui-nav-item layadmin-flexible" lay-unselect><a href="javascript:;" layadmin-event="flexible" title="侧边伸缩" lay-tips="侧边伸缩"> <i
              class="layui-icon layui-icon-shrink-right" id="LAY_app_flexible"
            ></i>
          </a></li>
          <li class="layui-nav-item" lay-unselect><a href="javascript:;" layadmin-event="refresh" title="刷新"> <i class="layui-icon layui-icon-refresh-3"></i>
          </a></li>
        </ul>
         -->
        <!-- 头部区域 -->
        <div class="layui-side1 layui-side-menu1">
          <div class="layui-side-scroll1">
            <ul class="layui-nav-temp layui-layout-right" lay-filter="layadmin-layout-right"></ul>
          </div>
        </div>
      </div>
      <!-- 主体内容 -->
      <div class="layui-body" id="LAY_app_body">
        <div class="ContentDiv" >
          <div>
            <h1><%=Data.getString("Caption")%></h1>  
          </div>
          <div>
            <%=Data.getString("Content")%>
          </div>   
        </div>
      </div>
    </div>
    <!-- 辅助元素，一般用于移动设备下遮罩 -->
    <div class="layadmin-body-shade" layadmin-event="shade"></div>
  </div>
  <script src="../assets/layui/layui.js"></script>
  <script>
  layui.config({
    base: '../assets/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use('index');
  </script>
  
  

</body>


</html>
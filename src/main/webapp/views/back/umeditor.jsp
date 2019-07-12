<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String  ID=request.getParameter("ID")==null?"0":request.getParameter("ID");
String  Type=request.getParameter("Type")==null?"1":request.getParameter("Type");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>umeditor</title>
    <link href="<%=request.getContextPath() %>/umeditor/themes/default/css/umeditor.min.css" type="text/css" rel="stylesheet">

    <script src="<%=request.getContextPath() %>/umeditor/third-party/jquery.min.js"></script>
    
    <script src="<%=request.getContextPath() %>/umeditor/third-party/template.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/umeditor/umeditor.config.js?d=1"></script>
    <script type="text/javascript" charset="utf-8" src="<%=request.getContextPath() %>/umeditor/umeditor.min.js?d=1"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/umeditor/lang/zh-cn/zh-cn.js"></script>
    <link rel="stylesheet" href="../../assets/layui/css/layui.css" media="all">
    <script src="https://cdn.staticfile.org/angular.js/1.4.6/angular.min.js"></script>
  
<script type="text/javascript" src="../../assets/js/ParameterData.js"></script>
    <script src="../../assets/layui/layui.js"></script>
    <script src="../../assets/layui/init.js"></script>
    
    <style type="text/css">
        .disabled {
            opacity: 0.5;
            cursor: default;
            *filter: alpha( opacity=50 );
        }
        .links a{
            color: #ff5400;
            margin-right: 5px;
        }
        .links a.green{
            color: green;
        }
        img{
          width: 100%;
        }
        
        .layui-upload-drag {
          background-color: #ffffff9e;
          padding-top: 100px;
          padding-bottom: 0px;
          opacity: 0;
          visibility: hidden;
          transition: all 0.3s linear;
        }
        .layui-upload-drag-out:hover  .layui-upload-drag {
          visibility: visible;
          opacity: 1;
          transition: all 0.3s linear;
          border: 1px red solid;
        }
    </style>

</head>
<body id ='umeditorApp' ng-app="umeditorApp" ng-controller="umeditorCtrl" >
  <div class="layui-form" style="width: calc(100vw - 45px);padding: 10px;">
    <div class="layui-form-item" >
      <label class="layui-form-label">ID</label>
      <div class="layui-input-block">
        <input type="text"  class="layui-input" ng-model="Data.ID" readonly>
      </div>
    </div>
    <div class="layui-form-item"  ng-hide="Data.Type=='5'">
      <label class="layui-form-label">类型</label>
      <div class="layui-input-block">
        <select id='DataType' ng-model="Data.Type"  >
          <option value="-1" ></option>
          <option value="{{a.id}}" ng-repeat="a in ArticleType">{{a.Name}}</option>
        </select>
      </div>
    </div>        
    <div class="layui-form-item">
      <label class="layui-form-label">标题</label>
      <div class="layui-input-block">
        <input type="text"  placeholder="请输入标题" class="layui-input" ng-model="Data.Caption" >
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">关键字</label>
      <div class="layui-input-block">
        <input type="text"  placeholder="关键字" class="layui-input" ng-model="Data.KeyWord" >
      </div>
    </div>
    <div class="layui-form-item">
      <label class="layui-form-label">摘要</label>
      <div class="layui-input-block">
        <input type="text"  placeholder="摘要" class="layui-input" ng-model="Data.Abstract" >
      </div>
    </div>
    
    <div class="layui-form-item">
      <label class="layui-form-label">文章图片</label>
      <div class="layui-input-block" >
        <div class="layui-upload-drag-out" style="width: 496px;height: 246px;position: relative;box-sizing: border-box;">         
          <img ng-src="{{Data.ImageUrl}}" style="width: 100%;height: 100%;">
          <div class="layui-upload-drag" id="ImageUrl" style="width: 496px;height: 246px;position:absolute;box-sizing: border-box;top: 0;left: 0;">
            <i class="layui-icon"></i>
            <p style="color:#000;">点击上传，或将文件拖拽到此处</p>
          </div>
        </div>
      </div>
    </div>
    
    
    
    
    
    <input type="hidden" id="ContentType" ng-model="Data.ContentType" value="<%=Type%>" >
  </div>    
  <div style="padding: 10px;">
    <button class="layui-btn" ng-click="PostArticle()" style="width:calc(100% - 10px);">保存</button>
  </div>  
  <div style="width: calc(100vw - 45px);padding: 10px;height: calc(100vh - 30px);">
      <script type="text/plain" id="editor" style="width:100%;height:100%;"></script>
  </div>
<script type="text/javascript">

var ArticleID=<%=ID%>;
<%
if (Type.equals("2")){
  %>
  var ContentType='5';
  <%  
}
else{
  %>
  var ContentType='-1';
  <%
}  
%>
  var serverPath = '/websiteDD/umeditor/',
  um = UM.getEditor('editor', {
      imageUrl:serverPath + "jsp/imageUp.jsp",
      //imagePath:serverPath,
      imagePath:"",
      lang:/^zh/.test(navigator.language || navigator.browserLanguage || navigator.userLanguage) ? 'zh-cn' : 'en',
      langPath:UMEDITOR_CONFIG.UMEDITOR_HOME_URL + "lang/",
      focus: true
  });
</script>    
<script src="umeditor.js"></script>
</body>
</html>
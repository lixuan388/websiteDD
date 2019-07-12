var app = angular.module('umeditorApp', []);
app.controller('umeditorCtrl', function($scope, $http, $location) {
  $scope.Data={};  
  

  $scope.ArticleType=[]; 

  var ArticleType=GetParameterData('ArticleType');
  for (i in ArticleType){
    var j={'id':i,'Name':ArticleType[i]}
    $scope.ArticleType.push(j);
  }
  console.log('$scope.ArticleType');
  console.log($scope.ArticleType);
  
  $scope.Init=function(){
    $http({
      method: 'get',
      url : '../../GetArticle.json?ID='+ArticleID+'&d=' + new Date().getTime(),
    }).then(function successCallback(response) {
      var data=response.data;
      
      console.log(data);
      if (data.MsgID==1){
        $scope.Data=data.Data;
        if ($scope.Data.Type==''){
          $scope.Data.Type=ContentType;
        }
        UM.getEditor('editor').setContent($scope.Data.Content, false);
        console.log($scope.Data);
      }
      else{
        return;
      }      
    }, function errorCallback(response) {
      // 请求失败执行代码
    });    
//    $http({
//      method: 'get',
//      url : '../../ArticleType.json?d=' + new Date().getTime(),
//    }).then(function successCallback(response) {
//      var data=response.data;
//      
//      console.log(data);
//      if (data.MsgID==1){
//        $scope.ArticleType=data.Data;
//        //layui.form.render();
//      }
//      else{
//        return;
//      }      
//    }, function errorCallback(response) {
//      // 请求失败执行代码
//    });
    
    var script = document.createElement("script");
    script.src = "umeditor2.js";
    script.type = "text/javascript";
    document.body.appendChild(script);
    
    
  }

  $scope.PostArticle=function(){
    console.log($('#DataType').val());
    
    
    var Data=$scope.Data;
    if (ContentType!=5){
      Data.Type=$('#DataType').val();
    }
    Data.ID=''+Data.ID;
    Data.UpdateDate=new Date().getTime();
//    if (Data.Caption)
    

    Data.Content=UM.getEditor('editor').getContent();
    Data.ContentType=$('#ContentType').val();
    
    
    console.log(Data);
    
    var json ={'Data':Data}
    $http({
      method: 'POST',
      data:JSON.stringify(json),
      url:'../../PostArticle.json'
    }).then(function successCallback(response) {
      var data=response.data;
      if (data.MsgID==1){
        layer.alert("数据保存成功！", "提示");
      }
      else{
        layer.alert(data.MsgText, "错误");
        return;
      }      
    }, function errorCallback(response) {
      // 请求失败执行代码
    });
  }
  $scope.Init();
  
  
}); 


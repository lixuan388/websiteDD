      layui.config({
        base : '../../assets/' // 静态资源所在路径      
      }).use(['form' ,'layer','upload'], function() {
        var $ = layui.jquery, upload = layui.upload, setter = layui.setter,
        layer = layui.layer;
        var form = layui.form;
        /*
        var script = document.createElement("script");
        script.src = "umeditor.js";
        script.type = "text/javascript";
        document.body.appendChild(script);
        */      
        //拖拽上传
        upload.render({
          elem: '#ImageUrl'
          ,url: webConfig.ContextPath+'/UploadFile'
          ,done: function(res){
            console.log(res);
            angular.element(umeditorApp).scope().Data.ImageUrl='/UploadFile/'+res.FileList[0];
            angular.element(umeditorApp).scope().$apply();
          }
        });
        console.log('upload.render');
      });
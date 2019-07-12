layui.config({
  base : '../assets/' // 静态资源所在路径
}).extend({
  index : 'lib/index' // 主入口模块

}).define(['index','carousel'], function(exports){
  var $=layui.$
  ,setter = layui.setter
  ,element = layui.element
  ,admin = layui.admin
  ,tabsPage = admin.tabsPage
  ,view = layui.view
  ,carousel=layui.carousel

  ,LoadSliderWrapper=function(){
    view.req({
      type: 'get'
      ,url: setter.ContextPath+'/Menu.json'
      ,dataType: 'json'
      ,success: function(res){
        router = layui.router();
        //console.log(res);
        if (res.MsgID==1){          
//          console.log(router);
          SliderWrapper=$('#slider-wrapper div');
          SliderWrapper.html('');
          
          
          var path=router.path;
//          console.log(path);
          var data=res.data[path[0]];
          
          //console.log(data);
          
          for (i=1;i<path.length;i++){
            
            data=data.list[path[i]];
//            console.log(data);
          }
          var KeyWord=data.KeyWord;
          var meta=$('meta[name=keywords]');

//          console.log(meta);
          if (meta.length==0){
            meta=$('<meta name="keywords">');
            $('head').append(meta);
          }
          meta.attr("content",KeyWord);
          $('head').find('title').html(data.title);
          if (data.imageList.length>1){
            for (i in data.imageList){
              //console.log("index:"+i);
              var url=data.imageList[i];
              var Content=data['Content'+i]?data['Content'+i]:"";
//              console.log("Content:"+Content);
              SliderWrapper.append('<div > <img class="slide-li" src="'+url+'">'+Content+'</div>');
            }
            SliderWrapper.parent().addClass('layui-carousel');
            SliderWrapper.parent().addClass('slider-wrapper');
            carousel.render({
              elem: '#slider-wrapper'
              ,width: '100vw'
              ,height: '100vh'
              ,anim:'updown'
              ,interval: 50000
            });
          }
          else if (data.imageList.length==1){
            var url=data.imageList[0];
            var Content=data['Content0']?data['Content0']:"";
//            console.log("Content:"+Content);
            SliderWrapper.html('<div ><img style="width: 100%;" src="'+url+'">'+Content+'</div>');
          }

          if (document.getElementById("ContentListDiv" )){
            angular.bootstrap(document.getElementById("ContentListDiv" ), ['ContentListApp']); // 手动加载2
          }

          if (document.getElementById("ContentList2Div" )){
            angular.bootstrap(document.getElementById("ContentList2Div" ), ['ContentListApp']); // 手动加载2
          }

          
        }

      }
    });
  }
  
  LoadSliderWrapper();
  window.onhashchange=function(){
    LoadSliderWrapper();
  }
  
  carousel.on('change(slider-wrapper)', function(obj){ //test1来源于对应HTML容器的 lay-filter="test1" 属性值
    console.log(obj.item); //当前条目的索引
    obj.item.find('.swiper-container').each(function(){
      console.log(this);
      swiper = new Swiper({
        el: '.swiper-container',
        centeredSlides:false,
        slidesPerView:2,
        spaceBetween:window.innerHeight /864 * 10 ,
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev',
        },
      });
    })

  });     
  
  //对外输出
  exports('page', {
    LoadSliderWrapper:LoadSliderWrapper
  });
});

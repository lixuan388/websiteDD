layui.config({
  base : '../../assets/' // 静态资源所在路径
}).extend({
  index : 'lib/index' // 主入口模块
}).use(
    [ 'index', 'tree', 'laytpl', 'upload','layer' ],
    function() {
      var $ = layui.jquery, setter = layui.setter, admin = layui.admin, laytpl = layui.laytpl, upload = layui.upload, $body = $('body'), Level1Tpl = $("#MenuLevel1").html()
      ,layer=layui.layer
      ,Level2Tpl =$("#MenuLevel2").html()
      , menu = {
        LoadMenu : function() {
          admin.req({
            //url : setter.ContextPath + '/assets/json/menu.json',
            url : setter.ContextPath + '/Menu.json',
            type : 'get',
            success : function(res) {
              if (res.MsgID != 1) {
                console.log(res.MsgText);
              } else {
                var menu = res.data;
                var nav = $('[lay-filter=lay-menu]');
                for (item in menu) {
                  var list = '';
                  if (menu[item].list) {
                    var listData = menu[item].list
                    list = $('<ul></ul>');
                    for (listItem in listData) {
                      listData[listItem].KeyWord=listData[listItem].KeyWord?listData[listItem].KeyWord:'';
                      laytpl(Level2Tpl).render(listData[listItem], function(html) {
                        list.append(html)
                      });
                    }
                  }
                  laytpl(Level1Tpl).render(menu[item], function(html) {
                    var li = $(html);
                    li.append(list);
                    nav.append(li);
                  });
                }
                nav.addClass('lay-menu');
                sortable();
              }
            }
          });
        },
        titleEdit : function(othis) {
          var v = $(othis).find("span").html();
          layer.prompt({
            formType : 0,
            value : v,
            title : '请输入菜单名称',
          }, function(value, index, elem) {

            $(othis).find("span").html(value);
            layer.close(index);
          });
        },
        KeyWordEdit : function(othis) {
          var v = $(othis).find("span").html();
          layer.prompt({
            formType : 2,
            value : v,
            title : '请输入关键字',  area: ['400px', '150px'] //自定义文本域宽高
          }, function(value, index, elem) {

            $(othis).find("span").html(value);
            layer.close(index);
          });
        },
        AddMenu : function(othis) {
          layer.prompt({
            formType : 0,
            value : '菜单名称',
            title : '请输入菜单名称',
          }, function(value, index, elem) {

            list = $(othis).parent().find(">ul");
            var listData={'title':value};
            laytpl(Level2Tpl).render(listData, function(html) {
              list.append(html)
            });
            sortable();
            layer.close(index);
          });
        },
        InsertFirstMenu : function(othis) {
          layer.prompt({
            formType : 0,
            value : '菜单名称',
            title : '请输入菜单名称',
          }, function(value, index, elem) {

            list = $(othis).parent().parent();
            var listData={'title':value};
            laytpl(Level1Tpl).render(listData, function(html) {
              var li = $(html);
              li.append("<ul></ul>");
              list.append(li);
            });            
            sortable();
            layer.close(index);
          });
        },
        DelMenu : function(othis) {
          layer.confirm('是否确认删除此菜单？', {icon: 3, title:'提示'}, function(index){
            //do something
            li = $(othis).parent();
            li.remove();
            layer.close(index);
            layer.close(index);
          }, function(index){
            //do something
            layer.close(index);
          });
        },
        DelImage:function(othis){
          var img=$(othis).parent().parent();
          img.remove();
        },
        submit : function() {
          console.log($(this));
          console.log('$(this)[0].files.length');
          console.log($(this)[0].files.length);
          // var $loadingToast = $('#loadingToast');
          // $loadingToast.fadeIn(100);
          for (i = 0; i < $(this)[0].files.length; i++) {
            var formData = new FormData();
            formData.append('file', $(this)[0].files[i]);
            var tpl = $('<li class="pic-item"  ><div class="pic-img"><img src="../../image/add-item-load-img.png" ></div></li>');
            $(this).parent().before(tpl);
            menu['req'].call(this, tpl, formData);
          }
        },
        req : function(tpl, formData) {
          console.log("menu['req'].call this");
          admin.req({
            url : setter.ContextPath +'/UploadFile',
            type : 'POST',
            dataType : 'json',
            cache : false,
            data : formData,
            processData : false,
            contentType : false,
            success : function(json) {
              tpl.find("img").attr("src", "/UploadFile/" + json.FileList[0]);
            }
          });
        }
        ,getLevel:function(othis){
//          console.log(othis);
          var name=$(othis).find('>a>span').html();          
          var list=[]
          $(othis).find('>ul>li').each(function(){
//            console.log('has level');
            list.push(menu['getLevel'].call(this, this));
          });          
          var imageList=[]
          $(othis).find('>div>ul>li>.pic-img>img').each(function(){
            var src=$(this).attr('src')
            imageList.push(src);
          });
          var KeyWord=$(othis).find('[lay-event=KeyWordEdit]>span').html();
          var levelData={'name':name,'title':name,'list':list,'imageList':imageList,'KeyWord':KeyWord};
          return levelData;
        }
        ,PostData:function(){
          var MenuData=[];
          $('#lay-menu').find('>li').each(function(){
            var levelData=menu['getLevel'].call(this, this);
            MenuData.push(levelData);
          })
          var data={"data":MenuData}
          admin.req({
            url : setter.ContextPath +'/PostMenuData',
            type : 'POST',
            dataType : 'json',
            cache : false,
            data : JSON.stringify(data),
            processData : false,
            contentType : false,
            success : function(json) {
              layer.alert(json.MsgText, function(index){
                layer.close(index);
              });       
            }
          });
        }
        ,
      }
      
      function sortable(){
        $("ul#local_upload").sortable({
          cursor: "move",
          items :"li.pic-item",                        //只是li可以拖动
          opacity: 0.6,                       //拖动时，透明度为0.6
          revert: true,                       //释放时，增加动画
          update : function(event, ui){       //更新排序之后
              console.log($(this).sortable("toArray"));
          }
         });
      }
      // 点击事件
      $body.on('click', '*[lay-event]', function() {
        var othis = $(this), attrEvent = othis.attr('lay-event');
        menu[attrEvent] && menu[attrEvent].call(this, othis);
      });
      $body.on('change', '[type=file]', function() {
        var othis = $(this), attrEvent = othis.attr('lay-event');
        menu['submit'].call(this, othis);
      });

      menu.LoadMenu();
    });

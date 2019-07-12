layui.extend({
  index : 'lib/index' // 主入口模块
}).use([ 'index', 'console', 'element' ], function() {
  var $ = layui.$, setter = layui.setter, admin = layui.admin, element = layui.element;
  // 读取订单状态统计

  function CreateMenu() {
    for (i in tree) {
      // console.log(tree[i])
      var p = tree[i];
      var li = $('<li data-name="home" class="layui-nav-item"></li>');
      var href=p.href?'lay-href="' + p.href + '"':'';
      li.append('<a href="javascript:;" lay-tips="' + p.text + '" lay-direction="2" '+href+'><i class="layui-icon ' + p.icon + '"></i><cite>' + p.text + '</cite></a>');
      if (p.nodes.length > 0) {
        var dl = $('<dl class="layui-nav-child"></dl>');
        for (j in p.nodes) {
          var nodes = p.nodes[j];
          var dd = '<dd><a href="javascript:;" lay-href="' + nodes.href + '" ><i class="layui-icon ' + nodes.icon + '"></i><cite>' + nodes.text + '</cite></a></dd>'
          dl.append(dd);
          if (nodes.IsLink) {
            $('.layadmin-shortcut ul').append('<li class="layui-col-xs3"><a lay-href="' + nodes.href + '"><i class="layui-icon ' + nodes.icon + '"></i><cite>' + nodes.text + '</cite></a></li>');
          }
        }
        li.append(dl);
      }
      $('.layui-side-menu .layui-nav').append(li);
    }
    element.render();
  }
  
  function LoadCacheData()  {
    admin.req({
      url : webConfig.ContextPath + '/ArticleType.json',
      type : 'get',
      success : function(res) {
        if (res.MsgID != 1) {
          console.log(res.MsgText);
        } else {
          
          var data={};
          $.map(res.Data,function(i,item){  

            data[res.Data[item].id]=res.Data[item].Name;
          });  
          console.log(data);
          SetParameterData('ArticleType',data);
        }
      }
    });
  };
  
  CreateMenu();
  LoadCacheData();
  

})

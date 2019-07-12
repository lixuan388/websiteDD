layui.config({
  base : '../../assets/' // 静态资源所在路径
}).extend({
  index : 'lib/index' // 主入口模块
}).use([ 'index', 'table', 'layer' ], function() {
  var $ = layui.jquery, admin = layui.admin, $body = $('body'), layer = layui.layer

  var table = layui.table;

  //第一个实例
  table.render({
    elem : '#UserTable',
    url : '../../User.json' //数据接口
    ,
    page : false //开启分页
    ,
    cols : [ [ //表头
    {
      field : 'ID',
      title : 'ID',
      width : 80,
      align : 'center'
    }, {
      field : 'UserName',
      title : '用户名',
      edit : 'text',
      align : 'center'
    }, {
      field : 'PassWord',
      title : '密码',
      edit : 'text',
      align : 'center'
    } ] ]
    ,parseData: function(res){ //res 即为原始返回的数据
      return {
        "code": res.MsgID, //解析接口状态
        "msg": res.MsgText, //解析提示文本
        "count": res.data.length, //解析数据长度
        "data": res.data //解析数据列表
      };
    }  
    ,response: {
     statusCode: 1 //规定成功的状态码，默认：0
      } 
  
  });

  // 点击事件
  $body.on('click', '*[lay-event]', function() {
    var othis = $(this), attrEvent = othis.attr('lay-event');
    menu[attrEvent] && menu[attrEvent].call(this, othis);
  });
});

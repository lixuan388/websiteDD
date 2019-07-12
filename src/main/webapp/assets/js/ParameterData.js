function GetParameterData(name)
{
  
  return layui.data('DD.layuiAdmin.'+name+'.Json').json;
}

function SetParameterData(name,json)
{
  layui.data('DD.layuiAdmin.'+name+'.Json',{'key':'json','value':json});
}
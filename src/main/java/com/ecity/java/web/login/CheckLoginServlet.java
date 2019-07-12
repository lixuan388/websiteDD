package com.ecity.java.web.login;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.ecity.java.json.JSONArray;
import com.ecity.java.json.JSONObject;
import com.java.sql.MongoConnect;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * 登录验证
 */

@WebServlet("/Login/CheckLogin")
public class CheckLoginServlet extends HttpServlet {

  private static final long serialVersionUID = 1221671299145751538L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // TODO Auto-generated method stub
    Map<String, String[]> params = req.getParameterMap();
    String UserCode = params.get("UserCode") == null ? "" : (String) (params.get("UserCode")[0]);
    String PassWord = params.get("PassWord") == null ? "" : (String) (params.get("PassWord")[0]);
    CheckLogin(req, resp, UserCode, PassWord);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // TODO Auto-generated method stub
    doGet(req, resp);
  }

  public void CheckLogin(HttpServletRequest req, HttpServletResponse resp, String UserCode, String PassWord)
      throws IOException {
    resp.setContentType("application/json;charset=utf-8");
    resp.setCharacterEncoding("UTF-8");
    resp.setHeader("Cache-Control", "no-cache");
    UserCode = URLDecoder.decode(UserCode, "UTF-8");
    JSONObject ReturnJson = new JSONObject();
    
    if (UserCode.equals("")) {
      ReturnJson.put("MsgID", "-1");
      ReturnJson.put("MsgTest", "请输入用户名！");
    } else {
      if (CheckUser(req,UserCode, PassWord))
      {
        ReturnJson.put("MsgID", "1");
        ReturnJson.put("MsgTest", "Success!");
        ReturnJson.put("UserName", UserCode);
      }
      else {
        ReturnJson.put("MsgID", "-1");
        ReturnJson.put("MsgTest", "用户名不存在");
      }
    }
    resp.getWriter().print(ReturnJson.toString());
    resp.getWriter().flush();
  }
  
  public boolean CheckUser(HttpServletRequest req,String UserName,String PassWord) {
    try {
      // 连接到 mongodb 服务
      MongoDatabase mongoDatabase = MongoConnect.GetConnect();
      MongoCollection<Document> collection = mongoDatabase.getCollection("User");
      FindIterable<Document> findIterable = collection.find(Filters.eq("UserName", UserName));
      MongoCursor<Document> mongoCursor = findIterable.iterator();
      if (mongoCursor.hasNext()) {
        Document document = mongoCursor.next();
        String jsonStr = document.toJson();
        JSONObject json = new JSONObject(jsonStr);
        
        String PassWord2=json.getString("PassWord");
        if (PassWord.equals(PassWord2))
        {
          HttpSession session = req.getSession();
          String sessionId = session.getId();
          // 将数据存储到session�?
          session.setAttribute("UserSessionID", sessionId);
          session.setAttribute("UserID", json.getString("ID"));
          session.setAttribute("UserName", json.getString("UserName"));
          return true;
        }
        else
        {
          return false;
        }
      }
      else
      {
        return false;
      }
      
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      return false ;
    }
  }

}

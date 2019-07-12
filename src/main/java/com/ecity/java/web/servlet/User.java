package com.ecity.java.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.ecity.java.json.JSONArray;
import com.ecity.java.json.JSONObject;
import com.ecity.java.web.WebFunction;
import com.java.sql.MongoConnect;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@WebServlet("/User.json")

public class User extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 6530571141910637086L;


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // TODO Auto-generated method stub

    WebFunction.JsonHeaderInit(resp);

    try {
      // 连接到 mongodb 服务
      MongoDatabase mongoDatabase = MongoConnect.GetConnect();
      MongoCollection<Document> collection = mongoDatabase.getCollection("User");
      FindIterable<Document> findIterable = collection.find();
      MongoCursor<Document> mongoCursor = findIterable.iterator();
      JSONArray UserList=new JSONArray();
      while (mongoCursor.hasNext()) {
        Document document = mongoCursor.next();
        String jsonStr = document.toJson();
        JSONObject json = new JSONObject(jsonStr);
        JSONObject user=new JSONObject();
        user.put("UserName", json.get("UserName"));
        user.put("ID", json.get("ID"));
        user.put("PassWord", json.get("PassWord"));
        UserList.put(user);
      }
      
      JSONObject result=new JSONObject();
      result.put("MsgID", 1);
      result.put("MsgText", "Success!");
      result.put("data", UserList);
      resp.getWriter().print(result.toString());
      resp.getWriter().flush();
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      WebFunction.WriteMsgText(resp, -1, "数据读取失败！");
      return;
    }

  }

}

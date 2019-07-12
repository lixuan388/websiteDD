package com.ecity.java.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.ecity.java.json.JSONObject;
import com.ecity.java.web.WebFunction;
import com.java.sql.MongoConnect;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@WebServlet("/Menu.json")

public class Menu extends HttpServlet {

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
      MongoCollection<Document> collection = mongoDatabase.getCollection("Menu");
      FindIterable<Document> findIterable = collection.find(Filters.eq("Type", "Menu"));
      MongoCursor<Document> mongoCursor = findIterable.iterator();
      if (mongoCursor.hasNext()) {
        Document document = mongoCursor.next();
        String jsonStr = document.toJson();
        JSONObject json = new JSONObject(jsonStr);
        json.put("MsgID", 1);
        json.put("MsgText", "Success");
        resp.getWriter().print(json.toString());
        resp.getWriter().flush();
        return;
      } else {
        WebFunction.WriteMsgText(resp, -1, "无菜单数据！");
        return;
      }
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      WebFunction.WriteMsgText(resp, -1, "数据读取失败！");
      return;
    }

  }

}

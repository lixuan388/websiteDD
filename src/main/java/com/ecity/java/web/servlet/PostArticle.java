package com.ecity.java.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.ecity.java.json.JSONObject;
import com.ecity.java.web.WebFunction;
import com.java.sql.MongoConnect;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.util.JSON;


@WebServlet("/PostArticle.json")


public class PostArticle extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = -5845329787968738408L;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // TODO Auto-generated method stub
    
    
    JSONObject Data=WebFunction.GetRequestJson(req);
    WebFunction.JsonHeaderInit(resp);

    String JsonString =Data.getJSONObject("Data").toString();
    System.out.println("JsonString:"+JsonString);


    try{
      // 连接到 mongodb 服务

      MongoDatabase mongoDatabase = MongoConnect.GetConnect();
      MongoCollection<Document> collection = mongoDatabase.getCollection("Article");

      String ID=Data.getJSONObject("Data").getString("ID");
      System.out.println("ID:"+ID);
      
      DBObject bson = (DBObject)JSON.parse(JsonString);
      collection.updateMany(Filters.eq("ID",ID), new Document("$set",bson), new UpdateOptions().upsert(true));
      
     }catch(Exception e){
       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
       WebFunction.WriteMsgText(resp, -1, "数据保存失败！");
       return;
    }
    WebFunction.WriteMsgText(resp, 1, "数据保存成功！");
    
  }

}

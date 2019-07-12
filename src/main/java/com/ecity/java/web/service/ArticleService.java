package com.ecity.java.web.service;

import java.util.Date;

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

public class ArticleService {

  public JSONObject GetArticle(String ID,Boolean CanNew) {

    try {
      // 连接到 mongodb 服务
      MongoDatabase mongoDatabase = MongoConnect.GetConnect();
      MongoCollection<Document> collection = mongoDatabase.getCollection("Article");
      FindIterable<Document> findIterable = collection.find(Filters.eq("ID", ID));
      MongoCursor<Document> mongoCursor = findIterable.iterator();

      JSONObject ResultJson = new JSONObject();
      ResultJson.put("MsgID", 1);
      ResultJson.put("MsgText", "Success");
      JSONArray Data=new JSONArray();
      if (mongoCursor.hasNext()) {
        Document document = mongoCursor.next();
        String jsonStr = document.toJson();
        JSONObject json = new JSONObject(jsonStr);
        ResultJson.put("Data", json);
      } 
      else {
        if (CanNew) {
          JSONObject json = new JSONObject();
          json.put("ID", new Date().getTime());
          json.put("Caption", "");
          json.put("Content", "请输入文本");
          json.put("Type", "");
          json.put("InsDate", new Date().getTime());
          ResultJson.put("Data", json);
        }
      }
      return ResultJson;
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      return WebFunction.WriteMsgToJson(-1, "数据读取失败！");      
    }
  }
  public JSONObject GetType(String Type) {

    try {
      // 连接到 mongodb 服务
      MongoDatabase mongoDatabase = MongoConnect.GetConnect();
      MongoCollection<Document> collection = mongoDatabase.getCollection("ArticleType");
      FindIterable<Document> findIterable = collection.find(Filters.eq("Type", Type));
      MongoCursor<Document> mongoCursor = findIterable.iterator();

      JSONObject ResultJson = new JSONObject();
      ResultJson.put("MsgID", 1);
      ResultJson.put("MsgText", "Success");
      JSONArray Data=new JSONArray();
      while (mongoCursor.hasNext()) {
        Document document = mongoCursor.next();
        String jsonStr = document.toJson();
        JSONObject json = new JSONObject(jsonStr);
        Data.put(json);
      } 
      ResultJson.put("Data", Data);
      return ResultJson;
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      
      return WebFunction.WriteMsgToJson( -1, "数据读取失败！");
    }
  }
}

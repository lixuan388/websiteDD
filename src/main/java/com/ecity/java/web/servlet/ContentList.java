package com.ecity.java.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

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
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.util.JSON;

@WebServlet("/ContentList.json")

public class ContentList extends HttpServlet {

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
      MongoCollection<Document> collection = mongoDatabase.getCollection("ArticleType");
      Document FiltersDocument=new Document();
      FiltersDocument.put("Type",new Document("$eq","1"));
      
      FindIterable<Document> findIterable = collection.find(FiltersDocument);
      MongoCursor<Document> mongoCursor = findIterable.iterator();

      JSONObject ResultJson = new JSONObject();
      ResultJson.put("MsgID", 1);
      ResultJson.put("MsgText", "Success");
      JSONArray Data=new JSONArray();
      while (mongoCursor.hasNext()) {
        Document document = mongoCursor.next();
        String jsonStr = document.toJson();
        JSONObject json = new JSONObject(jsonStr);
        json.remove("_id");
        json.put("Content", GetContent(json.getString("id")));
        Data.put(json);
      } 
      ResultJson.put("Data", Data);
      WebFunction.ResponseJson(resp, ResultJson);
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      WebFunction.WriteMsgText(resp, -1, "数据读取失败！");
      return;
    }
  }
  
  public JSONArray GetContent(String Type) {
 // 连接到 mongodb 服务
    JSONArray Data=new JSONArray();
    try {
      MongoDatabase mongoDatabase = MongoConnect.GetConnect();
      MongoCollection<Document> collection = mongoDatabase.getCollection("Article");
      

      Document FiltersDocument=new Document();
      FiltersDocument.put("Type",new Document("$eq",Type));
      FiltersDocument.put("ContentType",new Document("$eq","1"));
      
      
      FindIterable<Document> findIterable = collection.find(FiltersDocument);
      MongoCursor<Document> mongoCursor = findIterable.iterator();
  
      int i =0;
      while (mongoCursor.hasNext() && i<4) {
        Document document = mongoCursor.next();
        String jsonStr = document.toJson();
        JSONObject json = new JSONObject(jsonStr);
        json.remove("Content");
        json.remove("_id");
        Data.put(json);
        i++;
      } 
      while (Data.length()<4) {

        JSONObject json = new JSONObject();
        json.put("id","-1");
        json.put("Caption","");
        Data.put(json);
      }
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
    }
    return Data;
  }


}

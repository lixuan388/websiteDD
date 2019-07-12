package com.java.sql;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoConnect {
  static MongoDatabase mongoDatabase = null;

  public static String host = "172.10.10.18";
  public static String port = "57017";
  public static String Database = "DD";
//  try{
//  	// 连接到 mongodb 服务
//    MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
//    
//    // 连接到数据库
//    MongoDatabase mongoDatabase = mongoClient.getDatabase("xj");  
//    System.out.println("Connect to database successfully");
//
//    MongoCollection<Document> collection = mongoDatabase.getCollection("test");
//
//    System.out.println("集合 test 选择成功");
//    
//    Document document = new Document("title", "MongoDB").  
//    append("description", "database").  
//    append("likes", 100).  
//    append("by", "Fly");  
//    List<Document> documents = new ArrayList<Document>();  
//    documents.add(document);  
//    collection.insertMany(documents);  
//    System.out.println("文档插入成功");  
//        
//     
//   }catch(Exception e){
//     System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//  }

  static public MongoDatabase GetConnect() {
    if (mongoDatabase == null) {
      System.out.println("----MongoDatabase.GetConnect----");
      MongoClient mongoClient = new MongoClient(host, Integer.parseInt(port));
      // 连接到数据库
      
      mongoDatabase = mongoClient.getDatabase(Database);
      System.out.println("Connect to database successfully");
    }
    return mongoDatabase;
  }

}

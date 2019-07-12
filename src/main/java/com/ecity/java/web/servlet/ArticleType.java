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
import com.ecity.java.web.service.ArticleService;
import com.java.sql.MongoConnect;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

@WebServlet("/ArticleType.json")

public class ArticleType extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 6530571141910637086L;


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // TODO Auto-generated method stub
    WebFunction.JsonHeaderInit(resp);
    ArticleService svc=new ArticleService();
    JSONObject ResultJson = svc.GetType("1");
    WebFunction.ResponseJson(resp, ResultJson);
  }

}

package com.ecity.java.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecity.java.web.WebFunction;
import com.ecity.java.web.service.ArticleService;

@WebServlet("/GetArticle.json")

public class GetArticle extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 6530571141910637086L;


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // TODO Auto-generated method stub

    WebFunction.JsonHeaderInit(resp);
    
    Map<String, String[]> params = req.getParameterMap();
    String ID = params.get("ID") == null ? "-1" : (String) (params.get("ID")[0]);
    
    ArticleService svc=new ArticleService();
    WebFunction.ResponseJson(resp, svc.GetArticle(ID, true));
    
  }



}

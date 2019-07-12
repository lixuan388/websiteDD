package com.java;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/version")

public class version extends HttpServlet {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public static final int DB_Sql = 1;
  public static final int DB_MySql = 2;

  public static int SqlDBType = DB_Sql;
//	public static int SqlDBType=DB_MySql;

  public static String Version = "websiteDD.2019.8.21.0016";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // TODO Auto-generated method stub
    resp.setContentType("text/html;charset=utf-8");
    resp.setHeader("Cache-Control", "no-cache");
    resp.getWriter().print(Version);
    System.out.println(Version);
    resp.getWriter().flush();
  }
}

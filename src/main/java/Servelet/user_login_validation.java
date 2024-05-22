package Servelet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/user_login")
public class user_login_validation extends HttpServlet {
 
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
	  
	  try{
	        String username = request.getParameter("username");   
	        String password = request.getParameter("password");
	        Class.forName("com.mysql.cj.jdbc.Driver");  // MySQL database connection
	        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AIRRESERVE?user=root&password=root");
  	        System.out.println("Connected to DB....!");
	        PreparedStatement pst = conn.prepareStatement("Select username,password from user_details where username=? and password=?");
	        pst.setString(1, username);
	        pst.setString(2, password);
	        ResultSet rs = pst.executeQuery();                        
	        if(rs.next()){
	           String U_name=request.getParameter("username");
	           
	           HttpSession session = request.getSession();
	           session.setAttribute("username",U_name);
	           	                     
	           RequestDispatcher view = request.getRequestDispatcher("user_login_pass.jsp");
	           view.forward(request, response);
	           System.out.println("login successful.....");
	        }       
	        else {
	           System.out.println("login failed......");
	          RequestDispatcher view = request.getRequestDispatcher("user_login_failed.jsp");
	          view.forward(request, response);
	        
	   }
	  }
	   catch(Exception e){  
		   System.out.println(e);
	       response.sendRedirect("error.jsp");       
	   }      
  }
}

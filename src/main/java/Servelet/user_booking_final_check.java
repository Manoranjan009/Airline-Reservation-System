package Servelet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user_booking_final_check")
public class user_booking_final_check extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        System.out.println("-------------------------------INSIDE USER_BOOKING_FINAL_CHECK ----------------------");
        PrintWriter out = response.getWriter();
        Connection con = null;
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "AIRRESERVE";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String password = "root";
        Statement st=null;
        
        String address=request.getParameter("address");    
        String payment_type=request.getParameter("payment_type");    
        String upino=request.getParameter("upino");    

        
        HttpSession session=request.getSession(false);  
        String PNR=(String)session.getAttribute("PNR");
        System.out.println(PNR);
        String ticket_type=(String)session.getAttribute("ticket_type");
        System.out.println(ticket_type);
        try    
        {    
            Class.forName(driver);
            con = DriverManager.getConnection(url + dbName, userName, password);
            System.out.println("connected!.....");   
               
                int fair1=0;
                String fair_q = "SELECT fair FROM payment_details WHERE PNR = ? ";
                PreparedStatement pstmt_fs=con.prepareStatement(fair_q);
                pstmt_fs.setString(1,PNR);
                ResultSet ff = pstmt_fs.executeQuery();
                    
                while(ff.next()){
                    fair1 = ff.getInt(1);
                }
                
               
                
                
                String update = "UPDATE payment_details SET address = ?, payment_gateway = ?, transaction_id = ?, actual_fair= ? WHERE PNR= ?";
                    PreparedStatement pst=con.prepareStatement(update);
                    pst.setString(1,address);
                    pst.setString(2,payment_type);
                    pst.setString(3,upino);                   
                    pst.setInt(4,fair1);
                    pst.setString(5, PNR);                        
                    int y=pst.executeUpdate();
                    if(y==1)
                    {
                        System.out.println("Values Updated Successfully");
                    }
                    RequestDispatcher view = request.getRequestDispatcher("payment_success.jsp");
                    view.forward(request, response);
                    
        }     
        catch (Exception e) {
            e.printStackTrace();
            RequestDispatcher view = request.getRequestDispatcher("payment_failed.jsp");
            view.forward(request, response);
        }
        out.close();
        System.out.println("-------------------------------INSIDE USER_BOOKING_FINAL_CHECK ----------------------");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
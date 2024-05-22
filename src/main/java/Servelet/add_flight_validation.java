package Servelet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/add_flight_validation")
public class add_flight_validation extends HttpServlet {
   
	public void doPost(HttpServletRequest request , HttpServletResponse response ) throws ServletException, IOException
	{
		String flight_id=request.getParameter("flight_id");
		String flight_no=request.getParameter("flight_no");
		String flight_name=request.getParameter("flight_name");
		String from_city=request.getParameter("from_city");
		String to_city=request.getParameter("to_city");
		String date_of_flight=request.getParameter("date_of_flight");
		String flight_arrival_time=request.getParameter("flight_arrival_time");
		String flight_reach_time=request.getParameter("flight_reach_time");
		//String duration=request.getParameter("duration");
		String first_class_seat=request.getParameter("first_class_seat");
		String first_class_seat_booked="0";
		String first_class_seat_aval=request.getParameter("first_class_seat");
		String buss_class_seat=request.getParameter("buss_class_seat");
		String buss_class_seat_booked="0";
		String buss_class_seat_aval=request.getParameter("buss_class_seat");
		String eco_class_seat=request.getParameter("eco_class_seat");
		String eco_class_seat_booked="0";
		String eco_class_seat_aval=request.getParameter("eco_class_seat");
		String first_class_price=request.getParameter("first_class_price");
		String buss_class_price=request.getParameter("buss_class_price");
		String eco_class_price=request.getParameter("eco_class_price");

		    String time1 = request.getParameter("flight_arrival_time");
		    String time2 = request.getParameter("flight_reach_time");
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		        Date date1 =null;
		        Date date2 = null;
				try {
					date1 = simpleDateFormat.parse(time1);
				    date2 = simpleDateFormat.parse(time2);
				} catch (ParseException e) {
					
					e.printStackTrace();
				}
		        
		        long differenceInMilliSeconds = Math.abs(date2.getTime() - date1.getTime());
		        long differenceInHours = (differenceInMilliSeconds / (60 * 60 * 1000)) % 24;
		        long differenceInMinutes = (differenceInMilliSeconds / (60 * 1000)) % 60;
		        long differenceInSeconds = (differenceInMilliSeconds / 1000) % 60;
		      
		        String duration = differenceInHours + ":" + differenceInMinutes + ":" + differenceInSeconds;

		        HttpSession session = request.getSession();
		        session.setAttribute("flight_id",flight_id);

		try
		{
		         Class.forName("com.mysql.cj.jdbc.Driver");
		           Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AIRRESERVE", "root", "root");
		           Statement st=conn.createStatement();
		           int i=st.executeUpdate("insert into flight_details values('"+flight_id+"','"+flight_no+"','"+flight_name+"','"+from_city+"','"+to_city+"','"+date_of_flight+"','"+flight_arrival_time+"','"+flight_reach_time+"','"+duration+"',"+first_class_seat+","+first_class_seat_booked+","+first_class_seat_aval+","+buss_class_seat+","+buss_class_seat_booked+","+buss_class_seat_aval+","+eco_class_seat+","+eco_class_seat_booked+","+eco_class_seat_aval+","+first_class_price+","+buss_class_price+","+eco_class_price+")");
		           RequestDispatcher view = request.getRequestDispatcher("add_flight_suucess.jsp");
		           view.forward(request, response);
		           System.out.println("flight added succesfully............!");
		        }
		        catch(Exception e)
		        {   
		        	System.out.println("flight added failed........ !" + e);		        	
		            RequestDispatcher view = request.getRequestDispatcher("addflights_failed.jsp");
		        	view.forward(request, response);
		        }
	}
}

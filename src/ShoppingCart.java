
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// this annotation maps this Java Servlet Class to a URL
@WebServlet("/shoppingcart")
public class ShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ShoppingCart() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // set response mime type
        response.setContentType("text/html"); 

        // get the printwriter for writing response
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>Shopping Cart</title></head>");
        out.println("<style>");
        out.println("body{\r\n" + 
        		"        background-image: url(\"backgroundforSite.png\");\r\n" + 
        		"    background-attachment: fixed;"
        		+ "font-family: Arial, Helvetica, sans-serif;}"
        		+ "color: white;"
        		+ "#games{\r\n" + 
        		"    font-family: Arial, Helvetica, sans-serif;\r\n" + 
        		"    border-collapse: collapse;\r\n" + 
        		"    width: 95%;\r\n" + 
        		"    height: 85%;\r\n" + 
        		"}"
        		+ "#games td, #games th {\r\n" + 
        		"    border: 3px solid #e8c7ea;\r\n" + 
        		"    padding: 8px;\r\n" + 
        		"background-color: #fff9ff;\r\n"
        		+ "}"
        		+ "#games th {\r\n" + 
        		"    padding-top: 12px;\r\n" + 
        		"    padding-bottom: 12px;\r\n" + 
        		"    text-align: left;\r\n" + 
        		"    background-color: #fb89ff;\r\n" + 
        		"    color: white;\r\n" + 
        		"}"
        		+ " a.top:link,a.top:visited{\r\n" + 
        		"    background-color: #fb89ff;\r\n" + 
        		"    color: white;\r\n" + 
        		"    padding: 5px 5px;\r\n" + 
        		"    \r\n" + 
        		"    text-align: center;\r\n" + 
        		"    text-decoration: none;\r\n" + 
        		"    display: inline-block;\r\n" + 
        		"}\r\n" + 
        		"    a.top:hover{\r\n" + 
        		"    background-color: #d16cd4;\r\n" + 
        		"    color: white;\r\n" + 
        		"    padding: 5px 5px;\r\n" + 
        		"    text-align: center;\r\n" + 
        		"    text-decoration: none;\r\n" + 
        		"    display: inline-block;\r\n" + 
        		"}"
        		+ "a{"
        		+ "text-align: center}");
        out.println("</style>");
        out.println("<body>");
        try {   
        		Cart cart = (Cart)request.getSession().getAttribute("cart");
        		if(!(cart == null || cart.getCart().isEmpty()))
        		{
        			out.println("<h1 align='center'>Your Shopping Cart</h1>");
        			out.println("<td><a class = 'top' href ='browse.html'> Continue Shopping</a></td>");
        			out.println("<table id='games'>");
		        	out.println("<tr>");
		    		out.println("<td>Title</td>");
		    		out.println("<td>Quantity</td>");
		    		out.println("<td>Edit Cart</td>");
		    		out.println("</tr>");
		    		ArrayList<String> currCart = cart.getCart();
		    		ArrayList<String> cartSet = new ArrayList<String>(50);
		    		for (String i : currCart) {
		    			if (!cartSet.contains(i))
		    				cartSet.add(i);
		    		}
		    		//printing!!
		    		for (String i : cartSet) {
		    			
	        			out.println("<tr>");
	        			String inThis = i.replaceAll("%20"," ");
		    			out.println("<td>" + inThis + "</td>");
		    			out.println("<td>" + cart.amount(i) + "</td>");
		    			out.println("<form name='cartEdit' onsubmit = reload(); method='post' action='CartEditServlet?"+i+"'>");
		    			out.println("<td><input type='number' name='editNum' min = '0'></input></td>");
		    			out.println("<td><input type = 'submit' value = 'Submit'/></td>");
		    			out.println("</form>");
		    			out.println("</tr>");
		    		}
		    		out.println("&nbsp;&nbsp;");
		    		out.println("<a class = 'top' href ='checkout.html'> Check Out >>></a>");
		    		out.println("</table>");
		    		out.println("</body>");
        		}
        		else
        		{
        			out.println("<h1 align='center'>Your Shopping Cart is Empty!</h1>");
        			out.println("<div align='center'>");
        			out.println("<td><a class='top'href ='browse.html'> Continue Shopping</a></td>");
        			out.println("</div>");
        			out.println("</table>");
    	    		out.println("</body>");
        		}
        		
        } catch (Exception e) {
        		/*
        		 * After you deploy the WAR file through tomcat manager webpage,
        		 *   there's no console to see the print messages.
        		 * Tomcat append all the print messages to the file: tomcat_directory/logs/catalina.out
        		 * 
        		 * To view the last n lines (for example, 100 lines) of messages you can use:
        		 *   tail -100 catalina.out
        		 * This can help you debug your program after deploying it on AWS.
        		 */
        		e.printStackTrace();
        		
        		out.println("<body>");
        		out.println("<p>");
        		out.println("Exception in doGet: " + e.getMessage());
        		out.println("</p>");
        		out.print("</body>");
        }
        
        out.println("</html>");
        out.close();
        
	}


}



import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// this annotation maps this Java Servlet Class to a URL
@WebServlet("/success")
public class Success extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Success() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // change this to your own mysql username and password
        String loginUser = "userfinal";
        String loginPasswd = "mypassword";
        String loginUrl = "jdbc:mysql://localhost:3306/cs122b";
		
        // set response mime type
        response.setContentType("text/html"); 

        // get the printwriter for writing response
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>SuperGameDataLand</title></head>");
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
        
        try {
        		Class.forName("com.mysql.jdbc.Driver").newInstance();
        		// create database connection
        		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        		// declare statement
        		// Statement statement = connection.createStatement();
    			String query = "INSERT INTO sales (cid,gid,sale_date) VALUES( (SELECT c.cid FROM customers c WHERE c.first_name = ? AND c.last_name = ? AND c.email = ?) , (SELECT g.gid FROM games g WHERE g.title = ? LIMIT 1) , (SELECT CONVERT (CURRENT_TIMESTAMP(),date)) );";
    			PreparedStatement statement = connection.prepareStatement(query);
    			
    			String firstName =(String)request.getSession().getAttribute("firstname");
        		String lastName = (String)request.getSession().getAttribute("lastname");
        		String email = ((User)request.getSession().getAttribute("user")).getUsername();
        		
        	
        		out.println("<body>");
        		//out.println("<h1 align='center'>"+resultSet.getString("title")+"</h1>");
        		out.println("<h1 align='center'>Purchase Successful!</h1>");
        		out.println();
        		out.println("<div align = 'center'>");
        		out.println(firstName + " "+ lastName +", at "+email+" Thank you for purchasing:");
        		out.println("</br>");
        		Cart cart = (Cart)request.getSession().getAttribute("cart");
        		
        		ArrayList<String> currCart = cart.getCart();
	    		ArrayList<String> cartSet = new ArrayList<String>(50);
	    		for (String i : currCart) {
	    			if (!cartSet.contains(i))
	    				cartSet.add(i);
	    			String inThis = i.replaceAll("%20"," ");
	    			statement.setString(1, firstName);
	    			statement.setString(2, lastName);
	    			statement.setString(3, email);
	    			statement.setString(4, inThis);
	    			statement.executeUpdate();
	    		}
	    		out.println("<table id='games'>");
	        	out.println("<tr>");
	    		out.println("<td>Title</td>");
	    		out.println("<td>Quantity</td>");
	    		out.println("</tr>");
	    		//printing!!
	    		for (String i : cartSet) {
	    			
        			out.println("<tr>");
        			String inThis = i.replaceAll("%20"," ");
	    			out.println("<td>" + inThis + "</td>");
	    			out.println("<td>" + cart.amount(i) + "</td>");
	    			out.println("</tr>");
	    		}
        		
        		
        		//"+firstAppSet.getString("first_appearance")+"

        		out.println("</table>");
        		request.getSession().setAttribute("cart",null);
        		out.println("<a class = 'top' href='succ.html'>Continue Shopping</a>");
        		out.println("<div>");
        		out.println("</body>");
        		
        		//resultSet.close();
        		//resultSet.close();
        		statement.close();
        		connection.close();
        		
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

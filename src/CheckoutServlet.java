import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//
@WebServlet(name = "checkoutservlet", urlPatterns = "/api/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String creditcard = request.getParameter("creditcard");
        String expire_date = request.getParameter("expire_date");
        request.getSession().setAttribute("firstname", firstname);
        request.getSession().setAttribute("lastname", lastname);
    	String email = ((User)request.getSession().getAttribute("user")).getUsername();        
        
        String loginUser = "userfinal";String loginPasswd = "mypassword";String loginUrl = "jdbc:mysql://localhost:3306/cs122b";        
        try {
            JsonObject responseJsonObject = new JsonObject();
        	if (!(firstname.isEmpty() || lastname.isEmpty() || creditcard.isEmpty() || expire_date.isEmpty())) {
	            //implementing login resultset
	        	Class.forName("com.mysql.jdbc.Driver").newInstance();
	    		// create database connection
	    		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
	    		// declare statement
	    		Statement statement = connection.createStatement();
	    		// prepare query
	    		String query = "select cc.first_name,cc.last_name,cc.ccid,cc.expire_date,c.email\r\n" + 
	    				"from customers c, creditcards cc\r\n" + 
	    				"where c.first_name = cc.first_name and c.last_name = cc.last_name and c.crid = cc.ccid;";
	    		// execute query
	    		ResultSet resultSet = statement.executeQuery(query);
	    		
	    		boolean foundMatch = false;
	    		while(resultSet.next()) {
	    			if(firstname.equals(resultSet.getString(1)) && lastname.equals(resultSet.getString(2)) &&
	    				creditcard.equals(resultSet.getString(3)) && expire_date.equals(resultSet.getString(4)) && 
	    				email.equals(resultSet.getString(5))) {
	    				foundMatch = true;
	    				break;
	    			}
	    		}		        		
		        if (foundMatch) { // Credit card success:
		        	System.out.println(request.getRequestURI());		
		            responseJsonObject.addProperty("status", "success");
		            responseJsonObject.addProperty("message", "success");
		        } else { // credit card fail
		            responseJsonObject.addProperty("status", "fail");
		            responseJsonObject.addProperty("message", "incorrect information");
		        }
	        } 
        	else { // missing values
	            responseJsonObject.addProperty("status", "fail");
	            responseJsonObject.addProperty("message", "Please fill in all boxes");
	        }
            response.getWriter().write(responseJsonObject.toString());
        } catch (Exception e) {
        	System.out.println("sike! you thot");
        }
    }
}

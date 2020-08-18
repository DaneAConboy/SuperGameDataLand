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
@WebServlet(name = "AndLogin", urlPatterns = "/api/login-android")
public class AndLogin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public AndLogin() {
        super();
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	 String username = request.getParameter("name");
         String password = request.getParameter("pass");
         
         String loginUser = "userfinal";String loginPasswd = "mypassword";String loginUrl = "jdbc:mysql://localhost:3306/cs122b";        
         try {
             //implementing login resultset
         	Class.forName("com.mysql.jdbc.Driver").newInstance();
     		// create database connection
     		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
     		// declare statement
     		Statement statement = connection.createStatement();
     		// prepare query
     		String query = "select email,password from customers;";
     		// execute query
     		ResultSet resultSet = statement.executeQuery(query);
     		boolean foundMatch = false;
     		boolean foundName = false;
     		//StrongPasswordEncryptor strong = new StrongPasswordEncryptor();
     		while(resultSet.next()) {
     		//	String encryptedPassword = resultSet.getString(2);
     			if(username.equals(resultSet.getString(1)) && password.equals(resultSet.getString(2)))
     				foundMatch = true;
     			if(username.equals(resultSet.getString(1)))
     				foundName = true;    				
     		}	       
 	        		
 	        if (foundMatch) { 
 	            // Login success:
 	            // set this user into the session
 	            request.getSession().setAttribute("user", new User(username));
 	            request.getSession().setAttribute("limit","20");
 	
 	            JsonObject responseJsonObject = new JsonObject();
 	            responseJsonObject.addProperty("status", "success");
 	            responseJsonObject.addProperty("message", "success");
 	
 	            response.getWriter().write(responseJsonObject.toString());
 	        } else {
 	            // Login fail
 	            JsonObject responseJsonObject = new JsonObject();
 	            responseJsonObject.addProperty("status", "fail");
 	            if (!foundName) {
 	                responseJsonObject.addProperty("message", "user " + username + " doesn't exist");
 	            } else{
 	                responseJsonObject.addProperty("message", "incorrect password");
 	            }
 	            response.getWriter().write(responseJsonObject.toString());
 	        }
         } catch (Exception e) {
         	JsonObject responseJsonObject = new JsonObject();
             responseJsonObject.addProperty("status", "fail");
             responseJsonObject.addProperty("message", "bustedd");
             response.getWriter().write(responseJsonObject.toString());
         }
     }
}

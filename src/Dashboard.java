
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
@WebServlet("/_dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Dashboard() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // set response mime type
        response.setContentType("text/html"); 

        // get the printwriter for writing response
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head><title>_Dashboard</title></head>");
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
        		"}"+
        		"form{"+
        		    "position: absolute;"+
        		  "top: 50%;"+
        		  "left: 50%;"+
        		  "background: #fff;"+
        		  "width: 285px;"+
        		  "margin: -215px 0 0 -182px;"+
        		  "padding: 40px;"+
        		  "box-shadow: 0 0 3px rgba(0, 0, 0, 0.3);"+
        		  "}"
        		+ "a{"
        		+ "text-align: center}"
        		  +"h1 {"+
        		  "margin: 0 0 20px;"+
            	"line-height: 1;"+
            	"color: #fb89ff;"+
            	"font-size: 25px;"+
            	"font-weight: 400;"+
            	"}");
        out.println("</style>");
        out.println("<body>");
        try {   
        			out.println("<form id='search_form' method='post' action='#'>\r\n" +
        					"<h1 align='center'>Add a new Game:</h1>"+
        					"    <label><b>Title</b></label>\r\n" + 
        					"    <br>\r\n" + 
        					"    <input type='text' placeholder='Enter Title' name='title'>\r\n" + 
        					"    <br>\r\n" + 
        					"    <label><b>Year</b></label>\r\n" + 
        					"    <br>\r\n" + 
        					"    <input type='text' placeholder='Enter Year' name='year'>\r\n" + 
        					"    <br>\r\n" + 
        					"    <label><b>Developer</b></label>\r\n" + 
        					"    <br>\r\n" + 
        					"    <input type='text' placeholder='Enter Lead Designer' name='developer'>\r\n" + 
        					"    <br>\r\n" + 
        					"    <br>\r\n" + 
        					"	<h1 align='center'>Add Characters:</h1>"+
        					"    <label><b>Name</b></label>\r\n" + 
        					"    <br>\r\n" + 
        					"    <input type='text' placeholder='Choose Your Character' name='character'>\r\n" + 
        					"    <br>\r\n" + 
        					"    <label><b>First Appearance</b></label>\r\n" + 
        					"    <br>\r\n" + 
        					"    <input type='text' placeholder='1st appearance (optional)' name='firstappear'>\r\n" + 
        					"    <br>\r\n" + 
        					"    <br>\r\n" + 
        					"	<h1 align='center'>Add Platform:</h1>"+
        					"    <label><b>Platform</b></label>\r\n" + 
        					"    <br>\r\n" + 
        					"    <input type='text' placeholder='Enter Platform' name='platform'>\r\n" + 
        					"    <br>\r\n" + 
        					"    <br>\r\n" +
        					"    <button type='submit'>Submit</button> \r\n" + 
        					"    <br>\r\n" +
        					"    <a href='metadata.txt'>See metadata >>><a>"+
        					"    <div id='dashsearch_error_message'></div>\r\n" + 
        					"</form>"
        					+ "<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>\r\n" + 
        					"<!-- include our own JS file -->\r\n" + 
        					"<script src='./dashsearch.js'></script>");
		    		out.println("</body>");
		    		
        		
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

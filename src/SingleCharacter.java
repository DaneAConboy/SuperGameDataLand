

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// this annotation maps this Java Servlet Class to a URL
@WebServlet("/singlechar")
public class SingleCharacter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SingleCharacter() {
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
        		Statement statement = connection.createStatement();

        
        		
        		// add table header row
        		
        		// prepare query
        		//String query = "";
        		
        		// execute query
        		//ResultSet resultSet = statement.executeQuery(query);
        		String charSingle = request.getQueryString();
        		charSingle = charSingle.replaceAll("%20", " ");
        		String query = "select g.title,g.year,g.lead_designer,group_concat(distinct p.name) as 'platform(s)',group_concat(distinct c.name) as 'character(s)',r.rating\r\n"+
        				"from games g, platforms p, games_on_platform gop, characters c, characters_in_games cig, ratings r\r\n"+
        				"where p.pid = gop.pid and gop.gid = g.gid and c.chid = cig.chid and cig.gid = g.gid and g.gid = r.gid\r\n"+
        				"and c.name = '"+charSingle+"' group by (g.title);";
        		String firstAppQuery = "select first_appearance from characters where name ='" +charSingle+ "';";
        		ResultSet firstAppSet = statement.executeQuery(firstAppQuery);
        		String thisbutt = "";
        		while(firstAppSet.next())
        		{
            		thisbutt = firstAppSet.getString("first_appearance");
        		}
        		out.println("<body>");
        		//out.println("<h1 align='center'>"+resultSet.getString("title")+"</h1>");
        		out.println("<h1 align='center'>"+charSingle+"</h1>");
        		out.println("<div align='center'>");
        		out.println("<strong>First Appearance Year: "+thisbutt+"</strong>");
        		out.println("</br>");
        		//"+firstAppSet.getString("first_appearance")+"
        		out.println("<a class = 'top' href='shoppingcart'>Check Out</a>");
        		out.println("</br>");
        		firstAppSet.close();
        		ResultSet resultSet = statement.executeQuery(query);
        		// add a row for every star result
        		out.println("<table id='games'>");
        		out.println("<tr>");
    			out.println("<th>All Game Appearances</th>");
        		out.println("<th>Add to Cart</th>");
        		out.println("</tr>");
        		while (resultSet.next()) {
        			// get a game from result set
        			out.println("<tr>");
        			String gameName = resultSet.getString("title");
        			out.println("<td><a href ='singlegame?"+gameName+"'>" + gameName + "</a></td>");
        			out.println("<td><a href ='addtocart?"+gameName+"'target=\"_blank\">     Add to Cart</a></td>");
        			out.println("</tr>");
        		}
        		out.println("<div>");
        		out.println("</table>");
        		
        		out.println("</body>");
        		
        		//resultSet.close();
        		resultSet.close();
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

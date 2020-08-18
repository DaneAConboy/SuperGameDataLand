

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
@WebServlet("/games")
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GameServlet() {
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
        out.println("<body style='background-color:#72ffcb;'>");
        
        try {
        		Class.forName("com.mysql.jdbc.Driver").newInstance();
        		// create database connection
        		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
        		// declare statement
        		Statement statement = connection.createStatement();
        		// prepare query
        		String query = "select g.title,g.year,g.lead_designer,group_concat(distinct p.name) as 'platform(s)',group_concat(distinct c.name) as 'character(s)',r.rating\r\n" + 
        				"from games g, platforms p, games_on_platform gop, characters c, characters_in_games cig, ratings r\r\n" + 
        				"where p.pid = gop.pid and gop.gid = g.gid and c.chid = cig.chid and cig.gid = g.gid and g.gid = r.gid\r\n" + 
        				"group by (g.title)\r\n" + 
        				"order by r.rating desc\r\n" + 
        				"limit 20;";
        		// execute query
        		ResultSet resultSet = statement.executeQuery(query);

        		out.println("<body>");
        		out.println("<h1 align='center'>Game Data</h1>");
        		
        		out.println("<table border>");
        		
        		// add table header row
        		out.println("<tr>");
        		out.println("<td>Title</td>");
        		out.println("<td>Year</td>");
        		out.println("<td>Lead Development Team</td>");
        		out.println("<td>Platform(s)</td>");
        		out.println("<td>Character(s)</td>");
        		out.println("<td>Overall Rating</td>");
        		out.println("</tr>");
        		
        		// add a row for every star result
        		while (resultSet.next()) {
        			// get a game from result set
        			String gameName = resultSet.getString("title");
        			String gameYear = resultSet.getString("year");
        			String gameDev = resultSet.getString("lead_designer");
        			String gamePlat = resultSet.getString("platform(s)");
        			String gameChars = resultSet.getString("character(s)");
        			String gameRating = resultSet.getString("rating");
        			
        			out.println("<tr>");
        			out.println("<td>" + gameName + "</td>");
        			out.println("<td>" + gameYear + "</td>");
        			out.println("<td>" + gameDev + "</td>");
        			out.println("<td>" + gamePlat + "</td>");
        			out.println("<td>" + gameChars + "</td>");
        			out.println("<td>" + gameRating + "</td>");
        			out.println("</tr>");
        		}
        		
        		out.println("</table>");
        		
        		out.println("</body>");
        		
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

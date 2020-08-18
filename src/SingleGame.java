

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// this annotation maps this Java Servlet Class to a URL
@WebServlet("/singlegame")
public class SingleGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SingleGame() {
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
        		PreparedStatement statement;
        		
        		String hold = request.getQueryString();
        		String gameSingle = hold.replaceAll("%20", " ");        		
        		String query = "select g.year,g.lead_designer,group_concat(distinct p.name) as 'platform(s)',group_concat(distinct c.name) as 'character(s)',r.rating\r\n"+
        				"from games g, platforms p, games_on_platform gop, characters c, characters_in_games cig, ratings r\r\n"+
        				"where p.pid = gop.pid and gop.gid = g.gid and c.chid = cig.chid and cig.gid = g.gid and g.gid = r.gid\r\n"+
        				"and g.title = ? group by (g.title);"; 
        		statement = connection.prepareStatement(query);
        		statement.setString(1, gameSingle);
        		ResultSet resultSet = statement.executeQuery();
        		out.println("<body>");
        		out.println("<h1 align='center'>"+gameSingle+"</h1>");
        		out.println("<div align='center'>");
        		out.println("<a class = 'top' href='shoppingcart'>Check Out</a>");
        		out.println("<table id='games'>");
        		out.println("<tr>");
        		out.println("<th>Year</th>");
        		out.println("<th>Lead Development Team</th>");
        		out.println("<th>Platform(s)</th>");
        		out.println("<th>Character(s)</th>");
        		out.println("<th>Overall Rating</th>");
        		out.println("<th>Add to Cart</th>");
        		out.println("</tr>");
        		
        		// add a row for every star result
        		while (resultSet.next()) {
        			// get a game from result set
        			String gameYear = resultSet.getString("year");
        			String gameDev = resultSet.getString("lead_designer");
        			String gamePlat = resultSet.getString("platform(s)");
        			String gameChars = resultSet.getString("character(s)");
        			String gameRating = resultSet.getString("rating");
        			
        			String[] platArray = gamePlat.split(",", -1);
        			String[] charArray = gameChars.split(",", -1);
        			String outputPlat = "<td>";
        			for(int a = 0; a < platArray.length; a++)
        			{
        				String link= platArray[a].replaceAll("&","%26");
        				outputPlat += "<a href ='singleplat?plat="+link+"&offset=0'>" + platArray[a] + "</a>, ";
        			}
        			outputPlat=outputPlat.substring(0,outputPlat.length()-2)+"</td>";
        			
        			String outputChars = "<td>";
        			for(int a = 0; a < charArray.length; a++)
        			{
        				outputChars += "<a href ='singlechar?"+charArray[a]+"'>" + charArray[a] + "</a>, ";
        			}
        			outputChars=outputChars.substring(0,outputChars.length()-2)+"</td>";
        			
        			out.println("<tr>");
        			out.println("<td>" + gameYear + "</td>");
        			out.println("<td>" + gameDev + "</td>");
        			out.println(outputPlat);
        			out.println(outputChars);
        			out.println("<td>" + gameRating + "</td>");
        			out.println("<td><a href ='addtocart?"+hold+"'target=\"_blank\">Add to Cart</a></td>");
        			out.println("</tr>");
        		}
        		out.println("</div>");
        		out.println("</table>");
        		
        		out.println("</body>");
        		
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

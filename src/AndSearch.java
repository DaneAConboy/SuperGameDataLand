import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.google.gson.JsonArray;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

//
@WebServlet(name = "AndSearch", urlPatterns = "/api/search-android")
public class AndSearch extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public AndSearch() {
        super();
    }


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	response.setContentType("application/json");
         
         String loginUser = "userfinal";String loginPasswd = "mypassword";String loginUrl = "jdbc:mysql://localhost:3306/cs122b";        
         try {
             //implementing login resultset
         	Class.forName("com.mysql.jdbc.Driver").newInstance();
     		// create database connection
     		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
     		// declare statement
     		Statement statement = connection.createStatement();
     		// prepare query
     		PrintWriter out = response.getWriter();
     		String[] keywords = request.getParameter("title").split(" ");
     		
     		String query = "select g.gid,g.title,g.year,g.lead_designer,group_concat(distinct p.name) as 'platform(s)',group_concat(distinct c.name) as 'character(s)',r.rating "+
    				"from games g, platforms p, games_on_platform gop, characters c, characters_in_games cig, ratings r " +
    				"where p.pid = gop.pid and gop.gid = g.gid and c.chid = cig.chid and cig.gid = g.gid " +
    				"and MATCH (g.title) against ('";
     		query += "+" + keywords[0] + "*";
     		for(int i = 1; i < keywords.length; i++)
     			query += " +"+keywords[i]+"*";
     		query += "' in boolean mode) group by (g.title);";
     		
     		String query2 = "select g.gid,g.title,g.year,g.lead_designer,group_concat(distinct p.name) as 'platform(s)',group_concat(distinct c.name) as 'character(s)',r.rating "+
    				"from games g, platforms p, games_on_platform gop, characters c, characters_in_games cig, ratings r " +
    				"where p.pid = gop.pid and gop.gid = g.gid and c.chid = cig.chid and cig.gid = g.gid " +
    				"and g.title LIKE '" +request.getParameter("title")+"';";
     		
     		//out.println(request.getParameter("title"));
     		//out.println(query);
     		//out.println("printl2");		
     		// execute query
     		ResultSet resultSet = statement.executeQuery(query);
     		//out.println("querryd");		
     		JsonArray return_this = new JsonArray();
     		while(resultSet.next()) {
     			JsonObject game = new JsonObject();
     			game.addProperty("title", resultSet.getString("title"));
     			//out.println(resultSet.getString("title"));
     			game.addProperty("year", resultSet.getString("year"));
     			game.addProperty("dev", resultSet.getString("lead_designer"));
     			game.addProperty("plats", resultSet.getString("platform(s)"));
     			game.addProperty("chars", resultSet.getString("character(s)"));
     			return_this.add(game);
     		}
     		//out.println(return_this.toString());		
     		out.write(return_this.toString());
         } catch (Exception e) {
         	JsonObject responseJsonObject = new JsonObject();
             responseJsonObject.addProperty("status", "fail");
             responseJsonObject.addProperty("message", "bustedd");
             response.getWriter().write(responseJsonObject.toString());
         }
     }
}


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

// server endpoint URL
@WebServlet("/ACsearch")
public class ACsearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/*
	 * populate the Marvel heros and DC heros hash map.
	 * Key is hero ID. Value is hero name.
	 */
	
    String loginUser = "userfinal";
    String loginPasswd = "mypassword";
    String loginUrl = "jdbc:mysql://localhost:3306/cs122b";
    
    public ACsearch() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JsonArray jsonArray = new JsonArray();
			// setup the response json arrray
			//JsonArray jsonArray = new JsonArray();
			//jsonArray.add(generateJsonObject("1", "donkeykonggame", "game"));
			//response.getWriter().write(jsonArray.toString());
			//return;
		
			// get the query string from parameter
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
    		
        	Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
  
			String[] keywords = request.getParameter("query").split(" ");
			String query = "SELECT title,gid FROM games WHERE MATCH (title) AGAINST (? IN BOOLEAN MODE) LIMIT 7;";
			String gg = "+"+keywords[0]+"*";
			for(int i = 1; i < keywords.length; i++)
				gg += " +"+keywords[i]+"*";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1,gg);
			ResultSet resultSet = statement.executeQuery();
			
			// return the empty json array if query is null or empty
			if (query == null || query.trim().isEmpty()) {
				response.getWriter().write(jsonArray.toString());
				return;
			}	
			// search on marvel heros and DC heros and add the results to JSON Array
			// this example only does a substring match
			// TODO: in project 4, you should do full text search with MySQL to find the matches on movies and stars
			while(resultSet.next()) {
				jsonArray.add(generateJsonObject(resultSet.getString("gid"), resultSet.getString("title"),"game"));
			}
			response.getWriter().write(jsonArray.toString());
			return;
		} catch (Exception e) {
			System.out.println(e);
			response.sendError(500, e.getMessage());
		}
	}
	
	/*
	 * Generate the JSON Object from hero and category to be like this format:
	 * {
	 *   "value": "Iron Man",
	 *   "data": { "category": "marvel", "heroID": 11 }
	 * }
	 * 
	 */
	private static JsonObject generateJsonObject(String gameID, String gameName,String categoryName) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("value", gameName);
		
		JsonObject additionalDataJsonObject = new JsonObject();
		additionalDataJsonObject.addProperty("category", categoryName);
		additionalDataJsonObject.addProperty("gid", Integer.parseInt(gameID));
		
		jsonObject.add("data", additionalDataJsonObject);
		return jsonObject;
	}


}

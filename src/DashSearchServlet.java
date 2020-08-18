import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Types;

//
@WebServlet(name = "DashSearchServlet", urlPatterns = "/api/dashsearch")
public class DashSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String uhoh="";
    	try {
    	String loginUser = "userfinal";
        String loginPasswd = "mypassword";
        String loginUrl = "jdbc:mysql://localhost:3306/cs122b";
        
        String title = request.getParameter("title");
        String year = request.getParameter("year");
        String developer = request.getParameter("developer");
        String character = request.getParameter("character");
        String firstappear = request.getParameter("firstappear");
        String platform = request.getParameter("platform");
               
        
    		Class.forName("com.mysql.jdbc.Driver").newInstance();
    		Connection connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
    		PreparedStatement statement;
    		String query;
            JsonObject responseJsonObject = new JsonObject();
            
        	int entriesfilled = 2;
        	if(title.isEmpty() && year.isEmpty() && developer.isEmpty() && platform.isEmpty())
        		entriesfilled = 0;
        	else if(!(title.isEmpty() || year.isEmpty() || developer.isEmpty() || platform.isEmpty()))
        		entriesfilled = 4;
	        if (entriesfilled==4 && !character.isEmpty()) {	        	
	        	// all necessary entries filled
	        	int yearInt = Integer.parseInt(year);
	        	query = "SELECT * FROM games WHERE title=? AND year=? AND lead_designer=?;";
	        	statement = connection.prepareStatement(query);
	        	statement.setString(1,title);
	        	statement.setInt(2,yearInt);
	        	statement.setString(3,developer);
	        	ResultSet result = statement.executeQuery();	        	
	        	if (result.isBeforeFirst()) {
	        		// the game exists
		            responseJsonObject.addProperty("status", "fail");
		            responseJsonObject.addProperty("message", "Game already exists");
	        	} else {
	        		// the game does not exist, and can be added
	        		query = "CALL add_game(?,?,?,?,?,?);";
		        	statement = connection.prepareStatement(query);
		        	statement.setString(1,title);
		        	statement.setInt(2,yearInt);
		        	statement.setString(3,developer);
		        	statement.setString(4,character);
		        	if(firstappear.isEmpty())
			        	statement.setString(5,"null");
		        	else
		        		statement.setString(5,firstappear);
		        	statement.setString(6,platform);
			        uhoh = "butts4";
		        	statement.executeUpdate();
			        uhoh = "butts3";
		            responseJsonObject.addProperty("status", "successg");
		            responseJsonObject.addProperty("message", "Added movie,character,platform (if unique), and corresponding links");

			        uhoh = "butts2";	            }
	        } else if(entriesfilled==0 && !character.isEmpty()){
	            // only character info filled
	        	query = "INSERT INTO characters (name,first_appearance) VALUES (?,?);";
	        	statement = connection.prepareStatement(query);
	        	statement.setString(1,character);
	        	if(firstappear.isEmpty())
		        	statement.setNull(2,Types.VARCHAR);
	        	else
	        		statement.setString(2,firstappear);
	        	statement.executeUpdate();
	            responseJsonObject.addProperty("status", "successc");
	            responseJsonObject.addProperty("message", "Added new character");
	        } else {
	        	// info entered incorrectly
	            responseJsonObject.addProperty("status", "fail");
	            responseJsonObject.addProperty("message", "Enter only character entry or all entries");
	        }
	        uhoh = "butts1";
            response.getWriter().write(responseJsonObject.toString());
        } catch (Exception e) {
        	JsonObject responseJsonObject = new JsonObject();
            responseJsonObject.addProperty("status", "fail");
            responseJsonObject.addProperty("message", uhoh);
            response.getWriter().write(responseJsonObject.toString());
        }
    }
}

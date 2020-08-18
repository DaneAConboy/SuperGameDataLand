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
@WebServlet(name = "SearchServlet", urlPatterns = "/api/search")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title;
        String year;
        String developer;
        String character;
        title = request.getParameter("title");
        year = request.getParameter("year");
        developer = request.getParameter("developer");
        character = request.getParameter("character");
        if(year == null)
        	year ="";
        if(developer == null)
        	developer ="";
        if(character == null)
        	character ="";
        if(title == null)
        	title ="";
               
        try {
        	String query = title + year + developer + character;
	        if (!query.isEmpty()) {
	        	//there is content to search with
	        	System.out.println(request.getRequestURI());
	            request.getSession().setAttribute("search", new Search(title,year, developer,character));
	            JsonObject responseJsonObject = new JsonObject();
	            responseJsonObject.addProperty("status", "success");
	            response.getWriter().write(responseJsonObject.toString());
	        } else {
	            // Nothing typed in
	            JsonObject responseJsonObject = new JsonObject();
	            responseJsonObject.addProperty("status", "fail");
	            responseJsonObject.addProperty("message", "Please enter search input");
	            response.getWriter().write(responseJsonObject.toString());
	        }
        } catch (Exception e) {
        	System.out.println("sike! you thot");
        }
    }
}



import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileWriter;

// this annotation maps this Java Servlet Class to a URL
@WebServlet("/gamelist")
public class GameList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GameList() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	long startTimeTS = System.nanoTime();
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
        out.println(""
        		+ "body{\r\n" + 
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
        		PreparedStatement statement;
        		long startTimeTJ = 0; long endTimeTJ = 0;
        		if(request.getParameter("type") != null)
        		{
        			String listtype = request.getParameter("type");
	        		if(listtype.equals("browsetitle"))
	        		{
	        			//do completely browse title
	        			out.println("<body>");
	        			out.println("<h1 align='center'>Game List</h1>");
	        			out.println("Sort By: ");
	        			out.println("<a href='gamelist?type=browsetitle&letter=a&offset=0'>a</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=b&offset=0'>b</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=c&offset=0'>c</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=d&offset=0'>d</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=e&offset=0'>e</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=f&offset=0'>f</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=g&offset=0'>g</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=h&offset=0'>h</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=i&offset=0'>i</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=j&offset=0'>j</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=k&offset=0'>k</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=l&offset=0'>l</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=m&offset=0'>m</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=n&offset=0'>n</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=o&offset=0'>o</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=p&offset=0'>p</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=q&offset=0'>q</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=r&offset=0'>r</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=s&offset=0'>s</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=t&offset=0'>t</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=u&offset=0'>u</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=v&offset=0'>v</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=w&offset=0'>w</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=x&offset=0'>x</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=y&offset=0'>y</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=z&offset=0'>z</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=1&offset=0'>1</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=2&offset=0'>2</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=3&offset=0'>3</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=4&offset=0'>4</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=5&offset=0'>5</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=6&offset=0'>6</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=7&offset=0'>7</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=8&offset=0'>8</a>");
	        			out.println("<a href='gamelist?type=browsetitle&letter=9&offset=0'>9</a>");
	        			String letter = request.getParameter("letter");
	        			out.println("<h1>	Page "+(Integer.parseInt(request.getParameter("offset"))/ Integer.parseInt((String)request.getSession().getAttribute("limit"))+1)+"	</h1>");
	            		out.println("<a class='top' href='shoppingcart'>Check Out</a>");
		        		if(request.getParameter("sort") != null)
		        		{
		            		out.println("<form name='cartEdit' method='post' action='LimitServlet?gamelist?type=browsetitle&letter="+letter+"&offset=0&sort="+request.getParameter("sort")+"&dir="+request.getParameter("dir")+"'</a></td>");
			    			out.println("<td><input type='number' name='editNum' min = '0'></input></td>");
			    			out.println("<td><input type = 'submit' value = 'Show Entries Per Page'/></td>");
		        			out.println("<td><a class='top' href ='gamelist?type=browsetitle&letter="+letter+"&offset="+Math.max(0,((Integer.parseInt(request.getParameter("offset"))) - Integer.parseInt((String)request.getSession().getAttribute("limit"))))+"&sort="+request.getParameter("sort")+"&dir="+request.getParameter("dir")+"'>< Previous</a></td>");
		        			out.println("<td><a class='top' href ='gamelist?type=browsetitle&letter="+letter+"&offset="+(((Integer.parseInt(request.getParameter("offset"))) + Integer.parseInt((String)request.getSession().getAttribute("limit"))))+"&sort="+request.getParameter("sort")+"&dir="+request.getParameter("dir")+"'>Next ></a></td>");
			       
		        		}
		        		else
		        		{
		        			out.println("<form name='cartEdit' method='post' action='LimitServlet?gamelist?type=browsetitle&letter="+letter+"&offset=0'</a></td>");
			    			out.println("<td><input type='number' name='editNum' min = '0'></input></td>");
			    			out.println("<td><input type = 'submit' value = 'Show Entries Per Page'/></td>");
			        		out.println("<td><a class='top' href ='gamelist?type=browsetitle&letter="+letter+"&offset="+Math.max(0,(Integer.parseInt(request.getParameter("offset"))-Integer.parseInt((String)request.getSession().getAttribute("limit"))))+"'>< Previous</a></td>");		        		
			           		out.println("<td><a class='top' href ='gamelist?type=browsetitle&letter="+letter+"&offset="+(Integer.parseInt(request.getParameter("offset"))+Integer.parseInt((String)request.getSession().getAttribute("limit")))+"'>Next ></a></td>");
			         
		        		}

		           		out.println("<td><a class='top' href ='gamelist?type=browsetitle&letter="+request.getParameter("letter")+"&offset=0&sort=title&dir=up'>Title &uarr;</a></td>");
		           		out.println("<td><a class='top' href ='gamelist?type=browsetitle&letter="+request.getParameter("letter")+"&offset=0&sort=title&dir=down'>Title &darr;</a></td>");
		           		out.println("<td><a class='top' href ='gamelist?type=browsetitle&letter="+request.getParameter("letter")+"&offset=0&sort=rating&dir=up'>Rating &darr;</a></td>");
		           		out.println("<td><a class='top' href ='gamelist?type=browsetitle&letter="+request.getParameter("letter")+"&offset=0&sort=rating&dir=down'>Rating &uarr;</a></td>");
		           		
	        			
		        		String query = "select g.gid,g.title,g.year,g.lead_designer,group_concat(distinct p.name) as 'platform(s)',group_concat(distinct c.name) as 'character(s)',r.rating\r\n"+
		        				"from games g, platforms p, games_on_platform gop, characters c, characters_in_games cig, ratings r\r\n" +
		        				"where p.pid = gop.pid and gop.gid = g.gid and c.chid = cig.chid and cig.gid = g.gid and g.gid = r.gid\r\n" +
		        				"and g.title LIKE ?\r\n" +
		        				"group by (g.title)\r\n";
		        		if(request.getParameter("sort") == null)
		        			query += "limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("title") & request.getParameter("dir").equals("up"))
		        			query += "ORDER BY g.title ASC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("title") & request.getParameter("dir").equals("down"))
		        			query += "ORDER BY g.title DESC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("rating") & request.getParameter("dir").equals("up"))
		        			query += "ORDER BY r.rating ASC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("rating") & request.getParameter("dir").equals("down"))
		        			query += "ORDER BY r.rating DESC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		statement = connection.prepareStatement(query);
		        		statement.setString(1, letter+"%");
		        		startTimeTJ = System.nanoTime();
		        		ResultSet resultSet = statement.executeQuery();
		        		endTimeTJ = System.nanoTime();
	        			out.println("<table id='games'>");
		        		out.println("<tr>");
		        		out.println("<th>Title</th>");
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
		        			String gameName = resultSet.getString("title");
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
		        			out.println("<td><a href ='singlegame?"+gameName+"'>" + gameName + "</a></td>");
		        			out.println("<td>" + gameYear + "</td>");
		        			out.println("<td>" + gameDev + "</td>");
		        			out.println(outputPlat);
		        			out.println(outputChars);
		        			out.println("<td>" + gameRating + "</td>");
		        			out.println("<td><a href ='addtocart?"+gameName+"'target=\"_blank\">Add to Cart</a></td>");
		        			out.println("</tr>");
		        		}
		        		
		        		out.println("</table>");
		        		out.println("</body>");
		        		
		        		resultSet.close();
	        		}
	        		else if(listtype.substring(0,12).equals("browseconsole"))
	        		{
	        			out.println("<body>");
	        			String console = request.getQueryString().substring(12);
	        			//console = console.replaceAll("%20", " ");
	        			out.println("<h1 align='center'>Game List: " +console+"</h1>");
		        		out.println("</body>");
	        			//do completely browse title
	        		}
        		}
        		else
        		{	
	        		out.println("<body>");
	        		out.println("<h1 align='center'>Game List</h1>");
	        		out.println("<h1> Page "+(Integer.parseInt(request.getParameter("offset"))/ Integer.parseInt((String)request.getSession().getAttribute("limit"))+1)+"	</h1>");
	        		out.println("<a class ='top' href='shoppingcart'>Check Out</a>");
	        		if(request.getParameter("sort") != null)
	        		{
		           		out.println("<form name='cartEdit' method='post' action='LimitServlet?gamelist?offset=0&sort="+request.getParameter("sort")+"&dir="+request.getParameter("dir")+"'</a></td>");
		    			out.println("<td><input type='number' name='editNum' min = '0'></input></td>");
		    			out.println("<td><input type = 'submit' value = 'Show Entries Per Page'/></td>");
	        			out.println("<td><a class='top' href ='gamelist?offset="+Math.max(0,((Integer.parseInt(request.getParameter("offset"))) - Integer.parseInt((String)request.getSession().getAttribute("limit"))))+"&sort="+request.getParameter("sort")+"&dir="+request.getParameter("dir")+"'>< Previous</a></td>");
	        			out.println("<td><a class='top' href ='gamelist?offset="+(((Integer.parseInt(request.getParameter("offset"))) + Integer.parseInt((String)request.getSession().getAttribute("limit"))))+"&sort="+request.getParameter("sort")+"&dir="+request.getParameter("dir")+"'>Next ></a></td>");
	        		}
	        		else
	        		{
		           		out.println("<form name='cartEdit' method='post' action='LimitServlet?gamelist?offset=0'</a></td>");
		    			out.println("<td><input type='number' name='editNum' min = '0'></input></td>");
		    			out.println("<td><input type = 'submit' value = 'Show Entries Per Page'/></td>");
		        		out.println("<td><a class='top' href ='gamelist?offset="+Math.max(0,(Integer.parseInt(request.getParameter("offset")) - Integer.parseInt((String)request.getSession().getAttribute("limit"))))+"'>" + "< Previous</a></td>");
		        		out.println("<td><a class='top' href ='gamelist?offset="+(Integer.parseInt(request.getParameter("offset")) + Integer.parseInt((String)request.getSession().getAttribute("limit")))+"'>" + "Next ></a></td>");
	        		}
	        		
	        		out.println("<td><a class='top' href ='gamelist?offset=0&sort=title&dir=up'>Title &uarr;</a></td>");
	           		out.println("<td><a class='top' href ='gamelist?offset=0&sort=title&dir=down'>Title &darr;</a></td>");
	           		out.println("<td><a class='top' href ='gamelist?offset=0&sort=rating&dir=up'>Rating &darr;</a></td>");
	           		out.println("<td><a class='top' href ='gamelist?offset=0&sort=rating&dir=down'>Rating &uarr;</a></td>");
	        		out.println("<table border>");
	        		
	        		// add table header row
	        		Search search = (Search)request.getSession().getAttribute("search");
	        		String title = search.getTitle();
	        		String year = search.getYear();
	        		String dev = search.getDev();
	        		String character = search.getCharacter();
	        		String query = "select g.gid,g.title,g.year,g.lead_designer,group_concat(distinct p.name) as 'platform(s)',group_concat(distinct c.name) as 'character(s)',r.rating "+
	        				"from games g, platforms p, games_on_platform gop, characters c, characters_in_games cig, ratings r "+
	        				"where p.pid = gop.pid and gop.gid = g.gid and c.chid = cig.chid and cig.gid = g.gid and g.gid = r.gid and ";
	        		String gg;
	        		if(year.length() == 4)
	        		{
		        		query += "MATCH (g.title) AGAINST (? IN BOOLEAN MODE) and g.year = ? and g.lead_designer LIKE ? and c.name LIKE ? group by (g.title) ";
		        		if(request.getParameter("sort") == null)
		        			query += "limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("title") & request.getParameter("dir").equals("up"))
		        			query += "ORDER BY g.title ASC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("title") & request.getParameter("dir").equals("down"))
		        			query += "ORDER BY g.title DESC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("rating") & request.getParameter("dir").equals("up"))
		        			query += "ORDER BY r.rating ASC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("rating") & request.getParameter("dir").equals("down"))
		        			query += "ORDER BY r.rating DESC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		statement = connection.prepareStatement(query);
		        		String[] keywords = title.split(" ");
		        		gg = "+"+keywords[0]+"*";
		        		for(int i = 1; i < keywords.length; i++)
		        			gg += " +"+keywords[i]+"*";
	        			statement.setString(1, gg);
	        			statement.setString(2, year);
	        			statement.setString(3, "%"+dev+"%");
	        			statement.setString(4, "%"+character+"%");
	        		}
	        		else if(year.length() == 0)
	        		{
		        		query += "MATCH (g.title) AGAINST (? IN BOOLEAN MODE) and g.lead_designer LIKE ? and c.name LIKE ? group by (g.title) ";
		        		if(request.getParameter("sort") == null)
		        			query += "limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("title") & request.getParameter("dir").equals("up"))
		        			query += "ORDER BY g.title ASC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("title") & request.getParameter("dir").equals("down"))
		        			query += "ORDER BY g.title DESC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("rating") & request.getParameter("dir").equals("up"))
		        			query += "ORDER BY r.rating ASC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("rating") & request.getParameter("dir").equals("down"))
		        			query += "ORDER BY r.rating DESC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
	        			statement = connection.prepareStatement(query);
	        			String[] keywords = title.split(" ");
	        			gg = "+"+keywords[0]+"*";
	        			for(int i = 1; i < keywords.length; i++)
	        				gg += " +"+keywords[i]+"*";
	        			statement.setString(1, gg);
	        			statement.setString(2, "%"+dev+"%");
	        			statement.setString(3, "%"+character+"%");
	        		}
	        		else
	        		{
	        			query += "g.year = ? group by (g.title) ";
	        			if(request.getParameter("sort") == null)
		        			query += "limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("title") & request.getParameter("dir").equals("up"))
		        			query += "ORDER BY g.title ASC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("title") & request.getParameter("dir").equals("down"))
		        			query += "ORDER BY g.title DESC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("rating") & request.getParameter("dir").equals("up"))
		        			query += "ORDER BY r.rating ASC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
		        		else if(request.getParameter("sort").equals("rating") & request.getParameter("dir").equals("down"))
		        			query += "ORDER BY r.rating DESC limit "+Integer.parseInt((String)request.getSession().getAttribute("limit"))+" OFFSET " + request.getParameter("offset")+";";
	        			statement = connection.prepareStatement(query);
	        			statement.setString(1, year);
	        		}
	        		out.println("<script>function added() {alert('Item added to cart!');}</script>");
	        		startTimeTJ = System.nanoTime();
	        		ResultSet resultSet = statement.executeQuery();
	        		endTimeTJ = System.nanoTime();
	        		out.println("<table id='games'>");
	        		out.println("<tr>");
	        		out.println("<th>Title</th>");
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
	        			String gameName = resultSet.getString("title");
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
	        			out.println("<td><a href ='singlegame?"+gameName+"'>" + gameName + "</a></td>");
	        			out.println("<td>" + gameYear + "</td>");
	        			out.println("<td>" + gameDev + "</td>");
	        			out.println(outputPlat);
	        			out.println(outputChars);
	        			out.println("<td>" + gameRating + "</td>");
	        			//out.println("<td><input id='Add to Cart' type='button' value='Add to Cart' onclick='addToCart(request,"+gameName+");added();' /></td>");
	        			out.println("<td><a href ='addtocart?"+gameName+"'target=\"_blank\">Add to Cart</a></td>");
	        			//'addToCart(request,"+gameName+");added();'
	        			out.println("</tr>");
	        		}
	        		
	        		out.println("</table>");
	        		out.println("</body>");
	        		
	        		resultSet.close();
        		}
        		//statement.close();
        		connection.close();
        		long endTimeTS = System.nanoTime();
        		long bing = endTimeTJ - startTimeTJ;
        		long bong = endTimeTS - startTimeTS;
        		BufferedWriter aa = new BufferedWriter(new FileWriter(new File("logTJ.txt")));
        		BufferedWriter ab = new BufferedWriter(new FileWriter(new File("logTS.txt")));
        		aa.write(bing + "\n"); aa.close();
        		ab.write(bong + "\n"); ab.close();
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
        		Search search = (Search)request.getSession().getAttribute("search");
        		out.println(search.getTitle()+" here");
        		out.println(search.getYear()+" here");
        		out.println(search.getDev()+" here");
        		out.println(search.getCharacter()+" here");
        		out.println("</p>");
        		out.print("</body>");
        }
        
        out.println("</html>");
        out.close();
        
	}


}

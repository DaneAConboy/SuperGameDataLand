
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLFastParse extends DefaultHandler 
{
	private String tempVal;
	private String shovetempVal;
	private StringBuffer tempStr;
	private String addTo;
	private String currParse;
	private String gidHold;
	private Connection connection;
	private Statement statement;
	private StringBuffer wholeThing;
	private int allAdded;
	private int total;
	public XMLFastParse()
	{
		
    	try{
	    	String loginUser = "userfinal";
	    	String loginPasswd = "mypassword";
	    	String loginUrl = "jdbc:mysql://localhost:3306/cs122b";  //HAVE TO CHANGE IP ADDR
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			statement = connection.createStatement();
    	}catch(Exception e)
    	{
    		System.out.println(e);
    	}
    	total = 0;
    	allAdded = 0;
		tempStr = new StringBuffer();
		wholeThing = new StringBuffer();
		addTo = "";
		currParse = "";
	}
	public void parseAndAdd()
	{
		try 
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
	        SAXParser sp = spf.newSAXParser();
	        currParse = "chars";
	        sp.parse("CharactersXML.xml",this);
	        currParse = "games";
	        sp.parse("GameXML.xml",this);
	        currParse = "charsInGames";
	        sp.parse("CharsInGamesXML.xml",this);
	        //System.out.println(wholeThing.toString());
	        for(int a = 10000; a <= 19004; a++)
	        {
	        	String query = "insert ignore into ratings (gid,rating,number_ratings) values ("+a+",5,35);";
	        	statement.executeUpdate(query);
	        	query = "insert ignore into characters_in_games (chid,gid) values (1,"+a+");";
	        	statement.executeUpdate(query);
	        }
	        System.out.println(""+allAdded+" total items added / connections made.");
	        //String query = wholeThing.toString();
			//statement.executeUpdate(query);
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
	 public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	        if (qName.equalsIgnoreCase("character") && currParse.equals("chars")) {
	        	addTo = "character";
	        	tempStr = new StringBuffer("INSERT IGNORE INTO characters (chid,name,first_appearance) VALUES (");
	        	total++;
	        }
	        if (qName.equalsIgnoreCase("game") && currParse.equals("charsInGames")) {
	        	addTo = "charsInGames";
	        	tempStr = new StringBuffer("INSERT IGNORE INTO characters_in_games (chid,gid) VALUES (");
	        	total++;
	        }
	        if (qName.equalsIgnoreCase("platform")) {
	        	addTo = "gameOnPlat";
	        	tempStr = new StringBuffer("INSERT IGNORE INTO games (gid,title,year,lead_designer) VALUES (");
	        	total++;
	        }
	    }
	 
	    public void characters(char[] ch, int start, int length) throws SAXException {
	        tempVal = new String(ch, start, length);
	    }
	 
	    public void endElement(String uri, String localName, String qName) throws SAXException {
	    	try {
	        if (qName.equalsIgnoreCase("character") && currParse.equals("chars")) {
	        	tempStr.append(");");
	            //System.out.println(tempStr.toString());
	            //wholeThing.append(tempStr.toString()+"\n");
	            String query = tempStr.toString()+"\n"; 
	        	statement.executeUpdate(query);
	            allAdded++;
		        //tempStr = new StringBuffer();

	        }else if (qName.equalsIgnoreCase("chid") && addTo.equals("character")) {
	            tempStr.append(tempVal+ ",");
	        }else if (qName.equalsIgnoreCase("name") && addTo.equals("character")) {
	        	tempStr.append("'"+tempVal+ "',");
	        }else if (qName.equalsIgnoreCase("firstap") && addTo.equals("character")) {
	        	tempStr.append("'"+tempVal+ "'");
	        }else if (qName.equalsIgnoreCase("character") && currParse.equals("charsInGames")) {
	        	tempStr.append(");");
	        	//System.out.println(tempStr.toString());
	        	//wholeThing.append(tempStr.toString()+"\n");
	        	String query = tempStr.toString()+"\n"; 
	        	statement.executeUpdate(query);
	        	allAdded++;
	        	tempStr = new StringBuffer("INSERT IGNORE INTO characters_in_games (chid,gid) VALUES (");
	        }else if (qName.equalsIgnoreCase("gid") && addTo.equals("charsInGames")) {
	        	shovetempVal = tempVal;
	        }else if (qName.equalsIgnoreCase("cid") && addTo.equals("charsInGames")) {
	            tempStr.append(tempVal+ ","+shovetempVal);
	        }else if(qName.equalsIgnoreCase("pid")) {
	        	shovetempVal = tempVal;
	        }
	        else if (qName.equalsIgnoreCase("gid") && addTo.equals("gameOnPlat")) {
	            tempStr.append(tempVal+ ",");
	            gidHold = tempVal;
	        }else if (qName.equalsIgnoreCase("title") && addTo.equals("gameOnPlat")) {
	        	tempStr.append("'"+tempVal+ "',");
	        }else if (qName.equalsIgnoreCase("year") && addTo.equals("gameOnPlat")) {
	        	tempStr.append(tempVal+",");
	        }else if (qName.equalsIgnoreCase("lead") && addTo.equals("gameOnPlat")) {
	        	tempStr.append("'"+tempVal+"'");
	        }else if (qName.equalsIgnoreCase("game") && addTo.equals("gameOnPlat")) {
	        	tempStr.append(");");
	        	//System.out.println(tempStr.toString());
	        	//wholeThing.append(tempStr.toString()+"\n");
	        	String query = tempStr.toString()+"\n"; 
	        	System.out.println(tempStr);
	        	try {
	        	statement.executeUpdate(query);}catch(Exception  e) {tempStr = new StringBuffer("INSERT IGNORE INTO games (gid,title,year,lead_designer) VALUES (");}
	        	tempStr = new StringBuffer("INSERT IGNORE INTO games (gid,title,year,lead_designer) VALUES (");
	        	allAdded++;
	        	//System.out.println("INSERT INTO games_on_platform (gid,pid) VALUES("+shovetempVal+","+gidHold+");");
	        	//wholeThing.append("INSERT IGNORE INTO games_on_platform (pid,gid) VALUES("+shovetempVal+","+gidHold+");"+"\n");
	        	query = "INSERT IGNORE INTO games_on_platform (pid,gid) VALUES("+shovetempVal+","+gidHold+");"+"\n"; 
	        	statement.executeUpdate(query);
	        	allAdded++;
	        }
	    	}catch(Exception e)
	    	{
	    		System.out.println(e);
	    	}
	        

	    }
    public static void main(String[] args) 
    {
    	XMLFastParse Ch = new XMLFastParse();
    	Ch.parseAndAdd();
    }
}

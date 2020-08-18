import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//
@WebServlet("/LimitServlet")
public class LimitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String getNum = request.getParameter("editNum");
        if(getNum != null && !getNum.equals(""))
        	request.getSession().setAttribute("limit",getNum);
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();
    	out.println("<html>");
    	out.println("<script>");
    	out.println("location.replace('/122bFullProject/"+request.getQueryString()+"');");
    	out.println("</script>");
        out.println("</html>");
        out.close();
    
    }
}

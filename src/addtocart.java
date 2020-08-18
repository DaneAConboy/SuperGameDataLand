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
@WebServlet("/addtocart")
public class addtocart extends HttpServlet {
    private static final long serialVersionUID = 1L;


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {  
        // set response mime type
        response.setContentType("text/html"); 

        // get the printwriter for writing response
        PrintWriter out = response.getWriter();
    	if (request.getSession().getAttribute("cart") == null)
    		request.getSession().setAttribute("cart", new Cart());
    	//newCart = request.getSession().getAttribute("cart").add;
    	((Cart)request.getSession().getAttribute("cart")).addToCart(request.getQueryString());
    	out.println("<html>");
    	out.println("<script>");
    	out.println("self.close();");
    	out.println("</script>");
        out.println("</html>");
        out.close();
    }
}

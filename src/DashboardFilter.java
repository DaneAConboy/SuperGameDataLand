import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(filterName = "DashboardFilter", urlPatterns = "/*")
public class DashboardFilter implements Filter {
	
    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        System.out.println("LoginFilter: " + httpRequest.getRequestURI());

        // Redirect to login page if the "user" attribute doesn't exist in session
        if(httpRequest.getRequestURI().endsWith("_dashboard") && httpRequest.getSession().getAttribute("employee") != null)
			httpResponse.sendRedirect("_dashboard.html");
        else if ((httpRequest.getRequestURI().endsWith("_dashboard.html") || httpRequest.getRequestURI().endsWith("_dashboard")) && httpRequest.getSession().getAttribute("employee") == null) {
            httpResponse.sendRedirect("employeelogin.html");
        } else {
            chain.doFilter(request, response);
        }
    }

    // Setup your own rules here to allow accessing some resources without logging in
    // Always allow your own login related requests(html, js, servlet, etc..)
    // You might also want to allow some CSS files, etc..
    /**
     * We need to have these function because this class implements Filter.
     * But we don’t need to put any code in them.
     *
     * @see Filter#init(FilterConfig)
     */

    public void init(FilterConfig fConfig) {
    }

    public void destroy() {
    }


}

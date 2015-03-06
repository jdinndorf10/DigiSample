package javaStuff;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String greeting;

	public HelloServlet() {
		
	}
	
	public HelloServlet(String greeting) {
		this.greeting = greeting;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().println("<h1>"+greeting+" from doGet</h1>");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			DBStartup.insertIntoDEMOTABLE(request.getParameter("message"));
			response.sendRedirect("/");
			
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	


}

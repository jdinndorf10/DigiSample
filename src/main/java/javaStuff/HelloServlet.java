package javaStuff;

import javax.servlet.RequestDispatcher;
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
			//request passed to index.jsp server side
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response); 
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			DBStartup.insertIntoDEMOTABLE(request.getParameter("message"));
			//browser sends another request
			//response.sendRedirect("/");
			
			//request passed on server side to jsp
			RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response); 
			return;
			
		}catch (Exception e) {
 			e.printStackTrace();
			return;
		}		
	}
	
	


}

package javaStuff;

import org.apache.jasper.servlet.JspServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class ServerStartup {
	
	public ServerStartup() {
		try {
			Server server = new Server(8080);
			//WebAppContext is a Handler that uses standard layout and web.xml to configure the servlets
			WebAppContext webapp = new WebAppContext();
			//Servlets are constrained Handlers
			
			webapp.setResourceBase("./src/main/java/webResources");
			webapp.setWelcomeFiles(new String[]{"index.jsp"});
			webapp.setContextPath("/");
			webapp.addServlet(new ServletHolder(new HelloServlet()), "/Hello/*");

			//to support jsp
			ServletHolder holderJsp = new ServletHolder("jsp", JspServlet.class);
			webapp.addServlet(holderJsp, "*.jsp");
			server.setHandler(webapp);
			
			server.start();
			System.out.print("Server Started on: "+server.getURI());
			

			
		} catch (Exception e) {
			// Server Failed to start
			System.out.println("\n\nERROR: Server failed to start!!\n\n");
			e.printStackTrace();
		}
	}

}

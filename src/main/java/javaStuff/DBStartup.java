package javaStuff;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.h2.tools.Server;

import snaq.db.DBPoolDataSource;

public class DBStartup {
	private static final DBPoolDataSource embeddedDatabase = initializeDataSource();

	public DBStartup() {
		try {
			//--starting DB--
			Connection connection = embeddedDatabase.getConnection();
			Statement statement = connection.createStatement();

			//--creating front interface--
			Server server = Server.createTcpServer().start();
			Server webServer = Server.createWebServer().start();
			System.out.println("Server started and connection is open");
			System.out.println("URL: jdbc:h2:" + server.getURL() + "/mem:test");
			System.out.println("URL: webServer" + webServer.getURL());

			//--creating DB data--
			DatabaseMetaData dbmd = connection.getMetaData();
			String[] tableTypes = { "TABLE" };
			ResultSet resultSet = dbmd.getTables(null, null, null, tableTypes);

			//checks if db already created
			//if (!resultSet.next() && !("DEMOTABLE" == resultSet.getString("TABLE_NAME"))) {
			if (!resultSet.next()) {
				statement.execute("CREATE TABLE DEMOTABLE (ROW_ID INTEGER UNSIGNED NOT NULL AUTO_INCREMENT, "
						+ "MESSAGE VARCHAR(500), PRIMARY KEY (ROW_ID))");

			}else {
				System.out.print("\n\n\n Table "+resultSet.getString("TABLE_NAME")+" created \n\n\n");
				//inserting data
				insertIntoDEMOTABLE("Init testing data");
				insertIntoDEMOTABLE("Init testing data2");
				ResultSet tableResultSet = getDEMOTABLE();
				while (tableResultSet.next()) {
					System.out.println(tableResultSet.getRow()+": "+tableResultSet.getString("MESSAGE"));
				}
			}

		} catch (Exception e) {
			System.out.println("\n\n\n Database Failed to Create \n\n\n");
			e.printStackTrace();
			System.exit(0);
		}
	}

	//used to initialize DB source
	private static DBPoolDataSource initializeDataSource() {
		DBPoolDataSource proxyDatabase = new DBPoolDataSource();
		proxyDatabase.setName("embeddedDatabase");
		proxyDatabase.setDescription("Embedded Database for testing JSP");
		proxyDatabase.setDriverClassName(org.h2.Driver.class.getCanonicalName());
		proxyDatabase.setUrl("jdbc:h2:file:./H2Database/EmbeddedDB");
		proxyDatabase.setUser("");
		proxyDatabase.setPassword("");

		return proxyDatabase;
	}

	public static DBPoolDataSource getProxydatabase() {
		return embeddedDatabase;
	}

	public static void insertIntoDEMOTABLE(String message) {
		try {
			Connection connection = getProxydatabase().getConnection();
			Statement statement = connection.createStatement();
			statement.execute("INSERT INTO DEMOTABLE (MESSAGE) VALUES (\'"+message+"\')");


		}catch(Exception e) {
			System.out.println("\n\n\n Database INSERT failed \n\n\n");
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static ResultSet getDEMOTABLE() {
		ResultSet selectionSet = null;

		try {
			Connection connection = getProxydatabase().getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM DEMOTABLE");
			selectionSet = statement.executeQuery();

		} catch(Exception e) {
			System.out.println("\n\n\n Getting Table Failed \n\n\n");
		}

		return selectionSet;
	}
}

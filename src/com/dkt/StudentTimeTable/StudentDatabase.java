package com.dkt.StudentTimeTable;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class StudentDatabase implements java.io.Closeable{


		protected String dbUser = "root";
		protected String dbPassword = "Abcd@1234";
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://localhost/studentdb";
		java.sql.Connection conn = null;

		public StudentDatabase() {

		}
		/* Database open code is moved out of constructor because
		 * user may require to open and close it regularly
		 * also it is derived from closeable, which will help to ensure
		 * db connections are closed by calling of it's close method
		 */
		public void open() {
			try {
				Class.forName(JDBC_DRIVER);

				// STEP 3: Open a connection
				System.out.println("Connecting to database...");

				conn = DriverManager.getConnection(DB_URL, dbUser, dbPassword);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
		}
		
		/* Helps to write those objects which are of type DBInterface */
		public boolean writeObject(DBInterface obj) throws ProvisionException {
			boolean ret_val = obj.saveObjectToDatabase(conn);
			return ret_val;
		}

		/* Reads the object from the database, implementation of reading
		 * and filling database lies inside the object itself
		 */
		public boolean readObject(DBInterface obj) throws ProvisionException {
			
			return obj.getObjectFromDatabase(conn);
		}

		/* Ensures that the database connections and other house cleaning job
		 * is done
		 * 
		 * @see java.io.Closeable#close()
		 */
		@Override
		public void close() throws IOException {
			// TODO Auto-generated method stub
			try {
				if(conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public boolean readObjectList(DBQueryListInterface obj) {
			// TODO Auto-generated method stub
			return obj.getObjectListFromDatabase(conn);
			
		}
		public void setDbUser(String un) {
			// TODO Auto-generated method stub
			this.dbUser = un;
		}
		public void setDbPassword(String pwd) {
			// TODO Auto-generated method stub
			this.dbPassword = pwd;
		}

}

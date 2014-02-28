package com.dkt.StudentTimeTable;

public interface DBInterface {
	boolean getObjectFromDatabase( java.sql.Connection conn);
	boolean saveObjectToDatabase( java.sql.Connection conn);
	boolean deleteObjectToDatabase( java.sql.Connection conn);


}

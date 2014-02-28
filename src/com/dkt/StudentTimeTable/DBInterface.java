package com.dkt.StudentTimeTable;

public interface DBInterface {
	boolean getObjectFromDatabase( java.sql.Connection conn) throws ProvisionException;
	boolean saveObjectToDatabase( java.sql.Connection conn) throws ProvisionException;
	boolean deleteObjectToDatabase( java.sql.Connection conn) throws ProvisionException;


}

package com.dkt.StudentTimeTable;

public class ProvisionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2951102978591755430L;
	protected int code;
	protected String message;
	public ProvisionException(int c, String msg) {
		code = c; message = msg;
	}
	public int getErrorCode() {
		return code;
	}
	public String getErrorMessage() {
		return "Error: "+code+": "+message;
	}
}

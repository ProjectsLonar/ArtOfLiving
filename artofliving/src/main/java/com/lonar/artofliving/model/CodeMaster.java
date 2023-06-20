package com.lonar.artofliving.model;

public interface CodeMaster {

	
	public static final int DATA_ALREADY_EXISTS = 0;
	public static final int INSERT_SUCCESSFULLY = 1; // addsucessfully
	public static final int INSERT_FAIL = 2;
	public static final int UPDATE_SUCCESSFULLY = 3; // updatesucessfully
	public static final int UPDATE_FAIL = 4;
	public static final int DELETE_SUCCESSFULLY = 5; // noresult
	public static final int DELETE_FAIL = 6; // invalidid
	public static final int ENTITY_CANNOT_DELETE = 7; // cannotdelete
	public static final int INTERNAL_SERVER_ERROR = 9; // internalservererror
	public static final int LINK_EXPIRED = 8; // login.linkexpired
	public static final int INPUT_IS_EMPTY = 10; // inputempty
	public static final int ENTITY_NOT_FOUND = 11; // cannotdelete
	public static final int FILE_UPLOADED_SUCESSFULLY = 12; 
	public static final int FILE_UPLOAD_FAIL = 13; 
	public static final int NO_DIRECTIVE_EXISTS = 14; 
	public static final int SUBMIT_FOR_APPROVAL = 15; 
	public static final int RECORD_FOUND = 16;
	public static final int RECORD_NOT_FOUND = 17;
	public static final int FILE_DOWNLOAD = 18;
	

	public static final int EXCEPTION = 500; // Exception
	public static final int SUCCESS = 501; // Exception
	public static final int FAIL = 502;
	
	
	
	public static final String ACTIVE="ACTIVE";
	public static final String INACTIVE="INACTIVE";
	public static final String INPROCESS="INPROCESS";
}

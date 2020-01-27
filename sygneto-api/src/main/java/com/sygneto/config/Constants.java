package com.sygneto.config;

/**
 * Application constants.
 */
public final class Constants {

	// Regex for acceptable logins
	public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

	public static final String SYSTEM_ACCOUNT = "system";
	public static final String DEFAULT_LANGUAGE = "en";
	public static final String ANONYMOUS_USER = "anonymoususer";

	public static final String SERVER_URL = "http://192.168.168.116:80/";
	//public static final String SERVER_URL = "http://192.168.168.109:80/";
	//public static final String SERVER_URL = "http://35.154.124.32:80/";
	public static final String PASSWORD = "user@1234";

	public static final String BASE_URL_RSET_PASSWORD = "http://192.168.168.116/sygneto";
	//public static final String BASE_URL_RSET_PASSWORD = "http://192.168.168.109/sygneto";
	//public static final String BASE_URL_RSET_PASSWORD = "https://35.154.124.32/userflow";
	// cofiguration for 192.168.168.82

	/*public static final String ORIGINAL_FILE_PATH = "/home/manoj/sygneto/originalImages";
	public static final String EXTRACTED_ZIP_FILE_PATH = "/home/manoj/sygneto/extractedImages";*/
	
	
	/*// cofiguration for 192.168.168.109
    public static final String ORIGINAL_FILE_PATH = "/home/itarium/sygneto/originalImages";
	public static final String EXTRACTED_ZIP_FILE_PATH = "/home/itarium/sygneto/extractedImages";*/
	
	// cofiguration for 192.168.168.116
    public static final String ORIGINAL_FILE_PATH = "/home/itarium/sygneto/originalImages";
	public static final String EXTRACTED_ZIP_FILE_PATH = "/home/itarium/sygneto/extractedImages";

	//cofiguration for 35.154.124.32
/*	public static final String ORIGINAL_FILE_PATH = "/home/ubuntu/sygneto/originalImages";
	public static final String EXTRACTED_ZIP_FILE_PATH = "/home/ubuntu/sygneto/extractedImages";*/
	
	// cofiguration for local
	//public static final String  ORIGINAL_FILE_PATH="D:\\files";
	
/*	"/home/itarium/sygneto/originalImages" />
    <Context path="/sygneto/view" docBase="/home/itarium/sygneto/extractedImages" />*/
	public static final String VIEW = "/sygneto/view/";
	public static final String DATA = "/sygneto/data/";

}

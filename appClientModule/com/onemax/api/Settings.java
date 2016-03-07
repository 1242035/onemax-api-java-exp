package com.onemax.api;

public class Settings 
{
	 public static final String BASE_URL = "http://api-sandbox.onemax.com.vn:1310";
	
	 public static final String TOKEN_SERVER_URL = BASE_URL +"/oauth/access_token";

	 public static final String AUTHORIZATION_SERVER_URL = BASE_URL + "/oauth/authorize";
	 /** Value of the "API Key". */
	 public static final String API_KEY = "d5d48ae18a408d1e2bf124327e1c83aa";

	  /** Value of the "API Secret". */
	  public static final String API_SECRET = "c22abbc5ce613477237bebb1f433fd96";
	
	  /** OAuth 2 scope. */
	  public static final String SCOPE = "";
	  /** Port in the "Callback URL". */
	  public static final int PORT = 1310;
	
	  /** Domain name in the "Callback URL". */
	  public static final String DOMAIN = "127.0.0.1";
	  
	  public static void errorIfNotSpecified() throws Exception 
	  {
		   if (API_KEY.isEmpty() || API_SECRET.isEmpty() ) 
		   {
			   throw new Exception("API_KEY and API_SECRET in " + Settings.class );
		   }
	  }
}

package com.onemax.api;

public class OAuth2ClientCredentials 
{
	 public static final String BASE_URL = "https://sandbox-api.onemax.com.vn";
	
	 public static final String TOKEN_SERVER_URL = BASE_URL +"/oauth2/token";

	 public static final String AUTHORIZATION_SERVER_URL = BASE_URL + "/oauth2/authorize";
	 /** Value of the "API Key". */
	 public static final String API_KEY = "d5d48ae18a408d1e2bf124327e1c83aa";

	  /** Value of the "API Secret". */
	  public static final String API_SECRET = "c22abbc5ce613477237bebb1f433fd96";
	
	  /** Port in the "Callback URL". */
	  public static final int PORT = 80;
	
	  /** Domain name in the "Callback URL". */
	  public static final String DOMAIN = "127.0.0.1";
	  
	  public static void errorIfNotSpecified() throws Exception 
	  {
		   if (API_KEY.isEmpty() || API_SECRET.isEmpty() ) 
		   {
			   throw new Exception("API_KEY and API_SECRET in " + OAuth2ClientCredentials.class );
		   }
	  }
}

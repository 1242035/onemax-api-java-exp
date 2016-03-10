package com.onemax.api;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.GeneralSecurityException;

import java.util.Map;

import com.google.api.client.auth.oauth.OAuthHmacSigner;
import com.google.api.client.auth.oauth.OAuthParameters;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;

public class OnemaxBase 
{
	public static final HttpResponse get(String url, Map<String,String> params) throws IOException, GeneralSecurityException
	{
		url += 
		OAuthParameters oauthParameters = getAuthParams( url );
		
	    HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(oauthParameters);
	    HttpResponse response = requestFactory.buildGetRequest(url).execute();
	    
	    return response;
	}
	public static final HttpResponse post(GenericUrl url, Map<String,String> params) throws IOException, GeneralSecurityException
	{
	    OAuthParameters oauthParameters = getAuthParams( url );
	   System.out.println(params.toString() );
	    HttpContent body = (params == null) ? null :  ( new UrlEncodedContent( params ) );
	    HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(oauthParameters);
	    HttpResponse response = requestFactory.buildPostRequest(url, body ).execute();
	    dump(response);
	    return response;
	}
	public static final OAuthHmacSigner getSignature()
	{
		 OAuthHmacSigner signer  = new OAuthHmacSigner();
		 signer.clientSharedSecret = Settings.API_SECRET;
		 return signer;
	}
	
	public static final OAuthParameters getAuthParams(GenericUrl url) throws GeneralSecurityException
	{
		OAuthHmacSigner signer = getSignature();
		System.out.println("URL :: " + url.build()  );
	    System.out.println("getSignature :: " + signer.computeSignature( null ) );
	    System.out.println("getSignature :: " + signer.getSignatureMethod() );
	  
		OAuthParameters oauthParameters = new OAuthParameters();
	    
	    oauthParameters.signer = signer;
	    oauthParameters.consumerKey = Settings.API_KEY;    
	    oauthParameters.computeNonce();
	    oauthParameters.computeTimestamp();
	    oauthParameters.version = Settings.API_VERSION;
	    
	    return oauthParameters;
	}
	public static String parse(Map<String, String> params)
	{
		
	}
	public static void dump(Object o)
	{
	    Field[] fields = o.getClass().getDeclaredFields() ;
	    try
	    {
	        for(Field field : fields){
	            field.setAccessible(true);
	            Object value = field.get(o);
	            System.out.println(field.getName() + "=" + field.get(o));
	        }
	    }
	    catch(Exception e){
	        e.printStackTrace();
	    }
	}
}

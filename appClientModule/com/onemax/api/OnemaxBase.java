package com.onemax.api;

import java.io.IOException;
import java.util.Arrays;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

public class OnemaxBase {
	
	/** Directory to store user credentials. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".store/onemax");

	/**
	 * Global instance of the {@link DataStoreFactory}. The best practice is to
	 * make it a single globally shared instance across your application.
	 */
	private static FileDataStoreFactory DATA_STORE_FACTORY;
	

	/** Global instance of the HTTP transport. */
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	/** Global instance of the JSON factory. */
	static final JsonFactory JSON_FACTORY = new JacksonFactory();

	
	public static HttpRequestFactory getRequest() throws Exception
	{
		DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		final Credential credential = OnemaxBase.authorize();
		HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
			@Override
			public void initialize(HttpRequest request) throws IOException {
				credential.initialize(request);
				request.setParser(new JsonObjectParser(JSON_FACTORY));
			}
		});
		return requestFactory;
	}
	/** Authorizes the installed application to access user's protected data. */
	public static Credential authorize() throws Exception 
	{
		Settings.errorIfNotSpecified();
		// set up authorization code flow
		AuthorizationCodeFlow flow = new AuthorizationCodeFlow
				.Builder(
						BearerToken.authorizationHeaderAccessMethod(),
						HTTP_TRANSPORT, 
						JSON_FACTORY, 
						new GenericUrl(Settings.TOKEN_SERVER_URL),
						new ClientParametersAuthentication(Settings.API_KEY, Settings.API_SECRET),
						Settings.API_KEY, 
						Settings.AUTHORIZATION_SERVER_URL
				)
				.setScopes( Arrays.asList(Settings.SCOPE) )
				.setDataStoreFactory(DATA_STORE_FACTORY)
				.build();
		// authorize
		LocalServerReceiver receiver = new LocalServerReceiver
				                           .Builder()
				                           .setHost(Settings.DOMAIN)
				                           .setPort(Settings.PORT)
				                           .build();
		return new AuthorizationCodeInstalledApp(flow, receiver)
				.authorize("user");
	}
}

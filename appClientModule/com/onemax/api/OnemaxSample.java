package com.onemax.api;

import java.io.IOException;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
public class OnemaxSample {
	
	private static void postRedeem(HttpRequestFactory requestFactory) throws IOException {
		Url url = new Url( OAuth2ClientCredentials.BASE_URL + "/v1/merchants/vouchers/redeemtion");
	}
	
	private static void getCountries(HttpRequestFactory requestFactory) throws IOException {
		Url url = new Url( OAuth2ClientCredentials.BASE_URL + "/countries");
		url.setFields("id,code,name");

		HttpRequest request = requestFactory.buildGetRequest(url);
		CountryList countries = request.execute().parseAs(CountryList.class);
		if (countries.list.isEmpty()) 
		{
			System.out.println("No country found.");
		} 
		else 
		{
			if (countries.hasMore) 
			{
				System.out.print("First ");
			}
			System.out.println(countries.list.size() + " Countries found:");
			for (Country video : countries.list) 
			{
				System.out.println();
				System.out.println("-----------------------------------------------");
				System.out.println("ID: " + video.id);
				System.out.println("Title: " + video.name);
				System.out.println("code: " + video.code);
			}
		}
	}

	public static void main(String[] args) {
		try
		{
			HttpRequestFactory requestFactory = OnemaxBase.getRequest();
			getCountries(requestFactory);
			// Success!
			return;
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.exit(1);
	}
}

package com.onemax.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;

import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.UrlEncodedContent;
import com.google.gson.JsonObject;
public class OnemaxSample {
	
	private static void postRedeem(HttpRequestFactory requestFactory, String voucherCode, int qnty) throws IOException {
		Url url = new Url( Settings.BASE_URL + "/v1/merchants/vouchers/redeemtion");
		Map<String, String> params = new HashMap<String, String>(3);
        params.put("voucher_code", voucherCode);
        params.put("qnty", String.valueOf(qnty) );
        HttpContent content = new UrlEncodedContent(params);
        HttpRequest request = requestFactory.buildPostRequest(url, content);
		
        HttpResponse response = request.execute();

        JsonObject jo = null;
        try {
            if (response.isSuccessStatusCode()) {

                String json = response.parseAsString();
                System.out.println(json);
            } else{
            	System.out.println( "Request failed with " + response.getStatusCode() );
            }
        } finally {
            response.disconnect();
        }
	}
	
	private static void getCountries(HttpRequestFactory requestFactory) throws IOException {
		Url url = new Url( Settings.BASE_URL + "/countries");
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

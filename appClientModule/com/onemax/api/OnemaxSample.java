package com.onemax.api;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.UrlEncodedContent;
public class OnemaxSample 
{	
	private static HttpResponse postRedeem(String voucherCode, int qnty) throws IOException, GeneralSecurityException 
	{
		//
		String url = Settings.BASE_URL + "/v1/merchants/vouchers/redeemtion";
		GenericUrl requestUrl = new GenericUrl( url );
		
		Map<String, String> params = new HashMap<String, String>(2);
        params.put("voucher_code", voucherCode);
        params.put("qnty", String.valueOf(qnty) );
       
        HttpResponse response = OnemaxBase.post(requestUrl, params);
        return response;
	}
	
	private static HttpResponse getCountries() throws IOException, GeneralSecurityException 
	{
		Map<String, String> params = new HashMap<String, String>(2);
		params.put("country_code", "vn");
		
		String url =  Settings.BASE_URL + "/states";
		GenericUrl requestUrl = new GenericUrl( url );
		HttpResponse response = OnemaxBase.get(requestUrl,params);
		return response;
	}
	
	private static HttpResponse getStates() throws IOException, GeneralSecurityException 
	{
		String states = "vn";
		String url =  Settings.BASE_URL + "/countries";
		GenericUrl requestUrl = new GenericUrl( url );
		requestUrl.set("country_code", states);
		HttpResponse response = OnemaxBase.get(requestUrl);
		return response;
	}
	
	public static void main(String[] args) 
	{
		try
		{
			HttpResponse response = getCountries();
			//R1K3-H769
			//HttpResponse response = postRedeem("R1K3-H769", 1);
			System.out.println(response.parseAsString());
			return;
		} 
		catch (IOException e) {
			System.err.println(e.getMessage());
		} 
		catch (Throwable t) {
			t.printStackTrace();
		}
		System.exit(1);
	}
}

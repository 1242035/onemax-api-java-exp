package com.onemax.api;

import java.util.List;

import com.google.api.client.util.Key;

public class CountryList 
{
	  @Key
	  public List<Country> list;

	  @Key
	  public int limit;

	  @Key("has_more")
	  public boolean hasMore;
}

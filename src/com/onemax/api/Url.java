package com.onemax.api;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Key;

public class Url extends GenericUrl 
{
  @Key
  private String fields;

  public DailyMotionUrl(String encodedUrl) 
  {
    super(encodedUrl);
  }

  /**
   * @return the fields
   */
  public String getFields() 
  {
    return fields;
  }

  /**
   * @param fields the fields to set
   */
  public void setFields(String fields) 
  {
    this.fields = fields;
  }
}

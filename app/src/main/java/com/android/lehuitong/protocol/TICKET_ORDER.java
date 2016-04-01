package com.android.lehuitong.protocol;

import org.json.JSONException;

public class TICKET_ORDER {
	
	public String order_id;
	
	public String order_status;
	
	public String good_id;
	
	public String effective_time_start;
	
	public String effective_time_end;
	
	public GOODS goods = new GOODS();
	
	
	public void fromJson() throws JSONException{
		
	}
	
	
}

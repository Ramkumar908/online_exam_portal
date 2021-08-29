package com.exam.model;
import java.util.Map;

public class ResponseWrapper {
	
	private StatusDescriptionModel statusDescription;
	private Map<String, Object> data;
	
	public StatusDescriptionModel getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(StatusDescriptionModel statusDescription) {
		this.statusDescription = statusDescription;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	

}


package com.exam.model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class StatusDescriptionModel {
	
	

	private Integer statusCode;
	private String statusMessage;
	private String transactionId;
	
	
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	@Override
	public String toString() {
		return "StatusDescriptionModel [statusCode=" + statusCode + ", statusMessage=" + statusMessage+ "]";
	}
	
}

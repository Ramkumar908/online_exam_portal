package com.exam.model;

public class StudentResult {
	private double marksGot;
	private int correctAnswers;
	private int attempted;
	
	
	public StudentResult() {
		
	}


	public StudentResult(double marksGot, int correctAnswers,int attempted) {
		this.marksGot = marksGot;
		this.correctAnswers = correctAnswers;
		this.attempted = attempted;
	}


	public double getMarksGot() {
		return marksGot;
	}


	public void setMarksGot(double marksGot) {
		this.marksGot = marksGot;
	}


	public int  getCorrectAnswers() {
		return correctAnswers;
	}


	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}


	public int getAttempted() {
		return attempted;
	}


	public void setAttempted(int attempted) {
		this.attempted = attempted;
	}


	@Override
	public String toString() {
		return "StudentResult [marksGot=" + marksGot + ", correctAnswers=" + correctAnswers + ", attempted=" + attempted
				+ "]";
	}
	


}

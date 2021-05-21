package com.CCT.TripApplication.entities.DTO;
/**
 * This entity for detail of expense.
 * @author Diren
 *
 */
public class ExpenseDetail {

	//Properties
	private String username;
	private double spent;
	private double result;

	// Constructor
	public ExpenseDetail() {
	}

	public ExpenseDetail(String username, double spent, double result) {
		this.username = username;
		this.spent = spent;
		this.result = result;
	}

	//Getter and Setter methods
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getSpent() {
		return spent;
	}

	public void setSpent(double spent) {
		this.spent = spent;
	}

	public double getResult() {
		return result;
	}

	public void setResult(double result) {
		this.result = result;
	}

}

package com.CCT.TripApplication.entities;

public class Expense {

	// Properties
	private int expenseId;
	private String expenseDescription;
	private double expenseAmount;
	private User user; // who add expense
	private Trip trip; // which trip is added

	// Constructor
	public Expense() {
	}

	public Expense(String expenseDescription, double expenseAmount, User user, Trip trip) {
		this.expenseDescription = expenseDescription;
		this.expenseAmount = expenseAmount;
		this.user = user;
		this.trip = trip;
	}
	
	//Getter and Setter methods
	public int getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}

	public String getExpenseDescription() {
		return expenseDescription;
	}

	public void setExpenseDescription(String expenseDescription) {
		this.expenseDescription = expenseDescription;
	}

	public double getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(double expenseAmount) {
		this.expenseAmount = expenseAmount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

}

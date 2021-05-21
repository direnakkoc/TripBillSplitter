package com.CCT.TripApplication.entities;

import java.util.ArrayList;

public class Trip {

	// Properties
	private String tripname;
	private boolean tripStatus;
	private ArrayList<String> usernames; // who is in the trip

	// Constructor
	public Trip() {
		this.usernames = new ArrayList<String>();
	}

	// Getter and Setter methods
	public String getTripname() {
		return tripname;
	}

	public void setTripname(String tripname) {
		this.tripname = tripname;
	}

	public boolean isTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(boolean tripStatus) {
		this.tripStatus = tripStatus;
	}

	public ArrayList<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(ArrayList<String> usernames) {
		this.usernames = usernames;
	}

}

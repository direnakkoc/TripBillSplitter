package com.CCT.TripApplication.business.messages;
/**
 * Messages for needed
 * @author Diren
 *
 */
public class Message {

	// User messages
	public static String usersNotFound = "Users  are not found";
	public static String usersAreListed = "Users are successfully listed";
	public static String UserSaved = "User is saved to the system";
	public static String UserNotSaved = "Something went wrong. User is not saved";
	public static String UserDeleted = "User is deleted";
	public static String UserUpdated = "User is updated";
	public static String UserIsNotExisted = "This user is not existed in the system";
	public static String UserIsExisted = "User is existed.";
	public static String UsernameIsTooShort = "Username should be more than 2 letter";
	public static String UserNotFound = "User is not found.";
	public static String Joined = "Successfully joined";
	public static String PasswordIsTooShort = "Password should be more than 6 character";

	// Expense messages
	public static String ExpensesAreListed = "Expense(s) are/is successfully listed";
	public static String ExpensesNotFound = "Expense(s) are/is not found";
	public static String ExpenseSaved = "Expense is saved";
	public static String ExpenseDidNotSaved = "Something went wrong. Expense is not saved";
	public static String ExpenseDeleted = "Expense is deleted";
	public static String ExpenseUpdated = "Expense is updated";
	public static String ExpenseIsNotExisted = "Error: Expense is not existed";

	// Trip messages
	public static String TripsNotFound = "Trips are not found";
	public static String TripsAreListed = "Trips are successfully listed";
	public static String TripSaved = "Trip is saved to the system. You can join now.";
	public static String TripNotSaved = "Something went wrong. Trip is not saved";
	public static String TripDeleted = "Trip is deleted";
	public static String TripUpdated = "Trip is updated";
	public static String TripIsNotActive = "Error: Trip is not active anymore.";
	public static String TripIsNotExisted = "Error: This trip is not existed in the system";
	public static String ThisTripNameUsed = "Error: This trip name is used. Please, join the trip";

}

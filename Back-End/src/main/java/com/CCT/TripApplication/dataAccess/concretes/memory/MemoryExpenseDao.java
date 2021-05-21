package com.CCT.TripApplication.dataAccess.concretes.memory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.CCT.TripApplication.dataAccess.abstracts.ExpenseDao;
import com.CCT.TripApplication.entities.Expense;

/**
 * Data access layer for in memory of the Expense class
 * 
 * @author Diren
 *
 */
@Repository
public class MemoryExpenseDao implements ExpenseDao {

	private List<Expense> expenses; // List of Expense to save expenses in memory
	private int expenseId; // primary key of expense class

	// Constructor
	public MemoryExpenseDao() {
		this.expenses = new ArrayList<>();
		this.expenseId = 0;
	}

	/**
	 * Method to save the expenses to in memory database system
	 * 
	 * @param expense Expense which user spent and add the system
	 */
	@Override
	public void save(Expense expense) {
		// Setting the expense's primary key
		expense.setExpenseId(expenseId + 1);
		// Setting the expense's tripname to upper case and trim around the spaces
		expense.getTrip().setTripname(expense.getTrip().getTripname().toUpperCase().trim());
		// Adding expense to the list of expenses
		expenses.add(expense);
		expenseId++;
	}

	/**
	 * Method to delete an Expense from the memory database system
	 * 
	 * @param expense Expense which user offered to delete
	 */
	@Override
	public void delete(Expense expense) {
		Expense expenseToDelete = new Expense(); // Creating an instance of expense class
		// Loops for all the expenses
		for (Expense e : expenses) {
			// If ids are matched delete that expense
			if (e.getExpenseId() == expense.getExpenseId()) {
				expenseToDelete = e;
				expenses.remove(expenseToDelete); // Remove from the database
				return; // Complete the delete method
			}
		}
	}

	/**
	 * Method to update an Expense from the memory database system
	 * 
	 * @param expense Expense which user wants to update
	 */
	@Override
	public void update(Expense expense) {
		Expense expenseToUpdate = new Expense(); // Creating an instance of expense class
		// Loops for all the expenses
		for (Expense e : expenses) {
			// If ids are matched update that expense
			if (e.getExpenseId() == expense.getExpenseId()) {
				expenseToUpdate = e;
				// Setting expense amount which is given by user
				expenseToUpdate.setExpenseAmount(expense.getExpenseAmount());
				// Setting expense description which is given by user
				expenseToUpdate.setExpenseDescription(expense.getExpenseDescription());
				// Setting Trip which is given by user
				expense.getTrip().setTripname(expense.getTrip().getTripname().toUpperCase());
				expenseToUpdate.setTrip(expense.getTrip());
				// Setting User which is given by user
				expenseToUpdate.setUser(expense.getUser());
				return; // complete the update method
			}
		}
	}

	/**
	 * Return all the expenses which are in the memory database
	 */
	@Override
	public List<Expense> findAll() {
		return this.expenses;
	}

	/**
	 * Return an expense which expense's id is given by user
	 * 
	 * @param id Expense id which is primary key of expense
	 */
	@Override
	public Expense get(int id) {
		Expense expense = new Expense(); // Creating an instance of expense class
		// Loops for all the expenses
		for (Expense e : expenses) {
			// If ids are matched take that expense
			if (e.getExpenseId() == id) {
				expense = e;
				return expense;
			}
		}
		return expense;
	}

}

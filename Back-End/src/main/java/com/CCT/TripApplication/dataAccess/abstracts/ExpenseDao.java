package com.CCT.TripApplication.dataAccess.abstracts;

import java.util.List;

import com.CCT.TripApplication.entities.Expense;

/**
 * CRUD methods for the Expense class
 * 
 * @author Diren
 *
 */
public interface ExpenseDao {
	/**
	 * Save method for expenses
	 * 
	 * @param expense Expense which user spent and add the system
	 */
	void save(Expense expense);

	/**
	 * Delete method for expenses
	 * 
	 * @param expense Expense which user offered to delete
	 */
	void delete(Expense expense);

	/**
	 * Update method for expenses
	 * 
	 * @param expense Expense which user wants to update
	 */
	void update(Expense expense);

	/**
	 * Return the all of the expenses
	 * 
	 * @return
	 */
	List<Expense> findAll();

	/**
	 * Return a Expense from the system
	 * 
	 * @param id Expense id which is primary key of expense
	 * @return
	 */
	Expense get(int id);
}

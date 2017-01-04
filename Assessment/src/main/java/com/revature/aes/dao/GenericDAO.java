
package com.revature.aes.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The Interface GenericDAO.
 *
 * @param <T>
 *            the generic type
 */
@Repository
public interface GenericDAO<T> extends JpaRepository<T, Integer> {
	
	/**
	 * Creates the.
	 *
	 * @param t
	 *            the t
	 * @return true, if successful
	 */
	public boolean create(T t);
	
	/**
	 * Update.
	 *
	 * @param index
	 *            the index
	 * @param t
	 *            the t
	 * @return true, if successful
	 */
	public boolean update(int index, T t);
	
	/**
	 * Removes the.
	 *
	 * @param index
	 *            the index
	 * @return true, if successful
	 */
	public boolean delete(int index);
	
	/**
	 * Gets the by index.
	 *
	 * @param index
	 *            the index
	 * @return the by index
	 */
	public T findTById(int index);
	
	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	public List<T> findAll();
	
}

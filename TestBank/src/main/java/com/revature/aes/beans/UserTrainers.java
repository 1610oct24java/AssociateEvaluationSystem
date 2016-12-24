/****************************************************************
 * Project Name: Associate Evaluation System - Test Bank
 * 
 * Description: A simple rest application that persists test
 * 		information into a database. Use to evaluate associates
 * 		performance both during and before employment with Revature 
 * 		LLC.
 * 
 * Authors: Matthew Beauregard, Conner Anderson, Travis Deshotels,
 * 		Edward Crader, Jon-Erik Williams 
 ****************************************************************/

package com.revature.aes.beans;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
@Entity
@Table(name="AES_USER_TRAINERS")
public class UserTrainers implements Serializable
{

	/**
	 * @serialVersionUID An auto-generated field that is used for serialization.
	 */
	private static final long serialVersionUID = -1729135840119033834L;

	/**
	 * @user An User who role is of type associate.
	 */
	@JoinColumn(name="USER_ID")
	private User associate;
	
	/**
	 * @trainer The trainer of the associate.
	 */
	@JoinColumn(name="USER_ID")
	private User trainer;

	public UserTrainers()
	{
		super();
	}

	public UserTrainers(User associate, User trainer)
	{
		this();
		this.associate = associate;
		this.trainer = trainer;
	}

	public User getAssociate()
	{
		return associate;
	}

	public void setAssociate(User associate)
	{
		this.associate = associate;
	}

	public User getTrainer()
	{
		return trainer;
	}

	public void setTrainer(User trainer)
	{
		this.trainer = trainer;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((associate == null) ? 0 : associate.hashCode());
		result = prime * result + ((trainer == null) ? 0 : trainer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserTrainers other = (UserTrainers) obj;
		if (associate == null)
		{
			if (other.associate != null)
				return false;
		} else if (!associate.equals(other.associate))
			return false;
		if (trainer == null)
		{
			if (other.trainer != null)
				return false;
		} else if (!trainer.equals(other.trainer))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "UserTrainers [associate=" + associate + ", trainer=" + trainer + "]";
	}
	
	
}

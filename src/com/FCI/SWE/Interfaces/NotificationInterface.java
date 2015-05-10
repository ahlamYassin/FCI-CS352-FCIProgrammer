package com.FCI.SWE.Interfaces;

 
public interface NotificationInterface {
	public void add(Observer obj);

	// method to notify observers of change
	public void notifyUsers();

}

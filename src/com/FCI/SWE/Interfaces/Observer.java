package com.FCI.SWE.Interfaces;

import com.FCI.SWE.Models.Message;

public interface Observer {
	// method to update the observer 
	public void sendNotify();

	// attach with subject to observe
	public void setSubject(Message msg);
}
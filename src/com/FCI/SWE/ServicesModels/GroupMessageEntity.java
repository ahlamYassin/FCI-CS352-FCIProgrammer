package com.FCI.SWE.ServicesModels;

import java.util.ArrayList;
import java.util.List;

import com.FCI.SWE.Interfaces.NotificationInterface;
import com.FCI.SWE.Interfaces.Observer;
 
public class GroupMessageEntity implements NotificationInterface{
	private List<Observer> observers;
	private String message;
	private boolean changed;
	private final Object MUTEX = new Object();

	@Override
	public void add(Observer obj) {

	}

	@Override
	public void notifyUsers() {
		List<Observer> observersLocal = null;

		synchronized (MUTEX) {
			if (!changed)
				return;
			observersLocal = new ArrayList<Observer>(this.observers);
			this.changed = false;
		}
		for (Observer obj : observersLocal) {
			obj.sendNotify();
		}
	}

	// method to post message to the group
	public void postMessage(String msg) {
 		this.message = msg;
		this.changed = true;
		notifyUsers();
	}
}

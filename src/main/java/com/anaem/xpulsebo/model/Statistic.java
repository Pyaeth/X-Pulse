package com.anaem.xpulsebo.model;

import java.util.Map;

public class Statistic {
	
	private int noOfTransactions;
	private int noIncoming;
	private int noOutgoing;

	private float incoming;
	private float outgoing;
	
	private float balance;
	private float comparedToLast;
	
	public Map<String, Integer> statisticsInt;
	public Map<String, Float> statisticsFlt;
	
	public int getNoOfTransactions() {
		return noOfTransactions;
	}
	public void setNoOfTransactions(int noOfTransactions) {
		this.noOfTransactions = noOfTransactions;
	}
	public int getNoIncoming() {
		return noIncoming;
	}
	public void setNoIncoming(int noIncoming) {
		this.noIncoming = noIncoming;
	}
	public int getNoOutgoing() {
		return noOutgoing;
	}
	public void setNoOutgoing(int noOutgoing) {
		this.noOutgoing = noOutgoing;
	}
	public float getIncoming() {
		return incoming;
	}
	public void setIncoming(float incoming) {
		this.incoming = incoming;
	}
	public float getOutgoing() {
		return outgoing;
	}
	public void setOutgoing(float outgoing) {
		this.outgoing = outgoing;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public float getComparedToLast() {
		return comparedToLast;
	}
	public void setComparedToLast(float comparedToLast) {
		this.comparedToLast = comparedToLast;
	}
	
	
}

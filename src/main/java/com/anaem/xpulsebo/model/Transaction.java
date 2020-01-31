package com.anaem.xpulsebo.model;

import java.sql.Date;
import java.time.LocalDate;

public class Transaction {
	private int tid;
	private int uid;
	private Date drequest;
	private Date dprocess;
	private String type;
	private String details;
	private float amount;
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public Date getDrequest() {
		return drequest;
	}
	public void setDrequest(Date drequest) {
		this.drequest = drequest;
	}
	public Date getDprocess() {
		return dprocess;
	}
	public void setDprocess(Date dprocess) {
		this.dprocess = dprocess;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	
}

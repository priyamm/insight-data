package com.insightdatascience;

import java.util.Date;

public class Transaction {
	
	public Date createdTime;
	public Integer target;
	public Integer actor;
	public Integer status;
	
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
	public Integer getActor() {
		return actor;
	}
	public void setActor(Integer actor) {
		this.actor = actor;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Transaction [createdTime=" + createdTime + ", target=" + target + ", actor=" + actor + ", status="
				+ status + "]";
	}
}

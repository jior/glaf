package com.glaf.base.modules.others.model;

import java.io.Serializable;

public class TotalMoney implements Serializable{
	
	private  double totalDecisionSum;
	
	private double totalOrderSum;
	private double totalOrderSum2;
	private double acceptSum;
	private double purchaseAcceptSum;
	public double getAcceptSum() {
		return acceptSum;
	}

	public void setAcceptSum(double acceptSum) {
		this.acceptSum = acceptSum;
	}

	public double getTotalOrderSum2() {
		return totalOrderSum2;
	}

	public void setTotalOrderSum2(double totalOrderSum2) {
		this.totalOrderSum2 = totalOrderSum2;
	}

	private double totalContractSum;
	
	private double realPaySum;
	
	private double realPaySum2;
	
	private double unPaySum;
	
	private double unPaySum2;


	public double getTotalDecisionSum() {
		return totalDecisionSum;
	}

	public void setTotalDecisionSum(double totalDecisionSum) {
		this.totalDecisionSum = totalDecisionSum;
	}

	public double getTotalOrderSum() {
		return totalOrderSum;
	}

	public void setTotalOrderSum(double totalOrderSum) {
		this.totalOrderSum = totalOrderSum;
	}

	public double getTotalContractSum() {
		return totalContractSum;
	}

	public double getRealPaySum() {
		return realPaySum;
	}

	public void setRealPaySum(double realPaySum) {
		this.realPaySum = realPaySum;
	}

	public double getRealPaySum2() {
		return realPaySum2;
	}

	public void setRealPaySum2(double realPaySum2) {
		this.realPaySum2 = realPaySum2;
	}

	public double getUnPaySum() {
		return unPaySum;
	}

	public void setUnPaySum(double unPaySum) {
		this.unPaySum = unPaySum;
	}

	public double getUnPaySum2() {
		return unPaySum2;
	}

	public void setUnPaySum2(double unPaySum2) {
		this.unPaySum2 = unPaySum2;
	}

	public void setTotalContractSum(double totalContractSum) {
		this.totalContractSum = totalContractSum;
	}

	public double getPurchaseAcceptSum() {
		return purchaseAcceptSum;
	}

	public void setPurchaseAcceptSum(double purchaseAcceptSum) {
		this.purchaseAcceptSum = purchaseAcceptSum;
	}
	
	
	
	
	
}

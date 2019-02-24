package com.ibanking.payment;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Student {
	private int stId;
	private int scFee;
	public Student() {
		
	}
	public Student(int stId, int scFee) {
		this.stId=stId;
		this.scFee=scFee;
	}
	public int getStId() {
		return stId;
	}
	public void setStId(int stId) {
		this.stId = stId;
	}
	public int getScFee() {
		return scFee;
	}
	public void setScFee(int scFee) {
		this.scFee = scFee;
	}
}

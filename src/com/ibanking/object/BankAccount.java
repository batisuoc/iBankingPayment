package com.ibanking.object;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BankAccount {
	private String bankId;
	private String name;
	private int phone;
	private String email;
	private int balance;
	public BankAccount() {
		
	}
	public BankAccount(String bankId, String name, int phone, String email, int balance) {
		this.bankId=bankId;
		this.name=name;
		this.phone=phone;
		this.email=email;
		this.balance=balance;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
}

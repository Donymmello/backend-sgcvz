package com.supportportal.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String code;

	private Date createDate;

	private double balance;

	@ManyToOne
	@JsonBackReference(value = "user")
	private User user;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Operation> operations;

	public Account() {
		super();
	}

	public Account(String code, Date createDate, double balance, User user) {
		super();
		this.code = code;
		this.createDate = createDate;
		this.balance = balance;
		this.user = user;
	}

	public Account(String code, Date createDate, double balance, User user, List<Operation> operations) {
		super();
		this.code = code;
		this.createDate = createDate;
		this.balance = balance;
		this.user = user;
		this.operations = operations;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public void doOperation(Operation operation) {
		if (getOperations() == null) {
			this.operations = new ArrayList<>();
		}
		getOperations().add(operation);
		operation.setAccount(this);
	}

}

package com.sean.data;

public class Person {
	/**
	 * ����
	 */
	private String username;
	/**
	 * ����
	 */
	private int age;
	/**
	 * ����
	 */
	private int weight;

	public Person(String username, int age ,int weight) {
		super();
		this.username = username;
		this.age = age;
		this.weight = weight;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String toString() {
		return "����:" + this.username + "\t" + "����:" + this.age + "\t" + "����:"
				+ this.weight;
	}
}
package com.sean.data;

import java.math.BigDecimal;

public class Room {

	private String roomName;

	private int area;

	private BigDecimal price;

	private String address;

	private String phoneNumber;

	public Room(String roomName, int area, BigDecimal price) {
		this.roomName = roomName;
		this.area = area;
		this.price = price;
		this.address = "Suzhou street, Haidian District, Beijing";
		this.phoneNumber = "0000000";
	}

	public Room(String roomName) {
		this.roomName = roomName;
	}

	public Room(String roomName, String area, String price, String address,
			String phone) {
		this.roomName = roomName;
		this.area = Integer.parseInt(area);
		this.price = new BigDecimal(price);
		this.address = address;
		this.phoneNumber = phone;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * ��������ͬ����Ϊ��ͬһ����
	 * 
	 * @param room
	 * @return
	 */
	public boolean equal(Room room) {
		if (this.roomName.equals(room.roomName))
			return true;
		else
			return false;
	}
}

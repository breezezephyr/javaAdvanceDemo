package com.sean.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sean.data.Person;
import com.sean.data.Room;

public class OrderService {
	public Map<Room, Person[]> orderMap;

	public OrderService() {
		orderMap = new HashMap<Room, Person[]>();
		try {
			this.initOrderList();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initOrderList() throws IOException {
		StringBuffer str = new StringBuffer("");
		String roomdata = "";
		String st = "";
		FileInputStream fs = new FileInputStream("room.txt");
		InputStreamReader isr = new InputStreamReader(fs);
		BufferedReader br = new BufferedReader(isr);
		while ((roomdata = br.readLine()) != null) {
			String[] data = roomdata.split(",");
			Room room = new Room(data[0], data[1], data[2], data[3], data[4]);
			orderMap.put(room, null);
		}
	}

	public void addOrder(Person[] persons, String roomName) {
		Set<Room> rooms = new HashSet<Room>();
		rooms = orderMap.keySet();
		Room targetRoom = new Room(roomName);
		for (Room r : rooms) {
			if (r.equal(targetRoom)) {
				if (orderMap.get(r) != null) {
					System.out.println("Room " + roomName + " is occupied");
					break;
				}
				targetRoom = r;
			}
		}
		if (targetRoom.getAddress() != null)
			orderMap.put(targetRoom, persons);
		else
			System.out.println("Room " + roomName + " is not exist");
	}

	public Person[] queryUsers(String roomName) {
		Set<Room> rooms = new HashSet<Room>();
		rooms = orderMap.keySet();
		Room targetRoom = new Room(roomName);
		for (Room r : rooms) {
			if (r.equal(targetRoom))
				targetRoom = r;
		}
		if (targetRoom.getAddress() != null) {
			Person[] persons = orderMap.get(targetRoom);
			return persons;
		} else
			return null;
	}
	public static void main(String[] args) {
		OrderService ordService = new OrderService();
		Room room = new Room("001");
		Person[] person_01 = { new Person("Sean", 20, 111) };
		ordService.addOrder(person_01, "003");
		ordService.queryUsers("003");
		System.out.println(ordService.queryUsers("003")[0]);
	}
}

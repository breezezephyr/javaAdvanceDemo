package com.sean.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import com.sean.data.Room;

public class RoomService {
	private Set<Room> allRooms;

	public RoomService() {
		allRooms = new HashSet<Room>();
	}

	public Set<Room> getEntireRooms(String filePathAndName) throws IOException {
		StringBuffer str = new StringBuffer("");
		String roomdata = "";
		String st = "";
		FileInputStream fs = new FileInputStream(filePathAndName);
		InputStreamReader isr = new InputStreamReader(fs);
		BufferedReader br = new BufferedReader(isr);
		while ((roomdata = br.readLine()) != null) {
			String[] data = roomdata.split(",");
			Room room = new Room(data[0], data[1], data[2], data[3], data[4]);
			allRooms.add(room);
		}
		return allRooms;
	}

	public static void main(String[] args) {
		RoomService  rooms = new RoomService();
		String dirPath  ;
		try {
			
			rooms.getEntireRooms("room.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

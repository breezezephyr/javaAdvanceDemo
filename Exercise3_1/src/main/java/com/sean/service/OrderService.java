package com.sean.service;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.sean.data.Person;
import com.sean.data.Room;

public class OrderService {
    protected final Logger logger = LoggerFactory.getLogger(OrderService.class);
    public Map<Room, Person[]> orderMap;

    public OrderService() {
        orderMap = new HashMap<Room, Person[]>();
        try {
            this.initOrderList();
        } catch (IOException e) {
            logger.warn("room.txt not exist");
        }
    }

    private void initOrderList() throws IOException {
        String roomData = "";
        URL url = Resources.getResource("room.txt");
        List<String> lines = Resources.readLines(url, Charsets.UTF_8);
        for (Iterator<String> it = lines.iterator(); it.hasNext(); ) {
            roomData = it.next();
            String[] data = roomData.split(",");
            Room room = new Room(data[0], data[1], data[2], data[3], data[4]);
            orderMap.put(room, null);
        }
    }

    public void addOrder(Person[] persons, String roomName) {
        Set<Room> rooms = orderMap.keySet();
        Room targetRoom = new Room(roomName);
        for (Room r : rooms) {
            if (r.equal(targetRoom)) {
                if (orderMap.get(r) != null) {
                    logger.info("Room ".concat(roomName).concat(" is occupied"));
                    break;
                }
                targetRoom = r;
            }
        }
        if (targetRoom.getAddress() != null)
            orderMap.put(targetRoom, persons);
        else
            logger.info("Room " + roomName + " is not exist");
    }

    public Person[] queryUsers(String roomName) {
        Set<Room> rooms = orderMap.keySet();
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
        Person[] person_01 = {new Person("Sean", 20, 111)};
        ordService.addOrder(person_01, "003");
        System.out.println(ordService.queryUsers("003")[0]);
    }
}

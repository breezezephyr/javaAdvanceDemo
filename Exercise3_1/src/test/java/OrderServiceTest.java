import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sean.data.Person;
import com.sean.data.Room;
import com.sean.service.OrderService;

public class OrderServiceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    OrderService ordService;
    Map<Room, Person[]> ordMap;

    @Before
    public void setUp() throws Exception {
        ordService = new OrderService();
        ordMap = ordService.orderMap;
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    public void testAddOrder_2() {
        Room room = new Room("001");
        Person[] person_01 = {new Person("Sean", 20, 66)};
        ordMap.put(room, person_01);
        ordService.addOrder(person_01, "001");
        System.out.println(outContent.toString());
        assertTrue(errContent.toString().contains("Room 001 is occupied"));

    }

    @Test
    public void testAddOrder_1() {
        Person[] person_01 = {new Person("Sean", 20, 111)};
        ordService.addOrder(person_01, "003");
    }

    @Test
    public void testAddOrder_3() {
        Person[] person_01 = {new Person("Sean", 20, 111)};
        ordService.addOrder(person_01, "013");
        assertTrue(errContent.toString().contains("Room 013 is not exist"));
    }

    @Test
    public void testQueryUsers() {
        Person[] person_01 = {new Person("Sean", 20, 80),
                new Person("Nancy", 20, 50)};
        ordService.addOrder(person_01, "003");
        Person[] persons = ordService.queryUsers("003");
        assertEquals("Sean", persons[0].getUsername());
        assertEquals("Nancy", persons[1].getUsername());
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }
}

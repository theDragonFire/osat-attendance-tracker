package controller;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.Date;

public class ControllerTest {
	
	private Controller controller;
	
	private static final String NEW_FILE_LOC = System.getProperty("user.dir") + File.separator + "newFile.xml";
	private static final String FILE_LOC = System.getProperty("user.dir") + File.separator + "test.xml";
	private static final String STOCK_PERSON = "STOCK PERSON";
	private static final String STOCK_PERSON2 = "STOCK PERSON 2";
	private static final String PRESENT = "p";
	
	@Before
	public void setUp() throws Exception {
		controller = new Controller();
	}

	@After
	public void tearDown() throws Exception {
		File newFile = new File(NEW_FILE_LOC);
		if(newFile.exists())
			newFile.delete();
	}
	
	@Test
	public void testLoadRoster() throws Exception {
		controller.loadRoster(FILE_LOC);
		assertTrue(controller.rosterIsLoaded());
		assertFalse(controller.rosterIsEmpty());
		assertEquals(FILE_LOC, controller.rosterLocation());
	}
	
	@Test
	public void testNewRoster() throws Exception {
		controller.newRoster(NEW_FILE_LOC);
		assertTrue(controller.rosterIsLoaded());
		assertTrue(controller.rosterIsEmpty());
		assertEquals(NEW_FILE_LOC, controller.rosterLocation());
	}
	
	@Test
	public void testSaveRoster() throws Exception {
		
		controller.loadRoster(FILE_LOC);
		controller.addMember(STOCK_PERSON);
		controller.saveRoster();
		
		Controller other = new Controller();
		other.loadRoster(FILE_LOC);
		
		assertEquals(controller, other);
		
		controller.removeMember(STOCK_PERSON);
		controller.saveRoster();
		
	}
	
	@Test
	public void testMarkAttendance() throws Exception {
		
		// Test marking a single person
		// Need to create a date object because that is how attendance is stored
		Date todayDate = new Date();
		GregorianCalendar today = todayDate.toGregorianCalendar();
		
		controller.newRoster(NEW_FILE_LOC);
		controller.addMember(STOCK_PERSON);
		controller.markAttendance(STOCK_PERSON, PRESENT, today);
		
		ArrayList<GregorianCalendar> attendance = new ArrayList<GregorianCalendar>();
		attendance.add(today);
		
		assertEquals(controller.getMemberAttendance(STOCK_PERSON), attendance);
		
		// Test marking multiple people
		Date lastYearDate = new Date(todayDate.year()-1, todayDate.month(), todayDate.day());
		GregorianCalendar lastYear = lastYearDate.toGregorianCalendar();
		lastYear.set(today.get(Calendar.YEAR) - 1, today.get(Calendar.MONTH), today.get(Calendar.DATE));
		
		HashMap<String, String> attendanceMarks = new HashMap<String, String>();
		attendanceMarks.put(STOCK_PERSON, PRESENT);
		attendanceMarks.put(STOCK_PERSON2, PRESENT);
		
		controller.addMember(STOCK_PERSON2);
		controller.markAttendance(attendanceMarks, lastYear);
		
		attendance.add(lastYear);
		
		assertEquals(controller.getMemberAttendance(STOCK_PERSON), attendance);
		
		attendance.remove(today);
		
		assertEquals(controller.getMemberAttendance(STOCK_PERSON2), attendance);
		
	}

}

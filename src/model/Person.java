package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import nu.xom.*;

public class Person {
	
	private String name;
	
	private HashMap<String, String> info;
	/*
	
	Recommended attributes for Robotics
	
	int grade;
	String team;
	String position;
	String email;
	String discordAccount
	
	*/
		
	private ArrayList<GregorianCalendar> attendance;
	private ArrayList<Task> tasks;
	
	//Temporary ID
	private int tempID;
	
	public Person(String name, int tempID) {
		this.name = name;
		this.info = new HashMap<String, String>();
		this.attendance = new ArrayList<GregorianCalendar>();
		this.tasks = new ArrayList<Task>();
		this.tempID = tempID;
	}
	
	public Person(String name, HashMap<String, String> info, int tempID) {
		this.name = name;
		this.info = info;
		this.attendance = new ArrayList<GregorianCalendar>();
		this.tasks = new ArrayList<Task>();
		this.tempID = tempID;
	}
	
	public Person(String name, HashMap<String, String> info, ArrayList<GregorianCalendar> attendance, ArrayList<Task> tasks, int tempID) {
		this.name = name;
		this.info = info;
		this.attendance = attendance;
		this.tasks = tasks;
		this.tempID = tempID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public HashMap<String, String> getInfo() {
		return info;
	}
	
	public ArrayList<GregorianCalendar> getAttendance() {
		return attendance;
	}
	
	public ArrayList<Task> getTasks() {
		return tasks;
	}
	
	public int getTempID() {
		return tempID;
	}
	
	public void addInfo(String attribute, String value) {
		info.put(attribute, value);
	}
	
	public void removeInfo(String attribute) {
		info.remove(attribute);
	}
	
	public void addAttendedDay(GregorianCalendar date) {
		attendance.add(date);
	}
	
	public void removeAttendedDay(GregorianCalendar date) {
		attendance.remove(date);
	}
	
	public void addTask(Task task) {
		tasks.add(task);
	}
	
	public void editTask(Task prevTask, Task newTask) {
		tasks.add(tasks.indexOf(prevTask), newTask);
		tasks.remove(prevTask);
	}
	
	public void removeTask(Task task) {
		tasks.remove(task);
	}
	
	public Element toXMLelement() {
		
		//Create elements (info elements created later)
		Element name = new Element("name");
		Element attendance = new Element("attendance");
		Element tasks = new Element("tasks");
		
		//Create main element
		Element person = new Element("person");
		
		//Add name to name element and add name element
		name.appendChild(this.name);
		person.appendChild(name);
		
		//Create and add info elements from the hash map
		for(String textDescriptor : info.keySet()) {
			
			//Create a new element, add a descriptor, and add the actual text
			Element info = new Element("info");
			Attribute detail = new Attribute("descriptor", textDescriptor);
			info.addAttribute(detail);
			info.appendChild(this.info.get(textDescriptor));
			
			person.appendChild(info);
			
		}
		
		//Add each attendance date correctly
		for(int i = 0; i < this.attendance.size(); i++) {
			
			//Get the next date
			GregorianCalendar date = this.attendance.get(i);
			Element formattedDate = new Element("date");
			
			//Build date element in correct format (YYYY-MM-DD)
			String year = Integer.toString(date.get(Calendar.YEAR));
			String month = Integer.toString(date.get(Calendar.MONTH) + 1);
			String day = Integer.toString(date.get(Calendar.DAY_OF_MONTH));
			
			if(date.get(Calendar.DAY_OF_MONTH) < 10) {
				day = "0" + day;
			}
			
			if(date.get(Calendar.MONTH) < 10) {
				month = "0" + month;
			}
			
			if(date.get(Calendar.YEAR) < 10) {
				year = "000" + year;
			} else if(date.get(Calendar.YEAR) < 100) {
				year = "00" + year;
			} else if(date.get(Calendar.YEAR) < 1000) {
				year = "0" + year;
			}
			
			
			//Add the formatted date to the date element
			formattedDate.appendChild(year + "-" + month + "-" + day);
			
			//Add the date element to the attendance element
			attendance.appendChild(formattedDate);
		}
		
		//Add the attendance element
		person.appendChild(attendance);
		
		//Add the tasks
		for(int i = 0; i < this.tasks.size(); i++) {
			Element task = this.tasks.get(i).toXMLelement();
			tasks.appendChild(task);
		}
		
		//Add the tasks element
		person.appendChild(tasks);
		
		//Return the person element
		return person;
		
		
	}
	
}

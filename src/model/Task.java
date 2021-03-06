package model;

import java.util.Calendar;
import java.util.GregorianCalendar;

import nu.xom.*;

public class Task {
	
	private String title;
	private String description;
	
	/**
	 * The date by which the task should be done
	 */
	private Date due;
	
	public Task(String title, String description, int yearDue, int monthDue, int dayDue) {
		
		//Set title and description of task
		this.title = title;
		this.description = description;
		
		//Set the day that the task is due
		this.due = new Date(yearDue, monthDue, dayDue);
		
	}
	
	public Task(String title, String description, Date due) {
		
		//Set title and description of task
		this.title = title;
		this.description = description;
		
		//Set the due date
		this.due = due;
		
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDue() {
		return due;
	}

	public void setDue(Date due) {
		this.due = due;
	}
	
	/**
	 * Check to see if the task is due
	 * @return true if the task is due
	 */
	public boolean isDue() {
		
		GregorianCalendar today = new GregorianCalendar();
		
		//If today's year, month, and day is greater than or equal to the year, month, and day (GregorianCalendar month is 0-based)
		if(today.get(Calendar.YEAR) >= due.year() && (today.get(Calendar.MONTH) - 1) >= due.month() && today.get(Calendar.DAY_OF_MONTH) >= due.day()) return true;
		else return false;
		
	}
	
	/**
	 * Convert the task to an XML element
	 * @return the task as an XML element
	 */
	public Element toXMLelement() {
		
		//Create elements
		Element title = new Element("title");
		Element description = new Element("description");
		Element due = new Element("due");
		
		//Add info to title and description elements
		title.appendChild(this.title);
		description.appendChild(this.description);
		
		//Build due element in correct format (YYYY-MM-DD)
		Date date = this.due;
		
		String year = Integer.toString(date.year());
		String month = Integer.toString(date.month());
		String day = Integer.toString(date.day());
		
		if(date.day() < 10) {
			day = "0" + day;
		}
		
		if(date.month() < 10) {
			month = "0" + month;
		}
		
		if(date.year() < 10) {
			year = "000" + year;
		} else if(date.year() < 100) {
			year = "00" + year;
		} else if(date.year() < 1000) {
			year = "0" + year;
		}
		
		//Add the formatted date to the due element
		due.appendChild(year + "-" + month + "-" + day);
		
		//Build the task element and return it
		Element task = new Element("task");
		task.appendChild(title);
		task.appendChild(description);
		task.appendChild(due);
		
		return task;
		
	}

}

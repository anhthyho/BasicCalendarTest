import java.text.SimpleDateFormat;
import java.util.*;

/*
 * HW#2 MyCalendar 
 * author: Anh-Thy Ho 
 */
public class MyCalendar {
	private ArrayList<Event> events;

	/**
	 * creates the calendar and initializes the arraylist
	 */
	public MyCalendar() {
		events = new ArrayList<Event>();
	}

	/**
	 * adds events to the calendar by populating the arraylist 
	 * @param e is the event to be added 
	 * @return 
	 * @throws Exception
	 */
	public boolean addEvent(Event e) throws Exception {

		ArrayList<Event> sameTime = new ArrayList<>();
		for (Event sd : events) {
			if (sd.getDate().equals(e.getDate())) {

				int eStart = e.getStartTime();
				int eEnd = e.getEndTime();
				int sdStart = sd.getStartTime();
				int sdEnd = sd.getEndTime();
				
				int eMin = e.getStartTimeMin();
				int eEMin = e.getEndTimeMin();
				int sdSM = sd.getStartTimeMin();
				int sdEM = sd.getEndTimeMin(); 

				if (eStart < sdEnd && eEnd > sdStart) { // event begins before current ends
					sameTime.add(e);
				} else if (eStart > sdStart && eEnd < sdEnd) { // new event is within old event timeframe
					sameTime.add(e);
				}
				else if (eStart==sdStart || eStart==sdEnd || eEnd==sdStart || sdEnd==eStart ){
					if (sdEM>=eMin && eEMin>=sdSM ){ //if start minutes is more than new ending minutes 06:20 > 06:15
						sameTime.add(e);	
					} else if (eEMin>=sdSM && eMin<=sdEM){
						sameTime.add(e);
					} else if (eMin<=sdSM && eEMin>=sdSM){
						sameTime.add(e);
					}
				}
			}

		}

		if (!sameTime.contains(e)) { // adds what does not have overlapping
										// times, prints added event information
			events.add(e);
			//System.out.println(e.getInfo());
			return true;
		}else {
			System.out.println("Cannot add event, time overlaps with existing event ");
			return false;
		}

	}

	/**
	 * removes the events from a specified date 
	 * @param date is the date that the events are removed from
	 */
	public void removeSpecifiedDate(String date) {
		ArrayList<Event> newEvents = new ArrayList<>(); 
		for (Event e : events) {
			if (!e.getDate().equals(date)) {
				newEvents.add(e);
			}
		}
		events = newEvents;
	}

	/**
	 * removes one specified event from the calendar
	 * @param date is the day to look for specific event
	 * @param event is the string to delete (event name)
	 */
	public void removeSpecifiedEvent(String date, String event) {
		ArrayList<Event> newEvents = new ArrayList<>(); 
		for (Event e : events) {
			if (e.getDate().equals(date) && !e.getEventNameLC().equals(event)) {
				newEvents.add(e);
			}
			else if (!e.getDate().equals(date)){
				newEvents.add(e);
			}
			
		}
		events = newEvents;
	}
	

	/**
	 * gets the arraylist of events in month
	 * @param month is the month to look for events
	 * @return arraylist of events in given month
	 */
	public ArrayList<Event> monthEvents(int month) {
		ArrayList<Event> monthEvents = new ArrayList<>();
		for (Event e : events) {
			if (month == Integer.parseInt(e.getMonth())) {
				monthEvents.add(e);
			}
		}
		return monthEvents;
	}

	/**
	 * prints the events in a day 
	 * @param enteredDate the date to look for events on
	 * @throws Exception
	 */
	public void printDayEvents(String enteredDate) throws Exception {
		ArrayList<Event> current = new ArrayList<>();
		if (enteredDate.substring(1, 2).equals("/")) {
			enteredDate = "0" + enteredDate;
		}

		for (Event e : events) {
			if (e.getDate().equals(enteredDate)) {

				current.add(e);
			}
		}
		
		Collections.sort(current);
		for (Event c : current) {
			System.out.println(c.getEventName() + " " + c.getTimeFrame());
		}
	}

	/**
	 * prints the events in a given month
	 * @param enteredDate is the month to look for events 
	 * @throws Exception
	 */
	public void printMonthEvents(String enteredDate) throws Exception {
		ArrayList<Event> current = new ArrayList<>();
		for (Event e : events) {
			String month = e.getMonth();
			if (month.equals(enteredDate)) {
				current.add(e);
			}
		}
		Collections.sort(current);
		for (Event c : current) {
			System.out.println(c.getInfo());
		}
	}
	
	public boolean contains(Event e){
		if (events.contains(e)){
			return true;
		}
		else {
			return false; 
		}
	}
	/**
	 * prints all the events in the calendar 
	 * @throws Exception
	 */
	public void printAll() throws Exception {
		Collections.sort(events);
		for (Event e : events) {
			System.out.println(e.getInfo());
		}

	}

}

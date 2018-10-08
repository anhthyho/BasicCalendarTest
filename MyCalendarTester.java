import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/*
 * HW#2 MyCalendarTester 
 * author: Anh-Thy Ho 
 */
enum MONTHS {
	Jan, Feb, March, Apr, May, June, July, Aug, Sep, Oct, Nov, Dec;
}

enum DAYS {
	Su, Mo, Tu, We, Th, Fr, Sa;
}

public class MyCalendarTester {
	public static void main(String[] args) throws Exception {

		System.out.println("Select one of the following options: ");
		System.out.println("[L]oad   [V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");
		System.out.println(" ");
		GregorianCalendar cal = new GregorianCalendar(); // capture today
		Scanner sc = new Scanner(System.in);

		MyCalendar mc = new MyCalendar();

		printCalendar(cal);

		File f = new File("events.txt");
		ArrayList<Event> newEvents = new ArrayList<>();

		while (sc.hasNextLine()) {
			System.out.println("Select one of the following options: ");
			System.out.println("[L]oad   [V]iew by  [C]reate, [G]o to [E]vent list [D]elete  [Q]uit");
			System.out.println(" ");
			String input = sc.nextLine();
			input = input.toLowerCase();

			if (input.equals("v")) {
				System.out.println("[D]ay view or [M] view");
				input = sc.next();
				input = input.toLowerCase();
				if (input.equals("d")) {

					int c = GregorianCalendar.getInstance().get(Calendar.DATE);
					String year = new SimpleDateFormat("yyyy").format(new Date());
					String date = cal.get(Calendar.MONTH) + 1 + "/" + c + "/" + year;
					Date date2 = new SimpleDateFormat("MM/dd/yyyy").parse(date);
					DateFormat df = new SimpleDateFormat("EEEE" + " " + "MMMM dd" + ", " + "YYYY");
					String dateString = df.format(date2);
					System.out.println(dateString);
					mc.printDayEvents(date);
					System.out.println(" ");
					System.out.println("[P]revious [N]ext [M]ain menu");
					while (sc.hasNext()) {
						input = sc.next();
						input = input.toLowerCase();

						if (input.equals("p")) {
							c = c - 1;
							date = cal.get(Calendar.MONTH) + 1 + "/" + c + "/" + year;
							date2 = new SimpleDateFormat("MM/dd/yyyy").parse(date);
							df = new SimpleDateFormat("EEEE" + " " + "MMMM dd" + ", " + "YYYY");
							dateString = df.format(date2);
							System.out.println(dateString);
							mc.printDayEvents(date);
						} else if (input.equals("n")) {
							c = c + 1;
							date = cal.get(Calendar.MONTH) + 1 + "/" + c + "/" + year;
							date = cal.get(Calendar.MONTH) + 1 + "/" + c + "/" + year;
							date2 = new SimpleDateFormat("MM/dd/yyyy").parse(date);
							df = new SimpleDateFormat("EEEE" + " " + "MMMM dd" + ", " + "YYYY");
							dateString = df.format(date2);
							System.out.println(dateString);
							mc.printDayEvents(date);

						} else if (input.equals("m")) {
							break;
						}
						System.out.println(" ");
						System.out.println("[P]revious [N]ext [M]ain menu");
					}
				} else if (input.equals("m")) {
					printMonthCalendar(cal, mc);
					String date = cal.get(Calendar.MONTH) + 1 + "";
					mc.printMonthEvents(date);
					System.out.println(" ");
					System.out.println("[P]revious [N]ext [M]ain menu");
					while (sc.hasNext()) {
						input = sc.next();
						input = input.toLowerCase();
						if (input.equals("p")) {
							cal.add(Calendar.MONTH, -1);
							printMonthCalendar(cal, mc);
							date = cal.get(Calendar.MONTH) + 1 + "";
							mc.printMonthEvents(date);
						} else if (input.equals("n")) {
							cal.add(Calendar.MONTH, 1);
							printMonthCalendar(cal, mc);
							date = cal.get(Calendar.MONTH) + 1 + "";
							mc.printMonthEvents(date);
						} else if (input.equals("m")) {
							break;
						}
						System.out.println(" ");
						System.out.println("[P]revious [N]ext [M]ain menu");
					}

				}
			} else if (input.equals("l")) {

				BufferedReader br = new BufferedReader(new FileReader(f));

				ArrayList<Integer> spaces = new ArrayList<>();
				String line;
				while ((line = br.readLine()) != null) {
					for (int i = 0; i < line.length(); i++) {
						if (line.substring(i, i + 1).equals(" ") && spaces.size() < 3) {
							spaces.add(i);
						}
					}
					String date = line.substring(0, spaces.get(0));
					String startTime = line.substring(spaces.get(0) + 1, spaces.get(1));
					String endTime = line.substring(spaces.get(1) + 1, spaces.get(2));
					String eventName = line.substring(spaces.get(2) + 1, line.length());
					Event e = new Event(date, startTime, endTime, eventName);
					if (mc.addEvent(e)) {
						newEvents.add(e);
					}
					// System.out.println(e.getInfo());
				}

				br.close();

			} else if (input.equals("c")) {
				System.out.println("Please enter a date (MM/DD/YYYY): ");
				String date = sc.nextLine();
				System.out.println("Please enter a start time (00:00) : ");
				String start = sc.nextLine();
				System.out.println("Please enter an end time (00:00) : ");
				String end = sc.nextLine();
				System.out.println("Please enter the event name : ");
				String eventName = sc.nextLine();
				Event newEvent = new Event(date, start, end, eventName);
				if (mc.addEvent(newEvent)) {
					newEvents.add(newEvent);
					System.out.println("Event added");

				}

			} else if (input.equals("e")) {
				mc.printAll();
			} else if (input.equals("d")) {
				System.out.println("[S]elected [A]ll [M]ain Menu");
				input = sc.nextLine();
				input = input.toLowerCase();
				if (input.equals("s")) {
					System.out.println("Enter date to delete events: ");
					input = sc.nextLine();
					String date = input;
					System.out.println("Enter event name: ");
					String event = sc.nextLine();
					event = event.toLowerCase();
					mc.removeSpecifiedEvent(date, event);
					System.out.println("Events have been deleted");

				} else if (input.equals("a")) {
					System.out.println("Enter date to delete events: ");
					input = sc.nextLine();
					String date = input;
					mc.removeSpecifiedDate(date);
					System.out.println("Events have been deleted, Remaining Events:");
					mc.printDayEvents(date);
				}

			} else if (input.equals("g")) {
				System.out.println("Enter a date (MM/DD/YYYY) : ");
				input = sc.next();
				String date = input;

				Date date2 = new SimpleDateFormat("MM/dd/yyyy").parse(date);
				DateFormat df = new SimpleDateFormat("EEEE" + "," + "MMMM dd YYYY");
				String dateString = df.format(date2);
				System.out.println(date + " : " + dateString);
				mc.printDayEvents(date);
			} else if (input.equals("q")) {

				BufferedWriter writer = new BufferedWriter(new FileWriter(f));
				for (Event e : newEvents) {
					if (mc.contains(e)) {
						String str = e.getDate() + " " + e.getTimes() + " " + e.getEventName() + "\n";
						writer.write(str);
					}
				}
				writer.close();
				break;
			}

		}
		System.out.println("Bye!");
	}

	public static void printCalendar(Calendar c) {
		MONTHS[] arrayOfMonths = MONTHS.values();
		DAYS[] arrayOfDays = DAYS.values();

		GregorianCalendar temp = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);

		String header = c.get(Calendar.MONTH) + " " + c.get(Calendar.YEAR) + " ";
		int headerCenter = (17 - header.length()) / 2;

		for (int i = 0; i < headerCenter; i++) {
			System.out.print(" ");
		}
		System.out.print(arrayOfMonths[c.get(Calendar.MONTH)] + " " + c.get(Calendar.YEAR));
		System.out.println("");

		for (int i = 0; i < arrayOfDays.length; i++) {
			System.out.print(arrayOfDays[i] + " ");
		}

		c.set(Calendar.DAY_OF_MONTH, 1);
		int space = c.get(Calendar.DAY_OF_WEEK);

		System.out.println("");

		int lastDay = temp.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int j = 0; j < space * 3 - 3; j++) {
			System.out.print(" ");
		}

		Date d = new Date();

		int currentDate = d.getDate();
		int count = space;
		for (int date = 1; date < lastDay + 1; date++) {

			if (count > 7) {
				System.out.println("");
				count = 1;
			}
			if (date < 10) {
				if (date == currentDate && Calendar.MONTH == (c.get(Calendar.MONTH))
						&& Calendar.YEAR == (c.get(Calendar.YEAR))) {
					System.out.print("[" + date + "]");
				} else {
					System.out.print(" " + date + " ");
				}

			} else {
				if (date == currentDate && Calendar.MONTH == (c.get(Calendar.MONTH))) {
					System.out.print("[" + date + "]");
				} else {
					System.out.print(date + " ");
				}
			}
			count++;

		}
		System.out.println("");

	}

	public static void printMonthCalendar(Calendar c, MyCalendar mc) {
		MONTHS[] arrayOfMonths = MONTHS.values();
		DAYS[] arrayOfDays = DAYS.values();

		GregorianCalendar temp = new GregorianCalendar(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);

		String header = c.get(Calendar.MONTH) + " " + c.get(Calendar.YEAR) + " ";
		int headerCenter = (17 - header.length()) / 2;

		for (int i = 0; i < headerCenter; i++) {
			System.out.print(" ");
		}
		System.out.print(arrayOfMonths[c.get(Calendar.MONTH)] + " " + c.get(Calendar.YEAR));
		System.out.println("");

		for (int i = 0; i < arrayOfDays.length; i++) {
			System.out.print(arrayOfDays[i] + " ");
		}

		c.set(Calendar.DAY_OF_MONTH, 1);
		int space = c.get(Calendar.DAY_OF_WEEK);

		System.out.println("");

		int lastDay = temp.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int j = 0; j < space * 3 - 3; j++) {
			System.out.print(" ");
		}
		Date d = new Date();

		int currentDate = d.getDate();
		int count = space;

		ArrayList<Integer> eventDays = new ArrayList<>();
		for (Event e : mc.monthEvents(c.get(Calendar.MONTH) + 1)) {
			eventDays.add(Integer.parseInt(e.getDay()));
		}

		/*
		 * for (Event e : mc.monthEvents(c.get(Calendar.MONTH))) {
		 * eventDays.add(Integer.parseInt(e.getMonth())); }
		 */

		for (int date = 1; date < lastDay + 1; date++) {

			if (count > 7) {
				System.out.println("");
				count = 1;
			}
			if (date < 10) {
				if (eventDays.contains(date)) {
					System.out.print("{" + date + "}");
				} else {
					System.out.print(" " + date + " ");
				}

			} else {
				if (eventDays.contains(date)) {
					System.out.print("{" + date + "}");
				} else {
					System.out.print(date + " ");
				}
			}
			count++;

		}
		System.out.println("");

	}
}
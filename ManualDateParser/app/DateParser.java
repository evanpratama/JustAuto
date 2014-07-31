package app;

import java.util.Hashtable;
import java.util.StringTokenizer;

public class DateParser {

	// Constants
	private final Integer MIN_DATE = new Integer(1);
	private final Hashtable<Integer, Integer> MAX_DATE = new Hashtable<Integer, Integer>();
	private final Integer MIN_YEAR = new Integer(1900);
	private final Integer MAX_YEAR = new Integer(2010);
	private final Integer MIN_MONTH = new Integer(1);
	private final Integer MAX_MONTH = new Integer(12);
	private final Integer FEBRUARY = new Integer(2);
	private final Integer DECEMBER = new Integer(12);
	private final Integer MAX_DATE_FEB_LEAP = new Integer(29);
	private final Integer COMMON_YEAR_DAYS = new Integer(365);
	private final Integer LEAP_YEAR_DAYS = new Integer(366);

	// Error Strings
	private final String INVALID_FORMAT = "Invalid input for \"%s\", Date Format must be in: DD MM YYYY";
	private final String NOT_A_NUMBER = "Invalid input for \"%s\", Input must be in numbers";
	private final String YEAR_OUT_OF_BOUND = "Invalid input for \"%s\", Year value is out of bound, please input between 1900 and 2010";
	private final String MONTH_OUT_OF_BOUND = "Invalid input for \"%s\", Month value is out of bound, please input between 01 and 12";
	private final String DATE_OUT_OF_BOUND = "Invalid input for \"%s\", Date value is out of bound, please input between 1 and %s";

	// Usage Error Strings
	public static final String USAGE = "Usage: java DateParser DD MM YYYY, DD MM YYYY";
	public static final String INVALID_USAGE = "Invalid arguments supplied";
	public static final String START_DATE_GREATER_THAN_END_DATE = "Invalid input, Start Date \"%s\" is greater than End Date \"%s\"";

	// Initialise variables in constructor
	public DateParser() {
		MAX_DATE.put(1, 31);
		MAX_DATE.put(2, 28); // default days for February (Non-leap year) is 28
		MAX_DATE.put(3, 31);
		MAX_DATE.put(4, 30);
		MAX_DATE.put(5, 31);
		MAX_DATE.put(6, 30);
		MAX_DATE.put(7, 31);
		MAX_DATE.put(8, 31);
		MAX_DATE.put(9, 30);
		MAX_DATE.put(10, 31);
		MAX_DATE.put(11, 30);
		MAX_DATE.put(12, 31);
	}

	// Method to check Leap year
	public boolean isLeapYear(Integer year) {
		// algorithm to check leap year
		if ((year.intValue() % 4 == 0 && year.intValue() % 100 != 0)
				|| year.intValue() % 400 == 0)
			return true;
		return false;
	}

	// Method to convert Date String to MyDate Object
	// Make sure it is validated first before using this method
	public MyDate convertToMyDate(String inputDate) {
		// Separate the input by space
		StringTokenizer tokenizer = new StringTokenizer(inputDate, " ");
		return new MyDate(tokenizer.nextToken(), tokenizer.nextToken(),
				tokenizer.nextToken());
	}

	// Method to validate Date String
	public boolean validDateString(String inputDate) {
		// Date format is DD MM YYYY
		// Separate the input by space
		StringTokenizer tokenizer = new StringTokenizer(inputDate, " ");

		// Check Overall Formatting
		if (tokenizer.countTokens() > 3 || tokenizer.countTokens() < 3) {
			System.out.println(String.format(INVALID_FORMAT, inputDate));
			return false;
		}

		Integer date, month, year;
		try {
			// passing tokens to variables for ease of use
			date = Integer.parseInt(tokenizer.nextToken());
			month = Integer.parseInt(tokenizer.nextToken());
			year = Integer.parseInt(tokenizer.nextToken());
		} catch (NumberFormatException e) {
			System.out.println(String.format(NOT_A_NUMBER, inputDate));
			return false;
		}

		// Check Year Value
		if (year > MAX_YEAR || year < MIN_YEAR) {
			System.out.println(String.format(YEAR_OUT_OF_BOUND, inputDate));
			return false;
		}

		// Check Month Value
		if (month > MAX_MONTH || month < MIN_MONTH) {
			System.out.println(String.format(MONTH_OUT_OF_BOUND, inputDate));
			return false;
		}

		// Check Date Value against the month
		if (isLeapYear(year) && month.equals(FEBRUARY)) {
			if (date > MAX_DATE_FEB_LEAP || date < MIN_DATE) {
				System.out.println(String.format(DATE_OUT_OF_BOUND, inputDate,
						MAX_DATE_FEB_LEAP));
				return false;
			}
		} else if (date > MAX_DATE.get(month) || date < MIN_DATE) {
			System.out.println(String.format(DATE_OUT_OF_BOUND, inputDate,
					MAX_DATE.get(month)));
			return false;
		}

		return true;
	}

	// Method to calculate days elapsed from 1st of January to target date
	// within the same year
	// Used for end date that is on a different year compared to the start date
	public Integer newYearToDate(MyDate date) {
		Integer days = new Integer(0);
		for (int i = 1; i < date.getMonth(); i++) {
			if (isLeapYear(date.getYear()) && i == FEBRUARY) {
				days += MAX_DATE_FEB_LEAP;
			} else
				days += MAX_DATE.get(i);
		}
		days += date.getDay();
		return days;
	}

	// Method to calculate days elapsed from target date to 31st of December
	// within the same year
	// Used for start date that is on a different year compared to the end date
	public Integer dateToEndOfYear(MyDate date) {
		Integer days = new Integer(1);
		if (isLeapYear(date.getYear()) && date.getMonth().equals(FEBRUARY)) {
			days += MAX_DATE_FEB_LEAP - date.getDay();
		} else
			days += MAX_DATE.get(date.getMonth()) - date.getDay();
		for (int i = date.getMonth() + 1; i <= DECEMBER; i++) {
			if (isLeapYear(date.getYear()) && i == FEBRUARY) {
				days += MAX_DATE_FEB_LEAP;
			} else
				days += MAX_DATE.get(i);
		}
		return days;
	}

	// Method to date elapsed from target date to 31st of December within the
	// same year
	public Integer interval(MyDate startDate, MyDate endDate) {
		Integer days = new Integer(0);

		if (!startDate.equals(endDate)) {
			if (startDate.compareTo(endDate) < 0) {
				if (!startDate.getYear().equals(endDate.getYear())) {
					for (int i = startDate.getYear() + 1; i <= endDate
							.getYear() - 1; i++) {
						if (isLeapYear(i))
							days += LEAP_YEAR_DAYS;
						else
							days += COMMON_YEAR_DAYS;
					}
					days += dateToEndOfYear(startDate);
					days += newYearToDate(endDate);
				}
				// different month in the same year
				else if (!startDate.getMonth().equals(endDate.getMonth())) {
					for (int i = startDate.getMonth() + 1; i <= endDate
							.getMonth() - 1; i++) {
						if (isLeapYear(startDate.getYear()) && i == FEBRUARY) {
							days += MAX_DATE_FEB_LEAP;
						} else
							days += MAX_DATE.get(i);
					}
					// including the start date in calculation
					days += MAX_DATE.get(startDate.getMonth())
							- startDate.getDay() + 1;
					days += endDate.getDay();
				}
				// the same year and month
				else
					// including the start date in calculation
					days += endDate.getDay() - startDate.getDay() + 1;
			}
			// the start date is greater than the end date
			else
				days += -1;
		}
		return days;
	}

	public static void main(String[] args) {
		DateParser parser = new DateParser();
		if (args.length > 6 || args.length < 6) {
			System.out.println(DateParser.INVALID_USAGE);
			System.out.println(DateParser.USAGE);
			return;
		}

		// Combine all arguments since java treats spaces as a separate argument
		String arguments = "";
		for (String s : args) {
			arguments += " " + s;
		}

		// Check whether input contains comma that splits up 2 dates
		StringTokenizer tokenizer = new StringTokenizer(arguments, ",");
		if (tokenizer.countTokens() > 2 || tokenizer.countTokens() < 2) {
			System.out.println(DateParser.INVALID_USAGE);
			System.out.println(DateParser.USAGE);
			return;
		}

		// passing tokens to variables for ease of use
		String date1 = tokenizer.nextToken().trim();
		String date2 = tokenizer.nextToken().trim();

		// Validate the dates
		if (parser.validDateString(date1) && parser.validDateString(date2)) {
			MyDate startDate = parser.convertToMyDate(date1);
			MyDate endDate = parser.convertToMyDate(date2);
			if (startDate.compareTo(endDate) <= 0) {
				System.out.println(startDate.toString() + ", "
						+ endDate.toString() + ", "
						+ parser.interval(startDate, endDate));
			} else
				System.out.println(String.format(
						DateParser.START_DATE_GREATER_THAN_END_DATE,
						startDate.toString(), endDate.toString()));
		}
	}
}

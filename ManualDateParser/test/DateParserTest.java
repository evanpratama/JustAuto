package test;

import static org.junit.Assert.*;

import org.junit.Test;

import app.DateParser;
import app.MyDate;

public class DateParserTest {
	
	@Test
	public void testIsLeapYear() {
		DateParser parser = new DateParser();
		assertTrue(parser.isLeapYear(2000)); //Leap Year
		assertFalse(parser.isLeapYear(2003)); //Non Leap Year
		assertTrue(parser.isLeapYear(2004)); //Leap Year
		assertFalse(parser.isLeapYear(2100)); //Non Leap Year
	}
	
	@Test
	public void testValidDateString() {
		DateParser parser = new DateParser();
		assertTrue(parser.validDateString("12 01 1987")); //normal input
		assertFalse(parser.validDateString("aa 01 1987")); // broken input with string
		assertFalse(parser.validDateString("12 01 2014")); //year is over the limit
		assertFalse(parser.validDateString("12 15 2014")); //month is over the limit
		assertFalse(parser.validDateString("29 02 2003")); //date is over the limit, leap year
		assertTrue(parser.validDateString("29 02 2004")); //normal input, leap year
		assertFalse(parser.validDateString("01 2014")); //incomplete input
	}
	
	@Test
	public void testJanuaryToDate() {
		DateParser parser = new DateParser();
		assertEquals(new Integer(365), parser.newYearToDate(new MyDate("31", "12", "2003"))); //to 31 December
		assertEquals(new Integer(366), parser.newYearToDate(new MyDate("31", "12", "2004"))); //to 31 December, leap year
		assertEquals(new Integer(12), parser.newYearToDate(new MyDate("12", "01", "2003"))); //to 12 January
		assertEquals(new Integer(72), parser.newYearToDate(new MyDate("12", "03", "2004"))); //to 12 March, leap year
		assertEquals(new Integer(31), parser.newYearToDate(new MyDate("31", "01", "2004"))); //to 31 January
		assertEquals(new Integer(1), parser.newYearToDate(new MyDate("01", "01", "2004"))); //to the 1st of January
	}
	
	@Test
	public void testDateToDecember() {
		DateParser parser = new DateParser();
		assertEquals(new Integer(365), parser.dateToEndOfYear(new MyDate("01", "01", "2003"))); //from 1 January
		assertEquals(new Integer(366), parser.dateToEndOfYear(new MyDate("01", "01", "2004"))); //from 1 January, leap year
		assertEquals(new Integer(334), parser.dateToEndOfYear(new MyDate("01", "02", "2003"))); //from 1 February
		assertEquals(new Integer(335), parser.dateToEndOfYear(new MyDate("01", "02", "2004")));//from 1 February, leap year
		assertEquals(new Integer(1), parser.dateToEndOfYear(new MyDate("31", "12", "2004"))); //From 31 December
		assertEquals(new Integer(31), parser.dateToEndOfYear(new MyDate("01", "12", "2004"))); //From 1 December
		
	}
	
	@Test
	public void testInterval() {
		DateParser parser = new DateParser();
		assertEquals(new Integer(0), parser.interval(new MyDate("01", "01", "2003"), new MyDate("01", "01", "2003"))); //Same Date
		assertEquals(new Integer(-1), parser.interval(new MyDate("01", "01", "2004"), new MyDate("01", "01", "2003"))); //Start Date greater than End Date
		assertEquals(new Integer(31), parser.interval(new MyDate("01", "01", "2003"), new MyDate("31", "01", "2003"))); //Same Month
		assertEquals(new Integer(365), parser.interval(new MyDate("01", "01", "2003"), new MyDate("31", "12", "2003"))); //Within the same year
		assertEquals(new Integer(366), parser.interval(new MyDate("01", "01", "2004"), new MyDate("31", "12", "2004"))); //Within the same year, leap year
		assertEquals(new Integer(730), parser.interval(new MyDate("01", "01", "2002"), new MyDate("31", "12", "2003"))); //Different year
		assertEquals(new Integer(731), parser.interval(new MyDate("01", "01", "2003"), new MyDate("31", "12", "2004"))); //Different year with leap year
	}
	
	@Test
	public void testMain() {
		DateParser.main(new String[]{}); //No Arguments
		DateParser.main(new String[]{"01", "2003,", "01", "01", "2003"}); //Missing argument
		DateParser.main(new String[]{"aa", "01", "2003,", "01", "01", "2003"}); //String input
		DateParser.main(new String[]{"01", "01", "2011,", "01", "01", "2003"}); //Year Out of Bound
		DateParser.main(new String[]{"01", "01", "2003,", "01", "01", "2011"}); //Year Out of Bound
		DateParser.main(new String[]{"01", "01", "1890,", "01", "01", "2003"}); //Year Out of Bound
		DateParser.main(new String[]{"01", "01", "2003,", "01", "01", "1890"}); //Year Out of Bound
		DateParser.main(new String[]{"01", "01", "2005,", "01", "01", "2003"}); //start Date is greater than end date
		DateParser.main(new String[]{"01", "13", "2003,", "01", "01", "2003"}); //Month Out of Bound
		DateParser.main(new String[]{"01", "01", "2003,", "01", "15", "2003"}); //Month Out of Bound
		DateParser.main(new String[]{"32", "01", "2003,", "01", "01", "2003"}); //Day Out of Bound
		DateParser.main(new String[]{"01", "01", "2003,", "31", "04", "2003"}); //Day Out of Bound
		DateParser.main(new String[]{"01", "01", "2003,", "01", "01", "2003"}); //Same Date
		DateParser.main(new String[]{"01", "01", "2003,", "01", "02", "2003"}); //Same Year, different Month
		DateParser.main(new String[]{"01", "01", "2003,", "01", "01", "2004"}); //Same Date, Same month different Year
		DateParser.main(new String[]{"01", "01", "2003,", "01", "01", "2005"}); //Same Date, Same month different Year, overlapping 1 leap year
		DateParser.main(new String[]{"01", "01", "2003,", "01", "01", "2009"}); //Same Date, Same month different Year, overlapping 2 leap year
	}

}
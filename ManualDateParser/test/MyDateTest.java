package test;

import static org.junit.Assert.*;

import org.junit.Test;

import app.MyDate;

public class MyDateTest {

	@Test
	public void testCompareTo() {
		assertEquals(0,new MyDate("31", "12", "2003").compareTo(new MyDate("31", "12", "2003"))); //Current date is equal to the other date
		assertEquals(-1,new MyDate("31", "12", "2003").compareTo(new MyDate("31", "12", "2004"))); //Current date is less than the other date
		assertEquals(1,new MyDate("31", "12", "2003").compareTo(new MyDate("31", "12", "2002"))); //Current date is greater than the other date
	}
	
	@Test
	public void testEquals() {
		assertTrue(new MyDate("31", "12", "2003").equals(new MyDate("31", "12", "2003"))); //The two date is equal
		assertFalse(new MyDate("31", "12", "2003").equals(new MyDate("01", "12", "2003"))); // The two date is not equal
		assertFalse(new MyDate("31", "12", "2003").equals(null)); // The other date is null
		assertFalse(new MyDate("31", "12", "2003").equals(new Integer(0))); // The other object is not a date.
	}
	
	@Test
	public void testToString() {
		assertEquals("31 12 2003",new MyDate("31", "12", "2003").toString()); // Matching output format
	}

}

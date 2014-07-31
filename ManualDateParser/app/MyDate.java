package app;

public class MyDate implements Comparable<MyDate> {

	private Integer day;
	private Integer month;
	private Integer year;
	
	public MyDate(String day, String month, String year)
	{
		this.day = Integer.parseInt(day);
		this.month = Integer.parseInt(month);
		this.year = Integer.parseInt(year);
	}
	
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	@Override
	public String toString(){
		return String.format("%02d", this.day) + String.format(" %02d", this.month) + String.format(" %d", this.year);
	}
	
	@Override
	public boolean equals(Object date){
		if (date == null) {
	        return false;
	    }
	    if (getClass() != date.getClass()) {
	        return false;
	    }
		return this.compareTo((MyDate)date) == 0;
	}

	@Override
	public int compareTo(MyDate date) {
		if(this.year.equals(date.getYear())){
			if(this.month.equals(date.getMonth())){
				if(this.day.equals(date.getDay())){
					return 0;
				}
				else
					return this.day.compareTo(date.getDay());
			}
			else
				return this.month.compareTo(date.getMonth());
		}
		else
			return this.year.compareTo(date.getYear());
	}
}



import org.apache.commons.lang3.builder.EqualsBuilder;

import com.dkt.smax.DaySchedule;
import com.dkt.smax.ExamWeekSchedule;
import com.dkt.smax.Student;
import com.dkt.smax.Subject;
import com.dkt.smax.TimeDuration;
import com.dkt.smax.TimeSlot;
import com.dkt.smax.TimeTable;
import com.dkt.smax.WeekSchedule;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Student creates his own information
		Student student = new Student("Gaurav", 1, 1, 2, "E&EC" );
		//Student creates a timetable
		TimeTable timeTable = new TimeTable();
		//Student creates a study week schedule
		WeekSchedule weekSchedule = new WeekSchedule();
		DaySchedule daySchedule = new DaySchedule();
		daySchedule.addTimeSlot(1, new TimeSlot(1, new Subject()));
		//more timeslots to be added for this day
		
		// add schedule for Monday
		weekSchedule.addDaySchedule(1, daySchedule);
		// add schedule for other days
		timeTable.addWeekSchedule(1, weekSchedule);
		// add week schedule for all the days by cloning them;
		
		//Student specify holiday period
		timeTable.addHoliday(2 /* week */, 5 /* day */);
		timeTable.addHolidays(2 /* start week */, 5 /* start day */, 3 /* end week */, 2 /* end day */);

		//student creates exam week schedule, start and end date
		ExamWeekSchedule examWeekSchedule = new ExamWeekSchedule();
		timeTable.addExamWeekSchedule(3/*week number*/, examWeekSchedule);
		
		if(Main.testTime())
			System.out.println("Test for class Time: Passed");
		else
			System.out.println("Test for class Time: Failed");
	}
	/* All test cases for the classes */

	/* Class: Time */
	public static boolean testTime() {
		TimeDuration tm = new TimeDuration(9, 15);
		TimeDuration tm1 = tm.add(new TimeDuration(0, 180));
//		if(EqualsBuilder.reflectionEquals(tm1, new Time(12,15))) {
		if(tm1.compareTo(new TimeDuration(12,16))) {
			System.out.println("Time: Test: Addition: Passed");
		} else {
			System.out.println("Time: Test: Addition: Failed");
			return false;
		}
		
		TimeDuration tm2 = tm.subtract(new TimeDuration(0, 200));
		if(EqualsBuilder.reflectionEquals(tm2, new TimeDuration(5,55))) {
			System.out.println("Time: Test: Subtraction: Passed");
		} else {
			System.out.println("Time: Test: Subtraction: Failed");
			return false;
		}
		return true;
	}

	/* Class: TimeSlot */
	public static boolean testTimeSlot() {
//		TimeSlot ts = new TimeSlot(new Subject(), new Time(8,0), 40);		
		return false;
	}
}

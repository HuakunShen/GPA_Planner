package project.gpa_calculator.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GPA_CalculatorTest {

    @Before
    public void setUp() throws Exception {
        Year year2018 = new Year("2018");
        Semester fall = new Semester("Fall");
        year2018.addSemester(fall);
        Course course1 = new Course("CSC207", "Software Design", 85d, 0.5);
        fall.addCourse(course1);
        Event event = new Event("midterm", 50d);
        Event event1 = new Event("final", 50d);
        course1.addEvent(event);
        course1.addEvent(event1);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void get_gpa() {
    }

    @Test
    public void calculate_letter_grade() {
        String gpa = GPA_Calculator.calculate_letter_grade(90);
        assertEquals(gpa, "A+");
    }

    @Test
    public void calculate_gpa() {

    }
}
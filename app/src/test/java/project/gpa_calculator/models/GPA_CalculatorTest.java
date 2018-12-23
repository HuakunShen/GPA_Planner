package project.gpa_calculator.models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GPA_CalculatorTest {
    private Year year2018;
    private Semester fall;
    private Course course1;
    private Event event;
    private Event event1;


    @Before
    public void setUp() throws Exception {
        year2018 = new Year("2018");
        fall = new Semester("Fall");
        year2018.addSemester(fall);
        course1 = new Course("CSC207", "Software Design", 85d, 0.5);
        fall.addCourse(course1);
        event = new Event("midterm", 50d);
        event1 = new Event("final", 50d);
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
        double result = GPA_Calculator.calculate_gpa("A+");
        assertEquals(4.0, result, 0.0);
        assertFalse(event.isDone());
        event.setEvent_score(80);
        testCourse();
        testSemester();
        testYear();
    }

    private void testYear() {
//        setup
//        assertEquals(3.03, GPA_Calculator.calculate_gpa(year2018), 0.1);
    }

    private void testSemester() {
        setupAnotherCourse();
        assertEquals(3.2d, GPA_Calculator.calculate_gpa(fall), 0.0);
    }

    private void setupAnotherCourse() {
        Course course2 = new Course("CSC343", "Intro To Database", 80d, 0.5d);
        fall.addCourse(course2);
        assertFalse(fall.isDone());
        Event event3 = new Event("final", 100d);
        course2.addEvent(event3);
        assertEquals(-1d, GPA_Calculator.calculate_gpa(course2), 0.0);
        assertFalse(fall.isDone());
        event3.setEvent_score(70);
        assertTrue(course2.isDone());
        assertEquals(2.7d, GPA_Calculator.calculate_gpa(course2), 0.0);
    }

    private void testCourse() {
        assertFalse(course1.isDone());
        assertEquals(40d, course1.scoreSoFar()[0], 0.0);
        assertEquals(90d, course1.scoreNeededForTarget(), 0.0);
        assertEquals(-1d, GPA_Calculator.calculate_gpa(course1), 0.0);
        event1.setEvent_score(85);
        assertTrue(course1.isDone());
        assertEquals(3.7d, GPA_Calculator.calculate_gpa(course1), 0.0);
    }


}
package project.gpa_calculator.test;

import project.gpa_calculator.models.Course;
import project.gpa_calculator.models.Event;
import project.gpa_calculator.models.Semester;
import project.gpa_calculator.models.Year;

public class Test {
    public static void main(String[] args) {
        Year year = new Year("first year");
        Semester semester = new Semester("fall");
        Course course = new Course("CSC148","what",50,0.5);
        Event A1 = new Event("A1",20);
        Event A2 = new Event("A2",20);
        Event A3 = new Event("A3",20);
        Event finals = new Event("final",40);
        course.addEvent(A1);
        course.addEvent(A2);
        course.addEvent(A3);
        course.addEvent(finals);
        semester.addCourse(course);
        year.addSemester(semester);
        A1.setEvent_score(16);
       Double[] a = course.scoreSoFar();
        System.out.println(a[0]+" "+a[1]+" "+a[2]);
        System.out.println(course.scoreNeededForTarget());

    }
}


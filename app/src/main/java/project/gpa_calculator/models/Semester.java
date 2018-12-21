package project.gpa_calculator.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class Semester implements Serializable, Iterable<Course> {
    private List<Course> course_list;
    private String semester_name;

    public Semester(String semester_name) {
        this.semester_name = semester_name;
        this.course_list = new ArrayList<>();
    }

    public Semester(List<Course> course_list, String semester_name) {
        this.course_list = course_list;
        this.semester_name = semester_name;
    }

    public String getSemester_name() {
        return semester_name;
    }

    public void setSemester_name(String semester_name) {
        this.semester_name = semester_name;
    }



    public boolean addCourse(Course course) {
        if (!courseExists(course.getDepartment() + course.getCourseNumber() + course.getTermCode())) {
            course_list.add(course);
            return true;
        }
        return false;
    }

    public boolean removeCourse(String course_name) {
        for (Course course : course_list) {
            if (course_name.equals(course.getDepartment() + course.getCourseNumber() + course.getTermCode())) {
                course_list.remove(course);
                return true;
            }
        }
        return false;
    }

    List<String> getCourseList() {
        List<String> temp_list = new ArrayList<>();
        for (Course course: this.course_list) {
            temp_list.add(course.getDepartment() + course.getCourseNumber() + course.getTermCode());
        }
        return temp_list;
    }

    @Override
    public String toString() {
        return "\n\tSemester{" +
                "course_list=" + course_list +
                ", \n\tsemester_name='" + semester_name + '\'' +
                "}\n";
    }

    boolean courseExists(String course_name) {
        for (Course course : course_list) {
            if (course_name.equals(course.getDepartment() + course.getCourseNumber() + course.getTermCode()))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<Course> iterator() {
        return new CourseIterator();
    }

    private class CourseIterator implements Iterator<Course> {
        private int next_index;

        @Override
        public boolean hasNext() {
            return next_index < course_list.size();
        }

        @Override
        public Course next() {
            return course_list.get(next_index++);
        }
    }
}

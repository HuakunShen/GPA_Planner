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

    public Course getCourse(String course_name) {
        for (Course course : this.course_list) {
            if (course.getCourse_code().equals(course_name)) {
                return course;
            }
        }
        return null;
    }

    public boolean addCourse(Course course) {
        if (!courseExists(course.getCourse_code())) {
            course_list.add(course);
            return true;
        }
        return false;
    }

    public boolean removeFromCourseList(int position) {
        if (position >= this.course_list.size() || position < 0) {
            return false;
        } else {
            this.course_list.remove(position);
            return true;
        }
    }

    public boolean removeCourse(String course_name) {
        for (Course course : course_list) {
            if (courseExists(course.getCourse_code())) {
                course_list.remove(course);
                return true;
            }
        }
        return false;
    }

    List<String> getCourseList() {
        List<String> temp_list = new ArrayList<>();
        for (Course course: this.course_list) {
            temp_list.add(course.getCourse_code());
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

    boolean courseExists(String course_code) {
        for (Course course : course_list) {
            if (course_code.equals(course.getCourse_code()))
                return true;
        }
        return false;
    }

    public boolean isDone() {
        for (Course course : this.course_list) {
            if (!course.isDone())
                return false;
        }
        return true;
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

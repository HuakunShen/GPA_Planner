package project.gpa_calculator.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Year implements Serializable, Iterable<Semester> {
    private List<Semester> semester_list;


    private List<Course> year_course_list;
    private String year_name;



    public List<Course> getYear_course_list() {
        return year_course_list;
    }

    public void setYear_course_list(List<Course> year_course_list) {
        this.year_course_list = year_course_list;
    }


    @Override
    public String toString() {
        return "Year{" +
                "semester_list=" + semester_list +
                ", \nyear_name='" + year_name + '\'' +
                '}';
    }

    public Year(List<Semester> semester_list, String year_name) {
        this.semester_list = semester_list;
        this.year_name = year_name;
        this.year_course_list = new ArrayList<>();
    }

    public Year(String year_name) {
        this.semester_list = new ArrayList<>();
        this.year_course_list = new ArrayList<>();
        this.year_name = year_name;
    }

    public boolean addCourse(Course course) {
        for (Semester semester : this.semester_list) {
            if (semester.courseExists(course.getCourse_code()))
                return false;
        }
        this.year_course_list.add(course);
        return true;
    }

    public boolean addSemester(Semester semester) {
        if (!semesterExists(semester.getSemester_name())) {
            semester_list.add(semester);
            return true;
        } else {
            return false;
        }
    }

    public String getYear_name() {
        return year_name;
    }

    public void setYear_name(String year_name) {
        this.year_name = year_name;
    }

    boolean semesterExists(String semester_name) {
        for (Semester semester : semester_list) {
            if (semester_name.equals(semester.getSemester_name()))
                return true;
        }
        return false;
    }

    boolean removeSemester(String semester_name) {
        for (Semester semester : semester_list) {
            if (semester_name.equals(semester.getSemester_name())) {
                semester_list.remove(semester);
                return true;
            }
        }

        return false;
    }


    @Override
    public Iterator<Semester> iterator() {
        return new SemesterIterator();
    }

    private class SemesterIterator implements Iterator<Semester> {
        private int next_index = 0;

        @Override
        public boolean hasNext() {
            return next_index < semester_list.size();
        }

        @Override
        public Semester next() {
            return semester_list.get(next_index++);
        }
    }

}

package project.gpa_calculator.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Course implements Serializable, Iterable<Event> {
    private String department;
    private String courseNumber;
    private String termCode;
    private double target;
    private String course_name;
    //    private WeightMap map;
    private List<Event> event_list;

    Course(String department, String courseNumber, String termCode, String name, double target, double credit) {
        this.department = department;
        this.courseNumber = courseNumber;
        this.termCode = termCode;
        this.course_name = name;
        this.target = target;
        event_list = new ArrayList<>();
        this.credit = credit;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    private double credit;

    @Override
    public String toString() {
        return "\n\t\tCourse{" +
                "course='" + department + courseNumber + termCode + '\'' +
                '}';
    }



    boolean addEvent(Event event) {
        for (Event e : event_list) {
            if (e.getEvent_name().equals(event.getEvent_name()))
                return false;
        }
        this.event_list.add(event);
        return true;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public double getTarget() {
        return target;
    }

    public void setTarget(double target) {
        this.target = target;
    }

    /**
     * calculate current score regardless of the events that are not finished yet
     *
     * @return a score user get so far (event if not all the events in the course have been completed
     */
    Double[] scoreSoFar() {
        double score_sum = 0d;
        double weight_sum = 0d;
        double total_weight = 0d;
        Iterator<Event> itr = iterator();

        for (Event event: event_list){
            if (event.isDone()) {
                weight_sum += event.getEvent_weight();
                score_sum += event.getEvent_score() * event.getEvent_weight();
            }
            total_weight += event.getEvent_weight();
        }

        score_sum /= weight_sum;
        return new Double[]{score_sum, weight_sum, total_weight};
    }

    /**
     * Based on event completed so far and their weight, calculate how much in average the user needs to score for the
     * rest of the event in order to reach the target score
     *
     * @return a average score needed in order to reach target score
     */
    double scoreNeededForTarget() {
        Double[] data_so_far = scoreSoFar();
        double score_so_far = data_so_far[0];
        double score_difference = this.target - score_so_far;
        double weight_sum = data_so_far[1];
        double total_weight = data_so_far[2];
        double weight_difference = total_weight - weight_sum;
        return score_difference / weight_difference;
    }

    @Override
    public Iterator<Event> iterator() {
        return new EventIterator();
    }

    private class EventIterator implements Iterator<Event> {
        private int next_index;

        EventIterator() {
        }

        @Override
        public boolean hasNext() {
            return next_index < event_list.size();
        }

        @Override
        public Event next() {
            return event_list.get(next_index++);
        }
    }


}


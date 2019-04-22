package project.gpa_calculator.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//TODO should check if all event add up to 100, warn user otherwise, also handle bonus
public class Course implements Serializable, Iterable<Event> {


    private double target;
    private String course_code;
    private String course_name;

    private String docID;
    private List<Event> event_list;
    private double credit;

    public Course(String course_code, String name, double target, double credit) {
        this.course_code = course_code;
        this.course_name = name;
        this.target = target;
        event_list = new ArrayList<>();
        this.credit = credit;
    }

    public Course() {
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }



    public double getCredit() {
        return credit;
    }
    @Exclude
    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public int size(){
        return event_list.size();
    }

    public int finishSize() {
        int i = 0;
        if(event_list == null){
            return 0;
        }
        for (Event event: this.event_list) {
            if (!event.isDone())
                i++;
        }
        return i;
    }

    @Override
    public String toString() {
        return "\n\t\tCourse{" +
                "course='" + course_code + '\'' +
                '}';
    }



    public boolean addEvent(Event event) {
        for (Event e : event_list) {
            if (e.getEvent_name().equals(event.getEvent_name()))
                return false;
        }
        this.event_list.add(event);
        return true;
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
    public Double[] scoreSoFar() {
        double score_sum = 0d;
        double weight_sum = 0d;
        double total_weight = 0d;
        for (Event event: event_list){
            if (event.isDone()) {
                weight_sum += event.getEvent_weight();
                score_sum += event.getEvent_score();
            }
            total_weight += event.getEvent_weight();
        }

        score_sum *= weight_sum / 100;
        Double[] result = new Double[]{score_sum, weight_sum, total_weight};
        return result;
    }

    /**
     * Based on event completed so far and their weight, calculate how much in average the user needs to score for the
     * rest of the event in order to reach the target score
     *
     * @return a average score needed in order to reach target score in percent
     */
    public double scoreNeededForTarget() {
        Double[] data_so_far = scoreSoFar();
        double score_so_far = data_so_far[0];
        double score_difference = this.target - score_so_far;
        double weight_sum = data_so_far[1];
        double total_weight = data_so_far[2];
        double weight_difference = total_weight - weight_sum;
        return score_difference / weight_difference * 100;
    }

    public boolean isDone() {
        if (this.event_list.isEmpty())
            return false;
        for (Event event: this.event_list) {
            if (!event.isDone())
                return false;
        }
        return true;
    }

    @Override
    public Iterator<Event> iterator() {
        return new EventIterator();
    }

    public boolean removeFromEventList(int position) {
        if (position >= this.event_list.size() || position < 0) {
            return false;
        } else {
            this.event_list.remove(position);
            return true;
        }
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


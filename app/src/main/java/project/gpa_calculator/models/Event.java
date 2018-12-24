package project.gpa_calculator.models;

import java.io.Serializable;

public class Event implements Serializable {
    /**
     * true if is done
     * false if not done yet
     */
    private boolean isDone;
    private String event_name;
    private double event_weight;
    private double event_score;

    public Event(String event_name, double event_weight) {
        this.event_name = event_name;
        this.event_weight = event_weight;
        this.event_score = 0d;
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        this.isDone = done;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public double getEvent_weight() {
        return event_weight;
    }

    public void setEvent_weight(double event_weight) {
        this.event_weight = event_weight;
    }

    public double getEvent_score() {
        return event_score;
    }

    public void setEvent_score(double event_score) {
        this.event_score = event_score;
        setDone(true);
    }


}

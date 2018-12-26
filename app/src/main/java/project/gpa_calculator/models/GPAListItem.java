package project.gpa_calculator.models;

public class GPAListItem extends ListItem {
    private int low, high;
    private double GPA;
    private String GPA_mark;
    public GPAListItem(int low, int high, double GPA, String GPA_mark){
        this.low = low;
        this.high = high;
        this.GPA = GPA;
        this.GPA_mark = GPA_mark;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public void setGPA_mark(String GPA_mark) {
        this.GPA_mark = GPA_mark;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public double getGPA() {
        return GPA;
    }

    public int getHigh() {
        return high;
    }

    public int getLow() {
        return low;
    }

    public String getGPA_mark() {
        return GPA_mark;
    }

    public String getName(){
        return GPA_mark;
    }

    public String getDescription(){
        return "range:"+ Integer.toString(low)+"-"+Integer.toString(high);
    }

    public String getGpa(){
        return Double.toString(GPA);
    }
}

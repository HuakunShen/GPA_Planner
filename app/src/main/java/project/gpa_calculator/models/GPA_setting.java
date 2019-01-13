package project.gpa_calculator.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class GPA_setting implements Iterable<GPA>, Serializable {

    private static GPA_setting instance;
    private String docID;
    private ArrayList<GPA> GPAs = new ArrayList<>();


    @Override
    public Iterator<GPA> iterator() {
        return new Iterator<GPA>() {
            private final Iterator<GPA> GPAI = GPAs.iterator();

            @Override
            public boolean hasNext() {
                return GPAI.hasNext();
            }

            @Override
            public GPA next() {
                return GPAI.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("no changes allowed");
            }
        };

    }



    /**
     * basic setting that match University of Toronto GPA
     */
    private GPA_setting(){
        GPAs.add(new GPA(90,100,4.0,"A+"));
        GPAs.add(new GPA(85,89,4.0,"A"));
        GPAs.add(new GPA(80,84,3.7,"A-"));
        GPAs.add(new GPA(77,79,3.3,"B+"));
        GPAs.add(new GPA(73,76,3.0,"B"));
        GPAs.add(new GPA(70,72,2.7,"B-"));
        GPAs.add(new GPA(67,69,2.3,"C+"));
        GPAs.add(new GPA(63,66,2.0,"C"));
        GPAs.add(new GPA(60,62,1.7,"C-"));
        GPAs.add(new GPA(57,59,1.3,"D+"));
        GPAs.add(new GPA(53,56,1.0,"D"));
        GPAs.add(new GPA(50,52,0.7,"D-"));
        GPAs.add(new GPA(0,49,0.0,"F"));

    }

    public static GPA_setting getInstance() {
        if(instance == null){
            instance = new GPA_setting();
        }
        return instance;
    }

    public ArrayList<GPA> getGPAs() {
        return GPAs;
    }

    public void setGPAs(ArrayList<GPA> GPAs) {
        this.GPAs = GPAs;
    }

    public void add(GPA new_gpa){
        GPAs.add(new_gpa);
    }
    public void remove(int pos){
        GPAs.remove(pos);
    }
    public void update(int pos,GPA new_gpa){
        GPAs.set(pos,new_gpa);
    }
    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getDocID() {
        return docID;
    }
}

package project.gpa_calculator.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class GPA_setting implements Iterable<GPA>, Serializable {
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
    public GPA_setting(){
        GPAs.add(new GPA(100,90,4.0,"A+"));
        GPAs.add(new GPA(89,85,4.0,"A"));
        GPAs.add(new GPA(84,80,3.7,"A-"));
        GPAs.add(new GPA(79,77,3.3,"B+"));
        GPAs.add(new GPA(76,73,3.0,"B"));
        GPAs.add(new GPA(72,70,2.7,"B-"));
        GPAs.add(new GPA(69,67,2.3,"C+"));
        GPAs.add(new GPA(66,63,2.0,"C"));
        GPAs.add(new GPA(62,60,1.7,"C-"));
        GPAs.add(new GPA(59,57,1.3,"D+"));
        GPAs.add(new GPA(56,53,1.0,"D"));
        GPAs.add(new GPA(52,50,0.7,"D-"));
        GPAs.add(new GPA(49,0,0.0,"F"));

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
}

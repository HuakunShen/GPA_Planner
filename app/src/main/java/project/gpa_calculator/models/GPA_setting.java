package project.gpa_calculator.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class GPA_setting implements Iterable<GPA>, Serializable {
    ArrayList<GPA> GPAs = new ArrayList<>();

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

    public GPA_setting(){
        GPAs.add(new GPA(100,90,4.0,"A+"));
        GPAs.add(new GPA(89,85,4.0,"A"));
        GPAs.add(new GPA(84,80,3.7,"A-"));
        GPAs.add(new GPA(79,77,3.3,"B+"));
    }

    public void add(GPA new_gpa){
        GPAs.add(new_gpa);
    }
}

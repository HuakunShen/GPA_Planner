package project.gpa_calculator.models;

public class ListItem {
    private String name;
    private String description;


    private String gpa;


    public ListItem(String name, String description, String gpa) {
        this.name = name;
        this.description = description;
        this.gpa = gpa;
    }

    public String getGpa() {
        return gpa;
    }

    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

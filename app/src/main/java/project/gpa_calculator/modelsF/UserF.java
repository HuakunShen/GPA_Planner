package project.gpa_calculator.modelsF;

import com.google.firebase.database.Exclude;

public class UserF {
    private String username;
    private String uID;
    private String student_number;
    private String school;
    private String email;


    public UserF() {
    }

    public UserF(String username, String uID, String email) {
        this.username = username;
        this.uID = uID;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

}

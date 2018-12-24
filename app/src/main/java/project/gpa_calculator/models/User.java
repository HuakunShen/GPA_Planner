package project.gpa_calculator.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String username;
    private String nickname;
    private String password;
    private String student_number;
    private String school;
    private String student_type;
    private List<Year> year_list;

    public User(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname.equals("") ? username : nickname;
        this.password = password;
        year_list = new ArrayList<>();
    }

    public boolean addYear(Year new_year) {
        for (Year year : this.year_list) {
            if (year.getYear_name().equals(new_year.getYear_name()))
                return false;
        }
        this.year_list.add(new_year);
        return true;
    }

    public boolean removeYear(String year_name) {
        for (int i = 0; i < this.year_list.size(); i++) {
            if (this.year_list.get(i).getYear_name().equals(year_name)) {
                this.year_list.remove(i);
                return true;
            }
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }


    //TODO this does not change password, it's still the old password
    public boolean setPassword(String old_password, String new_password) {
        if (this.password.equals(old_password)) {
            this.password = new_password;
            return true;
        } else {
            return false;
        }
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

    public String getStudent_type() {
        return student_type;
    }

    public void setStudent_type(String student_type) {
        this.student_type = student_type;
    }


}

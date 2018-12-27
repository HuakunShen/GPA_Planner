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
    private GPA_setting gpa_setting;
    private List<Year> year_list;

    public User(String username, String nickname, String password) {
        this.username = username;
        this.nickname = nickname.equals("") ? username : nickname;
        this.password = password;
        year_list = new ArrayList<>();
        gpa_setting = new GPA_setting();
    }

    public Year getYear(String year_name) {
        for (Year year : this.year_list) {
            if (year.getYear_name().equals(year_name))
                return year;
        }
        return null;
    }

    public boolean removeFromYearList(int position) {
        if (position >= this.year_list.size() || position < 0) {
            return false;
        } else {
            this.year_list.remove(position);
            return true;
        }
    }

    public List<Year> getYear_list() {
        return year_list;
    }

    public void setYear_list(List<Year> year_list) {
        this.year_list = year_list;
    }

    public boolean addYear(Year new_year) {
        for (Year year : this.year_list) {
            if (year.getYear_name().equals(new_year.getYear_name()))
                return false;
        }
        this.year_list.add(new_year);
        return true;
    }

    public void addGPA(int low, int high,double gpa,String mark){
        GPA new_gpa = new GPA(high,low,gpa,mark);
        gpa_setting.add(new_gpa);
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

    public GPA_setting getGpa_setting() {
        return gpa_setting;
    }

    public void removeFromGPA(int pos){
        gpa_setting.remove(pos);
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

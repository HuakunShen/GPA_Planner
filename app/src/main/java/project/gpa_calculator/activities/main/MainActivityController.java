package project.gpa_calculator.activities.main;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import project.gpa_calculator.models.User;

public class MainActivityController {

    private User user;


    public MainActivityController() {
        user = new User("admin", "admin", "admin");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }




}

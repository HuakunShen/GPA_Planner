# Android App - GPA Planner

## Still in Progress

### Progress so far

* Login and sign up with email or Google
* Add Year, Semester, Course, Event Objects to views (recycler view), display of these items work perfectly
* GPA Setter (can set customize GPA Schema for students from any school)

### Todo

* Add Slide Bar for every event in courses as Simulator
* Add GPA Calculating Functionalities
* Make a Web App Version of this Android App, share the same Database (reference `todoist`)

### Bugs

* Google Sign In Fail to work due to updates

## Description
* Designed to keep track of students' score in every course and provide the plan for them
* For a single course:
  * The app asks for a target GPA, and based on what the student got so far, the app provides information of how good he/she has to be (at least) in order to eventually reach the target score.
* For A semester:
  * Given a target score, the App could tell how good a student has to be in each course in average in order to reach the target
* For A year:
  * Given a target score, the App could tell how good a student has to be in each semester in average in order to reach the target
* For Each course, semester and year, the App also provides a 
* Data of this app is stored in Firebase Firestore (may change to Firebase Database in the future)
* With Firebase supported authentication

![1565247672914](./readme_img/v1_login)
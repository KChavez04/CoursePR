//Written by Kasandra Chavez, except where otherwise stated (at the bottom)

package edu.tntech.csc2310;


import com.google.gson.stream.JsonReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;


public class CourseCatalog {
    private ArrayList<Course> db = null;
    private String subject;
    private String catalogYear;     //catalogYear is term


    /**
     * Caches a copy of the course catalog, if one does not already exist. Calls Course() to create each course in the requested catalog.
     * Otherwise, open the previous version of the file.
     *
     * @param subject - Holds the course's subject code (like CSC or MATH).
     * @param catalogYear - Holds the catalog year (integer).
     * @throws CatalogNotFoundException - Throws an error when a catalog can not be found or created.
     */
    public CourseCatalog(String subject, String catalogYear) throws CatalogNotFoundException {
        subject = subject.trim().toUpperCase();
        catalogYear = catalogYear.trim();
        this.subject = subject;
        this.catalogYear = catalogYear;
        this.db = new ArrayList();


        try {
            // First time you run this, it creates the file
            File file = new File("src/main/resources/" + subject + "_" + catalogYear + ".json");
            Gson gson = new Gson();

            if(!file.exists()) {
                FileWriter out = new FileWriter(file);

                ArrayList<String> courseList = getCourseNumbers(subject, catalogYear);

                if(courseList.size() > 0) {
                    file.createNewFile();


                    try {
                        for (int i = 0; i < courseList.size(); i++) {
                            Course course = new Course(subject, courseList.get(i), catalogYear);
                            db.add(course);
                        }
                    } catch (CourseNotFoundException e) {
                        e.printStackTrace();
                    }

                        String jsonString = gson.toJson(db);

                        out.write(jsonString);
                        out.close();


                } else { // Subject or Catalog Year Not Found
                            this.subject = null;
                            this.catalogYear = null;
                            this.db = null;
                            file.delete();
                            throw new CatalogNotFoundException(this.subject + " from " + this.catalogYear + " was not found");
                        }

            } else {
                FileReader in = new FileReader(file);
                JsonReader jr = new JsonReader(in);
                Course[] courses = gson.fromJson(jr, Course[].class);

                if (courses.length < 1) {
                    throw new CatalogNotFoundException(subject + "is empty");
                } else {
                    for (Course c : courses) {
                        this.db.add(c);
                    }
                }

                in.close();

            }

        } catch(IOException ex){

        }

    }


    /**
     * @return db - Returns the list of courses.
     */
    public ArrayList<Course> getCourses(){
        return db;
    }


    /**
     * Confirms this course number is a match. / Retrieves the course by the requested course number.
     *
     * @param number - Holds the number of the course (such as the 1300 in CSC 1300).
     * @return result - This return the requested course.
     */
    public Course getCourse(String number) {
        Course c = null;

        //iterate through the entire database
        for (int i = 0; i < db.size(); i++) {
            c = db.get(i);
            String confirmedNumber = c.getNumber();

            if (number.equals(confirmedNumber)) {
                return db.get(i);
            }
        }

        return null;

    }


    /**
     * This gets the course numbers in a catalog by subject code and catalog year.
     *
     * @param subject - This holds the course's subject code (such as CSC from CSC 1300).
     * @param catalogYear - This holds the requested catalog year.
     * @return list - Returns the list of course numbers.
     */
    public static ArrayList<String> getCourseNumbers(String subject, String catalogYear){
        ArrayList<String> courseNumbers = new ArrayList();

        try {
            //JSOUP - this part gathers the catalog details
            Document doc = Jsoup.connect("https://ttuss1.tntech.edu/PROD/bwckctlg.p_display_courses?sel_crse_strt=1000&sel_crse_end=4999&sel_subj=&sel_levl=&sel_schd=&sel_coll=&sel_divs=&sel_dept=&sel_attr=&term_in=" + catalogYear + "&one_subj=" + subject).get();

            //NTTITLE SECTION - pulls only the nttitle tagged data which we need to find the course's title
            Elements courseNames = doc.select(".nttitle");

            //separates the courses into an array
            String[] tempCourseNames;
            tempCourseNames = courseNames.text().split(subject + " ");

            //clean up string elements to only the course number, call Course() to build each course, and pass to db
            for (int i = 1; i < tempCourseNames.length; i++) {
                if (tempCourseNames[i].length() > 2) {
//                    tempCourseNames[i] = tempCourseNames[i].substring(0, 4);
                    courseNumbers.add(tempCourseNames[i].substring(0, 4));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return courseNumbers;
    }


    /**
     * @return subject - Returns the course subject code selected. Removes the white space (trim) and set to capital letters.
     */
    public String getSubject() {
        return subject.toUpperCase().trim();
    }


    /**
     * @return catalogYear - Returns the catalog year selected. It is trimmed to remove the white space.
     */
    public String getCatalogYear() {
        return catalogYear.trim();
    }

}

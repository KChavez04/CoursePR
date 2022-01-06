//Written by Kasandra Chavez, except where otherwise stated (at the bottom)

package edu.tntech.csc2310;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import java.lang.String;


public class Course {
    private String subject;
    private String number;      //class number, like 1300 from CSC1300
    private String title;
    private String description;
    private String[] prerequisites;
    private int credits;

    /**
     * This method creates a course. It does this by pulling TTU's course catalog information through Jsoup.
     * It then calls subsequent functions to flatten the data.
     *
     * @param subject - Holds the subject code for a course.
     *      * @param number - Holds the course number.
     *      * @param term - Holds the catalog term (integers).
     *      * @throws CourseNotFoundException - Throws an error when a course is unable to be built.
     */
    public Course(String subject, String number, String term) throws CourseNotFoundException {
        subject = subject.trim().toUpperCase();
        number = number.trim();
        this.subject = subject;
        this.number = number;


        try {
            //JSOUP - this part gathers the course details
            Document doc = Jsoup.connect("https://ttuss1.tntech.edu/PROD/bwckctlg.p_disp_course_detail?cat_term_in=" + term + "&subj_code_in=" + subject + "&crse_numb_in=" + number).get();


            //NTTITLE SECTION - pulls only the nttitle tagged data which we need to find the course's title
            Elements courseNames = doc.select(".nttitle");

            if(courseNames.size() > 0) {
                setTitle(courseNames);

                //NTDEFAULT SECTION - pulls only the ntdefault tagged data which we need to find the description, credit hours, and prerequisites
                Elements courseDetails = doc.select(".ntdefault");

                setDescription(courseDetails);
                setCredits(courseDetails);
                setPrerequisites(courseDetails);

            } else{
                throw new CourseNotFoundException(subject + " " + number + " does not exist");
            }

        } catch(IOException e){
            e.printStackTrace();
        }

    }


    /**
     * @return subject, number, title, description - Returns the listed variables, separated by spaces.
     */
    public String toString(){
        return getSubject() + " " + getNumber() + " " + getTitle() + " \n" + getDescription() + " \n" + getPrerequisites() + " \n" + credits;
    }


    /**
     * @return subject - Returns the course's subject code.
     */
    public String getSubject(){
        return this.subject.trim().toUpperCase();
    }


    /**
     * @return number - Returns the course number.
     */
    public String getNumber(){
        return this.number.trim();
    }


    /**
     * Flattens the pulled data to only the title.
     *
     * @param courseNames - The .nttitle tagged data that has yet to be flattened.
     */
    private void setTitle(Elements courseNames){
        //TITLE
        //find the length of the substring needed to get the title
        int titleSubstringLength = 4;
        titleSubstringLength += subject.length();
        titleSubstringLength += number.length();

        //find the course's title and assign it to the variable
        if((titleSubstringLength - subject.length()) > 5) {
            for (int i = 0; i < courseNames.size(); i++) {
                String names = courseNames.get(i).text();
                this.title = names.trim().substring(titleSubstringLength);    //instead of 11
            }
        }
        else{
            this.title = null;
        }

    }


    /**
     * @return title - Returns the course's title.
     */
    public String getTitle(){
        return this.title;
    }


    /**
     * Flattens the pulled data to only the course's description.
     *
     * @param courseDetails - The .ntdefault tagged data that has yet to be flattened.
     */
    private void setDescription(Elements courseDetails){
        //DESCRIPTION
        //find the index of "Credit hours"
        int descriptionIndex = 0;
        descriptionIndex += courseDetails.toString().indexOf(".000");

        //clean up string to only return description
        if(descriptionIndex > 0) {
            String courseDescription = null;
            courseDescription = courseDetails.toString().trim().substring(23, descriptionIndex - 6);
            this.description = courseDescription;
        }
        else{
            this.description = null;
        }

    }


    /**
     * @return description - Returns a course's description.
     */
    public String getDescription(){
        return description;
    }


    /**
     * Flattens the .ntdefault tagged data into just the course's credit hours.
     *
     * @param courseDetails - The .ntdefault tagged data that has yet to be flattened.
     */
    private void setCredits(Elements courseDetails){
        //CREDIT HOURS
        //find the index of "Credit hours"
        int creditHoursIndex = -6;
        creditHoursIndex += courseDetails.toString().indexOf("Credit hours");

        //clean up string to only return credit hours
        if(creditHoursIndex > 0) {
            String courseCreditHours = null;
            courseCreditHours = courseDetails.toString().trim().substring(creditHoursIndex, creditHoursIndex + 1);

            //convert the credit hours to an int type
            try {
                credits = Integer.parseInt(courseCreditHours);
            } catch(NumberFormatException e){
                System.out.println("Number format error.");
            }
        }
        else{
            this.credits = 0;
        }



    }


    /**
     * @return credits - Returns the number of credit hours for a course.
     */
    public int getCredits(){
        return credits;
    }


    /**
     * Flattens the .ntdefault tagged data into just a course's prerequisites.
     *
     * @param courseDetails - The .ntdefault tagged data that has yet to be flattened.
     */
    private void setPrerequisites(Elements courseDetails){
        //PREREQUISITES (received lots of help from TA here)
        //find the index in the text that starts prerequisite list
        int generalRequirementsIndex = 0;
        generalRequirementsIndex += courseDetails.text().lastIndexOf("General Requirements:");

        //if there are no prerequisites
        if(generalRequirementsIndex <= 0){
            this.prerequisites = null;
        }

        //otherwise, (if there are prerequisites)
        else {
            //convert courseDetails to remove HTML
            String courseDetailsText = courseDetails.text().substring(generalRequirementsIndex+22);

            //pieces we do not want in the array
            String[] toRemove = {"Course or Test:", "Minimum Grade of C", "Minimum Grade of D", "May not be taken concurrently.", "May be taken concurrently.", "(", ")", "Return to Previous", "New Search"};
            for (int i = 0; i < toRemove.length; i++) {
                courseDetailsText = courseDetailsText.replace(toRemove[i], "");
            }

            //split up prerequisites - this section was given by the TA
            courseDetailsText = courseDetailsText.trim();
            courseDetailsText = courseDetailsText.replace("and", ",");
            courseDetailsText = courseDetailsText.replace("or", ",");

            //assign the array to this.prerequisites
            this.prerequisites = courseDetailsText.trim().split(",");
        }

    }


    /**
     * @return prerequisites - Returns a course's prerequisites.
     */
    public String[] getPrerequisites(){
        return prerequisites;
    }


    /**
     * Prints the message listed in msg and vals.
     *
     * @param msg - Holds the message.
     * @param vals - Holds the value attached to the message.
     */
    //the following method was written by Jerry Gannod
    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, vals));
    }
}

//Written by Kasandra Chavez
package edu.tntech.csc2310;

public class CourseNotFoundException extends Exception {
    private String message;


    /**
     * Promotes to a super class.
     */
    public CourseNotFoundException() {
        super();
    }


    /**
     * Promotes to a super class.
     *
     * @param message - Holds the string passed in which
     */
    public CourseNotFoundException(String message){
        super(message);
        this.message = "Course has not been found: " + message;
    }


    /**
     * @return message - This returns the message.
     */
    @Override
    public String getMessage(){
        return this.message;
    }


    /**
     * @return getMessage() - This returns an error and calls getMessage().
     */
    @Override
    public String toString(){
        return "Course Error: " + this.getMessage();
    }

}

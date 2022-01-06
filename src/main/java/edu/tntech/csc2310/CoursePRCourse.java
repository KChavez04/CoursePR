//Written by Jerry Gannod, comments written by Kasandra Chavez
package edu.tntech.csc2310;


public class CoursePRCourse {

    private final long id;

    private final Course attribute;

    /**
     * Sets in the values for ID and the course attribute.
     *
     * @param id - Holds the identifying integer (long).
     * @param course - Holds the attribute for a course.
     */
    public CoursePRCourse(long id, Course course) {
        this.id = id;
        this.attribute = course;
    }

    /**
     * @return attribute - Returns the attribute for a course.
     */
    public Course getAttribute(){
        return this.attribute;
    }

    /**
     * @return id - Returns the ID.
     */
    public long getId() {
        return id;
    }

}

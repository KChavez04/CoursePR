//Written by Jerry Gannod, comments written by Kasandra Chavez
package edu.tntech.csc2310;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class CoursePRCourseService {

    private final AtomicLong counter = new AtomicLong();

    /**
     * Builds a course catalog course by course.
     *
     * @param subject - Holds the subject of a course (such as CSC or MATH).
     * @param number - Holds the number of a course (such as the 1300 in CSC1300).
     * @return cps - Returns the CoursePRCourse object that holds the courses.
     */
    @GetMapping("/courses")
    public CoursePRCourse courses(@RequestParam(value = "subject") String subject, @RequestParam(value = "number") String number) {
        CourseCatalog c = null;
        try {
            c = new CourseCatalog(subject, "202180");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Course course = c.getCourse(number);
        CoursePRCourse cps = new CoursePRCourse(counter.incrementAndGet(), course);
        return cps;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CoursePRCourseService.class.getName());

}

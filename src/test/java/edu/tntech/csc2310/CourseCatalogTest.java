//written by Jerry Gannod except where otherwise specified (at the bottom)
package edu.tntech.csc2310;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CourseCatalogTest {

    public static CourseCatalog catalog;

    @BeforeClass
    public static void setUp() throws Exception {
        CourseCatalogTest.catalog = new CourseCatalog("CSC", "202180");
    }

    @Test
    public void getCourse() {
        Course c = CourseCatalogTest.catalog.getCourse("2770");
        assertEquals("Course test", "Intro to Systems & Networking", c.getTitle());
        assertEquals("Course test credits", 3, c.getCredits());
    }

    @Test
    public void getCourseNonExistent() {
        Course c1 = CourseCatalogTest.catalog.getCourse("2001");
        assertNull(c1);
    }


    @Test
    public void badSubject() {
        CourseCatalog c = null;
        try {
            c = new CourseCatalog("Missing", "202180");
        } catch (Exception e) {
            e.printStackTrace();
            assertTrue("Bad subject passed", true);
        }
    }

    @Test
    public void trimSubject() throws CatalogNotFoundException {
        CourseCatalog c = new CourseCatalog(" CSC ", "202180");
        assertEquals("subject trim", "CSC", c.getSubject());
    }

    @Test
    public void toUpperSubject() throws CatalogNotFoundException {
        CourseCatalog c = new CourseCatalog("math", "202180");
        assertEquals("subject lowercase", "MATH", c.getSubject());
    }


    @Test
    public void getCourseNumbers() {
        ArrayList list = CourseCatalog.getCourseNumbers("CSC", "202180");
        assertEquals("CourseNumber test", 62, list.size());
        list = CourseCatalog.getCourseNumbers("COMM", "202180");
        assertEquals("CourseNumber test", 37, list.size());
    }

    @Test
    public void getCatalogYear() {
        assertEquals("Catalog Year", "202180", CourseCatalogTest.catalog.getCatalogYear());
    }

    @Test
    public void getSubject() {
        assertEquals("Subject", "CSC", CourseCatalogTest.catalog.getSubject());
    }


    //written by Kasandra Chavez
    @Test
    public void getFalseCourse() throws NullPointerException{
        Course currentCourse = null;

        try{
            currentCourse = catalog.getCourse("0001");
            assertEquals("False getSubject() test", "CSC", currentCourse.getSubject());

            currentCourse = catalog.getCourse("0001");
            assertEquals("False getNumber() test", "1300", currentCourse.getNumber());

            currentCourse = catalog.getCourse("0001");
            assertEquals("False getTitle() test", "Intro/Prob Solving-Comp Prog", currentCourse.getTitle());

            currentCourse = catalog.getCourse("0001");
            assertEquals("False getDescription() test", "Prerequisite: CSC 1200 or", currentCourse.getDescription());

            currentCourse = catalog.getCourse("0001");
            assertEquals("False getPrerequisites() test", "CSC 1200", currentCourse.getPrerequisites());

        } catch(NullPointerException e){
            assertNull(currentCourse);
        }

    }

}
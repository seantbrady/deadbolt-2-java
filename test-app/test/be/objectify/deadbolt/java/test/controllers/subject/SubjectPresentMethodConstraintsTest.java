package be.objectify.deadbolt.java.test.controllers.subject;

import be.objectify.deadbolt.java.test.DataLoader;
import com.google.common.collect.ImmutableMap;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static play.test.Helpers.*;

public class SubjectPresentMethodConstraintsTest
{

    private static final int PORT = 3333;

    @Before
    public void setUp()
    {
        RestAssured.port = PORT;
    }

    @Test
    public void testSubjectMustBePresent_noSubjectIsPresent()
    {
        running(testServer(PORT,
                           fakeApplication(new ImmutableMap.Builder<String, String>().put("deadbolt.java.handlers.defaultHandler",
                                                                                          "be.objectify.deadbolt.java.test.security.TestDeadboltHandler")
                                                                                     .build())),
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        RestAssured.given()
                                   .cookie("user", "greet")
                                   .expect()
                                   .statusCode(401)
                                   .when()
                                   .get("/subject/present/m/subjectMustBePresent");
                    }
                });
    }

    @Test
    public void testSubjectMustBePresent_subjectIsPresent()
    {
        running(testServer(PORT,
                           fakeApplication(new ImmutableMap.Builder<String, String>().put("deadbolt.java.handlers.defaultHandler",
                                                                                          "be.objectify.deadbolt.java.test.security.TestDeadboltHandler")
                                                                                     .build(),
                                           new DataLoader("/be/objectify/deadbolt/java/test/standard.xml"))),
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        RestAssured.given()
                                   .cookie("user", "greet")
                                   .expect()
                                   .statusCode(200)
                                   .when()
                                   .get("/subject/present/m/subjectMustBePresent");
                    }
                });
    }

    @Test
    public void testSubjectMustBePresent_noSubjectIsPresent_controllerExplicitlyUnrestricted()
    {
        running(testServer(PORT,
                           fakeApplication(new ImmutableMap.Builder<String, String>().put("deadbolt.java.handlers.defaultHandler",
                                                                                          "be.objectify.deadbolt.java.test.security.TestDeadboltHandler")
                                                                                     .build())),
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        RestAssured.given()
                                   .cookie("user", "greet")
                                   .expect()
                                   .statusCode(401)
                                   .when()
                                   .get("/subject/present/m/subjectMustBePresentInUnrestrictedController");
                    }
                });
    }

    @Test
    public void testSubjectMustBePresent_subjectIsPresent_controllerExplicitlyUnrestricted()
    {
        running(testServer(PORT,
                           fakeApplication(new ImmutableMap.Builder<String, String>().put("deadbolt.java.handlers.defaultHandler",
                                                                                          "be.objectify.deadbolt.java.test.security.TestDeadboltHandler")
                                                                                     .build(),
                                           new DataLoader("/be/objectify/deadbolt/java/test/standard.xml"))),
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        RestAssured.given()
                                   .cookie("user", "greet")
                                   .expect()
                                   .statusCode(200)
                                   .when()
                                   .get("/subject/present/m/subjectMustBePresentInUnrestrictedController");
                    }
                });
    }
}

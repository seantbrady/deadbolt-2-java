package be.objectify.deadbolt.java.test.controllers.dynamic;

import be.objectify.deadbolt.java.test.DataLoader;
import com.google.common.collect.ImmutableMap;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

public class DynamicControllerConstraintsTest
{

    private static final int PORT = 3333;

    @Before
    public void setUp()
    {
        RestAssured.port = PORT;
    }

    @Test
    public void testProtectedByControllerLevelDynamic_noSubjectIsPresent()
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
                                   .get("/dynamic/c/niceName");
                    }
                });
    }

    @Test
    public void testProtectedByControllerLevelDynamic_subjectHasUnauthorisedUserName()
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
                                   .cookie("user", "steve")
                                   .expect()
                                   .statusCode(401)
                                   .when()
                                   .get("/dynamic/c/niceName");
                    }
                });
    }


    @Test
    public void testProtectedByControllerLevelDynamic_subjectHasAuthorisedUserName()
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
                                   .get("/dynamic/c/niceName");
                    }
                });
    }

    @Test
    public void testUnrestricted_noSubjectIsPresent()
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
                                   .statusCode(200)
                                   .when()
                                   .get("/dynamic/c/niceName/open");
                    }
                });
    }

    @Test
    public void testUnrestricted_subjectHasUnauthorisedUserName()
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
                                   .cookie("user", "steve")
                                   .expect()
                                   .statusCode(200)
                                   .when()
                                   .get("/dynamic/c/niceName/open");
                    }
                });
    }


    @Test
    public void testUnrestricted_subjectHasAuthorisedUserName()
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
                                   .get("/dynamic/c/niceName/open");
                    }
                });
    }
}

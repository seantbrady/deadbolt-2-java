package be.objectify.deadbolt.java.test.controllers.pattern;

import be.objectify.deadbolt.java.test.DataLoader;
import com.google.common.collect.ImmutableMap;
import com.jayway.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

public class EqualityControllerConstraintsTest
{

    private static final int PORT = 3333;

    @Before
    public void setUp()
    {
        RestAssured.port = PORT;
    }

    @Test
    public void testProtectedByControllerLevelEquality_noSubjectIsPresent()
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
                                   .get("/pattern/equality/c/checkEquality");
                    }
                });
    }

    @Test
    public void testProtectedByControllerLevelEquality_subjectDoesNotHavePermission()
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
                                   .cookie("user", "lotte")
                                   .expect()
                                   .statusCode(401)
                                   .when()
                                   .get("/pattern/equality/c/checkEquality");
                    }
                });
    }


    @Test
    public void testProtectedByControllerLevelEquality_subjectHasPermission()
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
                                   .get("/pattern/equality/c/checkEquality");
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
                                   .get("/pattern/equality/c/checkEquality/open");
                    }
                });
    }

    @Test
    public void testUnrestricted_subjectHasPermission()
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
                                   .get("/pattern/equality/c/checkEquality/open");
                    }
                });
    }


    @Test
    public void testUnrestricted_subjectDoesNotHavePermission()
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
                                   .get("/pattern/equality/c/checkEquality/open");
                    }
                });
    }
}

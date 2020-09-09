package main;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.testng.Assert.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DependencyClassTest {
    private TimePoint timepoint;
    private int durationPos;
    private Dependency dependency1;
    private Dependency dependency2;

    @BeforeAll
    public void setUp(){
        // Nominal case: non null TimePoint, positive duration
        timepoint = new TimePoint();
        durationPos = 10;
        dependency1 = new Dependency(timepoint, durationPos);

        // Nominal case: non null TimePoint, no duration
        dependency2 = new Dependency(timepoint);
    }

    @Test
    public void constructorTest(){
        // Error case: null TimePoint
        try {
            new Dependency(null);
            fail("Created new dependency despite TimePoint being null");
        }
        catch(AssertionError error){
            assertTrue(true, "Assert did not catch null TimePoint");
        }

        // Error case: negative duration
        try {
            new Dependency(timepoint, -10);
            fail("Created new dependency despite duration being negative");
        }
        catch(AssertionError error){
            assertTrue(true, "Assert did not catch negative duration");
        }
    }

    @Test
    public void getPrevious(){
        assertEquals(timepoint, dependency1.getPrevious(), "TimePoints do not match");
        assertEquals(timepoint, dependency2.getPrevious(), "TimePoints do not match");
    }

    @Test
    public void getDuration(){
        assertEquals(durationPos, dependency1.getDuraction(), "Durations do not match");
        assertEquals(0, dependency2.getDuraction(), "Durations do not match");
    }

}
package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class TimePointTest {
    private TimePoint timepoint1;
    private TimePoint timepoint2;

    @BeforeEach
    void setUp(){
        timepoint1 = new TimePoint();
        timepoint2 = new TimePoint();
    }

    @Test
    void previewTimePointsTest() {
        Set<TimePoint> expectedTimepoints = new HashSet<>();

        expectedTimepoints.add(timepoint1);
        expectedTimepoints.add(timepoint2);

        TimePoint timepoint3 = new TimePoint();
        timepoint3.addPrevious(timepoint1);
        timepoint3.addPrevious(timepoint2);

        Set<TimePoint> actualTimepoints = timepoint3.previewTimePoints();
        Iterator expectedTpIter = expectedTimepoints.iterator();
        Iterator actualTpIter = actualTimepoints.iterator();

        while (actualTpIter.hasNext()){
            assertEquals(expectedTpIter.next(),actualTpIter.next(), "Timepoints not returned correctly");
        }
    }

    @Test
    void addPreviousTest(){
        // Nominal case
        assertTrue(timepoint2.addPrevious(timepoint1), "Did not add TimePoint successfully");

        // Error case: null TimePoint
        try {
            timepoint2.addPrevious(null);
            fail("Added TimePoint despite being null");
        }
        catch(AssertionError error){
            assertTrue(true, "Assert did not catch null TimePoint");
        }

        // Error case: negative duration
        int duration = -10;
        try {
            timepoint2.addPrevious(timepoint1, duration);
            fail("Added TimePoint despite having a negative duration");
        }
        catch(AssertionError error){
            assertTrue(true, "Assert did not catch negative duration");
        }

        // Error case: frozen TimePoint
        TimePoint timepoint3 = new TimePoint();
        timepoint3.freeze();
        try {
            timepoint2.addPrevious(timepoint3);
            fail("Added TimePoint despite being frozen");
        }
        catch(AssertionError error){
            assertTrue(true, "Assert did not catch frozen TimePoint");
        }
    }

    // Test for inDegree() and isIndependent()
    @Test
    void isIndependentTest(){
        assertTrue(timepoint2.isIndependent(), "TimePoint should be independent");
        assertEquals(timepoint2.inDegree(), 0, "Number of dependencies should be 0");
        timepoint2.addPrevious(timepoint1);
        assertFalse(timepoint2.isIndependent(), "TimePoint should not be independent");
        assertEquals(timepoint2.inDegree(), 1, "Number of dependencies should be 1");
    }

    // Test for freeze() and isFrozen()
    @Test
    void freezeTest() {
        assertFalse(timepoint1.isFrozen(), "TimePoint should not be frozen");
        timepoint1.freeze();
        assertTrue(timepoint1.isFrozen(), "TimePoint should be frozen");
    }
}
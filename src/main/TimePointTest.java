package main;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TimePointTest {
    private Activity activity1;
    private Activity activity2;
    private TimePoint timepoint1;
    private TimePoint timepoint2;
    private TimePoint.Side side1;
    private TimePoint.Side side2;


    @Before
    public void setUp() throws  SchedulerException{
        long duration = ThreadLocalRandom.current().nextLong(100);
        ActivityGroup activities = new ActivityGroup();
        activity1 = Activity.create(duration, activities, null);
        activity2 = Activity.create(duration, activities, null);
        side1 = TimePoint.Side.BEGIN;
        side2 = TimePoint.Side.END;
        timepoint1 = new TimePoint(activity1, side1);
        timepoint2 = new TimePoint(activity2, side2);
    }

    @Test
    public void previewTimePointsTest() {
        Set<TimePoint> expectedTimepoints = new HashSet<>();

        expectedTimepoints.add(timepoint1);
        expectedTimepoints.add(timepoint2);

        TimePoint timepoint3 = new TimePoint(activity1, side2);
        timepoint3.addPrevious(timepoint1);
        timepoint3.addPrevious(timepoint2);

        Set<TimePoint> actualTimepoints = timepoint3.previousTimePoints();
        Iterator expectedTpIter = expectedTimepoints.iterator();
        Iterator actualTpIter = actualTimepoints.iterator();

        while (actualTpIter.hasNext()){
            Assert.assertEquals("Timepoints not returned correctly", expectedTpIter.next(),actualTpIter.next());
        }
    }

    @Test
    public void addPreviousNominal(){
        // Nominal case
        Assert.assertTrue("Did not add TimePoint successfully", timepoint2.addPrevious(timepoint1));
    }

    @Test(expected = AssertionError.class)
    public void addPreviousNull(){
        // Error case: negative duration
        timepoint2.addPrevious(null);
    }

    @Test(expected = AssertionError.class)
    public void addPreviousNeg(){
        // Error case: negative duration
        timepoint2.addPrevious(timepoint1, -10L);
    }

    @Test(expected = AssertionError.class)
    public void addPreviousFrozen(){
        // Error case: frozen TimePoint
        timepoint2.freeze();
        timepoint2.addPrevious(timepoint2);
    }

    // Test for inDegree() and isIndependent()
    @Test
    public void isIndependentTest(){
        Assert.assertTrue("TimePoint should be independent", timepoint2.isIndependent());
        Assert.assertEquals("Number of dependencies should be 0", timepoint2.inDegree(), 0);
        timepoint2.addPrevious(timepoint1);
        Assert.assertFalse("TimePoint should not be independent", timepoint2.isIndependent());
        Assert.assertEquals("Number of dependencies should be 1", timepoint2.inDegree(), 1);
    }

    // Test for freeze() and isFrozen()
    @Test
    public void freezeTest() {
        Assert.assertFalse("TimePoint should not be frozen", timepoint1.isFrozen());
        timepoint1.freeze();
        Assert.assertTrue("TimePoint should be frozen", timepoint1.isFrozen());
    }

    // Test for getActivity() and getDescription()
    @Test
    public void activityTest(){
        Assert.assertEquals("Activity is incorrect", activity1, timepoint1.getActivity());
        Assert.assertEquals("Activity is incorrect", activity2, timepoint2.getActivity());

        Assert.assertEquals("Description is incorrect", timepoint1.getActivity().getDescription() + ":" + timepoint1.getSide().name(), timepoint1.getDescription());
        Assert.assertEquals("Description is incorrect", timepoint2.getActivity().getDescription() + ":" + timepoint2.getSide().name(), timepoint2.getDescription());
    }

    // Test for getSide()
    @Test
    public void getSideTest(){
        Assert.assertEquals("Side is incorrect", side1, timepoint1.getSide());
        Assert.assertEquals("Side is incorrect", side2, timepoint2.getSide());
    }
}
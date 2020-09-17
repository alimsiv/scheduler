package main;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.InvalidParameterException;
import java.util.concurrent.ThreadLocalRandom;


public class ActivityTest {
    long duration;
    ActivityGroup activities;
    String description;
    Activity activity;
    TimePoint beginTimePoint;
    TimePoint endTimePoint;

    @Before
    public void setUp() throws SchedulerException{
        duration = ThreadLocalRandom.current().nextLong(100);
        activities = new ActivityGroup();
        description = "this is an activity";
        activity = Activity.create(duration, activities, description);

        beginTimePoint = new TimePoint(activity, TimePoint.Side.BEGIN);
        endTimePoint = new TimePoint(activity, TimePoint.Side.END);
        endTimePoint.addPrevious(beginTimePoint,duration);
    }

    @Test(expected = SchedulerException.class)
    public void createNegDurationTest() throws SchedulerException{
        // Error case: negative duration
        duration *= (-1);
        activity = Activity.create(duration, activities, description);
    }

    @Test(expected = InvalidParameterException.class)
    public void createNullActivitiesTest() throws SchedulerException{
        // Error case: null ActivityGroup
        activities = null;
        activity = Activity.create(duration, activities, description);
    }

    //Test for addDependency(), extremePoint() and dependencies()
    @Test
    public void addDependencyNominalTest() {
        try {
            TimePoint.Side side = TimePoint.Side.BEGIN;
            Assert.assertTrue("Activity should not have any dependencies", activity.dependencies(side).isEmpty());

            String description2 = "this is another activity";
            long duration2 = ThreadLocalRandom.current().nextLong(100);
            Activity activity2 = Activity.create(duration2, activities, description2);

            TimePoint otherTimePoint = new TimePoint(activity2, side);
            activity.addDependency(side, otherTimePoint);

            Assert.assertFalse("Activity should have dependencies", activity.dependencies(side).isEmpty());
        } catch (Exception e) {
            Assert.fail("Threw exception: " + e.toString());
        }
    }

    @Test(expected = InvalidParameterException.class)
    public void addDependencyNullTimePointTest() throws SchedulerException{
        TimePoint.Side side = TimePoint.Side.BEGIN;
        Assert.assertTrue("Activity should not have any dependencies", activity.dependencies(side).isEmpty());

        TimePoint otherTimePoint = null;
        activity.addDependency(side, otherTimePoint);
    }

    @Test(expected = SchedulerException.class)
    public void addDependencyFrozenTest() throws SchedulerException{
        TimePoint.Side side = TimePoint.Side.BEGIN;
        Assert.assertTrue("Activity should not have any dependencies", activity.dependencies(side).isEmpty());

        String description2 = "this is another activity";
        long duration2 = ThreadLocalRandom.current().nextLong(100);
        Activity activity2 = Activity.create(duration2, activities, description2);
        TimePoint otherTimePoint = new TimePoint(activity2, side);

        activity.freeze();
        activity.addDependency(side, otherTimePoint);
    }

    //Tests for freeze() and isFrozen()
    @Test
    public void frozenTest() {
        Assert.assertFalse("Begin TimePoint should not be frozen", activity.extremePoint(TimePoint.Side.BEGIN).isFrozen());
        Assert.assertFalse("End TimePoint should not be frozen", activity.extremePoint(TimePoint.Side.END).isFrozen());
        activity.freeze();
        Assert.assertTrue("Begin TimePoint should be frozen", activity.extremePoint(TimePoint.Side.BEGIN).isFrozen());
        Assert.assertTrue("End TimePoint should be frozen", activity.extremePoint(TimePoint.Side.END).isFrozen());
    }

    @Test
    public void getActivitiesTest() {
        Assert.assertEquals("ActivityGroup is not returned corrected", activities, activity.getActivities());
    }

    @Test
    public void getDurationTest() {
        Assert.assertEquals("Duration is not returned corrected", duration, activity.getDuration());
    }

    @Test
    public void getDescriptionTest() {
        Assert.assertEquals("Description is not returned corrected", description, activity.getDescription());
    }
}
package main;

import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class ActivityGroupTest {
    Collection<TimePoint> timePointList = new ArrayList<>();

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
        beginTimePoint.freeze();
        endTimePoint.freeze();
    }

    @Test
    public void addTimePointsNominalTest() {
        timePointList.add(beginTimePoint);
        activities.addTimePoints(timePointList);
    }

    @Test(expected = AssertionError.class)
    public void addTimePointsNullCollectionTest() {
        activities.addTimePoints(null);
    }

    @Test(expected = AssertionError.class)
    public void addTimePointsNullTimePointTest() {
        timePointList.add(null);
        activities.addTimePoints(timePointList);
    }
}
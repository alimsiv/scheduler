package main;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

import java.util.concurrent.ThreadLocalRandom;

public class DependencyClassTest {
    private static TimePoint timepoint;
    private static long duration;
    private static Dependency dependency1;
    private static Dependency dependency2;

    @BeforeClass
    public static void setUp() throws Exception{
        duration = ThreadLocalRandom.current().nextLong(100);
        ActivityGroup activities = new ActivityGroup();
        Activity activity = Activity.create(duration, activities, null);
        TimePoint.Side side = TimePoint.Side.BEGIN;
        timepoint = new TimePoint(activity, side);

        // Nominal case: non null TimePoint, positive duration
        dependency1 = new Dependency(timepoint, duration);

        // Nominal case: non null TimePoint, no duration
        dependency2 = new Dependency(timepoint);
    }

    @Test(expected = AssertionError.class)
    public void constructorTest(){
        // Error case: null TimePoint
        new Dependency(null);
    }

    @Test
    public void getPrevious(){
        Assert.assertEquals("TimePoints do not match", timepoint, dependency1.getPrevious());
        Assert.assertEquals("TimePoints do not match", timepoint, dependency2.getPrevious());
    }

    @Test
    public void getDuration(){
        Assert.assertEquals("Durations do not match", duration, dependency1.getDuration());
        Assert.assertEquals("Durations do not match", 0, dependency2.getDuration());
    }

}
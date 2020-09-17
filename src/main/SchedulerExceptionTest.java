package main;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class SchedulerExceptionTest {
    private static SchedulerException.Builder exception;
    private static TimePoint timepoint1;
    private static TimePoint timepoint2;
    private static SchedulerException.Error error;

    @BeforeClass
    public static void setUp() throws SchedulerException{
        long duration = ThreadLocalRandom.current().nextLong(100);
        ActivityGroup activities = new ActivityGroup();
        Activity activity1 = Activity.create(duration, activities, null);
        Activity activity2 = Activity.create(duration, activities, null);
        timepoint1 = new TimePoint(activity1, TimePoint.Side.BEGIN);
        timepoint2 = new TimePoint(activity2, TimePoint.Side.END);


        SchedulerException.Error[] errors = SchedulerException.Error.values();
        int randomError = ThreadLocalRandom.current().nextInt(errors.length);
        error = errors[randomError];
        exception = new SchedulerException.Builder(error).setTimePoint(timepoint1).setOtherTimePoint(timepoint2);
        exception.build();
    }

    @Test
    public void getError() {
        assertEquals("Error returned does not match", error, exception.getError());
    }

    @Test
    public void getTimePoint() {
        assertEquals("TimePoint returned does not match", timepoint1, exception.getTimePoint());
    }

    @Test
    public void getOtherTimePoint() {
        assertEquals("TimePoint returned does not match", timepoint2, exception.getOtherTimePoint());
    }
}
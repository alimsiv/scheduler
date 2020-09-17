package main;

import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String args[]) throws Exception{
        long duration1 = ThreadLocalRandom.current().nextLong(100);
        long duration2 = ThreadLocalRandom.current().nextLong(100);
        ActivityGroup activities = new ActivityGroup();
        Activity activity1 = Activity.create(duration1, activities, "This is activity1");
        Activity activity2 = Activity.create(duration2, activities, "This is activity2");
        TimePoint.Side side1 = TimePoint.Side.BEGIN;
        TimePoint.Side side2 = TimePoint.Side.END;


        TimePoint t1 = new TimePoint(activity1, side1);
        TimePoint t2 = new TimePoint(activity2, side2);
        t2.addPrevious(t1);

        TimePoint t3 = new TimePoint(activity1, side2);
        TimePoint t4 = new TimePoint(activity2, side1);

        t4.addPrevious(t2, 2);
        t4.addPrevious(t3, 3);

        /*
                    t4
                   /  \
                  /    \
                 t2     t3
                /
               /
              t1
         */

        System.out.println(t2.toString());
    }
}
package main;

import java.util.ArrayList;
import java.util.Collection;

public final class ActivityGroup {
    private Collection<TimePoint> timePointList = new ArrayList<TimePoint>();

    final void addTimePoints(Collection<TimePoint> timePoints){
        assert (timePoints != null) : "timePoints is null";
        for (TimePoint t: timePoints) {
            assert (t != null) : "TimePoint is null";
            assert (t.isFrozen()) : new SchedulerException.Builder(SchedulerException.Error.POINT_NOT_FROZEN)
                    .setTimePoint(t)
                    .build();
            assert (!timePointList.contains(t)) : new SchedulerException.Builder(SchedulerException.Error.TIME_POINT_EXISTS)
                    .setTimePoint(t)
                    .build();
            timePointList.add(t);
        }

    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String output = System.identityHashCode(this) + "\nTimePoints of Activity Group: ";
        if (timePointList.isEmpty())
            return output + "\nnone";

        for (TimePoint t: timePointList){
            output = sb.append("\n").append(t.toSimpleString()).toString();
        }
        return output;
    }


}

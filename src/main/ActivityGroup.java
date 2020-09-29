package main;

import java.util.*;

public final class ActivityGroup {
    private Set<TimePoint> timePointList = new HashSet<>();

    public final List<TimePoint> sort(){
        // orders all the time points in the group
        return TimePointSorter.sort(timePointList);
    }

    // UPDATE - new method
    public static final LinkedHashMap<TimePoint, Long> timeline(List<TimePoint> sortedPoints){
        // takes a sorted list of time points and assigns them to their timeline

        LinkedHashMap<TimePoint, Long> timeline = new LinkedHashMap<>();

        // todo -  time point t cannot find the completion time of a predecessor.
        //information should include a SchedulerException for CIRCULAR_DEPENDENCY with the predecessor and the dependency duration.

        return timeline;
    }

    // todo - use varargs?
    final void addTimePoints(Collection<TimePoint> timePoints){
        assert (Objects.nonNull(timePoints)) : "timePoints is null";
        for (TimePoint t: timePoints) {
            assert (Objects.nonNull(t)) : "TimePoint is null";
            assert (t.isFrozen()) : buildException(SchedulerException.Error.POINT_NOT_FROZEN, t);
            assert (!timePointList.contains(t)) : buildException(SchedulerException.Error.TIME_POINT_EXISTS, t);
            timePointList.add(t);
        }
    }

    private SchedulerException buildException(SchedulerException.Error error, TimePoint t){
        return new SchedulerException.Builder(error)
                .setTimePoint(t)
                .build();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String output = sb.append("\n").append(System.identityHashCode(this))
                    .append("\nTimePoints of Activity Group: ").toString();
        if (timePointList.isEmpty())
            return sb.append("\n\tnone").toString();

        for (TimePoint t: timePointList){
            output = sb.append("\n\t").append(t.toSimpleString()).toString();
        }
        return output;
    }


    //todo: check out @Rule for testing
}

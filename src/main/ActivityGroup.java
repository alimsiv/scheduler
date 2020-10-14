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
        SchedulerException.assertNonNull(sortedPoints);
        LinkedHashMap<TimePoint, Long> timelineMap = new LinkedHashMap<>();

        for (TimePoint timePoint : sortedPoints){
            // TODO - remove isIndependent
            if (timePoint.isIndependent()){
                timelineMap.put(timePoint, 0L);
            }
            else {
                timelineMap.put(timePoint, maxStartTime(timePoint, timelineMap));
            }
        }
        return timelineMap;
    }

    // UPDATE - new helper method
    private static long maxStartTime(TimePoint timePoint, LinkedHashMap<TimePoint, Long> timelineMap){
        long maxTime = 0L;
        for (Dependency dependency : timePoint.getDependencies()){
            TimePoint predecessor = dependency.getPrevious();
            long duration = dependency.getDuration();
            if (!timelineMap.containsKey(predecessor)){
                throw new IllegalArgumentException("Circular dependency", SchedulerException.buildSchedulerException(SchedulerException.Error.CIRCULAR_DEPENDENCY, predecessor, duration).build());
            }
            maxTime = Math.max(maxTime, timelineMap.get(predecessor) + duration);
        }
        return maxTime;
    }

    final void addTimePoints(Collection<TimePoint> timePoints){
        SchedulerException.assertNonNull(timePoints);
        for (TimePoint timePoint: timePoints) {
            SchedulerException.assertNonNull(timePoint);
            assert (timePoint.isFrozen()) : buildSchedulerException(SchedulerException.Error.POINT_NOT_FROZEN, timePoint);
            assert (!timePointList.contains(timePoint)) : buildSchedulerException(SchedulerException.Error.TIME_POINT_EXISTS, timePoint);
            timePointList.add(timePoint);
        }
    }

    private SchedulerException buildSchedulerException(SchedulerException.Error error, TimePoint t){
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
}

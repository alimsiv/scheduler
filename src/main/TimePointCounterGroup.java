package main;

import java.util.*;

final class TimePointCounterGroup {
    private Map<TimePoint, TimePointCounter> counters;
    private List<TimePoint> independentTimePoints;

    private TimePointCounterGroup(Map<TimePoint, TimePointCounter> counters, List<TimePoint> independentTimePoints){
        this.counters = counters;
        this.independentTimePoints = independentTimePoints;
    }

    static final TimePointCounterGroup create(Set<TimePoint> timePoints){
        // returns a new TimePointCounterGroup, whose counters are initialized to the indegrees of the corresponding TimePoint
        SchedulerException.assertNonNull(timePoints);
        Map<TimePoint, TimePointCounter> newCounters = new HashMap<>();
        List<TimePoint> newIndependentTimePoints = new ArrayList<>();
        for (TimePoint timePoint : timePoints){
            SchedulerException.assertNonNull(timePoint);
            if (timePoint.isIndependent()){
                newIndependentTimePoints.add(timePoint);
            }
            else {
                newCounters.put(timePoint, new TimePointCounter(timePoint.inDegree()));
            }
        }
        return new TimePointCounterGroup(newCounters, newIndependentTimePoints);
    }

    final boolean isAnyIndependent(){
        return !independentTimePoints.isEmpty();
    }

    final TimePoint removeIndependent(){
        assert (isAnyIndependent()) : "There are no independent time points.";
        return independentTimePoints.remove(0);
    }

    final void decrementCounters(Set<TimePoint> timePoints){
        // decrements all the counters corresponding to the given time points and,
        // if any counter reaches zero, inserts it in the independent list
        SchedulerException.assertNonNull(timePoints);
        for (TimePoint timePoint : timePoints){
            SchedulerException.assertNonNull(timePoint);
            assert (counters.containsKey(timePoint)) : "Time point is not know to TimePointCounterGroup";
            assert (!timePoint.isIndependent()) : "Time point is already independent";
            if (counters.get(timePoint).decrement() == 0){
                independentTimePoints.add(timePoint);
                counters.remove(timePoint);
            }
            // Else - the time point is still dependent and should stay in the counters map
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        return sb.append("TimePointCounterGroup: ").append(System.identityHashCode(this))
                .append("\n\tCounters: ").append(counters.toString())
                .append("\n\tIndependent TimePoints: ").append(independentTimePoints.toString().indent(8)).toString();
    }
}

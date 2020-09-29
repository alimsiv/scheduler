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
        assert (Objects.nonNull(timePoints)) : "Time points are null";
        Map<TimePoint, TimePointCounter> newCounters = new HashMap<>();
        List<TimePoint> newIndependentTimePoints = new ArrayList<>();
        for (TimePoint t : timePoints){
            assert (Objects.nonNull(t)) : "Time point is null";
            if (t.isIndependent()){
                newIndependentTimePoints.add(t);
            }
            else {
                newCounters.put(t, new TimePointCounter(t.inDegree()));
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
        assert (Objects.nonNull(timePoints)) : "Time points are null";
        for (TimePoint t : timePoints){
            assert (Objects.nonNull(t)) : "Time point is null";
            assert (counters.containsKey(t)) : "Time point is not know to TimePointCounterGroup";
            assert (!t.isIndependent()) : "Time point is already independent";
            if (counters.get(t).decrement() == 0){
                //todo - one line?
                independentTimePoints.add(t);
                counters.remove(t);
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

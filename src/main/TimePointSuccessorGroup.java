package main;

import java.util.*;

final class TimePointSuccessorGroup {
    private final Map<TimePoint, Set<TimePoint>> successors;

    private TimePointSuccessorGroup(Map<TimePoint, Set<TimePoint>> successors){
        this.successors = successors;
    }

    final Set<TimePoint> getSuccessors(TimePoint timePoint){
        SchedulerException.assertNonNull(timePoint);
        assert (successors.containsKey(timePoint)) : "Time Point is not in map";
        return successors.get(timePoint);
    }

    // UPDATE - reimplemented method
    static final TimePointSuccessorGroup create(Set<TimePoint> timePoints){
        // sets the successors by running through the previous time points of the arguments.
        SchedulerException.assertNonNull(timePoints);
        Map<TimePoint, Set<TimePoint>> successors = new HashMap<>();
        for (TimePoint timePoint : timePoints){
            SchedulerException.assertNonNull(timePoint);
            successors.put(timePoint, successorsOfTimePoint(timePoint, timePoints));
        }
        return new TimePointSuccessorGroup(successors);
    }

    // UPDATE - new helper method
    private static Set<TimePoint> successorsOfTimePoint(TimePoint timePoint, Set<TimePoint> timePoints){
        Set<TimePoint> successorsOfTimePoint = new HashSet<>();
        for (TimePoint potentialSuccessor : timePoints){
            if (potentialSuccessor.previousTimePoints().contains(timePoint)){
                successorsOfTimePoint.add(potentialSuccessor);
            }
            // Else, this potential successor is not a successor of this TimePoint
        }
        return successorsOfTimePoint;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        return sb.append("TimePointSuccessorGroup: ").append(System.identityHashCode(this))
                .append("\n").append(successors.toString().indent(4)).toString();
    }
}

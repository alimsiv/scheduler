package main;

import java.util.*;

final class TimePointSuccessorGroup {
    private final Map<TimePoint, Set<TimePoint>> successors;

    private TimePointSuccessorGroup(Map<TimePoint, Set<TimePoint>> successors){
        this.successors = successors;
    }

    final Set<TimePoint> getSuccessors(TimePoint timePoint){
        assert (Objects.nonNull(timePoint)) : "Time point is null";
        return successors.get(timePoint);
    }


    // UPDATE - reimplemented method
    static final TimePointSuccessorGroup create(Set<TimePoint> timePoints){
        // sets the successors by running through the previous time points of the arguments.
        assert (Objects.nonNull(timePoints)) : "Time points are null";
        Map<TimePoint, Set<TimePoint>> successors = new HashMap<>();
        for (TimePoint timePoint : timePoints){
            assert (Objects.nonNull(timePoint)) : "Time point is null";
            successors.put(timePoint, findSuccessorsOfTimePoint(timePoint, timePoints));
        }
        return new TimePointSuccessorGroup(successors);
    }

    static final Set<TimePoint> findSuccessorsOfTimePoint(TimePoint timePoint, Set<TimePoint> timePoints){
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

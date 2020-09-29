package main;

import java.sql.Time;
import java.util.*;

final class TimePointSorter {

    static final List<TimePoint> sort(Set<TimePoint> timePoints){
        // returns a list of time points so that each time point can only depend
        // on the previous time points in the list
        assert (Objects.nonNull(timePoints)) : "Set of time points is null";

        List<TimePoint> sorted = new ArrayList<>();

        TimePointSuccessorGroup successorGroup = TimePointSuccessorGroup.create(timePoints);
        TimePointCounterGroup counterGroup = TimePointCounterGroup.create(timePoints);

        while (counterGroup.isAnyIndependent()){
            TimePoint current = counterGroup.removeIndependent();
            assert (Objects.nonNull(current)) : "Time point is null";
            assert (current.isFrozen()) : "Time point is not frozen";

            // remove current from the list of independents and append it to the sorted list
            sorted.add(current);

            // decrement the counters all of successors of current and, upon reaching zero, add the successor to the independents
            // todo - May give you null errors depending on how you construct successors.
            counterGroup.decrementCounters(successorGroup.getSuccessors(current));
        }
        assert (sorted.size() >= timePoints.size()) : "Error in dependencies";
        return sorted;
    }

    // no toString method
}

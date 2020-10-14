package main;

import java.util.*;

final class TimePointSorter {

    static final List<TimePoint> sort(Set<TimePoint> timePoints){
        // returns a list of time points so that each time point can only depend
        // on the previous time points in the list
        SchedulerException.assertNonNull(timePoints);

        List<TimePoint> sorted = new ArrayList<>();

        TimePointSuccessorGroup successorGroup = TimePointSuccessorGroup.create(timePoints);
        TimePointCounterGroup counterGroup = TimePointCounterGroup.create(timePoints);

        while (counterGroup.isAnyIndependent()){
            TimePoint current = counterGroup.removeIndependent();
            SchedulerException.assertNonNull(current);
            assert (current.isFrozen()) : "Time point is not frozen";

            // remove current from the list of independents and append it to the sorted list
            sorted.add(current);

            // decrement the counters all of successors of current and, upon reaching zero, add the successor to the independents
            Set<TimePoint> sucessors = successorGroup.getSuccessors(current);
            if (Objects.nonNull(sucessors)) {
                counterGroup.decrementCounters(sucessors);
            }
            // Else, there are no sucessors, no need to decrement counters
        }
        assert (sorted.size() >= timePoints.size()) : "Error in dependencies";
        return sorted;
    }

    // no toString method
}

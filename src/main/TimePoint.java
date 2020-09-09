package main;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class TimePoint {
    private final Set<Dependency> dependencies = new HashSet<>();
    private boolean frozen = false; // regulates whether dependencies can still be added to the time point

    // Returns all previous time points due to dependences
    public final Set<TimePoint> previewTimePoints(){
        return dependencies.stream().map(i -> i.getPrevious()).collect(Collectors.toSet());
    }

    // Adds dependency to this time point
    final boolean addPrevious(TimePoint previousTimePoint, long duration){
        return (addPrecondition(previousTimePoint,duration));
    }

    // Adds dependency of zero duration
    final boolean addPrevious(TimePoint previousTimePoint){
        return (addPrecondition(previousTimePoint,0));
    }

    private boolean addPrecondition(TimePoint previousTimePoint, long duration){
        assert (previousTimePoint != null) : "Previous TimePoint is null";
        assert (!previousTimePoint.isFrozen()) : "Previous TimePoint is frozen";
        assert (duration >= 0) : "Duration is less than 0";
        dependencies.add(new Dependency(previousTimePoint, duration));
        return true;
    }

    // Returns number of dependencies
    public final int inDegree(){
        return dependencies.size();
    }

    // Returns true if this main.TimePoint has any dependencies
    public final boolean isIndependent(){
        return dependencies.isEmpty();
    }

    public final void freeze(){
        frozen = true;
    }

    public final boolean isFrozen(){return frozen;}

    public Set<Dependency> getDependencies() {
        return new HashSet<>(dependencies);
    }

    // Returns String representation of this TimePoint object and its frozen status
    public final String toSimpleString(){
        return "\nTimePoint: " + System.identityHashCode(this) +  "\nFrozen status: " + frozen;

    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        String output = sb.append(toSimpleString()).append("\nDependencies:").toString();
        if (dependencies.isEmpty()){
            output = sb.append("\nnone").toString();
        }
        else {
            for (Dependency d : dependencies) {
                output = sb.append("\n").append(System.identityHashCode(d)).toString();
            }
        }
        for (Dependency d : dependencies){
            output = sb.append("\n\n").append(d.toString()).toString();
        }
        return output;
    }
}

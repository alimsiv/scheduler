package main;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class TimePoint {
    private final Set<Dependency> dependencies = new HashSet<>();
    private boolean frozen = false;
    private final Activity activity;
    private final Side side;
    private final String description;

    public enum Side {
        BEGIN,
        END;
    }

    TimePoint(Activity activity, Side side){
        this.activity = activity;
        this.side = side;
        this.description = activity.getDescription() + ":" + side.name();
    }

    // Returns all previous time points due to dependencies
    public final Set<TimePoint> previousTimePoints(){
        return dependencies.stream().map(i -> i.getPrevious()).collect(Collectors.toSet());
    }

    // Adds dependency to this time point
    final boolean addPrevious(TimePoint previousTimePoint, long duration){
        return (addPreviousDependency(previousTimePoint,duration));
    }

    // Adds dependency of zero duration
    final boolean addPrevious(TimePoint previousTimePoint){
        return (addPreviousDependency(previousTimePoint,0));
    }

    private boolean addPreviousDependency(TimePoint previousTimePoint, long duration){
        assert (previousTimePoint != null) : "Previous TimePoint is null";
        assert (!frozen) : new SchedulerException.Builder(SchedulerException.Error.POINT_FROZEN)
                .setTimePoint(previousTimePoint).build();
        assert (duration >= 0) : new SchedulerException.Builder(SchedulerException.Error.INVALID_DURATION)
                .setTimePoint(previousTimePoint)
                .setDuration(duration).build();
        return dependencies.add(new Dependency(previousTimePoint, duration));
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

    public final Activity getActivity() {
        return activity;
    }

    public final Side getSide() {
        return side;
    }

    public final String getDescription() {
        return description;
    }

    // Returns String representation of this TimePoint object and its frozen status
    public final String toSimpleString(){
        return "\nTimePoint: " + System.identityHashCode(this) +
                "\nFrozen status: " + frozen +
                "\nActivity: " + activity.toString() +
                "\nSide: " + side.toString() +
                "\nDescription: " + description.toString();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String output = sb.append(toSimpleString())
                .append("\n-------------------------------")
                .append("\nActivity: ").append(activity.toString())
                .append("\n-------------------------------")
                .append("\nDependencies:").toString();
        if (dependencies.isEmpty()){
            return sb.append("\nnone").toString();
        }

        for (Dependency d : dependencies) {
            output = sb.append("\n").append(System.identityHashCode(d)).toString();
        }
        for (Dependency d : dependencies){
            output = sb.append("\n\n").append(d.toString()).toString();
        }

        return output;
    }
}

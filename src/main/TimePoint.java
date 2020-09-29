package main;

import java.util.HashSet;
import java.util.Objects;
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
        END
    }

    TimePoint(Activity activity, Side side){
        Checker.assertNonNull(activity,side);
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
        assert (Objects.nonNull(previousTimePoint)) : "Previous TimePoint is null";
        assert (!frozen) : buildException(SchedulerException.Error.POINT_FROZEN, previousTimePoint,duration);
        assert (duration >= 0) : buildException(SchedulerException.Error.INVALID_DURATION, previousTimePoint,duration);
        return dependencies.add(new Dependency(previousTimePoint, duration));
    }

    private SchedulerException buildException(SchedulerException.Error error, TimePoint t, long duration) {
        return new SchedulerException.Builder(error)
                .setTimePoint(t)
                .setDuration(duration)
                .build();
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
        StringBuilder sb = new StringBuilder();
        return sb.append("\nTimePoint: ").append(System.identityHashCode(this))
                .append("\n\tDescription: ").append(description)
                .append("\n\tSide: ").append(side.name())
                .append("\n\tFrozen status: ").append(frozen)
                .append("\n\tActivity: ").append(activity.toString().indent(8)).toString();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        String output = sb.append(toSimpleString())
                .append("\tDependencies:").toString();
        if (dependencies.isEmpty()){
            return sb.append("\n\t\tnone").toString();
        }
        for (Dependency d : dependencies) {
            output = sb.append("\n\t\t").append(System.identityHashCode(d)).toString();
        }
        for (Dependency d : dependencies){
            output = sb.append("\n\n").append(d.toString()).toString();
        }
        return output;
    }
}

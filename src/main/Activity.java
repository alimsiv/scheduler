package main;

import java.util.EnumMap;
import java.util.Objects;
import java.util.Set;

public final class Activity {
    private final ActivityGroup activities;
    private final String description;
    private final long duration;
    private boolean frozen;
    private final EnumMap<TimePoint.Side, TimePoint> timePointMap;

    private Activity(ActivityGroup activities, String description, long duration){
        this.activities = activities;
        this.description = description;
        this.duration = duration;

        TimePoint beginTimePoint = new TimePoint(this, TimePoint.Side.BEGIN);
        TimePoint endTimePoint = new TimePoint(this, TimePoint.Side.END);
        endTimePoint.addPrevious(beginTimePoint,duration);

        timePointMap = new EnumMap<>(TimePoint.Side.class);
        timePointMap.put(beginTimePoint.getSide(), beginTimePoint);
        timePointMap.put(endTimePoint.getSide(), endTimePoint);
    }

    final TimePoint extremePoint(TimePoint.Side side){
        //returns the time point at the given side
        SchedulerException.assertNonNull(side);
        return timePointMap.get(side);
    }

    public final Set<Dependency> dependencies(TimePoint.Side side){
        //returns the dependencies at the given side
        SchedulerException.assertNonNull(side);
        return extremePoint(side).getDependencies();
    }

    public static final Activity create(long duration, ActivityGroup activities, String description) throws SchedulerException{
        if (duration < 0){
            throw new IllegalArgumentException("Duration is negative", SchedulerException.buildSchedulerException(SchedulerException.Error.INVALID_DURATION, null, duration).build());
        }

        Objects.requireNonNull(activities);
        return new Activity(activities, Objects.requireNonNullElse(description, ""), duration);
    }

    public final boolean addDependency(TimePoint.Side side, TimePoint other) throws SchedulerException{
        // adds a dependency of zero duration from the other time point
        if (Objects.isNull(side) || Objects.isNull(other)){
            throw new IllegalArgumentException("Invalid null inputs to addDependency()");
        }
        TimePoint currentTimePoint = timePointMap.get(side);
        if (frozen){
            throw new IllegalArgumentException("Duration is negative", new SchedulerException.Builder(SchedulerException.Error.INVALID_DEPENDENCY)
                    .setTimePoint(currentTimePoint)
                    .setOtherTimePoint(other)
                    .setDuration(duration).build());
        }
        return currentTimePoint.addPrevious(other);
    }

    public final void freeze(){
        frozen = true;
        timePointMap.forEach(((side, timePoint) -> timePoint.freeze()));
        activities.addTimePoints(timePointMap.values());
    }

    public ActivityGroup getActivities() {
        return activities;
    }

    public long getDuration() {
        return duration;
    }

    public final String getDescription() {
        return description;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        return sb.append("\n").append(System.identityHashCode(this))
                .append("\nDescription: ").append(description)
                .append("\nDuration: ").append(duration)
                .append("\nFrozen status: ").append(frozen)
                .append("\nActivity Group: ").append(activities.toString().indent(8)).toString();
    }
}

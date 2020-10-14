package main;

public final class Dependency {
    private final TimePoint previous; //previous time point
    private final long duration; //length of time that must pass since the previous time point

    Dependency(TimePoint previous){
        this(previous, 0);
    }

    Dependency(TimePoint previous, long duration){
        SchedulerException.assertNonNull(previous);
        this.previous = previous;
        this.duration = duration;
    }

    public final TimePoint getPrevious() {
        return previous;
    }

    public final long getDuration() {
        return duration;
    }

    @Override
    public final String toString(){
        StringBuilder sb = new StringBuilder();
        return sb.append("\nDependency: ").append(System.identityHashCode(this))
        .append("\n\tDuration: ").append(duration)
        .append(previous.toString().indent(4)).toString();
    }
}

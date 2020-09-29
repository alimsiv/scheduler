package main;

import java.util.Objects;

public final class Dependency {
    private final TimePoint previous; //previous time point
    private final long duration; //length of time that must pass since the previous time point

    Dependency(TimePoint previous, long duration){
        assert (Objects.nonNull(previous)) : "Previous TimePoint is null";
        this.previous = previous;
        this.duration = duration;
    }

    Dependency(TimePoint previous){
        assert (Objects.nonNull(previous)) : "Previous TimePoint is null";
        this.previous = previous;
        this.duration = 0;
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

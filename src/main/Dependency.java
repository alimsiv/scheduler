package main;

public final class Dependency {
    private final TimePoint previous; //previous time point
    private final long duration; //length of time that must pass since the previous time point

    Dependency(TimePoint previous, long duration){
        assert (previous != null) : "Previous TimePoint is null";
        assert (duration >= 0) : "Duration is less than 0";
        this.previous = previous;
        this.duration = duration;
    }

    Dependency(TimePoint previous){
        assert (previous != null) : "Previous TimePoint is null";
        this.previous = previous;
        this.duration = 0;
    }

    public final TimePoint getPrevious() {
        return previous;
    }

    public final long getDuraction() {
        return duration;
    }

    public final String toString(){
        return "Dependency: " + System.identityHashCode(this) + "\nDuration: " + duration + previous.toString();
    }
}

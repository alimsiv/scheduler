package main;

public final class SchedulerException extends Exception{

    private static final long serialVersionUID = 555L;
    private final Error error;
    private final long duration;
    private final TimePoint timePoint;
    private final TimePoint otherTimePoint;

    public enum Error{
        POINT_FROZEN,
        TIME_POINT_EXISTS,
        POINT_NOT_FROZEN,
        INVALID_DURATION,
        INVALID_DEPENDENCY;
    }

    private SchedulerException(Builder builder){
        error = builder.getError();
        duration = builder.getDuration();
        timePoint = builder.getTimePoint();
        otherTimePoint = builder.getOtherTimePoint();
    }

    final static class Builder{
        private final Error error;
        private long duration;
        private TimePoint timePoint;
        private TimePoint otherTimePoint;

        Builder(Error error){
            this.error = error;
        }

        final SchedulerException build(){
            return new SchedulerException(this);
        }

        Error getError() {return error;}

        long getDuration() {
            return duration;
        }

        Builder setDuration(long duration) {
            this.duration = duration;
            return this;
        }

        TimePoint getTimePoint() {
            return timePoint;
        }

        Builder setTimePoint(TimePoint timePoint) {
            this.timePoint = timePoint;
            return this;
        }

        TimePoint getOtherTimePoint() {
            return otherTimePoint;
        }

        Builder setOtherTimePoint(TimePoint otherTimePoint) {
            this.otherTimePoint = otherTimePoint;
            return this;
        }

        @Override
        public String toString(){
            String output = "";
            output += (error != null) ? "\nError: " + error.toString() : "";
            output += "\nDuration: " + duration;
            output += (timePoint != null) ? "\nTimePoint: " + timePoint.toString() : "";
            output += (otherTimePoint != null) ? "\nOther TimePoint: " + otherTimePoint.toString() : "";
            return output;
        }
    }

    public final Error getError() {
        return error;
    }

    public final long getDuration() {
        return duration;
    }

    public final TimePoint getTimePoint() {
        return timePoint;
    }

    public final TimePoint getOtherTimePoint() {
        return otherTimePoint;
    }

    @Override
    public String toString(){
        String output = "";
        output += (error != null) ? "\nError: " + error.toString() : "";
        output += "\nDuration: " + duration;
        output += (timePoint != null) ? "\nTimePoint: " + timePoint.toString() : "";
        output += (otherTimePoint != null) ? "\nOther TimePoint: " + otherTimePoint.toString() : "";
        return output;
    }
}

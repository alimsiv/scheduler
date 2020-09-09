package main;

public final class SchedulerException extends Exception{


    //serialVersionUID
    private final Error error;
    private final TimePoint timePoint;
    private final TimePoint otherTimePoint;

    private SchedulerException(Builder builder){
        error = builder.error;
        timePoint = builder.timePoint;
        otherTimePoint = builder.otherTimePoint;
    }

    //nested enum
    public enum Error{

    }


    final static class Builder{
        private final Error error;
        private long duration;
        private TimePoint timePoint;
        private TimePoint otherTimePoint;

        public Builder(Error error){
            this.error = error;
        }

        final SchedulerException build(){
            //todo with appropriate parameters
            return new SchedulerException(this);
        }

        public Error getError() {
            return error;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        public TimePoint getTimePoint() {
            return timePoint;
        }

        public void setTimePoint(TimePoint timePoint) {
            this.timePoint = timePoint;
        }

        public TimePoint getOtherTimePoint() {
            return otherTimePoint;
        }

        public void setOtherTimePoint(TimePoint otherTimePoint) {
            this.otherTimePoint = otherTimePoint;
        }
    }

    public Error getError() {
        return error;
    }

    public TimePoint getTimePoint() {
        return timePoint;
    }

    public TimePoint getOtherTimePoint() {
        return otherTimePoint;
    }

    public String toString(){
        return "Error: " + error.toString() + "\nTimePoint: \n" + timePoint.toSimpleString() + "\nOther Timepoint: \n" + otherTimePoint.toSimpleString();
    }
}

package main;

final class TimePointCounter {
    private int counter;

    TimePointCounter(int counter){
        assert (counter >= 0) : "Counter is negative";
        this.counter = counter;
    }

    final int decrement(){
        assert (counter > 0) : "Counter is not positive";
        return --counter;
    }

    final boolean isIndependent(){
        return (counter == 0);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        return sb.append("TimePointCounter: ").append(System.identityHashCode(this))
                .append("\n\tcounter: ").append(counter).toString();
    }
}

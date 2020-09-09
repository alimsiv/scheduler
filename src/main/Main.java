package main;

public class Main {

    public static void main(String args[]) {
        TimePoint t1 = new TimePoint();
        TimePoint t2 = new TimePoint();
        t2.addPrevious(t1);

        TimePoint t3 = new TimePoint();
        TimePoint t4 = new TimePoint();

        t4.addPrevious(t2, 2);
        t4.addPrevious(t3, 3);

        /*
                    t4
                   /  \
                  /    \
                 t2     t3
                /
               /
              t1
         */

        System.out.println(t4.toString());
    }
}
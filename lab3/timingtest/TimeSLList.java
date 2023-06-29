package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // Create an AList to store the size of different ALists used in test
        AList<Integer> Ns = new AList<>();
        int[] N = {6000, 8000, 10000, 12000, 24000, 120000};
        for (int n : N) {
            Ns.addLast(n);
        }

        // Create an AList to store times used in creating different ALists
        AList<Double> times = new AList<>();
        // Create an AList to store opCounts
        AList<Integer> opCounts = new AList<>();

        for (int i = 0; i < Ns.size(); i++) {
            // Instantiate a new SLList
            SLList<Integer> sl = new SLList<>();

            // Add N items into SLList
            for (int n = 0; n < Ns.get(i); n++) {
                sl.addLast(n);
            }

            int opCount = 0;

            // start the timer
            Stopwatch sw = new Stopwatch();
            for (int op = 0; op < 10000; op++) {
                sl.getLast();
                opCount++;
            }
            double time = sw.elapsedTime();

            times.addLast(time);
            opCounts.addLast(opCount);
        }
        printTimingTable(Ns, times, opCounts);
    }

}

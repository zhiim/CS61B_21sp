package gh2;

import deque.ArrayDeque;

//Note: This file will not compile until you complete the Deque implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private ArrayDeque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // Initiate an ArrayDeque
        int capacity = (int) Math.round(SR / frequency);
        buffer = new ArrayDeque<>();
        // Fill buffer with zeros
        for (int i = 0; i < capacity; i++) {
            buffer.addFirst(0.0);
        }
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        double r = 0.0;

        int bufferCapacity = buffer.size();
        // replace elements of buffer with random numbers between -0.5 and 0.5
        for (int i = 0; i < bufferCapacity; i++) {
            r = Math.random() - 0.5;
            buffer.removeLast();
            buffer.addFirst(r);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double newDouble = (buffer.get(0) + buffer.get(1)) * 0.5 * DECAY;
        buffer.removeFirst();
        buffer.addLast(newDouble);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }
}

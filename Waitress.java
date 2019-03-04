import java.util.Random;

/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {

    private WaitingArea waitingArea;

    /**
     * Creates a new waitress. Make sure to save the parameter in the class
     *
     * @param waitingArea The waiting area for customers
     */
    Waitress(WaitingArea waitingArea) {
        // TODO Implement required functionality

        this.waitingArea = waitingArea;
    }

    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {
        // TODO Implement required functionality

        Random rnd = new Random();

        while (SushiBar.isOpen || !this.waitingArea.isEmpty()) {

            try {
                Thread.sleep(rnd.nextInt(SushiBar.waitressWait));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Customer next = this.waitingArea.next();

            if (next == null) {
                continue;
            }

            next.order();
        }
    }
}


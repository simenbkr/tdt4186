import java.util.Random;

/**
 * This class implements the consumer part of the producer/consumer problem.
 * One waitress instance corresponds to one consumer.
 */
public class Waitress implements Runnable {

    private final WaitingArea waitingArea;

    /**
     * Creates a new waitress. Make sure to save the parameter in the class
     *
     * @param waitingArea The waiting area for customers
     */
    Waitress(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This is the code that will run when a new thread is
     * created for this instance
     */
    @Override
    public void run() {

        Customer next;

        while (SushiBar.isOpen || !this.waitingArea.isEmpty()) {

            // Fetch the next customer from the shared resource, the waiting area.
            synchronized (this.waitingArea) {

                if (this.waitingArea.isEmpty() && SushiBar.isOpen) {
                    try {
                        this.waitingArea.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Get the next customer.
                next = this.waitingArea.next();

                // If is the last customer, wake up other threads to let them die.
                if (!SushiBar.isOpen && this.waitingArea.isEmpty()) {
                    this.waitingArea.notifyAll();
                }

            }

            // Check that next is not null, as it might be if there has been a wait, and the thread has been woken. In
            // this case, the thread will have to repeat.
            if (next != null) {
                // Order.
                next.order();
            }

        }
    }
}


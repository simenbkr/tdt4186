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

        Random rnd = new Random();
        Customer next = null;

        while (SushiBar.isOpen || !this.waitingArea.isEmpty()) {

            // Fetch the next customer from the shared resource, the waiting area.
            synchronized (this.waitingArea) {

                if(this.waitingArea.isEmpty() && SushiBar.isOpen) {
                    try {
                        this.waitingArea.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {

                    // Get the next customer.
                    next = this.waitingArea.next();

                    // This is the last customer. Wake up other threads to let them die.
                    if(!SushiBar.isOpen && this.waitingArea.isEmpty()) {
                        this.waitingArea.notifyAll();
                    }

                }
            }

            // Check that next is not null, as it might be if there has been a wait, and the thread has been woken. In
            // this case, the thread will have to repeat.
            if(next != null) {
                // Order.
                next.order();
            }

        }
    }
}


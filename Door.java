import java.util.Random;

/**
 * This class implements the Door component of the sushi bar assignment
 * The Door corresponds to the Producer in the producer/consumer problem
 */
public class Door implements Runnable {

    /**
     * Creates a new Door. Make sure to save the
     *
     * @param waitingArea   The customer queue waiting for a seat
     */
    private final WaitingArea waitingArea;

    public Door(WaitingArea waitingArea) {
        this.waitingArea = waitingArea;
    }

    /**
     * This method will run when the door thread is created (and started)
     * The method should create customers at random intervals and try to put them in the waiting area
     */
    @Override
    public void run() {
        Random rnd = new Random();

        while (SushiBar.isOpen) {

            try {
                Thread.sleep(rnd.nextInt(SushiBar.doorWait));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (this.waitingArea) {
                if (this.waitingArea.isFull()) {
                    try {
                        this.waitingArea.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                Customer customer = new Customer(SushiBar.customerCounter.get());
                this.waitingArea.enter(customer);
            }

        }
    }
}

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class implements a waiting area used as the bounded buffer, in the producer/consumer problem.
 */
public class WaitingArea {

    private int size;
    private Queue<Customer> customerQueue;


    /**
     * Creates a new waiting area.
     *
     * @param size The maximum number of Customers that can be waiting.
     */
    public WaitingArea(int size) {
        this.size = size;
        this.customerQueue = new LinkedList<>();
    }

    /**
     * This method should put the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized void enter(Customer customer) {
        customerQueue.add(customer);
        SushiBar.write(Thread.currentThread().getName() + "   | Customer #" + customer.getCustomerID() + " is now waiting.");

        SushiBar.customerCounter.increment();

        // Notify any waitress that a new customer has arrived.
        this.notify();
    }

    /**
     * @return The customer that is first in line.
     */
    public synchronized Customer next() {
        Customer customer = customerQueue.poll();

        if (customer == null) {
            return null;
        }

        SushiBar.write(Thread.currentThread().getName() + "   | Customer #" + customer.getCustomerID() + " is now fetched.");

        // Notify the door that there is space again.
        this.notify();
        return customer;
    }

    public boolean isEmpty() {
        return this.customerQueue.size() == 0;
    }

    public boolean isFull() {
        return this.customerQueue.size() == this.size;
    }
}

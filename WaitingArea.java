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
        // TODO Implement required functionality
        this.size = size;
        this.customerQueue = new LinkedList<>();
    }

    /**
     * This method should put the customer into the waitingArea
     *
     * @param customer A customer created by Door, trying to enter the waiting area
     */
    public synchronized void enter(Customer customer) {
        // TODO Implement required functionality
        customerQueue.add(customer);
        SushiBar.write(Thread.currentThread().getName() + " | Customer #" + customer.getCustomerID() + " is now waiting.");

        SushiBar.customerCounter.increment();
        this.notify();
    }

    /**
     * @return The customer that is first in line.
     */
    public synchronized Customer next() {
        // TODO Implement required functionality
        Customer customer = customerQueue.poll();

        if (customer == null) {
            return null;
        }

        SushiBar.write(Thread.currentThread().getName() + " | Customer #" + customer.getCustomerID() + " is now fetched.");
        this.notify();
        return customer;
    }

    // Add more methods as you see fit
    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.customerQueue.size() == 0;
    }

    public boolean isFull() {
        return this.customerQueue.size() == this.size;
    }
}

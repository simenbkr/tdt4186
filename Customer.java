import java.util.Random;

/**
 * This class implements a customer, which is used for holding data and update the statistics
 *
 */
public class Customer {

    /**
     *  Creates a new Customer.
     *  Each customer should be given a unique ID
     */

    private int customerID;

    public Customer(int customerID) {
        this.customerID = customerID;
    }


    /**
     * Here you should implement the functionality for ordering food as described in the assignment.
     */
    public synchronized void order(){
        Random rnd = new Random();

        try {
            Thread.sleep(rnd.nextInt(SushiBar.waitressWait));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int orders = rnd.nextInt(SushiBar.maxOrder - 1) + 1;
        int takeaway = rnd.nextInt(orders);
        int eatenHere = orders - takeaway;

        SushiBar.takeawayOrders.add(takeaway);
        SushiBar.totalOrders.add(orders);
        SushiBar.servedOrders.add(eatenHere);

        SushiBar.write(Thread.currentThread().getName() + "   | Customer #" + this.customerID + " is now eating.");

        try {
            Thread.sleep(rnd.nextInt(SushiBar.customerWait * orders));
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        SushiBar.write(Thread.currentThread().getName() + "   | Customer #" + this.customerID + " is now leaving.");
    }

    /**
     *
     * @return Should return the customerID
     */
    public int getCustomerID() {
        return this.customerID;
    }

    // Add more methods as you see fit
}

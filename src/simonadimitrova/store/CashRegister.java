package simonadimitrova.store;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;
import java.util.Random;

public class CashRegister extends Entity {
    private static int ID = 0;

    // used to generate the effect of processing time
    private final Random random;

    private Store store;
    private ClientThread thread;

    public CashRegister() {
        this(ID++);
    }

    public CashRegister(int id) {
        super(id);
        random = new Random(id);
    }

    public synchronized Store getStore() {
        return store;
    }

    public synchronized void setStore(Store store) {
        this.store = store;
    }

    public synchronized boolean isOpen() {
        return thread != null;
    }

    public synchronized Cashier getCashier() {
        return thread.cashier;
    }

    public synchronized  void open(Cashier cashier) {
        if (isOpen()) {
            throw new InvalidStateException("Already open, close it first");
        }

        if (store == null) {
            throw new InvalidStateException("No store");
        }

        // run the register in a new thread
        thread = new ClientThread(cashier);
        thread.start();
    }

    public void close() throws InterruptedException {
        ClientThread thread;

        synchronized (CashRegister.this) {
            if (!isOpen()) {
                // alredy closed
                return;
            }

            // wait to the finish serving the current client
            thread = this.thread;
            thread.closing = true;
            this.thread = null;
            notifyAll();
        }

        System.out.println("CashRegister " + id + " about to join");
        thread.join();
        System.out.println("CashRegister " + id + " joined");
    }

    public synchronized void queueClient(Client client) {
        if (!isOpen()) {
            throw new InvalidStateException("Cash register not open");
        }

        thread.clients.add(client);

        // wake up the thread
        notifyAll();
    }

    private class ClientThread extends Thread {
        public final Queue<Client> clients = new ArrayDeque<>();
        public final Cashier cashier;
        public boolean closing = false;

        public ClientThread(Cashier cashier) {
            this.cashier = cashier;
        }

        @Override
        public void run() {
            while (true) {
                Client client;

                synchronized (CashRegister.this) {
                    while (!closing && clients.isEmpty()) {
                        try {
                            // wait until either stopping or there's a client
                            System.out.println(this + ": no work to do");
                            CashRegister.this.wait();
                        } catch (InterruptedException ex) {
                        }
                    }

                    if (closing && clients.isEmpty()) {
                        System.out.println(this + ": about to close");
                        return;
                    }

                    client = clients.remove();
                }

                // TODO: process client
                System.out.println(this + ": processing with " + client.items.size() + " items");
                for (ItemQuantity item : client.items) {
                    System.out.println(this + " processing " + item);

                    try {
                        Thread.sleep(500 + random.nextInt(1500));
                    } catch (InterruptedException ex) { }
                }

                Receipt receipt = new Receipt(cashier, new Date(), client.items);
                store.addReceipt(receipt);
                System.out.println("Issue receipt #" + receipt.getId());
            }
        }

        @Override
        public String toString() {
            return String.format("CashRegister #%d %s", id, cashier);
        }
    }

    @Override
    public synchronized String toString() {
        return String.format("CashRegister #%d %s",
                id, isOpen() ? getCashier() : "CLOSED");
    }
}

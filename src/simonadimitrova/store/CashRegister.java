package simonadimitrova.store;

import java.io.IOException;
import java.util.*;

public class CashRegister {
    private static int ID = 0;

    // used to generate the effect of processing time
    private final Random random;

    private final int id;
    private Store store;
    private ClientThread thread;

    public CashRegister() {
        this(ID++);
    }

    public CashRegister(int id) {
        this.id = id;
        random = new Random(id);
    }

    public int getId() {
        return id;
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

    public synchronized void open(Cashier cashier) {
        if (isOpen()) {
            throw new IllegalStateException("Already open, close it first");
        }

        if (store == null) {
            throw new IllegalStateException("No store");
        }

        // run the register in a new thread
        thread = new ClientThread(cashier);
        thread.start();
    }

    public void close() throws InterruptedException {
        ClientThread thread;

        synchronized (CashRegister.this) {
            if (!isOpen()) {
                // already closed
                return;
            }

            // wait to the finish serving the current client
            thread = this.thread;
            thread.closing = true;
            this.thread = null;
            notifyAll();
        }

        thread.join();
    }

    public synchronized void queueClient(Client client) {
        if (!isOpen()) {
            throw new IllegalStateException("Cash register not open");
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

                System.out.println(this + ": processing client with " + client.items.size() + " items");

                List<ItemQuantity> passed = new ArrayList<>();
                List<InsufficientItemQuantityException> failed = new ArrayList<>();

                for (ItemQuantity item : client.items) {
                    System.out.println(this + ": processing " + item);
                    try {
                        store.removeItem(item.getItem(), item.getQuantity());
                        passed.add(item);
                    } catch (InsufficientItemQuantityException e) {
                        failed.add(e);
                    }

                    try {
                        // make it look as though the cashier is taking time to serve the client
                        Thread.sleep(500 + random.nextInt(1500));
                    } catch (InterruptedException ex) {
                    }
                }

                if (!failed.isEmpty()) {
                    for (ItemQuantity item : passed) {
                        // restore the passed items
                        store.addItem(item.getItem(), item.getQuantity());
                    }

                    System.err.println(this + ": failed processing client due to insufficient items:");
                    for (InsufficientItemQuantityException e : failed) {
                        System.err.println(this + ": " + e.getMessage());
                    }
                } else {
                    // all was fine, issue a receipt
                    Receipt receipt = new Receipt(cashier, new Date(), passed);
                    try {
                        store.addReceipt(receipt);
                    } catch (IOException e) {
                        System.err.println("Could not save receipt " + receipt.getId() + ": " + e.getMessage());
                    }

                    System.out.println(String.format(
                            "%s: issued receipt #%d (total: %.2f BGN)",
                            this, receipt.getId(), receipt.getTotal()));
                }
            }
        }

        @Override
        public String toString() {
            return String.format("CashRegister #%d (%s)", id, cashier);
        }
    }

    @Override
    public synchronized String toString() {
        return String.format("CashRegister #%d (%s)",
                id, isOpen() ? getCashier() : "CLOSED");
    }
}

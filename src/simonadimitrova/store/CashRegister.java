package simonadimitrova.store;

import sun.plugin.dom.exception.InvalidStateException;

public class CashRegister extends Entity {
    private ClientThread thread;

    public CashRegister(int id) {
        super(id);
    }

    public synchronized boolean isOpen() {
        return thread != null;
    }

    public synchronized Cashier getCashier() {
        return thread.cashier;
    }

    public synchronized  void open(Cashier cashier) {
        if (isOpen()) {
            throw new InvalidStateException("Already open");
        }

        // run the register in a new thread
        thread = new ClientThread(cashier);
    }

    public synchronized void close() throws InterruptedException {
        if (!isOpen()) {
            throw new InvalidStateException("Already closed");
        }

        // wait to the finish serving the current client
        thread.stopping = true;
        thread.join();
        thread = null;
    }

    private class ClientThread extends Thread {
        public final Cashier cashier;
        public Client client;
        public boolean stopping = false;

        public ClientThread(Cashier cashier) {
            this.cashier = cashier;
        }

        @Override
        public void run() {
            synchronized (CashRegister.this) {
                while (!stopping && client == null) {
                    try {
                        // wait until either stopping or there's a client
                        CashRegister.this.wait();
                    } catch (InterruptedException ex) { }
                }

                if (stopping && client == null) {
                    return;
                }


            }


            // TODO: process client
        }
    }

    @Override
    public synchronized String toString() {
        return String.format("CashRegister #%d %s",
                id, isOpen() ? getCashier() : "CLOSED");
    }
}

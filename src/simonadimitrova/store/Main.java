package simonadimitrova.store;

public class Main {
    public static void main(String[] args) {
        // Create a new store
        Store store = new Store("Fantastico");

        // Add cash registers

        // Add cashiers
        store.addCashier(new Cashier(1, "Dimitar Dimitrov"));
        store.addCashier(new Cashier(4, "Petia Todorova"));
        store.addCashier(new Cashier(10, "Anelia Ivanova"));
        store.addCashier(new Cashier(10, "Anelia Ivanova"));
        // Add stock
        // Process clients
        // Wait for clients to be processed
    }
}

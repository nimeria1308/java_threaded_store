package simonadimitrova.store;

public class Main {
    public static void main(String[] args) {
        // Create a new store
        Store store = new Store("Fantastico");

        // Add 9 cash registers
        store.addCashRegister(new CashRegister());
        store.addCashRegister(new CashRegister());
        store.addCashRegister(new CashRegister());
        store.addCashRegister(new CashRegister());
        store.addCashRegister(new CashRegister());
        store.addCashRegister(new CashRegister());
        store.addCashRegister(new CashRegister());
        store.addCashRegister(new CashRegister());
        store.addCashRegister(new CashRegister());

        // Add 6 cashiers
        store.addCashier(new Cashier("Dimitar Dimitrov"));
        store.addCashier(new Cashier("Petia Todorova"));
        store.addCashier(new Cashier("Anelia Ivanova"));
        store.addCashier(new Cashier("Maria Petkova"));
        store.addCashier(new Cashier("Dragan Todorov"));
        store.addCashier(new Cashier("Ivelina Georgieva"));

        // Add 14 items to stock
        store.addItem(new Item("Milk Olympus 2% 1L", 3.19, 14), 80);
        store.addItem(new Item("Milk Vereya 2% 2L", 4.39, 21), 150);

        store.addItem(new Item("Yogurt Olympus 3.6% 400g", 1.19, 21), 40);
        store.addItem(new Item("Yogurt Elena 3.6% 400g", 1.39, 21), 100);
        store.addItem(new Item("Yogurt Elena 2% 400g", 1.19, 21), 150);

        store.addItem(new Item("Yellow Cheese Madzharov per KG", 23.99, 7), 30);
        store.addItem(new Item("Goat Feta Cheese Madzharov 200g", 4.45, 7), 20);

        store.addItem(new Item("Simid Bread 830g", 1.39, 7), 80);
        store.addItem(new Item("Toaster Simid Bread 550g", 1.29, 7), 30);

        store.addItem(new Item("Jack Daniels 0.7L", 42.99, 700), 15);
        store.addItem(new Item("Johnny Walter Black Label 0.7L", 52.99, 700), 15);

        store.addItem(new Item("Coca Cola 1.5L", 1.99, 365), 150);
        store.addItem(new Item("Coca Cola 0.5L", 1.29, 365), 150);
        store.addItem(new Item("Coca Cola 0.33L", 1.19, 365), 150);

        // Process clients
        // Wait for clients to be processed
    }
}

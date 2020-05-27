package simonadimitrova.store;

public class Main {
    public static void main(String[] args) throws Exception {
        // Create a new store
        Store store = new Store("T market");

        // Add 3 cash registers
        store.addCashRegister(new CashRegister());
        store.addCashRegister(new CashRegister());
        store.addCashRegister(new CashRegister());

        // Add 5 cashiers
        store.addCashier(new Cashier("Dimitar Dimitrov"));
        store.addCashier(new Cashier("Petia Todorova"));
        store.addCashier(new Cashier("Anelia Ivanova"));
        store.addCashier(new Cashier("Maria Petkova"));
        store.addCashier(new Cashier("Dragan Todorov"));

        // Add 15 items to stock
        store.addItem(new Item("Toilet Paper 4pcs", 2.99, 700), 30);

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

        // Open cash registers
        for (int i = 0; i < 3; i++) {
            store.getCashRegister(i).open(store.getCashier(i));
        }

        // Queue and process clients
        store.getCashRegister(0).queueClient(
                new Client(
                        new ItemQuantity[] {
                                new ItemQuantity(store.getItem(0), 5),
                                new ItemQuantity(store.getItem(1), 2),
                                new ItemQuantity(store.getItem(3), 3),
        }));

        store.getCashRegister(0).queueClient(
                new Client(
                        new ItemQuantity[] {
                                new ItemQuantity(store.getItem(0), 3),
                                new ItemQuantity(store.getItem(7), 2),
                        }));

        store.getCashRegister(1).queueClient(
                new Client(
                        new ItemQuantity[] {
                                new ItemQuantity(store.getItem(0), 20),
                        }));

        store.getCashRegister(1).queueClient(
                new Client(
                        new ItemQuantity[] {
                                new ItemQuantity(store.getItem(8), 2),
                                new ItemQuantity(store.getItem(6), 0.7),
                                new ItemQuantity(store.getItem(3), 4),
                        }));

        store.getCashRegister(2).queueClient(
                new Client(
                        new ItemQuantity[] {
                                new ItemQuantity(store.getItem(10), 5),
                                new ItemQuantity(store.getItem(11), 5),
                                new ItemQuantity(store.getItem(12), 15),
                                new ItemQuantity(store.getItem(0), 10),
                        }));

        store.getCashRegister(2).queueClient(
                new Client(
                        new ItemQuantity[] {
                                new ItemQuantity(store.getItem(0), 10),
                        }));

        //  Start closing the registers and wait for clients to be processed
        for (int i = 0; i < 3; i++) {
            store.getCashRegister(i).close();
        }

        // print receipts
        for (Receipt receipt : store.getReceipts()) {
            System.out.println(receipt);
        }

        System.out.println(String.format(
                "Total store revenue: %.2f", store.getTotalRevenue()));
    }
}

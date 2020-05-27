package simonadimitrova.store;

public class Cashier {
    private static int ID = 0;

    private final int id;
    private String name;

    public Cashier(String name) {
        this(ID++, name);
    }

    public Cashier(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Cashier #%d %s", id, name);
    }
}

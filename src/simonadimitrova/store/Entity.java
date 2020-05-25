package simonadimitrova.store;

public class Entity {
    protected final int id;

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof Entity) {
            return id == ((Entity) other).id;
        }

        return false;
    }
}

package generic.mvc;

public class CustomerDAO implements DAO<Customer>{
    @Override
    public void add(Customer customer) {

    }

    @Override
    public boolean remove(int index) {
        return false;
    }

    @Override
    public void update(int index, int T) {

    }

    @Override
    public <E> E getValue() {
        return null;
    }
}

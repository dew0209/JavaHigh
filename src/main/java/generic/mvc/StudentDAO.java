package generic.mvc;

public class StudentDAO implements DAO<Student>{
    @Override
    public void add(Student student) {

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

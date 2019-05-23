package app.dao.contract;

import java.util.List;

public interface Dao<T> {

    void save(T t);

    void update(T t, String[] params) ;

    void delete(T t);

    List<T> getAll();
}
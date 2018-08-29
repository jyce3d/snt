package be.sdlg.snt.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, ID extends Serializable> {

		T get(ID id);
		List<T> findAll();
		T add(T entity);
		T update(T entity);
		void delete(T entity);
}

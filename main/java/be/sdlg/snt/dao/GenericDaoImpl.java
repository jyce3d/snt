package be.sdlg.snt.dao;

import java.io.Serializable;
import java.util.List;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import be.sdlg.snt.model.DBUser;

public abstract class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID>{
	private SessionFactory sessionFactory;
	private Class<T> persistentClass;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public GenericDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public T get(ID id) {
	  return	 (T) getCurrentSession().get(persistentClass, id);
	}
	public String hqlFindAll() {
		return "";
	}
	public List<T> findAll() {
		if (hqlFindAll().length()==0)
			return null;
		else 
			return (List<T>) getCurrentSession().createQuery(hqlFindAll()).list();
	}

	public T add(T entity) {
		getCurrentSession().save(entity);
		return entity;
	}

	public T update(T entity) {
		getCurrentSession().update(entity);
		return entity;
	}

	public void delete(T entity) {
		getCurrentSession().delete(entity);
		
	}

}

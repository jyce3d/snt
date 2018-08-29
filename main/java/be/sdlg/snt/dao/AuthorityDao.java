package be.sdlg.snt.dao;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import be.sdlg.snt.model.Authority;

public class AuthorityDao extends GenericDaoImpl<Authority, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.Authority au";
	}
	

}

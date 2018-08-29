package be.sdlg.snt.dao;

import java.util.List;

import org.hibernate.Query;

import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.Study;
import be.sdlg.snt.model.StudyUser;

public class StudyDao  extends GenericDaoImpl<Study, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.Study s";
	}
	
	public List<Study> findStudiesByUser(DBUser user) {
		Query query = getCurrentSession().createQuery("select s from be.sdlg.snt.model.StudyUser su inner join su.study s where su.user.id= :userId order by s.studyName ");
		query.setParameter("userId",user.getId());
		return query.list();

	}
	
	
}

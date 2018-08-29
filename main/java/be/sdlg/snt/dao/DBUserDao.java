package be.sdlg.snt.dao;
import java.util.List;

import org.hibernate.Query;

import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.Grant;



public class DBUserDao extends GenericDaoImpl<DBUser, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.DBUser u";
	}

	public DBUser findByUserName(String userName) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.DBUser u where u.name = :userName");
		query.setParameter("userName", userName);
		if (query.list().size() == 0) return null;
		return (DBUser) query.list().get(0);
	}
	public List<DBUser> findUserByStudyByLocation(Long studyId, Long locationId, Long grant ) {
		Query query = getCurrentSession().createQuery("select su.user from be.sdlg.snt.model.StudyUser su inner join su.properList p inner join p.grantList gl " +
				"where p.studyLocation.location.id = :locationId AND p.studyUser.study.id = :studyId AND gl.value =:grant");
		query.setParameter("studyId",studyId);
		query.setParameter("locationId",locationId);
		query.setParameter("grant",grant);
		return query.list();

	}

}

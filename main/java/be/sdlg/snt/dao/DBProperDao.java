package be.sdlg.snt.dao;

import java.util.List;

import org.hibernate.Query;

import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.Grant;
import be.sdlg.snt.model.Location;
import be.sdlg.snt.model.Study;
import be.sdlg.snt.model.DBProper;

public class DBProperDao extends GenericDaoImpl<DBProper, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.DBProper pr";
	}
	public List<DBProper> findDBProperList(Study study, Location location, DBUser user) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.DBProper pr where pr.studyLocation.location.id = :locationId AND pr.studyUser.user.id=:userId AND pr.studyUser.study.id = :studyId order by pr.studyLocation.location.name");
		query.setParameter("locationId", location.getId());
		query.setParameter("userId", user.getId());
		query.setParameter("studyId",study.getId());
		return query.list();
	}
	public List<DBProper> findDBProperList(Long studyId, Long locationId) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.DBProper pr where pr.studyLocation.location.id = :locationId AND  pr.studyLocation.study.id = :studyId order by pr.studyLocation.location.name");
		query.setParameter("locationId", locationId);
		query.setParameter("studyId",studyId);
		return query.list();
	}
	public List<DBProper> hasGrant(Long studyId, Long locationId, Long userId, Long g) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.DBProper p join p.grantList gl " 
				+ " WHERE  gl.value = :grant and p.studyUser.user.id =:userId and p.studyUser.study.id = :studyId and p.studyLocation.location.id = :locationId");
		query.setParameter("locationId", locationId);
		query.setParameter("studyId",studyId);
		query.setParameter("userId", userId);
		query.setParameter("grant", g);
		return query.list();
	}
}

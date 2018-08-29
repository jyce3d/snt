package be.sdlg.snt.dao;

import java.util.List;

import org.hibernate.Query;

import be.sdlg.snt.model.DBUser;
import be.sdlg.snt.model.Location;

public class LocationDao  extends GenericDaoImpl<Location, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.Location l order by l.name";
	}
	
	public List<Location> findLocationsByStudyByUser(Long studyId, Long userId ) {
		Query query = getCurrentSession().createQuery("select l from be.sdlg.snt.model.DBProper p inner join p.studyLocation.location l inner join p.grantList gl where p.studyUser.user.id = :userId and p.studyLocation.study.id=:studyId order by l.name");
		query.setParameter("studyId",studyId);
		query.setParameter("userId",userId);
		return query.list();

	}
}

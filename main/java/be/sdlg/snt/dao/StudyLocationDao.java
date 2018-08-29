package be.sdlg.snt.dao;

import org.hibernate.Query;

import be.sdlg.snt.model.Location;
import be.sdlg.snt.model.Study;
import be.sdlg.snt.model.StudyLocation;


public class StudyLocationDao extends GenericDaoImpl<StudyLocation, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.StudyLocation sl";
	}
	public StudyLocation get(Study study, Location location) throws Exception {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.StudyLocation sl where sl.location.id = :locationId and sl.study.id = :studyId");
		query.setParameter("locationId",location.getId());
		query.setParameter("studyId",study.getId());
		int size = query.list().size();
		if (size == 0) return null;
		if (size>1) throw new Exception("Study location must be unique for given location and study.");
		return (StudyLocation) query.list().get(0);		
	}


}

package be.sdlg.snt.dao;

import java.util.List;

import org.hibernate.Query;

import be.sdlg.snt.model.StudyEventData;
import be.sdlg.snt.model.StudyEventDataAudit;

public class StudyEventDataAuditDao extends GenericDaoImpl<StudyEventData, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.StudyEventDataAudit sea";
	}
	
	public List<StudyEventDataAudit> findStudyEventDataAuditRecords(Long subjectDataId) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.StudyEventDataAudit sea where sea.clinicalData.id = :clinicalDataId ");
		query.setParameter("clinicalDataId",subjectDataId);
		return query.list();
	}

}

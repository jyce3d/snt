package be.sdlg.snt.dao;

import java.util.List;

import org.hibernate.Query;
import be.sdlg.snt.model.StudyEventData;

public class StudyEventDataDao extends GenericDaoImpl<StudyEventData, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.StudyEventData sed";
	}
	
	public List<StudyEventData> findStudyEventDataBySubject( Long subjectDataId ) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.StudyEventData sed where sed.subjectData.id= :subjectDataId");
		query.setParameter("subjectDataId",subjectDataId);
		return query.list();

	}
	public List<StudyEventData> findStudyEventDataBySubjectIdByStudyEventDefId(Long studyId, Long subjectId,  Long studyEventDefId) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.StudyEventData sed where sed.study.id = :studyId and sed.studyEventDef.id = :studyEventDefId and sed.subjectData.id = :subjectId");
		query.setParameter("studyId", studyId);
		query.setParameter("subjectId",subjectId);
		query.setParameter("studyEventDefId",studyEventDefId);
		return query.list();
	}

}

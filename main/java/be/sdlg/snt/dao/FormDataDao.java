package be.sdlg.snt.dao;

import java.util.List;

import org.hibernate.Query;

import be.sdlg.snt.model.FormData;

public class FormDataDao extends GenericDaoImpl<FormData, Long>{
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.FormData fd";
	}
	
	public List<FormData> findFormDataBySubjectIdByFormDefId(Long studyId, Long studyEventDefId, Long formDefId, Long subjectId) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.FormData fd  where fd.study.id = :studyId and fd.studyEventData.studyEventDef.id = :studyEventDefId and fd.formDef.id =:formDefId and fd.studyEventData.subjectData.id=:subjectId");
		query.setParameter("studyId",studyId);
		query.setParameter("studyEventDefId",studyEventDefId);
		query.setParameter("formDefId",formDefId);
		query.setParameter("subjectId", subjectId);
		return query.list();
		
	}
}

package be.sdlg.snt.dao;

import java.util.List;

import org.hibernate.Query;

import be.sdlg.snt.model.SubjectDataAudit;

public class SubjectDataAuditDao  extends GenericDaoImpl<SubjectDataAudit, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.SubjectDataAudit sda";
	}
	
	public List<SubjectDataAudit> findSubjectDataAuditRecords(Long subjectDataId) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.SubjectDataAudit sda where sda.clinicalData.id = :clinicalDataId ");
		query.setParameter("clinicalDataId",subjectDataId);
		return query.list();
	}
	
}

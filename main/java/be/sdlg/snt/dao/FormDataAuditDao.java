package be.sdlg.snt.dao;

import java.util.List;

import org.hibernate.Query;

import be.sdlg.snt.model.FormDataAudit;

public class FormDataAuditDao extends GenericDaoImpl<FormDataAudit, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.FormDataAudit fda";
	}
	
	public List<FormDataAudit> findFormDataAuditRecords(Long formDataId) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.FormDataAudit fda where fda.clinicalData.id = :clinicalDataId ");
		query.setParameter("clinicalDataId",formDataId);
		return query.list();
	}


}

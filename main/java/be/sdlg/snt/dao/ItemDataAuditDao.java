package be.sdlg.snt.dao;

import java.util.List;

import org.hibernate.Query;

import be.sdlg.snt.model.ItemDataAudit;


public class ItemDataAuditDao extends GenericDaoImpl<ItemDataAudit, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.ItemDataAudit sea";
	}
	
	public List<ItemDataAudit> findItemDataAuditRecords(Long itemDataId) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.ItemDataAudit sea where sea.clinicalData.id = :clinicalDataId ");
		query.setParameter("clinicalDataId",itemDataId);
		return query.list();
	}


}

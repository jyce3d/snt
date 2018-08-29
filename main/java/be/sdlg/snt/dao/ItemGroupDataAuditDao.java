package be.sdlg.snt.dao;

import java.util.List;

import org.hibernate.Query;

import be.sdlg.snt.model.ItemGroupDataAudit;


public class ItemGroupDataAuditDao extends GenericDaoImpl<ItemGroupDataAudit, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.ItemGroupDataAudit igda";
	}
	
	public List<ItemGroupDataAudit> findItemGroupDataAuditRecords(Long itemGroupDataId) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.ItemGroupDataAudit sea where sea.clinicalData.id = :clinicalDataId ");
		query.setParameter("clinicalDataId",itemGroupDataId);
		return query.list();
	}


}

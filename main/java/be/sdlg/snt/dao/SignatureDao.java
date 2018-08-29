package be.sdlg.snt.dao;

import org.hibernate.Query;

import be.sdlg.snt.model.Signature;
import be.sdlg.snt.model.ClinicalData;

public class SignatureDao extends GenericDaoImpl<Signature, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.Signature s";
	}
	public Signature findCurrentSignature(ClinicalData clinicalData) {
		return findCurrentSignature(clinicalData.getId());
	}
	public Signature findCurrentSignature(Long clinicalDataId) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.Signature s where s.clinicalData.id = :clinicalDataId order by s.id desc");
		query.setParameter("clinicalDataId",clinicalDataId);
		if (query.list().size() == 0) return null;
		return (Signature) query.list().get(0);
		
	}



}

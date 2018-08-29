package be.sdlg.snt.dao;

import java.util.List;

import org.hibernate.Query;


import be.sdlg.snt.model.SignatureDef;

public class SignatureDefDao extends GenericDaoImpl<SignatureDef, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.SignatureDef sd";
	}
	public SignatureDef findSignature(Long studyId, Long signatureUsage ) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.SignatureDef sd where sd.study.id = :studyId and sd.signatureUsage = :signatureUsage");
		query.setParameter("studyId",studyId);
		query.setParameter("signatureUsage",signatureUsage);
		return (SignatureDef) query.list().get(0);

	}

}

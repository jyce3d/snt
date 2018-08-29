package be.sdlg.snt.dao;


import org.hibernate.Query;

import be.sdlg.snt.model.MetadataVersion;
import be.sdlg.snt.model.Study;



public class MetadataVersionDao extends GenericDaoImpl<MetadataVersion, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.MetadataVersion mv";
	}
	public MetadataVersion findCurrentMetadataVersion(Study study) {
		Query query = getCurrentSession().createQuery("from be.sdlg.snt.model.MetadataVersion mv where mv.study.id = :studyId order by version desc");
		query.setParameter("studyId",study.getId());
		if (query.list().size() == 0) return null;
		return (MetadataVersion) query.list().get(0);
	}

}

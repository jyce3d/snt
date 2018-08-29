package be.sdlg.snt.dao;

import be.sdlg.snt.model.ClinicalData;

public class ClinicalDataDao  extends GenericDaoImpl<ClinicalData, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.ClinicalData cd";
	}

}

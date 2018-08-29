package be.sdlg.snt.dao;

import be.sdlg.snt.model.Description;

public class DescriptionDao extends GenericDaoImpl<Description, Long>{
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.Description d";
	}

}

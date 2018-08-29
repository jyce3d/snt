package be.sdlg.snt.dao;

import be.sdlg.snt.model.ItemGroupRef;

public class ItemGroupRefDao extends GenericDaoImpl<ItemGroupRef, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.ItemGroupRef itr";
	}

}

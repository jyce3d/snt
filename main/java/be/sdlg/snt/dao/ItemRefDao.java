package be.sdlg.snt.dao;

import be.sdlg.snt.model.ItemRef;

public class ItemRefDao extends GenericDaoImpl<ItemRef, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.ItemRef ir";
	}

}

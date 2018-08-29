package be.sdlg.snt.dao;

import be.sdlg.snt.model.ItemDef;

public class ItemDefDao extends GenericDaoImpl<ItemDef, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.ItemDef id";
	}

}

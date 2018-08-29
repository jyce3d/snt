package be.sdlg.snt.dao;

import be.sdlg.snt.model.ItemData;

public class ItemDataDao extends GenericDaoImpl<ItemData, Long> {
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.ItemData id";
	}


}

package be.sdlg.snt.dao;

import be.sdlg.snt.model.Protocol;

public class ProtocolDao extends GenericDaoImpl<Protocol, Long> {
	
	public String hqlFindAll() {
		return "from be.sdlg.snt.model.Protocol p";
	}

}

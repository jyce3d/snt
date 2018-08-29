package be.sdlg.snt.impl;
// This service is only used for authentication (not transactionnal)=> FALSE hibernate needs a transaction
// to operate.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import be.sdlg.snt.UserEx;
import be.sdlg.snt.dao.DBUserDao;
import be.sdlg.snt.model.DBUser;

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired 
	private DBUserDao dbUserDao;
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
	        UserDetails userDetails = null;
	        DBUser userEntity = dbUserDao.findByUserName(username);

	        if (userEntity == null) {
	          throw new UsernameNotFoundException("user not found");
	        }
	        userDetails = new UserEx(userEntity);

	        return userDetails;
	}
	public void setDbUserDao(DBUserDao dbUserDao) {
		this.dbUserDao = dbUserDao;
	}
	
}

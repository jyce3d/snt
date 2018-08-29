package be.sdlg.snt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;

import be.sdlg.snt.model.Authority;
import be.sdlg.snt.model.DBUser;
// On crée un specific car
// il n'y a pas de lien entre le contenu DBUser chargé par hibernate
// et le user springsecurity.userdetails.User
// UserEx est un Spring security User crée à partir d'un DBUser et qui contient la
// propriété ID de DB User. UserEx n'est pas une entité
@SuppressWarnings("deprecation")
public class UserEx extends User {
	
	private static final long serialVersionUID = 1L;
	private final Long id;

	// Constructor
	public UserEx(DBUser dbUser) {
        super (dbUser.getName(), dbUser.getPassword(), getAuthorities(dbUser));

        this.id = dbUser.getId();
    }
	
	public Long getId() {
		return id;
	}

	public static final Collection<GrantedAuthority> getAuthorities(DBUser dbUser) {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Authority authority: dbUser.getAuthorityList()) {

            authorities.add(new GrantedAuthorityImpl(authority.getAuthority()));
        }
        return authorities;
	}

}

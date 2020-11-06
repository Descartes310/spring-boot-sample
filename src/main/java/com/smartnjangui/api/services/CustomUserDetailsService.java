package com.smartnjangui.api.services;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.smartnjangui.api.repositories.entities.User;


@SuppressWarnings("serial")
@Service
public class CustomUserDetailsService implements UserDetailsService, Serializable {

	private UserService userService;
	
	public CustomUserDetailsService(UserService userService) {
		// TODO Auto-generated constructor stub
		this.userService = userService;
	}
	
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(
            		HttpStatus.NOT_FOUND, 
            		"Can't found user with that this email: "+email);
        }
        return new CustomUserPrincipal(user);
    }



	public static class CustomUserPrincipal implements UserDetails, Serializable {

    	private final User user;
    	
    	public CustomUserPrincipal(User user) {
    		this.user = user;
    	}
    	
    	
    	public User getUser() {
    		return user;
    	}

    	@Override
    	public Collection<? extends GrantedAuthority> getAuthorities() {
    		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
    		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    		return grantedAuthorities;
    	}

    	@Override
    	public String getPassword() {
    		return user.getPassword();
    	}

    	@Override
    	public String getUsername() {
    		return user.getEmail();
    	}

    	@Override
    	public boolean isAccountNonExpired() {
    		return true;
    	}

    	@Override
    	public boolean isAccountNonLocked() {
    		return true;
    	}

    	@Override
    	public boolean isCredentialsNonExpired() {
    		return true;
    	}

    	@Override
    	public boolean isEnabled() {
    		return true;
    	}

    }
    
}


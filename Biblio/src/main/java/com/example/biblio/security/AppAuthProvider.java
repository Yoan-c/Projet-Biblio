package com.example.biblio.security;

import com.example.biblio.service.UtilisateurService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class AppAuthProvider extends DaoAuthenticationProvider {
    @Autowired
    UtilisateurService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) authentication;
        String mail = auth.getName();
        String password = auth.getCredentials().toString();

        UserDetails user = userService.loadUserByUsername(mail);
        if(user == null){
            throw new BadCredentialsException("Mail/Password doesn't match for "+auth.getPrincipal());
        }
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication){
       return true;
    }
}

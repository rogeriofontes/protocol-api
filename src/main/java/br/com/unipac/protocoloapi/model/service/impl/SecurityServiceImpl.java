package br.com.unipac.protocoloapi.model.service.impl;

import br.com.unipac.protocoloapi.model.service.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecurityServiceImpl implements SecurityService {

    // private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
            return false;
        }

        return authentication.isAuthenticated();
    }

    @Override
    public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthToken);

        if (usernamePasswordAuthToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
            log.debug(String.format("Auto login %s successfully!", username));
        }
    }

    @Override
    public String getCurrentLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            // if (springSecurityUser instanceof UserDetails) {}
            return springSecurityUser.getUsername();
        } else {
            String springSecurityUser = (String) authentication.getPrincipal();
            // if (springSecurityUser instanceof String) {}
            return springSecurityUser;
        }

    }

    @Override
    public void logoff() {
        SecurityContextHolder.clearContext();
    }
}

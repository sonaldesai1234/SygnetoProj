package com.sygneto.web.rest;

import com.sygneto.security.jwt.JWTFilter;
import com.sygneto.security.jwt.TokenProvider;
import com.sygneto.web.rest.vm.LoginVM;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sygneto.domain.Authority;
import com.sygneto.domain.SocialClass;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.domain.User;
import com.sygneto.repository.UserRepository;
import com.sygneto.security.jwt.JWTFilter;
import com.sygneto.security.jwt.TokenProvider;
import com.sygneto.web.rest.vm.LoginVM;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Set;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
public class UserJWTController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    
    @Autowired
    UserRepository userRepository;

    public UserJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }
/*
    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {

        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }*/
    
    @PostMapping("/authenticate")
    public Object authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());
        SocialClass socialClass=new SocialClass();
   	    SygnetoResponse res=new SygnetoResponse();

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
       // return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
        Optional<User> user=userRepository.findOneByLogin(loginVM.getUsername());
      
        socialClass.setLogin(loginVM.getUsername());
		socialClass.setEmail(user.get().getEmail());
		socialClass.setEmployeeId(user.get().getEmployeeId());
		socialClass.setUserId(user.get().getId());
		socialClass.setFirstName(user.get().getFirstName());
		socialClass.setLastName(user.get().getLastName());
		//socialClass.setAuthorities(user.get().getAuthorities());
		 Set<String> authorities = new HashSet<>();
		 
		for(Authority role:user.get().getAuthorities())
		{
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+role.getName());
			authorities.add(role.getName());
		}
		
		socialClass.setAuthorities(authorities);
		
		socialClass.setId_token(jwt);
		res.setStatus("successful");
		res.setStatusCode(200);
		res.setData(socialClass);
        return res;
    } 

    /**
     * Object to return as body in JWT Authentication.
     */
    class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}

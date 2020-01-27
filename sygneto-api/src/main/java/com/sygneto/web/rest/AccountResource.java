package com.sygneto.web.rest;




import com.sygneto.domain.ResetPassword;
import com.sygneto.domain.ResetPasswordByAdmin;
import com.sygneto.domain.SygnetoResponse;
import com.sygneto.domain.User;
import com.sygneto.repository.UserRepository;
import com.sygneto.security.SecurityUtils;
import com.sygneto.service.MailService;
import com.sygneto.service.UserService;
import com.sygneto.service.dto.PasswordChangeDTO;
import com.sygneto.service.dto.UserDTO;
import com.sygneto.web.rest.errors.*;
import com.sygneto.web.rest.vm.KeyAndPasswordVM;
import com.sygneto.web.rest.vm.ManagedUserVM;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    private static class AccountResourceException extends RuntimeException {
        private AccountResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    private final UserRepository userRepository;

    private final UserService userService;

    private final MailService mailService;
    
    @Autowired
	private PasswordEncoder passwordEncoder;

    public AccountResource(UserRepository userRepository, UserService userService, MailService mailService) {

        this.userRepository = userRepository;
        this.userService = userService;
        this.mailService = mailService;
    }

    /**
     * {@code POST  /register} : register the user.
     *
     * @param managedUserVM the managed user View Model.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        if (!checkPasswordLength(managedUserVM.getPassword())) {
            throw new InvalidPasswordException();
        }
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationEmail(user);
    }

    /**
     * {@code GET  /activate} : activate the registered user.
     *
     * @param key the activation key.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be activated.
     */
    @GetMapping("/activate")
    public void activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountResourceException("No user was found for this activation key");
        }
    }

    /**
     * {@code GET  /authenticate} : check if the user is authenticated, and return its login.
     *
     * @param request the HTTP request.
     * @return the login if the user is authenticated.
     */
    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * {@code GET  /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be returned.
     */
    @GetMapping("/account")
    public UserDTO getAccount() {
        return userService.getUserWithAuthorities()
            .map(UserDTO::new)
            .orElseThrow(() -> new AccountResourceException("User could not be found"));
    }

    /**
     * {@code POST  /account} : update the current user information.
     *
     * @param userDTO the current user information.
     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user login wasn't found.
     */
    @PostMapping("/account")
    public void saveAccount(@Valid @RequestBody UserDTO userDTO) {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElseThrow(() -> new AccountResourceException("Current user login not found"));
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getLogin().equalsIgnoreCase(userLogin))) {
            throw new EmailAlreadyUsedException();
        }
        Optional<User> user = userRepository.findOneByLogin(userLogin);
        if (!user.isPresent()) {
            throw new AccountResourceException("User could not be found");
        }
        userService.updateUser(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
            userDTO.getLangKey(), userDTO.getImageUrl());
    }

    /**
     * {@code POST  /account/change-password} : changes the current user's password.
     *
     * @param passwordChangeDto current and new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the new password is incorrect.
     */
    @PostMapping(path = "/changePassword")
    public Object changePassword(@RequestBody PasswordChangeDTO passwordChangeDto) {
    	
        if (!checkPasswordLength(passwordChangeDto.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        userService.changePassword(passwordChangeDto.getCurrentPassword(), passwordChangeDto.getNewPassword());
        log.debug("AccountResource change password successfully: {}", userService);
		String abc = "password changed Successfully";
		SygnetoResponse res = new SygnetoResponse();
		res.setStatus("200");
		res.setMessage(abc);
		return res;
    }

    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     * @throws EmailNotFoundException {@code 400 (Bad Request)} if the email address is not registered.
     */
    @PostMapping(path = "resetPassword")
    public Object requestPasswordReset(@RequestBody ResetPassword resetPassword) {
       mailService.sendPasswordResetMail(
           userService.requestPasswordReset(resetPassword.getEmail())
               .orElseThrow(EmailNotFoundException::new)
       );
       String abc = "Reset password link sent to your mail id";
       SygnetoResponse res = new SygnetoResponse();
		res.setStatus("200");
		res.setMessage(abc);
		return res;
    }

    @PostMapping(path = "resetPasswordByAdmin")
	public Object changePasswordCustom(@RequestBody ResetPasswordByAdmin resetPasswordByAdmin) {
    	SygnetoResponse res = new SygnetoResponse();
		Optional<User> user = userRepository.findOneByLogin(resetPasswordByAdmin.getLogin());

		User userData = new User();

		if (user.isPresent()) {
			userData = user.get();

			String encryptedPassword = passwordEncoder.encode(resetPasswordByAdmin.getPassword());
			userData.setPassword(encryptedPassword);

			User userReturn = userService.updateUserByIdWithPassword(userData);

			if (userReturn != null) {
				res.setMessage("password changed successfully");
				res.setStatus("success");
			} else {
				res.setMessage("password not changed successfully");
				res.setStatus("failure");
				return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
			}

		} else {

			res.setMessage("user not found");
			res.setStatus("failure");
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}

		return res;
	}
    
    /**
     * {@code POST   /account/reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the password could not be reset.
     */
    @PostMapping(path = "/account/reset-password/finish")
    public Object finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
    	SygnetoResponse res = new SygnetoResponse();
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
        	res.setMessage("Password not matches criteria");
        	res.setStatus("Failure");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
        Optional<User> user =
            userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

    	if (!user.isPresent()) {
    		res.setMessage("Invalid session for reset password");
    		res.setStatus("Failure");
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}
		else
		{
			res.setMessage("Password reset successfully");
			res.setStatus("Success");
			return new ResponseEntity<>(res, HttpStatus.OK);
		}
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
            password.length() >= ManagedUserVM.PASSWORD_MIN_LENGTH &&
            password.length() <= ManagedUserVM.PASSWORD_MAX_LENGTH;
    }
}

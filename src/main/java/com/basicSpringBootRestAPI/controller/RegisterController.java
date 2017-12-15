package com.basicSpringBootRestAPI.controller;

import com.basicSpringBootRestAPI.entity.Role;
import com.basicSpringBootRestAPI.entity.User;
import com.basicSpringBootRestAPI.entity.VerificationToken;
import com.basicSpringBootRestAPI.enums.UserRoleEnum;
import com.basicSpringBootRestAPI.repository.RoleRepository;
import com.basicSpringBootRestAPI.repository.UserRepository;
import com.basicSpringBootRestAPI.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
@RequestMapping(value = "register")
public class RegisterController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping("/user")
    @ResponseBody
    public String registerUser(User user, HttpServletRequest httpServletRequest) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setEnabled(false);
        Role userRole = roleRepository.findByAuthority(UserRoleEnum.ROLE_USER.getValue());
        user.getRoles().add(userRole);
        userRepository.save(user);

        VerificationToken verificationToken = new VerificationToken();
        String token = UUID.randomUUID().toString();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        String authUrl = "http://"
                + httpServletRequest.getServerName()
                + ":"
                + httpServletRequest.getServerPort()
                + httpServletRequest.getContextPath()
                + "/register/verifyToken"
                + "?token=" + token;
        System.out.println(authUrl);

        return "User Registered Successfully";
    }

    @RequestMapping("/role")
    @ResponseBody
    public String registerRole() {
        if (roleRepository.count() == 0) {
            Role userRole = new Role();
            userRole.setAuthority(UserRoleEnum.ROLE_USER.getValue());
            roleRepository.save(userRole);
            Role adminRole = new Role();
            adminRole.setAuthority(UserRoleEnum.ROLE_ADMIN.getValue());
            roleRepository.save(adminRole);
            return "Roles Registered Successfully";
        } else {
            return "Roles has been Registered Already";
        }
    }

    @RequestMapping("/verifyToken")
    @ResponseBody
    public String verifyUserToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        return "User verify successfully";
    }

}

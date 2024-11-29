package com.project.btl_mmt1.service;

public interface IAuthService {
    User createUser(SignupDTO userDTO) throws Exception;
    String login(LoginDTO loginDTO) throws Exception;

}

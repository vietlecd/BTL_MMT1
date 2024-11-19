package com.project.btl_mmt1.service;

import com.project.btl_mmt1.dto.LoginDTO;
import com.project.btl_mmt1.dto.SignupDTO;
import com.project.btl_mmt1.models.User;

public interface IAuthService {
    User createUser(SignupDTO userDTO) throws Exception;
    String login(LoginDTO loginDTO) throws Exception;

}

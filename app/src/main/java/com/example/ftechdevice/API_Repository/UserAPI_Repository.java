package com.example.ftechdevice.API_Repository;

import com.example.ftechdevice.API_Service.UserAPI_Service;
import com.example.ftechdevice.Model.ModelRequestDTO.JWTObject;
import com.example.ftechdevice.Model.ModelRequestDTO.LoginRequestDTO;
import com.example.ftechdevice.Model.ModelRequestDTO.RegisterRequestDTO;
import com.example.ftechdevice.Model.ModelRequestDTO.UserCretidentialDTO;
import com.example.ftechdevice.Model.ModelRespone.LoginResponse;
import com.example.ftechdevice.Model.ModelRespone.MessageRespone;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;
import retrofit2.Call;

@ActivityScoped
public class UserAPI_Repository {

    private final UserAPI_Service userapiService;

    @Inject
    public UserAPI_Repository(UserAPI_Service userapiService) {
        this.userapiService = userapiService;
    }

    public Call<JWTObject> getUserCretidential(UserCretidentialDTO userCretidentialDTO) {
        return userapiService.getUserCretidential(userCretidentialDTO);
    }

    public Call<MessageRespone> callRegister(RegisterRequestDTO registerRequestDTO) {
        return userapiService.callRegisterUser(registerRequestDTO);
    }
    // Nhan
    public Call<LoginResponse> loginUser(LoginRequestDTO loginRequestDTO) {
        return userapiService.loginUser(loginRequestDTO);
    }



}
package com.example.ftechdevice.API_Repository;

import com.example.ftechdevice.API_Service.UserAPI_Service;
import com.example.ftechdevice.Model.ModelRequestDTO.JWTObject;
import com.example.ftechdevice.Model.ModelRequestDTO.LoginRequestDTO;
import com.example.ftechdevice.Model.ModelRequestDTO.RegisterRequestDTO;
import com.example.ftechdevice.Model.ModelRequestDTO.UserCretidentialDTO;
import com.example.ftechdevice.Model.ModelRequestDTO.UserRequestDTO;
import com.example.ftechdevice.Model.ModelRespone.FileUploadResponse;
import com.example.ftechdevice.Model.ModelRespone.LoginResponse;
import com.example.ftechdevice.Model.ModelRespone.MessageRespone;
import com.example.ftechdevice.Model.ModelRespone.RegisterResponseDTO;
import com.example.ftechdevice.Model.ModelRespone.UserResponseDTO;

import javax.inject.Inject;

import dagger.hilt.android.scopes.ActivityScoped;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
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

    public Call<RegisterResponseDTO> registerUser(RegisterRequestDTO registerRequestDTO) {
        return userapiService.registerUser(registerRequestDTO);
    }

    public Call<UserResponseDTO> getUserByEmail(String token, String email) {
        return userapiService.getUserByEmail(token,email);
    }

    public Call<ResponseBody> uploadFile(String token, MultipartBody.Part file) {
        return userapiService.uploadFile(token, file);
    }
    public Call<UserResponseDTO> updateUser(String token, String email, UserRequestDTO userRequestDTO) {
        return userapiService.updateUser("Bearer " + token, email, userRequestDTO);
    }


}
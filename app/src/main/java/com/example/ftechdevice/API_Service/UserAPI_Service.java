package com.example.ftechdevice.API_Service;


import com.example.ftechdevice.Model.ModelRequestDTO.JWTObject;
import com.example.ftechdevice.Model.ModelRequestDTO.RegisterRequestDTO;
import com.example.ftechdevice.Model.ModelRequestDTO.UserCretidentialDTO;
import com.example.ftechdevice.Model.ModelRespone.MessageRespone;
import com.example.ftechdevice.Model.ModelRespone.TokenReponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserAPI_Service {

    @POST("/auth/sign-in")
    Call<JWTObject> getUserCretidential(@Body UserCretidentialDTO userCretidentialDTO);

    @POST("/auth/sign-up")
    Call<MessageRespone> callRegisterUser(@Body RegisterRequestDTO registerRequestDTO);





}
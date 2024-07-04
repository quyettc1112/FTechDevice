package com.example.ftechdevice.API_Service;


import com.example.ftechdevice.Model.ModelRequestDTO.JWTObject;
import com.example.ftechdevice.Model.ModelRequestDTO.LoginRequestDTO;
import com.example.ftechdevice.Model.ModelRequestDTO.RegisterRequestDTO;
import com.example.ftechdevice.Model.ModelRequestDTO.UserCretidentialDTO;
import com.example.ftechdevice.Model.ModelRespone.FileUploadResponse;
import com.example.ftechdevice.Model.ModelRespone.LoginResponse;
import com.example.ftechdevice.Model.ModelRespone.MessageRespone;
import com.example.ftechdevice.Model.ModelRespone.RegisterResponseDTO;
import com.example.ftechdevice.Model.ModelRespone.TokenReponse;
import com.example.ftechdevice.Model.ModelRespone.UserResponseDTO;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserAPI_Service {

    @POST("/auth/sign-in")
    Call<JWTObject> getUserCretidential(@Body UserCretidentialDTO userCretidentialDTO);

    @POST("/auth/sign-up")
    Call<MessageRespone> callRegisterUser(@Body RegisterRequestDTO registerRequestDTO);

    @POST("/api/auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequestDTO loginRequestDTO);

    @POST("/api/auth/register")
    Call<RegisterResponseDTO> registerUser(@Body RegisterRequestDTO registerRequestDTO);

    @GET("/api/auth/user")
    Call<UserResponseDTO> getUserByEmail(@Header("Authorization") String token , @Query("email") String email);

    @Multipart
    @POST("/api/files")
    Call<ResponseBody> uploadFile(@Header("Authorization") String token, @Part MultipartBody.Part file);


}
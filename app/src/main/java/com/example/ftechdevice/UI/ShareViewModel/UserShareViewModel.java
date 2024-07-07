package com.example.ftechdevice.UI.ShareViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ftechdevice.Model.ModelRequestDTO.JWTObject;
import com.example.ftechdevice.Model.ModelRequestDTO.LoginRequestDTO;
import com.example.ftechdevice.Model.ModelRequestDTO.UserCretidentialDTO;
import com.example.ftechdevice.Model.ModelRespone.LoginResponse;
import com.example.ftechdevice.Model.ModelRespone.UserResponseDTO;

public class UserShareViewModel extends ViewModel {
    private final MutableLiveData<UserCretidentialDTO> _userCredentials = new MutableLiveData<>();
    public LiveData<UserCretidentialDTO> getUserCredentials() {
        return _userCredentials;
    }
    private final MutableLiveData<UserResponseDTO> _userResponse = new MutableLiveData<>();

    public LiveData<UserResponseDTO> getUserResponse() {
        return _userResponse;
    }
    private final MutableLiveData<LoginRequestDTO> _loginCredentials = new MutableLiveData<>();

    public LiveData<LoginRequestDTO> getloginCredentials() {
        return _loginCredentials;
    }
    ///
    private final MutableLiveData<JWTObject> _jwtToken = new MutableLiveData<>();
    public LiveData<JWTObject> getJwtToken() {
        return _jwtToken;
    }


    // User Credentials
    public void updateUserCretidential(UserCretidentialDTO userCretidentialDTO) {
        _userCredentials.setValue(userCretidentialDTO);
    }

    public UserCretidentialDTO getUserCretidentail() {
        return _userCredentials.getValue();
    }
    //
    public void updateLoginCretidential(LoginRequestDTO loginRequestDTO) {
        _loginCredentials.setValue(loginRequestDTO);
    }

    public LoginRequestDTO getloginCretidentail() {
        return _loginCredentials.getValue();
    }
    //

    // User JWT
    public void updateJWTToken(JWTObject jwtObject) {
        _jwtToken.setValue(jwtObject);
    }

    public JWTObject getJWTToken() {
        return _jwtToken.getValue();
    }

    // Function to clear JWT Token
    public void clearJWTToken() {
        _jwtToken.setValue(null);
    }

}

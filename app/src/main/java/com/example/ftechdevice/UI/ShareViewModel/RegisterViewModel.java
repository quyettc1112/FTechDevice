package com.example.ftechdevice.UI.ShareViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ftechdevice.Model.ModelRequestDTO.RegisterRequestDTO;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterViewModel extends ViewModel {

    @Inject
    public RegisterViewModel() {
    }

    private final MutableLiveData<String> _email = new MutableLiveData<>();
    private final MutableLiveData<String> _password = new MutableLiveData<>();
    private final MutableLiveData<String> _phone = new MutableLiveData<>();
    private final MutableLiveData<String> _name = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _gender = new MutableLiveData<>();

    public LiveData<String> getEmail() {
        return _email;
    }

    public LiveData<String> getPassword() {
        return _password;
    }

    public LiveData<String> getPhone() {
        return _phone;
    }

    public LiveData<String> getName() {
        return _name;
    }

    public LiveData<Boolean> getGender() {
        return _gender;
    }



    public void updateEmail(String newEmail) {
        _email.setValue(newEmail);
    }

    public void updatePassword(String newPassword) {
        _password.setValue(newPassword);
    }

    public void updatePhone(String newPhone) {
        String formattedPhone = formatPhoneNumber(newPhone);
        if (formattedPhone != null) {
            _phone.setValue(formattedPhone);
        }
    }

    private String formatPhoneNumber(String phone) {
        if (phone.length() == 10 && phone.startsWith("0")) {
            return "+84" + phone.substring(1);
        } else {
            return null;
        }
    }

    public void updateName(String newName) {
        _name.setValue(newName);
    }

    public void updateGender(Boolean newGender) {
        _gender.setValue(newGender);
    }

    public RegisterRequestDTO getRegisterDTO() {
        return new RegisterRequestDTO(
                _email.getValue(),
                _password.getValue(),
                _name.getValue(),
                _phone.getValue(),
                _gender.getValue()
        );
    }

    public void clearAllFields() {
        _email.setValue("");
        _password.setValue("");
        _phone.setValue("");
        _name.setValue("");
        _gender.setValue(false);
    }
}
package com.example.ftechdevice.UI.ShareViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ftechdevice.Model.ModelRequestDTO.UserRequestDTO;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UpdateViewModel extends ViewModel {

    private final MutableLiveData<String> _email = new MutableLiveData<>();
    private final MutableLiveData<String> _username = new MutableLiveData<>();
    private final MutableLiveData<String> _fullName = new MutableLiveData<>();
    private final MutableLiveData<String> _phone = new MutableLiveData<>();
    private final MutableLiveData<String> _address = new MutableLiveData<>();
    private final MutableLiveData<String> _avatar = new MutableLiveData<>();

    @Inject
    public UpdateViewModel() {
    }

    public LiveData<String> getEmail() {
        return _email;
    }

    public LiveData<String> getUsername() {
        return _username;
    }

    public LiveData<String> getFullName() {
        return _fullName;
    }

    public LiveData<String> getPhone() {
        return _phone;
    }

    public LiveData<String> getAddress() {
        return _address;
    }

    public LiveData<String> getAvatar() {
        return _avatar;
    }

    public void updateEmail(String newEmail) {
        _email.setValue(newEmail);
    }

    public void updateUsername(String newUsername) {
        _username.setValue(newUsername);
    }

    public void updateFullName(String newFullName) {
        _fullName.setValue(newFullName);
    }

    public void updatePhone(String newPhone) {
        _phone.setValue(newPhone);
    }

    public void updateAddress(String newAddress) {
        _address.setValue(newAddress);
    }

    public void updateAvatar(String newAvatar) {
        _avatar.setValue(newAvatar);
    }

    public UserRequestDTO getUserRequestDTO() {
        return new UserRequestDTO(
                _email.getValue(),
                _username.getValue(),
                _fullName.getValue(),
                _phone.getValue(),
                _address.getValue(),
                _avatar.getValue()
        );
    }

    public void clearAllFields() {
        _email.setValue("");
        _username.setValue("");
        _fullName.setValue("");
        _phone.setValue("");
        _address.setValue("");
        _avatar.setValue("");
    }
}

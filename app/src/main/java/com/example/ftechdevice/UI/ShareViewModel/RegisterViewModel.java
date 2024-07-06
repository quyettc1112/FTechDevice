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
    private final MutableLiveData<String> _username = new MutableLiveData<>();
    private final MutableLiveData<String> _password = new MutableLiveData<>();
    private final MutableLiveData<String> _fullName = new MutableLiveData<>();
    private final MutableLiveData<String> _phone = new MutableLiveData<>();
    private final MutableLiveData<String> _address = new MutableLiveData<>();
    private final MutableLiveData<Integer> _roleId = new MutableLiveData<>();
    private final MutableLiveData<String> _avatar = new MutableLiveData<>();

    public LiveData<String> getEmail() {
        return _email;
    }

    public LiveData<String> getUsername() {
        return _username;
    }

    public LiveData<String> getPassword() {
        return _password;
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

    public LiveData<Integer> getRoleId() {
        return _roleId;
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

    public void updatePassword(String newPassword) {
        _password.setValue(newPassword);
    }

    public void updateFullName(String newFullName) {
        _fullName.setValue(newFullName);
    }

    public void updatePhone(String newPhone) {
        String formattedPhone = formatPhoneNumber(newPhone);
        if (formattedPhone != null) {
            _phone.setValue(formattedPhone);
        }
    }

    public void updateAddress(String newAddress) {
        _address.setValue(newAddress);
    }

    public void updateRoleId(int newRoleId) {
        _roleId.setValue(newRoleId);
    }

    public void updateAvatar(String newAvatar) {
        _avatar.setValue(newAvatar);
    }

    private String formatPhoneNumber(String phone) {
        if (phone.length() == 10 && phone.startsWith("0")) {
            return phone.substring(1);
        } else {
            return " ";
        }
    }

    public RegisterRequestDTO getRegisterDTO() {
        return new RegisterRequestDTO(
                _email.getValue(),
                _username.getValue(),
                _password.getValue(),
                _fullName.getValue(),
                _phone.getValue(),
                _address.getValue(),
                _roleId.getValue(),
                _avatar.getValue()
        );
    }

    public void clearAllFields() {
        _email.setValue("");
        _username.setValue("");
        _password.setValue("");
        _fullName.setValue("");
        _phone.setValue("");
        _address.setValue("");
        _roleId.setValue(null);
        _avatar.setValue("");
    }
}

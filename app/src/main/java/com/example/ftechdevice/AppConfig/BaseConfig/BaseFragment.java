package com.example.ftechdevice.AppConfig.BaseConfig;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ftechdevice.R;

public class BaseFragment extends Fragment {

    public BaseFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    protected void navigateToPage(int actionId) {
        NavHostFragment.findNavController(this).navigate(actionId);
    }

    protected void navigateToPage(int actionId, Bundle bundle) {
        NavHostFragment.findNavController(this).navigate(actionId, bundle);
    }

    protected void navigateToPage(NavDirections direction) {
        NavHostFragment.findNavController(this).navigate(direction);
    }

    protected void showLoading(String title, String message, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        if (requireActivity() instanceof BaseActivity) {
            ((BaseActivity) requireActivity()).showLoading(title, message, cancelable, cancelListener);
        }
    }

    protected void showLoading(String title, String message) {
        showLoading(title, message, false, null);
    }

    protected void hideLoading() {
        if (requireActivity() instanceof BaseActivity) {
            ((BaseActivity) requireActivity()).hideLoading();
        }
    }

    protected void showErrorMessage(int messageId) {
        String message = requireContext().getString(messageId);
        showErrorMessage(message);
    }

    protected void showErrorMessage(String message) {
        if (requireActivity() instanceof BaseActivity) {
            ((BaseActivity) requireActivity()).showErrorDialog(message);
        }
    }

    protected void showNotify(String title, String message) {

    }

    private String getDefaultNotifyTitle() {
        return getString(R.string.default_notify_title);
    }

    protected void showNotify(int titleId, int messageId) {
        if (requireActivity() instanceof BaseActivity) {
            ((BaseActivity) requireActivity()).showNotifyDialog(titleId, messageId);
        }
    }

    protected void showLoadingMore(boolean isShow) {
        // Implement your logic here
    }
}

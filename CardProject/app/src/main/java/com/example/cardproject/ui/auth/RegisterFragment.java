package com.example.cardproject.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cardproject.R;
import com.example.cardproject.util.OperationResult;
import com.example.cardproject.viewmodel.RegisterViewModel;

public class RegisterFragment extends Fragment {

    private RegisterViewModel mViewModel;

    private EditText name, lastName, address, email, password, confirmPassword;

    private Button registerBtn;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupListeners(view);
        setupObservers();

    }

    private void setupListeners(View view) {

        mViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        name = (EditText) view.findViewById(R.id.registerFirstNameET);
        lastName = (EditText) view.findViewById(R.id.registerLastNameET);
        address = (EditText) view.findViewById(R.id.registerAddressET);
        email = (EditText) view.findViewById(R.id.registerEmailET);
        password = (EditText) view.findViewById(R.id.registerPasswordET);
        confirmPassword = (EditText) view.findViewById(R.id.registerConfirmPasswordET);

        registerBtn = (Button) view.findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean valid = isValid(name.getText().toString(), lastName.getText().toString(), address.getText().toString(), email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString());

                if (valid) {
                    mViewModel.registerUser(name.getText().toString(), lastName.getText().toString(), address.getText().toString(), email.getText().toString(), password.getText().toString());
                }
            }
        });

    }

    private void setupObservers() {
        mViewModel.registerUserResult.observe(getViewLifecycleOwner(), new Observer<OperationResult>() {
            @Override
            public void onChanged(OperationResult operationResult) {
                switch (operationResult.status) {
                    case SUCCESS:
                        Toast.makeText(requireContext(), operationResult.message, Toast.LENGTH_SHORT).show();

                        Navigation.findNavController(requireActivity(), R.id.auth_nav_graph).popBackStack();
                        break;
                    case ERROR:
                        Toast.makeText(requireContext(), operationResult.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private boolean isValid(String name, String lastName, String address, String email, String password, String confirmPassword) {
        if (name.isEmpty()) {
            this.name.setError(getResources().getString(R.string.name_required));
            return false;
        }

        if (lastName.isEmpty()) {
            this.lastName.setError(getResources().getString(R.string.last_name_required));
            return false;
        }

        if (address.isEmpty()) {
            this.address.setError(getResources().getString(R.string.address_required));
            return false;
        }

        if (email.isEmpty()) {
            this.email.setError(getResources().getString(R.string.email_required));
            return false;
        }

        if (password.isEmpty()) {
            this.password.setError(getResources().getString(R.string.password_required));
            return false;
        }

        if (confirmPassword.isEmpty()) {
            this.confirmPassword.setError(getResources().getString(R.string.password_required));
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email.setError(getResources().getString(R.string.email_wrong_format));
            return false;
        }

        if (password.length() < 6) {
            this.password.setError(getResources().getString(R.string.password_length_error));
            return false;
        }

        if (!confirmPassword.equals(password)) {
            this.confirmPassword.setError(getResources().getString(R.string.password_no_match));
            return false;
        }

        return true;
    }
}
package com.example.cardproject.ui.auth;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cardproject.R;
import com.example.cardproject.ui.DashboardActivity;
import com.example.cardproject.util.OperationResult;
import com.example.cardproject.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel mViewModel;

    private EditText email, password;
    
    private Button loginButton, registerButton;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        setupViewModel();
        setupListeners(view);
        setupObservers();
    }

    private void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void setupListeners(View view) {

        email = (EditText) view.findViewById(R.id.loginEmailET);
        password = (EditText) view.findViewById(R.id.loginPasswordET);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        registerButton = (Button) view.findViewById(R.id.loginRegisterBtn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(requireActivity(), R.id.auth_nav_graph).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = validateInput(email.getText().toString(), password.getText().toString());

                if (isValid) {
                    mViewModel.loginUser(email.getText().toString(), password.getText().toString());
                }
            }
        });
    }
    private void setupObservers() {
        mViewModel.loginUserResult.observe(getViewLifecycleOwner(), new Observer<OperationResult>() {
            @Override
            public void onChanged(OperationResult operationResult) {
                switch (operationResult.status) {
                    case SUCCESS:
                        Toast.makeText(requireContext(), operationResult.message, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(requireActivity(), DashboardActivity.class);
                        requireActivity().startActivity(intent);
                        requireActivity().finish();

                        break;
                    case ERROR:
                        Toast.makeText(requireContext(), operationResult.message, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            this.email.setError(getResources().getString(R.string.email_required));
            return false;
        }
        if (password.isEmpty()) {
            this.password.setError(getResources().getString(R.string.password_required));
            return false;
        }

        return true;
    }
}
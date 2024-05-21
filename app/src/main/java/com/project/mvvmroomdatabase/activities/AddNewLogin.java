package com.project.mvvmroomdatabase.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.project.mvvmroomdatabase.R;
import com.project.mvvmroomdatabase.clickhandlers.SaveDataClickHandler;
import com.project.mvvmroomdatabase.databinding.ActivityAddNewLoginBinding;
import com.project.mvvmroomdatabase.datasource.Logins;
import com.project.mvvmroomdatabase.views.ViewModel;

public class AddNewLogin extends AppCompatActivity {

    private SaveDataClickHandler handler;
    private Logins logins;
    private ActivityAddNewLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Set activity layout
        setContentView(R.layout.activity_add_new_login);

        // Apply system insets to the main layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Logins object
        logins = new Logins();

        // Bind the layout with data binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_login);

        // Initialize ViewModel
        ViewModel viewModel = new ViewModelProvider(this).get(ViewModel.class);

        // Initialize SaveDataClickHandler
        handler = new SaveDataClickHandler(this, logins, viewModel);

        // Set click handler for the layout
        binding.setClickHandler(handler);
        binding.setLoginViewModel(logins);
    }
}

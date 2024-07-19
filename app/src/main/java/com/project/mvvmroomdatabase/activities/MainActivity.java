package com.project.mvvmroomdatabase.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mvvmroomdatabase.R;
import com.project.mvvmroomdatabase.adapters.Adapter;
import com.project.mvvmroomdatabase.clickhandlers.MainActivityClickHandler;
import com.project.mvvmroomdatabase.clickhandlers.SaveDataClickHandler;
import com.project.mvvmroomdatabase.database.LoginDatabase;
import com.project.mvvmroomdatabase.databinding.ActivityAddNewLoginBinding;
import com.project.mvvmroomdatabase.databinding.ActivityMainBinding;
import com.project.mvvmroomdatabase.datasource.Logins;
import com.project.mvvmroomdatabase.views.Repository;
import com.project.mvvmroomdatabase.views.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Click Handlers
    private MainActivityClickHandler mainHandler;
    private SaveDataClickHandler loginHandler;



    // Data Binding
    private ActivityMainBinding mainBinding;
    private ActivityAddNewLoginBinding loginBinding;

    // Data source
    private ArrayList<Logins> loginsArrayList = new ArrayList<>();
    private LoginDatabase database;

    // Adapter
    private Adapter adapter;

    // ViewModel
    private ViewModel viewModel;

    // Repository
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable edge-to-edge display
        EdgeToEdge.enable(this);

        // Bind the layout with data binding
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Apply system insets to the main layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize main activity click handler and EditLogin Click Handler
        mainHandler = new MainActivityClickHandler(this);
        mainBinding.setClick(mainHandler);



        // Set up RecyclerView
        RecyclerView recyclerView = mainBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Initialize database
        database = LoginDatabase.getInstance(this);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        // Observe changes in data from the ViewModel
        viewModel.getAllLogins().observe(this, new Observer<List<Logins>>() {
            @Override
            public void onChanged(List<Logins> logins) {
                loginsArrayList.clear();
                for (Logins c : logins)
                    loginsArrayList.add(c);
                adapter.notifyDataSetChanged(); // Notify adapter of data change
            }
        });

        adapter = new Adapter(loginsArrayList); // Initialize adapter
        recyclerView.setAdapter(adapter); // Set adapter to RecyclerView


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            // onMove() is not used here, so return false
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; // Not used here, returning false to indicate that no move action is allowed
            }

            // onSwiped() is called when the item is swiped
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                    // Get the login item corresponding to the swiped ViewHolder
                    Logins l = loginsArrayList.get(viewHolder.getAdapterPosition());
                    if (direction== ItemTouchHelper.RIGHT) {
                    // Delete the item from the ViewModel
                    viewModel.delete(l);
                }
                    else{
                        Intent i=new Intent(MainActivity.this, Edit.class);
                        i.putExtra("id",l.getId());
                        startActivity(i);



                    }
            }
        }).attachToRecyclerView(recyclerView);





    }
    @Override
    public void onBackPressed() {
        // Custom behavior when the back button is pressed
        super.onBackPressed();
        // Finish the current activity and all parent activities
        finishAffinity();
    }
}

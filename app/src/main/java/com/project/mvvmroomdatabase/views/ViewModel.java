package com.project.mvvmroomdatabase.views;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.mvvmroomdatabase.datasource.Logins;

import java.util.List;

/**
 * ViewModel class that provides data to the UI and acts as a communication center
 * between the Repository and the UI. This class is lifecycle-aware and survives
 * configuration changes.
 */
public class ViewModel extends AndroidViewModel {

    private final Repository repository;
    private LiveData<Logins> byId;
    private LiveData<List<Logins>> allLogins;

    /**
     * Constructor for the ViewModel class.
     *
     * @param application The application context.
     */
    public ViewModel(@NonNull Application application) {
        super(application);
        this.repository = new Repository(application);
        this.allLogins = repository.getAllLogins();
    }

    /**
     * Returns a LiveData list of all login entries.
     *
     * @return A LiveData list of all login entries.
     */
    public LiveData<List<Logins>> getAllLogins() {
        return allLogins;
    }

    /**
     * Deletes a login entry from the repository.
     *
     * @param logins The login entry to be deleted.
     */
    public void delete(Logins logins) {
        repository.delete(logins);
    }

    /**
     * Updates a login entry in the repository.
     *
     * @param logins The login entry to be updated.
     */
    public void change(Logins logins) {
        repository.change(logins);
    }

    /**
     * give any particular login by id and survive configuration changes
     * @param id
     * @return
     */
    public LiveData findById(int id){
       byId= repository.getById(id);
       return byId;
    }

    /**
     * Inserts a login entry into the repository.
     *
     * @param logins The login entry to be inserted.
     */
    public void add(Logins logins) {
        repository.insert(logins);
    }

}

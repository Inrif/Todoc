package com.cleanup.todoc.model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cleanup.todoc.model.pojos.Task;

import java.util.List;

@Dao
public interface TaskDao {


    // METHODS -------------------------------------------------------------------------------------

    // -- CREATE --

    @Insert
    long insertTask(final Task task);

    // -- READ --

    @Query("SELECT * FROM task")
    LiveData<List<Task>> getTasks();

    @Query("SELECT * FROM task WHERE id = :taskId")
    LiveData<Task> getTaskById(final long taskId);

    // -- UPDATE --

    @Update
    int updateTask(final Task task);

    // -- DELETE --

    @Query("DELETE FROM task WHERE id = :taskId")
    int deleteTaskById(final long taskId);
}

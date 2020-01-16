package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.pojos.Task;

import java.util.List;

import javax.inject.Inject;

public class TaskRepositoryImpl implements Repository.TaskRepository {

    // FIELDS --------------------------------------------------------------------------------------

    @Inject
    TaskDao mTaskDao;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     */
    @Inject
    public TaskRepositoryImpl() {}

    /**
     * Constructor
     * @param taskDao a {@link TaskDao}
     */
    @VisibleForTesting
    public TaskRepositoryImpl(@NonNull TaskDao taskDao) {
        this.mTaskDao = taskDao;
    }

    // METHODS -------------------------------------------------------------------------------------

    // -- CREATE --

    @Override
    public long insertTask(@NonNull final Task task) {
        return this.mTaskDao.insertTask(task);
    }

    // -- READ --

    @NonNull
    @Override
    public LiveData<List<Task>> getTasks() {
        return this.mTaskDao.getTasks();
    }

    // -- DELETE --

    @Override
    public int deleteTaskById(final long taskId) {
        return this.mTaskDao.deleteTaskById(taskId);
    }
}

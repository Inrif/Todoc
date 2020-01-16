package com.cleanup.todoc.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.cleanup.todoc.model.pojos.Project;
import com.cleanup.todoc.model.pojos.Task;
import com.cleanup.todoc.repositories.Repository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel{

    // FIELDS --------------------------------------------------------------------------------------

    @NonNull
    private final Repository.ProjectRepository mProjectRepository;
    @NonNull
    private final Repository.TaskRepository mTaskRepository;
    @NonNull
    private final Executor mExecutor;

    private LiveData<List<Project>> mProjects;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     * @param projectRepository a {@link Repository.ProjectRepository}
     * @param taskRepository a {@link Repository.TaskRepository}
     * @param executor a {@link Executor}
     */
    public TaskViewModel(@NonNull Repository.ProjectRepository projectRepository,
                         @NonNull Repository.TaskRepository taskRepository,
                         @NonNull Executor executor) {
        this.mProjectRepository = projectRepository;
        this.mTaskRepository = taskRepository;
        this.mExecutor = executor;
    }

    // METHODS -------------------------------------------------------------------------------------

    // -- INITIALISATION --

    /**
     * Initializes the {@link ViewModel}
     */
    public void init() {
        // PROJECTS
        if (this.mProjects == null) {
            this.mProjects = this.mProjectRepository.getProjects();
        }
    }

    // -- TASKS --

    /**
     * Inserts a {@link Task} into database in asynchronous way
     * @param task a {@link Task}
     */
    public void insertTask(@NonNull final Task task) {
        this.mExecutor.execute(() -> this.mTaskRepository.insertTask(task));
    }

    /**
     * Returns a {@link LiveData} of {@link List<Task>}
     * @return a {@link LiveData} of {@link List<Task>}
     */
    @NonNull
    public LiveData<List<Task>> getTasks() {
        return this.mTaskRepository.getTasks();
    }

    /**
     * Deletes a {@link Task} into database in asynchronous way
     * @param taskId an integer that contains the id value
     */
    public void deleteTaskById(final long taskId) {
        this.mExecutor.execute(() -> this.mTaskRepository.deleteTaskById(taskId));
    }

    // -- PROJECTS --

    /**
     * Returns a {@link LiveData} of {@link List<Project>}
     * @return a {@link LiveData} of {@link List<Project>}
     */
    @NonNull
    public LiveData<List<Project>> getProjects() {
        return this.mProjects;
    }
}

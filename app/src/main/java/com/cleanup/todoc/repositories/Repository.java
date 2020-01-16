package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cleanup.todoc.model.pojos.Project;
import com.cleanup.todoc.model.pojos.Task;

import java.util.List;

public interface Repository {
    // INTERFACES ----------------------------------------------------------------------------------

    // -- PROJECT REPOSITORY --

    interface ProjectRepository {

        // METHODS ---------------------------------------------------------------------------------

        // -- CREATE --

        /**
         * Inserts a {@link Project} into database and returns the id value
         * @param project a {@link Project}
         * @return an integer that contains the id value
         */
        long insertProject(@NonNull final Project project);

        // -- READ --

        /**
         * Returns a {@link LiveData} of {@link List <Project>}
         * @return a {@link LiveData} of {@link List<Project>}
         */
        @NonNull
        LiveData<List<Project>> getProjects();

        /**
         * Returns a {@link LiveData} of {@link Project} which has the same id that the argument
         * @param projectId an integer that contains the id value
         * @return a {@link LiveData} of {@link Project}
         */
        @NonNull
        LiveData<Project> getProjectById(final long projectId);
    }

    // -- TASK REPOSITORY --

    interface TaskRepository {

        // METHODS ---------------------------------------------------------------------------------

        // -- CREATE --

        /**
         * Inserts a {@link Task} into database and returns the id value
         * @param task a {@link Task}
         * @return an integer that contains the id value
         */
        long insertTask(@NonNull final Task task);

        // -- READ --

        /**
         * Returns a {@link LiveData} of {@link List<Task>}
         * @return a {@link LiveData} of {@link List<Task>}
         */
        @NonNull
        LiveData<List<Task>> getTasks();

        /**
         * Deletes a {@link Task} into database and returns the deleted tuple number
         * @param taskId an integer that contains the id value
         * @return an integer that contains the deleted tuple number
         */
        int deleteTaskById(final long taskId);
    }
}

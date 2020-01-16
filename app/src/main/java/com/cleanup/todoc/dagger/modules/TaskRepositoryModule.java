package com.cleanup.todoc.dagger.modules;

import com.cleanup.todoc.repositories.Repository;
import com.cleanup.todoc.repositories.TaskRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class TaskRepositoryModule {

     /*
        Information:    Singleton annotation is not necessary because "IN THIS ONLY CASE",
                        the Application instance, "TodocApplication", is unique.

        see: https://github.com/google/dagger/issues/832#issuecomment-320508239
     */

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Creates a bind between the {@link Repository.TaskRepository} interface
     * and the {@link TaskRepositoryImpl} class
     * @param repository the {@link TaskRepositoryImpl} class to instance
     * @return a {@link Repository.TaskRepository} interface
     */
    @Singleton
    @Binds
    abstract Repository.TaskRepository bindTaskRepository(TaskRepositoryImpl repository);
}

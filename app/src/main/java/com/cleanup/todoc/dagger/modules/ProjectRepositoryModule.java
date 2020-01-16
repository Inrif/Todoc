package com.cleanup.todoc.dagger.modules;

import com.cleanup.todoc.repositories.ProjectRepositoryImpl;
import com.cleanup.todoc.repositories.Repository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ProjectRepositoryModule {

     /*
        Information:    Singleton annotation is not necessary because "IN THIS ONLY CASE",
                        the Application instance, "TodocApplication", is unique.

        see: https://github.com/google/dagger/issues/832#issuecomment-320508239
     */

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Creates a bind between the {@link Repository.ProjectRepository} interface
     * and the {@link ProjectRepositoryImpl} class
     * @param repository the {@link ProjectRepositoryImpl} class to instance
     * @return a {@link Repository.ProjectRepository} interface
     */
    @Singleton
    @Binds
    abstract Repository.ProjectRepository bindProjectRepository(ProjectRepositoryImpl repository);
}

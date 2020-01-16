package com.cleanup.todoc.repositories;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.pojos.Project;

import java.util.List;

import javax.inject.Inject;


public class ProjectRepositoryImpl implements Repository.ProjectRepository {

    // FIELDS --------------------------------------------------------------------------------------

    @Inject
    ProjectDao mProjectDao;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Constructor
     */
    @Inject
    public ProjectRepositoryImpl() {}

    /**
     * Constructor
     * @param projectDao a {@link ProjectDao}
     */
    @VisibleForTesting
    public ProjectRepositoryImpl(@NonNull ProjectDao projectDao) {
        this.mProjectDao = projectDao;
    }

    // METHODS -------------------------------------------------------------------------------------

    // -- CREATE --

    @Override
    public long insertProject(@NonNull final Project project) {
        return this.mProjectDao.insertProject(project);
    }

    // -- READ --

    @NonNull
    @Override
    public LiveData<List<Project>> getProjects() {
        return this.mProjectDao.getProjects();
    }

    @NonNull
    @Override
    public LiveData<Project> getProjectById(final long projectId) {
        return this.mProjectDao.getProjectById(projectId);
    }
}

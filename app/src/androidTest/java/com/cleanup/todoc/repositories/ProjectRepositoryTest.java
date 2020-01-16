package com.cleanup.todoc.repositories;


import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.utils.LiveDataTestUtil;
import com.cleanup.todoc.model.database.TodocDatabase;
import com.cleanup.todoc.model.pojos.Project;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Abbesolo on 28/12/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.repositories
 *
 * Android test on {@link Repository.ProjectRepository}.
 */
@RunWith(AndroidJUnit4.class)

public class ProjectRepositoryTest {
    // FIELDS --------------------------------------------------------------------------------------

    private TodocDatabase mDatabase;
    private Repository.ProjectRepository mRepository;

    private final long FIRST_PROJECT_ID = 1;
    private final Project FIRST_PROJECT = new Project("Project 1", 0xFFEADAD1);

    private final long SECOND_PROJECT_ID = 2;
    private final Project SECOND_PROJECT = new Project("Project 2", 0xFFB4CDBA);

    // RULES (Synchronized Tests) ------------------------------------------------------------------

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    // METHODS -------------------------------------------------------------------------------------

    @Before
    public void setUp() {
        this.mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();

        this.mRepository = new ProjectRepositoryImpl(this.mDatabase.mProjectDao());
    }

    @After
    public void tearDown() {
        this.mDatabase.close();
    }

    // -- CREATE --

    @Test
    public void insertProject_shouldBeSuccess() {
        final long insertResult = this.mRepository.insertProject(FIRST_PROJECT);

        // TEST: Good insertion
        assertEquals(FIRST_PROJECT_ID, insertResult);
    }

    // -- READ --

    @Test
    public void getProjects_shouldBeEmpty() throws InterruptedException {
        final List<Project> projects = LiveDataTestUtil.getValue(this.mRepository.getProjects());

        // TEST: Empty List
        assertTrue(projects.isEmpty());
    }

    @Test
    public void insertProject_2Times_Then_getProjects_shouldBeSuccess() throws InterruptedException {
        // BEFORE: Adds 2 projects
        this.mRepository.insertProject(FIRST_PROJECT);
        this.mRepository.insertProject(SECOND_PROJECT);

        final List<Project> projects = LiveDataTestUtil.getValue(this.mRepository.getProjects());

        // TEST: Size
        assertEquals(2, projects.size());

        // TEST: First project
        Project expectedProject;
        expectedProject = new Project(FIRST_PROJECT_ID,
                FIRST_PROJECT.getName(),
                FIRST_PROJECT.getColor());
        assertEquals(expectedProject, projects.get(0));

        // TEST: Second project
        expectedProject = new Project(SECOND_PROJECT_ID,
                SECOND_PROJECT.getName(),
                SECOND_PROJECT.getColor());
        assertEquals(expectedProject, projects.get(1));
    }

    @Test
    public void insertProject_Then_getProjectById_shouldBeSuccess() throws InterruptedException {
        // BEFORE: Adds a project
        this.mRepository.insertProject(FIRST_PROJECT);

        final Project project = LiveDataTestUtil.getValue(this.mRepository.getProjectById(FIRST_PROJECT_ID));

        // TEST: The only project
        final Project expectedProject = new Project(FIRST_PROJECT_ID,
                FIRST_PROJECT.getName(),
                FIRST_PROJECT.getColor());
        assertEquals(expectedProject, project);
    }

    @Test
    public void getProjectById_shouldBeNull() throws InterruptedException {
        final Project project = LiveDataTestUtil.getValue(this.mRepository.getProjectById(FIRST_PROJECT_ID));

        // TEST: Null Project
        assertNull(project);
    }

}

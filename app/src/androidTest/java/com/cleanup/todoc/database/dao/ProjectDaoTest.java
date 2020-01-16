package com.cleanup.todoc.database.dao;


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


@RunWith(AndroidJUnit4.class)

public class ProjectDaoTest {


    // FIELDS --------------------------------------------------------------------------------------

    private TodocDatabase mDatabase;

    private final long FIRST_PROJECT_ID = 1;
    private final Project FIRST_PROJECT = new Project("Project 1", 0xFFEADAD1);

    private final long SECOND_PROJECT_ID = 2;
    private final Project SECOND_PROJECT = new Project("Project 2", 0xFFB4CDBA);

    // RULES (Synchronized Tests) ------------------------------------------------------------------

    @Rule
    public InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule ();

    // METHODS -------------------------------------------------------------------------------------

    @Before
    public void setUp() {
        this.mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void tearDown() {
        this.mDatabase.close();
    }

    // -- CREATE --

    @Test
    public void insertProject_shouldBeSuccess() {
        final long insertResult = this.mDatabase.mProjectDao()
                .insertProject(FIRST_PROJECT);

        // TEST: Good Id
        assertEquals(FIRST_PROJECT_ID, insertResult);
    }

    // -- READ --

    @Test
    public void getProjectById_shouldBeNull() throws InterruptedException {
        final Project project = LiveDataTestUtil.getValue(this.mDatabase.mProjectDao()
                .getProjectById(FIRST_PROJECT_ID));

        // TEST: No project
        assertNull(project);
    }

    @Test
    public void getProjects_shouldBeEmpty() throws InterruptedException {
        final List<Project> projects = LiveDataTestUtil.getValue(this.mDatabase.mProjectDao()
                .getProjects());

        // TEST: Size
        assertEquals(0, projects.size());
    }

    @Test
    public void insertProject_Then_getProjectById_shouldBeSuccess() throws InterruptedException {
        // BEFORE: Adds a project
        this.mDatabase.mProjectDao()
                .insertProject(FIRST_PROJECT);

        // THEN: Retrieves the project thanks to its Id
        final Project project = LiveDataTestUtil.getValue(this.mDatabase.mProjectDao()
                .getProjectById(FIRST_PROJECT_ID));

        // TEST: The only project
        final Project expectedProject = new Project(FIRST_PROJECT_ID,
                FIRST_PROJECT.getName(),
                FIRST_PROJECT.getColor());
        assertEquals(expectedProject, project);
    }

    @Test
    public void insertProject_2Times_Then_getProjects_shouldBeSuccess() throws InterruptedException {
        // BEFORE: Adds 2 projects
        this.mDatabase.mProjectDao()
                .insertProject(FIRST_PROJECT);
        this.mDatabase.mProjectDao()
                .insertProject(SECOND_PROJECT);

        // THEN: Retrieves all the projects
        final List<Project> projects = LiveDataTestUtil.getValue(this.mDatabase.mProjectDao()
                .getProjects());

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

    // -- UPDATE --

    @Test
    public void insertProject_Then_updateProject_shouldBeSuccess() {
        // BEFORE: Adds a project
        this.mDatabase.mProjectDao()
                .insertProject(FIRST_PROJECT);

        // THEN: Updates the project [Id of FIRST_PROJECT but name and cole of SECOND_PROJECT]
        final Project expectedProject = new Project(FIRST_PROJECT_ID,
                SECOND_PROJECT.getName(),
                SECOND_PROJECT.getColor());
        final int updateResult = this.mDatabase.mProjectDao()
                .updateProject(expectedProject);

        // TEST: Updates the only project
        assertEquals(1, updateResult);
    }

    @Test
    public void insertProject_Then_updateProject_shouldNotUpdate() {
        // BEFORE: Adds a project
        this.mDatabase.mProjectDao()
                .insertProject(FIRST_PROJECT);

        // THEN: Updates the project [It is not the same Id]
        final int updateResult = this.mDatabase.mProjectDao()
                .updateProject(SECOND_PROJECT);

        // TEST: No update
        assertEquals(0, updateResult);
    }

    // -- DELETE --

    @Test
    public void insertProject_Then_deleteProjectById_shouldBeSuccess() {
        // BEFORE: Adds a project
        this.mDatabase.mProjectDao()
                .insertProject(FIRST_PROJECT);

        // Then: Deletes the project
        final int deleteResult = this.mDatabase.mProjectDao()
                .deleteProjectById(FIRST_PROJECT_ID);

        // TEST: The only project is deleted
        assertEquals(1, deleteResult);
    }

    @Test
    public void insertProject_Then_deleteProjectById_shouldBeNotSuccess() {
        // BEFORE: Adds a project
        this.mDatabase.mProjectDao()
                .insertProject(FIRST_PROJECT);

        // Then: Deletes the project
        final int deleteResult = this.mDatabase.mProjectDao()
                .deleteProjectById(SECOND_PROJECT_ID);

        // TEST: No delete
        assertEquals(0, deleteResult);
    }
}

package com.cleanup.todoc.repositories;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.database.sqlite.SQLiteConstraintException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.database.utils.LiveDataTestUtil;
import com.cleanup.todoc.model.database.TodocDatabase;
import com.cleanup.todoc.model.pojos.Project;
import com.cleanup.todoc.model.pojos.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Abbesolo on 28/12/2019.
 * Name of the project: todoc-master
 * Name of the package: com.cleanup.todoc.repositories
 *
 * Android test on {@link Repository.TaskRepository}.
 */
@RunWith(AndroidJUnit4.class)

public class TaskRepositoryTest {
    // FIELDS --------------------------------------------------------------------------------------

    private TodocDatabase mDatabase;
    private Repository.TaskRepository mRepository;

    private final long FIRST_TASK_ID = 1;
    private final Task FIRST_TASK = new Task(1, "Task 1", 0);

    private final long SECOND_TASK_ID = 2;
    private final Task SECOND_TASK = new Task(1, "Task 2", 1);

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

        this.mRepository = new TaskRepositoryImpl(this.mDatabase.mTaskDao());

        // BEFORE: Create a project
        final long insertResult = this.mDatabase.mProjectDao()
                .insertProject(new Project ("Project 1", 0xFFEADAD1));

        // TEST: Good Id
        assertEquals(1, insertResult);
    }

    @After
    public void tearDown() {
        this.mDatabase.close();
    }

    // -- CREATE --

    @Test
    public void insertTask_shouldBeSuccess() {
        final long insertResult = this.mRepository.insertTask(FIRST_TASK);

        // TEST: Good insertion
        assertEquals(FIRST_TASK_ID, insertResult);
    }

    @Test(expected = SQLiteConstraintException.class)
    public void insertTask_shouldThrowSQLiteConstraintException() throws SQLiteConstraintException {
        // BEFORE: Create task with a project id that is not present in project table
        Task task = new Task(100, "Task error", 6);

        this.mRepository.insertTask(task);
    }

    // -- READ --

    @Test
    public void getTasks_shouldBeEmpty() throws InterruptedException {
        final List<Task> tasks = LiveDataTestUtil.getValue(this.mRepository.getTasks());

        // TEST: Empty List
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void insertTask_2Times_Then_getTasks_shouldBeSuccess() throws InterruptedException {
        // BEFORE: Adds 2 tasks
        this.mRepository.insertTask(FIRST_TASK);
        this.mRepository.insertTask(SECOND_TASK);

        final List<Task> tasks = LiveDataTestUtil.getValue(this.mRepository.getTasks());

        // TEST: Size
        assertEquals(2, tasks.size());

        // TEST: First task
        Task expectedTask;
        expectedTask = new Task(FIRST_TASK_ID,
                FIRST_TASK.getProjectId(),
                FIRST_TASK.getName(),
                FIRST_TASK.getCreationTimestamp());
        assertEquals(expectedTask, tasks.get(0));

        // TEST: Second task
        expectedTask = new Task(SECOND_TASK_ID,
                SECOND_TASK.getProjectId(),
                SECOND_TASK.getName(),
                SECOND_TASK.getCreationTimestamp());
        assertEquals(expectedTask, tasks.get(1));
    }

    // -- DELETE --

    @Test
    public void insertTask_Then_deleteProjectById_shouldBeSuccess() {
        // BEFORE: Adds a task
        this.mRepository.insertTask(FIRST_TASK);

        // Then: Deletes the task
        final int deleteResult = this.mRepository.deleteTaskById(FIRST_TASK_ID);

        // TEST: The only project is deleted
        assertEquals(1, deleteResult);
    }

    @Test
    public void insertTask_Then_deleteProjectById_shouldBeNotSuccess() {
        // BEFORE: Adds a task
        this.mRepository.insertTask(FIRST_TASK);

        // Then: Deletes the task
        final int deleteResult = this.mRepository.deleteTaskById(SECOND_TASK_ID);

        // TEST: No delete
        assertEquals(0, deleteResult);
    }
}

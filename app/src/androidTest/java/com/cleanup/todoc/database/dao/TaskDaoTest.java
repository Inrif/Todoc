package com.cleanup.todoc.database.dao;

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
import static org.junit.Assert.assertNull;


@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    // FIELDS --------------------------------------------------------------------------------------

    private TodocDatabase mDatabase;

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
        final long insertResult = this.mDatabase.mTaskDao()
                .insertTask(FIRST_TASK);

        // TEST: Good Id
        assertEquals(FIRST_TASK_ID, insertResult);
    }

    @Test(expected = SQLiteConstraintException.class)
    public void insertTask_shouldThrowSQLiteConstraintException() throws SQLiteConstraintException {
        // BEFORE: Create task with a project id that is not present in project table
        Task task = new Task(100, "Task error", 6);

        this.mDatabase.mTaskDao()
                .insertTask(task);
    }

    // -- READ --

    @Test
    public void getTaskById_shouldBeNull() throws InterruptedException {
        final Task task = LiveDataTestUtil.getValue(this.mDatabase.mTaskDao()
                .getTaskById(FIRST_TASK_ID));

        // TEST: No task
        assertNull(task);
    }

    @Test
    public void getTasks_shouldBeEmpty() throws InterruptedException {
        final List<Task> tasks = LiveDataTestUtil.getValue(this.mDatabase.mTaskDao()
                .getTasks());

        // TEST: Size
        assertEquals(0, tasks.size());
    }

    @Test
    public void insertTask_Then_getTaskById_shouldBeSuccess() throws InterruptedException {
        // BEFORE: Adds a task
        this.mDatabase.mTaskDao()
                .insertTask(FIRST_TASK);

        // THEN: Retrieves the task thanks to its Id
        final Task task = LiveDataTestUtil.getValue(this.mDatabase.mTaskDao()
                .getTaskById(FIRST_TASK_ID));

        // TEST: The only task
        final Task expectedTask = new Task(FIRST_TASK_ID,
                FIRST_TASK.getProjectId(),
                FIRST_TASK.getName(),
                FIRST_TASK.getCreationTimestamp());
        assertEquals(expectedTask, task);
    }

    @Test
    public void insertTask_2Times_Then_getTasks_shouldBeSuccess() throws InterruptedException {
        // BEFORE: Adds 2 tasks
        this.mDatabase.mTaskDao()
                .insertTask(FIRST_TASK);
        this.mDatabase.mTaskDao()
                .insertTask(SECOND_TASK);

        // THEN: Retrieves all the tasks
        final List<Task> tasks = LiveDataTestUtil.getValue(this.mDatabase.mTaskDao().getTasks());

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

    // -- UPDATE --

    @Test
    public void insertTask_Then_updateTask_shouldBeSuccess() {
        // BEFORE: Adds a task
        this.mDatabase.mTaskDao()
                .insertTask(FIRST_TASK);

        // THEN: Updates the task [Id of FIRST_TASK but the rest of SECOND_TASK]
        final Task expectedTask = new Task(FIRST_TASK_ID,
                SECOND_TASK.getProjectId(),
                SECOND_TASK.getName(),
                SECOND_TASK.getCreationTimestamp());
        final int updateResult = this.mDatabase.mTaskDao()
                .updateTask(expectedTask);

        // TEST: Updates the only task
        assertEquals(1, updateResult);
    }

    @Test
    public void insertTask_Then_updateTask_shouldNotUpdate() {
        // BEFORE: Adds a task
        this.mDatabase.mTaskDao()
                .insertTask(FIRST_TASK);

        // THEN: Updates the task [It is not the same Id]
        final int updateResult = this.mDatabase.mTaskDao()
                .updateTask(SECOND_TASK);

        // TEST: No update
        assertEquals(0, updateResult);
    }

    // -- DELETE --

    @Test
    public void insertTask_Then_deleteProjectById_shouldBeSuccess() {
        // BEFORE: Adds a task
        this.mDatabase.mTaskDao()
                .insertTask(FIRST_TASK);

        // Then: Deletes the task
        final int deleteResult = this.mDatabase.mTaskDao()
                .deleteTaskById(FIRST_TASK_ID);

        // TEST: The only project is deleted
        assertEquals(1, deleteResult);
    }

    @Test
    public void insertTask_Then_deleteProjectById_shouldBeNotSuccess() {
        // BEFORE: Adds a task
        this.mDatabase.mTaskDao()
                .insertTask(FIRST_TASK);

        // Then: Deletes the task
        final int deleteResult = this.mDatabase.mTaskDao()
                .deleteTaskById(SECOND_TASK_ID);

        // TEST: No delete
        assertEquals(0, deleteResult);
    }
}

package com.cleanup.todoc;

import com.cleanup.todoc.model.database.TodocDatabase;
import com.cleanup.todoc.model.pojos.Project;
import com.cleanup.todoc.model.pojos.Task;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Unit tests for tasks
 *
 * @author Gaëtan HERFRAY
 */
public class TaskUnitTest {
    // FIELDS --------------------------------------------------------------------------------------

    private final Task TASK1 = new Task(1, 1, "aaa", 123);
    private final Task TASK2 = new Task(2, 2, "zzz", 124);
    private final Task TASK3 = new Task(3, 3, "hhh", 125);
    private final Task TASK4 = new Task(4, 4, "AAA", 126);
    private final Task TASK5 = new Task(5, 5, "ZZZ", 127);

    // METHODS -------------------------------------------------------------------------------------

    // -- PROJECTS --

    @Test
    public void test_initial_projects() {
        final List<Project> projects = TodocDatabase.PROJECTS;

        assertEquals("Projet Tartampion", projects.get(0).getName());
        assertEquals("Projet Lucidia", projects.get(1).getName());
        assertEquals("Projet Circus", projects.get(2).getName());
    }

    // -- COMPARATORS --

    @Test
    public void test_az_comparator() {
        final List<Task> tasks = Arrays.asList(TASK1, TASK2, TASK3, TASK4, TASK5);

        Collections.sort(tasks, new Task.TaskAZComparator());

        assertSame(tasks.get(0), TASK1);
        assertSame(tasks.get(1), TASK4);
        assertSame(tasks.get(2), TASK3);
        assertSame(tasks.get(3), TASK2);
        assertSame(tasks.get(4), TASK5);
    }

    @Test
    public void test_za_comparator() {
        final List<Task> tasks = Arrays.asList(TASK1, TASK2, TASK3, TASK4, TASK5);

        Collections.sort(tasks, new Task.TaskZAComparator());

        assertSame(tasks.get(0), TASK2);
        assertSame(tasks.get(1), TASK5);
        assertSame(tasks.get(2), TASK3);
        assertSame(tasks.get(3), TASK1);
        assertSame(tasks.get(4), TASK4);
    }

    @Test
    public void test_recent_comparator() {
        final List<Task> tasks = Arrays.asList(TASK1, TASK2, TASK3);

        Collections.sort(tasks, new Task.TaskRecentComparator());

        assertSame(tasks.get(0), TASK3);
        assertSame(tasks.get(1), TASK2);
        assertSame(tasks.get(2), TASK1);
    }

    @Test
    public void test_old_comparator() {
        final List<Task> tasks = Arrays.asList(TASK1, TASK2, TASK3);

        Collections.sort(tasks, new Task.TaskOldComparator());

        assertSame(tasks.get(0), TASK1);
        assertSame(tasks.get(1), TASK2);
        assertSame(tasks.get(2), TASK3);
    }
}
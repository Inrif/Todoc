package com.cleanup.todoc.dagger.modules;

import android.content.Context;

import com.cleanup.todoc.model.dao.ProjectDao;
import com.cleanup.todoc.model.dao.TaskDao;
import com.cleanup.todoc.model.database.TodocDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class DatabaseModule {
     /*
        Information:    Singleton annotation is not necessary because "IN THIS ONLY CASE",
                        the Application instance, "TodocApplication", is unique.

        see: https://github.com/google/dagger/issues/832#issuecomment-320508239
     */

    // METHODS -------------------------------------------------------------------------------------

    /**
     * Provides the {@link TodocDatabase}
     * @param context the {@link Context}
     * @return the {@link TodocDatabase}
     */
    @Singleton
    @Provides
    static TodocDatabase provideTodocDatabase(final Context context) {
        return TodocDatabase.getInstance(context);
    }

    /**
     * Provides the {@link ProjectDao}
     * @param database the {@link TodocDatabase}
     * @return the {@link ProjectDao}
     */
    @Singleton
    @Provides
    static ProjectDao provideProjectDao(final TodocDatabase database) {
        return database.mProjectDao();
    }

    /**
     * Provides the {@link TaskDao}
     * @param database the {@link TodocDatabase}
     * @return the {@link TaskDao}
     */
    @Singleton
    @Provides
    static TaskDao provideTaskDao(final TodocDatabase database) {
        return database.mTaskDao();
    }
}

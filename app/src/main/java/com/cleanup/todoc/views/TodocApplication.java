package com.cleanup.todoc.views;

import android.app.Application;
import android.support.annotation.NonNull;

import com.cleanup.todoc.dagger.components.ApplicationComponent;
import com.cleanup.todoc.dagger.components.DaggerApplicationComponent;

public class TodocApplication extends Application {
    // FIELDS --------------------------------------------------------------------------------------

    @SuppressWarnings("NullableProblems")
    @NonNull
    private ApplicationComponent mComponent;

    // METHODS -------------------------------------------------------------------------------------

    // -- APPLICATION --

    @Override
    public void onCreate() {
        super.onCreate();

        /* Dagger2 */
        this.mComponent = DaggerApplicationComponent.builder()
                .context(getApplicationContext())
                .build();
    }

    // -- COMPONENT --

    /**
     * Returns the {@link ApplicationComponent}
     * @return the {@link ApplicationComponent}
     */
    public ApplicationComponent getApplicationComponent() {
        return this.mComponent;
    }

}

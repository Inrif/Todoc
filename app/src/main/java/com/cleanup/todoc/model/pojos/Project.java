package com.cleanup.todoc.model.pojos;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import java.util.Objects;

/**
 * <p>Models for project in which tasks are included.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(tableName = "project")
public class Project {
    /**
     * The unique identifier of the project
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    /**
     * The name of the project
     */
    @NonNull
    @ColumnInfo(name = "name")
    private final String name;

    /**
     * The hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    @ColumnInfo(name = "color")
    private final int color;

    // CONSTRUCTORS --------------------------------------------------------------------------------

    /**
     * Instantiates a new Project.
     * @param id    the unique identifier of the project to set
     * @param name  the name of the project to set
     * @param color the hex (ARGB) code of the color associated to the project to set
     */
    @Ignore
    @VisibleForTesting
    public Project(long id, @NonNull String name, @ColorInt int color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    /**
     * Instantiates a new Project.
     * @param name  the name of the project to set
     * @param color the hex (ARGB) code of the color associated to the project to set
     */
    public Project(@NonNull String name, @ColorInt int color) {
        this.name = name;
        this.color = color;
    }


    // METHODS -------------------------------------------------------------------------------------

    // -- GETTER AND SETTER --

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @ColorInt
    public int getColor() {
        return color;
    }

    // -- FROM OBJECT CLASS --

    @Override
    public boolean equals(@Nullable Object obj) {
        // Same address
        if (this == obj) return true;

        // Null or different class
        if (obj == null || getClass() != obj.getClass()) return false;

        // Cast Object to Project
        Project project = (Project) obj;

        return Objects.equals(this.id, project.id)     &&
                Objects.equals(this.name, project.name) &&
                Objects.equals(this.color, project.color);
    }

    @Override
    @NonNull
    public String toString() {
        return getName();
    }


//    /**
//     * Returns all the projects of the application.
//     *
//     * @return all the projects of the application
//     */
//    @NonNull
//    public static Project[] getAllProjects() {
//        return new Project[]{
//                new Project( "Projet Tartampion", 0xFFEADAD1),
//                new Project( "Projet Lucidia", 0xFFB4CDBA),
//                new Project( "Projet Circus", 0xFFA3CED2),
//        };
//    }
//
//    /**
//     * Returns the project with the given unique identifier, or null if no project with that
//     * identifier can be found.
//     *
//     * @param id the unique identifier of the project to return
//     * @return the project with the given unique identifier, or null if it has not been found
//     */
//    @Nullable
//    public static Project getProjectById(long id) {
//        for (Project project : getAllProjects()) {
//            if (project.id == id)
//                return project;
//        }
//        return null;
//    }
//
//    /**
//     * Returns the unique identifier of the project.
//     *
//     * @return the unique identifier of the project
//     */
//    public long getId() {
//        return id;
//    }
//
//    /**
//     * Returns the name of the project.
//     *
//     * @return the name of the project
//     */
//    @NonNull
//    public String getName() {
//        return name;
//    }
//
//    /**
//     * Returns the hex (ARGB) code of the color associated to the project.
//     *
//     * @return the hex (ARGB) code of the color associated to the project
//     */
//    @ColorInt
//    public int getColor() {
//        return color;
//    }
//
//    @Override
//    @NonNull
//    public String toString() {
//        return getName();
//    }
}

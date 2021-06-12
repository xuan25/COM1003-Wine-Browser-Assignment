package assignment2019.codeprovided;

/*
 * WineType.java  	1.0  06/04/2019
 *
 * Copyright (c) University of Sheffield 2019
 */

/**
 * WineType.java
 *
 * Provides a helper enum with constants representing the accepted wine types.
 *
 * @version 1.0  06/04/2019
 *
 * @author Maria-Cruz Villa-Uriol (m.villa-uriol@sheffield.ac.uk)
 */

public enum WineType {
    ALL("all"),
    RED("red"),
    WHITE("white");

    private String wineTypeName;

    WineType(String wTName) { wineTypeName = wTName; }

}
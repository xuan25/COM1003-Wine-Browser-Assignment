package assignment2019.codeprovided;

/*
 * QueryCondition.java  	1.0  06/04/2019
 *
 * Copyright (c) University of Sheffield 2019
 */

/**
 * QueryCondition.java
 *
 * Class designed to create QueryCondition objects which make up parts of an individual query.
 *
 * @version 1.0  06/04/2019
 *
 * @author Maria-Cruz Villa-Uriol (m.villa-uriol@sheffield.ac.uk)
 */

public class QueryCondition
{

    // Instance variables
    WineProperty wineProperty;
    String operator;
    double value;

    // Constructor
    public QueryCondition(WineProperty wineProperty, String operator, double value) {
        this.wineProperty = wineProperty;
        this.operator = operator;
        this.value = value;
    }

    public WineProperty getWineProperty()
    {
        return wineProperty;
    }

    public void setWineProperty(WineProperty wineProperty)
    {
        this.wineProperty = wineProperty;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    public String toString() {
    	return this.getWineProperty() + " " + this.getOperator() + " " + this.getValue();
    }
}

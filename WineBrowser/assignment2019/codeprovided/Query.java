package assignment2019.codeprovided;
// import statements
import java.util.*;
import java.util.Comparator;

/*
 * Query.java  	1.0  06/04/2019
 *
 * Copyright (c) University of Sheffield 2019
 */

/**
 * Query.java
 *
 * Class designed to be used to create Query objects from the Query List.
 *
 * @version 1.0  06/04/2019
 *
 * @author Maria-Cruz Villa-Uriol (m.villa-uriol@sheffield.ac.uk)
 */

public class Query {

    // Instance variables
	// wineList is kept up to date with the results of the query (see solveQuery)
    List<WineSample> wineList = new ArrayList<>();
    List<QueryCondition> queryConditionList = new ArrayList<>();
    WineType wineType;

    // Constructor
    public Query(List<WineSample> wineList, List<QueryCondition> queryConditionList, WineType wineType) {
    	this.wineList = wineList;
        this.queryConditionList = queryConditionList;
        this.wineType = wineType;
    }

    /**
     * Getter for wineList (the list of WineSample objects of a Query object)
     *
     * @return  List of WineSample objects
     */
    public List<WineSample> getWineList() {
        return wineList;
    }

    /**
     * Setter for wineList (the list of WineSample objects of a Query object)
     *
     * @param List of WineSample objects
     */
    public void setWineList(List<WineSample> wineList) {
        this.wineList = wineList;
    }

    /**
     * Getter for wineType of Query object
     *
     * @return wineType of Query object
     */
    public WineType getWineType() {
        return wineType;
    }

    /**
     * Setter for wineType of Query object
     *
     * @param wineType
     */
    public void setWineType(WineType wineType) {
        this.wineType = wineType;
    }

    /**
     * Getter for queryConditionList of Query object
     *
     * @return queryConditionList of Query objects
     */
    public List<QueryCondition> getQueryConditionList() {
        return queryConditionList;
    }

    /**
     * Setter for queryConditionList List of Wines of Query object
     *
     * @param queryConditionList
     */
    public void setQueryConditionList(List<QueryCondition> queryConditionList) {
        this.queryConditionList = queryConditionList;
    }

    /**
     * executeQuery method - performs assessment of wine samples against query criteria and adds successful samples to ArrayList and returns it.
     *
     * @param wineList The list of sorted relevant wines
     * @param wineProperty The wine property
     * @param propertyOperator The operator of the property
     * @param propertyValue The criteria, a double value for the wine sample (such as a 6 for quality...)
     * @return ArrayList of all wines which meet criteria
     */
    public List<WineSample> executeQuery(List<WineSample> wineList, WineProperty wineProperty, String propertyOperator, double propertyValue) {
        List<WineSample> solvedWineList = new ArrayList<>();

        for (WineSample w : wineList) {
            switch (propertyOperator) {
                case ">":
                    Collections.reverse(wineList);
                    if (w.getProperty(wineProperty) > propertyValue) solvedWineList.add(w);
                    break;
                case ">=":
                    Collections.reverse(wineList);
                    if (w.getProperty(wineProperty) >= propertyValue) solvedWineList.add(w);
                    break;
                case "<":
                    if (w.getProperty(wineProperty) < propertyValue) solvedWineList.add(w);
                    break;
                case "<=":
                    if (w.getProperty(wineProperty) <= propertyValue) solvedWineList.add(w);
                    break;
                case "=":
                    if (w.getProperty(wineProperty) == propertyValue) solvedWineList.add(w);
                    break;
                case "!=":
                    if (w.getProperty(wineProperty) != propertyValue) solvedWineList.add(w);
                    break;
                default:
                    break;
            }
        }

        return solvedWineList;
}

    /**
     * solveQuery method - Creates a TreeSet sorted by the relevant property which WineSample objects are being assessed on and
     * then copies the wineList into it. The executeQuery method is then called on each WineSample to be assessed with all valid
     * wines being added to an ArrayList which is then returned.
     * The method also updates the Query wineList to contain the solved query results.
     *
     * @return ArrayList of all valid wine samples
     */
    public List<WineSample> solveQuery() {
        List<WineSample> solvedWineList = new ArrayList<>();

        for (QueryCondition subQuery : queryConditionList)
        {
            double value = subQuery.getValue();
            String operator = subQuery.getOperator();
            WineProperty wineProperty = subQuery.getWineProperty();
            List<WineSample> auxWineList = new ArrayList<>();

            Comparator<WineSample> comparator = new PropertyComparator(wineProperty);
            SortedSet<WineSample> wineSet = new TreeSet<>(comparator);

            wineSet.addAll(wineList);
            auxWineList.addAll(wineSet);
            solvedWineList = executeQuery(auxWineList, wineProperty, operator, value);
            this.setWineList(solvedWineList);

        }
        return solvedWineList;
    }
}

/**
 * PropertyComparator.java
 *
 * Comparator class necessary to sort the contents in the TreeSet built by solveQuery in Query object 
 *
 * @version 1.0  06/04/2019
 *
 * @author Maria-Cruz Villa-Uriol (m.villa-uriol@sheffield.ac.uk)
 */
class PropertyComparator implements Comparator<WineSample>
{
    WineProperty propertyToCompare;

    public PropertyComparator(WineProperty wineProperty)
    {
        propertyToCompare = wineProperty;
    }

    public int compare(WineSample a, WineSample b)
    {
        double propA = a.getProperty(propertyToCompare);
        double propB = b.getProperty(propertyToCompare);

        if (propA > propB) return 1;
        if (propA < propB) return -1;
        return 1;
    }
}

package assignment2019.codeprovided;
// import statements

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/*
 * AbstractWineSampleBrowserPanel.java  	1.0  06/04/2019
 *
 * Copyright (c) University of Sheffield 2019
 */

/**
 * AbstractWineSampleBrowserPanel.java
 *
 * Class that provides a basic implementation of the main elements in the GUI as discussed in the handout.
 *
 * @version 1.0  06/04/2019
 *
 * @author Maria-Cruz Villa-Uriol (m.villa-uriol@sheffield.ac.uk)
 */

// constructor
public abstract class AbstractWineSampleBrowserPanel extends JPanel {

    // instance variables
	
    // cellar provides a convenient access to the original datasets - might be of use
	protected AbstractWineSampleCellar cellar;
	// filteredWineSampleList is the list that needs to be kept up to date with the results of
	// running the list of QueryCondition objects in queryConditionList
    protected List<WineSample> filteredWineSampleList;
    
	// queryConditionList contains all the conditions as they are added when the button buttonAddFilter is clicked
	// the contents of queryConditionList will need to be cleared if the buttonClearFilters is clicked
	protected List<QueryCondition> queryConditionList = new ArrayList<>();
	    
    // by default all wine samples are used
    protected WineType wineType = WineType.ALL;
   
    // starting the definition of buttons 
    // buttonAddFilter will add a new query condition to queryConditionList when clicked
    // clicking this button implies calling the method addFilter(...)
    protected JButton buttonAddFilter = new JButton("Add Filter");
    // buttonClearFilters will remove all query conditions in queryConditionList when clicked
    // clicking this button implies calling the method clearFilters(...)
    protected JButton buttonClearFilters = new JButton("Clear All Filters");

    // defining the combobox used to select the wine type to which the filters (queryConditionList or list of SubQuery object) need to be applied
    protected String[] wineTypes = { WineType.ALL.name(), WineType.RED.name(), WineType.WHITE.name() };
    protected JComboBox<String> comboWineTypes = new JComboBox<>(wineTypes);

    // defining the combobox used to select the wine property to build the filter (or SubQuery object) that will be applied
    protected String[] propertyNames = {
    		"Fixed Acidity",
            "Volatile Acidity",
            "Citric Acidity",
            "Residual Sugar",
            "Chlorides",
            "Free Sulfur Dioxide",
            "Total Sulfur Dioxide",
            "Density",
            "pH",
            "Sulphates",
            "Alcohol",
            "Quality"};
    protected JComboBox<String> comboProperties = new JComboBox<>(propertyNames);

    // defining the combobox used to select the operator that will be used to build the filter (or SubQuery object) than will be applied
    protected String[] operators = {">", ">=", "<", "<=", "=", "!="};
    protected JComboBox<String> comboOperators = new JComboBox<>(operators);

    // defining the textfield where the value of the QueryCondition (or filter)
    protected JTextField value = new JTextField(5);

    // defining all the labels to facilitate the what goes where in the GUI
    protected JLabel wineTypeSelectorLabel = new JLabel("Wine list:", SwingConstants.LEFT);
    protected JLabel queryLabel = new JLabel("Query condition:", SwingConstants.LEFT);
    protected JLabel operatorLabel = new JLabel("Operator:", SwingConstants.LEFT);
    protected JLabel operatorValueLabel = new JLabel("Value:", SwingConstants.LEFT);
    protected JLabel wineResultsTitle = new JLabel("Wine samples:", SwingConstants.LEFT);
    protected JLabel wineStatsTitle = new JLabel("Wine samples statistics:", SwingConstants.LEFT);
    protected JLabel queryConditionsLabel = new JLabel("List of query conditions:", SwingConstants.LEFT);

    // defining the three JTextAreas that will need to be updated every time the buttons
    // buttonAddFilter and buttonClearFilters are clicked
    // queryConditionsTextArea will show the contents of queryConditionsList
    protected JTextArea queryConditionsTextArea = new JTextArea(1, 50);
    // statisticsTextArea will show basic summary statistics for the filteredWineSampleList 
    // (which contains the results after executing the filters or QueryCondition in queryConditionsList)
    protected JTextArea statisticsTextArea = new JTextArea(50, 50);
    // samplesTextArea will show the results contained in the filteredWineSampleList object
    protected JTextArea filteredWineSamplesTextArea = new JTextArea(50, 50);

    // Constructor
    public AbstractWineSampleBrowserPanel(AbstractWineSampleCellar cellar) {
    	// providing access to the cellar so that this class can access its contents if required 
        this.cellar = cellar;
        // by default the GUI starts showing all wine samples 
        this.filteredWineSampleList = cellar.getWineSampleList(WineType.ALL);
        
        // building the GUI using a combination of JPanels and a range of LayoutManagers
        // to get a compelling GUI
        this.setLayout(new BorderLayout());

        JPanel queryPanel = new JPanel();
        queryPanel.setLayout(new GridLayout(4, 1));

        JPanel typeSelectorPanel = new JPanel();
        typeSelectorPanel.setLayout(new FlowLayout());
        typeSelectorPanel.add(wineTypeSelectorLabel);
        typeSelectorPanel.add(comboWineTypes);
        JPanel filterBuilderPanel = new JPanel();
        filterBuilderPanel.setLayout(new FlowLayout());
        filterBuilderPanel.add(queryLabel);
        filterBuilderPanel.add(comboProperties);
        filterBuilderPanel.add(operatorLabel);
        filterBuilderPanel.add(comboOperators);
        filterBuilderPanel.add(operatorValueLabel);
        filterBuilderPanel.add(value);
        filterBuilderPanel.add(buttonAddFilter);
        filterBuilderPanel.add(buttonClearFilters);

        queryPanel.add(typeSelectorPanel);
        queryPanel.add(filterBuilderPanel);
        queryPanel.add(queryConditionsLabel);

        JScrollPane jscQueries = new JScrollPane(queryConditionsTextArea);
        queryPanel.add(jscQueries);

        JPanel wineDisplayPanel = new JPanel();
        wineDisplayPanel.setLayout(new FlowLayout());
        JPanel statisticsPanel = new JPanel();
        statisticsPanel.setLayout(new BorderLayout());
        JScrollPane statisticsScrollPane = new JScrollPane(statisticsTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        statisticsPanel.add(wineStatsTitle, BorderLayout.NORTH);
        statisticsPanel.add(statisticsScrollPane, BorderLayout.CENTER);
        JPanel samplesPanel = new JPanel();
        samplesPanel.setLayout(new BorderLayout());
        JScrollPane samplesScrollPane = new JScrollPane(filteredWineSamplesTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        samplesPanel.add(wineResultsTitle, BorderLayout.NORTH);
        samplesPanel.add(samplesScrollPane, BorderLayout.CENTER);
        wineDisplayPanel.add(statisticsPanel);
        wineDisplayPanel.add(samplesPanel);
        this.add(queryPanel, BorderLayout.NORTH);
        this.add(wineDisplayPanel, BorderLayout.CENTER);

        // adding the listeners, you will need to implement this method to register the events generated
        // by the GUI components that will be expecting a change in the results being displayed by the GUI
        this.addListeners();

    }

    /**
     * getQueryConditionList method - getter of query conditions list
     * @return List of QueryCondition objects
     */
    public List<QueryCondition> getQueryConditionList() {
    	return queryConditionList;
    }

    /**
     * getFilteredWineSampleList method - getter of filtered wine sample list
     * @return List of WineSample objects after running filters (or list of QueryConditions)
     */
    public List<WineSample> getFilteredWineSampleList() {
    	return filteredWineSampleList;
    }
    
    // list of abstract methods starts
    
    /**
     * addListeners method - adds relevant actionListeners to the GUI components
     * You will need to listen (at least) to the following:
     * - buttonAddFilter
     * - buttonClearFilters
     * - comboWineTypes, if you want the samplesTextArea to be updated to show only the wine samples
     *            specified by this combobox
     * 
     */
    public abstract void addListeners();

    /**
     * addFilter method - 
     * 1- this method is called when the JButton buttonAddFilter is clicked 
     * 2- adds a new filter (a QueryCondition object) to queryConditionsList ArrayList
     * 3- updates the GUI results accordingly, i.e. updates the three JTextAreas as follows:
     *    3a- queryConditionsTextArea will show the new QueryCondition
     *    3b- statisticsTextArea will show the updated statistics for the results after applying this filter
     *    3c- samplesTextArea will show the contents of filteredWineList (the results after applying this filter)
     */
    public abstract void addFilter();

    /**
     * clearFilters method - clears all filters from the queryConditionsList ArrayList and updates
     * the relevant GUI components when the button buttonClearFilters is clicked
     */
    public abstract void clearFilters();

    /**
     * updateStatistics method - updates the statistics to be displayed in the 
     * statisticsTextArea when the results being shown in the GUI need to be updated,
     * recalculates the average, minimum and maximum values for each wine property.
     */
    public abstract void updateStatistics();

    /**
     * updateWineList method - updates the wine list when changes are made
     */
    public abstract void updateWineList();

    /**
     * executeQuery method - executes the complete query to the relevant wine list
     */
    public abstract void executeQuery();
    
}

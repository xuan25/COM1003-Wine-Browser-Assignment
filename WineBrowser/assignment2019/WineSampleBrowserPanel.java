package assignment2019;
import assignment2019.codeprovided.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * WineSampleBrowserPannel.java
 *
 * Class that provides fully implementation of the main elements in the GUI.
 *
 * @version 1.0  30/04/2019
 *
 * @author Boxuan Shan
 */

public class WineSampleBrowserPanel extends AbstractWineSampleBrowserPanel {

    public WineSampleBrowserPanel(AbstractWineSampleCellar cellar) {
        super(cellar);
        updateWineList();
    }

    /**
     * Adds relevant actionListeners to the GUI components
     */
    public void addListeners() {
        comboWineTypes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                executeQuery();
            }
        });
        buttonAddFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addFilter();
            }
        });
        buttonClearFilters.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFilters();
            }
        });
    }

    /**
     * Add Filter when the JButton buttonAddFilter is clicked
     */
    public void addFilter() {
    	WineProperty property;
        String operator;
        double val;
    	try{
    		property = WineProperty.values()[comboProperties.getSelectedIndex()];
        	operator = comboOperators.getSelectedItem().toString();
        	val = Double.valueOf(value.getText());
    	}
    	catch(NumberFormatException ex){
    		System.out.println("Invalid filter value: " + ex.getMessage() + ".");
    		return;
    	}
    	catch(Exception ex){
    		System.out.println("Invalid filter options.");
    		return;
    	}

        QueryCondition condition = new QueryCondition(property, operator, val);
        queryConditionList.add(condition);
        queryConditionsTextArea.append(condition.toString() + "; ");
        executeQuery();
    }

    /**
     * Clears all filters from the queryConditionsList ArrayList and updates
     * the relevant GUI components when the button buttonClearFilters is clicked
     */
    public void clearFilters() {
        queryConditionList.clear();
        queryConditionsTextArea.setText("");
        executeQuery();
    }

    /**
     * Updates the statistics to be displayed in the
     * statisticsTextArea when the results being shown in the GUI need to be updated,
     * recalculates the average, minimum and maximum values for each wine property.
     */
    public void updateStatistics() {

        // Check filtered data
        if(filteredWineSampleList.size() == 0) {
            statisticsTextArea.setText("No data");
            return;
        }

        // Print titles
        statisticsTextArea.setText("");
        statisticsTextArea.append("\t");
        for (WineProperty property : WineProperty.values()) {
            statisticsTextArea.append(property.toString() + "\t");
        }
        statisticsTextArea.append("\n");

        // Print Max row
        statisticsTextArea.append("Max\t");
        for (WineProperty property : WineProperty.values()) {
            List<WineSample> sortedSamples = WineSampleCellar.getSortedSamples(filteredWineSampleList, property);
            statisticsTextArea.append(sortedSamples.get(sortedSamples.size()-1).getProperty(property) + "\t");
            // Check for long property name
            if(property.toString().length() > 15)
                statisticsTextArea.append("\t");
        }
        statisticsTextArea.append("\n");

        // Print Min row
        statisticsTextArea.append("Min\t");
        for (WineProperty property : WineProperty.values()) {
            List<WineSample> sortedSamples = WineSampleCellar.getSortedSamples(filteredWineSampleList, property);
            statisticsTextArea.append(sortedSamples.get(0).getProperty(property) + "\t");
            // Check for long property name
            if(property.toString().length() > 15)
                statisticsTextArea.append("\t");
        }
        statisticsTextArea.append("\n");

        // Print Average row
        statisticsTextArea.append("Average\t");
        for (WineProperty property : WineProperty.values()) {
            double average = 0;
            for (WineSample sample : filteredWineSampleList) {
                average += sample.getProperty(property);
            }
            average /= filteredWineSampleList.size();
            statisticsTextArea.append(((double)Math.round(average*10000)/10000) + "\t");
            // Check for long property name
            if(property.toString().length() > 15)
                statisticsTextArea.append("\t");
        }
        statisticsTextArea.append("\n");
        statisticsTextArea.append("\n");

        // Print count
        statisticsTextArea.append("Showing " + filteredWineSampleList.size()
        						+ " out of " + cellar.getWineSampleList(WineType.ALL).size()
        						+ " samples.");
    }

    /**
     * Updates the wine list when changes are made
     */
    public void updateWineList() {
        // Print titles
        filteredWineSamplesTextArea.setText("");
        filteredWineSamplesTextArea.append("WINE TYPE\t");
        filteredWineSamplesTextArea.append("ID\t");
        for (WineProperty property : WineProperty.values()) {
            filteredWineSamplesTextArea.append(property.toString() + "\t");
        }
        filteredWineSamplesTextArea.append("\n");

        // Print samples
        for (WineSample wineSample : filteredWineSampleList) {
            filteredWineSamplesTextArea.append(wineSample.getType() + "\t");
            filteredWineSamplesTextArea.append(wineSample.getId() + "\t");
            for (WineProperty property : WineProperty.values()) {
                filteredWineSamplesTextArea.append(wineSample.getProperty(property) + "\t");
                // Check for long property name
                if(property.toString().length() > 15)
                    filteredWineSamplesTextArea.append("\t");
            }
            filteredWineSamplesTextArea.append("\n");
        }
        updateStatistics();
    }

    /**
     * Executes the complete query to the relevant wine list
     */
    public void executeQuery() {
        WineType type = WineType.values()[comboWineTypes.getSelectedIndex()];
        // Check for filters
        if(queryConditionList.size() > 0) {
            // Filter
            Query query = new Query(cellar.getWineSampleList(type), queryConditionList, type);
            filteredWineSampleList = query.solveQuery();
        }
        else
            // Full list
            filteredWineSampleList = cellar.getWineSampleList(type);
        updateWineList();
    }

}

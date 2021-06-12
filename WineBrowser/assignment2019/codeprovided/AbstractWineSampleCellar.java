package assignment2019.codeprovided;
/**
 * Main method - receives CSV files with the relevant wine sample data and a text file with relevant query data and calls the readWineFile and readQueryFile methods.
 * <p>
 * Method accepts 2 documents made up of one line of Strings and multiple lines of doubles. It then calls the
 * readWineFile method. Also accepts a text file made up of lines of Strings which it calls the readQueryFile method on.
 * <p>
 * Method stores three ArrayLists of Wine samples. One is for red wines, the second is for white wines and the third is a combined list.
 * <p>
 * Method also stores ArrayLists of queries passed from the text file.
 * <p>
 * Method then calls further methods to print statistical information about the wine lists passed to it. It also
 * contains the format which the information is printed in.
 * <p>
 * Following this, the method creates the GUI for the Portuguese Wine Samples program
 *
 * @param args two CSV files and a text file defined by the user
 */

// Import statements

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
 * AbstractWineSampleCellar.java  	1.0  06/04/2019
 *
 * Copyright (c) University of Sheffield 2019
 */

/**
 * AbstractWineSampleCellar.java
 *
 * Abstract class designed to be extended. 
 * Provides basic reading functionalities of wine sample datasets and queries.
 * Provides 
 *
 * @version 1.0  06/04/2019
 *
 * @author Maria-Cruz Villa-Uriol (m.villa-uriol@sheffield.ac.uk)
 */

public abstract class AbstractWineSampleCellar {

	protected Map<WineType, List<WineSample>> wineSampleRacks;

	/**
	 * constructor - reads wine sample datasets and list of queries from text file,
	 * and initialises the wineSampleRacks Map
     */
    public AbstractWineSampleCellar(String redWineFilename, String whiteWineFilename, String queryFilename) {

        wineSampleRacks = new HashMap<>();
        // editWineList will read the data and will insert the list of samples into the wineSampleRacks variable 
        editWineList(WineType.RED, redWineFilename);
        editWineList(WineType.WHITE, whiteWineFilename);

        // updateCellar will update wineSampleRacks to contain 'also' an additional list containing all wine samples (in this case red and white)
        updateCellar();
    }

    /**
     * readWineFile method - reads the two CSV files and the type passed by the main. It then reads the contents of the files
     * and creates the relevant wine sample objects and returns them into a list. Catches exception errors
     * should they occur.
     *
     * @param wineFile This is either the redWineFile or whiteWineFile depending on which list called the method
     * @param wineType This is a WineType enum containing either red or white depending on which list called the method
     * @return List of WineSample objects
     */
    public static List<WineSample> readWineFile(String wineFile,  WineType wineType) throws IllegalArgumentException {
        List<WineSample> wineList = new ArrayList<>();
        String split = ";";
        int count = 1;
        wineFile = wineFile.replaceAll(" ", "%20");

        try (BufferedReader br = new BufferedReader(new FileReader(wineFile))) {
            String line = br.readLine();
            if (line == null) {
                throw new IllegalArgumentException("File is empty. Please, provide a valid dataset.");
            }
            while ((line = br.readLine()) != null) {
                String[] wineLine = line.toLowerCase().split(split);
                if (wineLine.length != 12) {
                    throw new IllegalArgumentException("Inadequate file format. Please, provide a valid dataset.");
                }
                try {
                    double fixedAcidity = Double.parseDouble(wineLine[0]);
                    double volatileAcidity = Double.parseDouble((wineLine[1]));
                    double citricAcid = Double.parseDouble(wineLine[2]);
                    double residualSugar = Double.parseDouble((wineLine[3]));
                    double chlorides = Double.parseDouble(wineLine[4]);
                    double freeSulfurDioxide = Double.parseDouble((wineLine[5]));
                    double totalSulfurDioxide = Double.parseDouble(wineLine[6]);
                    double density = Double.parseDouble((wineLine[7]));
                    double pH = Double.parseDouble(wineLine[8]);
                    double sulphates = Double.parseDouble((wineLine[9]));
                    double alcohol = Double.parseDouble(wineLine[10]);
                    double quality = Double.parseDouble((wineLine[11]));
                    // the wine sample ID is created by this reader, it is not provided in the original files
                    int id = count;		
                    WineSample wine = new WineSample(id, wineType, fixedAcidity, volatileAcidity, citricAcid, residualSugar, chlorides, freeSulfurDioxide, totalSulfurDioxide, density, pH, sulphates, alcohol, quality);
                    wineList.add(wine);
                    count++;
                } catch (NumberFormatException e) {
                    System.out.println("File format is incorrect - only double values are allowed. Please, revise.");
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println(wineFile + " could not be found. Please, provide a correct filename.");

        } catch (IOException e) {
            System.out.println("For unknown reasons the file could not be handled. Please, revise if you are providing the correct datasets and using the appropriate file formats.");
            // enable the next line for debugging purposes
            // e.printStackTrace();
        }

        return wineList;
    }

    /**
     * readQueryFile - reads each query and splits each word in the queryFile into individual Strings and stores them in an ArrayList
     * for further processing and interpretation of readQueries method
     *
     * @param queryFile The text file containing relevant queries
     * @return ArrayList of Strings
     */
    public static List<String> readQueryFile(String queryFile) {

        List<String> textQueries = new ArrayList<>();
        String split = " ";

        try (BufferedReader br = new BufferedReader(new FileReader(queryFile))) {
            String line = br.readLine();
            if (line == null) {
                throw new IllegalArgumentException("File is empty");
            }
            while (line != null) {
                String[] query = line.toLowerCase().split(split);
                for (int i = 0; i < query.length; i++) {
                	textQueries.add(query[i]);
                }
                line = br.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println(queryFile + " could not be found");

        } catch (IOException e) {
            System.out.println("File could not be handled");
            // enable the next line for debugging purposes
            // e.printStackTrace();
        }
        return textQueries;
    }

    /**
     * Reads the contents of <code>filename</code> and stores it in the wineRack as wine type <code>wineType</code>
     * @param wineType Either RED, WHITE or ALL
     * @param filename the name of the .csv to read
     */
    public void editWineList(WineType wineType, String filename){
        wineSampleRacks.put(wineType, new ArrayList<>(readWineFile(filename, wineType)));
    }
    
    /**
     * getWineSampleList method - returns the list of wine samples relevant to the specified Wine Type. 
     * Returns a List<WineSample>.
     *
     * @param wineType
     * @return Either redWineSamplesList, whiteWineSamplesList or allWineSamplesList
     */
    public List<WineSample> getWineSampleList(WineType wineType) {
        return wineSampleRacks.get(wineType);
    }

    /**
     * getWineSampleCount method - returns how many wine samples of the given type are stored
     *
     * @param wineType Either RED, WHITE or ALL
     * @return number of wines held of type <code>wineType</code>
     */
    public int getWineSampleCount(WineType wineType) {
        List<WineSample> list = getWineSampleList(wineType);
        return list.size(); // list should never be null
    }
    
    // list of abstract methods starts
    
    /**
     * readQueries method - 
     * 1 - receives the List of Strings, each of the is one query as specified
     * in the handout (section "The queries")
     * 2 - assesses their content, creates the relevant QueryCondition and Query objects 
     * 3 - and then returns a List of the Query objects
     *
     * @param queryList The List of Strings from the readQueryFile method
     * @return List of all Query objects
     */
    public abstract List<Query> readQueries(List<String> queryList);
    
    /**
     * updateCellar method - updates wineSampleRacks to contain 'also' an additional list 
     * containing ALL wine samples (in this case red and white)
     */
    public abstract void updateCellar();

    /**
     * displayQueryResults method - displays in console the results of a query in a meaningful format to the user
     *
     * @param query The Query object to be printed
     */
    public abstract void displayQueryResults(Query query);

    /**
     * bestQualityWine method - receives the wine type
     * Returns a list of objects which have been assigned the highest quality score in the list.
     *
     * @param wineType Either RED, WHITE or ALL
     * @return collection of WineSample objects with the highest quality
     */
    public abstract List<WineSample> bestQualityWine(WineType wineType);

    /**
     * worstQualityWine method - receives the wine type 
     * Returns a list of objects which have been assigned the lowest quality score in the list.
     *
     * @param wineType Either RED, WHITE or ALL
     * @return collection of WineSample objects with the lowest quality
     */
    public abstract List<WineSample> worstQualityWine(WineType wineType);

    /**
     * highestPH method - receives the wine type 
     * Returns a list of objects which have the highest pH in the list.
     *
     * @param wineType Either RED, WHITE or ALL
     * @return collection of WineSample objects with the highest pH
     */
    public abstract List<WineSample> highestPH(WineType wineType);
    
    /**
     * lowestPH method - receives the wine type 
     * Returns a list of objects which have the lowest pH in the list.
     *
     * @param wineType Either RED, WHITE or ALL
     * @return collection of WineSample objects with the lowest pH
     */
    public abstract List<WineSample> lowestPH(WineType wineType);

    /**
     * highestAlcoholContent method - receives the wine type
     * Returns the highest alcohol value in the list for the specified wineType.
     *
     * @param wineType Either RED, WHITE or ALL
     * @return  a double value with the highest alcohol content
     */
    public abstract double highestAlcoholContent(WineType wineType);

    /**
     * lowestCitricAcid method - receives the wine type
     * Returns the lowest citric acid value in the list for the specified wineType.
     *
     * @param wineType Either RED, WHITE or ALL
     * @return a double value with the lowest citric acid content
     */
    public abstract double lowestCitricAcid(WineType wineType);

    /**
     * averageAlcoholContent method - receives the wine type
     * Returns the count variable divided by the number of objects in the List.
     *
     * @param wineType Either RED, WHITE or ALL
     * @return average alcohol content of the list as a double
     */
    public abstract double averageAlcoholContent(WineType wineType);
       
}

package assignment2019;
import assignment2019.codeprovided.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * WineSampleCellar.java
 *
 * Class that provides functionalities of processing wine sample datasets and queries.
 *
 * @version 1.0  30/04/2019
 *
 * @author Boxuan Shan
 */

public class WineSampleCellar extends AbstractWineSampleCellar {

    public WineSampleCellar(String redWineFilename, String whiteWineFilename, String queryFilename) {
        super(redWineFilename, whiteWineFilename, queryFilename);
    }

    /**
     * Read a list of strings and return a list of Queries
     * @param queryList The List of Strings from the readQueryFile method
     * @return List of all Query objects
     */
    public List<Query> readQueries(List<String> queryList) {
        Iterator<String> wordsIterator = queryList.iterator();
        List<Query> queries = new ArrayList<Query>();
        // Parse text
        if(wordsIterator.next().equals("select"))
            while(wordsIterator.hasNext()) {
                // Process types
                List<WineType> types = new ArrayList<WineType>();
                do {
                    types.add(WineType.valueOf(wordsIterator.next().toUpperCase()));
                }while(wordsIterator.next().equals("or"));
                // Merge types
                WineType type;
                if(types.size() == 2)
                    type = WineType.ALL;
                else
                    type = types.get(0);

                // Process conditions
                List<QueryCondition> conditions = new ArrayList<QueryCondition>();
                do {
                    WineProperty property = WineProperty.fromFileIdentifier(wordsIterator.next());
                    String operator = wordsIterator.next();
                    String value;

                    // Check if the operator and value are not split
                    if((operator.startsWith(">=") || operator.startsWith("!=") || operator.startsWith("<="))
                            && operator.length() > 2){
                        value = operator.substring(2);
                        operator = operator.substring(0, 2);
                    }
                    else if(!(operator.startsWith(">=") || operator.startsWith("!=") || operator.startsWith("<="))
                            && (operator.startsWith(">") || operator.startsWith("=") || operator.startsWith("<"))
                            && operator.length() > 1) {
                        value = operator.substring(1);
                        operator = operator.substring(0, 1);
                    }
                    else
                        value = wordsIterator.next();

                    // Construct QueryCondition
                    conditions.add(new QueryCondition(property, operator, Double.valueOf(value)));
                }while (wordsIterator.hasNext() && wordsIterator.next().equals("and"));

                // Construct Query
                List<WineSample> samples = new ArrayList<WineSample>();
                samples.addAll(this.getWineSampleList(type));
                queries.add(new Query(samples, conditions, type));
            }
        return queries;
    }

    /**
     * Updates wineSampleRacks to contain 'also' an additional list
     * containing ALL wine samples (in this case red and white)
     */
    public void updateCellar() {
        List<WineSample> samples = new ArrayList<WineSample>();
        for(WineType type : this.wineSampleRacks.keySet())
            samples.addAll(wineSampleRacks.get(type));
        this.wineSampleRacks.put(WineType.ALL, samples);
    }

    /**
     * Displays in console the results of a query in a meaningful format to the user
     * @param query The Query object to be printed
     */
    public void displayQueryResults(Query query) {
        String type;
        if(query.getWineType() == WineType.ALL)
            type = "red or white";
        else
            type = query.getWineType().toString().toLowerCase();

        String conditions = query.getQueryConditionList().get(0).toString();
        if(query.getQueryConditionList().size() > 1)
            for(int i=1; i<query.getQueryConditionList().size(); i++)
            conditions += " and " + query.getQueryConditionList().get(i).toString();

        System.out.println("* Select " + type + " where " + conditions + " *");

        List<WineSample> result = query.solveQuery();
        System.out.println("In total, " + result.size() + " " + type + " wine samples match your query");
        if(result.size() > 0) {
            System.out.println("The list of " + type + " wine samples are:");
            for (WineSample sample : result) {
                System.out.println("[" + sample.getType() + " wine, "
                                + "sample #" +  sample.getId() + ", "
                                + "Fixed acidity: " + sample.getFixedAcidity() +  ", "
                                + "Volatile acidity: " + sample.getVolatileAcidity() + ", "
                                + "Citric acid: " + sample.getCitricAcid() + ", "
                                + "Residual sugar: " + sample.getResidualSugar() + ", "
                                + "Chlorides: " + sample.getChlorides() + ", "
                                + "Free sulfur dioxide: " + sample.getFreeSulfurDioxide() + ", "
                                + "Total sulfur dioxide: " + sample.getTotalSulfurDioxide() + ", "
                                + "Density: " + sample.getDensity() + ", "
                                + "pH: " + sample.getpH() + ", "
                                + "Sulphates: " + sample.getSulphates() + ", "
                                + "Alcohol: " + sample.getAlcohol() + ", "
                                + "Quality: " + sample.getQuality() + "]");
            }
        }
    }

    /**
     * Receives the wine type
     * Returns a list of objects which have been assigned the highest quality score in the list.
     * @param wineType Either RED, WHITE or ALL
     * @return collection of WineSample objects with the highest quality
     */
    public List<WineSample> bestQualityWine(WineType wineType) {
        List<WineSample> samples = getSortedSamples(this.getWineSampleList(wineType), WineProperty.Quality);
        double bestQuality = samples.get(samples.size()-1).getQuality();
        List<WineSample> results = new ArrayList<WineSample>();
        for(int i = samples.size()-1; i>=0; i--) {
            WineSample sample = samples.get(i);
            if(sample.getQuality() != bestQuality)
                break;
            results.add(sample);
        }
        return results;
    }

    /**
     * Receives the wine type
     * Returns a list of objects which have been assigned the lowest quality score in the list.
     * @param wineType Either RED, WHITE or ALL
     * @return collection of WineSample objects with the lowest quality
     */
    public List<WineSample> worstQualityWine(WineType wineType) {
        List<WineSample> samples = getSortedSamples(this.getWineSampleList(wineType), WineProperty.Quality);
        double worstQuality = samples.get(0).getQuality();
        List<WineSample> results = new ArrayList<WineSample>();
        for(int i = 0; i<samples.size(); i++) {
            WineSample sample = samples.get(i);
            if(sample.getQuality() != worstQuality)
                break;
            results.add(sample);
        }
        return results;
    }

    /**
     * Receives the wine type
     * Returns a list of objects which have the highest pH in the list.
     * @param wineType Either RED, WHITE or ALL
     * @return collection of WineSample objects with the highest pH
     */
    public List<WineSample> highestPH(WineType wineType) {
        List<WineSample> samples = getSortedSamples(this.getWineSampleList(wineType), WineProperty.PH);
        double highestPH = samples.get(samples.size()-1).getpH();
        List<WineSample> results = new ArrayList<WineSample>();
        for(int i = samples.size()-1; i>=0; i--) {
            WineSample sample = samples.get(i);
            if(sample.getpH() != highestPH)
                break;
            results.add(sample);
        }
        return results;
    }

    /**
     * Receives the wine type
     * Returns a list of objects which have the lowest pH in the list.
     * @param wineType Either RED, WHITE or ALL
     * @return collection of WineSample objects with the lowest pH
     */
    public List<WineSample> lowestPH(WineType wineType) {
        List<WineSample> samples = getSortedSamples(this.getWineSampleList(wineType), WineProperty.PH);
        double lowestPH = samples.get(0).getpH();
        List<WineSample> results = new ArrayList<WineSample>();
        for(int i = 0; i<samples.size(); i++) {
            WineSample sample = samples.get(i);
            if(sample.getpH() != lowestPH)
                break;
            results.add(sample);
        }
        return results;
    }

    /**
     * Receives the wine type
     * Returns the highest alcohol value in the list for the specified wineType.
     * @param wineType Either RED, WHITE or ALL
     * @return  a double value with the highest alcohol content
     */
    public double highestAlcoholContent(WineType wineType) {
        List<WineSample> samples = getSortedSamples(this.getWineSampleList(wineType), WineProperty.Alcohol);
        return samples.get(samples.size()-1).getAlcohol();
    }

    /**
     * Receives the wine type
     * Returns the lowest citric acid value in the list for the specified wineType.
     * @param wineType Either RED, WHITE or ALL
     * @return a double value with the lowest citric acid content
     */
    public double lowestCitricAcid(WineType wineType) {
        List<WineSample> samples = getSortedSamples(this.getWineSampleList(wineType), WineProperty.CitricAcid);
        return samples.get(0).getCitricAcid();
    }

    /**
     * Receives the wine type
     * Returns the count variable divided by the number of objects in the List.
     * @param wineType Either RED, WHITE or ALL
     * @return average alcohol content of the list as a double
     */
    public double averageAlcoholContent(WineType wineType) {
        List<WineSample> samples = new ArrayList<WineSample>();
        samples.addAll(this.getWineSampleList(wineType));
        double averageAlcohol = 0;
        for(WineSample sample : samples)
            averageAlcohol += sample.getAlcohol();
        averageAlcohol /= samples.size();
        return averageAlcohol;
    }

    public static List<WineSample> getSortedSamples(List<WineSample> samples, WineProperty sortProperty) {
        List<WineSample> results = new ArrayList<>();
        SortedSet<WineSample> wineSet = new TreeSet<>(new PropertyComparator(sortProperty));
        wineSet.addAll(samples);
        results.addAll(wineSet);
        return results;
    }

    /**
     *
     * Comparator class to sort the samples in the TreeSet by Property
     *
     * @version 1.0  06/04/2019
     *
     * @author Maria-Cruz Villa-Uriol (m.villa-uriol@sheffield.ac.uk)
     */
    private static class PropertyComparator implements Comparator<WineSample>
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

}

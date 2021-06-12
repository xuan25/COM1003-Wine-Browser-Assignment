package assignment2019;
import assignment2019.codeprovided.*;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JFrame;

/**
 * WineSampleBrowser.java
 *
 * Main class of the browser
 *
 * @version 1.0  30/04/2019
 *
 * @author Boxuan Shan
 */

public class WineSampleBrowser extends JFrame {

    // Frame property
    private final int FRAME_WIDTH = 1300, FRAME_HEIGHT = 730;

    public WineSampleBrowser(String wineRedFile, String wineWhiteFile, String queryFile) {
        // Construct cellar
        WineSampleCellar cellar = new WineSampleCellar(wineRedFile, wineWhiteFile, queryFile);

        // Construct and show the panel
        WineSampleBrowserPanel panel = new WineSampleBrowserPanel(cellar);
        getContentPane().add(panel);

        setTitle("Wine Sample Browser");
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocation((screenDimension.width-FRAME_WIDTH)/2, (screenDimension.height-FRAME_HEIGHT)/2);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Console output
        handleQuestions(cellar);
        handleQuery(cellar, queryFile);
    }

    /**
     * Print out Q&A in the console
     * @param cellar WineSampleCellar
     */
    private void handleQuestions(WineSampleCellar cellar) {
        // Q1
        System.out.println("Q1. Total number of wine samples: "
                            + cellar.getWineSampleCount(WineType.ALL));
        System.out.println();

        // Q2
        System.out.println("Q2. Number of RED wine samples: "
                            + cellar.getWineSampleCount(WineType.RED)
                            + " out of " + cellar.getWineSampleCount(WineType.ALL));
        System.out.println();

        // Q3
        System.out.println("Q3. Number of WHITE wine samples: "
                            + cellar.getWineSampleCount(WineType.WHITE)
                            + " out of " + cellar.getWineSampleCount(WineType.ALL));
        System.out.println();

        // Q4
        System.out.println("Q4. The best quality wine samples are:");
        for(WineSample sample : cellar.bestQualityWine(WineType.ALL))
            System.out.println("* Wine ID " + sample.getId() +  " of type " + sample.getType()
                                +  " with a quality score of " + sample.getQuality());
        System.out.println();

        // Q5
        System.out.println("Q5. The worst quality wine samples are:");
        for(WineSample sample : cellar.worstQualityWine(WineType.ALL))
            System.out.println("* Wine ID " + sample.getId() +  " of type " + sample.getType()
                                + " with a quality score of " + sample.getQuality());
        System.out.println();

        // Q6
        System.out.println("Q6. The highest PH wine samples are:");
        for(WineSample sample : cellar.highestPH(WineType.ALL))
            System.out.println("* Wine ID " + sample.getId() +  " of type " + sample.getType()
                                + " with a PH of " + sample.getpH());
        System.out.println();

        // Q7
        System.out.println("Q7. The lowest PH wine samples are:");
        for(WineSample sample : cellar.lowestPH(WineType.ALL))
            System.out.println("* Wine ID " + sample.getId() +  " of type " + sample.getType()
                                + " with a PH of " + sample.getpH());
        System.out.println();

        // Q8
        System.out.println("Q8. The highest value of alcohol grade for the whole sample of red wines: "
                            + cellar.highestAlcoholContent(WineType.RED));
        System.out.println();

        // Q9
        System.out.println("Q9. The lowest value of citric acid for the whole sample of white wines: "
                            + cellar.lowestCitricAcid(WineType.WHITE));
        System.out.println();

        // Q10
        System.out.println("Q10. The average value of alcohol grade for the whole sample of white wines: "
                            + cellar.averageAlcoholContent(WineType.WHITE));
        System.out.println();
    }

    /**
     * Print out Queries in the console
     * @param cellar WineSampleCellar
     * @param queriesFile queries Filename
     */
    private void handleQuery(WineSampleCellar cellar, String queriesFile) {
        List<String> queryList = WineSampleCellar.readQueryFile(queriesFile);
        List<Query> queries = cellar.readQueries(queryList);
        // print out
        for (int i=0; i<queries.size(); i++) {
            System.out.println("****** Query #" + (i+1) +" ******");
            cellar.displayQueryResults(queries.get(i));
            System.out.println("************************");
            System.out.println();
        }
    }

    public static void main(String args[]) {
        String redWineFile = args[0];
        String whiteWineFile = args[1];
        String queriesFile = args[2];

        new WineSampleBrowser(redWineFile, whiteWineFile, queriesFile);
    }
}

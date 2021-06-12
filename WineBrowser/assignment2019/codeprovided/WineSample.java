package assignment2019.codeprovided;

/*
 * WineSample.java  	1.0  06/04/2019
 *
 * Copyright (c) University of Sheffield 2019
 */

/**
 * WineSample.java
 *
 * Class designed to be used to create objects from wine samples.
 *
 * @version 1.0  06/04/2019
 *
 * @author Maria-Cruz Villa-Uriol (m.villa-uriol@sheffield.ac.uk)
 */

public class WineSample {

    // Instance variables
    private int id;
    private WineType type;
    private double fixedAcidity;
    private double volatileAcidity;
    private double citricAcid;
    private double residualSugar;
    private double chlorides;
    private double freeSulfurDioxide;
    private double totalSulfurDioxide;
    private double density;
    private double pH;
    private double sulphates;
    private double alcohol;
    private double quality;

    // Constructor
    public WineSample(int id, WineType type, double fixedAcidity, double volatileAcidity, double citricAcid, double residualSugar, double chlorides, double freeSulfurDioxide, double totalSulfurDioxide, double density, double pH, double sulphates, double alcohol, double quality) {
        this.id = id;
        this.type = type;
        this.fixedAcidity = fixedAcidity;
        this.volatileAcidity = volatileAcidity;
        this.citricAcid = citricAcid;
        this.residualSugar = residualSugar;
        this.chlorides = chlorides;
        this.freeSulfurDioxide = freeSulfurDioxide;
        this.totalSulfurDioxide = totalSulfurDioxide;
        this.density = density;
        this.pH = pH;
        this.sulphates = sulphates;
        this.alcohol = alcohol;
        this.quality = quality;
    }

    /**
     * Get the value of a given property of the wine sample
     * @param the wine property to select
     * @return the value of the chosen wine property
     */
    public double getProperty(WineProperty property)
    {
        switch (property)
        {
            case FixedAcidity:
                return getFixedAcidity();
            case VolatileAcidity:
                return getVolatileAcidity();
            case CitricAcid:
                return getCitricAcid();
            case ResidualSugar:
                return getResidualSugar();
            case Chlorides:
                return getChlorides();
            case FreeSulfurDioxide:
                return getFreeSulfurDioxide();
            case TotalSulfurDioxide:
                return getTotalSulfurDioxide();
            case Density:
                return getDensity();
            case PH:
                return getpH();
            case Sulphates:
                return getSulphates();
            case Alcohol:
                return getAlcohol();
            case Quality:
                return getQuality();
            default:
                return -1;
        }
    }

    /**
     * Getter for id of WineSample object
     *
     * @return id of WineSample object
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for fixedAcidity of WineSample object
     *
     * @return fixedAcidity of WineSample object
     */
    public double getFixedAcidity() {
        return this.fixedAcidity;
    }

    /**
     * Getter for volatileAcidity of WineSample object
     *
     * @return volatileAcidity of WineSample object
     */
    public double getVolatileAcidity() {
        return this.volatileAcidity;
    }

    /**
     * Getter for citricAcid of WineSample object
     *
     * @return citricAcid of WineSample object
     */
    public double getCitricAcid() {
        return this.citricAcid;
    }

    /**
     * Getter for residualSugar of WineSample object
     *
     * @return residualSugar of WineSample object
     */
    public double getResidualSugar() {
        return this.residualSugar;
    }

    /**
     * Getter for chlorides of WineSample object
     *
     * @return chlorides of WineSample object
     */
    public double getChlorides() {
        return this.chlorides;
    }

    /**
     * Getter for freeSulfurDioxide of WineSample object
     *
     * @return freeSulfurDioxide of WineSample object
     */
    public double getFreeSulfurDioxide() {
        return this.freeSulfurDioxide;
    }

    /**
     * Getter for totalSulfurDioxide of WineSample object
     *
     * @return totalSulfurDioxide of WineSample object
     */
    public double getTotalSulfurDioxide() {
        return this.totalSulfurDioxide;
    }

    /**
     * Getter for density of WineSample object
     *
     * @return density of WineSample object
     */
    public double getDensity() {
        return this.density;
    }

    /**
     * Getter for pH of WineSample object
     *
     * @return pH of WineSample object
     */
    public double getpH() {
        return this.pH;
    }

    /**
     * Getter for sulphates of WineSample object
     *
     * @return sulphates of WineSample object
     */
    public double getSulphates() {
        return this.sulphates;
    }

    /**
     * Getter for alcohol of WineSample object
     *
     * @return alcohol of WineSample object
     */
    public double getAlcohol() {
        return this.alcohol;
    }

    /**
     * Getter for quality of WineSample object
     *
     * @return quality of WineSample object
     */
    public double getQuality() {
        return this.quality;
    }

    /**
     * Setter for type of WineSample object
     *
     * @return type of WineSample object
     */
    public WineType getType() {
        return this.type;
    }
    
    /**
     * Setter for id of WineSample object
     *
     * @param id of WineSample object
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter for fixedAcidity of WineSample object
     *
     * @param fixedAcidity of WineSample object
     */
    public void setFixedAcidity(double fixedAcidity) {
        this.fixedAcidity = fixedAcidity;
    }

    /**
     * Setter for volatileAcidity of WineSample object
     *
     * @param volatileAcidity of WineSample object
     */
    public void setVolatileAcidity(double volatileAcidity) {
        this.volatileAcidity = volatileAcidity;
    }

    /**
     * Setter for citricAcid of WineSample object
     *
     * @param citricAcid of WineSample object
     */
    public void setCitricAcid(double citricAcid) {
        this.citricAcid = citricAcid;
    }

    /**
     * Setter for residualSugar of WineSample object
     *
     * @param residualSugar of WineSample object
     */
    public void setResidualSugar(double residualSugar) {
        this.residualSugar = residualSugar;
    }

    /**
     * Setter for chlorides of WineSample object
     *
     * @param chlorides of WineSample object
     */
    public void setChlorides(double chlorides) {
        this.chlorides = chlorides;
    }

    /**
     * Setter for freeSulfurDioxide of WineSample object
     *
     * @param freeSulfurDioxide of WineSample object
     */
    public void setFreeSulfurDioxide(double freeSulfurDioxide) {
        this.freeSulfurDioxide = freeSulfurDioxide;
    }

    /**
     * Setter for totalSulfurDioxide of WineSample object
     *
     * @param totalSulfurDioxide of WineSample object
     */
    public void setTotalSulfurDioxide(double totalSulfurDioxide) {
        this.totalSulfurDioxide = totalSulfurDioxide;
    }

    /**
     * Setter for density of WineSample object
     *
     * @param density of WineSample object
     */
    public void setDensity(double density) {
        this.density = density;
    }

    /**
     * Setter for pH of WineSample object
     *
     * @param pH of WineSample object
     */
    public void setpH(double pH) {
        this.pH = pH;
    }

    /**
     * Setter for sulphates of WineSample object
     *
     * @param sulphates of WineSample object
     */
    public void setSulphates(double sulphates) {
        this.sulphates = sulphates;
    }

    /**
     * Setter for alcohol of WineSample object
     *
     * @param alcohol of WineSample object
     */
    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    /**
     * Setter for quality of WineSample object
     *
     * @param quality of WineSample object
     */
    public void setQuality(int quality) {
        this.quality = quality;
    }

    /**
     * Setter for type of WineSample object
     *
     * @param type of WineSample object
     */
    public void setType(WineType type) {
        this.type = type;
    }
    
    public static void main(String args[]) {
    	// a few tests here
    	WineSample w = new WineSample(125, WineType.RED, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1, 1.1, 1.2);
    	System.out.println(w.getType());
    	WineSample w2 = new WineSample(125, WineType.WHITE, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1, 1.1, 1.2);
    	System.out.println(w2.getType());
    }
}

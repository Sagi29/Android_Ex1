package com.example.exercise_1;

public class TopTen {

    private double lat;
    private double lon;
    private long timestamp;
    private int numOfMoves;


    public TopTen() { }

    public TopTen(double lat, double lon, long timestamp, int numOfMoves) {
        this.lat = lat;
        this.lon = lon;
        this.timestamp = timestamp;
        this.numOfMoves = numOfMoves;
    }

    public double getLat() {
        return lat;
    }

    public TopTen setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLon() {
        return lon;
    }

    public TopTen setLon(double lon) {
        this.lon = lon;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public TopTen setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getNumOfMoves() {
        return numOfMoves;
    }

    public TopTen setNumOfMoves(int numOfMoves) {
        this.numOfMoves = numOfMoves;
        return this;
    }

}

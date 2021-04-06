package com.nlstn.coords;

public class Coordinate {

    private String id;
    private String owner;
    private String coord;

    public Coordinate(String id, String owner, String coord) {
        this.id = id;
        this.owner = owner;
        this.coord = coord;
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getCoord() {
        return coord;
    }

    @Override
    public String toString() {
        return id + ": " + coord;
    }

}

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

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCoord() {
        return coord;
    }

    @Override
    public String toString() {
        return id + ": " + coord;
    }

}

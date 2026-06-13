package com.renwer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vector {

    private String vectorClass;
    private List<Double> coordinates;
    private int dimension;


    public Vector(List<Double> coordinates, String vectorClass) {
        this.vectorClass = vectorClass;
        this.coordinates = coordinates;
        this.dimension = coordinates.size();
    }

    public Vector(List<Double> coordinates) {
        this(coordinates, null);
    }

    //Initializes a zero vector in R defined by dimension
    public Vector(int dimension) {
        ArrayList<Double> coordinates = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            coordinates.add(0.0);
        }
        this.vectorClass = null;
        this.coordinates = coordinates;
        this.dimension = dimension;
    }

    public String getVectorClass() {
        return this.vectorClass;
    }

    public int getDimension() {
        return dimension;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setVectorClass(String vectorClass) {
        this.vectorClass = vectorClass;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "vectorClass='" + vectorClass + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Objects.equals(vectorClass, vector.vectorClass) &&
                Objects.equals(coordinates, vector.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vectorClass, coordinates);
    }
}


package com.renwer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Model {

    private Set<Vector> trainingSet; //all vectors read from the training set file
    private int k;

    public Model(String trainingSetPath, String testingSetPath, int k) throws Exception {
        this.k = k;
        trainingSet = readDataSet(trainingSetPath);
        System.out.println("Finished reading training set!");
        System.out.println("Estimated model's precision: " + getPrecision(testingSetPath));
    }

    private double getVectorDistance(Vector vector1, Vector vector2) throws Exception {

        if (vector1.getDimension() == vector2.getDimension()) {

            double distanceAcc = 0;

            for (int i = 0; i < vector1.getDimension(); i++) {
                distanceAcc += Math.pow((vector2.getCoordinates().get(i) - vector1.getCoordinates().get(i)), 2);
            }
            return Math.sqrt(distanceAcc);
        } else {
            throw new Exception("Vector dimension inconsistent with the training set!");
        }
    }

    public String classify(Vector inputVector) throws Exception {
        String result = null;
        Map<Vector, Double> distances = new HashMap<>();

        for (Vector vector: trainingSet) {
            distances.put(vector, getVectorDistance(vector, inputVector));
        }

        List<Vector> nearestNeigbors = distances.entrySet().stream() //sort the map
                                        .sorted(Map.Entry.comparingByValue())
                                        .map(Map.Entry::getKey)
                                        .collect(Collectors.toList());

        Map<String, Integer> typeCount = new HashMap<>(); //declare another map with type count
        for (int i = 0; i < k; i++) {
            int count = typeCount.getOrDefault(nearestNeigbors.get(i).getVectorClass(), 0);
            typeCount.put(nearestNeigbors.get(i).getVectorClass(), count + 1);
        }
        int number = 0;
        for (String key : typeCount.keySet()) { //find the most frequently occurring Vector Class
            if (typeCount.get(key) > number) {
                result = key;
                number = typeCount.get(key);
            } else if (typeCount.get(key) == number) {
                result = "Don't know";
                number = typeCount.get(key);
            }
        }
        return result;
    }


    private Set<Vector> readDataSet(String path) {
        BufferedReader reader;
        Set<Vector> resultSet = new HashSet<>();
        try {
            reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                String vectorClass = null;
                String[] fields = line.split(",");
                List<Double> coordinates = new ArrayList<>();
                for (int i = 0; i < fields.length; i++) {
                    try {
                        coordinates.add(Double.parseDouble(fields[i]));
                    } catch (NumberFormatException nfe) {
                        vectorClass = fields[i];
                    }
                }
                Vector vector = new Vector(coordinates, vectorClass);
                resultSet.add(vector);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    private double getPrecision(String testingSetPath) throws Exception {
        Set<Vector> testSet = readDataSet(testingSetPath);
        double mistakesCount = 0, testSetSize = testSet.size();
        for (Vector v: testSet) {
            String vectorClass = v.getVectorClass();
            Vector testVector = new Vector(v.getCoordinates());

            if (!classify(testVector).equals(vectorClass)) {
                mistakesCount++;
            }
        }
        //System.out.println("Mistakes count: " + mistakesCount + ", test set size: " + testSetSize);
        return 100 - (mistakesCount/testSetSize)*100;
    }
}

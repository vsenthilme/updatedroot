package com.renwer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please enter the K of nearest neighbors to be considered");
        int k = Integer.parseInt(reader.readLine());

        Model model = new Model("train.txt", "test.txt", k);

        System.out.println(model.classify(new Vector(Arrays.asList(5.6,3.0,4.1,1.0))));
        System.out.println(model.classify(new Vector(Arrays.asList(5.8,2.6,4.0,1.2))));
        System.out.println(model.classify(new Vector(Arrays.asList(6.6,2.9,4.6,1.3))));

        while (true) {
            System.out.println("Please enter a vector to classify: ");
            String[] vectorString = reader.readLine().replaceAll(" ", "").split(",");
            try {
                List<Double> coordinates = new ArrayList<>();
                for (int i = 0; i < vectorString.length; i++) {
                    coordinates.add(Double.parseDouble(vectorString[i]));
                }
                Vector vector = new Vector(coordinates);
                System.out.println("Your Iris was classified as: " + model.classify(vector));
            } catch (Exception e) {
                System.out.println("Please enter a valid vector for the supplied training set.\nComma-separated ints or doubles, no repeated spaces.");
            }
        }

    }
}

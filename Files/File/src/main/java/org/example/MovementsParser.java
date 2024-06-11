package org.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovementsParser {
    public static void task() {

        String path = "C:\\Users\\marle\\Downloads\\movementList.csv";
        List<String> lines = new ArrayList<>();
        Map<String, Double> result = new HashMap<>();
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String line : lines.subList(1, lines.size())) {
            String[] fragments = line.split(",");
            String paymentType = getPaymentType(fragments[5]);
            String moneyLost = fragments[7];
            if (!result.containsKey(paymentType)) {
                result.put(paymentType, 0.);
            }
            double sum = result.get(paymentType) + Double.parseDouble(moneyLost);
            result.put(paymentType, sum);
        }

        for (String key : result.keySet()){
            System.out.println(key + ": " + result.get(key));
        }
    }

    public static String getPaymentType(String paymentMethod){
        String regex = "[^a-zA-Z0-9]([a-zA-Z0-9\s]+)[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}\s[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(paymentMethod);
        return matcher.find() ? matcher.group(1).trim() : null;
    }
}
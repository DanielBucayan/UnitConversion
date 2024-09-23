package com.apc.entjavamid40;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("convert")
public class MyController {

    // Allowed units for conversion
    List<String> allowedUnits = Arrays.asList("mm", "cm", "in", "m", "ft");

    // Conversion factors relative to meters
    private double convertToMeters(double value, String unit) {
        switch (unit) {
            case "mm":
                return value / 1000;
            case "cm":
                return value / 100;
            case "in":
                return value * 0.0254;
            case "m":
                return value;
            case "ft":
                return value * 0.3048;
            default:
                throw new IllegalArgumentException("Invalid unit: " + unit);
        }
    }

    // Convert from meters to the target unit
    private double convertFromMeters(double valueInMeters, String targetUnit) {
        switch (targetUnit) {
            case "mm":
                return valueInMeters * 1000;
            case "cm":
                return valueInMeters * 100;
            case "in":
                return valueInMeters / 0.0254;
            case "m":
                return valueInMeters;
            case "ft":
                return valueInMeters / 0.3048;
            default:
                throw new IllegalArgumentException("Invalid target unit: " + targetUnit);
        }
    }

    @GetMapping("/{value}/{unit1}/{unit2}/")
    public String convert(@PathVariable double value, @PathVariable String unit1, @PathVariable String unit2) {
        // Validate the units
        if (!allowedUnits.contains(unit1) || !allowedUnits.contains(unit2)) {
            return "Invalid units. Allowed units are: " + allowedUnits.toString();
        }

        // Convert to meters first
        double valueInMeters = convertToMeters(value, unit1);

        // Convert from meters to the target unit
        double convertedValue = convertFromMeters(valueInMeters, unit2);

        return String.format("%.4f %s = %.4f %s", value, unit1, convertedValue, unit2);
    }
}
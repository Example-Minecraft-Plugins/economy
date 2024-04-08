package me.davipccunha.tests.economy.api.util;

public class EconomyFormatter {
    public static String exponentFormat(double value) {
        if (value < 1000)
            return String.format("%.2fe0", value);

        final int thousands = (int) (Math.log(value) / Math.log(1000));
        final int exponent = thousands * 3;

        value /= Math.pow(1000, thousands);

        return String.format("%.2fe%d", value, exponent);
    }

    public static String suffixFormat(double value) {
        final String[] suffixes = {
                "", "K", "M", "B", "T", "Q", "Qi", "S", "Se", "O", "N", "D"
        };

        String expFormat = exponentFormat(value);

        final float number = Float.parseFloat(expFormat.split("e")[0].replace(",", "."));
        final int exponent = Integer.parseInt(expFormat.split("e")[1]);

        int index = exponent / 3;

        if (index >= suffixes.length)
            return expFormat;

        return String.format("%.2f%s", number, suffixes[index]);
    }
}

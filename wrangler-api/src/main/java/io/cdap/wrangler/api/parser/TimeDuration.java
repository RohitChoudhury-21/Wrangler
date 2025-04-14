package io.cdap.wrangler.api.parser;

public class TimeDuration extends Token {
    private final long milliseconds;

    public TimeDuration(String value) throws IllegalArgumentException {
        super(value);
        this.milliseconds = parseDuration(value);
    }

    private long parseDuration(String input) {
        input = input.trim().toLowerCase();
        double number;
        String unit;

        if (input.matches("^[0-9]+(\\.[0-9]+)?(ms|s|m|h)$")) {
            number = Double.parseDouble(input.replaceAll("[a-z]+$", ""));
            unit = input.replaceAll("^[0-9.]+", "");

            switch (unit) {
                case "ms": return (long)(number);
                case "s":  return (long)(number * 1000);
                case "m":  return (long)(number * 60 * 1000);
                case "h":  return (long)(number * 60 * 60 * 1000);
                default: throw new IllegalArgumentException("Unknown time unit: " + unit);
            }
        } else {
            throw new IllegalArgumentException("Invalid time duration format: " + input);
        }
    }

    public long getMilliseconds() {
        return milliseconds;
    }
}

package io.cdap.wrangler.api.parser;

public class ByteSize extends Token {
    private final long bytes;

    public ByteSize(String value) throws IllegalArgumentException {
        super(value);
        this.bytes = parseBytes(value);
    }

    private long parseBytes(String input) {
        input = input.trim().toUpperCase();
        double number;
        String unit;

        if (input.matches("^[0-9]+(\\.[0-9]+)?[A-Z]{1,3}$")) {
            number = Double.parseDouble(input.replaceAll("[A-Z]+$", ""));
            unit = input.replaceAll("^[0-9.]+", "");

            switch (unit) {
                case "B": return (long)(number);
                case "KB": return (long)(number * 1024);
                case "MB": return (long)(number * 1024 * 1024);
                case "GB": return (long)(number * 1024 * 1024 * 1024);
                case "TB": return (long)(number * 1024L * 1024 * 1024 * 1024);
                default: throw new IllegalArgumentException("Unknown byte unit: " + unit);
            }
        } else {
            throw new IllegalArgumentException("Invalid byte size format: " + input);
        }
    }

    public long getBytes() {
        return bytes;
    }
}

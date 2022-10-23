package historyAndStatistic;

import java.text.DecimalFormat;

public class EncodeHistory {
    private final String input;
    private final String output;
    private final long timeOfEncoding;

    public EncodeHistory(String input, String output, long timeOfEncoding) {
        this.input = input;
        this.output = output;
        this.timeOfEncoding = timeOfEncoding;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        return "<"+input+"> --> <"+output+"> ("+decimalFormat.format(timeOfEncoding)+" nano-seconds)";
    }
}


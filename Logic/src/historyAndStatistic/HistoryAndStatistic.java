package historyAndStatistic;

import java.util.ArrayList;
import java.util.List;

public class HistoryAndStatistic {

    private List<EncodingsOfCodeConfiguration> machineHistoryAndStatisticList;

    public HistoryAndStatistic() {
        this.machineHistoryAndStatisticList = new ArrayList<>();
    }

    public List<EncodingsOfCodeConfiguration> getMachineHistoryAndStatisticList() {
        return machineHistoryAndStatisticList;
    }

    public void resetHistoryAndStatistic() {
        this.machineHistoryAndStatisticList =new ArrayList<>();
    }
    @Override
    public String toString() {
        StringBuilder toString= new StringBuilder();
        if(machineHistoryAndStatisticList.size()!=0) {
            toString.append("History And Statistic:" + "\n");
            for (EncodingsOfCodeConfiguration encodingsOfCodeConfiguration : machineHistoryAndStatisticList) {
                toString.append(encodingsOfCodeConfiguration.toString()).append("\n");
            }
        }
        else
            toString.append("There is no history and statistic to this machine");

        return String.valueOf(toString);
    }
}

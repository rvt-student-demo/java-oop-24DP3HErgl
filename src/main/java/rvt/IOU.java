package rvt;

import java.util.HashMap;
import java.util.Map;

public class IOU {
    private final Map<String, Double> debts;

    public IOU() {
        this.debts = new HashMap<>();
    }

    public void setSum(String toWhom, double amount) {
        this.debts.put(toWhom, amount);
    }

    public double howMuchDoIOweTo(String toWhom) {
        return this.debts.getOrDefault(toWhom, 0.0);
    }
    
    
    public static void main(String[] args) {
        IOU mattsIOU = new IOU();
        mattsIOU.setSum("Arthur", 51.5);
        mattsIOU.setSum("Michael", 30);

System.out.println(mattsIOU.howMuchDoIOweTo("Arthur"));
System.out.println(mattsIOU.howMuchDoIOweTo("Michael"));
    }
}


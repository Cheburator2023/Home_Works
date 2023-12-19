package ru.calculator;

import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

public class Summator {
    private Integer sum = 0;
    private Integer prevValue = 0;
    private Integer prevPrevValue = 0;
    private Integer sumLastThreeValues = 0;
    private Integer someValue = 0;
    private final TIntList listValues = new TIntArrayList(10_001);

    // !!! сигнатуру метода менять нельзя
    public void calc(Data data) {
        listValues.add(data.value());
        if (listValues.size() % 1_000 == 0) {
            listValues.clear();
        }
        sum += data.value();

        sumLastThreeValues = data.value() + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = data.value();

        for (var idx = 0; idx < 3; idx++) {
            someValue += (sumLastThreeValues * sumLastThreeValues / (data.value() + 1) - sum);
            someValue = Math.abs(someValue) + listValues.size();
        }
    }

    public Integer getSum() {
        return sum;
    }

    public Integer getPrevValue() {
        return prevValue;
    }

    public Integer getPrevPrevValue() {
        return prevPrevValue;
    }

    public Integer getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public Integer getSomeValue() {
        return someValue;
    }
}
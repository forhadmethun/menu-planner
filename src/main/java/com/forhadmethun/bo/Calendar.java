package com.forhadmethun.bo;

import java.time.LocalDate;
import java.util.Map;

public class Calendar {
    private Map<LocalDate, Long> dateToDayId;
    private Map<Long, Long> mealIdToDayId;

    public Map<LocalDate, Long> getDateToDayId() {
        return dateToDayId;
    }

    public void setDateToDayId(Map<LocalDate, Long> dateToDayId) {
        this.dateToDayId = dateToDayId;
    }

    public Map<Long, Long> getMealIdToDayId() {
        return mealIdToDayId;
    }

    public void setMealIdToDayId(Map<Long, Long> mealIdToDayId) {
        this.mealIdToDayId = mealIdToDayId;
    }
}

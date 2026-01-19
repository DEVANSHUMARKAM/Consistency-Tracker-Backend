package com.gate.tracker.consistency.dto;

import java.time.LocalDate;

public interface DailyCount {
    LocalDate get_id();   // MongoDB group key
    int getCount();
}

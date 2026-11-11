package ch.zli.m223.service;

import ch.zli.m223.model.Entry;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Map;

@ApplicationScoped
public class TimeSummaryService {

    public Map<LocalDate, Duration> CalculateSummaryPerDay(List<Entry> entries) {

        Map<LocalDate, Duration> summaries = new HashMap<>();

        for (Entry entry : entries) {

            Duration duration = Duration.between(
                entry.getCheckIn(),
                entry.getCheckOut()
            );

            LocalDate date = entry.getCheckIn().toLocalDate();
            Duration currentDuration = 
                summaries.getOrDefault(date, Duration.ZERO);
            summaries.put(date, currentDuration.plus(duration));
            
        }

        return summaries;
    }
}

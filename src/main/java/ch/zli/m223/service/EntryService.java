package ch.zli.m223.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.time.Duration;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;

import ch.zli.m223.service.TimeSummaryService;
import ch.zli.m223.model.ApplicationUser;
import ch.zli.m223.model.Entry;
import ch.zli.m223.repository.ApplicationUserRepository;
import ch.zli.m223.repository.EntryRepository;

@ApplicationScoped
public class EntryService {
    
    @Inject
    private EntryRepository entryRepository;
    TimeSummaryService timeSummaryService;


    @Inject
    ApplicationUserRepository userRepository;

    @Transactional
    public Entry createEntry(Entry entry, String username) {
        if (username != null) {
            ApplicationUser user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new NotAuthorizedException("Unknown user", "Bearer"));
            entry.setUser(user);
        }
        entryRepository.persist(entry);
        return entry;
    }

    public List<Entry> findAll() {
        return entryRepository.listAll();
    }
    @Transactional
    public void deleteEntry(long id) {
        if (!entryRepository.deleteById(id)) {
            throw new jakarta.ws.rs.NotFoundException();
        }
    }
    @Transactional
    public Entry updateEntry(long id, Entry entry) {
        Entry existingEntry = entryRepository.findById(id);

        if (existingEntry == null) {
            throw new NotFoundException();
        }

        existingEntry.setCheckIn(entry.getCheckIn());
        existingEntry.setCheckOut(entry.getCheckOut());

        return existingEntry;
    }

    public Map<LocalDate, Duration> GetTimeSummaries() {
        List<Entry> entries = entryRepository.listAll();

        Map<LocalDate, Duration> summaries =
                timeSummaryService.CalculateSummaryPerDay(entries);

        return summaries;
    }
}

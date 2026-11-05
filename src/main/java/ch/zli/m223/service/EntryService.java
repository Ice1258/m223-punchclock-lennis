package ch.zli.m223.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import ch.zli.m223.model.Entry;
import ch.zli.m223.repository.EntryRepository;

@ApplicationScoped
public class EntryService {
    @Inject
    private EntryRepository entryRepository;

    @Transactional
    public Entry createEntry(Entry entry) {
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
}

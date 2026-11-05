package ch.zli.m223.controller;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import ch.zli.m223.dto.EntryDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import ch.zli.m223.model.Entry;
import ch.zli.m223.service.EntryService;

@Path("/entries")
@Tag(name = "Entries", description = "Handling of entries")
public class EntryController {

    @Inject
    EntryService entryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EntryDto> index() {
        return entryService.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public EntryDto create(@NotNull @Valid EntryDto dto) {
        Entry entry = toEntity(dto);
        Entry created = entryService.createEntry(entry);
        return toDto(created);
    }


    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") long id) {
        entryService.deleteEntry(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public EntryDto update(
            @PathParam("id") long id,
            @NotNull @Valid EntryDto dto) {

        Entry entry = toEntity(dto);
        Entry updated = entryService.updateEntry(id, entry);
        return toDto(updated);
    }


    private Entry toEntity(EntryDto dto) {
        Entry entry = new Entry();
        entry.setCheckIn(dto.getCheckIn());
        entry.setCheckOut(dto.getCheckOut());
        return entry;
    }

    private EntryDto toDto(Entry entry) {
        EntryDto dto = new EntryDto();
        dto.setId(entry.getId());
        dto.setCheckIn(entry.getCheckIn());
        dto.setCheckOut(entry.getCheckOut());
        return dto;
    }

}

package ru.job4j.dreamjob.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.dreamjob.Application;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CandidateDBStoreTest {

    private static final String DELETE_CANDIDATES = "DELETE FROM candidate";
    private final CandidateDBStore store = new CandidateDBStore(new Application().loadPool());

    @BeforeEach
    void wipeTable() {
        try (Connection cn = new Application().loadPool().getConnection();
             PreparedStatement ps = cn.prepareStatement(DELETE_CANDIDATES)) {
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddCandidate() {
        Candidate candidate = new Candidate(0, "Nick", "Student", false,
                new City(1, null), new byte[0], LocalDateTime.now());
        store.add(candidate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getName()).isEqualTo(candidate.getName());
    }

    @Test
    public void whenUpdateCandidate() {
        Candidate candidate = new Candidate(0, "Nick", "Student", false,
                new City(1, null), new byte[0], LocalDateTime.now());
        store.add(candidate);
        Candidate candidateForUpdate = new Candidate(candidate.getId(), "Nick",
                "Java Developer. Experience < 1 year", false, new City(1, null), new byte[0],
                LocalDateTime.now());
        store.update(candidateForUpdate);
        Candidate candidateInDb = store.findById(candidate.getId());
        assertThat(candidateInDb.getDescription()).isEqualTo(candidateForUpdate.getDescription());
    }

    @Test
    public void whenFindAllCandidates() {
        Candidate firstCandidate = new Candidate(0, "Nick", "Student", false,
                new City(1, null), new byte[0], LocalDateTime.now());
        Candidate secondCandidate = new Candidate(0, "Max", "Java Developer. Experience < 1 year",
                false, new City(1, null), new byte[0], LocalDateTime.now());
        store.add(firstCandidate);
        store.add(secondCandidate);
        List<Candidate> candidates = store.findAll();
        assertThat(candidates.size()).isEqualTo(2);
    }
}

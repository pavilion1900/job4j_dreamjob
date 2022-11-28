package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CandidateStore {

    private static final CandidateStore INSTANCE = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private CandidateStore() {
        candidates.put(1, new Candidate(1, "Nick", "Student"));
        candidates.put(2, new Candidate(2, "Max", "Java Developer. Experience < 1 year"));
        candidates.put(3, new Candidate(3, "Pavel", "Java Developer. Experience > 1 year"));
    }

    public static CandidateStore instOf() {
        return INSTANCE;
    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public void add(Candidate candidate) {
        candidates.put(candidate.getId(), candidate);
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void update(Candidate candidate) {
        candidates.put(candidate.getId(), candidate);
    }
}

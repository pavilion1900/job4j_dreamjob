package ru.job4j.dreamjob.service;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.persistence.CandidateStore;

import java.util.Collection;

public class CandidateService {

    private static final CandidateService INSTANCE = new CandidateService();
    private final CandidateStore candidateStore = CandidateStore.instOf();

    private CandidateService() {
    }

    public static CandidateService instOf() {
        return INSTANCE;
    }

    public Collection<Candidate> findAll() {
        return candidateStore.findAll();
    }

    public void add(Candidate candidate) {
        candidateStore.add(candidate);
    }

    public Candidate findById(int id) {
        return candidateStore.findById(id);
    }

    public void update(Candidate candidate) {
        candidateStore.update(candidate);
    }
}

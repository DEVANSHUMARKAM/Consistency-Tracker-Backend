package com.gate.tracker.consistency.controller;


import com.gate.tracker.consistency.model.LearningSession;
import com.gate.tracker.consistency.repository.LearningSessionRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/sessions")
public class LearningSessionController {

    private final LearningSessionRepository repository;

    public LearningSessionController(LearningSessionRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public LearningSession saveSession(@RequestBody LearningSession session) {

        if (session.getConcepts() == null || session.getConcepts().isEmpty()) {
            throw new IllegalArgumentException("Concept list cannot be empty");
        }

        session.setCreatedAt(LocalDateTime.now());
        return repository.save(session);
    }

    @GetMapping("/heatmap")
    public List<Map<String, Object>> getHeatmapData(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return repository.countConceptsPerDay(start, end)
                .stream()
                .map(d -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("date", d.get_id());
                    m.put("count", d.getCount());
                    return m;
                })
                .toList();
    }

    @GetMapping("/day")
    public List<LearningSession> getSessionsForDay(
            @RequestParam LocalDate date
    ) {
        return repository.findByDate(date);
    }

    @PutMapping("/{id}")
    public LearningSession updateSession(
            @PathVariable String id,
            @RequestBody LearningSession updated
    ) {
        LearningSession existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        existing.setSubject(updated.getSubject());
        existing.setConcepts(updated.getConcepts());
        existing.setCreatedAt(LocalDateTime.now()); // update time

        return repository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable String id) {
        repository.deleteById(id);
    }


}

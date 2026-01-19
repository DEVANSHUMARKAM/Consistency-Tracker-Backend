package com.gate.tracker.consistency.repository;

import com.gate.tracker.consistency.dto.DailyCount;
import com.gate.tracker.consistency.model.LearningSession;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;

import java.util.List;
import java.time.LocalDate;

public interface LearningSessionRepository
        extends MongoRepository<LearningSession, String> {

    List<LearningSession> findByDate(LocalDate date);

    @Aggregation(pipeline = {
            "{ $match: { date: { $gte: ?0, $lte: ?1 } } }",
            "{ $unwind: '$concepts' }",
            "{ $addFields: { day: { $dateTrunc: { date: '$date', unit: 'day', timezone: 'Asia/Kolkata' } } } }",
            "{ $group: { _id: '$day', count: { $sum: 1 } } }",
            "{ $sort: { _id: 1 } }"
    })
    List<DailyCount> countConceptsPerDay(LocalDate start, LocalDate end);

}

package com.gate.tracker.consistency.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "learning_sessions")
public class LearningSession {

    @Id
    private String id;

    private LocalDate date;

    private String subject;

    private List<String> concepts;

    private LocalDateTime createdAt;
}

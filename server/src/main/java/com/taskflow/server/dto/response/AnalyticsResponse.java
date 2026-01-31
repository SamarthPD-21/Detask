package com.taskflow.server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsResponse {

    private long totalBoards;
    private long totalCards;
    private long completedCards;
    private long overdueTasks;
    private long tasksCompletedThisWeek;
    private long tasksCreatedThisWeek;
    private double completionRate;
    private Map<String, Long> cardsByPriority;
    private Map<String, Long> cardsByLabel;
    private Map<String, Long> tasksCompletedByDay;
    private Map<String, Long> tasksByMember;
}

package com.taskflow.server.dto.response;

import java.util.Map;

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

    public AnalyticsResponse() {}

    public long getTotalBoards() { return totalBoards; }
    public void setTotalBoards(long totalBoards) { this.totalBoards = totalBoards; }
    public long getTotalCards() { return totalCards; }
    public void setTotalCards(long totalCards) { this.totalCards = totalCards; }
    public long getCompletedCards() { return completedCards; }
    public void setCompletedCards(long completedCards) { this.completedCards = completedCards; }
    public long getOverdueTasks() { return overdueTasks; }
    public void setOverdueTasks(long overdueTasks) { this.overdueTasks = overdueTasks; }
    public long getTasksCompletedThisWeek() { return tasksCompletedThisWeek; }
    public void setTasksCompletedThisWeek(long tasksCompletedThisWeek) { this.tasksCompletedThisWeek = tasksCompletedThisWeek; }
    public long getTasksCreatedThisWeek() { return tasksCreatedThisWeek; }
    public void setTasksCreatedThisWeek(long tasksCreatedThisWeek) { this.tasksCreatedThisWeek = tasksCreatedThisWeek; }
    public double getCompletionRate() { return completionRate; }
    public void setCompletionRate(double completionRate) { this.completionRate = completionRate; }
    public Map<String, Long> getCardsByPriority() { return cardsByPriority; }
    public void setCardsByPriority(Map<String, Long> cardsByPriority) { this.cardsByPriority = cardsByPriority; }
    public Map<String, Long> getCardsByLabel() { return cardsByLabel; }
    public void setCardsByLabel(Map<String, Long> cardsByLabel) { this.cardsByLabel = cardsByLabel; }
    public Map<String, Long> getTasksCompletedByDay() { return tasksCompletedByDay; }
    public void setTasksCompletedByDay(Map<String, Long> tasksCompletedByDay) { this.tasksCompletedByDay = tasksCompletedByDay; }
    public Map<String, Long> getTasksByMember() { return tasksByMember; }
    public void setTasksByMember(Map<String, Long> tasksByMember) { this.tasksByMember = tasksByMember; }

    public static AnalyticsResponseBuilder builder() { return new AnalyticsResponseBuilder(); }

    public static class AnalyticsResponseBuilder {
        private final AnalyticsResponse r = new AnalyticsResponse();
        public AnalyticsResponseBuilder totalBoards(long totalBoards) { r.totalBoards = totalBoards; return this; }
        public AnalyticsResponseBuilder totalCards(long totalCards) { r.totalCards = totalCards; return this; }
        public AnalyticsResponseBuilder completedCards(long completedCards) { r.completedCards = completedCards; return this; }
        public AnalyticsResponseBuilder overdueTasks(long overdueTasks) { r.overdueTasks = overdueTasks; return this; }
        public AnalyticsResponseBuilder tasksCompletedThisWeek(long tasksCompletedThisWeek) { r.tasksCompletedThisWeek = tasksCompletedThisWeek; return this; }
        public AnalyticsResponseBuilder tasksCreatedThisWeek(long tasksCreatedThisWeek) { r.tasksCreatedThisWeek = tasksCreatedThisWeek; return this; }
        public AnalyticsResponseBuilder completionRate(double completionRate) { r.completionRate = completionRate; return this; }
        public AnalyticsResponseBuilder cardsByPriority(Map<String, Long> cardsByPriority) { r.cardsByPriority = cardsByPriority; return this; }
        public AnalyticsResponseBuilder cardsByLabel(Map<String, Long> cardsByLabel) { r.cardsByLabel = cardsByLabel; return this; }
        public AnalyticsResponseBuilder tasksCompletedByDay(Map<String, Long> tasksCompletedByDay) { r.tasksCompletedByDay = tasksCompletedByDay; return this; }
        public AnalyticsResponseBuilder tasksByMember(Map<String, Long> tasksByMember) { r.tasksByMember = tasksByMember; return this; }
        public AnalyticsResponse build() { return r; }
    }
}

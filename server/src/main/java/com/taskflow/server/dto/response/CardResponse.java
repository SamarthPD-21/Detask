package com.taskflow.server.dto.response;

import com.taskflow.server.model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class CardResponse {

    private String id;
    private String title;
    private String description;
    private String boardId;
    private String listId;
    private int position;
    private Priority priority;
    private LocalDateTime dueDate;
    private LocalDateTime startDate;
    private boolean completed;
    private LocalDateTime completedAt;
    private Set<String> assigneeIds;
    private List<UserResponse> assignees;
    private Set<String> labelIds;
    private List<Label> labels;
    private List<Attachment> attachments;
    private List<Checklist> checklists;
    private List<Comment> comments;
    private int commentsCount;
    private int attachmentsCount;
    private int checklistProgress;
    private String coverColor;
    private String coverImage;
    private int watchersCount;
    private boolean isWatching;
    private boolean archived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private UserResponse creator;

    public CardResponse() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getBoardId() { return boardId; }
    public void setBoardId(String boardId) { this.boardId = boardId; }
    public String getListId() { return listId; }
    public void setListId(String listId) { this.listId = listId; }
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    public Priority getPriority() { return priority; }
    public void setPriority(Priority priority) { this.priority = priority; }
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }
    public Set<String> getAssigneeIds() { return assigneeIds; }
    public void setAssigneeIds(Set<String> assigneeIds) { this.assigneeIds = assigneeIds; }
    public List<UserResponse> getAssignees() { return assignees; }
    public void setAssignees(List<UserResponse> assignees) { this.assignees = assignees; }
    public Set<String> getLabelIds() { return labelIds; }
    public void setLabelIds(Set<String> labelIds) { this.labelIds = labelIds; }
    public List<Label> getLabels() { return labels; }
    public void setLabels(List<Label> labels) { this.labels = labels; }
    public List<Attachment> getAttachments() { return attachments; }
    public void setAttachments(List<Attachment> attachments) { this.attachments = attachments; }
    public List<Checklist> getChecklists() { return checklists; }
    public void setChecklists(List<Checklist> checklists) { this.checklists = checklists; }
    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
    public int getCommentsCount() { return commentsCount; }
    public void setCommentsCount(int commentsCount) { this.commentsCount = commentsCount; }
    public int getAttachmentsCount() { return attachmentsCount; }
    public void setAttachmentsCount(int attachmentsCount) { this.attachmentsCount = attachmentsCount; }
    public int getChecklistProgress() { return checklistProgress; }
    public void setChecklistProgress(int checklistProgress) { this.checklistProgress = checklistProgress; }
    public String getCoverColor() { return coverColor; }
    public void setCoverColor(String coverColor) { this.coverColor = coverColor; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public int getWatchersCount() { return watchersCount; }
    public void setWatchersCount(int watchersCount) { this.watchersCount = watchersCount; }
    public boolean isWatching() { return isWatching; }
    public void setWatching(boolean isWatching) { this.isWatching = isWatching; }
    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public UserResponse getCreator() { return creator; }
    public void setCreator(UserResponse creator) { this.creator = creator; }

    public static CardResponseBuilder builder() { return new CardResponseBuilder(); }

    public static class CardResponseBuilder {
        private final CardResponse r = new CardResponse();
        public CardResponseBuilder id(String id) { r.id = id; return this; }
        public CardResponseBuilder title(String title) { r.title = title; return this; }
        public CardResponseBuilder description(String description) { r.description = description; return this; }
        public CardResponseBuilder boardId(String boardId) { r.boardId = boardId; return this; }
        public CardResponseBuilder listId(String listId) { r.listId = listId; return this; }
        public CardResponseBuilder position(int position) { r.position = position; return this; }
        public CardResponseBuilder priority(Priority priority) { r.priority = priority; return this; }
        public CardResponseBuilder dueDate(LocalDateTime dueDate) { r.dueDate = dueDate; return this; }
        public CardResponseBuilder startDate(LocalDateTime startDate) { r.startDate = startDate; return this; }
        public CardResponseBuilder completed(boolean completed) { r.completed = completed; return this; }
        public CardResponseBuilder completedAt(LocalDateTime completedAt) { r.completedAt = completedAt; return this; }
        public CardResponseBuilder assigneeIds(Set<String> assigneeIds) { r.assigneeIds = assigneeIds; return this; }
        public CardResponseBuilder assignees(List<UserResponse> assignees) { r.assignees = assignees; return this; }
        public CardResponseBuilder labelIds(Set<String> labelIds) { r.labelIds = labelIds; return this; }
        public CardResponseBuilder labels(List<Label> labels) { r.labels = labels; return this; }
        public CardResponseBuilder attachments(List<Attachment> attachments) { r.attachments = attachments; return this; }
        public CardResponseBuilder checklists(List<Checklist> checklists) { r.checklists = checklists; return this; }
        public CardResponseBuilder comments(List<Comment> comments) { r.comments = comments; return this; }
        public CardResponseBuilder commentsCount(int commentsCount) { r.commentsCount = commentsCount; return this; }
        public CardResponseBuilder attachmentsCount(int attachmentsCount) { r.attachmentsCount = attachmentsCount; return this; }
        public CardResponseBuilder checklistProgress(int checklistProgress) { r.checklistProgress = checklistProgress; return this; }
        public CardResponseBuilder coverColor(String coverColor) { r.coverColor = coverColor; return this; }
        public CardResponseBuilder coverImage(String coverImage) { r.coverImage = coverImage; return this; }
        public CardResponseBuilder watchersCount(int watchersCount) { r.watchersCount = watchersCount; return this; }
        public CardResponseBuilder isWatching(boolean isWatching) { r.isWatching = isWatching; return this; }
        public CardResponseBuilder archived(boolean archived) { r.archived = archived; return this; }
        public CardResponseBuilder createdAt(LocalDateTime createdAt) { r.createdAt = createdAt; return this; }
        public CardResponseBuilder updatedAt(LocalDateTime updatedAt) { r.updatedAt = updatedAt; return this; }
        public CardResponseBuilder createdBy(String createdBy) { r.createdBy = createdBy; return this; }
        public CardResponseBuilder creator(UserResponse creator) { r.creator = creator; return this; }
        public CardResponse build() { return r; }
    }
}

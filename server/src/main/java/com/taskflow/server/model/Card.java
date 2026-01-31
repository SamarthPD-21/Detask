package com.taskflow.server.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Document(collection = "cards")
@CompoundIndex(name = "board_list_idx", def = "{'boardId': 1, 'listId': 1}")
public class Card {

    @Id
    private String id;

    private String title;

    private String description;

    @Indexed
    private String boardId;

    @Indexed
    private String listId;

    private int position;

    private Priority priority = Priority.MEDIUM;

    private LocalDateTime dueDate;

    private LocalDateTime startDate;

    private boolean completed = false;

    private LocalDateTime completedAt;

    private Set<String> assigneeIds = new HashSet<>();

    private Set<String> labelIds = new HashSet<>();

    private List<Attachment> attachments = new ArrayList<>();

    private List<Checklist> checklists = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    private String coverColor;

    private String coverImage;

    private int watchersCount = 0;

    private Set<String> watcherIds = new HashSet<>();

    private boolean archived = false;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String createdBy;

    public Card() {
    }

    public Card(String id, String title, String description, String boardId, String listId, int position,
                Priority priority, LocalDateTime dueDate, LocalDateTime startDate, boolean completed,
                LocalDateTime completedAt, Set<String> assigneeIds, Set<String> labelIds,
                List<Attachment> attachments, List<Checklist> checklists, List<Comment> comments,
                String coverColor, String coverImage, int watchersCount, Set<String> watcherIds,
                boolean archived, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.boardId = boardId;
        this.listId = listId;
        this.position = position;
        this.priority = priority != null ? priority : Priority.MEDIUM;
        this.dueDate = dueDate;
        this.startDate = startDate;
        this.completed = completed;
        this.completedAt = completedAt;
        this.assigneeIds = assigneeIds != null ? assigneeIds : new HashSet<>();
        this.labelIds = labelIds != null ? labelIds : new HashSet<>();
        this.attachments = attachments != null ? attachments : new ArrayList<>();
        this.checklists = checklists != null ? checklists : new ArrayList<>();
        this.comments = comments != null ? comments : new ArrayList<>();
        this.coverColor = coverColor;
        this.coverImage = coverImage;
        this.watchersCount = watchersCount;
        this.watcherIds = watcherIds != null ? watcherIds : new HashSet<>();
        this.archived = archived;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Set<String> getAssigneeIds() {
        return assigneeIds;
    }

    public void setAssigneeIds(Set<String> assigneeIds) {
        this.assigneeIds = assigneeIds;
    }

    public Set<String> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(Set<String> labelIds) {
        this.labelIds = labelIds;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Checklist> getChecklists() {
        return checklists;
    }

    public void setChecklists(List<Checklist> checklists) {
        this.checklists = checklists;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getCoverColor() {
        return coverColor;
    }

    public void setCoverColor(String coverColor) {
        this.coverColor = coverColor;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public int getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(int watchersCount) {
        this.watchersCount = watchersCount;
    }

    public Set<String> getWatcherIds() {
        return watcherIds;
    }

    public void setWatcherIds(Set<String> watcherIds) {
        this.watcherIds = watcherIds;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", boardId='" + boardId + '\'' +
                ", listId='" + listId + '\'' +
                ", position=" + position +
                ", priority=" + priority +
                ", completed=" + completed +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String title;
        private String description;
        private String boardId;
        private String listId;
        private int position;
        private Priority priority = Priority.MEDIUM;
        private LocalDateTime dueDate;
        private LocalDateTime startDate;
        private boolean completed = false;
        private LocalDateTime completedAt;
        private Set<String> assigneeIds = new HashSet<>();
        private Set<String> labelIds = new HashSet<>();
        private List<Attachment> attachments = new ArrayList<>();
        private List<Checklist> checklists = new ArrayList<>();
        private List<Comment> comments = new ArrayList<>();
        private String coverColor;
        private String coverImage;
        private int watchersCount = 0;
        private Set<String> watcherIds = new HashSet<>();
        private boolean archived = false;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder boardId(String boardId) {
            this.boardId = boardId;
            return this;
        }

        public Builder listId(String listId) {
            this.listId = listId;
            return this;
        }

        public Builder position(int position) {
            this.position = position;
            return this;
        }

        public Builder priority(Priority priority) {
            this.priority = priority != null ? priority : Priority.MEDIUM;
            return this;
        }

        public Builder dueDate(LocalDateTime dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public Builder completedAt(LocalDateTime completedAt) {
            this.completedAt = completedAt;
            return this;
        }

        public Builder assigneeIds(Set<String> assigneeIds) {
            this.assigneeIds = assigneeIds != null ? assigneeIds : new HashSet<>();
            return this;
        }

        public Builder labelIds(Set<String> labelIds) {
            this.labelIds = labelIds != null ? labelIds : new HashSet<>();
            return this;
        }

        public Builder attachments(List<Attachment> attachments) {
            this.attachments = attachments != null ? attachments : new ArrayList<>();
            return this;
        }

        public Builder checklists(List<Checklist> checklists) {
            this.checklists = checklists != null ? checklists : new ArrayList<>();
            return this;
        }

        public Builder comments(List<Comment> comments) {
            this.comments = comments != null ? comments : new ArrayList<>();
            return this;
        }

        public Builder coverColor(String coverColor) {
            this.coverColor = coverColor;
            return this;
        }

        public Builder coverImage(String coverImage) {
            this.coverImage = coverImage;
            return this;
        }

        public Builder watchersCount(int watchersCount) {
            this.watchersCount = watchersCount;
            return this;
        }

        public Builder watcherIds(Set<String> watcherIds) {
            this.watcherIds = watcherIds != null ? watcherIds : new HashSet<>();
            return this;
        }

        public Builder archived(boolean archived) {
            this.archived = archived;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Card build() {
            return new Card(id, title, description, boardId, listId, position, priority,
                    dueDate, startDate, completed, completedAt, assigneeIds, labelIds,
                    attachments, checklists, comments, coverColor, coverImage, watchersCount,
                    watcherIds, archived, createdAt, updatedAt, createdBy);
        }
    }
}

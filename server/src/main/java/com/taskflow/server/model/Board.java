package com.taskflow.server.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Document(collection = "boards")
public class Board {

    @Id
    private String id;

    private String name;

    private String description;

    private String backgroundColor;

    private String backgroundImage;

    @Indexed
    private String ownerId;

    private Set<String> memberIds = new HashSet<>();

    private List<String> listOrder = new ArrayList<>();

    private boolean archived = false;

    private Visibility visibility = Visibility.PRIVATE;

    private Set<Label> labels = new HashSet<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Board() {
    }

    public Board(String id, String name, String description, String backgroundColor, String backgroundImage,
                 String ownerId, Set<String> memberIds, List<String> listOrder, boolean archived,
                 Visibility visibility, Set<Label> labels, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.backgroundImage = backgroundImage;
        this.ownerId = ownerId;
        this.memberIds = memberIds != null ? memberIds : new HashSet<>();
        this.listOrder = listOrder != null ? listOrder : new ArrayList<>();
        this.archived = archived;
        this.visibility = visibility != null ? visibility : Visibility.PRIVATE;
        this.labels = labels != null ? labels : new HashSet<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Set<String> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(Set<String> memberIds) {
        this.memberIds = memberIds;
    }

    public List<String> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<String> listOrder) {
        this.listOrder = listOrder;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(id, board.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", archived=" + archived +
                ", visibility=" + visibility +
                ", createdAt=" + createdAt +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String name;
        private String description;
        private String backgroundColor;
        private String backgroundImage;
        private String ownerId;
        private Set<String> memberIds = new HashSet<>();
        private List<String> listOrder = new ArrayList<>();
        private boolean archived = false;
        private Visibility visibility = Visibility.PRIVATE;
        private Set<Label> labels = new HashSet<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder backgroundColor(String backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder backgroundImage(String backgroundImage) {
            this.backgroundImage = backgroundImage;
            return this;
        }

        public Builder ownerId(String ownerId) {
            this.ownerId = ownerId;
            return this;
        }

        public Builder memberIds(Set<String> memberIds) {
            this.memberIds = memberIds != null ? memberIds : new HashSet<>();
            return this;
        }

        public Builder listOrder(List<String> listOrder) {
            this.listOrder = listOrder != null ? listOrder : new ArrayList<>();
            return this;
        }

        public Builder archived(boolean archived) {
            this.archived = archived;
            return this;
        }

        public Builder visibility(Visibility visibility) {
            this.visibility = visibility != null ? visibility : Visibility.PRIVATE;
            return this;
        }

        public Builder labels(Set<Label> labels) {
            this.labels = labels != null ? labels : new HashSet<>();
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

        public Board build() {
            return new Board(id, name, description, backgroundColor, backgroundImage, ownerId,
                    memberIds, listOrder, archived, visibility, labels, createdAt, updatedAt);
        }
    }
}

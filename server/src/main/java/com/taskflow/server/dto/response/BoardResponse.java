package com.taskflow.server.dto.response;

import com.taskflow.server.model.Label;
import com.taskflow.server.model.Visibility;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class BoardResponse {

    private String id;
    private String name;
    private String description;
    private String backgroundColor;
    private String backgroundImage;
    private String ownerId;
    private UserResponse owner;
    private Set<String> memberIds;
    private List<UserResponse> members;
    private List<String> listOrder;
    private boolean archived;
    private Visibility visibility;
    private Set<Label> labels;
    private int totalCards;
    private int completedCards;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BoardResponse() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }
    public String getBackgroundImage() { return backgroundImage; }
    public void setBackgroundImage(String backgroundImage) { this.backgroundImage = backgroundImage; }
    public String getOwnerId() { return ownerId; }
    public void setOwnerId(String ownerId) { this.ownerId = ownerId; }
    public UserResponse getOwner() { return owner; }
    public void setOwner(UserResponse owner) { this.owner = owner; }
    public Set<String> getMemberIds() { return memberIds; }
    public void setMemberIds(Set<String> memberIds) { this.memberIds = memberIds; }
    public List<UserResponse> getMembers() { return members; }
    public void setMembers(List<UserResponse> members) { this.members = members; }
    public List<String> getListOrder() { return listOrder; }
    public void setListOrder(List<String> listOrder) { this.listOrder = listOrder; }
    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }
    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility visibility) { this.visibility = visibility; }
    public Set<Label> getLabels() { return labels; }
    public void setLabels(Set<Label> labels) { this.labels = labels; }
    public int getTotalCards() { return totalCards; }
    public void setTotalCards(int totalCards) { this.totalCards = totalCards; }
    public int getCompletedCards() { return completedCards; }
    public void setCompletedCards(int completedCards) { this.completedCards = completedCards; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static BoardResponseBuilder builder() { return new BoardResponseBuilder(); }

    public static class BoardResponseBuilder {
        private final BoardResponse r = new BoardResponse();
        public BoardResponseBuilder id(String id) { r.id = id; return this; }
        public BoardResponseBuilder name(String name) { r.name = name; return this; }
        public BoardResponseBuilder description(String description) { r.description = description; return this; }
        public BoardResponseBuilder backgroundColor(String backgroundColor) { r.backgroundColor = backgroundColor; return this; }
        public BoardResponseBuilder backgroundImage(String backgroundImage) { r.backgroundImage = backgroundImage; return this; }
        public BoardResponseBuilder ownerId(String ownerId) { r.ownerId = ownerId; return this; }
        public BoardResponseBuilder owner(UserResponse owner) { r.owner = owner; return this; }
        public BoardResponseBuilder memberIds(Set<String> memberIds) { r.memberIds = memberIds; return this; }
        public BoardResponseBuilder members(List<UserResponse> members) { r.members = members; return this; }
        public BoardResponseBuilder listOrder(List<String> listOrder) { r.listOrder = listOrder; return this; }
        public BoardResponseBuilder archived(boolean archived) { r.archived = archived; return this; }
        public BoardResponseBuilder visibility(Visibility visibility) { r.visibility = visibility; return this; }
        public BoardResponseBuilder labels(Set<Label> labels) { r.labels = labels; return this; }
        public BoardResponseBuilder totalCards(int totalCards) { r.totalCards = totalCards; return this; }
        public BoardResponseBuilder completedCards(int completedCards) { r.completedCards = completedCards; return this; }
        public BoardResponseBuilder createdAt(LocalDateTime createdAt) { r.createdAt = createdAt; return this; }
        public BoardResponseBuilder updatedAt(LocalDateTime updatedAt) { r.updatedAt = updatedAt; return this; }
        public BoardResponse build() { return r; }
    }
}

package com.taskflow.server.dto.response;

import com.taskflow.server.model.Label;
import com.taskflow.server.model.Visibility;

import java.util.List;
import java.util.Set;

public class BoardDetailResponse {

    private String id;
    private String name;
    private String description;
    private String backgroundColor;
    private String backgroundImage;
    private String ownerId;
    private UserResponse owner;
    private List<UserResponse> members;
    private Visibility visibility;
    private Set<Label> labels;
    private List<ListResponse> lists;
    private int totalCards;
    private int completedCards;

    public BoardDetailResponse() {}

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
    public List<UserResponse> getMembers() { return members; }
    public void setMembers(List<UserResponse> members) { this.members = members; }
    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility visibility) { this.visibility = visibility; }
    public Set<Label> getLabels() { return labels; }
    public void setLabels(Set<Label> labels) { this.labels = labels; }
    public List<ListResponse> getLists() { return lists; }
    public void setLists(List<ListResponse> lists) { this.lists = lists; }
    public int getTotalCards() { return totalCards; }
    public void setTotalCards(int totalCards) { this.totalCards = totalCards; }
    public int getCompletedCards() { return completedCards; }
    public void setCompletedCards(int completedCards) { this.completedCards = completedCards; }

    public static BoardDetailResponseBuilder builder() { return new BoardDetailResponseBuilder(); }

    public static class BoardDetailResponseBuilder {
        private final BoardDetailResponse r = new BoardDetailResponse();
        public BoardDetailResponseBuilder id(String id) { r.id = id; return this; }
        public BoardDetailResponseBuilder name(String name) { r.name = name; return this; }
        public BoardDetailResponseBuilder description(String description) { r.description = description; return this; }
        public BoardDetailResponseBuilder backgroundColor(String backgroundColor) { r.backgroundColor = backgroundColor; return this; }
        public BoardDetailResponseBuilder backgroundImage(String backgroundImage) { r.backgroundImage = backgroundImage; return this; }
        public BoardDetailResponseBuilder ownerId(String ownerId) { r.ownerId = ownerId; return this; }
        public BoardDetailResponseBuilder owner(UserResponse owner) { r.owner = owner; return this; }
        public BoardDetailResponseBuilder members(List<UserResponse> members) { r.members = members; return this; }
        public BoardDetailResponseBuilder visibility(Visibility visibility) { r.visibility = visibility; return this; }
        public BoardDetailResponseBuilder labels(Set<Label> labels) { r.labels = labels; return this; }
        public BoardDetailResponseBuilder lists(List<ListResponse> lists) { r.lists = lists; return this; }
        public BoardDetailResponseBuilder totalCards(int totalCards) { r.totalCards = totalCards; return this; }
        public BoardDetailResponseBuilder completedCards(int completedCards) { r.completedCards = completedCards; return this; }
        public BoardDetailResponse build() { return r; }
    }
}

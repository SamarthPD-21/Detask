package com.taskflow.server.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class ListResponse {

    private String id;
    private String name;
    private String boardId;
    private int position;
    private List<String> cardOrder;
    private List<CardResponse> cards;
    private boolean archived;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ListResponse() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBoardId() { return boardId; }
    public void setBoardId(String boardId) { this.boardId = boardId; }
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }
    public List<String> getCardOrder() { return cardOrder; }
    public void setCardOrder(List<String> cardOrder) { this.cardOrder = cardOrder; }
    public List<CardResponse> getCards() { return cards; }
    public void setCards(List<CardResponse> cards) { this.cards = cards; }
    public boolean isArchived() { return archived; }
    public void setArchived(boolean archived) { this.archived = archived; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static ListResponseBuilder builder() { return new ListResponseBuilder(); }

    public static class ListResponseBuilder {
        private final ListResponse r = new ListResponse();
        public ListResponseBuilder id(String id) { r.id = id; return this; }
        public ListResponseBuilder name(String name) { r.name = name; return this; }
        public ListResponseBuilder boardId(String boardId) { r.boardId = boardId; return this; }
        public ListResponseBuilder position(int position) { r.position = position; return this; }
        public ListResponseBuilder cardOrder(List<String> cardOrder) { r.cardOrder = cardOrder; return this; }
        public ListResponseBuilder cards(List<CardResponse> cards) { r.cards = cards; return this; }
        public ListResponseBuilder archived(boolean archived) { r.archived = archived; return this; }
        public ListResponseBuilder createdAt(LocalDateTime createdAt) { r.createdAt = createdAt; return this; }
        public ListResponseBuilder updatedAt(LocalDateTime updatedAt) { r.updatedAt = updatedAt; return this; }
        public ListResponse build() { return r; }
    }
}

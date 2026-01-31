package com.taskflow.server.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class CardMoveRequest {

    @NotBlank(message = "Target list ID is required")
    private String targetListId;

    private int position;

    public CardMoveRequest() {
    }

    public CardMoveRequest(String targetListId, int position) {
        this.targetListId = targetListId;
        this.position = position;
    }

    public String getTargetListId() {
        return targetListId;
    }

    public void setTargetListId(String targetListId) {
        this.targetListId = targetListId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardMoveRequest that = (CardMoveRequest) o;
        return position == that.position && Objects.equals(targetListId, that.targetListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetListId, position);
    }

    @Override
    public String toString() {
        return "CardMoveRequest{" +
                "targetListId='" + targetListId + '\'' +
                ", position=" + position +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String targetListId;
        private int position;

        public Builder targetListId(String targetListId) {
            this.targetListId = targetListId;
            return this;
        }

        public Builder position(int position) {
            this.position = position;
            return this;
        }

        public CardMoveRequest build() {
            return new CardMoveRequest(targetListId, position);
        }
    }
}

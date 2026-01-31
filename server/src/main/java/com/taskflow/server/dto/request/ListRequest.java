package com.taskflow.server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class ListRequest {

    @NotBlank(message = "List name is required")
    @Size(min = 1, max = 100, message = "List name must be between 1 and 100 characters")
    private String name;

    private Integer position;

    public ListRequest() {
    }

    public ListRequest(String name, Integer position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListRequest that = (ListRequest) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ListRequest{" +
                "name='" + name + '\'' +
                ", position=" + position +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private Integer position;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder position(Integer position) {
            this.position = position;
            return this;
        }

        public ListRequest build() {
            return new ListRequest(name, position);
        }
    }
}

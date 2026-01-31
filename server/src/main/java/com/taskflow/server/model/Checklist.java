package com.taskflow.server.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Checklist {
    private String id;
    private String title;
    private List<ChecklistItem> items = new ArrayList<>();

    public Checklist() {
    }

    public Checklist(String id, String title, List<ChecklistItem> items) {
        this.id = id;
        this.title = title;
        this.items = items != null ? items : new ArrayList<>();
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

    public List<ChecklistItem> getItems() {
        return items;
    }

    public void setItems(List<ChecklistItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Checklist checklist = (Checklist) o;
        return Objects.equals(id, checklist.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Checklist{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", items=" + items +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String title;
        private List<ChecklistItem> items = new ArrayList<>();

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder items(List<ChecklistItem> items) {
            this.items = items != null ? items : new ArrayList<>();
            return this;
        }

        public Checklist build() {
            return new Checklist(id, title, items);
        }
    }
}

package com.taskflow.server.model;

public enum ActivityType {
    // Board activities
    BOARD_CREATED,
    BOARD_UPDATED,
    BOARD_ARCHIVED,
    BOARD_MEMBER_ADDED,
    BOARD_MEMBER_REMOVED,
    
    // List activities
    LIST_CREATED,
    LIST_UPDATED,
    LIST_ARCHIVED,
    LIST_MOVED,
    
    // Card activities
    CARD_CREATED,
    CARD_UPDATED,
    CARD_MOVED,
    CARD_ARCHIVED,
    CARD_ASSIGNED,
    CARD_UNASSIGNED,
    CARD_DUE_DATE_SET,
    CARD_DUE_DATE_REMOVED,
    CARD_COMPLETED,
    CARD_REOPENED,
    
    // Comment activities
    COMMENT_ADDED,
    COMMENT_UPDATED,
    COMMENT_DELETED,
    
    // Checklist activities
    CHECKLIST_ADDED,
    CHECKLIST_ITEM_COMPLETED,
    CHECKLIST_ITEM_UNCOMPLETED,
    
    // Attachment activities
    ATTACHMENT_ADDED,
    ATTACHMENT_REMOVED,
    
    // Label activities
    LABEL_ADDED,
    LABEL_REMOVED
}

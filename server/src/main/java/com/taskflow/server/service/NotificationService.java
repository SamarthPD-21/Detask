package com.taskflow.server.service;

import com.taskflow.server.dto.response.NotificationResponse;
import com.taskflow.server.dto.response.PageResponse;
import com.taskflow.server.model.Notification;
import com.taskflow.server.model.NotificationType;
import com.taskflow.server.repository.NotificationRepository;
import com.taskflow.server.util.MapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MapperUtils mapperUtils;

    public void createNotification(String userId, String title, String message,
                                   NotificationType type, String boardId, String cardId) {
        Notification notification = Notification.builder()
                .userId(userId)
                .title(title)
                .message(message)
                .type(type)
                .boardId(boardId)
                .cardId(cardId)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
        log.debug("Notification created for user: {} - {}", userId, title);
    }

    public PageResponse<NotificationResponse> getUserNotifications(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notificationPage = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);

        List<NotificationResponse> notifications = notificationPage.getContent().stream()
                .map(mapperUtils::toNotificationResponse)
                .collect(Collectors.toList());

        return PageResponse.<NotificationResponse>builder()
                .content(notifications)
                .page(page)
                .size(size)
                .totalElements(notificationPage.getTotalElements())
                .totalPages(notificationPage.getTotalPages())
                .first(notificationPage.isFirst())
                .last(notificationPage.isLast())
                .build();
    }

    public List<NotificationResponse> getUnreadNotifications(String userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId);
        return notifications.stream()
                .map(mapperUtils::toNotificationResponse)
                .collect(Collectors.toList());
    }

    public long getUnreadCount(String userId) {
        return notificationRepository.countByUserIdAndReadFalse(userId);
    }

    @Transactional
    public NotificationResponse markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            notification.setReadAt(LocalDateTime.now());
            notification = notificationRepository.save(notification);
        }
        return mapperUtils.toNotificationResponse(notification);
    }

    @Transactional
    public void markAllAsRead(String userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndReadFalseOrderByCreatedAtDesc(userId);
        for (Notification notification : notifications) {
            notification.setRead(true);
            notification.setReadAt(LocalDateTime.now());
        }
        notificationRepository.saveAll(notifications);
    }

    @Transactional
    public void deleteNotification(String notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}

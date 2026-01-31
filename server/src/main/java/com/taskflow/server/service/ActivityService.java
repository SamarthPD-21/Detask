package com.taskflow.server.service;

import com.taskflow.server.dto.response.ActivityResponse;
import com.taskflow.server.dto.response.PageResponse;
import com.taskflow.server.model.Activity;
import com.taskflow.server.model.ActivityType;
import com.taskflow.server.model.User;
import com.taskflow.server.repository.ActivityRepository;
import com.taskflow.server.repository.UserRepository;
import com.taskflow.server.util.MapperUtils;
import com.taskflow.server.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;
    private final MapperUtils mapperUtils;

    public void logActivity(String boardId, String cardId, String listId, ActivityType type, String description) {
        String userId = SecurityUtils.getCurrentUserId();
        if (userId == null) return;

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return;

        Activity activity = Activity.builder()
                .boardId(boardId)
                .cardId(cardId)
                .listId(listId)
                .userId(userId)
                .userName(user.getFullName())
                .userAvatar(user.getAvatarUrl())
                .type(type)
                .description(description)
                .createdAt(LocalDateTime.now())
                .build();

        activityRepository.save(activity);
        log.debug("Activity logged: {} - {}", type, description);
    }

    public PageResponse<ActivityResponse> getBoardActivities(String boardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Activity> activityPage = activityRepository.findByBoardIdOrderByCreatedAtDesc(boardId, pageable);

        List<ActivityResponse> activities = activityPage.getContent().stream()
                .map(mapperUtils::toActivityResponse)
                .collect(Collectors.toList());

        return PageResponse.<ActivityResponse>builder()
                .content(activities)
                .page(page)
                .size(size)
                .totalElements(activityPage.getTotalElements())
                .totalPages(activityPage.getTotalPages())
                .first(activityPage.isFirst())
                .last(activityPage.isLast())
                .build();
    }

    public PageResponse<ActivityResponse> getCardActivities(String cardId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Activity> activityPage = activityRepository.findByCardIdOrderByCreatedAtDesc(cardId, pageable);

        List<ActivityResponse> activities = activityPage.getContent().stream()
                .map(mapperUtils::toActivityResponse)
                .collect(Collectors.toList());

        return PageResponse.<ActivityResponse>builder()
                .content(activities)
                .page(page)
                .size(size)
                .totalElements(activityPage.getTotalElements())
                .totalPages(activityPage.getTotalPages())
                .first(activityPage.isFirst())
                .last(activityPage.isLast())
                .build();
    }

    public List<ActivityResponse> getRecentBoardActivities(String boardId, int hours) {
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        List<Activity> activities = activityRepository.findByBoardIdAndCreatedAtAfterOrderByCreatedAtDesc(boardId, since);

        return activities.stream()
                .map(mapperUtils::toActivityResponse)
                .collect(Collectors.toList());
    }
}

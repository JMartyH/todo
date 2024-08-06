package com.todo.service.impl;

import com.todo.entity.ToDoEntity;
import com.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class ReminderService {


    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private final ToDoRepository toDoRepository;

    @Scheduled(fixedRate = 60000) // Every hour 3600000
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dueTime = now.plusHours(24);
        List<ToDoEntity> dueToDos = toDoRepository.findToDosDueSoon(now, dueTime);
        log.info(dueToDos.toString());
        for (ToDoEntity toDo : dueToDos) {
            // Send a reminder notification through the WebSocket
            this.messagingTemplate.convertAndSend("/topic/reminders",
                    "Reminder: To-do '" + toDo.getTitle() + "' is due soon!");
        }
    }


}

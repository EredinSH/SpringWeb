package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailScheduler {

    private final SimpleEmailService simpleEmailService;
    private final TaskRepository taskRepository;
    private final AdminConfig adminConfig;
    private static final String SUBJECT = "Tasks: Once a day email";

    public String communicat() {
        long size = taskRepository.count();
        String communicat;
        if (size == 1) {
            communicat = "Currently in database you got: " + size + " task";
        } else {
            communicat = "Currently in database you got: " + size + " tasks";
        }

        return communicat;
    }

    @Scheduled(cron = "0 0 10 * * *")
    public void sendInformationEmail() {

        simpleEmailService.send(
                Mail.builder()
                        .mailTo(adminConfig.getAdminMail())
                        .subject(SUBJECT)
                        .message(communicat())
                        .build()
        );

    }

}

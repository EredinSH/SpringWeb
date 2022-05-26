package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("preview_message", "Short information about email");
        context.setVariable("tasks_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("company_goal", adminConfig.getCompanyGoal());
        context.setVariable("company_email", adminConfig.getCompanyEmail());
        context.setVariable("company_phone", adminConfig.getCompanyPhone());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        context.setVariable("goodbye", "If we can be of any further assistance, please let us know.");
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildOneADayMail(String message) {
        Context context = new Context();
        context.setVariable("messageOAD", message);
        context.setVariable("tasks_urlOAD", "http://localhost:8888/crud");
        context.setVariable("buttonOAD", "Visit website");
        context.setVariable("admin_nameOAD", adminConfig.getAdminName());
        context.setVariable("company_nameOAD", adminConfig.getCompanyName());
        context.setVariable("company_goalOAD", adminConfig.getCompanyGoal());
        context.setVariable("company_emailOAD", adminConfig.getCompanyEmail());
        context.setVariable("company_phoneOAD", adminConfig.getCompanyPhone());
        context.setVariable("show_buttonOAD", true);
        context.setVariable("is_friendOAD", true);
        context.setVariable("admin_configOAD", adminConfig);
        context.setVariable("goodbyeOAD", "If we can be of any further assistance, please let us know.");
        return templateEngine.process("mail/one_a_day", context);
    }

}

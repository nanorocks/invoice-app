package com.app.invoice.App.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class ThymeleafUtil {
    @Autowired
    private TemplateEngine templateEngine;

    public String getHtmlFromTemplate(String templateName, Model model) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        Context context = new Context();
        context.setVariables(model.asMap());

        return templateEngine.process(templateName, context);
    }
}

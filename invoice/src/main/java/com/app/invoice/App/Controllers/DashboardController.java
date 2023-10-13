package com.app.invoice.App.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String index(Model model) {
        model.addAttribute("message", "Hello from Thymeleaf!");
        return "dashboard"; // This is the name of the Thymeleaf template (without the extension).
    }
}

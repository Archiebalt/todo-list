package my.code.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@ControllerAdvice
public class GlobalExceptionHandler implements ErrorController {

    @GetMapping("/error")
    public String getErrorPage() {
        return "error-page";
    }

    @ExceptionHandler(Throwable.class)
    public String handleThrowable(Throwable throwable) {
        return "redirect:/error";
    }

}

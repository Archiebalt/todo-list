package my.code.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
    private MockMvc mockMvc;

    @Test
    void getErrorPage_ShouldReturnErrorPage() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(globalExceptionHandler).build();

        mockMvc.perform(get("/error"))
                .andExpect(status().isOk())
                .andExpect(view().name("error-page"));
    }

    @Test
    void handleThrowable_ShouldRedirectToErrorPage() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(new Object() {
                    @GetMapping("/throw")
                    public void throwException() throws Exception {
                        throw new RuntimeException("Test exception");
                    }
                })
                .setControllerAdvice(globalExceptionHandler)
                .build();

        mockMvc.perform(get("/throw"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/error"));
    }
}
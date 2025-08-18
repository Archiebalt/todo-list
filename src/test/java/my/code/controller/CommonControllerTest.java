package my.code.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import my.code.entity.RecordStatus;
import my.code.entity.dto.RecordsContainerDto;
import my.code.service.RecordService;

@ExtendWith(MockitoExtension.class)
class CommonControllerTest {

    @Mock
    private RecordService recordService;

    @InjectMocks
    private CommonController commonController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(commonController).build();
    }

    @Test
    void redirectToHomePage_ShouldRedirectToHome() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    void getMainPage_ShouldReturnMainPageWithRecords() throws Exception {
        RecordsContainerDto container = new RecordsContainerDto(Collections.emptyList(), 0, 0);
        when(recordService.findAllRecords(null)).thenReturn(container);

        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("main-page"));
    }

    @Test
    void addRecord_ShouldSaveRecordAndRedirect() throws Exception {
        mockMvc.perform(post("/add-record")
                        .param("title", "Test Title"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        verify(recordService).saveRecord("Test Title");
    }

    @Test
    void makeRecordDone_ShouldUpdateStatusAndRedirect() throws Exception {
        mockMvc.perform(post("/make-record-done")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        verify(recordService).updateRecordStatus(1, RecordStatus.DONE);
    }

    @Test
    void makeRecordDone_WithFilter_ShouldPreserveFilterParameter() throws Exception {
        mockMvc.perform(post("/make-record-done")
                        .param("id", "1")
                        .param("filter", "ACTIVE"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home?filter=ACTIVE"));

        verify(recordService).updateRecordStatus(1, RecordStatus.DONE);
    }

    @Test
    void deleteRecord_ShouldDeleteRecordAndRedirect() throws Exception {
        mockMvc.perform(post("/delete-record")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        verify(recordService).deleteRecord(1);
    }
}
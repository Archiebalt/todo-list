package my.code.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import my.code.entity.RecordStatus;
import my.code.entity.dto.RecordsContainerDto;
import my.code.service.RecordService;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommonController {
    RecordService recordService;

    @GetMapping("/")
    public String redirectToHomePage() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getMainPage(Model model, @RequestParam(name = "filter", required = false) String filterMode) {
        RecordsContainerDto container = recordService.findAllRecords(filterMode);
        model.addAttribute("records", container.getRecords());
        model.addAttribute("numberOfDoneRecords", container.getNumberOfDoneRecords());
        model.addAttribute("numberOfActiveRecords", container.getNumberOfActiveRecords());
        return "main-page";
    }

    @PostMapping(value = "/add-record")
    public String addRecord(@RequestParam String title) {
        recordService.saveRecord(title);
        return "redirect:/home";
    }

    @PostMapping(value = "/make-record-done")
    public String makeRecordDone(@RequestParam int id,
            @RequestParam(name = "filter", required = false) String filterMode) {
        recordService.updateRecordStatus(id, RecordStatus.DONE);
        return "redirect:/home" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }

    @PostMapping(value = "/delete-record")
    public String deleteRecord(@RequestParam int id,
            @RequestParam(name = "filter", required = false) String filterMode) {
        recordService.deleteRecord(id);
        return "redirect:/home" + (filterMode != null && !filterMode.isBlank() ? "?filter=" + filterMode : "");
    }
}

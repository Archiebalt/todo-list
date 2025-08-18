package my.code.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import my.code.dao.RecordRepository;
import my.code.entity.Record;
import my.code.entity.RecordStatus;
import my.code.entity.dto.RecordsContainerDto;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordService recordService;

    private Record activeRecord;
    private Record doneRecord;

    @BeforeEach
    void setUp() {
        activeRecord = new Record("Active Task");
        activeRecord.setStatus(RecordStatus.ACTIVE);

        doneRecord = new Record("Done Task");
        doneRecord.setStatus(RecordStatus.DONE);
    }

    @Test
    void findAllRecords_ShouldReturnAllRecordsWhenNoFilter() {
        List<Record> records = Arrays.asList(activeRecord, doneRecord);
        when(recordRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(records);

        RecordsContainerDto result = recordService.findAllRecords(null);

        assertEquals(2, result.getRecords().size());
        assertEquals(1, result.getNumberOfDoneRecords());
        assertEquals(1, result.getNumberOfActiveRecords());
    }

    @Test
    void findAllRecords_ShouldFilterByActiveStatus() {
        List<Record> records = Arrays.asList(activeRecord, doneRecord);
        when(recordRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(records);

        RecordsContainerDto result = recordService.findAllRecords("ACTIVE");

        assertEquals(1, result.getRecords().size());
        assertEquals(RecordStatus.ACTIVE, result.getRecords().get(0).getStatus());
    }

    @Test
    void findAllRecords_ShouldFilterByDoneStatus() {
        List<Record> records = Arrays.asList(activeRecord, doneRecord);
        when(recordRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(records);

        RecordsContainerDto result = recordService.findAllRecords("DONE");

        assertEquals(1, result.getRecords().size());
        assertEquals(RecordStatus.DONE, result.getRecords().get(0).getStatus());
    }

    @Test
    void findAllRecords_ShouldReturnAllRecordsForInvalidFilter() {
        List<Record> records = Arrays.asList(activeRecord, doneRecord);
        when(recordRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))).thenReturn(records);

        RecordsContainerDto result = recordService.findAllRecords("INVALID");

        assertEquals(2, result.getRecords().size());
    }

    @Test
    void saveRecord_ShouldSaveValidRecord() {
        recordService.saveRecord("New Task");
        verify(recordRepository).save(any(Record.class));
    }

    @Test
    void saveRecord_ShouldNotSaveEmptyTitle() {
        recordService.saveRecord("");
        recordService.saveRecord("   ");
        recordService.saveRecord(null);
        verify(recordRepository, never()).save(any(Record.class));
    }

    @Test
    void updateRecordStatus_ShouldUpdateStatus() {
        recordService.updateRecordStatus(1, RecordStatus.DONE);
        verify(recordRepository).update(1, RecordStatus.DONE);
    }

    @Test
    void deleteRecord_ShouldDeleteRecord() {
        recordService.deleteRecord(1);
        verify(recordRepository).deleteById(1);
    }
}
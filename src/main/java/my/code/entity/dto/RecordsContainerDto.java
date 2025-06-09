package my.code.entity.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import my.code.entity.Record;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecordsContainerDto {

    final List<Record> records;
    final int numberOfDoneRecords;
    final int numberOfActiveRecords;
    
}

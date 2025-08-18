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
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecordsContainerDto {

    List<Record> records;
    int numberOfDoneRecords;
    int numberOfActiveRecords;
    
}

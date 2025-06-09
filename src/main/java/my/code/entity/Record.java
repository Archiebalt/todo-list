package my.code.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "records")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "title", nullable = false, length = 50)
    String title;

    @Column(name = "status", nullable = false)
    RecordStatus status;

    public Record() {
    }

    public Record(String title) {
        this.title = title;
        this.status = RecordStatus.ACTIVE;
    }

}

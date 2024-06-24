package org.xwstxkx.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "raw_data")
public class RawData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Объект адпейта из телеграма, тип данных - jsonb
    //В БД хранится не в формате строки, а в формате json
    @Type(JsonBinaryType.class)
    @Column(name = "event", columnDefinition = "jsonb")
    private Update event;
}


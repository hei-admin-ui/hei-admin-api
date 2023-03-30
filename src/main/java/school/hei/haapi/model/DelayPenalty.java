package school.hei.haapi.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import school.hei.haapi.repository.types.PostgresEnumType;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "\"delayPenalty\"")
@Getter
@Setter
@TypeDef(name = "psql_enum", typeClass = PostgresEnumType.class)
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DelayPenalty {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private String id;

    private Integer interest_percent;

    @Type(type = "psql_enum")
    @Enumerated(EnumType.STRING)
    private Timerate interest_timerate;

    private Integer grace_delay;

    private Integer applicability_delay_after_grace;

    private Instant creation_datetime;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DelayPenalty that = (DelayPenalty) o;
        return id != null && Objects.equals(id, that.id);
    }
    public enum Timerate {
        DAILY
    }
}

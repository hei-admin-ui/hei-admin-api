package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.hibernate.id.UUIDGenerator;
import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.CreateDelayPenaltyChange;
import school.hei.haapi.model.DelayPenalty;

import java.time.Instant;
import java.util.UUID;

@Component
@AllArgsConstructor
public class DelayPenaltyMapper {

    public DelayPenalty toDomain(CreateDelayPenaltyChange domain){
        return DelayPenalty
                .builder()
                .id(String.valueOf(new UUIDGenerator()))
                .creation_datetime(Instant.now())
                .interest_percent(domain.getInterestPercent())
                .interest_timerate(DelayPenalty.Timerate.valueOf(domain.getInterestTimerate().toString()))
                .grace_delay(domain.getGraceDelay())
                .applicability_delay_after_grace(domain.getApplicabilityDelayAfterGrace())
                .build();
    }
    public school.hei.haapi.endpoint.rest.model.DelayPenalty toRest(DelayPenalty rest){
        school.hei.haapi.endpoint.rest.model.DelayPenalty restDelay = new school.hei.haapi.endpoint.rest.model.DelayPenalty();
        restDelay.setId(rest.getId());
        restDelay.setCreationDatetime(rest.getCreation_datetime());
        restDelay.setInterestPercent(rest.getInterest_percent());
        restDelay.setInterestTimerate(school.hei.haapi.endpoint.rest.model.DelayPenalty.InterestTimerateEnum.valueOf(rest.getInterest_timerate().toString()));
        restDelay.setGraceDelay(rest.getGrace_delay());
        restDelay.setApplicabilityDelayAfterGrace(rest.getApplicability_delay_after_grace());
        return restDelay;
    }
}

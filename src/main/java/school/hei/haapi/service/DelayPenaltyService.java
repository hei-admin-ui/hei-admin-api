package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.DelayPenalty;
import school.hei.haapi.model.validator.DelayPenaltyValidator;
import school.hei.haapi.repository.DelayPenaltyRepository;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class DelayPenaltyService {
    private final DelayPenaltyRepository repository;
    private final DelayPenaltyValidator validator;

    public DelayPenalty getDelayPenalty(){
        List<DelayPenalty> getAll = repository.getDescPenalty();
        if(getAll.size() == 0){
            return DelayPenalty.builder()
                    .id("default")
                    .interest_percent(0)
                    .interest_timerate(DelayPenalty.Timerate.DAILY)
                    .grace_delay(0)
                    .creation_datetime(Instant.now())
                    .applicability_delay_after_grace(0)
                    .build();
        } else {
        return getAll.get(0);
        }
    }
     public DelayPenalty updateDelayPenalty(DelayPenalty update){
        validator.accept(update);
        return repository.save(update);
     }
}

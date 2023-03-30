package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.DelayPenalty;
import school.hei.haapi.model.validator.DelayPenaltyValidator;
import school.hei.haapi.repository.DelayPenaltyRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DelayPenaltyService {
    private final DelayPenaltyRepository delayPenaltyRepository;
    private final DelayPenaltyValidator delayPenaltyValidator;

    public DelayPenalty getDelayPenalty(){
        List<DelayPenalty> delayPenalties = delayPenaltyRepository.findAll();
        DelayPenalty activeDelay = delayPenalties.get(0);
        for (DelayPenalty delayPenalty: delayPenalties) {
            if(activeDelay.getCreation_datetime().compareTo(delayPenalty.getCreation_datetime()) < 0){
                activeDelay = delayPenalty;
            }
        }
        return activeDelay;
    }
     public DelayPenalty updateDelayPenalty(DelayPenalty update){
        delayPenaltyValidator.accept(update);
        return delayPenaltyRepository.save(update);
     }
}

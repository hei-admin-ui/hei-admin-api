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
    private final DelayPenaltyRepository repository;
    private final DelayPenaltyValidator validator;

    public DelayPenalty getDelayPenalty(){
        List<DelayPenalty> getAll = repository.findAll();
        DelayPenalty activeDelay = getAll.get(0);
        for (DelayPenalty delayPenalty: getAll) {
            if(activeDelay.getCreation_datetime().compareTo(delayPenalty.getCreation_datetime()) < 0){
                activeDelay = delayPenalty;
            }
        }
        return activeDelay;
    }
     public DelayPenalty updateDelayPenalty(DelayPenalty update){
        validator.accept(update);
        return repository.save(update);
     }
}

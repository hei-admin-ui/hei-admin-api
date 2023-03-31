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
        List<DelayPenalty> getAll = repository.getDescPenalty();
        return getAll.get(0);
    }
     public DelayPenalty updateDelayPenalty(DelayPenalty update){
        validator.accept(update);
        return repository.save(update);
     }
}

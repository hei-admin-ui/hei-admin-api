package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.DelayPenalty;
import school.hei.haapi.repository.DelayPenaltyRepository;

import java.time.Instant;
import java.util.List;

import static school.hei.haapi.endpoint.rest.model.Fee.StatusEnum.LATE;

@Service
@AllArgsConstructor
@Slf4j
public class DelayPenaltyService {

  private static final school.hei.haapi.endpoint.rest.model.Fee.StatusEnum DEFAULT_STATUS = LATE;
  private final DelayPenaltyRepository delayPenaltyRepository;
  public List<DelayPenalty> getCurrentDelayPenalty(Instant date, int pageSize){
    Pageable pageable = PageRequest.of(1, pageSize);
    return delayPenaltyRepository.getCurrentDelayPenalty(date, pageable);
  }
  public DelayPenalty setCurrentDelayPenalty(DelayPenalty delayPenalty){
    return delayPenaltyRepository.save(delayPenalty);
  }
}

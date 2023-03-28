package school.hei.haapi.endpoint.rest.validator;

import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.CreateDelayPenaltyChange;
import school.hei.haapi.endpoint.rest.model.DelayPenalty;
import school.hei.haapi.model.exception.BadRequestException;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
public class CreateDelayPenaltyValidator implements Consumer<CreateDelayPenaltyChange> {

  public void accept(DelayPenalty delayPenalty) {
    this.accept(delayPenalty);
  }

  @Override
  public void accept(CreateDelayPenaltyChange delayPenalty) {
    Set<String> violationMessages = new HashSet<>();
    if (delayPenalty.getInterestTimerate() == null) {
      violationMessages.add("Interest time rate is mandatory");
    }
    if (delayPenalty.getInterestPercent() == null) {
      violationMessages.add("Interest percent is mandatory");
    } else if(delayPenalty.getInterestPercent() < 0){
      violationMessages.add("Interest percent must be positive");
    }

    if (delayPenalty.getGraceDelay() == null) {
      violationMessages.add("Grace delay is mandatory");
    } else if(delayPenalty.getGraceDelay() < 0){
      violationMessages.add("Grace delay must be positive");
    }

    if (delayPenalty.getApplicabilityDelayAfterGrace() == null) {
      violationMessages.add("Applicability delay after grace is mandatory");
    } else if(delayPenalty.getApplicabilityDelayAfterGrace() < 0){
      violationMessages.add("Applicability delay after grace must be positive");
    }

    if (!violationMessages.isEmpty()) {
      String formattedViolationMessages = violationMessages.stream()
          .map(String::toString)
          .collect(Collectors.joining(". "));
      throw new BadRequestException(formattedViolationMessages);
    }
  }
}

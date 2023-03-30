package school.hei.haapi.model.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.model.DelayPenalty;
import school.hei.haapi.model.exception.BadRequestException;

import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class DelayPenaltyValidator implements Consumer<DelayPenalty> {

    private final Validator validator;

    public void accept(List<DelayPenalty> delayPenalties) {
        delayPenalties.forEach(this::accept);
    }

    @Override
    public void accept(DelayPenalty delayPenalty) {
        Set<String> violationMessages = new HashSet<>();

        if (delayPenalty.getInterest_percent() == null || delayPenalty.getInterest_percent() < 0) {
            violationMessages.add("Interest percent is mandatory or must be positive");
        }
        if (delayPenalty.getInterest_timerate() == null) {
            violationMessages.add("Interest Time Rate is mandatory");
        }
        if (delayPenalty.getGrace_delay() == null || delayPenalty.getGrace_delay() < 0) {
            violationMessages.add("GraceDelay must be superior than 0 and mandatory");
        }
        if (delayPenalty.getApplicability_delay_after_grace() == null || delayPenalty.getApplicability_delay_after_grace() < 0) {
            violationMessages.add("GraceDelay must be superior than 0 and mandatory");
        }
        if (!violationMessages.isEmpty()) {
            String formattedViolationMessages = violationMessages.stream()
                    .map(String::toString)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(formattedViolationMessages);
        }
    }

}

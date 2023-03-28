package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.CreateDelayPenaltyChange;
import school.hei.haapi.endpoint.rest.model.CreateFee;
import school.hei.haapi.endpoint.rest.model.DelayPenalty;
import school.hei.haapi.endpoint.rest.validator.CreateDelayPenaltyValidator;
import school.hei.haapi.endpoint.rest.validator.CreateFeeValidator;
import school.hei.haapi.model.User;
import school.hei.haapi.model.exception.BadRequestException;
import school.hei.haapi.model.exception.NotFoundException;
import school.hei.haapi.service.UserService;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toUnmodifiableList;
import static school.hei.haapi.endpoint.rest.model.DelayPenalty.StatusEnum.UNPAID;

@Component
@AllArgsConstructor
public class DelayPenaltyMapper {
    private final CreateDelayPenaltyValidator createDelayPenaltyValidator;

    public DelayPenalty toRestFee(school.hei.haapi.model.DelayPenalty delayPenalty) {
        return new DelayPenalty()
                .id(delayPenalty.getId())
                .interestTimerate(delayPenalty.getInterestTimeRate())
                .interestPercent(delayPenalty.getInterestPercent())
                .graceDelay(delayPenalty.getGraceDelay())
                .applicabilityDelayAfterGrace(delayPenalty.getApplicabilityDelayAfterGrace())
                .creationDatetime(delayPenalty.getCreationDatetime());
    }

    private school.hei.haapi.model.DelayPenalty toDomainFee(CreateDelayPenaltyChange delayPenalty) {
      createDelayPenaltyValidator.accept(delayPenalty);

        return school.hei.haapi.model.DelayPenalty.builder()
                .interestTimeRate(delayPenalty.getInterestTimerate())
                .interestPercent(delayPenalty.getInterestPercent())
                .graceDelay(delayPenalty.getGraceDelay())
                .applicabilityDelayAfterGrace(delayPenalty.getApplicabilityDelayAfterGrace())
                .creationDatetime(delayPenalty.getCreationDatetime()).build();
    }


}

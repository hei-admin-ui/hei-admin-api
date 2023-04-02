package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.DelayPenaltyMapper;
import school.hei.haapi.endpoint.rest.model.CreateDelayPenaltyChange;
import school.hei.haapi.endpoint.rest.model.DelayPenalty;
import school.hei.haapi.service.DelayPenaltyService;


@RestController
@AllArgsConstructor
public class DelayPenaltyController {
    private final DelayPenaltyMapper mapper;
    private final DelayPenaltyService service;

    @PutMapping("/delay_penalty_change")
    public DelayPenalty crupdateDelayPenalty(@RequestBody CreateDelayPenaltyChange news){
        return mapper.toRest(service.updateDelayPenalty(mapper.toDomain(news)));
    }

    @GetMapping("/delay_penalty")
    public DelayPenalty getDelayPenalty(){
        return mapper.toRest(service.getDelayPenalty());
    }
}
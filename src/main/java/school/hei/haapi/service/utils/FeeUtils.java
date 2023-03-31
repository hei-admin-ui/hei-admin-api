package school.hei.haapi.service.utils;

import school.hei.haapi.model.DelayPenalty;
import school.hei.haapi.model.Fee;
import school.hei.haapi.service.DelayPenaltyService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;


public class FeeUtils {
    private static DelayPenaltyService delayPenaltyService;
    public static int countRemainingAccount(int base, int grace_delay, int applicabilityAfterGrace) {

        for (int i = 1; i <= applicabilityAfterGrace; i++) {
            base += ((base * grace_delay) / 100);
        }
        return base;
    }

    public static int checkDelayValue(Fee fee, Instant nowDatetime, int graceDelay, Instant dueFeesTime, DelayPenalty delayPenalty) {
        if (nowDatetime.plus(graceDelay, ChronoUnit.DAYS).isBefore(dueFeesTime)) {
            return countRemainingAccount(fee.getRemainingAmount(), graceDelay, delayPenalty.getApplicability_delay_after_grace());
        }
        return fee.getRemainingAmount();
    }
    public static List<Fee> checkStudentFeesDelay (List<Fee> feeList){
        DelayPenalty delayPenalty = delayPenaltyService.getDelayPenalty();
        for (Fee fee : feeList) {
            checkDelayValue(fee,Instant.now(),delayPenalty.getGrace_delay(),fee.getDueDatetime(),delayPenalty);
        }
        return  feeList;
    }
}

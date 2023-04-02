package school.hei.haapi.service.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class FeeUtils {
    public static double realAmountToPay(double remainingAmount, int interest, Instant due_datetime, int grace_delay, Instant today, int applicabilityAGD){
        Instant lastDay = due_datetime.plus(grace_delay, ChronoUnit.DAYS);
        Instant interestDay = lastDay.plus(1,ChronoUnit.DAYS);
        LocalDate targetDate = LocalDate.of(interestDay.atZone(ZoneId.of("UTC")).getYear(),interestDay.atZone(ZoneId.of("UTC")).getMonthValue(),interestDay.atZone(ZoneId.of("UTC")).getDayOfMonth());

        if(today.atZone(ZoneId.of("UTC")).toLocalDate().isAfter(targetDate)){
            if(today.isAfter(lastDay.plus(applicabilityAGD,ChronoUnit.DAYS))){
                for(int i = 0; i < applicabilityAGD; i++ ){
                    remainingAmount += ((remainingAmount*interest)/100);
                }
            }
            else{
                LocalDate convertToday = today.atZone(ZoneId.of("UTC")).toLocalDate();
                for(int i = 0; i < ChronoUnit.DAYS.between(targetDate,convertToday); i++){
                    remainingAmount += ((remainingAmount*interest)/100);
                }
            }
        }
        return remainingAmount;
    }
}

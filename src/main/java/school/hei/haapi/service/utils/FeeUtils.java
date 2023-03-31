package school.hei.haapi.service.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class FeeUtils {
    public static int realAmountToPay(int remainingAmount, int interest, Instant due_datetime, int grace_delay, Instant today, int applicabilityAGD){
        Instant lastDay = due_datetime.plus(grace_delay, ChronoUnit.DAYS);
        if(today.isAfter(lastDay)){
            if(today.atZone(ZoneId.systemDefault()).getDayOfMonth() > lastDay.plus(applicabilityAGD,ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).getDayOfMonth()){
                for(int i = 1; i <= applicabilityAGD; i++ ){
                    remainingAmount += ((remainingAmount*interest)/100);
                }
            }
            else{
                for(int i = 1; i <= today.minus(lastDay.atZone(ZoneId.systemDefault()).getDayOfMonth(),ChronoUnit.DAYS).atZone(ZoneId.systemDefault()).getDayOfMonth(); i++){
                    remainingAmount += ((remainingAmount*interest)/100);
                }
            }
        }
        return remainingAmount;
    }
}

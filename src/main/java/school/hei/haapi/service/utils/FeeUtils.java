package school.hei.haapi.service.utils;

public class FeeUtils {
    public static int countRemainingAccount(int base,int grace_delay){
        int value = 0;
        int tempValue = 0;
        for(int i=0;i<=grace_delay;i++){
            if(i==0){
                tempValue = base;
            }
            value += (tempValue*(grace_delay/100));
            tempValue += value;
            value = 0;
        }
        return tempValue;
    }
}

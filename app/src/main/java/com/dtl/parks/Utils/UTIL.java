package com.dtl.parks.Utils;

public class UTIL {
    public static final String ParksUrl = "https://developer.nps.gov/api/v1/parks?stateCode=cal&api_key=aiC1QMiaDBPghUUd8fdbQQdU6MFLcPeAEN4a49Hw";
    public static String getParksUrl(String stateCode){
        return "https://developer.nps.gov/api/v1/parks?stateCode="+stateCode+"&api_key=aiC1QMiaDBPghUUd8fdbQQdU6MFLcPeAEN4a49Hw";
    }
}

package ihs.APIdownloaders;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.AbstractMap.SimpleEntry;


public class NBPCurrencyRateDownloader extends SiteContentDownloader {

    private static final String API_URL = "http://api.nbp.pl/api/exchangerates/rates/c/";
    private static final String API_SUFIX = "?format=json";
    private static final long CACHE_REFRESH_TIME = TimeUnit.HOURS.toMillis(5);

    private Map<String, SimpleEntry<Double, Long>> cache = new HashMap<>(); //(currencyCode , (currencyRate, timeOfNextUpdate))
    private static final NBPCurrencyRateDownloader instance = new NBPCurrencyRateDownloader();

    public static NBPCurrencyRateDownloader getInstance() {
        return instance;
    }
    protected NBPCurrencyRateDownloader(){}

    public synchronized double getCurrencyRate(String currencyCode) {
        SimpleEntry<Double, Long> ret = cache.get(currencyCode);
        if(ret == null || ret.getValue() < System.currentTimeMillis()){
            //We dont have info in cache or it is too old.
            Double rate = downloadCurrencyRate(currencyCode);
            cache.put(currencyCode, ret = new SimpleEntry<>(rate, System.currentTimeMillis() + CACHE_REFRESH_TIME));
        }
        return ret.getKey();
    }

    private Double downloadCurrencyRate(String currencyCode) {
        String fullURL = API_URL + currencyCode.toUpperCase() + API_SUFIX;
        JsonObject rootObj = getResponseJson(fullURL);
        if (rootObj != null) {
            JsonArray rates = rootObj.getAsJsonArray("rates");
            if (rates != null) {
                return rates.get(0).getAsJsonObject().get("ask").getAsDouble();
            }
        }
        return null;
    }

}
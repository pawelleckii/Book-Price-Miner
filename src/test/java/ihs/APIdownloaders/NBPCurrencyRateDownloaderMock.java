package ihs.APIdownloaders;

import com.google.gson.JsonObject;
import ihs.TestingUtils;

public class NBPCurrencyRateDownloaderMock extends NBPCurrencyRateDownloader {

    public static String USD_CURR_CODE = "USD";
    public static double USD_CURR_RATE = 3.8001;


    static final String URL = "http://api.nbp.pl/api/exchangerates/rates/c/USD?format=json";
    static final String fileLocation = "src\\test\\resources\\nbp\\NbpUSD.json";

    @Override
    protected JsonObject getResponseJson(String fullURL) {
        if (fullURL.equals(URL)){
            return TestingUtils.readJsonObject(fileLocation);
        }
        throw new IllegalArgumentException();
    }

}

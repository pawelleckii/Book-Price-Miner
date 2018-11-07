package ihs.api;

import com.google.gson.JsonObject;
import ihs.TestUtils;

public class NBPCurrencyRateDownloaderMock extends NBPCurrencyRateDownloader {

    public static String USD_CURR_CODE = "USD";
    public static double USD_CURR_RATE = 3.8001;

    static final String URL = "http://api.nbp.pl/api/exchangerates/rates/c/USD?format=json";
    static final String FILE_LOCATION = "src\\test\\resources\\nbp\\NbpUSD.json";


    @Override
    protected JsonObject getResponseJson(String fullURL) {
        if (fullURL.equals(URL)){
            return TestUtils.readJsonObject(FILE_LOCATION);
        }
        throw new IllegalArgumentException();
    }
}

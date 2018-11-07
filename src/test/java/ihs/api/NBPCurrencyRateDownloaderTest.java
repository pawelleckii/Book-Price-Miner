package ihs.api;

import org.junit.Before;
import org.junit.Test;


public class NBPCurrencyRateDownloaderTest {


    @Before
    public void setup(){
    }

    /***
     * Checking if we are properly parsing prepared Json file.
     */
    @Test
    public void getCurrencyRate() {
        NBPCurrencyRateDownloader nbpDownloader = new NBPCurrencyRateDownloaderMock();
        double response = nbpDownloader.getCurrencyRate(NBPCurrencyRateDownloaderMock.USD_CURR_CODE);
        assert NBPCurrencyRateDownloaderMock.USD_CURR_RATE == response;
    }


}

package ihs.APIdownloaders;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Class for downloading site content and returning it in JsonObject.
 * Every api downloader extends it.
 */
public class SiteContentDownloader {

    protected JsonObject getResponseJson(String fullURL) {
        InputStream stream = null;
        try {
            URL url = new URL(fullURL);
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jsonParser = new JsonParser();
            stream = (InputStream) request.getContent();
            JsonElement root = jsonParser.parse(new InputStreamReader(stream));
            JsonObject rootObj = root.getAsJsonObject();
            stream.close();
            return rootObj;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String urlifyTitle(String userRawTitle){
        return userRawTitle.replace(" ", "+").replace("/", "");
    }

}

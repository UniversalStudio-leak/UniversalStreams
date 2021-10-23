package ru.universalstudio.streams.youtube;

import java.math.*;
import com.google.gson.*;
import ru.universalstudio.streams.*;
import ru.universalstudio.streams.youtube.http.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class Live {

    private final JsonElement json;

    public Live(String string) {
        this.json = (new JsonParser()).parse(Requests.get("https://www.googleapis.com/youtube/v3/videos?key=" + Manager.getInstance().getConfig().getString("GOOGLE_API_KEY") + "&part=statistics,snippet,liveStreamingDetails&id=" + (string.split("v="))[1]));
    }

    public BigInteger getViews() {
        return this.json.getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("liveStreamingDetails").getAsJsonObject().get("concurrentViewers").getAsBigInteger();
    }

    public String getTitle() {
        String string = this.json.getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("snippet").getAsJsonObject().get("title").getAsString();
        return (string.length() >= 52) ? string.substring(0, 52).concat("..") : string;
    }

    public BigInteger getLikes() {
        return this.json.getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("statistics").getAsJsonObject().get("likeCount").getAsBigInteger();
    }

    public BigInteger getDisLike() {
        return this.json.getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("statistics").getAsJsonObject().get("dislikeCount").getAsBigInteger();
    }

}

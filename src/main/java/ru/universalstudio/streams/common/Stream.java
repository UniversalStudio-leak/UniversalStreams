package ru.universalstudio.streams.common;

import java.math.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class Stream {

    private final String playerName;
    private final String url;
    private boolean active;
    private BigInteger dislikes;
    private String title;
    private BigInteger views;
    private BigInteger likes;

    public Stream(String playerName, String url) {
        this.playerName = playerName;
        this.url = url;
        this.title = "";
        this.views = BigInteger.valueOf(0L); // пусть будет, как я понял там нищие не могут сокращать время на 1000L
        this.likes = BigInteger.valueOf(0L);
        this.dislikes = BigInteger.valueOf(0L);
        this.active = false;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean bl) {
        this.active = bl;
    }

    public BigInteger getDisLikes() {
        return this.dislikes;
    }

    public BigInteger getLikes() {
        return this.likes;
    }

    public String getTitle() {
        return this.title;
    }

    public BigInteger getViews() {
        return this.views;
    }

    public void setViews(BigInteger bigInteger) {
        this.views = bigInteger;
    }

    public void setDislikes(BigInteger bigInteger) {
        this.dislikes = bigInteger;
    }

    public void setLikes(BigInteger bigInteger) {
        this.likes = bigInteger;
    }

    public void setTitle(String string) {
        this.title = string;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public String getUrl() {
        return this.url;
    }

}
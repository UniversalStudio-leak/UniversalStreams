package ru.universalstudio.streams.common;

import java.util.concurrent.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 */

public class StreamManager {

    private static ConcurrentHashMap<String, Stream> streams = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, Stream> getStreams() {
        return streams;
    }

    public static void remove(String string) {
        streams.remove(string.toLowerCase());
    }

    public static boolean contains(String string) {
        return streams.containsKey(string.toLowerCase());
    }

}
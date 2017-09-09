package ru.kachkovsky.curcon.data.http;

import java.net.HttpURLConnection;

public interface ResponseParser<T> {
    <T> T parseResponse(HttpURLConnection connection) throws Exception;
}

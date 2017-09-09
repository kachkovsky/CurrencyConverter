package ru.kachkovsky.curcon.data.http;

import java.io.InputStream;

import ru.kachkovsky.curcon.data.http.HttpClient.Response;

public interface ResponseParser {
    <T> T parseResponse(Class<T> clazz, Response response) throws Exception;

    <T> T parseResponse(Class<T> clazz, InputStream stream) throws Exception;
}

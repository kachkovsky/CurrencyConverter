package ru.kachkovsky.curcon.data.http;

import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import ru.kachkovsky.curcon.data.http.HttpClient.Response;

public class XmlResponseParser implements ResponseParser {

    public <T> T parseResponse(Class<T> clazz, Response response) throws Exception {
        return new Persister().read(clazz, new ByteArrayInputStream(response.getBody()));
    }

    public <T> T parseResponse(Class<T> clazz, InputStream stream) throws Exception {
        return new Persister().read(clazz, stream);
    }
}

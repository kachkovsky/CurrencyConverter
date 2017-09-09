package ru.kachkovsky.curcon.data.http;

import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;

import ru.kachkovsky.curcon.data.http.HttpClient.Response;

public class XmlResponseParser<T> implements ResponseParser<T> {


    private final Class<T> typeParameterClass;

    public XmlResponseParser(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public <T> T parseResponse(HttpURLConnection connection) throws Exception {
        return (T) new Persister().read(typeParameterClass, connection.getInputStream());
    }
}

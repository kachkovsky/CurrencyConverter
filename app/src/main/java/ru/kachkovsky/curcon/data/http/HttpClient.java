package ru.kachkovsky.curcon.data.http;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    private static String TAG = HttpClient.class.getSimpleName();

    private static class Holder {
        private static final HttpClient INSTANCE = new HttpClient();
    }

    public static HttpClient getInstance() {
        return Holder.INSTANCE;
    }

    public static class RequestParameters {

        public enum CacheMode {
            DEFAULT,
            FROM_CACHE,
            FROM_NET;
        }

        private String url;
        private Integer connectionTimeout;
        private Integer readTimeout;
        private boolean useCache;
        private CacheMode cacheMode;

        public RequestParameters() {
            useCache = true;
        }

        public boolean isUseCache() {
            return useCache;
        }

        public void setUseCache(boolean useCache) {
            this.useCache = useCache;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(Integer connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public Integer getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(Integer readTimeout) {
            this.readTimeout = readTimeout;
        }

        public CacheMode getCacheMode() {
            return cacheMode;
        }

        public void setCacheMode(CacheMode cacheMode) {
            this.cacheMode = cacheMode;
        }
    }

    public static class Response {
        private Integer code;
        private byte[] body;
        private Exception error;

        public Exception getError() {
            return error;
        }

        public void setError(Exception error) {
            this.error = error;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public byte[] getBody() {
            return body;
        }

        public void setBody(byte[] body) {
            this.body = body;
        }
    }

    private static final int CONNECTION_TIMEOUT = 30000;
    private static final int READ_TIMEOUT = 30000;

    private HttpClient() {
    }

    public Response doRequest(RequestParameters requestParams) {
        Response response = null;
        try {
            response = handleResponse(openConnection(requestParams));
        } catch (IOException e) {
            Log.e(TAG, "response parsing error", e);
            response = new Response();
            response.setError(e);
        }
        return response;
    }

    private HttpURLConnection openConnection(RequestParameters requestParams) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(requestParams.getUrl()).openConnection();
        connection.setUseCaches(requestParams.isUseCache());
        connection.setDoInput(true);
        connection.setDoOutput(false);
        connection.setConnectTimeout(requestParams.getConnectionTimeout() != null ? requestParams.getConnectionTimeout() : CONNECTION_TIMEOUT);
        connection.setReadTimeout(requestParams.getReadTimeout() != null ? requestParams.getReadTimeout() : READ_TIMEOUT);
        if (requestParams.isUseCache()) {
            switch (requestParams.cacheMode) {
                case DEFAULT:
                    connection.addRequestProperty("Cache-Control", "max-stale=2419200");
                    break;
                case FROM_CACHE:
                    connection.addRequestProperty("Cache-Control", "max-stale=2419200, only-if-cached");
                    break;
                case FROM_NET:
                    connection.addRequestProperty("Cache-Control", "no-cache");
                    break;
            }
        }
        return connection;
    }

    private Response handleResponse(HttpURLConnection connection) throws IOException {
        Response response = new Response();
        response.setCode(connection.getResponseCode());
        try {
            response.setBody(streamToBytes(connection.getInputStream()));
        } catch (IOException e) {
            Log.e(TAG, "response body parsing error", e);
            response.setError(e);
        } finally {
            connection.disconnect();
        }
        return response;
    }

    private static final int BUFFER_SIZE = 16384; //2 ^ 14

    private byte[] streamToBytes(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[BUFFER_SIZE];

        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }
}

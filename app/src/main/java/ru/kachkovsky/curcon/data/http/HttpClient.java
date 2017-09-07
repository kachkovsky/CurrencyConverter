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

    public Response doRequest(String url) {
        Response response = null;
        try {
            response = handleResponse(openConnection(url));
        } catch (IOException e) {
            Log.e(TAG, "response parsing error", e);
            response = new Response();
            response.setError(e);
        }
        return response;
    }

    private HttpURLConnection openConnection(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setConnectTimeout(CONNECTION_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
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

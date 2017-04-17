package kirill.sorokin.ru.calculatorcurrency;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Kirill on 12.04.2017.
 */
public class NetworkThread extends AsyncTask<Void, Void, String > {

    private static final String ADDRESS = "http://www.cbr.ru/scripts/XML_daily.asp";

    public interface DownloadCompleteListener {
        void downloadComplete();
    }

    private Context context;
    private boolean stop = false;
    private DownloadCompleteListener listener = null;

    public NetworkThread(Context context) {
        this.context = context;
    }

    public void setListener(DownloadCompleteListener listener) {
        this.listener = listener;
    }

    private boolean isNetworkAvalibably() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public boolean isStop() {
        return stop;
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (isNetworkAvalibably()) {
            try {
                URL url = new URL(ADDRESS);
                Log.d();
                return downloadUrl(url);
            } catch (Exception e) {
                Log.e(e.getMessage(), e);
            }
        }
        return null;
    }

    private String downloadUrl(URL url) {
        Log.d();
        InputStream stream = null;
        HttpURLConnection connection = null;
        String result = null;
        try {
            Log.d();
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.connect();
            Log.d("CONNECT_SUCCESS");
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            stream = connection.getInputStream();
            Log.d("GET_INPUT_STREAM_SUCCESS");
            if (stream != null) {
                result = readStream(stream);
            }
        } catch (IOException e) {
            Log.e(e.getMessage(), e);
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                Log.e(e.getMessage(), e);
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    private String readStream(InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader input = null;
        try {
            input = new InputStreamReader(stream, "Cp1251");
            char[] buf = new char[1000];
            int size = input.read(buf);
            while (size > 0) {
                sb.append(buf, 0, size);
                size = input.read(buf);
            }
        } catch (IOException e) {
            Log.e(e.getMessage(), e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                Log.e(e.getMessage(), e);
            }
        }
        return sb.toString();
    }

    @Override
    protected void onPostExecute(String resultString) {
        super.onPostExecute(resultString);
        if (resultString != null && !resultString.isEmpty()) {
            PrefManager.setValCurs(context, resultString);
        }
        stop = true;
        if (listener != null) {
            listener.downloadComplete();
        } else {
            Log.e("listener is null");
        }
    }
}


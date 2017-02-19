package giraffe.fonsecakarsten.com.giraffe;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kfonseca on 2/19/17.
 */

class fetchThesaurus extends AsyncTask<String, Void, ArrayList<String>> {
    private ArrayList<String> toReturn = new ArrayList<>();
    private URL url;

    public fetchThesaurus(URL ur) {
        this.url = ur;
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            connection.setRequestMethod("GET");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
        connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

        int responseCode = 0;
        try {
            responseCode = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        final StringBuilder output = new StringBuilder("Request URL " + url);
//        //output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
//        output.append(System.getProperty("line.separator")).append("Response Code ").append(responseCode);
//        output.append(System.getProperty("line.separator")).append("Type ").append("GET");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line;
        try {
            while ((line = br.readLine()) != null) {
                toReturn.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toReturn;
    }
}


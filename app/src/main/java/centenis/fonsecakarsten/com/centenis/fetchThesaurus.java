package centenis.fonsecakarsten.com.centenis;

import android.os.AsyncTask;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kfonseca on 2/19/17.
 */

class fetchThesaurus extends AsyncTask<String, Void, ArrayList<String>> {
    private String word1;

    public fetchThesaurus(String word) {
        word1 = word;
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {

        DatamuseQuery query1 = new DatamuseQuery();
        JSONParse json = new JSONParse();
        ArrayList<String> relatedWords = new ArrayList<>();
        String[] toReturn = json.parseWords(query1.findSimilar(word1));
        int[] wordScores = json.parseScores(query1.findSimilar(word1));
        for (int i = 0; i < wordScores.length; i++) {
            if (wordScores[i] > 50000) {
                relatedWords.add(toReturn[i]);
            }
        }
        return relatedWords;
    }
}


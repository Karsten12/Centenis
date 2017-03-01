package centenis.fonsecakarsten.com.centenis;

import android.os.AsyncTask;

import java.util.ArrayList;

/**
 * Created by kfonseca on 2/19/17.
 */

class fetchThesaurus extends AsyncTask<String, Void, Boolean> {
    private String word1;
    private ArrayList<String> watson1;

    public fetchThesaurus(String word, ArrayList<String> watsonWords) {
        word1 = word;
        watson1 = watsonWords;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        boolean containsGuess = false;
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

        if (relatedWords.size() != 0) {
            for (int i = 0; i < relatedWords.size(); i++) {
                if (watson1.contains(relatedWords.get(i))) {
                    containsGuess = true;
                }
            }
        }


        return containsGuess;
    }
}


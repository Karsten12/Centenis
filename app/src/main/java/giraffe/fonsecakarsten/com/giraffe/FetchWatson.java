package giraffe.fonsecakarsten.com.giraffe;

import android.content.Context;
import android.os.AsyncTask;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.io.File;
import java.net.URL;

/**
 * Created by kfonseca on 2/18/17.
 */

public class FetchWatson extends AsyncTask<URL, Integer, Long> {

    private Context context;
    private String path;

    FetchWatson(Context current, String realpath) {
        this.context = current;
        this.path = realpath;
    }

    @Override
    protected Long doInBackground(URL... params) {
        VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey("bd8ca3f2243490e58d837849484862d2ba777df6");

        System.out.println(path);
        ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                .images(new File(path))
                .build();
        VisualClassification result = service.classify(options).execute();
        System.out.println(result);
        return null;
    }

    @Override
    protected void onPostExecute(Long aLong) {

    }
}


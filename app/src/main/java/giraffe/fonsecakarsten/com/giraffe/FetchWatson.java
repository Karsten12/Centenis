package giraffe.fonsecakarsten.com.giraffe;

import android.content.Context;
import android.os.AsyncTask;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassifier;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by kfonseca on 2/18/17.
 */

public class FetchWatson extends AsyncTask<URL, Integer, ArrayList<String>> {

    //private Context context;
    private String path;

    FetchWatson(Context current, String realpath) {
        //this.context = current;
        this.path = realpath;
    }

    @Override
    protected ArrayList<String> doInBackground(URL... params) {
        ArrayList<String> tags = new ArrayList<>();
        VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey("bd8ca3f2243490e58d837849484862d2ba777df6");
        ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                .images(new File(path))
                .build();
        VisualClassification result = service.classify(options).execute();
        List<VisualClassifier.VisualClass> idk = result.getImages().get(0).getClassifiers().get(0).getClasses();
        for (int i = 0; i < idk.size(); i++) {
            tags.add(idk.get(i).getName());
        }
        return tags;
    }
}


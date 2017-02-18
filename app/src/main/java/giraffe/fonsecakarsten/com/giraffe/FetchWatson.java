package giraffe.fonsecakarsten.com.giraffe;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by kfonseca on 2/18/17.
 */

public class FetchWatson extends AsyncTask<URL, Integer, Long> {

    private Context context;

    FetchWatson(Context current) {
        this.context = current;
    }

    @Override
    protected Long doInBackground(URL... params) {
        VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey("{bd8ca3f2243490e58d837849484862d2ba777df6}");

        //Context context = ();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();
        File f = new File("src/main/res/drawable/frog1");
        ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                .images(f)
                .build();
        VisualClassification result = service.classify(options).execute();
        System.out.println(result);
        return null;
    }
}


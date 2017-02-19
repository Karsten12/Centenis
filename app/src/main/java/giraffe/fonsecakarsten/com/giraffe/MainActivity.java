package giraffe.fonsecakarsten.com.giraffe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private ArrayList<String> tags;
    private EditText mText;
    PermissionListener permissionlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
        Button btnSelectImage2 = (Button) findViewById(R.id.btnSelectImage2);
        imageView = (ImageView) findViewById(R.id.imgView);
        mText = (EditText) findViewById(R.id.insertText);
        btnSelectImage.setOnClickListener(this);
        btnSelectImage2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSelectImage:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
                break;

            case R.id.btnSelectImage2:
                String str1 = mText.getText().toString();
                try {
                    if (str1.length() != 0) {
                        getThesaurus(str1);
                    } else {
                        Toast toast = Toast.makeText(this, "Please make sure to first choose an image and then enter a guess", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == Activity.RESULT_OK && data != null) {
            String realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
            FetchWatson task = new FetchWatson(realPath);
            try {
                tags = task.execute().get();
                imageView.setImageURI(data.getData());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


    private void getThesaurus(String word) throws IOException {
        ArrayList<String> toReturn = new ArrayList<>();
        String api_key = "ad74d3ccf397694429ae145c20db06cc"; // DO NOT CHANGE
        String inter = String.format("http://words.bighugelabs.com/api/2/%s/%s/", api_key, word);
        URL url = new URL(inter);
        fetchThesaurus fetch2 = new fetchThesaurus(url);
        try {
            toReturn = fetch2.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // Check to see if the thesaurus returned any information close to the watson description
        for (int i = 0; i < toReturn.size(); i++) {
            if (tags.contains(toReturn.get(i))) {
                System.out.println("True");
            }
        }
    }


//    private boolean isMediaAvailable() {
//
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    public void tags() {
//
//        if (!isMediaAvailable()) {
//            Utility.finishWithError(this, "Media Not Available");
//        } else {
//
//            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + Environment.DIRECTORY_DOWNLOADS;
//
//
//            File file = new File(path);
//            mRootPath = file.getAbsoluteFile().getPath();
//
//
//            mFileNames = new ArrayList<String>();
//
//            File filesInDirectory[] = file.listFiles();
//
//
//            if (filesInDirectory != null) {
//                for (int i = 0; i < filesInDirectory.length; i++) {
//                    mFileNames.add(filesInDirectory[i].getName());
//                }
//            }
//
//        }
//    }

//    private class listener implements RecognitionListener {
//        public void onReadyForSpeech(Bundle params) {
//            //Log.d(TAG, "onReadyForSpeech");
//        }
//
//        public void onBeginningOfSpeech() {
//            //Log.d(TAG, "onBeginningOfSpeech");
//        }
//
//        public void onRmsChanged(float rmsdB) {
//            //Log.d(TAG, "onRmsChanged");
//        }
//
//        public void onBufferReceived(byte[] buffer) {
//            //Log.d(TAG, "onBufferReceived");
//        }
//
//        public void onEndOfSpeech() {
//            //Log.d(TAG, "onEndofSpeech");
//        }
//
//        public void onError(int error) {
//            //Log.d(TAG, "error " + error);
//            //mText.setText("error " + error);
//        }
//
//        public void onResults(Bundle results) {
//            String str = "";
//            //Log.d(TAG, "onResults " + results);
//            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
//            for (int i = 0; i < data.size(); i++) {
//                //Log.d(TAG, "result " + data.get(i));
//                str += data.get(i);
//            }
//            mText.setText("results: " + String.valueOf(data.size()));
//        }
//
//        public void onPartialResults(Bundle partialResults) {
//            //Log.d(TAG, "onPartialResults");
//        }
//
//        public void onEvent(int eventType, Bundle params) {
//            //Log.d(TAG, "onEvent " + eventType);
//        }
//    }

}

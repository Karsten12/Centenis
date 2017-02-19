package giraffe.fonsecakarsten.com.giraffe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtSDK;
    Button btnSelectImage, btnSelectImage2;
    TextView txtUriPath, txtRealPath;
    ImageView imageView;
    String state = Environment.getExternalStorageState();
    ArrayList<String> tags;
    private SpeechRecognizer sr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
        btnSelectImage2 = (Button) findViewById(R.id.btnSelectImage2);
        imageView = (ImageView) findViewById(R.id.imgView);
        TextView mText = (TextView) findViewById(R.id.printText);

        // add click listener to button
        btnSelectImage.setOnClickListener(this);
        btnSelectImage2.setOnClickListener(this);
        sr = SpeechRecognizer.createSpeechRecognizer(this);
        sr.setRecognitionListener(new listener());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSelectImage:
                // 1. on Upload click call ACTION_GET_CONTENT intent
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                // 2. pick image only
                intent.setType("image/*");
                // 3. start activity
                startActivityForResult(intent, 0);
                break;

            case R.id.btnSelectImage2:
                if (tags.size() != 0) {
                    Intent intent1 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent1.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "voice.recognition.test");
                    intent1.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                    sr.startListening(intent1);
                    Log.i("111111", "11111111");
                }
                break;
            default:
                break;
        }
    }


    private boolean isMediaAvailable() {

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else {
            return false;
        }
    }

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


    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == Activity.RESULT_OK && data != null) {

            String realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
            FetchWatson task = new FetchWatson(this, realPath);
            try {
                tags = task.execute().get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            imageView.setImageURI(data.getData());

        }
    }

    class listener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            //Log.d(TAG, "onReadyForSpeech");
        }

        public void onBeginningOfSpeech() {
            //Log.d(TAG, "onBeginningOfSpeech");
        }

        public void onRmsChanged(float rmsdB) {
            //Log.d(TAG, "onRmsChanged");
        }

        public void onBufferReceived(byte[] buffer) {
            //Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            //Log.d(TAG, "onEndofSpeech");
        }

        public void onError(int error) {
            //Log.d(TAG, "error " + error);
            //mText.setText("error " + error);
        }

        public void onResults(Bundle results) {
            String str = "";
            //Log.d(TAG, "onResults " + results);
            ArrayList data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for (int i = 0; i < data.size(); i++) {
                //Log.d(TAG, "result " + data.get(i));
                str += data.get(i);
            }
            System.out.println(str);

            String[] tag2 = String.valueOf(data.size()).split(" ");
            //mText.setText("results: " + String.valueOf(data.size()));
            if (tag2.length != 0) {
                for (int i = 0; i < tag2.length; i++) {
//                    if (tags.contains(tag2[0])) {
//
//                    }
                }
            }
        }

        public void onPartialResults(Bundle partialResults) {
            //Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            //Log.d(TAG, "onEvent " + eventType);
        }
    }

}

package giraffe.fonsecakarsten.com.giraffe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import siclo.com.ezphotopicker.api.EZPhotoPick;
import siclo.com.ezphotopicker.api.EZPhotoPickStorage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


//        EZPhotoPickConfig config = new EZPhotoPickConfig();
//        config.photoSource = PhotoSource.GALERY;
//        //config.maxExportingSize = 1000;
//        EZPhotoPick.startPhotoPickActivity(this, config);
        VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey("{bd8ca3f2243490e58d837849484862d2ba777df6}");

        Context context = getApplicationContext();
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
                .images(new File("src/main/res/drawable/giraffetall.jpg"))
                .build();
        VisualClassification result = service.classify(options).execute();
        System.out.println(result);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == EZPhotoPick.PHOTO_PICK_REQUEST_CODE) {
            try {
                Bitmap pickedPhoto = new EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap(300);

                File f = new File(this.getCacheDir(), "idk");

                f.createNewFile();

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                pickedPhoto.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                byte[] bitmapdata = bos.toByteArray();

                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();

//                VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
//                service.setApiKey("{bd8ca3f2243490e58d837849484862d2ba777df6}");
//
//                Context context = getApplicationContext();
//                CharSequence text = "Hello toast!";
//                int duration = Toast.LENGTH_SHORT;
//
//                Toast toast = Toast.makeText(context, text, duration);
//                toast.show();
//                ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
//                        .images(new File("src/main/res/drawable/giraffetall.jpg"))
//                        .build();
//                VisualClassification result = service.classify(options).execute();
//                //System.out.println(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

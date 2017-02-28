package centenis.fonsecakarsten.com.centenis;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private ArrayList<String> tags;
    private EditText mText;

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
                if (str1.length() != 0) {
                    getThesaurus(str1);
                } else {
                    Toast toast = Toast.makeText(this, "Please make sure to first choose an image and then enter a guess", Toast.LENGTH_SHORT);
                    toast.show();
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


    private void getThesaurus(String word) {
        ArrayList<String> relatedWords = new ArrayList<>();
        fetchThesaurus fetchThesaurus = new fetchThesaurus(word);
        try {
            relatedWords = fetchThesaurus.execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        // Check to see if the thesaurus returned any information close to the watson description
        if (relatedWords.size() != 0) {
            for (int i = 0; i < relatedWords.size(); i++) {
                if (tags.contains(relatedWords.get(i))) {
                    System.out.println("True");
                }
            }
        }
        // If true, display green check mark or something?
        // Move onto next picture

    }

}

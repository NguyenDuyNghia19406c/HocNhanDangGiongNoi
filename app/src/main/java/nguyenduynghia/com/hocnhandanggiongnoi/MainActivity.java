package nguyenduynghia.com.hocnhandanggiongnoi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import nguyenduynghia.com.hocnhandanggiongnoi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    HashMap<String,String>dictionary=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        makeDictionary();
        addEvents();
    }

    private void makeDictionary() {
        dictionary.put("school","trường học");
        dictionary.put("university","đại học");
        dictionary.put("student","sinh viên");
        dictionary.put("beautiful","đẹp");
        dictionary.put("international","quốc tế");
    }

    private void addEvents() {
        binding.btnVoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyGiongNoiGoogleAI();
            }
        });

        binding.btnTao1Tu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taoTuNgauNhien();
            }
        });

        binding.btnCach2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GoogleRecognitionWithoutDialogActivity.class);
                startActivity(intent);
            }
        });


    }

    private void taoTuNgauNhien() {
        Random rd=new Random();
        int index=rd.nextInt(dictionary.size());
        String word= (String) dictionary.keySet().toArray()[index];
        binding.txtTao.setText(word);
    }

    private void xuLyGiongNoiGoogleAI() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"vi_VN");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi_VN");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
               "Talk some thing");
        try {
            startActivityForResult(intent, 113);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "This phone is not support Google AI",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==113&&requestCode==RESULT_OK){

            //gg trar về 1 mảng các chuỗi âm thanh mà nó phân tích ra đc
            //thường phần tử đầu tiên là đúng nhất
            //
            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            binding.txtSpeechToText.setText(result.get(0));
        }
    }
}

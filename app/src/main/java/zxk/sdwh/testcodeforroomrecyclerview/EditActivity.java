package zxk.sdwh.testcodeforroomrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {
    private Button button;
    private EditText editTextNumber,editTextEnglish;
    private WordViewModel wordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        final int id = intent.getIntExtra("id",-1);
        wordViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(WordViewModel.class);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextEnglish = findViewById(R.id.editTextEnglish);
        button = findViewById(R.id.button);
        if (id != -1){
            WordDatabase.getDatabase(this).getWordDao().getMyId(id).observe(this, new Observer<Word>() {
                @Override
                public void onChanged(Word word) {
                    Log.e("dsd",word.getEnglish());
                    editTextNumber.setText(String.valueOf(word.getNumber()));
                    editTextEnglish.setText(word.getEnglish());
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Word word = new Word(Integer.parseInt(editTextNumber.getText().toString()), editTextEnglish.getText().toString());
                    word.setId(id);
                    wordViewModel.updateWord(word);
                    finish();
                }
            });
        }else {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Word word = new Word(Integer.parseInt(editTextNumber.getText().toString()), editTextEnglish.getText().toString());
                    wordViewModel.insertWord(word);
                    finish();
                }
            });
        }
    }

}

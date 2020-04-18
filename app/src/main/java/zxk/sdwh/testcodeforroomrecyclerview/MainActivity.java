package zxk.sdwh.testcodeforroomrecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonInsert,buttonUpdate,buttonDelete,buttonDeleteAll;
    private WordViewModel wordViewModel;
    private RecyclerView recyclerView;
    private WordRecyclerViewAdapter wordRecyclerViewAdapter;
    private int x;
    private int y;
    private SwipeRefreshLayout swipeRefreshLayout;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定recyclerView
        recyclerView = findViewById(R.id.recycler_view);
        wordRecyclerViewAdapter = new WordRecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(wordRecyclerViewAdapter);
        //使用ViewModel
        wordViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(WordViewModel.class);
        wordViewModel.getAllWordLive().observe(this, new Observer<List<Word>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(final List<Word> words) {
                    //recyclerView.addItemDecoration(new ItemDirection(MainActivity.this,words));
                wordRecyclerViewAdapter.setAllWord(words);
                //当ItemDecoration已经添加了一个的时候，为了防止重复绘制，移除掉旧数据的，同时继续给新的数据设置Decoration，新的就变为了
                //0，如果数据再次改变刷新，那么就移除0.再添加
                if (recyclerView.getItemDecorationCount()!=0){
                    recyclerView.removeItemDecorationAt(0);
                }
                    recyclerView.addItemDecoration(new ItemDirection(MainActivity.this, new ItemDirection.DecorationCallback() {
                        @Override
                        public int getGroupId(int position) {
                            Log.e("xxx", String.valueOf(x++));
                            return words.get(position).getNumber();

                        }
                        @Override
                        public String getGroupFirstLine(int position) {
                            Log.e("yyyy", String.valueOf(y++));
                            return String.valueOf(words.get(position).getNumber());
                        }
                    }));
                wordRecyclerViewAdapter.notifyDataSetChanged();
            }
        });
        swipeRefreshLayout = findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(MainActivity.this,EditActivity.class);
                        startActivity(intent);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },10);
            }
        });

        buttonInsert = findViewById(R.id.button_insert);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);
        buttonDeleteAll = findViewById(R.id.button_delete_all);
        //插入模拟数据
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<4;i++) {
                    Word word = new Word(i, "a"+i);
                    wordViewModel.insertWord(word);
                }
                for (int i=3;i<7;i++) {
                    Word word = new Word(i, "a"+i);
                    wordViewModel.insertWord(word);
                }
                for (int i=6;i<8;i++) {
                    Word word = new Word(i, "a"+i);
                    wordViewModel.insertWord(word);
                }

            }
        });
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordViewModel.deleteAllWord();
            }
        });
        //处理item的点击和长按事件
        wordRecyclerViewAdapter.setOnClickListener(new WordRecyclerViewAdapter.OnClickListener() {
            @Override
            public void OnClicked(View view, int position) {
                int id = (int) view.getTag(R.id.id);
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
            @Override
            public void OnLongClicked(View view, int position) {
                int id = (int) view.getTag(R.id.id);
                int number = (int) view.getTag(R.id.number);
                String english = (String) view.getTag(R.id.english);
                Word word = new Word(number,english);
                Toast.makeText(MainActivity.this,"你已经删除了"+ number+english,Toast.LENGTH_SHORT).show();
                word.setId(id);
                wordViewModel.deleteWord(word);
            }
        });
    }
}

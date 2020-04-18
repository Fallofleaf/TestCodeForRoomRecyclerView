package zxk.sdwh.testcodeforroomrecyclerview;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {
    private LiveData<List<Word>> allWordLive;


    private WordDao wordDao;
    WordRepository(Context context){
        WordDatabase wordDatabase = WordDatabase.getDatabase(context.getApplicationContext());
        wordDao = wordDatabase.getWordDao();
        allWordLive = wordDao.getAllWord();
    }
    LiveData<List<Word>> getAllWordLive(){ return allWordLive; }



    void insertWord(Word... words){new InsertAsyncTask(wordDao).execute(words);}
    void updatetWord(Word... words){new updateAsyncTask(wordDao).execute(words);}
    void deleteWord(Word... words){new deleteAsyncTask(wordDao).execute(words);}
    void deleteAllWord(Word... words){new deleteAllAsyncTask(wordDao).execute();}


    static class InsertAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;
        InsertAsyncTask(WordDao wordDao){this.wordDao = wordDao;}
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.insertWord(words);
            return null;
        }
    }
    static class updateAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;
        updateAsyncTask(WordDao wordDao){this.wordDao = wordDao;}
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.updatetWord(words);
            return null;
        }
    }
    static class deleteAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao wordDao;
        deleteAsyncTask(WordDao wordDao){this.wordDao = wordDao;}
        @Override
        protected Void doInBackground(Word... words) {
            wordDao.deletetWord(words);
            return null;
        }
    }
    static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void>{
        private WordDao wordDao;
        deleteAllAsyncTask(WordDao wordDao){this.wordDao = wordDao;}
        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWord();
            return null;
        }
    }
}

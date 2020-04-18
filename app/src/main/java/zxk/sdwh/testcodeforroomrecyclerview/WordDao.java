package zxk.sdwh.testcodeforroomrecyclerview;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    @Insert
    void insertWord(Word... words);
    @Update
    void updatetWord(Word... words);
    @Delete
    void deletetWord(Word... words);
    @Query("SELECT * FROM WORD ORDER BY number DESC")
    LiveData<List<Word>> getAllWord();
    @Query("DELETE FROM Word")
    void deleteAllWord();
    @Query("SELECT * FROM WORD WHERE id = :myId")
    LiveData<Word> getMyId(int myId);

}

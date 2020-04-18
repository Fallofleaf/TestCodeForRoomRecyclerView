package zxk.sdwh.testcodeforroomrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class WordRecyclerViewAdapter extends RecyclerView.Adapter<WordRecyclerViewAdapter.MyViewHolder> {

    List<Word> allWord = new ArrayList<>();


    private OnClickListener onClickListener;
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setAllWord(List<Word> allWord) {
        this.allWord = allWord;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_content,parent,false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Word word=allWord.get(position);
        holder.itemContentNumber.setText(String.valueOf(word.getNumber()));
        holder.itemContentEnglish.setText(word.getEnglish());
        holder.itemView.setTag(R.id.id, word.getId());
        holder.itemView.setTag(R.id.number, word.getNumber());
        holder.itemView.setTag(R.id.english, word.getEnglish());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.OnClicked(holder.itemView,position);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onClickListener.OnLongClicked(holder.itemView,position);
                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        return allWord.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView itemContentNumber;
        TextView itemContentEnglish;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemContentNumber = itemView.findViewById(R.id.item_content_number);
            itemContentEnglish = itemView.findViewById(R.id.item_content_english);
        }
    }
    public interface OnClickListener{
        void OnClicked(View view,int position);
        void OnLongClicked(View view,int position);
    }
}
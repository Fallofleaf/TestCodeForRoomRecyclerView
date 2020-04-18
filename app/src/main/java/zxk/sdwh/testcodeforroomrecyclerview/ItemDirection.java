package zxk.sdwh.testcodeforroomrecyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ItemDirection extends RecyclerView.ItemDecoration {
    private static final String TAG = "SectionDecoration";

    private TextPaint textPaint;
    private Paint paint;
    private int topGap;
    private Paint.FontMetrics fontMetrics;
    private DecorationCallback callback;


    public ItemDirection(Context context, DecorationCallback decorationCallback) {
        Resources res = context.getResources();
        this.callback= decorationCallback;

        paint = new Paint();
        paint.setColor(res.getColor(R.color.colorAccent));

        textPaint = new TextPaint();
        //textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        //textPaint.setAntiAlias(true);
        textPaint.setTextSize(80);
        //textPaint.setColor(Color.BLACK);
        //textPaint.getFontMetrics(fontMetrics);
        //textPaint.setTextAlign(Paint.Align.LEFT);
        //fontMetrics = new Paint.FontMetrics();
        topGap = res.getDimensionPixelSize(R.dimen.threeTwo);//32dp

    }



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        Log.i(TAG, "getItemOffsets：" + position);
        //int groupId = callback.getGroupId(position);
        //if (groupId < 0) return;
        if (position == 0 || isFirstInGroup(position)) {//同组的第一个才添加padding
            outRect.top = topGap;
        } else {
            outRect.top = 0;
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        int x =0;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(view);
            int groupId = callback.getGroupId(position);
            if (groupId < 0) return;





            String textLine = String.valueOf(callback.getGroupFirstLine(position));
            if (position == 0 || isFirstInGroup(position)) {
                float top = view.getTop() - topGap;
                float bottom = view.getTop();
                c.drawRect(left, top, right, bottom, paint);//绘制矩形
                c.drawText(textLine, left, bottom, textPaint);//绘制文本
            }
        }
    }

    private boolean isFirstInGroup(int position) {
        if (position == 0) {
            return true;
        } else {
            int prevGroupId = callback.getGroupId(position - 1);
            int groupId = callback.getGroupId(position);
            return prevGroupId != groupId;
        }
    }

    public interface DecorationCallback {
        int getGroupId(int position);
        String getGroupFirstLine(int position);
    }
}
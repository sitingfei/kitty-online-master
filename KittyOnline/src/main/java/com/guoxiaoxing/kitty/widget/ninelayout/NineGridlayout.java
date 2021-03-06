package com.guoxiaoxing.kitty.widget.ninelayout;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


/**
 * 九宫格图片布局
 *
 * @author guoxiaoxing
 */
public class NineGridlayout extends ViewGroup {

    private int columns;
    private int rows;
    /*图片之间的间隔*/
    private int gap = 5;
    /*屏幕总宽度*/
    private int totalWidth;
    /*图片数据*/
    private List<String> listData;

    public NineGridlayout(Context context) {
        super(context);
    }

    public NineGridlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        ScreenTools screenTools = ScreenTools.instance(getContext());
        totalWidth = screenTools.getScreenWidth() - screenTools.dip2px(30);//去掉屏幕两边的留白
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    /**
     * 根据行列数量计算图片长宽（图片长宽相等）
     * 对应关系如下
     * num	row	column
     * 1	   1	1
     * 2	   1	2
     * 3	   1	3
     * 4	   2	2
     * 5	   2	3
     * 6	   2	3
     * 7	   3	3
     * 8	   3	3
     * 9	   3	3
     */
    private void layoutChildrenView() {

        int childrenCount = listData.size();

        int singleWidth = 0;
        switch (columns) {
            case 1:
                singleWidth = totalWidth;
                break;
            case 2:
                singleWidth = (totalWidth - gap) / 2;
                break;
            case 3:
                singleWidth = (totalWidth - gap * (3 - 1)) / 3;
                break;
        }

        int singleHeight = singleWidth;

        //根据子view数量确定高度
        LayoutParams params = getLayoutParams();
        params.height = singleHeight * rows + gap * (rows - 1);
        setLayoutParams(params);

        for (int i = 0; i < childrenCount; i++) {
            SimpleDraweeView childrenView = (SimpleDraweeView) getChildAt(i);
            childrenView.setImageURI(Uri.parse(listData.get(i)));
            int[] position = findPosition(i);
            int left = (singleWidth + gap) * position[1];
            int top = (singleHeight + gap) * position[0];
            int right = left + singleWidth;
            int bottom = top + singleHeight;
            childrenView.layout(left, top, right, bottom);
        }

    }


    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if ((i * columns + j) == childNum) {
                    position[0] = i;//行
                    position[1] = j;//列
                    break;
                }
            }
        }
        return position;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }


    public void setImagesData(ArrayList<String> lists) {
        if (lists == null || lists.isEmpty()) {
            return;
        }
        //初始化布局
        generateChildrenLayout(lists.size());
        //这里做一个重用view的处理
        if (listData == null) {
            int i = 0;
            while (i < lists.size()) {
                SimpleDraweeView simpleDraweeView = generateImageView();
                addView(simpleDraweeView, generateDefaultLayoutParams());
                i++;
            }
        } else {
            int oldViewCount = listData.size();
            int newViewCount = lists.size();
            if (oldViewCount > newViewCount) {
                removeViews(newViewCount - 1, oldViewCount - newViewCount);
            } else if (oldViewCount < newViewCount) {
                for (int i = 0; i < newViewCount - oldViewCount; i++) {
                    SimpleDraweeView simpleDraweeView = generateImageView();
                    addView(simpleDraweeView, generateDefaultLayoutParams());
                }
            }
        }
        listData = lists;
        layoutChildrenView();
    }


    /**
     * 根据图片个数确定行列数量
     * 对应关系如下
     * num	row	column
     * 1	   1	1
     * 2	   1	2
     * 3	   1	3
     * 4	   2	2
     * 5	   2	3
     * 6	   2	3
     * 7	   3	3
     * 8	   3	3
     * 9	   3	3
     *
     * @param length
     */
    private void generateChildrenLayout(int length) {
        if (length <= 3) {
            rows = 1;
            columns = length;
        } else if (length <= 6) {
            rows = 2;
            columns = 3;
            if (length == 4) {
                columns = 2;
            }
        } else {
            rows = 3;
            columns = 3;
        }
    }

    private SimpleDraweeView generateImageView() {
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getContext());
        simpleDraweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        simpleDraweeView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        simpleDraweeView.setBackgroundColor(Color.parseColor("#f5f5f5"));
        return simpleDraweeView;
    }


}

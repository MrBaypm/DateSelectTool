package com.datepicker.view;

import android.view.View;

import com.datepicker.R;
import com.datepicker.adapter.ArrayWheelAdapter;
import com.datepicker.lib.WheelView;
import com.datepicker.listener.OnItemSelectedListener;

import java.util.List;


public class WheelList {

    private View view;
    private WheelView wheelView;
    private int gravity;

    private boolean[] type;

    List<String> items;
    private int currentItem;


    // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
    private int textSize = 18;
    //文字的颜色和分割线的颜色
    int textColorOut;
    int textColorCenter;
    int dividerColor;
    // 条目间距倍数
    float lineSpacingMultiplier = 1.6F;

    private float mLineHeight=100.0f;

    private WheelView.DividerType dividerType;

    public WheelList(View view) {
        super();
        this.view = view;
        type = new boolean[]{true, true, true, true, true, true};
        setView(view);
    }

    public WheelList(View view, boolean[] type, int gravity, int textSize) {
        super();
        this.view = view;
        this.type = type;
        this.gravity = gravity;
        this.textSize = textSize;
        setView(view);
    }

    public void setData(List<String> _items) {

        items = _items;

        wheelView = (WheelView) view.findViewById(R.id.wheelView);
        wheelView.setAdapter(new ArrayWheelAdapter(items));// 设置"年"的显示数据
        wheelView.setCurrentItem(0);// 初始化时显示的数据
        wheelView.setGravity(gravity);

        OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                currentItem = index;
            }
        };

        wheelView.setOnItemSelectedListener(wheelListener_year);

        if (type.length != 6) {
            throw new RuntimeException("type[] length is not 6");
        }
        wheelView.setVisibility(type[0] ? View.VISIBLE : View.GONE);

        setContentTextSize();
    }

    private void setContentTextSize() {
        wheelView.setTextSize(textSize);
    }

    private void setTextColorOut() {
        wheelView.setTextColorOut(textColorOut);
    }

    private void setTextColorCenter() {
        wheelView.setTextColorCenter(textColorCenter);
    }

    private void setDividerColor() {
        wheelView.setDividerColor(dividerColor);
    }

    private void setDividerType() {
        wheelView.setDividerType(dividerType);
    }

    private void setLineSpacingMultiplier() {
        wheelView.setLineSpacingMultiplier(lineSpacingMultiplier);
    }


    private void setItemHeight() {
        wheelView.setLineHeight(mLineHeight);
    }

    public void setLabels(String label_year) {
        if (label_year != null) {
            wheelView.setLabel(label_year);
        }
    }


    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelView.setCyclic(cyclic);
    }


    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }



    /**
     * 设置行高
     * @param lineHeight
     */
    public void setLineHeight(float lineHeight) {
        this.mLineHeight = lineHeight;
        setItemHeight();
    }

    /**
     * 设置间距倍数,但是只能在1.0-2.0f之间
     *
     * @param lineSpacingMultiplier
     */
    public void setLineSpacingMultiplier(float lineSpacingMultiplier) {
        this.lineSpacingMultiplier = lineSpacingMultiplier;
        setLineSpacingMultiplier();
    }

    /**
     * 设置分割线的颜色
     *
     * @param dividerColor
     */
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        setDividerColor();
    }

    /**
     * 设置分割线的类型
     *
     * @param dividerType
     */
    public void setDividerType(WheelView.DividerType dividerType) {
        this.dividerType = dividerType;
        setDividerType();
    }

    /**
     * 设置分割线之间的文字的颜色
     *
     * @param textColorCenter
     */
    public void setTextColorCenter(int textColorCenter) {
        this.textColorCenter = textColorCenter;
        setTextColorCenter();
    }

    /**
     * 设置分割线以外文字的颜色
     *
     * @param textColorOut
     */
    public void setTextColorOut(int textColorOut) {
        this.textColorOut = textColorOut;
        setTextColorOut();
    }

    /**
     * Label 是否只显示中间选中项的
     *
     * @param isCenterLabel
     */

    public void isCenterLabel(Boolean isCenterLabel) {
        wheelView.isCenterLabel(isCenterLabel);
    }

    public String getItem() {
        return currentItem+"";
    }

    public void setCurrentItem(int mCurrentItem) {
        this.currentItem=mCurrentItem;
        setListCurrentItem();
    }

    private void setListCurrentItem() {
        wheelView.setCurrentItem(currentItem);
    }
}

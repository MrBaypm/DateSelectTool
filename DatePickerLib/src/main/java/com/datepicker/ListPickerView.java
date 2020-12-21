package com.datepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.datepicker.lib.WheelView;
import com.datepicker.listener.CustomListener;
import com.datepicker.view.BasePickerView;
import com.datepicker.view.WheelList;

import java.util.List;

/**
 * 列表选择器
 */
public class ListPickerView extends BasePickerView implements View.OnClickListener {

    private int layoutRes;
    private CustomListener customListener;

    WheelList wheelList; //自定义控件
    private Button btnSubmit, btnCancel; //确定、取消按钮
    private TextView tvTitle;//标题
    private OnItemSelectListener itemSelectListener;//回调接口
    private int gravity = Gravity.CENTER;//内容显示位置 默认居中
    private boolean[] type;// 显示类型

    private String Str_Submit;//确定按钮字符串
    private String Str_Cancel;//取消按钮字符串
    private String Str_Title;//标题字符串

    private int Color_Submit;//确定按钮颜色
    private int Color_Cancel;//取消按钮颜色
    private int Color_Title;//标题颜色

    private int Color_Background_Wheel;//滚轮背景颜色
    private int Color_Background_Title=-1;//标题背景颜色
    private String colorWheelBgColor;  // wheelview背景颜色

    private int Size_Submit_Cancel;//确定取消按钮大小
    private int Size_Title;//标题字体大小
    private int Size_Content;//内容字体大小


    private boolean cyclic;//是否循环
    private boolean cancelable;//是否能取消
    private boolean isCenterLabel;//是否只显示中间的label

    private int textColorOut; //分割线以外的文字颜色
    private int textColorCenter; //分割线之间的文字颜色
    private int dividerColor; //分割线的颜色
    private int backgroundId; //显示时的外部背景色颜色,默认是灰色

    // 条目间距倍数 默认1.6
    private float lineSpacingMultiplier = 1.6F;
    private float mItemHeight=100.0f;
    private boolean isDialog;//是否是对话框模式
    private String label_item;
    private List<String> mItems;
    private int mCurrentItem=0;

    private WheelView.DividerType dividerType;//分隔线类型

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    //构造方法
    public ListPickerView(Builder builder) {
        super(builder.context);
        this.itemSelectListener = builder.itemSelectListener;
        this.gravity = builder.gravity;
        this.type = builder.type;
        this.Str_Submit = builder.Str_Submit;
        this.Str_Cancel = builder.Str_Cancel;
        this.Str_Title = builder.Str_Title;
        this.Color_Submit = builder.Color_Submit;
        this.Color_Cancel = builder.Color_Cancel;
        this.Color_Title = builder.Color_Title;
        this.Color_Background_Wheel = builder.Color_Background_Wheel;
        this.Color_Background_Title = builder.Color_Background_Title;
        this.Size_Submit_Cancel = builder.Size_Submit_Cancel;
        this.Size_Title = builder.Size_Title;
        this.Size_Content = builder.Size_Content;

        this.cyclic = builder.cyclic;
        this.isCenterLabel = builder.isCenterLabel;
        this.cancelable = builder.cancelable;
        this.label_item = builder.label_item;
        this.mItems=builder.mItems;
        this.mCurrentItem=builder.currentItem;

        this.textColorCenter = builder.textColorCenter;
        this.textColorOut = builder.textColorOut;
        this.dividerColor = builder.dividerColor;
        this.customListener = builder.customListener;
        this.layoutRes = builder.layoutRes;
        this.lineSpacingMultiplier = builder.lineSpacingMultiplier;
        this.mItemHeight=builder.lineHeight;
        this.isDialog = builder.isDialog;
        this.dividerType = builder.dividerType;
        this.backgroundId = builder.backgroundId;
        this.decorView = builder.decorView;
        this.colorWheelBgColor=builder.colorWheelBgColor;
        initView(builder.context);
    }


    //建造器
    public static class Builder {
        private int layoutRes = R.layout.pickerview_time;
        private CustomListener customListener;
        private Context context;
        private OnItemSelectListener itemSelectListener;
        private boolean[] type = new boolean[]{true, true, true, false, false, false};//显示类型 默认全部年月日
        private int gravity = Gravity.CENTER;//内容显示位置 默认居中

        private String Str_Submit;//确定按钮文字
        private String Str_Cancel;//取消按钮文字
        private String Str_Title;//标题文字

        private int Color_Submit;//确定按钮颜色
        private int Color_Cancel;//取消按钮颜色
        private int Color_Title;//标题颜色

        private int Color_Background_Wheel;//滚轮背景颜色
        private int Color_Background_Title;//标题背景颜色
        private String colorWheelBgColor;// wheelview 背景颜色

        private int Size_Submit_Cancel = 17;//确定取消按钮大小
        private int Size_Title = 18;//标题字体大小
        private int Size_Content = 18;//内容字体大小

        private String label_item;

        private List<String> mItems;
        private int currentItem;


        private boolean cyclic = false;//是否循环
        private boolean cancelable = true;//是否能取消

        private boolean isCenterLabel = true ;//是否只显示中间的label
        public ViewGroup decorView ;//显示pickerview的根View,默认是activity的根view

        private int textColorOut; //分割线以外的文字颜色
        private int textColorCenter; //分割线之间的文字颜色
        private int dividerColor; //分割线的颜色
        private int backgroundId; //显示时的外部背景色颜色,默认是灰色
        private WheelView.DividerType dividerType;//分隔线类型
        // 条目间距倍数 默认1.6
        private float lineSpacingMultiplier = 1.6F;
        private float lineHeight = 100.0f;

        private boolean isDialog;//是否是对话框模式


        //Required
        public Builder(Context context, OnItemSelectListener listener) {
            this.context = context;
            this.itemSelectListener = listener;
        }

        //Option
        public Builder setType(boolean[] type) {
            this.type = type;
            return this;
        }

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setSubmitText(String Str_Submit) {
            this.Str_Submit = Str_Submit;
            return this;
        }

        public Builder isDialog(boolean isDialog) {
            this.isDialog = isDialog;
            return this;
        }

        public Builder setCancelText(String Str_Cancel) {
            this.Str_Cancel = Str_Cancel;
            return this;
        }

        public Builder setTitleText(String Str_Title) {
            this.Str_Title = Str_Title;
            return this;
        }

        public Builder setSubmitColor(int Color_Submit) {
            this.Color_Submit = Color_Submit;
            return this;
        }

        public Builder setCancelColor(int Color_Cancel) {
            this.Color_Cancel = Color_Cancel;
            return this;
        }
        /**
         * 必须是viewgroup
         * 设置要将pickerview显示到的容器id
         * @param decorView
         * @return
         */
        public Builder setDecorView(ViewGroup decorView) {
            this.decorView = decorView;
            return this;
        }

        public Builder setBgColor(int Color_Background_Wheel) {
            this.Color_Background_Wheel = Color_Background_Wheel;
            return this;
        }

        public Builder setWheelViewBgColor(String  wheelBgColor) {
            this.colorWheelBgColor = wheelBgColor;
            return this;
        }

        /**
         * shape 格式的drawable
         * @param drawResId
         * @return
         */
        public Builder setTitleBgColor(int drawResId) {
            this.Color_Background_Title = drawResId;
            return this;
        }

        public Builder setTitleColor(int Color_Title) {
            this.Color_Title = Color_Title;
            return this;
        }

        public Builder setData(List<String> items) {
            this.mItems = items;
            return this;
        }

        public Builder setSubCalSize(int Size_Submit_Cancel) {
            this.Size_Submit_Cancel = Size_Submit_Cancel;
            return this;
        }

        public Builder setTitleSize(int Size_Title) {
            this.Size_Title = Size_Title;
            return this;
        }

        public Builder setContentSize(int Size_Content) {
            this.Size_Content = Size_Content;
            return this;
        }



        public Builder setLayoutRes(int res, CustomListener customListener) {
            this.layoutRes = res;
            this.customListener = customListener;
            return this;
        }


        public Builder setLineHeigth(float lineHeigth) {
            this.lineHeight = lineHeigth;
            return this;
        }


        /**
         * 设置间距倍数,但是只能在1.2-2.0f之间
         *
         * @param lineSpacingMultiplier
         */
        public Builder setLineSpacingMultiplier(float lineSpacingMultiplier) {
            this.lineSpacingMultiplier = lineSpacingMultiplier;
            return this;
        }

        /**
         * 设置分割线的颜色
         *
         * @param dividerColor
         */
        public Builder setDividerColor(int dividerColor) {
            this.dividerColor = dividerColor;
            return this;
        }

        /**
         * 设置分割线的类型
         *
         * @param dividerType
         */
        public Builder setDividerType(WheelView.DividerType dividerType) {
            this.dividerType = dividerType;
            return this;
        }

        /**
         * //显示时的外部背景色颜色,默认是灰色
         * @param backgroundId
         */

        public Builder setBackgroundId(int backgroundId) {
            this.backgroundId = backgroundId;
            return this;
        }

        /**
         * 设置分割线之间的文字的颜色
         *
         * @param textColorCenter
         */
        public Builder setTextColorCenter(int textColorCenter) {
            this.textColorCenter = textColorCenter;
            return this;
        }

        /**
         * 设置分割线以外文字的颜色
         *
         * @param textColorOut
         */
        public Builder setTextColorOut(int textColorOut) {
            this.textColorOut = textColorOut;
            return this;
        }

        public Builder isCyclic(boolean cyclic) {
            this.cyclic = cyclic;
            return this;
        }

        public Builder setOutSideCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }


        public Builder setCurrentItem(int currentItemAtIdx){
            this.currentItem = currentItemAtIdx;
            return this;
        }

        public Builder setLabel(String label_item) {
            this.label_item = label_item;
            return this;
        }

        public Builder isCenterLabel(boolean isCenterLabel) {
            this.isCenterLabel = isCenterLabel;
            return this;
        }


        public ListPickerView build() {
            return new ListPickerView(this);
        }
    }


    @SuppressLint("NewApi")
    private void initView(Context context) {
        setDialogOutSideCancelable(cancelable);
        initViews(backgroundId);
        init();
        initEvents();
        if (customListener == null) {
            LayoutInflater.from(context).inflate(R.layout.pickerview_list, contentContainer);

            //顶部标题
            tvTitle = (TextView) findViewById(R.id.tvTitle);

            //确定和取消按钮
            btnSubmit = (Button) findViewById(R.id.btnSubmit);
            btnCancel = (Button) findViewById(R.id.btnCancel);

            btnSubmit.setTag(TAG_SUBMIT);
            btnCancel.setTag(TAG_CANCEL);

            btnSubmit.setOnClickListener(this);
            btnCancel.setOnClickListener(this);

            //设置文字
            btnSubmit.setText(TextUtils.isEmpty(Str_Submit) ? context.getResources().getString(R.string.pickerview_submit) : Str_Submit);
            btnCancel.setText(TextUtils.isEmpty(Str_Cancel) ? context.getResources().getString(R.string.pickerview_cancel) : Str_Cancel);
            tvTitle.setText(TextUtils.isEmpty(Str_Title) ? "" : Str_Title);//默认为空

            //设置文字颜色
            btnSubmit.setTextColor(Color_Submit == 0 ? pickerview_timebtn_nor : Color_Submit);
            btnCancel.setTextColor(Color_Cancel == 0 ? pickerview_timebtn_nor : Color_Cancel);
            tvTitle.setTextColor(Color_Title == 0 ? pickerview_topbar_title : Color_Title);

            //设置文字大小
            btnSubmit.setTextSize(Size_Submit_Cancel);
            btnCancel.setTextSize(Size_Submit_Cancel);
            tvTitle.setTextSize(Size_Title);
            RelativeLayout rv_top_bar = (RelativeLayout) findViewById(R.id.rv_topbar);
            if (Color_Background_Title==-1){
                rv_top_bar.setBackground(context.getDrawable(R.drawable.pickerview_bg_topbar));
            }else{
                rv_top_bar.setBackground(context.getDrawable(Color_Background_Title));
            }
        } else {
            customListener.customLayout(LayoutInflater.from(context).inflate(layoutRes, contentContainer));
        }
        // 时间转轮 自定义控件
        LinearLayout timePickerView = (LinearLayout) findViewById(R.id.timepicker);

        if (!TextUtils.isEmpty(colorWheelBgColor)){
            timePickerView.setBackgroundColor(Color.parseColor(colorWheelBgColor));
        }else{
            timePickerView.setBackgroundColor(Color.parseColor("#181a22"));
        }

        wheelList = new WheelList(timePickerView, type, gravity, Size_Content);
        setData(mItems);

        wheelList.setLabels(label_item);
        setOutSideCancelable(cancelable);
        wheelList.setCyclic(cyclic);
        wheelList.setDividerColor(dividerColor);
        wheelList.setDividerType(dividerType);
        wheelList.setLineSpacingMultiplier(lineSpacingMultiplier);
        wheelList.setTextColorOut(textColorOut);
        wheelList.setTextColorCenter(textColorCenter);
        wheelList.isCenterLabel(isCenterLabel);
        wheelList.setLineHeight(mItemHeight);
        wheelList.setCurrentItem(mCurrentItem);
    }


    public void setData(List<String> items) {
        if (wheelList!=null){
            wheelList.setData(items);
        }
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_SUBMIT)) {
            returnData();
        }
        dismiss();
    }

    public void returnData() {
        if (itemSelectListener != null) {
            try {
                String item=wheelList.getItem();
                itemSelectListener.onItemSelected(item, clickView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnItemSelectListener {
        void onItemSelected(String item, View v);
    }

    @Override
    public boolean isDialog() {
        return isDialog;
    }
}

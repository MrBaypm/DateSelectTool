# DateSelectTool
date selector tool

//日期选择工具
var builder = TimePickerView.Builder(activity)
        { date, v ->
            showLog("" + SimpleDateFormat("yyyy-MM-dd").format(date))
        }
            .setCancelText("取消") //取消按钮文字
            .setSubmitText("确定") //确认按钮文字
            .setLineHeigth(UtilSystem.dip2px(activity, 56.0f).toFloat())
            .setContentSize(20)//滚轮文字大小
            .setTitleBgColor(R.drawable.pickerview_bg_topbar)
            .setTitleText("时间")//标题文字
            .setTitleColor(Color.WHITE)
            .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
            //   .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
            //    .setRangDate(startDate,endDate)//起始终止年月日设定
            .setLabel("年", "月", "日")//默认设置为年月日时分秒
            .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。

        val pvTime = TimePickerView(builder!!)
        pvTime.show()
        
        

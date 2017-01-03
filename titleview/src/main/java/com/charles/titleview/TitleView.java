package com.charles.titleview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * com.charles.titleview.TitleView
 *
 * @author Just.T
 * @since 16/12/29
 */
public class TitleView extends RelativeLayout {
    private int mBackgroudColor;
    private int mTitleColor;
    private String mTitle;
    private boolean mBackButton = true;
    private String mRightText;
    private static final int DEFAULT_BACKGROUND_COLOR = 0xffffffff; //默认背景色为白色
    private static final int DEFAULT_TITLE_COLOR = 0xff333333;//默认标题文字颜色
    private static final boolean DEFAULT_BACK_BUTTON = true;//默认返回按钮是显示
    private TextView tv_title;
    private TextView tv_right_text;
    private ImageView iv_back;
    private RelativeLayout mRootView;
    private Activity context;
    private onBackListener mListener;


    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (context instanceof Activity) {
            this.context = (Activity) context;
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.titleview, this, true);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs,
                    R.styleable.TitleView, defStyleAttr, 0);
            mBackgroudColor = a.getColor(R.styleable.TitleView_title_background, DEFAULT_BACKGROUND_COLOR);
            mTitleColor = a.getColor(R.styleable.TitleView_title_color, DEFAULT_TITLE_COLOR);
            mTitle = a.getString(R.styleable.TitleView_title_text);
            mBackButton = a.getBoolean(R.styleable.TitleView_back_button, DEFAULT_BACK_BUTTON);
            mRightText = a.getString(R.styleable.TitleView_right_text);

        } else {
            mBackgroudColor = DEFAULT_BACKGROUND_COLOR;
            mTitleColor = DEFAULT_TITLE_COLOR;
        }
        init(inflate);
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void init(View view) {
        mRootView = (RelativeLayout) view.findViewById(R.id.rl_root);
        tv_title = (TextView) view.findViewById(R.id.title);
        tv_right_text = (TextView) view.findViewById(R.id.tv_right_text);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        setRightText(mRightText);
        setTitleColor(mTitleColor);
        setTitle(mTitle);
        setBackButton(mBackButton);
        setBackgroudColor(mBackgroudColor);
    }


    /**
     * 设置背景色
     *
     * @param mBackgroudColor
     */
    public void setBackgroudColor(int mBackgroudColor) {
        this.mBackgroudColor = mBackgroudColor;
        mRootView.setBackgroundColor(mBackgroudColor);
    }


    /**
     * 设置标题颜色
     *
     * @param mTitleColor
     */
    public void setTitleColor(int mTitleColor) {
        this.mTitleColor = mTitleColor;
        tv_title.setTextColor(mTitleColor);
    }


    /**
     * 设置标题文字
     *
     * @param mTitle
     */
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
        tv_title.setText(mTitle);
    }


    /**
     * 设置返回按钮是否显示
     * 如果显示  点击后回关闭当前activity
     * 如果需要关闭前回调 调用setBackListener设置回调函数
     * 如果不需要显示  传入false 或者 xml中自定义属性  app:back_button = false
     *
     * @param mBackButton
     */
    public void setBackButton(boolean mBackButton) {
        this.mBackButton = mBackButton;
        iv_back.setVisibility(mBackButton ? VISIBLE : GONE);
        if (mBackButton) {
            iv_back.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (context != null) {
                        if (mListener != null) mListener.onBack();
                        context.finish();
                    }
                }
            });
        }
    }

    /**
     * 设置右边栏文字（目前只支持文字显示   以后改为支持文字或者图片）//TODO
     *
     * @param mRightText
     */
    public void setRightText(String mRightText) {
        this.mRightText = mRightText;
        tv_right_text.setText(mRightText);
    }

    /**
     * 返回按钮点击后 页面关闭前的回调设置
     *
     * @param listener
     */
    public void setBackListener(onBackListener listener) {
        this.mListener = listener;
        setBackButton(true);
    }

    /**
     * 右侧按钮的点击设置
     *
     * @param listener
     */
    public void setRightTextClickListener(OnClickListener listener) {
        if (mRightText.length() > 0)
            tv_right_text.setOnClickListener(listener);
    }

    /**
     * 获取右侧文字
     *
     * @return
     */
    public String getRightText() {
        return mRightText;
    }

    /**
     * 获取当前返回按钮显示状态
     *
     * @return
     */
    public boolean getBackButton() {
        return mBackButton;
    }

    /**
     * 获取标题文字
     *
     * @return
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * 获取标题颜色
     *
     * @return
     */
    public int getTitleColor() {
        return mTitleColor;
    }

    /**
     * 获取背景色
     *
     * @return
     */
    public int getBackgroudColor() {
        return mBackgroudColor;
    }


    /**
     * 回调接口
     */
    public interface onBackListener {
        void onBack();
    }

}
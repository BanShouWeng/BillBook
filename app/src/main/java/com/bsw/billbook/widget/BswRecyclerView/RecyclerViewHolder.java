package com.bsw.billbook.widget.BswRecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsw.billbook.utils.Const;
import com.bsw.billbook.utils.TxtUtils;


/**
 * ViewHolder
 *
 * @author leiming
 * @date 2018/4/22 11:26
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;//集合类，layout里包含的View,以view的id作为key，value是view对象
    @SuppressWarnings("FieldCanBeLocal")
    private Context mContext;//上下文对象

    public RecyclerViewHolder(Context ctx, View itemView) {
        super(itemView);
        mContext = ctx;
        mViews = new SparseArray<>();
    }

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mViews = new SparseArray<>();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public ImageView getImageView(@IdRes int viewId) {
        return getView(viewId);
    }

    public RecyclerViewHolder setText(@IdRes int viewId, String value) {
        TextView view = getView(viewId);
        view.setText(TxtUtils.getText(value));
        return this;
    }

    public RecyclerViewHolder setText(@IdRes int viewId, Spanned value) {
        TextView view = getView(viewId);
        if (Const.notEmpty(value) && value.length() > 0) {
            view.setText(value);
        } else {
            view.setText("-");
        }
        return this;
    }

    public RecyclerViewHolder setText(@IdRes int viewId, @StringRes int valueId) {
        TextView view = getView(viewId);
        view.setText(valueId);
        return this;
    }

    public RecyclerViewHolder setVisibility(@IdRes int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public int getVisibility(@IdRes int viewId) {
        return getView(viewId).getVisibility();
    }

    /**
     * 设置文本以及色值
     *
     * @param viewId 设置文本的View的Id
     * @param color  设置的颜色
     * @param value  设置的文字，添加防空判断，否则会导致如下错误
     *               https://www.zhihu.com/question/63655287
     * @return holder
     */
    public RecyclerViewHolder setText(@IdRes int viewId, int color, String value) {
        TextView view = getView(viewId);
        view.setText(TxtUtils.getText(value));
        view.setTextColor(color);
        return this;
    }

    public RecyclerViewHolder setImageRes(@IdRes int viewId, @DrawableRes int resId) {
        ((ImageView) getView(viewId)).setImageResource(resId);
        return this;
    }

    public RecyclerViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ((ImageView) getView(viewId)).setImageBitmap(bitmap);
        return this;
    }

    public RecyclerViewHolder setBackground(@IdRes int viewId, @DrawableRes int resId) {
        getView(viewId).setBackgroundResource(resId);
        return this;
    }

    @SuppressWarnings("UnusedReturnValue")
    public RecyclerViewHolder setClickListener(@IdRes int viewId, View.OnClickListener listener) {
        getView(viewId).setOnClickListener(listener);
        return this;
    }

    public RecyclerViewHolder setClickListener(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
        return this;
    }
}

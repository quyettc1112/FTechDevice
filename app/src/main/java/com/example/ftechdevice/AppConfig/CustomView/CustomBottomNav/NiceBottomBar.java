package com.example.ftechdevice.AppConfig.CustomView.CustomBottomNav;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.ftechdevice.Common.Constants.Constants;
import com.example.ftechdevice.R;

import java.util.ArrayList;
import java.util.List;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.*;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class NiceBottomBar extends View {

    // Default attribute values
    private int barBackgroundColor = Color.parseColor(Constants.WHITE_COLOR_HEX);
    private int barIndicatorColor = Color.parseColor(Constants.DEFAULT_PRIMARY_COLOR);
    private int barIndicatorInterpolator = 4;
    private float barIndicatorWidth = d2p(50f);
    private boolean barIndicatorEnabled = true;
    private int barIndicatorGravity = 1;
    private float itemIconSize = d2p(18.5f);
    private float itemIconMargin = d2p(2.5f);
    private int itemTextColor = Color.parseColor(Constants.DEFAULT_PRIMARY_COLOR_ACTIVE);
    private int itemTextColorActive = Color.parseColor(Constants.DEFAULT_PRIMARY_COLOR);
    private float itemTextSize = d2p(12f);
    private int itemBadgeColor = itemTextColorActive;
    private int itemFontFamily = 0;
    private int activeItem = 0;

    /**
     * Dynamic variables
     */
    private int currentActiveItemColor = itemTextColor;
    private float indicatorLocation = 0f;

    // Represent long press time, when press time > longPressTime call the function callback.onItemLongClick
    private int longPressTime = 500;
    private final float titleSideMargins = d2p(12f);

    private List<BottomBarItem> items = new ArrayList<>();

    private OnItemSelectedListener onItemSelectedListener;
    private OnItemReselectedListener onItemReselectedListener;
    private OnItemLongClickListener onItemLongClickListener;

    private OnItemSelected onItemSelected = (int index) -> {};
    private OnItemReselected onItemReselected = (int index) -> {};
    private OnItemLongClick onItemLongClick = (int index) -> {};

    private final Paint paintIndicator = new Paint();
    private final Paint paintText = new Paint();
    private final Paint paintBadge = new Paint();

    public NiceBottomBar(Context context) {
        super(context);
        init(null);
    }

    public NiceBottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.NiceBottomBar, 0, 0);
            barBackgroundColor = typedArray.getColor(R.styleable.NiceBottomBar_backgroundColor, this.barBackgroundColor);
            barIndicatorColor = typedArray.getColor(R.styleable.NiceBottomBar_indicatorColor, this.barIndicatorColor);
            barIndicatorWidth = typedArray.getDimension(R.styleable.NiceBottomBar_indicatorWidth, this.barIndicatorWidth);
            barIndicatorEnabled = typedArray.getBoolean(R.styleable.NiceBottomBar_indicatorEnabled, this.barIndicatorEnabled);
            itemTextColor = typedArray.getColor(R.styleable.NiceBottomBar_textColor, this.itemTextColor);
            itemTextColorActive = typedArray.getColor(R.styleable.NiceBottomBar_textColorActive, this.itemTextColorActive);
            itemTextSize = typedArray.getDimension(R.styleable.NiceBottomBar_textSize, this.itemTextSize);
            itemIconSize = typedArray.getDimension(R.styleable.NiceBottomBar_iconSize, this.itemIconSize);
            itemIconMargin = typedArray.getDimension(R.styleable.NiceBottomBar_iconMargin, this.itemIconMargin);
            activeItem = typedArray.getInt(R.styleable.NiceBottomBar_activeItem, this.activeItem);
            barIndicatorInterpolator = typedArray.getInt(R.styleable.NiceBottomBar_indicatorInterpolator, this.barIndicatorInterpolator);
            barIndicatorGravity = typedArray.getInt(R.styleable.NiceBottomBar_indicatorGravity, this.barIndicatorGravity);
            itemBadgeColor = typedArray.getColor(R.styleable.NiceBottomBar_badgeColor, this.itemBadgeColor);
            itemFontFamily = typedArray.getResourceId(R.styleable.NiceBottomBar_itemFontFamily, this.itemFontFamily);
            items = new BottomBarParser(getContext(), typedArray.getResourceId(R.styleable.NiceBottomBar_menu, 0)).parse();
            typedArray.recycle();

            setBackgroundColor(barBackgroundColor);

            // Update default attribute values
            paintIndicator.setColor(barIndicatorColor);
            paintText.setColor(itemTextColor);
            paintText.setTextSize(itemTextSize);
            paintBadge.setColor(itemBadgeColor);

            if (itemFontFamily != 0)
                paintText.setTypeface(ResourcesCompat.getFont(getContext(), itemFontFamily));
        }

        paintIndicator.setAntiAlias(true);
        paintIndicator.setStyle(Paint.Style.STROKE);
        paintIndicator.setStrokeWidth(10f);
        paintIndicator.setStrokeCap(Paint.Cap.ROUND);

        paintText.setAntiAlias(true);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextAlign(Paint.Align.CENTER);
        paintText.setFakeBoldText(true);

        paintBadge.setAntiAlias(true);
        paintBadge.setStyle(Paint.Style.FILL);
        paintBadge.setStrokeWidth(4f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float lastX = 0f;
        float itemWidth = (float) getWidth() / items.size();

        for (BottomBarItem item : items) {
            // Prevent text overflow by shortening the item title
            boolean shorted = false;
            while (paintText.measureText(item.getTitle()) > (itemWidth - titleSideMargins)) {
                item.setTitle(item.getTitle().substring(0, item.getTitle().length() - 1));
                shorted = true;
            }
            // Add ellipsis character to item text if it is shorted
            if (shorted) {
                item.setTitle(item.getTitle().substring(0, item.getTitle().length() - 1) + getContext().getString(R.string.ellipsis));
            }

            item.setRect(new RectF(lastX, 0f, itemWidth + lastX, getHeight()));
            lastX += itemWidth;
        }

        // Set initial active item
        setActiveItem(activeItem);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float textHeight = (paintText.descent() + paintText.ascent()) / 2;

        // Push the item components from the top a bit if the indicator is at the top
        float additionalTopMargin = barIndicatorGravity == 1 ? 0f : 10f;

        for (int i = 0; i < items.size(); i++) {
            BottomBarItem item = items.get(i);
            item.getIcon().mutate();
            item.getIcon().setBounds(
                    (int) (item.getRect().centerX() - itemIconSize / 2),
                    (int) (getHeight() / 2 - itemIconSize - itemIconMargin / 2 + additionalTopMargin),
                    (int) (item.getRect().centerX() + itemIconSize / 2),
                    (int) (getHeight() / 2 - itemIconMargin / 2 + additionalTopMargin)
            );

            DrawableCompat.setTint(item.getIcon(), i == activeItem ? currentActiveItemColor : itemTextColor);
            item.getIcon().draw(canvas);

            // Draw item title
            paintText.setColor(i == activeItem ? currentActiveItemColor : itemTextColor);
            canvas.drawText(
                    item.getTitle(),
                    item.getRect().centerX(),
                    item.getRect().centerY() - textHeight + itemIconSize / 2 + (itemIconMargin / 2) + additionalTopMargin,
                    paintText
            );

            // Draw item badge
            if (item.getBadgeSize() > 0)
                drawBadge(canvas, item);
        }

        // Draw indicator
        if (barIndicatorEnabled) {
            canvas.drawLine(
                    indicatorLocation - barIndicatorWidth / 2,
                    barIndicatorGravity == 1 ? getHeight() - 5.0f : 5f,
                    indicatorLocation + barIndicatorWidth / 2,
                    barIndicatorGravity == 1 ? getHeight() - 5.0f : 5f,
                    paintIndicator
            );
        }
    }

    // Handle item clicks
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && Math.abs(event.getDownTime() - event.getEventTime()) < longPressTime) {
            for (int i = 0; i < items.size(); i++) {
                BottomBarItem item = items.get(i);
                if (item.getRect().contains(event.getX(), event.getY())) {
                    if (i != this.activeItem) {
                        setActiveItem(i);
                        onItemSelected.onItemSelected(i);
                        if (onItemSelectedListener != null) onItemSelectedListener.onItemSelect(i);
                    } else {
                        onItemReselected.onItemReselected(i);
                        if (onItemReselectedListener != null) onItemReselectedListener.onItemReselect(i);
                    }
                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_UP) {
            if (Math.abs(event.getDownTime() - event.getEventTime()) > longPressTime) {
                for (int i = 0; i < items.size(); i++) {
                    BottomBarItem item = items.get(i);
                    if (item.getRect().contains(event.getX(), event.getY())) {
                        onItemLongClick.onItemLongClick(i);
                        if (onItemLongClickListener != null) onItemLongClickListener.onItemLongClick(i);
                    }
                }
            }
        }

        return true;
    }

    // Draw item badge
    private void drawBadge(Canvas canvas, BottomBarItem item) {
        paintBadge.setStyle(Paint.Style.FILL);
        paintBadge.setColor(itemTextColorActive);

        canvas.drawCircle(
                item.getRect().centerX() + itemIconSize / 2 - 4,
                getHeight() / 2 - itemIconSize - itemIconMargin / 2 + 10,
                item.getBadgeSize(),
                paintBadge
        );

        paintBadge.setStyle(Paint.Style.STROKE);
        paintBadge.setColor(barBackgroundColor);

        canvas.drawCircle(
                item.getRect().centerX() + itemIconSize / 2 - 4,
                getHeight() / 2 - itemIconSize - itemIconMargin / 2 + 10,
                item.getBadgeSize(),
                paintBadge
        );
    }

    // Add item badge
    public void setBadge(int pos) {
        if (pos >= 0 && pos < items.size() && items.get(pos).getBadgeSize() == 0f) {
            ValueAnimator animator = ValueAnimator.ofFloat(0f, 15f);
            animator.setDuration(100);
            animator.addUpdateListener(animation -> {
                items.get(pos).setBadgeSize((float) animation.getAnimatedValue());
                invalidate();
            });
            animator.start();
        }
    }

    // Remove item badge
    public void removeBadge(int pos) {
        if (pos >= 0 && pos < items.size() && items.get(pos).getBadgeSize() > 0f) {
            ValueAnimator animator = ValueAnimator.ofFloat(items.get(pos).getBadgeSize(), 0f);
            animator.setDuration(100);
            animator.addUpdateListener(animation -> {
                items.get(pos).setBadgeSize((float) animation.getAnimatedValue());
                invalidate();
            });
            animator.start();
        }
    }

    public int getActiveItem() {
        return activeItem;
    }

    public void setActiveItem(int pos) {
        activeItem = pos;

        animateIndicator(pos);
        setItemColors();
    }

    private void animateIndicator(int pos) {
        ValueAnimator animator = ValueAnimator.ofFloat(indicatorLocation, items.get(pos).getRect().centerX());
        animator.setInterpolator(getInterpolator(this.barIndicatorInterpolator));

        animator.addUpdateListener(animation -> {
            indicatorLocation = (float) animation.getAnimatedValue();
            invalidate();
        });

        animator.start();
    }

    // Apply transition animation to item color
    private void setItemColors() {
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), itemTextColor, itemTextColorActive);
        animator.addUpdateListener(animation -> currentActiveItemColor = (int) animation.getAnimatedValue());
        animator.start();
    }

    private Interpolator getInterpolator(int type) {
        switch (type) {
            case 0:
                return new AccelerateInterpolator();
            case 1:
                return new DecelerateInterpolator();
            case 2:
                return new AccelerateDecelerateInterpolator();
            case 3:
                return new AnticipateInterpolator();
            case 4:
                return new AnticipateOvershootInterpolator();
            case 5:
                return new LinearInterpolator();
            case 6:
                return new OvershootInterpolator();
            default:
                return new AnticipateOvershootInterpolator();
        }
    }

    private float d2p(float dp) {
        return getResources().getDisplayMetrics().densityDpi / 160f * dp;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    public void setOnItemReselectedListener(OnItemReselectedListener listener) {
        this.onItemReselectedListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    // Listener interfaces
    public interface OnItemSelectedListener {
        void onItemSelect(int index);
    }

    public interface OnItemReselectedListener {
        void onItemReselect(int index);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int index);
    }

    @FunctionalInterface
    public interface OnItemSelected {
        void onItemSelected(int index);
    }

    @FunctionalInterface
    public interface OnItemReselected {
        void onItemReselected(int index);
    }

    @FunctionalInterface
    public interface OnItemLongClick {
        void onItemLongClick(int index);
    }
}

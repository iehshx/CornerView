package com.iehshx.cornerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class CornerView extends FrameLayout {

    private boolean CORNER_TOP_LEFT = false;
    private boolean CORNER_TOP_RIGHT = false;
    private boolean CORNER_BOTTOM_LEFT = false;
    private boolean CORNER_BOTTOM_RIGHT = false;


    public CornerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CornerView(Context context) {
        super(context);
        init(context, null);
    }

    private final RectF roundRect = new RectF();
    private final RectF bottomRect = new RectF();
    private int rect_adius = 10;
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CornerView);
            CORNER_TOP_LEFT = a.getBoolean(R.styleable.CornerView_cornerTopLeft, false);
            CORNER_TOP_RIGHT = a.getBoolean(R.styleable.CornerView_cornerTopRight, false);
            CORNER_BOTTOM_LEFT = a.getBoolean(R.styleable.CornerView_cornerBottomLeft, false);
            CORNER_BOTTOM_RIGHT = a.getBoolean(R.styleable.CornerView_cornerBottomRight, false);
        }
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
        float density = getResources().getDisplayMetrics().density;
        rect_adius = (int) (rect_adius * density);
    }

    public void setCorner(int adius) {
        rect_adius = adius;
        invalidate();
    }

    public void setCornerTopLeft(boolean isCorner) {
        CORNER_TOP_LEFT = isCorner;
    }

    public void setCornerTopRight(boolean isCorner) {
        CORNER_TOP_RIGHT = isCorner;
    }

    public void setCornerBottomLeft(boolean isCorner) {
        CORNER_BOTTOM_LEFT = isCorner;
    }

    public void setCornerBottomRight(boolean isCorner) {
        CORNER_BOTTOM_RIGHT = isCorner;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int w = getWidth();
        int h = getHeight();
        roundRect.set(0, 0, w, h);
        bottomRect.set(bottom, bottom, w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(roundRect, rect_adius, rect_adius, zonePaint);
        if (!CORNER_TOP_LEFT) {
            canvas.drawRect(0, 0, rect_adius, rect_adius, zonePaint);
        }
        if (!CORNER_TOP_RIGHT) {
            canvas.drawRect(roundRect.right - rect_adius, 0, roundRect.right, rect_adius, zonePaint);
        }
        if (!CORNER_BOTTOM_LEFT) {
            canvas.drawRect(0, roundRect.bottom - rect_adius, rect_adius, roundRect.bottom, zonePaint);
        }
        if (!CORNER_BOTTOM_RIGHT) {
            canvas.drawRect(roundRect.right - rect_adius, roundRect.bottom - rect_adius, roundRect.right, roundRect.bottom, zonePaint);
        }
        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);

        super.draw(canvas);
        canvas.restore();
    }

}
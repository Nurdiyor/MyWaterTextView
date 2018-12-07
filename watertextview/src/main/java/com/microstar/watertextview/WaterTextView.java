package com.microstar.watertextview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.Gravity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by QarakenBacho on 13.05.2018.
 */

public class WaterTextView extends android.support.v7.widget.AppCompatTextView {

    private DrawableBackgroundSpan spanBoth;
    private DrawableBackgroundSpan spanTop;
    private DrawableBackgroundSpan spanBottom;
    private DrawableBackgroundSpan spanNone;
    private int alpha = 85;
    private static int DIFFERENCE = 120;

    public WaterTextView(Context context) {
        super(context);
        init();
    }

    public WaterTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaterTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void setBackgroundAlpha(int alpha) {
        this.alpha = alpha;
        init();
    }

    private void gogo() {
        String s = this.getText().toString();
        int weight=0;
        if (this.getTextSize()<=100)weight=5;
        if (this.getTextSize()<=70) weight=10;
        if (this.getTextSize()<=50) weight=15;
        if (this.getTextSize()<=40) weight=20;
        if (this.getTextSize()<=20) weight=40;

        char m[] = s.toCharArray();
        String X = "";
        for (int i = 0; i < m.length; i++) {
            if (m[i] == ' ' || m[i] == '.' || m[i] == ',' || m[i] == ':' || m[i] == ';') {
                X += m[i]+"\n";
            } else {
                X +=m[i];
            }
        }
        String ss[]=X.split("\n");
        String sss[]=new String[ss.length+10];
        String s1="";
        int t=0;
        sss[t]="";
        sss[t]+=ss[0];
        for (int i = 1; i < ss.length; i++) {
            if (weight>sss[t].length()){
                sss[t]+=ss[i];
                s1+=sss[t];
            }else {
                t++;
                sss[t]="";
                sss[t]+="\n"+ss[i];
                s1+=sss[t];
            }
        }
        this.setText(s1);
    }

    private void init() {
        gogo();
        Drawable both = getResources().getDrawable(R.drawable.both);
        both.setAlpha(alpha);
        this.spanBoth = new DrawableBackgroundSpan(both);

        Drawable top = getResources().getDrawable(R.drawable.top);
        top.setAlpha(alpha);
        this.spanTop = new DrawableBackgroundSpan(top);

        Drawable bottom = getResources().getDrawable(R.drawable.bottom);
        bottom.setAlpha(alpha);
        this.spanBottom = new DrawableBackgroundSpan(bottom);

        Drawable none = getResources().getDrawable(R.drawable.none);
        none.setAlpha(alpha);
        this.spanNone = new DrawableBackgroundSpan(none);

        this.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.setBreakStrategy(Layout.BREAK_STRATEGY_SIMPLE);
        }
        invalidate();
    }


    @Override
    public void onDraw(Canvas canvas) {
        List<Integer> lines = new ArrayList<>();
        List<Float> widths = new ArrayList<>();
        for (int i = 0; i < this.getLineCount(); i++) {
            lines.add(this.getLayout().getLineEnd(i));
            widths.add(this.getLayout().getLineWidth(i));
        }
        BackgroundSpannable span = null;
        int PADDING = 0;
        int start = 0;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.size() >= 0) {
                span = spanBoth;
                PADDING = 45;
            }
            if (i < lines.size() - 1)
                if (widths.get(i) > widths.get(i + 1)) {
                    span = spanBoth;
                    PADDING = 45;
                } else if (i > 0)
                    if (widths.get(i) > widths.get(i - 1) && widths.get(i) > widths.get(i + 1)) {
                        span = spanBoth;
                        PADDING = 45;
                    }

            if (lines.size() > 1) {
                if (i == 0) {
//                    if (widths.get(i + 1) - widths.get(i) > DIFFERENCE) {
                    if (widths.get(i + 1) > widths.get(i)) {
                        if (this.spanBottom != null) {
                            span = spanBottom;
                            PADDING = 40;
                        }
                    }
                } else {
                    if (i == lines.size() - 1) {
                        if (widths.get(i - 1) > widths.get(i)) {
                            if (this.spanTop != null) {
                                span = spanTop;
                                PADDING = 40;
                            }
                        } else {
                            span = spanBoth;
                            PADDING = 45;
                        }
                    } else {
//                        if (widths.get(i + 1) - widths.get(i) > DIFFERENCE && widths.get(i - 1) - widths.get(i) > DIFFERENCE) {
                        if (widths.get(i + 1) > widths.get(i) && widths.get(i - 1) > widths.get(i) && i != lines.size() - 1) {
                            if (this.spanNone != null) {
                                span = this.spanNone;
                                PADDING = 25;
                            }
                        } else {
//                            if (widths.get(i - 1) - widths.get(i) > DIFFERENCE && widths.get(i + 1) - widths.get(i) < DIFFERENCE) {
                            if (widths.get(i - 1) > widths.get(i) && widths.get(i + 1) < widths.get(i)) {
                                if (this.spanTop != null) {
                                    span = spanTop;
                                    PADDING = 40;
                                }
                            } else {
//                                if (widths.get(i + 1) - widths.get(i) > DIFFERENCE && widths.get(i - 1) - widths.get(i) < DIFFERENCE) {
                                if (widths.get(i + 1) > widths.get(i) && widths.get(i - 1) < widths.get(i)) {
                                    if (this.spanBottom != null) {
                                        span = spanBottom;
                                        PADDING = 40;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            span.setMainTextView(this);
            if (i == lines.size() - 1) {
                span.setRange(start, lines.get(i));
            } else {
                span.setRange(start, lines.get(i) - 1);
            }
            span.updateDrawState(canvas, PADDING);

            start = lines.get(i);
        }
        super.onDraw(canvas);
    }
}

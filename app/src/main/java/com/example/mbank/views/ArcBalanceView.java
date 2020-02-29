package com.example.mbank.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.mbank.R;
import com.example.mbank.utils.DateUtils;

public class ArcBalanceView extends View {

    //constants
    private static final String TAG = "ArcBalanceView";
    private static final String ACCOUNT_BALLANCE = "78 827,";
    private static final String CURRENT_SPENT = "1 424,70";
    private static final float SWEEP_ANGLE = 280;

    private Paint circlePaint = new Paint(), textPaint = new Paint(), progressCirclePaint = new Paint(), minMaxLinesPaint = new Paint();

    private SweepGradient gradient;
    private RectF oval = new RectF();

    //text Rect
    private Rect textRect = new Rect();

    private Bitmap cardYellow, c2cBlue;

    private int[] colors;
    private float[] points;

    //arc build variables
    boolean drawProgressCircle = true, drawMinMaxLines = true, drawThinStrokeCircle = false, drawFullCircle = false;
    float strokeWidth = 17, balanceFontSize = 75, circleRadius;
    int strokeColor, thinCircleColor, imageId = R.drawable.ic_card_yellow;

    private Context context;

    public ArcBalanceView(Context context) {
        super(context);
        setWillNotDraw(true);
        this.context = context;
    }

    public ArcBalanceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(true);
        this.context = context;
    }

    public ArcBalanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(true);
        this.context = context;
    }

    public ArcBalanceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(true);
        this.context = context;
    }

    public void init() {
        float gradientLength = 0.020f;

        colors = new int[]{
                //red right start arc
                ContextCompat.getColor(context, R.color.mBankRed),
                ContextCompat.getColor(context, R.color.mBankRed),

                //green left arc
                ContextCompat.getColor(context, R.color.mBankGreen),
                ContextCompat.getColor(context, R.color.mBankGreen),

                //yellow top arc
                ContextCompat.getColor(context, R.color.mBankYellow),
                ContextCompat.getColor(context, R.color.mBankYellow),

                //red right arc
                ContextCompat.getColor(context, R.color.mBankRed),
        };
        points = new float[]{
                //red right start arc
                0.0f,
                0.250f,

                //green left arc
                0.250f,
                getThirdPointOfArc(1) / 360 - gradientLength / 2,

                //yellow top arc
                getThirdPointOfArc(1) / 360 + gradientLength / 2,
                getThirdPointOfArc(2) / 360 - gradientLength / 2,

                //red right arc
                getThirdPointOfArc(2) / 360 + gradientLength / 2,
        };
        cardYellow = BitmapFactory.decodeResource(getResources(), R.drawable.ic_card_yellow);
        cardYellow = getResizedBitmap(cardYellow, 50, 50);


        c2cBlue = BitmapFactory.decodeResource(getResources(), R.drawable.ic_c2c_blue);
        c2cBlue = getResizedBitmap(c2cBlue, 50, 50);

        this.setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        circleRadius = getWidth() / 2;

        //draw arc
        if (!drawFullCircle) {
            drawArc(canvas);
        } else {
            drawFullCircle(canvas);
        }

        //draw inner, small, gray circle
        circlePaint.setStrokeWidth(1);
        circlePaint.setColor(getResources().getColor(R.color.grayDarkSilver, null));
        circlePaint.setShader(null);
        canvas.drawCircle(oval.centerX(), oval.centerY(), circleRadius * 0.7f, circlePaint);

        if (drawThinStrokeCircle) {
            //draw thin circle inside the circle/arc
            circlePaint.setColor(thinCircleColor);
            circlePaint.setStrokeWidth(2);
            canvas.drawCircle(oval.centerX(), oval.centerY(), circleRadius - strokeWidth, circlePaint);
        }

        //draw text inside circle
        if (!drawFullCircle) {
            drawTextInsideCircle(canvas);
        } else if (drawThinStrokeCircle) {
            if (strokeColor == getResources().getColor(R.color.mBankBlue, null)) {
                drawTextWithImage(canvas, c2cBlue);
            } else {
                drawTextWithImage(canvas, cardYellow);
            }
        }else {
            drawTextWithHugeCenter(canvas);
        }

        //draw in the bottom of canvas
        if (!drawFullCircle)
            drawTextBelowArc(canvas);

        //draw small circle on the arc
        if (drawProgressCircle)
            drawProgressCircle(canvas, 250);

        //draw lines indicating min and max arc values
        if (drawMinMaxLines)
            drawMinMaxLines(canvas);
    }


    //draw components methods

    private void drawArc(@NonNull Canvas canvas) {

        //draw arc
        circlePaint.setStrokeWidth(strokeWidth);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);

        circlePaint.setAntiAlias(true);
        int center_x = getWidth() / 2;
        int center_y = getHeight() / 2;
        oval.set(center_x - circleRadius + strokeWidth,
                center_y - circleRadius + strokeWidth,
                center_x + circleRadius - strokeWidth,
                center_y + circleRadius - strokeWidth);
        if (strokeColor == 0) {
            gradient = new SweepGradient(oval.centerX(), oval.centerY(), colors, points);
            circlePaint.setShader(gradient);
        } else {
            circlePaint.setColor(strokeColor);
        }
        canvas.drawArc(oval, 130, SWEEP_ANGLE, false, circlePaint);
    }

    private void drawFullCircle(@NonNull Canvas canvas) {
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        int center_x = getWidth() / 2;
        int center_y = getHeight() / 2;
        oval.set(center_x - circleRadius + strokeWidth,
                center_y - circleRadius + strokeWidth,
                center_x + circleRadius - strokeWidth,
                center_y + circleRadius - strokeWidth);

        if (drawThinStrokeCircle) {
            circlePaint.setColor(getResources().getColor(R.color.grayLight, null));
            circlePaint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(oval.centerX(), oval.centerY(), circleRadius - strokeWidth / 2f, circlePaint);
            circlePaint.setStrokeWidth(5);
            circlePaint.setColor(strokeColor);
            canvas.drawCircle(oval.centerX(), oval.centerY(), circleRadius - strokeWidth / 2f, circlePaint);
        } else {
            circlePaint.setColor(strokeColor);
            circlePaint.setStrokeWidth(strokeWidth);
            canvas.drawCircle(oval.centerX(), oval.centerY(), circleRadius - strokeWidth / 2f, circlePaint);
        }


    }

    private void drawProgressCircle(@NonNull Canvas canvas, int progress) {

        //draw small circle on the arc
        progressCirclePaint.setStyle(Paint.Style.STROKE);
        progressCirclePaint.setColor(getResources().getColor(R.color.mBankYellow, null));
        progressCirclePaint.setAntiAlias(true);
        progressCirclePaint.setStrokeWidth(6);
        float x = getXcoordinateOnArc(progress);
        float y = getYcoordinateOnArc(progress);
        canvas.drawCircle(x, y, 13f, progressCirclePaint);
        progressCirclePaint.setStyle(Paint.Style.FILL);
        progressCirclePaint.setColor(getResources().getColor(R.color.mBankOrangeLight, null));
        canvas.drawCircle(x, y, 13f, progressCirclePaint);
    }

    private void drawTextInsideCircle(@NonNull Canvas canvas) {

        //draw text inside circle
        textPaint.setColor(getResources().getColor(R.color.lightBlack, null));
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(50);
        textPaint.getTextBounds(ACCOUNT_BALLANCE, 0, ACCOUNT_BALLANCE.length(), textRect);
        float fontRatio = textRect.width() / (2 * circleRadius * 0.7f);
        if (fontRatio < 0.8f) {
            balanceFontSize = (1.7f - fontRatio) * textPaint.getTextSize();
            textPaint.setTextSize(balanceFontSize);
            textPaint.getTextBounds(ACCOUNT_BALLANCE, 0, ACCOUNT_BALLANCE.length(), textRect);
        }
        // TODO: 22.10.2019 obliczanie rozmiaru czcionki na podstawie balansu konta
//        if(2*circleRadius*0.7f+>)
        float textX = (float) (getWidth() / 2 - (textRect.width() + 52) / 2 - textRect.left);
        float textY = (float) (getHeight() / 2 + textRect.height() / 2 - textRect.bottom);
        float textPlnHeight = textRect.height();
        textPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        canvas.drawText(ACCOUNT_BALLANCE, textX, textY, textPaint);
        textPaint.setTextSize(0.6f * balanceFontSize);
        canvas.drawText("81", textX + 5f + textRect.width(), textY, textPaint);

        textPaint.setTextSize(0.45f * balanceFontSize);
        textPaint.setColor(getResources().getColor(R.color.grayMidDark, null));
        textPaint.getTextBounds("Dostępne środki", 0, "Dostępne środki".length(), textRect);
        textX = (float) (getWidth() / 2 - (textRect.width()) / 2 - textRect.left);
        canvas.drawText("Dostępne środki", textX, textY - textPlnHeight / 2f - textRect.height() / 2f - 35f, textPaint);

        textPaint.setColor(getResources().getColor(R.color.dark, null));
        textPaint.setTextSize(0.45f * balanceFontSize);
        textPaint.getTextBounds("PLN", 0, "PLN".length(), textRect);
        textX = (float) (getWidth() / 2 - (textRect.width()) / 2 - textRect.left);
        canvas.drawText("PLN", textX, textY + textPlnHeight / 2f - textRect.height() / 2f + 35f, textPaint);
    }

    private void drawTextWithImage(Canvas canvas, Bitmap bitmap) {
        Paint p = new Paint();
        p.setAntiAlias(true);
        String upperText = "Zamów\nkartę kredytową lub\ndodaj tą, którą masz.";
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setColor(getResources().getColor(R.color.mBankBlack, null));
        textPaint.setTextSize(30);
        textPaint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        textPaint.getTextBounds("kartę kredytową lub", 0, "kartę kredytową lub".length(), textRect);

        float x, y = (float) Math.sqrt(Math.pow(circleRadius, 2) - Math.pow(textRect.width() / 2, 2)) - textRect.height();
        for (String line : upperText.split("\n")) {
            textPaint.getTextBounds(line, 0, line.length(), textRect);
            x = oval.centerX() - textRect.width() / 2f;
            canvas.drawText(line, x, y, textPaint);
            y += textPaint.descent() - textPaint.ascent();
        }
        y = y - bitmap.getHeight() / 2f;
        canvas.drawBitmap(bitmap,
                oval.centerX() - bitmap.getWidth() / 2f,
                y,
                p);

        String bottomText = "Zobaczysz tutaj jej saldo.";
        textPaint.setColor(getResources().getColor(R.color.grayMidDark, null));
        textPaint.setTextSize(26);
        y = y + bitmap.getHeight() + 30;

        for (String line : bottomText.split("\n")) {
            textPaint.getTextBounds(line, 0, line.length(), textRect);
            x = oval.centerX() - textRect.width() / 2f;
            canvas.drawText(line, x, y, textPaint);
            y += textPaint.descent() - textPaint.ascent();
        }
    }

    private void drawTextWithHugeCenter(Canvas canvas) {
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setColor(strokeColor);
        textPaint.setTextSize(50);
        textPaint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));

        textPaint.getTextBounds("w pasażu", 0, "w pasażu".length(), textRect);
        float x = oval.centerX() - textRect.width()/2f - textRect.left;
        float y = oval.centerY() + textRect.height()/2f - textRect.bottom;

        canvas.drawText("w pasażu", x, y, textPaint);

        textPaint.setColor(getResources().getColor(R.color.mBankBlack, null));
        textPaint.setTextSize(40);
        textPaint.getTextBounds("Sprawdź", 0, "Sprawdź".length(), textRect);
        x = oval.centerX() - textRect.width()/2f - textRect.left;
        canvas.drawText("Sprawdź", x, y+100, textPaint);

        textPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        textPaint.setColor(getResources().getColor(R.color.grayMidDark, null));
        textPaint.setTextSize(27);
        textPaint.getTextBounds("Kredyt gotówkowy", 0, "Kredyt gotówkowy".length(), textRect);
        x = oval.centerX() - textRect.width()/2f - textRect.left;
        canvas.drawText("Kredyt gotówkowy", x, y-100, textPaint);
    }

    private void drawTextBelowArc(@NonNull Canvas canvas) {
        String textToDraw = "Wydatki do "+ DateUtils.getFormatedPastDate(0,"dd MMM");
        String balanceToDraw = CURRENT_SPENT;
        if (strokeColor != 0) {
            //if second option chosen
            textToDraw = "Wolne środki";
            balanceToDraw = ACCOUNT_BALLANCE + "40";
        }
        textPaint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
        textPaint.setColor(getResources().getColor(R.color.mBankBlack, null));
        textPaint.getTextBounds(textToDraw, 0, textToDraw.length(), textRect);
        float textX = (oval.width() / 2 + strokeWidth - (textRect.width() / 2f - textRect.left));
        float textY = (float) (((oval.height() - strokeWidth) / 2) * Math.sin(Math.toRadians(90)) + oval.centerY()) - textRect.height() / 2f;
        canvas.drawText(textToDraw, textX, textY, textPaint);

        if (strokeColor == 0) {
            textPaint.setColor(getResources().getColor(R.color.mBankYellow, null));
        } else {
            textPaint.setColor(strokeColor);
        }

        textPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        textPaint.setTextSize(0.6f * balanceFontSize);
        textPaint.getTextBounds(balanceToDraw, 0, balanceToDraw.length(), textRect);
        textX = (oval.width() / 2 + strokeWidth - (textRect.width() + 57) / 2f - textRect.left);
        textY = textY + (textRect.height() / 2f + textRect.bottom + 25);
        canvas.drawText(balanceToDraw, textX, textY, textPaint);

        textPaint.setTextSize(0.4f * balanceFontSize);
        canvas.drawText(" PLN", textX + textRect.width() + 5f, textY - textRect.height() / 2f + textRect.bottom, textPaint);

    }

    private void drawMinMaxLines(@NonNull Canvas canvas) {
        //draw lines indicating min and max arc values
        float lineLength = 17;

        minMaxLinesPaint.setAntiAlias(true);
        minMaxLinesPaint.setColor(getResources().getColor(R.color.grayMidDark, null));
        minMaxLinesPaint.setStrokeWidth(1.5f);
        textPaint.setTextSize(0.4f * balanceFontSize);
        textPaint.setColor(getResources().getColor(R.color.grayMidDark, null));
        textPaint.getTextBounds("min", 0, "min".length(), textRect);

        float startX = getXcoordinateOnArc(getThirdPointOfArc(1));
        float startY = getYcoordinateOnArc(getThirdPointOfArc(1));
        canvas.drawLine(startX, startY, startX - lineLength, startY - lineLength, minMaxLinesPaint);
        canvas.drawLine(startX - lineLength, startY - lineLength, startX - lineLength - 15, startY - lineLength, minMaxLinesPaint);
        canvas.drawText("min", startX - lineLength - 24 - textRect.width(), startY - lineLength + textRect.height() / 2f - textRect.bottom, textPaint);

        startX = getXcoordinateOnArc(getThirdPointOfArc(2));
        startY = getYcoordinateOnArc(getThirdPointOfArc(2));
        canvas.drawLine(startX, startY, startX + lineLength, startY - lineLength, minMaxLinesPaint);
        canvas.drawLine(startX + lineLength, startY - lineLength, startX + lineLength + 15, startY - lineLength, minMaxLinesPaint);
        canvas.drawText("max", startX + lineLength + 17, startY - lineLength + textRect.height() / 2f - textRect.bottom, textPaint);

    }


    //private getters

    private float getThirdPointOfArc(int numerator) {
        return 90 + (360 - SWEEP_ANGLE) / 2 + (SWEEP_ANGLE / 3) * numerator;
    }

    private float getXcoordinateOnArc(float angle) {
        return (float) ((oval.width() / 2) * Math.cos(Math.toRadians(angle)) + oval.centerX());
    }

    private float getYcoordinateOnArc(float angle) {
        return (float) ((oval.height() / 2) * Math.sin(Math.toRadians(angle)) + oval.centerY());
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    //seters

    public ArcBalanceView setDrawProgressCircle(boolean drawProgressCircle) {
        this.drawProgressCircle = drawProgressCircle;
        return this;
    }

    public ArcBalanceView setDrawMinMaxLines(boolean drawMinMaxLines) {
        this.drawMinMaxLines = drawMinMaxLines;
        return this;
    }

    public ArcBalanceView setDrawThinStrokeCircle(boolean drawThinStrokeCircle) {
        this.drawThinStrokeCircle = drawThinStrokeCircle;
        return this;
    }

    public ArcBalanceView setDrawFullCircle(boolean drawFullCircle) {
        this.drawFullCircle = drawFullCircle;
        return this;
    }

    public ArcBalanceView setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    public ArcBalanceView setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        return this;
    }

    public ArcBalanceView setThinCircleColor(int thinCircleColor) {
        this.thinCircleColor = thinCircleColor;
        return this;
    }

    public ArcBalanceView setImageId(int imageId) {
        this.imageId = imageId;
        return this;
    }

}

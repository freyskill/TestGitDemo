package top.km.testgitdemo;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ShineProgress extends View {
    private final String TAG = "ShineProgress";
    private final long DEFAULT_DURATION = 2000;
    private Context mContext;
    private Paint mPaint = null;
    private LinearGradient mLeftGradient;
    private final int COLOR_START = Color.parseColor("#0011B9F7");
    private final int COLOR_END = Color.parseColor("#11B9F7");

    private final int BG_HEIGHT = 4;
    private final int SHINE_HEIGHT = 38;
    private Bitmap mBitmap;
    private Rect mSrcRect;
    private Rect mDstRect;
    private int mWidth = 0;
    private int mHeight = 0;
    private int mDrawWidth = 0;
    private long mStartTime = 0;

    public ShineProgress(Context context) {
        this(context,null);
    }

    public ShineProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShineProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        mPaint = new Paint();

        mBitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.light)).getBitmap();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeight = getHeight();
        if (mWidth == 0 || mHeight == 0) {
            return;
        }
//        if (mLeftGradient == null) {
//            mLeftGradient = new LinearGradient(0, 0, mWidth, mHeight
//                    , new int[]{COLOR_START, COLOR_END, COLOR_START}, null, Shader.TileMode.CLAMP);
//            mPaint.setShader(mLeftGradient);
//        }
//        canvas.drawRect(0, (mHeight - BG_HEIGHT) / 2, mWidth, (mHeight + BG_HEIGHT) / 2, mPaint);
        if (mBitmap != null) {
            if (mSrcRect == null) {
                mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
            }
            if (mDstRect == null) {
                mDstRect = new Rect(0, (mHeight - SHINE_HEIGHT) / 2
                        , mBitmap.getWidth(), (mHeight + SHINE_HEIGHT) / 2);
            }
            mDstRect.left = mDrawWidth;
            if (mDrawWidth >= mWidth - mBitmap.getWidth()) {
                mDstRect.right = mDstRect.left + mWidth - mDrawWidth;
            } else if (mDrawWidth < mBitmap.getWidth()) {
                mDstRect.right = mDstRect.left + mBitmap.getWidth() / 2 + Math.min(mBitmap.getWidth() / 2, mDstRect.left);
            } else {
                mDstRect.right = mDrawWidth + mBitmap.getWidth();
            }
            if (mStartTime == 0) {
                mStartTime = System.currentTimeMillis();
            }
            long space = (System.currentTimeMillis() - mStartTime);
            mDrawWidth = (int) (space / (float) DEFAULT_DURATION * mWidth);
            if (mDrawWidth >= mWidth - mBitmap.getWidth() / 2) {
                mDrawWidth = 0;
                mStartTime = 0;
            }

//            if(space==50){
//                mPaint.setAlpha(0);
//            }else if(space == 100){
//                mPaint.setAlpha(10);
//            }else if(space == 150){
//                mPaint.setAlpha(20);
//            }else {
//                mPaint.setAlpha(100);
//            }

            canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, mPaint);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDrawWidth = 0;
    }

    volatile boolean flag;

    public void release() {
        flag = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag){
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    postInvalidate();
                }

            }
        }).start();
    }

    public void done(){
        flag = false;
    }
}

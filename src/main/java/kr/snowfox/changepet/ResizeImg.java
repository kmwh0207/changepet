package kr.snowfox.changepet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

public class ResizeImg {
    int mobile_width;
    int mobile_height;
    int img_height;
    int img_width;
    public ResizeImg(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        mobile_width = displayMetrics.widthPixels;
        mobile_height = displayMetrics.heightPixels;
    }
    public Bitmap convertimg(String id,int W,int H){
        
        int size= 1;
        
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        
        try {
            
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(id, options);
     
            img_height=options.outHeight;
            img_width=options.outWidth;
            
            
        } catch(Throwable e) {
            
        }finally{
            if(img_height>H && img_width>H){
                float sizeH=img_height/(float)H;
                float sizeW=img_width/(float)W;
                float sizeadd = (sizeH>sizeW ? sizeH:sizeW); 
                size = (int)Math.floor(sizeadd);
            }
        }
        options.inSampleSize = size;
        Bitmap src = BitmapFactory.decodeFile(id, options);
        Bitmap resized = Bitmap.createScaledBitmap(src, W, H, true);
        src.recycle();
        src = null;
        return resized;
    }
}
package kr.snowfox.changepet;

import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.content.Context;

public class SurfaceGameview extends SurfaceView implements SurfaceHolder.Callback{
    
    private SurfaceHolder surfaceholder;
    //public characterThread thread;
    //holder.setFormat(PixelFormat.TRANSLUCENT);
    public SurfaceGameview (Context context){
        super(context);
        this.setZOrderOnTop(false);
        


}
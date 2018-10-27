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
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        
    }
}
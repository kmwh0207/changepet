package kr.snowfox.changepet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import java.util.Date;
import java.text.SimpleDateFormat;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.widget.ImageView;
//해상도 가로모드에서 세로 360 가로 640
public class MainActivity extends Activity {
    
    static final public int CHANGE_VIEW = 1;
    int SURFACEVIEW_MODE = 0;
    int OPENGLES_MODE = 1;
    static int GRAPHIC_MODE=1;
    private MainGLSurfaceview mGLSurfaceview;

    Sharedpref setting;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState
     *         If the activity is being re-initialized after
     *         previously being shut down then this Bundle contains the data it most
     *         recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setting = Sharedpref.getInstance("GRAPHIC",this);
        //TODO bundle로 저장되어 불러올값 정하기
        if (savedInstanceState != null) {
          long savedtime = savedInstanceState.getLong("PAST_DATE");
          long time=System.currentTimeMillis()-savedtime;
          Date date = new Date(time);
          SimpleDateFormat DateFormat = new SimpleDateFormat("dd일만이에요");
          //TODO 인사말 사용되게 만들기
          String openmessage= DateFormat.format(date);
        }
        GRAPHIC_MODE=setting.getPref("VIEWMODE");
        if(GRAPHIC_MODE==0){
            setting.setPref("VIEWMODE",SURFACEVIEW_MODE);
            setContentView(R.layout.activity_main);
        } else{
            Message message = handler.obtainMessage();
            message.what = CHANGE_VIEW;
            message.arg1 = GRAPHIC_MODE;
            handler.sendMessage(message);
        }
        
        //graphic
        
        
      }
    
    final Handler handler = new Handler(){
        @Override public void handleMessage(Message msg){
            switch (msg.what) {
                case CHANGE_VIEW:
                    if(msg.arg1==OPENGLES_MODE){
                        mGLSurfaceview = new MainGLSurfaceview(MainActivity.this);
                        setContentView(mGLSurfaceview);
                    }else{
                        setContentView(R.layout.activity_main);
                    }
                    break; 
                default: 
                    break; 
                } 
            } 
        };

      @Override
      protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO 종료시자동저장
        long savetime=System.currentTimeMillis();
        outState.putLong("PAST_DATE", savetime);
      }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(kr.snowfox.changepet.R.menu.main, menu);
        return true;
    }

}

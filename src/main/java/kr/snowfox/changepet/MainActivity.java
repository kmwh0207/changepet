package kr.snowfox.changepet;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.SurfaceView;

import java.util.Date;
import java.text.SimpleDateFormat;

import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
//해상도 가로모드에서 세로 360 가로 640
//http://milkdrops.net/index.php/archives/665 리스트뷰 이거 참고좀
public class MainActivity extends Activity {
    
    static final public int CHANGE_VIEW = 1;
    int SURFACEVIEW_MODE = 0;
    int OPENGLES_MODE = 1;
    int LIBGDX_MODE = 2;
    static int GRAPHIC_MODE=1;
    private MainGLSurfaceview mGLSurfaceview;
    private Applicationclass Appclass;
    protected LayoutInflater inflater;
    
    Vibrator vibrator;
    Sharedpref setting;
    SurfaceView surfaceview;
    ImageView loadview;
    View v;
    
    final int images[]={R.drawable.load_a,R.drawable.load_b,R.drawable.load_c,R.drawable.load_d,R.drawable.load_e,R.drawable.load_f,R.drawable.load_g,R.drawable.load_h};
    static int cur = 0;
    
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
        setContentView(R.layout.activity_main);
        inflater =(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //ApplicationClass context를통해전달가능
        Appclass = (Applicationclass)getApplicationContext();
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
          Toast.makeText(MainActivity.this,openmessage, Toast.LENGTH_SHORT).show();
        }
        GRAPHIC_MODE=setting.getPref("VIEWMODE");
        
        //System service
        //inflater =(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
               
               
        GridView list = (GridView)findViewById(R.id.mainlist);
        int m_list[]={R.drawable.m_a,R.drawable.m_b,R.drawable.m_c,
        R.drawable.m_d,R.drawable.m_e,R.drawable.m_f};
        Adapter adapter = new Adapter(this,R.layout.row,m_list);
        list.setAdapter(adapter);
        
        final ImageView textEx=(ImageView)findViewById(R.id.t_exp); 
        //touch
        list.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                vibrator.vibrate(300);
                switch(position){
                    case 0:
                        textEx.setImageResource(R.drawable.m_ta);
                        gamestart();
                        break;
                    case 1:
                        textEx.setImageResource(R.drawable.m_tb);
                        //gamestart();
                        
                        break;
                    case 2:
                        textEx.setImageResource(R.drawable.m_tc);
                        //gamestart();
                        break;
                    case 3:
                        textEx.setImageResource(R.drawable.m_td);
                        //gamestart();
                        break;
                    case 4:
                        textEx.setImageResource(R.drawable.m_te);
                        //gamestart();
                        break;
                    case 5:
                        textEx.setImageResource(R.drawable.m_tf);
                        //gamestart();
                        break;
                    default:
                        break;
                }
            }
        });
        
        //vibration
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        
      }
    //TODO 설정에 따라 opengl사용유무 정하기 단 gridview가 정상호출되기위해 시작은 건들지말자
    final Handler handler = new Handler(){
        @Override public void handleMessage(Message msg){
            switch (msg.what) {
                case CHANGE_VIEW:
                    if(msg.arg1==SURFACEVIEW_MODE){
                        v = inflater.inflate(R.layout.gamelayout,null);
                        surfaceview = (SurfaceView)v.findViewById(R.id.surfaceview);
                        surfaceview.getHolder().addCallback(new SurfaceGameview(MainActivity.this));
                        loadstop();
                        setContentView(v);
                        loadview = (ImageView)v.findViewById(R.id.loadingE);
                    }else if(msg.arg1==OPENGLES_MODE){
                        mGLSurfaceview = new MainGLSurfaceview(MainActivity.this);
                        setContentView(mGLSurfaceview);
                    }else if(msg.arg1==LIBGDX_MODE){
                        loadview.setVisibility(View.VISIBLE);
                        Intent LibgdxStart = new Intent(MainActivity.this,AndroidLauncher.class);
                        //LibgdxStart.setComponent(new ComponentName("kr.snowfox.changepet", "kr.snowfox.changepet.AndroidLauncher"));
                        startActivity(LibgdxStart);
                        loadstop();
                        onPause();
                    }else{
                        Toast.makeText(getApplicationContext(),"실행할 엔진이 정의되지않았습니다.",Toast.LENGTH_LONG).show();
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
    public void onWindowFocusChanged(boolean hasFocus){
    }
    
    protected synchronized void gamestart(){
        //setContentView(R.layout.gamelayout);
        loadview = (ImageView)findViewById(R.id.loading);
        loading.start();
        Message message = handler.obtainMessage();
        message.what = CHANGE_VIEW;
        message.arg1 = GRAPHIC_MODE;
        handler.sendMessage(message);
        //loadview = (ImageView)v.findViewById(R.id.loading);
    }
    
    
    //TODO 안보이게 하는 기능 추가
    Thread loading = new Thread(new Runnable(){
         @Override
         public void run(){
             //cur =0;
             while(true){
                 
                 handler.post(new Runnable(){
                     @Override
                     public void run(){
                         //progressbar.setprogress(value);
                         loadview.setImageResource(images[cur]);
                         //Toast.makeText(getApplicationContext(),"수행.",Toast.LENGTH_LONG).show();
                         cur++;
                        if(cur>=images.length){
                            cur =0;
                         } 
                    }
                 });
                             
           
                 try{
                     Thread.sleep(300);
                 }catch(InterruptedException e){}
        }
    }});
    
    public void loadstop(){
        loading.interrupt();
        loadview.setVisibility(View.INVISIBLE);
    }
    

}

class Adapter extends BaseAdapter {
            Context context;
            int layout;
            int img[];
            LayoutInflater inf;
         
            public Adapter(Context context, int layout, int[] img) {
                this.context = context;
                this.layout = layout;
                this.img = img;
                inf = (LayoutInflater) context.getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
            }
         
            @Override
            public int getCount() {
                return img.length;
            }
         
            @Override
            public Object getItem(int position) {
                return img[position];
            }
         
            @Override
            public long getItemId(int position) {
                return position;
            }
         
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView==null)
                    convertView = inf.inflate(layout, null);
                ImageView iv = (ImageView)convertView.findViewById(R.id.rowimg);
                iv.setImageResource(img[position]);
         
                return convertView;
            }
        }
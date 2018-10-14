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
import android.content.Context;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
//해상도 가로모드에서 세로 360 가로 640
public class MainActivity extends Activity {
    
    static final public int CHANGE_VIEW = 1;
    int SURFACEVIEW_MODE = 0;
    int OPENGLES_MODE = 1;
    static int GRAPHIC_MODE=1;
    private MainGLSurfaceview mGLSurfaceview;
    Vibrator vibrator;
    Sharedpref setting;
    SurfaceView surfaceview;
    ImageView loadview;
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
        Message message = handler.obtainMessage();
        message.what = CHANGE_VIEW;
        message.arg1 = GRAPHIC_MODE;
        handler.sendMessage(message);
        
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
                    if(msg.arg1==OPENGLES_MODE){
                        mGLSurfaceview = new MainGLSurfaceview(MainActivity.this);
                        setContentView(mGLSurfaceview);
                    }else{
                        
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
    
    protected void gamestart(){
        //setContentView(R.layout.gamelayout);
        //System service
        //LayoutInflater inflater =(LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = inflater.inflate(R.layout.gamelayout,null);
        //loadview = (ImageView)v.findViewById(R.id.loading);
        loadview = (ImageView)findViewById(R.id.loading);
        loading.run();
        //surfaceview = (SurfaceView)v.findViewById(R.id.surfaceview);
        //surfaceview.getHolder().addCallback(new SurfaceGameview(MainActivity.this));
        //setContentView(v);
        loadstop();
    }
    
    
    //TODO 안보이게 하는 기능 추가
    Thread loading = new Thread(new Runnable(){
         final int images[]={R.drawable.load_a,R.drawable.load_b,R.drawable.load_c,R.drawable.load_d,R.drawable.load_e,R.drawable.load_f,R.drawable.load_g,R.drawable.load_h};
         int cur=0;
         @Override
         public void run(){
             loadview.setVisibility(View.VISIBLE);
             while(true){
                 handler.post(new Runnable(){
                     @Override
                     public void run(){
                         //progressbar.setprogress(value);
                         loadview.setImageResource(images[cur]);
                     }
                 });
                 try{
                     Thread.sleep(100);
                     cur++;
                 }catch(InterruptedException e){}
                 if(cur>=images.length){
                     cur =0;
                 }
             }
        }
    });
    
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
package kr.snowfox.changepet;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.content.Intent;

public class Intro extends Activity{
    protected ImageView introimg;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        
        //Bundle extras = getIntent().getBooleanExtra("finish",true);
        
        
        introimg = (ImageView)findViewById(R.id.intro);
        
        Animation translate = AnimationUtils.loadAnimation(this,R.anim.alpha);
        translate.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
                //introimg.setVisibility(View.VISIBLE);
                
            }           
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }           
            @Override
            public void onAnimationEnd(Animation arg0) {

                //intro1.putBooleanExtra("finish",false);
                //intent.putExtra("isfinish",String.valueOf(editText.getText()));
                Intent mainactivity = new Intent(Intro.this,MainActivity.class);
                startActivity(mainactivity);
                onPause();
            }
        });
        introimg.startAnimation(translate);
        
    }
    
    
      public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
 
        // 배경이미지로 등록된 Drawable을 가져와 애니메이션을 시작합니다.
        /* AnimationDrawable adAnimatedDrawable =
            (AnimationDrawable) mIvAnimation.getBackground();
        adAnimatedDrawable.start();     */
         
        
    }
    
    public void onPause() {
        super.onPause();
        // Remove the activity when its off the screen
        finish();
     }
 }
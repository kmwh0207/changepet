package kr.snowfox.changepet;

import android.content.SharedPreferences;
import android.content.Context;

public class Sharedpref {
    //public Sharedpref minstance;
    
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    // 값 불러오기
    private Sharedpref(String name,Context context){
        pref = context.getSharedPreferences(name,context.MODE_PRIVATE);
        editor = pref.edit();
    }
    
    protected static Sharedpref getInstance(String name, Context context){
        /*
           if(minstance ==null){
         
            minstance = new Sharedpref(name, context);
        }
        */
        return new Sharedpref(name, context);
    }
        
    public int getPref(String name){
        return pref.getInt(name,2);
    }
     
    // 값 저장하기
    public void setPref(String name, int num){
        editor.putInt(name, num);
        editor.commit();
    }
     
    // 값(Key Data) 삭제하기
    public void removePref(String name){
        editor.remove(name);
        editor.commit();
    }
     
    // 값(ALL Data) 삭제하기
    public void removeAllPref(){
        editor.clear();
        editor.commit();
    }
}
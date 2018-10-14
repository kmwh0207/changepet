package kr.snowfox.changepet;

import android.util.Log;

public class UpdateThread extends Thread {

        public UpdateThread() {
            // 초기화 작업
        }

        public void run() {

            int time = 0;

            while (true) {
                time++;
                try {
                    // 스레드에게 수행시킬 동작들 구현
                    Thread.sleep(1000); // 1초간 Thread를 잠재운다
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.i("경과된 시간 : ", Integer.toString(time));
            }
        }
    }

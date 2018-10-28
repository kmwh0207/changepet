package kr.snowfox.changepet;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import kr.snowfox.changepet.Maingame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.game);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Maingame(), config);
		//initializeForView(new Maingame(), config);
	}
}

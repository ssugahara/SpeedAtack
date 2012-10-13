package co.jp.digitalvision.speedattack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SpeedAttackMain extends Activity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btn = (Button)findViewById(R.id.startBtn);
	    btn.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
			    // TODO Auto-generated method stub
			    // インテントのインスタンス生成
			    Intent intent = new Intent(SpeedAttackMain.this, SpeedAttackActivity.class);
			    // 次画面のアクティビティ起動
			    startActivity(intent);
		    }
	    });
    }
}

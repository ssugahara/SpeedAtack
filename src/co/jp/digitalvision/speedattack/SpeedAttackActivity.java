package co.jp.digitalvision.speedattack;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class SpeedAttackActivity extends Activity {
	/** 1～25の整数配列 */
	private static final Map<Integer, Integer> NUMBER_VALUE = new HashMap<Integer, Integer>();
	private final int fp = ViewGroup.LayoutParams.MATCH_PARENT;
	private final int wc = ViewGroup.LayoutParams.WRAP_CONTENT;
	private TableLayout table;
	private static int NUMBER_COUNT = 1;
	private static int MAX_BUTTON_NUM = 25;

	static{
		for( int i = 0; i < MAX_BUTTON_NUM; i++){
			NUMBER_VALUE.put(i, i);
		}
	}

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Random	r = new Random();
        int key;
        int value;
        int count = 0;
        // ボタンを生成
        Button btn[] = new Button[MAX_BUTTON_NUM];
        LinearLayout lla = new LinearLayout(this);
		lla.setOrientation(LinearLayout.VERTICAL);
		setContentView(lla);
		table = new TableLayout(this);

		/**============================================================================
		 * タイマー処理開始
		 =============================================================================*/
		final Chronometer chronometer = new Chronometer( this );
		chronometer.setBase(SystemClock.elapsedRealtime());
		chronometer.start();

		/**============================================================================
		 * ランダムにボタンを表示する
		 =============================================================================*/
        for(;;){
        	key = r.nextInt(MAX_BUTTON_NUM);
        	if( NUMBER_VALUE.containsKey( key ) ){
            	value = NUMBER_VALUE.get( key );
            	NUMBER_VALUE.remove( key );
            	btn[count] = new Button( this );
	            btn[count].setText( String.valueOf( (value + 1) ) );
	            btn[count].setBackgroundColor( Color.argb(127, 0, 63, 255 ) );
	           	btn[count].setPadding(7, 15, 7, 15);
	            btn[count].setOnClickListener( new OnClickListener() {
					public void onClick( View v ) {
						Button btn1 = ( Button )v;
						int count = Integer.valueOf( btn1.getText().toString() );
						if(NUMBER_COUNT == count){
							v.setVisibility( View.INVISIBLE );
							if( NUMBER_COUNT == MAX_BUTTON_NUM ){
								// タイマー停止
								chronometer.stop();
								// 処理時間を計算する
								AlertDialog.Builder builder = new AlertDialog.Builder( SpeedAttackActivity.this );
								builder.setMessage( "あなたのレコードは [" + getLapsedTime((SystemClock.elapsedRealtime() - (chronometer.getBase()))) + "秒]かかりました")
								.setCancelable(false)
								.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int id) {
									    // TODO Auto-generated method stub
									    // インテントのインスタンス生成
									    Intent intent = new Intent(SpeedAttackActivity.this, SpeedAttackMain.class);
									    // 次画面のアクティビティ起動
									    startActivity(intent);
									}
								}).create().show();
							}
							NUMBER_COUNT++;
						}
					}
	            });
	            count++;
	        }
        	if( NUMBER_VALUE.isEmpty() ){
        		break;
        	}
        }

        /**
         *  tablerowを作成
         *  5行 x 5列
         */
        for(int i = 0; i < 5; i++){
        	TableRow row = new TableRow(this);
        	row.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        	// 5列
        	for(int j = 0; j < 5; j++){
                row.addView(btn[(i*5)+j]);
            }
            table.addView(row);
        }
        lla.addView(table, new TableLayout.LayoutParams(wc, fp));
		chronometer.start();
    }

    /**
     * 経過時間を取得する
     *
     * @return 経過時間 MM:SS:SSSフォーマット(※【MM】が【0】の場合は【MM】は削除する
     */
    private String getLapsedTime(long laptime){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTimeInMillis(laptime);

    	String format = "";
    	if(calendar.get(Calendar.HOUR) > 0){
    		format = String.valueOf( Calendar.HOUR ) + ":";
    	}
    	if(calendar.get(Calendar.MINUTE) > 0){
    		format += String.valueOf( Calendar.MINUTE ) + ":";
    	}
    	if(calendar.get(Calendar.SECOND) > 0){
    		format += String.valueOf( Calendar.SECOND ) + ".";
    	}
    	if(calendar.get(Calendar.MILLISECOND) > 0){
    		System.out.println( String.format("000",  String.valueOf( Calendar.MILLISECOND ) ) );
    		format += String.valueOf( Calendar.MILLISECOND );
    	}else{
    		format += "000";
    	}
    	return format;
    }

}
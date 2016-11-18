package st.asojuku.ac.jp.batterywarning;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        super.onResume();

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){



                    int scale = intent.getIntExtra("scale",0);
                    int level = intent.getIntExtra("level",0);


                    Log.i("scale",Integer.toString(scale));
                    Log.i("level",Integer.toString(level));

                    int batteryLevel = level*100 / scale;

                    if(batteryLevel == 15){
                        Notification.Builder builder = new Notification.Builder(context);

                        int notificationId = intent.getIntExtra("notificationId",0);

                        NotificationManager notificationManager =
                                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        builder.setSmallIcon(android.R.drawable.ic_dialog_info)
                                .setContentTitle("バッテリー残量")
                                .setContentText("バッテリー残量が"+batteryLevel+"％になりました")
                                .setPriority(Notification.PRIORITY_DEFAULT);

                        notificationManager.notify(notificationId,builder.build());

                        Log.i("通知","通知しました");
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(broadcastReceiver,filter);

        Log.i("バッテリー残量計測","バッテリー残量計測開始しました");
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(broadcastReceiver);

        Log.i("バッテリー残量計測","バッテリー残量計測終了しました");
    }
}

package com.aqeeb.batteryinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtBatteryStatus, txtBatteryPlug, txtBatteryHealth, txtBatteryVoltage, txtBatteryLevel, txtBatteryTemp, txtBatteryTech;
    IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Views
        txtBatteryStatus = (TextView) findViewById(R.id.txtStatus);
        txtBatteryPlug = (TextView) findViewById(R.id.txtPlug);
        txtBatteryHealth = (TextView) findViewById(R.id.txtHealth);
        txtBatteryVoltage = (TextView) findViewById(R.id.txtVoltage);
        txtBatteryLevel = (TextView) findViewById(R.id.txtLevel);
        txtBatteryTemp = (TextView) findViewById(R.id.txtTemp);
        txtBatteryTech = (TextView) findViewById(R.id.txtTech);

        //Intent Filter
        intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

        final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Status
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                        status == BatteryManager.BATTERY_STATUS_FULL;
                if(isCharging)
                    txtBatteryStatus.setText("Battery Status: Charging");
                else
                    txtBatteryStatus.setText("Battery Status: Charged Full");

                //Power Plug
                int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean isCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB ||
                        chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
                if(isCharge)
                    txtBatteryPlug.setText("Power Source : USB");
                else
                    txtBatteryPlug.setText("Power Source : AC");

                //Level
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                float batteryPct = (level/(float)scale)*100;
                txtBatteryLevel.setText("Battery Level : "+batteryPct+" %");

                //Voltage
                int voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
                txtBatteryVoltage.setText("Battery Voltage : " +voltage+" mv");

                //Temperature
                int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
                txtBatteryTemp.setText("Battery Temperature: "+temp+" °C");

                //Technology
                String tech = intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
                txtBatteryTech.setText("Battery Technology : "+tech);

                //Health
                int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1);
                switch (health)
                {
                    case BatteryManager.BATTERY_HEALTH_COLD:
                        txtBatteryHealth.setText("Battery Health : COLD");
                        break;
                    case BatteryManager.BATTERY_HEALTH_DEAD:
                        txtBatteryHealth.setText("Battery Health : DEAD");
                        break;
                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        txtBatteryHealth.setText("Battery Health : GOOD");
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        txtBatteryHealth.setText("Battery Health : OVERHEAT");
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        txtBatteryHealth.setText("Battery Health : OVER VOLTAGE");
                        break;
                    case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                        txtBatteryHealth.setText("Battery Health : UNKNOWN");
                        break;
                    case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                        txtBatteryHealth.setText("Battery Health : FAILURE");
                        break;
                    default:
                        break;
                }
            }
        };

        //Call function
        MainActivity.this.registerReceiver(broadcastReceiver, intentFilter);
    }
}

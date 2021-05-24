package com.example.capstone_design.bluetooth;

import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone_design.MainActivity;
import com.example.capstone_design.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;


/*
* 앱 -> 라즈베리파이 전송 데이터
* 1. 사용자 uid(JWT token)
* 2. 와이파이 이름
* 3. 와이파이 비밀번호
* */

public class BluetoothActivity extends AppCompatActivity {

    private ImageView back_btn;
    private TextView registered_device_info;
    private Button paired_bluetooth_btn;
    private Button bluetooth_scan_btn;
    private ListView scaned_bluetooth_list;
    private Button register_device_btn;
    private EditText input_wifi_name;
    private EditText input_wifi_pwd;
    private EditText input_wifi_pin;


    private BluetoothAdapter btAdapter;
    private final static int REQUEST_ENABLE_BT = 1;
    BluetoothDevice device;
    BluetoothSocket btSocket = null;
    ConnectedThread connectedThread;
    Set<BluetoothDevice> pairedDevices;
    ArrayAdapter<String> btArrayAdapter;
    ArrayList<String> deviceAddressArray;

    String TAG = "HYEALS";
    UUID BT_MODULE_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); // 라즈베리파이/아두이노-안드로이드 블루투스 통신 프로토콜(SerialPortServiceClass_UUID)


    // 디바이스와 연결할 때 사용
    private String name=""; // 디바이스 이름
    private String address=""; // 디바이스 MAC 주소
    private boolean flag;

    // 브로드캐스트를 스캔할 때 등록하기 때문에(registerReceiver()) 스캔 안하고 뒤로 가기 눌렀을 때 onDestroy에서 unregisterReceiver를 할 때 등록되지 않은 리시버 오류가 발생
    // 해결 방안 -> flag값을 세워서 스캔 버튼을 눌렀을 때의 flag값일 때만 unregisterReceiver가 동작하도록 함
    private boolean bc_flag;

    // 라즈베리파이에 전송할 데이터
    String token;
    String wifi_name;
    String wifi_pwd;
    String wifi_pin;

    private SharedPreferences.Editor sharedEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        back_btn = findViewById(R.id.back_btn);
        registered_device_info = findViewById(R.id.registered_device_info);
        paired_bluetooth_btn = findViewById(R.id.paired_device_btn);
        bluetooth_scan_btn = findViewById(R.id.bluetooth_scan_btn);
        scaned_bluetooth_list = findViewById(R.id.scaned_bluetooth_list);
        register_device_btn = findViewById(R.id.register_device_btn);

        SharedPreferences saveShared = getSharedPreferences("DB", MODE_PRIVATE);
        sharedEditor = saveShared.edit();

        SharedPreferences loadShared = getSharedPreferences("DB", MODE_PRIVATE);

        // 뒤로가기 버튼
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
            }
        });

        token = loadShared.getString("token", "");

        // 블루투스 활성화
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        // 리스트뷰에 리스트 항목 연결
        deviceAddressArray = new ArrayList<>();
        btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        scaned_bluetooth_list.setAdapter(btArrayAdapter);


        // 블루투스 권한 확인
        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // 페어링 된 디바이스 목록 출력 버튼
        paired_bluetooth_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                btArrayAdapter.clear();
                if (deviceAddressArray != null && !deviceAddressArray.isEmpty()) {
                    deviceAddressArray.clear();
                }

                pairedDevices = btAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    for (BluetoothDevice device : pairedDevices) {
                        String deviceName = device.getName();
                        String deviceHardwareAddress = device.getAddress();

                        deviceAddressArray.contains(deviceHardwareAddress);
                        btArrayAdapter.add(deviceName);
                        deviceAddressArray.add(deviceHardwareAddress);

                    }
                }
            }
        });

        // 블루투스 검색 버튼
        bluetooth_scan_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (btAdapter.isDiscovering()) {
                    btAdapter.cancelDiscovery();
                } else {
                    if (btAdapter.isEnabled()) {
                        btAdapter.startDiscovery();
                        btArrayAdapter.clear();

                        if (deviceAddressArray != null && !deviceAddressArray.isEmpty()) {
                            deviceAddressArray.clear();
                        }

                        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                        registerReceiver(receiver, filter);
                        bc_flag = true;

                    } else {
                        Toast.makeText(getApplicationContext(), "bluetooth not on", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // 연결할 디바이스 선택 부분
        scaned_bluetooth_list.setOnItemClickListener(new customOnItemClickListener()); // 리스트 아이템 클릭 이벤트 주기

        // 디바이스에 데이터 전송 부분
        register_device_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                device = btAdapter.getRemoteDevice(address);

                // 소켓 생성 및 연결
                try{
                    btSocket = createBluetoothSocket(device);
                    btSocket.connect();
                }catch (Exception e){
                    flag = false;
                    registered_device_info.setText("블루투스 연결 실패");
                    e.printStackTrace();
                }
                if(flag){
                    registered_device_info.setText( "'" + name + "'");
                    connectedThread = new ConnectedThread(btSocket);

                    sharedEditor.putString("PIN", "등록된 PIN 번호: " + wifi_pin);
                    sharedEditor.commit();

                    connectedThread.start();
                }
                if(connectedThread!=null){
                    connectedThread.write("from_app/" + token + "/" + wifi_name + "/" + wifi_pwd + "/" + wifi_pin);
                    //Log.d(TAG, " [데이터 보냄] " + "디바이스 이름: " + device.getName() + "/ 디바이스 UUID: "  + device.getUuids() + "/ UID: " + token + "/ WIFI NAME: " + wifi_name + "/ WIFI PWD: " + wifi_pwd);
                }
            }
        });

    }

    // 리스트 아이템 클릭 이벤트 리스너
    private class customOnItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            Toast.makeText(getApplicationContext(), btArrayAdapter.getItem(position), Toast.LENGTH_SHORT).show();

            //registered_device_info.setText("try...");

            name = btArrayAdapter.getItem(position); // 디바이스 이름 얻어오는 부분
            address = deviceAddressArray.get(position); // 디바이스 MAC 주소 얻어오는 부분
            flag = true;

            Log.d(TAG, "name: " + name +  " " + "address: " + address);

            View v = getLayoutInflater().inflate(R.layout.send_wifi_info, null);

             //WIFI 입력 다이얼로그 띄우기
            AlertDialog.Builder dialog = new AlertDialog.Builder(BluetoothActivity.this);
                    dialog.setTitle("디바이스와 연결할 WIFI 정보 입력");
                    dialog.setView(v)
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                                input_wifi_name = v.findViewById(R.id.input_wifi_name);
                                input_wifi_pwd = v.findViewById(R.id.input_wifi_pwd);
                                input_wifi_pin = v.findViewById(R.id.input_wifi_pin);
                                wifi_name = input_wifi_name.getText().toString();
                                wifi_pwd = input_wifi_pwd.getText().toString();
                                wifi_pin = input_wifi_pin.getText().toString();

                        }
                    })
                    .setNegativeButton(
                            "취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });

                    dialog.show();

        }
    }


    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getName()!=null && device.getAddress()!=null){
                    String deviceName = device.getName();
                    String deviceHardwareAddress = device.getAddress(); // MAC address

                    if(!deviceAddressArray.contains(deviceHardwareAddress)){
                        btArrayAdapter.add(deviceName);
                        deviceAddressArray.add(deviceHardwareAddress);
                        btArrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", UUID.class);
            return (BluetoothSocket) m.invoke(device, BT_MODULE_UUID);
        } catch (Exception e) {
            Log.e(TAG, "Could not create Insecure RFComm Connection",e);
        }
        return  device.createRfcommSocketToServiceRecord(BT_MODULE_UUID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        if(bc_flag){
            unregisterReceiver(receiver);
        }

    }

}

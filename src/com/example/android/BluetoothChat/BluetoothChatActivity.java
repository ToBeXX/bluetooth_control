package com.example.android.BluetoothChat;
/**
 * 描述：此类为蓝牙调试助手主界面
 *  
 * 作用：接收发送蓝牙消息、建立蓝牙通信连接、打印蓝牙消息
 */
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BluetoothChatActivity extends Activity {
    // 调试
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;

    //从BluetoothChatService发送处理程序的消息类型
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private TextView mTitle;
    private ListView mConversationView;
    Button content;
Button chukoudakaibtn,chukouguanbibtn;
Button ershidakaibtn,ershiguanbibtn;
Button shierdakaibtn,shierguanbibtn;
Button lingdakaibtn,lingguanbibtn;
    // 连接设备的名称
    private String mConnectedDeviceName = null;
    private ArrayAdapter<String> mConversationArrayAdapter;
    
    private StringBuffer mOutStringBuffer;
    // 本地蓝牙适配器
    private BluetoothAdapter mBluetoothAdapter = null;
    // 成员对象聊天服务
    private BluetoothChatService mChatService = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.e(TAG, "+++ ON CREATE +++");  

        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.custom_title);

        mTitle = (TextView) findViewById(R.id.title_left_text);
        mTitle.setText(R.string.app_name);
        mTitle = (TextView) findViewById(R.id.title_right_text);

        chukoudakaibtn=(Button)findViewById(R.id.openbutton1);
        chukouguanbibtn=(Button)findViewById(R.id.closebutton1);
        ershidakaibtn=(Button)findViewById(R.id.openbutton2);
        ershiguanbibtn=(Button)findViewById(R.id.closebutton2);
        shierdakaibtn=(Button)findViewById(R.id.openbutton3);
        shierguanbibtn=(Button)findViewById(R.id.closebutton3);
        lingdakaibtn=(Button)findViewById(R.id.openbutton4);
        lingguanbibtn=(Button)findViewById(R.id.closebutton4);
        
        chukoudakaibtn.setOnClickListener(new chukoukai());
        chukouguanbibtn.setOnClickListener(new chukouguan());
        shierdakaibtn.setOnClickListener(new shierkai());
        shierguanbibtn.setOnClickListener(new shierguan());
        lingdakaibtn.setOnClickListener(new lingkai());
        lingguanbibtn.setOnClickListener(new lingguan());
        ershidakaibtn.setOnClickListener(new ershikai());
        ershiguanbibtn.setOnClickListener(new ershiguan());

                // 获取本地蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // 判断蓝牙是否可用
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "蓝牙是不可用的", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        content=(Button)findViewById(R.id.maincontentbtn);
        content.setOnClickListener(new content());

    	}

        



   

	class chukoukai implements OnClickListener
	{
		@Override
		public void onClick(View v) {	
			sendMessage("a");
		
		}
		
		
	}
	
	class chukouguan implements OnClickListener
	{
		@Override
		public void onClick(View v) {	
			sendMessage("b");
		
		}
		
		
	}
	
	class ershikai implements OnClickListener
	{
		@Override
		public void onClick(View v) {	
			sendMessage("c");
		
		}
		
		
	}
	
	class ershiguan implements OnClickListener
	{
		@Override
		public void onClick(View v) {	
			sendMessage("d");
		
		}
		
		
	}
	
	class shierkai implements OnClickListener
	{
		@Override
		public void onClick(View v) {	
			sendMessage("e");
		
		}
		
		
	}
	
	class shierguan implements OnClickListener
	{
		@Override
		public void onClick(View v) {	
			sendMessage("f");
		
		}
		
		
	}
	
	
	class lingkai implements OnClickListener
	{
		@Override
		public void onClick(View v) {	
			sendMessage("g");
		
		}
		
		
	}
	
	class lingguan implements OnClickListener
	{
		@Override
		public void onClick(View v) {	
			sendMessage("h");
		
		}
		
		
	}
	
	
    
    class content implements OnClickListener
    {

		@Override
		public void onClick(View v) {
			Intent serverIntent = new Intent(BluetoothChatActivity.this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
			
		}
    	
    	
    }

    @Override
    public void onStart() {
        super.onStart();
        if(D) Log.e(TAG, "++ ON START ++");

        // 判断蓝牙是否打开.
        // setupChat在onActivityResult()将被调用
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            if (mChatService == null) setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if(D) Log.e(TAG, "+ ON RESUME +");
        if (mChatService != null) {
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
              mChatService.start();
            }
        }
    }

  private void setupChat() {
        Log.d(TAG, "setupChat()");

        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
        mConversationView = (ListView) findViewById(R.id.in);
        mConversationView.setAdapter(mConversationArrayAdapter);
        // 初始化BluetoothChatService进行蓝牙连接
        mChatService = new BluetoothChatService(this, mHandler);

        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

		super.onDestroy();
        // 停止蓝牙
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
            BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * 发送消息
     * @param message  发送的内容
     */
    private void sendMessage(String message) {
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        if (message.length() > 0) {
            byte[] send = message.getBytes();
            mChatService.write(send);
        }
    }

    // 回车键监听
    private TextView.OnEditorActionListener mWriteListener =
        new TextView.OnEditorActionListener() {
        public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                String message = view.getText().toString();
                sendMessage(message);
            }
            if(D) Log.i(TAG, "END onEditorAction");
            return true;
        }
    };

    // 此Handler处理BluetoothChatService传来的消息
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MESSAGE_STATE_CHANGE:
                if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                case BluetoothChatService.STATE_CONNECTED:
                    mTitle.setText(R.string.title_connected_to);
                    mTitle.append(mConnectedDeviceName);
                    mConversationArrayAdapter.clear();
                    break;
                case BluetoothChatService.STATE_CONNECTING:
                    mTitle.setText(R.string.title_connecting);
                    break;
                case BluetoothChatService.STATE_LISTEN:
                case BluetoothChatService.STATE_NONE:
                    mTitle.setText(R.string.title_not_connected);
                    break;
                }
                break;
            case MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                String writeMessage = new String(writeBuf);
                mConversationArrayAdapter.add("Me:  " + writeMessage);
                break;
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // 读取到的数据
                String readMessage = new String(readBuf, 0, msg.arg1);
                mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                break;
            case MESSAGE_DEVICE_NAME:
                // 保存连接设备的名字
                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                Toast.makeText(getApplicationContext(), "连接到"
                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case MESSAGE_TOAST:
                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                               Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
        case REQUEST_CONNECT_DEVICE:
            // 当DeviceListActivity返回与设备连接的消息
            if (resultCode == Activity.RESULT_OK) {
                // 得到链接设备的MAC
                String address = data.getExtras()
                                     .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                // 得到BLuetoothDevice对象
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                // 试图连接到设备
                mChatService.connect(device);
            }
            break;
        case REQUEST_ENABLE_BT:
            // 判断蓝牙是否启用
            if (resultCode == Activity.RESULT_OK) {
                // 建立连接
                setupChat();
            } else {
                Log.d(TAG, "蓝牙未启用");
                Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        case R.id.discoverable:
            // 允许被发现设备
            ensureDiscoverable();
            return true;
        }
        return false;
    }

}
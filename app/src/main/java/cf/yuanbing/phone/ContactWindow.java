package cf.yuanbing.phone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class ContactWindow extends AppCompatActivity implements View.OnClickListener {
    private String telNumber;
    private String contactName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_window);
        Intent intent = getIntent();
        telNumber = intent.getStringExtra("TelNumber");
        contactName = intent.getStringExtra("Name");
        Toolbar toolbar = findViewById(R.id.tb_dialog);
        if (contactName == null || "".equals(contactName)) {
            toolbar.setTitle(telNumber);
        } else {
            toolbar.setTitle(contactName);
        }
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        Button buttonCall = findViewById(R.id.btn_make_call);
        buttonCall.setOnClickListener(this);
        Button buttonCancel = findViewById(R.id.btn_cancel);
        buttonCancel.setOnClickListener(this);
        Button buttonMessage = findViewById(R.id.btn_send_message);
        buttonMessage.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_make_call:
                makeCall();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_send_message:
                sendMessage();
                break;
            default:
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call();
                    finish();
                } else {
                    Toast.makeText(this, "你取消了授权", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    send();
                    finish();
                } else {
                    Toast.makeText(this, "你取消了授权", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }
    private void makeCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            call();
            finish();
        }
    }
    private void call() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + telNumber));
        startActivity(intent);
    }
    private void sendMessage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, 2);
        } else {
            send();
            finish();
        }
    }
    private void send() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:" + telNumber));
        startActivity(intent);
    }
}

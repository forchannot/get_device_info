package com.copy.copy_device_info;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.DeviceIdentifier;
import com.github.gzuliyujiang.oaid.IGetter;
import com.google.gson.JsonObject;

public class GetDeviceInfoActivity extends AppCompatActivity
        implements ActivityResultCallback<Boolean> {
    JsonObject json = new JsonObject();
    int random = (int) ((Math.random() * 9 + 1) * 100000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener(v -> obtainDeviceId());
    }

    @Override
    public void onActivityResult(Boolean result) {
        obtainDeviceId();
    }

    private void obtainDeviceId() {
        String model = Build.MODEL;
        String version = Build.VERSION.RELEASE;
        String fingerprint = Build.FINGERPRINT;
        String device = Build.DEVICE;
        String board = Build.BOARD;
        String product = Build.PRODUCT;
        json.addProperty("deviceModel", model);
        json.addProperty("androidVersion", version);
        json.addProperty("deviceFingerprint", fingerprint);
        json.addProperty("deviceName", device);
        json.addProperty("deviceBoard", board);
        json.addProperty("deviceProduct", product);
        AlertDialog.Builder builder = new AlertDialog.Builder(GetDeviceInfoActivity.this);
        DeviceIdentifier.getOAID(this);
        // 获取OAID/AAID，异步回调
        builder.setTitle("设备信息");
        DeviceID.getOAID(
                this,
                new IGetter() {
                    @Override
                    public void onOAIDGetComplete(String result) {
                        handleResult(result);
                    }

                    @Override
                    public void onOAIDGetError(Exception error) {
                        handleResult("error_" + random);
                    }

                    private void handleResult(String oaidResult) {
                        String message =
                                String.format(
                                        "设备型号：%s\n系统版本：%s\n设备指纹：%s\n设备名：%s\n主板名：%s\n产品名：%s\nOAID：%s",
                                        model,
                                        version,
                                        fingerprint,
                                        device,
                                        board,
                                        product,
                                        oaidResult);
                        json.addProperty("oaid", oaidResult);
                        builder.setMessage(message)
                                .setPositiveButton("点击复制", (dialog, which) -> copyToClipboard())
                                .show();
                    }

                    private void copyToClipboard() {
                        ClipboardManager clipboard =
                                (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("deviceInfo", json.toString());
                        if (clipboard != null) {
                            clipboard.setPrimaryClip(clip);
                            Toast.makeText(
                                            GetDeviceInfoActivity.this,
                                            "信息已复制到剪贴板",
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }
}

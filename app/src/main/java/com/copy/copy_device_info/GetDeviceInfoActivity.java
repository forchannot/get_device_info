package com.copy.copy_device_info;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.flayone.oaid.MyOAID;
import com.google.gson.JsonObject;

public class GetDeviceInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JsonObject json = new JsonObject();

        String model = Build.MODEL;
        String version = Build.VERSION.RELEASE;
        String fingerprint = Build.FINGERPRINT;
        String device = Build.DEVICE;
        String board = Build.BOARD;
        String product = Build.PRODUCT;
        MyOAID.getOAID(
                this,
                oaid -> {
                    try {
                        json.addProperty("oaid", oaid);
                        json.addProperty("deviceModel", model);
                        json.addProperty("androidVersion", version);
                        json.addProperty("deviceFingerprint", fingerprint);
                        json.addProperty("deviceName", device);
                        json.addProperty("deviceBoard", board);
                        json.addProperty("deviceProduct", product);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                });
        Button myButton = findViewById(R.id.myButton);
        myButton.setOnClickListener(
                v -> {
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(GetDeviceInfoActivity.this);
                    builder.setTitle("设备信息");
                    builder.setMessage(
                            String.format(
                                    "设备型号：%s\n系统版本：%s\n设备指纹：%s\n设备名：%s\n主板名：%s\n产品名：%s\nOAID：%s",
                                    model,
                                    version,
                                    fingerprint,
                                    device,
                                    board,
                                    product,
                                    json.get("oaid").getAsString()));
                    builder.setPositiveButton(
                            "点击复制",
                            (dialog, which) -> {
                                // 复制JSON到剪贴板
                                ClipboardManager clipboard =
                                        (ClipboardManager)
                                                getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clip =
                                        ClipData.newPlainText("deviceInfo", json.toString());
                                if (clipboard != null) {
                                    clipboard.setPrimaryClip(clip);
                                    Toast.makeText(
                                                    GetDeviceInfoActivity.this,
                                                    "信息已复制到剪贴板",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                                // 关闭对话框
                                dialog.dismiss();
                            });
                    builder.show();
                });
    }
}

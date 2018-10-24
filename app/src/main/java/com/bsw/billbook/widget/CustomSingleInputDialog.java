package com.bsw.billbook.widget;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bsw.billbook.R;
import com.bsw.billbook.utils.TxtUtils;
import com.zxycloud.zxwl.R;
import com.zxycloud.zxwl.utils.TxtUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author leiming
 * @date 2018/04/02
 */
public class CustomSingleInputDialog {

    private Context context;
    private AlertDialog myDialog;

    private EditText gatewayIdInput;

    private OnCustomSingleInputListener listener;
    private String userPhone;

    public static CustomSingleInputDialog show(Context context, OnCustomSingleInputListener listener) {
        return new CustomSingleInputDialog(context, listener);
    }

    private CustomSingleInputDialog(Context context, OnCustomSingleInputListener listener) {
        this.context = context;
        this.listener = listener;
        initDialog();
    }

    @SuppressLint("InflateParams")
    private void initDialog() {
        myDialog = new AlertDialog.Builder(context).create();
        myDialog.setView(LayoutInflater.from(context).inflate(R.layout.dialog_single_input_layout, null));
        myDialog.show();

        Window window = myDialog.getWindow();

        assert window != null;

        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        window.findViewById(R.id.dialog_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });

        window.findViewById(R.id.dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                listener.getResult("");
            }
        });

        gatewayIdInput = window.findViewById(R.id.gateway_id_input);
    }

    /**
     * 显示提示文本
     *
     * @param message 显示信息的资源ID
     */
    private void toast(int message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    private void confirm() {
        Map<String, String> params = new HashMap<>();
        String gatewayName = TxtUtils.getText(gatewayIdInput);
        if (TextUtils.isEmpty(gatewayName)) {
            toast(R.string.information_incomplete);
            return;
        }
        listener.getResult(gatewayName);
        myDialog.dismiss();
    }

    public void dismiss() {
        myDialog.dismiss();
    }

    public interface OnCustomSingleInputListener {
        void getResult(String gatewayName);
    }
}

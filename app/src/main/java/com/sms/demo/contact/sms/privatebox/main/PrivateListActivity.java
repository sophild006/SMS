package com.sms.demo.contact.sms.privatebox.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.sms.demo.R;
import com.sms.demo.contact.sms.dialog.ChooseDialog;
import com.sms.demo.contact.sms.privatebox.pwd.PrivateBoxPwdActivity;
import com.sms.demo.contact.sms.privatebox.bean.ThreadIdMsg;
import com.sms.demo.contact.sms.privatebox.db.DbUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */

public class PrivateListActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private PrivateAdapter mAdapter;
    private ImageView ivAddPrivate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_private_list);
        Intent intent = new Intent(this, PrivateBoxPwdActivity.class);
        intent.putExtra("mode_pwd", 2);
        startActivityForResult(intent, 10);
        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listview);
        ivAddPrivate = (ImageView) findViewById(R.id.iv_private_add);
        List<ThreadIdMsg> sms = DbUtil.getInstance().getSms();
        if (sms != null) {
            Log.d("wwq", "sms: " + sms.toString());
        }
        mAdapter = new PrivateAdapter(this, sms);
        listView.setAdapter(mAdapter);

        initEvent();
    }

    private void initEvent() {
        ivAddPrivate.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10:
                break;
            case 20:
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.iv_private_add:
                showChooseMenuDialog();
                break;
        }
    }

    private void showChooseMenuDialog() {
        final ChooseDialog backdialog = new ChooseDialog(this);
        backdialog.getWindow().setLayout((int) getResources().getDimension(R.dimen.with),
                (int) getResources().getDimension(R.dimen.medium_hight));
        backdialog.show();
    }

    public class PrivateTask extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }
    }
}

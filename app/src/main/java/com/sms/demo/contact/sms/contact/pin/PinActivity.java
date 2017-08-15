package com.sms.demo.contact.sms.contact.pin;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.sms.demo.R;
import com.sms.demo.contact.sms.contact.ContactData;
import com.sms.demo.contact.sms.contact.ParentContactData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PinActivity extends AppCompatActivity {

    private Map<Integer, List<String>> maps = new HashMap<>();

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_ping);
        PinnedHeaderListView listView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
//        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        RelativeLayout header1 = (RelativeLayout) inflator.inflate(R.layout.list_item, null);
//        ((TextView) header1.findViewById(R.id.tv_name)).setText("HEADER 1");
//        RelativeLayout header2 = (RelativeLayout) inflator.inflate(R.layout.list_item, null);
//        ((TextView) header2.findViewById(R.id.tv_name)).setText("HEADER 2");
//        RelativeLayout footer = (RelativeLayout) inflator.inflate(R.layout.list_item, null);
//        ((TextView) footer.findViewById(R.id.tv_name)).setText("FOOTER");
//        listView.addHeaderView(header1);
//        listView.addHeaderView(header2);
//        listView.addFooterView(footer);
        List<ParentContactData> parentContactDatas = new ArrayList<>();
        List<ContactData> listData = new ArrayList<>();
        getAllContact(listData);
        Collections.sort(listData, new PinyinComparator());
        for (int i = 0; i < listData.size(); i++) {
            ParentContactData pData = new ParentContactData();
            pData.setLetter(listData.get(i).sortKey);
            List<ContactData> dList = new ArrayList<ContactData>();
            dList.add(listData.get(i));
            while ((i + 1) < listData.size()
                    && listData.get(i + 1).sortKey.equals(listData.get(i).sortKey)) {
                dList.add(listData.get(i + 1));
                i++;
            }
            pData.setData(dList);
            parentContactDatas.add(pData);
        }

        ContactSectionedAdapter sectionedAdapter = new ContactSectionedAdapter(parentContactDatas);
        listView.setAdapter(sectionedAdapter);
        listView.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
                Log.d("wwq", "position: " + position);
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {
                Log.d("wwq", "section: " + section);
            }
        });
    }

    public class PinyinComparator implements Comparator<ContactData> {

        @Override
        public int compare(ContactData o1, ContactData o2) {
            //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
            if (o2.getSortKey().equals("#")) {
                return -1;
            } else if (o1.getSortKey().equals("#")) {
                return 1;
            } else {
                return o1.getSortKey().compareTo(o2.getSortKey());
            }
        }
    }


    private static final String[] MYPROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID};

    /**
     * see http://blog.chinaunix.net/uid-26930580-id-4137246.html
     *
     * @param listMembers
     */
    public void getAllContact(List<ContactData> listMembers) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, MYPROJECTION,
                null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY);
        try {

            if (cursor.moveToFirst()) {
                do {
                    ContactData contact = new ContactData();
                    String name = cursor.getString(0);
                    String sortKey = getSortKey(cursor.getString(1));
                    String contact_phone = cursor.getString(2);
                    int contact_id = cursor.getInt(3);
                    contact.name = name;
                    String pinyin = CharacterParser.getInstance().getSelling(name);
                    String sortString = pinyin.substring(0, 1).toUpperCase();
                    // 正则表达式，判断首字母是否是英文字母
                    if (sortString.matches("[A-Z]")) {
                        contact.sortKey = (sortString.toUpperCase());
                    } else {
                        contact.sortKey = "#";
                    }
//                    contact.sortKey = sortKey;
                    contact.phone = contact_phone;
                    contact.setId(contact_id);
//                    if (name != null)
                    listMembers.add(contact);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            Log.d("wwq", "list:  " + listMembers.toString());
        }
    }

    /**
     * 获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
     *
     * @param sortKeyString 数据库中读取出的sort key
     * @return 英文字母或者#
     */
    private static String getSortKey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        }
        return "#";
    }
}

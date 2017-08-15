package com.sms.demo.contact.sms.contact;

import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */

public class ParentContactData {

    public String letter;
    private List<ContactData> data;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public List<ContactData> getData() {
        return data;
    }

    public void setData(List<ContactData> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ParentContactData{" +
                "letter='" + letter + '\'' +
                ", data=" + data +
                '}';
    }
}

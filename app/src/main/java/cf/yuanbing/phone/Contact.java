package cf.yuanbing.phone;

import android.graphics.Bitmap;

public class Contact {
    private String contactName;
    private String callTime;
    private String callTimes;
    private int callType;
    private String telNumber;
    private Bitmap contactHead = null;

    public Bitmap getContactHead() {
        return contactHead;
    }

    public void setContactHead(Bitmap contactHead) {
        this.contactHead = contactHead;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int callType) {
        this.callType = callType;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallTimes() {
        return callTimes;
    }

    public void setCallTimes(String callTimes) {
        this.callTimes = callTimes;
    }
}

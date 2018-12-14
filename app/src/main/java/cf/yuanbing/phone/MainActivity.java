package cf.yuanbing.phone;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ArrayList<Contact> callIn = new ArrayList<>();
    static ArrayList<Contact> callMiss = new ArrayList<>();
    static ArrayList<Contact> callOut = new ArrayList<>();
    private Bitmap head = null;
    private String contactNumber;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CALL_LOG}, 1);
        } else {
            getContactInfo();
            replaceFragment(new CallInFragment());
        }
        setFragment();
    }



    private void setFragment() {
        TabLayout tabLayout = findViewById(R.id.tl_tab);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        replaceFragment(new CallInFragment());
                        break;
                    case 1:
                        replaceFragment(new CallOutFragment());
                        break;
                    case 2:
                        replaceFragment(new CallMissFragment());
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fg_contact_log_list, fragment);
        fragmentTransaction.commit();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getContactInfo() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String contactName = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    String contactNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    this.contactNumber= contactNumber;
//                    getBitmapHead();
                    Contact contact = new Contact();
                    contact.setTelNumber(contactNumber);  // 记录联系人的号码
                    contact.setContactName(contactName);  // 记录联系人姓名
                    contact.setCallTime(duration);  // 记录通话时长
                    contact.setCallTimes(date);  // 记录通话的日期
                    contact.setCallType(type);  // 记录通话记录的类型
                    contact.setContactHead(head);
                    switch (contact.getCallType()) {
                        case 1:
                            callIn.add(contact);
                            break;
                        case 2:
                            callOut.add(contact);
                            break;
                        case 3:
                            callMiss.add(contact);
                        default:
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getContactInfo();
                    replaceFragment(new CallInFragment());
                } else {
                    Toast.makeText(this, "你取消了授权", Toast.LENGTH_SHORT).show();
                }
                break;
//            case 2:
//                if (/*grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED*/true) {
//                    findPhoto();
//                    replaceFragment(new CallInFragment());
//                } else {
//                    Toast.makeText(this, "你取消了授权", Toast.LENGTH_SHORT).show();
//                }
//                break;
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void getBitmapHead() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) ==
//                PackageManager.PERMISSION_GRANTED) {
//            findPhoto();
//        } else {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS}, 2);
//        }
//    }
//    private void findPhoto() {
//        Cursor cursor1 = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null,
//                null, null, null);
//        int contactId = 0;
//        while (cursor1.moveToNext()) {
//            int rawContactId = cursor1.getInt(cursor1.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));
//            String contactNumber = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Data.DATA4));
//            if (this.contactNumber.equals(contactNumber)) {
//                contactId = rawContactId;
//            }
//        }
//        Cursor cursor2 = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null,
//                null, null, null);
//        while (cursor2.moveToNext()) {
//            Log.e("TAG", "=======================================" + cursor2.getInt(cursor2.getColumnIndex(ContactsContract.Contacts.PHOTO_ID)) + contactId);
//            if (contactId == cursor2.getInt(cursor2.getColumnIndex(ContactsContract.Contacts._ID))) {
//                byte[] bytes = cursor2.getBlob(cursor2.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
//                if (bytes != null) {
//                    head = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                }
//                Log.e("TAG", "=======================================" + cursor2.getInt(cursor2.getColumnIndex(ContactsContract.Contacts.PHOTO_ID)) + contactId);
//            }
//        }
//    }
}

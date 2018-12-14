package cf.yuanbing.phone;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private ArrayList<Contact> contact;
    private ArrayList<View> items = new ArrayList<>();
    public ContactAdapter(ArrayList<Contact> contact) {
        this.contact = contact;
    }
    static class ContactViewHolder extends RecyclerView.ViewHolder {
        private ImageView contactHead;
        private TextView contactName;
        private TextView callTime;
        private TextView callTimes;
        private ImageView callType;
        public ContactViewHolder(View view) {
            super(view);
            contactHead = view.findViewById(R.id.iv_contact_head);
            contactName = view.findViewById(R.id.tv_contact_name);
            callTime = view.findViewById(R.id.tv_call_time);
            callTimes = view.findViewById(R.id.tv_call_times);
            callType = view.findViewById(R.id.iv_call_type);
        }
    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull final ViewGroup viewGroup, final int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View view = layoutInflater.inflate(R.layout.contact_item, viewGroup, false);
        final ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(viewGroup.getContext(), ContactWindow.class);
                intent.putExtra("TelNumber", contact.get(contactViewHolder.getAdapterPosition()).getTelNumber());
                intent.putExtra("Name", contact.get(contactViewHolder.getAdapterPosition()).getContactName());
                viewGroup.getContext().startActivity(intent);
            }
        });
        return contactViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int position) {
        if (contact.get(position).getContactHead() != null) {
            contactViewHolder.contactHead.setImageBitmap(contact.get(position).getContactHead());// 设置联系人头像
        } else {
            contactViewHolder.contactHead.setImageResource(R.drawable.header);
        }
        if (contact.get(position).getContactName() == null ||
                "".equals(contact.get(position).getContactName())) {
            contactViewHolder.contactName.setText(contact.get(position).getTelNumber());  // 设置联系人姓名
        } else {
            contactViewHolder.contactName.setText(contact.get(position).getContactName());  // 设置联系人姓名
        }
        contactViewHolder.callTimes.setText(stampToDate(contact.get(position)
                .getCallTimes(), "HH:mm"));  // 设置通话时间
        switch (contact.get(position).getCallType()) {
            case 1:
                contactViewHolder.callType.setImageResource(R.drawable.call_in);
                contactViewHolder.callTime.setText("呼入" + contact.get(position).getCallTime() + "s");  // 设置通话时长
                break;
            case 2:
                contactViewHolder.callType.setImageResource(R.drawable.call_out);
                contactViewHolder.callTime.setText("呼出" + contact.get(position).getCallTime() + "s");  // 设置通话时长
                break;
            case 3:
                contactViewHolder.callType.setImageResource(R.drawable.call_miss);
                contactViewHolder.callTime.setText("未接");
                break;
            default:
        }
    }
    @Override
    public int getItemCount() {
        return contact.size();
    }
    private String stampToDate(String s, String pattern){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    public ArrayList<Contact> getDataList() {
        return contact;
    }
}

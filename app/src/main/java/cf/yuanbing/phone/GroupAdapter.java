package cf.yuanbing.phone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private Context context;
    private ArrayList<Contact> contacts;
    private ArrayList<ArrayList<Contact>> groupMembers = new ArrayList<>();
    private int groupCount = 0;
    // 记录每条通话记录的年，月，日
    private ArrayList<Integer> years = new ArrayList<>();
    private ArrayList<Integer> months = new ArrayList<>();
    private ArrayList<Integer> days = new ArrayList<>();
    // 记录当前的系统日期
    private Calendar calendar;
    private int currentYear;
    private int currentMonth;
    private int currentDay;

    public GroupAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
        getGroups();
        calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    }

    @NonNull
    @Override
    public GroupAdapter.GroupViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.date_tag, viewGroup, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.GroupViewHolder holder, int i) {
        if (years.get(i) == currentYear && months.get(i) == currentMonth && days.get(i) == currentDay) {
            // 当日的通话记录
            setContacts(holder, i);
        } else if (years.get(i) == currentYear && months.get(i) == currentMonth &&
                (currentDay - days.get(i)) == 1) {
            // 昨天的通话记录
            holder.date.setText("昨天");
            setContacts(holder, i);
        } else if (years.get(i) == currentYear && months.get(i) == currentMonth &&
                (currentDay - days.get(i)) == 2) {
            // 前天的通话记录
            holder.date.setText("前天");
            setContacts(holder, i);
        } else {
            // 其他时间的通话记录
            holder.date.setText(years.get(i) + "年" + months.get(i) + "月" + days.get(i) + "日");
            setContacts(holder, i);
        }
    }

    @Override
    public int getItemCount() {
        return groupCount;
    }

    private void setContacts(GroupViewHolder holder, int i) {
        holder.contactList.setAdapter(new ContactAdapter(groupMembers.get(i)));
        holder.contactList.setLayoutManager(new LinearLayoutManager(context));
        MyItemTouchHelper myItemTouchHelper = new MyItemTouchHelper(
                new MyItemTouchHelper.MySimpleCallback(ItemTouchHelper.END,
                        ItemTouchHelper.LEFT, holder.contactList, this, holder));
        myItemTouchHelper.attachToRecyclerView(holder.contactList);
    }

    private void getGroups() {
        // 获取通话记录可以分成多少组
        int year = 0;
        int month = 0;
        int day = 0;
        for (int i = 0; i < contacts.size(); i++) {
            int year1 = Integer.parseInt(stampToDate(contacts.get(i).getCallTimes(), "yyyy"));
            int month1 = Integer.parseInt(stampToDate(contacts.get(i).getCallTimes(), "MM"));
            int day1 = Integer.parseInt(stampToDate(contacts.get(i).getCallTimes(), "dd"));
            if (year == year1 && month == month1 && day == day1) {
                (groupMembers.get(groupMembers.size() - 1)).add(contacts.get(i));
            } else {
                year = year1;
                month = month1;
                day = day1;
                years.add(year1);
                months.add(month1);
                days.add(day1);
                groupCount++;
                groupMembers.add(new ArrayList<Contact>());
                (groupMembers.get(groupMembers.size() - 1)).add(contacts.get(i));
            }
        }
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
        return contacts;
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private RecyclerView contactList;
        public GroupViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.tv_date);
            contactList = view.findViewById(R.id.rv_contact);
        }
    }
}

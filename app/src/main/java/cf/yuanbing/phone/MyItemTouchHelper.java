package cf.yuanbing.phone;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;

public class MyItemTouchHelper extends ItemTouchHelper {
    public MyItemTouchHelper(MyItemTouchHelper.MySimpleCallback mySimpleCallback) {
        super(mySimpleCallback);
    }
    static class MySimpleCallback extends ItemTouchHelper.SimpleCallback {
        private RecyclerView recyclerView;
        private ArrayList<Contact> contacts;
        private GroupAdapter adapter;
        private GroupAdapter.GroupViewHolder holder;
        public MySimpleCallback(int moveDirection, int swipeDirection, RecyclerView recyclerView,
                                GroupAdapter adapter, GroupAdapter.GroupViewHolder holder) {
            super(moveDirection, swipeDirection);
            this.recyclerView = recyclerView;
            this.adapter = adapter;
            this.holder = holder;
            this.contacts= this.adapter.getDataList();
        }
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder holder,
                              @NonNull RecyclerView.ViewHolder taget) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder holder, int position) {
            position = holder.getAdapterPosition();
            RecyclerView.Adapter adapter = (ContactAdapter) recyclerView.getAdapter();
            assert adapter != null;
            ArrayList<Contact> dataList = ((ContactAdapter) adapter).getDataList();
            contacts.remove(dataList.remove(position));
            adapter.notifyItemRemoved(position);
            if (recyclerView.getChildCount() == 1) {
                this.adapter.notifyItemRemoved(this.holder.getAdapterPosition());
            }
        }
    }
}

package cf.yuanbing.phone;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CallOutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
                             Bundle saveInstanceBundle) {
        View view = layoutInflater.inflate(R.layout.contact_log_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_date_tag);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setAdapter(new GroupAdapter(container.getContext(), MainActivity.callOut));
        return view;
    }
}

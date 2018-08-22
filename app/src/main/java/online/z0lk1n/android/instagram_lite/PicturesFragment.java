package online.z0lk1n.android.instagram_lite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class PicturesFragment extends Fragment implements View.OnClickListener {
    public static final String NAME = "cb2d00bb-ca6b-45e6-a501-80f70efa65b9";
    public static final String PICTURE = "205348a2-a71c-4269-b894-6eb778180e5f";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private int currentPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pictures, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        FloatingActionButton fab = view.findViewById(R.id.fab_add_picture);
        fab.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),
                2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        List<ImageView> list = new ArrayList<>();
        adapter = new RecyclerViewAdapter(list);

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                currentPosition = position;
            }
        });

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(PICTURE, 0);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PICTURE, currentPosition);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_add_picture:
                showAddPictureDialog(view);
                break;
            default:
                break;
        }
    }

    private void showAddPictureDialog(View view) {

    }
}

package online.z0lk1n.android.instagram_lite.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import online.z0lk1n.android.instagram_lite.R;

public final class DatabaseFragment extends Fragment {

    public static final String NAME = "ba536e1d-3a9d-4db9-9b8c-9e7539d9a20b";
    private static final String TAG = "DatabaseFragment";

    public static DatabaseFragment newInstance(Bundle bundle) {
        DatabaseFragment currentFragment = new DatabaseFragment();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_database, container, false);
        return view;
    }

    private void init(View view) {
    }
}

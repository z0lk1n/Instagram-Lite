package online.z0lk1n.android.instagram_lite.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.ui.activity.MainActivity;

public final class NetworkFragment extends Fragment {

    public static final String NAME = "676e7daa-88da-437c-b675-4075f66de676";
    private static final String TAG = "NetworkFragment";

    public static NetworkFragment newInstance(Bundle bundle) {
        NetworkFragment currentFragment = new NetworkFragment();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_network, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ((MainActivity) view.getContext()).hideFloatingActionButton();
    }
}

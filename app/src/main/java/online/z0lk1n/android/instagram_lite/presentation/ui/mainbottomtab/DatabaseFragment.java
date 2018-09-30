package online.z0lk1n.android.instagram_lite.presentation.ui.mainbottomtab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;

import org.jetbrains.annotations.NotNull;

import online.z0lk1n.android.instagram_lite.R;

public final class DatabaseFragment extends MvpAppCompatFragment {

    public static final String NAME = "ba536e1d-3a9d-4db9-9b8c-9e7539d9a20b";

    public static DatabaseFragment newInstance(Bundle bundle) {
        DatabaseFragment currentFragment = new DatabaseFragment();
        Bundle args = new Bundle();
        args.putBundle("gettedArgs", bundle);
        currentFragment.setArguments(args);
        return currentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_database, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
    }
}

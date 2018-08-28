package online.z0lk1n.android.instagram_lite.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.util.Preferences;

public class PhotoFragment extends Fragment {
    public static final String NAME = "b9e6ee8c-3f43-457d-ad77-4d99891ef7bc";
    private static final String TAG = "PhotoFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);

        ImageView imgViewFullScreenPhoto = view.findViewById(R.id.full_screen_photo);

        Picasso.get()
                .load(new Preferences(getActivity()).getPhoto())
                .centerCrop()
                .error(R.drawable.ic_photo)
                .into(imgViewFullScreenPhoto);

        return view;
    }
}

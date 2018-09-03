package online.z0lk1n.android.instagram_lite.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.activity.MainActivity;
import online.z0lk1n.android.instagram_lite.util.PhotoManager;
import online.z0lk1n.android.instagram_lite.util.Preferences;

public class PhotoFragment extends Fragment {
    public static final String NAME = "b9e6ee8c-3f43-457d-ad77-4d99891ef7bc";
    private static final String TAG = "PhotoFragment";
    private int width;
    private int height;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        width = PhotoManager.calculateWidthOfPhoto(context, 1);
        height = PhotoManager.calculateHeightOfPhoto(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        ((MainActivity) getActivity()).hideFloatingActionButton();
        File file = getFile();
//        switch (PhotoManager.getOrientationPhoto(file.getPath())) {
//            case ExifInterface.ORIENTATION_NORMAL:
//                setPhoto(view, file, width, 0);
//                break;
//            case ExifInterface.ORIENTATION_ROTATE_90:
//                setPhoto(view, file, 0, height);
//                break;
//            default:
//                break;
//        }
        setPhoto(view, file, width, width);
    }

    private void setPhoto(View view, File file, int w, int h) {
        PhotoManager.setPhoto(view.findViewById(R.id.full_screen_photo), file, w, h);
    }

    private File getFile() {
        return new File(new Preferences(getActivity()).getPhoto());
    }

}

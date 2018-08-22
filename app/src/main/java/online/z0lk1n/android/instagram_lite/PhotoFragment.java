package online.z0lk1n.android.instagram_lite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PhotoFragment extends android.app.Fragment implements View.OnClickListener {
    public static final String NAME = "cb2d00bb-ca6b-45e6-a501-80f70efa65b9";
    public static final String PICTURE = "205348a2-a71c-4269-b894-6eb778180e5f";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private int currentPosition = 0;

    private final int CAMERA_REQUEST = 1;
    private String mCurrentPhotoPath;
    private ImageView imageView;
    private Uri photoURI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        FloatingActionButton fab = view.findViewById(R.id.fab_add_picture);
        fab.setOnClickListener(this);
        imageView = view.findViewById(R.id.image_I);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),
                2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        List<PhotoItem> list = new ArrayList<>();
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
                makePhoto(view);
                break;
            default:
                break;
        }
    }

    private void makePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                photoURI = Uri.fromFile(photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, CAMERA_REQUEST);
                Snackbar.make(view, R.string.photo_uploaded, Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            imageView.setImageURI(photoURI);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity()
                .getApplicationContext()
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
}

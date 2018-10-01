package online.z0lk1n.android.instagram_lite.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.data.model.PhotoItem;

public final class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<PhotoItem> photoItemList;
    private OnItemClickListener itemClickListener;
    private PhotoManager photoManager;
    private FileManager fileManager;
    private int dimens;

    public RecyclerViewAdapter(PhotoManager photoManager, FileManager fileManager, int dimens) {
        this.photoManager = photoManager;
        this.fileManager = fileManager;
        this.dimens = dimens;
        this.photoItemList = new ArrayList<>();
    }

    public interface OnItemClickListener {
        void onPhotoClick(int position);

        void onPhotoLongClick(int position);

        void onFavoritesClick(boolean isChecked, int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull @NotNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bindView(i);
    }

    @Override
    public int getItemCount() {
        return photoItemList.size();
    }

    public void addItems(List<PhotoItem> photoItems) {
        photoItemList.clear();
        photoItemList.addAll(photoItems);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgView_picture) ImageView imgViewPhoto;
        @BindView(R.id.toggle_favorites) ToggleButton toggleFavorites;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            imgViewPhoto.setOnClickListener(view ->
                    itemClickListener.onPhotoClick(getAdapterPosition()));

            imgViewPhoto.setOnLongClickListener(view -> {
                    itemClickListener.onPhotoLongClick(getAdapterPosition());
                    return true;
            });

            toggleFavorites.setOnCheckedChangeListener((buttonView, isChecked) ->
                    itemClickListener.onFavoritesClick(isChecked, getAdapterPosition()));
        }

        private void bindView(int position) {
            photoManager.setPhoto(imgViewPhoto, getFile(position), dimens, dimens);
            if (photoItemList.get(position).isFavorites()) {
                toggleFavorites.setChecked(true);
            }
        }

        @NonNull
        private File getFile(int position) {
            return new File(photoItemList.get(position).getPhotoPath());
        }
    }
}
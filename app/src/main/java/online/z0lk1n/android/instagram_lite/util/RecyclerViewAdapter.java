package online.z0lk1n.android.instagram_lite.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.model.PhotoItem;

public final class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private List<PhotoItem> photoItemList;
    private OnItemClickListener itemClickListener;
    private Preferences preferences;
    private int dimens;

    public RecyclerViewAdapter(List<PhotoItem> photoItemList, int dimens, Preferences preferences) {
        this.photoItemList = photoItemList;
        this.dimens = dimens;
        this.preferences = preferences;
    }

    public interface OnItemClickListener {
        void onPhotoClick(int position);

        void onPhotoLongClick(int position);

        void onFavoritesClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final @NonNull RecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bindView(i);
    }

    @Override
    public int getItemCount() {
        return photoItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgViewPhoto;
        ImageView imgFavorites;

        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgViewPhoto = itemView.findViewById(R.id.imgView_picture);
            imgFavorites = itemView.findViewById(R.id.imgView_favorites);

            imgViewPhoto.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onPhotoClick(getAdapterPosition());
                }
            });
            imgViewPhoto.setOnLongClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onPhotoLongClick(getAdapterPosition());
                    return true;
                }
                return false;
            });
            imgFavorites.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onFavoritesClick(getAdapterPosition());
                }
            });
        }

        private void bindView(int position) {
            PhotoManager.setPhoto(imgViewPhoto, getFile(position), dimens, dimens);
            if (preferences.getFavorites().contains(photoItemList.get(position).getPhotoPath())) {
                imgFavorites.setImageResource(R.drawable.ic_star);
            } else {
                imgFavorites.setImageResource(R.drawable.ic_star_border);
            }
        }

        @NonNull
        private File getFile(int position) {
            return new File(photoItemList.get(position).getPhotoPath());
        }
    }
}
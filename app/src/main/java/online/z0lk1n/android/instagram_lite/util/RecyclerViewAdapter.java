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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private List<PhotoItem> photoItemList;
    private OnItemClickListener itemClickListener;
    private int dimens;

    public RecyclerViewAdapter(List<PhotoItem> photoItemList, int dimens) {
        this.photoItemList = photoItemList;
        this.dimens = dimens;
    }

    public interface OnItemClickListener {
        void onPhotoClick(View view, int position);

        void onPhotoLongClick(View view, int position);

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
                    itemClickListener.onPhotoClick(itemView, getAdapterPosition());
                }
            });
            imgViewPhoto.setOnLongClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onPhotoLongClick(itemView, getAdapterPosition());
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
            if (photoItemList.get(position).isFavorites()) {
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
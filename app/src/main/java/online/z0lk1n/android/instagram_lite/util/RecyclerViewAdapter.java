package online.z0lk1n.android.instagram_lite.util;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.model.PhotoItem;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<PhotoItem> photoItemList;
    private OnItemClickListener itemClickListener;

    public RecyclerViewAdapter(List<PhotoItem> photoItemList) {
        this.photoItemList = photoItemList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
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
        Picasso.get()
                .load(photoItemList.get(i).getPhoto())
                .resize(300, 300)
                .centerCrop()
                .error(R.drawable.ic_photo)
                .into(viewHolder.imgViewPhoto);
    }

    @Override
    public int getItemCount() {
        return photoItemList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgViewPhoto;
//        ImageView imgFavorites;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewPhoto = itemView.findViewById(R.id.imgView_picture);
//            imgFavorites = itemView.findViewById(R.id.imgView_favorites);

            imgViewPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if(itemClickListener != null)   {
//                        itemClickListener.onItemClick(view, getAdapterPosition());
//                    }
//                    notifyItemChanged(getAdapterPosition());
                }
            });
            imgViewPhoto.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showDeletePhotoDialog(view);
                    notifyItemChanged(getAdapterPosition());
                    return true;
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private void showDeletePhotoDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext())
                .setTitle("Delete Photo?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new File(photoItemList.get(which).getPhoto().getPath()).deleteOnExit();
                        photoItemList.remove(which);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }
}

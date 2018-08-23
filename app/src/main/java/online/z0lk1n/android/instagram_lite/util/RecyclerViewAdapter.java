package online.z0lk1n.android.instagram_lite.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
        viewHolder.imgViewPhoto.setImageURI(photoItemList.get(i).getPhoto());
    }

    @Override
    public int getItemCount() {
        return photoItemList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder    {
        ImageView imgViewPhoto;
//        Button btnFavorites;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewPhoto = itemView.findViewById(R.id.imgView_picture);
//            btnFavorites = itemView.findViewById(R.id.btn_Favorites);

//            imgViewPhoto.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(itemClickListener != null)   {
//                        itemClickListener.onItemClick(view, getAdapterPosition());
//                    }
//                    notifyItemChanged(getAdapterPosition());
//                }
//            });

//            btnFavorites.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                }
//            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}

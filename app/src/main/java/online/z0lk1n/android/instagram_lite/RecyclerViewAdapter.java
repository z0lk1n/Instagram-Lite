package online.z0lk1n.android.instagram_lite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<PhotoItem> picturesList;
    private OnItemClickListener itemClickListener;

    public RecyclerViewAdapter(List<PhotoItem> picturesList) {
        this.picturesList = picturesList;
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
        viewHolder.imgViewPicture.setImageBitmap(picturesList.get(i).getPhoto());
    }

    @Override
    public int getItemCount() {
        return picturesList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder    {
        ImageView imgViewPicture;
        Button btnFavorites;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewPicture = itemView.findViewById(R.id.imgView_picture);
            btnFavorites = itemView.findViewById(R.id.btn_Favorites);

            imgViewPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemClickListener != null)   {
                        itemClickListener.onItemClick(view, getAdapterPosition());
                    }
                    notifyItemChanged(getAdapterPosition());
                }
            });

            btnFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}

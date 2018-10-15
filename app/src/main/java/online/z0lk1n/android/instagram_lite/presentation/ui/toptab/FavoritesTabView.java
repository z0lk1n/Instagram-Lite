package online.z0lk1n.android.instagram_lite.presentation.ui.toptab;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import online.z0lk1n.android.instagram_lite.data.model.PhotoItem;

@StateStrategyType(AddToEndStrategy.class)
public interface FavoritesTabView extends MvpView {

    void showDeletePhotoDialog(final int position);

    void notifyItemRemoved(int position);

    void closeDialog();

    void showNotifyingMessage(String message);

    void updatePhotoList(List<PhotoItem> photoItems);
}

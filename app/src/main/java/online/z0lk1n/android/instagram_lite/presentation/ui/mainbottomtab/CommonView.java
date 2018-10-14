package online.z0lk1n.android.instagram_lite.presentation.ui.mainbottomtab;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import online.z0lk1n.android.instagram_lite.data.model.PhotoItem;

@StateStrategyType(AddToEndStrategy.class)
public interface CommonView extends MvpView {

    void showDeletePhotoDialog(int position);

    void showNotifyingMessage(String message);

    void notifyItem(int position, int action);

    void startCamera();

    void updatePhotoList(List<PhotoItem> photoItems);
}

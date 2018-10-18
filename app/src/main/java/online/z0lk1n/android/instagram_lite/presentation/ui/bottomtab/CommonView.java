package online.z0lk1n.android.instagram_lite.presentation.ui.bottomtab;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import online.z0lk1n.android.instagram_lite.data.database.PhotoEntity;

@StateStrategyType(AddToEndStrategy.class)
public interface CommonView extends MvpView {

    void showDeletePhotoDialog(final String photoPath);

    void showNotifyingMessage(String message);

    void notifyItem(int position, int action);

    void startCamera();

    void updatePhotoList(List<PhotoEntity> photoEntities);

    void closeDialog();
}

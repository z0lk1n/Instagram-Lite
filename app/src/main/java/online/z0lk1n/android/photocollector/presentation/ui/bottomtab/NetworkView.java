package online.z0lk1n.android.photocollector.presentation.ui.bottomtab;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import online.z0lk1n.android.photocollector.data.database.PhotoEntity;

@StateStrategyType(AddToEndStrategy.class)
public interface NetworkView extends MvpView {

    void showNotifyingMessage(String message);

    void updatePhotoList(List<PhotoEntity> photoEntities);

    void showLoading();

    void hideLoading();
}

package online.z0lk1n.android.instagram_lite.presentation.view.common;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.io.File;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CommonView extends MvpView {
    void showDeletePhotoDialog(final int position);

    void showFullPhoto(final int position);

    void showNotifyingMessage(String message);

    void notifyItem(int position, int action);

    void startCamera(File file);
}

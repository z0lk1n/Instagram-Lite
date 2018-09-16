package online.z0lk1n.android.instagram_lite.presentation.mvp.mainbottomtab;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CommonView extends MvpView {
    void showDeletePhotoDialog(int position);

    void showFullPhoto(String photoPath);

    void showNotifyingMessage(String message);

    void notifyItem(int position, int action);

    void startCamera(String fileName, String suffix);
}

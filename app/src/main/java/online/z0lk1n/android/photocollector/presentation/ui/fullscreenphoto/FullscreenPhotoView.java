package online.z0lk1n.android.photocollector.presentation.ui.fullscreenphoto;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndStrategy.class)
public interface FullscreenPhotoView extends MvpView {

    void show();

    void hide();
}

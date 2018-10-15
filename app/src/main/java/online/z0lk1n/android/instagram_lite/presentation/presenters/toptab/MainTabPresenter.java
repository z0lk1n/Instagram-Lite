package online.z0lk1n.android.instagram_lite.presentation.presenters.toptab;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import online.z0lk1n.android.instagram_lite.R;
import online.z0lk1n.android.instagram_lite.presentation.ui.Screens;
import online.z0lk1n.android.instagram_lite.presentation.ui.toptab.MainTabView;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainTabPresenter extends MvpPresenter<MainTabView> {

    @Inject Router router;

    public boolean showFragment(int itemId) {
        switch (itemId) {
            case R.id.action_common:
                router.navigateTo(new Screens.CommonScreen());
                return true;
            case R.id.action_network:
                router.navigateTo(new Screens.NetworkScreen());
                return true;
            case R.id.action_database:
                router.navigateTo(new Screens.DatabaseScreen());
                return true;
            default:
                return false;
        }
    }
}

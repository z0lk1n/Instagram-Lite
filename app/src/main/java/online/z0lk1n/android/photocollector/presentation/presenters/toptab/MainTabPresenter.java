package online.z0lk1n.android.photocollector.presentation.presenters.toptab;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import online.z0lk1n.android.photocollector.R;
import online.z0lk1n.android.photocollector.presentation.ui.Screens;
import online.z0lk1n.android.photocollector.presentation.ui.toptab.MainTabView;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainTabPresenter extends MvpPresenter<MainTabView> {

    @Inject Router router;

    public boolean showFragment(int itemId) {
        switch (itemId) {
            case R.id.action_common:
                router.replaceScreen(new Screens.CommonScreen());
                return true;
            case R.id.action_network:
                router.replaceScreen(new Screens.NetworkScreen());
                return true;
            case R.id.action_database:
                router.replaceScreen(new Screens.DatabaseScreen());
                return true;
            default:
                return false;
        }
    }

    public void visibleToUser(int itemId) {
        showFragment(itemId);
    }
}

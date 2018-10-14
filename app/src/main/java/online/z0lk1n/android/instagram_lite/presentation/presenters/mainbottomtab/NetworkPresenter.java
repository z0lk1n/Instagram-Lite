package online.z0lk1n.android.instagram_lite.presentation.presenters.mainbottomtab;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import online.z0lk1n.android.instagram_lite.presentation.ui.mainbottomtab.NetworkView;
import ru.terrakok.cicerone.Router;

@InjectViewState
public final class NetworkPresenter extends MvpPresenter<NetworkView> {

    @Inject Router router;
}

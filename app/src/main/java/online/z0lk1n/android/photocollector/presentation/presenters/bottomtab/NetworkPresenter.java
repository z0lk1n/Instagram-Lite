package online.z0lk1n.android.photocollector.presentation.presenters.bottomtab;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import online.z0lk1n.android.photocollector.presentation.ui.bottomtab.NetworkView;
import ru.terrakok.cicerone.Router;

@InjectViewState
public final class NetworkPresenter extends MvpPresenter<NetworkView> {

    @Inject Router router;
}

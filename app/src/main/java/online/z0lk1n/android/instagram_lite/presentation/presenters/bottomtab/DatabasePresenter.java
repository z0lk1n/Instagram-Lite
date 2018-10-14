package online.z0lk1n.android.instagram_lite.presentation.presenters.bottomtab;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import online.z0lk1n.android.instagram_lite.presentation.ui.bottomtab.DatabaseView;
import ru.terrakok.cicerone.Router;

@InjectViewState
public final class DatabasePresenter extends MvpPresenter<DatabaseView> {

    @Inject Router router;
}

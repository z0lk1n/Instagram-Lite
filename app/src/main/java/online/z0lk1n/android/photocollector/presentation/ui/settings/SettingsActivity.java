package online.z0lk1n.android.photocollector.presentation.ui.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import online.z0lk1n.android.photocollector.App;
import online.z0lk1n.android.photocollector.R;
import online.z0lk1n.android.photocollector.presentation.ui.Screens;
import online.z0lk1n.android.photocollector.presentation.ui.global.BaseActivity;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public final class SettingsActivity extends BaseActivity {

    @BindView(R.id.toolbar_settings) Toolbar toolbar;

    @Inject NavigatorHolder navigatorHolder;
    @Inject Router router;

    private Navigator navigator = new SupportAppNavigator(this, R.id.settings_container)    {
        @Override
        public void applyCommands(Command[] commands) {
            super.applyCommands(commands);
            getSupportFragmentManager().executePendingTransactions();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        App.getInstance().getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        init();
        if (savedInstanceState == null) {
            navigator.applyCommands(new Command[]{new Replace(new Screens.SettingsFragmentScreen())});
        }
    }

    private void init() {
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        router.exit();
    }
}
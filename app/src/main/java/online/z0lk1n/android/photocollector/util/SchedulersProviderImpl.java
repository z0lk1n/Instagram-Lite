package online.z0lk1n.android.photocollector.util;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class SchedulersProviderImpl implements SchedulersProvider {

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }
}

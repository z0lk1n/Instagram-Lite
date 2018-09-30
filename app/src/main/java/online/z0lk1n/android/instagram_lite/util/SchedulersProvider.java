package online.z0lk1n.android.instagram_lite.util;

import io.reactivex.Scheduler;

public interface SchedulersProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();
}

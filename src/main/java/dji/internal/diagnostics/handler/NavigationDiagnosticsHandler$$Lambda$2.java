package dji.internal.diagnostics.handler;

import dji.internal.diagnostics.DJIDiagnosticsImpl;
import dji.utils.function.Consumer;
import dji.utils.function.Consumer$$CC;
import java.util.Set;

final /* synthetic */ class NavigationDiagnosticsHandler$$Lambda$2 implements Consumer {
    private final Set arg$1;

    private NavigationDiagnosticsHandler$$Lambda$2(Set set) {
        this.arg$1 = set;
    }

    static Consumer get$Lambda(Set set) {
        return new NavigationDiagnosticsHandler$$Lambda$2(set);
    }

    public void accept(Object obj) {
        this.arg$1.add((DJIDiagnosticsImpl) obj);
    }

    public Consumer andThen(Consumer consumer) {
        return Consumer$$CC.andThen(this, consumer);
    }
}

package dji.internal.diagnostics.handler;

import dji.midware.data.model.P3.DataSmartBatteryGetPushDynamicData;
import dji.utils.function.Predicate;
import dji.utils.function.Predicate$$CC;

final /* synthetic */ class BatteryDiagnosticsHandler$$Lambda$0 implements Predicate {
    static final Predicate $instance = new BatteryDiagnosticsHandler$$Lambda$0();

    private BatteryDiagnosticsHandler$$Lambda$0() {
    }

    public Predicate and(Predicate predicate) {
        return Predicate$$CC.and(this, predicate);
    }

    public Predicate negate() {
        return Predicate$$CC.negate(this);
    }

    public Predicate or(Predicate predicate) {
        return Predicate$$CC.or(this, predicate);
    }

    public boolean test(Object obj) {
        return BatteryDiagnosticsHandler.lambda$initDiagnosticsList$0$BatteryDiagnosticsHandler((DataSmartBatteryGetPushDynamicData) obj);
    }
}

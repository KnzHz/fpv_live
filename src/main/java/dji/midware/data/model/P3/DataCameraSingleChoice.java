package dji.midware.data.model.P3;

import android.support.annotation.Keep;
import dji.fieldAnnotation.EXClassNullAway;
import dji.midware.data.config.P3.CmdIdCamera;
import dji.midware.data.config.P3.CmdSet;
import dji.midware.data.config.P3.DataConfig;
import dji.midware.data.config.P3.DeviceType;
import dji.midware.data.manager.P3.DataBase;
import dji.midware.data.packages.P3.SendPack;
import dji.midware.interfaces.DJIDataCallBack;
import dji.midware.interfaces.DJIDataSyncListener;
import dji.midware.util.BytesUtil;

@Keep
@EXClassNullAway
public class DataCameraSingleChoice extends DataBase implements DJIDataSyncListener {
    private static DataCameraSingleChoice instance = null;
    private int index;

    public static synchronized DataCameraSingleChoice getInstance() {
        DataCameraSingleChoice dataCameraSingleChoice;
        synchronized (DataCameraSingleChoice.class) {
            if (instance == null) {
                instance = new DataCameraSingleChoice();
            }
            dataCameraSingleChoice = instance;
        }
        return dataCameraSingleChoice;
    }

    public DataCameraSingleChoice setKey(int index2) {
        this.index = index2;
        return this;
    }

    /* access modifiers changed from: protected */
    public void doPack() {
        this._sendData = new byte[8];
        BytesUtil.arraycopy(BytesUtil.getBytes(this.index), this._sendData, 0);
    }

    public void start(DJIDataCallBack callBack) {
        SendPack pack = new SendPack();
        pack.senderType = DeviceType.APP.value();
        pack.receiverType = DeviceType.CAMERA.value();
        pack.cmdType = DataConfig.CMDTYPE.REQUEST.value();
        pack.isNeedAck = DataConfig.NEEDACK.YES.value();
        pack.encryptType = DataConfig.EncryptType.NO.value();
        pack.cmdSet = CmdSet.CAMERA.value();
        pack.cmdId = CmdIdCamera.CmdIdType.SingleChoice.value();
        pack.data = getSendData();
        start(pack, callBack);
    }
}

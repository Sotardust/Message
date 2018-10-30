package com.dai.message.ui.main.missedcalls;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.dai.message.MessageApplication;
import com.dai.message.callback.CallBack;
import com.dai.message.repository.entity.AllCallsEntity;
import com.dai.message.ui.main.CallRecordViewModel;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;


public class MissedCallsViewModel extends CallRecordViewModel {


    private IWXAPI iwxapi;

    public MissedCallsViewModel(@NonNull Application application) {
        super(application);
        iwxapi = getIwxapi(application.getApplicationContext());
    }

    /**
     * 设置LiveData 监听设置列表数据变化
     */
    private MutableLiveData<ArrayList<AllCallsEntity>> mMissedCallsList;


    public MutableLiveData<ArrayList<AllCallsEntity>> getMissedCallsList() {
        if (mMissedCallsList == null) {
            mMissedCallsList = new MutableLiveData<>();
            distinctMissedCalls();
        }
        return mMissedCallsList;
    }


    private IWXAPI getIwxapi(Context context) {
        //微信AppID
        final String APP_ID = "wx4b46660f04a64bc4";
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, APP_ID, true);
        iwxapi.registerApp(APP_ID);
        return iwxapi;
    }

    /**
     * 获取未接来电通话记录
     * 未接类型：3
     *
     * @return AllCalls实体集合
     */
    private void distinctMissedCalls() {
        repository.getCallsEntities(new CallBack<List<AllCallsEntity>>() {
            @Override
            public void onChangeData(List<AllCallsEntity> data) {
                mMissedCallsList.setValue((ArrayList<AllCallsEntity>) data);
            }
        }, "3");
    }

    /**
     * 测试向微信发送消息
     *
     * @param text
     */
    public void sendMessageToWeChat(String text) {

        WXTextObject textObject = new WXTextObject();
        textObject.text = "测试数据分享到微信";

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = "***测试数据分享到微信***";

        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = String.valueOf(format.format(System.currentTimeMillis()));
        req.transaction = "text";
        req.message = msg;
//        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        iwxapi.sendReq(req);

    }
}

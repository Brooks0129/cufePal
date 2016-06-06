package com.brooks.cufer.push;

import org.android.agoo.client.BaseConstants;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;

import android.util.Log;

import com.umeng.fb.ConversationActivity;
import com.umeng.fb.push.FeedbackPush;
import com.umeng.message.UmengBaseIntentService;
import com.umeng.message.entity.UMessage;




public class MyPushIntentService extends UmengBaseIntentService{
	private static final String TAG = MyPushIntentService.class.getName();

	@Override
	protected void onMessage(Context context, Intent intent) {
		super.onMessage(context, intent);
        FeedbackPush.getInstance(context).init(ConversationActivity.class,true);
        Log.d(TAG, "onMessage");
        if(FeedbackPush.getInstance(context).onFBMessage(intent)) {
            //The push message is reply from developer.
            return;
        }

        //The push message is not reply from developer.
        /*************** other code ***************/
        try {
            //����ͨ��MESSAGE_BODYȡ����Ϣ��
            String message = intent.getStringExtra(BaseConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));
            Log.d(TAG, "message="+message);    //��Ϣ��
            Log.d(TAG, "custom="+msg.custom);    //�Զ�����Ϣ������
            Log.d(TAG, "title="+msg.title);    //֪ͨ����
            Log.d(TAG, "text="+msg.text);    //֪ͨ����
            // code  to handle message here
            // ...
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}

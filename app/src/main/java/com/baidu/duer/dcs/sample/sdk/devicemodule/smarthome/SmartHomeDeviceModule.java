package com.baidu.duer.dcs.sample.sdk.devicemodule.smarthome;

import android.widget.Toast;

import com.baidu.duer.dcs.api.BaseDeviceModule;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.sample.sdk.devicemodule.smarthome.message.CloseSmartHomePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.smarthome.message.OpenSmartHomePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.sms.message.SmsClientContextPayload;
import com.baidu.duer.dcs.util.message.ClientContext;
import com.baidu.duer.dcs.util.message.Directive;
import com.baidu.duer.dcs.util.message.HandleDirectiveException;
import com.baidu.duer.dcs.util.message.Header;
import com.baidu.duer.dcs.util.message.Payload;

/*
 * create by zhaomingqiang 2018/12/18
 * */
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SmartHomeDeviceModule extends BaseDeviceModule {
    private List<ISmartHomeListener> listeners;

    public SmartHomeDeviceModule(IMessageSender messageSender)
    {
        super(ApiConstants.NAMESPACE,messageSender);
        listeners = new CopyOnWriteArrayList<>();
    }
    @Override
    public ClientContext clientContext() {

        /*String namespace = ApiConstants.NAMESPACE;
        String name = ApiConstants.Events.SmartHomeState.NAME;
        Header header = new Header(namespace, name);
        SmsClientContextPayload payload = new SmsClientContextPayload();
        return new ClientContext(header, payload);*/
        return null;
    }

    @Override
    public void handleDirective(Directive directive) throws HandleDirectiveException {
        String headerName = directive.getName();
        Payload payload = directive.getPayload();
        if(ApiConstants.Directives.Open.NAME.equals(headerName))
        {

            handleOpenSmartHome((OpenSmartHomePayload) payload);
        }
        else if (ApiConstants.Directives.Close.NAME.equals(headerName))
        {
            handleCloseSmartHome((CloseSmartHomePayload) payload);
        }
        else {
            //handleOpenSmartHome((OpenSmartHomePayload) payload);
            String message = "launch app cannot handle the directive";
            throw (new HandleDirectiveException(
                    HandleDirectiveException.ExceptionType.UNSUPPORTED_OPERATION, message));
        }


    }

    @Override
    public HashMap<String, Class<?>> supportPayload() {
        HashMap<String,Class<?>> map = new HashMap<>();
        map.put(getNameSpace() + ApiConstants.Directives.Open.NAME , OpenSmartHomePayload.class);
        map.put(getNameSpace() + ApiConstants.Directives.Close.NAME , CloseSmartHomePayload.class);
        return map;
    }

    @Override
    public void release() {
        listeners.clear();

    }

    public void addSmartHomeListener(ISmartHomeListener listener)
    {
        if(listener != null)
        {
            listeners.add(listener);
        }
    }

    public void removeSmartHomeListener(ISmartHomeListener listener)
    {
        if(listener !=null&&listeners.contains(listener) )
        {
            listeners.remove(listener);
        }
    }

    public void handleOpenSmartHome(OpenSmartHomePayload payload)
    {
        for(ISmartHomeListener listener : listeners)
        {
            listener.OnOpenSmartHome(payload);
        }
    }

    public void handleCloseSmartHome(CloseSmartHomePayload payload)
    {
        for(ISmartHomeListener listener : listeners)
        {
            listener.OnCloseSmartHome(payload);
        }
    }


    //给家用电器发送红外指令借接口
    public interface ISmartHomeListener{
        public void OnOpenSmartHome(OpenSmartHomePayload payload);
        public void OnCloseSmartHome(CloseSmartHomePayload payload);
    }
}

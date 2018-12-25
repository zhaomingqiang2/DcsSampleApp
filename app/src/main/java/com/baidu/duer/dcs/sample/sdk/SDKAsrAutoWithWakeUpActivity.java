/*
 * *
 * Copyright (c) 2017 Baidu, Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.baidu.duer.dcs.sample.sdk;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.Toast;

import com.baidu.duer.dcs.api.IDcsSdk;
import com.baidu.duer.dcs.api.IMessageSender;
import com.baidu.duer.dcs.api.config.DcsConfig;
import com.baidu.duer.dcs.framework.message.PayloadConfig;
import com.baidu.duer.dcs.sample.sdk.devicemodule.alarms.AlarmsDeviceModule;
import com.baidu.duer.dcs.sample.sdk.devicemodule.alarms.message.SetAlarmPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.alarms.message.SetTimerPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.alarms.message.ShowAlarmsPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.alarms.message.ShowTimersPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.app.AppDeviceModule;
import com.baidu.duer.dcs.sample.sdk.devicemodule.app.message.LaunchAppPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.app.message.TryLaunchAppPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.applauncher.AppLauncherDeviceModule;
import com.baidu.duer.dcs.sample.sdk.devicemodule.applauncher.AppLauncherImpl;
import com.baidu.duer.dcs.sample.sdk.devicemodule.applauncher.IAppLauncher;
import com.baidu.duer.dcs.sample.sdk.devicemodule.applauncher.message.AppInfo;
import com.baidu.duer.dcs.sample.sdk.devicemodule.asr.AsrDeviceModule;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.DeviceControlDeviceModule;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.AdjustBrightnessPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetAssistiveTouchPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetBluetoothPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetBrightnessPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetCellularModePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetCellularPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetGpsPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetHotspotPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetNfcPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetPhoneModePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetPhonePowerPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetPortraitLockPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetSynchronizationPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetVibrationPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetVpnPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.devicecontrol.message.SetWifiPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.smarthome.SmartHomeDeviceModule;
import com.baidu.duer.dcs.sample.sdk.devicemodule.smarthome.message.CloseSmartHomePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.smarthome.message.OpenSmartHomePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.sms.SmsDeviceModule;
import com.baidu.duer.dcs.sample.sdk.devicemodule.sms.message.SelectRecipientPayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.sms.message.SendSmsByNamePayload;
import com.baidu.duer.dcs.sample.sdk.devicemodule.sms.message.SendSmsByNumberPayload;
import com.baidu.duer.dcs.util.AsrType;
import com.baidu.duer.dcs.util.devicemodule.asr.message.HandleAsrResultPayload;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by guxiuzhong@baidu.com on 2017/9/6.
 */
public class SDKAsrAutoWithWakeUpActivity extends SDKBaseActivity {




    @Override
    public boolean enableWakeUp() {
        return true;
    }

    @Override
    protected void addOtherDeviceModule(IDcsSdk dcsSdk, IMessageSender messageSender) {
        super.addOtherDeviceModule(dcsSdk, messageSender);
        //增加打开应用的devicemodule。add by zhaomingqiang 2018/12/17
        final AppLauncherDeviceModule appDeviceModule = new AppLauncherDeviceModule(messageSender, AppLauncherImpl.getInstance(SDKAsrAutoWithWakeUpActivity.this));
        appDeviceModule.addAppLauncherListener(new AppLauncherDeviceModule.IAppLauncherListener() {
            @Override
            public void onLaunchApp(com.baidu.duer.dcs.sample.sdk.devicemodule.applauncher.message.LaunchAppPayload payload) {
                Toast.makeText(SDKAsrAutoWithWakeUpActivity.this.getApplicationContext(),"onLaunchApp",Toast.LENGTH_LONG).show();
                if(appDeviceModule.getAppLauncher().launchAppByName(SDKAsrAutoWithWakeUpActivity.this,payload.getAppName()))
                {
                    Toast.makeText(SDKAsrAutoWithWakeUpActivity.this,"open success:"+payload.getAppName(),Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(SDKAsrAutoWithWakeUpActivity.this,"open failed:"+payload.getAppName(),Toast.LENGTH_LONG).show();
                }


            }
        });

        dcsSdk.putDeviceModule(appDeviceModule);
        //SMS短信
        SmsDeviceModule smsDeviceModule = new SmsDeviceModule(messageSender);
        smsDeviceModule.addSmsListener(new SmsDeviceModule.ISmsListener() {
            @Override
            public void onSendSmsByName(SendSmsByNamePayload payload) {
                Toast.makeText(SDKAsrAutoWithWakeUpActivity.this.getApplicationContext(),"onSendSmsByName"+payload.getCandidateRecipients().get(0).getContactName(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onSelectRecipient(SelectRecipientPayload payload) {
                Toast.makeText(SDKAsrAutoWithWakeUpActivity.this.getApplicationContext(),"onSelectRecipient",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onSendSmsByNumber(SendSmsByNumberPayload payload) {
                Toast.makeText(SDKAsrAutoWithWakeUpActivity.this.getApplicationContext(),"onSendSmsByNumber"+payload.getRecipient().getPhoneNumber()+payload.getRecipient().getDisplayName()+payload.getMessageContent(),Toast.LENGTH_LONG).show();

            }
        });
        dcsSdk.putDeviceModule(smsDeviceModule);
        //PayloadConfig

        SmartHomeDeviceModule smartHomeDeviceModule = new SmartHomeDeviceModule(messageSender);
        smartHomeDeviceModule.addSmartHomeListener(new SmartHomeDeviceModule.ISmartHomeListener() {
            @Override
            public void OnOpenSmartHome(OpenSmartHomePayload payload) {
                Toast.makeText(SDKAsrAutoWithWakeUpActivity.this,"open"+payload.getDeviceName(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void OnCloseSmartHome(CloseSmartHomePayload payload) {
                Toast.makeText(SDKAsrAutoWithWakeUpActivity.this,"close"+payload.getDeviceName(),Toast.LENGTH_LONG).show();;
            }
        });
        dcsSdk.putDeviceModule(smartHomeDeviceModule);

        AsrDeviceModule asrDeviceModule = new AsrDeviceModule(messageSender);
        asrDeviceModule.addAsrListener(new AsrDeviceModule.IAsrListener() {
            @Override
            public void onHandleAsrResult(HandleAsrResultPayload payload) {
                Toast.makeText(SDKAsrAutoWithWakeUpActivity.this,"12"+payload.getContent(),Toast.LENGTH_LONG);
            }
        });
        //dcsSdk.putDeviceModule(asrDeviceModule);


    }

    @Override
    public int getAsrMode() {
        return DcsConfig.ASR_MODE_ONLINE;
    }

    @Override
    public AsrType getAsrType() {
        return AsrType.AUTO;
    }


}
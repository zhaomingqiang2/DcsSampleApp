package com.baidu.duer.dcs.sample.sdk.devicemodule.smarthome;
/*
* create by zhaomingqiang 2018/12/18
* */

public class ApiConstants {
    public static final String  NAMESPACE ="ai.dueros.device_interface.thirdparty.smarthome";
    public static final String NAME = "SmartHomeInterface";

    public static final class Events {
        public static final class SmartHomeState {
            public static final String NAME = SmartHomeState.class.getSimpleName();
        }
    }

    public static final class Directives{
        public static final class Open{
            public static final String NAME = Open.class.getSimpleName();
        }

        public static final class Close{
            public static final String NAME = Close.class.getSimpleName();
        }

    }
}

var isWebviewFlag;

// 设置启用 webView 首页面并加装成功
function setWebViewFlag() {
    isWebviewFlag = true;
    alert("isWebviewFlag"+isWebviewFlag);
};
// native 2 Js data
function native2JsData(obj) {
    alert("setDataFlag"+obj);
};

// js 2 native data  数据封装到url中
function js2NativeData(url) {
    var iFrame;
    iFrame = document.createElement("iframe");
    iFrame.setAttribute("src", url);
    iFrame.setAttribute("style", "display:none;");
    iFrame.setAttribute("height", "0px");
    iFrame.setAttribute("width", "0px");
    iFrame.setAttribute("frameborder", "0");
    document.body.appendChild(iFrame);
    iFrame.parentNode.removeChild(iFrame);
    iFrame = null;
};

// html 数据封装成Json
function data2Json(funName, args) {
    var commend = {
        functionName : funName,
        arguments : args
    };
    var jsonStr = JSON.stringify(commend);
    var url = "people:" + jsonStr;
    js2NativeData(url);
};

var PeopleAgent = {
    /**
     * 获取Android端数据
     */
    getAndroid : function(callBack) {
        if (isWebviewFlag) {
            data2Json("getAndroid", [ callBack.name ]);
        }
    },
    /**
     * 结构化事件
     * 
     * @param evenArray
     *            List<String>类型.最大8个
     * @param evenValue
     *            int 类型
     * @param eventLabel
     *            String类型.事件标签，事件的一个属性说明
     */
    onCCEvent : function(evenArray, evenValue, eventLabel) {
        if (isWebviewFlag) {
            data2Json("onCCEvent", [ evenArray, evenValue, eventLabel ]);
        }
    },
    /**
     * 自定义事件数量统计
     * 
     * @param eventId
     *            String类型.
     */
    onEvent : function(eventId) {
        if (isWebviewFlag) {
            data2Json("onEvent", [ eventId ]);
        }
    },
    /**
     * 自定义事件数量统计
     * 
     * @param eventId
     *            String类型.事件ID，
     * @param eventLabel
     *            String类型.事件标签，事件的一个属性说明
     */
    onEventWithLabel : function(eventId, eventLabel) {
        if (isWebviewFlag) {
            data2Json("onEventWithLabel", [ eventId, eventLabel ]);
        }
    },
    /**
     * 自定义事件数量统计
     * 
     * @param eventId
     *            String类型.事件ID，
     * @param eventData
     *            Map<String,String>类型.当前事件的属性集合，最多支持10个K-V值
     */
    onEventWithParameters : function(eventId, eventData) {
        if (isWebviewFlag) {
            data2Json("onEventWithParameters", [ eventId, eventData ]);
        }
    },
    /**
     * 自定义事件数值型统计
     * 
     * @param eventId
     *            String类型.事件ID，
     * @param eventData
     *            Map<String,String>类型.事件的属性集合
     * @param eventNum
     *            int 类型.事件持续时长，单位毫秒，您需要手动计算并传入时长，作为事件的时长参数
     * 
     */
    onEventWithCounter : function(eventId, eventData, eventNum) {
        if (isWebviewFlag) {
            data2Json("onEventWithCounter", [ eventId, eventData, eventNum ]);
        }
    },
    /**
     * 页面统计开始时调用
     * 
     * @param pageName
     *            String类型.页面名称
     */
    onPageBegin : function(pageName) {
        if (isWebviewFlag) {
            data2Json("onPageBegin", [ pageName ]);
        }
    },
    /**
     * 页面统计结束时调用
     * 
     * @param pageName
     *            String类型.页面名称
     */
    onPageEnd : function(pageName) {
        if (isWebviewFlag) {
            data2Json("onPageEnd", [ pageName ]);
        }
    },
    /**
     * 统计帐号登录接口 *
     * 
     * @param UID
     *            用户账号ID,长度小于64字节
     */
    profileSignInWithPUID : function(UID) {
        if (isWebviewFlag) {
            data2Json("profileSignInWithPUID", [ UID ]);
        }
    },

    /**
     * 帐号统计退出接口
     */
    profileSignOff : function() {
        if (isWebviewFlag) {
            data2Json("profileSignOff", []);
        }
    },

};

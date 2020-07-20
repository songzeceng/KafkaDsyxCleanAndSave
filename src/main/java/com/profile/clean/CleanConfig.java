package com.profile.clean;

public class CleanConfig {
    //////////////////////////////////通用部分/////////////////////////////////////////////
    // 必要字段定义，参考文档数据仓库采集接口规范(1).docx的2.3.2节 消息头Header和2.3.3节 事件Event
    public static final String[] sGeneralNecessaryKeys = {
            "mac", "terminalType", "userId", "ip", "hardVer", /*"imei",*/
            "bizType", "eventId", "time", "appProduct", /*"data"*/
    };
    // 现有数据都没有data字段

    // 事件定义，参考文档数据仓库采集接口规范(1).docx的4.1节 事件大类定义
    public static final String sSystemRunningEvent     = "0";  // 系统运行
    public static final String sPageVisitEvent         = "1";  // 页面访问
    public static final String sExposureEvent          = "2";  // 曝光
    public static final String sSelectPlayEvent        = "3";  // 点播
    public static final String sPreferenceEvent        = "4";  // 喜好
    public static final String sBusinessUsingEvent     = "5";  // 业务使用
    public static final String sLiveEvent              = "6";  // 直播
    public static final String sTimeShiftingEvent      = "7";  // 时移
    public static final String sReviewEvent            = "8";  // 回看
    public static final String sApplicationEvent       = "9";  // 应用
    public static final String sSystemSettingEvent     = "10"; // 系统设置
    public static final String sShoppingEvent          = "11"; // 购物
    public static final String sTerminalExceptionEvent = "12"; // 终端异常

    //////////////////////////////////应用部分/////////////////////////////////////////////
    // 应用必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.13.3节 公共数据
    public static final String[] sApplicationNecessaryKeys = {
            "appId", "appName", "appVer"
    };

    // 应用事件定义，参考文档数据仓库采集接口规范(1).docx的4.13.2节 事件定义
    public static final String sApplicationDownloadEvent   = "001";    // 应用下载
    public static final String sApplicationRunningEvent    = "002";    // 应用运行

    //////////////////////////////////业务部分/////////////////////////////////////////////
    // 业务必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.10.3节 公共数据
    public static final String[] sBusinessNecessaryKeys = {
            "traceId"
    };
    // 若dvb频道则以tsId.serId格式上报id，如何确定是dvb频道？

    // 切换直播类型扩充必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.10.6节 事件-资源分享
    public static final String[] sResourceShareNecessaryKeys = {
            "id", "shareSite"
    };

    // 切换直播类型扩充必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.10.7节 事件-应用分享
    public static final String[] sApplicationShareNecessaryKeys = {
            "shareSite"
    };

    // 业务事件定义，参考文档数据仓库采集接口规范(1).docx的4.10.2节 事件定义
    public static final String sSearchEvent            = "001";    // 搜索
    public static final String sSearchSelectEvent      = "002";    // 搜索选择
    public static final String sFilterEvent            = "003";    // 筛选
    public static final String sFilterSelectEvent      = "004";    // 筛选选择
    public static final String sResourceShareEvent     = "005";    // 资源分享
    public static final String sApplicationShareEvent  = "006";    // 应用分享

    //////////////////////////////////页面访问部分//////////////////////////////////////////
    // 曝光事件必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.3.3节 公共数据
    public static final String[] sExposureNecessaryKeys = {
            "ids", "resType", "recomType", "recomPos",
            "traceId"
    };

    // Launcher事件扩充必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.3.3节 公共数据
    public static final String[] sLauncherExposureNecessaryKeys = {
            "itemTabId", "layout"
    };
    // 没有Launcher对应的事件id

    //////////////////////////////////曝光部分//////////////////////////////////////////
    // recomType段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.4.3节 公共数据
    public static final String[] sExposureRecomTypeValidValues = {
            "0", "1", "2"
    };

    // resType字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.4.3节 公共数据
    public static final String[] sExposureResTypeValidValues = {
            "asset", "channel", "ad", "epg",
            "richMedia"
    };

    // 列表资源曝光事件定义，参考文档数据仓库采集接口规范(1).docx的4.4.2节 事件定义
    public static final String sResourceListExposureEvent  = "001";    // 资源列表曝光

    //////////////////////////////////直播部分/////////////////////////////////////////////
    // 直播必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.6.3节 公共数据
    public static final String[] sLiveNecessaryKeys = {
            "id", "fromPlat", "resType", "recomType"
    };
    // 若dvb频道则以tsId.serId格式上报id，如何确定是dvb频道？

    // 切换直播类型扩充必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.6.3节 公共数据
    public static final String[] sSwitchLiveTypeNecessaryKeys = {
            "newType"
    };

    // fromPlat字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.6.3节 公共数据
    public static final String[] sLiveFromPlatValidValues = {
            "my", "huan"
    };

    // resType字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.6.3节 公共数据
    public static final String[] sLiveResTypeValidValues = {
            "asset", "channel", "ad", "epg"
    };

    // recomType字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.5.3节 公共数据
    public static final String[] sLiveRecomTypeValidValues = {
            "0", "1", "2"
    };

    // 直播事件定义，参考文档数据仓库采集接口规范(1).docx的4.6.2节 事件定义
    public static final String sEnterLiveChannelEvent  = "001";    // 进入直播频道
    public static final String sExit1Event             = "007";    // 退出
    public static final String sExit2Event             = "008";    // 退出
    public static final String sLiveVolumeUpEvent      = "009";    // 音量+
    public static final String sLiveVolumeDownEvent    = "010";    // 音量-
    public static final String sLivePlayDoneEvent      = "011";    // 结束
    public static final String sLivePlayBeatEvent      = "012";    // 播放心跳
    public static final String sSwitchChannelEvent     = "014";    // 切台
    public static final String sLiveChannelListEvent   = "015";    // 频道列表
    public static final String sLiveSilenceEvent       = "017";    // 静音
    public static final String sGroup2SingleEvent      = "018";    // 组播切单播
    public static final String sSingle2GroupEvent      = "019";    // 单播切组播
    public static final String sLiveOrderEvent = "020";    // 预约
    public static final String sOrderCancelEvent       = "021";    // 取消预约
    public static final String sSwitchLiveTypeEvent    = "022";    // 直播类型切换
    public static final String sSwitchProgrammeEvent   = "023";    // 节目单切换

    //////////////////////////////////页面访问部分//////////////////////////////////////////
    // 页面访问事件必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.3.3节 公共数据
    public static final String[] sPageVisitNecessaryKeys = {
            "optState", "id", "fromId"
    };
    // 点击或详情扩充必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.3.4节 事件-点击、详情
    public static final String[] sClickOrDetailNecessaryKeys = {
            "resType", "traceId"
    };
    // 启动页访问扩充必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.3.5节 Launcher访问
    public static final String[] sLauncherVisitNecessaryKeys = {
            "resType", "traceId", "layoutVersion", "layoutId",
            "itemAssetId", "itemTabId"
    };

    // optState字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.3.3节 公共数据
    public static final String[] sPageVisitOptStateValidValues = {
            "1", "2"
    };

    // resType字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.3.4节 事件-点击、详情和4.3.5节 Launcher访问
    public static final String[] sPageVisitResTypeValidValues = {
            "asset", "channel", "ad", "epg",
            "richMedia", "wikiEpg", "news"
    };

    // leaveType字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.3.6节 注册页面访问
    public static final String[] sLeaveTypeValidValues = {
            "0", "1", "2", "99"
    };
    // 没有注册页面访问对应的事件id

    // 页面访问事件定义，参考文档数据仓库采集接口规范(1).docx的4.3.2节 事件定义
    public static final String sLauncherVisitEvent     = "010";    // Launcher访问
    public static final String sApplicationURLEvent    = "011";    // 应用链接
    public static final String sTopicURLEvent          = "012";    // 专题链接
    public static final String sColumnURLEvent         = "013";    // 栏目链接
    public static final String sDetailURLEvent         = "014";    // 详情链接
    public static final String sURLEnterEvent          = "015";    // 链接进入
    public static final String sResourceClickEvent     = "016";    // 资源点击

    //////////////////////////////////喜好部分/////////////////////////////////////////////
    // 喜好事件必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.9.3节 公共数据
    public static final String[] sPreferenceNecessaryKeys = {
            "id", "fromPlat", "resType"
    };


    // fromPlat字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.9.3节 公共数据
    public static final String[] sPreferenceFromPlatValidValues = {
            "my", "huan"
    };

    // resType字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.9.3节 公共数据
    public static final String[] sPreferenceResTypeValidValues = {
            "asset", "channel"
    };

    // 喜好事件定义，参考文档数据仓库采集接口规范(1).docx的4.9.2节 事件定义
    public static final String sFavoriteEvent  = "001";    // 收藏
    public static final String sChaseEvent     = "003";    // 追剧

    //////////////////////////////////回看部分/////////////////////////////////////////////
    // 回看必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.8.3节 公共数据
    public static final String[] sReviewNecessaryKeys = {
            "id", "fromPlat", "resType", "traceId"
    };
    // 若dvb频道则以tsId.serId格式上报id，如何确定是dvb频道？


    // fromPlat字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.8.3节 公共数据
    public static final String[] sReviewFromPlatValidValues = {
            "my", "huan"
    };

    // resType字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.8.3节 公共数据
    public static final String[] sReviewResTypeValidValues = {
            "asset", "channel", "ad", "epg"
    };

    // 回看事件定义，参考文档数据仓库采集接口规范(1).docx的4.8.2节 事件定义
    public static final String sReviewDetailEvent = "000";    // 详情
    public static final String sReviewPlayEvent = "001";    // 播放
    public static final String sReviewPauseEvent = "002";    // 暂停
    public static final String sReviewRestoreEvent = "003";    // 恢复播放
    public static final String sReviewToggleEvent = "004";    // 拖动
    public static final String sReviewFastForwardEvent = "005";    // 快进
    public static final String sReviewFastBackwardEvent = "006";    // 快退
    public static final String sReviewExitEvent = "007";    // 退出
    public static final String sReviewExceptEvent = "008";    // 异常
    public static final String sReviewVolumeUpEvent        = "009";    // 音量+
    public static final String sReviewVolumeDownEvent     = "010";    // 音量-
    public static final String sReviewPlayDoneEvent        = "011";    // 结束
    public static final String sReviewPlayBeatEvent        = "012";    // 播放心跳
    public static final String sReviewChannelListEvent     = "015";    // 频道列表
    public static final String sChannelProgrammeListEvent  = "016";    // 频道节目单
    public static final String sReviewSilenceEvent         = "017";    // 静音
    public static final String sReviewSelectTimeEvent = "018";    // 选时播放

    //////////////////////////////////点播部分/////////////////////////////////////////////
    // 点播必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.5.3节 公共数据
    public static final String[] sSelectPlayNecessaryKeys = {
            "id", "fromPlat", "resType", "recomType",
            "recomPos", "traceId"
    };

    // fromPlat字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.5.3节 公共数据
    public static final String[] sSelectPlayFromPlatValidValues = {
            "my", "huan"
    };

    // resType字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.5.3节 公共数据
    public static final String[] sSelectPlayResTypeValidValues = {
            "asset"
    };

    // recomType字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.5.3节 公共数据
    public static final String[] sSelectPlayRecomTypeValidValues = {
            "0", "1", "2"
    };

    // 点播事件定义，参考文档数据仓库采集接口规范(1).docx的4.5.2节 事件定义
    public static final String sSelectPlayDetailEvent        = "000";    // 详情, 弃用？
    public static final String sSelectPlayPlayEvent          = "001";    // 播放
    public static final String sSelectPlayPauseEvent         = "002";    // 暂停
    public static final String sSelectPlayRestoreEvent       = "003";    // 恢复播放
    public static final String sSelectPlayToggleEvent        = "004";    // 拖动
    public static final String sSelectPlayFastForwardEvent   = "005";    // 快进
    public static final String sSelectPlayFastBackwardEvent  = "006";    // 快退
    public static final String sSelectPlayExitEvent          = "007";    // 退出
    public static final String sSelectPlayExceptEvent        = "008";    // 异常，弃用？
    public static final String sSelectPlayVolumeUpEvent      = "009";    // 音量+
    public static final String sSelectPlayVolumeDownEvent    = "010";    // 音量-
    public static final String sSelectPlayPlayDoneEvent      = "011";    // 结束
    public static final String sSelectPlayPlayBeatEvent      = "012";    // 播放心跳
    public static final String sSelectPlayOrderEvent         = "013";    // 订购
    public static final String sSelectPlaySilenceEvent       = "017";    // 静音
    public static final String sSelectPlaySelectTimeEvent    = "018";    // 选时播放

    //////////////////////////////////购物部分/////////////////////////////////////////////
    // 商品页面访问必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.14.4节 事件-商品页面访问
    public static final String[] sItemPageVisitNecessaryKeys = {
            "optState", "id", "fromId"
    };

    // 商品列表曝光必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.14.5节 事件-商品列表曝光
    public static final String[] sItemListExposureNecessaryKeys = {
            "ids", "rowNum"
    };

    // 商品详情点击必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.14.6节 事件-商品详情点击
    public static final String[] sItemDetailClickNecessaryKeys = {
            "id"
    };

    // 商品详情退出必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.14.7节 事件-商品详情退出
    public static final String[] sItemDetailExitNecessaryKeys = {
            "id", "duration"
    };

    // optState字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.14.7节 事件-商品详情退出
    public static final String[] sShoppingOptStateValidValues = {
            "1", "2"
    };

    // 购物事件定义，参考文档数据仓库采集接口规范(1).docx的4.14.2节 事件定义
    public static final String sItemPageVisitEvent     = "000";    // 商品页面访问
    public static final String sItemListExposureEvent  = "001";    // 商品列表曝光
    public static final String sItemDetailClickEvent   = "002";    // 商品详情点击
    public static final String sItemDetailExitEvent    = "003";    // 商品详情退出

    //////////////////////////////////系统运行部分//////////////////////////////////////////
    // 系统升级必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.2.3 公共数据
    public static final String[] sUpgradeNecessaryKeys = {
            "upgradeType"
    };

    // upgrade字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.2.3 公共数据
    public static final String[] sUpgradeValidValues = {
            "1", "2"
    };

    // 系统运行事件定义，参考文档数据仓库采集接口规范(1).docx的4.2节 系统运行
    public static final String sBootEvent      = "0";  // 系统运行
    public static final String sShutdownEvent  = "1";  // 页面访问
    public static final String sRebootEvent    = "2";  // 曝光
    public static final String sUpgradeEvent   = "3";  // 点播

    //////////////////////////////////系统设置部分//////////////////////////////////////////
    // 应用信息上报必要字段定义，参考文档参考文档数据仓库采集接口规范(1).docx的4.11.7节 事件-应用信息上报
    public static final String[] sApplicationInfoNecessaryKeys = {
            "appId", "appName", "appVer", "appSize"
    };

    // 系统设置事件定义，参考文档数据仓库采集接口规范(1).docx的4.11.2节 事件定义
    public static final String sWIFISettingEvent       = "001";    // Wifi设置
    public static final String sDPIEvent               = "002";    // 分辨率
    public static final String sSystemUpgradeEvent     = "003";    // 系统升级
    public static final String sPageSettingEvent       = "004";    // 页面设置（重庆）
    public static final String sDisplayModeEvent       = "005";    // 显示模式（重庆）
    public static final String sProjectMenuEvent       = "006";    // 工程菜单（重庆）
    public static final String sApplicationInfoEvent   = "007";    // 应用信息（重庆）

    //////////////////////////////////终端异常部分//////////////////////////////////////////
    // 终端异常必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.12.3节 公共数据
    public static final String[] sTerminalExceptionNecessaryKeys = {
            "bizId", "errCode"
    };

    // 终端异常事件定义，参考文档数据仓库采集接口规范(1).docx的4.12.2节 事件定义
    public static final String sROMExceptionEvent          = "001";    // Wifi设置
    public static final String sApplicationExceptionEvent  = "002";    // 分辨率

    //////////////////////////////////时移部分/////////////////////////////////////////////
    // 时移必要字段定义，参考文档数据仓库采集接口规范(1).docx的4.7.3节 公共数据
    public static final String[] sTimeShiftingNecessaryKeys = {
            "id", "resType", "traceId"
    };
    // 若dvb频道则以tsId.serId格式上报id，如何确定是dvb频道？

    // resType字段有效值定义，参考文档数据仓库采集接口规范(1).docx的4.7.3节 公共数据
    public static final String[] sTimeShiftingResTypeValidValues = {
            "asset", "channel", "ad", "epg"
    };

    // 时移事件定义，参考文档数据仓库采集接口规范(1).docx的4.7.2节 事件定义
    public static final String sEnterTimeShiftingEvent  = "001";    // 进入时移
    public static final String sTimeShiftingPauseEvent              = "002";    // 暂停
    public static final String sTimeShiftingRestoreEvent            = "003";    // 恢复
    public static final String sTimeShiftingToggleEvent             = "004";    // 拖动
    public static final String sTimeShiftingFastForwardEvent        = "005";    // 快进
    public static final String sTimeShiftingFastBackwardEvent       = "006";    // 快退
    public static final String sTimeShiftingExitEvent               = "007";    // 退出
    public static final String sTimeShiftingExceptEvent             = "008";    // 异常
    public static final String sTimeShiftingVolumeUpEvent           = "009";    // 音量+
    public static final String sTimeShiftingVolumeDownEvent         = "010";    // 音量-
    public static final String sTimeShiftingPlayDoneEvent           = "011";    // 结束
    public static final String sTimeShiftingPlayBeatEvent           = "012";    // 播放心跳
    public static final String sTimeShiftingSilenceEvent            = "017";    // 静音
    public static final String sTimeShiftingSelectTimeEvent         = "018";    // 选时播放
}

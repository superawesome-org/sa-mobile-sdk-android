
(function() {
    // Dynamic values from page request

    var runtimeParams = {"tagVersion":"url-standard-7","deviceInfo":{"deviceType":"Desktop","primaryHardwareType":"desktop","mobileDevice":false,"osName":"OSX","osVersion":"10.15.7","platform":"DesktopPlatform","platformVersion":null,"browserName":"Chrome","browserVersion":"119.0.0.0","browserRenderingEngine":"WebKit","vendor":"Apple","model":null},"weather":{"windy":"0","currentCondition":"sunny","apparentTemperature":1.0,"temperature":4.6,"windSpeed":9,"cloudCoverage":19,"conditions":[{"name":"sunny","weight":1}]},"ipCountryCode":"GB","ipRegionCode":null,"ipPrincipalSubdivisionCode":"ENG","ipCityGeonameId":"2647428","ipCity":"Harrogate","ipPostalCode":"HG1","ipLat":54.0001,"ipLng":-1.5352,"accountId":"fd236ef7","folderId":"84f6ae69","placementId":"4f6465f4","supplierId":null,"sessionId":"s1700840701x6bbbf0b8892890x90184564","purpose":"live","secure":1,"clientTimestamp":null,"clientTimeZoneOffsetInMinutes":null,"clientIp":"86.177.65.0","gpsLat":54.0001,"gpsLng":-1.5352,"language":"en","acceptLanguage":"en-GB,en-US;q=0.9,en;q=0.8","platformAdvId":null,"platformAdvIdTrackingLimited":null,"userIdentifiers":{},"variantChoices":{"EventMetadata":"newMeta"},"neustarSegment":null,"customAudiences":{},"derivedAudiences":{},"destinationDefinitions":[],"dynamicContent":[],"moatCustomAttributes":{},"admarvel_format":null,"overrides":{"placementId":"4f6465f4","customAudiences":{},"derivedAudiences":{},"deviceInfo":{"deviceType":"Desktop","primaryHardwareType":"desktop","mobileDevice":false,"osName":"OSX","osVersion":"10.15.7","platform":"DesktopPlatform","platformVersion":null,"browserName":"Chrome","browserVersion":"119.0.0.0","browserRenderingEngine":"WebKit","vendor":"Apple","model":null},"language":"en","ipCountryCode":"GB","ipRegionCode":null,"ipPrincipalSubdivisionCode":"ENG","ipCityGeonameId":"2647428","ipCity":"Harrogate","weather":{"windy":"0","currentCondition":"sunny","apparentTemperature":1.0,"temperature":4.6,"windSpeed":9,"cloudCoverage":19,"conditions":[{"name":"sunny","weight":1}]},"clientTimestamp":null,"clientTimeZoneOffsetInMinutes":null},"utSignals":null,"_mraidCheck":null,"fallbackSdk":null,"passthroughParameters":{},"googleMapsClientKey":null,"shouldShowMapsPrompt":false,"pageSkinsHost":null,"pageSkinsPath":null,"stickToPageHeader":null,"externalAdServer":"Custom","externalCreativeId":null,"externalCreativeName":null,"externalPlacementId":null,"externalPlacementName":null,"externalSiteId":null,"externalSiteName":null,"externalSupplierId":null,"externalSupplierName":null,"externalLineItemId":null,"externalSessionId":null,"externalBundleId":null,"externalCreativeWidth":null,"externalCreativeHeight":null,"externalCampaignId":null,"externalCampaignName":null,"externalAdvertiserId":null,"externalDestinationUrl":null,"clickUrl":null,"useClickAsDestination":null,"scriptId":"blank-script-id","firehoseUrl":null,"clickEvent":null,"clickUrlNeedsDest":null,"ncu":null,"firstPage":1,"dataURIsEnabled":0,"monotypeProjectId":"c46ed090-3671-4163-a85b-b06b4038ae38","iosAdvId":null,"iosAdvIdTrackingLimited":null,"androidAdvId":null,"androidAdvIdTrackingLimited":null,"moatRandom":{"first":804042727,"second":1180733071},"skipOffset":null,"_enablePoliteLoading":null,"massProductionPreview":null,"fontSubsetterIsDisabled":null,"_politeImageUrl":null,"_politeClickThrough":null,"sticky":null,"_mopubExt":null,"enabledServices":[],"creativeVariantLockSize":null,"screenIdLock":null,"vastVersion":null,"standalonePreview":null,"campaignExplorer":null,"exportingFirstFrame":null,"exportingFrameRate":null,"exportingVideoAssetBlobHash":null,"country":"GB","productCategoryCode":null,"campaignName":"SDK VPAID Test Auto","agencyId":null,"customPartnerAttributeBrandId":null,"suppressCloseButton":null,"featureFlags":{"defaultVideo":true,"perAccountRateLimiting":false,"iOS17InlineVideoFix":true,"noConfigSkinPositioning":false},"eventIndex":"0"};
    runtimeParams.overridableClickThroughDestinationUrl = false;
    runtimeParams.redirectJsClientTimestamp = new Date() / 1000;



var macros = function (x) {
    if (x instanceof Array) {
        return x.map(macros);
    } else {
        var macroTags = [
            ['%%CACHEBUSTER%%', (Math.random()+'').slice(2)]
,['%n', (Math.random()+'').slice(2)]
,['%s', "https"]
,['\\[timestamp\\]', (Math.random()+'').slice(2)]
,['\\{celtraAccountId\\}', "fd236ef7"]
,['\\{celtraAgencyId\\}', ""]
,['\\{celtraAndroidAdvIdTrackingLimitedBoolStr\\}', ""]
,['\\{celtraAndroidAdvIdTrackingLimited\\}', ""]
,['\\{celtraAndroidAdvId\\}', ""]
,['\\{celtraCampaignId:int\\}', "2230759017"]
,['\\{celtraCampaignId\\}', "84f6ae69"]
,['\\{celtraCampaignName\\}', "SDK%20VPAID%20Test%20Auto"]
,['\\{celtraCountryCode\\}', "GB"]
,['\\{celtraCreativeId:int\\}', "754413262"]
,['\\{celtraCreativeId\\}', "2cf76ece"]
,['\\{celtraCreativeVariant:urlenc\\}', ""]
,['\\{celtraCreativeVariant\\}', ""]
,['\\{celtraCustomPartnerAttribute\\[code\\]\\}', ""]
,['\\{celtraExternalAdServer\\}', "Custom"]
,['\\{celtraExternalAdvertiserId\\}', ""]
,['\\{celtraExternalBundleId\\}', ""]
,['\\{celtraExternalCampaignId\\}', ""]
,['\\{celtraExternalCampaignName\\}', ""]
,['\\{celtraExternalCreativeId\\}', ""]
,['\\{celtraExternalCreativeName\\}', ""]
,['\\{celtraExternalDestinationUrl\\}', ""]
,['\\{celtraExternalLineItemId\\}', ""]
,['\\{celtraExternalPlacementId\\}', ""]
,['\\{celtraExternalPlacementName\\}', ""]
,['\\{celtraExternalSessionId\\}', ""]
,['\\{celtraExternalSiteId\\}', ""]
,['\\{celtraExternalSiteName\\}', ""]
,['\\{celtraExternalSupplierId\\}', ""]
,['\\{celtraExternalSupplierName\\}', ""]
,['\\{celtraFeedReportLabel\\}', ""]
,['\\{celtraIosAdvIdTrackingLimitedBoolStr\\}', ""]
,['\\{celtraIosAdvIdTrackingLimited\\}', ""]
,['\\{celtraIosAdvId\\}', ""]
,['\\{celtraPlacementId:int\\}', "1331979764"]
,['\\{celtraPlacementId\\}', "4f6465f4"]
,['\\{celtraPlatformAdvIdTrackingLimited\\}', ""]
,['\\{celtraPlatformAdvId\\}', ""]
,['\\{celtraProductCategoryCode\\}', ""]
,['\\{celtraProto\\}', "https"]
,['\\{celtraRandom\\}', (Math.random()+'').slice(2)]
,['\\{celtraSessionId\\}', "s1700840701x6bbbf0b8892890x90184564"]
,['\\{celtraSupplierId:int\\}', ""]
,['\\{celtraSupplierId\\}', ""]

        ];
        return macroTags.reduce(function(str, replacementRule, idx, arr) {
            return str.replace(new RegExp(replacementRule[0], 'ig'), replacementRule[1] ? replacementRule[1] : '');
        }, x);
    }
};

    // Dynamic values that we do not want to pass forward in urls,
    // so we look them up on every page request based on runtimeParams
    var urlOpenedOverrideUrls = {};
    var storeOpenedOverrideUrls = {};
    var urlOpenedUrlAppendage = "";
    var clickThroughDestinationUrl = null;

    // Less dynamic values for payload request
    var payloadBase = "https://cache-ssl.celtra.com/api/creatives/2cf76ece/compiled/vpaid.js";
    var cacheParams = {"v": "2-a289ee2c04", "secure": 1, "cachedVariantChoices": "W10-", "isPurposePreview": 0, "eventMetadataExperiment": 'newMeta', "inmobi": typeof imraid !== 'undefined' && typeof _im_imai !== 'undefined' ? '1' : '0'};


    var trackers = (function() {
    return [
        // 3rd-party tracker (regular)
function(event) {
    if (event.name == 'adLoading')
        return {urls: macros([])};

    if (event.name == 'firstInteraction')
        return {urls: macros([])};

    if (event.name == 'creativeLoaded')
        return {urls: macros([])};

    if (event.name == 'creativeRendered')
        return {urls: macros([])};

    if (event.name == 'viewable00')
        return {urls: macros([])};

    if (event.name == 'viewable501')
        return {urls: macros([])};

    if (event.name == 'expandRequested')
        return {urls: macros([])};

    if (event.name == 'videoPlayInitiated')
        return {urls: macros([])};

    if (event.clazz === "MasterVideo") {
        if (event.name == 'videoStart')
            return {urls: macros([])};

        if (event.name == 'videoFirstQuartile')
            return {urls: macros([])};

        if (event.name == 'videoMidpoint')
            return {urls: macros([])};

        if (event.name == 'videoThirdQuartile')
            return {urls: macros([])};

        if (event.name == 'videoComplete')
            return {urls: macros([])};
    }

    if (event.name == 'videoPause')
        return {urls: macros([])};

    if (event.name == 'videoMuted')
        return {urls: macros([])};

    if (event.name == 'videoUnmuted')
        return {urls: macros([])};

    if (event.name == 'custom')
        return {urls: macros({}[event.label] || [])};

    if (event.name == 'urlOpened')
        return {urls: macros({}[event.label] || [])};

    if (event.name == 'storeOpened')
        return {urls: macros({}[event.label] || [])};
},
// 3rd-party tracker (click regular)
function(event) {
    if (event.name === "urlOpened" || event.name === "storeOpened" || event.name === "clickThroughDestinationOpened")
        return {urls: macros([]), events: [{name: 'click'}] };
}
    ]
})();
    trackers.urlsAndEventsFor = function(event) {
        return this.reduce(function(acc, tracker) {
            var ue = tracker(event) || {};
            return {
                urls:   acc.urls.concat(ue.urls || []),
                events: acc.events.concat(ue.events || [])
            };
        }, {urls: [], events: []});
    };



var adLoadingEvent = {"name":"adLoading","sessionId":"s1700840701x6bbbf0b8892890x90184564"};
adLoadingEvent.clientTimestamp = new Date/1000;

trackers.urlsAndEventsFor(adLoadingEvent).urls.forEach(function(url) {
    // On IE 8+ URLs containing '%' character sometimes throw an error and
    // stop current JS run loop. One solution  would be to look for that
    // and replace it with '%25', but we've decided not to modify incoming
    // URLs, because this issue is really a combination of two external
    // problems: broken URL on a buggy browser.
    // https://celtra.atlassian.net/browse/MAB-4476
    try {
        var img = new Image;

        img.src = url;
    } catch(e) {}
});



(function () {
    runtimeParams.protoLoading = {};

    var base64img = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH4wEKCBsN103sxwAAAB1pVFh0Q29tbWVudAAAAAAAQ3JlYXRlZCB3aXRoIEdJTVBkLmUHAAAAC0lEQVQI12NgAAIAAAUAAeImBZsAAAAASUVORK5CYII=";

    runtimeParams.protoLoading.dataLoadStatus = null;
    // Test if CSP is blocking "data:" source for images
    var dataImg = new Image();
    dataImg.onload = function() {
        runtimeParams.protoLoading.dataLoadStatus = "supported";
    };
    dataImg.onerror = function(e) {
        runtimeParams.protoLoading.dataLoadStatus = "blocked";
    };
    dataImg.src = "data:image/png;base64," + base64img;

    runtimeParams.protoLoading.blobLoadStatus = null;
    // Test if CSP is blocking "blob:" source for images
    var url = null;
    try {
        var binaryImg = atob(base64img);
        var length = binaryImg.length;
        var ab = new ArrayBuffer(length);
        var ua = new Uint8Array(ab);
        for (var i = 0; i < length; i++) {
            ua[i] = binaryImg.charCodeAt(i);
        }
        var blob = new Blob([ab], {type: 'image/png'});
        url = URL.createObjectURL(blob);
    } catch(error) {
        runtimeParams.protoLoading.blobLoadStatus = "error";
        runtimeParams.protoLoading.blobErrorMessage = error.name + ': ' + error.message;
        return;
    }
    var blobImg = new Image();
    blobImg.onload = function() {
        runtimeParams.protoLoading.blobLoadStatus = "supported";
        URL.revokeObjectURL(url);
    };
    blobImg.onerror = function(e) {
        runtimeParams.protoLoading.blobLoadStatus = "blocked";
    };
    blobImg.src = url;
})();


    function buildPayloadUrl(payloadBase) {
      var pairs = [];
      for (var k in cacheParams)
          pairs.push(encodeURIComponent(k) + '=' + encodeURIComponent(cacheParams[k]));
      return payloadBase + '?' + pairs.join('&');
    }

    var payloadUrl = buildPayloadUrl(payloadBase);

// Request and run payload
var payload = document.createElement('script');
var payloadWin = null;
payload.src = "http://localhost:8080/yellow_box_celtra_payload"; //payloadUrl
payload.onload = function() {
    runtimeParams.payloadJsClientTimestamp = new Date() / 1000;
    try {
        payloadWin.celtraDeviceInfoRuntimeParams = runtimeParams.deviceInfo;
        payloadWin.celtra.payloads[payloadUrl](payload, runtimeParams, trackers, urlOpenedOverrideUrls, storeOpenedOverrideUrls, macros, urlOpenedUrlAppendage, clickThroughDestinationUrl);
    } catch (e) {
        console.log('Unable to initialize VPAID creative payload, could not find celtra property on window.');
    }
};
var insertPayloadScript = function(vpaidApi, width, height, viewMode, desiredBitrate, creativeData, environmentVars) {
    var doc = environmentVars.slot.ownerDocument;
    payloadWin = doc.defaultView || doc.parentWindow;
    environmentVars.slot.appendChild(payload);
    payload.vpaidEnvironment = {
        vpaidApi        : vpaidApi,
        width           : width,
        height          : height,
        viewMode        : viewMode,
        desiredBitrate  : desiredBitrate,
        creativeData    : creativeData,
        environmentVars : environmentVars
    };
};
    function inherit(e,t){function n(){}n.prototype=t.prototype,e.prototype=new n,Object.defineProperties(e,{uber:{get:function(){return t.prototype},enumerable:!1,configurable:!1},uberConstructor:{get:function(){return t},enumerable:!1,configurable:!1}}),e.prototype.constructor=e}function extend(e,t){for(var n=1;n<arguments.length;n++){t=arguments[n];if(t instanceof Object)for(var r in t)e[r]=t[r]}return e}function deep(e,t){for(var n in t)e[n]instanceof Object&&t[n]instanceof Object?deep(e[n],t[n]):e[n]=t[n]}function delayed(e,t){var n=null;return function(){n&&clearTimeout(n),n=setTimeout(e,t)}}function throttled(e,t){var n=null;return function(){n=n||setTimeout(function(){n=null,e()},t)}}function deferred(e,t,n,r){return function(){defer(e,t,n,r)}}function useAsap(){return"undefined"!=typeof creative&&creative.adapter&&creative.adapter.useAsap||"undefined"!=typeof adapter&&adapter.useAsap}function hasClass(e,t){return e.classList.contains(t)}function addClass(e,t){e.classList.add(t)}function removeClass(e,t){e.classList.remove(t)}function toggleClass(e,t){e.classList.toggle(t)}function cssurl(e){return"url('"+e.replace(/'/g,"\\'")+"')"}function camelize(e){return e.replace(/-([a-z])/g,function(e,t){return t.toUpperCase()})}function ucfirst(e){return e.charAt(0).toUpperCase()+e.slice(1)}function zeroPad(e,t){null==t&&(t=2);t=Math.max(0,t-(""+e).length);return(""+Math.pow(10,t)).slice(1)+e}function htmlentitize(e){return e.replace(/&/g,"&amp;").replace(/"/g,"&quot;").replace(/'/g,"&#39;").replace(/</g,"&lt;").replace(/>/g,"&gt;")}function removeHtmlTags(e){for(var t,n="(?:[^\"'>]|\"[^\"]*\"|'[^']*')*",r=new RegExp("<(?:!--(?:(?:-*[^->])*--+|-?)|script\\b"+n+">[\\s\\S]*?</script\\s*|style\\b"+n+">[\\s\\S]*?</style\\s*|/?[a-z]"+n+")>","gi");(e=(t=e).replace(r,""))!==t;);return e.replace(/</g,"&lt;")}function trim(e){return e.replace(/^\s+|\s+$/g,"")}function to_utf8(e){return unescape(encodeURIComponent(e))}function isRTLChar(e){return!!(e=String(e).charCodeAt(0))&&(1424<=e&&e<=1535||1536<=e&&e<=1791||1792<=e&&e<=1871||1872<=e&&e<=1919||2144<=e&&e<=2159||2208<=e&&e<=2303||64288<=e&&e<=64335||64336<=e&&e<=65023||65136<=e&&e<=65279||8207===e||8235===e||8238===e)}function isLTRChar(e){return!!(e=String(e).charCodeAt(0))&&(65<=e&&e<=90||97<=e&&e<=122||192<=e&&e<=214||216<=e&&e<=246||248<=e&&e<=696||768<=e&&e<=1424||2048<=e&&e<=8191||11264<=e&&e<=64284||65022<=e&&e<=65135||65277<=e&&e<=65535||8206===e||8234===e||8237===e)}function guessWritingDirection(e){for(var t=Math.min(25,e.length),n=0;n<t;n++){var r=e.charAt(n);if(isLTRChar(r))return"ltr";if(isRTLChar(r))return"rtl"}return null}function randInt(){return(Math.random()+"").slice(2)}function _isListenerOptionSupported(e){var t=_isListenerOptionSupported.opts;if(void 0===t[e]){t[e]=!1;try{window.addEventListener("listener-test",null,Object.defineProperty({},e,{get:function(){t[e]=!0}}))}catch(e){}}return t[e]}function _buildNativeListenerOptions(e){var t;return"object"!=typeof e?!!e:_isListenerOptionSupported("capture")?(t={capture:!!e.capture},_isListenerOptionSupported("passive")&&(t.passive=!!e.passive),t):!!e.capture}function _analyzeTouch(e){var t,n=Math.abs(e.firstTouch.x-e.lastTouch.x),r=Math.abs(e.firstTouch.y-e.lastTouch.y);return"y"===e.validAxis?t=r>e.minDistanceForSwipe:"x"===e.validAxis&&(t=n>e.minDistanceForSwipe),{isHorizontal:r<n,isConsideredSwipe:t}}function attach(e,t,n,r){t=getEventNames()[t.toLowerCase()]||t;e.addEventListener(t,n,_buildNativeListenerOptions(r))}function detach(e,t,n,r){t=getEventNames()[t.toLowerCase()]||t;e.removeEventListener(t,n,_buildNativeListenerOptions(r))}function once(t,n,r,o){attach(t,n,function e(){detach(t,n,e,o),r.apply(this,arguments)},o)}function trigger(e,t,n,r){var o=document.createEvent("HTMLEvents"),n=(o.initEvent(t,n,r),"on"+ucfirst(t));return"function"==typeof e[n]&&e[n](),e.dispatchEvent(o)}function fakeclick(t,e){var n=(e=e||window).document.createElement("a"),e=(n.cssText="visibility: hidden",n.addEventListener("click",function(e){t(),e.preventDefault(),e.stopPropagation(),n.parentNode.removeChild(n)},!0),e.document.body.appendChild(n),document.createEvent("MouseEvents"));e.initEvent("click",!0,!0),n.dispatchEvent(e)}function fakeClickAhrefBlank(e,t,n){t=t||noop;var r=(n=n||window).document.createElement("a"),e=(r.style.cssText="visibility: hidden",r.setAttribute("href",e),r.setAttribute("target","_blank"),r.addEventListener("click",function(e){e.stopPropagation(),t(),r.parentNode.removeChild(r)},!0),n.document.body.appendChild(r),n.document.createEvent("MouseEvents"));e.initEvent("click",!0,!0),r.dispatchEvent(e)}!function(a){if(!this.defer){try{for(;void 0!==a.parent.location.href&&a.parent.document!==a.document;)a=a.parent}catch(e){}e="function"==typeof(u=a).setImmediate;var t,n,r,o,i,c,u,e,s=u.MutationObserver?function(e){t=document.createElement("div"),new MutationObserver(function(){e(),t=null}).observe(t,{attributes:!0}),t.setAttribute("i","1")}:!e&&u.postMessage&&!u.importScripts&&u.addEventListener?(o="com.setImmediate"+Math.random(),i=0,c={},u.addEventListener("message",function(e){e.source===u&&0===e.data.indexOf(o)&&(e=e.data.split(":")[1],c[e](),delete c[e])},!1),function(e){var t=9007199254740991===i?0:++i;c[t]=e,u.postMessage(o+":"+t,"*")}):!e&&u.document&&"onreadystatechange"in document.createElement("script")?function(e){(n=document.createElement("script")).onreadystatechange=function(){n.onreadystatechange=null,n.parentNode.removeChild(n),n=null,e()},document.body.appendChild(n)}:(r=e&&setImmediate||setTimeout,function(e){r(e)});this.defer=function(e,t,n,r){var o,i,t=0|t;n&&(void 0!==defer.perf?i=defer.perf:"undefined"!=typeof creative?i=creative.perf:"undefined"!=typeof perf&&(i=perf),i&&!i._stopTrackingDefers&&(o=i.start("defer",{deferId:n,delay:t}))),e&&(i=function(){o&&o.end(),e()},r?s(i):a.setTimeout(i,t))}}}(window),Function.prototype.bind||Object.defineProperty(Function.prototype,"bind",{value:function(e){var t=this;return function(){return t.apply(e,arguments)}}}),function(t){t.getEventNames=function(){var e={};return"WebKitAnimationEvent"in t&&(e.animationstart="webkitAnimationStart",e.animationiteration="webkitAnimationIteration",e.animationend="webkitAnimationEnd"),"WebKitTransitionEvent"in t&&(e.transitionend="webkitTransitionEnd"),e}}(window),_isListenerOptionSupported.opts={};var getTimestamp=void 0===window.performance||void 0===window.performance.now?Date.now:window.performance.now.bind(window.performance);function noop(){}function nullai(e,t){t&&t()}function retTrue(){return!0}function retFalse(){return!1}function offset(e){var t=e.getBoundingClientRect(),e=e.ownerDocument,n=e.documentElement,e=e.defaultView;return{top:t.top+(e.pageYOffset||n.scrollTop)-(n.clientTop||0),left:t.left+(e.pageXOffset||n.scrollLeft)-(n.clientLeft||0)}}function addCssRule(e,t,n){var r=document.createElement("style");r.textContent=e+" {"+t+"}",(n?n.document:document).getElementsByTagName("head")[0].appendChild(r)}function redrawAndroidIframe(){var e=document.createElement("style");document.body.appendChild(e),document.body.removeChild(e)}function parseQuery(e){var t={};return(e=e.replace(/\&$/,"").replace(/\+/g,"%20")).split("&").forEach(function(e){e=e.split("=").map(decodeURIComponent);t[e[0]]=e[1]}),t}function buildQuery(e){var t,n=[];for(t in e)n.push(encodeURIComponent(t)+"="+encodeURIComponent(e[t]));return n.join("&")}function postBlob(e,t){var n=new XMLHttpRequest;n.open("POST",creative.apiUrl+"blobs?base64=1"),n.setRequestHeader("Content-Type","application/octet-stream"),n.onreadystatechange=function(){4===n.readyState&&t(n.responseText,n.status)},n.send(e)}function tmpl(e,t){if(!e)return"";var n;if(-1==e.indexOf("<%"))n=function(){return e};else{var r=e.split(/<%\s*|\s*%>/g),o="var p = []; with(o) {\n",i=!1;r.forEach(function(e){i?"="==e[0]?o+="  p.push("+e.replace(/^=\s*|\s*$/g,"")+");\n":o+="  "+e+"\n":e&&(o+="  p.push('"+e.replace(/'/g,"\\'").split(/\r?\n/g).join("\\n');\n  p.push('")+"');\n"),i=!i}),o+='} return p.join("");';try{n=new Function("o",o)}catch(e){r=new Error("Cannot parse template! (see `template` property)");throw r.template=o,r}}return t?n(t):n}function flash(){var e=document.createElement("div");e.style.background="white",e.style.opacity=.005,e.style.position="absolute",e.style.top=0,e.style.left=0,e.style.width="100%",e.style.height="100%",e.style.zIndex=2147483647,document.body.appendChild(e),setTimeout(function(){e.parentNode.removeChild(e)},0)}function crc32(e){"use strict";for(var t=-1,n=0,r=[0,-227835133,-516198153,324072436,-946170081,904991772,648144872,-724933397,-1965467441,2024987596,1809983544,-1719030981,1296289744,-1087877933,-1401372889,1578318884,274646895,-499825556,-244992104,51262619,-675000208,632279923,922689671,-996891772,-1702387808,1760304291,2075979607,-1982370732,1562183871,-1351185476,-1138329528,1313733451,549293790,-757723683,-1048117719,871202090,-416867903,357341890,102525238,-193467851,-1436232175,1477399826,1264559846,-1187764763,1845379342,-1617575411,-1933233671,2125378298,820201905,-1031222606,-774358714,598981189,-143008082,85089709,373468761,-467063462,-1170599554,1213305469,1526817161,-1452612982,2107672161,-1882520222,-1667500394,1861252501,1098587580,-1290756417,-1606390453,1378610760,-2032039261,1955203488,1742404180,-1783531177,-878557837,969524848,714683780,-655182201,205050476,-28094097,-318528869,526918040,1361435347,-1555146288,-1340167644,1114974503,-1765847604,1691668175,2005155131,-2047885768,-604208612,697762079,986182379,-928222744,476452099,-301099520,-44210700,255256311,1640403810,-1817374623,-2130844779,1922457750,-1503918979,1412925310,1197962378,-1257441399,-350237779,427051182,170179418,-129025959,746937522,-554770511,-843174843,1070968646,1905808397,-2081171698,-1868356358,1657317369,-1241332974,1147748369,1463399397,-1521340186,-79622974,153784257,444234805,-401473738,1021025245,-827320098,-572462294,797665321,-2097792136,1889384571,1674398607,-1851340660,1164749927,-1224265884,-1537745776,1446797203,137323447,-96149324,-384560320,461344835,-810158936,1037989803,781091935,-588970148,-1834419177,1623424788,1939049696,-2114449437,1429367560,-1487280117,-1274471425,1180866812,410100952,-367384613,-112536529,186734380,-538233913,763408580,1053836080,-860110797,-1572096602,1344288421,1131464017,-1323612590,1708204729,-1749376582,-2065018290,1988219213,680717673,-621187478,-911630946,1002577565,-284657034,493091189,238226049,-61306494,-1307217207,1082061258,1395524158,-1589280451,1972364758,-2015074603,-1800104671,1725896226,952904198,-894981883,-638100751,731699698,-11092711,222117402,510512622,-335130899,-1014159676,837199303,582374963,-790768336,68661723,-159632680,-450051796,390545967,1230274059,-1153434360,-1469116676,1510247935,-1899042540,2091215383,1878366691,-1650582816,-741088853,565732008,854102364,-1065151905,340358836,-433916489,-177076669,119113024,1493875044,-1419691417,-1204696685,1247431312,-1634718085,1828433272,2141937292,-1916740209,-483350502,291187481,34330861,-262120466,615137029,-691946490,-980332558,939183345,1776939221,-1685949482,-1999470558,2058945313,-1368168502,1545135305,1330124605,-1121741762,-210866315,17165430,307568514,-532767615,888469610,-962626711,-707819363,665062302,2042050490,-1948470087,-1735637171,1793573966,-1104306011,1279665062,1595330642,-1384295599],n=0;n<e.length;n++)t=t>>>8^r[255&(t^e.charCodeAt(n))];return(-1^t)>>>0}function isArray(e){return"[object Array]"===Object.prototype.toString.call(e)}function isDefAndNotNull(e){return null!=e}function updateQueryStringParameter(e,t,n){t=encodeURIComponent(t),n=encodeURIComponent(n);var r=new RegExp("([?|&])"+t+"=.*?(&|$)","i");return separator=-1!==e.indexOf("?")?"&":"?",e.match(r)?e.replace(r,"$1"+t+"="+n+"$2"):e+separator+t+"="+n}function fetchShortenedUrl(r,e,t){t=t||noop;var n,o=!1,i=fetchShortenedUrl.inProgress,a=fetchShortenedUrl.cache;function c(t,n){i[r].forEach(function(e){e[t](n)}),delete i[r]}"http"==!r.slice(0,4)?t():a[r]?defer(function(){e(a[r])},0,"fetchShortenedUrl defer success callback"):(i[r]||(i[r]=[],o=!0),i[r].push({success:e,error:t}),o&&(t=(creative.runtimeParams.secure?creative.cachedApiUrl:creative.insecureCachedApiUrl)+"shortenedUrls/",o={url:r,fields:"shortUrlKey"},n={cbName:"shortener_"+btoa(to_utf8(r)).replace(/\//g,"$").replace(/\+/g,"_").replace(/=/g,"")},loadJSONP(t+"?"+buildQuery(o),n,function(e){e=creative.shareUrl+e.shortUrlKey;c("success",a[r]=e)},function(){requestCreateShortenedUrl(r,c)})))}function requestCreateShortenedUrl(t,n){var e=(creative.runtimeParams.secure?creative.apiUrl:creative.insecureApiUrl)+"shortenedUrls/",r=fetchShortenedUrl.cache,o=new XMLHttpRequest;o.open("POST",e),o.setRequestHeader("Content-Type","application/json; charset=utf-8"),o.onreadystatechange=function(){var e;4===o.readyState&&(201===o.status?(e=JSON.parse(o.responseText),e=creative.shareUrl+e.shortUrlKey,r[t]=e,n("success",e)):n("error"))},o.send(JSON.stringify({url:t}))}function isMediaPlaying(e){try{return 0<e.currentTime&&!e.paused&&!e.ended}catch(e){return!1}}function merge(){for(var e,t={},n=0;n<arguments.length;n+=1)if(null!=(e=arguments[n]))for(var r in e)t[r]=e[r];return t}function clamp(e,t,n){return Math.max(e,Math.min(t,n))}function lerp(e,t,n){return e+n*(t-e)}function map(e,t,n,r,o){return(o-e)/(t-e)*(r-n)+n}function step(e,t){return t<e?0:1}function pulse(e,t,n){return step(e,n)-step(t,n)}function smoothstep(e,t,n){n=clamp(0,1,(n-e)/(t-e));return n*n*n*((6*n-15)*n+10)}function bump(e,t,n){n=clamp(0,1,(n-e)/(t-e));return(Math.cos(Math.PI*n)+1)/2}function getWindowNesting(e){var t={iframe:e!==e.top,friendlyIframe:!1,iabFriendlyIframe:!1,hostileIframe:!1,iframeDepth:0};if(t.iframe){var n=e;try{for(t.friendlyIframe=!!e.top.location.href,t.iabFriendlyIframe=t.friendlyIframe&&void 0!==e.inDapIF&&e.inDapIF;void 0!==n.parent.location.href&&n.parent.document!==n.document;)n=n.parent,t.iframeDepth++;void 0===e.top.document&&(t.hostileIframe=!0)}catch(e){t.hostileIframe=!0}}return t}function isFetchSupported(){return!!(window.fetch&&window.URL&&URL.createObjectURL)}function isBeaconSupported(){return!(!window.navigator||!window.navigator.sendBeacon)}function isNativeImplementation(e){return"function"==typeof e&&-1<e.toString().indexOf("[native code]")}function getViewportGeometry(e,t){var n=deviceInfo.deviceType.desktop()&&"BackCompat"!=e.document.compatMode,r=deviceInfo.os.ios("9",null),o=e.document.documentElement||{};return{width:"win"!==t&&(n||r||"doc"===t)?o.clientWidth:e.innerWidth,height:"win"!==t&&(n||"doc"===t)?o.clientHeight:e.innerHeight,left:e.scrollX||o.scrollLeft||0,top:e.scrollY||o.scrollTop||0}}function safeQuerySelector(e,t){try{return e.querySelector(t)}catch(e){return null}}function safeQuerySelectorAll(e,t){try{return e.querySelectorAll(t)}catch(e){return[]}}function getElementRectRelativeToTopViewport(e){var t=e?CRect.adopt(e.getBoundingClientRect()):CRect.ZERO,n=e.ownerDocument&&e.ownerDocument.defaultView||null;try{for(;n&&n!==n.parent&&void 0!==n.parent.location.href&&n.parent.document!==n.document&&n.frameElement;){var r=CRect.adopt(n.frameElement.getBoundingClientRect());t.left+=r.left,t.top+=r.top,n=n.parent}}catch(e){}return t}Date.now||(Date.now=function(){return+new Date}),Array.prototype.waitForEach||Object.defineProperty(Array.prototype,"waitForEach",{value:function(o,i,a){var c=this.length;c?this.forEach(function(e,t,n){var r=!1;o.call(a,e,function(){if(r)throw"Called `done` multiple times for element "+t;r=!0,--c||i()},t,n)},a):i()}}),function(i){i.loadJS=function(t,e,n,r){var o=i.loadJS.externals,e={success:e||noop,error:n||noop};if(!/^[A-Za-z0-9]*:\/\/|^\/\//.test(t)){var n=creative.hostedFiles.filter(function(e){return e.filepath===t})[0];if(void 0===n)return console.warn('Hosted file "'+t+'" was not found.'),void e.error();if(!1!==n.loaded)return console.warn('Hosted file "'+t+'" is already loaded.'),void setTimeout(e.success,0);n.loaded=!0,t=creative.cachedApiUrl+"hostedFiles/"+creative.id+"/"+creative.version+"/"+t}t in o&&!r?o[t].loaded?setTimeout(e.success,0):o[t].cbs.push(e):(o[t]={cbs:[e]},(n=document.createElement("script")).type="text/javascript",n.onload=function(){o[t].loaded=!0,o[t].cbs.forEach(function(e){e.success()}),o[t].cbs=[]},n.onerror=function(){o[t].cbs.forEach(function(e){e.error()}),o[t].cbs=[],delete o[t]},n.src=t,i.loadJS.appendToRoot(n))},i.loadJS.externals={},i.loadJS.appendToRoot=function(e){return document.querySelector("head").appendChild(e)},i.loadJSONP=function(e,t,n,r){"function"==typeof t&&(r=n,n=t,t={});var o=t.cbName||"__jsonp"+randInt();e+=(-1==e.indexOf("?")?"?":"&")+(t.paramName||"jsonp")+"="+o,i[o]=function(e){n(e),delete i[o]},loadJS(e,noop,r,!0)}}(window),void 0===Function.prototype.name&&function(){var t=/^function\s+(\w+?)\s*?\(/;Object.defineProperty(Function.prototype,"name",{get:function(){var e=this.constructor.prototype.toString.call(this);return t.test(e)?e.match(t)[1]:""}})}(),fetchShortenedUrl.cache={},fetchShortenedUrl.inProgress={};;
function Logger(e){if(!(this instanceof Logger))return new Logger(e);e=e||"";var n=noop;(0<=Logger.enabledNames.indexOf(e)||0<=Logger.enabledNames.indexOf("all"))&&(n=function(){var e=[].slice.apply(arguments);e.unshift(Logger._ts()+" ["+Logger.sessionId+(this.name?" "+this.name:"")+"]"),console.log.apply(console,e)}),this.name=e,this.log=n,this.warn=n,this.debug=n,this.error=n}Logger._ts=function(){var e=new Date;function n(e,n){for(e+="";e.length<n;)e="0"+e;return e}return e.getFullYear()+"-"+n(e.getMonth()+1,2)+"-"+n(e.getDate(),2)+" "+n(e.getHours(),2)+":"+n(e.getMinutes(),2)+":"+n(e.getSeconds(),2)+"."+n(e.getTime()-1e3*Math.floor(e.getTime()/1e3),3)},Logger.init=function(e,n){Logger.enabledNames=e,Logger.sessionId=n||(Math.random()+"").slice(15)},Logger.initFromRuntimeParams=function(e){Logger.init(e.debug?e.debug.split(","):[],e.sessionId)};;
var EventEmitter={emit:function(e){var t,n=this._listeners;n&&n[e]&&(t=[].slice.call(arguments,1),n[e].forEach(function(e){if("undefined"!=typeof window)e.apply(window,t);else{if("undefined"==typeof self)throw"EventEmitter not supported in current scope.";e.apply(self,t)}}))},emits:function(e){var t=arguments;return function(){this.emit.apply(this,t)}.bind(this)},addListener:function(e,t){var n=this._listeners;(n=n||(this._listeners={}))[e]||(n[e]=[]),n[e].push(t)},removeListener:function(e,t){var n=this._listeners;n&&n[e]&&(n[e]=n[e].filter(function(e){return e!==t}))},once:function(t,n){var i=this;i.on(t,function e(){i.off(t,e),n.apply(this,arguments)})},onAll:function(e,t){e.split(" ").waitForEach(function(e,t){this.once(e,t)},t,this)}};EventEmitter.on=EventEmitter.addListener,EventEmitter.off=EventEmitter.removeListener,EventEmitter.addEventListener=EventEmitter.addListener,EventEmitter.removeEventListener=EventEmitter.removeListener,"undefined"!=typeof module&&null!==module&&(module.exports=EventEmitter);;
!function(t){function e(){Logger.initFromRuntimeParams(runtimeParams),this.trace("VPAIDAPI constructor"),this._eventHandler=extend({clearListeners:function(t){var e=this._listeners;e&&e[t]&&(e[t]=[])}},EventEmitter),this._proxyObject=null,this.adLinear=1,this.adExpanded=0,this.adSkippableState=0,this.adCompanions="",this.adIcons=0,this.adWidth=0,this.adHeight=0,this.viewMode="normal",this.adVolume=1,Object.defineProperties(this,{adDuration:{get:function(){return this.getAdDuration()}},adRemainingTime:{get:function(){return this.getAdRemainingTime()}}}),this._startQueued=!1}var i=e.prototype;i.constructor=e,i.trace=function(){try{Logger("vpaid").log("CELTRA: "+JSON.stringify(arguments))}catch(t){}},i.setup=function(t){Logger("vpaid").log("CELTRA: setup()"),this._proxyObject=t;var e,i=["AdLoaded","AdStarted","AdStopped","AdSkipped","AdSkippableStateChange","AdSizeChange","AdLinearChange","AdDurationChange","AdExpandedChange","AdRemainingTimeChange","AdVolumeChange","AdImpression","AdVideoStart","AdVideoFirstQuartile","AdVideoMidpoint","AdVideoThirdQuartile","AdVideoComplete","AdClickThru","AdInteraction","AdUserAcceptInvitation","AdUserMinimize","AdUserClose","AdPaused","AdPlaying","AdLog","AdError"];for(e in i)!function(e){this._proxyObject.on(e,function(){var t={eventName:e,args:Array.prototype.slice.call(arguments)};this.handleEvent(t)}.bind(this))}.bind(this)(i[e]);this._startQueued&&defer(function(){this._proxyObject.startAd()}.bind(this))},i.handleEvent=function(t){this.trace(t);var e=t.args||[];e.unshift(t.eventName),"AdVolumeChange"===t.eventName&&this._handleAdVolumeChange(e[1]),this._eventHandler.emit.apply(this._eventHandler,e)},i._handleAdVolumeChange=function(t){this.adVolume=t,this.environmentVars.videoSlot.volume=t},i.getAdIcons=function(){return!1},i.getAdHeight=function(){return this.adHeight},i.getAdWidth=function(){return this.adWidth},i.getAdLinear=function(){return this.trace("getAdLinear()"),!0},i.getAdRemainingTime=function(){return this.trace("getAdRemainingTime()"),this._proxyObject?this._proxyObject.getAdRemainingTime():-2},i.getAdDuration=function(){return this.trace("getAdDuration()"),this._proxyObject?this._proxyObject.getAdDuration():-2},i.setAdVolume=function(t){this.trace("setAdVolume() value =",t),this.handleEvent({eventName:"AdVolumeChange",args:[t]})},i.getAdVolume=function(){return this.trace("getAdVolume()"),this.adVolume},i.handshakeVersion=function(t){return this.trace("handshakeVersion()",t),"2.0"},i.initAd=function(t,e,i,n,r,d){this.trace("initAd()",t,e,i,n,r,d),this.environmentVars=d,this.adWidth=t,this.adHeight=e,this.viewMode=i,this.environmentVars.videoSlotCanAutoPlay&&(void 0!==(s=(a=this.environmentVars.videoSlot).play())&&s.then(function(){}).catch(function(t){}),a.pause());var a,s=[].slice.apply(arguments);s.unshift(this),insertPayloadScript.apply(null,s),"1"===runtimeParams._fireAdLoadedOnInit&&defer(function(){this.handleEvent({eventName:"AdLoaded",args:[]})}.bind(this))},i.startAd=function(){this.trace("startAd()"),this._proxyObject?this._proxyObject.startAd():this._startQueued=!0},i.stopAd=function(){this.trace("stopAd()"),this._proxyObject.stopAd()},i.skipAd=function(){this.trace("skipAd()"),this._proxyObject.skipAd()},i.resizeAd=function(t,e,i){this.trace("resizeAd()",t,e,i),this.adWidth=t,this.adHeight=e,this.viewMode=i,this._proxyObject.resizeAd(t,e,i)},i.pauseAd=function(){this.trace("pauseAd()"),this._proxyObject.pauseAd()},i.resumeAd=function(){this.trace("resumeAd()"),this._proxyObject.resumeAd()},i.subscribe=function(t,e,i){this._eventHandler.on(e,t.bind(i))},i.unsubscribe=function(t){this._eventHandler.clearListeners(t)},i.handleError=function(t){this.handleEvent({eventName:"AdError",args:[t]})},i.getAdExpanded=function(){return 0},i.getAdSkippableState=function(){return 0},i.getAdCompanions=function(){return null},i.expandAd=function(){return 0},i.collapseAd=function(){return 0},t.getVPAIDAd=function(){return new e}}(window);;




})();

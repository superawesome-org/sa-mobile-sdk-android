(function() {
    // payload delivery

    if (!window.celtra)
        window.celtra = {};
    if (!window.celtra.payloads)
        window.celtra.payloads = {};

    window.celtra.payloads["https://cache-ssl.celtra.com/api/creatives/041f1fd0/compiled/vpaid.js?v=30-a289ee2c04&secure=1&cachedVariantChoices=W10-&isPurposePreview=0&eventMetadataExperiment=newMeta&inmobi=0"] = function(script, runtimeParams, trackers, urlOpenedOverrideUrls, storeOpenedOverrideUrls, macros, urlOpenedUrlAppendage, clickThroughDestinationUrl) {
        function inherit(e, t) {
            function n() {}
            n.prototype = t.prototype, e.prototype = new n, Object.defineProperties(e, {
                uber: {
                    get: function() {
                        return t.prototype
                    },
                    enumerable: !1,
                    configurable: !1
                },
                uberConstructor: {
                    get: function() {
                        return t
                    },
                    enumerable: !1,
                    configurable: !1
                }
            }), e.prototype.constructor = e
        }

        function extend(e, t) {
            for (var n = 1; n < arguments.length; n++) {
                t = arguments[n];
                if (t instanceof Object)
                    for (var r in t) e[r] = t[r]
            }
            return e
        }

        function deep(e, t) {
            for (var n in t) e[n] instanceof Object && t[n] instanceof Object ? deep(e[n], t[n]) : e[n] = t[n]
        }

        function delayed(e, t) {
            var n = null;
            return function() {
                n && clearTimeout(n), n = setTimeout(e, t)
            }
        }

        function throttled(e, t) {
            var n = null;
            return function() {
                n = n || setTimeout(function() {
                    n = null, e()
                }, t)
            }
        }

        function deferred(e, t, n, r) {
            return function() {
                defer(e, t, n, r)
            }
        }

        function useAsap() {
            return "undefined" != typeof creative && creative.adapter && creative.adapter.useAsap || "undefined" != typeof adapter && adapter.useAsap
        }

        function hasClass(e, t) {
            return e.classList.contains(t)
        }

        function addClass(e, t) {
            e.classList.add(t)
        }

        function removeClass(e, t) {
            e.classList.remove(t)
        }

        function toggleClass(e, t) {
            e.classList.toggle(t)
        }

        function cssurl(e) {
            return "url('" + e.replace(/'/g, "\\'") + "')"
        }

        function camelize(e) {
            return e.replace(/-([a-z])/g, function(e, t) {
                return t.toUpperCase()
            })
        }

        function ucfirst(e) {
            return e.charAt(0).toUpperCase() + e.slice(1)
        }

        function zeroPad(e, t) {
            null == t && (t = 2);
            t = Math.max(0, t - ("" + e).length);
            return ("" + Math.pow(10, t)).slice(1) + e
        }

        function htmlentitize(e) {
            return e.replace(/&/g, "&amp;").replace(/"/g, "&quot;").replace(/'/g, "&#39;").replace(/</g, "&lt;").replace(/>/g, "&gt;")
        }

        function removeHtmlTags(e) {
            for (var t, n = "(?:[^\"'>]|\"[^\"]*\"|'[^']*')*", r = new RegExp("<(?:!--(?:(?:-*[^->])*--+|-?)|script\\b" + n + ">[\\s\\S]*?</script\\s*|style\\b" + n + ">[\\s\\S]*?</style\\s*|/?[a-z]" + n + ")>", "gi");
                (e = (t = e).replace(r, "")) !== t;);
            return e.replace(/</g, "&lt;")
        }

        function trim(e) {
            return e.replace(/^\s+|\s+$/g, "")
        }

        function to_utf8(e) {
            return unescape(encodeURIComponent(e))
        }

        function isRTLChar(e) {
            return !!(e = String(e).charCodeAt(0)) && (1424 <= e && e <= 1535 || 1536 <= e && e <= 1791 || 1792 <= e && e <= 1871 || 1872 <= e && e <= 1919 || 2144 <= e && e <= 2159 || 2208 <= e && e <= 2303 || 64288 <= e && e <= 64335 || 64336 <= e && e <= 65023 || 65136 <= e && e <= 65279 || 8207 === e || 8235 === e || 8238 === e)
        }

        function isLTRChar(e) {
            return !!(e = String(e).charCodeAt(0)) && (65 <= e && e <= 90 || 97 <= e && e <= 122 || 192 <= e && e <= 214 || 216 <= e && e <= 246 || 248 <= e && e <= 696 || 768 <= e && e <= 1424 || 2048 <= e && e <= 8191 || 11264 <= e && e <= 64284 || 65022 <= e && e <= 65135 || 65277 <= e && e <= 65535 || 8206 === e || 8234 === e || 8237 === e)
        }

        function guessWritingDirection(e) {
            for (var t = Math.min(25, e.length), n = 0; n < t; n++) {
                var r = e.charAt(n);
                if (isLTRChar(r)) return "ltr";
                if (isRTLChar(r)) return "rtl"
            }
            return null
        }

        function randInt() {
            return (Math.random() + "").slice(2)
        }

        function _isListenerOptionSupported(e) {
            var t = _isListenerOptionSupported.opts;
            if (void 0 === t[e]) {
                t[e] = !1;
                try {
                    window.addEventListener("listener-test", null, Object.defineProperty({}, e, {
                        get: function() {
                            t[e] = !0
                        }
                    }))
                } catch (e) {}
            }
            return t[e]
        }

        function _buildNativeListenerOptions(e) {
            var t;
            return "object" != typeof e ? !!e : _isListenerOptionSupported("capture") ? (t = {
                capture: !!e.capture
            }, _isListenerOptionSupported("passive") && (t.passive = !!e.passive), t) : !!e.capture
        }

        function _analyzeTouch(e) {
            var t, n = Math.abs(e.firstTouch.x - e.lastTouch.x),
                r = Math.abs(e.firstTouch.y - e.lastTouch.y);
            return "y" === e.validAxis ? t = r > e.minDistanceForSwipe : "x" === e.validAxis && (t = n > e.minDistanceForSwipe), {
                isHorizontal: r < n,
                isConsideredSwipe: t
            }
        }

        function attach(e, t, n, r) {
            t = getEventNames()[t.toLowerCase()] || t;
            e.addEventListener(t, n, _buildNativeListenerOptions(r))
        }

        function detach(e, t, n, r) {
            t = getEventNames()[t.toLowerCase()] || t;
            e.removeEventListener(t, n, _buildNativeListenerOptions(r))
        }

        function once(t, n, r, o) {
            attach(t, n, function e() {
                detach(t, n, e, o), r.apply(this, arguments)
            }, o)
        }

        function trigger(e, t, n, r) {
            var o = document.createEvent("HTMLEvents"),
                n = (o.initEvent(t, n, r), "on" + ucfirst(t));
            return "function" == typeof e[n] && e[n](), e.dispatchEvent(o)
        }

        function fakeclick(t, e) {
            var n = (e = e || window).document.createElement("a"),
                e = (n.cssText = "visibility: hidden", n.addEventListener("click", function(e) {
                    t(), e.preventDefault(), e.stopPropagation(), n.parentNode.removeChild(n)
                }, !0), e.document.body.appendChild(n), document.createEvent("MouseEvents"));
            e.initEvent("click", !0, !0), n.dispatchEvent(e)
        }

        function fakeClickAhrefBlank(e, t, n) {
            t = t || noop;
            var r = (n = n || window).document.createElement("a"),
                e = (r.style.cssText = "visibility: hidden", r.setAttribute("href", e), r.setAttribute("target", "_blank"), r.addEventListener("click", function(e) {
                    e.stopPropagation(), t(), r.parentNode.removeChild(r)
                }, !0), n.document.body.appendChild(r), n.document.createEvent("MouseEvents"));
            e.initEvent("click", !0, !0), r.dispatchEvent(e)
        }! function(a) {
            if (!this.defer) {
                try {
                    for (; void 0 !== a.parent.location.href && a.parent.document !== a.document;) a = a.parent
                } catch (e) {}
                e = "function" == typeof(u = a).setImmediate;
                var t, n, r, o, i, c, u, e, s = u.MutationObserver ? function(e) {
                    t = document.createElement("div"), new MutationObserver(function() {
                        e(), t = null
                    }).observe(t, {
                        attributes: !0
                    }), t.setAttribute("i", "1")
                } : !e && u.postMessage && !u.importScripts && u.addEventListener ? (o = "com.setImmediate" + Math.random(), i = 0, c = {}, u.addEventListener("message", function(e) {
                    e.source === u && 0 === e.data.indexOf(o) && (e = e.data.split(":")[1], c[e](), delete c[e])
                }, !1), function(e) {
                    var t = 9007199254740991 === i ? 0 : ++i;
                    c[t] = e, u.postMessage(o + ":" + t, "*")
                }) : !e && u.document && "onreadystatechange" in document.createElement("script") ? function(e) {
                    (n = document.createElement("script")).onreadystatechange = function() {
                        n.onreadystatechange = null, n.parentNode.removeChild(n), n = null, e()
                    }, document.body.appendChild(n)
                } : (r = e && setImmediate || setTimeout, function(e) {
                    r(e)
                });
                this.defer = function(e, t, n, r) {
                    var o, i, t = 0 | t;
                    n && (void 0 !== defer.perf ? i = defer.perf : "undefined" != typeof creative ? i = creative.perf : "undefined" != typeof perf && (i = perf), i && !i._stopTrackingDefers && (o = i.start("defer", {
                        deferId: n,
                        delay: t
                    }))), e && (i = function() {
                        o && o.end(), e()
                    }, r ? s(i) : a.setTimeout(i, t))
                }
            }
        }(window), Function.prototype.bind || Object.defineProperty(Function.prototype, "bind", {
                value: function(e) {
                    var t = this;
                    return function() {
                        return t.apply(e, arguments)
                    }
                }
            }),
            function(t) {
                t.getEventNames = function() {
                    var e = {};
                    return "WebKitAnimationEvent" in t && (e.animationstart = "webkitAnimationStart", e.animationiteration = "webkitAnimationIteration", e.animationend = "webkitAnimationEnd"), "WebKitTransitionEvent" in t && (e.transitionend = "webkitTransitionEnd"), e
                }
            }(window), _isListenerOptionSupported.opts = {};
        var getTimestamp = void 0 === window.performance || void 0 === window.performance.now ? Date.now : window.performance.now.bind(window.performance);

        function noop() {}

        function nullai(e, t) {
            t && t()
        }

        function retTrue() {
            return !0
        }

        function retFalse() {
            return !1
        }

        function offset(e) {
            var t = e.getBoundingClientRect(),
                e = e.ownerDocument,
                n = e.documentElement,
                e = e.defaultView;
            return {
                top: t.top + (e.pageYOffset || n.scrollTop) - (n.clientTop || 0),
                left: t.left + (e.pageXOffset || n.scrollLeft) - (n.clientLeft || 0)
            }
        }

        function addCssRule(e, t, n) {
            var r = document.createElement("style");
            r.textContent = e + " {" + t + "}", (n ? n.document : document).getElementsByTagName("head")[0].appendChild(r)
        }

        function redrawAndroidIframe() {
            var e = document.createElement("style");
            document.body.appendChild(e), document.body.removeChild(e)
        }

        function parseQuery(e) {
            var t = {};
            return (e = e.replace(/\&$/, "").replace(/\+/g, "%20")).split("&").forEach(function(e) {
                e = e.split("=").map(decodeURIComponent);
                t[e[0]] = e[1]
            }), t
        }

        function buildQuery(e) {
            var t, n = [];
            for (t in e) n.push(encodeURIComponent(t) + "=" + encodeURIComponent(e[t]));
            return n.join("&")
        }

        function postBlob(e, t) {
            var n = new XMLHttpRequest;
            n.open("POST", creative.apiUrl + "blobs?base64=1"), n.setRequestHeader("Content-Type", "application/octet-stream"), n.onreadystatechange = function() {
                4 === n.readyState && t(n.responseText, n.status)
            }, n.send(e)
        }

        function tmpl(e, t) {
            if (!e) return "";
            var n;
            if (-1 == e.indexOf("<%")) n = function() {
                return e
            };
            else {
                var r = e.split(/<%\s*|\s*%>/g),
                    o = "var p = []; with(o) {\n",
                    i = !1;
                r.forEach(function(e) {
                    i ? "=" == e[0] ? o += "  p.push(" + e.replace(/^=\s*|\s*$/g, "") + ");\n" : o += "  " + e + "\n" : e && (o += "  p.push('" + e.replace(/'/g, "\\'").split(/\r?\n/g).join("\\n');\n  p.push('") + "');\n"), i = !i
                }), o += '} return p.join("");';
                try {
                    n = new Function("o", o)
                } catch (e) {
                    r = new Error("Cannot parse template! (see `template` property)");
                    throw r.template = o, r
                }
            }
            return t ? n(t) : n
        }

        function flash() {
            var e = document.createElement("div");
            e.style.background = "white", e.style.opacity = .005, e.style.position = "absolute", e.style.top = 0, e.style.left = 0, e.style.width = "100%", e.style.height = "100%", e.style.zIndex = 2147483647, document.body.appendChild(e), setTimeout(function() {
                e.parentNode.removeChild(e)
            }, 0)
        }

        function crc32(e) {
            "use strict";
            for (var t = -1, n = 0, r = [0, -227835133, -516198153, 324072436, -946170081, 904991772, 648144872, -724933397, -1965467441, 2024987596, 1809983544, -1719030981, 1296289744, -1087877933, -1401372889, 1578318884, 274646895, -499825556, -244992104, 51262619, -675000208, 632279923, 922689671, -996891772, -1702387808, 1760304291, 2075979607, -1982370732, 1562183871, -1351185476, -1138329528, 1313733451, 549293790, -757723683, -1048117719, 871202090, -416867903, 357341890, 102525238, -193467851, -1436232175, 1477399826, 1264559846, -1187764763, 1845379342, -1617575411, -1933233671, 2125378298, 820201905, -1031222606, -774358714, 598981189, -143008082, 85089709, 373468761, -467063462, -1170599554, 1213305469, 1526817161, -1452612982, 2107672161, -1882520222, -1667500394, 1861252501, 1098587580, -1290756417, -1606390453, 1378610760, -2032039261, 1955203488, 1742404180, -1783531177, -878557837, 969524848, 714683780, -655182201, 205050476, -28094097, -318528869, 526918040, 1361435347, -1555146288, -1340167644, 1114974503, -1765847604, 1691668175, 2005155131, -2047885768, -604208612, 697762079, 986182379, -928222744, 476452099, -301099520, -44210700, 255256311, 1640403810, -1817374623, -2130844779, 1922457750, -1503918979, 1412925310, 1197962378, -1257441399, -350237779, 427051182, 170179418, -129025959, 746937522, -554770511, -843174843, 1070968646, 1905808397, -2081171698, -1868356358, 1657317369, -1241332974, 1147748369, 1463399397, -1521340186, -79622974, 153784257, 444234805, -401473738, 1021025245, -827320098, -572462294, 797665321, -2097792136, 1889384571, 1674398607, -1851340660, 1164749927, -1224265884, -1537745776, 1446797203, 137323447, -96149324, -384560320, 461344835, -810158936, 1037989803, 781091935, -588970148, -1834419177, 1623424788, 1939049696, -2114449437, 1429367560, -1487280117, -1274471425, 1180866812, 410100952, -367384613, -112536529, 186734380, -538233913, 763408580, 1053836080, -860110797, -1572096602, 1344288421, 1131464017, -1323612590, 1708204729, -1749376582, -2065018290, 1988219213, 680717673, -621187478, -911630946, 1002577565, -284657034, 493091189, 238226049, -61306494, -1307217207, 1082061258, 1395524158, -1589280451, 1972364758, -2015074603, -1800104671, 1725896226, 952904198, -894981883, -638100751, 731699698, -11092711, 222117402, 510512622, -335130899, -1014159676, 837199303, 582374963, -790768336, 68661723, -159632680, -450051796, 390545967, 1230274059, -1153434360, -1469116676, 1510247935, -1899042540, 2091215383, 1878366691, -1650582816, -741088853, 565732008, 854102364, -1065151905, 340358836, -433916489, -177076669, 119113024, 1493875044, -1419691417, -1204696685, 1247431312, -1634718085, 1828433272, 2141937292, -1916740209, -483350502, 291187481, 34330861, -262120466, 615137029, -691946490, -980332558, 939183345, 1776939221, -1685949482, -1999470558, 2058945313, -1368168502, 1545135305, 1330124605, -1121741762, -210866315, 17165430, 307568514, -532767615, 888469610, -962626711, -707819363, 665062302, 2042050490, -1948470087, -1735637171, 1793573966, -1104306011, 1279665062, 1595330642, -1384295599], n = 0; n < e.length; n++) t = t >>> 8 ^ r[255 & (t ^ e.charCodeAt(n))];
            return (-1 ^ t) >>> 0
        }

        function isArray(e) {
            return "[object Array]" === Object.prototype.toString.call(e)
        }

        function isDefAndNotNull(e) {
            return null != e
        }

        function updateQueryStringParameter(e, t, n) {
            t = encodeURIComponent(t), n = encodeURIComponent(n);
            var r = new RegExp("([?|&])" + t + "=.*?(&|$)", "i");
            return separator = -1 !== e.indexOf("?") ? "&" : "?", e.match(r) ? e.replace(r, "$1" + t + "=" + n + "$2") : e + separator + t + "=" + n
        }

        function fetchShortenedUrl(r, e, t) {
            t = t || noop;
            var n, o = !1,
                i = fetchShortenedUrl.inProgress,
                a = fetchShortenedUrl.cache;

            function c(t, n) {
                i[r].forEach(function(e) {
                    e[t](n)
                }), delete i[r]
            }
            "http" == !r.slice(0, 4) ? t() : a[r] ? defer(function() {
                e(a[r])
            }, 0, "fetchShortenedUrl defer success callback") : (i[r] || (i[r] = [], o = !0), i[r].push({
                success: e,
                error: t
            }), o && (t = (creative.runtimeParams.secure ? creative.cachedApiUrl : creative.insecureCachedApiUrl) + "shortenedUrls/", o = {
                url: r,
                fields: "shortUrlKey"
            }, n = {
                cbName: "shortener_" + btoa(to_utf8(r)).replace(/\//g, "$").replace(/\+/g, "_").replace(/=/g, "")
            }, loadJSONP(t + "?" + buildQuery(o), n, function(e) {
                e = creative.shareUrl + e.shortUrlKey;
                c("success", a[r] = e)
            }, function() {
                requestCreateShortenedUrl(r, c)
            })))
        }

        function requestCreateShortenedUrl(t, n) {
            var e = (creative.runtimeParams.secure ? creative.apiUrl : creative.insecureApiUrl) + "shortenedUrls/",
                r = fetchShortenedUrl.cache,
                o = new XMLHttpRequest;
            o.open("POST", e), o.setRequestHeader("Content-Type", "application/json; charset=utf-8"), o.onreadystatechange = function() {
                var e;
                4 === o.readyState && (201 === o.status ? (e = JSON.parse(o.responseText), e = creative.shareUrl + e.shortUrlKey, r[t] = e, n("success", e)) : n("error"))
            }, o.send(JSON.stringify({
                url: t
            }))
        }

        function isMediaPlaying(e) {
            try {
                return 0 < e.currentTime && !e.paused && !e.ended
            } catch (e) {
                return !1
            }
        }

        function merge() {
            for (var e, t = {}, n = 0; n < arguments.length; n += 1)
                if (null != (e = arguments[n]))
                    for (var r in e) t[r] = e[r];
            return t
        }

        function clamp(e, t, n) {
            return Math.max(e, Math.min(t, n))
        }

        function lerp(e, t, n) {
            return e + n * (t - e)
        }

        function map(e, t, n, r, o) {
            return (o - e) / (t - e) * (r - n) + n
        }

        function step(e, t) {
            return t < e ? 0 : 1
        }

        function pulse(e, t, n) {
            return step(e, n) - step(t, n)
        }

        function smoothstep(e, t, n) {
            n = clamp(0, 1, (n - e) / (t - e));
            return n * n * n * ((6 * n - 15) * n + 10)
        }

        function bump(e, t, n) {
            n = clamp(0, 1, (n - e) / (t - e));
            return (Math.cos(Math.PI * n) + 1) / 2
        }

        function getWindowNesting(e) {
            var t = {
                iframe: e !== e.top,
                friendlyIframe: !1,
                iabFriendlyIframe: !1,
                hostileIframe: !1,
                iframeDepth: 0
            };
            if (t.iframe) {
                var n = e;
                try {
                    for (t.friendlyIframe = !!e.top.location.href, t.iabFriendlyIframe = t.friendlyIframe && void 0 !== e.inDapIF && e.inDapIF; void 0 !== n.parent.location.href && n.parent.document !== n.document;) n = n.parent, t.iframeDepth++;
                    void 0 === e.top.document && (t.hostileIframe = !0)
                } catch (e) {
                    t.hostileIframe = !0
                }
            }
            return t
        }

        function isFetchSupported() {
            return !!(window.fetch && window.URL && URL.createObjectURL)
        }

        function isBeaconSupported() {
            return !(!window.navigator || !window.navigator.sendBeacon)
        }

        function isNativeImplementation(e) {
            return "function" == typeof e && -1 < e.toString().indexOf("[native code]")
        }

        function getViewportGeometry(e, t) {
            var n = deviceInfo.deviceType.desktop() && "BackCompat" != e.document.compatMode,
                r = deviceInfo.os.ios("9", null),
                o = e.document.documentElement || {};
            return {
                width: "win" !== t && (n || r || "doc" === t) ? o.clientWidth : e.innerWidth,
                height: "win" !== t && (n || "doc" === t) ? o.clientHeight : e.innerHeight,
                left: e.scrollX || o.scrollLeft || 0,
                top: e.scrollY || o.scrollTop || 0
            }
        }

        function safeQuerySelector(e, t) {
            try {
                return e.querySelector(t)
            } catch (e) {
                return null
            }
        }

        function safeQuerySelectorAll(e, t) {
            try {
                return e.querySelectorAll(t)
            } catch (e) {
                return []
            }
        }

        function getElementRectRelativeToTopViewport(e) {
            var t = e ? CRect.adopt(e.getBoundingClientRect()) : CRect.ZERO,
                n = e.ownerDocument && e.ownerDocument.defaultView || null;
            try {
                for (; n && n !== n.parent && void 0 !== n.parent.location.href && n.parent.document !== n.document && n.frameElement;) {
                    var r = CRect.adopt(n.frameElement.getBoundingClientRect());
                    t.left += r.left, t.top += r.top, n = n.parent
                }
            } catch (e) {}
            return t
        }
        Date.now || (Date.now = function() {
                return +new Date
            }), Array.prototype.waitForEach || Object.defineProperty(Array.prototype, "waitForEach", {
                value: function(o, i, a) {
                    var c = this.length;
                    c ? this.forEach(function(e, t, n) {
                        var r = !1;
                        o.call(a, e, function() {
                            if (r) throw "Called `done` multiple times for element " + t;
                            r = !0, --c || i()
                        }, t, n)
                    }, a) : i()
                }
            }),
            function(i) {
                i.loadJS = function(t, e, n, r) {
                    var o = i.loadJS.externals,
                        e = {
                            success: e || noop,
                            error: n || noop
                        };
                    if (!/^[A-Za-z0-9]*:\/\/|^\/\//.test(t)) {
                        var n = creative.hostedFiles.filter(function(e) {
                            return e.filepath === t
                        })[0];
                        if (void 0 === n) return console.warn('Hosted file "' + t + '" was not found.'), void e.error();
                        if (!1 !== n.loaded) return console.warn('Hosted file "' + t + '" is already loaded.'), void setTimeout(e.success, 0);
                        n.loaded = !0, t = creative.cachedApiUrl + "hostedFiles/" + creative.id + "/" + creative.version + "/" + t
                    }
                    t in o && !r ? o[t].loaded ? setTimeout(e.success, 0) : o[t].cbs.push(e) : (o[t] = {
                        cbs: [e]
                    }, (n = document.createElement("script")).type = "text/javascript", n.onload = function() {
                        o[t].loaded = !0, o[t].cbs.forEach(function(e) {
                            e.success()
                        }), o[t].cbs = []
                    }, n.onerror = function() {
                        o[t].cbs.forEach(function(e) {
                            e.error()
                        }), o[t].cbs = [], delete o[t]
                    }, n.src = t, i.loadJS.appendToRoot(n))
                }, i.loadJS.externals = {}, i.loadJS.appendToRoot = function(e) {
                    return document.querySelector("head").appendChild(e)
                }, i.loadJSONP = function(e, t, n, r) {
                    "function" == typeof t && (r = n, n = t, t = {});
                    var o = t.cbName || "__jsonp" + randInt();
                    e += (-1 == e.indexOf("?") ? "?" : "&") + (t.paramName || "jsonp") + "=" + o, i[o] = function(e) {
                        n(e), delete i[o]
                    }, loadJS(e, noop, r, !0)
                }
            }(window), void 0 === Function.prototype.name && function() {
                var t = /^function\s+(\w+?)\s*?\(/;
                Object.defineProperty(Function.prototype, "name", {
                    get: function() {
                        var e = this.constructor.prototype.toString.call(this);
                        return t.test(e) ? e.match(t)[1] : ""
                    }
                })
            }(), fetchShortenedUrl.cache = {}, fetchShortenedUrl.inProgress = {};;

        function CTickerQueue() {
            this._queue = [], this._i = this.length
        }
        Object.defineProperty(CTickerQueue.prototype, "length", {
            get: function() {
                return this._queue.length
            }
        }), CTickerQueue.filterNulls = function(e) {
            return null !== e
        }, CTickerQueue.prototype._removeNulls = function() {
            this._queue = this._queue.filter(CTickerQueue.filterNulls)
        }, CTickerQueue.prototype.process = function(e) {
            if (this.length) {
                for (this._i = 0; this._i < this.length; this._i++) this._queue[this._i] && this._queue[this._i](e);
                this._removeNulls()
            }
        }, CTickerQueue.prototype.add = function(e) {
            this._queue.indexOf(e) < 0 && this._queue.push(e)
        }, CTickerQueue.prototype.remove = function(e) {
            e = this._queue.indexOf(e);
            ~e && (this._queue[e] = null)
        }, CTickerQueue.prototype.empty = function() {
            this.length && (this._queue.length = 0)
        };;

        function CTicker(e, t) {
            this._win = t || window, e.environment && e.isNative && !e.isRequestAnimationFrameBroken && !this._isDeviceUserAgentBlacklisted ? this.raf = e.environment : this.raf = e.polyfill, this._queues = {
                update: new CTickerQueue,
                nextUpdate: new CTickerQueue,
                render: new CTickerQueue,
                nextRender: new CTickerQueue,
                next: new CTickerQueue
            }, this._tick = this._tick.bind(this), this.raf.nextFrame(this._tick)
        }
        Object.defineProperty(CTicker.prototype, "_isDeviceUserAgentBlacklisted", {
            get: function() {
                var t = this._win.navigator.userAgent;
                return ["RAZR HD", "SOL23", "LG-D855", "LG-D851", "LG-D850", "LG-D852", "LGLS990", "LGUS990", "VS985 4G"].some(function(e) {
                    return -1 < t.indexOf(e)
                })
            }
        }), CTicker.prototype.stop = function() {
            try {
                this._queues.update.empty(), this._queues.render.empty()
            } finally {
                this.raf.cancelFrame(this._tick)
            }
        }, CTicker.prototype._tick = function(e) {
            try {
                this._queues.update.process(e), this._queues.nextUpdate.process(e), this._queues.render.process(e), this._queues.nextRender.process(e), this._queues.next.process(e), this._queues.next.empty(), this._queues.nextRender.empty(), this._queues.nextUpdate.empty()
            } finally {
                this.raf.nextFrame(this._tick)
            }
        }, CTicker.prototype.frame = function(e, t) {
            this._queues[t = t || "render"].add(e)
        }, CTicker.prototype.removeFrame = function(e, t) {
            this._queues[t = t || "render"].remove(e)
        };;

        function RequestAnimationFrameWrapper(e) {
            this._lastTime = 0, this._win = e, this._createRAFWrapper = this._createRAFWrapper.bind(this), this._createCAFWrapper = this._createCAFWrapper.bind(this);
            var e = this._extractFromWindow(this._win),
                t = this._createPolyfill();
            this.environment = this._wrapRafAndCaf(e), this.polyfill = this._wrapRafAndCaf(t), this.isNative = !!e && this._isNative(e.nextFrame)
        }
        Object.defineProperty(RequestAnimationFrameWrapper.prototype, "isRequestAnimationFrameBroken", {
            get: function() {
                var e = getWindowNesting(this._win);
                return !(!deviceInfo.browser.firefox() || !e.hostileIframe)
            }
        }), RequestAnimationFrameWrapper.prototype._extractFromWindow = function(t) {
            var r = t.requestAnimationFrame || null,
                n = t.cancelAnimationFrame || null;
            return ["ms", "moz", "webkit", "o"].forEach(function(e) {
                r = r || t[e + "RequestAnimationFrame"] || null, n = n || t[e + "CancelAnimationFrame"] || t[e + "CancelRequestAnimationFrame"] || null
            }), {
                nextFrame: r,
                cancelFrame: n
            }
        }, RequestAnimationFrameWrapper.prototype._createPolyfill = function() {
            var i = this;
            return {
                nextFrame: function(e) {
                    var t = getTimestamp(),
                        r = Math.max(0, 16 - (t - i._lastTime)),
                        n = i._win.setTimeout(function() {
                            e(t + r)
                        }, r);
                    return i._lastTime = t + r, n
                },
                cancelFrame: function(e) {
                    i._win.clearTimeout(e)
                }
            }
        }, RequestAnimationFrameWrapper.prototype._isNative = function(e) {
            return !!(e && -1 < Function.prototype.toString.call(e).indexOf("[native code]"))
        }, RequestAnimationFrameWrapper.prototype._wrapRafAndCaf = function(e) {
            return e && e.nextFrame && e.cancelFrame ? {
                nextFrame: this._createRAFWrapper(e.nextFrame),
                cancelFrame: this._createCAFWrapper(e.cancelFrame)
            } : null
        }, RequestAnimationFrameWrapper.prototype._createRAFWrapper = function(t) {
            return function(e) {
                return t.call(this._win, e)
            }.bind(this)
        }, RequestAnimationFrameWrapper.prototype._createCAFWrapper = function(t) {
            return function(e) {
                t.call(this._win, e)
            }.bind(this)
        };;

        function CPoint(t, i) {
            this.x = +t || 0, this.y = +i || 0
        }

        function CRect(t, i, e, o) {
            this.left = +t || 0, this.top = +i || 0, this.width = +e || 0, this.height = +o || 0
        }
        CPoint.offset = function(t) {
            t = offset(t);
            return new CPoint(t.left, t.top)
        }, CPoint.convertPointFromNodeToPage = function(t, i) {
            return t && void 0 === t.x ? (t = CPoint.offset(t), new CPoint((i ? i.x : 0) + (t ? t.x : 0), (i ? i.y : 0) + (t ? t.y : 0))) : null
        }, CPoint.convertPointFromPageToNode = function(t, i) {
            return t && void 0 === t.x ? (t = CPoint.offset(t), new CPoint((i ? i.x : 0) - (t ? t.x : 0), (i ? i.y : 0) - (t ? t.y : 0))) : null
        }, CPoint.sample = function(t, i, e) {
            for (var o = Math.floor(Math.sqrt(t.width * i / t.height)), n = Math.floor(Math.sqrt(t.height * i / t.width)), h = t.width / o, r = t.height / n, s = 0; s < n; s++)
                for (var f = 0; f < o; f++) e((f + .5) * h + t.left, (s + .5) * r + t.top)
        }, CPoint.epsilon = function(t, i, e) {
            return Math.abs(t - i) < e
        }, CPoint.prototype.equals = function(t) {
            return CPoint.epsilon(this.x, t.x, .25) && CPoint.epsilon(this.y, t.y, .25)
        }, CRect.ZERO = new CRect, Object.defineProperties(CRect.prototype, {
            right: {
                get: function() {
                    return this.left + this.width
                },
                set: function(t) {
                    var i = this.left;
                    t < this.left && (this.left = t, t = i), this.width = t - this.left
                },
                enumerable: !0
            },
            bottom: {
                get: function() {
                    return this.top + this.height
                },
                set: function(t) {
                    var i = this.top;
                    t < this.top && (this.top = t, t = i), this.height = t - this.top
                },
                enumerable: !0
            },
            tl: {
                get: function() {
                    return new CPoint(this.left, this.top)
                }
            },
            tr: {
                get: function() {
                    return new CPoint(this.right, this.top)
                }
            },
            bl: {
                get: function() {
                    return new CPoint(this.left, this.bottom)
                }
            },
            br: {
                get: function() {
                    return new CPoint(this.right, this.bottom)
                }
            },
            area: {
                get: function() {
                    return this.width * this.height
                }
            }
        }), CRect.adopt = function(t) {
            return new CRect(t.left, t.top, t.width, t.height)
        }, CRect.prototype.zero = function() {
            return 0 === this.area
        }, CRect.prototype.intersect = function(t) {
            var i, e, o;
            return !!t && (i = Math.max(this.left, t.left), e = Math.max(this.top, t.top), o = Math.min(this.right, t.right) - i, t = Math.min(this.bottom, t.bottom) - e, o < 0 || t < 0 ? CRect.ZERO : new CRect(i, e, o, t))
        }, CRect.prototype.copyFrom = function(t) {
            this.left = t.left, this.top = t.top, this.width = t.width, this.height = t.height
        }, CRect.prototype.relativeTo = function(t) {
            return new CRect(this.left - t.left, this.top - t.top, this.width, this.height)
        }, CRect.prototype.contains = function(t) {
            return t instanceof CPoint ? t.x >= this.left && t.x <= this.right && t.y >= this.top && t.y <= this.bottom : t instanceof CRect ? t.left >= this.left && t.right <= this.right && t.top >= this.top && t.bottom <= this.bottom : void 0
        }, CRect.prototype.map = function(t, i) {
            var e = i.width / t.width,
                o = i.height / t.height;
            return new CRect((this.left - t.left) * e + i.left, (this.top - t.top) * o + i.top, this.width * e, this.height * o)
        }, CRect.prototype.equals = function(t) {
            return t && this.left == t.left && this.top == t.top && this.right == t.right && this.bottom == t.bottom
        }, CRect.prototype.toString = function() {
            return "CRect: " + JSON.stringify(this)
        };;
        window.CeltraDeviceInfo = {},
            function(o) {
                function s(e, n) {
                    this.os = new t(e.osName, e.osVersion), this.browser = new r(e.browserName, e.browserVersion, n), this.engine = new i(e.browserRenderingEngine, e.browserRenderingEngineVersion), this.deviceType = new a(e.deviceType, e.mobileDevice), this.vendor = e.vendor || "", this.model = e.model || "", this.userAgent = n
                }

                function t(e, n) {
                    this.name = e || "", this.version = n || "0"
                }

                function r(e, n, t) {
                    this.name = e || "", this.version = n || "0", this.userAgent = t
                }

                function i(e, n) {
                    this.name = e || "", this.version = n || "0"
                }

                function a(e, n) {
                    this.hardwareType = e || "", this.mobile = n || ""
                }

                function h(e, n) {
                    var t, r, i;
                    if (!e) return !0;
                    if (0 === n.length) i = r = null;
                    else if (1 === n.length) r = n[0], i = n[0];
                    else {
                        if (2 !== n.length) throw "Invalid number of arguments";
                        r = n[0], i = n[1]
                    }

                    function o(e) {
                        return parseInt(e.replace(/\D/g, ""), 10)
                    }
                    for (e = e.split(".").map(o), r = r ? r.split(".").map(o) : [], i = i ? i.split(".").map(o) : [], t = Math.max(r.length, i.length) - e.length; 0 <= t; t--) e.push(0);

                    function s(e, n) {
                        for (var t = 0; t < Math.min(e.length, n.length); t++) {
                            if (e[t] < n[t]) return -1;
                            if (e[t] > n[t]) return 1
                        }
                        return 0
                    }
                    return !(-1 === s(e, r) || 1 === s(e, i))
                }
                t.prototype.android = function() {
                    return "Android" === this.name && h(this.version, arguments)
                }, t.prototype.osx = function() {
                    return "OSX" === this.name && h(this.version, arguments)
                }, t.prototype.ios = function() {
                    return "IOS" === this.name && h(this.version, arguments)
                }, t.prototype.windows = function() {
                    return "Windows" === this.name && h(this.version, arguments)
                }, t.prototype.windowsPhone = function() {
                    return "WindowsPhone" === this.name && h(this.version, arguments)
                }, t.prototype.linux = function() {
                    return "Linux" === this.name
                }, r.prototype.safari = function() {
                    return "Safari" === this.name && h(this.version, arguments)
                }, r.prototype.chrome = function() {
                    return 0 === this.name.indexOf("Chrom") && h(this.version, arguments)
                }, r.prototype.ie = function() {
                    return 0 === this.name.indexOf("Internet Explorer") && h(this.version, arguments)
                }, r.prototype.android = function() {
                    return "Android Browser" === this.name && h(this.version, arguments)
                }, r.prototype.samsung = function() {
                    return "Samsung Browser" === this.name && h(this.version, arguments)
                }, r.prototype.opera = function() {
                    return 0 === this.name.indexOf("Opera") && h(this.version, arguments)
                }, r.prototype.silk = function() {
                    return "Amazon Silk" === this.name && h(this.version, arguments)
                }, r.prototype.firefox = function() {
                    return 0 === this.name.indexOf("Firefox") && h(this.version, arguments)
                }, r.prototype.edge = function() {
                    return "Edge" === this.name && h(this.version, arguments)
                }, r.prototype.miui = function() {
                    return "MIUI Browser" === this.name && h(this.version, arguments)
                }, r.prototype.webView = function() {
                    var e, n, t = null;
                    return "iP" === (o.navigator.platform || "").substr(0, 2) ? (e = -1 !== (e = this.userAgent).indexOf("Safari") && -1 !== e.indexOf("Version"), n = !/constructor/i.test(o.HTMLElement), e && !o.navigator.standalone ? t = "iOS Safari or Safari Controller" : (n || o.indexedDB) && o.statusbar.visible ? (o.webkit && o.webkit.messageHandlers || n || o.indexedDB) && (t = "WKWebView") : t = "UIWebView") : /^.*\/\d\.\d \(.*wv\).*/.test(this.userAgent) && (t = "AndroidWebView"), t
                }, r.prototype.facebookApp = function() {
                    var e = this.userAgent;
                    return -1 < e.indexOf("FBAN") || -1 < e.indexOf("FBAV")
                }, i.prototype.webkit = function() {
                    return "WebKit" === this.name && h(this.version, arguments)
                }, i.prototype.gecko = function() {
                    return "Gecko" === this.name && h(this.version, arguments)
                }, i.prototype.trident = function() {
                    return "Trident" === this.name && h(this.version, arguments)
                }, i.prototype.presto = function() {
                    return "Presto" === this.name && h(this.version, arguments)
                }, i.prototype.blink = function() {
                    return "Blink" === this.name && h(this.version, arguments)
                }, a.prototype.phone = function() {
                    return "Phone" === this.hardwareType
                }, a.prototype.tablet = function() {
                    return "Tablet" === this.hardwareType
                }, a.prototype.desktop = function() {
                    return "Desktop" === this.hardwareType
                }, a.prototype.mobileDevice = function() {
                    return !0 === this.mobile
                }, o.CeltraDeviceInfo.create = function(e, n) {
                    var t, r, i;
                    return e = e || o.navigator.userAgent || "", r = n = n || {}, -1 === (t = e).indexOf("Playstation") && (i = t.match(/\bSilk\/([0-9._-]+)\b/)) && (r.browserName = "Amazon Silk", r.browserVersion = i[1]), r.browserName && r.browserVersion && (0 === r.browserName.indexOf("Chrome") && h(r.browserVersion, ["28", null]) || 0 === r.browserName.indexOf("Opera") && h(r.browserVersion, ["15", null])) && (r.browserRenderingEngine = "Blink"), r.browserRenderingEngineVersion || (r.browserRenderingEngineVersion = (i = (i = (i = t.match(/Trident\/([0-9.]+)/)) || ((i = t.match(/Presto\/([0-9.]+)/)) || t.match(/AppleWebKit\/*([0-9.]+)/i))) || t.match(/Gecko\/*([0-9.]+)/)) ? i[1] : ""), new s(n, e)
                }
            }(window);;
        window.deviceInfo = CeltraDeviceInfo.create(window.navigator.userAgent, window.celtraDeviceInfoRuntimeParams);;
        ! function(e) {
            e.celtra = e.celtra || {};
            var t = {},
                u = (t.ios = i, t.android = r, t.webkit = function() {
                    var e = n().match(/AppleWebKit\/*([0-9.]+)/i);
                    return !!e && a(e[1], arguments)
                }, t.windowsPhone = o, t.gecko = function() {
                    var e = n().match(/Gecko\/*([0-9.]+)/i);
                    return !!e && a(e[1], arguments)
                }, t.windows = function() {
                    var e = n().match(/MSIE ([0-9]{1,}[.0-9]{0,})|Trident.* rv:([0-9]{1,}[.0-9]{0,})/i);
                    return !!e && a(e[1] || e[2], arguments)
                }, t.kindleSilk = function() {
                    var e = n().match(/Silk\/([0-9.]+)/);
                    return !!e && a(e[1], arguments)
                }, t.kindle = function() {
                    var e = n().toLowerCase();
                    return !!/kftt|kfot|kfjwi|kfjwa|kfso|kfth|kfapwi|kfthwi|kfsowi|kfthwa|kfapwa|kfap|sd4930ur/.test(e) && a("2", arguments)
                }, t.iframe = function() {
                    return u.top !== e
                }, t.desktop = function() {
                    return !(r() || i() || o())
                }, t.tablet = function() {
                    return /iPad/.test(n()) || 550 < ("undefined" != typeof offsetWidthOverride ? offsetWidthOverride : (document.body.offsetWidth, Math.min(innerWidth, innerHeight)))
                }, t.chromeBased = function() {
                    if (!(i() || window.chrome && "Google Inc." === window.navigator.vendor)) return !1;
                    var e;
                    e = i() ? n().match(/CriOS\/([0-9]+.)/) : n().match(/Chrome\/([0-9]+.)/);
                    return !!e && a(e[1], arguments)
                }, t.isHighDensityDisplay = function() {
                    return 1 < window.devicePixelRatio || window.matchMedia && window.matchMedia("(-webkit-min-device-pixel-ratio: 1.5),(min--moz-device-pixel-ratio: 1.5),(-o-min-device-pixel-ratio: 3/2),(min-resolution: 1.5dppx)").matches
                }, extend(e.celtra, t), delete t.webkit, extend(e, t), {
                    max: e.Math.max,
                    min: e.Math.min,
                    userAgent: e.navigator.userAgent,
                    top: e.top
                }),
                n = function() {
                    return void 0 !== e.userAgentOverride ? e.userAgentOverride : u.userAgent
                };

            function i() {
                var e = n().match(/iP(ad|hone|od).*OS ([0-9_]+)/);
                return !!e && a(e[2].replace(/_/g, "."), arguments)
            }

            function r() {
                var e = n().match(/Android ([0-9.]+)/);
                return !n().match(/Windows Phone/) && !!e && a(e[1], arguments)
            }

            function o() {
                var e = n().match(/Windows Phone ([0-9.]+)/);
                return !!e && a(e[1], arguments)
            }

            function a(e, t) {
                if (!e) return !0;
                if (0 === t.length) r = i = null;
                else if (1 === t.length) i = t[0], r = t[0];
                else {
                    if (2 !== t.length) throw "Invalid number of arguments";
                    i = t[0], r = t[1]
                }

                function n(e) {
                    return parseInt(e.replace(/\D/g, ""), 10)
                }
                e = e.split(".").map(n);
                for (var i = i ? i.split(".").map(n) : [], r = r ? r.split(".").map(n) : [], o = u.max(i.length, r.length) - e.length; 0 <= o; o--) e.push(0);

                function a(e, t) {
                    for (var n = 0; n < u.min(e.length, t.length); n++) {
                        if (e[n] < t[n]) return -1;
                        if (e[n] > t[n]) return 1
                    }
                    return 0
                }
                return !(-1 === a(e, i) || 1 === a(e, r))
            }
        }(window);;
        ! function(n) {
            var e = n.navigator.userAgent,
                r = function() {
                    var e = n;
                    try {
                        for (; void 0 !== e.parent.location.href && e.parent.document !== e.document;) e = e.parent
                    } catch (e) {}
                    return e
                }(),
                t = /rv:.*Gecko\//.test(e),
                i = /MSIE|Trident\//.test(e),
                o = /WebKit/.test(e),
                a = /^-?([mM]oz|[wW]eb[kK]it|[mM]s)-?/,
                l = {},
                s = [],
                f = o ? "webkit" : i ? "ms" : t ? "Moz" : "",
                u = (e = e.match(/AppleWebKit\/(\d+)/)) && ~~e[1] < 540,
                c = ["transform", "transform-origin", "transform-style", "transition", "transition-delay", "transition-duration", "transition-property", "transition-timing-function", "animation", "animation-delay", "animation-direction", "animation-duration", "animation-fill-mode", "animation-iteration-count", "animation-name", "animation-play-state", "animation-timing-function", "appearance", "backface-visibility", "perspective", "perspective-origin"],
                m = d();

            function d() {
                return r.getComputedStyle(r.document.body, null)
            }

            function p(e, t, i) {
                if (!e) throw new Error("No element specified!");
                if (E(t)) {
                    if (!(t in e.style) && p.BREAK_ON_ERROR) throw new Error("Invalid CSS attribute " + t);
                    if (void 0 === i) return e.style[t];
                    e.style[t] = i
                } else {
                    if (-1 == s.indexOf(t) && y(t), void 0 === i) return e.style[l[t]];
                    e.style[l[t]] = i
                }
            }

            function w(e, t) {
                if (!e) throw new Error("No element specified!");
                var i, e = e.ownerDocument.defaultView.getComputedStyle(e, null);
                if (u && -1 < c.indexOf(t) && (t = "webkit" + v(t)), !e || "none" == e.display || !(n === r || (i = n.frameElement.ownerDocument.defaultView.getComputedStyle(n.frameElement, null)) && "none" != i.display)) return null;
                if (t in e || !p.BREAK_ON_ERROR) return E(t) ? e[t] : (-1 == s.indexOf(t) && y(t), e[l[t]]);
                throw new Error("Invalid CSS attribute " + t)
            }

            function y(e) {
                m = m || d();
                var t = f + v(e);
                if (u && -1 < c.indexOf(e)) l[e] = t;
                else if (m && e in m) l[e] = e;
                else if (m && t in m) l[e] = t;
                else if (!m && deviceInfo.browser.firefox) l[e] = e;
                else if (p.BREAK_ON_ERROR) throw new Error("Invalid CSS attribute " + e + ' or iframe still display "none" in FF');
                s.push(e)
            }

            function E(e) {
                return a.test(e)
            }

            function v(e) {
                return (e += "") ? e[0].toUpperCase() + e.slice(1) : ""
            }
            p.BREAK_ON_ERROR = !1, n.celtra = n.celtra || {}, n.celtra.styler = {
                css: p,
                computedCSS: w,
                isWebkit: o,
                isGecko: t,
                isIE: i
            }, n.css = p, n.computedCSS = w
        }(window);;

        function PerformanceTracker(t, e) {
            this.trackingCenter = t, this.trackingEnabled = e.get("PerformanceTiming") || e.get("MonotypeOffloadFonts"), this._id = 0
        }
        PerformanceTracker.prototype.start = function(t, e) {
            var r = this,
                t = {
                    name: t,
                    type: "interval",
                    id: this._id++,
                    startTime: Date.now(),
                    args: e || []
                };
            return t.end = function() {
                this.endTime = Date.now();
                var t = this.endTime - this.startTime;
                Logger("perf").log(this.name + "(#" + this.id + ") " + t), r.collect(this)
            }.bind(t), t
        }, PerformanceTracker.prototype.collect = function(t) {
            this.trackingEnabled && (t = {
                name: "perfTiming",
                section: t.name,
                type: t.type,
                perfId: t.id,
                startTime: t.startTime,
                endTime: t.endTime,
                args: t.args
            }, this.trackingCenter.trackNoLaterThan(t, 1e3))
        }, PerformanceTracker.prototype.mark = function(t) {
            t = this.start(t);
            t.type = "mark", t.endTime = t.startTime, this.collect(t)
        }, PerformanceTracker.prototype.marks = function(t) {
            return function() {
                this.mark(t)
            }.bind(this)
        };;
        var EventEmitter = {
            emit: function(e) {
                var t, n = this._listeners;
                n && n[e] && (t = [].slice.call(arguments, 1), n[e].forEach(function(e) {
                    if ("undefined" != typeof window) e.apply(window, t);
                    else {
                        if ("undefined" == typeof self) throw "EventEmitter not supported in current scope.";
                        e.apply(self, t)
                    }
                }))
            },
            emits: function(e) {
                var t = arguments;
                return function() {
                    this.emit.apply(this, t)
                }.bind(this)
            },
            addListener: function(e, t) {
                var n = this._listeners;
                (n = n || (this._listeners = {}))[e] || (n[e] = []), n[e].push(t)
            },
            removeListener: function(e, t) {
                var n = this._listeners;
                n && n[e] && (n[e] = n[e].filter(function(e) {
                    return e !== t
                }))
            },
            once: function(t, n) {
                var i = this;
                i.on(t, function e() {
                    i.off(t, e), n.apply(this, arguments)
                })
            },
            onAll: function(e, t) {
                e.split(" ").waitForEach(function(e, t) {
                    this.once(e, t)
                }, t, this)
            }
        };
        EventEmitter.on = EventEmitter.addListener, EventEmitter.off = EventEmitter.removeListener, EventEmitter.addEventListener = EventEmitter.addListener, EventEmitter.removeEventListener = EventEmitter.removeListener, "undefined" != typeof module && null !== module && (module.exports = EventEmitter);;

        function TaskScheduler() {
            this.hub = extend({}, EventEmitter)
        }
        TaskScheduler.prototype.when = function() {
            var n = this,
                e = Array.isArray(arguments[0]) ? arguments[0] : Array.prototype.slice.call(arguments);
            return {
                run: function(t) {
                    e.waitForEach(function(t, e) {
                        n.hub.once(t, e)
                    }, t)
                }
            }
        }, TaskScheduler.prototype.notify = function(t) {
            this.paused || this.hub.emit(t)
        }, TaskScheduler.prototype.notifies = function(t) {
            return function() {
                this.notify(t)
            }.bind(this)
        }, TaskScheduler.prototype.pause = function() {
            this._paused = !0
        };;

        function TrackingCenter(e, t, n, i, s, r) {
            this._sessionId = e, this._accountId = t, this._trackingUrl = i, this._trackers = s, this._purpose = n, this._flushCycle = null, this._flushCycleLength = 1e4, this._useBatching = r, this._usePixel = !0, this.windowForPixels = window, this._instantiation = randInt(), this._eventIndex = 0, this._pendingEvents = [], this._eventsInProgress = [], this._pendingPixels = [], this._waitingTrackingRequests = 0, this._isBeaconSupported = this.windowForPixels.navigator && this.windowForPixels.navigator.sendBeacon, this._decrementWaiting = this._decrementWaiting.bind(this), this.flush = this.flush.bind(this), this.batchFlush = this.batchFlush.bind(this), this._flushPixels = this._flushPixels.bind(this)
        }
        extend(TrackingCenter.prototype, EventEmitter), TrackingCenter.eventCountLimit = 1e3, TrackingCenter.prototype.setExperimentStatus = function(e) {
            var t;
            e && e.get && (t = e.get("BatchTrackingRequests"), this._useBatching = t ? "control" !== t.chosenVariant.slice(0, 7) : this._useBatching, t = t && "Beacon" === t.chosenVariant.slice(-6), e = e.get("TrackWithBeaconAPI") || t, this._useBeacon = e && this._isBeaconSupported, this._usePixel = !(t && this._useBeacon))
        }, TrackingCenter.prototype.batchFlush = function() {
            this._useBatching ? this.flush() : noop()
        }, TrackingCenter.prototype.startBatchFlushCycle = function(e) {
            this._useBatching && (this.windowForPixels.setTimeout(this.flush, e), this._flushCycle = this.windowForPixels.setTimeout(this.flush, this._flushCycleLength))
        }, TrackingCenter.prototype.isTrackingLimitReached = function() {
            return this._eventIndex >= TrackingCenter.eventCountLimit
        }, TrackingCenter.prototype._createTrackingEvent = function(e) {
            return e = JSON.parse(JSON.stringify(e)), extend({
                sessionId: this._sessionId,
                accountId: this._accountId,
                stream: "adEvents",
                instantiation: this._instantiation,
                index: this._eventIndex++,
                clientTimestamp: new Date / 1e3
            }, e)
        }, TrackingCenter.prototype._addEventPendingToQueues = function(e) {
            e = this._resolvePixelsAndEventsForQueue([e]);
            this.isTrackingLimitReached() && e.events.push(this._createTrackingEvent({
                name: "eventLimitReached"
            })), e.events.forEach(function(e) {
                Logger("tracking").log("Queuing event " + JSON.stringify(e))
            }), e.pixels.forEach(function(e) {
                Logger("tracking").log("Queuing pixel " + JSON.stringify(e))
            }), this._pendingEvents = this._pendingEvents.concat(e.events), this._pendingPixels = this._pendingPixels.concat(e.pixels)
        }, TrackingCenter.prototype.track = function(e, t) {
            this.trackNoLaterThan(e, 0, t)
        }, TrackingCenter.prototype.trackNoLaterThan = function(e, t, n) {
            var i, s, r;
            this.isTrackingLimitReached() ? console.warn("Tracking limit reached: " + TrackingCenter.eventCountLimit + " events.") : (i = t, parseInt(i, 10) !== i ? t = 1500 : t < 0 && (t = 0), n = n || noop, "live" !== this._purpose ? (i = this._createTrackingEvent(e), Logger("tracking").log("Ignoring non-live event " + JSON.stringify(i)), defer(n)) : (s = null, this._addEventPendingToQueues(e), r = this.windowForPixels, this.once("trackingQueueEmptied", function() {
                s && (r.clearTimeout(s), s = null), n()
            }.bind(this)), s = this._useBatching ? r.setTimeout(this._flushPixels, t) : r.setTimeout(this.flush, t)))
        }, TrackingCenter.prototype._resolvePixelsAndEventsForQueue = function(e) {
            var o = this;
            return function e(t, n, i) {
                var s, r;
                return 0 == t.length ? {
                    events: n,
                    pixels: i
                } : (s = t[0], s = o._createTrackingEvent(s), r = o._trackers.urlsAndEventsFor(s), e(t.slice(1).concat(r.events || []), n.concat(s), i.concat(r.urls || [])))
            }(e, [], [])
        }, TrackingCenter.prototype._fireBeacon = function(e, t) {
            this.windowForPixels.navigator.sendBeacon(e), t()
        }, TrackingCenter.prototype._firePixel = function(e, t) {
            function n() {
                i.onload = i.onerror = null, t()
            }
            var i = this.windowForPixels.document.createElement("img");
            i.onload = i.onerror = n;
            try {
                i.src = e
            } catch (e) {
                n()
            }
        }, TrackingCenter.prototype._decrementWaiting = function() {
            0 < this._waitingTrackingRequests && (--this._waitingTrackingRequests || this.emit("trackingQueueEmptied"))
        }, TrackingCenter.prototype.flush = function(e, t, n) {
            var i, s = this,
                r = e || noop,
                o = (t = void 0 === t ? this._usePixel : t, n = void 0 === n ? this._useBeacon : n, s._pendingEvents);
            s._pendingEvents = [], s._eventsInProgress = s._eventsInProgress.concat(o), o.length && (s._waitingTrackingRequests++, this._useBatching && (this.windowForPixels.clearTimeout(this._flushCycle), r = function() {
                s._flushCycle = s.windowForPixels.setTimeout(s.flush, s._flushCycleLength), (e || noop)()
            }), i = function() {
                s._eventsInProgress = s._eventsInProgress.filter(function(e) {
                    return -1 === o.indexOf(e)
                }), s._decrementWaiting()
            }, n && s._fireBeacon(s._getTrackerBeaconUrl(o), i), t && s._firePixel(s._getTrackerPixelUrl(o), i)), s._flushPixels(), 0 === s._waitingTrackingRequests ? this.windowForPixels.setTimeout(r, 0) : this.once("trackingQueueEmptied", r)
        }, TrackingCenter.prototype.flushWithBeacon = function(e) {
            this._isBeaconSupported ? this.flush(e, !1, !0) : this.flush(e)
        }, TrackingCenter.prototype._flushPixels = function() {
            var t = this;
            t._pendingPixels.forEach(function(e) {
                t._waitingTrackingRequests++, t._firePixel(e, t._decrementWaiting)
            }), t._pendingPixels = []
        }, TrackingCenter.prototype._getTrackerUrl = function(e) {
            return base64json = this.windowForPixels.btoa(to_utf8(JSON.stringify(e))), this._trackingUrl + "json/" + base64json + "?crc32c=" + crc32(base64json)
        }, TrackingCenter.prototype._getTrackerPixelUrl = function(e) {
            return this._getTrackerUrl({
                events: e
            })
        }, TrackingCenter.prototype._getTrackerBeaconUrl = function(e) {
            return this._getTrackerUrl({
                events: e.map(function(e) {
                    e = JSON.parse(JSON.stringify(e));
                    return e.beacon = !0, e
                })
            })
        }, TrackingCenter.prototype._getTrackerRedirectUrl = function(e, t, n, i) {
            e = {
                events: e,
                pixels: t,
                dest: n
            };
            return (i = i || {}).jsRedirectFunc && (e.redirectFunc = i.jsRedirectFunc), this._getTrackerUrl(e)
        }, TrackingCenter.prototype.wrapRedirectPageUrl = function(e, t) {
            e = this._getTrackerRedirectUrl(this._pendingEvents.concat(this._eventsInProgress), this._pendingPixels, e, t);
            return this._useBeacon ? this.flush(void 0, !1) : (this._pendingEvents = [], this._pendingPixels = []), e
        };;

        function Experiments(t, e) {
            for (var n in this._instances = {}, t) this._instances[n] = new Experiment(n, t[n], e);
            e.setExperimentStatus(this)
        }

        function Experiment(t, e, n) {
            this.key = t, this.chosenVariant = e, this._trackingCenter = n, this._variantExposedTracked = !1, this._variantSucceededTracked = !1, this.trackExposure = function() {
                this._track("variantExposed")
            }.bind(this), this.trackSuccess = function() {
                this._track("variantSucceeded")
            }.bind(this)
        }
        Experiments.prototype.get = function(t, e) {
            t = [t].concat(e || []).map(function(t) {
                return (t = encodeURIComponent(t)).replace(/'/g, "%27").replace(/\(/g, "%28").replace(/\)/g, "%29").replace(/!/g, "%21").replace(/~/g, "%7E")
            }).join("/");
            return this._instances[t] || null
        }, Experiment.prototype._track = function(t) {
            this["_" + t + "Tracked"] || (this["_" + t + "Tracked"] = !0, this._trackingCenter.track({
                name: t,
                experimentKey: this.key,
                variant: this.chosenVariant
            }))
        };;

        function TouchEventSimulator(t) {
            this.el = t, this.doc = t.ownerDocument || t, this.win = this.doc.defaultView, this.touch = null, this._initialised = !1, this._firstEventFired = !1, this.handleFirstEvent = this.handleFirstEvent.bind(this), this.handleMouseDown = this.handleMouseDown.bind(this), this.handleMouseMove = this.handleMouseMove.bind(this), this.handleMouseUp = this.handleMouseUp.bind(this), this.handleMouseOut = this.handleMouseOut.bind(this), this.handlePointerDown = this.handlePointerDown.bind(this), this.handlePointerMove = this.handlePointerMove.bind(this), this.handlePointerUp = this.handlePointerUp.bind(this), this.handlePointerCancel = this.handlePointerCancel.bind(this), this.captureBubbleEvents = this.captureBubbleEvents.bind(this)
        }
        TouchEventSimulator.mode = null, TouchEventSimulator.prototype.start = function() {
            this._initialised ? "function" == typeof Logger && Logger("TouchEventSimulator").warn("Touch event simulator already initialised!") : ("function" == typeof Logger && Logger("TouchEventSimulator").log("Enabling touch event simulation"), this.doc.defaultView.msPointerEnabled ? this.startPointers() : this.startMouse(), this.el.addEventListener("touchstart", this.captureBubbleEvents, !1), this.el.addEventListener("touchmove", this.captureBubbleEvents, !1), this.el.addEventListener("touchend", this.captureBubbleEvents, !1), this.el.addEventListener("touchcancel", this.captureBubbleEvents, !1), this.el.addEventListener("tap", this.captureBubbleEvents, !1))
        }, TouchEventSimulator.prototype.stop = function() {
            this._initialised ? ("function" == typeof Logger && Logger("TouchEventSimulator").log("Disabling touch event simulation"), this.win && this.win.msPointerEnabled ? this.stopPointers() : this.stopMouse(), this.el.removeEventListener("touchstart", this.captureBubbleEvents, !1), this.el.removeEventListener("touchmove", this.captureBubbleEvents, !1), this.el.removeEventListener("touchend", this.captureBubbleEvents, !1), this.el.removeEventListener("touchcancel", this.captureBubbleEvents, !1), this.el.removeEventListener("tap", this.captureBubbleEvents, !1)) : "function" == typeof Logger && Logger("TouchEventSimulator").warn("Touch event simulator not running!")
        }, TouchEventSimulator.prototype.captureBubbleEvents = function(t) {
            t.stopPropagation(), t.preventDefault()
        }, TouchEventSimulator.prototype.startMouse = function() {
            this.el.addEventListener("mousedown", this.handleMouseDown, !0), this.el.addEventListener("mousemove", this.handleMouseMove, !0), this.el.addEventListener("mouseup", this.handleMouseUp, !0), this.el.addEventListener("mouseout", this.handleMouseOut, !0), this.el.addEventListener("dragstart", this.handleDragStart, !0), this._initialised = !0
        }, TouchEventSimulator.prototype.stopMouse = function() {
            this.el.removeEventListener("mousedown", this.handleMouseDown, !0), this.el.removeEventListener("mousemove", this.handleMouseMove, !0), this.el.removeEventListener("mouseup", this.handleMouseUp, !0), this.el.removeEventListener("mouseout", this.handleMouseOut, !0), this.el.removeEventListener("dragstart", this.handleDragStart, !0), this._initialised = !1
        }, TouchEventSimulator.prototype.startPointers = function() {
            this.el.addEventListener("MSPointerDown", this.handlePointerDown, !0), this.el.addEventListener("MSPointerMove", this.handlePointerMove, !0), this.el.addEventListener("MSPointerUp", this.handlePointerUp, !0), this.el.addEventListener("MSPointerCancel", this.handlePointerCancel, !0), this._initialised = !0
        }, TouchEventSimulator.prototype.stopPointers = function() {
            this.el.removeEventListener("MSPointerDown", this.handlePointerDown, !0), this.el.removeEventListener("MSPointerMove", this.handlePointerMove, !0), this.el.removeEventListener("MSPointerUp", this.handlePointerUp, !0), this.el.removeEventListener("MSPointerCancel", this.handlePointerCancel, !0), this._initialised = !1
        }, TouchEventSimulator.prototype.init = function() {
            this.doc.defaultView.navigator.msPointerEnabled ? this.start() : (this.el.addEventListener("touchstart", this.handleFirstEvent, !0), this.el.addEventListener("mousedown", this.handleFirstEvent, !0))
        }, TouchEventSimulator.prototype.updateTouchCoordinates = function(t) {
            this.touch.screenX = t.screenX, this.touch.screenY = t.screenY, this.touch.pageX = t.pageX, this.touch.pageY = t.pageY, this.touch.clientX = t.clientX, this.touch.clientY = t.clientY
        }, TouchEventSimulator.prototype.ignorables = ["select", "input", "textarea"], TouchEventSimulator.prototype.isFormElement = function(t) {
            return -1 < this.ignorables.indexOf(t.nodeName.toLowerCase())
        }, TouchEventSimulator.prototype.isIgnorable = function(t) {
            for (var e = t; e && e instanceof HTMLElement; e = e.parentNode)
                if (hasClass(e, "ignore-toucheventsimulator")) return !0;
            return !1
        }, TouchEventSimulator.prototype.handleFirstEvent = function(t) {
            this._firstEventFired || (this._firstEventFired = !0, this.el.removeEventListener("touchstart", this.handleFirstEvent, !0), this.el.removeEventListener("mousedown", this.handleFirstEvent, !0), "touch" != TouchEventSimulator.mode && "mousedown" == t.type ? (TouchEventSimulator.mode = "mouse", this.start(), this.handleMouseDown(t)) : TouchEventSimulator.mode = "touch")
        }, TouchEventSimulator.prototype.handleMouseDown = function(t) {
            0 != t.button || this.isIgnorable(t.target) || (this.touch = {
                identifier: 0,
                target: t.target
            }, this.updateTouchCoordinates(t), t.stopPropagation(), this.isFormElement(t.target) || t.preventDefault(), this.fireTouchEvent("touchstart", t))
        }, TouchEventSimulator.prototype.handleMouseMove = function(t) {
            this.touch && 0 == t.button && (this.updateTouchCoordinates(t), t.stopPropagation(), t.preventDefault(), this.fireTouchEvent("touchmove", t))
        }, TouchEventSimulator.prototype.handleMouseUp = function(t) {
            this.touch && 0 == t.button && (this.updateTouchCoordinates(t), t.stopPropagation(), t.preventDefault(), this.fireTouchEvent("touchend", t), this.touch = null)
        }, TouchEventSimulator.prototype.handleMouseOut = function(t) {
            this.touch && 0 == t.button && (t.clientX <= 0 || t.clientX >= this.win.innerWidth || t.clientY <= 0 || t.clientY >= this.win.innerHeight) && this.cancelInteraction()
        }, TouchEventSimulator.prototype.handleDragStart = function(t) {
            return t.preventDefault(), !1
        }, TouchEventSimulator.prototype.handlePointerDown = function(t) {
            0 == t.button && (this.touch = {
                identifier: 0,
                target: t.target
            }, this.updateTouchCoordinates(t), this.fireTouchEvent("touchstart", t))
        }, TouchEventSimulator.prototype.handlePointerMove = function(t) {
            this.touch && 0 == t.button && (this.updateTouchCoordinates(t), this.fireTouchEvent("touchmove", t))
        }, TouchEventSimulator.prototype.handlePointerUp = function(t) {
            this.touch && 0 == t.button && (this.updateTouchCoordinates(t), this.fireTouchEvent("touchend", t))
        }, TouchEventSimulator.prototype.handlePointerCancel = function(t) {
            this.touch && 0 == t.button && (this.updateTouchCoordinates(t), this.fireTouchEvent("touchcancel", t), this.touch = null)
        }, TouchEventSimulator.prototype.fireTouchEvent = function(t, e) {
            var n = this.doc.createEvent("HTMLEvents");
            n.initEvent(t, !0, !0), "touchend" == t || "touchcancel" == t ? (n.touches = n.targetTouches = [], n.changedTouches = [this.touch]) : n.touches = n.targetTouches = n.changedTouches = [this.touch], e.target.dispatchEvent(n)
        }, TouchEventSimulator.prototype.cancelInteraction = function() {
            this.touch && (this.fireTouchEvent("touchcancel", this.touch), this.touch = null)
        };;

        function AggregatorTracking(t) {
            this._trackingCenter = t
        }
        AggregatorTracking.prototype.trackAggregator = function(t, r, g) {
            "object" == typeof r && (g = r, r = void 0), this._trackingCenter.track({
                name: "aggregator",
                metric: t,
                value: r = void 0 !== r ? r : 1,
                customDimensions: g = g || {}
            })
        }, AggregatorTracking.prototype.trackAggregatorTime = function(t, r, g) {
            this.trackAggregator(t, Math.round(r), g)
        }, AggregatorTracking.prototype.trackAggregatorUsingTimer = function(t, r) {
            var g = new Date;
            return function() {
                this.trackAggregatorTime(t, new Date - g, r)
            }.bind(this)
        }, AggregatorTracking.prototype.trackAggregatorMagicTriplet = function(r, g, e) {
            this.trackAggregator(r + "Attempts", g);
            var a = new Date;
            return function() {
                var t = new Date - a;
                (!e || t < e) && (this.trackAggregator(r + "Successes", g), this.trackAggregatorTime(r + "Time", t, g))
            }.bind(this)
        };;

        function Logger(e) {
            if (!(this instanceof Logger)) return new Logger(e);
            e = e || "";
            var n = noop;
            (0 <= Logger.enabledNames.indexOf(e) || 0 <= Logger.enabledNames.indexOf("all")) && (n = function() {
                var e = [].slice.apply(arguments);
                e.unshift(Logger._ts() + " [" + Logger.sessionId + (this.name ? " " + this.name : "") + "]"), console.log.apply(console, e)
            }), this.name = e, this.log = n, this.warn = n, this.debug = n, this.error = n
        }
        Logger._ts = function() {
            var e = new Date;

            function n(e, n) {
                for (e += ""; e.length < n;) e = "0" + e;
                return e
            }
            return e.getFullYear() + "-" + n(e.getMonth() + 1, 2) + "-" + n(e.getDate(), 2) + " " + n(e.getHours(), 2) + ":" + n(e.getMinutes(), 2) + ":" + n(e.getSeconds(), 2) + "." + n(e.getTime() - 1e3 * Math.floor(e.getTime() / 1e3), 3)
        }, Logger.init = function(e, n) {
            Logger.enabledNames = e, Logger.sessionId = n || (Math.random() + "").slice(15)
        }, Logger.initFromRuntimeParams = function(e) {
            Logger.init(e.debug ? e.debug.split(",") : [], e.sessionId)
        };;

        function InViewObject(t, e) {
            this.view = t, this.inViewParent = e, this._init()
        }
        extend(InViewObject.prototype, EventEmitter), InViewObject.prototype._init = function() {
            this.rectInView = CRect.ZERO, this.areaInViewRatio = 0, this.active = !1, this._maxPossibleDimensions = {
                width: 0,
                height: 0,
                area: 0
            }, this.computeRectInView = this.computeRectInView.bind(this), this.start = this.start.bind(this), this.stop = this.stop.bind(this)
        }, InViewObject.prototype.getNode = function() {
            return this.view.getNode()
        }, InViewObject.prototype.getParentMaxPossDims = function() {
            return this.inViewParent ? this.inViewParent.getMaxPossibleDimensions() : this.adapter.getViewportRect()
        }, InViewObject.prototype.computeRectInView = function() {
            var t = this.inViewParent.getRectInView(),
                e = this.getBoundingClientRect(),
                t = t.intersect(e),
                i = !1,
                e = (this.rectInView.equals(t) || (this.rectInView = t, i = !0), this._computeMaxPossibleDimensions(e, this.getParentMaxPossDims()), 0 < this._maxPossibleDimensions.area ? t.area / this._maxPossibleDimensions.area : 0),
                n = !1;
            e !== this.areaInViewRatio && (this.areaInViewRatio = e, n = !0), i && (this.emit("rectInViewChanged", t), Logger("InViewObject").debug(this.view.toString() + ", rectInViewChanged: " + this.rectInView.toString())), n && (this.emit("areaInViewRatioChanged", e), Logger("InViewObject").debug(this.view.toString() + ", areaInViewRatioChanged: " + e))
        }, InViewObject.prototype.getBoundingClientRect = function() {
            var t = this.getNode();
            return t ? CRect.adopt(t.getBoundingClientRect()) : CRect.ZERO
        }, InViewObject.prototype.getMaxPossibleDimensions = function() {
            return this._maxPossibleDimensions
        }, InViewObject.prototype._computeMaxPossibleDimensions = function(t, e) {
            this._maxPossibleDimensions.width = Math.min(e.width, t.width), this._maxPossibleDimensions.height = Math.min(e.height, t.height), this._maxPossibleDimensions.area = this._maxPossibleDimensions.width * this._maxPossibleDimensions.height
        }, InViewObject.prototype.getRectInView = function() {
            return this.rectInView
        }, InViewObject.prototype.getAreaInViewRatio = function() {
            return this.areaInViewRatio
        }, InViewObject.prototype.start = function() {
            this.active || (this._start(), this.active = !0, this.computeRectInView())
        }, InViewObject.prototype._start = function() {
            this.inViewParent.on("rectInViewChanged", this.computeRectInView), this.inViewParent.on("areaInViewRatioChanged", this.computeRectInView)
        }, InViewObject.prototype.stop = function() {
            this.active && (this.computeRectInView(), this._stop(), this.active = !1)
        }, InViewObject.prototype._stop = function() {
            this.inViewParent.off("rectInViewChanged", this.computeRectInView), this.inViewParent.off("areaInViewRatioChanged", this.computeRectInView)
        };;

        function AdViewableTimeObserver(e) {
            this._tracker = e, this._readyToShow = !1, this._adapter
        }

        function AdViewableInViewObjectObserver(e) {
            AdViewableInViewObjectObserver.uberConstructor.apply(this, arguments), this._inViewObjects = [], this._viewableInViewObjects = []
        }

        function AdViewableUnitObserver(e) {
            AdViewableUnitObserver.uberConstructor.apply(this, arguments), this._units = [], this._viewableUnits = [], this._containerIsViewable = !1
        }
        AdViewableTimeObserver.prototype.readyToShow = function(e) {
            throw new Error("AdViewableInViewObjectObserver.readyToShow not implemented!")
        }, AdViewableTimeObserver.prototype.registerUnit = function(e) {
            throw new Error("AdViewableInViewObjectObserver.registerUnit not implemented!")
        }, AdViewableTimeObserver.prototype.registerAdapter = function(e) {
            throw new Error("AdViewableTimeObserver.registerAdapter not implemented!")
        }, AdViewableTimeObserver.prototype.stop = function() {
            this._tracker.stop()
        }, inherit(AdViewableInViewObjectObserver, AdViewableTimeObserver), AdViewableInViewObjectObserver.prototype.registerAdapter = function(e) {
            this._adapter = e;
            var t = this._tracker;
            e.mediaState.on("videoStarted", function() {
                this._tryStartTracker()
            }.bind(this)), e.mediaState.on("videoStopped", function() {
                0 === this._viewableInViewObjects.length && t.stop()
            }.bind(this))
        }, AdViewableInViewObjectObserver.prototype.readyToShow = function() {
            this._readyToShow = !0, this._tryStartTracker()
        }, AdViewableInViewObjectObserver.prototype.registerUnit = function(e) {
            var r = e.inView,
                e = this._inViewObjects,
                a = this._viewableInViewObjects,
                n = this._tracker; - 1 === e.indexOf(r) && (e.push(r), !(-1 !== a.indexOf(r)) && 0 < r.getAreaInViewRatio() && a.push(r), this._tryStartTracker(), r.on("areaInViewRatioChanged", function(e) {
                var t = a.indexOf(r),
                    i = -1 !== t;
                !i && 0 < e ? (a.push(r), this._tryStartTracker()) : 0 === e && i && (a.splice(t, 1), 0 === a.length && n.stop())
            }.bind(this)))
        }, AdViewableInViewObjectObserver.prototype._tryStartTracker = function() {
            var e = this._adapter,
                e = e && e.mediaState.playingVideo;
            return !(!this._readyToShow || !(0 < this._viewableInViewObjects.length || e)) && (this._tracker.start(), !0)
        }, inherit(AdViewableUnitObserver, AdViewableTimeObserver), AdViewableUnitObserver.prototype.registerAdapter = function(e) {
            this._adapter = e, this._containerIsViewable = e.containerIsViewable;
            var t = this._tracker;
            e.on("containerViewableChange", function(e) {
                (this._containerIsViewable = e) ? this._tryStartTracker(): t.stop()
            }.bind(this)), e.mediaState.on("videoStarted", function() {
                this._tryStartTracker()
            }.bind(this)), e.mediaState.on("videoStopped", function() {
                this._adapter.containerIsViewable && 0 !== this._viewableUnits.length || t.stop()
            }.bind(this))
        }, AdViewableUnitObserver.prototype.readyToShow = function() {
            this._readyToShow = !0, this._tryStartTracker()
        }, AdViewableUnitObserver.prototype.registerUnit = function(t) {
            if (!this._adapter) throw new Error("adapter was not registered yet!");
            var e = this._units,
                i = this._viewableUnits,
                r = this._tracker; - 1 === e.indexOf(t) && (e.push(t), -1 === i.indexOf(t) && t._visible && (i.push(t), this._tryStartTracker()), t.on("appeared", function() {
                -1 === i.indexOf(t) && (i.push(t), this._tryStartTracker())
            }.bind(this)), t.on("disappeared", function() {
                var e = i.indexOf(t); - 1 !== e && (i.splice(e, 1), 0 === i.length && r.stop())
            }.bind(this)))
        }, AdViewableUnitObserver.prototype._tryStartTracker = function() {
            var e = this._adapter;
            return !!(e && (e.containerIsViewable || e.mediaState.playingVideo) && this._readyToShow && 0 < this._viewableUnits.length) && (this._tracker.start(), !0)
        };;

        function AdViewableTimeTracker(i, e, t, n) {
            this._trackingCenter = i, this._raf = e, this._win = t, this._intervals = n || {
                3e3: 1e3,
                1e4: 2e3,
                63e3: 3e3
            }, this._isRunning = !1, this._viewableMilliseconds = 0, this._fromTime = 0, this._pendingPingTimeoutId, this._pendingPingRafId, this._maxRafLatency = 500
        }
        AdViewableTimeTracker.eventName = "viewableTime", AdViewableTimeTracker.TrackingData = function(i, e) {
            return {
                name: AdViewableTimeTracker.eventName,
                from: i / 1e3,
                to: (e = e < i ? i : e) / 1e3
            }
        }, AdViewableTimeTracker.prototype.start = function() {
            this._isRunning || (this._isRunning = !0, this._fromTime = Date.now(), 0 === this._viewableMilliseconds ? this._initPing() : this._setNextPing())
        }, AdViewableTimeTracker.prototype.stop = function() {
            var i, e;
            this._clearPendingPing(), this._isRunning && -1 !== this._getPingInterval() && (e = (i = Date.now()) - this._fromTime, this._viewableMilliseconds += e, e = new AdViewableTimeTracker.TrackingData(this._fromTime, i), this._track(e)), this._isRunning = !1
        }, AdViewableTimeTracker.prototype._initPing = function() {
            var i = this._fromTime,
                e = this._getPingInterval(),
                t = new AdViewableTimeTracker.TrackingData(this._fromTime, i);
            this._pendingPingRafId = this._raf.nextFrame(function() {
                this._track(t), this._setFromTimeForNextPing(i, e), this._setNextPing()
            }.bind(this))
        }, AdViewableTimeTracker.prototype._setNextPing = function() {
            this._clearPendingPing();
            var t = this._getPingInterval();
            this._isRunning && -1 !== t && (this._pendingPingTimeoutId = this._win.setTimeout(function() {
                var e;
                this._isRunning && (e = Date.now(), this._viewableMilliseconds += t, this._normalizeFromTime(e, t), this._pendingPingRafId = this._raf.nextFrame(function() {
                    var i = new AdViewableTimeTracker.TrackingData(this._fromTime, e);
                    this._track(i), this._setFromTimeForNextPing(e, t), this._setNextPing()
                }.bind(this)))
            }.bind(this), t))
        }, AdViewableTimeTracker.prototype._normalizeFromTime = function(i, e) {
            var t = i - this._fromTime;
            e + this._maxRafLatency < t && (this._fromTime = i)
        }, AdViewableTimeTracker.prototype._setFromTimeForNextPing = function(i, e) {
            var t = Date.now();
            this._fromTime = e < t - i ? t : i
        }, AdViewableTimeTracker.prototype._getPingInterval = function() {
            var e = this._viewableMilliseconds,
                t = this._intervals,
                i = Object.keys(this._intervals),
                n = +i[i.length - 1],
                r = -1;
            return i.some(function(i) {
                return e < +i && (r = t[i], n < e + r && (r = -1), !0)
            }), r
        }, AdViewableTimeTracker.prototype._clearPendingPing = function() {
            this._raf.cancelFrame(this._pendingPingRafId), this._win.clearTimeout(this._pendingPingTimeoutId)
        }, AdViewableTimeTracker.prototype._track = function(i) {
            this._trackingCenter.track(i)
        };;
        ! function(t) {
            var i = e.prototype;

            function e() {
                this.playingVideo = !1
            }
            extend(i, EventEmitter), i.stopVideo = function() {
                this.playingVideo = !1, this.emit("videoStopped")
            }, i.startVideo = function() {
                this.playingVideo = !0, this.emit("videoStarted")
            }, t.MediaState = e
        }(window);;
        ! function(e) {
            "use strict";

            function t(e, r, t, n) {
                if (-1 === ["PUT", "DELETE"].indexOf(e)) throw new Error("Unsupported request method: " + e);
                if (void 0 === creative.runtimeParams.customAudiences[r]) throw new Error('Custom audience "' + r + '" is not used by the creative.');
                creative.runtimeParams.customAudiences[r] = {
                    userExists: t,
                    userData: n
                };
                var i, u, s, o = creative.secure ? creative.customAudiencesUrl : creative.insecureCustomAudiencesUrl,
                    c = "/audiences/" + r,
                    a = creative.userIdentifiers;
                for (i in a)
                    for (var d in a[i]) d = a[u = i][d], s = void 0, (s = new XMLHttpRequest).open(e, o + c + "/" + encodeURIComponent(u) + "/" + encodeURIComponent(d) + "?" + creative.authTokenUrlParam), s.send(n)
            }
            var r = {
                toString: function() {
                    return "[Clazz CustomAudiences]"
                }
            };
            r.addUser = function(e, r) {
                creative.runtimeParams.userOptOut || t("PUT", e, !0, r)
            }, r.removeUser = function(e) {
                creative.runtimeParams.userOptOut || t("DELETE", e, !1)
            }, e.CustomAudiences = r
        }(window);;

        function ReportingObserverWrapper(e) {
            this._reportTypes = ["intervention"], this._processReport = e, this._processReports = this._processReports.bind(this), this._emptyQueueAndProcessReports = this._emptyQueueAndProcessReports.bind(this), this._childObservers = []
        }
        ReportingObserverWrapper.prototype._processReports = function(e) {
            e.forEach(function(e) {
                e = this._getReportId(e);
                e && this._processReport(e)
            }.bind(this))
        }, ReportingObserverWrapper.prototype._getReportId = function(e) {
            var r, t, s = e.type;
            return -1 !== this._reportTypes.indexOf(s) && (r = ReportingObserverWrapper.reportValidators[s], t = e.body, r && t) ? Object.keys(r).filter(function(e) {
                e = r[e];
                return e && e(t)
            })[0] : null
        }, ReportingObserverWrapper.prototype._emptyQueueAndProcessReports = function() {
            this._childObservers.forEach(function(e) {
                e = e.takeRecords();
                this._processReports(e)
            }, this)
        }, ReportingObserverWrapper.prototype.createChildObserverAndStartObserving = function(e) {
            "ReportingObserver" in e && ((e = new e.ReportingObserver(this._processReports, {
                types: this._reportTypes,
                buffered: !0
            })).observe(), this._childObservers.push(e))
        }, ReportingObserverWrapper.prototype.start = function() {
            this._childObservers.forEach(function(e) {
                e.observe()
            }), window.addEventListener("beforeunload", this._emptyQueueAndProcessReports), window.addEventListener("unload", this._emptyQueueAndProcessReports)
        }, ReportingObserverWrapper.prototype.stop = function() {
            this._childObservers.forEach(function(e) {
                e.disconnect()
            }), window.removeEventListener("beforeunload", this._emptyQueueAndProcessReports), window.removeEventListener("unload", this._emptyQueueAndProcessReports)
        }, ReportingObserverWrapper.reportValidators = {
            intervention: {
                heavyAdCPUIntervention: function(e) {
                    return "HeavyAdIntervention" === e.id && -1 < e.message.indexOf("CPU usage") && -1 === e.message.indexOf("future version")
                },
                heavyAdNetworkIntervention: function(e) {
                    return "HeavyAdIntervention" === e.id && -1 < e.message.indexOf("network usage") && -1 === e.message.indexOf("future version")
                }
            }
        };;
        ! function() {
            var A = [".video-player-wrapper {", "    position: absolute;", "    width: 100%;", "    height: 100%;", "    background: #000;", "    top: 0;", "    left: 0;", "    overflow: hidden;", "}", ".video-player-engine {", "    position: absolute;", "    width: 100%;", "    height: 100%;", "    min-height: 100%;", "    top: 0;", "    left: 0;", "    margin: 0;", "    padding: 0;", "    overflow: hidden;", "}", ".video-player-engine video,", ".video-player-engine .canvasContainer", "{", "    position: relative;", "    width: 100%;", "    height: 100%;", "    min-height: 100%;", "    background: #000;", "    top: 0;", "    left: 0;", "}", ".video-player-poster {", "    position: absolute;", "    background-size: contain;", "    background-repeat: no-repeat no-repeat;", "    background-position: center center;", "}", ".video-player-fitting-crop .video-player-poster {", "    background-size: cover;", "}", ".video-player-engine canvas {", "    position: relative;", "    width: 100%;", "    background: #000;", "    top: 0;", "    left: 0;", "}", ".video-player-engine canvas {", "    image-rendering: optimizeSpeed;", "    image-rendering: -moz-crisp-edges;", "    -ms-interpolation-mode: nearest-neighbor;", "    image-rendering: optimize-contrast;", "    image-rendering: -webkit-pixelated;", "    image-rendering: crisp-edges;", "    image-rendering: -webkit-optimize-speed;", "    image-rendering: -webkit-optimize-contrast;", "}", ".video-player-wrapper-empty {", "    background-color: #000;", "}", ".video-player-wrapper-empty::after {", '    content: "";', "    position: absolute;", "    top: 0;", "    left: 0;", "    right: 0;", "    bottom: 0;", '    background-image: url("data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyOCAyOCIgd2lkdGg9IjI4IiBoZWlnaHQ9IjI4Ij48cGF0aCBmaWxsPSIjY2NjIiBkPSJNMSAxNGMwIDcuMiA1LjggMTMgMTMgMTNzMTMtNS44IDEzLTEzUzIxLjIgMSAxNCAxIDEgNi44IDEgMTR6bTI0IDBjMCA2LjEtNC45IDExLTExIDExUzMgMjAuMSAzIDE0IDcuOSAzIDE0IDNzMTEgNC45IDExIDExem0tMTQtNHY4bDctNC03LTR6Ii8+PC9zdmc+");', "    background-repeat: no-repeat;", "    background-position: center;", "}", ".video-controls-container {", "    position: absolute;", "    width: 100%;", "    height: 100%;", "    top: 0;", "    left: 0;", "    font-size: 14px;", "}", ".video-basic-controls-wrapper {", "    position: absolute;", "    width: 100%;", "    height: 100%;", "}", ".video-controls-slider-container {", "    position: absolute;", "    width: 100%;", "    height: 8px;", "    bottom: -1px;", "    left: 0;", "    background: #111;", "}", ".video-controls-progress-bar {", "    opacity: 0.3;", "}", ".video-controls-progress-bar,", ".video-controls-time-bar {", "    position: absolute;", "    width: 0;", "    top: 0;", "    left: 0;", "    height: 100%;", "}", "@-webkit-keyframes video-player-spin{", "    0%   { -webkit-transform: rotate(0deg); }", "    100% { -webkit-transform: rotate(359deg); }", "}", "@-moz-keyframes video-player-spin{", "    0%   { -moz-transform: rotate(0deg); }", "    100% { -moz-transform: rotate(359deg); }", "}", "@keyframes video-player-spin{", "    0%   { transform: rotate(0deg); }", "    100% { transform: rotate(359deg); }", "}", ".video-player-spinner-big,", ".video-player-spinner-small {", "    display: block;", "    position: absolute;", "    top: 50%;", "    left: 50%;", "    -webkit-animation: video-player-spin 1.5s infinite linear;", "    animation: video-player-spin 1.5s infinite linear;", "}", ".video-player-spinner-big {", "    width: 35px;", "    height: 35px;", "    margin-top: -17px;", "    margin-left: -17px;", '    background-image:url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACMAAAAjCAQAAAC00HvSAAADTklEQVR4AZWVTWgcZRyHn/c/7zubbDbJJsTEioiIH7EGq6EIRqwWqbRClXrx4E0QxYt3b4qK9qgoePDmwRYVS6xFW7Ueeqj0kqIVQYwljTVt0o/dZD9n3r/zshGSGpOd54GZ0+9h/qcxbMh03PfK4LPl8cFy0SJsialxIyfHRz+6Y6pkDR0xXWQ8azlaGvt0fF9sMsiTsazh2933fzEyKICu2+bKfP/izg96IlDA6JXmhdPVY81T6Xk2wAuCz1zFTNOh+PKD71sRMIZqY3aa59iERYtktvDrMm735LGCNQaE38/O7WGRzRBCJljrhCyB0l2HXKSgxpw7zt4xNmcBZNUSlU4XGPl4aEhRVc595fd6tvKm8LAEY4qrR0V3T87YyGDMn2d4mC6ZL9JDmEm6gBcYPhiJqlJdnt8/T7dSQ0JEhQEQ4m2Ph4jq3GdDi0N0Lw21arHSL0jP84WCZlQqV1+6Sh5ZRoix9PiiLe1TBbj0w4gnJ4v1qBeQqN8W79HQYeU78tPAIqBFWxwNFbR1mvy0iMEY+qyNvRK4SH68cQAGiaz64MTCBHndkRhngr3SbnYyM6Mz5NYah81EWnXN8D66OSKvxBrjMpH6X+ozNd4Rk1dKxgW1KbVfskpGYapAXqNRXGakdal/o1kHX95VJq/RbcYFWZLGkVY1fE+hvPT6Enmc2y5lnIk0TS8LraXjeM0ceqY6UKVrrbsvRHD+gvViqbyTtvDex4PD7w3Tre4xUzYOhyS/JUiCP3/pkPd49eVH2m+36cbqLrcdR2Rs8qutWcRiqbxRm9c0HDZywLxp2MrGE/FDxoo1VivJTEKCmSYQPXDrJ+LIQppeO3n9NZb5P+LC0+5eEm2Tar12mCvr/lPxU9veNRIy6luXK0f4kA1I9hR2Sp9PSLJIo/4lsxAwR/kX++TYWyZWT6qZrb9rp9pnkj/8dSqUon5zi73TTUhJs4QGV+qfh8h/MmAmRg7GYyGiXpPMlLbP3rS1Y6oJLU3Upxebh1lYs/yataQ9pVcH9hsJqdVMmHYS7ZAm0Ubjx9YJEtZgprmRaKz3hb5HpTeESHyIZYFOIq00f2qe4BqBTTMdrJuKJ+3t0bAUsH7FV5KFZLb9c3oWzwb8AzjvQbz9lli3AAAAAElFTkSuQmCC");', "}", ".video-player-spinner-small {", "    width: 18px;", "    height: 18px;", "    margin-top: -9px;", "    margin-left: -9px;", '    background-image:url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABIAAAASCAQAAAD8x0bcAAABeElEQVR4AXXSv2sTYRjA8e89997lcuR60eJQUdpFQQoqdHRWhAoddPBPcHRwE8TZxcXJf0BEsIjuQUQQtZMOpREM9RKL1RrqJbkf771nEGnKJf1+hmd5eKaHSe/vtzf30k63Q9X/8XZte6f8V/QrokopILq9/CB0+nnUip/l7zioUJgxrBbu6rnnodvp9G6wwaG04AEJRvAWHjXcr+3tC3pDcxhmzGcOpH7rxOm9uHfN3repIkMkwJPguljfXwVbAdOaBm3V7VD8pVE2fDpkNhJcQqXqOi+2OKrxJRxVFkXB0Ql1tKS/QZaE2fAkQGS4KbZ3xWM2+xRzpDJYz5Pw6mBxwLQ/vlq2XLMj6ZP9T7VjzYfGN1Qo97IslD9NV8j6d0e7wUrzsSwKExyv3XTOl1n2AWO1AHdt/p4Kkm/xy/xN2cMwb591Lkqj+JGs0wbrNQCy0rjjnQHdN7EZkYOJs8/ZC7pwsASgVp1L6qRVK9NiV3/RHyc/8RftSMiO8WZLaAAAAABJRU5ErkJggg==");', "}", ".video-controls-hiddable,", ".video-controls-unhiddable {", "    width: 100%;", "    height: 100%;", "}", ".video-basic-controls-wrapper .center-button {", "    position: absolute;", "    width: 4em;", "    height: 4em;", "    border-top-left-radius: 2em;", "    border-top-right-radius: 2em;", "    border-bottom-left-radius: 2em;", "    border-bottom-right-radius: 2em;", "    background: #111;", "    margin: auto;", "    top: 0;", "    left: 0;", "    bottom: 0;", "    right: 0;", "    text-align: center;", "    cursor: pointer;", "    opacity: 0.8;", "    z-index: 1;", "}", ".center-button div {", "    position: relative;", "    width: 100%;", "    color: white;", "    font-size: 2em;", "    top: 0.5em;", "}", ".center-button .celtra-icon-play { left: 0.1em;}", ".center-button .celtra-icon-replay { top: 0.62em; left:0.05em}", ".video-basic-controls-wrapper .custom-button {", "    position: absolute;", "    height: 100%;", "    width: 100%;", "    top: 0;", "    left: 0;", "    cursor: pointer;", "    opacity: 0.8;", "}", ".custom-button img {", "    margin: auto;", "    position: absolute;", "    top: 0;", "    right: 0;", "    bottom: 0;", "    left: 0;", "}", ".video-controls-button {", "    position: absolute;", "    cursor: pointer;", "    font-size: 1.5em;", "    color: white;", "    width: 2.5em;", "    height: 2.5em;", "}", ".video-controls-desktop .video-controls-button div {", "    margin-top: -0.5em;", "}", ".ie-click-event {", "    background-color: rgba(255,255,255,0);", "}", ".video-controls-button-shadow {", "    text-shadow: 1px 1px 3px #000;", "}", ".video-controls-button-top-right {", "    right: 0;", "    top: 0;", "}", ".video-controls-desktop .video-controls-button-top-right {", "    margin-right: 0.5em;", "    margin-top: 0.5em;", "    width: 1.5em;", "    height: 1.5em;", "}", ".video-controls-button-bottom-right {", "    right: 0;", "    bottom: 0;", "}", ".video-controls-desktop .video-controls-button-bottom-right {", "    margin-right: 0.5em;", "    margin-bottom: 0.75em;", "    width: 1.5em;", "    height: 1.5em;", "}", ".video-controls-button div {", "    position: absolute;", "    height: 50%;", "    width: 100%;", "    left: 0;", "    text-align: center;", "    top:  50%;", "    margin-top: -0.75em;", "    vertical-align: middle;", "}", ".video-controls-button .celtra-icon-close {", "    font-size: 0.7em;", "    left: 0.07412em;", "}", ".video-controls-button .celtra-icon-enter-full-screen,", ".video-controls-button .celtra-icon-exit-full-screen {", "    left: 0.09em;", "}", ".video-controls-pending {", "    animation: video-controls-pending 1s infinite linear;", "}", "@-webkit-keyframes video-controls-pending {", "    0%   { opacity: 1 }", "    50% { opacity: 0 }", "    100%   { opacity: 1 }", "}", "@keyframes video-controls-pending {", "    0%   { opacity: 1 }", "    50% { opacity: 0 }", "    100%   { opacity: 1 }", "}", "@font-face {", '    font-family: "celtraicons";', '    src:url("data:application/font-woff;base64,d09GRk9UVE8AAAqEAAoAAAAACjwAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAABDRkYgAAAA9AAABq8AAAavkHaHf09TLzIAAAekAAAAYAAAAGAIIvzDY21hcAAACAQAAABMAAAATBpVzGRnYXNwAAAIUAAAAAgAAAAIAAAAEGhlYWQAAAhYAAAANgAAADb/fCRYaGhlYQAACJAAAAAkAAAAJAPjAfJobXR4AAAItAAAAEgAAABIHLIB9G1heHAAAAj8AAAABgAAAAYAElAAbmFtZQAACQQAAAFdAAABXWEQuipwb3N0AAAKZAAAACAAAAAgAAMAAAEABAQAAQEBDGNlbHRyYWljb25zAAECAAEAOvgcAvgbA/gYBB4KABlT/4uLHgoAGVP/i4sMB4pq+JX4dAUdAAAA0A8dAAAA1REdAAAACR0AAAamEgATAQEMFxkbHiMoLTI3PEFGS1BVWl9kY2VsdHJhaWNvbnNjZWx0cmFpY29uc3UwdTF1MjB1RTYwMHVFNjAxdUU2MDJ1RTYwM3VFNjA0dUU2MDV1RTYwNnVFNjA3dUU2MDh1RTYwOXVFNjBBdUU2MEJ1RTYwQ3VFNjBEAAACAYkAEAASAgABAAQABwAKAA0ASwCpAPUCGAJDAs0DBQPvA/sEHgRFBG0E2wWp/JQO/JQO/JQO+5QOi/dUFYs/rUbAXAi1uwVjrnK/i8SL9eHh9YvAi7t2rmgIQ0P3VIuL91RAQAVduUuoRIv7IYv7B/sHi/shCA6L9+kVi/tP9wiK9yD7G4v4Yvsg+x4F93qoFWRkBaZ0nGqLZYtlempwdAiyZAWvrKK6i8CLwHS6Z6wI19cVZWUFuWCoTotIi0huTl1gCLFlBcO/rtaL3YvdaNVTwAgOPPdt+HQVRYtNamNXCF65i/se9x6LUcQFqra8p8SL54vWQIsuiy9AQC+LPItJwXnVCFmLBZ0m5D71i/cLi+3ti/cLi/cMKez7C4sIDvhf95UVg4uDh4WFhYWHg4uDi4KPg5GFkYWTiJOLlIuTjpGRkZGOk4uUi5OIk4WRhZGDj4KLCEveFfutiwWIi4iJiImIiIqIi4gIi/thBYuIjIiOiI6JjomOiwj3rYsFj4uOjY2Njo6MjouOCIv3YQWLjoqOiI6JjYiNh4sI++c+FYeLiYmLhwiLSwWLho2Jj4uQi42Ni5AIi8sFi4+JjYaLCIX3BxX4LYsFmYuXhpWBloGQf4t9CIv7YQWLfYZ/gIGBgX+GfYsI/C2LBX2Lf5CBlYGVhpeLmQiL92EFi5mQl5WVlZWXkJmLCPcO+zsVi3OVdZx8CJmaBX6Wg5yLnYutp6esi5yLm4SWgAh0dMiLi8hzcwV9mnaUdItei2dmi14IDvgl9/EVd5/7EfsR+xH3EXd39xH7EfsR+xGfd/cR9xH3EfsRn5/7EfcRBQ74lfgVFXiCd4Z1iKGYnKCTo3Z/dYJzh3ifb5hti1GLXFyLUYuDjIKMhDSPPrVXy4J7hXmLeAiLZp5rp3h6jHuQfZKLi4uLi4qLWK9hu4GCiYKJgYuEi4WMhIyZYbJtuYpnb116WosIg4uCjIOMum3CecaL91aL9Pc1i/cfi4+LkIuPoJqdnZmhCA73t2sVi/ef0IuV2zyLi74Fi6KRm62LCLWLi9MFhIxyjW2LTothZotGCItQRouLO9CLi/uf3osFDveznxWLlIiShJGFkoSOgouCi4OIhYSFhYiEi4KLgo6EkYSRhZOIlIuUi5KOkZGSko6Si5QI3ssVi/etBYuPio6IjYmOiIyHiwj7YYsFiIuIioiIiYmKiIuHCIv7rQWLh4yIjYmOiI6KjosI92GLBY+LjoyNjo6NjI6Ljwg+9+cVi4+JjYeLCEuLBYaLiYmLh4uHjYiQiwjLiwWPi42Oi48I9weRFYv8LQWLfYZ/gYGBgX+GfYsI+2GLBX2Lf5CBlYGVhpeLmQiL+C0Fi5mQl5WVlpWWkJmLCPdhiwWZi5eGlYGVgZB/i30IDtlqFYv4lfgI+5EFDqL4dBX3R4uL/JX7R4uL+JUF97aLFfdHi4v8lftHi4v4lQUO5/fAFVpZ91mMivdXW1s53SgpBfeY+04VjPtXvLzdOe3uOd27uwUO90v4HhW8u/tWi4v7Vbu63Drt7QX3Z/vHFYr3VVtaOt0pKdw6XFsFDvhSyhWotJy+i8KL1WzOWLoIaWkFtWSlVYtOi2F/ZXZrCGiuBZeikqWLp4u6drZrqQhoaAWjdppti2mLfYd9hn8IO9mL90kyMfsg9yFvb/h4/HmoqEjOBfw494QVi/s984r3E/sOi/ca+zL3MgUO+I/QFYuLi4uLiwj7L/cv9y/3LwWLi4uLi4uNjYyNjI2NkYqRhpAIQtQFhpCFjIWJiYqJiomJi4uLi4uLCPsv+y/7L/cvBYuLi4uLi4mNiYyJjIWNhYqGhghCQgWGhoqFjYWMiYyJjYmLi4uLi4sI9y/7L/sv+y8Fi4uLi4uLiYmKiYqJiYWMhZCGCNRCBZCGkYqRjY2MjYyNjYuLi4uLiwj3L/cv9y/7LwWLi4uLi4uNiY2KjYqRiZGMkJAI1NQFkJCMkYmRio2KjYmNCA74lBT4lBWLDAoAAAMCAAGQAAUAAAFMAWYAAABHAUwBZgAAAPUAGQCEAAAAAAAAAAAAAAAAAAAAARAAAAAAAAAAAAAAAAAAAAAAQAAA5g0B4P/g/+AB4AAgAAAAAQAAAAAAAAAAAAAAIAAAAAAAAgAAAAMAAAAUAAMAAQAAABQABAA4AAAACgAIAAIAAgABACDmDf/9//8AAAAAACDmAP/9//8AAf/jGgQAAwABAAAAAAAAAAAAAAABAAH//wAPAAEAAAABAAAXLCAzXw889QALAgAAAAAAzy5xzgAAAADPLnHO////3wIBAeAAAAAIAAIAAAAAAAAAAQAAAeD/4AAAAgD/////AgEAAQAAAAAAAAAAAAAAAAAAABIAAAAAAAAAAAAAAAABAAAAAgAAAAIAAAABsQAAAgD//wIAAG8CAAAAAgAAiwIAAGUCAABOAgAAFwIAAAoCAAAmAgAAAAIAAAEAAFAAABIAAAAAAA4ArgABAAAAAAABABYAAAABAAAAAAACAA4AYwABAAAAAAADABYALAABAAAAAAAEABYAcQABAAAAAAAFABYAFgABAAAAAAAGAAsAQgABAAAAAAAKACgAhwADAAEECQABABYAAAADAAEECQACAA4AYwADAAEECQADABYALAADAAEECQAEABYAcQADAAEECQAFABYAFgADAAEECQAGABYATQADAAEECQAKACgAhwBjAGUAbAB0AHIAYQBpAGMAbwBuAHMAVgBlAHIAcwBpAG8AbgAgADEALgAwAGMAZQBsAHQAcgBhAGkAYwBvAG4Ac2NlbHRyYWljb25zAGMAZQBsAHQAcgBhAGkAYwBvAG4AcwBSAGUAZwB1AGwAYQByAGMAZQBsAHQAcgBhAGkAYwBvAG4AcwBHAGUAbgBlAHIAYQB0AGUAZAAgAGIAeQAgAEkAYwBvAE0AbwBvAG4AAAAAAwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=="),', '        url("data:font/truetype;base64,AAEAAAALAIAAAwAwT1MvMggi/MMAAAC8AAAAYGNtYXAaVcxkAAABHAAAAExnYXNwAAAAEAAAAWgAAAAIZ2x5ZtKQLWwAAAFwAAAJfGhlYWT/fCRYAAAK7AAAADZoaGVhA+MB8gAACyQAAAAkaG10eByyAfQAAAtIAAAASGxvY2EUzhFSAAALkAAAACZtYXhwABgAwAAAC7gAAAAgbmFtZWEQuioAAAvYAAABXXBvc3QAAwAAAAANOAAAACAAAwIAAZAABQAAAUwBZgAAAEcBTAFmAAAA9QAZAIQAAAAAAAAAAAAAAAAAAAABEAAAAAAAAAAAAAAAAAAAAABAAADmDQHg/+D/4AHgACAAAAABAAAAAAAAAAAAAAAgAAAAAAACAAAAAwAAABQAAwABAAAAFAAEADgAAAAKAAgAAgACAAEAIOYN//3//wAAAAAAIOYA//3//wAB/+MaBAADAAEAAAAAAAAAAAAAAAEAAf//AA8AAQAAAAAAAAAAAAIAADc5AQAAAAABAAAAAAAAAAAAAgAANzkBAAAAAAEAAAAAAAAAAAACAAA3OQEAAAAAAQAAAAACAAHAACMAADcUHgIXNy4DNTQ+AjMyHgIXBzM1By4DIyIOAhUADBcgFCoPGBEJHjRGKBQmIh8NSMBLESkuMhs1XUYowB02MSoSMA0gJSgWKEY0HggOFQ1IwEsRHBQKKEZdNQADAAAAEgIBAeAABQAcADMAABMVHwERBzcHHgMVFA4CBxc+AzU0LgInNwceAxUUDgIHFz4DNTQuAicAdIyM5icKEQsGBgsRCicNFhAICBAWDUwmERwTCwsTHBEmFSEYDQ0YIRUBVbsBhwHOih0nCBUYGw4OGxgVCCcNHSIlFBQlIh0NTCYQJyswGRkwKycQJhQvNTofHzo1LxQAAAABAAAALgGyAeAALQAAEyIOAgcnFTMnPgMzMh4CFRQOAiMiLgInIx4DMzI+AjU0LgIj2RoxLCYPLYo6DB0jKBUiPS4aGi49Ih41Kx4HMgcmOUcoLU86IyM6Ty0B4AwWHxQuijkQGhMKGy09IyI9LhoTIi8cJkEvHCM6Ty0tTzsiAAX//wBHAf8BegAoAFUAbACZAL0AAAEiDgIHDgMVFB4CFx4DMzI+Ajc+AzU0LgInLgMjJyEiDgIHDgMdARQeAhceAzMhMj4CNT4CND0BPAEuASc0LgIjBSIOAh0BFB4CMzI+Aj0BNC4CIychMh4CFx4DHQEUDgIHDgMjISIuAicuAz0BND4CNz4DMxcUHgIXNy4DNTQ+AjMyHgIXBzM1By4DIyIOAhUBywMGBgUCAgQCAgICBAICBQYGAwQGBgUCAgQCAQECBAICBQYGBED+5wEDAgIBAQEBAQEBAQEBAgIDAQEZAgICAwECAQECAQMCAgL+rQEDAQEBAQMBAgICAQECAgIGAZkGCQkJAwQGBAICBAYEAwkJCQb+ZwUKCQgEBAUEAgIEBQQECAkKBXoDCAoGDgUIBQMKEBcMBwwLCgQXPRgFDQ8QCREdFwwBAQICAwMCBQYGAwQGBgUCAgQCAQECBAICBQYGBAMGBgUCAwMCAlMBAQEBAQICAwHNAQMCAgEBAQEBAQEBAQECAgMBzQEDAgIBAQEBAU0BAQMBQAICAgEBAgICQAEDAQFzAgQFBAQICQoFzQUKCQgEBAUEAgIEBQQECAkKBc0FCgkIBAQFBAKnCREQDgUPBAsLDQcNFhEKAwUGBBc9GAYJBgMNFh4RAAAAAQBvAE8BkQFxAAsAAAEnBycHFwcXNxc3JwGRFH19FH19FH19FH0BXRR9fRR9fRR9fRR9AAEAAAARAgEBsgBpAAABDgMjPgM3DgMjLgMHJg4CFRwBHgEVLgMnDgMVFB4CFwYuAic0FDQUNRQeAhciBiIGJwYmBiYHHgM3DgMHJgYmBiceAxc+AzUwPAI1PgM3AgEHDw8QCAgPCwoDCBAREQkHEhMWCxYmHRABASA+NzATBAUEAgcMEgoGDQwLBQwXHxIDBwcHBAIFBQUDBRQZHxIOHiEjEwMGBwYDESYpKxZJb0wnCA4NDAYBgQMGAwQEDQ4SCAMJBgYGDgcGAQESGycVBAUHBQQCEB4mGQcMDg0IDRoWFAYBAwMFAgEBAQIBFCEdEQUCAgEBAgECAQ8bEg0BCxANBQEBAQIBAg0QDQYBATZXaTYFAgQBBwsPDgkAAQCL/+ABdQHgABwAAAURMzcjNTQ+AjsBNSIuAiMiDgIdASMVMxEzASNFCk8DCQ8NKgMLERQLFyYbD0VFUyABC1AzCQ4LBUgBAQEOGycaO1D+9QAABABl/+EBmAHgACgAVQBsAJkAACU0LgInLgMjIg4CBw4DFRQeAhceAzMyPgI3PgM1NxE0LgInLgIiKwEqAQ4BBw4CFBURHAEeARceAjI7AToBPgE3PgM1AzQuAisBIg4CFRQeAjsBMj4CNTcRFA4CBw4DKwEiLgInLgM1ETQ+Ajc+AzsBMh4CFx4DFQEfAQMDAwIFBgYDBAYFBgICBAIBAQIEAgIGBQYEAwYGBQIDAwMBUwEBAQEBAgIDAc0BAgMCAQEBAQEBAQECAwIBzQEDAgIBAQEBAU0BAQMBQAICAgEBAgICQAEDAQFzAgMGBAQICQoFzQUKCQgEAwYEAgIEBgMECAkKBc0FCgkIBAQGAwIUAwYGBQIDAwMBAQMDAwIFBgYDAwcFBQMCBAIBAQIEAgMFBQcDQAEZAgICAgEBAgEBAgEBAgICAv7nAQMCAgEBAgEBAgEBAgIDAQFTAQMBAQEBAwECAgIBAQICAgb+ZwUKCQgEBAYDAgIDBgQECAkKBQGZBQoJCAQEBgQBAQQGBAQICQoFAAAAAQBO/98BwgHgAAIAABcRBU4BdCECAf0AAgAX/98B7AHgAAQACQAAEzMTIxMhMwMjAxeyAbQBASG0AbIBAeD9/wIB/f8CAQAAAgAK/+AB9AHgAAYADQAAEwczJwcnBwUXNxc3JzdcMcUBMFJjAQQBMVJiUjABLDHEMVNjucQyU2RRMQAAAgAmAAUB3AG6AAYADQAAEzcHFTcXNxMnBycHFwe3McIwUWLTATBRYlEvAYoxAcAuUGH+zsAwUWFSLwAAAgAA/98CAQHgADMAOQAAJT4DNTQuAicHHgMVFA4CByc+AzU0LgInBx4DFRQOAgcnNQcnBwE3JyUVHwE1JwG+CxEMBgsVHxMiDxoRCgUIDAgjBAcFAwcOFAwjCQ4LBQEDAwJQWYwcAeQdQ/5caH+ePxAiJiYWGzUvLBAhECEoKxcPHxsbCyIKERQTDBAjHhsLIgkSFxcNBQsJCgRNtluOHf4cHETvqAJ5hZ8AAAEAAf/hAf8B3wCEAAAlOAMxJzc4AzE+AzU2NC4BLwEuAiIHIg4CBzgDMQcnOAMxLgMjJiIOAQ8BDgIUFxQeAhc4AzEXBzgDMQ4DFQYUHgEfAR4CMjcyPgI3OAMxNxc4AzEeAzMWMj4BPwE+AjQnNC4CJwH7m5sBAQEBAQECAkkCBAQFAgEBAgEBm5sBAQIBAQIFBAQCSQICAQEBAQEBm5sBAQEBAQECAkkCBAQFAgEBAgEBm5sBAQIBAQIFBAQCSQICAQEBAQEBRZubAQECAQECBQQEAkkCAgEBAQEBAZubAQEBAQEBAgJJAgQEBQIBAQIBAZubAQECAQECBQQEAkkCAgEBAQEBAZubAQEBAQEBAgJJAgQEBQIBAQIBAQAAAAEAAAABAAAoDgB8Xw889QALAgAAAAAAzy5xzgAAAADPLnHO////3wIBAeAAAAAIAAIAAAAAAAAAAQAAAeD/4AAAAgD/////AgEAAQAAAAAAAAAAAAAAAAAAABIAAAAAAAAAAAAAAAABAAAAAgAAAAIAAAABsQAAAgD//wIAAG8CAAAAAgAAiwIAAGUCAABOAgAAFwIAAAoCAAAmAgAAAAIAAAEAAAAAAAoAFAAeAFIAoADgAdoB9AKCAqwDeAOEA5wDugPYBC4EvgAAAAEAAAASAL4ABQAAAAAAAgAAAAAAAAAAAAAAAAAAAAAAAAAOAK4AAQAAAAAAAQAWAAAAAQAAAAAAAgAOAGMAAQAAAAAAAwAWACwAAQAAAAAABAAWAHEAAQAAAAAABQAWABYAAQAAAAAABgALAEIAAQAAAAAACgAoAIcAAwABBAkAAQAWAAAAAwABBAkAAgAOAGMAAwABBAkAAwAWACwAAwABBAkABAAWAHEAAwABBAkABQAWABYAAwABBAkABgAWAE0AAwABBAkACgAoAIcAYwBlAGwAdAByAGEAaQBjAG8AbgBzAFYAZQByAHMAaQBvAG4AIAAxAC4AMABjAGUAbAB0AHIAYQBpAGMAbwBuAHNjZWx0cmFpY29ucwBjAGUAbAB0AHIAYQBpAGMAbwBuAHMAUgBlAGcAdQBsAGEAcgBjAGUAbAB0AHIAYQBpAGMAbwBuAHMARwBlAG4AZQByAGEAdABlAGQAIABiAHkAIABJAGMAbwBNAG8AbwBuAAAAAAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=");', "    font-weight: normal;", "    font-style: normal;", "}", '[class^="celtra-icon-"], [class*=" celtra-icon-"] {', '    font-family: "celtraicons";', "    speak: none;", "    font-style: normal;", "    font-weight: normal;", "    font-variant: normal;", "    text-transform: none;", "    line-height: 1;", "    -webkit-font-smoothing: antialiased;", "    -moz-osx-font-smoothing: grayscale;", "}", '.celtra-icon-redo:before { content: "\\e600"; }', '.celtra-icon-close:before { content: "\\e60d"; }', '.celtra-icon-go-landscape:before { content: "\\e603"; }', '.celtra-icon-twitter:before { content: "\\e605"; }', '.celtra-icon-facebook:before { content: "\\e606"; }', '.celtra-icon-phone:before { content: "\\e607"; }', '.celtra-icon-play:before { content: "\\e608"; }', '.celtra-icon-pause:before { content: "\\e609"; }', '.celtra-icon-exit-full-screen:before { content: "\\e60a"; }', '.celtra-icon-enter-full-screen:before { content: "\\e60b"; }', '.celtra-icon-unmute:before { content: "\\e60c"; }', '.celtra-icon-mute:before { content: "\\e601"; }', '.celtra-icon-replay:before { content: "\\e602"; }', '.celtra-icon-close2:before { content: "\\e604"; }', ".spinner .center-button {", "    display: none;", "}", ".web-audio .celtra-icon-mute, .web-audio .celtra-icon-unmute { color: red; }", ""].join("\n");
            this.VideoPlayerCss = A
        }();;
        ! function() {
            var o = {};
            new Image;
            o.ZINDEX_MAX = 2147483647, o.LANDSCAPE = 1, o.PORTRAIT = 2, o.ENDED = 0, o.PLAYING = 1, o.PAUSED = 2, o.BUFFERING = 3, o.VIDEO_CUED = 5, o.UNSTARTED = 6, o.SEEKING = 7, o.STATE = {
                0: "ENDED",
                1: "PLAYING",
                2: "PAUSED",
                3: "BUFFERING",
                5: "VIDEO_CUED",
                6: "UNSTARTED",
                7: "SEEKING"
            }, o.TRUTHY_REGEX = /^(yes|true|1)$/i, o.isTruthy = function(t) {
                return o.TRUTHY_REGEX.test(t)
            }, o.isTouchDevice = !!("ontouchstart" in window), o.validFor = function(t, e, n) {
                var i = Date.now() + e;
                return function() {
                    Date.now() <= i && t.apply(n || null, arguments)
                }
            }, o.exists = function(t, e) {
                return -1 !== t.indexOf(e)
            }, o.lastCall = 0, o.deduplicate = function(t, e, n) {
                return function() {
                    Date.now() - o.lastCall > e && (t.apply(n, arguments), o.lastCall = Date.now())
                }
            }, o.isFunction = function(t) {
                return "function" == typeof t
            }, o.constantly = function(t) {
                return function() {
                    return t
                }
            }, o.hash = function() {
                for (var t = Array.prototype.slice.apply(arguments), e = 0, n = 0; n < t.length; n += 1) e += 1e3 * t[n] + e << 1;
                return e
            }, o.curry = Function.prototype.curry || function() {
                var t = this,
                    e = Array.prototype.slice.call(arguments);
                return function() {
                    return t.apply(this, e.concat(Array.prototype.slice.call(arguments)))
                }
            }, o.offset = function(t, e) {
                var t = t.getBoundingClientRect() || {
                        top: 0,
                        left: 0
                    },
                    e = e || document,
                    n = e.documentElement,
                    e = e.defaultView;
                return {
                    top: t.top + (e.pageYOffset || n.scrollTop) - (n.clientTop || 0),
                    left: t.left + (e.pageXOffset || n.scrollLeft) - (n.clientLeft || 0)
                }
            }, o.attachHandlers = function(e, n, t) {
                for (var i in t) {
                    var r = isArray(t[i]) ? t[i] : [t[i]];
                    r.forEach(function(t) {
                        t = e[t];
                        t && n.on(i, t.bind(e))
                    })
                }
            }, o.idempotented = function(e, n) {
                var i = !1;
                if (o.isFunction(e)) return function() {
                    var t;
                    i || (t = Array.prototype.slice.call(arguments), i = !0, e.call(n || window, t))
                };
                throw "idempotented called with no function."
            }, o.delayed = function(e, n, i) {
                var r = null;
                return function() {
                    var t = arguments;
                    clearTimeout(r), r = setTimeout(function() {
                        e.apply(i, t)
                    }, n)
                }
            }, o.swapClass = function(t, e, n) {
                hasClass(t, e) ? (removeClass(t, e), addClass(t, n)) : (removeClass(t, n), addClass(t, e))
            }, o.memoize = function(r) {
                return function() {
                    var t, e = Array.prototype.slice.call(arguments),
                        n = "",
                        i = e.length;
                    for (r.memoize = r.memoize || {}; i--;) n += (t = e[i]) === Object(t) ? JSON.stringify(t) : t, r.memoize || (r.memoize = {});
                    return n in r.memoize ? r.memoize[n] : r.memoize[n] = r.apply(this, e)
                }
            }, o.toMMSS = function(t) {
                var e = Math.floor(t / 36e5),
                    n = Math.floor((t - 36e5 * e) / 60),
                    t = Math.round(t - 36e5 * e - 60 * n);
                return (n = n < 10 ? "0" + n : n) + ":" + (t = t < 10 ? "0" + t : t)
            }, o.fitComponent = function(t, e, n, i, r) {
                var o, a, l, c = 0 < 1 - n / i * (e / t),
                    r = (!!r ? !c : c) ? (o = n, l = 0, i - (a = Math.ceil(e * (n / t)))) : (a = i, l = n - (o = Math.ceil(t * (i / e))), 0);
                return {
                    width: Math.ceil(o),
                    height: Math.ceil(a),
                    marginHorizontal: l >> 1,
                    marginVertical: r >> 1
                }
            }, o.removeUnits = function(t) {
                return parseInt(t.replace(/[a-z]+/, ""))
            }, o.capitaliseFirstLetter = function(t) {
                return t.charAt(0).toUpperCase() + t.slice(1)
            }, o.createHandlerName = function(t, e) {
                return (e = e || "on") + o.capitaliseFirstLetter(t)
            }, o.forEach = function(t, e, n) {
                for (var i in t) e.call(n, t[i], i, t)
            }, o.changeStyle = function(t, e, n) {
                for (var i, r = 0; r < n.length; r += 1)(i = n[r]) && (i.style[t] = e)
            }, o.show = function() {
                o.changeStyle("display", "", arguments)
            }, o.hide = function() {
                o.changeStyle("display", "none", arguments)
            }, o.showCursor = function(t) {
                o.changeStyle("cursor", "", t)
            }, o.hideCursor = function(t) {
                o.changeStyle("cursor", "none", t)
            }, o.removeElements = function() {
                for (var t = 0; t < arguments.length; t += 1) {
                    var e = arguments[t];
                    e && e.parentNode && e.parentNode.removeChild(e)
                }
            }, o.removeChildren = function(t) {
                for (; t.firstChild;) t.removeChild(t.firstChild)
            }, o.isNode = function(t) {
                return "object" == typeof Node ? t instanceof Node : t && "object" == typeof t && "number" == typeof t.nodeType && "string" == typeof t.nodeName
            }, o.urlSerialize = function(t) {
                var e, n = [];
                for (e in t) n.push(encodeURIComponent(e) + "=" + encodeURIComponent(t[e]));
                return n.join("&")
            }, o.composeUrl = function() {
                for (var t = [], e = null, n = !1, i = 0; i < arguments.length; i += 1) {
                    var r = arguments[i] || "";
                    if ("object" == typeof r) {
                        e = o.urlSerialize(r);
                        break
                    }
                    n = 0 < (r = "function" == typeof r ? r() || "" : r).length && "/" == r[r.length - 1], r = (0 === i ? r : r.replace(/^\//, "")).replace(/\/$/, "");
                    r && t.push(r)
                }
                return t.join("/") + (n ? "/" : "") + (e ? "?" + e : "")
            }, o.xBindFactory = function(n) {
                return function(t) {
                    var e = t.getAttribute("x-bind");
                    e && (n[e] = t).removeAttribute("x-bind")
                }
            }, o.createDom = function(t, e, n, r) {
                t = t.createElement("div"), r = r || noop;
                return t.innerHTML = tmpl("string" == typeof e ? e : e.join(""), n || {}),
                    function t(e) {
                        for (var n = e.children || [], i = 0; i < n.length; i += 1) t(n[i]);
                        return r(e), e
                    }(t).children[0]
            }, o.createStyleTag = function(t, e) {
                t = t.createElement("link");
                return t.setAttribute("rel", "stylesheet"), t.setAttribute("href", e), t
            }, o.insertStyleTag = function(t, e, n) {
                var i = n.getElementsByTagName("head")[0];
                n.getElementById(t) || ((n = document.createElement("style")).setAttribute("id", t), n.innerHTML = e, i.appendChild(n))
            }, o.createSourceTag = function(t, e) {
                t = t.createElement("source");
                return t.setAttribute("src", e), t
            }, o.hasValue = function(t) {
                return null != t && NaN !== t && "" !== t
            }, o.filterObject = function(t, e) {
                var n, i = {};
                for (n in t) t.hasOwnProperty(n) && (e && e(t[n]) || t[n]) && (i[n] = t[n]);
                return i
            }, o.complement = function(t) {
                return function() {
                    return !t.apply(this, arguments)
                }
            }, o.EMPTY_PIXEL = "R0lGODlhAQABAPAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==", (this.CeltraPlayerUtils = o).forEach = function(t, e, n) {
                if (t.forEach) t.forEach(e, n);
                else
                    for (var i in t) {
                        var r = t[i];
                        e.call(n, i, r)
                    }
            }, o.isTouchDevice = !!("ontouchstart" in window), o.isTopNode = function(t) {
                return "BODY" === t.tagName || "viewport" === t.id || "celtra-modal" === t.id
            }, o.orientationLockHandler = function(t, e) {
                var n = t.getControlsElement(),
                    i = t.getWrapperElement();
                i && (e ? (t.isState(CeltraPlayerUtils.PLAYING) ? t.__playAfterRotate = !0 : t.__playAfterRotate = !1, t.pause(), n && (n.style.display = "none"), i.style.zIndex = -1) : (n && (n.style.display = ""), t.__playAfterRotate && t.play(), i.style.zIndex = CeltraPlayerUtils.ZINDEX_MAX))
            }, o.construct = function(t, e) {
                function n() {
                    return t.apply(this, e)
                }
                return n.prototype = t.prototype, new n
            }, o.getUrl = function(t) {
                var t = (!!creative.runtimeParams.secure ? t : "insecure" + o.capitaliseFirstLetter(t)) + "Url",
                    e = ("undefined" != typeof creative ? creative : urls)[t];
                if (e) return e;
                throw "Undefined url key " + t
            }, o.ifDefined = function(t, e) {
                return void 0 !== t ? t : e
            }, o.base64ToArrayBuffer = function(t) {
                for (var e = window.atob(t), n = e.length, i = new Uint8Array(n), r = 0; r < n; r++) {
                    var o = e.charCodeAt(r);
                    i[r] = o
                }
                return i.buffer
            }, o.createSourceObject = function(t, e) {
                return {
                    url: t,
                    mediaType: e
                }
            }, o.setMediaElementSource = function(n, i, t) {
                var r;
                1 === t.length ? i.src = t[0].url : (r = [].slice.apply(i.childNodes || []).map(function(t) {
                    return t.src
                }), t.forEach(function(t) {
                    var e; - 1 < r.indexOf(t.url) || ((e = n.createElement("source")).src = t.url, e.type = o.generateMediaType(t.mediaType), i.appendChild(e))
                }))
            }, o.generateMediaType = function(t) {
                if (!t || !t.mime) return "";
                if (creative.runtimeParams.featureFlags.iOS17InlineVideoFix && deviceInfo.os.ios("17", null)) return t.mime;
                var e = [(e = t.codecs || {}).video, e.audio].filter(function(t) {
                    return !!t
                }).join(", ");
                return t.mime + (e ? '; codecs="' + e + '"' : "")
            }
        }(),
        function() {
            this.StatefulEventEmitter = {
                changeState: function(t) {
                    var e = this._state;
                    this._state != t && (this._state = t, this.emit("statechange", t, e))
                },
                getState: function() {
                    return this._state
                },
                isState: function() {
                    return -1 !== Array.prototype.slice.apply(arguments).indexOf(this._state)
                }
            }, extend(this.StatefulEventEmitter, EventEmitter)
        }(),
        function() {
            var r = -1 !== navigator.userAgent.indexOf("Safari") && -1 === navigator.userAgent.indexOf("CriOS");

            function t(t, e, n) {
                this.intervalId = null, this.containerEl = null, this.externalResize = noop, this.lastWidth = 0, this.lastHeight = 0, this.lastScrollX = 0, this.lastScrollY = 0, this.doc = t.ownerDocument, this.win = "defaultView" in this.doc ? this.doc.defaultView : this.doc.parentWindow, this.initialHostOffset = {
                    left: this.win.pageXOffset || document.documentElement.scrollLeft,
                    top: this.win.pageYOffset || document.documentElement.scrollTop
                }, this.containerEl = t;
                var i = this.containerEl.style;
                i.position = n ? "fixed" : "absolute", i.zIndex = CeltraPlayerUtils.ZINDEX_MAX - 1, i.width = "100%", i.height = "100%", i.left = "0px", i.top = "0px", e || (i.background = "#000"), this.containerEl.addEventListener("touchmove", o), this.containerEl.addEventListener("touchstart", o), this.win.document.body.appendChild(this.containerEl), this.resize = function() {
                    var t = this.containerEl.style;
                    t.width = this.lastWidth + "px", t.height = this.lastHeight + "px", t.zIndex = CeltraPlayerUtils.ZINDEX_MAX, this.emit("resize", {
                        width: this.lastWidth,
                        height: this.lastHeight,
                        scrollX: this.lastScrollX,
                        scrollY: this.lastScrollY
                    })
                }.bind(this), this.tick = function() {
                    window === ("undefined" == typeof adapter ? creative.adapter : adapter).getTopWindow() && (this.containerEl.style.width = "100%", this.containerEl.style.height = "100%");
                    var t = Math.max(this.win.document.documentElement.clientWidth, this.win.innerWidth || 0),
                        e = Math.max(this.win.document.documentElement.clientHeight, this.win.innerHeight || 0);
                    (this.lastWidth !== t || this.lastHeight !== e || ios("7") && r) && (this.lastWidth = t, this.lastHeight = e, defer(this.resize))
                }.bind(this), this.intervalId = setInterval(this.tick, 60), new Tapper(t)
            }

            function o(t) {
                var e = null != typeof adapter ? adapter : creative.adapter;
                "touchstart" == t.type && e.useNativeClickForTapDetection || t.preventDefault(), t.stopPropagation()
            }
            extend(t.prototype, EventEmitter), t.prototype.getElement = function() {
                return this.containerEl
            }, t.prototype.destroy = function() {
                this.containerEl && (this.containerEl.removeEventListener("touchmove", o), this.containerEl.removeEventListener("touchstart", o), clearInterval(this.intervalId), this.containerEl.innerHTML = "")
            }, window.FullScreenHandler = t
        }();;
        ! function() {
            function t(e) {
                switch (e) {
                    case "video/mp4":
                        return "video.mp4";
                    case "video/webm":
                        return "video.webm";
                    case "video/avi":
                        return "video.avi";
                    case "audio/mp4":
                        return "audio.m4v";
                    default:
                        throw new Error("Unknown mime type")
                }
            }
            var n = {
                    startMuted: !1,
                    preload: !1,
                    from: null,
                    to: null,
                    blobHash: null,
                    hasAudio: !0,
                    skipCodecs: [],
                    codecPriority: ["vp9", "h265", "h264", "vp8"],
                    transcodingGroup: "inline",
                    customUrlParameters: {},
                    forceMPEG4Video: !1,
                    forceMPEG1Video: !1
                },
                a = CeltraPlayerUtils.composeUrl,
                c = CeltraPlayerUtils.filterObject,
                s = CeltraPlayerUtils.getUrl,
                u = (createSourceObject = CeltraPlayerUtils.createSourceObject, generateMediaType = CeltraPlayerUtils.generateMediaType, VideoEngineProvider = {}, {
                    vp9: {
                        low: ["vp9_served_480p"],
                        medium: ["vp9_served_720p"],
                        high: ["vp9_served_1080p"]
                    },
                    h265: {
                        low: ["x265_served_480p"],
                        medium: ["x265_served_1080p"],
                        high: ["x265_served_1080p"]
                    },
                    h264: {
                        low: ["mpeg4HQ"],
                        medium: ["mpeg4HQPlus"],
                        high: ["mpeg4HD"]
                    },
                    vp8: {
                        low: ["webmHQ"],
                        medium: ["webmHQ"],
                        high: ["webmHD"]
                    }
                }),
                l = function(e, r) {
                    return !!{
                        h265: deviceInfo.browser.chrome(),
                        vp9: deviceInfo.os.android(null, "7.1") || r.isVideo360 && deviceInfo.browser.firefox() || deviceInfo.browser.safari() && deviceInfo.deviceType.desktop()
                    }[e]
                },
                d = function() {
                    return deviceInfo.deviceType.desktop() || deviceInfo.os.ios("10", null) && !deviceInfo.browser.chrome(null, "64") || deviceInfo.os.android() && deviceInfo.browser.chrome("53", null) && !deviceInfo.browser.samsung() || deviceInfo.os.android() && deviceInfo.browser.samsung("6.4", null)
                },
                v = (AudioEngineProvider = {}, function(e) {
                    return "undefined" != typeof creative ? (e = creative.videoTranscodingPresets[e] && creative.videoTranscodingPresets[e].algoVersion, creative.version + (e ? "-" + e : "")) : null
                });
            VideoEngineProvider.createVideoEngineSpec = function(e, r) {
                var r = merge(n, r),
                    i = (r.skipCodecs = r.skipCodecs.filter(function(e) {
                        return -1 === ["h264", "vp8"].indexOf(e)
                    }), "1" == r.campaignExplorer && (r.startMuted = !0), o = e, (i = r).forceMPEG4Video || !i.forceMPEG1Video && !i.fallbackToMPEG1 && (d() || o) ? "html5" : "jsVideo"),
                    o = {
                        inUserInitiatedThread: e,
                        startMuted: r.videoElement ? r.videoElement.muted : r.startMuted
                    };
                return "jsVideo" == i ? merge({
                    clazz: "JsVideoEngine",
                    presets: [(i = r).fallbackToMPEG1 ? "mpeg1LQVideo" : "mpeg1SHQVideo"],
                    engineType: "mpeg1",
                    doAVSync: i.hasAudio && !i.isTeaser,
                    isTeaser: i.isTeaser,
                    preload: i.preload,
                    options: {}
                }, o) : merge(function(r, e) {
                    var i = "";
                    if (r.isMasterVideo)
                        if (deviceInfo.deviceType.desktop() && !r.capStreamAtHQPlus) i = "high";
                        else i = "medium";
                    else if ((deviceInfo.deviceType.desktop() || r.isVideo360 || r.hqQuality || r.isSnapchatFormat) && !r.isTeaser) i = "medium";
                    else i = "low";
                    var o = r.codecPriority.filter(function(e) {
                        return r.skipCodecs.indexOf(e) === -1 && !l(e, r)
                    }).map(function(e) {
                        return u[e][i]
                    }).reduce(function(e, r) {
                        return e.concat(r)
                    }, []).filter(function(e) {
                        return creative.videoTranscodingGroups[r.transcodingGroup].indexOf(e) !== -1
                    });
                    if (r.videoElement) {
                        var n = document.createElement("video");
                        var d = ["", "maybe", "probably"];
                        var t = o.map(function(e) {
                            var r = creative.videoTranscodingPresets[e].mediaType;
                            var i = deviceInfo.browser.chrome() && e.indexOf("mpeg4") === 0 ? "probably" : n.canPlayType(generateMediaType(r));
                            return {
                                id: e,
                                canPlayType: i
                            }
                        }).sort(function(e, r) {
                            if (e.canPlayType === r.canPlayType) return 0;
                            return d.indexOf(e.canPlayType) > d.indexOf(r.canPlayType) ? -1 : 1
                        }).shift().id;
                        o = [t]
                    }
                    var a = !deviceInfo.deviceType.desktop() && r.isVideo360 && e && !r.startMuted,
                        c;
                    return {
                        clazz: deviceInfo.os.ios(null, "10") && r.isVideo360 ? "CrossOriginHtml5VideoEngine" : "Html5VideoEngine",
                        presets: o,
                        engineType: "html5",
                        doAVSync: a,
                        isTeaser: r.isTeaser,
                        preload: r.preload,
                        crossOrigin: true,
                        options: {}
                    }
                }(r, e), o)
            }, VideoEngineProvider.createVideoSourceObjects = function(e, n) {
                var d = e.doAVSync || e.isTeaser;
                return e.presets.map(function(e) {
                    r = n;
                    var r = c(merge({
                            from: r.from,
                            to: r.to,
                            muted: d ? "1" : null,
                            transform: "VideoStream",
                            celtraCacheBust: v(e)
                        }, r.customUrlParameters)),
                        i = creative.videoTranscodingPresets[e].mediaType,
                        o = t(i.mime);
                    return createSourceObject(a(s("cachedApi"), "videoStream", n.blobHash, e, o, r), i)
                })
            }, window.VideoEngineProvider = VideoEngineProvider, AudioEngineProvider.createAudioEngineSpec = function(e) {
                return e.doAVSync ? {
                    clazz: "undefined" != typeof AudioContext || "undefined" != typeof webkitAudioContext ? "WebAudioEngine" : "Html5AudioEngine",
                    preload: e.preload
                } : null
            }, AudioEngineProvider.createAudioSourceObjects = function(n) {
                return ["aacAudio"].map(function(e) {
                    var r = n ? (r = n, c(merge({
                            from: r.from,
                            to: r.to,
                            transfom: "VideoStream",
                            celtraCacheBust: v(e)
                        }, r.customUrlParameters))) : {},
                        i = creative.videoTranscodingPresets[e].mediaType,
                        o = t(i.mime);
                    return createSourceObject(a(s("cachedApi"), "videoStream", n.blobHash, e, o, r), i)
                })
            }, window.AudioEngineProvider = AudioEngineProvider
        }();;
        ! function() {
            function i() {}
            var a, t, n = {
                    fsvp: {
                        attachment: "body",
                        positioning: "screen",
                        type: "layer"
                    }
                },
                l = {},
                s = null,
                d = !1,
                c = null;

            function o() {
                videoEventsAdapter = null, d = !1, a = s = null
            }

            function f() {
                videoEventsAdapter && (videoEventsAdapter.destroy(), videoEventsAdapter = null), s && (s.destroy(), a.destroy(), o())
            }
            i.init = function(e) {
                o(), l = merge({
                    hideControls: !1,
                    controlsHidden: !1,
                    baseUrl: urls.staticUrl + "runner/FullscreenVideoPlayer/",
                    muteUnmuteEnabled: !((ios("9") || ios("8")) && tablet())
                }, e || {}), adapter.createPlacements(n, function() {
                    c = adapter.placements.fsvp
                })
            }, i.play = function(e, t, n) {
                adapter._stopAllMedia("toPlayVideo"), f();
                d = !1, a = new FullScreenHandler(c.getContainer(), !1, android("4", "5") && !l.forceAbsolutePositioning), 0;
                try {
                    var o = parseFloat(t.file.meta.duration)
                } catch (e) {
                    o = null
                }
                t.file.meta && (l.muteUnmuteEnabled = l.muteUnmuteEnabled && t.file.meta.hasAudio);

                function r(e) {
                    return new BasicVideoControls(e)
                }
                o = {
                    autoplay: !1,
                    baseUrl: l.baseUrl,
                    closeFSOnEnd: !0,
                    fullscreenMode: "permanent",
                    autohideTime: 3,
                    duration: o
                }, t.file.meta && (o.videoWidth = t.file.meta.width, o.videoHeight = t.file.meta.height), o = (s = new VideoPlayer(a.getElement(), function() {
                    return {
                        clazz: "Html5VideoEngine",
                        engineType: "html5",
                        videoUrl: t.file.getUrl(),
                        doAVSync: !1,
                        options: {}
                    }
                }, function(e, t) {
                    e = new BasicVideoControlsController(e, r, extend(l, t));
                    return e.on("playButtonPressed", function() {
                        s.play()
                    }.bind(this)), e
                }, function() {
                    return !0
                }, o)).createSpecs(!0, !1);
                s.initialize(o), i.attachListeners(n), videoEventsAdapter = new VideoEventsAdapter(s), e.registerSource(videoEventsAdapter), s.on("userInteraction", function() {
                    e.trackUserInteraction()
                }), c.setPosition(0, 0), c.show(), s.play(), s.redraw()
            }, i.attachListeners = function(e) {
                t = CeltraPlayerUtils.idempotented(function() {
                    e(d)
                }), s.on("timeupdate", i.onTimeUpdate), s.on("close", i.close), s.on("ended", i.close), s.on("error", i.close), s.on("exitfullscreen", i.close), adapter.once("dismissed", i.close), adapter.once("collapsed", i.close), adapter.on("orientationchange", i.refresh), adapter.on("resize", i.refresh), adapter.once("mediaStopRequested", i.close)
            }, i.removeListeners = function() {
                s.off("timeupdate", i.onTimeUpdate), s.off("close", i.close), s.off("ended", i.close), s.off("error", i.close), s.off("exitfullscreen", i.close), adapter.off("dismissed", i.close), adapter.off("collapsed", i.close), adapter.off("orientationchange", i.refresh), adapter.off("resize", i.refresh), adapter.off("mediaStopRequested", i.close)
            }, i.onTimeUpdate = function() {
                .5 < s.currentTime && (d = !0)
            }, i.refresh = function() {
                s.redraw()
            }, i.close = function() {
                null !== s && (c.hide(), i.removeListeners(), s.pause(), s.hide(), t(), defer(f, "undefined" != typeof ADMARVEL ? 1e3 : 100))
            }, window.FullscreenVideoPlayer = i
        }();;
        ! function() {
            this.Html5VideoEngine = function(e, t) {
                var n = merge({}, {
                        playsInline: !1,
                        maxTimeupdatesPerSecond: 15
                    }, t.options),
                    t = (n.startMuted = !!t.startMuted, n.crossOrigin = !!t.crossOrigin, n.videoElement),
                    r = !!t,
                    i = r ? t : document.createElement("video"),
                    t = i,
                    o = n;
                t.setAttribute("x-celtra-media", ""), o.playsInline && (t.setAttribute("webkit-playsinline", ""), t.setAttribute("playsinline", ""), o.startMuted && (t.muted = !0)), o.crossOrigin && (t.crossOrigin = "anonymous"), t.preload = o.preload ? "auto" : "none", CeltraPlayerUtils.setMediaElementSource(document, t, e);
                for (var u = {}, a = (extend(u, EventEmitter), function(e) {
                        u.emit(e.type, e)
                    }.bind(this)), d = ["loadstart", "loadedmetadata", "durationchange", "progress", "canplay", "play", "playing", "pause", "ended", "buffering", "endbuffering", "muted", "unmuted", "seeking", "seeked", "exitfullscreen", "webkitendfullscreen", "error", "canunmute", "custominfo", "playersourceloading"], s = d.length - 1; 0 <= s; s--) i.addEventListener(d[s], a);

                function c() {
                    m !== i.volume && (m = i.volume, u.emit("volumechange", m))
                }

                function l() {
                    var e = u.currentTime;
                    g % Math.round(60 / n.maxTimeupdatesPerSecond) == 0 && u.isPlaying & e !== f && (f = e, u.emit("timeupdate", e)), g++
                }
                var m = i.volume,
                    f = 0,
                    g = 0,
                    p = (Ticker.frame(c), Ticker.frame(l), Object.defineProperties(u, {
                        currentTime: {
                            get: function() {
                                return this.getCurrentTime()
                            },
                            set: function(e) {
                                this.setCurrentTime(e)
                            }
                        },
                        reportsSeeking: {
                            get: function() {
                                return !!i.reportsSeeking
                            }
                        },
                        buffered: {
                            get: function() {
                                return i.buffered
                            }
                        },
                        duration: {
                            get: function() {
                                return i.duration
                            }
                        },
                        muted: {
                            get: function() {
                                return i.muted
                            },
                            set: function(e) {
                                i.muted = e
                            }
                        },
                        isPlaying: {
                            get: function() {
                                return !(!(0 < this.getCurrentTime()) || i.paused || i.ended)
                            }
                        },
                        paused: {
                            get: function() {
                                return i.paused
                            }
                        },
                        activeSrc: {
                            get: function() {
                                return i.currentSrc || i.src
                            }
                        }
                    }), r || (i.videoWidth = n.videoWidth, i.videoHeight = n.videoHeight), u.continuePlaying = function() {
                        i.play()
                    }.bind(this), u.render = function() {
                        !r && android("4.0", "4.2") && (i.style.height = "auto", i.style.minHeight = "auto");
                        try {
                            i.load()
                        } catch (e) {}
                        return i
                    }, i.play ? i.play.bind(i) : noop),
                    y = i.pause ? i.pause.bind(i) : noop,
                    v = !0,
                    h = function() {
                        u.emit("autoplaynotpossible")
                    },
                    b = function() {
                        var e;
                        u.pauseEnforced || void 0 !== (e = p()) && e.catch(function(e) {
                            u.emit("autoplayrejected"), "NotAllowedError" !== e.name && "AbortError" !== e.name || (Logger("Html5VideoEngine").error("Video Engine - ", e), i.muted = !0, u.emit("forcemuted"), v ? (v = !1, b()) : (h(), h = noop))
                        })
                    };
                return u.mute = function() {
                    i.muted = !0
                }, u.unMute = function() {
                    i.muted = !1
                }, u.setCurrentTime = function(e) {
                    u.isReady() && (i.currentTime = e)
                }, u.getCurrentTime = function() {
                    return u.isReady() && 0 < i.currentTime ? i.currentTime : 0
                }, u.isReady = function() {
                    return i && 0 !== i.readyState
                }, u.play = function() {
                    u.pauseEnforced = !1, celtra.webkit("534.30") || celtra.webkit("537.36") || fakeclick(function() {
                        b()
                    }.bind(this)), defer(function() {
                        b()
                    }.bind(this)), CeltraPlayerUtils.isNode(i) && i.addEventListener("progress", i.continuePlaying)
                }, u.pause = function() {
                    CeltraPlayerUtils.isNode(i) && i.removeEventListener("progress", i.continuePlaying, {}, {}), y(), u.pauseEnforced = !0
                }, u.destroy = function() {
                    y();
                    for (var e = d.length - 1; 0 <= e; e--) i.removeEventListener(d[e], a);
                    r || CeltraPlayerUtils.removeElements(i), Ticker.removeFrame(c), Ticker.removeFrame(l)
                }, u
            }
        }();;
        ! function(e) {
            var s = Object.create(EventEmitter),
                t = window.AudioContext || window.webkitAudioContext,
                o = null,
                i = !1,
                n = !1;
            extend(s, {
                init: function(e, t, i) {
                    return this._sourceNode = null, this._buffer = null, this._bufferSilence = null, this._encodedBuffer = null, this._emitTimeUpdateInterval = null, this._sourceUrl = e, this._paused = !0, this._pausedAt = 0, this._timeDiff = 0, this._loadStatus = "unstarted", this.loop = !!i, t && this.load(), this
                },
                load: function() {
                    var e;
                    "unstarted" === this._loadStatus && (this._loadStatus = "pending", (e = new XMLHttpRequest).open("GET", this._sourceUrl, !0), e.responseType = "arraybuffer", e.onload = function() {
                        this._encodedBuffer = e.response, o && this._decodeAudioData()
                    }.bind(this), e.send())
                },
                unlock: function() {
                    i || (this._iosHack(), this._createAudioContext(), this._createNodeAndPlayFrom(this._bufferSilence, 0, this.loop))
                },
                playAudio: function(e) {
                    this._iosHack(), this._createAudioContext(), this.load();
                    e = null == e ? this._pausedAt : e;
                    this.pause(), this._timeDiff = o.currentTime - e, this._paused = !this._buffer, this._createNodeAndPlayFrom(this._buffer || this._bufferSilence, e, this.loop), this._emitTimeUpdate(), i || setTimeout(function() {
                        var e, t;
                        this._sourceNode && (e = this._sourceNode.playbackState === this._sourceNode.PLAYING_STATE, t = this._sourceNode.playbackState === this._sourceNode.FINISHED_STATE, i = i || !!this._buffer && (e || t))
                    }.bind(this), 0)
                },
                pause: function() {
                    clearInterval(this._emitTimeUpdateInterval), this._pausedAt = this.currentTime, this._paused = !0, this._clearSourceNode()
                },
                destroy: function() {
                    this.pause(), "function" == typeof o.close && o.close(), this._sourceNode = null, this._buffer = null, this._bufferSilence = null, this._encodedBuffer = null
                },
                _iosHack: function() {
                    ios() && !n && (this._createAudioContext(), this._createNodeAndPlayFrom(this._bufferSilence, 0, this.loop), "function" == typeof o.close && (o.close(), o = null), this._createAudioContext(), n = !0)
                },
                _createAudioContext: function() {
                    o || (o = new t, this._bufferSilence = o.createBuffer(1, 1, o.sampleRate), this._encodedBuffer && !this._buffer && this._decodeAudioData())
                },
                _createNodeAndPlayFrom: function(e, t, i) {
                    this._clearSourceNode(), this._sourceNode = o.createBufferSource(), this._sourceNode.connect(o.destination), this._sourceNode.buffer = e, this._sourceNode.loop = i, "function" == typeof this._sourceNode.noteGrainOn ? this._sourceNode.noteGrainOn(0, t, e.duration - t) : "function" == typeof this._sourceNode.start ? this._sourceNode.start(0, t) : this._sourceNode.noteOn(0, t)
                },
                _clearSourceNode: function() {
                    if (this._sourceNode) {
                        try {
                            "function" == typeof this._sourceNode.stop ? this._sourceNode.stop(0) : this._sourceNode.noteOff(0)
                        } catch (e) {}
                        this._sourceNode.disconnect(), this._sourceNode = null
                    }
                },
                _emitTimeUpdate: function() {
                    this.paused || this._buffer && this.currentTime >= this._buffer.duration ? clearInterval(this._emitTimeUpdateInterval) : this._emitTimeUpdateInterval = setInterval(this.emit.bind(this, "timeupdate"), 250)
                },
                _decodeAudioData: function() {
                    o.decodeAudioData(this._encodedBuffer, function(e) {
                        this._buffer = e, this._loadStatus = "loaded", this.emit("canplay"), this.emit("canplaythrough")
                    }.bind(this))
                }
            }), Object.defineProperties(s, {
                paused: {
                    get: function() {
                        return this._paused
                    }
                },
                ended: {
                    get: function() {
                        return !1
                    }
                },
                ready: {
                    get: function() {
                        return "loaded" === this._loadStatus
                    }
                },
                playedBefore: {
                    get: function() {
                        return i
                    }
                },
                currentTime: {
                    get: function() {
                        return this._paused ? this._pausedAt : o.currentTime - this._timeDiff
                    },
                    set: function(e) {
                        this._pausedAt = e, "loaded" !== this._loadStatus || this._paused || (this.pause(), this.playAudio(e))
                    }
                }
            }), e.WebAudioEngine = function(e, t, i) {
                return Object.create(s).init(e, t, !!i)
            }
        }(this);;
        ! function() {
            this.Html5AudioEngine = function(e, t) {
                var n, o, r;
                return "undefined" == typeof Audio ? null : ((n = new Audio).isUnlocked = !1, t ? (n.preload = "auto", CeltraPlayerUtils.setMediaElementSource(document, n, e), n.start = noop) : (n.preload = "none", n.start = function() {
                    CeltraPlayerUtils.setMediaElementSource(document, n, e), n.start = noop
                }), n.ready = !1, r = function() {
                    .3 < n.buffered / n.duration && o()
                }, n.addEventListener("canplay", o = function(e) {
                    n.ready = !0
                }), n.addEventListener("canplaythrough", o), n.addEventListener("progress", r), n.unlock = noop, n.on = n.addEventListener, n.off = n.removeEventListener, n.playedBefore = !1, n.unlock = function() {
                    this.isUnlocked || this.playedBefore || (fakeclick(function() {
                        celtra.webkit("537.36") && n.play(), n.pause()
                    }), this.isUnlocked = !0)
                }, n.playFrom = function(e) {
                    n.start(), n.ready && (fakeclick(function() {
                        n.play()
                    }), void 0 !== e && (n.currentTime = e, n.playedBefore = !0))
                }, n.destroy = function() {
                    for (this.pause(), this.src = null; this.firstElementChild;) this.removeChild(this.lastElementChild);
                    n.removeEventListener("progress", r), n.removeEventListener("canplay", o), n.removeEventListener("canplaythrough", o)
                }, n.setAttribute("x-celtra-media", ""), n)
            }
        }();;
        ! function() {
            this.AVSyncAudio = {
                init: function(i, t, e) {
                    this.onPause = this.onPause.bind(this), this.onEnded = this.onEnded.bind(this), this.onPlay = this.onPlay.bind(this), this.onReplayed = this.onReplayed.bind(this), this.onMuted = this.onMuted.bind(this), this.onUnMuted = this.onUnMuted.bind(this), this.onTimeUpdate = this.onTimeUpdate.bind(this), this.onFirstPlay = this.onFirstPlay.bind(this), this.destroy = this.destroy.bind(this), this.video = i, this.audio = t, this.muted = e, this._lastSyncTime = 0, this.video.once("destroy", this.destroy), this.video.once("userplayed", this.onFirstPlay), this.video.on("muted", this.onMuted), this.video.on("unmuted", this.onUnMuted), this.video.on("replayed", this.onReplayed)
                },
                attach: function() {
                    this.video.on("pause", this.onPause), this.video.on("ended", this.onEnded), this.video.on("userpaused", this.onPause), this.video.on("seeking", this.onPause), this.video.on("play", this.onPlay), this.video.on("playing", this.onPlay), this.video.on("seeked", this.onPlay), this.video.on("timeupdate", this.onTimeUpdate)
                },
                destroy: function() {
                    this.audio.pause(), this.video.off("userplayed", this.onFirstPlayed), this.video.off("userpaused", this.onPause), this.video.off("pause", this.onPause), this.video.off("seeking", this.onPause), this.video.off("ended", this.onEnded), this.video.off("play", this.onPlay), this.video.off("playing", this.onPlay), this.video.off("seeked", this.onPlay), this.video.off("muted", this.onMuted), this.video.off("unmuted", this.onUnMuted), this.video.off("timeupdate", this.onTimeUpdate), this.video.off("replayed", this.onReplayed)
                },
                onFirstPlay: function() {
                    var e, s, o, d;
                    this.video.off("userplayed", this.onFirstPlay), this.audio.start(), this.muted || this.audio.ready ? (fakeclick(function() {
                        this.audio.load()
                    }.bind(this)), this.attach()) : (s = e = !1, o = celtra.webkit("534.30"), d = function() {
                        var i = isMediaPlaying(this.video),
                            t = isMediaPlaying(this.audio);
                        if (t && i || t && s || i && e || e && s || this.audio.ready) {
                            this.attach(), this.video.silentPlay();
                            try {
                                this.video.currentTime = this.audio.currentTime
                            } catch (i) {}
                        } else i && !t && (this.video.silentPause(), s = !0), !t || i || o || (this.audio.pause(), e = !0), defer(d.bind(this), 120)
                    }.bind(this), o ? this.audio.playFrom() : fakeclick(function() {
                        this.audio.load()
                    }.bind(this)), defer(d, 500))
                },
                onPause: function() {
                    this.audio.pause()
                },
                onEnded: function() {
                    this.audio.pause(), this.audio.ready && (this.audio.currentTime = 0)
                },
                onPlay: function() {
                    this.muted || this.audio.playFrom()
                },
                onReplayed: function() {
                    this.muted || (this.audio.playFrom(0), this._lastSyncTime = Date.now())
                },
                onMuted: function() {
                    this.muted = !0, this.audio.pause()
                },
                onUnMuted: function() {
                    this.muted = !1, isMediaPlaying(this.video) && this.audio.ready ? this.audio.playFrom(this.video.currentTime) : this.audio.unlock()
                },
                onTimeUpdate: function() {
                    var i = isMediaPlaying(this.audio);
                    if (!(!i && this.audio.playedBefore || this.muted))
                        if (!this.audio.paused || this.muted || this.video.paused) {
                            var i = this.video.currentTime - this.audio.currentTime,
                                t = Math.abs(i);
                            if (!(t < .6 || this.video.currentTime < 1 || Date.now() - this._lastSyncTime < 3e3))
                                if (this._lastSyncTime = Date.now(), i < 0 && t < 2) try {
                                    this.video.currentTime = this.audio.currentTime
                                } catch (i) {} else try {
                                    this.audio.currentTime = this.video.currentTime + .3
                                } catch (i) {}
                        } else this.audio.playFrom(this.video.currentTime)
                }
            }
        }();;
        ! function() {
            this.AVSyncWebAudio = {
                init: function(i, t, e) {
                    this.onPause = this.onPause.bind(this), this.onEnded = this.onEnded.bind(this), this.onMuted = this.onMuted.bind(this), this.onUnMuted = this.onUnMuted.bind(this), this.onTimeUpdate = this.onTimeUpdate.bind(this), this.onFirstPlay = this.onFirstPlay.bind(this), this.destroy = this.destroy.bind(this), this.video = i, this.audio = t, this.muted = e, this._lastSyncTime = 0, i.once("destroy", this.destroy), i.once("userplayed", this.onFirstPlay), this.video.on("muted", this.onMuted), this.video.on("unmuted", this.onUnMuted)
                },
                attach: function() {
                    this.video.on("pause", this.onPause), this.video.on("ended", this.onEnded), this.video.on("userpaused", this.onPause), this.video.on("seeking", this.onPause), this.video.on("timeupdate", this.onTimeUpdate)
                },
                destroy: function() {
                    this.audio.pause(), this.video.off("userplayed", this.onFirstPlayed), this.video.off("pause", this.onPause), this.video.off("userpaused", this.onPause), this.video.off("seeking", this.onPause), this.video.off("ended", this.onEnded), this.video.off("muted", this.onMuted), this.video.off("unmuted", this.onUnMuted), this.video.off("timeupdate", this.onTimeUpdate)
                },
                onFirstPlay: function() {
                    this.video.off("userplayed", this.onFirstPlay);

                    function i() {
                        t && !s && e.silentPause()
                    }
                    var t = !0,
                        e = this.video,
                        s = celtra.webkit("534.30");
                    this.muted || this.audio.ready ? (this.audio.load(), this.attach()) : (this.video.once("canplay", i), this.video.once("playing", i), this.video.once("timeupdate", i), this.audio.once("canplaythrough", function() {
                        t = !1, this.video.off("canplay", i), this.video.off("playing", i), this.video.off("timeupdate", i), isMediaPlaying(this.video) || this.video.silentPlay(), !isMediaPlaying(this.audio) && android() && this.audio.playAudio(), this.attach()
                    }.bind(this)), this.audio.load())
                },
                onPause: function() {
                    this.audio.pause()
                },
                onEnded: function() {
                    this.audio.pause(), this.audio.currentTime = 0
                },
                onMuted: function() {
                    this.muted = !0, this.audio.pause()
                },
                onUnMuted: function() {
                    this.muted = !1, isMediaPlaying(this.video) ? this.audio.playAudio(this.video.currentTime) : this.audio.unlock()
                },
                onTimeUpdate: function() {
                    if (!this.muted)
                        if (this.audio.paused && !this.video.paused) this.audio.playAudio(this.video.currentTime);
                        else {
                            var i = this.video.currentTime - this.audio.currentTime,
                                t = Math.abs(i);
                            if (!(t < .6 || this.video.currentTime < 1 || Date.now() - this._lastSyncTime < 3e3))
                                if (this._lastSyncTime = Date.now(), i < 0 && t < 2) try {
                                    this.video.currentTime = this.audio.currentTime
                                } catch (i) {} else try {
                                    this.audio.currentTime = this.video.currentTime
                                } catch (i) {}
                        }
                }
            }
        }();;
        ! function() {
            function s(e, i, t) {
                this._videoPlayer = e, this._opts = merge({}, s.DEFAULTS, t), this.isVisible = !1, this._controlsInitiallyDisabled = !1, this._isFirstPlay = !0, this._playAfterSeeking = !1, this._isMuted = !1, this._spinnerShown = !0, this._cursorHidden = !1, CeltraPlayerUtils.attachHandlers(this, this._videoPlayer, s.PLAYER_EVENTS, !0), this._changeVisibility = this._changeVisibility.bind(this), this.handleUserInteraction = this.handleUserInteraction.bind(this), this._videoControls = i(this.handleUserInteraction)
            }
            extend(s.prototype, EventEmitter), s.DEFAULTS = {
                autohideTime: 0,
                progressbar: !0,
                progressbarColour: "#4589ce",
                startInFullScreen: !1,
                controlsHidden: !1,
                fullscreenMode: "both",
                minLengthToSeek: 30,
                muteUnmuteEnabled: !0
            }, s.PLAYER_EVENTS = {
                durationchange: "onVPDurationChange",
                muted: "onVPMuted",
                unmuted: "onVPUnmuted",
                progress: "onVPProgress",
                play: "onVPPlay",
                playing: "onVPPlaying",
                pause: "onVPPause",
                autoplayrejected: "onVPAutoplayRejected",
                forcemuted: "onVPForceMuted",
                ended: "onVPEnded",
                buffering: "onVPBuffering",
                timeupdate: "onVPTimeupdate",
                seekto: "onVPSeekto",
                enterfullscreen: "onVPEnterFullscreen",
                exitfullscreen: "onVPExitFullscreen",
                orientationchange: "onVPOrientationChange",
                canunmute: "onVPCanUnmute",
                playersourceloading: "onVPPlayerSourceLoading"
            }, extend(s.prototype, {
                render: function(e) {
                    e = this._videoControls.render(e, this._opts);
                    return this.init(), e
                },
                init: function() {
                    this._changeVisibilityTimed(!1), this._opts.progressbar || this._videoControls.hide("sliderContainer"), "permanent" === this._opts.fullscreenMode ? (this._videoControls.hide("enterFullScreen", "exitFullScreen"), this._videoControls.show("close")) : !0 === this._videoPlayer.isDesktop && !1 === this._videoPlayer.dtCanFullscreen ? this._videoControls.hide("close", "enterFullScreen", "exitFullScreen") : (this._videoControls.hide("close", "exitFullScreen"), this._videoControls.show("enterFullScreen")), "disabled" === this._opts.fullscreenMode && this._videoControls.hide("enterFullScreen", "exitFullScreen", "close"), this._opts.debug ? this._videoControls.show("monitor") : this._videoControls.hide("monitor"), this._opts.muteUnmuteEnabled ? (this._videoControls.hide("mute"), this._videoControls.hide("unMute")) : this._videoControls.disableMuteUnmuteControls(), this._videoControls.hide("pause", "replay", "play"), this._opts.isAutoplay || this._videoControls.show("play"), this._spinnerHide(), this._videoPlayer.isDesktop && this._videoControls.setDTMouseMoveHandler(this._videoPlayer.getWrapperElement())
                },
                adjustSizes: function(e) {
                    e < 80 ? this._videoControls.addClass("spinner", "video-player-spinner-small") : this._videoControls.addClass("spinner", "video-player-spinner-big")
                },
                reset: function() {
                    this._controlsInitiallyDisabled = !1, this._videoControls.hide("pause", "replay"), this._videoControls.show("play"), this._spinnerHide(), defer(function() {
                        this._videoControls.hide("duringPlaySet")
                    }.bind(this), 500)
                },
                destroy: function() {
                    this._videoControls.destroy()
                },
                handleUserInteraction: function(e, i) {
                    switch (e) {
                        case "vidWrapper":
                            this._changeVisibilityTimed(!0), this._makeControlsDisapear();
                            break;
                        case "wrapper":
                            this._videoPlayer._spinnerShown ? this._changeVisibilityTimed(!0) : (!this.isVisible || this._videoPlayer.ended || this._spinnerShown || i ? this._changeVisibilityTimed(!0) : this._videoPlayer.paused ? (this._videoPlayer.play(), this.emit("userInteraction", {
                                isUserEngaged: !0
                            })) : (this._videoPlayer.pause(), this.emit("userInteraction")), this._makeControlsDisapear());
                            break;
                        case "mute":
                            this._videoPlayer.mute(), this._videoControls.pendingStateOn("mute"), this._makeControlsDisapear(), this.emit("userInteraction");
                            break;
                        case "unMute":
                            this._videoPlayer.unMute(), this._videoControls.pendingStateOn("unMute"), this._makeControlsDisapear(), this.emit("userInteraction", {
                                isUserEngaged: !0
                            });
                            break;
                        case "enterFullScreen":
                            this._videoPlayer.enterFullScreen(), this._makeControlsDisapear(), this.emit("userInteraction", {
                                isUserEngaged: !0
                            });
                            break;
                        case "exitFullScreen":
                            this._videoPlayer.exitFullScreen(), this._makeControlsDisapear(), this.emit("userInteraction");
                            break;
                        case "pause":
                            this._controlsInitiallyDisabled || (this._videoPlayer.pause(), this.emit("userInteraction"));
                            break;
                        case "play":
                            this._controlsInitiallyDisabled || (this.emit("userInteraction", {
                                isUserEngaged: !0
                            }), this.emit("playButtonPressed"));
                            break;
                        case "replay":
                            this._changeVisibilityTimed(!1), this._videoPlayer.replay(), this.emit("userInteraction", {
                                isUserEngaged: !0
                            });
                            break;
                        case "close":
                            this._videoPlayer.close(), this.emit("userInteraction")
                    }
                },
                onVPPlayerSourceLoading: function() {
                    this._spinnerShow()
                },
                onVPCanUnmute: function(e) {},
                onVPMuted: function(e) {
                    this._isMuted = !0, this._videoControls.hide("mute"), this._videoControls.show("unMute"), this._videoControls.pendingStateOff("unMute"), this._videoControls.pendingStateOff("mute")
                },
                onVPUnmuted: function(e) {
                    this._isMuted = !1, this._videoControls.hide("unMute"), this._videoControls.show("mute"), this._videoControls.pendingStateOff("unMute"), this._videoControls.pendingStateOff("mute")
                },
                onVPPlay: function() {
                    this._videoControls.hideAndShowWrapper(), this._isFirstPlay && (this._isFirstPlay = !1, this._controlsInitiallyDisabled = !0, this._spinnerShow()), this._changeVisibility(!0), this._videoControls.hide("play")
                },
                onVPPlaying: function() {
                    var e = this._opts.autohideTime;
                    this._videoControls.hide("play", "pause", "replay"), e && this._changeVisibilityTimed(!1, 1e3 * e)
                },
                onVPPause: function() {
                    this._changeVisibilityTimed(!0), this._spinnerShown || this._videoControls.show("play")
                },
                onVPAutoplayRejected: function() {
                    this.reset()
                },
                onVPForceMuted: function() {
                    this.onVPMuted()
                },
                onVPBuffering: function() {
                    this._spinnerShow(), this._videoControls.hide("play")
                },
                onVPEnded: function() {
                    this._changeVisibilityTimed(!1), this._videoControls.hide("play", "pause"), this._opts.pauseOnEnd || this._videoControls.show("replay")
                },
                onVPProgress: function(e) {
                    this._progressRatio = e, this._videoControls.setProgressBarWidth(e)
                },
                onVPTimeupdate: function(e) {
                    var i = this._videoPlayer.duration;
                    this._videoControls.setTimeBarWidth(i && e / i || 0), this._controlsInitiallyDisabled && (this._controlsInitiallyDisabled = !1), this._videoPlayer.paused || (this._videoControls.hide("play"), this._videoControls.hide("pause"), this._videoControls.hide("replay")), this._spinnerHide()
                },
                onVPEnterFullscreen: function(e) {
                    this._videoControls.hideAndShowWrapper(), this._videoControls.hide("enterFullScreen"), this._videoControls.show("exitFullScreen"), this._makeControlsDisapear()
                },
                onVPOrientationChange: function(e) {
                    this._opts.fullscreenLandscapeButtonsHack && this._videoControls.orientationChange(function(e) {
                        e === CeltraPlayerUtils.LANDSCAPE && this._videoPlayer.isFullScreen ? this._elements.close.style.bottom = this._elements.exitFullScreen.style.bottom = this._elements.mute.style.bottom = this._elements.unMute.style.bottom = "26px" : this._elements.close.style.bottom = this._elements.exitFullScreen.style.bottom = this._elements.mute.style.bottom = this._elements.unMute.style.bottom = ""
                    })
                },
                onVPExitFullscreen: function(e) {
                    this._videoControls.hideAndShowWrapper(), this._videoControls.hide("exitFullScreen"), this._videoControls.show("enterFullScreen"), this._makeControlsDisapear()
                },
                onVPSeekto: function(e) {
                    this._videoControls.setTimeBarWidth(e)
                },
                _changeVisibility: function(e) {
                    this._opts.controlsHidden ? (this.isVisible = !0, this._videoControls.hide("duringPlaySet")) : e ? (this._videoControls.show("duringPlaySet"), this._videoPlayer.isDesktop && this._cursorHidden && (this._videoControls.showCursor(this._videoPlayer.getWrapperElement()), this._cursorHidden = !1), this.isVisible = !0) : (this._videoControls.hide("duringPlaySet"), this._videoPlayer.isDesktop && this._videoPlayer.isFullScreen && (this._videoControls.hideCursor(this._videoPlayer.getWrapperElement()), this._cursorHidden = !0), this.isVisible = !1, this._videoPlayer.isDesktop && (this._videoControls.hasMouseMoved = !0))
                },
                _changeVisibilityTimed: function(e, i) {
                    clearTimeout(this._changeVisibilityTimeout), i ? this._changeVisibilityTimeout = setTimeout(this._changeVisibility.bind(this, e), i) : this._changeVisibility(e)
                },
                _makeControlsDisapear: function() {
                    var e = this._opts.autohideTime;
                    e && this._changeVisibilityTimed(!1, 1e3 * e)
                },
                _spinnerHide: function() {
                    this._spinnerShown && (this._spinnerShown = !1, this._videoControls.hide("spinner"))
                },
                _spinnerShow: function() {
                    !this._spinnerShown && this._opts.canShowSpinner && (this._spinnerShown = !0, defer(function() {
                        this._spinnerShown && (this._videoControls.hide("play"), this._videoControls.show("spinner"))
                    }.bind(this), 300))
                }
            }), this.BasicVideoControlsController = s
        }();;
        ! function() {
            function o(t) {
                this._container = null, this._userInteractionHandler = t, this.onTapHandler = CeltraPlayerUtils.deduplicate(this.onTapHandler, 400, this), this.hasMouseMoved = !1
            }
            extend(o.prototype, EventEmitter), o.TEMPLATE = ['<div class="video-basic-controls-wrapper <%= desktop %> touchable" data-bind="wrapper">', '  <div class="video-controls-hiddable" data-bind="duringPlaySet">', '    <div class="video-controls-button video-controls-button-shadow video-controls-button-bottom-right touchable" data-bind="mute"><div class="celtra-icon-mute"></div></div>', '    <div class="video-controls-button video-controls-button-shadow video-controls-button-bottom-right touchable" data-bind="unMute"><div class="celtra-icon-unmute"></div></div>', '    <div class="video-controls-button video-controls-button-shadow video-controls-button-top-right touchable" data-bind="close"><div class="celtra-icon-close"></div></div>', '    <div class="video-controls-button video-controls-button-shadow video-controls-button-top-right touchable" data-bind="enterFullScreen"><div class="celtra-icon-enter-full-screen"></div></div>', '    <div class="video-controls-button video-controls-button-shadow video-controls-button-top-right touchable" data-bind="exitFullScreen"><div class="celtra-icon-exit-full-screen"></div></div>', '    <div class="video-controls-slider-container touchable"  data-bind="sliderContainer">', '        <div class="video-controls-progress-bar" data-bind="progressBar"></div>', '        <div class="video-controls-time-bar" data-bind="timeBar"></div>', "    </div>", "  </div>", '  <div class="video-controls-unhiddable" data-bind="outOfPlaySet">', '    <div class="video-sizable"><div class="video-player-spinner-big" data-bind="spinner"></div></div>', '    <div class="center-button touchable" data-bind="pause"><div class="celtra-icon-pause"></div></div>', '    <div class="center-button touchable" data-bind="play"><div class="celtra-icon-play"></div></div>', '    <div class="center-button touchable" data-bind="replay"><div class="celtra-icon-replay"></div></div>', "  </div>", "</div>"], extend(o.prototype, {
                render: function(t, n) {
                    var e, i = n.isMasterVideo ? [] : ["play", "replay"];
                    return this._container || (e = {}, CeltraPlayerUtils.createDom(t, o.TEMPLATE, {
                        desktop: windows("10") ? "video-controls-desktop ie-click-event" : desktop() ? "video-controls-desktop" : ""
                    }, this._getWrapper(e)), this._container = e.wrapper, n.isMasterVideo && this.hide("outOfPlaySet"), this._container.querySelector("[data-bind=progressBar]").style.backgroundColor = n.progressbarColour, this._container.querySelector("[data-bind=timeBar]").style.backgroundColor = n.progressbarColour, (t = "undefined" != typeof creative ? creative : window.creative) && (e = "MRAIDAdapter" === t.adapter.constructor.name, "crop" === n.fitting && e && (this._container.querySelector("[data-bind=exitFullScreen]").style.top = "50px")), i.forEach(function(t) {
                        var e, i;
                        n[t] && (e = n[t], i = this._getElements(t)[0], removeClass(i, "center-button"), addClass(i, "custom-button"), i.innerHTML = '<img src="' + e.getUrl() + '" data-bind="' + t + '" style="width: ' + e.width + "px; height: " + e.height + 'px;" />')
                    }.bind(this)), this._container.addEventListener("tap", this.onTapHandler)), this._container
                },
                show: function() {
                    CeltraPlayerUtils.show.apply(null, this._getElements.apply(this, arguments))
                },
                hide: function() {
                    CeltraPlayerUtils.hide.apply(null, this._getElements.apply(this, arguments))
                },
                showCursor: function(t) {
                    CeltraPlayerUtils.showCursor([t])
                },
                hideCursor: function(t) {
                    CeltraPlayerUtils.hideCursor([t])
                },
                setProgressBarWidth: function(t) {
                    t = Math.min(t, 1), this._container && this._container.querySelector("[data-bind=progressBar]") && (this._container.querySelector("[data-bind=progressBar]").style.width = 100 * t + "%")
                },
                setTimeBarWidth: function(t) {
                    t = Math.min(t, 1), this._container && this._container.querySelector("[data-bind=timeBar]") && (this._container.querySelector("[data-bind=timeBar]").style.width = 100 * t + "%")
                },
                hideAndShowWrapper: function() {
                    this.hide("wrapper"), defer(function() {
                        this.show("wrapper")
                    }.bind(this))
                },
                disableMuteUnmuteControls: function() {
                    this._getElements("mute")[0].innerHTML = "", this._getElements("unMute")[0].innerHTML = ""
                },
                pendingStateOn: function(t) {
                    addClass(this._getElements(t)[0], "video-controls-pending")
                },
                pendingStateOff: function(t) {
                    this._container && removeClass(this._getElements(t)[0], "video-controls-pending")
                },
                addClass: function(t, e) {
                    t = this._getElements(t)[0];
                    t && addClass(t, e)
                },
                removeClass: function(t, e) {
                    t = this._getElements(t)[0];
                    t && removeClass(t, e)
                },
                orientationChange: function(t) {
                    t()
                },
                onTapHandler: function(t) {
                    t.stopPropagation();
                    t = t.target.getAttribute("data-bind");
                    t && this._userInteractionHandler(t)
                },
                setDTMouseMoveHandler: function(e) {
                    e.addEventListener("mousemove", function(t) {
                        t.stopPropagation();
                        t = t.target, t = t !== e ? t.getAttribute("data-bind") : "vidWrapper";
                        this.hasMouseMoved ? this.hasMouseMoved = !1 : "wrapper" !== t && "vidWrapper" !== t || this._userInteractionHandler(t, !0)
                    }.bind(this))
                },
                _getWrapper: function(i) {
                    return function(t) {
                        var e = t.getAttribute("data-bind");
                        e && (i[e] = t)
                    }
                },
                _getElements: function() {
                    if (this._container) {
                        for (var t, e = [], i = 0; i < arguments.length; i++)(t = this._container.querySelector("[data-bind=" + arguments[i] + "]")) && e.push(t);
                        return e
                    }
                },
                destroy: function() {
                    this._container && (this._container.removeEventListener("tap", this.onTapHandler), CeltraPlayerUtils.removeElements(this._container), this._container = null)
                }
            }), this.BasicVideoControls = o
        }();;
        ! function(i) {
            function t(e, t, i, n, s) {
                this._containerEl = e, this._opts = merge({}, o, s), this._videoEngine = null, this._videoEngineSpecProvider = t, this.isUserGazing = n, this._doc = e.ownerDocument, this._win = this._doc.defaultView, this._initialized = !1, this._firstPlay = !0, this.silentPauseActive = !1, this._engineType = null, this._videoPresets = [], this._muted = !0, i && (this._controlsController = i(this, this._opts), this._controlsController && this._controlsController.on("userInteraction", this.emit.bind(this, "userInteraction"))), this._aspectRatio = this._opts.videoWidth / this._opts.videoHeight, this.isFullScreen = !this.playsInline(), this.isDesktop = this._opts.isDesktop, this.anchoringPoint = this._opts.anchoringPoint, this.buffered = 0, this._state = null, this._posterShown = !0, this._seeked = !1, this._repeated = 0, this.playSucceeded = !1, this._playingEmitted = !1, this._firstRenderEmitted = !1, this._timeUpdateCounter = 0, this._lastTimeUpdateEmitted = 0, this.play = this.play.bind(this), this.silentPlay = this.silentPlay.bind(this), this.pause = this.pause.bind(this), this.silentPause = this.silentPause.bind(this), this.mute = this.mute.bind(this), this.unMute = this.unMute.bind(this), this.redraw = this.redraw.bind(this), this.enterFullScreen = this.enterFullScreen.bind(this), this.exitFullScreen = this.exitFullScreen.bind(this), this.destroy = this.destroy.bind(this), this._onFullscreenChange = this._onFullscreenChange.bind(this), this._init(), this.changeState(CeltraPlayerUtils.UNSTARTED), this._fullScreenAncestors = []
            }
            extend(t.prototype, StatefulEventEmitter), Object.defineProperty(t.prototype, "paused", {
                get: function() {
                    return this._videoEngine && this._videoEngine.paused
                }
            }), Object.defineProperty(t.prototype, "componentName", {
                get: function() {
                    return this._opts.componentName
                }
            }), Object.defineProperty(t.prototype, "duration", {
                get: function() {
                    return this.serverReportedDuration || this._videoEngine && this._videoEngine.duration || 0
                }
            }), Object.defineProperty(t.prototype, "serverReportedDuration", {
                get: function() {
                    return this._opts.duration
                }
            }), Object.defineProperty(t.prototype, "currentTime", {
                get: function() {
                    return this._videoEngine && this._videoEngine.getCurrentTime() || 0
                },
                set: function(e) {
                    this._videoEngine && this._videoEngine.setCurrentTime(e)
                }
            }), Object.defineProperty(t.prototype, "reportsSeeking", {
                get: function() {
                    return !(!this._videoEngine || !this._videoEngine.reportsSeeking)
                }
            }), Object.defineProperty(t.prototype, "muted", {
                get: function() {
                    return this._muted
                }
            }), Object.defineProperty(t.prototype, "fullscreen", {
                get: function() {
                    return this.isFullScreen
                }
            }), Object.defineProperty(t.prototype, "engineType", {
                get: function() {
                    return this._engineType
                }
            }), Object.defineProperty(t.prototype, "videoPresets", {
                get: function() {
                    return this._videoPresets
                }
            }), Object.defineProperty(t.prototype, "activeSrc", {
                get: function() {
                    return this._videoEngine && this._videoEngine.activeSrc
                }
            }), Object.defineProperty(t.prototype, "activePreset", {
                get: function() {
                    return this._videoPresets && this._videoPresets.filter(function(e) {
                        return this.activeSrc && ~this.activeSrc.indexOf(e)
                    }.bind(this))[0]
                }
            }), Object.defineProperty(t.prototype, "engineInitialized", {
                get: function() {
                    return this._initialized
                }
            }), Object.defineProperty(t.prototype, "ended", {
                get: function() {
                    return this.isState(CeltraPlayerUtils.ENDED)
                }
            });
            var o = {
                    canShowSpinner: !0,
                    closeFSOnEnd: !1,
                    fitting: "fit",
                    fullscreenMode: "both",
                    pauseOnEnd: !1,
                    playFrom: null,
                    playTo: null,
                    poster: "data:image/gif;base64," + CeltraPlayerUtils.EMPTY_PIXEL,
                    repeatTimes: 0,
                    videoWidth: 160,
                    videoHeight: 100
                },
                n = {
                    loadstart: "onLoadStart",
                    loadedmetadata: "onLoadedMetaData",
                    durationchange: "onDurationChange",
                    progress: "onProgress",
                    timeupdate: ["onTimeUpdate", "onTimeUpdateRepeat"],
                    canplay: "onCanPlay",
                    play: "onPlay",
                    playing: "onPlaying",
                    pause: "onPause",
                    autoplayrejected: "onAutoplayRejected",
                    autoplaynotpossible: "onAutoplayNotPossible",
                    forcemuted: "onForceMuted",
                    ended: "onEnded",
                    buffering: "onBuffering",
                    endbuffering: "onEndBuffering",
                    muted: "onMuted",
                    unmuted: "onUnMuted",
                    seeking: "onSeeking",
                    seeked: "onSeeked",
                    exitfullscreen: "onExitFullScreen",
                    webkitendfullscreen: "onExitFullScreen",
                    error: "onError",
                    canunmute: "onCanUnMute",
                    custominfo: "onCustomInfo",
                    playersourceloading: "onPlayerSourceLoading",
                    volumechange: "onVolumeChange"
                };
            t.DOM = ['<div class="video-player-wrapper" x-bind="_wrapperEl">', '    <div class="video-player-engine" x-bind="_engineContainerEl"></div>', '    <div x-bind="_posterEl" class="video-player-poster" style="background-image: url(\'<%= poster %>\')"></div>', '    <div class="video-controls-container" x-bind="_controlsWrapperEl"></div>', "</div>"], t.prototype._init = function() {
                var e;
                CeltraPlayerUtils.createDom(this._doc, t.DOM, this._opts, CeltraPlayerUtils.xBindFactory(this)), CeltraPlayerUtils.insertStyleTag("celtra-video-player-style", VideoPlayerCss, this._doc), this._controlsController && (this._controlsEl = this._controlsController.render(this._doc), this._controlsWrapperEl.appendChild(this._controlsEl)), this._containerEl.appendChild(this._wrapperEl), this._posterShow(), this._controlsController && this._controlsController.adjustSizes(this._containerEl.clientHeight), this._opts.preload && (e = this.createSpecs(!1, this._opts.startMuted), this.initialize(e)), this.dtCanFullscreen = this.isDesktop && this._toggleDesktopFSStateHandlers("add")
            }, t.prototype.setDimensions = function(e, t) {
                this.width = e, this.height = t, this.redraw(), this.emit("rendered")
            }, t.prototype.getVideoElement = function() {
                return this._videoEngine
            }, t.prototype.getControlsElement = function() {
                return this._controlsEl
            }, t.prototype.getWrapperElement = function() {
                return this._wrapperEl
            }, t.prototype._posterHide = function() {
                this._posterShown && (this._posterShown = !1, this._posterEl.style.display = "none")
            }, t.prototype._posterShow = function() {
                this._posterShown || (this._posterShown = !0, this._posterEl.style.display = "")
            }, extend(t.prototype, {
                onLoadStart: function() {
                    this.emit("loadstart")
                },
                onLoadedMetaData: function() {
                    this.redraw(), this.emit("canunmute")
                },
                onCanPlay: function() {
                    this.emit("canplay")
                },
                onDurationChange: function() {
                    this.emit("durationchange")
                },
                onBuffering: function() {
                    this.changeState(CeltraPlayerUtils.BUFFERING), this.emit("buffering")
                },
                onEndBuffering: function() {
                    this.emit("endbuffering")
                },
                onPlayerSourceLoading: function(e) {
                    (this._opts.autoplay || e) && this.emit("playersourceloading")
                },
                onMuted: function() {
                    this._muted = !0, this.emit("muted")
                },
                onUnMuted: function() {
                    this._muted = !1, this.emit("unmuted")
                },
                onVolumeChange: function(e) {
                    this._muted = 0 == e, this.emit(this._muted ? "muted" : "unmuted"), this.emit("volumechange", e)
                },
                onProgress: function() {
                    "object" == typeof this._videoEngine.buffered && 0 < this._videoEngine.buffered.length ? this.buffered = this._videoEngine.buffered.end(0) : this.buffered = this._videoEngine.buffered, this.emit("progress", this.buffered / (this.duration || 1))
                },
                onTimeUpdate: function(e) {
                    !this._playingEmitted && 2 < this._timeUpdateCounter && 0 < this.buffered && (this._playingEmitted = !0, this.onEndBuffering(), this.changeState(CeltraPlayerUtils.PLAYING), this.emit("playing")), this._timeUpdateCounter += 1;
                    var t = Date.now();
                    3 < this._timeUpdateCounter && 70 < t - this._lastTimeUpdateEmitted && (this._lastTimeUpdateEmitted = t, this._posterHide(), this._firstRenderEmitted || (this.emit("firstRender"), this._firstRenderEmitted = !0), this.emit("timeupdate", this._videoEngine.getCurrentTime()))
                },
                onTimeUpdateRepeat: function(e) {
                    this._opts.playTo && e >= this._opts.playTo && (this._hasToBeRepeated() ? this._doRepeat() : (this.pause(), this._doEnd()))
                },
                onPlay: function() {
                    this.emit("play")
                },
                onPlaying: function() {
                    this.emit("playing"), this.changeState(CeltraPlayerUtils.PLAYING), this.playSucceeded = !0, this.redraw()
                },
                onPause: function() {
                    this.changeState(CeltraPlayerUtils.PAUSED), this.emit("pause")
                },
                onAutoplayRejected: function() {
                    this.emit("autoplayrejected")
                },
                onAutoplayNotPossible: function() {
                    this.emit("autoplaynotpossible")
                },
                onForceMuted: function() {
                    this.emit("forcemuted")
                },
                onExitFullScreen: function() {
                    this.exitFullScreen()
                },
                onEnded: function() {
                    this._playingEmitted = !1, this._hasToBeRepeated() ? this._doRepeat() : this._doEnd()
                },
                getContainerDimensions: function() {
                    var e;
                    return this.isFullScreen ? {
                        width: (e = this.dtCanFullscreen ? this._wrapperEl : this._wrapperEl.parentNode).clientWidth,
                        height: e.clientHeight
                    } : {
                        width: this.width,
                        height: this.height
                    }
                },
                redraw: function() {
                    var e = "fit" !== this._opts.fitting,
                        t = this._aspectRatio,
                        i = this.getContainerDimensions(),
                        n = CeltraPlayerUtils.fitComponent(100 * t, 100, i.width, i.height, e);
                    switch (this.anchoringPoint) {
                        case "top":
                            n.marginVertical = 0;
                            break;
                        case "bottom":
                            n.marginVertical = Math.ceil(i.height - n.height)
                    }
                    this._opts.videoElement || (this._wrapperEl.style.background = this._opts.barColor);

                    function s(e) {
                        e.width = n.width + "px", e.height = n.height + "px", e.minHeight = n.height + "px", e.left = n.marginHorizontal + "px", e.top = n.marginVertical + "px"
                    }
                    e ? (s(this._engineContainerEl.style), s(this._posterEl.style), (t = this._controlsWrapperEl.style).width = i.width + "px", t.height = i.height + "px", t.minHeight = i.height + "px", t.left = 0, t.top = 0) : (s(this._engineContainerEl.style), s(this._posterEl.style), s(this._controlsWrapperEl.style))
                },
                onSeeking: function() {
                    this.emit("seeking")
                },
                onSeeked: function(e) {
                    if (this._seeked) {
                        switch (this._seeked = !1, this._stateBeforeSeek) {
                            case CeltraPlayerUtils.PLAYING:
                                this.play();
                                break;
                            case CeltraPlayerUtils.PAUSED:
                                this.silentPause()
                        }
                        this._stateBeforeSeek = void 0
                    }
                    this.emit("seeked")
                },
                onError: function() {
                    this.emit("error")
                },
                onCanUnMute: function() {
                    this.emit("canunmute")
                },
                onCustomInfo: function(e) {
                    this.emit("custominfo", e)
                }
            }), extend(t.prototype, {
                getState: function() {
                    return this._state
                },
                mute: function() {
                    this._videoEngine && "function" == typeof this._videoEngine.mute && this._videoEngine.mute(), this.onMuted()
                },
                unMute: function() {
                    this._videoEngine && "function" == typeof this._videoEngine.unMute && this._videoEngine.unMute(), this.onUnMuted()
                },
                silentPlay: function() {
                    this._videoEngine.play()
                },
                createSpecs: function(e, t) {
                    e = this._videoEngineSpecProvider(e, t), e = merge(e, {
                        options: this._opts
                    }), this._opts.videoStream && this._opts.videoStream.useRaw && (e.videoUrl = (creative.secure ? creative.cachedApiUrl : creative.insecureCachedApiUrl) + "blobs/" + this._opts.videoStream.blobHash), t = AudioEngineProvider.createAudioEngineSpec(e);
                    return {
                        videoEngineSpec: e,
                        videoSources: e.videoUrl ? [CeltraPlayerUtils.createSourceObject(e.videoUrl, null)] : VideoEngineProvider.createVideoSourceObjects(e, this._opts.videoStream),
                        audioEngineSpec: t,
                        audioSources: t ? AudioEngineProvider.createAudioSourceObjects(this._opts.videoStream) : null
                    }
                },
                initialize: function(e) {
                    this._createEngines(e), CeltraPlayerUtils.isNode(this._engineEl) && !this._engineEl.parentNode && this._engineContainerEl.appendChild(this._engineEl), e.videoEngineSpec.options.videoElement && (this._wrapperEl.style.background = "transparent"), CeltraPlayerUtils.attachHandlers(this, this._videoEngine, n), void 0 !== e.videoEngineSpec.startMuted && (e.videoEngineSpec.startMuted ? this.mute() : this.unMute()), this._initialized = !0
                },
                play: function(e) {
                    null == this._videoEngine ? Logger("VideoPlayer").log("Video not initialized!") : (this.isState(CeltraPlayerUtils.UNSTARTED, CeltraPlayerUtils.ENDED) && this.changeState(CeltraPlayerUtils.BUFFERING), this._firstPlay && !e && this.unMute(), this._firstPlay = !1, this._videoEngine.play(), this.emit("userplayed"))
                },
                _createEngines: function(e) {
                    var t;
                    null === this._videoEngine && (this._engineType = e.videoEngineSpec.engineType, this._videoPresets = e.videoEngineSpec.presets, "JsVideoEngine" == e.videoEngineSpec.clazz ? this._videoEngine = new JsVideoEngine(e.videoSources[0].url, this._engineType, e.videoEngineSpec) : "Html5VideoEngine" == e.videoEngineSpec.clazz && (this._videoEngine = Html5VideoEngine(e.videoSources, e.videoEngineSpec)), null !== e.audioEngineSpec && (t = null, "WebAudioEngine" == e.audioEngineSpec.clazz ? (t = WebAudioEngine(e.audioSources[0].url, e.audioEngineSpec.preload), Object.create(AVSyncWebAudio).init(this, t, e.videoEngineSpec.startMuted)) : "Html5AudioEngine" == e.audioEngineSpec.clazz && (t = Html5AudioEngine(e.audioSources, e.audioEngineSpec.preload), Object.create(AVSyncAudio).init(this, t, e.videoEngineSpec.startMuted))), this._engineEl = this._videoEngine.render(this._doc, {
                        playsInline: this.playsInline()
                    }))
                },
                silentPause: function() {
                    this._videoEngine.pause()
                },
                pause: function() {
                    this._videoEngine && this._videoEngine.pause(), this.emit("userpaused"), this._playingEmitted = !1
                },
                close: function() {
                    this.playsInline() ? this.exitFullScreen() : this.emit("close")
                },
                replay: function() {
                    this.emit("replayed"), this.setCurrentTime(0), this._playingEmitted = !1, this.play()
                },
                reset: function() {
                    this._videoEngine.pause(), this._videoEngine.reset(), this._posterShow(), this._controlsController && this._controlsController.reset(), this._timeUpdateCounter = 0, this._playingEmitted = !1
                },
                enterFullScreen: function() {
                    if (this._videoEngine) {
                        if (this._containerElzIndex = this._containerEl.style.zIndex, this.dtCanFullscreen) {
                            var e = this._wrapperEl;
                            (e.requestFullscreen || e.webkitRequestFullscreen || e.mozRequestFullScreen || e.msRequestFullscreen).bind(e)()
                        } else {
                            this.isFullScreen = !0;
                            var t = this._wrapperEl;
                            for (this.isState(CeltraPlayerUtils.PAUSED); t && !t.getAttribute("class").includes("celtra-base-creative-unit");) t = t.parentNode;
                            t.insertBefore(this._wrapperEl, t.firstChild), this._wrapperEl.style.zIndex = CeltraPlayerUtils.ZINDEX_MAX, this.redraw(), this.silentPauseActive = !0, defer(function() {
                                this.silentPauseActive = !1
                            }.bind(this), 500), this._initialized && this._videoEngine.pause()
                        }
                        this._initialized && this._videoEngine.play(), this.emit("enterfullscreen")
                    }
                },
                exitFullScreen: function() {
                    var e;
                    this.dtCanFullscreen ? ((e = document).exitFullscreen || e.webkitExitFullscreen || e.mozCancelFullScreen || e.msExitFullscreen).bind(e)() : (this.isFullScreen = !1, this.isState(CeltraPlayerUtils.PAUSED, CeltraPlayerUtils.ENDED), this._wrapperEl.style.zIndex = "", this._containerEl.style.zIndex = this._containerElzIndex, this._containerEl.appendChild(this._wrapperEl), this.silentPauseActive = !0, defer(function() {
                        this.silentPauseActive = !1
                    }.bind(this), 500), this.once("pause", CeltraPlayerUtils.validFor(function() {
                        this.play(), this._muted || defer(this.unMute.bind(this), 100)
                    }.bind(this), 500))), this.redraw(), this.emit("exitfullscreen")
                },
                hide: function() {
                    this._wrapperEl && (this._wrapperEl.style.display = "none")
                },
                show: function() {
                    this._wrapperEl && (this._wrapperEl.style.display = "")
                },
                destroy: function() {
                    this._videoEngine && (this._videoEngine.pause(), this._videoEngine.destroy(), this._controlsController && this._controlsController.destroy());
                    var e = function() {
                        CeltraPlayerUtils && this._wrapperEl && CeltraPlayerUtils.removeElements(this._wrapperEl), this._toggleDesktopFSStateHandlers("remove"), this._wrapperEl = null
                    }.bind(this);
                    "undefined" != typeof TouchEventSimulator ? defer(e, 100) : e(), this.emit("destroy")
                },
                getDuration: function() {
                    return this.duration
                },
                getCurrentTime: function() {
                    return this._videoEngine ? this._videoEngine.getCurrentTime() : null
                },
                setCurrentTime: function(e) {
                    this._videoEngine && this._videoEngine.setCurrentTime(e)
                },
                seekTo: function(e) {
                    this.emit("seekto", e), this._seeked || (this._stateBeforeSeek = this.getState(), this.getState() === CeltraPlayerUtils.PLAYING && this.pause()), this._seeked = !0, this._videoEngine.seekToRatio(e)
                }
            }), t.prototype._hasToBeRepeated = function() {
                return this._repeated < this._opts.repeatTimes
            }, t.prototype._doRepeat = function() {
                this._repeated += 1, this.setCurrentTime(this._opts.playFrom || 0), this.play(), this.emit("repeat")
            }, t.prototype._doEnd = function() {
                this._opts.pauseOnEnd ? this.pause() : this._posterShow(), this._timeUpdateCounter = 0, this.changeState(CeltraPlayerUtils.ENDED), this.emit("ended"), this._opts.closeFSOnEnd && this.isFullScreen && this.exitFullScreen()
            }, t.prototype._onFullscreenChange = function() {
                this.isFullScreen ? (this.exitFullScreen(), this.isFullScreen = !1) : (this.isFullScreen = !0, this.redraw())
            }, t.prototype._toggleDesktopFSStateHandlers = function(e) {
                var t = e + "EventListener",
                    e = ["", "webkit", "moz", "ms"].some(function(e) {
                        return void 0 !== document["on" + e + "fullscreenchange"] && (document[t]("ms" !== e ? e + "fullscreenchange" : "MSFullscreenChange", this._onFullscreenChange), !0)
                    }.bind(this));
                return i[t] && i[t]("resize", this.redraw), e && !!(document.fullscreenEnabled || document.webkitFullscreenEnabled || document.mozFullScreenEnabled || document.msFullscreenEnabled)
            }, t.prototype.playsInline = function() {
                return "permanent" !== this._opts.fullscreenMode
            }, i.VideoPlayer = t
        }(window);;
        ! function() {
            function t(t, i) {
                this.maxPlayingSegmentLength = t, this.callback = i, this.reset()
            }
            t.prototype.init = function(t) {
                this._startPlayingPosition = t, this._lastPlayingPosition = t
            }, t.prototype.addEvent = function(t) {
                t > this._lastPlayingPosition && (this._lastPlayingPosition = t), this._lastPlayingPosition - this._startPlayingPosition >= this.maxPlayingSegmentLength && this.flush()
            }, t.prototype.flush = function(t) {
                var i, n;
                null != this._startPlayingPosition && (i = this._startPlayingPosition, n = this._lastPlayingPosition, (t || .4 < n - i) && i < n && (this.callback({
                    from: i,
                    to: n
                }), this._startPlayingPosition = this._lastPlayingPosition))
            }, t.prototype.reset = function() {
                this._startPlayingPosition = null, this._lastPlayingPosition = null
            }, window.PlayedSegmentComputationUnit = t
        }();;
        ! function() {
            var o = [void 0, null, 0, 1];
            var i = {
                videoStart: function(n, t, i) {
                    return t > Math.min(1, .25 * i)
                },
                videoFirstQuartile: function(n, t, i) {
                    i *= .25;
                    return n < i && i <= t
                },
                videoMidpoint: function(n, t, i) {
                    i *= .5;
                    return n < i && i <= t
                },
                videoThirdQuartile: function(n, t, i) {
                    i *= .75;
                    return n < i && i <= t
                },
                videoComplete: function(n, t, i) {
                    return t > Math.max(.75 * i, i - 2)
                }
            };
            window.QuartileEventsEmitter = function(n) {
                n.QUARTILE_EVENTS = i;
                var r = {},
                    u = (Object.keys(i).forEach(function(n) {
                        r[n] = i[n]
                    }), null),
                    t = function() {
                        var n, t = null === u || null !== u && this.currentTime > u ? this.currentTime : u,
                            i = (n = this).serverReportedDuration || (-1 !== o.indexOf(n.duration) ? null : n.duration);
                        if (null !== i && null !== u && t && t - u < 1)
                            for (var e in 0 < u && u < 1 && (u = 0), r) r[e](u, t, i) && (this.emit(e, {
                                name: e,
                                label: this.componentName
                            }), delete r[e]);
                        u = t
                    }.bind(n);
                return n.on("timeupdate", t), n.on("destroy", function() {
                    n.off("timeupdate", t)
                }), n
            }
        }();;
        ! function() {
            function t(t, e) {
                for (var i in this._video = t, this._lastObservedDuration = null, this._lastObservedPosition = null, this._intervalId = null, this._firstSegmentEmitted = !1, this._running = !1, this._currentTime = 0, this._playedSegmentsCount = 0, this.stop = this.stop.bind(this), this.stopAndReset = this.stopAndReset.bind(this), this.start = this.start.bind(this), this.tick = this.tick.bind(this), this.flush = this.flush.bind(this), this.reemitQuartileEvent = function(t) {
                        this.emit(t.name, t), this._playedSegmentComputationUnit.flush()
                    }.bind(this), this.trackDurationChange = this.trackDurationChange.bind(this), this.stitchAndTrackVideoPlayedSegment = this.stitchAndTrackVideoPlayedSegment.bind(this), this._viewDirectionsObserver = "function" == typeof ViewDirectionsObserver && this._video instanceof VideoPlayer360 ? new ViewDirectionsObserver(t, this.getCurrentTime.bind(this)) : null, this._playedSegmentComputationUnit = new PlayedSegmentComputationUnit(1, this.stitchAndTrackVideoPlayedSegment), this._lastVideoPlayedSegment = {
                        from: 0,
                        to: 0
                    }, this._serverReportedDuration = t.serverReportedDuration, t.serverReportedDuration ? (defer(function() {
                        this.emit("videoDurationUpdate", {
                            duration: t.serverReportedDuration
                        })
                    }.bind(this)), this.checkDuration = !1) : (this.checkDuration = !0, t.on("durationchange", this.trackDurationChange)), t.on("pause", this.stop), t.on("timeupdate", this.start), t.on("ended", this.stopAndReset), t.on("repeat", this.stopAndReset), t.on("playing", this.start), t.on("seeked", this.start), s().on("mediaStopRequested", this.stop), t.on("muted", this.flush), t.on("unmuted", this.flush), t.on("enterfullscreen", this.flush), t.on("exitfullscreen", this.flush), t.QUARTILE_EVENTS) t.on(i, this.reemitQuartileEvent)
            }
            var n = [void 0, null, 0, 1],
                r = [100, 300, 6e3],
                s = (extend(t.prototype, EventEmitter), function() {
                    return "undefined" != typeof creative ? creative.adapter : adapter
                });
            t.prototype.stitchAndTrackVideoPlayedSegment = function(t) {
                t = {
                    from: this._lastVideoPlayedSegment.to,
                    to: t.to
                };
                this.trackVideoPlayedSegment(t)
            }, t.prototype.trackVideoPlayedSegment = function(t) {
                var e;
                this._lastVideoPlayedSegment = t, this._serverReportedDuration && t.from >= this._serverReportedDuration || (e = !this._serverReportedDuration || t.to < this._serverReportedDuration ? t.to : this._serverReportedDuration, t = extend({}, t, {
                    to: e,
                    name: "videoPlayedSegment",
                    muted: !!this._video.muted,
                    fullscreen: void 0 === this._video.fullscreen || this._video.fullscreen,
                    gaze: this._video.isUserGazing()
                }), this._viewDirectionsObserver && (t.viewDirections = this._viewDirectionsObserver.getDirections()), this.emit("videoPlayedSegment", t), this._playedSegmentsCount += 1)
            }, t.prototype.getCurrentTime = function() {
                return this._video && this._video.currentTime > this._currentTime && (this._currentTime = this._video.currentTime), this._currentTime
            }, t.prototype.start = function() {
                this._running || (this._running = !0, this._viewDirectionsObserver && this._viewDirectionsObserver.start(), s().mediaState.startVideo(), this._firstSegmentEmitted || (this._currentTime = this.getCurrentTime() + .01, this.trackVideoPlayedSegment({
                    from: 0,
                    to: this.getCurrentTime()
                }), this._firstSegmentEmitted = !0), this._playedSegmentComputationUnit.init(this.getCurrentTime()), clearInterval(this._intervalId), this._intervalId = setInterval(this.tick, 250))
            }, t.prototype.stopAndReset = function() {
                this.stop(!0), this._playedSegmentComputationUnit && (this._playedSegmentComputationUnit.reset(), this._currentTime = 0, this._firstSegmentEmitted = !1, this._viewDirectionsObserver && this._viewDirectionsObserver.reset())
            }, t.prototype.flush = function() {
                this._playedSegmentComputationUnit && this._playedSegmentComputationUnit.flush()
            }, t.prototype.stop = function(t) {
                (this._running || t) && (this._running && (s().mediaState.stopVideo(), this._running = !1, clearInterval(this._intervalId)), this._viewDirectionsObserver && this._viewDirectionsObserver.stop(), this._playedSegmentComputationUnit.addEvent(this.getCurrentTime()), this._playedSegmentComputationUnit.flush(t))
            }, t.prototype.trackDurationChange = function() {
                var t, e, i, s;
                this.checkDuration && (t = this._video.duration, e = -1 !== n.indexOf(t), i = -1 !== r.indexOf(t), s = t !== this._lastObservedDuration, e || i || !s || (this._serverReportedDuration = t, this.emit("videoDurationUpdate", {
                    duration: t
                }), this._lastObservedDuration = t))
            }, t.prototype.tick = function() {
                this.trackDurationChange();
                var t = this.getCurrentTime(),
                    e = Date.now() - (this._lastTickTime || Date.now());
                t == this._lastObservedPosition && 1e3 < e && this.stop(), this._playedSegmentComputationUnit.addEvent(t), this._lastObservedPosition = t, this._lastTickTime = Date.now()
            }, t.prototype.destroy = function() {
                var t, e = this._video;
                for (t in this.stop(), e.off("pause", this.stop), e.off("ended", this.stopAndReset), e.off("playing", this.start), e.off("timeupdate", this.start), e.off("durationchange", this.tick), e.off("muted", this.flush), e.off("unmuted", this.flush), e.off("enterfullscreen", this.flush), e.off("exitfullscreen", this.flush), e.off("durationchange", this.trackDurationChange), e.QUARTILE_EVENTS) e.off(t, this.reemitQuartileEvent);
                s().off("mediaStopRequested", this.stop), this._playedSegmentComputationUnit.flush()
            }, this.VideoEventsAdapter = t
        }();;
        var CCalendar = {
            saveTheDateAction: function(e, t, a) {
                var n = {
                    start: t.start.value,
                    end: t.end.value
                };
                t.eventName && (n.eventName = t.eventName), t.location && (n.location = t.location), t.reminder && (n.reminder = t.reminder), t.notes && (n.notes = t.notes), creative.adapter.sendToEventMonitor("dateSaved", t.triggerId, e.screen.name, n, null, e.initiatedBeforeScreenShown()), e.track({
                    name: "dateSaved",
                    label: t.reportLabel
                }), creative.adapter._stopAllMedia(), creative.adapter.saveCalendarEvent(t, a)
            },
            getEventICSUrl: function(a, e) {
                var t, n = {
                    format: "ics"
                };
                return ["eventName", "location", "start", "end", "timezone", "reminder", "notes"].forEach(function(e) {
                    var t;
                    e in a && ("string" == typeof(t = "object" == typeof(t = a[e]) && t.hasOwnProperty("value") ? t.value : t) && t.length && (n[e] = t))
                }), n.start && "eventName" in n ? (n.end && 10 === n.end.length && (t = new Date(Date.parse(n.end) + 864e5), n.end = t.getFullYear() + "-" + ("0" + (t.getMonth() + 1)).slice(-2) + "-" + ("0" + t.getDate()).slice(-2)), urls.cachedApiUrl.replace(/^https:/, e) + "calendar?" + buildQuery(n)) : null
            },
            getEventGoogleCalendarUrl: function(e) {
                function t(e, t) {
                    var a;
                    return a = (e = e)[t = t].value.replace(" ", "T"), e.allDay && (a = a.substr(0, 10) + "T" + ("start" === t ? "00:00:00" : "23:59:59"), e.timezone = "user"), new Date(a + ("user" === e.timezone ? (t = a, t = new Date(t).getTimezoneOffset(), e = Math.abs(t), a = Math.floor(e / 60), (0 < t ? "-" : "+") + n(a) + ":" + n(e - 60 * a)) : "+00:00")).toISOString().replace(/[-:]|(?:\.[0-9]*)/g, "")
                }

                function n(e) {
                    return ("0" + e).slice(-2)
                }
                var a = "https://calendar.google.com/calendar/" + (desktop() ? "render?" : "gp#~calendar:view=e&") + "action=TEMPLATE",
                    e = {
                        dates: t(e, "start") + "/" + t(e, "end"),
                        location: e.location,
                        text: e.eventName,
                        details: e.notes
                    };
                return a + "&" + buildQuery(e)
            },
            toString: function() {
                return "[Clazz CCalendar]"
            }
        };;

        function StateObject(e) {
            Object.defineProperties(this, {
                values: {
                    enumerable: !1,
                    configurable: !1,
                    writable: !0,
                    value: {}
                },
                _isDirty: {
                    enumerable: !1,
                    configurable: !1,
                    writable: !0,
                    value: !1
                }
            }), Object.keys(e).forEach(function(t) {
                this.registerValue(t, e[t])
            }, this)
        }
        extend(StateObject.prototype, EventEmitter), StateObject.prototype.registerValue = function(r, t) {
            this.values[r] = {
                dirty: !1,
                value: t
            }, Object.defineProperty(this, r, {
                get: function() {
                    return this.values[r].value
                },
                set: function(t) {
                    var e = this.values[r],
                        i = e.value;
                    t != i && (e.dirty = !0, e.value = t, this._isDirty = !0, this.emit("change:" + r, t, i))
                },
                enumerable: !0
            })
        }, StateObject.prototype.markClean = function(e) {
            var i = !1;
            Object.keys(this.values).forEach(function(t) {
                e && t !== e || (this[t].dirty = !1), i = i || this[t].dirty
            }, this.values), this._isDirty = i
        }, StateObject.prototype.getDirtyValues = function() {
            for (var t = {}, e = Object.keys(this.values), i = 0; i < e.length; i++) this.values[e[i]].dirty && (t[e[i]] = this.values[e[i]].value);
            return t
        }, StateObject.prototype.isDirty = function(t) {
            return void 0 === t ? this._isDirty : this.values[t].dirty
        }, StateObject.prototype.anyDirty = function(t) {
            1 < arguments.length && (t = Array.prototype.slice.apply(arguments));
            for (var e = 0; e < t.length; e++)
                if (this.values[t[e]].dirty) return !0;
            return !1
        }, StateObject.prototype.copy = function() {
            var t, e = {};
            for (t in this.values) e[t] = this[t];
            return new StateObject(e)
        }, StateObject.prototype.copyFrom = function(t) {
            for (var e in t.values) this[e] = t[e]
        };;

        function StateAnimation(t, i) {
            this.stateObject = t, this.propertyName = i, this.running = !1, this.lastUpdateTime = null, this.tick = this.tick.bind(this), this._callback = null
        }

        function SpringyAnimation(t, i, e) {
            StateAnimation.apply(this, arguments), this.options = extend({}, SpringyAnimation.defaults, e || {}), this.state = {
                x: t[i],
                v: 0
            }, this.derivative = {
                dx: 0,
                dv: 0
            }
        }

        function EasingAnimation(t, i, e) {
            StateAnimation.apply(this, arguments), this.options = extend({}, EasingAnimation.defaults, e || {}), this._startValue = null, this._startTime = null, this._targetValue = null, this._targetTime = null
        }
        StateAnimation.prototype.start = function() {
            this.running || (this.lastUpdateTime = null, this.running = !0, Ticker.frame(this.tick, "update"))
        }, StateAnimation.prototype.pause = function() {
            Ticker.removeFrame(this.tick, "update"), this.running = !1
        }, StateAnimation.prototype.tick = function() {
            var t = Date.now();
            this.update(t - (this.lastUpdateTime || t)), this.lastUpdateTime = t
        }, StateAnimation.prototype.update = function(t) {
            throw new Error("Not implemented")
        }, Object.defineProperty(StateAnimation.prototype, "value", {
            get: function() {
                return this.stateObject[this.propertyName]
            },
            set: function(t) {
                this.stateObject[this.propertyName] = t
            },
            enumerable: !0
        }), inherit(SpringyAnimation, StateAnimation), SpringyAnimation.defaults = {
            springForce: 1e3,
            damping: 20,
            restThreshold: 1
        }, SpringyAnimation.prototype.animateTo = function(t, i, e) {
            this.running && this.pause(), "function" == typeof i && (e = i, i = 0), this.d = t, this.state.v = "number" == typeof i ? i : 0, this.state.x = this.value, this._callback = e, this.start()
        }, SpringyAnimation.prototype.update = function(t) {
            t = Math.min(.03, t / 1e3);
            Math.abs(this.state.x - this.d) < this.options.restThreshold && Math.abs(this.state.v) < this.options.restThreshold ? (this.pause(), this.value = this.d, this.d = null, defer(this._callback || noop), this._callback = null) : (this._integrate(this.state, t), this.value = this.state.x)
        }, SpringyAnimation.prototype._integrate = function(t, i) {
            var e = this._getDerivative(this.state),
                a = this._getDerivative(this.state, .5 * i, e),
                n = this._getDerivative(this.state, .5 * i, a),
                s = this._getDerivative(this.state, i, n);
            t.x += i / 6 * (e.dx + 2 * (a.dx + n.dx) + s.dx), t.v += i / 6 * (e.dv + 2 * (a.dv + n.dv) + s.dv)
        }, SpringyAnimation.prototype._getDerivative = function(t, i, e) {
            return void 0 === e ? {
                dx: t.v,
                dv: this._acceleration(t)
            } : {
                dx: (t = {
                    x: t.x + e.dx * i,
                    v: t.v + e.dv * i
                }).v,
                dv: this._acceleration(t, i)
            }
        }, SpringyAnimation.prototype._acceleration = function(t) {
            return -this.options.springForce * (t.x - this.d) - this.options.damping * t.v
        }, inherit(EasingAnimation, StateAnimation), EasingAnimation.defaults = {
            exp: 1.8
        }, EasingAnimation.presets = {
            easeIn: .6,
            easeOut: 1.8,
            linear: 1
        }, EasingAnimation.prototype.animateTo = function(t, i, e) {
            this.running && this.pause(), this._callback = e, this._startValue = this.value, this._startTime = Date.now(), this._targetValue = t, this._targetTime = i, this.start()
        }, EasingAnimation.prototype.update = function(t) {
            var i = Date.now() - this._startTime,
                e = this._targetValue - this._startValue;
            i >= this._targetTime ? (this.pause(), this.value = this._targetValue, defer(this._callback || noop), this._startValue = null, this._startTime = null, this._targetValue = null, this._targetTime = null, this._callback = null) : this.value = Math.pow(i / this._targetTime, this.options.exp) * e + this._startValue
        };;

        function ViewportManager(t) {
            this.container = t.container, this.width = this.originalWidth = t.width || 0, this.height = this.originalHeight = t.height || 0, this.iframe = this.container.ownerDocument.defaultView.frameElement, this.init()
        }
        extend(ViewportManager.prototype, EventEmitter), ViewportManager.prototype.init = function() {
            this.container.style.overflow = "hidden", this.container.style.position = "absolute", this.container.style.left = this.container.style.top = 0, this.iframe.style.display = "block", this.update()
        }, ViewportManager.prototype.update = function() {
            this.container.style.width = this.width + "px", this.container.style.height = this.height + "px", this.iframe.style.width = this.width + "px", this.iframe.style.height = this.height + "px", this.iframe.width = this.width, this.iframe.height = this.height
        }, ViewportManager.prototype.setSize = function(t, i) {
            this.width = t, this.height = i, this.update()
        };;

        function Placement(t) {
            this.root = null, this.width = {
                value: 100,
                unit: "%"
            }, this.height = {
                value: 100,
                unit: "%"
            }, this.left = {
                value: 0,
                unit: "px"
            }, this.top = {
                value: 0,
                unit: "px"
            }, this.borders = {
                left: 0,
                right: 0,
                top: 0,
                bottom: 0
            }, this._positioning = t || "container", this.origin = {
                horizontal: "left",
                vertical: "top"
            }, this._usesFixedPositioning = !1, this._minWidth = {
                value: 0,
                unit: "px"
            }, this._minHeight = {
                value: 0,
                unit: "px"
            }, this.touchEventSimulator = null, this.overflow = !1, this._visible = !1, this.isSticky = !1, this.bypassSizing = !1, this.bypassPositioning = !1
        }
        extend(Placement.prototype, EventEmitter), Placement.ZINDEX_MAX = 2147483647, Placement.prototype.getRootWindow = function() {
            return this.root.ownerDocument.defaultView
        }, Placement.prototype.attachTo = function(t, e) {
            throw new Error("Placement.attachTo not implemented")
        }, Placement.prototype.getContainer = function() {
            throw new Error("Placement.getContainer not implemented")
        }, Placement.prototype.getContentWindow = function() {
            return this.getContainer().ownerDocument.defaultView
        }, Object.defineProperty(Placement.prototype, "creative", {
            get: function() {
                return this.getContainer().ownerDocument.defaultView.creative
            }
        }), Object.defineProperty(Placement.prototype, "positioning", {
            get: function() {
                return this._positioning
            },
            set: function(t) {
                this._positioning = t, this.update()
            }
        }), Object.defineProperty(Placement.prototype, "visible", {
            get: function() {
                return this._visible
            }
        }), Object.defineProperty(Placement.prototype, "_canUseFixedPositioning", {
            get: function() {
                return this.usesFixedPositioning && this.supportsFixedPositioning
            },
            enumerable: !1
        }), Placement.prototype.setMinimumSize = function(t, e) {
            t = this._parseLength(t), e = this._parseLength(e);
            this._minWidth.value = t.value, this._minWidth.unit = t.unit, this._minHeight.value = e.value, this._minHeight.unit = e.unit, this._visible && this.update()
        }, Placement.prototype.setSize = function(t, e, o) {
            var i, t = this._parseLength(t),
                e = this._parseLength(e),
                s = this.getViewportGeometry(),
                n = extend({}, this._minWidth),
                r = extend({}, this._minHeight);
            o || (this.width = {
                value: t.value,
                unit: t.unit
            }, this.height = {
                value: e.value,
                unit: e.unit
            }), this._visible && (o = "screen" == this.positioning ? (i = s.width, s.height) : ("page" == this.positioning ? (i = this.root.ownerDocument.documentElement.offsetWidth, this.root.ownerDocument.documentElement) : (i = this.root.parentNode.offsetWidth, this.root.parentNode)).offsetHeight, "%" == t.unit && (t.value = Math.round(t.value * i / 100), t.unit = "px"), "%" == e.unit && (e.value = Math.round(e.value * o / 100), e.unit = "px"), "%" == n.unit && (n.value = Math.round(n.value * i / 100), n.unit = "px"), "%" == r.unit && (r.value = Math.round(r.value * o / 100), r.unit = "px"), this.bypassSizing || (this.root.style.width = Math.max(t.value, n.value) + t.unit, this.root.style.height = Math.max(e.value, r.value) + e.unit), this.emit("resized"))
        }, Placement.prototype.getBaseOffset = function() {
            var t = this.root.ownerDocument.createElement("div"),
                e = this.root.parentNode,
                o = (t.style.left = 0, t.style.top = 0, t.style.position = "absolute", t.style.setProperty("display", "block", "important"), e.appendChild(t), offset(t));
            return e.removeChild(t), o
        }, Placement.prototype.applyCustomPubClasses = function() {
            var t = this._getPubClasses();
            this._usePubClasses = 0 < t.length, this._usePubClasses && (this._disableSelfStyling(), this.addClasses.apply(this, t.concat(["celtra-placement"])))
        }, Placement.prototype.usePubClasses = function() {
            return this._usePubClasses
        }, Placement.prototype._disableSelfStyling = function() {
            this.bypassPositioning = !("StickyPlacement" === this.constructor.name || this.isSticky), this.bypassSizing = !0, this._clearCSS(), this.bypassPositioning && (this.root.style.position = "relative")
        }, Placement.prototype._getPubClasses = function() {
            var t = this.creative && this.creative.customAttributes,
                e = [];
            return e = t && t.hasOwnProperty("cssCustomClass") && "string" == typeof t.cssCustomClass ? t.cssCustomClass.split(",") : e
        }, Placement.prototype._clearCSS = function() {
            this.root.style = ""
        }, Placement.prototype.addClasses = function(t) {
            (t = Array.prototype.slice.apply(arguments).filter(function(t) {
                return !!t
            })).forEach(function(e) {
                try {
                    addClass(this.root, e)
                } catch (t) {
                    console.warn('Class "' + e + '" not added: ' + t.message)
                }
            }.bind(this))
        }, Placement.prototype.setBorders = function(t, e, o, i) {
            this.root.style.borderLeft = t + "px solid transparent", this.root.style.borderRight = e + "px solid transparent", this.root.style.borderTop = o + "px solid transparent", this.root.style.borderBottom = i + "px solid transparent", this.borders = {
                left: t,
                right: e,
                top: o,
                bottom: i
            }
        }, Placement.prototype.setPosition = function(t, e, o, i) {
            var s, n = this._parseLength(t),
                r = this._parseLength(e),
                h = this.getViewportGeometry(),
                l = this.getBaseOffset();
            if (o || (this.left.value = n.value, this.left.unit = n.unit, this.top.value = r.value, this.top.unit = r.unit), this._visible && !this.bypassPositioning) {
                switch (this.positioning) {
                    case "screen":
                        this._canUseFixedPositioning ? (this.root.style.position = "fixed", "left" == this.origin.horizontal ? (this.root.style.left = n.value + n.unit, this.root.style.right = "auto", this.root.style.marginLeft = "") : "center" == this.origin.horizontal ? (this.root.style.left = n.value + n.unit, this.root.style.right = "auto", this.root.style.marginLeft = i ? "" : (h.width - this.root.offsetWidth) / 2 + "px") : (this.root.style.right = n.value + n.unit, this.root.style.left = "auto", this.root.style.marginLeft = ""), "top" == this.origin.vertical ? (this.root.style.top = r.value + r.unit, this.root.style.bottom = "auto", this.root.style.marginTop = "") : "center" == this.origin.vertical ? (this.root.style.top = r.value + r.unit, this.root.style.bottom = "auto", this.root.style.marginTop = (h.height - this.root.offsetHeight) / 2 + "px") : (this.root.style.bottom = r.value + r.unit, this.root.style.top = "auto", this.root.style.marginTop = "")) : (s = u = "", "%" == n.unit && (n.value *= h.width / 100, n.unit = "px"), "%" == r.unit && (r.value *= h.height / 100, r.unit = "px"), "left" == this.origin.horizontal ? n.value += h.left : "center" == this.origin.horizontal ? (n.value += h.left, u = (h.width - this.root.offsetWidth) / 2 + "px") : (a = "%" == this.width.unit ? this.width.value / 100 * h.width : this.width.value + this.borders.left + this.borders.right, n.value = h.left + h.width - a - n.value), "top" == this.origin.vertical ? r.value += h.top : "center" == this.origin.vertical ? (r.value += h.top, s = (h.height - this.root.offsetHeight) / 2 + "px") : (a = "%" == this.height.unit ? this.height.value / 100 * h.height : this.height.value + this.borders.top + this.borders.bottom, r.value = h.top + h.height - a - r.value), this.root.style.position = "absolute", this.root.style.left = n.value - l.left + "px", this.root.style.top = r.value - l.top + "px", this.root.style.marginLeft = u, this.root.style.marginTop = s, this.root.style.right = this.root.style.bottom = "auto");
                        break;
                    case "container":
                        this.root.style.position = "absolute", "left" == this.origin.horizontal ? (this.root.style.left = n.value + n.unit, this.root.style.right = "auto", this.root.style.marginLeft = "") : "center" == this.origin.horizontal ? (this.root.style.left = n.value + n.unit, this.root.style.right = "auto", "px" == this.height.unit ? this.root.style.marginLeft = -Math.round(this.width.value / 2) + "px" : this.root.style.marginLeft = -this.root.offsetWidth / 2 + "px") : (this.root.style.left = "auto", this.root.style.right = n.value + n.unit, this.root.style.marginLeft = ""), "top" == this.origin.vertical ? (this.root.style.top = r.value + r.unit, this.root.style.bottom = "auto", this.root.style.marginTop = "") : "center" == this.origin.vertical ? (this.root.style.top = r.value + r.unit, this.root.style.bottom = "auto", "px" == this.height.unit ? this.root.style.marginTop = -Math.round(this.height.value / 2) + "px" : this.root.style.marginTop = -this.root.offsetHeight / 2 + "px") : (this.root.style.top = "auto", this.root.style.bottom = r.value + r.unit, this.root.style.marginTop = "");
                        break;
                    case "page":
                        var a = this.root.ownerDocument.documentElement.clientWidth,
                            u = this.root.ownerDocument.documentElement.clientHeight;
                        "%" == n.unit && (n.value = Math.round(n.value * a / 100), n.unit = "px"), "%" == r.unit && (r.value = Math.round(r.value * u / 100), r.unit = "px"), this.root.style.position = "absolute", "left" == this.origin.horizontal ? (this.root.style.left = n.value - l.left + "px", this.root.style.top = r.value - l.top + "px") : "center" == this.origin.horizontal ? (this.root.style.left = (a - this.root.offsetWidth) / 2 - l.left + n.value + "px", this.root.style.top = (u - this.root.offsetHeight) / 2 - l.top + r.value + "px") : (this.root.style.left = a - this.root.offsetWidth - l.left - n.value + "px", this.root.style.top = u - this.root.offsetHeight - l.top - r.value + "px"), this.root.style.right = this.root.style.bottom = "auto";
                        break;
                    case "static":
                        this.root.style.position = "static", this.root.style.left = this.root.style.top = 0, this.root.style.right = this.root.style.bottom = "auto", this.root.style.marginLeft = this.root.style.marginTop = ""
                }
                this.emit("repositioned")
            }
        }, Placement.prototype.update = function(t) {
            this.setSize(this.width.value + this.width.unit, this.height.value + this.height.unit, !0), this.setPosition(this.left.value + this.left.unit, this.top.value + this.top.unit, !0), defer(t || noop, void 0, void 0, useAsap())
        }, Placement.prototype.supportsFixedPositioning = !0, Object.defineProperty(Placement.prototype, "usesFixedPositioning", {
            get: function() {
                return this._usesFixedPositioning
            },
            set: function(t) {
                this._usesFixedPositioning = !!t, this.update()
            },
            enumerable: !0
        }), Placement.prototype.hide = function() {
            this.root.style.display = "none", this._visible = !1, this.emit("hidden")
        }, Placement.prototype.show = function() {
            this.root.style.display = "block", this._visible = !0, this.update(), this.emit("shown")
        }, Placement.prototype.destroy = function() {
            this.emit("destroyed"), this.root && this.root.parentNode && this.root.parentNode.removeChild(this.root), this.root = null
        }, Placement.prototype.setZIndex = function(t) {
            "max" == (t = t < 0 ? Placement.ZINDEX_MAX + t : t) && (t = Placement.ZINDEX_MAX), this.root.style.zIndex = t
        }, Placement.prototype.createElement = function(t) {
            throw new Error("Not implemented")
        }, Placement.prototype.querySelector = function(t) {
            throw new Error("Not implemented")
        }, Placement.prototype.hidePlacementFromScreenReader = function() {}, Placement.prototype.getViewportGeometry = function(t) {
            return getViewportGeometry(this.getRootWindow(), t)
        }, Placement.prototype.getPlacementGeometry = function() {
            var t, e;
            return "screen" == this.positioning && this._canUseFixedPositioning ? {
                width: this.root.offsetWidth,
                height: this.root.offsetHeight,
                left: this.root.offsetLeft,
                top: this.root.offsetTop
            } : (t = offset(this.root), e = this.getViewportGeometry(), {
                width: this.root.offsetWidth,
                height: this.root.offsetHeight,
                left: t.left - e.left,
                top: t.top - e.top
            })
        }, Placement.prototype.getGlobalGeometry = function(t) {
            for (var e = this.getRootWindow(), o = this.root.getBoundingClientRect(), i = CRect.adopt(o); e !== t && e !== e.parent;) o = CRect.adopt(e.frameElement.getBoundingClientRect()), i.left += o.left, i.top += o.top, i = i.intersect(o), e = e.parent;
            return i
        }, Placement.prototype._parseLength = function(t) {
            if (!isNaN(t)) return {
                value: t,
                unit: "px"
            };
            var e = t.match(/^(-?[\d.]+)(px|%)$/);
            if (e) return {
                value: parseFloat(e[1], 10),
                unit: e[2]
            };
            throw new Error('Cannot parse length "' + t + '"')
        }, Placement.prototype.getUnitGeometry = function(t, e) {
            var e = e ? getElementRectRelativeToTopViewport(this.root) : this.getPlacementGeometry(),
                o = {
                    left: e.left,
                    top: e.top,
                    width: t.size.width,
                    height: t.size.height
                },
                i = t.horizontalPosition || "center",
                s = t.verticalPosition || "center";
            return "center" == i ? o.left += (e.width - t.size.width) / 2 : "right" == i && (o.left += e.width - t.size.width), "center" == s ? o.top += (e.height - t.size.height) / 2 : "bottom" == s && (o.top += e.height - t.size.height), o.left = Math.round(o.left), o.top = Math.round(o.top), o
        }, Placement.prototype.getRelativeUnitGeometry = function(t) {
            var e = offset(t.node);
            return e.width = t.node.offsetWidth, e.height = t.node.offsetHeight, e
        }, Placement.prototype.populate = function(t, e) {
            this.getContainer().appendChild(t), this.emit("populated"), e && defer(e, void 0, void 0, useAsap())
        }, Placement.prototype._getViewportElement = function() {
            throw new Error("Not implemented")
        }, Placement.prototype.setBackground = function(t) {
            this.root.style.background = t = null == t ? "none" : t
        }, Placement.prototype.setOverflow = function() {
            function o(t) {
                t.preventDefault()
            }
            return function(t) {
                var e = this._getViewportElement();
                t ? (e.style.overflowY = "auto", ios() && (e.style.webkitOverflowScrolling = "touch", detach(e, "touchmove", o, !1))) : (e.style.overflowY = "hidden", ios() && (e.style.webkitOverflowScrolling = "auto", attach(e, "touchmove", o, !1)))
            }
        }(), Placement.prototype._flash = function() {
            var t = this.getContainer().ownerDocument,
                e = this.createElement("div");
            e.style.cssText = "position:absolute;top:0;left:0;width:100%;height:100%;opacity:0.01;background:black;", t.body.appendChild(e), defer(function() {
                e.parentNode.removeChild(e)
            })
        };;

        function IframePlacement(t, e, n, r) {
            Placement.apply(this, arguments), this.frame = null, this.vm = null, this.unitScript = e, this.windowVarsByRef = n, this.windowVarsByCopy = r, this._syncInterval = null, this._syncIframeSize = this._syncIframeSize.bind(this)
        }
        inherit(IframePlacement, Placement), IframePlacement.create = function(t, e, n, r) {
            e = new IframePlacement(e, "", n, r);
            return e.root = t.parentNode, e.root.style.display = "none", e.startSyncingIframeSize(), e.frame = t, e.setupFrame(), addClass(e.root, "notranslate"), e
        }, IframePlacement.baseHTML = '<!DOCTYPE html><html><head><meta charset="utf-8"/><meta name="viewport" content="initial-scale=1,maximum-scale=1"/></head><body><div id="viewport"></div></body></html>', IframePlacement.prototype.setSize = function() {
            IframePlacement.uber.setSize.apply(this, arguments), this._syncIframeSize()
        }, IframePlacement.prototype.show = function() {
            IframePlacement.uber.show.apply(this, arguments), this._syncIframeSize()
        }, IframePlacement.prototype._syncIframeSize = function() {
            this._visible && this.vm && (this.root.offsetWidth != this.vm.width || this.root.offsetHeight != this.vm.height) && this.vm.setSize(0 | this.root.offsetWidth, 0 | this.root.offsetHeight)
        }, IframePlacement.prototype.startSyncingIframeSize = function() {
            this._syncInterval = this._setInterval(this._syncIframeSize, 100)
        }, IframePlacement.prototype.stopSyncingIframeSize = function() {
            this._syncInterval && this._clearInterval(this._syncInterval), this._syncInterval = null
        }, IframePlacement.prototype.getContainer = function() {
            if (this.root) return this.querySelector("#viewport");
            throw new Error("Root node not attached to document yet!")
        }, IframePlacement.prototype._setInterval = function(t, e) {
            return this.windowVarsByRef.adapter.getTopWindow().setInterval(t, e)
        }, IframePlacement.prototype._clearInterval = function(t, e) {
            return this.windowVarsByRef.adapter.getTopWindow().clearInterval(t, e)
        }, IframePlacement.prototype.attachTo = function(t, r) {
            this.root = t.ownerDocument.createElement("div"), this.root.style.overflow = "hidden", addClass(this.root, "notranslate"), this.hide(), t.appendChild(this.root), this.startSyncingIframeSize(), this.frame = t.ownerDocument.createElement("iframe"), this.frame.style.border = "0px", this.frame.setAttribute("frameborder", "0"), this.frame.setAttribute("scrolling", "no"), this.frame.setAttribute("allowFullScreen", ""), this.root.appendChild(this.frame), defer(function() {
                if (!this.frame.contentWindow) throw new Error("Can't access contentWindow of an iframe, skip placement initialization.");
                for (var t in this.frame.contentDocument.open(), this.windowVarsByRef) this.frame.contentWindow[t] = this.windowVarsByRef[t];
                var e = "";
                for (t in this.windowVarsByCopy) e += "window." + t + " = " + JSON.stringify(this.windowVarsByCopy[t]) + ";\n";
                this.frame.contentDocument.write(IframePlacement.baseHTML);
                var n = this.frame.contentDocument.createElement("script");
                n.textContent = e + ";\n" + this.unitScript, this.frame.contentDocument.body.appendChild(n), attach(this.frame, "load", function() {
                    this.setupFrame(), r && defer(r, void 0, void 0, useAsap())
                }.bind(this)), this.frame.contentDocument.close()
            }.bind(this), void 0, void 0, useAsap())
        }, IframePlacement.prototype.destroy = function() {
            this.stopSyncingIframeSize(), IframePlacement.uber.destroy.apply(this, arguments)
        }, IframePlacement.prototype.createElement = function(t) {
            return this.frame.contentDocument.createElement(t)
        }, IframePlacement.prototype.querySelector = function(t) {
            return this.frame.contentDocument ? this.frame.contentDocument.querySelector(t) : null
        }, IframePlacement.prototype.querySelectorAll = function(t) {
            return this.frame.contentDocument ? this.frame.contentDocument.querySelectorAll(t) : []
        }, IframePlacement.prototype._getViewportElement = function() {
            return this.querySelector("#viewport")
        }, IframePlacement.prototype.hidePlacementFromScreenReader = function() {
            this.frame.tabIndex = -1, this.frame.setAttribute("aria-hidden", "true")
        }, IframePlacement.prototype.setupFrame = function() {
            var t = this.frame.contentDocument.createElement("script");
            t.textContent = "window.touchEventSimulator = new TouchEventSimulator(document);window.touchEventSimulator.init();function __parseJSON(s){return JSON.parse(s);}", this.frame.contentDocument.querySelector("head").appendChild(t), this.touchEventSimulator = this.frame.contentWindow.touchEventSimulator, this.vm = new ViewportManager({
                container: this._getViewportElement()
            })
        };;

        function DivPlacement(t) {
            Placement.apply(this, arguments)
        }
        inherit(DivPlacement, Placement), DivPlacement.prototype.attachTo = function(t, e) {
            this.root = t.ownerDocument.createElement("div"), this.root.style.overflow = "hidden", addClass(this.root, "notranslate"), this.hide(), t.appendChild(this.root), this.touchEventSimulator = new TouchEventSimulator(this.root), this.touchEventSimulator.init(), e && defer(e, 0, "DivPlacement.attachTo defer callback", useAsap())
        }, DivPlacement.prototype.destroy = function() {
            DivPlacement.uber.destroy.apply(this, arguments), this.touchEventSimulator.stop(), this.touchEventSimulator = null
        }, DivPlacement.prototype.getContainer = function() {
            return this.root
        }, DivPlacement.prototype.createElement = function(t) {
            return this.root.ownerDocument.createElement(t)
        }, DivPlacement.prototype.querySelector = function(t) {
            return this.root.querySelector(t)
        }, DivPlacement.prototype.querySelectorAll = function(t) {
            return this.root.querySelectorAll(t)
        }, DivPlacement.prototype._getViewportElement = function() {
            return this.root
        };;

        function StickyPlacement(t, i, e, s, o) {
            IframePlacement.apply(this, arguments), this.stickyOptions = o, this._handleDismissTouchEnd = this._handleDismissTouchEnd.bind(this), this.origin.horizontal = "center", this.origin.vertical = this.stickyOptions.stickiness, this.usesFixedPositioning = !0, this._originalDocumentPadding = null
        }
        inherit(StickyPlacement, IframePlacement), StickyPlacement.create = function(t, i, e, s, o) {
            i = new StickyPlacement(i, "", e, s, o);
            return i.root = t.parentNode, i.root.style.display = "none", i.startSyncingIframeSize(), i.frame = t, i.setupFrame(), addClass(i.root, "notranslate"), i
        }, StickyPlacement.prototype.setPosition = StickyPlacement.prototype.setZIndex = noop, StickyPlacement.prototype.show = function() {
            IframePlacement.prototype.show.apply(this, arguments), IframePlacement.prototype.setZIndex.call(this, -20), this.enableDocumentPadding(), this.update()
        }, StickyPlacement.prototype.hide = function() {
            IframePlacement.prototype.hide.apply(this, arguments), this.disableDocumentPadding()
        }, StickyPlacement.prototype.attachTo = function() {
            StickyPlacement.uber.attachTo.apply(this, arguments), addClass(this.root, "celtra-placement-sticky")
        }, StickyPlacement.prototype.populate = function(t, i) {
            this.stickyOptions.showDismissButton && this.once("populated", this.createDismissButton.bind(this)), IframePlacement.prototype.populate.apply(this, arguments)
        }, StickyPlacement.prototype.update = function(t, i) {
            this.root && this.root.parentNode && (IframePlacement.prototype.setSize.call(this, this.width.value + this.width.unit, this.height.value + this.height.unit, !0), IframePlacement.prototype.setPosition.call(this, 0, 0, !0, i), defer(t))
        }, StickyPlacement.prototype.createDismissButton = function() {
            var viewport = this.getViewportGeometry();
            with(this.root.style.overflow = "visible", this._dismissButton = this.root.ownerDocument.createElement("img"), this._dismissButton.src = this.stickyOptions.baseUrl + "runner/clazzes/CreativeUnit/close-up.svg", this._dismissButton.style) switch (position = "absolute", left = "auto", right = "6px", width = "32px", height = "32px", zIndex = 10, this.stickyOptions.stickiness) {
                case "top":
                    top = "auto", bottom = "-16px";
                    break;
                case "bottom":
                    top = "-16px", bottom = "auto"
            }
            deviceInfo.deviceType.mobileDevice() ? attach(this._dismissButton, "touchend", this._handleDismissTouchEnd, !1) : attach(this._dismissButton, "click", this._handleDismissTouchEnd, !1), this.root.appendChild(this._dismissButton)
        }, StickyPlacement.prototype._handleDismissTouchEnd = function(t) {
            t.preventDefault(), t.stopPropagation(), this.disableDocumentPadding(), (this.stickyOptions.dismissCallback || noop)()
        }, StickyPlacement.prototype.enableDocumentPadding = function() {
            var t = this.getRootWindow(),
                i = t.document.documentElement;
            this._originalDocumentPadding || (this._originalDocumentPadding = t.getComputedStyle(i)["padding-" + this.stickyOptions.stickiness]), i.style["padding-" + this.stickyOptions.stickiness] = this.root.offsetHeight + "px"
        }, StickyPlacement.prototype.disableDocumentPadding = function() {
            this.getRootWindow().document.documentElement.style["padding-" + this.stickyOptions.stickiness] = this._originalDocumentPadding, this._originalDocumentPadding = null
        }, StickyPlacement.prototype.hideStickyCloseButton = function() {
            this._dismissButton.style.display = "none"
        }, StickyPlacement.prototype.showStickyCloseButton = function() {
            this._dismissButton.style.display = "block"
        };;

        function Format(e, t) {
            this.adapter = e, this.state = null, this.units = t, this.observesViewability = !0, this.observingViewability = function(e) {
                return this.observesViewability && this.adapter.viewabilityMeasurable["viewable" + e]
            }.bind(this), this._observesViewableTime = !1, this.observingViewableTime = this.observingViewableTime.bind(this), this.observationAbilities = {
                observingViewability: this.observingViewability,
                observingViewableTime: this.observingViewableTime
            }, this._allowNonNativeRAFForViewableTime = "1" == this.adapter.runtimeParams._allowNonNativeRAFForViewableTime, "Accessibility" in window && (this._accessibility = new Accessibility), this.adapter.nesting && this.adapter.nesting.hostileIframe && this.needsAccessToHostPage() && this.adapter.trackingCenter.track({
                name: "userError",
                userErrorId: "nonFriendlyIFrame"
            })
        }
        extend(Format.prototype, EventEmitter), Object.defineProperty(Format.prototype, "placements", {
            get: function() {
                return this.adapter.placements
            },
            enumerable: !0
        }), Format.prototype.observingViewableTime = function() {
            var e = this.adapter;
            return this._observesViewableTime && !!this.observingViewability("00") && e.raf.environment && (e.raf.isNative || this._allowNonNativeRAFForViewableTime) && (e.supportsContainerViewability || e.canMeasureViewportPlacementGeometry)
        }, Format.prototype.transitionTo = function(e, t, i) {
            throw new Error("transitionTo not implemented")
        }, Format.prototype.destroy = function() {
            for (var e in this.placements) this.adapter.destroyPlacement(e), delete this.placements[e];
            this.adapter._stopObservingEnvironment(), this.adViewableTimeObserver && this.adViewableTimeObserver.stop()
        }, Format.prototype.hacks = {}, Format.prototype._attachInViewToOrderedPlacements = function(e, t, i) {
            var r = i || t;
            e._outerPlacement = r, this.adapter.canMeasureViewportPlacementGeometry && (r.inView || (r.inView = new PlacementInView(this.adapter, r)), i && !t.inView && (t.inView = new PlacementInView(this.adapter, t, i.inView)), e.inView || (e.inView = new InViewObject(e, t.inView)))
        }, Format.prototype.connectPlacementEventsToUnitBehavior = function(t, i) {
            this.adapter.on("orientationchange", function() {
                t.update(noop, this.adapter.adBehavior.crossScreenSticky)
            }.bind(this)), t.on("resized", function() {
                var e;
                !t._visible || (e = t.getPlacementGeometry()).width < 2 && e.height < 2 || i.setAvailableSize(e.width, e.height)
            }.bind(this)), t.on("shown", function() {
                var e = t.getPlacementGeometry();
                i.setAvailableSize(e.width, e.height), i.enterRenderTree()
            }), t.on("hidden", function() {
                i.exitRenderTree()
            })
        }, Format.prototype._trackViewportPlacementGeometry = function(e) {
            var t = this.adapter.getTopWindow(),
                e = e.getGlobalGeometry(t),
                t = getViewportGeometry(t, "win"),
                e = CRect.adopt(e),
                t = (e.left += t.left, e.top += t.top, {
                    name: "viewportPlacementGeometry",
                    pageDimensions: this.adapter.getPageDimensions(),
                    viewportPositionRect: t,
                    firstPlacementPositionRect: e
                });
            this.adapter.trackingCenter.trackNoLaterThan(t)
        }, Format.prototype.setupViewableTimeObserver = function() {
            var e, t;
            this._observesViewableTime = "0" !== this.adapter.runtimeParams._trackViewableTime, this.observingViewableTime() && (e = this.adapter, t = new AdViewableTimeTracker(e.trackingCenter, e.raf.environment, e.getTopWindow()), e.canMeasureViewportPlacementGeometry ? this.adViewableTimeObserver = new AdViewableInViewObjectObserver(t) : this.adViewableTimeObserver = new AdViewableUnitObserver(t), this.adViewableTimeObserver.registerAdapter(e))
        }, Format.IGNORABLES = ["INPUT", "TEXTAREA", "SELECT"], Format.isElementIgnorable = function(e) {
            return -1 < Format.IGNORABLES.indexOf(e.tagName)
        }, Format.prototype.needsAccessToHostPage = function() {
            return !1
        }, Format.prototype._goToUnit = function(e, t) {
            var i, r = null;
            for (i in this.units)
                if (r = this.units[i].localId == e ? this.units[i] : r) break;
            t(r)
        };;

        function UnitViewabilityObserver(e, t, i, r) {
            this.adapter = e, this.win = e.getTopWindow(), this.placement = t, this.element = i, this._listeners = [], this._active = !1, this._currentViewability = null, this._inTimer = {}, this._ratio = 0, this._samplingRect = new CRect, this._lastSamplingTimestamp = 0, this._minSamplingTimeDelta = 200, this._calc = this._calc.bind(this), this._useIntersectionObserver = !!r, this._asyncComputed = {
                ratio: 0,
                boundingClientRect: {
                    bottom: 0,
                    height: 0,
                    left: 0,
                    right: 0,
                    top: 0,
                    width: 0
                }
            }
        }
        UnitViewabilityObserver.samplingCount = 100, UnitViewabilityObserver.minimumOpacity = .95, UnitViewabilityObserver.prototype._getUnitVisibleGeometry = function() {
            return this._useIntersectionObserver ? this._getIntersectionObserverGeometry() : this._getOldObserverGeometry()
        }, UnitViewabilityObserver.prototype._getOldObserverGeometry = function() {
            var e = this.adapter.getPlacementRect(this.placement),
                t = this.adapter.getViewportRect(),
                i = CRect.adopt(this.placement.getContainer().getBoundingClientRect()),
                r = CRect.adopt(this.element.getBoundingClientRect()),
                n = r.intersect(i),
                r = r.map(i, e),
                n = n ? n.map(i, e).intersect(t) : null;
            return {
                globalUnitRect: r,
                intersect: n,
                ratio: n && 0 < r.area ? n.area / r.area : 0
            }
        }, UnitViewabilityObserver.prototype._getIntersectionObserverGeometry = function() {
            var e, t, i, r = 0;
            return 0 < this._asyncComputed.ratio && (e = this.adapter.getPlacementRect(this.placement), t = CRect.adopt(this.placement.getContainer().getBoundingClientRect()), i = this._asyncComputed.rect, r = new CRect(i.x, i.y, i.width, i.height).map(t, e)), {
                globalUnitRect: this._asyncComputed.boundingClientRect,
                intersect: r,
                ratio: this._asyncComputed.ratio
            }
        }, UnitViewabilityObserver.prototype._handleIntersect = function(e, t) {
            e.forEach(function(e) {
                this._asyncComputed = {
                    ratio: e.intersectionRatio,
                    rect: e.intersectionRect,
                    boundingClientRect: e.boundingClientRect
                }
            }.bind(this))
        }, UnitViewabilityObserver.prototype._intersectionObserverSetUp = function() {
            var e = this.element,
                t = {
                    threshold: function(e) {
                        for (var t = [], i = 0; i <= e; i++) t.push(i / e);
                        return t
                    }(50)
                };
            this._useIntersectionObserver = new IntersectionObserver(this._handleIntersect.bind(this), t), this._useIntersectionObserver.observe(e)
        }, UnitViewabilityObserver.prototype.computeUnitInteractableRatio = function(e) {
            var t;
            return this.element.parentNode && this.placement.root.parentNode && this.adapter.containerIsViewable && this.element.ownerDocument.defaultView ? ((t = this._getUnitVisibleGeometry()).intersect, t = t.ratio, this._ratio = t, this._ratio) : 0
        }, UnitViewabilityObserver.prototype.addListener = function(e) {
            this._listeners.push(e), this._start()
        }, UnitViewabilityObserver.prototype.removeListener = function(t) {
            this._listeners = this._listeners.filter(function(e) {
                return e !== t
            }), 0 === this._listeners.length && this.stop()
        }, UnitViewabilityObserver.prototype._calc = function(e) {
            var t;
            this._active && (t = this._getUnitVisibleGeometry().globalUnitRect, this._currentViewability = this.computeUnitInteractableRatio(e), this._listeners.forEach(function(e) {
                this._currentViewability !== e.previousViewability && (e.previousViewability = this._currentViewability, e(this._currentViewability, t))
            }, this))
        }, UnitViewabilityObserver.prototype._start = function() {
            this._active || (this._active = !0, this._useIntersectionObserver && this._intersectionObserverSetUp(), this.adapter.on("containerViewableChange", this._calc), Ticker.frame(this._calc, "update"))
        }, UnitViewabilityObserver.prototype.stop = function() {
            if (this._active) {
                for (var e in this._active = !1, this.adapter.off("containerViewableChange", this._calc), Ticker.removeFrame(this._calc, "update"), this._inTimer) this.win.clearTimeout(this._inTimer[e]);
                this._inTimer = {}, this._useIntersectionObserver && this._useIntersectionObserver.unobserve(this.element)
            }
        }, UnitViewabilityObserver.prototype.waitForViewable = function(r, n) {
            var s = function(e, t) {
                var i = UnitViewabilityObserver.selectCriterion(r, t);
                (0 == i.ratio ? 0 < e : e >= i.ratio) ? (t = function() {
                    this.removeListener(s), n(i)
                }.bind(this), "Core" == r ? t() : this._inTimer[r] || (this._inTimer[r] = this.win.setTimeout(t, i.time))) : this._inTimer[r] && (this.win.clearTimeout(this._inTimer[r]), this._inTimer[r] = null)
            }.bind(this);
            return this.addListener(s), s
        }, UnitViewabilityObserver.prototype.waitForViewableCore = function(e) {
            return this.waitForViewable("Core", e)
        }, UnitViewabilityObserver.prototype.waitForViewableIAB = function(e) {
            return this.waitForViewable("IAB", e)
        }, UnitViewabilityObserver.prototype.waitForVideoViewableIAB = function(e) {
            return this.waitForViewable("IABvideo", e)
        }, UnitViewabilityObserver.prototype.waitForVideoViewableFB = function(e) {
            return this.waitForViewable("FBvideo", e)
        }, UnitViewabilityObserver.LARGE_AD_PIXEL_COUNT = 242500, UnitViewabilityObserver.selectCriterion = function(e, t) {
            switch (e) {
                case "IAB":
                    return t && t.width * t.height > UnitViewabilityObserver.LARGE_AD_PIXEL_COUNT ? this.criteria.IABlargeAd : this.criteria.IABsmallAd;
                case "Core":
                case "IABvideo":
                case "FBvideo":
                    return this.criteria[e];
                default:
                    throw new Error("Unknown viewability criterion.")
            }
        }, UnitViewabilityObserver.criteria = {
            Core: {
                name: "Core",
                ratio: 0,
                time: 0
            },
            IABsmallAd: {
                name: "50/1",
                ratio: .5,
                time: 1e3
            },
            IABlargeAd: {
                name: "30/1",
                ratio: .3,
                time: 1e3
            },
            IABvideo: {
                name: "50/2",
                ratio: .5,
                time: 2e3
            },
            FBvideo: {
                name: "50/3",
                ratio: .5,
                time: 3e3
            }
        };;

        function PlacementInView(t, e, i) {
            this._init(), this.adapter = t, this.placement = e, this.node = e.getContainer(), this.inViewParent = i, this.outerRectInView = CRect.ZERO, this._lastScrollTime = 0, this._isTouchDown = !1, this._autoSwitchObserverRunningState = this._autoSwitchObserverRunningState.bind(this), this.destroy = this.destroy.bind(this), this._onScroll = this._onScroll.bind(this), this._onTouchStart = this._onTouchStart.bind(this), this._onTouchEnd = this._onTouchEnd.bind(this), this.hacks = {
                wrongBoundingRectWhileScrolling: deviceInfo.os.ios() && this.placement.isSticky
            }, this.inViewParent || (this._tick = this._tick.bind(this), this._timestepInterval = 200, this._lastRunTime = 0, this.adapter.on("containerViewableChange", this._autoSwitchObserverRunningState)), e.on("destroyed", this.destroy), this._autoSwitchObserverRunningState(), e.on("shown", this._autoSwitchObserverRunningState), e.on("hidden", this._autoSwitchObserverRunningState), this.hacks.wrongBoundingRectWhileScrolling && (this.adapter.getTopWindow().addEventListener("scroll", this._onScroll, !0), this.adapter.getTopWindow().addEventListener("touchstart", this._onTouchStart, !0), this.adapter.getTopWindow().addEventListener("touchend", this._onTouchEnd, !0))
        }
        inherit(PlacementInView, InViewObject), PlacementInView.prototype._onScroll = function() {
            this._lastScrollTime = Date.now()
        }, PlacementInView.prototype._onTouchStart = function() {
            this._isTouchDown = !0
        }, PlacementInView.prototype._onTouchEnd = function() {
            this._isTouchDown = !1
        }, PlacementInView.prototype._autoSwitchObserverRunningState = function() {
            var t = this.placement.visible && this.adapter.containerIsViewable;
            t && !this.active ? this.start() : !t && this.active && (this.stop(), this.rectInView = CRect.ZERO, this.outerRectInView = CRect.ZERO, this.areaInView = 0, this.emit("rectInViewChanged", this.rectInView), this.emit("outerRectInViewChanged", this.outerRectInView), Logger("InViewObject").debug(this.placement.constructor.name + ", rectInViewChanged: " + this.rectInView.toString()), this.emit("areaInViewRatioChanged", this.areaInView), Logger("InViewObject").debug(this.placement.constructor.name + ", areaInViewRatioChanged: " + this.areaInView))
        }, PlacementInView.prototype.getNode = function() {
            return this.placement.getContainer()
        }, PlacementInView.prototype.getOuterRectInView = function() {
            return this.outerRectInView
        }, PlacementInView.prototype.getParentRect = function() {
            return this.inViewParent ? this.inViewParent.getOuterRectInView() : this.adapter.getViewportRect()
        }, PlacementInView.prototype.computeRectInView = function() {
            var t, e, i, n;
            this.hacks.wrongBoundingRectWhileScrolling && (Date.now() - this._lastScrollTime < 100 || this._isTouchDown) || (t = this.getNode(), n = this.adapter.getPlacementRect(this.placement), t = t ? CRect.adopt(t.getBoundingClientRect()) : CRect.ZERO, i = this.getParentRect(), n = (i = (e = this.adapter.getPlacementRect(this.placement)).intersect(i)).zero() ? CRect.ZERO : i.map(n, t), this._computeMaxPossibleDimensions(e, this.getParentMaxPossDims()), this.rectInView.equals(n) || (this.rectInView = n, this.emit("rectInViewChanged", n), Logger("InViewObject").debug(this.placement.constructor.name + ", rectInViewChanged: " + this.rectInView.toString())), this.outerRectInView.equals(i) || (this.outerRectInView = i, this.emit("outerRectInViewChanged", i), Logger("InViewObject").debug(this.placement.constructor.name + ", placementRectInViewChanged: " + this.outerRectInView.toString())))
        }, PlacementInView.prototype.destroy = function() {
            this.hacks.wrongBoundingRectWhileScrolling && (this.adapter.getTopWindow().removeEventListener("scroll", this._onScroll, !0), this.adapter.getTopWindow().removeEventListener("touchstart", this._onTouchStart, !0), this.adapter.getTopWindow().removeEventListener("touchend", this._onTouchEnd, !0)), this.stop(), this.adapter.off("containerViewableChange", this._autoSwitchObserverRunningState)
        }, PlacementInView.prototype._start = function() {
            this.inViewParent ? this.inViewParent.on("outerRectInViewChanged", this.computeRectInView) : this.adapter.Ticker.frame(this._tick, "update")
        }, PlacementInView.prototype._stop = function() {
            this.inViewParent ? this.inViewParent.off("outerRectInViewChanged", this.computeRectInView) : this.adapter.Ticker.removeFrame(this._tick, "update")
        }, PlacementInView.prototype._tick = function() {
            var t = Date.now();
            t - this._lastRunTime > this._timestepInterval && (this.computeRectInView(), this._lastRunTime = t)
        };;

        function Tapper(T, m) {
            var C = "undefined" != typeof creative && creative.adapter || adapter;

            function w(t) {
                for (var e = t, a = null; 1 != e.nodeType;) e = e.parentNode;
                for (; e && 1 == e.nodeType && e.tagName;) {
                    var n = e.tagName.toLowerCase();
                    if (hasClass(e, "touchable") || "a" === n && e.href || "button" === n || "img" === n || "input" === n && e.type && "button" === e.type.toLowerCase()) {
                        a = e;
                        break
                    }
                    e = e.parentNode
                }
                return a
            }

            function k(t, e) {
                t = t.ownerDocument.createEvent("MouseEvents");
                t.initEvent("tap", !0, !0), e.dispatchEvent(t)
            }(m = m || C.useNativeClickForTapDetection) && (attach(T, "click", function(t) {
                t = w(t.target);
                t && k(T, t)
            }, !C.nativeClickEmittedOnSwipe), C.useNativeClickForTapDetection) || attach(T, "touchstart", function(t) {
                var a, n, c, o, r, i, u, h, s, d, l, v, p, e, f, g = w(t.target);
                g && (c = !1, celtra.iframe() && (n = (a = function() {
                    var t = C.getTopWindow();
                    return [t.scrollX, t.scrollY, t.innerWidth, t.innerHeight]
                })(), c = !0), o = t.targetTouches[0].clientX - 10, r = t.targetTouches[0].clientX + 10, i = t.targetTouches[0].clientY - 10, u = t.targetTouches[0].clientY + 10, addClass(g, "touched"), l = d = s = h = !1, v = new Date, p = function(t) {
                    var e;
                    h || s || (e = t.targetTouches[0].clientX, t = t.targetTouches[0].clientY, h = c && n.toString() != a().toString(), ((s = !(o <= e && e <= r && i <= t && t <= u)) || h ? removeClass : addClass)(g, "touched"))
                }, e = function(t) {
                    var e;
                    l || (l = !0, detach(T, "touchmove", p, !1), detach(T, "touchend", arguments.callee, !1), hasClass(g, "touched") && (e = 200 < new Date - v && !deviceInfo.deviceType.desktop(), removeClass(g, "touched"), s || h || d || e || m || k(T, g)))
                }, f = function() {
                    detach(T, "touchend", f, !0), nextFrame(function() {
                        d = !0, e()
                    })
                }, attach(T, "touchcancel", function(t) {
                    removeClass(g, "touched"), e(t)
                }, !1), attach(T, "touchmove", p, !1), attach(T, "touchend", e, !1), attach(T, "touchend", f, !0))
            }, !0)
        };
        ! function(t) {
            var i = e.prototype;

            function e(t, i, e, n) {
                for (var o in this._videoComponent = t, this._onStartAd = i, this._placement = n, this._creative = e, this._startedPlaying = !1, this._started = !1, this._playing = !1, this._videoStarted = !1, this._videoComponentEvents = {
                        play: this._onPlayerPlay.bind(this),
                        playing: this._onPlayerPlaying.bind(this),
                        pause: this._onPlayerPause.bind(this),
                        ended: this._onPlayerEnded.bind(this),
                        durationchange: this._onPlayerDurationChange.bind(this),
                        videoStart: this._onVideoStart.bind(this),
                        videoFirstQuartile: this._onVideoFirstQuartile.bind(this),
                        videoMidpoint: this._onVideoMidpoint.bind(this),
                        videoThirdQuartile: this._onVideoThirdQuartile.bind(this),
                        videoComplete: this._onVideoComplete.bind(this),
                        muted: this._onMuted.bind(this),
                        unmuted: this._onUnMuted.bind(this)
                    }, this._creativeEvents = {
                        interaction: this._onAdInteraction.bind(this)
                    }, this._videoComponentEvents) this._videoComponent.on(o, this._videoComponentEvents[o]);
                for (var s in this._creativeEvents) this._creative.on(s, this._creativeEvents[s]);
                this.startAd = this.startAd.bind(this), this.getAdDuration = this.getAdDuration.bind(this), this.getAdRemainingTime = this.getAdRemainingTime.bind(this), this.userDismissed = !1, this.destroy = this.destroy.bind(this), "1" !== this._creative.runtimeParams._fireAdLoadedOnInit && defer(function() {
                    this.emit("AdLoaded")
                }.bind(this))
            }
            e.NOT_INITIALISED = -1, e.NOT_PLAYING = -2, extend(i, EventEmitter), i.startAd = function() {
                this._videoComponent ? this._onStartAd(this) : this.handleError("No video available.")
            }, i.stopAd = function() {
                creative.adapter.dismiss()
            }, i.resumeAd = function() {
                this._videoComponent ? this._videoComponent.playAction(this._actionContext(), {}, noop) : this.handleError("No video available.")
            }, i.pauseAd = function() {
                this._videoComponent.pauseAction(this._actionContext(), {}, noop)
            }, i.skipAd = function() {
                creative.adapter.dismiss(function() {
                    this.emit("AdSkipped")
                }.bind(this))
            }, i.getAdDuration = function() {
                var t = e.NOT_PLAYING;
                return t = this._videoComponent && 0 !== this._videoComponent.getDuration() ? this._videoComponent.getDuration() : t
            }, i.getAdRemainingTime = function() {
                var t = e.NOT_PLAYING;
                return t = this._videoComponent && 0 !== this._videoComponent.getDuration() ? this._videoComponent.getDuration() - this._videoComponent.getCurrentTime() : t
            }, i.resizeAd = function(t, i, e) {
                this._placement.once("resized", this.emits("AdSizeChange"))
            }, i.destroy = function() {
                if (this._videoComponent) {
                    for (var t in this._videoComponentEvents) this._videoComponent.off(t, this._videoComponentEvents[t]);
                    for (var i in this._creativeEvents) this._creative.off(i, this._creativeEvents[i]);
                    this.userDismissed && this.emit("AdUserClose")
                }
                this.emit("AdStopped")
            }, i._onPlayerPlay = function() {
                this._started || (this._started = !0, this.emit("AdStarted"))
            }, i._onPlayerPlaying = function() {
                this._onPlayerPlay(), this._playing = !0, this.emit("AdDurationChange"), this._startedPlaying ? this.emit("AdPlaying") : this._startedPlaying = !0
            }, i._onPlayerPause = function(t) {
                this._playing && t < this._videoComponent.getDuration() && (this._playing = !1, this.emit("AdDurationChange"), this.emit("AdPaused"))
            }, i._onPlayerEnded = function() {
                this._playing = !1, creative.adapter.dismiss()
            }, i._onPlayerDurationChange = function() {
                this._started && this.emit("AdDurationChange")
            }, i._onAdInteraction = function(t) {
                this.emit("AdInteraction", "")
            }, i._onVideoStart = function(t) {
                this._videoStarted || (this._videoStarted = !0, this.emit("AdImpression"), this.emit("AdVideoStart"), this._creative.adapter.trackingCenter.trackNoLaterThan({
                    name: "vpaidImpression"
                }), this._creative.adapter.trackingCenter.batchFlush())
            }, i._onVideoFirstQuartile = function(t) {
                this.emit("AdVideoFirstQuartile")
            }, i._onVideoMidpoint = function(t) {
                this.emit("AdVideoMidpoint")
            }, i._onVideoThirdQuartile = function(t) {
                this.emit("AdVideoThirdQuartile")
            }, i._onVideoComplete = function(t) {
                this.emit("AdVideoComplete")
            }, i._onMuted = function() {
                this.emit("AdVolumeChange", 0)
            }, i._onUnMuted = function() {
                this.emit("AdVolumeChange", 1)
            }, i.handleError = function(t) {
                this.emit("AdError", t)
            }, i._actionContext = function() {
                return new ActionContext(this._videoComponent)
            }, t.VPAIDObject = e
        }(window);;

        function UniversalInteractiveVideoFormat(t, e) {
            Format.apply(this, arguments), t.adBehavior.interstitial = !0, t.adBehavior.expandable = !1, this._vpaidObject = null, this._masterVideo = null, this._vpaidVideoFileMeta = null, this._updatePlacementSizeAndPosition = this._updatePlacementSizeAndPosition.bind(this), this._slot = t.getSlot && t.getSlot(), this._placementState = new StateObject({
                width: 0,
                height: 0,
                left: 0,
                top: 0
            }), this._update = this._update.bind(this), this._render = this._render.bind(this), this.adapter.on("stateChange", function(t) {
                this.transitionTo(t)
            }.bind(this)), this.setup()
        }
        inherit(UniversalInteractiveVideoFormat, Format), UniversalInteractiveVideoFormat.prototype.setup = function() {
            var i = 1 == this.adapter.runtimeParams.campaignExplorer,
                a = (this.setupViewableTimeObserver(), new TaskScheduler);
            a.when("ready").run(function() {
                var t = this.adapter.isPreviewAdapter || this.adapter.isMobileWebAdapter;
                this.adapter.createPlacements({
                    banner: {
                        attachment: "container",
                        positioning: t ? "container" : "static",
                        type: "unit"
                    }
                }, function() {
                    var t = this.placements.banner;
                    this.units.banner = t.creative.units.banner, this._setupVpaid(function() {
                        a.notify("vpaidStartCalled")
                    }.bind(this)), this.populateBanner(t, function() {
                        this.adViewableTimeObserver && this.adViewableTimeObserver.registerUnit(t.creative.units.banner), a.notify("bannerPopulated")
                    }.bind(this))
                }.bind(this))
            }.bind(this)), a.when("bannerPopulated").run(function() {
                var t = this.placements.banner;
                this.units.banner;
                t.creative.trackCreativeLoaded(this.observationAbilities), this.adapter.triggerLoadedEvent(this.units.banner.node), "live" === this.adapter.runtimeParams.purpose && t.creative.isMoatVideoEnabled && (this._moatApi = new MoatVideoTrackingAPI(this.adapter, t.root, this._masterVideo), this._moatApi.startTracking())
            }.bind(this)), a.when(["bannerPopulated", "vpaidStartCalled"]).run(function() {
                var t, e;
                i ? (t = this.placements.banner, e = this.units.banner, t.show(), this.adapter.viewabilityMeasurable.viewable00 && this.adapter.waitForViewableCore(t, e.node, function(t) {
                    a.pause(), this.start(), this.transitionTo("default")
                }.bind(this))) : (a.pause(), this.start(), this.transitionTo("default"))
            }.bind(this)), this.adapter.on("ready", a.notifies("ready"))
        }, UniversalInteractiveVideoFormat.prototype.start = function() {
            this.adapter.Ticker.frame(this._update, "update"), this.adapter.Ticker.frame(this._render, "render")
        }, UniversalInteractiveVideoFormat.prototype.stop = function() {
            this.adapter.Ticker.removeFrame(this._update, "update"), this.adapter.Ticker.removeFrame(this._render, "render")
        }, UniversalInteractiveVideoFormat.prototype._update = function() {
            this._updatePlacementSizeAndPosition()
        }, UniversalInteractiveVideoFormat.prototype._render = function() {
            this._placementState.isDirty() && 0 < this._placementState.values.width.value && 0 < this._placementState.values.height.value && (this.placements.banner.setSize(this._placementState.width, this._placementState.height), this.placements.banner.setPosition(this._placementState.left, this._placementState.top), this._placementState.markClean())
        }, UniversalInteractiveVideoFormat.prototype._updatePlacementSizeAndPosition = function() {
            var t, e, i;
            e = this._slot ? (t = this._slot.offsetWidth, this._slot.offsetHeight) : (t = (e = this.placements.banner.getViewportGeometry()).width, e.height), t / e > this._vpaidVideoFileMeta.width / this._vpaidVideoFileMeta.height ? (i = e * (this._vpaidVideoFileMeta.width / this._vpaidVideoFileMeta.height), this._placementState.width = i, this._placementState.height = e, this._placementState.left = (t - i) / 2, this._placementState.top = 0) : (i = t * (this._vpaidVideoFileMeta.height / this._vpaidVideoFileMeta.width), this._placementState.width = t, this._placementState.height = i, this._placementState.left = 0, this._placementState.top = (e - i) / 2)
        }, UniversalInteractiveVideoFormat.prototype.populateBanner = function(t, e) {
            var i = t.creative.units.banner,
                a = (i.swipeable = !0, i.firstAppearWithoutUserInteraction = !0, i.showCloseButton = !1, i.fillsContainer = !0, i.usePlaceboBar = !1, this._attachInViewToOrderedPlacements(i, t, this.placements.wrapper), this.connectPlacementEventsToUnitBehavior(t, i), i.getNode(function() {
                    t.populate(a, e), t.setZIndex(-2), t.setBackground(t.creative.units.banner.backgroundColor)
                }))
        }, UniversalInteractiveVideoFormat.prototype.transitionTo = function(t, e, i) {
            if (null == this.state && "default" == t) this.placements.banner.show(), this.units.banner.appear(), this.adapter.startObscuringContent(), i && defer(i);
            else {
                if ("default" == this.state && "dismissed" == t) this.adapter.stopObscuringContent(), this.placements.banner.hide(), this.units.banner.disappear();
                else if (null != this.state || "dismissed" != t) throw new Error("Invalid state transition request! (" + this.state + " => " + t + ")");
                this.destroy()
            }
            this.state = t
        }, UniversalInteractiveVideoFormat.prototype.destroy = function() {
            this._vpaidObject && this._vpaidObject.destroy(), this.stop(), UniversalInteractiveVideoFormat.uber.destroy.call(this)
        }, UniversalInteractiveVideoFormat.prototype._setupVpaid = function(t) {
            this._masterVideo = this.units.banner.getMasterVideo(), this._vpaidVideoFileMeta = this._masterVideo.video.meta, this._updatePlacementSizeAndPosition(), this.units.banner.setAvailableSize(this._placementState.values.width.value, this._placementState.values.height.value), this.adapter.initVpaid ? this._vpaidObject = this.adapter.initVpaid(this._masterVideo, t, this.placements.banner) : defer(t, void 0, void 0, this.adapter.useAsap)
        }, UniversalInteractiveVideoFormat.prototype.needsAccessToHostPage = function() {
            return !0
        };;

        function SDKAdapter(e, t, i, n, r, o, a, s, d, p) {
            function h(e) {
                return this.experiments && this.experiments.get && this.experiments.get(e)
            }

            function c(e, t) {
                return "1" === t || e && "treatment" === e.chosenVariant && "0" !== t
            }
            this.runtimeParams = e, this.trackingCenter = t, this.aggregatorTracking = i, this.experiments = n, this.useAsap = c(h("DeferWithAsap"), e._useAsap), this._renderBeforeDomReady = c(h("RenderBeforeDomReady"), e._renderImmediately), this.macros = a, this.perf = p, defer.perf = p, this.ampDetected = !1, this.ampNestingLevel = "", this.safeFrameDetected = !1, this.scriptElement = script, this.scriptElement.parentNode.celtra || (this.scriptElement.parentNode.celtra = {
                loaded: !1,
                viewabilityObservee: null
            }), this.domApi = this.scriptElement.parentNode.celtra, this.adapterWindow = window, this.tagWindow = this.scriptElement.ownerDocument.defaultView, this.nesting = getWindowNesting(this.tagWindow), this.tagElement = this.scriptElement.parentNode, this.hostElement = this.tagElement, this.storeOpenedOverrideUrls = o, this.urlOpenedOverrideUrls = r, this.urlOpenedUrlAppendage = s, this.clickThroughDestinationUrl = d, this.useNativeClickForTapDetection = !0, this.nativeClickEmittedOnSwipe = deviceInfo.deviceType.desktop() || deviceInfo.os.ios(), this.preventIABFriendlyIFrameBusting = !1, this.providesCloseButton = !1, this.enableCustomStateTransitions = !1, this.adBehavior = {
                interstitial: !1,
                expandable: !1,
                expanded: !1,
                sticky: !1,
                crossScreenSticky: !1,
                attachInlinePlacementToViewport: !1,
                scrollable: !1,
                subpixelScrollable: !1
            }, this.activeGeometryBaseline = {
                placement: "banner",
                unit: "banner"
            }, this.isRxAvailable = !1, this.viewabilityMeasurable = {
                viewable00: !1,
                viewable501: !1
            }, this.canMeasureViewportPlacementGeometry = !1, this.canMeasureViewportPlacementGeometryRelativeToPage = !1, this.canMeasureContainerAreaInViewRatio = !1, this.supportsContainerViewability = !1, this.supportsContainerInitialViewability = !1, this.useFSVP = !0, void 0 !== this.runtimeParams.topLayerZIndex && (t = parseInt(this.runtimeParams.topLayerZIndex, 10), !isNaN(t) && 20 < t && (Placement.ZINDEX_MAX = Math.min(Placement.ZINDEX_MAX, t))), this.hacks = {
                useResizeOnScroll: ios("9")
            }, this._hooks = {}, this.placements = {}, this.supportsSticky = !1, this._handleResize = this._handleResize.bind(this), this._handleOrientationChange = this._handleOrientationChange.bind(this), this._handleViewportChange = this._handleViewportChange.bind(this), this._handleDetachedFromDocument = this._handleDetachedFromDocument.bind(this), this._updatePlacements = this._updatePlacements.bind(this), this.listenForExpandErrors = this.listenForExpandErrors.bind(this), this._onReportReceived = this._onReportReceived.bind(this), this._keyboardShown = !1, this._initialInnerDims = null, this._onKeyboardShown = this._onKeyboardShown.bind(this), this._onKeyboardHidden = this._onKeyboardHidden.bind(this), this._resizeTimer = null, this._listening = !1, this._touchEventSimulator = null, this.hostContainerSize = {
                width: "100%",
                height: "100%"
            }, this.mediaState = new MediaState, this.nativeRAFIsSupported = ["r", "webkitR", "mozR", "msR"].some(function(e) {
                return e + "equestAnimationFrame" in window
            }), this.visibilityApiIsSupported = ["webkit", "moz", "ms", ""].some(function(e) {
                return (e ? e + "Hidden" : "hidden") in window.document
            });
            var l = function(e) {
                e && (this.trackingCenter.track({
                    name: "containerBecameViewable"
                }), this.off("containerViewableChange", l))
            }.bind(this);
            defer(function() {
                var e = this.perf.start("Adapter.startObservingEnvironment");
                this._startObservingEnvironment(), e.end(), this.supportsContainerInitialViewability && (this.containerIsViewable ? this.trackingCenter.track({
                    name: "containerBecameViewable"
                }) : this.on("containerViewableChange", l))
            }.bind(this), 0, "SDKAdapter defer _startObservingEnvironment", this.useAsap), this.on("containerViewableChange", this.trackingCenter.batchFlush), this.getTopWindow().document.addEventListener("unload", this.trackingCenter.batchFlush), this.getTopWindow().document.addEventListener("beforeunload", this.trackingCenter.batchFlush), this.getTopWindow().document.addEventListener("pagehide", this.trackingCenter.batchFlush), this.trackingCenter.startBatchFlushCycle(1e3), this.on("resize", this._updatePlacements), this._displayed = !1, this.on("displayed", function() {
                this._displayed = !0
            }.bind(this)), this.sdkReady = !1, this.canOpenUrlInSameWindow = !1, this._MAX_TRACKING_DURATION = 2e3, this._initRequestAnimationFrame(), this.once("domReady", this.perf.marks("Adapter.domReady")), this.once("documentReady", this.perf.marks("Adapter.documentReady")), this.once("sdkReady", this.perf.marks("Adapter.sdkReady")), this.once("ready", this.perf.marks("Adapter.ready")), defer(function() {
                var e = this.perf.start("Adapter.initSdk");
                this._initSdk(deferred(function() {
                    e.end(), this.sdkReady = !0, this.emit("sdkReady")
                }.bind(this), 0, "SDKAdapter defer sdkReady", this.useAsap))
            }.bind(this), 0, "SDKAdapter defer _initSdk", this.useAsap), this.on("documentReady", function() {
                android("4.0") && this.getTopWindow().setInterval(function() {
                    for (var e in this.placements) this.placements[e]._flash()
                }.bind(this), 250), this.runtimeParams._mraidCheck && defer(function() {
                    this.collectAndTrackData(function(e) {
                        e({
                            isMraid: void 0 !== this.tagWindow.mraid
                        })
                    }.bind(this), "mraidCheckEnvironmentInfo")
                }.bind(this), 1e3)
            }.bind(this)), this.useFixedPositioningForInterstitial = !1
        }
        extend(SDKAdapter.prototype, EventEmitter), SDKAdapter.prototype.protoLoading = {}, Object.defineProperties(SDKAdapter.prototype.protoLoading, {
            dataURIsSupported: {
                get: function() {
                    return !!runtimeParams.protoLoading && "supported" === runtimeParams.protoLoading.dataLoadStatus
                },
                enumerable: !0
            },
            blobURIsSupported: {
                get: function() {
                    return !!runtimeParams.protoLoading && "supported" === runtimeParams.protoLoading.blobLoadStatus
                },
                enumerable: !0
            }
        }), Object.defineProperty(SDKAdapter.prototype, "__CELTRA", {
            get: function() {
                return this.getTopWindow().__CELTRA || (this.getTopWindow().__CELTRA = {
                    formats: []
                }), this.getTopWindow().__CELTRA
            },
            enumerable: !0
        }), Object.defineProperty(SDKAdapter.prototype, "assumeScrollSupported", {
            get: function() {
                return !1
            }
        }), SDKAdapter.prototype._initRequestAnimationFrame = function() {
            var e = this.getTopWindow();
            this.raf = new RequestAnimationFrameWrapper(e), this.Ticker = new CTicker(this.raf, e), this.nextFrame = this.Ticker.raf.nextFrame, this.cancelFrame = this.Ticker.raf.cancelFrame, window.nextFrame = this.Ticker.raf.nextFrame, window.cancelFrame = this.Ticker.raf.cancelFrame, window.Ticker = this.Ticker
        }, SDKAdapter.prototype._startObservingEnvironment = function() {
            if (!this._listening) {
                try {
                    var e = this.tagWindow,
                        t = 0;
                    for (; e;) {
                        if ((n = e).context && void 0 !== n.context.pageViewId) {
                            this.ampDetected = !0, this.ampNestingLevel = t;
                            break
                        }
                        if ("$sf" in (i = e) && i.$sf.ext) {
                            this.safeFrameDetected = !0;
                            break
                        }
                        if (e == e.parent) break;
                        e = e.parent, t++
                    }
                } catch (e) {}
                this.resizeListener = this._createViewportListener(), this.resizeListener.start(this._handleResize, this._handleViewportChange);
                var i, n, r, o = this._createOrientationObserver(function(e) {
                    this.orientationObserver = e, this.orientationObserver.start(), Object.defineProperty(this, "orientation", {
                        get: function() {
                            return this.orientationObserver.getOrientation()
                        }.bind(this),
                        configurable: !0
                    })
                }.bind(this));
                o && (this.orientationObserver = o, this.orientationObserver.start(), Object.defineProperty(this, "orientation", {
                    get: function() {
                        return this.orientationObserver.getOrientation()
                    }.bind(this),
                    configurable: !0
                })), this.containerViewabilityObserver = this._createContainerViewabilityObserver(), this.containerViewabilityObserver.start(), Object.defineProperty(this, "containerIsViewable", {
                    get: function() {
                        return this.containerViewabilityObserver.isViewable
                    }.bind(this),
                    configurable: !0
                }), this.documentAttachmentObserver = this._createDocumentAttachmentObserver(), this.documentAttachmentObserver.start(this._handleDetachedFromDocument), "ReportingObserver" in window && (this._reportingObserver = new ReportingObserverWrapper(this._onReportReceived), this._reportingObserver.start()), this.on("domReady", function() {
                    this._initDocument(deferred(this.emits("documentReady"), 0, "SDKAdapter._startObservingEnvironment deferred documentReady", this.useAsap))
                }.bind(this)), this.once("sdkReady", function() {
                    this.collectAndTrackData(function(e) {
                        var t = this.tagElement.parentNode,
                            i = {
                                width: this.getTopWindow().innerWidth,
                                height: this.getTopWindow().innerHeight
                            },
                            n = {
                                width: this.tagWindow.innerWidth,
                                height: this.tagWindow.innerHeight
                            },
                            i = {
                                scope: "global",
                                userAgent: this.getTopWindow().navigator.userAgent,
                                orientation: this.orientation,
                                topmostReachableWindow: i,
                                hostWindow: n,
                                nesting: this.nesting,
                                pageVisibilityApi: this.visibilityApiIsSupported,
                                requestAnimationFrame: this.nativeRAFIsSupported,
                                topWindowNativeRAFSupported: this.raf.isNative,
                                allowNonNativeRAFForViewableTimeUsed: "1" == runtimeParams._allowNonNativeRAFForViewableTime,
                                clientTimeZoneOffsetInMinutes: (new Date).getTimezoneOffset(),
                                supportsContainerViewability: this.supportsContainerViewability,
                                supportsContainerInitialViewability: this.supportsContainerInitialViewability,
                                tagParentWidth: t ? t.clientWidth : "",
                                tagParentHeight: t ? t.clientHeight : "",
                                ampDetected: this.ampDetected,
                                ampNestingLevel: this.ampNestingLevel,
                                safeFrameDetected: this.safeFrameDetected,
                                fetchSupported: isFetchSupported(),
                                asapEnabled: this.useAsap,
                                nativePromisesSupported: isNativeImplementation(window.Promise),
                                beaconSupported: isBeaconSupported(),
                                IntersectionObserverSupported: "IntersectionObserver" in window && "IntersectionObserverEntry" in window && "intersectionRatio" in window.IntersectionObserverEntry.prototype,
                                isMutationObserverSupported: isNativeImplementation(window.MutationObserver),
                                webView: deviceInfo.browser.webView(),
                                isWindowOpenNative: "function" == typeof this.getTopWindow().open && -1 < Function.prototype.toString.call(this.getTopWindow().open).indexOf("[native code]")
                            };
                        runtimeParams.universalTagSignals && (i.universalTagSignals = runtimeParams.universalTagSignals), runtimeParams.protoLoading && (i.protoLoading = runtimeParams.protoLoading), this.nesting.iframe && !this.nesting.friendlyIframe || (i.topWindowLocation = this.getTopWindow().location.protocol + "//" + this.getTopWindow().location.host, void 0 !== i.topWindowLocation && (i.topWindowLocationLength = i.topWindowLocation.length, 200 < i.topWindowLocationLength && (i.topWindowLocation = i.topWindowLocation.slice(0, 200) + "..."))), e(i)
                    }.bind(this), "environmentInfo")
                }.bind(this)), this._renderBeforeDomReady ? this.emit("domReady") : (r = !1, this._waitForDomReady(function() {
                    r || (this.emit("domReady"), r = !0)
                }.bind(this))), this._listening = !0
            }
        }, SDKAdapter.prototype._onReportReceived = function(e) {
            throw new Error("SDKAdapter._onReportReceived not implemented!")
        }, SDKAdapter.prototype._initSdk = function(e) {
            e()
        }, SDKAdapter.prototype._initDocument = function(e) {
            e()
        }, SDKAdapter.prototype._onKeyboardShown = function() {
            this._keyboardShown = !0
        }, SDKAdapter.prototype._onKeyboardHidden = function() {
            this._keyboardShown = !1, defer(this._updatePlacements)
        }, SDKAdapter.prototype.setIABFriendlyIframeSize = function() {}, SDKAdapter.prototype._createViewportListener = function() {
            var n = this,
                t = {
                    _resizeCallback: null,
                    _viewportChangeCallback: null,
                    start: function(e, t) {
                        var i = n.getTopWindow();
                        this._resizeCallback = e, this._viewportChangeCallback = t, i.addEventListener("resize", this._resizeHandler, !1), i.addEventListener("scroll", this._scrollHandler, !1), ios() && (i.addEventListener("focus", this._focusHandler, !0), i.addEventListener("blur", this._blurHandler, !0))
                    },
                    stop: function() {
                        var e = n.getTopWindow();
                        e.removeEventListener("resize", this._resizeHandler, !1), e.removeEventListener("scroll", this._scrollHandler, !1), ios() && (e.removeEventListener("focus", this._focusHandler, !0), e.removeEventListener("blur", this._blurHandler, !0))
                    }
                };
            return t._resizeHandler = function() {
                n.hacks.useResizeOnScroll && (t.numTimesScrollTriggeredAfterResize = 0), "undefined" != typeof window ? (this._resizeCallback(), this._viewportChangeCallback(), void 0 !== window.admarvelSDKVersion && defer(this._resizeCallback.bind(this), 200)) : n.dismiss()
            }.bind(t), t._scrollHandler = function() {
                "undefined" != typeof window ? (n.hacks.useResizeOnScroll && void 0 !== t.numTimesScrollTriggeredAfterResize && (t.numTimesScrollTriggeredAfterResize++, t.numTimesScrollTriggeredAfterResize <= 2 && this._resizeCallback()), this._viewportChangeCallback()) : n.dismiss()
            }.bind(t), t._focusHandler = function(e) {
                -1 < ["INPUT", "TEXTAREA"].indexOf(e.target.nodeName) && (n._keyboardShown = !0)
            }, t._blurHandler = function() {
                n._keyboardShown = !1;
                var e = ios("9", null) ? 100 : 0;
                defer(function() {
                    n._keyboardShown || (t._resizeCallback(), t._viewportChangeCallback())
                }, e)
            }, t
        }, SDKAdapter.prototype._createOrientationObserver = function() {
            var e = this,
                t = this.getTopWindow(),
                i = {
                    _orientationChangeEventQueued: !1,
                    start: function() {
                        t.addEventListener("orientationchange", this._handler, !1)
                    },
                    stop: function() {
                        t.removeEventListener("orientationchange", this._handler, !1)
                    },
                    getOrientation: function() {
                        return e._normalizeOrientation(t.orientation) || 0
                    },
                    _handler: function() {
                        "undefined" != typeof window ? (e.emit("_rawOrientationChange"), i._orientationChangeEventQueued = !0, e.once("resize", i._trigger), setTimeout(i._trigger, 1e3)) : e.dismiss()
                    },
                    _trigger: function() {
                        i._orientationChangeEventQueued && (i._orientationChangeEventQueued = !1, defer(e.emits("orientationchange")))
                    }
                };
            return i
        }, SDKAdapter.prototype._createContainerViewabilityObserver = function() {
            var e = this;
            return {
                isViewable: !1,
                start: function() {
                    defer(function() {
                        this.isViewable = !0, e.emit("containerViewableChange", this.isViewable)
                    }.bind(this), void 0, void 0, this.useAsap)
                },
                stop: function() {}
            }
        }, SDKAdapter.prototype.triggerLoadedEvent = function(e) {
            this.domApi.loaded = !0, this.domApi.viewabilityObservee = e, this._dispatchCeltraLoadedEvent(this.tagElement)
        }, SDKAdapter.prototype.triggerClosedEvent = function() {
            this._dispatchCeltraClosedEvent(this.tagElement)
        }, SDKAdapter.prototype._dispatchCeltraLoadedEvent = function(e) {
            var t = e.ownerDocument.createEvent("HTMLEvents");
            t.initEvent("celtraLoaded", !0, !1), e.dispatchEvent(t)
        }, SDKAdapter.prototype._dispatchCeltraClosedEvent = function(e) {
            var t = e.ownerDocument.createEvent("HTMLEvents");
            t.initEvent("celtraClosed", !0, !1), e.dispatchEvent(t)
        }, SDKAdapter.prototype.waitForDisplayed = function(e) {
            if (this._displayed) return defer(e);
            this.once("displayed", e)
        }, SDKAdapter.prototype.waitForContainerViewable = function(t) {
            if (this.containerIsViewable) return defer(t, void 0, void 0, this.useAsap);
            var i = function(e) {
                e && (this.off(i), defer(t, void 0, void 0, this.useAsap))
            }.bind(this);
            this.on("containerViewableChange", i)
        }, SDKAdapter.prototype._createDocumentAttachmentObserver = function() {
            var t = this.tagElement,
                e = this.getTopWindow(),
                i = new e.Function("f", "return function() { f(); };"),
                n = new e.Function("f", "interval", "return setTimeout(f, interval);"),
                r = new e.Function("f", "return requestAnimationFrame(f);");
            return {
                _interval: 60,
                _detachmentHandler: null,
                _timer: null,
                _topWinTimerTickWrapper: null,
                _rafRequestId: null,
                _topWinRafTickWrapper: null,
                start: function(e) {
                    this._detachmentHandler = e, this._topWinTimerTickWrapper = i(this._timerTick.bind(this)), this._timer = n(this._topWinTimerTickWrapper, this._interval), this._topWinRafTickWrapper = i(this._rafTick.bind(this)), this._rafRequestId = r(this._topWinRafTickWrapper)
                },
                stop: function() {
                    e.clearTimeout(this._timer), (this._timer = null) !== this._rafRequestId && (e.cancelAnimationFrame(this._rafRequestId), this._rafRequestId = null)
                },
                _timerTick: function() {
                    this._isDetached() ? this._detachmentHandler() : this._timer = n(this._topWinTimerTickWrapper, this._interval)
                },
                _rafTick: function() {
                    this._isDetached() ? this._detachmentHandler() : this._rafRequestId = r(this._topWinRafTickWrapper)
                },
                _isDetached: function() {
                    if (t.parentNode && t.ownerDocument.defaultView && window && window.document) {
                        for (var e = t;
                            (e = e.parentNode) && e !== e.ownerDocument.documentElement;);
                        return !e && !(this._timer = null)
                    }
                    return !0
                }
            }
        }, SDKAdapter.prototype._normalizeOrientation = function(e) {
            var t = this.getTopWindow().navigator.userAgent;
            return e = ["Nexus 10", "GT-N8", "SM-P60", "GT-P5", "SCH-19", "GT-P7", "SM-T9", "SM-T8"].some(function(e) {
                return -1 != t.indexOf(e)
            }) && 270 == (e += 90) ? -90 : e
        }, SDKAdapter.prototype._waitForDomReady = function(e) {
            var t = this.getTopWindow().document;
            "interactive" === t.readyState || "loaded" === t.readyState || "complete" === t.readyState ? defer(e, 0, "SDKAdapter._waitForDomReady defer cb", this.useAsap) : t.addEventListener("DOMContentLoaded", e, !1)
        }, SDKAdapter.prototype.waitForWindowLoad = function(e) {
            function t() {
                n || (n = !0, e())
            }
            var i = this.getTopWindow(),
                n = !1;
            "complete" === i.document.readyState ? defer(e, 0, "SDKAdapter.waitForWindowLoad readyState === complete cb", this.useAsap) : (i.setTimeout(t, 3e4), i.addEventListener("load", t, !1))
        }, SDKAdapter.prototype._stopObservingEnvironment = function() {
            this._listening && (this.resizeListener.stop(this._handleResize), this.orientationObserver.stop(), this.documentAttachmentObserver.stop(), this._reportingObserver && this._reportingObserver.stop(), this._listening = !1)
        }, SDKAdapter.prototype.setMinimumSize = function(e, t) {}, SDKAdapter.prototype._handleResize = function() {
            var t = !1;
            this.emit("beforeResize", function(e) {
                t = e
            }), t ? defer(function() {
                this.emit("resize")
            }.bind(this), 700) : this.emit("resize")
        }, SDKAdapter.prototype._handleViewportChange = function() {
            this.emit("viewportChange")
        }, SDKAdapter.prototype._handleOrientationChange = function(e) {
            this.emit("orientationchange", e)
        }, SDKAdapter.prototype._handleDetachedFromDocument = function() {
            this.dismiss()
        }, SDKAdapter.prototype._updatePlacements = function(e) {
            if (e = e || noop, this._keyboardShown) return e();
            var t, i = 0,
                n = this;

            function r() {
                --i || (e && e(), defer(n.emits("placementsUpdated"), void 0, void 0, this.useAsap))
            }
            for (t in this.placements) i++, "undefined" != typeof window && this.placements[t].update(r, this.adBehavior.crossScreenSticky)
        }, SDKAdapter.prototype._stopAllMedia = function(e) {
            this._stopMediaTagsInPlacements(), this.emit("mediaStopRequested", e)
        }, SDKAdapter.prototype._stopMediaTagsInPlacements = function() {
            for (var e in this.placements)
                for (var t = this.placements[e].querySelectorAll("audio,video"), i = 0; i < t.length; i++) t[i].hasAttribute("x-celtra-media") || "function" != typeof t[i].pause || t[i].pause()
        }, SDKAdapter.prototype.createPlacements = function(n, e) {
            var r, t, i = Object.keys(n),
                o = Object.keys(this.placements),
                i = i.filter(function(e) {
                    return -1 == o.indexOf(e)
                }),
                a = this.perf.start("Adapter.createPlacements", i);
            0 == i.length ? (a.end(), defer(e, 0, "SDKAdapter.createPlacements defer no placements callback", this.useAsap)) : (r = new TaskScheduler, t = this.useAsap, r.when(i).run(function() {
                a.end(), defer(e, 0, "SDKAdapter.createPlacements defer callback", t)
            }), i.forEach(function(t) {
                var e = n[t],
                    e = {
                        attachment: e.attachment,
                        positioning: e.positioning,
                        type: e.type
                    },
                    i = this.perf.start("Adapter.createPlacement", {
                        name: t,
                        descriptor: e
                    });
                this.createPlacement(n[t], function(e) {
                    i.end(), this.placements[t] = e, defer(r.notifies(t), 0, "SDKAdapter.createPlacements defer scheduler notifies", this.useAsap)
                }.bind(this))
            }, this))
        }, SDKAdapter.prototype.createPlacement = function(e, t) {
            throw new Error("SDKAdapter.createPlacement not implemented!")
        }, SDKAdapter.prototype.destroyPlacement = function(e) {
            for (var t in this.placements) t == e && (this.placements[t].destroy(), delete this.placements[t])
        }, SDKAdapter.prototype.hook = function(e, t) {
            this._hooks[e] = t
        }, SDKAdapter.prototype.waitForHook = function(e, t) {
            var i = Array.prototype.slice.call(arguments, 1);
            this._hooks[e] ? this._hooks[e].apply(null, i) : t()
        }, SDKAdapter.prototype.getPlacementRect = function(e) {
            return this.nesting.iabFriendlyIframe && this.preventIABFriendlyIFrameBusting ? e.root ? getElementRectRelativeToTopViewport(e.root) : CRect.ZERO : e.root ? CRect.adopt(e.root.getBoundingClientRect()) : CRect.ZERO
        }, SDKAdapter.prototype.getViewportRect = function() {
            var e = this.getTopWindow();
            return CRect.adopt({
                left: 0,
                top: 0,
                width: e.innerWidth,
                height: e.innerHeight
            })
        }, SDKAdapter.prototype.expand = function(e, t) {
            throw new Error("SDKAdapter.expand not implemented!")
        }, SDKAdapter.prototype.collapse = function(e) {
            throw new Error("SDKAdapter.collapse not implemented!")
        }, SDKAdapter.prototype.dismiss = function(e) {
            throw new Error("SDKAdapter.dismiss not implemented!")
        }, SDKAdapter.prototype.resize = function(e, t) {
            throw new Error("SDKAdapter.resize not implemented!")
        }, SDKAdapter.prototype.playVideoInPlayer = function(e, t, i) {
            throw new Error("SDKAdapter.playVideoInPlayer not implemented!")
        }, SDKAdapter.prototype.callPhone = function(e) {
            window.location.href = "tel:" + encodeURIComponent(e)
        }, SDKAdapter.prototype.openBrowserSameWindowInHostileIFrame = function(e) {
            window.top.location.href = e
        }, SDKAdapter.prototype.getApp = function(e) {
            throw new Error("SDKAdapter.getApp not implemented!")
        }, SDKAdapter.prototype.canSaveImage = function() {
            throw new Error("SDKAdapter.canSaveImage not implemented!")
        }, SDKAdapter.prototype.saveImage = function(e, t, i) {
            throw new Error("SDKAdapter.saveImage not implemented!")
        }, SDKAdapter.prototype.getVideoCapabilities = function() {
            return {}
        }, SDKAdapter.prototype.sendToEventMonitor = function(e, t, i, n, r) {}, SDKAdapter.prototype.notifycreativeRendered = function() {}, SDKAdapter.prototype.getTopWindow = function() {
            if (this._topWindow) return this._topWindow;
            var e = window;
            try {
                for (; void 0 !== e.parent.location.href && e.parent.document !== e.document;) e = e.parent
            } catch (e) {}
            return this._topWindow = e
        }, SDKAdapter.prototype.inNativeFullscreen = function() {
            return ["fullscreenElement", "webkitFullscreenElement", "mozFullScreenElement", "msFullscreenElement"].some(function(e) {
                e = this.getTopWindow().document[e];
                return e && null !== e
            }.bind(this))
        }, SDKAdapter.prototype.trackEventsAndOpenBrowser = function(e, t, i, n) {
            if (!e) return this.trackingCenter.track({
                name: "userError",
                userErrorId: "exitUriIsEmpty"
            }), (i || noop)();
            var r = !1,
                o = function() {
                    r || (r = !0, this.openBrowser(e, t, n), i && i())
                }.bind(this);
            this._stopAllMedia(), !this.canOpenUrlInSameWindow || t ? (this.trackingCenter.flush(), o()) : (this.trackingCenter.flush(o), defer(o, this._MAX_TRACKING_DURATION))
        }, SDKAdapter.prototype.openBrowser = function(e, t, i) {
            t ? this._tryOpenInNewWindow(e, i) : this._tryOpenInSameWindow(e, i)
        }, SDKAdapter.prototype._openInNewWindow = function(e) {
            try {
                var t = this.getTopWindow().open(e);
                return !(!t || t.closed || void 0 === t.closed)
            } catch (e) {
                return !1
            }
        }, SDKAdapter.prototype._tryOpenInNewWindow = function(e, t) {
            return !!this._openInNewWindow(e, t) || (this.aggregatorTracking.trackAggregator("openBrowserFailed", 1, {
                targetWindow: "new"
            }), !1)
        }, SDKAdapter.prototype._openInSameWindow = function(e) {
            throw new Error("SDKAdapter._openInSameWindow not implemented!")
        }, SDKAdapter.prototype._tryOpenInSameWindow = function(e, t) {
            try {
                return this._openInSameWindow(e, t), !0
            } catch (e) {
                return this.aggregatorTracking.trackAggregator("openBrowserFailed", 1, {
                    targetWindow: "same"
                }), !1
            }
        }, SDKAdapter.prototype.getPageDimensions = function() {
            var e = this.getTopWindow().document,
                t = e.body,
                e = e.documentElement;
            return {
                height: Math.max(t.scrollHeight, t.offsetHeight, e.clientHeight, e.scrollHeight, e.offsetHeight),
                width: Math.max(t.scrollWidth, t.offsetWidth, e.clientWidth, e.scrollWidth, e.offsetWidth)
            }
        }, SDKAdapter.prototype.saveCalendarEvent = function(e, t) {
            e = e;
            var i = deviceInfo.os.ios() ? (i = adapter.hacks.useDefaultProtocolForSaveCalendar ? "https:" : "webcal:", {
                url: CCalendar.getEventICSUrl(e, i),
                openInNewWindow: !1
            }) : {
                url: CCalendar.getEventGoogleCalendarUrl(e),
                openInNewWindow: !0
            };
            i && i.url ? (this.openBrowser(i.url, i.openInNewWindow), t(!0)) : t(!1)
        }, SDKAdapter.prototype.startObscuringContent = function() {}, SDKAdapter.prototype.stopObscuringContent = function() {}, SDKAdapter.prototype.setOrientationLock = function() {
            return !1
        }, SDKAdapter.prototype.autoLock = function() {
            return !1
        }, SDKAdapter.prototype._getDefaultLockOrientation = function() {
            return "a"
        }, SDKAdapter.prototype.determineLockOrientation = function() {
            var e = creative.unitSizes.modal,
                t = (this.runtimeParams.lockOrientation || this._getDefaultLockOrientation()).toLowerCase();

            function i() {
                return this.orientation % 180 == 0 ? "portrait" : "landscape"
            }
            return "n" === t ? "noOrientation" : "l" === t ? "landscape" : "p" === t ? "portrait" : "a" === t && e ? e.width < e.height ? "portrait" : e.width > e.height ? "landscape" : i() : i()
        }, SDKAdapter.prototype.collectAndTrackData = function(e, t) {
            if ("function" != typeof e) throw new Error("The dataCollector param was not a function.");
            if (void 0 === t) throw new Error("The eventName param was not defined.");
            var i = 1e3;
            defer(function() {
                try {
                    e(function(e) {
                        e.name = t, this.trackingCenter.trackNoLaterThan(e, i)
                    }.bind(this))
                } catch (e) {
                    this.trackingCenter.trackNoLaterThan({
                        name: t,
                        error: e.message + "\n" + e.stack
                    }, i)
                }
            }.bind(this), void 0, void 0, this.useAsap)
        }, SDKAdapter.prototype._createViewportElement = function() {
            var e = document.createElement("div");
            return e.id = "viewport", e.style.cssText = "position: absolute; left: 0; top: 0; width: 100%; height: 100%; overflow: visible;", this.tagWindow.document.body.appendChild(e), e
        }, SDKAdapter.prototype.getViewportGeometry = function(e) {
            return getViewportGeometry(this.getTopWindow(), e)
        }, SDKAdapter.prototype._getViewportElement = function() {
            return this.viewport
        }, SDKAdapter.prototype.listenForExpandErrors = function() {}, SDKAdapter.prototype.enableScroll = function() {}, SDKAdapter.prototype.disableScroll = function() {};;

        function VPAIDAdapter(t, e, i) {
            SDKAdapter.apply(this, arguments), this.viewabilityMeasurable = {
                viewable00: !1,
                viewable501: !1
            }, this.providesCloseButton = !0, this._environmentVars = this.scriptElement.vpaidEnvironment.environmentVars, this._vpaidApi = this.scriptElement.vpaidEnvironment.vpaidApi, this._videoSlot = this._environmentVars.videoSlot, this._slot = this._environmentVars.slot, this._slot.style.minWidth = this._slot.style.minHeight = "100%", this._videoSlotControlsInitialValue = this._videoSlot.controls, this._videoSlot.controls = !1, this.on("documentReady", function() {
                this._reportingObserver && this._reportingObserver.createChildObserverAndStartObserving(this._slot.ownerDocument.defaultView), this._trackSize(), this.emit("ready")
            }.bind(this))
        }
        inherit(VPAIDAdapter, SDKAdapter), VPAIDAdapter.prototype._trackSize = function() {
            this.collectAndTrackData(function(t) {
                function e(t) {
                    return void 0 === t ? "undefined" : t
                }
                t({
                    scope: "vpaid",
                    vpaidAdSize: {
                        width: e(this._vpaidApi.adWidth),
                        height: e(this._vpaidApi.adHeight)
                    },
                    slotSize: {
                        width: e(this._slot.offsetWidth),
                        height: e(this._slot.offsetHeight)
                    },
                    videoElementSize: {
                        width: e(this._videoSlot.offsetWidth),
                        height: e(this._videoSlot.offsetHeight)
                    }
                })
            }.bind(this), "environmentInfo")
        }, VPAIDAdapter.prototype.getSlot = function() {
            return this._slot
        }, VPAIDAdapter.prototype.initVpaid = function(t, e, i) {
            return this._vpaidObject = new VPAIDObject(t, e, creative, i), this._vpaidApi.setup(this._vpaidObject), this._vpaidObject
        }, VPAIDAdapter.prototype.handleError = function(t) {
            this._vpaidApi.handleError(t)
        }, VPAIDAdapter.prototype._onReportReceived = function(t) {
            switch (t) {
                case "heavyAdCPUIntervention":
                case "heavyAdNetworkIntervention":
                    this.trackingCenter.track({
                        name: "userError",
                        userErrorId: t
                    }), this.trackingCenter.flushWithBeacon(), this.emit("stateChange", "dismissed");
                    break;
                default:
                    console.warn("Received unknown report!")
            }
        }, VPAIDAdapter.prototype._createOrientationObserver = function() {
            var t = this,
                e = this.getTopWindow(),
                i = this._slot,
                n = i.offsetHeight,
                o = i.offsetWidth,
                r = 0,
                s = {
                    _orientationChangeEventQueued: !1,
                    start: function() {
                        n = i.offsetHeight, o = i.offsetWidth, r = e.setInterval(function() {
                            var t = this.getOrientation();
                            (i.offsetHeight < i.offsetWidth ? 1 : 0) !== t && this._handler()
                        }.bind(this), 200)
                    },
                    stop: function() {
                        r && e.clearInterval(r)
                    },
                    getOrientation: function() {
                        return n < o ? 1 : 0
                    },
                    _handler: function() {
                        "undefined" != typeof window ? (n = i.offsetHeight, o = i.offsetWidth, t.emit("_rawOrientationChange"), s._orientationChangeEventQueued = !0, t.once("resize", s._trigger), setTimeout(s._trigger, 1e3)) : (r && e.clearInterval(r), t.dismiss())
                    },
                    _trigger: function() {
                        s._orientationChangeEventQueued && (s._orientationChangeEventQueued = !1, defer(t.emits("orientationchange")))
                    }
                };
            return s
        }, VPAIDAdapter.prototype.createPlacement = function(t, e) {
            e = e || noop;
            var i = new DivPlacement(t.attachment);
            i.attachTo(this._slot, function() {
                e(i)
            })
        }, VPAIDAdapter.prototype.dismiss = function(t, e) {
            this._slot.style.minWidth = this._slot.style.minHeight = "", this._videoSlot.controls = this._videoSlotControlsInitialValue, e && this._vpaidObject && (this._vpaidObject.userDismissed = e.consideredUserInitiatedByBrowser), this._stopAllMedia();
            var i = {};
            e && Object.keys(e).forEach(function(t) {
                i[t] = e[t]
            }), this.waitForHook("beforeDismiss", function() {
                this._stopObservingEnvironment(), this.emit("stateChange", "dismissed"), this.trackingCenter.flush(), (t || noop)()
            }.bind(this), i)
        }, VPAIDAdapter.prototype.getVideoCapabilities = function() {
            return {
                videoElement: this._environmentVars && this._videoSlot
            }
        }, VPAIDAdapter.prototype.openBrowser = function(t) {
            this._vpaidApi.handleEvent({
                eventName: "AdClickThru",
                args: [t, randInt(), !0]
            })
        }, VPAIDAdapter.prototype.getApp = function(t) {
            this.openBrowser(t)
        }, VPAIDAdapter.prototype.canSaveImage = function() {
            return !1
        };;

        Logger.initFromRuntimeParams(runtimeParams);

        var urls = {
            "creativeUrl": "https://ads.celtra.com/compiled/041f1fd0/",
            "apiUrl": "https://hub.celtra.com/api/",
            "cachedApiUrl": "https://cache-ssl.celtra.com/api/",
            "staticUrl": "https://cache-ssl.celtra.com/api/static/va289ee2c04/",
            "qrUrl": "https://qr.celtra.com/api/",
            "insecureCreativeUrl": "http://ads.celtra.com/compiled/041f1fd0/",
            "insecureApiUrl": "http://hub.celtra.com/api/",
            "insecureCachedApiUrl": "http://cache.celtra.com/api/",
            "insecureStaticUrl": "http://cache.celtra.com/api/static/va289ee2c04/",
            "insecureQrUrl": "http://qr.celtra.com/api/",
            "trackingUrl": "https://track.celtra.com/",
            "insecureTrackingUrl": "http://track.celtra.com/",
            "geoUrl": "https://geo.celtra.com/",
            "insecureGeoUrl": "http://geo.celtra.com/",
            "customAudiencesUrl": "https://audiences.celtra.com",
            "insecureCustomAudiencesUrl": "http://audiences.celtra.com",
            "shareUrl": "https://hub-user.celtra.com/share/",
            "insecureShareUrl": "http://hub-user.celtra.com/share/"
        };
        urls.resourceUrl = runtimeParams.secure ? urls.staticUrl : urls.insecureStaticUrl;

        var shouldBatchTrackers = !!'';

        var trackingCenter = new TrackingCenter(
            runtimeParams.sessionId,
            runtimeParams.accountId,
            runtimeParams.purpose,
            runtimeParams.secure ? urls.trackingUrl : urls.insecureTrackingUrl,
            trackers,
            runtimeParams._batchTrackers === '1' || shouldBatchTrackers
        );

        if (runtimeParams.fallbackSdkUsed === '1') {
            trackingCenter.track({
                name: 'fallbackSdkUsed',
                sdk: 'VPAID'
            });
        }

        var aggregatorTracking = new AggregatorTracking(trackingCenter);

        var experiments = new Experiments(runtimeParams.variantChoices, trackingCenter);

        var trackingLoadingTimesExperiment = experiments.get('TrackingLoadingTimes');

        var perf = new PerformanceTracker(trackingCenter, experiments);

        if (runtimeParams.clientTimestamp) {
            // Do it manually instead of using var p = perf.start() + p.end() pattern because we want to log
            // times from before PerformanceTracker is available
            perf.collect({
                name: 'redirectDownload',
                type: 'interval',
                id: null,
                startTime: parseFloat(runtimeParams.clientTimestamp) * 1000,
                endTime: runtimeParams.redirectJsClientTimestamp * 1000,
                args: []
            });
        }

        perf.collect({
            name: 'payloadDownload',
            type: 'interval',
            id: null,
            startTime: runtimeParams.redirectJsClientTimestamp * 1000,
            endTime: runtimeParams.payloadJsClientTimestamp * 1000,
            args: []
        });

        var unitName = 'banner';
        var requiredDeviceType = 'Any';

        perf.collect({
            name: 'payLoadDownloadedToAdapter.new',
            type: 'interval',
            id: null,
            startTime: runtimeParams.payloadJsClientTimestamp * 1000,
            endTime: Date.now(),
            args: []
        });

        var perfAdapterNew = perf.start('Adapter.new');
        var adapter = new VPAIDAdapter(runtimeParams, trackingCenter, aggregatorTracking, experiments, urlOpenedOverrideUrls, storeOpenedOverrideUrls, macros, urlOpenedUrlAppendage, clickThroughDestinationUrl, perf);
        perfAdapterNew.end();
        trackingCenter.windowForPixels = adapter.getTopWindow();

        var perfFormatNew = perf.start('Format.new');
        var format = new UniversalInteractiveVideoFormat(adapter, {
            "banner": {
                "size": null,
                "layouts": [{
                    "orientation": "independent",
                    "minSize": {
                        "width": 0,
                        "height": 0
                    },
                    "unitSize": null,
                    "designTimeSize": {
                        "width": 800,
                        "height": 450
                    },
                    "unitAlignment": {
                        "horizontal": "center",
                        "vertical": "center"
                    }
                }]
            }
        });
        perfFormatNew.end();

        (function() {
            // Add head or body, if they do not yet exist
            var html = document.documentElement;
            var head = document.querySelector('head');
            if (!head) {
                head = document.createElement('head');
                html.insertBefore(head, html.firstChild);
            }
            var body = document.querySelector('body');
            if (!body) {
                body = document.createElement('body');
                html.appendChild(body);
            }


            // Add CSS
            var styles = document.createElement('style');
            styles.textContent = "\n.warning-wrapper {\n    font-family : Tahoma, Verdana, sans-serif;\n    width       : 100%;\n    height      : 100%;\n    position    : absolute;\n    z-index     : 100;\n    overflow    : hidden;\n}\n\n.warning-canvas {\n    width  : 100%;\n    height : 100%;\n}\n\n.warning-message-container {\n    position         : absolute;\n    top              : 50%;\n    width            : 100%;\n    transform        : translateY(-50%);\n    background-color : #000000;\n}\n\n.warning-message-container.audio-video-asset {\n    transform        : translateY(-50%) !important;\n}\n\n.warning-message-container .warning-message {\n    max-width      : 300px;\n    margin         : auto;\n    margin-bottom  : 22px;\n    margin-top     : 18px;\n    color          : #ffffff;\n    font-size      : 14px;\n    letter-spacing : 1.05px;\n    line-height    : 22px;\n    text-align     : center;\n    white-space    : normal;\n}\n\n.warning-message-container.audio-video-asset .warning-message {\n    font-size: 18px;\n    max-width: 70%;\n}\n\n.warning-message-container .warning-triangle {\n    width      : 33px;\n    height     : 29px;\n    margin     : auto;\n    margin-top : 22px;\n    display    : none;\n\n    background-image : url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMzMiIGhlaWdodD0iMjkiIHZpZXdCb3g9IjAgMCAzMyAyOSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayI+PGRlZnM+PHBhdGggaWQ9ImEiIGQ9Ik0wIDI3TDE1LjUgMCAzMSAyN3oiLz48L2RlZnM+PGcgZmlsbD0ibm9uZSIgZmlsbC1ydWxlPSJldmVub2RkIj48ZyBzdHJva2UtbGluZWpvaW49InJvdW5kIiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgxIDEpIj48dXNlIGZpbGwtb3BhY2l0eT0iMCIgZmlsbD0iI0ZGRiIgeGxpbms6aHJlZj0iI2EiLz48cGF0aCBzdHJva2U9IiNGRkYiIGQ9Ik0tLjg2NCAyNy41TDE1LjUtMS4wMDQgMzEuODY0IDI3LjVILS44NjR6Ii8+PC9nPjxwYXRoIGQ9Ik0xNi41IDkuNXYxMSIgc3Ryb2tlPSIjRkZGIiBzdHJva2UtbGluZWNhcD0ic3F1YXJlIi8+PHBhdGggZmlsbD0iI0ZGRiIgZD0iTTE2IDIzaDF2MmgtMXoiLz48L2c+PC9zdmc+);\n    background-size  : 33px 29px;\n}\n\n.warning-message-container .warning-hourglass {\n    width      : 19px;\n    height     : 23px;\n    margin     : auto;\n    margin-top : 22px;\n    display    : none;\n\n    background-image : url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTkiIGhlaWdodD0iMjMiIHZpZXdCb3g9IjAgMCAxOSAyMyIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj48ZyBzdHJva2U9IiNGRkYiIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+PHBhdGggZD0iTTMuMyAxNi41aDEyLjRNMTYuNSAyMi41di0xYzAtNC40LTEuNy04LjMtNC4xLTEwIDIuNC0xLjcgNC4xLTUuNiA0LjEtMTB2LTFNMi41LjV2MWMwIDQuNCAxLjcgOC4zIDQuMSAxMC0yLjQgMS43LTQuMSA1LjYtNC4xIDEwdjEiLz48cGF0aCBkPSJNLjUuNWgxOE0uNSAyMi41aDE4IiBzdHJva2UtbGluZWNhcD0ic3F1YXJlIi8+PC9nPjwvc3ZnPg==);\n    background-size  : 19px 23px;\n}\n\n.warning-message-container .show {\n    display: block;\n}\n\n\n.video-controls-wrapper {\n    position : absolute;\n    width    : 100%;\n    height   : 100%;\n}\n\n/* Shrinks the icon's wrapper for 20px at the top and 20px at the bottom\n   to compensate with interscroller's advertisement bars. */\n.video-controls-wrapper.vertical-UI-offset {\n    height : calc(100% - 40px);\n    top    : 20px;\n}\n\n.st0 {\n    fill              : rgba(0, 0, 0, .4);\n    enable-background : new;\n}\n\n.dark-theme .st0 {\n    fill : rgba(255, 255, 255, 1);\n}\n\n.st1 {\n    fill : rgba(255, 255, 255, 1);\n}\n\n.dark-theme .st1 {\n    fill : rgba(0, 0, 0, 0.4);\n}\n\n#container {\n  width    : 375px;\n  height   : 557px;\n  position : absolute;\n}\n\n#top_left_box {\n    position   : absolute;\n    left       : 14px;\n    top        : 14px;\n    width      : 32px;\n    height     : 32px;\n    background : none;\n}\n\n#top_left_box.disable {\n    visibility : hidden;\n}\n\n#inner_top_left_box {\n    position   : absolute;\n    left       : 56px;\n    top        : 14px;\n    width      : 32px;\n    height     : 32px;\n    background : none;\n}\n\n#inner_top_left_box.align_to_left {\n    left : 14px;\n}\n\n#inner_top_left_center_box {\n    position   : absolute;\n    left       : 98px;\n    top        : 14px;\n    width      : 54px;\n    height     : 32px;\n    background : none;\n}\n\n#center_box {\n    position : absolute;\n    width    : 88px;\n    height   : 88px;\n    left     : calc(50% - 44px);\n    top      : calc(50% - 44px);\n}\n\n#custom_center_box {\n    width  : 100%;\n    height : 100%;\n}\n\n#bottom_right_box {\n    position   : absolute;\n    bottom     : 14px;\n    right      : 14px;\n    width      : 32px;\n    height     : 32px;\n    background : none;\n}\n\n#bottom_right_box.disable {\n    visibility : hidden;\n}\n\n#inner_bottom_right_box {\n    position   : absolute;\n    right      : 56px;\n    bottom     : 14px;\n    width      : 32px;\n    height     : 32px;\n    background : none;\n}\n\n#inner_bottom_right_box.align_to_right {\n    right : 14px;\n}\n\n#vignette {\n    position   : absolute;\n    width      : 100%;\n    height     : 100%;\n    background : radial-gradient(circle at center, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 0) 65%, rgba(0, 0, 0, .2) 100%);\n    visibility : hidden;\n}\n\n#vignette.show {\n    visibility : visible;\n}\n\n#bottom-tagline {\n    position       : absolute;\n    width          : 100%;\n    bottom         : 15px;\n    opacity        : 0;\n\n    font-family    : Arial, Helvetica, Helvetica-Light, sans-serif;\n    font-size      : 11px;\n    line-height    : 11px;\n    font-weight    : 400;\n    text-align     : center;\n    letter-spacing : 0.5px;\n    pointer-events : none;\n    color          : white;\n    text-shadow    : 0 1px 2px rgba(0, 0, 0, 0.65);\n}\n\n#bottom-tagline.show {\n    opacity : .45;\n}\n\n/* HIDEABLE WRAPPER (shown by default) */\n\n#hideable-controls {\n    width      : 100%;\n    height     : 100%;\n    visibility : visible;\n    opacity    : 1;\n    transition : visibility 0s, opacity 1.5s;\n}\n\n#hideable-controls.hide {\n    visibility : hidden;\n    opacity    : 0;\n    transition : opacity 1.5s, visibility 1.5s;\n}\n\n/* Motion Permission */\n\n#permission {\n    position   : absolute;\n    width      : 100%;\n    height     : 100%;\n    cursor     : pointer;\n    visibility : hidden;\n    opacity    : 0;\n    transition : opacity 1.5s, visibility 1.5s;\n\n    background    : rgba(0, 0, 0, .4);\n    border-radius : 17px;\n}\n\n#permission.show {\n    visibility : visible;\n    opacity    : 1;\n    transition : visibility 0s, opacity 1.5s;\n}\n\n.permission-content {\n    position       : absolute;\n    top            : 2px;\n    font-family    : Helvetica, Helvetica-Light, sans-serif;\n    font-size      : 9px;\n    line-height    : 10px;\n    font-weight    : 400;\n    text-align     : center;\n    letter-spacing : 0.5px;\n    pointer-events : none;\n    color          : white;\n}\n\n.permission-ring {\n    border        : 2px solid rgba(255, 255, 255, 1);\n    position      : absolute;\n    left          : 50%;\n    top           : 50%;\n    width         : 45px;\n    height        : 24px;\n    border-radius : 17px;\n    transform     : translate(-50%, -50%);\n}\n\n/* BI RADAR 32*/\n\n#radar {\n    position   : absolute;\n    width      : 32px;\n    height     : 32px;\n    cursor     : pointer;\n    visibility : hidden;\n    opacity    : 0;\n    transition : opacity 1.5s, visibility 1.5s;\n}\n\n#radar.show {\n    visibility : visible;\n    opacity    : 1;\n    transition : visibility 0s, opacity 1.5s;\n}\n\n.radarbg {\n    background    : rgba(0, 0, 0, .4);\n    position      : absolute;\n    width         : 30px;\n    height        : 30px;\n    top           : 50%;\n    left          : 50%;\n    transform     : translate(-50%, -50%);\n    border-radius : 15px;\n}\n\n.dark-theme .radarbg {\n    background : rgba(255, 255, 255, 1);\n}\n\n.radardot {\n    background    : rgba(255, 255, 255, 1);\n    position      : absolute;\n    width         : 4px;\n    height        : 4px;\n    border-radius : 50%;\n    left          : 50%;\n    top           : 50%;\n    transform     : translate(-50%, -50%);\n}\n\n.dark-theme .radardot {\n    background : rgba(0,0,0,.4);\n}\n\n.radarring {\n    border        : 2px solid rgba(255, 255, 255, 1);\n    position      : absolute;\n    width         : 22px;\n    height        : 22px;\n    border-radius : 100%;\n    left          : 50%;\n    top           : 50%;\n    transform     : translate(-50%, -50%);\n}\n\n.dark-theme .radarring {\n    border : 2px solid rgba(0, 0, 0, .4);\n}\n\n.radarpizza {\n    border              : 5px solid rgba(255, 255, 255, 1);\n    position            : absolute;\n    width               : 8px;\n    height              : 8px;\n    border-radius       : 100%;\n    border-right-color  : transparent;\n    border-bottom-color : transparent;\n    border-left-color   : transparent;\n    left                : 50%;\n    top                 : 50%;\n    transform           : translate(-50%, -50%) rotate(0deg);\n}\n\n.dark-theme .radarpizza {\n    border              : 5px solid rgba(0, 0, 0, 0.4);\n    border-radius       : 100%;\n    border-right-color  : transparent;\n    border-bottom-color : transparent;\n    border-left-color   : transparent;\n}\n\n.radardotYcontainer {\n    position  : absolute;\n    width     : 32px;\n    height    : 32px;\n    left      : 50%;\n    top       : 50%;\n    transform : translate(-50%, -50%) rotate(0deg);\n}\n\n.radarYdot {\n    background    : rgba(255, 255, 255, 1);\n    position      : absolute;\n    width         : 4px;\n    height        : 4px;\n    border-radius : 100%;\n    left          : 50%;\n    top           : 50%;\n    transform     : translate(-15px, -50%) rotate(0deg);\n}\n\n.dark-theme .radarYdot {\n    background : rgba(0, 0, 0, .4);\n}\n\n/* SOUNDLOADER */\n\n#sound_loader {\n    position   : absolute;\n    width      : 32px;\n    height     : 32px;\n    opacity    : 0;\n    transition : opacity 1.5s;\n}\n\n#sound_loader.show {\n    opacity    : 1;\n    transition : opacity 1.5s;\n}\n\n.sound_loader_rotator {\n    position  : absolute;\n    width     : 32px;\n    height    : 32px;\n    animation : SOUND-ROTORFULL 1.6s linear infinite;\n}\n\n@keyframes SOUND-ROTORFULL {\n    0%   { transform: rotate(0deg); }\n    100% { transform: rotate(360deg); }\n}\n\n.sound-loader-circular {\n    position  : absolute;\n    left      : 0;\n    animation : SOUND-ROTOR 2s ease infinite;\n}\n\n.sound-loader-path {\n    stroke         : rgba(255, 255, 255, 1);\n    stroke-linecap : square;\n    animation      : SOUND-DASH 2s ease infinite;\n}\n\n.sound-loader-circular-bg {\n    position  : absolute;\n    left      : 0;\n    animation : SOUND-ROTOR 2s ease infinite; /*  KILL THE DONUT */\n}\n\n.sound-loader-path-bg {\n    stroke         : rgba(0, 0, 0, .4);\n    stroke-linecap : square;\n    animation      : SOUND-DASH 2s ease infinite; /*  KILL THE DONUT */\n}\n\n@keyframes SOUND-ROTOR {\n    0%, 50% { transform: rotate(0deg); }\n    100%    { transform: rotate(360deg); }\n}\n\n@keyframes SOUND-DASH {\n    0%, 100% { stroke-dasharray:  2px, 90px; stroke-dashoffset: 0; }\n    50%      { stroke-dasharray: 57px, 90px; stroke-dashoffset: 0; }\n}\n\n#sound {\n    position   : absolute;\n    width      : 32px;\n    height     : 32px;\n    cursor     : pointer;\n    visibility : hidden;\n    opacity    : 0;\n    transition : opacity 1.5s, visibility 1.5s;\n}\n\n#sound.show {\n    visibility : visible;\n    opacity    : 1;\n    transition : visibility 0s, opacity 1.5s;\n}\n\n#mute {\n    position : absolute;\n    width    : 32px;\n    height   : 32px;\n}\n\n#unmute {\n    position : absolute;\n    width    : 32px;\n    height   : 32px;\n}\n\n.switch_on {\n    animation : switch_on .4s ease forwards;\n}\n\n@keyframes switch_on {\n    0%   { transform: scale(0); opacity:0; }\n    100% { transform: scale(1); opacity:1; }\n}\n\n.switch_off {\n    animation : switch_off .4s ease forwards;\n}\n\n@keyframes switch_off {\n    0%   { transform: scale(1); opacity:1; }\n    100% { transform: scale(0); opacity:0; }\n}\n\n/* PLAY BUTTON */\n\n.outliner {\n    border        : 2px solid white;\n    opacity       : .2;\n    width         : 84px;\n    height        : 84px;\n    position      : absolute;\n    z-index       : 0;\n    border-radius : 50%;\n}\n\n#play {\n    width     : 88px;\n    height    : 88px;\n    cursor    : pointer;\n    transform : scale(0);\n}\n\n#play.show {\n    animation : ZOOMIN 0.4s ease forwards;\n}\n\n#play.hide {\n    animation : ZOOMINrev 0.4s ease forwards;\n}\n\n.circleBg {\n    background    : rgba(0, 0, 0, .4);\n    width         : 84px;\n    height        : 84px;\n    position      : absolute;\n    left          : 2px;\n    top           : 2px;\n    border-radius : 100%;\n}\n\n.dark-theme .circleBg {\n    background : rgba(255, 255, 255, 1);\n}\n\n.playtriangle {\n    position      : absolute;\n    width         : 0;\n    height        : 0;\n    border-top    : 20px solid transparent;\n    border-left   : 34px solid rgba(255, 255, 255, 1);\n    border-bottom : 20px solid transparent;\n    left          : 32px;\n    top           : 24px;\n}\n\n.dark-theme .playtriangle {\n    border-left : 34px solid rgba(0, 0, 0, .4);\n}\n\n@keyframes ZOOMIN {\n    0%   { transform : scale(0); }\n    100% { transform : scale(1); }\n}\n\n@keyframes ZOOMINrev {\n    0%   { transform : scale(1); }\n    100% { transform : scale(0); }\n}\n\n/* CENTRAL SPINNER 360*/\n\n#spinner360 {\n    position  : absolute;\n    width     : 88px;\n    height    : 88px;\n    z-index   : 0;\n    transform : scale(0);\n}\n\n#spinner360.show {\n    animation : ZOOMIN 0.4s ease forwards;\n}\n\n#spinner360.hide {\n    animation : ZOOMINrev 0.4s ease forwards;\n}\n\n.disk {\n    width           : 90px;\n    height          : 90px;\n    position        : absolute;\n    z-index         : 2;\n    perspective     : 94px;\n    transform-style : preserve-3d;\n    transform       : rotatex(-28deg) rotatey(0deg) rotatez(0deg);\n}\n\n.ring {\n    position        : absolute;\n    clip            : rect(0, 80px, 80px, 40px);\n    height          : 80px;\n    width           : 80px;\n    transform-style : preserve-3d;\n}\n\n.ring:after {\n    clip          : rect(0, 80px, 80px, 40px);\n    content       : '';\n    border-radius : 50%;\n    height        : 80px;\n    width         : 80px;\n    position      : absolute;\n    animation     : animate360globe 1.8s ease-in-out infinite;\n}\n\n@keyframes animate360globe {\n    0%, 40% {\n        box-shadow : inset rgba(255, 255, 255, 1) 0 0 0 2px;\n        transform  : rotate(-180deg) scale(.9) ;\n        opacity    : 0;\n    }\n    70% {\n        box-shadow : inset rgba(255, 255, 255, 1) 0 0 0 8px;\n        opacity    : 1;\n    }\n    100% {\n        box-shadow : inset rgba(255 ,255, 255, 1) 0 0 0 2px;\n        transform  : rotate(180deg) scale(.9);\n        opacity    : 0;\n    }\n}\n\n.dark-theme .ring:after {\n    animation : animate360globeDark 1.8s ease-in-out infinite;\n}\n\n@keyframes animate360globeDark {\n    0%, 40% {\n        box-shadow : inset rgba(0, 0, 0, .4) 0 0 0 2px;\n        transform  : rotate(-180deg) scale(.9) ;\n        opacity    : 0;\n    }\n    70% {\n        box-shadow : inset rgba(0, 0, 0, .4) 0 0 0 8px;\n        opacity    : 1;\n    }\n    100% {\n        box-shadow : inset rgba(0, 0, 0, .4) 0 0 0 2px;\n        transform  : rotate(180deg) scale(.9);\n        opacity    : 0;\n    }\n}\n\n.spin {\n    animation : LOADER 1.8s linear infinite;\n}\n\n@keyframes LOADER {\n    0%, 40% { transform : translate(5px, 0px) rotatex(90deg) rotatey(0deg) rotatez(0deg); }\n    100%    { transform : translate(5px, 4px) rotatex(90deg) rotatey(0deg) rotatez(180deg);}\n}\n\n.t360 {\n    position        : absolute;\n    z-index         : 2;\n    width           : 80px;\n    height          : 80px;\n    transform-style : preserve-3d;\n    left            : 4px;\n    top             : 4px;\n    animation       : T360 1.8s infinite ease;\n}\n\n@keyframes T360 {\n    0%, 40%, 100% { transform : scale(.9); }\n    70%           { transform : scale(.8); }\n}\n\n/* CUSTOM PLAY & REPLAY */\n\n.custom-button {\n    position   : absolute;\n    height     : 100%;\n    width      : 100%;\n    top        : 0;\n    left       : 0;\n    cursor     : pointer;\n    visibility : hidden;\n    opacity    : 0;\n    transform  : opacity 1.5s, visibility 1.5s;\n}\n\n.custom-button.show {\n    visibility : visible;\n    opacity    : 0.8;\n    transition : visibility 0s, opacity 1.5s;\n}\n\n.custom-button img {\n    margin   : auto;\n    position : absolute;\n    top      : 0;\n    right    : 0;\n    bottom   : 0;\n    left     : 0;\n}\n\n/* LOADER */\n\n#loader {\n    position   : absolute;\n    width      : 32px;\n    height     : 32px;\n    opacity    : 0;\n    transition : opacity 1.5s;\n}\n\n#loader.show {\n    opacity    : 1;\n    transition : opacity 1.5s;\n}\n\n.loader_rotator {\n    position  : absolute;\n    width     : 32px;\n    height    : 32px;\n    opacity   : 1;\n    animation : LOADERROTORFULL 1.6s linear infinite;\n}\n\n@keyframes LOADERROTORFULL {\n    0%   { transform: rotate(0deg); }\n    100% { transform: rotate(360deg); }\n}\n\n.loader-circular {\n    position  : absolute;\n    left      : 0;\n    z-index   : 2;\n    animation : ROTOR 2s ease infinite;\n}\n\n.loader-circular-bg {\n    position  : absolute;\n    left      : 0;\n    z-index   : 1;\n    animation : ROTOR 2s ease infinite;\n}\n\n.loader-path {\n    stroke         : rgba(255, 255, 255, 1);\n    stroke-linecap : square;\n    animation      : DASHLOADER 2s ease infinite;\n}\n\n.dark-theme .loader-path {\n    stroke : rgba(0,0,0,.4);\n}\n\n.loader-path-bg {\n    stroke         : rgba(0, 0, 0, .4);\n    stroke-linecap : square;\n    animation      : DASHLOADER 2s ease infinite;\n}\n\n.dark-theme .loader-path-bg {\n    stroke : rgba(255, 255, 255, 1);\n}\n\n@keyframes ROTOR {\n    0%, 50% { transform: rotate(0deg); }\n    100%    { transform: rotate(360deg); }\n}\n\n@keyframes DASHLOADER {\n    0%, 100% {  stroke-dasharray:  2px, 90px; stroke-dashoffset: 0; }\n    50%      {  stroke-dasharray: 70px, 90px; stroke-dashoffset: 0; }\n}\n\n/* COUNTDOWN */\n\n#countdown {\n    position   : absolute;\n    width      : 32px;\n    height     : 32px;\n    cursor     : default;\n    opacity    : 0;\n    transition : opacity 1.5s;\n}\n\n#countdown.show {\n    opacity    : 1;\n    transition : opacity 1.5s;\n}\n\n.celtra-countdown {\n    position : absolute;\n}\n\n.celtra-countdown-canvas {\n    position : absolute;\n    left     : 0;\n    z-index  : 1;\n    top      : 0;\n}\n\n.celtra-countdown-text-wrapper {\n    position : absolute;\n    z-index  : 2;\n    top      : 0;\n    left     : 0;\n\n    display     : -webkit-flexbox;\n    display     : -ms-flexbox;\n    display     : flex;\n    -webkit-user-select : none;\n       -moz-user-select : none;\n        -ms-user-select : none;\n            user-select : none;\n\n    font-family : Helvetica, Helvetica-Light, Arial, sans-serif;\n    color       : '#ffffff';\n\n    -webkit-flex-align  : center;\n    -ms-flex-align         : center;\n        align-items         : center;\n    -ms-flex-pack     : center;\n        justify-content     : center;\n}\n\n.dark-theme .celtra-countdown-text-wrapper {\n    color : rgba(0, 0, 0, .4);\n}\n\n/* RESUME V3 */\n#resume {\n    position   : absolute;\n    width      : 32px;\n    height     : 32px;\n    cursor     : pointer;\n    visibility : hidden;\n    opacity    : 0;\n    transition : opacity 1.5s, visibility 1.5s;\n}\n\n#resume.show {\n    visibility : visible;\n    opacity    : 1;\n    transition : visibility 0s, opacity 1.5s;\n}\n\n.resumebg {\n    background    : rgba(0, 0, 0, .4);\n    position      : absolute;\n    width         : 32px;\n    height        : 32px;\n    border-radius : 50%;\n}\n\n.dark-theme .resumebg {\n    background : rgba(255, 255, 255, 1);\n}\n\n.resume_ring {\n    border        : 2px solid rgba(255, 255, 255, 1);\n    position      : absolute;\n    width         : 22px;\n    height        : 22px;\n    border-radius : 50%;\n    top           : 3px;\n    left          : 3px;\n}\n\n.dark-theme .resume_ring {\n    border : 2px solid rgba(0, 0, 0, .4);\n}\n\n.resume_triangle {\n    position : absolute;\n    width    : 32px;\n    height   : 32px;\n}\n\n/* REPLAY */\n#replay {\n    position   : absolute;\n    width      : 32px;\n    height     : 32px;\n    cursor     : pointer;\n    visibility : hidden;\n    opacity    : 0;\n    transition : opacity 1.5s, visibility 1.5s;\n}\n\n#replay.show {\n    visibility : visible;\n    opacity    : 1;\n    transform  : rotate(360deg) scale(1,1);\n    transition : visibility 0s, transform 0.9s, opacity 1.5s;\n}\n\n.replayer {\n    width     : 32px;\n    height    : 32px;\n    transform : rotate(45deg) scale(1,1);\n}\n\n/* FULLSCREEN */\n#fullscreen_arrow {\n    position   : absolute;\n    width      : 32px;\n    height     : 32px;\n    cursor     : pointer;\n    visibility : hidden;\n    opacity    : 0;\n    transition : opacity 1.5s, visibility 1.5s;\n}\n\n#fullscreen_arrow.show {\n    visibility : visible;\n    opacity    : 1;\n    transform  : visibility 0s, opacity 1.5s;\n}\n\n.topright_arrow {\n    top       : -7px;\n    right     : -7px;\n    transform : translate3d(-2px, 2px, 0) rotate(45deg);\n}\n\n.botleft_arrow {\n    bottom    : -7px;\n    left      : -7px;\n    transform : translate3d(2px, -2px, 0) rotate(225deg);\n}\n\n.corner {\n    position : absolute;\n    width    : 32px;\n    height   : 32px;\n}\n\n.toprightANIMA {\n    animation : TOPRIGHT .666s ease forwards;\n}\n\n.botleftANIMA {\n    animation : BOTLEFT .666s ease forwards;\n}\n\n.toprightANIMAout {\n    animation : TOPRIGHTOUT .666s ease forwards;\n}\n\n.botleftANIMAout {\n    animation : BOTLEFTOUT .666s ease forwards;\n}\n\n@keyframes TOPRIGHT {\n    0%   { transform : translate3d(0, 0, 0) rotate(-135deg); }\n    30%  { transform : translate3d(2px, -2px, 0) rotate(-45deg); }\n    100% { transform : translate3d(-2px, 2px, 0) rotate(45deg); }\n}\n\n@keyframes BOTLEFT {\n    0%   { transform : translate3d(0, 0, 0) rotate(45deg); }\n    30%  { transform : translate3d(-2px, 2px, 0) rotate(135deg); }\n    100% { transform : translate3d(2px, -2px, 0) rotate(225deg); }\n}\n\n@keyframes TOPRIGHTOUT {\n    0%   { transform : translate3d(-2px, 2px, 0) rotate(45deg); }\n    30%  { transform : translate3d(2px, -2px, 0) rotate(45deg); }\n    100% { transform : translate3d(0, 0, 0) rotate(225deg); }\n}\n\n@keyframes BOTLEFTOUT {\n    0%   { transform : translate3d(2px, -2px, 0) rotate(225deg); }\n    30%  { transform : translate3d(-2px, 2px, 0) rotate(225deg); }\n    100% { transform : translate3d(0, 0, 0) rotate(405deg); }\n}\n\n.transplanted-form-element-container input,\n.transplanted-form-element-container textarea,\n.transplanted-form-element {\n    -webkit-user-select: text;\n       -moz-user-select: text;\n        -ms-user-select: text;\n            user-select: text;\n    text-align: center;\n}\n\n.celtra-display-image-overlay {\n    position: absolute;\n    top: 0; right: 0; bottom: 0; left: 0;\n    z-index: 30;\n    background-repeat: no-repeat;\n    background-position: center center;\n    /* background-color and background-image set in code */\n}\n\n.celtra-display-image-overlay .celtra-back-button {\n    position: absolute;\n    top: 0;\n    width: 30px; height: 30px;\n    background: url('https://cache-ssl.celtra.com/api/static/va289ee2c04/runner/clazzes/CreativeUnit/back.png');\n    background-size: 30px 60px;\n}\n\n.celtra-display-image-overlay .celtra-back-button.left {\n    left: 0;\n}\n\n.celtra-display-image-overlay .celtra-back-button.right {\n    right: 0;\n}\n\n.celtra-display-image-overlay .celtra-back-button.touched {\n    background-position: 0 100%;\n}\n\n.celtra-fit-to-container {\n    max-width: 100%;\n    max-height: 100%;\n}\n\n.celtra-center-content {\n    width: 100%;\n    height: 100%;\n    text-align: center;\n}\n.celtra-center-content::before {\n    content: '';\n    display: inline-block;\n    height: 100%;\n    vertical-align: middle;\n}\n.celtra-center-content > * {\n    vertical-align: middle;\n}\n\n.celtra-creative-border {\n    position: absolute;\n    width: 100%;\n    height: 100%;\n    z-index: 20;\n    border-style: solid;\n    box-sizing: border-box;\n    pointer-events: none;\n}\n\n/* Disables green outline on tapped objects on HTC phones running Android */\n* {\n    -webkit-tap-highlight-color : rgba(0,0,0,0);\n    tap-highlight-color         : rgba(0,0,0,0);\n}\n\n.celtra-creative-unit {\n    font-family: Helvetica;\n    font-size: 12pt;\n\n    position: relative; /* so contained elements are positioned OK and 'overflow: hidden' works */\n\n    /*\n      z-indices:\n        10 - screenHolder\n        20 - placeboBar\n        40 - alert\n        30 - displayImageAction overlay\n        100 - close button\n    */\n}\n\n.celtra-screen-holder {\n    position: absolute; top: 0; left: 0; bottom: 0; right: 0;\n    z-index: 10;\n}\n\n.celtra-creative-unit, .celtra-creative-unit * {\n    -webkit-text-size-adjust: none;\n        -ms-text-size-adjust: none;\n            text-size-adjust: none;\n    tap-highlight-color: transparent;\n    touch-callout: none;\n    -webkit-user-select: none;\n       -moz-user-select: none;\n        -ms-user-select: none;\n            user-select: none; /* override to 'text' manually if needed */\n\n    margin: 0;\n    padding: 0;\n}\n\n.celtra-close-button {\n    position: absolute;\n    top: 0; right: 0;\n    width: 30px; height: 30px;\n    z-index: 99999999;\n    overflow: hidden;\n    -webkit-transform: translateZ(0);\n}\n\n.celtra-close-button-text {\n    font-family: Arial, Helvetica, Tahoma, sans-serif;\n    font-size: 12px;\n    line-height: 12px;\n    padding: 10px;\n    white-space: nowrap;\n    width: auto;\n    height: auto;\n    cursor: pointer;\n}\n\n.celtra-close-button.touched {\n    visibility: hidden;\n}\n\n.celtra-close-button-down {\n    visibility: hidden;\n}\n\n.celtra-close-button-up.touched+.celtra-close-button-down {\n    visibility: visible;\n}\n\n.celtra-close-button-text.touched {\n    visibility: visible;\n    padding-top: 11px;\n    padding-right: 9px;\n}\n\n.celtra-creative-unit input,\n.celtra-creative-unit textarea {\n    -webkit-user-select: text;\n       -moz-user-select: text;\n        -ms-user-select: text;\n            user-select: text;\n    text-align: center;\n}\n.celtra-screen {\n    /*\n      Screens themselves have 0 area and visible overflow (default), so\n      click-through from base to master screen works.\n      \n      The master has z-index 0 and the primary screen has z-index 1.\n    */\n    position: absolute;\n    top: 0; left: 0; \n    \n    pointer-events: none;\n    overflow: hidden;\n}\n\n.celtra-screen > * {\n    pointer-events: auto;\n}\n/*\nThis extra CSS attribute is required for Internet Explorer 10+ to correctly\nroute Pointer events to the object being touched. This is mutually exclusive\nwith scrolling, so there's no way to have both at the same time. This will\nalso prevent swipeable banners if any element on stage has the `touchable`\nCSS class, but will generate `tap` events.\n*/\n.touchable { -ms-touch-action: none; }\n\n/*\nOn IE, if a background is not set on an element, it is\ntransparent to mouse and touch events. This forces the\nelement to become opaque to user events.\n*/\n.celtra-screen-object { background: rgba(0,0,0,0); }\n\n/*\nPointer cursor for hotspot component on desktop.\n*/\n.celtra-hotspot { cursor: pointer; }\n\n\n/*\nForce GPU rendering for all children of animated layers in order to prevent jittery scaling animations.\nBecause the `animated` class is added to objects only when SceneOverrides.js is included, this style rule\nwill not affect any elements in creatives which are not using scenes.\n*/\n.celtra-screen-object.animated div,\n.celtra-screen-object.animated > img,\n.celtra-screen-object.animated .celtra-screen-object div,\n.celtra-screen-object.animated .celtra-screen-object > img {\n    transform: perspective(1px);\n}\n\n.celtra-picture {\n    overflow: hidden;\n}\n\n.celtra-picture.empty {\n    background-color: #000000;\n    background-position: center;\n    background-repeat: no-repeat;\n    background-size: 28px;\n    border: 2px solid #404040;\n    box-sizing: border-box;\n}\n\n.celtra-custom-code {\n    overflow: hidden;\n}\n\n";
            head.appendChild(styles);

            // Add JS
            var js = document.createElement('script');
            js.textContent = "function inherit(e,t){function n(){}n.prototype=t.prototype,e.prototype=new n,Object.defineProperties(e,{uber:{get:function(){return t.prototype},enumerable:!1,configurable:!1},uberConstructor:{get:function(){return t},enumerable:!1,configurable:!1}}),e.prototype.constructor=e}function extend(e,t){for(var n=1;n<arguments.length;n++){t=arguments[n];if(t instanceof Object)for(var r in t)e[r]=t[r]}return e}function deep(e,t){for(var n in t)e[n]instanceof Object&&t[n]instanceof Object?deep(e[n],t[n]):e[n]=t[n]}function delayed(e,t){var n=null;return function(){n&&clearTimeout(n),n=setTimeout(e,t)}}function throttled(e,t){var n=null;return function(){n=n||setTimeout(function(){n=null,e()},t)}}function deferred(e,t,n,r){return function(){defer(e,t,n,r)}}function useAsap(){return\"undefined\"!=typeof creative&&creative.adapter&&creative.adapter.useAsap||\"undefined\"!=typeof adapter&&adapter.useAsap}function hasClass(e,t){return e.classList.contains(t)}function addClass(e,t){e.classList.add(t)}function removeClass(e,t){e.classList.remove(t)}function toggleClass(e,t){e.classList.toggle(t)}function cssurl(e){return\"url('\"+e.replace(/'/g,\"\\\\'\")+\"')\"}function camelize(e){return e.replace(/-([a-z])/g,function(e,t){return t.toUpperCase()})}function ucfirst(e){return e.charAt(0).toUpperCase()+e.slice(1)}function zeroPad(e,t){null==t&&(t=2);t=Math.max(0,t-(\"\"+e).length);return(\"\"+Math.pow(10,t)).slice(1)+e}function htmlentitize(e){return e.replace(/&/g,\"&amp;\").replace(/\"/g,\"&quot;\").replace(/'/g,\"&#39;\").replace(/</g,\"&lt;\").replace(/>/g,\"&gt;\")}function removeHtmlTags(e){for(var t,n=\"(?:[^\\\"'>]|\\\"[^\\\"]*\\\"|'[^']*')*\",r=new RegExp(\"<(?:!--(?:(?:-*[^->])*--+|-?)|script\\\\b\"+n+\">[\\\\s\\\\S]*?</script\\\\s*|style\\\\b\"+n+\">[\\\\s\\\\S]*?</style\\\\s*|/?[a-z]\"+n+\")>\",\"gi\");(e=(t=e).replace(r,\"\"))!==t;);return e.replace(/</g,\"&lt;\")}function trim(e){return e.replace(/^\\s+|\\s+$/g,\"\")}function to_utf8(e){return unescape(encodeURIComponent(e))}function isRTLChar(e){return!!(e=String(e).charCodeAt(0))&&(1424<=e&&e<=1535||1536<=e&&e<=1791||1792<=e&&e<=1871||1872<=e&&e<=1919||2144<=e&&e<=2159||2208<=e&&e<=2303||64288<=e&&e<=64335||64336<=e&&e<=65023||65136<=e&&e<=65279||8207===e||8235===e||8238===e)}function isLTRChar(e){return!!(e=String(e).charCodeAt(0))&&(65<=e&&e<=90||97<=e&&e<=122||192<=e&&e<=214||216<=e&&e<=246||248<=e&&e<=696||768<=e&&e<=1424||2048<=e&&e<=8191||11264<=e&&e<=64284||65022<=e&&e<=65135||65277<=e&&e<=65535||8206===e||8234===e||8237===e)}function guessWritingDirection(e){for(var t=Math.min(25,e.length),n=0;n<t;n++){var r=e.charAt(n);if(isLTRChar(r))return\"ltr\";if(isRTLChar(r))return\"rtl\"}return null}function randInt(){return(Math.random()+\"\").slice(2)}function _isListenerOptionSupported(e){var t=_isListenerOptionSupported.opts;if(void 0===t[e]){t[e]=!1;try{window.addEventListener(\"listener-test\",null,Object.defineProperty({},e,{get:function(){t[e]=!0}}))}catch(e){}}return t[e]}function _buildNativeListenerOptions(e){var t;return\"object\"!=typeof e?!!e:_isListenerOptionSupported(\"capture\")?(t={capture:!!e.capture},_isListenerOptionSupported(\"passive\")&&(t.passive=!!e.passive),t):!!e.capture}function _analyzeTouch(e){var t,n=Math.abs(e.firstTouch.x-e.lastTouch.x),r=Math.abs(e.firstTouch.y-e.lastTouch.y);return\"y\"===e.validAxis?t=r>e.minDistanceForSwipe:\"x\"===e.validAxis&&(t=n>e.minDistanceForSwipe),{isHorizontal:r<n,isConsideredSwipe:t}}function attach(e,t,n,r){t=getEventNames()[t.toLowerCase()]||t;e.addEventListener(t,n,_buildNativeListenerOptions(r))}function detach(e,t,n,r){t=getEventNames()[t.toLowerCase()]||t;e.removeEventListener(t,n,_buildNativeListenerOptions(r))}function once(t,n,r,o){attach(t,n,function e(){detach(t,n,e,o),r.apply(this,arguments)},o)}function trigger(e,t,n,r){var o=document.createEvent(\"HTMLEvents\"),n=(o.initEvent(t,n,r),\"on\"+ucfirst(t));return\"function\"==typeof e[n]&&e[n](),e.dispatchEvent(o)}function fakeclick(t,e){var n=(e=e||window).document.createElement(\"a\"),e=(n.cssText=\"visibility: hidden\",n.addEventListener(\"click\",function(e){t(),e.preventDefault(),e.stopPropagation(),n.parentNode.removeChild(n)},!0),e.document.body.appendChild(n),document.createEvent(\"MouseEvents\"));e.initEvent(\"click\",!0,!0),n.dispatchEvent(e)}function fakeClickAhrefBlank(e,t,n){t=t||noop;var r=(n=n||window).document.createElement(\"a\"),e=(r.style.cssText=\"visibility: hidden\",r.setAttribute(\"href\",e),r.setAttribute(\"target\",\"_blank\"),r.addEventListener(\"click\",function(e){e.stopPropagation(),t(),r.parentNode.removeChild(r)},!0),n.document.body.appendChild(r),n.document.createEvent(\"MouseEvents\"));e.initEvent(\"click\",!0,!0),r.dispatchEvent(e)}!function(a){if(!this.defer){try{for(;void 0!==a.parent.location.href&&a.parent.document!==a.document;)a=a.parent}catch(e){}e=\"function\"==typeof(u=a).setImmediate;var t,n,r,o,i,c,u,e,s=u.MutationObserver?function(e){t=document.createElement(\"div\"),new MutationObserver(function(){e(),t=null}).observe(t,{attributes:!0}),t.setAttribute(\"i\",\"1\")}:!e&&u.postMessage&&!u.importScripts&&u.addEventListener?(o=\"com.setImmediate\"+Math.random(),i=0,c={},u.addEventListener(\"message\",function(e){e.source===u&&0===e.data.indexOf(o)&&(e=e.data.split(\":\")[1],c[e](),delete c[e])},!1),function(e){var t=9007199254740991===i?0:++i;c[t]=e,u.postMessage(o+\":\"+t,\"*\")}):!e&&u.document&&\"onreadystatechange\"in document.createElement(\"script\")?function(e){(n=document.createElement(\"script\")).onreadystatechange=function(){n.onreadystatechange=null,n.parentNode.removeChild(n),n=null,e()},document.body.appendChild(n)}:(r=e&&setImmediate||setTimeout,function(e){r(e)});this.defer=function(e,t,n,r){var o,i,t=0|t;n&&(void 0!==defer.perf?i=defer.perf:\"undefined\"!=typeof creative?i=creative.perf:\"undefined\"!=typeof perf&&(i=perf),i&&!i._stopTrackingDefers&&(o=i.start(\"defer\",{deferId:n,delay:t}))),e&&(i=function(){o&&o.end(),e()},r?s(i):a.setTimeout(i,t))}}}(window),Function.prototype.bind||Object.defineProperty(Function.prototype,\"bind\",{value:function(e){var t=this;return function(){return t.apply(e,arguments)}}}),function(t){t.getEventNames=function(){var e={};return\"WebKitAnimationEvent\"in t&&(e.animationstart=\"webkitAnimationStart\",e.animationiteration=\"webkitAnimationIteration\",e.animationend=\"webkitAnimationEnd\"),\"WebKitTransitionEvent\"in t&&(e.transitionend=\"webkitTransitionEnd\"),e}}(window),_isListenerOptionSupported.opts={};var getTimestamp=void 0===window.performance||void 0===window.performance.now?Date.now:window.performance.now.bind(window.performance);function noop(){}function nullai(e,t){t&&t()}function retTrue(){return!0}function retFalse(){return!1}function offset(e){var t=e.getBoundingClientRect(),e=e.ownerDocument,n=e.documentElement,e=e.defaultView;return{top:t.top+(e.pageYOffset||n.scrollTop)-(n.clientTop||0),left:t.left+(e.pageXOffset||n.scrollLeft)-(n.clientLeft||0)}}function addCssRule(e,t,n){var r=document.createElement(\"style\");r.textContent=e+\" {\"+t+\"}\",(n?n.document:document).getElementsByTagName(\"head\")[0].appendChild(r)}function redrawAndroidIframe(){var e=document.createElement(\"style\");document.body.appendChild(e),document.body.removeChild(e)}function parseQuery(e){var t={};return(e=e.replace(/\\&$/,\"\").replace(/\\+/g,\"%20\")).split(\"&\").forEach(function(e){e=e.split(\"=\").map(decodeURIComponent);t[e[0]]=e[1]}),t}function buildQuery(e){var t,n=[];for(t in e)n.push(encodeURIComponent(t)+\"=\"+encodeURIComponent(e[t]));return n.join(\"&\")}function postBlob(e,t){var n=new XMLHttpRequest;n.open(\"POST\",creative.apiUrl+\"blobs?base64=1\"),n.setRequestHeader(\"Content-Type\",\"application/octet-stream\"),n.onreadystatechange=function(){4===n.readyState&&t(n.responseText,n.status)},n.send(e)}function tmpl(e,t){if(!e)return\"\";var n;if(-1==e.indexOf(\"<%\"))n=function(){return e};else{var r=e.split(/<%\\s*|\\s*%>/g),o=\"var p = []; with(o) {\\n\",i=!1;r.forEach(function(e){i?\"=\"==e[0]?o+=\"  p.push(\"+e.replace(/^=\\s*|\\s*$/g,\"\")+\");\\n\":o+=\"  \"+e+\"\\n\":e&&(o+=\"  p.push('\"+e.replace(/'/g,\"\\\\'\").split(/\\r?\\n/g).join(\"\\\\n');\\n  p.push('\")+\"');\\n\"),i=!i}),o+='} return p.join(\"\");';try{n=new Function(\"o\",o)}catch(e){r=new Error(\"Cannot parse template! (see `template` property)\");throw r.template=o,r}}return t?n(t):n}function flash(){var e=document.createElement(\"div\");e.style.background=\"white\",e.style.opacity=.005,e.style.position=\"absolute\",e.style.top=0,e.style.left=0,e.style.width=\"100%\",e.style.height=\"100%\",e.style.zIndex=2147483647,document.body.appendChild(e),setTimeout(function(){e.parentNode.removeChild(e)},0)}function crc32(e){\"use strict\";for(var t=-1,n=0,r=[0,-227835133,-516198153,324072436,-946170081,904991772,648144872,-724933397,-1965467441,2024987596,1809983544,-1719030981,1296289744,-1087877933,-1401372889,1578318884,274646895,-499825556,-244992104,51262619,-675000208,632279923,922689671,-996891772,-1702387808,1760304291,2075979607,-1982370732,1562183871,-1351185476,-1138329528,1313733451,549293790,-757723683,-1048117719,871202090,-416867903,357341890,102525238,-193467851,-1436232175,1477399826,1264559846,-1187764763,1845379342,-1617575411,-1933233671,2125378298,820201905,-1031222606,-774358714,598981189,-143008082,85089709,373468761,-467063462,-1170599554,1213305469,1526817161,-1452612982,2107672161,-1882520222,-1667500394,1861252501,1098587580,-1290756417,-1606390453,1378610760,-2032039261,1955203488,1742404180,-1783531177,-878557837,969524848,714683780,-655182201,205050476,-28094097,-318528869,526918040,1361435347,-1555146288,-1340167644,1114974503,-1765847604,1691668175,2005155131,-2047885768,-604208612,697762079,986182379,-928222744,476452099,-301099520,-44210700,255256311,1640403810,-1817374623,-2130844779,1922457750,-1503918979,1412925310,1197962378,-1257441399,-350237779,427051182,170179418,-129025959,746937522,-554770511,-843174843,1070968646,1905808397,-2081171698,-1868356358,1657317369,-1241332974,1147748369,1463399397,-1521340186,-79622974,153784257,444234805,-401473738,1021025245,-827320098,-572462294,797665321,-2097792136,1889384571,1674398607,-1851340660,1164749927,-1224265884,-1537745776,1446797203,137323447,-96149324,-384560320,461344835,-810158936,1037989803,781091935,-588970148,-1834419177,1623424788,1939049696,-2114449437,1429367560,-1487280117,-1274471425,1180866812,410100952,-367384613,-112536529,186734380,-538233913,763408580,1053836080,-860110797,-1572096602,1344288421,1131464017,-1323612590,1708204729,-1749376582,-2065018290,1988219213,680717673,-621187478,-911630946,1002577565,-284657034,493091189,238226049,-61306494,-1307217207,1082061258,1395524158,-1589280451,1972364758,-2015074603,-1800104671,1725896226,952904198,-894981883,-638100751,731699698,-11092711,222117402,510512622,-335130899,-1014159676,837199303,582374963,-790768336,68661723,-159632680,-450051796,390545967,1230274059,-1153434360,-1469116676,1510247935,-1899042540,2091215383,1878366691,-1650582816,-741088853,565732008,854102364,-1065151905,340358836,-433916489,-177076669,119113024,1493875044,-1419691417,-1204696685,1247431312,-1634718085,1828433272,2141937292,-1916740209,-483350502,291187481,34330861,-262120466,615137029,-691946490,-980332558,939183345,1776939221,-1685949482,-1999470558,2058945313,-1368168502,1545135305,1330124605,-1121741762,-210866315,17165430,307568514,-532767615,888469610,-962626711,-707819363,665062302,2042050490,-1948470087,-1735637171,1793573966,-1104306011,1279665062,1595330642,-1384295599],n=0;n<e.length;n++)t=t>>>8^r[255&(t^e.charCodeAt(n))];return(-1^t)>>>0}function isArray(e){return\"[object Array]\"===Object.prototype.toString.call(e)}function isDefAndNotNull(e){return null!=e}function updateQueryStringParameter(e,t,n){t=encodeURIComponent(t),n=encodeURIComponent(n);var r=new RegExp(\"([?|&])\"+t+\"=.*?(&|$)\",\"i\");return separator=-1!==e.indexOf(\"?\")?\"&\":\"?\",e.match(r)?e.replace(r,\"$1\"+t+\"=\"+n+\"$2\"):e+separator+t+\"=\"+n}function fetchShortenedUrl(r,e,t){t=t||noop;var n,o=!1,i=fetchShortenedUrl.inProgress,a=fetchShortenedUrl.cache;function c(t,n){i[r].forEach(function(e){e[t](n)}),delete i[r]}\"http\"==!r.slice(0,4)?t():a[r]?defer(function(){e(a[r])},0,\"fetchShortenedUrl defer success callback\"):(i[r]||(i[r]=[],o=!0),i[r].push({success:e,error:t}),o&&(t=(creative.runtimeParams.secure?creative.cachedApiUrl:creative.insecureCachedApiUrl)+\"shortenedUrls/\",o={url:r,fields:\"shortUrlKey\"},n={cbName:\"shortener_\"+btoa(to_utf8(r)).replace(/\\//g,\"$\").replace(/\\+/g,\"_\").replace(/=/g,\"\")},loadJSONP(t+\"?\"+buildQuery(o),n,function(e){e=creative.shareUrl+e.shortUrlKey;c(\"success\",a[r]=e)},function(){requestCreateShortenedUrl(r,c)})))}function requestCreateShortenedUrl(t,n){var e=(creative.runtimeParams.secure?creative.apiUrl:creative.insecureApiUrl)+\"shortenedUrls/\",r=fetchShortenedUrl.cache,o=new XMLHttpRequest;o.open(\"POST\",e),o.setRequestHeader(\"Content-Type\",\"application/json; charset=utf-8\"),o.onreadystatechange=function(){var e;4===o.readyState&&(201===o.status?(e=JSON.parse(o.responseText),e=creative.shareUrl+e.shortUrlKey,r[t]=e,n(\"success\",e)):n(\"error\"))},o.send(JSON.stringify({url:t}))}function isMediaPlaying(e){try{return 0<e.currentTime&&!e.paused&&!e.ended}catch(e){return!1}}function merge(){for(var e,t={},n=0;n<arguments.length;n+=1)if(null!=(e=arguments[n]))for(var r in e)t[r]=e[r];return t}function clamp(e,t,n){return Math.max(e,Math.min(t,n))}function lerp(e,t,n){return e+n*(t-e)}function map(e,t,n,r,o){return(o-e)/(t-e)*(r-n)+n}function step(e,t){return t<e?0:1}function pulse(e,t,n){return step(e,n)-step(t,n)}function smoothstep(e,t,n){n=clamp(0,1,(n-e)/(t-e));return n*n*n*((6*n-15)*n+10)}function bump(e,t,n){n=clamp(0,1,(n-e)/(t-e));return(Math.cos(Math.PI*n)+1)/2}function getWindowNesting(e){var t={iframe:e!==e.top,friendlyIframe:!1,iabFriendlyIframe:!1,hostileIframe:!1,iframeDepth:0};if(t.iframe){var n=e;try{for(t.friendlyIframe=!!e.top.location.href,t.iabFriendlyIframe=t.friendlyIframe&&void 0!==e.inDapIF&&e.inDapIF;void 0!==n.parent.location.href&&n.parent.document!==n.document;)n=n.parent,t.iframeDepth++;void 0===e.top.document&&(t.hostileIframe=!0)}catch(e){t.hostileIframe=!0}}return t}function isFetchSupported(){return!!(window.fetch&&window.URL&&URL.createObjectURL)}function isBeaconSupported(){return!(!window.navigator||!window.navigator.sendBeacon)}function isNativeImplementation(e){return\"function\"==typeof e&&-1<e.toString().indexOf(\"[native code]\")}function getViewportGeometry(e,t){var n=deviceInfo.deviceType.desktop()&&\"BackCompat\"!=e.document.compatMode,r=deviceInfo.os.ios(\"9\",null),o=e.document.documentElement||{};return{width:\"win\"!==t&&(n||r||\"doc\"===t)?o.clientWidth:e.innerWidth,height:\"win\"!==t&&(n||\"doc\"===t)?o.clientHeight:e.innerHeight,left:e.scrollX||o.scrollLeft||0,top:e.scrollY||o.scrollTop||0}}function safeQuerySelector(e,t){try{return e.querySelector(t)}catch(e){return null}}function safeQuerySelectorAll(e,t){try{return e.querySelectorAll(t)}catch(e){return[]}}function getElementRectRelativeToTopViewport(e){var t=e?CRect.adopt(e.getBoundingClientRect()):CRect.ZERO,n=e.ownerDocument&&e.ownerDocument.defaultView||null;try{for(;n&&n!==n.parent&&void 0!==n.parent.location.href&&n.parent.document!==n.document&&n.frameElement;){var r=CRect.adopt(n.frameElement.getBoundingClientRect());t.left+=r.left,t.top+=r.top,n=n.parent}}catch(e){}return t}Date.now||(Date.now=function(){return+new Date}),Array.prototype.waitForEach||Object.defineProperty(Array.prototype,\"waitForEach\",{value:function(o,i,a){var c=this.length;c?this.forEach(function(e,t,n){var r=!1;o.call(a,e,function(){if(r)throw\"Called `done` multiple times for element \"+t;r=!0,--c||i()},t,n)},a):i()}}),function(i){i.loadJS=function(t,e,n,r){var o=i.loadJS.externals,e={success:e||noop,error:n||noop};if(!/^[A-Za-z0-9]*:\\/\\/|^\\/\\//.test(t)){var n=creative.hostedFiles.filter(function(e){return e.filepath===t})[0];if(void 0===n)return console.warn('Hosted file \"'+t+'\" was not found.'),void e.error();if(!1!==n.loaded)return console.warn('Hosted file \"'+t+'\" is already loaded.'),void setTimeout(e.success,0);n.loaded=!0,t=creative.cachedApiUrl+\"hostedFiles/\"+creative.id+\"/\"+creative.version+\"/\"+t}t in o&&!r?o[t].loaded?setTimeout(e.success,0):o[t].cbs.push(e):(o[t]={cbs:[e]},(n=document.createElement(\"script\")).type=\"text/javascript\",n.onload=function(){o[t].loaded=!0,o[t].cbs.forEach(function(e){e.success()}),o[t].cbs=[]},n.onerror=function(){o[t].cbs.forEach(function(e){e.error()}),o[t].cbs=[],delete o[t]},n.src=t,i.loadJS.appendToRoot(n))},i.loadJS.externals={},i.loadJS.appendToRoot=function(e){return document.querySelector(\"head\").appendChild(e)},i.loadJSONP=function(e,t,n,r){\"function\"==typeof t&&(r=n,n=t,t={});var o=t.cbName||\"__jsonp\"+randInt();e+=(-1==e.indexOf(\"?\")?\"?\":\"&\")+(t.paramName||\"jsonp\")+\"=\"+o,i[o]=function(e){n(e),delete i[o]},loadJS(e,noop,r,!0)}}(window),void 0===Function.prototype.name&&function(){var t=/^function\\s+(\\w+?)\\s*?\\(/;Object.defineProperty(Function.prototype,\"name\",{get:function(){var e=this.constructor.prototype.toString.call(this);return t.test(e)?e.match(t)[1]:\"\"}})}(),fetchShortenedUrl.cache={},fetchShortenedUrl.inProgress={};;\nfunction CPoint(t,i){this.x=+t||0,this.y=+i||0}function CRect(t,i,e,o){this.left=+t||0,this.top=+i||0,this.width=+e||0,this.height=+o||0}CPoint.offset=function(t){t=offset(t);return new CPoint(t.left,t.top)},CPoint.convertPointFromNodeToPage=function(t,i){return t&&void 0===t.x?(t=CPoint.offset(t),new CPoint((i?i.x:0)+(t?t.x:0),(i?i.y:0)+(t?t.y:0))):null},CPoint.convertPointFromPageToNode=function(t,i){return t&&void 0===t.x?(t=CPoint.offset(t),new CPoint((i?i.x:0)-(t?t.x:0),(i?i.y:0)-(t?t.y:0))):null},CPoint.sample=function(t,i,e){for(var o=Math.floor(Math.sqrt(t.width*i/t.height)),n=Math.floor(Math.sqrt(t.height*i/t.width)),h=t.width/o,r=t.height/n,s=0;s<n;s++)for(var f=0;f<o;f++)e((f+.5)*h+t.left,(s+.5)*r+t.top)},CPoint.epsilon=function(t,i,e){return Math.abs(t-i)<e},CPoint.prototype.equals=function(t){return CPoint.epsilon(this.x,t.x,.25)&&CPoint.epsilon(this.y,t.y,.25)},CRect.ZERO=new CRect,Object.defineProperties(CRect.prototype,{right:{get:function(){return this.left+this.width},set:function(t){var i=this.left;t<this.left&&(this.left=t,t=i),this.width=t-this.left},enumerable:!0},bottom:{get:function(){return this.top+this.height},set:function(t){var i=this.top;t<this.top&&(this.top=t,t=i),this.height=t-this.top},enumerable:!0},tl:{get:function(){return new CPoint(this.left,this.top)}},tr:{get:function(){return new CPoint(this.right,this.top)}},bl:{get:function(){return new CPoint(this.left,this.bottom)}},br:{get:function(){return new CPoint(this.right,this.bottom)}},area:{get:function(){return this.width*this.height}}}),CRect.adopt=function(t){return new CRect(t.left,t.top,t.width,t.height)},CRect.prototype.zero=function(){return 0===this.area},CRect.prototype.intersect=function(t){var i,e,o;return!!t&&(i=Math.max(this.left,t.left),e=Math.max(this.top,t.top),o=Math.min(this.right,t.right)-i,t=Math.min(this.bottom,t.bottom)-e,o<0||t<0?CRect.ZERO:new CRect(i,e,o,t))},CRect.prototype.copyFrom=function(t){this.left=t.left,this.top=t.top,this.width=t.width,this.height=t.height},CRect.prototype.relativeTo=function(t){return new CRect(this.left-t.left,this.top-t.top,this.width,this.height)},CRect.prototype.contains=function(t){return t instanceof CPoint?t.x>=this.left&&t.x<=this.right&&t.y>=this.top&&t.y<=this.bottom:t instanceof CRect?t.left>=this.left&&t.right<=this.right&&t.top>=this.top&&t.bottom<=this.bottom:void 0},CRect.prototype.map=function(t,i){var e=i.width/t.width,o=i.height/t.height;return new CRect((this.left-t.left)*e+i.left,(this.top-t.top)*o+i.top,this.width*e,this.height*o)},CRect.prototype.equals=function(t){return t&&this.left==t.left&&this.top==t.top&&this.right==t.right&&this.bottom==t.bottom},CRect.prototype.toString=function(){return\"CRect: \"+JSON.stringify(this)};;\nfunction Logger(e){if(!(this instanceof Logger))return new Logger(e);e=e||\"\";var n=noop;(0<=Logger.enabledNames.indexOf(e)||0<=Logger.enabledNames.indexOf(\"all\"))&&(n=function(){var e=[].slice.apply(arguments);e.unshift(Logger._ts()+\" [\"+Logger.sessionId+(this.name?\" \"+this.name:\"\")+\"]\"),console.log.apply(console,e)}),this.name=e,this.log=n,this.warn=n,this.debug=n,this.error=n}Logger._ts=function(){var e=new Date;function n(e,n){for(e+=\"\";e.length<n;)e=\"0\"+e;return e}return e.getFullYear()+\"-\"+n(e.getMonth()+1,2)+\"-\"+n(e.getDate(),2)+\" \"+n(e.getHours(),2)+\":\"+n(e.getMinutes(),2)+\":\"+n(e.getSeconds(),2)+\".\"+n(e.getTime()-1e3*Math.floor(e.getTime()/1e3),3)},Logger.init=function(e,n){Logger.enabledNames=e,Logger.sessionId=n||(Math.random()+\"\").slice(15)},Logger.initFromRuntimeParams=function(e){Logger.init(e.debug?e.debug.split(\",\"):[],e.sessionId)};;\nwindow.CeltraDeviceInfo={},function(o){function s(e,n){this.os=new t(e.osName,e.osVersion),this.browser=new r(e.browserName,e.browserVersion,n),this.engine=new i(e.browserRenderingEngine,e.browserRenderingEngineVersion),this.deviceType=new a(e.deviceType,e.mobileDevice),this.vendor=e.vendor||\"\",this.model=e.model||\"\",this.userAgent=n}function t(e,n){this.name=e||\"\",this.version=n||\"0\"}function r(e,n,t){this.name=e||\"\",this.version=n||\"0\",this.userAgent=t}function i(e,n){this.name=e||\"\",this.version=n||\"0\"}function a(e,n){this.hardwareType=e||\"\",this.mobile=n||\"\"}function h(e,n){var t,r,i;if(!e)return!0;if(0===n.length)i=r=null;else if(1===n.length)r=n[0],i=n[0];else{if(2!==n.length)throw\"Invalid number of arguments\";r=n[0],i=n[1]}function o(e){return parseInt(e.replace(/\\D/g,\"\"),10)}for(e=e.split(\".\").map(o),r=r?r.split(\".\").map(o):[],i=i?i.split(\".\").map(o):[],t=Math.max(r.length,i.length)-e.length;0<=t;t--)e.push(0);function s(e,n){for(var t=0;t<Math.min(e.length,n.length);t++){if(e[t]<n[t])return-1;if(e[t]>n[t])return 1}return 0}return!(-1===s(e,r)||1===s(e,i))}t.prototype.android=function(){return\"Android\"===this.name&&h(this.version,arguments)},t.prototype.osx=function(){return\"OSX\"===this.name&&h(this.version,arguments)},t.prototype.ios=function(){return\"IOS\"===this.name&&h(this.version,arguments)},t.prototype.windows=function(){return\"Windows\"===this.name&&h(this.version,arguments)},t.prototype.windowsPhone=function(){return\"WindowsPhone\"===this.name&&h(this.version,arguments)},t.prototype.linux=function(){return\"Linux\"===this.name},r.prototype.safari=function(){return\"Safari\"===this.name&&h(this.version,arguments)},r.prototype.chrome=function(){return 0===this.name.indexOf(\"Chrom\")&&h(this.version,arguments)},r.prototype.ie=function(){return 0===this.name.indexOf(\"Internet Explorer\")&&h(this.version,arguments)},r.prototype.android=function(){return\"Android Browser\"===this.name&&h(this.version,arguments)},r.prototype.samsung=function(){return\"Samsung Browser\"===this.name&&h(this.version,arguments)},r.prototype.opera=function(){return 0===this.name.indexOf(\"Opera\")&&h(this.version,arguments)},r.prototype.silk=function(){return\"Amazon Silk\"===this.name&&h(this.version,arguments)},r.prototype.firefox=function(){return 0===this.name.indexOf(\"Firefox\")&&h(this.version,arguments)},r.prototype.edge=function(){return\"Edge\"===this.name&&h(this.version,arguments)},r.prototype.miui=function(){return\"MIUI Browser\"===this.name&&h(this.version,arguments)},r.prototype.webView=function(){var e,n,t=null;return\"iP\"===(o.navigator.platform||\"\").substr(0,2)?(e=-1!==(e=this.userAgent).indexOf(\"Safari\")&&-1!==e.indexOf(\"Version\"),n=!/constructor/i.test(o.HTMLElement),e&&!o.navigator.standalone?t=\"iOS Safari or Safari Controller\":(n||o.indexedDB)&&o.statusbar.visible?(o.webkit&&o.webkit.messageHandlers||n||o.indexedDB)&&(t=\"WKWebView\"):t=\"UIWebView\"):/^.*\\/\\d\\.\\d \\(.*wv\\).*/.test(this.userAgent)&&(t=\"AndroidWebView\"),t},r.prototype.facebookApp=function(){var e=this.userAgent;return-1<e.indexOf(\"FBAN\")||-1<e.indexOf(\"FBAV\")},i.prototype.webkit=function(){return\"WebKit\"===this.name&&h(this.version,arguments)},i.prototype.gecko=function(){return\"Gecko\"===this.name&&h(this.version,arguments)},i.prototype.trident=function(){return\"Trident\"===this.name&&h(this.version,arguments)},i.prototype.presto=function(){return\"Presto\"===this.name&&h(this.version,arguments)},i.prototype.blink=function(){return\"Blink\"===this.name&&h(this.version,arguments)},a.prototype.phone=function(){return\"Phone\"===this.hardwareType},a.prototype.tablet=function(){return\"Tablet\"===this.hardwareType},a.prototype.desktop=function(){return\"Desktop\"===this.hardwareType},a.prototype.mobileDevice=function(){return!0===this.mobile},o.CeltraDeviceInfo.create=function(e,n){var t,r,i;return e=e||o.navigator.userAgent||\"\",r=n=n||{},-1===(t=e).indexOf(\"Playstation\")&&(i=t.match(/\\bSilk\\/([0-9._-]+)\\b/))&&(r.browserName=\"Amazon Silk\",r.browserVersion=i[1]),r.browserName&&r.browserVersion&&(0===r.browserName.indexOf(\"Chrome\")&&h(r.browserVersion,[\"28\",null])||0===r.browserName.indexOf(\"Opera\")&&h(r.browserVersion,[\"15\",null]))&&(r.browserRenderingEngine=\"Blink\"),r.browserRenderingEngineVersion||(r.browserRenderingEngineVersion=(i=(i=(i=t.match(/Trident\\/([0-9.]+)/))||((i=t.match(/Presto\\/([0-9.]+)/))||t.match(/AppleWebKit\\/*([0-9.]+)/i)))||t.match(/Gecko\\/*([0-9.]+)/))?i[1]:\"\"),new s(n,e)}}(window);;\nwindow.deviceInfo=CeltraDeviceInfo.create(window.navigator.userAgent,window.celtraDeviceInfoRuntimeParams);;\n!function(e){e.celtra=e.celtra||{};var t={},u=(t.ios=i,t.android=r,t.webkit=function(){var e=n().match(/AppleWebKit\\/*([0-9.]+)/i);return!!e&&a(e[1],arguments)},t.windowsPhone=o,t.gecko=function(){var e=n().match(/Gecko\\/*([0-9.]+)/i);return!!e&&a(e[1],arguments)},t.windows=function(){var e=n().match(/MSIE ([0-9]{1,}[.0-9]{0,})|Trident.* rv:([0-9]{1,}[.0-9]{0,})/i);return!!e&&a(e[1]||e[2],arguments)},t.kindleSilk=function(){var e=n().match(/Silk\\/([0-9.]+)/);return!!e&&a(e[1],arguments)},t.kindle=function(){var e=n().toLowerCase();return!!/kftt|kfot|kfjwi|kfjwa|kfso|kfth|kfapwi|kfthwi|kfsowi|kfthwa|kfapwa|kfap|sd4930ur/.test(e)&&a(\"2\",arguments)},t.iframe=function(){return u.top!==e},t.desktop=function(){return!(r()||i()||o())},t.tablet=function(){return/iPad/.test(n())||550<(\"undefined\"!=typeof offsetWidthOverride?offsetWidthOverride:(document.body.offsetWidth,Math.min(innerWidth,innerHeight)))},t.chromeBased=function(){if(!(i()||window.chrome&&\"Google Inc.\"===window.navigator.vendor))return!1;var e;e=i()?n().match(/CriOS\\/([0-9]+.)/):n().match(/Chrome\\/([0-9]+.)/);return!!e&&a(e[1],arguments)},t.isHighDensityDisplay=function(){return 1<window.devicePixelRatio||window.matchMedia&&window.matchMedia(\"(-webkit-min-device-pixel-ratio: 1.5),(min--moz-device-pixel-ratio: 1.5),(-o-min-device-pixel-ratio: 3/2),(min-resolution: 1.5dppx)\").matches},extend(e.celtra,t),delete t.webkit,extend(e,t),{max:e.Math.max,min:e.Math.min,userAgent:e.navigator.userAgent,top:e.top}),n=function(){return void 0!==e.userAgentOverride?e.userAgentOverride:u.userAgent};function i(){var e=n().match(/iP(ad|hone|od).*OS ([0-9_]+)/);return!!e&&a(e[2].replace(/_/g,\".\"),arguments)}function r(){var e=n().match(/Android ([0-9.]+)/);return!n().match(/Windows Phone/)&&!!e&&a(e[1],arguments)}function o(){var e=n().match(/Windows Phone ([0-9.]+)/);return!!e&&a(e[1],arguments)}function a(e,t){if(!e)return!0;if(0===t.length)r=i=null;else if(1===t.length)i=t[0],r=t[0];else{if(2!==t.length)throw\"Invalid number of arguments\";i=t[0],r=t[1]}function n(e){return parseInt(e.replace(/\\D/g,\"\"),10)}e=e.split(\".\").map(n);for(var i=i?i.split(\".\").map(n):[],r=r?r.split(\".\").map(n):[],o=u.max(i.length,r.length)-e.length;0<=o;o--)e.push(0);function a(e,t){for(var n=0;n<u.min(e.length,t.length);n++){if(e[n]<t[n])return-1;if(e[n]>t[n])return 1}return 0}return!(-1===a(e,i)||1===a(e,r))}}(window);;\n!function(n){var e=n.navigator.userAgent,r=function(){var e=n;try{for(;void 0!==e.parent.location.href&&e.parent.document!==e.document;)e=e.parent}catch(e){}return e}(),t=/rv:.*Gecko\\//.test(e),i=/MSIE|Trident\\//.test(e),o=/WebKit/.test(e),a=/^-?([mM]oz|[wW]eb[kK]it|[mM]s)-?/,l={},s=[],f=o?\"webkit\":i?\"ms\":t?\"Moz\":\"\",u=(e=e.match(/AppleWebKit\\/(\\d+)/))&&~~e[1]<540,c=[\"transform\",\"transform-origin\",\"transform-style\",\"transition\",\"transition-delay\",\"transition-duration\",\"transition-property\",\"transition-timing-function\",\"animation\",\"animation-delay\",\"animation-direction\",\"animation-duration\",\"animation-fill-mode\",\"animation-iteration-count\",\"animation-name\",\"animation-play-state\",\"animation-timing-function\",\"appearance\",\"backface-visibility\",\"perspective\",\"perspective-origin\"],m=d();function d(){return r.getComputedStyle(r.document.body,null)}function p(e,t,i){if(!e)throw new Error(\"No element specified!\");if(E(t)){if(!(t in e.style)&&p.BREAK_ON_ERROR)throw new Error(\"Invalid CSS attribute \"+t);if(void 0===i)return e.style[t];e.style[t]=i}else{if(-1==s.indexOf(t)&&y(t),void 0===i)return e.style[l[t]];e.style[l[t]]=i}}function w(e,t){if(!e)throw new Error(\"No element specified!\");var i,e=e.ownerDocument.defaultView.getComputedStyle(e,null);if(u&&-1<c.indexOf(t)&&(t=\"webkit\"+v(t)),!e||\"none\"==e.display||!(n===r||(i=n.frameElement.ownerDocument.defaultView.getComputedStyle(n.frameElement,null))&&\"none\"!=i.display))return null;if(t in e||!p.BREAK_ON_ERROR)return E(t)?e[t]:(-1==s.indexOf(t)&&y(t),e[l[t]]);throw new Error(\"Invalid CSS attribute \"+t)}function y(e){m=m||d();var t=f+v(e);if(u&&-1<c.indexOf(e))l[e]=t;else if(m&&e in m)l[e]=e;else if(m&&t in m)l[e]=t;else if(!m&&deviceInfo.browser.firefox)l[e]=e;else if(p.BREAK_ON_ERROR)throw new Error(\"Invalid CSS attribute \"+e+' or iframe still display \"none\" in FF');s.push(e)}function E(e){return a.test(e)}function v(e){return(e+=\"\")?e[0].toUpperCase()+e.slice(1):\"\"}p.BREAK_ON_ERROR=!1,n.celtra=n.celtra||{},n.celtra.styler={css:p,computedCSS:w,isWebkit:o,isGecko:t,isIE:i},n.css=p,n.computedCSS=w}(window);;\nvar Freezer={};Freezer.unfreeze=function(n,r,e){var l=[],s={},f=0,d=null;function z(n,e,o,t,a){ActionInvocationSimulator.isDryRun()?ActionInvocationSimulator.needsDryRun(n.internalId)&&(ActionInvocationSimulator.logActionDryRun(n.internalId,n.method,e.localId),e[n.method+\"ActionDryRun\"]?e[n.method+\"ActionDryRun\"](o,t,a):a()):e[n.method+\"Action\"](o,t,a)}var o,t=function n(a){if(a instanceof Array)return a.map(n);if(a instanceof Object){var e,o;if(\"ActionInvocation\"===a.clazz){a.internalId=++f;var i=d,c=function(n,e){if(!n)throw\"Cannot execute an action without a context.\";e=e||noop;var o=c.isStatic;if(void 0===o&&(o=!c.instance),c.disabled)e();else if(o){if(c.instance)throw\"ActionInvocation for a static method must not have an instance assigned.\";if(\"function\"!=typeof c.actionClazz[c.method+\"Action\"])throw\"Clazz \"+c.actionClazz+' does not define the static action \"'+c.method+'\".';var t=extend({},c.args,{triggerId:i,caller:a});z(c,c.actionClazz,n,t,e)}else if(c.instance){if(!(c.instance instanceof c.actionClazz))throw\"Instance \"+c.instance+\" is not an instance of clazz \"+c.actionClazz;if(\"function\"!=typeof c.instance[c.method+\"Action\"])throw\"Instance of clazz \"+c.actionClazz+' does not define the instance action \"'+c.method+'\".';t=extend({},c.args,{triggerId:i,caller:a}),z(c,c.instance,n,t,e)}else ActionInvocationSimulator.isDryRun()?e():defer(e,0,\"Freezer.unfreeze defer c\")}}else if(a.clazz){var t=r[a.clazz];if(!t)throw new Error(\"Unsupported clazz: \"+a.clazz);c=Object.create(t.prototype)}else c={},a.onFire&&(d=a.type);for(e in a)a.hasOwnProperty(e)&&\"clazz\"!==e&&(c[e]=n(a[e]));for(e in l.push(c),c.localId&&(s[c.localId]&&console.error(\"Duplicate localId: \"+c.localId),s[c.localId]=c),c)\"clazz\"===e.slice(-5).toLowerCase()&&(o=c[e])&&(r[o]?c[e]=r[o]:console.error(\"Unsupported clazz: \"+c.clazz));return c}return a}(n);for(o in l.forEach(function(n){for(var e in n)if(\"LocalId\"===e.slice(-7)){var o=n[e];o instanceof Function||(null===o?i=null:(i=s[o])||console.error(\"Invalid localId reference: \"+o),n[e.slice(0,-7)]=i,delete n[e])}else if(\"LocalIds\"===e.slice(-8)){for(var t=[],a=0;a<n[e].length;a++){var i,c=n[e][a];(i=s[c])||console.error(\"Invalid localId reference: \"+c),t.push(i)}n[e.slice(0,-8)]=t,delete n[e]}}),e)t[o]=e[o];return l.reverse().forEach(function(n){n.awake&&n.awake()}),t};;\nfunction Batcher(e){e=e||{},this._cache={},this._downloadQueue={},this._base=e.baseUrl,this._protoLoading=e.protoLoading}Batcher.prototype.getDataUri=function(e,t){var a,r,n,c=this;if(\"function\"!=typeof t)throw\"getDataURI callback is not a function\";if(-1!==e.indexOf(\"://\"))a=e;else{if(\"/\"===e[0])throw\"An absolute path as URL?\";a=this._base+e}0===a.indexOf(creative.cachedApiUrl)?(r=a.slice(creative.cachedApiUrl.length),n=!0):0===a.indexOf(creative.insecureCachedApiUrl)&&(r=a.slice(creative.insecureCachedApiUrl.length),n=!1),r?r in this._cache?defer(function(){t(c._cache[r])},0,\"Batcher.getDataUri in cache\",useAsap()):(this._downloadQueue[r]||(this._downloadQueue[r]={requested:!1,secure:!1,callbacks:[]}),this._downloadQueue[r].callbacks.push(t),n&&(this._downloadQueue[r].secure=!0),defer(function(){var e,t=[],a=!1;for(e in c._downloadQueue){var r;7992<c._generateBatchUrl(!0,t.concat(e)).length||((r=c._downloadQueue[e]).requested||(r.requested=!0,t.push(e),r.secure&&(a=!0)))}t.length&&(Logger(\"batcher\").log(t.length+\" new batchable URLs requested during last runloop iteration (\"+(a?\"at least one secure\":\"none secure\")+\"):\\n - \"+t.join(\"\\n - \")),c._loadBatch(a,t,function(e){for(var t in e){var a=e[t];!1===a&&(a=(c._downloadQueue[t].secure?creative.cachedApiUrl:creative.insecureCachedApiUrl)+t),c._cache[t]=a,c._downloadQueue[t].callbacks.forEach(function(e){e(a)}),delete c._downloadQueue[t]}}))},0,\"Batcher.getDataUri downloadQueue\",useAsap())):defer(function(){t(e)},0,\"Batcher.getDataUri url not batchable\",useAsap())},Batcher.prototype._generateBatchUrl=function(e,t){return(e?creative.cachedApiUrl:creative.insecureCachedApiUrl)+\"batch?urls=\"+t.sort().map(encodeURIComponent).join(\",\")},Batcher.prototype._loadBatch=function(e,t,o){var a,i=this._generateBatchUrl(e,t);isFetchSupported()&&this._protoLoading.blobURIsSupported?fetch(i+\"&binary=1\").then(function(e){return e.arrayBuffer()}).then(function(e){for(var t={},a=new DataView(e),r=0;r<e.byteLength;){var n=a.getUint32(r),c=(r+=4,JSON.parse(String.fromCharCode.apply(null,new Uint8Array(e.slice(r,r+n))))),n=(r+=n,e.slice(r,r+c.bodyLength));r+=c.bodyLength,200===c.code?t[c.path]=URL.createObjectURL(new Blob([n],{type:c.contentType})):(Logger(\"batcher\").warn(\"Batcher: Could not retrieve URL '\"+c.path+\"' using batcher, returning it directly.\"),creative.track({name:\"batcherError\",url:i,path:c.path,binary:!0}),t[c.path]=!1)}o(t)}):this._protoLoading.dataURIsSupported?loadJSONP(i,{cbName:\"__batcher_jsonp_\"+Math.abs(function(e){for(var t=5381,a=0;a<e.length;a++)t=(t<<5)+t+(char=e.charCodeAt(a));return t}(i))},function(e){var t,a={};for(t in e){var r=e[t];200===r.code?a[t]=r.data:(Logger(\"batcher\").warn(\"Batcher: Could not retrieve URL '\"+t+\"' using batcher, returning it directly.\"),creative.track({name:\"batcherError\",url:i,path:t,binary:!1}),a[t]=!1)}o(a)}):(a=t.reduce(function(e,t){return e[t]=!1,e},{}),defer(function(){o(a)},0,\"Batcher._loadBatch defer callback, no data: and no blob: support, returning urls directly.\"))};;\nfunction EngagementTracker(e,t){var i,c,u=15,l=150,d=1e3,s=15,p=s+5,v={capture:!0,passive:!0},m=e,f=t,n=t.adapter,o=n.trackingCenter,h=t.units,g=!1,I=null,w=!0,b={},L=0,T=null,j=Date.now(),M=0,y=0,E=e.ownerDocument.defaultView||e.ownerDocument.parentWindow;function a(e){var t,n,o,a,r;g||(t=D(e.target))&&(g=!0,n=randInt(),o=m.getBoundingClientRect(),a=t.node.getBoundingClientRect(),r=m.parentNode.getBoundingClientRect(),i=Math.round(o.left-r.left),c=Math.round(o.top-r.top),f.setUniversalInteractionId(n),\"touchstart\"===e.type?(w=!0,M=e.targetTouches[0].pageX,y=e.targetTouches[0].pageY,m.addEventListener(\"touchmove\",U,v),m.addEventListener(\"touchend\",P,v)):\"mousedown\"===e.type&&(w=!1,M=e.clientX,y=e.clientY,m.addEventListener(\"mousemove\",U,v),m.addEventListener(\"mouseup\",P,v)),deviceInfo.deviceType.desktop()&&m.addEventListener(\"mouseout\",C,v),j=T=Date.now(),L=0,(b=function(e,t){var n=null,o=null,a=null,r=!1;t instanceof Screen?(o=null,a=(n=t).parentUnitVariant,r=!0):\"undefined\"!=typeof ScreenObject&&t instanceof ScreenObject?(n=t.parentScreen,o=t,a=n.parentUnitVariant,r=!0):\"undefined\"!=typeof CreativeUnitVariant&&t instanceof CreativeUnitVariant&&(a=t,r=!(o=n=null));if(r)return extend({unitName:(a||n).parentUnit.name,unitVariantLocalId:a?a.localId:null,screenLocalId:n?n.localId:null,screenTitle:n?n.title:null,screenIsMaster:n?n.isMasterScreen():null,objectLocalId:o?o.localId:null,objectName:o?o.name:null,objectClazz:o?o.constructor.name:null,initiationTimestamp:new Date/1e3},e);return e}(b={name:\"universalInteraction\",universalInteractionId:n,unitSize:{w:m.offsetWidth,h:m.offsetHeight},objectSize:t?{t:Math.round(a.top-o.top),l:Math.round(a.left-o.left),w:Math.round(a.width),h:Math.round(a.height)}:null,path:[],addPoint:function(e,t,n,o){this.path.push({x:Math.round(e-i),y:Math.round(t-c),clientTimestamp:n,objectLocalId:o?o.localId:null})}},t)).addPoint(M,y,j/1e3,t))}function U(e){var t,n,o,a,r;g&&(I&&(E.clearTimeout(I),I=null),\"touchmove\"===e.type?(t=e.targetTouches[0].pageX,n=e.targetTouches[0].pageY):\"mousemove\"===e.type&&(t=e.clientX,n=e.clientY),L+=(e=t-M,o=n-y,Math.sqrt(e*e+o*o)),M=t,y=n,o=(e=Date.now())-j,a=Math.pow(b.path.length/(.5*s),6),r=Math.pow((e-T)/d,6),b.path.length<p&&(u+a*u<L||l+r*l<o)&&(b.addPoint(t,n,e/1e3,D(document.elementFromPoint(t,n))),j=e,L=0))}function C(e){var t=Date.now();I=E.setTimeout(function(){P(0,t)},200)}function P(e,t){g&&(g=!1,t=t||Date.now(),b.addPoint(M,y,t/1e3,D(document.elementFromPoint(M,y))),o.track(b),n.sendUserInteractionToPreview&&n.sendUserInteractionToPreview(),w?(m.removeEventListener(\"touchmove\",U,v),m.removeEventListener(\"touchend\",P,v)):(m.removeEventListener(\"mousemove\",U,v),m.removeEventListener(\"mouseup\",P,v)),deviceInfo.deviceType.desktop()&&m.removeEventListener(\"mouseout\",C,v))}function D(e){if(e)for(var a,r,t=e;;){if(t.id&&0==t.id.indexOf(\"celtra-\")){r=t.id.replace(\"celtra-\",\"\"),0==t.id.indexOf(\"celtra-object-\")&&(a=t.id.replace(\"celtra-object-\",\"\"));var n=function e(t){for(var n in t)if(t.hasOwnProperty(n)){if(t[n].name&&t[n].name==r)return t[n];if(t[n].localId&&t[n].localId==a)return t[n];var o;if(t[n].variants?o=e(t[n].variants):t[n].screens?o=e(t[n].screens):t[n].objects?o=e(t[n].objects):t[n].content&&t[n].content.objects?o=e(t[n].content.objects):t[n].component&&(o=t[n].component),o)return o}}(h);if(n)return\"undefined\"!=typeof CreativeUnit&&n instanceof CreativeUnit?n.currentScreen:n}if(!t.parentNode)break;t=t.parentNode}return null}attach(m,\"touchstart\",a,v),attach(m,\"mousedown\",a,v)};\nfunction Tapper(T,m){var C=\"undefined\"!=typeof creative&&creative.adapter||adapter;function w(t){for(var e=t,a=null;1!=e.nodeType;)e=e.parentNode;for(;e&&1==e.nodeType&&e.tagName;){var n=e.tagName.toLowerCase();if(hasClass(e,\"touchable\")||\"a\"===n&&e.href||\"button\"===n||\"img\"===n||\"input\"===n&&e.type&&\"button\"===e.type.toLowerCase()){a=e;break}e=e.parentNode}return a}function k(t,e){t=t.ownerDocument.createEvent(\"MouseEvents\");t.initEvent(\"tap\",!0,!0),e.dispatchEvent(t)}(m=m||C.useNativeClickForTapDetection)&&(attach(T,\"click\",function(t){t=w(t.target);t&&k(T,t)},!C.nativeClickEmittedOnSwipe),C.useNativeClickForTapDetection)||attach(T,\"touchstart\",function(t){var a,n,c,o,r,i,u,h,s,d,l,v,p,e,f,g=w(t.target);g&&(c=!1,celtra.iframe()&&(n=(a=function(){var t=C.getTopWindow();return[t.scrollX,t.scrollY,t.innerWidth,t.innerHeight]})(),c=!0),o=t.targetTouches[0].clientX-10,r=t.targetTouches[0].clientX+10,i=t.targetTouches[0].clientY-10,u=t.targetTouches[0].clientY+10,addClass(g,\"touched\"),l=d=s=h=!1,v=new Date,p=function(t){var e;h||s||(e=t.targetTouches[0].clientX,t=t.targetTouches[0].clientY,h=c&&n.toString()!=a().toString(),((s=!(o<=e&&e<=r&&i<=t&&t<=u))||h?removeClass:addClass)(g,\"touched\"))},e=function(t){var e;l||(l=!0,detach(T,\"touchmove\",p,!1),detach(T,\"touchend\",arguments.callee,!1),hasClass(g,\"touched\")&&(e=200<new Date-v&&!deviceInfo.deviceType.desktop(),removeClass(g,\"touched\"),s||h||d||e||m||k(T,g)))},f=function(){detach(T,\"touchend\",f,!0),nextFrame(function(){d=!0,e()})},attach(T,\"touchcancel\",function(t){removeClass(g,\"touched\"),e(t)},!1),attach(T,\"touchmove\",p,!1),attach(T,\"touchend\",e,!1),attach(T,\"touchend\",f,!0))},!0)};\nfunction TouchEventSimulator(t){this.el=t,this.doc=t.ownerDocument||t,this.win=this.doc.defaultView,this.touch=null,this._initialised=!1,this._firstEventFired=!1,this.handleFirstEvent=this.handleFirstEvent.bind(this),this.handleMouseDown=this.handleMouseDown.bind(this),this.handleMouseMove=this.handleMouseMove.bind(this),this.handleMouseUp=this.handleMouseUp.bind(this),this.handleMouseOut=this.handleMouseOut.bind(this),this.handlePointerDown=this.handlePointerDown.bind(this),this.handlePointerMove=this.handlePointerMove.bind(this),this.handlePointerUp=this.handlePointerUp.bind(this),this.handlePointerCancel=this.handlePointerCancel.bind(this),this.captureBubbleEvents=this.captureBubbleEvents.bind(this)}TouchEventSimulator.mode=null,TouchEventSimulator.prototype.start=function(){this._initialised?\"function\"==typeof Logger&&Logger(\"TouchEventSimulator\").warn(\"Touch event simulator already initialised!\"):(\"function\"==typeof Logger&&Logger(\"TouchEventSimulator\").log(\"Enabling touch event simulation\"),this.doc.defaultView.msPointerEnabled?this.startPointers():this.startMouse(),this.el.addEventListener(\"touchstart\",this.captureBubbleEvents,!1),this.el.addEventListener(\"touchmove\",this.captureBubbleEvents,!1),this.el.addEventListener(\"touchend\",this.captureBubbleEvents,!1),this.el.addEventListener(\"touchcancel\",this.captureBubbleEvents,!1),this.el.addEventListener(\"tap\",this.captureBubbleEvents,!1))},TouchEventSimulator.prototype.stop=function(){this._initialised?(\"function\"==typeof Logger&&Logger(\"TouchEventSimulator\").log(\"Disabling touch event simulation\"),this.win&&this.win.msPointerEnabled?this.stopPointers():this.stopMouse(),this.el.removeEventListener(\"touchstart\",this.captureBubbleEvents,!1),this.el.removeEventListener(\"touchmove\",this.captureBubbleEvents,!1),this.el.removeEventListener(\"touchend\",this.captureBubbleEvents,!1),this.el.removeEventListener(\"touchcancel\",this.captureBubbleEvents,!1),this.el.removeEventListener(\"tap\",this.captureBubbleEvents,!1)):\"function\"==typeof Logger&&Logger(\"TouchEventSimulator\").warn(\"Touch event simulator not running!\")},TouchEventSimulator.prototype.captureBubbleEvents=function(t){t.stopPropagation(),t.preventDefault()},TouchEventSimulator.prototype.startMouse=function(){this.el.addEventListener(\"mousedown\",this.handleMouseDown,!0),this.el.addEventListener(\"mousemove\",this.handleMouseMove,!0),this.el.addEventListener(\"mouseup\",this.handleMouseUp,!0),this.el.addEventListener(\"mouseout\",this.handleMouseOut,!0),this.el.addEventListener(\"dragstart\",this.handleDragStart,!0),this._initialised=!0},TouchEventSimulator.prototype.stopMouse=function(){this.el.removeEventListener(\"mousedown\",this.handleMouseDown,!0),this.el.removeEventListener(\"mousemove\",this.handleMouseMove,!0),this.el.removeEventListener(\"mouseup\",this.handleMouseUp,!0),this.el.removeEventListener(\"mouseout\",this.handleMouseOut,!0),this.el.removeEventListener(\"dragstart\",this.handleDragStart,!0),this._initialised=!1},TouchEventSimulator.prototype.startPointers=function(){this.el.addEventListener(\"MSPointerDown\",this.handlePointerDown,!0),this.el.addEventListener(\"MSPointerMove\",this.handlePointerMove,!0),this.el.addEventListener(\"MSPointerUp\",this.handlePointerUp,!0),this.el.addEventListener(\"MSPointerCancel\",this.handlePointerCancel,!0),this._initialised=!0},TouchEventSimulator.prototype.stopPointers=function(){this.el.removeEventListener(\"MSPointerDown\",this.handlePointerDown,!0),this.el.removeEventListener(\"MSPointerMove\",this.handlePointerMove,!0),this.el.removeEventListener(\"MSPointerUp\",this.handlePointerUp,!0),this.el.removeEventListener(\"MSPointerCancel\",this.handlePointerCancel,!0),this._initialised=!1},TouchEventSimulator.prototype.init=function(){this.doc.defaultView.navigator.msPointerEnabled?this.start():(this.el.addEventListener(\"touchstart\",this.handleFirstEvent,!0),this.el.addEventListener(\"mousedown\",this.handleFirstEvent,!0))},TouchEventSimulator.prototype.updateTouchCoordinates=function(t){this.touch.screenX=t.screenX,this.touch.screenY=t.screenY,this.touch.pageX=t.pageX,this.touch.pageY=t.pageY,this.touch.clientX=t.clientX,this.touch.clientY=t.clientY},TouchEventSimulator.prototype.ignorables=[\"select\",\"input\",\"textarea\"],TouchEventSimulator.prototype.isFormElement=function(t){return-1<this.ignorables.indexOf(t.nodeName.toLowerCase())},TouchEventSimulator.prototype.isIgnorable=function(t){for(var e=t;e&&e instanceof HTMLElement;e=e.parentNode)if(hasClass(e,\"ignore-toucheventsimulator\"))return!0;return!1},TouchEventSimulator.prototype.handleFirstEvent=function(t){this._firstEventFired||(this._firstEventFired=!0,this.el.removeEventListener(\"touchstart\",this.handleFirstEvent,!0),this.el.removeEventListener(\"mousedown\",this.handleFirstEvent,!0),\"touch\"!=TouchEventSimulator.mode&&\"mousedown\"==t.type?(TouchEventSimulator.mode=\"mouse\",this.start(),this.handleMouseDown(t)):TouchEventSimulator.mode=\"touch\")},TouchEventSimulator.prototype.handleMouseDown=function(t){0!=t.button||this.isIgnorable(t.target)||(this.touch={identifier:0,target:t.target},this.updateTouchCoordinates(t),t.stopPropagation(),this.isFormElement(t.target)||t.preventDefault(),this.fireTouchEvent(\"touchstart\",t))},TouchEventSimulator.prototype.handleMouseMove=function(t){this.touch&&0==t.button&&(this.updateTouchCoordinates(t),t.stopPropagation(),t.preventDefault(),this.fireTouchEvent(\"touchmove\",t))},TouchEventSimulator.prototype.handleMouseUp=function(t){this.touch&&0==t.button&&(this.updateTouchCoordinates(t),t.stopPropagation(),t.preventDefault(),this.fireTouchEvent(\"touchend\",t),this.touch=null)},TouchEventSimulator.prototype.handleMouseOut=function(t){this.touch&&0==t.button&&(t.clientX<=0||t.clientX>=this.win.innerWidth||t.clientY<=0||t.clientY>=this.win.innerHeight)&&this.cancelInteraction()},TouchEventSimulator.prototype.handleDragStart=function(t){return t.preventDefault(),!1},TouchEventSimulator.prototype.handlePointerDown=function(t){0==t.button&&(this.touch={identifier:0,target:t.target},this.updateTouchCoordinates(t),this.fireTouchEvent(\"touchstart\",t))},TouchEventSimulator.prototype.handlePointerMove=function(t){this.touch&&0==t.button&&(this.updateTouchCoordinates(t),this.fireTouchEvent(\"touchmove\",t))},TouchEventSimulator.prototype.handlePointerUp=function(t){this.touch&&0==t.button&&(this.updateTouchCoordinates(t),this.fireTouchEvent(\"touchend\",t))},TouchEventSimulator.prototype.handlePointerCancel=function(t){this.touch&&0==t.button&&(this.updateTouchCoordinates(t),this.fireTouchEvent(\"touchcancel\",t),this.touch=null)},TouchEventSimulator.prototype.fireTouchEvent=function(t,e){var n=this.doc.createEvent(\"HTMLEvents\");n.initEvent(t,!0,!0),\"touchend\"==t||\"touchcancel\"==t?(n.touches=n.targetTouches=[],n.changedTouches=[this.touch]):n.touches=n.targetTouches=n.changedTouches=[this.touch],e.target.dispatchEvent(n)},TouchEventSimulator.prototype.cancelInteraction=function(){this.touch&&(this.fireTouchEvent(\"touchcancel\",this.touch),this.touch=null)};;\nfunction SwipeListener(t){var a,n,c,h,e=t.target,o=t.swipeNodeGetter,i=t.swipe,u=t.minLength||50,r=Object.create(ClickPreventer).initClickPrevention(function(){return e});function s(t,e){return Math.abs(t)>Math.abs(e)?0<t?\"east\":\"west\":0<e?\"south\":\"north\"}function d(){detach(o(),\"touchmove\",l,!1),detach(o(),\"touchend\",g,!1)}function l(t){var e;1<t.touches.length?d():(e=t.changedTouches[0].pageX-a,t=t.changedTouches[0].pageY-n,(h=Math.max(Math.abs(e),Math.abs(t)))>Math.min(50,u)&&(null===c?c=s(e,t):(e=s(e,t),c!=e&&d())))}function g(t){d(),u<=h&&(i(c,h),r.attachClickPreventionHandler())}attach(window,\"touchstart\",noop,!1),attach(e,\"touchstart\",function(t){creative.adapter.useNativeClickForTapDetection||t.preventDefault(),1==t.touches.length&&(a=t.changedTouches[0].pageX,n=t.changedTouches[0].pageY,h=0,c=null,attach(o(),\"touchmove\",l,!1),attach(o(),\"touchend\",g,!1),r.detachClickPreventionHandler())},!1)};\nvar ClickPreventer={initClickPrevention:function(e){return this._getDefaultClickPreventionNode=e||function(){},this._clickPreventionNode=null,this._clickPreventionHandler=this._clickPreventionHandler.bind(this),this},attachClickPreventionHandler:function(e){creative.adapter.nativeClickEmittedOnSwipe&&!this._clickPreventionNode&&(this._clickPreventionNode=e||this._getDefaultClickPreventionNode(),attach(this._clickPreventionNode,\"click\",this._clickPreventionHandler,!0))},detachClickPreventionHandler:function(){creative.adapter.nativeClickEmittedOnSwipe&&this._clickPreventionNode&&(detach(this._clickPreventionNode,\"click\",this._clickPreventionHandler,!0),this._clickPreventionNode=null)},_clickPreventionHandler:function(e){e.stopPropagation(),this.detachClickPreventionHandler()}};;\nfunction PlaceboBar(i,n,r,e){var t=!1,l=document.createElement(\"div\"),a=(l.className=\"celtra-placebobar\",l.style.cssText=\"position: absolute; bottom: 60px; left: 0; right: 0; margin: auto; z-index: 20; width: 215px; height: 8px;\",document.createElement(\"img\")),a=(a.src=creative.resourceUrl+\"runner/clazzes/CreativeUnit/loading-back.png\",a.style.position=\"absolute\",a.style.width=\"100%\",a.style.height=\"100%\",l.appendChild(a),document.createElement(\"div\")),o=(a.style.position=\"absolute\",a.style.top=\"2px\",a.style.bottom=\"2px\",a.style.left=\"3px\",a.style.right=\"3px\",l.appendChild(a),document.createElement(\"div\"));return o.style.background=\"url(\"+creative.resourceUrl+\"runner/clazzes/CreativeUnit/loading-over.png) \"+e+\" repeat-x\",o.style.position=\"absolute\",o.style.width=\"0\",o.style.height=\"100%\",a.appendChild(o),l.start=function(){var a;t||(t=!0,a=new Date,setTimeout(function e(){if(!l.parentNode)return;var t=Math.min((new Date-a)/i,n);o.style.width=100*t+\"%\";t<n&&setTimeout(e,2*r*Math.random())},0))},l};\nvar EventEmitter={emit:function(e){var t,n=this._listeners;n&&n[e]&&(t=[].slice.call(arguments,1),n[e].forEach(function(e){if(\"undefined\"!=typeof window)e.apply(window,t);else{if(\"undefined\"==typeof self)throw\"EventEmitter not supported in current scope.\";e.apply(self,t)}}))},emits:function(e){var t=arguments;return function(){this.emit.apply(this,t)}.bind(this)},addListener:function(e,t){var n=this._listeners;(n=n||(this._listeners={}))[e]||(n[e]=[]),n[e].push(t)},removeListener:function(e,t){var n=this._listeners;n&&n[e]&&(n[e]=n[e].filter(function(e){return e!==t}))},once:function(t,n){var i=this;i.on(t,function e(){i.off(t,e),n.apply(this,arguments)})},onAll:function(e,t){e.split(\" \").waitForEach(function(e,t){this.once(e,t)},t,this)}};EventEmitter.on=EventEmitter.addListener,EventEmitter.off=EventEmitter.removeListener,EventEmitter.addEventListener=EventEmitter.addListener,EventEmitter.removeEventListener=EventEmitter.removeListener,\"undefined\"!=typeof module&&null!==module&&(module.exports=EventEmitter);;\nfunction InViewObject(t,e){this.view=t,this.inViewParent=e,this._init()}extend(InViewObject.prototype,EventEmitter),InViewObject.prototype._init=function(){this.rectInView=CRect.ZERO,this.areaInViewRatio=0,this.active=!1,this._maxPossibleDimensions={width:0,height:0,area:0},this.computeRectInView=this.computeRectInView.bind(this),this.start=this.start.bind(this),this.stop=this.stop.bind(this)},InViewObject.prototype.getNode=function(){return this.view.getNode()},InViewObject.prototype.getParentMaxPossDims=function(){return this.inViewParent?this.inViewParent.getMaxPossibleDimensions():this.adapter.getViewportRect()},InViewObject.prototype.computeRectInView=function(){var t=this.inViewParent.getRectInView(),e=this.getBoundingClientRect(),t=t.intersect(e),i=!1,e=(this.rectInView.equals(t)||(this.rectInView=t,i=!0),this._computeMaxPossibleDimensions(e,this.getParentMaxPossDims()),0<this._maxPossibleDimensions.area?t.area/this._maxPossibleDimensions.area:0),n=!1;e!==this.areaInViewRatio&&(this.areaInViewRatio=e,n=!0),i&&(this.emit(\"rectInViewChanged\",t),Logger(\"InViewObject\").debug(this.view.toString()+\", rectInViewChanged: \"+this.rectInView.toString())),n&&(this.emit(\"areaInViewRatioChanged\",e),Logger(\"InViewObject\").debug(this.view.toString()+\", areaInViewRatioChanged: \"+e))},InViewObject.prototype.getBoundingClientRect=function(){var t=this.getNode();return t?CRect.adopt(t.getBoundingClientRect()):CRect.ZERO},InViewObject.prototype.getMaxPossibleDimensions=function(){return this._maxPossibleDimensions},InViewObject.prototype._computeMaxPossibleDimensions=function(t,e){this._maxPossibleDimensions.width=Math.min(e.width,t.width),this._maxPossibleDimensions.height=Math.min(e.height,t.height),this._maxPossibleDimensions.area=this._maxPossibleDimensions.width*this._maxPossibleDimensions.height},InViewObject.prototype.getRectInView=function(){return this.rectInView},InViewObject.prototype.getAreaInViewRatio=function(){return this.areaInViewRatio},InViewObject.prototype.start=function(){this.active||(this._start(),this.active=!0,this.computeRectInView())},InViewObject.prototype._start=function(){this.inViewParent.on(\"rectInViewChanged\",this.computeRectInView),this.inViewParent.on(\"areaInViewRatioChanged\",this.computeRectInView)},InViewObject.prototype.stop=function(){this.active&&(this.computeRectInView(),this._stop(),this.active=!1)},InViewObject.prototype._stop=function(){this.inViewParent.off(\"rectInViewChanged\",this.computeRectInView),this.inViewParent.off(\"areaInViewRatioChanged\",this.computeRectInView)};;\nfunction TaskScheduler(){this.hub=extend({},EventEmitter)}TaskScheduler.prototype.when=function(){var n=this,e=Array.isArray(arguments[0])?arguments[0]:Array.prototype.slice.call(arguments);return{run:function(t){e.waitForEach(function(t,e){n.hub.once(t,e)},t)}}},TaskScheduler.prototype.notify=function(t){this.paused||this.hub.emit(t)},TaskScheduler.prototype.notifies=function(t){return function(){this.notify(t)}.bind(this)},TaskScheduler.prototype.pause=function(){this._paused=!0};;\nfunction DryRunResultSet(t){this._actions=t}DryRunResultSet.prototype.contains=function(e,o){return this._actions.some(function(t){var n=e==t.method,t=!o||o==t.instance;return n&&t})},DryRunResultSet.prototype.getAll=function(){return this._actions},DryRunResultSet.prototype.concat=function(t){return new DryRunResultSet(this._actions.concat(t.getAll()))};;\nvar ActionInvocationSimulator={_dryRun:!1,_dryRunActions:[],_dryRunExecutedActionsLocalIds:[],beginDryRun:function(){this._dryRun=!0,this._dryRunActions=[],this._dryRunExecutedActionsLocalIds=[]},endDryRun:function(){var n=this._dryRunActions;return this._dryRun=!1,this._dryRunActions=[],this._dryRunExecutedActionsLocalIds=[],new DryRunResultSet(n)},isDryRun:function(){return this._dryRun},needsDryRun:function(n){return!this._dryRunExecutedActionsLocalIds[n]},logActionDryRun:function(n,t,u){this._dryRunExecutedActionsLocalIds[n]=!0,this._dryRunActions.push({method:t,instance:u})}};;\nvar TriggerHost={_getTriggers:function(i,t){return t=t||retTrue,this.triggers.filter(function(r){return r.type==i&&t(r.parameters,r)})},_fireArrayOfTriggers:function(r,i,t){i=i||noop,t=t||new ActionContext(this),r.waitForEach(function(r,i){r.onFire?r.onFire(t,i):i()},i)},hasTriggers:function(r,i){return 0<this._getTriggers(r,i).length},fireTriggersDryRun:function(r,i,t,e){try{ActionInvocationSimulator.beginDryRun(),this.fireTriggers(r,i,t,e)}finally{r=ActionInvocationSimulator.endDryRun()}return r},fireTriggers:function(r,i,t,e){r=this._getTriggers(r,i);this._fireArrayOfTriggers(r,t,e)},fireSortedTriggers:function(r,i,t,e,n){r=this._getTriggers(r,i),n=n||null;this._fireArrayOfTriggers(r.sort(n),t,e)},triggerAction:function(r,i,t){this.fireTriggers(i.eventName,null,t,r)},triggerActionByLocalId:function(r,i,t){var e=this.triggers.filter(function(r){return r.localId===i.localId});0<e.length&&this._fireArrayOfTriggers(e,t,r)},triggerByLocalIdAction:function(r,i,t){i=i.event?[i.event]:[];this._fireArrayOfTriggers(i,t,r)},triggerByLocalIdActionDryRun:function(r,i,t){this.triggerByLocalIdAction(r,i,t)}};;\nvar SceneHost={_initImmediatelyPlayedScenes:function(){var e;this.immediatelyPlayedScenes&&(e=[],this instanceof Screen?(e=this.immediatelyPlayedScenes.appear.scenes,this.appearedAtLeastOnce||(e=e.concat(this.immediatelyPlayedScenes.firstAppear.scenes))):e=this.immediatelyPlayedScenes.conditionMet.scenes,e.forEach(function(e){e.renderFirstFrame()}))}};;\nfunction ActionContext(e,t){if(this._id=randInt(),this.initiator=e,this.initiationTimestamp=new Date/1e3,\"object\"==typeof t?(this.consideredUserInitiatedByBrowser=!!t.consideredUserInitiatedByBrowser,this.certainlyNotCausedByUserBehavior=!!t.certainlyNotCausedByUserBehavior):(this.consideredUserInitiatedByBrowser=!!t,this.certainlyNotCausedByUserBehavior=!1),this.inUserInitiatedIteration=this.consideredUserInitiatedByBrowser,this.consideredUserInitiatedByBrowser&&this.certainlyNotCausedByUserBehavior)throw new Error(\"Unable to create an ActionContext where both consideredUserInitiatedByBrowser and certainlyNotCausedByUserBehavior are true!\");if(e instanceof Screen)this.screen=e,this.screenObject=null,this.unitVariant=this.screen.parentUnitVariant;else if(e instanceof ScreenObject)this.screen=e.parentScreen,this.screenObject=e,this.unitVariant=this.screen.parentUnitVariant;else{if(!(\"undefined\"!=typeof CreativeUnitVariant&&e instanceof CreativeUnitVariant))throw new Error(\"Initiator (\"+e+\") is not a Screen or a ScreenObject. Analytics would be confused.\");this.screen=null,this.screenObject=null,this.unitVariant=e}if(this.inUserInitiatedIteration&&(t=function(){this.inUserInitiatedIteration=!1}.bind(this),defer(t),nextFrame(t)),this.trackUserInteraction=this.trackUserInteraction.bind(this),this._isAttributable=!!this.screen&&this._isScreenReadyForEventAttribution(),this.screen&&!this.screen.parentUnit.name)throw new Error(\"Parent unit missing name.\")}ActionContext.prototype._isScreenReadyForEventAttribution=function(){return this.screen.isMasterScreen()?this.screen.parentContainer.screens.some(function(e){return e.appearedAtLeastOnce&&e.isNormalScreen()}):this.screen.appearedAtLeastOnce},ActionContext.prototype.extendEvent=function(e){return extend({unitName:(this.unitVariant||this.screen).parentUnit.name,unitVariantLocalId:this.unitVariant?this.unitVariant.localId:null,screenLocalId:this.screen?this.screen.localId:null,screenTitle:this.screen?this.screen.title:null,screenIsMaster:this.screen?this.screen.isMasterScreen():null,objectLocalId:this.screenObject?this.screenObject.localId:null,objectName:this.screenObject?this.screenObject.name:null,objectClazz:this.screenObject?this.screenObject.constructor.name:null,initiationTimestamp:this.initiationTimestamp},e)},ActionContext.prototype.initiatedBeforeScreenShown=function(){return this.screen&&!this._isAttributable},ActionContext.prototype.track=function(e,t){this.initiatedBeforeScreenShown()&&console.warn('Event \"'+e.name+'\" appears to be initiated before a screen was ever shown and will be ignored.'),creative.track(this.extendEvent(e),t)},ActionContext.prototype.trackUserInteraction=function(){var e;creative.getUserInteracted()?creative.interactionTrackingTimeout||(this.track({name:\"interaction\"}),creative.interactionTrackingTimeout=setTimeout(function(){creative.interactionTrackingTimeout=null},1e3)):(creative.runtimeParams.userInteracted=1,creative.experiments.get(\"TrackViewableAreaChange\")&&(e=creative.adapter).canMeasureViewportPlacementGeometry&&\"function\"==typeof e.trackViewableAreaRatio&&e.trackViewableAreaRatio(),this.track({name:\"firstInteraction\"})),defer(creative.trackingCenter.batchFlush),creative.userInteracted(this)},ActionContext.prototype.trackClickThrough=function(e){e=e||noop,creative.runtimeParams.clickedThrough?this.track({name:\"clickThrough\"},e):(creative.runtimeParams.clickedThrough=1,this.track({name:\"firstClickThrough\"},e)),defer(creative.trackingCenter.batchFlush),creative.clickedThrough(this)};;\nfunction VideoContext(e,t,i){VideoContext.uberConstructor.call(this,e instanceof ActionContext?e.initiator:e,e&&e.userInitiated),this.file=t,this.url=t.getUrl(),this.label=i,this.source=null}inherit(VideoContext,ActionContext),VideoContext._eventNames=[\"videoDurationUpdate\",\"videoPlayAttempted\",\"videoPresetSelected\",\"videoPlayedSegment\",\"videoStart\",\"videoFirstQuartile\",\"videoMidpoint\",\"videoThirdQuartile\",\"videoComplete\"],VideoContext.prototype.registerSource=function(e){if(!e)throw\"Not a valid source of video events.\";this.source=e,VideoContext._eventNames.forEach(function(t){this.source.on(t,function(e){this.handleEvent(t,e)}.bind(this))}.bind(this))},VideoContext.prototype.handleEvent=function(e,t){this.track(extend(JSON.parse(JSON.stringify(t||{})),{name:e}))},VideoContext.prototype.track=function(e){var t;\"videoPlayAttempted\"==e.name&&(t=this.file.url?\"URL\":\"File\",extend(e,{sourceType:t,source:\"URL\"==t?this.file.url:this.file.blobHash,label:this.label})),VideoContext.uber.track.call(this,e)};;\nfunction ShakeListener(){this.shakeThreshold=1.5,this.canListen=!0,this.slidingWindow=5,this.signals=[],this.handleMotion=this.handleMotion.bind(this),this.topWindow=creative.adapter.getTopWindow()}extend(ShakeListener.prototype,EventEmitter),ShakeListener.prototype.start=function(){this.canListen=!0;var i=window.DeviceMotionEvent&&window.DeviceMotionEvent.requestPermission;creative.deviceMotionListenerAdded||(creative.deviceMotionListenerAdded=!0,i?window.DeviceMotionEvent.requestPermission().then(function(i){\"granted\"==i?this.topWindow.addEventListener(\"devicemotion\",this.handleMotion):this.emit(\"shakeFallback\")}.bind(this)).catch(function(i){console.warn(i.message+\". Triggering fallback behaviour\"),this.emit(\"shakeFallback\")}.bind(this)):this.topWindow.addEventListener(\"devicemotion\",this.handleMotion))},ShakeListener.prototype.stop=function(){this.canListen=!1,creative.deviceMotionListenerAdded&&(creative.deviceMotionListenerAdded=!1,this.topWindow.removeEventListener(\"devicemotion\",this.handleMotion))},ShakeListener.prototype.handleMotion=function(i){var e,t,n,s,h;this.canListen&&(this.signals.push(i.accelerationIncludingGravity),this.signals.length>this.slidingWindow&&this.signals.shift(),i={x:(i=this.signals.reduce(function(i,e){return{x:i.x+e.x,y:i.y+e.y,z:i.z+e.z}})).x/this.signals.length,y:i.y/this.signals.length,z:i.z/this.signals.length},this.prevSignal&&(e=null,h=Math.abs(i.x-this.prevSignal.x),s=Math.abs(i.y-this.prevSignal.y),t=Math.abs(i.z-this.prevSignal.z),90===Math.abs(creative.adapter.orientation)&&(n=s,s=h,h=n),s<h&&t<h&&h>this.shakeThreshold?e=\"x\":h<s&&t<s&&s>this.shakeThreshold?e=\"y\":h<t&&s<t&&t>this.shakeThreshold&&(e=\"z\"),(h>this.shakeThreshold||s>this.shakeThreshold||t>this.shakeThreshold)&&this.emit(\"shake\",e)),this.prevSignal=i)};;\n!function(){\"use strict\";t=window.navigator.userAgent,e=window.navigator.vendor,i=!!document.fonts;var t,e,i,r=/Apple/.test(e)?(e=/AppleWebKit\\/([0-9]+)(?:\\.([0-9]+))(?:\\.([0-9]+))/.exec(t))?i&&603<parseInt(e[1],10):i:(e=/Gecko.*Firefox\\/(\\d+)/.exec(t))?i&&46<parseInt(e[1],10):i,o=[\"Droid Sans\"];function l(t){var e=t.family.replace(/ /g,\"+\"),i=(i=t.style,\"italic\"===String(i).toLowerCase()?\"italic\":\"\");t=t.weight;t=(void 0===(t=/normal|bold|([1-9])00/i.exec(t))?\"\":\"normal\"===(t=t[0])?\"400\":\"bold\"===t?\"700\":t)+i;return 0<t.length?e+\":\"+t:e}function h(t,e,i){var o,n;r?(o=t.style+\" \"+t.weight+' 300px \"'+t.getBrowserCompatibleFontFamily()+'\"',n=t.hasUnicodeRangeDefined()&&t.hasSubsetStringDefined()?t.getSubsetString():\" \",document.fonts.load(o,n).then(function(t){(1<=t.length?e:i)()},function(t){i()})):\"undefined\"==typeof FontLoader?i(\"Font Loader is not initialized.\"):FontLoader.watch(t.getBrowserCompatibleFontFamily(),t.style,t.weight,e,i,t.getSubsetString())}function f(t,e,i,o){var n,s=[].slice.apply(document.getElementsByTagName(\"link\")).filter(function(t){return t.href===e})[0];s||((s=document.createElement(\"link\")).setAttribute(\"rel\",\"stylesheet\"),s.setAttribute(\"media\",\"all\"),s.setAttribute(\"href\",e)),r?(n=function(){s._finished=!0,h(t,i,o)},s._finished?h(t,i,o):(s.addEventListener(\"load\",n),s.addEventListener(\"error\",n))):setTimeout(function(){h(t,i,o)},0),s.parentNode||document.getElementsByTagName(\"head\")[0].appendChild(s)}function n(t){for(var e in t)this[e]=t[e]}n.prototype.getId=function(){return this.typefaceId||this.id},n.prototype.getSubsetString=function(){return this.subset},n.prototype.getTrackingUrl=function(){return this.trackingUrl},n.prototype.getUnicodeRange=function(){return this.unicodes},n.prototype.getBrowserCompatibleFontFamily=function(){if(this.isAdobe()||this.useGoogleFontsAPI()||this.isSystem())return this.family;var t=this.family+(this.stretch?\" \"+this.stretch:\"\")+\" \"+this.provider;if(this.hasSubsetStringDefined()&&!this.hasUnicodeRangeDefined()&&(t+=\" \"+encodeURIComponent(this.getSubsetString())),25<t.length){for(var e=5381,i=0,o=t.length;i<o;i++)e=(e<<5)+e+t.charCodeAt(i);return e.toString()}return t},n.prototype.setBaseUrl=function(t){this.baseUrl=t},n.prototype.isAdobe=function(){return\"adobe\"===this.provider},n.prototype.isCustom=function(){return\"custom\"===this.provider},n.prototype.isGoogle=function(){return\"google\"===this.provider},n.prototype.isMonotype=function(){return\"monotype\"===this.provider},n.prototype.isSystem=function(){return\"system\"===this.provider},n.prototype.hasSubsetStringDefined=function(){var t=this.getSubsetString();return\"string\"==typeof t&&0<t.length},n.prototype.hasTrackingUrlDefined=function(){var t=this.getTrackingUrl();return\"string\"==typeof t&&0<t.length},n.prototype.hasUnicodeRangeDefined=function(){var t=this.getUnicodeRange();return\"string\"==typeof t&&0<t.length},n.prototype.needsLoading=function(){return this.isAdobe()||this.isCustom()||this.isGoogle()||this.isMonotype()},n.prototype.useGoogleFontsAPI=function(){return\"google\"===this.provider&&-1!==o.indexOf(this.family)},n.prototype.getCssProperties=function(){return{\"font-family\":this.needsLoading()?'\"'+this.getBrowserCompatibleFontFamily()+'\", Helvetica, sans-serif':this.getBrowserCompatibleFontFamily(),\"font-style\":this.style,\"font-weight\":this.weight}},n.prototype.getFileUrl=function(t){if(\"string\"==typeof this.baseUrl){var e=this.hasSubsetStringDefined();if(this.isGoogle())var i=l(this),i=this.baseUrl+\"google/\"+i+\"/3_webfont.\"+t;else if(void 0!==this.files&&void 0!==this.files[t]){var o=this.getId();if(\"string\"!=typeof o)return this.baseUrl+this.files[t]+\".\"+t;var n=this.files[t].blobHash,s=this.files[t].name||\"webfont\",n=e?\"3_\"+n:n;i=this.baseUrl+o+\"/\"+n+\"/\"+encodeURIComponent(s)+\".\"+t}return\"string\"==typeof i&&e&&(i+=\"?subset=\"+encodeURIComponent(this.getSubsetString())),i}},n.prototype.generateFontFace=function(){var t=\"\",e=[],i=this.getFileUrl(\"woff2\"),i=(i&&e.push('url(\"'+i+'\") format(\"woff2\")'),this.getFileUrl(\"woff\")),i=(i&&e.push('url(\"'+i+'\") format(\"woff\")'),this.getFileUrl(\"ttf\"));return i&&e.push('url(\"'+i+'\") format(\"truetype\")'),this.style&&this.weight&&0<e.length&&(t=\"@font-face {\",t=(t=(t=(t+='font-family: \"'+this.getBrowserCompatibleFontFamily()+'\";')+\"font-style: \"+this.style+\";\")+\"font-weight: \"+this.weight+\";\")+\"src:\"+e.join(\",\")+\";\",this.hasUnicodeRangeDefined()&&(t+=\"unicode-range: \"+this.getUnicodeRange()+\";\"),t+=\"}\"),t},n.prototype.loadStyle=function(e){e=e||function(){};function t(t){e(t||\"Failed to load font files.\")}var i,o,n,s,r,a;this.isCustom()||this.isGoogle()&&!this.useGoogleFontsAPI()||this.isMonotype()?(o=this,n=e,s=t,r=\"font-style-manager\",(a=document.getElementById(r))||((a=document.createElement(\"style\")).id=r,document.getElementsByTagName(\"head\")[0].appendChild(a)),(r=o.generateFontFace())?([].slice.apply(a.sheet.cssRules).some(function(t){return t.style.getPropertyValue(\"font-family\").replace(/^['\"](.+)['\"]$/,\"$1\")==o.getBrowserCompatibleFontFamily()&&t.style.getPropertyValue(\"font-style\")==o.style&&t.style.getPropertyValue(\"font-weight\")==o.weight})||a.sheet.insertRule(r,a.sheet.cssRules.length),h(o,n,s)):s(\"Unable to define style for font without specifying files, style and weight.\")):this.isAdobe()?\"string\"==typeof this.projectId?f(this,i=\"https://use.typekit.net/\"+this.projectId+\".css\",e,t):t(\"Missing Adobe web project id.\"):this.useGoogleFontsAPI()?(i=\"https://fonts.googleapis.com/css?family=\"+l(this),this.hasSubsetStringDefined()&&(i+=\"&text=\"+encodeURIComponent(this.getSubsetString())),f(this,i,e,t)):this.isSystem()?e():t(\"Invalid or unsupported provider.\")},Object.defineProperty(n,\"useCSSFontLoadingAPI\",{value:r}),window.FontBase=n}();;\nvar ScreenNodeTransition={replace:function(t,e,r,s,i,a){r.style.opacity=0,r.style.display=\"block\",s.style.display=\"block\",e.style.display=\"none\",nextFrame(function(){r.style.opacity=1,t.style.opacity=0,nextFrame(function(){t.style.display=\"none\",nextFrame(a||noop)})})},fade:function(t,e,r,s,i,a){r.style.opacity=0,r.style.display=\"block\",s.style.display=\"block\",e.style.display=\"none\",once(t,\"transitionend\",function(){celtra.styler.css(r,\"transition\",\"\"),celtra.styler.css(t,\"transition\",\"\"),t.style.display=\"none\",nextFrame(a||noop)}),nextFrame(function(){celtra.styler.css(t,\"transition\",\"opacity \"+i.duration+\"ms ease-out\"),celtra.styler.css(r,\"transition\",\"opacity \"+i.duration+\"ms ease-out\"),nextFrame(function(){r.style.opacity=1,t.style.opacity=0})})},slide:function(t,e,r,s,i,a){var n={transition:\"all \"+i.duration+\"ms ease-out\"};switch(i.direction||\"west\"){case\"north\":n.startingFrom=\"translateY(0)\",n.endingFrom=\"translateY(\"+-i.unitSize.height+\"px)\",n.startingTo=\"translateY(\"+i.unitSize.height+\"px)\",n.endingTo=\"translateY(0)\";break;case\"south\":n.startingFrom=\"translateY(0)\",n.endingFrom=\"translateY(\"+i.unitSize.height+\"px)\",n.startingTo=\"translateY(\"+-i.unitSize.height+\"px)\",n.endingTo=\"translateY(0)\";break;case\"east\":n.startingFrom=\"translateX(0)\",n.endingFrom=\"translateX(\"+i.unitSize.width+\"px)\",n.startingTo=\"translateX(\"+-i.unitSize.width+\"px)\",n.endingTo=\"translateX(0)\";break;case\"west\":n.startingFrom=\"translateX(0)\",n.endingFrom=\"translateX(\"+-i.unitSize.width+\"px)\",n.startingTo=\"translateX(\"+i.unitSize.width+\"px)\",n.endingTo=\"translateX(0)\"}n.startingFromOpacity=1,n.startingToOpacity=0,n.endingFromOpacity=0,n.endingToOpacity=1,this._runTransition(t,e,r,s,n,i.duration,a)},flip:function(t,e,r,s,i,a){var n={transition:\"all \"+i.duration+\"ms ease-out\"},o=\"perspective(\"+2*i.unitSize.width+\"px) \";switch(i.direction||\"west\"){case\"north\":n.startingFrom=o+\"rotateX(0)\",n.endingFrom=o+\"rotateX(180deg)\",n.startingTo=o+\"rotateX(-180deg)\",n.endingTo=o+\"rotateX(0)\";break;case\"south\":n.startingFrom=o+\"rotateX(0)\",n.endingFrom=o+\"rotateX(-180deg)\",n.startingTo=o+\"rotateX(180deg)\",n.endingTo=o+\"rotateX(0)\";break;case\"east\":n.startingFrom=o+\"rotateY(0)\",n.endingFrom=o+\"rotateY(180deg)\",n.startingTo=o+\"rotateY(-180deg)\",n.endingTo=o+\"rotateY(0)\";break;case\"west\":n.startingFrom=o+\"rotateY(0)\",n.endingFrom=o+\"rotateY(-180deg)\",n.startingTo=o+\"rotateY(180deg)\",n.endingTo=o+\"rotateY(0)\"}celtra.styler.css(t.parentNode,\"transformStyle\",\"preserve-3d\"),celtra.styler.css(t,\"backfaceVisibility\",\"hidden\"),celtra.styler.css(e,\"backfaceVisibility\",\"hidden\"),celtra.styler.css(r,\"backfaceVisibility\",\"hidden\"),celtra.styler.css(s,\"backfaceVisibility\",\"hidden\"),celtra.styler.css(t,\"transformOrigin\",\"50% 50% 0\"),celtra.styler.css(e,\"transformOrigin\",\"50% 50% 0\"),celtra.styler.css(r,\"transformOrigin\",\"50% 50% 0\"),celtra.styler.css(s,\"transformOrigin\",\"50% 50% 0\"),this._runTransition(t,e,r,s,n,i.duration,function(){celtra.styler.css(t.parentNode,\"transformStyle\",\"\"),celtra.styler.css(t,\"backfaceVisibility\",\"\"),celtra.styler.css(e,\"backfaceVisibility\",\"\"),celtra.styler.css(r,\"backfaceVisibility\",\"\"),celtra.styler.css(s,\"backfaceVisibility\",\"\"),celtra.styler.css(t,\"transform\",\"\"),celtra.styler.css(e,\"transform\",\"\"),celtra.styler.css(r,\"transform\",\"\"),celtra.styler.css(s,\"transform\",\"\"),celtra.styler.css(t,\"transformOrigin\",\"\"),celtra.styler.css(e,\"transformOrigin\",\"\"),celtra.styler.css(r,\"transformOrigin\",\"\"),celtra.styler.css(s,\"transformOrigin\",\"\"),nextFrame(a||noop)})},cube:function(t,e,r,s,i,a){var n={transition:\"all \"+i.duration+\"ms ease-out\"},o=\"perspective(\"+2*i.unitSize.width+\"px) \";switch(i.direction||\"west\"){case\"north\":n.startingFrom=o+\"translateZ(\"+-i.unitSize.height/2+\"px) rotateX(0)     \",n.endingFrom=o+\"translateZ(\"+-i.unitSize.height/2+\"px) rotateX(90deg) \",n.startingTo=o+\"translateZ(\"+-i.unitSize.height/2+\"px) rotateX(-90deg)\",n.endingTo=o+\"translateZ(\"+-i.unitSize.height/2+\"px) rotateX(0)     \";break;case\"south\":n.startingFrom=o+\"translateZ(\"+-i.unitSize.height/2+\"px) rotateX(0)     \",n.endingFrom=o+\"translateZ(\"+-i.unitSize.height/2+\"px) rotateX(-90deg)\",n.startingTo=o+\"translateZ(\"+-i.unitSize.height/2+\"px) rotateX(90deg) \",n.endingTo=o+\"translateZ(\"+-i.unitSize.height/2+\"px) rotateX(0)     \";break;case\"east\":n.startingFrom=o+\"translateZ(\"+-i.unitSize.width/2+\"px) rotateY(0)     \",n.endingFrom=o+\"translateZ(\"+-i.unitSize.width/2+\"px) rotateY(90deg) \",n.startingTo=o+\"translateZ(\"+-i.unitSize.width/2+\"px) rotateY(-90deg)\",n.endingTo=o+\"translateZ(\"+-i.unitSize.width/2+\"px) rotateY(0)     \";break;case\"west\":n.startingFrom=o+\"translateZ(\"+-i.unitSize.width/2+\"px) rotateY(0)     \",n.endingFrom=o+\"translateZ(\"+-i.unitSize.width/2+\"px) rotateY(-90deg)\",n.startingTo=o+\"translateZ(\"+-i.unitSize.width/2+\"px) rotateY(90deg) \",n.endingTo=o+\"translateZ(\"+-i.unitSize.width/2+\"px) rotateY(0)     \"}deviceInfo.browser.chrome()&&(n.startingFrom+=\" rotateZ(0.01deg)\",n.startingTo+=\" rotateZ(0.01deg)\");var l=t.style.overflow,c=e.style.overflow,y=r.style.overflow,d=s.style.overflow;switch(celtra.styler.css(t.parentNode,\"transformStyle\",\"preserve-3d\"),t.style.overflow=e.style.overflow=\"hidden\",r.style.overflow=s.style.overflow=\"hidden\",celtra.styler.css(t,\"backfaceVisibility\",\"hidden\"),celtra.styler.css(e,\"backfaceVisibility\",\"hidden\"),celtra.styler.css(r,\"backfaceVisibility\",\"hidden\"),celtra.styler.css(s,\"backfaceVisibility\",\"hidden\"),i.direction||\"west\"){case\"north\":case\"south\":celtra.styler.css(t,\"transformOrigin\",\"50% 50% \"+-i.unitSize.height/2+\"px\"),celtra.styler.css(e,\"transformOrigin\",\"50% 50% \"+-i.unitSize.height/2+\"px\"),celtra.styler.css(r,\"transformOrigin\",\"50% 50% \"+-i.unitSize.height/2+\"px\"),celtra.styler.css(s,\"transformOrigin\",\"50% 50% \"+-i.unitSize.height/2+\"px\");break;case\"east\":case\"west\":celtra.styler.css(t,\"transformOrigin\",\"50% 50% \"+-i.unitSize.width/2+\"px\"),celtra.styler.css(e,\"transformOrigin\",\"50% 50% \"+-i.unitSize.width/2+\"px\"),celtra.styler.css(r,\"transformOrigin\",\"50% 50% \"+-i.unitSize.width/2+\"px\"),celtra.styler.css(s,\"transformOrigin\",\"50% 50% \"+-i.unitSize.width/2+\"px\")}this._runTransition(t,e,r,s,n,i.duration,function(){celtra.styler.css(t.parentNode,\"transformStyle\",\"\"),t.style.overflow=l,e.style.overflow=c,r.style.overflow=y,s.style.overflow=d,celtra.styler.css(t,\"transformOrigin\",\"\"),celtra.styler.css(e,\"transformOrigin\",\"\"),celtra.styler.css(r,\"transformOrigin\",\"\"),celtra.styler.css(s,\"transformOrigin\",\"\"),celtra.styler.css(t,\"backfaceVisibility\",\"\"),celtra.styler.css(e,\"backfaceVisibility\",\"\"),celtra.styler.css(r,\"backfaceVisibility\",\"\"),celtra.styler.css(s,\"backfaceVisibility\",\"\"),nextFrame(a||noop)})},_runTransition:function(t,e,r,s,i,a,n){celtra.styler.css(t,\"transform\",i.startingFrom),celtra.styler.css(e,\"transform\",i.startingFrom),celtra.styler.css(r,\"transform\",i.startingTo),celtra.styler.css(s,\"transform\",i.startingTo),t.style.opacity=e.style.opacity=void 0===i.startingFromOpacity?1:i.startingFromOpacity,r.style.opacity=s.style.opacity=void 0===i.startingToOpacity?1:i.startingToOpacity;var o=!(r.style.display=s.style.display=\"block\"),l=this._runOnce(function(){o||(o=!0,celtra.styler.css(r,\"transition\",\"\"),celtra.styler.css(s,\"transition\",\"\"),celtra.styler.css(t,\"transition\",\"\"),celtra.styler.css(e,\"transition\",\"\"),celtra.styler.css(r,\"transform\",\"\"),celtra.styler.css(s,\"transform\",\"\"),celtra.styler.css(t,\"transform\",\"\"),celtra.styler.css(e,\"transform\",\"\"),t.style.display=e.style.display=\"none\",t.style.opacity=e.style.opacity=r.style.opacity=s.style.opacity=\"\",nextFrame(n||noop))});once(r,\"transitionend\",l),setTimeout(function(){celtra.styler.css(r,\"transition\",i.transition),celtra.styler.css(t,\"transition\",i.transition),celtra.styler.css(s,\"transition\",i.transition),celtra.styler.css(e,\"transition\",i.transition),nextFrame(function(){o||(celtra.styler.css(t,\"transform\",i.endingFrom),celtra.styler.css(e,\"transform\",i.endingFrom),celtra.styler.css(r,\"transform\",i.endingTo),celtra.styler.css(s,\"transform\",i.endingTo),t.style.opacity=e.style.opacity=void 0===i.endingFromOpacity?1:i.endingFromOpacity,r.style.opacity=s.style.opacity=void 0===i.endingToOpacity?1:i.endingToOpacity)}),setTimeout(l,a+150)},20)},run:function(t,e,r,s,i,a){var n=this._getSupportedAnimation(i.animation||\"replace\");if(!(n in this))throw new Error(\"The transition function does not exist!\");this[n].apply(this,arguments)},_getSupportedAnimation:function(t){return!android(\"4\")||window.navigator.userAgent.match(/Chrome.*(Mobile)? Safari/)||\"cube\"!==t&&\"flip\"!==t?t:\"fade\"},_runOnce:function(t){var e=!1;return function(){if(!e)return e=!0,t()}}};;\nvar ScreenTransitioner={goingToScreen:null,_masterShown:!1,goToScreen:function(e,n,t){var i=Array.prototype.slice.apply(arguments);if(this.isAppearing){var r=function(){this._preloadReachableScreens(),t&&t()}.bind(this);if(!e)return r();if(e.loaded){var s=this.currentScreen;if(n=n||{},this.goingToScreen)return r();(this.goingToScreen=e).triggerBeforeAppear(function(){this.initiateScreenTransition(e,n,function(){this.goingToScreen=null,this.currentScreen=e,s&&(s.disappear(),s.exitRenderTree()),this._masterShown||(this._masterShown=!0,this.master.enterRenderTree(),this.master.appear()),this.currentScreen.enterRenderTree(),this.currentScreen.appear(),r()}.bind(this))}.bind(this))}else e.getNode(function(){this.goToScreen.apply(this,i)}.bind(this))}else this.once(\"appeared\",function(){this.goToScreen.apply(this,i)}.bind(this))},initiateScreenTransition:function(e,n,t){if(n=n||{},t=t||noop,this.visibleScreen==e)return t();var i,r,s,o,a=(this.visibleScreen=e)===this.loadingScreen;a&&e.loaded?this._screenTransition(e.getNode(),null,n,t):e.loaded&&this.master.loaded?(this.loadingScreen&&(this.loadingScreen.getNode().style.display=\"none\"),this._screenTransition(e.getNode(),this.master.getNode(),n,t)):(Logger(\"unit\").warn(\"Screens should be loaded but were not\"),s=function(){--o||this._screenTransition(i,r,n,t)},o=a?(i=this.loadingScreen.getNode(s.bind(this)),r=null,1):(i=e.getNode(s.bind(this)),r=this.master.getNode(s.bind(this)),2))},_screenTransition:function(e,n,t,i){function r(){s||(s=!0,i())}var s;e.style.display=\"none\",n&&(this._masterClone||this._createMasterClone(),this.screenHolder.appendChild(this._masterClone),n.parentNode||this.screenHolder.appendChild(n),this._masterClone.style.zIndex=n.style.zIndex=-1),e&&!e.parentNode&&this.screenHolder.appendChild(e),n&&(this._masterClone.style.display=\"block\",n.style.display=\"none\"),null!=this.currentScreen?(t.unitSize=extend({},this.size),ScreenNodeTransition.run(this.currentScreen.node,this._masterClone,e,n,t,function(){n.style.display=\"block\",this._masterClone.display=\"none\",i()}.bind(this))):(n&&(n.style.display=\"block\",this._masterClone.style.display=\"none\"),s=!(e.style.display=\"block\"),nextFrame(r),defer(r))},_preloadReachableScreens:function(){setTimeout(function(){var i=[];Object.keys(this.currentScreen.dynamicReachableScreenConditions).forEach(function(n){var e=n.split(\"/\"),t=creative.experiments.get(e[0],e.slice(1));null!=t&&Object.keys(this.currentScreen.dynamicReachableScreenConditions[n]).forEach(function(e){e!=t.chosenVariant&&(i=i.concat(this.currentScreen.dynamicReachableScreenConditions[n][e]))}.bind(this))}.bind(this)),this.currentScreen.reachableScreens.concat(this.master.reachableScreens).forEach(function(e){-1==i.indexOf(e.localId.toString())&&e.getNode()}.bind(this))}.bind(this),0)},_createMasterClone:function(){return this._masterClone&&this._masterClone.parentNode&&this._masterClone.parentNode.removeChild(this._masterClone),this._masterClone=this.master.getNode().cloneNode(!0),this._masterClone.style.display=\"none\",this._masterClone.style.zIndex=-1,this._masterClone}};;\nfunction AdViewableTimeObserver(e){this._tracker=e,this._readyToShow=!1,this._adapter}function AdViewableInViewObjectObserver(e){AdViewableInViewObjectObserver.uberConstructor.apply(this,arguments),this._inViewObjects=[],this._viewableInViewObjects=[]}function AdViewableUnitObserver(e){AdViewableUnitObserver.uberConstructor.apply(this,arguments),this._units=[],this._viewableUnits=[],this._containerIsViewable=!1}AdViewableTimeObserver.prototype.readyToShow=function(e){throw new Error(\"AdViewableInViewObjectObserver.readyToShow not implemented!\")},AdViewableTimeObserver.prototype.registerUnit=function(e){throw new Error(\"AdViewableInViewObjectObserver.registerUnit not implemented!\")},AdViewableTimeObserver.prototype.registerAdapter=function(e){throw new Error(\"AdViewableTimeObserver.registerAdapter not implemented!\")},AdViewableTimeObserver.prototype.stop=function(){this._tracker.stop()},inherit(AdViewableInViewObjectObserver,AdViewableTimeObserver),AdViewableInViewObjectObserver.prototype.registerAdapter=function(e){this._adapter=e;var t=this._tracker;e.mediaState.on(\"videoStarted\",function(){this._tryStartTracker()}.bind(this)),e.mediaState.on(\"videoStopped\",function(){0===this._viewableInViewObjects.length&&t.stop()}.bind(this))},AdViewableInViewObjectObserver.prototype.readyToShow=function(){this._readyToShow=!0,this._tryStartTracker()},AdViewableInViewObjectObserver.prototype.registerUnit=function(e){var r=e.inView,e=this._inViewObjects,a=this._viewableInViewObjects,n=this._tracker;-1===e.indexOf(r)&&(e.push(r),!(-1!==a.indexOf(r))&&0<r.getAreaInViewRatio()&&a.push(r),this._tryStartTracker(),r.on(\"areaInViewRatioChanged\",function(e){var t=a.indexOf(r),i=-1!==t;!i&&0<e?(a.push(r),this._tryStartTracker()):0===e&&i&&(a.splice(t,1),0===a.length&&n.stop())}.bind(this)))},AdViewableInViewObjectObserver.prototype._tryStartTracker=function(){var e=this._adapter,e=e&&e.mediaState.playingVideo;return!(!this._readyToShow||!(0<this._viewableInViewObjects.length||e))&&(this._tracker.start(),!0)},inherit(AdViewableUnitObserver,AdViewableTimeObserver),AdViewableUnitObserver.prototype.registerAdapter=function(e){this._adapter=e,this._containerIsViewable=e.containerIsViewable;var t=this._tracker;e.on(\"containerViewableChange\",function(e){(this._containerIsViewable=e)?this._tryStartTracker():t.stop()}.bind(this)),e.mediaState.on(\"videoStarted\",function(){this._tryStartTracker()}.bind(this)),e.mediaState.on(\"videoStopped\",function(){this._adapter.containerIsViewable&&0!==this._viewableUnits.length||t.stop()}.bind(this))},AdViewableUnitObserver.prototype.readyToShow=function(){this._readyToShow=!0,this._tryStartTracker()},AdViewableUnitObserver.prototype.registerUnit=function(t){if(!this._adapter)throw new Error(\"adapter was not registered yet!\");var e=this._units,i=this._viewableUnits,r=this._tracker;-1===e.indexOf(t)&&(e.push(t),-1===i.indexOf(t)&&t._visible&&(i.push(t),this._tryStartTracker()),t.on(\"appeared\",function(){-1===i.indexOf(t)&&(i.push(t),this._tryStartTracker())}.bind(this)),t.on(\"disappeared\",function(){var e=i.indexOf(t);-1!==e&&(i.splice(e,1),0===i.length&&r.stop())}.bind(this)))},AdViewableUnitObserver.prototype._tryStartTracker=function(){var e=this._adapter;return!!(e&&(e.containerIsViewable||e.mediaState.playingVideo)&&this._readyToShow&&0<this._viewableUnits.length)&&(this._tracker.start(),!0)};;\nfunction AdViewableTimeTracker(i,e,t,n){this._trackingCenter=i,this._raf=e,this._win=t,this._intervals=n||{3e3:1e3,1e4:2e3,63e3:3e3},this._isRunning=!1,this._viewableMilliseconds=0,this._fromTime=0,this._pendingPingTimeoutId,this._pendingPingRafId,this._maxRafLatency=500}AdViewableTimeTracker.eventName=\"viewableTime\",AdViewableTimeTracker.TrackingData=function(i,e){return{name:AdViewableTimeTracker.eventName,from:i/1e3,to:(e=e<i?i:e)/1e3}},AdViewableTimeTracker.prototype.start=function(){this._isRunning||(this._isRunning=!0,this._fromTime=Date.now(),0===this._viewableMilliseconds?this._initPing():this._setNextPing())},AdViewableTimeTracker.prototype.stop=function(){var i,e;this._clearPendingPing(),this._isRunning&&-1!==this._getPingInterval()&&(e=(i=Date.now())-this._fromTime,this._viewableMilliseconds+=e,e=new AdViewableTimeTracker.TrackingData(this._fromTime,i),this._track(e)),this._isRunning=!1},AdViewableTimeTracker.prototype._initPing=function(){var i=this._fromTime,e=this._getPingInterval(),t=new AdViewableTimeTracker.TrackingData(this._fromTime,i);this._pendingPingRafId=this._raf.nextFrame(function(){this._track(t),this._setFromTimeForNextPing(i,e),this._setNextPing()}.bind(this))},AdViewableTimeTracker.prototype._setNextPing=function(){this._clearPendingPing();var t=this._getPingInterval();this._isRunning&&-1!==t&&(this._pendingPingTimeoutId=this._win.setTimeout(function(){var e;this._isRunning&&(e=Date.now(),this._viewableMilliseconds+=t,this._normalizeFromTime(e,t),this._pendingPingRafId=this._raf.nextFrame(function(){var i=new AdViewableTimeTracker.TrackingData(this._fromTime,e);this._track(i),this._setFromTimeForNextPing(e,t),this._setNextPing()}.bind(this)))}.bind(this),t))},AdViewableTimeTracker.prototype._normalizeFromTime=function(i,e){var t=i-this._fromTime;e+this._maxRafLatency<t&&(this._fromTime=i)},AdViewableTimeTracker.prototype._setFromTimeForNextPing=function(i,e){var t=Date.now();this._fromTime=e<t-i?t:i},AdViewableTimeTracker.prototype._getPingInterval=function(){var e=this._viewableMilliseconds,t=this._intervals,i=Object.keys(this._intervals),n=+i[i.length-1],r=-1;return i.some(function(i){return e<+i&&(r=t[i],n<e+r&&(r=-1),!0)}),r},AdViewableTimeTracker.prototype._clearPendingPing=function(){this._raf.cancelFrame(this._pendingPingRafId),this._win.clearTimeout(this._pendingPingTimeoutId)},AdViewableTimeTracker.prototype._track=function(i){this._trackingCenter.track(i)};;\n!function(t){var i=e.prototype;function e(){this.playingVideo=!1}extend(i,EventEmitter),i.stopVideo=function(){this.playingVideo=!1,this.emit(\"videoStopped\")},i.startVideo=function(){this.playingVideo=!0,this.emit(\"videoStarted\")},t.MediaState=e}(window);;\nfunction StateObject(e){Object.defineProperties(this,{values:{enumerable:!1,configurable:!1,writable:!0,value:{}},_isDirty:{enumerable:!1,configurable:!1,writable:!0,value:!1}}),Object.keys(e).forEach(function(t){this.registerValue(t,e[t])},this)}extend(StateObject.prototype,EventEmitter),StateObject.prototype.registerValue=function(r,t){this.values[r]={dirty:!1,value:t},Object.defineProperty(this,r,{get:function(){return this.values[r].value},set:function(t){var e=this.values[r],i=e.value;t!=i&&(e.dirty=!0,e.value=t,this._isDirty=!0,this.emit(\"change:\"+r,t,i))},enumerable:!0})},StateObject.prototype.markClean=function(e){var i=!1;Object.keys(this.values).forEach(function(t){e&&t!==e||(this[t].dirty=!1),i=i||this[t].dirty},this.values),this._isDirty=i},StateObject.prototype.getDirtyValues=function(){for(var t={},e=Object.keys(this.values),i=0;i<e.length;i++)this.values[e[i]].dirty&&(t[e[i]]=this.values[e[i]].value);return t},StateObject.prototype.isDirty=function(t){return void 0===t?this._isDirty:this.values[t].dirty},StateObject.prototype.anyDirty=function(t){1<arguments.length&&(t=Array.prototype.slice.apply(arguments));for(var e=0;e<t.length;e++)if(this.values[t[e]].dirty)return!0;return!1},StateObject.prototype.copy=function(){var t,e={};for(t in this.values)e[t]=this[t];return new StateObject(e)},StateObject.prototype.copyFrom=function(t){for(var e in t.values)this[e]=t[e]};;\nfunction StateAnimation(t,i){this.stateObject=t,this.propertyName=i,this.running=!1,this.lastUpdateTime=null,this.tick=this.tick.bind(this),this._callback=null}function SpringyAnimation(t,i,e){StateAnimation.apply(this,arguments),this.options=extend({},SpringyAnimation.defaults,e||{}),this.state={x:t[i],v:0},this.derivative={dx:0,dv:0}}function EasingAnimation(t,i,e){StateAnimation.apply(this,arguments),this.options=extend({},EasingAnimation.defaults,e||{}),this._startValue=null,this._startTime=null,this._targetValue=null,this._targetTime=null}StateAnimation.prototype.start=function(){this.running||(this.lastUpdateTime=null,this.running=!0,Ticker.frame(this.tick,\"update\"))},StateAnimation.prototype.pause=function(){Ticker.removeFrame(this.tick,\"update\"),this.running=!1},StateAnimation.prototype.tick=function(){var t=Date.now();this.update(t-(this.lastUpdateTime||t)),this.lastUpdateTime=t},StateAnimation.prototype.update=function(t){throw new Error(\"Not implemented\")},Object.defineProperty(StateAnimation.prototype,\"value\",{get:function(){return this.stateObject[this.propertyName]},set:function(t){this.stateObject[this.propertyName]=t},enumerable:!0}),inherit(SpringyAnimation,StateAnimation),SpringyAnimation.defaults={springForce:1e3,damping:20,restThreshold:1},SpringyAnimation.prototype.animateTo=function(t,i,e){this.running&&this.pause(),\"function\"==typeof i&&(e=i,i=0),this.d=t,this.state.v=\"number\"==typeof i?i:0,this.state.x=this.value,this._callback=e,this.start()},SpringyAnimation.prototype.update=function(t){t=Math.min(.03,t/1e3);Math.abs(this.state.x-this.d)<this.options.restThreshold&&Math.abs(this.state.v)<this.options.restThreshold?(this.pause(),this.value=this.d,this.d=null,defer(this._callback||noop),this._callback=null):(this._integrate(this.state,t),this.value=this.state.x)},SpringyAnimation.prototype._integrate=function(t,i){var e=this._getDerivative(this.state),a=this._getDerivative(this.state,.5*i,e),n=this._getDerivative(this.state,.5*i,a),s=this._getDerivative(this.state,i,n);t.x+=i/6*(e.dx+2*(a.dx+n.dx)+s.dx),t.v+=i/6*(e.dv+2*(a.dv+n.dv)+s.dv)},SpringyAnimation.prototype._getDerivative=function(t,i,e){return void 0===e?{dx:t.v,dv:this._acceleration(t)}:{dx:(t={x:t.x+e.dx*i,v:t.v+e.dv*i}).v,dv:this._acceleration(t,i)}},SpringyAnimation.prototype._acceleration=function(t){return-this.options.springForce*(t.x-this.d)-this.options.damping*t.v},inherit(EasingAnimation,StateAnimation),EasingAnimation.defaults={exp:1.8},EasingAnimation.presets={easeIn:.6,easeOut:1.8,linear:1},EasingAnimation.prototype.animateTo=function(t,i,e){this.running&&this.pause(),this._callback=e,this._startValue=this.value,this._startTime=Date.now(),this._targetValue=t,this._targetTime=i,this.start()},EasingAnimation.prototype.update=function(t){var i=Date.now()-this._startTime,e=this._targetValue-this._startValue;i>=this._targetTime?(this.pause(),this.value=this._targetValue,defer(this._callback||noop),this._startValue=null,this._startTime=null,this._targetValue=null,this._targetTime=null,this._callback=null):this.value=Math.pow(i/this._targetTime,this.options.exp)*e+this._startValue};;\nfunction ThresholdObserver(e){this.threshold=e,this._value=null}extend(ThresholdObserver.prototype,EventEmitter),Object.defineProperty(ThresholdObserver.prototype,\"value\",{get:function(){return this._value},set:function(e){var t,h;null!==this._value&&this._value!==e&&(t=this._value-this.threshold)*(h=e-this.threshold)<=0&&(t<0?this.emit(\"up\"):h<0&&this.emit(\"down\")),this._value=e}});;\nfunction BaseCreativeUnit(){}inherit(BaseCreativeUnit,View),BaseCreativeUnit.IGNORABLES=[\"INPUT\",\"SELECT\",\"TEXTAREA\",\"BUTTON\"],BaseCreativeUnit.prototype.awake=function(){BaseCreativeUnit.uber.awake.call(this),this._availableSize={width:0,height:0},this._isRendering=!1,this._engagementTracker=null,this._rxState=null,this._rxStateCopies=[]},BaseCreativeUnit.prototype.createNode=function(){return Logger(\"unit\").log(\"Starting to load \"+this),this.on(\"loaded\",creative.adapter.perf.start(\"CreativeUnit.load\").end),creative.experiments.get(\"TrackingLoadingTimes\")&&this.once(\"_firstScreenLoaded\",function(){var e,t=Date.now()-1e3*creative.runtimeParams.payloadJsClientTimestamp;t<3e4&&(e={unitName:this.name},creative.aggregatorTracking.trackAggregator(\"unitLoadTime\",t,e),creative.aggregatorTracking.trackAggregator(\"unitLoadSuccesses\",e))}.bind(this)),creative.adapter.on(\"mediaStopRequested\",function(e){this.emit(\"mediaStopRequested\",e)}.bind(this)),this.unitDiv=BaseCreativeUnit.uber.createNode.call(this),this._engagementTracker=new EngagementTracker(this.unitDiv,creative),Tapper(this.unitDiv,this.useClickForTap),attach(this.unitDiv,\"touchstart\",this._handleTouchStart.bind(this)),attach(this.unitDiv,\"touchmove\",this._handleTouchMove.bind(this)),this.unitDiv},BaseCreativeUnit.prototype.setAvailableSize=function(e,t){var r=this._availableSize.width!=e||this._availableSize.height!=t;return this._availableSize.width=e,this._availableSize.height=t,r},BaseCreativeUnit.prototype.getAvailableSize=function(){return{width:this._availableSize.width,height:this._availableSize.height}},BaseCreativeUnit.prototype.enterRenderTree=function(){throw new Error(\"Not implemented\")},BaseCreativeUnit.prototype.exitRenderTree=function(){throw new Error(\"Not implemented\")},BaseCreativeUnit.prototype.finishedLoading=function(){BaseCreativeUnit.uber.finishedLoading.call(this),Logger(\"unit\").log(\"Finished loading \"+this)},BaseCreativeUnit.prototype.getAccessibilityProperties=function(){throw new Error(\"Not implemented\")},BaseCreativeUnit.prototype.openAccessibilityUrl=function(e){var t=new ActionContext(this.currentScreen);this.goToURLAction(t,{url:e,reportLabel:e},noop)},BaseCreativeUnit.prototype._handleTouchStart=function(e){if(!this.swipeable&&!creative.adapter.useNativeClickForTapDetection){for(var t=e.target;1!=t.nodeType;)t=t.parentNode;var r=function(){for(;t!==this.unitDiv;){if(t.className&&hasClass(t,\"map-container\"))return!0;t=t.parentNode}return!1}.bind(this);-1!=BaseCreativeUnit.IGNORABLES.indexOf(t.nodeName)||r()||e.preventDefault()}},BaseCreativeUnit.prototype._handleTouchMove=function(e){this.swipeable||e.preventDefault()},BaseCreativeUnit.prototype.createRxState=function(){this._rxState=this._generateNewRxStateObject()},BaseCreativeUnit.prototype._generateNewRxStateObject=function(){return new StateObject({left:null,top:null,right:null,bottom:null,width:null,height:null,vw:null,vh:null,x:null,y:null,pageYOffset:null})},BaseCreativeUnit.prototype.updateRxState=function(e){var t;this._rxState&&(e=getElementRectRelativeToTopViewport(e),t=creative.adapter.getTopWindow(),this._rxState.left=~~e.left,this._rxState.top=~~e.top,this._rxState.right=~~e.right,this._rxState.bottom=~~e.bottom,this._rxState.width=~~e.width,this._rxState.height=~~e.height,this._rxState.vw=t.innerWidth,this._rxState.vh=t.innerHeight,this._rxState.pageYOffset=t.pageYOffset,this._rxState.x=~~(e.left+e.width/2-t.innerWidth/2),this._rxState.y=~~(e.top+e.height/2-t.innerHeight/2),this._rxStateCopies.forEach(function(e){e.copyFrom(this._rxState)}.bind(this)))},BaseCreativeUnit.prototype.getRxStateObject=function(){var e;return this._rxState?((e=this._generateNewRxStateObject()).copyFrom(this._rxState),this._rxStateCopies.push(e),e):null},BaseCreativeUnit.prototype.goToScreenAction=function(e,t,r){throw new Error(\"Not implemented\")},BaseCreativeUnit.prototype.alertAction=function(e,t,r){this.alert({message:t.text},r)},BaseCreativeUnit.prototype.checkOrientationAction=function(e,t,r){var a,i=creative.adapter.orientation;0===i||180===i?a=t.onPortrait:-90!=i&&90!=i||(a=t.onLandscape),a?a(e,r):r()},BaseCreativeUnit.prototype.checkOrientationActionDryRun=BaseCreativeUnit.prototype.checkOrientationAction,BaseCreativeUnit.prototype._processURLParams=function(e){return\"string\"!=typeof e?e:creative.macros(e)},BaseCreativeUnit.prototype._addUrlAppendage=function(e){var t;return creative.urlOpenedUrlAppendage?(t=this.size.width+\"x\"+this.size.height,e+(0<=e.indexOf(\"?\")?\"&\":\"?\")+creative.urlOpenedUrlAppendage.replace(new RegExp(\"{celtraFirstUnitSize}\",\"ig\"),t)):e},BaseCreativeUnit.prototype.openBrowser=function(e,t,r,a){if(!t)return(r||noop)();creative.adapter.trackEventsAndOpenBrowser(this._processURLParams(t),creative.shouldClickThroughToNewWindow(e),r,a)},BaseCreativeUnit.prototype.openClickThroughDestinationAction=function(e,t,r){var a=creative.clickThroughDestinationUrl,i=creative.shouldClickThroughToNewWindow(e),n=!1,o=\"clickThroughDestinationOpened\",s=function(){n||(n=!0,creative.adapter.openBrowser(this._processURLParams(a),i))}.bind(this);a&&-1==[\"http://\",\"https://\"].indexOf(a)?(creative.adapter._stopAllMedia(),0<a.indexOf(\"itunes.apple.com/us/app/\")||0===a.indexOf(\"market://details?id=\")?(e.track({name:o,appUri:a}),e.trackClickThrough(),creative.adapter.trackingCenter.flush(),creative.adapter.getApp(this._processURLParams(a))):(a=this._addUrlAppendage(a),e.track({name:o,url:a}),e.trackClickThrough(),!creative.adapter.canOpenUrlInSameWindow||i?(creative.adapter.trackingCenter.flush(),s()):(creative.adapter.trackingCenter.flush(s),defer(s,2e3)))):e.track({name:\"userError\",userErrorId:\"exitUriIsEmpty\"}),e.screen&&creative.adapter.sendToEventMonitor(o,t.triggerId,e.screen.name,a||\"\",null,e.initiatedBeforeScreenShown()),r()},BaseCreativeUnit.prototype.goToURLAction=function(e,t,r){function a(e,t,r){c&&creative.aggregatorTracking.trackAggregator(\"openBrowserRefactor\",1,{branch:e,logic:t,environment:r})}var i=creative.urlOpenedOverrideUrls&&creative.urlOpenedOverrideUrls[t.reportLabel]||t.url,n=creative.shouldClickThroughToNewWindow(e),o=!1,s=function(){o||(o=!0,creative.adapter.openBrowser(this._processURLParams(i),n))}.bind(this),c=(creative.experiments.get(\"OpenBrowserRefactor\")||{}).chosenVariant,l=(creative.experiments.get(\"WindowOpenWithDelay\")||{}).chosenVariant;if(!i||-1<[\"http://\",\"https://\"].indexOf(i))return e.track({name:\"userError\",userErrorId:\"exitUriIsEmpty\"}),r();var d,i=this._addUrlAppendage(i),p=\"urlOpened\";creative.adapter._stopAllMedia(),e.track({name:p,url:i,label:t.reportLabel}),e.trackClickThrough(),deviceInfo.browser.ie()||deviceInfo.browser.edge()?!creative.adapter.canOpenUrlInSameWindow||n?(a(\"new-window\",\"flush-and-open\",\"desktop-windows\"),creative.adapter.trackingCenter.flush(),s()):(a(\"same-window\",\"flush-then-open\",\"desktop-windows\"),creative.adapter.trackingCenter.flush(s),defer(s,2e3)):\"on\"==c?!creative.adapter.canOpenUrlInSameWindow||n?(a(\"new-window\",\"flush-and-open\",\"all-except-desktop-windows\"),creative.adapter.trackingCenter.flush(),s()):(a(\"same-window\",\"flush-then-open\",\"all-except-desktop-windows\"),creative.adapter.trackingCenter.flush(s),defer(s,2e3)):!creative.adapter.canOpenUrlInSameWindow||n?(a(\"new-window\",\"wrap+open\",\"all-except-desktop-windows\"),l&&creative.aggregatorTracking.trackAggregator(\"windowOpenWithDelay\",1,{delay:l}),c&&creative.aggregatorTracking.trackAggregator(\"openBrowserRefactorFlushVsWrap\",1,{trackingWith:\"wrap\"}),d=creative.wrapRedirectPageUrl(this._processURLParams(i)),c&&(creative.aggregatorTracking.trackAggregator(\"openBrowserRefactorFlushVsWrap\",1,{trackingWith:\"flush\"}),creative.adapter.trackingCenter.flush()),void 0===l||\"control\"===l?creative.adapter.openBrowser(d,n):setTimeout(function(){creative.adapter.openBrowser(d,n)},0)):(a(\"same-window\",\"wrap+open\",\"all-except-desktop-windows\"),creative.adapter.openBrowser(creative.wrapRedirectPageUrl(this._processURLParams(i)),n)),creative.adapter.sendToEventMonitor(p,t.triggerId,e.screen.name,i,t.reportLabel,e.initiatedBeforeScreenShown()),r()},BaseCreativeUnit.prototype.facebookURLAction=function(e,t,r){if(!t.url)return r();var a=\"facebookShareAttempt\",i=(creative.adapter._stopAllMedia(),e.track({name:a,url:t.url,label:t.reportLabel}),e.trackClickThrough(),\"http://m.facebook.com/sharer.php?u=\"+encodeURIComponent(t.url));creative.adapter.openBrowser(creative.wrapRedirectPageUrl(i),creative.shouldClickThroughToNewWindow(e)),creative.adapter.sendToEventMonitor(a,t.triggerId,e.screen.name,i,t.reportLabel,e.initiatedBeforeScreenShown()),r()},BaseCreativeUnit.prototype.callURLAction=function(e,t,r){if(!t.url)return r();var t=this._processURLParams(t.url),a=new Image;a.onload=a.onerror=function(){r()},a.src=t},BaseCreativeUnit.prototype.displayImageAction=function(e,a,i){if(!a.file)return i();var t,r=function(e,t){var r=document.createElement(\"div\"),e=(r.className=\"celtra-display-image-overlay touchable\",r.style.backgroundImage='url(\"'+a.file.getUrl()+'\")',this.unitDiv.appendChild(r),(e>this.size.width||t>this.size.height)&&(r.style.backgroundSize=\"contain\"),a.color&&(r.style.backgroundColor=a.color),attach(r,\"tap\",function(e){r.parentNode.removeChild(r),i()},!1),document.createElement(\"div\"));e.className=\"celtra-back-button touchable \"+this.alignBackButtonHorizontal,r.appendChild(e)}.bind(this);a.file.meta?r(a.file.meta.width,a.file.meta.height):((t=new Image).onload=function(){r(this.width,this.height)},t.onerror=function(e){return console.error(\"Couldn't display image: \"+a.file.getUrl()),i()},t.src=a.file.getUrl())},BaseCreativeUnit.prototype.saveImageAction=function(i,n,o){var e,s=\"imageSaveAttempted\";function r(e,t){var r=e.getOriginalUrl(),a=e.name,e=e.blobHash;i.track({name:s,blobHash:e,label:n.reportLabel}),i.trackClickThrough(),creative.adapter.canSaveImage()?creative.adapter.trackingCenter.flush(function(){var e=\"shared.png\",e=(void 0!==a?e=a:\"string\"==typeof t&&0<t.trim().length&&(e=t.trim()+\".png\"),creative.adapter.saveImage(r,encodeURIComponent(e),o),t||a);creative.adapter.sendToEventMonitor(s,n.triggerId,i.screen.name,e,n.reportLabel,i.initiatedBeforeScreenShown())}):new SaveImageDialog(creative.adapter,creative.resourceUrl,r).show(o)}n.imageOrigin=n.imageOrigin||\"asset\",\"component\"==n.imageOrigin&&n.component?n.component.file?r(n.component.file,n.component.name):null===(e=n.component.getField())?o():-1===creative.runtimeParams.enabledServices.indexOf(\"contentSharing\")?this.alertAction(i,{text:\"This feature is not available in your region\"},o):(e=e.url.split(\",\")[1],creative.adapter.createPlacements({preloader:{attachment:\"body\",positioning:\"screen\",type:\"layer\"}},function(){var e=creative.adapter.placements.preloader,t=(e.setZIndex(\"max\"),e.setSize(\"100%\",\"100%\"),e.setPosition(0,0),e.createElement(\"link\")),t=(t.rel=\"stylesheet\",t.type=\"text/css\",t.href=creative.resourceUrl+\"runner/clazzes/PostBlobPreloader.css\",e.getContainer().appendChild(t),e.createElement(\"div\")),r=(addClass(t,\"celtra-preloader-container\"),e.createElement(\"div\"));addClass(r,\"celtra-preloader-image\"),r.style.backgroundImage=\"url(\"+creative.resourceUrl+\"runner/clazzes/CreativeUnit/preloader.png)\",t.appendChild(r),e.populate(t,function(){e.show()})}),postBlob(e,function(e,t){creative.adapter.destroyPlacement(\"preloader\"),200<=t&&t<300?(t=JSON.parse(e),e=creative.cachedApiUrl+\"blobs/\"+t.hash+\"/shared.png\",r(new File(e),n.component.name)):o()})):\"asset\"==n.imageOrigin&&n.file?r(n.file):o()},BaseCreativeUnit.prototype.playSoundAction=function(e,t,r){if(!t.file)return r();t=new Audio(t.file.getUrl());attach(t,\"ended\",function(){r()},!1),t.play()},BaseCreativeUnit.prototype.playAudioAction=function(e,t,r){this.playSoundAction(e,t,r),e.track({name:\"audioPlayed\",source:t.file.getUrl(),label:t.reportLabel})},BaseCreativeUnit.prototype.playVideoInPlayerAction=function(t,r,a){if(!r.file)return a();var e=creative.adapter.useFSVP,i=r.file.url?\"URL\":\"File\",n=new VideoContext(t,r.file,r.reportLabel);n.track({engineType:\"html5\",name:\"videoPlayAttempted\",trackable:e,filename:r.file.name,userInitiated:t.consideredUserInitiatedByBrowser,startMuted:!1,videoPlayerMode:\"fullscreen\",sourceType:i,source:\"URL\"==i?r.file.url:r.file.blobHash,label:r.reportLabel}),n.track({name:\"videoPresetSelected\",videoPreset:null}),creative.adapter.playVideoInPlayer(n,r,function(e){((e?r.onSuccess:r.onFailure)||nullai)(t,a)})},BaseCreativeUnit.prototype.playVideoInPlayerActionDryRun=function(e,t,r){if(!t.file)return r();(t.onSuccess||nullai)(e,noop),(t.onFailure||nullai)(e,noop),r()},BaseCreativeUnit.prototype.playVideoFromURLAction=function(t,r,a){if(!r.url)return a();var e=r.file=new File(r.url),i=creative.adapter.useFSVP,n=e.url?\"URL\":\"File\",o=(creative.adapter._stopAllMedia(),new VideoContext(t,e,r.reportLabel));o.track({engineType:\"html5\",name:\"videoPlayAttempted\",trackable:i,filename:null,userInitiated:t.consideredUserInitiatedByBrowser,startMuted:!1,videoPlayerMode:\"fullscreen\",sourceType:n,source:\"URL\"==n?e.url:e.blobHash,label:r.reportLabel}),o.track({name:\"videoPresetSelected\",videoPreset:null}),creative.adapter.playVideoInPlayer(o,r,function(e){((e?r.onSuccess:r.onFailure)||nullai)(t,a)})},BaseCreativeUnit.prototype.playVideoFromURLActionDryRun=function(e,t,r){if(!t.url)return r();(t.onSuccess||nullai)(e,noop),(t.onFailure||nullai)(e,noop),r()},BaseCreativeUnit.prototype.vibrateAction=function(e,t,r){r()},BaseCreativeUnit.prototype.getAppAction=function(t,r,e){function a(){return new RegExp(\"^\\\\d+$\").test(r.appStoreUrl)?\"https://itunes.apple.com/app/id\"+r.appStoreUrl:(u(r.appStoreUrl)?\"\":\"http://\")+r.appStoreUrl}function i(){return u(r.androidMarketAppId)?r.androidMarketAppId:android()||\"Android\"===p.osName?\"market://details?id=\"+r.androidMarketAppId:\"https://play.google.com/store/apps/details?id=\"+r.androidMarketAppId}var n,o,s,c,l,d,p=(creative.runtimeParams.overrides||creative.runtimeParams).deviceInfo,u=function(e){return new RegExp(\"(^http(s)?://)|(^market://)\",\"i\").test(e)};creative.adapter._stopAllMedia(),creative.storeOpenedOverrideUrls[r.reportLabel]?n=creative.storeOpenedOverrideUrls[r.reportLabel]:r.appStoreUrl&&(ios()||-1!==[\"OSX\",\"IOS\"].indexOf(p.osName))?n=a():r.androidMarketAppId&&(n=i()),n&&-1==[\"http://\",\"https://\"].indexOf(n)?(o=!ios(\"9\",null),s=\"storeOpened\",c=2,l=function(){creative.adapter.getApp(this._processURLParams(n))}.bind(this),d=function(){var e;!--c&&o&&(l(),e={},r.appStoreUrl&&(e.appStoreUrl=a()),r.androidMarketAppId&&(e.androidMarketAppId=i()),creative.adapter.sendToEventMonitor(s,r.triggerId,t.screen.name,e,r.reportLabel,t.initiatedBeforeScreenShown()))}.bind(this),t.track({name:s,appUri:n,label:r.reportLabel},d),t.trackClickThrough(d),o||setTimeout(l,0)):t.track({name:\"userError\",userErrorId:\"exitUriIsEmpty\"}),e()},BaseCreativeUnit.prototype.callPhoneAction=function(e,t,r){if(!t.number)return r();creative.adapter._stopAllMedia();function a(){creative.adapter.callPhone(t.number),creative.adapter.sendToEventMonitor(i,t.triggerId,e.screen.name,t.number,t.reportLabel,e.initiatedBeforeScreenShown())}var i=\"phoneCalled\",n=deviceInfo.os.ios()&&deviceInfo.browser.safari();e.track({name:i,number:t.number,label:t.reportLabel}),e.trackClickThrough(),n?(creative.adapter.trackingCenter.flush(),defer(a)):creative.adapter.trackingCenter.flush(a),r()},BaseCreativeUnit.prototype.answerSurveyQuestion=function(e,t,r){var a=/[0-9a-f]{8}/;[\"answerIds\",\"questionId\",\"surveyId\",\"answers\"].forEach(function(e){if(\"answerIds\"===e){if(!(t.answerIds instanceof Array))throw new Error(\"args.answerIds must be an array\");t.answerIds.forEach(function(e){if(!a.test(e))throw new Error(\"args.answerIds must contain Ids that have hexadecimal formatted string containing 8 characters\")})}else if(\"answers\"===e){if(!(t.answers instanceof Array)||t.answers.length!==t.answerIds.length)throw new Error(\"args.\"+e+\" length should be the same as args.answerIds\")}else if(!a.test(t[e]))throw new Error(\"args.\"+e+\" must be hexadecimal formatted string containing 8 characters\")}),e.track({name:\"surveyQuestionAnswered\",answerIds:t.answerIds,questionId:t.questionId,surveyId:t.surveyId,surveyVersion:t.surveyVersion,answerIdTextMappings:t.answers.map(function(e){return{id:e.id,text:e.text,orderIndex:e.orderIndex}})}),r()},BaseCreativeUnit.prototype.saveTheDateAction=function(e,r,t){return r.eventName&&r.location&&r.start?(r.end=r.end||new Date(r.start.getTime()+36e5),r.timezone=r.timezone||\"my\",i(r.start)?i(r.end)?r.end<r.start?(console.error(\"End date must be after the start date!\"),t()):-1===[\"my\",\"utc\",\"user\"].indexOf(r.timezone)?(console.error(\"Invalid timezone argument!\"),t()):void 0!==r.reminder&&-1===[\"5m\",\"15m\",\"30m\",\"1h\",\"2h\",\"1d\",\"2d\",\"date\"].indexOf(r.reminder)?(console.error(\"Invalid reminder argument!\"),t()):(r.start=a(r.start),r.end=a(r.end),void CCalendar.saveTheDateAction(e,r,t)):(console.error(\"Invalid end date\"),t()):(console.error(\"Invalid start date\"),t())):(console.error(\"saveTheDateAction must have eventName, location and start date defined!\"),t());function a(e){var t;return r.allDay?(t=new CDate).value=e.getFullYear()+\"-\"+zeroPad(e.getMonth()+1)+\"-\"+zeroPad(e.getDate()):(e=\"my\"===r.timezone?e.getUTCFullYear()+\"-\"+zeroPad(e.getUTCMonth()+1)+\"-\"+zeroPad(e.getUTCDate())+\" \"+zeroPad(e.getUTCHours())+\":\"+zeroPad(e.getUTCMinutes())+\":\"+zeroPad(e.getUTCSeconds()):e.getFullYear()+\"-\"+zeroPad(e.getMonth()+1)+\"-\"+zeroPad(e.getDate())+\" \"+zeroPad(e.getHours())+\":\"+zeroPad(e.getMinutes())+\":\"+zeroPad(e.getSeconds()),(t=new CDateTime).value=e,t.tz=r.timezone),t}function i(e){return e instanceof Date&&!isNaN(e)}},BaseCreativeUnit.prototype.resizeAction=function(e,t,r){throw new Error(\"Resize action is not available for this ad format or creative unit\")},BaseCreativeUnit.prototype._areRevealResizeActionArgsValid=function(e){return-1!==[\"intro\",\"collapsed\",\"maximum\",\"fullscreen\"].indexOf(e.height)},BaseCreativeUnit.prototype.expandAction=function(e,t,r){throw new Error(\"Expand action is not available for this ad format or creative unit\")},BaseCreativeUnit.prototype._assertAppearBeforeExpand=function(){this.hasAppearedAtLeastOnce||creative.adapter.trackingCenter.track({name:\"userError\",userErrorId:\"expandBeforeAppear\"})},BaseCreativeUnit.prototype.collapseAction=function(e,t,r){throw new Error(\"Collapse action is not available for this ad format or creative unit\")},BaseCreativeUnit.prototype.dismissAction=function(e,t,r){throw new Error(\"Dismiss action is not available for this ad format or creative unit\")},BaseCreativeUnit.prototype.findAll=function(){throw new Error(\"Not implemented!\")},BaseCreativeUnit.prototype.findByType=function(){throw new Error(\"Not implemented!\")},BaseCreativeUnit.prototype.find=function(e){e=this.findAll(e);return 0<e.length?e[0]:null};;\n!function(e){\"use strict\";function t(e,r,t,n){if(-1===[\"PUT\",\"DELETE\"].indexOf(e))throw new Error(\"Unsupported request method: \"+e);if(void 0===creative.runtimeParams.customAudiences[r])throw new Error('Custom audience \"'+r+'\" is not used by the creative.');creative.runtimeParams.customAudiences[r]={userExists:t,userData:n};var i,u,s,o=creative.secure?creative.customAudiencesUrl:creative.insecureCustomAudiencesUrl,c=\"/audiences/\"+r,a=creative.userIdentifiers;for(i in a)for(var d in a[i])d=a[u=i][d],s=void 0,(s=new XMLHttpRequest).open(e,o+c+\"/\"+encodeURIComponent(u)+\"/\"+encodeURIComponent(d)+\"?\"+creative.authTokenUrlParam),s.send(n)}var r={toString:function(){return\"[Clazz CustomAudiences]\"}};r.addUser=function(e,r){creative.runtimeParams.userOptOut||t(\"PUT\",e,!0,r)},r.removeUser=function(e){creative.runtimeParams.userOptOut||t(\"DELETE\",e,!1)},e.CustomAudiences=r}(window);;\n!function(e){\"use strict\";function n(t,n){var o=[];function a(e){n&&0<o.length||e.forEach(function(e){t(e)&&o.push(e),e.getActiveContainers().forEach(function(e){a(e.objects),i(e.scenes)})})}function i(e){!e||n&&0<o.length||e.forEach(function(e){t(e)&&o.push(e)})}return\"Screen\"==this.constructor.name?(a(this.objects),i(this.scenes)):\"function\"==typeof this.getActiveContainers&&this.getActiveContainers().forEach(function(e){a(e.objects),i(e.scenes)}),o}function o(e){return 0<(e=n.call(this,e,!0)).length?e[0]:null}function t(t){return function(e){return void 0===t||e.name==t}}var a={ambience:\"Ambience\",animatey:\"Animatey\",abtest:\"Best\",basket:\"Basket\",button:\"Button\",canvas:\"Canvas\",celebration:\"Celebration\",checkbox:\"CheckBox\",feedbasedchoice:\"ChoiceFeed\",countdown:\"Countdown\",draggy:\"Draggy\",dropdown:\"Dropdown\",dynamiccontent:\"DynamicContent\",facebookshare:\"FacebookShareButton\",fallback:\"Fallback\",frame:\"Frame\",flippy:\"Flippy\",group:\"Group\",gyro:\"Gyro\",hotspot:\"Hotspot\",input:\"Input\",map:\"Map\",transitions:\"Transitions\",slider:\"BeforeAfterSlider\",mastervideo:\"MasterVideo\",nearby:\"Nearby\",painty:\"Painty\",pannable:\"Pannable\",particles:\"Particle\",picture:\"Picture\",puzzle:\"Puzzle\",shapey:\"Shapey\",smoke:\"Smoke\",stack:\"Stack\",stopwatch:\"Stopwatch\",locatorclassic:\"StoreLocator2\",locator:\"StoreLocator3\",swipeygallery:\"Swipey\",swipeygroup:\"SwipeyGroup\",cube3d:\"Cube3d\",takephoto:\"TakePhotoButton\",texty:\"Texty\",twitterfeed:\"TwitterFeed\",twittershare:\"TwitterShareButton\",inlinevideo:\"Video\",\"360view\":\"View360\",\"360video\":\"Video360\",\"360\u00b0video\":\"Video360\",weather:\"Weather\",whatsappshare:\"WhatsAppShareButton\",wipeable:\"Wipeable\",youtube:\"Youtube\",scene:\"Scene\",brandtracksurvey:\"BrandTrackSurvey\",brandtrackquestion:\"BrandTrackQuestion\",customcode:\"CustomCode\",videoasset:\"VideoAsset\"};e.DiscoverableTrait={findAll:function(e){return n.call(this,t(e))},findByType:function(t){return void 0===t?[]:n.call(this,function(e){return e.constructor.name==((e=(e=t).toLowerCase().replace(/\\W+/g,\"\"))in a?a[e]:\"\")})},findByLocalId:function(t){return o.call(this,function(e){return e.localId==t})},find:function(e){return o.call(this,t(e))}}}(window);;\n!function(e){\"use strict\";var n={choice:{checkChoiceFeed:[\"feedFieldKey\"],ChoiceFeed:[\"choiceFeedFieldKey\"],ChoiceFeedECD:[\"choiceFeedFieldKey\"]},color:{Shapey:[\"backgroundColorFeedFieldKey\"],Texty:[\"textColorFeedFieldKey\"],RichTexty:[\"textFillFeedFieldKey\"]},font:{Texty:[\"textFontFeedFieldKey\"],Button:[\"textFontFeedFieldKey\"],RichTexty:[\"fontFeedFieldKey\"]},html:{CustomCode:[\"htmlContentFeedFieldKey\"],CustomCodeECD:[\"htmlContentFeedFieldKey\"]},image:{Picture:[\"imageSourceFeedFieldKey\"],Swipey:[\"contentItemsFeedFieldKey\"],Video:[\"posterImageFeedFieldKey\"]},text:{Texty:[\"textContentFeedFieldKey\"],Button:[\"callToActionTextFeedFieldKey\"],RichTexty:[\"textFeedFieldKey\"]},url:{goToFeedURL:[\"feedFieldKey\"],QRCode:[\"destinationUrlFeedFieldKey\"]},video:{MasterVideo:[\"videoSourceFeedFieldKey\"],VideoAsset:[\"videoSourceFeedFieldKey\"],Video:[\"videoSourceFeedFieldKey\"],VideoAssetECD:[\"videoSourceFeedFieldKey\"]},audio:{AudioECD:[\"audioSourceFeedFieldKey\"]}};function d(e){e=\"string\"==typeof e?e.split(\"/\"):[];if(!(e.length<2))return e[1]}var i={};function r(e){var t;return void 0===i[e]&&(i[e]={type:\"unknown\",rows:[]}),0===e.indexOf(\"LocationTableImplicitField\")&&\"unknown\"===i[e].type&&(t=\"text\",\"/website\"===e.slice(-8)&&(t=\"url\"),i[e].type=t),i[e]}var t=!1;function o(){return t||(creative.runtimeParams.dynamicContent.forEach(function(i){i.rows.forEach(function(e,n){var d=e.fieldValues;Object.keys(d).forEach(function(e){var t=r(e),i=d[e].metaData,e=d[e].value;t.rows[n]={metadata:\"object\"==typeof i?JSON.parse(JSON.stringify(i)):null,value:e}})}),i.fields.forEach(function(e){var t=r(\"CustomFeedField/\"+e.id);t.id=e.id,t.feedId=i.feedId,t.name=e.name,t.type=e.fieldType})}),t=!0),i}e.FeedData={checkAndTrackValue:function(e,t,i,n){e&&(this.isCorrectFieldType(e,i,n)?this.getFieldValueByKey(e,t)||(console.warn(\"Feed source missing for key on \"+i+\": \"+e+\".\"),creative.adapter.trackingCenter.track({name:\"userError\",userErrorId:\"feedValueMissing\"})):creative.adapter.trackingCenter.track({name:\"userError\",userErrorId:\"feedValueUnlinked\"}))},getCustomFields:function(){var t=o();return Object.keys(t).filter(function(e){return 0===e.indexOf(\"CustomFeedField\")}).map(function(e){e=t[e];return{id:e.id,feedId:e.feedId,name:e.name,type:e.type,values:e.rows.map(function(e){return e.value}).filter(function(e){return null!==e})}})},getCustomFieldByKey:function(e){var t=d(e);if(t)for(var i=this.getCustomFields(),n=0;n<i.length;n++)if(i[n].id===t)return i[n]},getCustomFieldByName:function(e){for(var t,i=this.getCustomFields(),n=0;n<i.length;n++)if(i[n].name===e){t=i[n];break}return t},getFeedByFieldKey:function(e){var t=d(e);if(t)return creative.runtimeParams.dynamicContent.find(function(e){return e.fields.some(function(e){return e.id==t})})},getFieldByKey:function(e){return o()[e]},getFieldRowsByKey:function(e){e=this.getFieldByKey(e),e=e?e.rows:[];return 1===e.length&&\"string\"==typeof e[0].value||1<e.length?e:[]},getFieldRowByKey:function(e,t){return t=\"number\"==typeof t?t:0,this.getFieldRowsByKey(e)[t]},getFieldValueByKey:function(e,t){e=this.getFieldRowByKey(e,t);return e&&e.value},isCorrectFieldType:function(e,t,i){var e=this.getFieldByKey(e);return!!e&&(\"RichTexty\"===t&&(i=i.split(\"#\")[0]+\"FeedFieldKey\"),e=e.type,-1!==((n[e]||{})[t]||[]).indexOf(i))}}}(window);;\nvar FeedImageOptimizationTrait={getFeedImageOptimizationSetting:function(i){if(this.feedImageOptimizationSettings)for(var t=0;t<this.feedImageOptimizationSettings.length;t++)if(this.feedImageOptimizationSettings[t].feedFieldKey===i)return this.feedImageOptimizationSettings[t];return null}};;\nvar FeedImageCreator={_createFeedImageFile:function(e,t){var i,n,e=e+\"FeedFieldKey\",t=FeedData.getFieldValueByKey(this[e],t);return\"string\"==typeof t&&FeedData.isCorrectFieldType(this[e],this.constructor.name,e)?(i=[],n=void 0,(e=this.parentScreen&&this.parentScreen.parentContainer?this.parentScreen.parentContainer.getFeedImageOptimizationSetting(this[e]):{})&&e.optimizedWidth&&e.optimizedHeight&&e.fitting&&(i=[{creativeUnitVariantId:this.parentScreen.parentContainer.localId,optimizedWidth:e.optimizedWidth,optimizedHeight:e.optimizedHeight}],n=e.fitting),e=ScreenObject._toSizePx(this.layoutCurrent.size,this.parentContainer.size),e={height:Math.max(1,e.height),width:Math.max(1,e.width)},new File({url:t,dynamic:!0,meta:e,optimizationSettings:i,fitting:n})):null}};;\nvar SaveImageDialog=function(t,i,e){this.adapter=t,this.resourceUrl=i,this.imageUrl=e,this.containerMinMargin=5,this.containerMinWidth=190,this.imageContainerTotalHMargin=40,this.imageContainerTotalVMargin=140,this.instructionOneLineWidth=250,this.instructionTwoLinesWidth=145,this._handleImageLoaded=this._handleImageLoaded.bind(this),this._resize=this._resize.bind(this)};SaveImageDialog.prototype.show=function(t){this.adapter.createPlacements({saveImageAction:{attachment:\"body\",positioning:\"screen\",type:\"layer\"}},function(){this.placement=this.adapter.placements.saveImageAction,this.placement.setZIndex(\"max\"),this.placement.setSize(\"100%\",\"100%\"),this.placement.setPosition(0,0),this.saveImageActionStyle=this.placement.createElement(\"link\"),this.saveImageActionStyle.rel=\"stylesheet\",this.saveImageActionStyle.type=\"text/css\",this.saveImageActionStyle.href=this.resourceUrl+\"runner/SaveImageDialog.css\",this.placement.getContainer().appendChild(this.saveImageActionStyle),this.overlay=this.placement.createElement(\"div\"),this.overlay.style.display=\"none\",this.overlay.className=\"celtra-save-image-action-overlay\",this.preloaderImage=this.placement.createElement(\"div\"),this.preloaderImage.className=\"celtra-save-image-action-preloader-image\",this.preloaderImage.style.backgroundImage=\"url(\"+this.resourceUrl+\"runner/clazzes/CreativeUnit/preloader.png)\",this.container=this.placement.createElement(\"div\"),this.container.className=\"celtra-save-image-action-container\",this.backButton=this.placement.createElement(\"div\"),this.backButton.style.backgroundImage=\"url(\"+this.resourceUrl+\"runner/clazzes/CreativeUnit/save-image-close-button.png)\",this.backButton.className=\"celtra-save-image-action-back-button\",this.title=this.placement.createElement(\"div\"),this.title.innerHTML=\"Save Image\",this.title.className=\"celtra-save-image-action-title\",this.titleLine1=this.placement.createElement(\"div\"),this.titleLine1.className=\"celtra-save-image-action-title-line-1\",this.titleLine2=this.placement.createElement(\"div\"),this.titleLine2.className=\"celtra-save-image-action-title-line-2\",this.imageContainer=this.placement.createElement(\"div\"),this.imageContainer.className=\"celtra-save-image-action-image-container\",this.image=this.placement.createElement(\"img\"),this.instruction=this.placement.createElement(\"div\"),this.instruction.style.backgroundImage=\"url(\"+this.resourceUrl+\"runner/clazzes/CreativeUnit/hold-to-save-icon.png)\",this.instruction.style.backgroundSize=\"21px 40px\",this.instruction.innerHTML=\"Tap & hold to save the image to your device.\",this.instruction.className=\"celtra-save-image-action-instruction\",this.image.onload=this._handleImageLoaded,this.image.src=this.imageUrl,this.container.appendChild(this.title),this.container.appendChild(this.titleLine1),this.container.appendChild(this.titleLine2),this.imageContainer.appendChild(this.image),this.container.appendChild(this.imageContainer),this.container.appendChild(this.instruction),this.container.appendChild(this.backButton),this.overlay.appendChild(this.preloaderImage),attach(this.backButton,\"touchend\",function(){this.adapter.destroyPlacement(\"saveImageAction\"),t()}.bind(this)),this.placement.populate(this.overlay,function(){this.placement.show()}.bind(this))}.bind(this))},SaveImageDialog.prototype._handleImageLoaded=function(){this._resize(),this.overlay.removeChild(this.preloaderImage),this.overlay.appendChild(this.container),this.placement.on(\"resized\",this._resize)},SaveImageDialog.prototype._resize=function(){var t,i,e=this.placement.getViewportGeometry(),a=e.width-2*this.containerMinMargin,n=e.height-2*this.containerMinMargin,a=a-this.imageContainerTotalHMargin,n=n-this.imageContainerTotalVMargin,s=a/n,h=this.image.naturalWidth/this.image.naturalHeight,r=this.image.naturalWidth>a,l=this.image.naturalHeight>n,o=this.containerMinMargin,c=this.containerMinMargin,g=0,m=0,d=(r&&l?(t=a,i=n,s<h?(this.image.style.width=(d=h/s*100)+\"%\",this.image.style.height=\"100%\",g=-(d-100)/2):(d=s/h*100,this.image.style.width=\"100%\",this.image.style.height=d+\"%\",m=-(d-100)/2*(i/t))):r?(g=-((s=this.image.naturalWidth/a*100)-100)/2,t=a,i=this.image.naturalHeight,this.image.style.width=s+\"%\",this.image.style.height=\"100%\",c=(e.height-i-this.imageContainerTotalVMargin)/2):l?(h=this.image.naturalHeight/n*100,m=(i=n)/(t=this.image.naturalWidth)*(-(h-100)/2),this.image.style.width=\"100%\",this.image.style.height=h+\"%\",o=(e.width-t-this.imageContainerTotalHMargin)/2):(t=this.image.naturalWidth,i=this.image.naturalHeight,this.image.style.width=\"100%\",this.image.style.height=\"100%\",o=(e.width-t-this.imageContainerTotalHMargin)/2,c=(e.height-i-this.imageContainerTotalVMargin)/2),t+this.imageContainerTotalHMargin),r=t+this.imageContainerTotalHMargin-this.containerMinWidth;r<0&&(o+=a=r/2,this.imageContainer.style.left=19-a+\"px\",this.imageContainer.style.right=19-a+\"px\",d-=r),this.container.style.left=o+\"px\",this.container.style.right=o+\"px\",this.container.style.top=c+\"px\",this.container.style.bottom=c+\"px\",this.image.style.marginLeft=g+\"%\",this.image.style.marginTop=m+\"%\",d<this.instructionOneLineWidth+80?(this.instruction.style.paddingTop=\"4px\",this.instruction.style.width=this.instructionTwoLinesWidth+\"px\",this.instruction.style.height=\"63px\",this.instruction.style.left=this.instruction.style.right=(d-this.instructionTwoLinesWidth-this.imageContainerTotalHMargin)/2+\"px\"):(this.instruction.style.paddingTop=\"16px\",this.instruction.style.width=this.instructionOneLineWidth+\"px\",this.instruction.style.height=\"51px\",this.instruction.style.left=this.instruction.style.right=(d-this.instructionOneLineWidth-this.imageContainerTotalHMargin)/2+\"px\")};;\nvar CCalendar={saveTheDateAction:function(e,t,a){var n={start:t.start.value,end:t.end.value};t.eventName&&(n.eventName=t.eventName),t.location&&(n.location=t.location),t.reminder&&(n.reminder=t.reminder),t.notes&&(n.notes=t.notes),creative.adapter.sendToEventMonitor(\"dateSaved\",t.triggerId,e.screen.name,n,null,e.initiatedBeforeScreenShown()),e.track({name:\"dateSaved\",label:t.reportLabel}),creative.adapter._stopAllMedia(),creative.adapter.saveCalendarEvent(t,a)},getEventICSUrl:function(a,e){var t,n={format:\"ics\"};return[\"eventName\",\"location\",\"start\",\"end\",\"timezone\",\"reminder\",\"notes\"].forEach(function(e){var t;e in a&&(\"string\"==typeof(t=\"object\"==typeof(t=a[e])&&t.hasOwnProperty(\"value\")?t.value:t)&&t.length&&(n[e]=t))}),n.start&&\"eventName\"in n?(n.end&&10===n.end.length&&(t=new Date(Date.parse(n.end)+864e5),n.end=t.getFullYear()+\"-\"+(\"0\"+(t.getMonth()+1)).slice(-2)+\"-\"+(\"0\"+t.getDate()).slice(-2)),urls.cachedApiUrl.replace(/^https:/,e)+\"calendar?\"+buildQuery(n)):null},getEventGoogleCalendarUrl:function(e){function t(e,t){var a;return a=(e=e)[t=t].value.replace(\" \",\"T\"),e.allDay&&(a=a.substr(0,10)+\"T\"+(\"start\"===t?\"00:00:00\":\"23:59:59\"),e.timezone=\"user\"),new Date(a+(\"user\"===e.timezone?(t=a,t=new Date(t).getTimezoneOffset(),e=Math.abs(t),a=Math.floor(e/60),(0<t?\"-\":\"+\")+n(a)+\":\"+n(e-60*a)):\"+00:00\")).toISOString().replace(/[-:]|(?:\\.[0-9]*)/g,\"\")}function n(e){return(\"0\"+e).slice(-2)}var a=\"https://calendar.google.com/calendar/\"+(desktop()?\"render?\":\"gp#~calendar:view=e&\")+\"action=TEMPLATE\",e={dates:t(e,\"start\")+\"/\"+t(e,\"end\"),location:e.location,text:e.eventName,details:e.notes};return a+\"&\"+buildQuery(e)},toString:function(){return\"[Clazz CCalendar]\"}};;\nfunction CDate(){}CDate.toString=function(){return\"[Clazz CDate]\"},CDate.prototype.toString=function(){return\"[CDate \"+this.localId+\"]\"},CDate.prototype.getLocalStartDate=function(){var t=this._getParts();return new Date(t[1],t[2]-1,t[3],0,0,0)},CDate.prototype.getLocalEndDate=function(){var t=this._getParts();return new Date(t[1],t[2]-1,t[3]+1,0,0,0)},CDate.prototype.getDate=function(){return this.getLocalStartDate()},CDate.prototype._getParts=function(){return this.value.match(/^(....)-(..)-(..)$/).map(function(t){return parseInt(t,10)})};;\nfunction CDateTime(){}CDateTime.toString=function(){return\"[Clazz CDateTime]\"},CDateTime.prototype.toString=function(){return\"[CDateTime \"+this.localId+\"]\"},CDateTime.prototype.getDate=function(){var t=this.value.match(/^(....)-(..)-(..) (..):(..):(..)$/).map(function(t){return parseInt(t,10)});switch(this.tz){case\"my\":case\"utc\":return new Date(Date.UTC(t[1],t[2]-1,t[3],t[4],t[5],t[6]));case\"user\":return new Date(t[1],t[2]-1,t[3],t[4],t[5],t[6]);default:Creative._throw(\"Invalid CDateTime timezone: \"+this.tz)}};;\nvar Twitter={showUserAction:function(e,t,r){var a,o;t.screenName&&(a=\"twitterProfileOpened\",creative.adapter._stopAllMedia(),e.track({name:a,screenName:t.screenName,label:t.reportLabel}),e.trackClickThrough(),o=\"http://\"+(desktop()?\"www\":\"mobile\")+\".twitter.com/\"+t.screenName,creative.adapter.openBrowser(creative.wrapRedirectPageUrl(o),creative.shouldClickThroughToNewWindow(e)),creative.adapter.sendToEventMonitor(a,t.triggerId,e.screen.name,\"@\"+t.screenName,t.reportLabel,e.initiatedBeforeScreenShown())),r()},tweetAction:function(e,t,r){var a;t.text&&(e.track({name:\"tweetPageOpened\",tweet:t.text,tweetVia:t.via,label:t.reportLabel}),e.trackClickThrough(),a=\"http://twitter.com/intent/tweet?text=\"+encodeURIComponent(t.text),t.via&&(a+=\"&via=\"+encodeURIComponent(t.via)),creative.adapter.openBrowser(creative.wrapRedirectPageUrl(creative.apiUrl+\"redirect?url=\"+encodeURIComponent(a)+\"&type=js\"),creative.shouldClickThroughToNewWindow(e))),r()}};;\n!function(t){\"use strict\";var p={stringPatternAngle:\"(?:[+-]?\\\\d*\\\\.?\\\\d+)(?:deg|grad|rad|turn)\",stringPatternColor:\"(?:#(?:[A-Fa-f0-9]{3,8})|(?:hsl(?:a)?)\\\\(\\\\s*(?:\\\\d+)\\\\s*,\\\\s*(?:\\\\d+(?:\\\\.\\\\d+)?%)\\\\s*,\\\\s*(?:\\\\d+(?:\\\\.\\\\d+)?%)\\\\s*(?:,\\\\s*(?:\\\\d+|\\\\d*.\\\\d+)\\\\s*)?\\\\s*\\\\)|(?:rgb(?:a)?)\\\\(\\\\s*(?:\\\\d+)\\\\s*,\\\\s*(?:\\\\d+)\\\\s*,\\\\s*(?:\\\\d+)\\\\s*(?:,\\\\s*(?:\\\\d+|\\\\d*.\\\\d+)\\\\s*)?\\\\s*\\\\))\",stringPatternSideCorner:\"top|bottom|left|right\",stringPatternValue:\"(?:[+-]?\\\\d*\\\\.?\\\\d+)(?:%|[a-z]+)?\",patternHex:/^(#|)([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$/,patternHexa:/^(#|)([A-Fa-f0-9]{8})$/,matchHexHash:1,matchHexColor:2,patternRgb:/^rgb\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*\\)$/i,matchRgbRed:1,matchRgbGreen:2,matchRgbBlue:3,patternRgba:/^rgba\\(\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*(\\d+)\\s*,\\s*((?:\\d\\.)?\\d\\d?)\\s*\\)$/i,matchRgbaRed:1,matchRgbaGreen:2,matchRgbaBlue:3,matchRgbaAlpha:4,patternGradient:/^\\-?(moz|webkit|o|ms|)(?:(?:-|)(?:repeating-|)(linear|radial|))-gradient\\((.*?)\\)(?:;|)$/i,matchGradientPrefix:1,matchGradientType:2,matchGradientValues:3,generatedGradientSyntax:null,matchGradientSyntaxLine:1,matchGradientSyntaxAngle:2,matchGradientSyntaxSideCorner:3,matchGradientSyntaxStopList:4,buildGradientSyntax:function(){var t=[\"(?:to\\\\s+)?((?:\",p.stringPatternSideCorner,\")\\\\s*(?:\",p.stringPatternSideCorner,\")?)\"].join(\"\"),t=[\"(\",[\"(\",p.stringPatternAngle,\")\"].join(\"\"),\"|\",t,\")\"].join(\"\"),e=[p.stringPatternColor,\"\\\\s*(?:\",p.stringPatternValue,\")?\"].join(\"\"),t=[\"^\\\\s*\",t,\"\\\\s*,\\\\s*(\",[\"(?:\",e,\"\\\\s*,\\\\s*)*\",e].join(\"\"),\")\\\\s*$\"].join(\"\");return p.generatedGradientSyntax=new RegExp(t,\"i\"),p.generatedGradientSyntax},generatedRadialGradientSyntax:null,matchRadialGradientSyntaxEllipse:1,matchRadialGradientSyntaxWidth:2,matchRadialGradientSyntaxHeight:3,matchRadialGradientSyntaxXPosition:4,matchRadialGradientSyntaxYPosition:5,matchRadialGradientSyntaxStopList:6,buildRadialGradientSyntax:function(){var t=[\"(ellipse)?\\\\s*\",\"(\",p.stringPatternValue,\")\\\\s+\",\"(\",p.stringPatternValue,\")\\\\s+at\\\\s+\",\"(\",p.stringPatternValue,\")\\\\s+\",\"(\",p.stringPatternValue,\")\"].join(\"\"),e=[p.stringPatternColor,\"\\\\s*(?:\",p.stringPatternValue,\")?\"].join(\"\"),t=[\"^\\\\s*\",t,\"\\\\s*,\\\\s*(\",[\"(?:\",e,\"\\\\s*,\\\\s*)*\",e].join(\"\"),\")\\\\s*$\"].join(\"\");return p.generatedRadialGradientSyntax=new RegExp(t,\"i\"),p.generatedRadialGradientSyntax},generatedGradientStopList:null,matchGradientStopListColor:1,matchGradientStopListPosition:2,buildGradientStopList:function(){var t=[\"\\\\s*(\",p.stringPatternColor,\")\",\"(?:\\\\s+\",\"(\",p.stringPatternValue,\"))?\",\"(?:\\\\s*,\\\\s*)?\"].join(\"\");return p.generatedGradientStopList=new RegExp(t,\"gi\"),p.generatedGradientStopList},gradientOldSideCorner:{\"to top\":\"left bottom, left top\",\"to left\":\"right top, left top\",\"to bottom\":\"left top, left bottom\",\"to right\":\"left top, right top\",\"to top right\":\"left bottom, right top\",\"to top left\":\"right bottom, left top\",\"to bottom right\":\"left top, right bottom\",\"to bottom left\":\"right top, left bottom\"},gradientPrefixedSideCorner:{\"to top\":\"bottom\",\"to left\":\"right\",\"to bottom\":\"top\",\"to right\":\"left\",\"to top right\":\"bottom left\",\"to top left\":\"bottom right\",\"to bottom right\":\"top left\",\"to bottom left\":\"top right\"}};Object.defineProperties(p,{patternGradientSyntax:{get:function(){return null===p.generatedGradientSyntax?p.buildGradientSyntax():p.generatedGradientSyntax}},patternRadialGradientSyntax:{get:function(){return null===p.generatedRadialGradientSyntax?p.buildRadialGradientSyntax():p.generatedRadialGradientSyntax}},patternGradientStopList:{get:function(){return null===p.generatedGradientStopList?p.buildGradientStopList():p.generatedGradientStopList}}}),p.convertAngleToSideCorner=function(t,e){e=((t=/\\-?(moz|webkit|o|ms)/.test(e)?90-t:t)+360)%360,t=\"to top\";return 347<e||e<14?t=\"to top\":14<=e&&e<=75?t=\"to top right\":75<e&&e<105?t=\"to right\":105<=e&&e<=167?t=\"to bottom right\":167<e&&e<194?t=\"to bottom\":194<=e&&e<=255?t=\"to bottom left\":255<e&&e<285?t=\"to left\":285<=e&&e<=347&&(t=\"to left top\"),t},p.convertSideCornerToAngle=function(t,e){var a=0;switch(t){case\"bottom\":case\"to top\":a=0;break;case\"left\":case\"to right\":a=90;break;case\"right\":case\"to left\":a=270;break;case\"top\":case\"to bottom\":a=180;break;case\"to bottom right\":case\"to right bottom\":a=167;break;case\"to bottom left\":case\"to left bottom\":a=194;break;case\"to top right\":case\"to right top\":a=14;break;case\"to top left\":case\"to left top\":a=347;break;case\"bottom right\":case\"right bottom\":a=285;break;case\"bottom left\":case\"left bottom\":a=75;break;case\"top right\":case\"right top\":a=255;break;case\"top left\":case\"left top\":a=105}return((a=/\\-?(moz|webkit|o|ms)/.test(e)?90-a:a)+360)%360},p.convertHexToRgba=function(t,e){var a,r=0,n=0,i=0,t=p.patternHex.exec(t);return null!==t&&t[p.matchHexColor]&&(3==(t=t[p.matchHexColor]).length&&(t=(a=t).substr(0,1)+a.substr(0,1)+a.substr(1,1)+a.substr(1,1)+a.substr(2,1)+a.substr(2,1)),r=parseInt(t.substr(0,2),16),n=parseInt(t.substr(2,2),16),i=parseInt(t.substr(4,2),16)),e=parseInt(e,10)/100,\"rgba(\"+r+\",\"+n+\",\"+i+\",\"+(e=isNaN(e)?1:e)+\")\"},p.isHex=function(t){return p.patternHex.test(t)},p.isHexa=function(t){return p.patternHexa.test(t)},p.isRgb=function(t){return p.patternRgb.test(t)},p.isRgba=function(t){return p.patternRgba.test(t)},p.isGradient=function(t){return p.patternGradient.test(t)},p.parseColorStopList=function(t){for(var e,a=p.patternGradientStopList.exec(t),r=[];null!==a;)e={color:a[p.matchGradientStopListColor]},a[p.matchGradientStopListPosition]&&(e.position=a[p.matchGradientStopListPosition]),r.push(e),a=p.patternGradientStopList.exec(t);return r},p.parseGradient=function(t){var e,a=null,t=p.patternGradient.exec(t);return null!==t&&(a={original:t[0],oldSyntax:!t[p.matchGradientType],prefix:t[p.matchGradientPrefix],type:t[p.matchGradientType],colorStopList:[]},null!==(e=p.patternGradientSyntax.exec(t[p.matchGradientValues]))&&(e[p.matchGradientSyntaxLine]&&(a.line=e[p.matchGradientSyntaxLine]),e[p.matchGradientSyntaxAngle]&&(a.angle=e[p.matchGradientSyntaxAngle]),e[p.matchGradientSyntaxSideCorner]&&(a.sideCorner=e[p.matchGradientSyntaxSideCorner]),a.colorStopList=p.parseColorStopList(e[p.matchGradientSyntaxStopList])),null!==(e=p.patternRadialGradientSyntax.exec(t[p.matchGradientValues]))&&(a.radialGradientEllipse=e[p.matchRadialGradientSyntaxEllipse],e[p.matchRadialGradientSyntaxWidth]&&(a.radialGradientWidth=e[p.matchRadialGradientSyntaxWidth]),e[p.matchRadialGradientSyntaxHeight]&&(a.radialGradientHeight=e[p.matchRadialGradientSyntaxHeight]),e[p.matchRadialGradientSyntaxXPosition]&&(a.radialGradientXPosition=e[p.matchRadialGradientSyntaxXPosition]),e[p.matchRadialGradientSyntaxYPosition]&&(a.radialGradientYPosition=e[p.matchRadialGradientSyntaxYPosition]),a.colorStopList=p.parseColorStopList(e[p.matchRadialGradientSyntaxStopList]))),a},p.createGradient=function(t){var e={backgroundColor:null,webkitGradient:null,mozLinearGradient:null,msLinearGradient:null,oLinearGradient:null,webkitLinearGradient:null,linearGradient:null,radialGradient:null},a=p.parseGradient(t)||t;if(a.oldSyntax)throw new Error('[Not supported] Can not create gradient from old \"webkit-gradient\" syntax.');for(var r,n,i,o,s=\"\",d=\"\",l=a.colorStopList.length,c=0;c<l;c++){var g=a.colorStopList[c];g.color&&(0!==c?(s+=\",\",d+=\",\"):e.backgroundColor=g.color,s+=g.color,d+=\"color-stop(\",g.position?(s+=\" \"+g.position,d+=g.position):d+=100/(l-1)*c+\"%\",d+=\", \"+g.color+\")\")}return\"linear\"===a.type?(void(t=0)!==a.angle?t=parseInt(a.angle,10):void 0!==a.sideCorner&&(t=p.convertSideCornerToAngle(a.line,a.prefix)),n=t,/\\-?(moz|webkit|o|ms)/.test(a.prefix)?t=90-n:n=90-t,r=p.convertAngleToSideCorner(t),r=p.gradientOldSideCorner[r],e.webkitGradient=\"-webkit-gradient(linear, \"+r+\", \"+d+\")\",e.mozLinearGradient=\"-moz-linear-gradient(\"+n+\"deg, \"+s+\")\",e.msLinearGradient=\"-ms-linear-gradient(\"+n+\"deg, \"+s+\")\",e.oLinearGradient=\"-o-linear-gradient(\"+n+\"deg, \"+s+\")\",e.webkitLinearGradient=\"-webkit-linear-gradient(\"+n+\"deg, \"+s+\")\",e.linearGradient=\"linear-gradient(\"+t+\"deg,\"+s+\")\"):\"radial\"===a.type&&(r=(r=(a.radialGradientEllipse||\"\")+\" \").replace(/^\\s+/,\"\"),n=parseInt(a.radialGradientWidth,10),t=parseInt(a.radialGradientHeight,10),i=parseInt(a.radialGradientXPosition,10),o=parseInt(a.radialGradientYPosition,10),e.radialGradient=\"radial-gradient(\"+r+(n+\"% \"+t+\"%\"+\" at \"+(i+\"% \"+o+\"%\"))+\",\"+s+\")\"),e},p.createGradientStyle=function(t){var e,a=p.createGradient(t),r=\"\";for(e in a){var n=a[e];null!==n&&p.isGradient(n)&&(r+=\"background-image: \"+n+\"; \")}return r},p.parseRgb=function(t){var e,a=0,r=0,n=0,i=1;if(p.isRgb(t))e=p.patternRgb.exec(t);else{if(!p.isRgba(t))throw new Error(\"Color format [\"+t+\"] is not supported, please provide rgb or rgba color format input.\");e=p.patternRgba.exec(t)}return null!==e&&(e[p.matchRgbaRed]&&(a=parseInt(e[p.matchRgbaRed],10)),e[p.matchRgbaGreen]&&(r=parseInt(e[p.matchRgbaGreen],10)),e[p.matchRgbaBlue]&&(n=parseInt(e[p.matchRgbaBlue],10)),e[p.matchRgbaAlpha]&&(i=parseFloat(e[p.matchRgbaAlpha]))),[a,r,n,i]},p.tint=function(t,e){return!0!==this.isGradient(t)?this.tintRgb(t,e):this.tintGradient(t,e)},p.shade=function(t,e){return!0!==this.isGradient(t)?this.shadeRgb(t,e):this.shadeGradient(t,e)},p.tintGradient=function(t,e){return this._transformGradient(t,e,255)},p.shadeGradient=function(t,e){return this._transformGradient(t,e,0)},p.shadeRgb=function(t,e){return this._transformRgb(t,e,0)},p.tintRgb=function(t,e){return this._transformRgb(t,e,255)},p._transformGradient=function(t,r,n){if(null===(t=this.parseGradient(t)))return null;t.colorStopList=t.colorStopList.map(function(t){var e=\"\";switch(!0){case this.isHex(t.color)||this.isHexa(t.color):var a=this.convertHexToRgba(t.color),e=this._transformRgb(a,r,n);break;case this.isRgba(t.color)||this.isRgb(t.color):e=this._transformRgb(t.color,r,n)}return Object.assign({},t,{color:e})},this);var e=this.createGradient(t);switch(t.type){case\"linear\":return e.linearGradient;case\"radial\":return e.radialGradient;default:return null}},p._transformRgb=function(t,e,a){var t=p.parseRgb(t),r=t[0],n=t[1],i=t[2],t=t[3];return\"rgba(\"+parseInt(r*(1-e)+a*e)+\",\"+parseInt(n*(1-e)+a*e)+\",\"+parseInt(i*(1-e)+a*e)+\",\"+t+\")\"},t.Color=p}(window),\"undefined\"!=typeof module&&void 0!==module.exports&&(module.exports=window.Color);;\n!function(){function t(t,e){for(var i in this._video=t,this._lastObservedDuration=null,this._lastObservedPosition=null,this._intervalId=null,this._firstSegmentEmitted=!1,this._running=!1,this._currentTime=0,this._playedSegmentsCount=0,this.stop=this.stop.bind(this),this.stopAndReset=this.stopAndReset.bind(this),this.start=this.start.bind(this),this.tick=this.tick.bind(this),this.flush=this.flush.bind(this),this.reemitQuartileEvent=function(t){this.emit(t.name,t),this._playedSegmentComputationUnit.flush()}.bind(this),this.trackDurationChange=this.trackDurationChange.bind(this),this.stitchAndTrackVideoPlayedSegment=this.stitchAndTrackVideoPlayedSegment.bind(this),this._viewDirectionsObserver=\"function\"==typeof ViewDirectionsObserver&&this._video instanceof VideoPlayer360?new ViewDirectionsObserver(t,this.getCurrentTime.bind(this)):null,this._playedSegmentComputationUnit=new PlayedSegmentComputationUnit(1,this.stitchAndTrackVideoPlayedSegment),this._lastVideoPlayedSegment={from:0,to:0},this._serverReportedDuration=t.serverReportedDuration,t.serverReportedDuration?(defer(function(){this.emit(\"videoDurationUpdate\",{duration:t.serverReportedDuration})}.bind(this)),this.checkDuration=!1):(this.checkDuration=!0,t.on(\"durationchange\",this.trackDurationChange)),t.on(\"pause\",this.stop),t.on(\"timeupdate\",this.start),t.on(\"ended\",this.stopAndReset),t.on(\"repeat\",this.stopAndReset),t.on(\"playing\",this.start),t.on(\"seeked\",this.start),s().on(\"mediaStopRequested\",this.stop),t.on(\"muted\",this.flush),t.on(\"unmuted\",this.flush),t.on(\"enterfullscreen\",this.flush),t.on(\"exitfullscreen\",this.flush),t.QUARTILE_EVENTS)t.on(i,this.reemitQuartileEvent)}var n=[void 0,null,0,1],r=[100,300,6e3],s=(extend(t.prototype,EventEmitter),function(){return\"undefined\"!=typeof creative?creative.adapter:adapter});t.prototype.stitchAndTrackVideoPlayedSegment=function(t){t={from:this._lastVideoPlayedSegment.to,to:t.to};this.trackVideoPlayedSegment(t)},t.prototype.trackVideoPlayedSegment=function(t){var e;this._lastVideoPlayedSegment=t,this._serverReportedDuration&&t.from>=this._serverReportedDuration||(e=!this._serverReportedDuration||t.to<this._serverReportedDuration?t.to:this._serverReportedDuration,t=extend({},t,{to:e,name:\"videoPlayedSegment\",muted:!!this._video.muted,fullscreen:void 0===this._video.fullscreen||this._video.fullscreen,gaze:this._video.isUserGazing()}),this._viewDirectionsObserver&&(t.viewDirections=this._viewDirectionsObserver.getDirections()),this.emit(\"videoPlayedSegment\",t),this._playedSegmentsCount+=1)},t.prototype.getCurrentTime=function(){return this._video&&this._video.currentTime>this._currentTime&&(this._currentTime=this._video.currentTime),this._currentTime},t.prototype.start=function(){this._running||(this._running=!0,this._viewDirectionsObserver&&this._viewDirectionsObserver.start(),s().mediaState.startVideo(),this._firstSegmentEmitted||(this._currentTime=this.getCurrentTime()+.01,this.trackVideoPlayedSegment({from:0,to:this.getCurrentTime()}),this._firstSegmentEmitted=!0),this._playedSegmentComputationUnit.init(this.getCurrentTime()),clearInterval(this._intervalId),this._intervalId=setInterval(this.tick,250))},t.prototype.stopAndReset=function(){this.stop(!0),this._playedSegmentComputationUnit&&(this._playedSegmentComputationUnit.reset(),this._currentTime=0,this._firstSegmentEmitted=!1,this._viewDirectionsObserver&&this._viewDirectionsObserver.reset())},t.prototype.flush=function(){this._playedSegmentComputationUnit&&this._playedSegmentComputationUnit.flush()},t.prototype.stop=function(t){(this._running||t)&&(this._running&&(s().mediaState.stopVideo(),this._running=!1,clearInterval(this._intervalId)),this._viewDirectionsObserver&&this._viewDirectionsObserver.stop(),this._playedSegmentComputationUnit.addEvent(this.getCurrentTime()),this._playedSegmentComputationUnit.flush(t))},t.prototype.trackDurationChange=function(){var t,e,i,s;this.checkDuration&&(t=this._video.duration,e=-1!==n.indexOf(t),i=-1!==r.indexOf(t),s=t!==this._lastObservedDuration,e||i||!s||(this._serverReportedDuration=t,this.emit(\"videoDurationUpdate\",{duration:t}),this._lastObservedDuration=t))},t.prototype.tick=function(){this.trackDurationChange();var t=this.getCurrentTime(),e=Date.now()-(this._lastTickTime||Date.now());t==this._lastObservedPosition&&1e3<e&&this.stop(),this._playedSegmentComputationUnit.addEvent(t),this._lastObservedPosition=t,this._lastTickTime=Date.now()},t.prototype.destroy=function(){var t,e=this._video;for(t in this.stop(),e.off(\"pause\",this.stop),e.off(\"ended\",this.stopAndReset),e.off(\"playing\",this.start),e.off(\"timeupdate\",this.start),e.off(\"durationchange\",this.tick),e.off(\"muted\",this.flush),e.off(\"unmuted\",this.flush),e.off(\"enterfullscreen\",this.flush),e.off(\"exitfullscreen\",this.flush),e.off(\"durationchange\",this.trackDurationChange),e.QUARTILE_EVENTS)e.off(t,this.reemitQuartileEvent);s().off(\"mediaStopRequested\",this.stop),this._playedSegmentComputationUnit.flush()},this.VideoEventsAdapter=t}();;\n!function(){var o=[void 0,null,0,1];var i={videoStart:function(n,t,i){return t>Math.min(1,.25*i)},videoFirstQuartile:function(n,t,i){i*=.25;return n<i&&i<=t},videoMidpoint:function(n,t,i){i*=.5;return n<i&&i<=t},videoThirdQuartile:function(n,t,i){i*=.75;return n<i&&i<=t},videoComplete:function(n,t,i){return t>Math.max(.75*i,i-2)}};window.QuartileEventsEmitter=function(n){n.QUARTILE_EVENTS=i;var r={},u=(Object.keys(i).forEach(function(n){r[n]=i[n]}),null),t=function(){var n,t=null===u||null!==u&&this.currentTime>u?this.currentTime:u,i=(n=this).serverReportedDuration||(-1!==o.indexOf(n.duration)?null:n.duration);if(null!==i&&null!==u&&t&&t-u<1)for(var e in 0<u&&u<1&&(u=0),r)r[e](u,t,i)&&(this.emit(e,{name:e,label:this.componentName}),delete r[e]);u=t}.bind(n);return n.on(\"timeupdate\",t),n.on(\"destroy\",function(){n.off(\"timeupdate\",t)}),n}}();;\n!function(){var o={};new Image;o.ZINDEX_MAX=2147483647,o.LANDSCAPE=1,o.PORTRAIT=2,o.ENDED=0,o.PLAYING=1,o.PAUSED=2,o.BUFFERING=3,o.VIDEO_CUED=5,o.UNSTARTED=6,o.SEEKING=7,o.STATE={0:\"ENDED\",1:\"PLAYING\",2:\"PAUSED\",3:\"BUFFERING\",5:\"VIDEO_CUED\",6:\"UNSTARTED\",7:\"SEEKING\"},o.TRUTHY_REGEX=/^(yes|true|1)$/i,o.isTruthy=function(t){return o.TRUTHY_REGEX.test(t)},o.isTouchDevice=!!(\"ontouchstart\"in window),o.validFor=function(t,e,n){var i=Date.now()+e;return function(){Date.now()<=i&&t.apply(n||null,arguments)}},o.exists=function(t,e){return-1!==t.indexOf(e)},o.lastCall=0,o.deduplicate=function(t,e,n){return function(){Date.now()-o.lastCall>e&&(t.apply(n,arguments),o.lastCall=Date.now())}},o.isFunction=function(t){return\"function\"==typeof t},o.constantly=function(t){return function(){return t}},o.hash=function(){for(var t=Array.prototype.slice.apply(arguments),e=0,n=0;n<t.length;n+=1)e+=1e3*t[n]+e<<1;return e},o.curry=Function.prototype.curry||function(){var t=this,e=Array.prototype.slice.call(arguments);return function(){return t.apply(this,e.concat(Array.prototype.slice.call(arguments)))}},o.offset=function(t,e){var t=t.getBoundingClientRect()||{top:0,left:0},e=e||document,n=e.documentElement,e=e.defaultView;return{top:t.top+(e.pageYOffset||n.scrollTop)-(n.clientTop||0),left:t.left+(e.pageXOffset||n.scrollLeft)-(n.clientLeft||0)}},o.attachHandlers=function(e,n,t){for(var i in t){var r=isArray(t[i])?t[i]:[t[i]];r.forEach(function(t){t=e[t];t&&n.on(i,t.bind(e))})}},o.idempotented=function(e,n){var i=!1;if(o.isFunction(e))return function(){var t;i||(t=Array.prototype.slice.call(arguments),i=!0,e.call(n||window,t))};throw\"idempotented called with no function.\"},o.delayed=function(e,n,i){var r=null;return function(){var t=arguments;clearTimeout(r),r=setTimeout(function(){e.apply(i,t)},n)}},o.swapClass=function(t,e,n){hasClass(t,e)?(removeClass(t,e),addClass(t,n)):(removeClass(t,n),addClass(t,e))},o.memoize=function(r){return function(){var t,e=Array.prototype.slice.call(arguments),n=\"\",i=e.length;for(r.memoize=r.memoize||{};i--;)n+=(t=e[i])===Object(t)?JSON.stringify(t):t,r.memoize||(r.memoize={});return n in r.memoize?r.memoize[n]:r.memoize[n]=r.apply(this,e)}},o.toMMSS=function(t){var e=Math.floor(t/36e5),n=Math.floor((t-36e5*e)/60),t=Math.round(t-36e5*e-60*n);return(n=n<10?\"0\"+n:n)+\":\"+(t=t<10?\"0\"+t:t)},o.fitComponent=function(t,e,n,i,r){var o,a,l,c=0<1-n/i*(e/t),r=(!!r?!c:c)?(o=n,l=0,i-(a=Math.ceil(e*(n/t)))):(a=i,l=n-(o=Math.ceil(t*(i/e))),0);return{width:Math.ceil(o),height:Math.ceil(a),marginHorizontal:l>>1,marginVertical:r>>1}},o.removeUnits=function(t){return parseInt(t.replace(/[a-z]+/,\"\"))},o.capitaliseFirstLetter=function(t){return t.charAt(0).toUpperCase()+t.slice(1)},o.createHandlerName=function(t,e){return(e=e||\"on\")+o.capitaliseFirstLetter(t)},o.forEach=function(t,e,n){for(var i in t)e.call(n,t[i],i,t)},o.changeStyle=function(t,e,n){for(var i,r=0;r<n.length;r+=1)(i=n[r])&&(i.style[t]=e)},o.show=function(){o.changeStyle(\"display\",\"\",arguments)},o.hide=function(){o.changeStyle(\"display\",\"none\",arguments)},o.showCursor=function(t){o.changeStyle(\"cursor\",\"\",t)},o.hideCursor=function(t){o.changeStyle(\"cursor\",\"none\",t)},o.removeElements=function(){for(var t=0;t<arguments.length;t+=1){var e=arguments[t];e&&e.parentNode&&e.parentNode.removeChild(e)}},o.removeChildren=function(t){for(;t.firstChild;)t.removeChild(t.firstChild)},o.isNode=function(t){return\"object\"==typeof Node?t instanceof Node:t&&\"object\"==typeof t&&\"number\"==typeof t.nodeType&&\"string\"==typeof t.nodeName},o.urlSerialize=function(t){var e,n=[];for(e in t)n.push(encodeURIComponent(e)+\"=\"+encodeURIComponent(t[e]));return n.join(\"&\")},o.composeUrl=function(){for(var t=[],e=null,n=!1,i=0;i<arguments.length;i+=1){var r=arguments[i]||\"\";if(\"object\"==typeof r){e=o.urlSerialize(r);break}n=0<(r=\"function\"==typeof r?r()||\"\":r).length&&\"/\"==r[r.length-1],r=(0===i?r:r.replace(/^\\//,\"\")).replace(/\\/$/,\"\");r&&t.push(r)}return t.join(\"/\")+(n?\"/\":\"\")+(e?\"?\"+e:\"\")},o.xBindFactory=function(n){return function(t){var e=t.getAttribute(\"x-bind\");e&&(n[e]=t).removeAttribute(\"x-bind\")}},o.createDom=function(t,e,n,r){t=t.createElement(\"div\"),r=r||noop;return t.innerHTML=tmpl(\"string\"==typeof e?e:e.join(\"\"),n||{}),function t(e){for(var n=e.children||[],i=0;i<n.length;i+=1)t(n[i]);return r(e),e}(t).children[0]},o.createStyleTag=function(t,e){t=t.createElement(\"link\");return t.setAttribute(\"rel\",\"stylesheet\"),t.setAttribute(\"href\",e),t},o.insertStyleTag=function(t,e,n){var i=n.getElementsByTagName(\"head\")[0];n.getElementById(t)||((n=document.createElement(\"style\")).setAttribute(\"id\",t),n.innerHTML=e,i.appendChild(n))},o.createSourceTag=function(t,e){t=t.createElement(\"source\");return t.setAttribute(\"src\",e),t},o.hasValue=function(t){return null!=t&&NaN!==t&&\"\"!==t},o.filterObject=function(t,e){var n,i={};for(n in t)t.hasOwnProperty(n)&&(e&&e(t[n])||t[n])&&(i[n]=t[n]);return i},o.complement=function(t){return function(){return!t.apply(this,arguments)}},o.EMPTY_PIXEL=\"R0lGODlhAQABAPAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==\",(this.CeltraPlayerUtils=o).forEach=function(t,e,n){if(t.forEach)t.forEach(e,n);else for(var i in t){var r=t[i];e.call(n,i,r)}},o.isTouchDevice=!!(\"ontouchstart\"in window),o.isTopNode=function(t){return\"BODY\"===t.tagName||\"viewport\"===t.id||\"celtra-modal\"===t.id},o.orientationLockHandler=function(t,e){var n=t.getControlsElement(),i=t.getWrapperElement();i&&(e?(t.isState(CeltraPlayerUtils.PLAYING)?t.__playAfterRotate=!0:t.__playAfterRotate=!1,t.pause(),n&&(n.style.display=\"none\"),i.style.zIndex=-1):(n&&(n.style.display=\"\"),t.__playAfterRotate&&t.play(),i.style.zIndex=CeltraPlayerUtils.ZINDEX_MAX))},o.construct=function(t,e){function n(){return t.apply(this,e)}return n.prototype=t.prototype,new n},o.getUrl=function(t){var t=(!!creative.runtimeParams.secure?t:\"insecure\"+o.capitaliseFirstLetter(t))+\"Url\",e=(\"undefined\"!=typeof creative?creative:urls)[t];if(e)return e;throw\"Undefined url key \"+t},o.ifDefined=function(t,e){return void 0!==t?t:e},o.base64ToArrayBuffer=function(t){for(var e=window.atob(t),n=e.length,i=new Uint8Array(n),r=0;r<n;r++){var o=e.charCodeAt(r);i[r]=o}return i.buffer},o.createSourceObject=function(t,e){return{url:t,mediaType:e}},o.setMediaElementSource=function(n,i,t){var r;1===t.length?i.src=t[0].url:(r=[].slice.apply(i.childNodes||[]).map(function(t){return t.src}),t.forEach(function(t){var e;-1<r.indexOf(t.url)||((e=n.createElement(\"source\")).src=t.url,e.type=o.generateMediaType(t.mediaType),i.appendChild(e))}))},o.generateMediaType=function(t){if(!t||!t.mime)return\"\";if(creative.runtimeParams.featureFlags.iOS17InlineVideoFix&&deviceInfo.os.ios(\"17\",null))return t.mime;var e=[(e=t.codecs||{}).video,e.audio].filter(function(t){return!!t}).join(\", \");return t.mime+(e?'; codecs=\"'+e+'\"':\"\")}}(),function(){this.StatefulEventEmitter={changeState:function(t){var e=this._state;this._state!=t&&(this._state=t,this.emit(\"statechange\",t,e))},getState:function(){return this._state},isState:function(){return-1!==Array.prototype.slice.apply(arguments).indexOf(this._state)}},extend(this.StatefulEventEmitter,EventEmitter)}(),function(){var r=-1!==navigator.userAgent.indexOf(\"Safari\")&&-1===navigator.userAgent.indexOf(\"CriOS\");function t(t,e,n){this.intervalId=null,this.containerEl=null,this.externalResize=noop,this.lastWidth=0,this.lastHeight=0,this.lastScrollX=0,this.lastScrollY=0,this.doc=t.ownerDocument,this.win=\"defaultView\"in this.doc?this.doc.defaultView:this.doc.parentWindow,this.initialHostOffset={left:this.win.pageXOffset||document.documentElement.scrollLeft,top:this.win.pageYOffset||document.documentElement.scrollTop},this.containerEl=t;var i=this.containerEl.style;i.position=n?\"fixed\":\"absolute\",i.zIndex=CeltraPlayerUtils.ZINDEX_MAX-1,i.width=\"100%\",i.height=\"100%\",i.left=\"0px\",i.top=\"0px\",e||(i.background=\"#000\"),this.containerEl.addEventListener(\"touchmove\",o),this.containerEl.addEventListener(\"touchstart\",o),this.win.document.body.appendChild(this.containerEl),this.resize=function(){var t=this.containerEl.style;t.width=this.lastWidth+\"px\",t.height=this.lastHeight+\"px\",t.zIndex=CeltraPlayerUtils.ZINDEX_MAX,this.emit(\"resize\",{width:this.lastWidth,height:this.lastHeight,scrollX:this.lastScrollX,scrollY:this.lastScrollY})}.bind(this),this.tick=function(){window===(\"undefined\"==typeof adapter?creative.adapter:adapter).getTopWindow()&&(this.containerEl.style.width=\"100%\",this.containerEl.style.height=\"100%\");var t=Math.max(this.win.document.documentElement.clientWidth,this.win.innerWidth||0),e=Math.max(this.win.document.documentElement.clientHeight,this.win.innerHeight||0);(this.lastWidth!==t||this.lastHeight!==e||ios(\"7\")&&r)&&(this.lastWidth=t,this.lastHeight=e,defer(this.resize))}.bind(this),this.intervalId=setInterval(this.tick,60),new Tapper(t)}function o(t){var e=null!=typeof adapter?adapter:creative.adapter;\"touchstart\"==t.type&&e.useNativeClickForTapDetection||t.preventDefault(),t.stopPropagation()}extend(t.prototype,EventEmitter),t.prototype.getElement=function(){return this.containerEl},t.prototype.destroy=function(){this.containerEl&&(this.containerEl.removeEventListener(\"touchmove\",o),this.containerEl.removeEventListener(\"touchstart\",o),clearInterval(this.intervalId),this.containerEl.innerHTML=\"\")},window.FullScreenHandler=t}();;\n!function(){function t(t,i){this.maxPlayingSegmentLength=t,this.callback=i,this.reset()}t.prototype.init=function(t){this._startPlayingPosition=t,this._lastPlayingPosition=t},t.prototype.addEvent=function(t){t>this._lastPlayingPosition&&(this._lastPlayingPosition=t),this._lastPlayingPosition-this._startPlayingPosition>=this.maxPlayingSegmentLength&&this.flush()},t.prototype.flush=function(t){var i,n;null!=this._startPlayingPosition&&(i=this._startPlayingPosition,n=this._lastPlayingPosition,(t||.4<n-i)&&i<n&&(this.callback({from:i,to:n}),this._startPlayingPosition=this._lastPlayingPosition))},t.prototype.reset=function(){this._startPlayingPosition=null,this._lastPlayingPosition=null},window.PlayedSegmentComputationUnit=t}();;\n!function(t){function s(t){this._player=t,this._startClock=this._startClock.bind(this),this._stopClock=this._stopClock.bind(this),this.update=this.update.bind(this),this._emitVideoDurationUpdate=this._emitVideoDurationUpdate.bind(this),this._duration=t.getDuration(),this._completionMarker=Math.max(0,this._duration-s.COMPLETION_WINDOW_LENGTH),this._uniqueSecondsPlayedList=[],this._timeUpdateIntervalId=0,this._last=this._initState(),this._quartileDurations={start:0,firstQuartile:.25*this._duration,midpoint:.5*this._duration,thirdQuartile:.75*this._duration,complete:this._completionMarker},t.addEventListener(\"timeupdate\",this.update),t.addEventListener(\"playing\",this._startClock),t.addEventListener(\"ended\",this._stopClock)}extend(s.prototype,EventEmitter),s.COMPLETION_WINDOW_LENGTH=.5,s.MAX_INTERPOLATION_GAP=5,s.prototype._initState=function(){return{playingSecond:0,inCompletionWindow:!1}},s.prototype._startClock=function(){this._timeUpdateIntervalId||(this._duration=this._player.getDuration(),this._emitVideoDurationUpdate(this._duration),this._emitVideoDurationUpdate=noop,this._timeUpdateIntervalId=setInterval(this.update.bind(this),250),this.update())},s.prototype._stopClock=function(){this._timeUpdateIntervalId&&clearInterval(this._timeUpdateIntervalId),this._timeUpdateIntervalId=0},s.prototype.update=function(t){Date.now();if(0<=(t=t||this._player.getCurrentTime())){var i=(t=t>this._duration?this._duration:t)>=this._completionMarker,e=(this._last.inCompletionWindow&&!i&&(this._last=this._initState()),Math.floor(t)+1);if(this._last.playingSecond!=e){var a=this._last.playingSecond>e?0:this._last.playingSecond;if(a+1<e)for(var n=Math.max(e-s.MAX_INTERPOLATION_GAP,a+1);n<e;n++)this._markAndEmitSecondPlayed(n,t);this._markAndEmitSecondPlayed(e,t)}a=this._uniqueSecondsPlayedList.length-1+(t-Math.floor(t));this._markAndEmitQuartilesForDuration(a),this._last.playingSecond=e,this._last.inCompletionWindow=i}},s.prototype._markAndEmitSecondPlayed=function(t,i){i={second:t,position:i,gaze:this._player.isUserGazing&&this._player.isUserGazing(),viewDirections:this._player.getViewDirections?this._player.getViewDirections():void 0};-1==this._uniqueSecondsPlayedList.indexOf(t)?(this._uniqueSecondsPlayedList.push(t),this.emit(\"secondPlayed\",i)):this.emit(\"secondReplayed\",i)},s.prototype._markAndEmitQuartilesForDuration=function(t){for(var i in this._quartileDurations)t>=this._quartileDurations[i]&&(delete this._quartileDurations[i],this.emit(i,t))},s.prototype._emitVideoDurationUpdate=function(t){this.emit(\"videoDurationUpdate\",t)},t.PlaybackTrackingEventsEmitter=s}(this);;\n!function(t){function i(t,i,e){this._context=i,this._prefix=e||\"video\",[\"start\",\"firstQuartile\",\"midpoint\",\"thirdQuartile\",\"complete\"].forEach(function(i){t.on(i,function(t){this._context.track({name:this._prefix+ucfirst(i),position:t}),\"start\"===i&&creative.trackCreativeRenderedOnVideoStart()}.bind(this))},this),[\"secondPlayed\",\"secondReplayed\"].forEach(function(i){t.on(i,function(t){this._context.track(extend({name:this._prefix+ucfirst(i)},t))}.bind(this))},this),t.on(\"videoDurationUpdate\",function(t){this._context.track({name:this._prefix+ucfirst(\"videoDurationUpdate\"),duration:t})}.bind(this))}extend(i.prototype,EventEmitter),i.prototype.changeContext=function(t){this._context=t},t.PlaybackTrackingEventsRecorder=i}(this);;\n!function(){var A=[\".video-player-wrapper {\",\"    position: absolute;\",\"    width: 100%;\",\"    height: 100%;\",\"    background: #000;\",\"    top: 0;\",\"    left: 0;\",\"    overflow: hidden;\",\"}\",\".video-player-engine {\",\"    position: absolute;\",\"    width: 100%;\",\"    height: 100%;\",\"    min-height: 100%;\",\"    top: 0;\",\"    left: 0;\",\"    margin: 0;\",\"    padding: 0;\",\"    overflow: hidden;\",\"}\",\".video-player-engine video,\",\".video-player-engine .canvasContainer\",\"{\",\"    position: relative;\",\"    width: 100%;\",\"    height: 100%;\",\"    min-height: 100%;\",\"    background: #000;\",\"    top: 0;\",\"    left: 0;\",\"}\",\".video-player-poster {\",\"    position: absolute;\",\"    background-size: contain;\",\"    background-repeat: no-repeat no-repeat;\",\"    background-position: center center;\",\"}\",\".video-player-fitting-crop .video-player-poster {\",\"    background-size: cover;\",\"}\",\".video-player-engine canvas {\",\"    position: relative;\",\"    width: 100%;\",\"    background: #000;\",\"    top: 0;\",\"    left: 0;\",\"}\",\".video-player-engine canvas {\",\"    image-rendering: optimizeSpeed;\",\"    image-rendering: -moz-crisp-edges;\",\"    -ms-interpolation-mode: nearest-neighbor;\",\"    image-rendering: optimize-contrast;\",\"    image-rendering: -webkit-pixelated;\",\"    image-rendering: crisp-edges;\",\"    image-rendering: -webkit-optimize-speed;\",\"    image-rendering: -webkit-optimize-contrast;\",\"}\",\".video-player-wrapper-empty {\",\"    background-color: #000;\",\"}\",\".video-player-wrapper-empty::after {\",'    content: \"\";',\"    position: absolute;\",\"    top: 0;\",\"    left: 0;\",\"    right: 0;\",\"    bottom: 0;\",'    background-image: url(\"data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyOCAyOCIgd2lkdGg9IjI4IiBoZWlnaHQ9IjI4Ij48cGF0aCBmaWxsPSIjY2NjIiBkPSJNMSAxNGMwIDcuMiA1LjggMTMgMTMgMTNzMTMtNS44IDEzLTEzUzIxLjIgMSAxNCAxIDEgNi44IDEgMTR6bTI0IDBjMCA2LjEtNC45IDExLTExIDExUzMgMjAuMSAzIDE0IDcuOSAzIDE0IDNzMTEgNC45IDExIDExem0tMTQtNHY4bDctNC03LTR6Ii8+PC9zdmc+\");',\"    background-repeat: no-repeat;\",\"    background-position: center;\",\"}\",\".video-controls-container {\",\"    position: absolute;\",\"    width: 100%;\",\"    height: 100%;\",\"    top: 0;\",\"    left: 0;\",\"    font-size: 14px;\",\"}\",\".video-basic-controls-wrapper {\",\"    position: absolute;\",\"    width: 100%;\",\"    height: 100%;\",\"}\",\".video-controls-slider-container {\",\"    position: absolute;\",\"    width: 100%;\",\"    height: 8px;\",\"    bottom: -1px;\",\"    left: 0;\",\"    background: #111;\",\"}\",\".video-controls-progress-bar {\",\"    opacity: 0.3;\",\"}\",\".video-controls-progress-bar,\",\".video-controls-time-bar {\",\"    position: absolute;\",\"    width: 0;\",\"    top: 0;\",\"    left: 0;\",\"    height: 100%;\",\"}\",\"@-webkit-keyframes video-player-spin{\",\"    0%   { -webkit-transform: rotate(0deg); }\",\"    100% { -webkit-transform: rotate(359deg); }\",\"}\",\"@-moz-keyframes video-player-spin{\",\"    0%   { -moz-transform: rotate(0deg); }\",\"    100% { -moz-transform: rotate(359deg); }\",\"}\",\"@keyframes video-player-spin{\",\"    0%   { transform: rotate(0deg); }\",\"    100% { transform: rotate(359deg); }\",\"}\",\".video-player-spinner-big,\",\".video-player-spinner-small {\",\"    display: block;\",\"    position: absolute;\",\"    top: 50%;\",\"    left: 50%;\",\"    -webkit-animation: video-player-spin 1.5s infinite linear;\",\"    animation: video-player-spin 1.5s infinite linear;\",\"}\",\".video-player-spinner-big {\",\"    width: 35px;\",\"    height: 35px;\",\"    margin-top: -17px;\",\"    margin-left: -17px;\",'    background-image:url(\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACMAAAAjCAQAAAC00HvSAAADTklEQVR4AZWVTWgcZRyHn/c/7zubbDbJJsTEioiIH7EGq6EIRqwWqbRClXrx4E0QxYt3b4qK9qgoePDmwRYVS6xFW7Ueeqj0kqIVQYwljTVt0o/dZD9n3r/zshGSGpOd54GZ0+9h/qcxbMh03PfK4LPl8cFy0SJsialxIyfHRz+6Y6pkDR0xXWQ8azlaGvt0fF9sMsiTsazh2933fzEyKICu2+bKfP/izg96IlDA6JXmhdPVY81T6Xk2wAuCz1zFTNOh+PKD71sRMIZqY3aa59iERYtktvDrMm735LGCNQaE38/O7WGRzRBCJljrhCyB0l2HXKSgxpw7zt4xNmcBZNUSlU4XGPl4aEhRVc595fd6tvKm8LAEY4qrR0V3T87YyGDMn2d4mC6ZL9JDmEm6gBcYPhiJqlJdnt8/T7dSQ0JEhQEQ4m2Ph4jq3GdDi0N0Lw21arHSL0jP84WCZlQqV1+6Sh5ZRoix9PiiLe1TBbj0w4gnJ4v1qBeQqN8W79HQYeU78tPAIqBFWxwNFbR1mvy0iMEY+qyNvRK4SH68cQAGiaz64MTCBHndkRhngr3SbnYyM6Mz5NYah81EWnXN8D66OSKvxBrjMpH6X+ozNd4Rk1dKxgW1KbVfskpGYapAXqNRXGakdal/o1kHX95VJq/RbcYFWZLGkVY1fE+hvPT6Enmc2y5lnIk0TS8LraXjeM0ceqY6UKVrrbsvRHD+gvViqbyTtvDex4PD7w3Tre4xUzYOhyS/JUiCP3/pkPd49eVH2m+36cbqLrcdR2Rs8qutWcRiqbxRm9c0HDZywLxp2MrGE/FDxoo1VivJTEKCmSYQPXDrJ+LIQppeO3n9NZb5P+LC0+5eEm2Tar12mCvr/lPxU9veNRIy6luXK0f4kA1I9hR2Sp9PSLJIo/4lsxAwR/kX++TYWyZWT6qZrb9rp9pnkj/8dSqUon5zi73TTUhJs4QGV+qfh8h/MmAmRg7GYyGiXpPMlLbP3rS1Y6oJLU3Upxebh1lYs/yataQ9pVcH9hsJqdVMmHYS7ZAm0Ubjx9YJEtZgprmRaKz3hb5HpTeESHyIZYFOIq00f2qe4BqBTTMdrJuKJ+3t0bAUsH7FV5KFZLb9c3oWzwb8AzjvQbz9lli3AAAAAElFTkSuQmCC\");',\"}\",\".video-player-spinner-small {\",\"    width: 18px;\",\"    height: 18px;\",\"    margin-top: -9px;\",\"    margin-left: -9px;\",'    background-image:url(\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABIAAAASCAQAAAD8x0bcAAABeElEQVR4AXXSv2sTYRjA8e89997lcuR60eJQUdpFQQoqdHRWhAoddPBPcHRwE8TZxcXJf0BEsIjuQUQQtZMOpREM9RKL1RrqJbkf771nEGnKJf1+hmd5eKaHSe/vtzf30k63Q9X/8XZte6f8V/QrokopILq9/CB0+nnUip/l7zioUJgxrBbu6rnnodvp9G6wwaG04AEJRvAWHjXcr+3tC3pDcxhmzGcOpH7rxOm9uHfN3repIkMkwJPguljfXwVbAdOaBm3V7VD8pVE2fDpkNhJcQqXqOi+2OKrxJRxVFkXB0Ql1tKS/QZaE2fAkQGS4KbZ3xWM2+xRzpDJYz5Pw6mBxwLQ/vlq2XLMj6ZP9T7VjzYfGN1Qo97IslD9NV8j6d0e7wUrzsSwKExyv3XTOl1n2AWO1AHdt/p4Kkm/xy/xN2cMwb591Lkqj+JGs0wbrNQCy0rjjnQHdN7EZkYOJs8/ZC7pwsASgVp1L6qRVK9NiV3/RHyc/8RftSMiO8WZLaAAAAABJRU5ErkJggg==\");',\"}\",\".video-controls-hiddable,\",\".video-controls-unhiddable {\",\"    width: 100%;\",\"    height: 100%;\",\"}\",\".video-basic-controls-wrapper .center-button {\",\"    position: absolute;\",\"    width: 4em;\",\"    height: 4em;\",\"    border-top-left-radius: 2em;\",\"    border-top-right-radius: 2em;\",\"    border-bottom-left-radius: 2em;\",\"    border-bottom-right-radius: 2em;\",\"    background: #111;\",\"    margin: auto;\",\"    top: 0;\",\"    left: 0;\",\"    bottom: 0;\",\"    right: 0;\",\"    text-align: center;\",\"    cursor: pointer;\",\"    opacity: 0.8;\",\"    z-index: 1;\",\"}\",\".center-button div {\",\"    position: relative;\",\"    width: 100%;\",\"    color: white;\",\"    font-size: 2em;\",\"    top: 0.5em;\",\"}\",\".center-button .celtra-icon-play { left: 0.1em;}\",\".center-button .celtra-icon-replay { top: 0.62em; left:0.05em}\",\".video-basic-controls-wrapper .custom-button {\",\"    position: absolute;\",\"    height: 100%;\",\"    width: 100%;\",\"    top: 0;\",\"    left: 0;\",\"    cursor: pointer;\",\"    opacity: 0.8;\",\"}\",\".custom-button img {\",\"    margin: auto;\",\"    position: absolute;\",\"    top: 0;\",\"    right: 0;\",\"    bottom: 0;\",\"    left: 0;\",\"}\",\".video-controls-button {\",\"    position: absolute;\",\"    cursor: pointer;\",\"    font-size: 1.5em;\",\"    color: white;\",\"    width: 2.5em;\",\"    height: 2.5em;\",\"}\",\".video-controls-desktop .video-controls-button div {\",\"    margin-top: -0.5em;\",\"}\",\".ie-click-event {\",\"    background-color: rgba(255,255,255,0);\",\"}\",\".video-controls-button-shadow {\",\"    text-shadow: 1px 1px 3px #000;\",\"}\",\".video-controls-button-top-right {\",\"    right: 0;\",\"    top: 0;\",\"}\",\".video-controls-desktop .video-controls-button-top-right {\",\"    margin-right: 0.5em;\",\"    margin-top: 0.5em;\",\"    width: 1.5em;\",\"    height: 1.5em;\",\"}\",\".video-controls-button-bottom-right {\",\"    right: 0;\",\"    bottom: 0;\",\"}\",\".video-controls-desktop .video-controls-button-bottom-right {\",\"    margin-right: 0.5em;\",\"    margin-bottom: 0.75em;\",\"    width: 1.5em;\",\"    height: 1.5em;\",\"}\",\".video-controls-button div {\",\"    position: absolute;\",\"    height: 50%;\",\"    width: 100%;\",\"    left: 0;\",\"    text-align: center;\",\"    top:  50%;\",\"    margin-top: -0.75em;\",\"    vertical-align: middle;\",\"}\",\".video-controls-button .celtra-icon-close {\",\"    font-size: 0.7em;\",\"    left: 0.07412em;\",\"}\",\".video-controls-button .celtra-icon-enter-full-screen,\",\".video-controls-button .celtra-icon-exit-full-screen {\",\"    left: 0.09em;\",\"}\",\".video-controls-pending {\",\"    animation: video-controls-pending 1s infinite linear;\",\"}\",\"@-webkit-keyframes video-controls-pending {\",\"    0%   { opacity: 1 }\",\"    50% { opacity: 0 }\",\"    100%   { opacity: 1 }\",\"}\",\"@keyframes video-controls-pending {\",\"    0%   { opacity: 1 }\",\"    50% { opacity: 0 }\",\"    100%   { opacity: 1 }\",\"}\",\"@font-face {\",'    font-family: \"celtraicons\";','    src:url(\"data:application/font-woff;base64,d09GRk9UVE8AAAqEAAoAAAAACjwAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAABDRkYgAAAA9AAABq8AAAavkHaHf09TLzIAAAekAAAAYAAAAGAIIvzDY21hcAAACAQAAABMAAAATBpVzGRnYXNwAAAIUAAAAAgAAAAIAAAAEGhlYWQAAAhYAAAANgAAADb/fCRYaGhlYQAACJAAAAAkAAAAJAPjAfJobXR4AAAItAAAAEgAAABIHLIB9G1heHAAAAj8AAAABgAAAAYAElAAbmFtZQAACQQAAAFdAAABXWEQuipwb3N0AAAKZAAAACAAAAAgAAMAAAEABAQAAQEBDGNlbHRyYWljb25zAAECAAEAOvgcAvgbA/gYBB4KABlT/4uLHgoAGVP/i4sMB4pq+JX4dAUdAAAA0A8dAAAA1REdAAAACR0AAAamEgATAQEMFxkbHiMoLTI3PEFGS1BVWl9kY2VsdHJhaWNvbnNjZWx0cmFpY29uc3UwdTF1MjB1RTYwMHVFNjAxdUU2MDJ1RTYwM3VFNjA0dUU2MDV1RTYwNnVFNjA3dUU2MDh1RTYwOXVFNjBBdUU2MEJ1RTYwQ3VFNjBEAAACAYkAEAASAgABAAQABwAKAA0ASwCpAPUCGAJDAs0DBQPvA/sEHgRFBG0E2wWp/JQO/JQO/JQO+5QOi/dUFYs/rUbAXAi1uwVjrnK/i8SL9eHh9YvAi7t2rmgIQ0P3VIuL91RAQAVduUuoRIv7IYv7B/sHi/shCA6L9+kVi/tP9wiK9yD7G4v4Yvsg+x4F93qoFWRkBaZ0nGqLZYtlempwdAiyZAWvrKK6i8CLwHS6Z6wI19cVZWUFuWCoTotIi0huTl1gCLFlBcO/rtaL3YvdaNVTwAgOPPdt+HQVRYtNamNXCF65i/se9x6LUcQFqra8p8SL54vWQIsuiy9AQC+LPItJwXnVCFmLBZ0m5D71i/cLi+3ti/cLi/cMKez7C4sIDvhf95UVg4uDh4WFhYWHg4uDi4KPg5GFkYWTiJOLlIuTjpGRkZGOk4uUi5OIk4WRhZGDj4KLCEveFfutiwWIi4iJiImIiIqIi4gIi/thBYuIjIiOiI6JjomOiwj3rYsFj4uOjY2Njo6MjouOCIv3YQWLjoqOiI6JjYiNh4sI++c+FYeLiYmLhwiLSwWLho2Jj4uQi42Ni5AIi8sFi4+JjYaLCIX3BxX4LYsFmYuXhpWBloGQf4t9CIv7YQWLfYZ/gIGBgX+GfYsI/C2LBX2Lf5CBlYGVhpeLmQiL92EFi5mQl5WVlZWXkJmLCPcO+zsVi3OVdZx8CJmaBX6Wg5yLnYutp6esi5yLm4SWgAh0dMiLi8hzcwV9mnaUdItei2dmi14IDvgl9/EVd5/7EfsR+xH3EXd39xH7EfsR+xGfd/cR9xH3EfsRn5/7EfcRBQ74lfgVFXiCd4Z1iKGYnKCTo3Z/dYJzh3ifb5hti1GLXFyLUYuDjIKMhDSPPrVXy4J7hXmLeAiLZp5rp3h6jHuQfZKLi4uLi4qLWK9hu4GCiYKJgYuEi4WMhIyZYbJtuYpnb116WosIg4uCjIOMum3CecaL91aL9Pc1i/cfi4+LkIuPoJqdnZmhCA73t2sVi/ef0IuV2zyLi74Fi6KRm62LCLWLi9MFhIxyjW2LTothZotGCItQRouLO9CLi/uf3osFDveznxWLlIiShJGFkoSOgouCi4OIhYSFhYiEi4KLgo6EkYSRhZOIlIuUi5KOkZGSko6Si5QI3ssVi/etBYuPio6IjYmOiIyHiwj7YYsFiIuIioiIiYmKiIuHCIv7rQWLh4yIjYmOiI6KjosI92GLBY+LjoyNjo6NjI6Ljwg+9+cVi4+JjYeLCEuLBYaLiYmLh4uHjYiQiwjLiwWPi42Oi48I9weRFYv8LQWLfYZ/gYGBgX+GfYsI+2GLBX2Lf5CBlYGVhpeLmQiL+C0Fi5mQl5WVlpWWkJmLCPdhiwWZi5eGlYGVgZB/i30IDtlqFYv4lfgI+5EFDqL4dBX3R4uL/JX7R4uL+JUF97aLFfdHi4v8lftHi4v4lQUO5/fAFVpZ91mMivdXW1s53SgpBfeY+04VjPtXvLzdOe3uOd27uwUO90v4HhW8u/tWi4v7Vbu63Drt7QX3Z/vHFYr3VVtaOt0pKdw6XFsFDvhSyhWotJy+i8KL1WzOWLoIaWkFtWSlVYtOi2F/ZXZrCGiuBZeikqWLp4u6drZrqQhoaAWjdppti2mLfYd9hn8IO9mL90kyMfsg9yFvb/h4/HmoqEjOBfw494QVi/s984r3E/sOi/ca+zL3MgUO+I/QFYuLi4uLiwj7L/cv9y/3LwWLi4uLi4uNjYyNjI2NkYqRhpAIQtQFhpCFjIWJiYqJiomJi4uLi4uLCPsv+y/7L/cvBYuLi4uLi4mNiYyJjIWNhYqGhghCQgWGhoqFjYWMiYyJjYmLi4uLi4sI9y/7L/sv+y8Fi4uLi4uLiYmKiYqJiYWMhZCGCNRCBZCGkYqRjY2MjYyNjYuLi4uLiwj3L/cv9y/7LwWLi4uLi4uNiY2KjYqRiZGMkJAI1NQFkJCMkYmRio2KjYmNCA74lBT4lBWLDAoAAAMCAAGQAAUAAAFMAWYAAABHAUwBZgAAAPUAGQCEAAAAAAAAAAAAAAAAAAAAARAAAAAAAAAAAAAAAAAAAAAAQAAA5g0B4P/g/+AB4AAgAAAAAQAAAAAAAAAAAAAAIAAAAAAAAgAAAAMAAAAUAAMAAQAAABQABAA4AAAACgAIAAIAAgABACDmDf/9//8AAAAAACDmAP/9//8AAf/jGgQAAwABAAAAAAAAAAAAAAABAAH//wAPAAEAAAABAAAXLCAzXw889QALAgAAAAAAzy5xzgAAAADPLnHO////3wIBAeAAAAAIAAIAAAAAAAAAAQAAAeD/4AAAAgD/////AgEAAQAAAAAAAAAAAAAAAAAAABIAAAAAAAAAAAAAAAABAAAAAgAAAAIAAAABsQAAAgD//wIAAG8CAAAAAgAAiwIAAGUCAABOAgAAFwIAAAoCAAAmAgAAAAIAAAEAAFAAABIAAAAAAA4ArgABAAAAAAABABYAAAABAAAAAAACAA4AYwABAAAAAAADABYALAABAAAAAAAEABYAcQABAAAAAAAFABYAFgABAAAAAAAGAAsAQgABAAAAAAAKACgAhwADAAEECQABABYAAAADAAEECQACAA4AYwADAAEECQADABYALAADAAEECQAEABYAcQADAAEECQAFABYAFgADAAEECQAGABYATQADAAEECQAKACgAhwBjAGUAbAB0AHIAYQBpAGMAbwBuAHMAVgBlAHIAcwBpAG8AbgAgADEALgAwAGMAZQBsAHQAcgBhAGkAYwBvAG4Ac2NlbHRyYWljb25zAGMAZQBsAHQAcgBhAGkAYwBvAG4AcwBSAGUAZwB1AGwAYQByAGMAZQBsAHQAcgBhAGkAYwBvAG4AcwBHAGUAbgBlAHIAYQB0AGUAZAAgAGIAeQAgAEkAYwBvAE0AbwBvAG4AAAAAAwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==\"),','        url(\"data:font/truetype;base64,AAEAAAALAIAAAwAwT1MvMggi/MMAAAC8AAAAYGNtYXAaVcxkAAABHAAAAExnYXNwAAAAEAAAAWgAAAAIZ2x5ZtKQLWwAAAFwAAAJfGhlYWT/fCRYAAAK7AAAADZoaGVhA+MB8gAACyQAAAAkaG10eByyAfQAAAtIAAAASGxvY2EUzhFSAAALkAAAACZtYXhwABgAwAAAC7gAAAAgbmFtZWEQuioAAAvYAAABXXBvc3QAAwAAAAANOAAAACAAAwIAAZAABQAAAUwBZgAAAEcBTAFmAAAA9QAZAIQAAAAAAAAAAAAAAAAAAAABEAAAAAAAAAAAAAAAAAAAAABAAADmDQHg/+D/4AHgACAAAAABAAAAAAAAAAAAAAAgAAAAAAACAAAAAwAAABQAAwABAAAAFAAEADgAAAAKAAgAAgACAAEAIOYN//3//wAAAAAAIOYA//3//wAB/+MaBAADAAEAAAAAAAAAAAAAAAEAAf//AA8AAQAAAAAAAAAAAAIAADc5AQAAAAABAAAAAAAAAAAAAgAANzkBAAAAAAEAAAAAAAAAAAACAAA3OQEAAAAAAQAAAAACAAHAACMAADcUHgIXNy4DNTQ+AjMyHgIXBzM1By4DIyIOAhUADBcgFCoPGBEJHjRGKBQmIh8NSMBLESkuMhs1XUYowB02MSoSMA0gJSgWKEY0HggOFQ1IwEsRHBQKKEZdNQADAAAAEgIBAeAABQAcADMAABMVHwERBzcHHgMVFA4CBxc+AzU0LgInNwceAxUUDgIHFz4DNTQuAicAdIyM5icKEQsGBgsRCicNFhAICBAWDUwmERwTCwsTHBEmFSEYDQ0YIRUBVbsBhwHOih0nCBUYGw4OGxgVCCcNHSIlFBQlIh0NTCYQJyswGRkwKycQJhQvNTofHzo1LxQAAAABAAAALgGyAeAALQAAEyIOAgcnFTMnPgMzMh4CFRQOAiMiLgInIx4DMzI+AjU0LgIj2RoxLCYPLYo6DB0jKBUiPS4aGi49Ih41Kx4HMgcmOUcoLU86IyM6Ty0B4AwWHxQuijkQGhMKGy09IyI9LhoTIi8cJkEvHCM6Ty0tTzsiAAX//wBHAf8BegAoAFUAbACZAL0AAAEiDgIHDgMVFB4CFx4DMzI+Ajc+AzU0LgInLgMjJyEiDgIHDgMdARQeAhceAzMhMj4CNT4CND0BPAEuASc0LgIjBSIOAh0BFB4CMzI+Aj0BNC4CIychMh4CFx4DHQEUDgIHDgMjISIuAicuAz0BND4CNz4DMxcUHgIXNy4DNTQ+AjMyHgIXBzM1By4DIyIOAhUBywMGBgUCAgQCAgICBAICBQYGAwQGBgUCAgQCAQECBAICBQYGBED+5wEDAgIBAQEBAQEBAQEBAgIDAQEZAgICAwECAQECAQMCAgL+rQEDAQEBAQMBAgICAQECAgIGAZkGCQkJAwQGBAICBAYEAwkJCQb+ZwUKCQgEBAUEAgIEBQQECAkKBXoDCAoGDgUIBQMKEBcMBwwLCgQXPRgFDQ8QCREdFwwBAQICAwMCBQYGAwQGBgUCAgQCAQECBAICBQYGBAMGBgUCAwMCAlMBAQEBAQICAwHNAQMCAgEBAQEBAQEBAQECAgMBzQEDAgIBAQEBAU0BAQMBQAICAgEBAgICQAEDAQFzAgQFBAQICQoFzQUKCQgEBAUEAgIEBQQECAkKBc0FCgkIBAQFBAKnCREQDgUPBAsLDQcNFhEKAwUGBBc9GAYJBgMNFh4RAAAAAQBvAE8BkQFxAAsAAAEnBycHFwcXNxc3JwGRFH19FH19FH19FH0BXRR9fRR9fRR9fRR9AAEAAAARAgEBsgBpAAABDgMjPgM3DgMjLgMHJg4CFRwBHgEVLgMnDgMVFB4CFwYuAic0FDQUNRQeAhciBiIGJwYmBiYHHgM3DgMHJgYmBiceAxc+AzUwPAI1PgM3AgEHDw8QCAgPCwoDCBAREQkHEhMWCxYmHRABASA+NzATBAUEAgcMEgoGDQwLBQwXHxIDBwcHBAIFBQUDBRQZHxIOHiEjEwMGBwYDESYpKxZJb0wnCA4NDAYBgQMGAwQEDQ4SCAMJBgYGDgcGAQESGycVBAUHBQQCEB4mGQcMDg0IDRoWFAYBAwMFAgEBAQIBFCEdEQUCAgEBAgECAQ8bEg0BCxANBQEBAQIBAg0QDQYBATZXaTYFAgQBBwsPDgkAAQCL/+ABdQHgABwAAAURMzcjNTQ+AjsBNSIuAiMiDgIdASMVMxEzASNFCk8DCQ8NKgMLERQLFyYbD0VFUyABC1AzCQ4LBUgBAQEOGycaO1D+9QAABABl/+EBmAHgACgAVQBsAJkAACU0LgInLgMjIg4CBw4DFRQeAhceAzMyPgI3PgM1NxE0LgInLgIiKwEqAQ4BBw4CFBURHAEeARceAjI7AToBPgE3PgM1AzQuAisBIg4CFRQeAjsBMj4CNTcRFA4CBw4DKwEiLgInLgM1ETQ+Ajc+AzsBMh4CFx4DFQEfAQMDAwIFBgYDBAYFBgICBAIBAQIEAgIGBQYEAwYGBQIDAwMBUwEBAQEBAgIDAc0BAgMCAQEBAQEBAQECAwIBzQEDAgIBAQEBAU0BAQMBQAICAgEBAgICQAEDAQFzAgMGBAQICQoFzQUKCQgEAwYEAgIEBgMECAkKBc0FCgkIBAQGAwIUAwYGBQIDAwMBAQMDAwIFBgYDAwcFBQMCBAIBAQIEAgMFBQcDQAEZAgICAgEBAgEBAgEBAgICAv7nAQMCAgEBAgEBAgEBAgIDAQFTAQMBAQEBAwECAgIBAQICAgb+ZwUKCQgEBAYDAgIDBgQECAkKBQGZBQoJCAQEBgQBAQQGBAQICQoFAAAAAQBO/98BwgHgAAIAABcRBU4BdCECAf0AAgAX/98B7AHgAAQACQAAEzMTIxMhMwMjAxeyAbQBASG0AbIBAeD9/wIB/f8CAQAAAgAK/+AB9AHgAAYADQAAEwczJwcnBwUXNxc3JzdcMcUBMFJjAQQBMVJiUjABLDHEMVNjucQyU2RRMQAAAgAmAAUB3AG6AAYADQAAEzcHFTcXNxMnBycHFwe3McIwUWLTATBRYlEvAYoxAcAuUGH+zsAwUWFSLwAAAgAA/98CAQHgADMAOQAAJT4DNTQuAicHHgMVFA4CByc+AzU0LgInBx4DFRQOAgcnNQcnBwE3JyUVHwE1JwG+CxEMBgsVHxMiDxoRCgUIDAgjBAcFAwcOFAwjCQ4LBQEDAwJQWYwcAeQdQ/5caH+ePxAiJiYWGzUvLBAhECEoKxcPHxsbCyIKERQTDBAjHhsLIgkSFxcNBQsJCgRNtluOHf4cHETvqAJ5hZ8AAAEAAf/hAf8B3wCEAAAlOAMxJzc4AzE+AzU2NC4BLwEuAiIHIg4CBzgDMQcnOAMxLgMjJiIOAQ8BDgIUFxQeAhc4AzEXBzgDMQ4DFQYUHgEfAR4CMjcyPgI3OAMxNxc4AzEeAzMWMj4BPwE+AjQnNC4CJwH7m5sBAQEBAQECAkkCBAQFAgEBAgEBm5sBAQIBAQIFBAQCSQICAQEBAQEBm5sBAQEBAQECAkkCBAQFAgEBAgEBm5sBAQIBAQIFBAQCSQICAQEBAQEBRZubAQECAQECBQQEAkkCAgEBAQEBAZubAQEBAQEBAgJJAgQEBQIBAQIBAZubAQECAQECBQQEAkkCAgEBAQEBAZubAQEBAQEBAgJJAgQEBQIBAQIBAQAAAAEAAAABAAAoDgB8Xw889QALAgAAAAAAzy5xzgAAAADPLnHO////3wIBAeAAAAAIAAIAAAAAAAAAAQAAAeD/4AAAAgD/////AgEAAQAAAAAAAAAAAAAAAAAAABIAAAAAAAAAAAAAAAABAAAAAgAAAAIAAAABsQAAAgD//wIAAG8CAAAAAgAAiwIAAGUCAABOAgAAFwIAAAoCAAAmAgAAAAIAAAEAAAAAAAoAFAAeAFIAoADgAdoB9AKCAqwDeAOEA5wDugPYBC4EvgAAAAEAAAASAL4ABQAAAAAAAgAAAAAAAAAAAAAAAAAAAAAAAAAOAK4AAQAAAAAAAQAWAAAAAQAAAAAAAgAOAGMAAQAAAAAAAwAWACwAAQAAAAAABAAWAHEAAQAAAAAABQAWABYAAQAAAAAABgALAEIAAQAAAAAACgAoAIcAAwABBAkAAQAWAAAAAwABBAkAAgAOAGMAAwABBAkAAwAWACwAAwABBAkABAAWAHEAAwABBAkABQAWABYAAwABBAkABgAWAE0AAwABBAkACgAoAIcAYwBlAGwAdAByAGEAaQBjAG8AbgBzAFYAZQByAHMAaQBvAG4AIAAxAC4AMABjAGUAbAB0AHIAYQBpAGMAbwBuAHNjZWx0cmFpY29ucwBjAGUAbAB0AHIAYQBpAGMAbwBuAHMAUgBlAGcAdQBsAGEAcgBjAGUAbAB0AHIAYQBpAGMAbwBuAHMARwBlAG4AZQByAGEAdABlAGQAIABiAHkAIABJAGMAbwBNAG8AbwBuAAAAAAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=\");',\"    font-weight: normal;\",\"    font-style: normal;\",\"}\",'[class^=\"celtra-icon-\"], [class*=\" celtra-icon-\"] {','    font-family: \"celtraicons\";',\"    speak: none;\",\"    font-style: normal;\",\"    font-weight: normal;\",\"    font-variant: normal;\",\"    text-transform: none;\",\"    line-height: 1;\",\"    -webkit-font-smoothing: antialiased;\",\"    -moz-osx-font-smoothing: grayscale;\",\"}\",'.celtra-icon-redo:before { content: \"\\\\e600\"; }','.celtra-icon-close:before { content: \"\\\\e60d\"; }','.celtra-icon-go-landscape:before { content: \"\\\\e603\"; }','.celtra-icon-twitter:before { content: \"\\\\e605\"; }','.celtra-icon-facebook:before { content: \"\\\\e606\"; }','.celtra-icon-phone:before { content: \"\\\\e607\"; }','.celtra-icon-play:before { content: \"\\\\e608\"; }','.celtra-icon-pause:before { content: \"\\\\e609\"; }','.celtra-icon-exit-full-screen:before { content: \"\\\\e60a\"; }','.celtra-icon-enter-full-screen:before { content: \"\\\\e60b\"; }','.celtra-icon-unmute:before { content: \"\\\\e60c\"; }','.celtra-icon-mute:before { content: \"\\\\e601\"; }','.celtra-icon-replay:before { content: \"\\\\e602\"; }','.celtra-icon-close2:before { content: \"\\\\e604\"; }',\".spinner .center-button {\",\"    display: none;\",\"}\",\".web-audio .celtra-icon-mute, .web-audio .celtra-icon-unmute { color: red; }\",\"\"].join(\"\\n\");this.VideoPlayerCss=A}();;\n!function(){function t(e){switch(e){case\"video/mp4\":return\"video.mp4\";case\"video/webm\":return\"video.webm\";case\"video/avi\":return\"video.avi\";case\"audio/mp4\":return\"audio.m4v\";default:throw new Error(\"Unknown mime type\")}}var n={startMuted:!1,preload:!1,from:null,to:null,blobHash:null,hasAudio:!0,skipCodecs:[],codecPriority:[\"vp9\",\"h265\",\"h264\",\"vp8\"],transcodingGroup:\"inline\",customUrlParameters:{},forceMPEG4Video:!1,forceMPEG1Video:!1},a=CeltraPlayerUtils.composeUrl,c=CeltraPlayerUtils.filterObject,s=CeltraPlayerUtils.getUrl,u=(createSourceObject=CeltraPlayerUtils.createSourceObject,generateMediaType=CeltraPlayerUtils.generateMediaType,VideoEngineProvider={},{vp9:{low:[\"vp9_served_480p\"],medium:[\"vp9_served_720p\"],high:[\"vp9_served_1080p\"]},h265:{low:[\"x265_served_480p\"],medium:[\"x265_served_1080p\"],high:[\"x265_served_1080p\"]},h264:{low:[\"mpeg4HQ\"],medium:[\"mpeg4HQPlus\"],high:[\"mpeg4HD\"]},vp8:{low:[\"webmHQ\"],medium:[\"webmHQ\"],high:[\"webmHD\"]}}),l=function(e,r){return!!{h265:deviceInfo.browser.chrome(),vp9:deviceInfo.os.android(null,\"7.1\")||r.isVideo360&&deviceInfo.browser.firefox()||deviceInfo.browser.safari()&&deviceInfo.deviceType.desktop()}[e]},d=function(){return deviceInfo.deviceType.desktop()||deviceInfo.os.ios(\"10\",null)&&!deviceInfo.browser.chrome(null,\"64\")||deviceInfo.os.android()&&deviceInfo.browser.chrome(\"53\",null)&&!deviceInfo.browser.samsung()||deviceInfo.os.android()&&deviceInfo.browser.samsung(\"6.4\",null)},v=(AudioEngineProvider={},function(e){return\"undefined\"!=typeof creative?(e=creative.videoTranscodingPresets[e]&&creative.videoTranscodingPresets[e].algoVersion,creative.version+(e?\"-\"+e:\"\")):null});VideoEngineProvider.createVideoEngineSpec=function(e,r){var r=merge(n,r),i=(r.skipCodecs=r.skipCodecs.filter(function(e){return-1===[\"h264\",\"vp8\"].indexOf(e)}),\"1\"==r.campaignExplorer&&(r.startMuted=!0),o=e,(i=r).forceMPEG4Video||!i.forceMPEG1Video&&!i.fallbackToMPEG1&&(d()||o)?\"html5\":\"jsVideo\"),o={inUserInitiatedThread:e,startMuted:r.videoElement?r.videoElement.muted:r.startMuted};return\"jsVideo\"==i?merge({clazz:\"JsVideoEngine\",presets:[(i=r).fallbackToMPEG1?\"mpeg1LQVideo\":\"mpeg1SHQVideo\"],engineType:\"mpeg1\",doAVSync:i.hasAudio&&!i.isTeaser,isTeaser:i.isTeaser,preload:i.preload,options:{}},o):merge(function(r,e){var i=\"\";if(r.isMasterVideo)if(deviceInfo.deviceType.desktop()&&!r.capStreamAtHQPlus)i=\"high\";else i=\"medium\";else if((deviceInfo.deviceType.desktop()||r.isVideo360||r.hqQuality||r.isSnapchatFormat)&&!r.isTeaser)i=\"medium\";else i=\"low\";var o=r.codecPriority.filter(function(e){return r.skipCodecs.indexOf(e)===-1&&!l(e,r)}).map(function(e){return u[e][i]}).reduce(function(e,r){return e.concat(r)},[]).filter(function(e){return creative.videoTranscodingGroups[r.transcodingGroup].indexOf(e)!==-1});if(r.videoElement){var n=document.createElement(\"video\");var d=[\"\",\"maybe\",\"probably\"];var t=o.map(function(e){var r=creative.videoTranscodingPresets[e].mediaType;var i=deviceInfo.browser.chrome()&&e.indexOf(\"mpeg4\")===0?\"probably\":n.canPlayType(generateMediaType(r));return{id:e,canPlayType:i}}).sort(function(e,r){if(e.canPlayType===r.canPlayType)return 0;return d.indexOf(e.canPlayType)>d.indexOf(r.canPlayType)?-1:1}).shift().id;o=[t]}var a=!deviceInfo.deviceType.desktop()&&r.isVideo360&&e&&!r.startMuted,c;return{clazz:deviceInfo.os.ios(null,\"10\")&&r.isVideo360?\"CrossOriginHtml5VideoEngine\":\"Html5VideoEngine\",presets:o,engineType:\"html5\",doAVSync:a,isTeaser:r.isTeaser,preload:r.preload,crossOrigin:true,options:{}}}(r,e),o)},VideoEngineProvider.createVideoSourceObjects=function(e,n){var d=e.doAVSync||e.isTeaser;return e.presets.map(function(e){r=n;var r=c(merge({from:r.from,to:r.to,muted:d?\"1\":null,transform:\"VideoStream\",celtraCacheBust:v(e)},r.customUrlParameters)),i=creative.videoTranscodingPresets[e].mediaType,o=t(i.mime);return createSourceObject(a(s(\"cachedApi\"),\"videoStream\",n.blobHash,e,o,r),i)})},window.VideoEngineProvider=VideoEngineProvider,AudioEngineProvider.createAudioEngineSpec=function(e){return e.doAVSync?{clazz:\"undefined\"!=typeof AudioContext||\"undefined\"!=typeof webkitAudioContext?\"WebAudioEngine\":\"Html5AudioEngine\",preload:e.preload}:null},AudioEngineProvider.createAudioSourceObjects=function(n){return[\"aacAudio\"].map(function(e){var r=n?(r=n,c(merge({from:r.from,to:r.to,transfom:\"VideoStream\",celtraCacheBust:v(e)},r.customUrlParameters))):{},i=creative.videoTranscodingPresets[e].mediaType,o=t(i.mime);return createSourceObject(a(s(\"cachedApi\"),\"videoStream\",n.blobHash,e,o,r),i)})},window.AudioEngineProvider=AudioEngineProvider}();;\n!function(i){function t(e,t,i,n,s){this._containerEl=e,this._opts=merge({},o,s),this._videoEngine=null,this._videoEngineSpecProvider=t,this.isUserGazing=n,this._doc=e.ownerDocument,this._win=this._doc.defaultView,this._initialized=!1,this._firstPlay=!0,this.silentPauseActive=!1,this._engineType=null,this._videoPresets=[],this._muted=!0,i&&(this._controlsController=i(this,this._opts),this._controlsController&&this._controlsController.on(\"userInteraction\",this.emit.bind(this,\"userInteraction\"))),this._aspectRatio=this._opts.videoWidth/this._opts.videoHeight,this.isFullScreen=!this.playsInline(),this.isDesktop=this._opts.isDesktop,this.anchoringPoint=this._opts.anchoringPoint,this.buffered=0,this._state=null,this._posterShown=!0,this._seeked=!1,this._repeated=0,this.playSucceeded=!1,this._playingEmitted=!1,this._firstRenderEmitted=!1,this._timeUpdateCounter=0,this._lastTimeUpdateEmitted=0,this.play=this.play.bind(this),this.silentPlay=this.silentPlay.bind(this),this.pause=this.pause.bind(this),this.silentPause=this.silentPause.bind(this),this.mute=this.mute.bind(this),this.unMute=this.unMute.bind(this),this.redraw=this.redraw.bind(this),this.enterFullScreen=this.enterFullScreen.bind(this),this.exitFullScreen=this.exitFullScreen.bind(this),this.destroy=this.destroy.bind(this),this._onFullscreenChange=this._onFullscreenChange.bind(this),this._init(),this.changeState(CeltraPlayerUtils.UNSTARTED),this._fullScreenAncestors=[]}extend(t.prototype,StatefulEventEmitter),Object.defineProperty(t.prototype,\"paused\",{get:function(){return this._videoEngine&&this._videoEngine.paused}}),Object.defineProperty(t.prototype,\"componentName\",{get:function(){return this._opts.componentName}}),Object.defineProperty(t.prototype,\"duration\",{get:function(){return this.serverReportedDuration||this._videoEngine&&this._videoEngine.duration||0}}),Object.defineProperty(t.prototype,\"serverReportedDuration\",{get:function(){return this._opts.duration}}),Object.defineProperty(t.prototype,\"currentTime\",{get:function(){return this._videoEngine&&this._videoEngine.getCurrentTime()||0},set:function(e){this._videoEngine&&this._videoEngine.setCurrentTime(e)}}),Object.defineProperty(t.prototype,\"reportsSeeking\",{get:function(){return!(!this._videoEngine||!this._videoEngine.reportsSeeking)}}),Object.defineProperty(t.prototype,\"muted\",{get:function(){return this._muted}}),Object.defineProperty(t.prototype,\"fullscreen\",{get:function(){return this.isFullScreen}}),Object.defineProperty(t.prototype,\"engineType\",{get:function(){return this._engineType}}),Object.defineProperty(t.prototype,\"videoPresets\",{get:function(){return this._videoPresets}}),Object.defineProperty(t.prototype,\"activeSrc\",{get:function(){return this._videoEngine&&this._videoEngine.activeSrc}}),Object.defineProperty(t.prototype,\"activePreset\",{get:function(){return this._videoPresets&&this._videoPresets.filter(function(e){return this.activeSrc&&~this.activeSrc.indexOf(e)}.bind(this))[0]}}),Object.defineProperty(t.prototype,\"engineInitialized\",{get:function(){return this._initialized}}),Object.defineProperty(t.prototype,\"ended\",{get:function(){return this.isState(CeltraPlayerUtils.ENDED)}});var o={canShowSpinner:!0,closeFSOnEnd:!1,fitting:\"fit\",fullscreenMode:\"both\",pauseOnEnd:!1,playFrom:null,playTo:null,poster:\"data:image/gif;base64,\"+CeltraPlayerUtils.EMPTY_PIXEL,repeatTimes:0,videoWidth:160,videoHeight:100},n={loadstart:\"onLoadStart\",loadedmetadata:\"onLoadedMetaData\",durationchange:\"onDurationChange\",progress:\"onProgress\",timeupdate:[\"onTimeUpdate\",\"onTimeUpdateRepeat\"],canplay:\"onCanPlay\",play:\"onPlay\",playing:\"onPlaying\",pause:\"onPause\",autoplayrejected:\"onAutoplayRejected\",autoplaynotpossible:\"onAutoplayNotPossible\",forcemuted:\"onForceMuted\",ended:\"onEnded\",buffering:\"onBuffering\",endbuffering:\"onEndBuffering\",muted:\"onMuted\",unmuted:\"onUnMuted\",seeking:\"onSeeking\",seeked:\"onSeeked\",exitfullscreen:\"onExitFullScreen\",webkitendfullscreen:\"onExitFullScreen\",error:\"onError\",canunmute:\"onCanUnMute\",custominfo:\"onCustomInfo\",playersourceloading:\"onPlayerSourceLoading\",volumechange:\"onVolumeChange\"};t.DOM=['<div class=\"video-player-wrapper\" x-bind=\"_wrapperEl\">','    <div class=\"video-player-engine\" x-bind=\"_engineContainerEl\"></div>','    <div x-bind=\"_posterEl\" class=\"video-player-poster\" style=\"background-image: url(\\'<%= poster %>\\')\"></div>','    <div class=\"video-controls-container\" x-bind=\"_controlsWrapperEl\"></div>',\"</div>\"],t.prototype._init=function(){var e;CeltraPlayerUtils.createDom(this._doc,t.DOM,this._opts,CeltraPlayerUtils.xBindFactory(this)),CeltraPlayerUtils.insertStyleTag(\"celtra-video-player-style\",VideoPlayerCss,this._doc),this._controlsController&&(this._controlsEl=this._controlsController.render(this._doc),this._controlsWrapperEl.appendChild(this._controlsEl)),this._containerEl.appendChild(this._wrapperEl),this._posterShow(),this._controlsController&&this._controlsController.adjustSizes(this._containerEl.clientHeight),this._opts.preload&&(e=this.createSpecs(!1,this._opts.startMuted),this.initialize(e)),this.dtCanFullscreen=this.isDesktop&&this._toggleDesktopFSStateHandlers(\"add\")},t.prototype.setDimensions=function(e,t){this.width=e,this.height=t,this.redraw(),this.emit(\"rendered\")},t.prototype.getVideoElement=function(){return this._videoEngine},t.prototype.getControlsElement=function(){return this._controlsEl},t.prototype.getWrapperElement=function(){return this._wrapperEl},t.prototype._posterHide=function(){this._posterShown&&(this._posterShown=!1,this._posterEl.style.display=\"none\")},t.prototype._posterShow=function(){this._posterShown||(this._posterShown=!0,this._posterEl.style.display=\"\")},extend(t.prototype,{onLoadStart:function(){this.emit(\"loadstart\")},onLoadedMetaData:function(){this.redraw(),this.emit(\"canunmute\")},onCanPlay:function(){this.emit(\"canplay\")},onDurationChange:function(){this.emit(\"durationchange\")},onBuffering:function(){this.changeState(CeltraPlayerUtils.BUFFERING),this.emit(\"buffering\")},onEndBuffering:function(){this.emit(\"endbuffering\")},onPlayerSourceLoading:function(e){(this._opts.autoplay||e)&&this.emit(\"playersourceloading\")},onMuted:function(){this._muted=!0,this.emit(\"muted\")},onUnMuted:function(){this._muted=!1,this.emit(\"unmuted\")},onVolumeChange:function(e){this._muted=0==e,this.emit(this._muted?\"muted\":\"unmuted\"),this.emit(\"volumechange\",e)},onProgress:function(){\"object\"==typeof this._videoEngine.buffered&&0<this._videoEngine.buffered.length?this.buffered=this._videoEngine.buffered.end(0):this.buffered=this._videoEngine.buffered,this.emit(\"progress\",this.buffered/(this.duration||1))},onTimeUpdate:function(e){!this._playingEmitted&&2<this._timeUpdateCounter&&0<this.buffered&&(this._playingEmitted=!0,this.onEndBuffering(),this.changeState(CeltraPlayerUtils.PLAYING),this.emit(\"playing\")),this._timeUpdateCounter+=1;var t=Date.now();3<this._timeUpdateCounter&&70<t-this._lastTimeUpdateEmitted&&(this._lastTimeUpdateEmitted=t,this._posterHide(),this._firstRenderEmitted||(this.emit(\"firstRender\"),this._firstRenderEmitted=!0),this.emit(\"timeupdate\",this._videoEngine.getCurrentTime()))},onTimeUpdateRepeat:function(e){this._opts.playTo&&e>=this._opts.playTo&&(this._hasToBeRepeated()?this._doRepeat():(this.pause(),this._doEnd()))},onPlay:function(){this.emit(\"play\")},onPlaying:function(){this.emit(\"playing\"),this.changeState(CeltraPlayerUtils.PLAYING),this.playSucceeded=!0,this.redraw()},onPause:function(){this.changeState(CeltraPlayerUtils.PAUSED),this.emit(\"pause\")},onAutoplayRejected:function(){this.emit(\"autoplayrejected\")},onAutoplayNotPossible:function(){this.emit(\"autoplaynotpossible\")},onForceMuted:function(){this.emit(\"forcemuted\")},onExitFullScreen:function(){this.exitFullScreen()},onEnded:function(){this._playingEmitted=!1,this._hasToBeRepeated()?this._doRepeat():this._doEnd()},getContainerDimensions:function(){var e;return this.isFullScreen?{width:(e=this.dtCanFullscreen?this._wrapperEl:this._wrapperEl.parentNode).clientWidth,height:e.clientHeight}:{width:this.width,height:this.height}},redraw:function(){var e=\"fit\"!==this._opts.fitting,t=this._aspectRatio,i=this.getContainerDimensions(),n=CeltraPlayerUtils.fitComponent(100*t,100,i.width,i.height,e);switch(this.anchoringPoint){case\"top\":n.marginVertical=0;break;case\"bottom\":n.marginVertical=Math.ceil(i.height-n.height)}this._opts.videoElement||(this._wrapperEl.style.background=this._opts.barColor);function s(e){e.width=n.width+\"px\",e.height=n.height+\"px\",e.minHeight=n.height+\"px\",e.left=n.marginHorizontal+\"px\",e.top=n.marginVertical+\"px\"}e?(s(this._engineContainerEl.style),s(this._posterEl.style),(t=this._controlsWrapperEl.style).width=i.width+\"px\",t.height=i.height+\"px\",t.minHeight=i.height+\"px\",t.left=0,t.top=0):(s(this._engineContainerEl.style),s(this._posterEl.style),s(this._controlsWrapperEl.style))},onSeeking:function(){this.emit(\"seeking\")},onSeeked:function(e){if(this._seeked){switch(this._seeked=!1,this._stateBeforeSeek){case CeltraPlayerUtils.PLAYING:this.play();break;case CeltraPlayerUtils.PAUSED:this.silentPause()}this._stateBeforeSeek=void 0}this.emit(\"seeked\")},onError:function(){this.emit(\"error\")},onCanUnMute:function(){this.emit(\"canunmute\")},onCustomInfo:function(e){this.emit(\"custominfo\",e)}}),extend(t.prototype,{getState:function(){return this._state},mute:function(){this._videoEngine&&\"function\"==typeof this._videoEngine.mute&&this._videoEngine.mute(),this.onMuted()},unMute:function(){this._videoEngine&&\"function\"==typeof this._videoEngine.unMute&&this._videoEngine.unMute(),this.onUnMuted()},silentPlay:function(){this._videoEngine.play()},createSpecs:function(e,t){e=this._videoEngineSpecProvider(e,t),e=merge(e,{options:this._opts}),this._opts.videoStream&&this._opts.videoStream.useRaw&&(e.videoUrl=(creative.secure?creative.cachedApiUrl:creative.insecureCachedApiUrl)+\"blobs/\"+this._opts.videoStream.blobHash),t=AudioEngineProvider.createAudioEngineSpec(e);return{videoEngineSpec:e,videoSources:e.videoUrl?[CeltraPlayerUtils.createSourceObject(e.videoUrl,null)]:VideoEngineProvider.createVideoSourceObjects(e,this._opts.videoStream),audioEngineSpec:t,audioSources:t?AudioEngineProvider.createAudioSourceObjects(this._opts.videoStream):null}},initialize:function(e){this._createEngines(e),CeltraPlayerUtils.isNode(this._engineEl)&&!this._engineEl.parentNode&&this._engineContainerEl.appendChild(this._engineEl),e.videoEngineSpec.options.videoElement&&(this._wrapperEl.style.background=\"transparent\"),CeltraPlayerUtils.attachHandlers(this,this._videoEngine,n),void 0!==e.videoEngineSpec.startMuted&&(e.videoEngineSpec.startMuted?this.mute():this.unMute()),this._initialized=!0},play:function(e){null==this._videoEngine?Logger(\"VideoPlayer\").log(\"Video not initialized!\"):(this.isState(CeltraPlayerUtils.UNSTARTED,CeltraPlayerUtils.ENDED)&&this.changeState(CeltraPlayerUtils.BUFFERING),this._firstPlay&&!e&&this.unMute(),this._firstPlay=!1,this._videoEngine.play(),this.emit(\"userplayed\"))},_createEngines:function(e){var t;null===this._videoEngine&&(this._engineType=e.videoEngineSpec.engineType,this._videoPresets=e.videoEngineSpec.presets,\"JsVideoEngine\"==e.videoEngineSpec.clazz?this._videoEngine=new JsVideoEngine(e.videoSources[0].url,this._engineType,e.videoEngineSpec):\"Html5VideoEngine\"==e.videoEngineSpec.clazz&&(this._videoEngine=Html5VideoEngine(e.videoSources,e.videoEngineSpec)),null!==e.audioEngineSpec&&(t=null,\"WebAudioEngine\"==e.audioEngineSpec.clazz?(t=WebAudioEngine(e.audioSources[0].url,e.audioEngineSpec.preload),Object.create(AVSyncWebAudio).init(this,t,e.videoEngineSpec.startMuted)):\"Html5AudioEngine\"==e.audioEngineSpec.clazz&&(t=Html5AudioEngine(e.audioSources,e.audioEngineSpec.preload),Object.create(AVSyncAudio).init(this,t,e.videoEngineSpec.startMuted))),this._engineEl=this._videoEngine.render(this._doc,{playsInline:this.playsInline()}))},silentPause:function(){this._videoEngine.pause()},pause:function(){this._videoEngine&&this._videoEngine.pause(),this.emit(\"userpaused\"),this._playingEmitted=!1},close:function(){this.playsInline()?this.exitFullScreen():this.emit(\"close\")},replay:function(){this.emit(\"replayed\"),this.setCurrentTime(0),this._playingEmitted=!1,this.play()},reset:function(){this._videoEngine.pause(),this._videoEngine.reset(),this._posterShow(),this._controlsController&&this._controlsController.reset(),this._timeUpdateCounter=0,this._playingEmitted=!1},enterFullScreen:function(){if(this._videoEngine){if(this._containerElzIndex=this._containerEl.style.zIndex,this.dtCanFullscreen){var e=this._wrapperEl;(e.requestFullscreen||e.webkitRequestFullscreen||e.mozRequestFullScreen||e.msRequestFullscreen).bind(e)()}else{this.isFullScreen=!0;var t=this._wrapperEl;for(this.isState(CeltraPlayerUtils.PAUSED);t&&!t.getAttribute(\"class\").includes(\"celtra-base-creative-unit\");)t=t.parentNode;t.insertBefore(this._wrapperEl,t.firstChild),this._wrapperEl.style.zIndex=CeltraPlayerUtils.ZINDEX_MAX,this.redraw(),this.silentPauseActive=!0,defer(function(){this.silentPauseActive=!1}.bind(this),500),this._initialized&&this._videoEngine.pause()}this._initialized&&this._videoEngine.play(),this.emit(\"enterfullscreen\")}},exitFullScreen:function(){var e;this.dtCanFullscreen?((e=document).exitFullscreen||e.webkitExitFullscreen||e.mozCancelFullScreen||e.msExitFullscreen).bind(e)():(this.isFullScreen=!1,this.isState(CeltraPlayerUtils.PAUSED,CeltraPlayerUtils.ENDED),this._wrapperEl.style.zIndex=\"\",this._containerEl.style.zIndex=this._containerElzIndex,this._containerEl.appendChild(this._wrapperEl),this.silentPauseActive=!0,defer(function(){this.silentPauseActive=!1}.bind(this),500),this.once(\"pause\",CeltraPlayerUtils.validFor(function(){this.play(),this._muted||defer(this.unMute.bind(this),100)}.bind(this),500))),this.redraw(),this.emit(\"exitfullscreen\")},hide:function(){this._wrapperEl&&(this._wrapperEl.style.display=\"none\")},show:function(){this._wrapperEl&&(this._wrapperEl.style.display=\"\")},destroy:function(){this._videoEngine&&(this._videoEngine.pause(),this._videoEngine.destroy(),this._controlsController&&this._controlsController.destroy());var e=function(){CeltraPlayerUtils&&this._wrapperEl&&CeltraPlayerUtils.removeElements(this._wrapperEl),this._toggleDesktopFSStateHandlers(\"remove\"),this._wrapperEl=null}.bind(this);\"undefined\"!=typeof TouchEventSimulator?defer(e,100):e(),this.emit(\"destroy\")},getDuration:function(){return this.duration},getCurrentTime:function(){return this._videoEngine?this._videoEngine.getCurrentTime():null},setCurrentTime:function(e){this._videoEngine&&this._videoEngine.setCurrentTime(e)},seekTo:function(e){this.emit(\"seekto\",e),this._seeked||(this._stateBeforeSeek=this.getState(),this.getState()===CeltraPlayerUtils.PLAYING&&this.pause()),this._seeked=!0,this._videoEngine.seekToRatio(e)}}),t.prototype._hasToBeRepeated=function(){return this._repeated<this._opts.repeatTimes},t.prototype._doRepeat=function(){this._repeated+=1,this.setCurrentTime(this._opts.playFrom||0),this.play(),this.emit(\"repeat\")},t.prototype._doEnd=function(){this._opts.pauseOnEnd?this.pause():this._posterShow(),this._timeUpdateCounter=0,this.changeState(CeltraPlayerUtils.ENDED),this.emit(\"ended\"),this._opts.closeFSOnEnd&&this.isFullScreen&&this.exitFullScreen()},t.prototype._onFullscreenChange=function(){this.isFullScreen?(this.exitFullScreen(),this.isFullScreen=!1):(this.isFullScreen=!0,this.redraw())},t.prototype._toggleDesktopFSStateHandlers=function(e){var t=e+\"EventListener\",e=[\"\",\"webkit\",\"moz\",\"ms\"].some(function(e){return void 0!==document[\"on\"+e+\"fullscreenchange\"]&&(document[t](\"ms\"!==e?e+\"fullscreenchange\":\"MSFullscreenChange\",this._onFullscreenChange),!0)}.bind(this));return i[t]&&i[t](\"resize\",this.redraw),e&&!!(document.fullscreenEnabled||document.webkitFullscreenEnabled||document.mozFullScreenEnabled||document.msFullscreenEnabled)},t.prototype.playsInline=function(){return\"permanent\"!==this._opts.fullscreenMode},i.VideoPlayer=t}(window);;\n!function(){function s(e,i,t){this._videoPlayer=e,this._opts=merge({},s.DEFAULTS,t),this.isVisible=!1,this._controlsInitiallyDisabled=!1,this._isFirstPlay=!0,this._playAfterSeeking=!1,this._isMuted=!1,this._spinnerShown=!0,this._cursorHidden=!1,CeltraPlayerUtils.attachHandlers(this,this._videoPlayer,s.PLAYER_EVENTS,!0),this._changeVisibility=this._changeVisibility.bind(this),this.handleUserInteraction=this.handleUserInteraction.bind(this),this._videoControls=i(this.handleUserInteraction)}extend(s.prototype,EventEmitter),s.DEFAULTS={autohideTime:0,progressbar:!0,progressbarColour:\"#4589ce\",startInFullScreen:!1,controlsHidden:!1,fullscreenMode:\"both\",minLengthToSeek:30,muteUnmuteEnabled:!0},s.PLAYER_EVENTS={durationchange:\"onVPDurationChange\",muted:\"onVPMuted\",unmuted:\"onVPUnmuted\",progress:\"onVPProgress\",play:\"onVPPlay\",playing:\"onVPPlaying\",pause:\"onVPPause\",autoplayrejected:\"onVPAutoplayRejected\",forcemuted:\"onVPForceMuted\",ended:\"onVPEnded\",buffering:\"onVPBuffering\",timeupdate:\"onVPTimeupdate\",seekto:\"onVPSeekto\",enterfullscreen:\"onVPEnterFullscreen\",exitfullscreen:\"onVPExitFullscreen\",orientationchange:\"onVPOrientationChange\",canunmute:\"onVPCanUnmute\",playersourceloading:\"onVPPlayerSourceLoading\"},extend(s.prototype,{render:function(e){e=this._videoControls.render(e,this._opts);return this.init(),e},init:function(){this._changeVisibilityTimed(!1),this._opts.progressbar||this._videoControls.hide(\"sliderContainer\"),\"permanent\"===this._opts.fullscreenMode?(this._videoControls.hide(\"enterFullScreen\",\"exitFullScreen\"),this._videoControls.show(\"close\")):!0===this._videoPlayer.isDesktop&&!1===this._videoPlayer.dtCanFullscreen?this._videoControls.hide(\"close\",\"enterFullScreen\",\"exitFullScreen\"):(this._videoControls.hide(\"close\",\"exitFullScreen\"),this._videoControls.show(\"enterFullScreen\")),\"disabled\"===this._opts.fullscreenMode&&this._videoControls.hide(\"enterFullScreen\",\"exitFullScreen\",\"close\"),this._opts.debug?this._videoControls.show(\"monitor\"):this._videoControls.hide(\"monitor\"),this._opts.muteUnmuteEnabled?(this._videoControls.hide(\"mute\"),this._videoControls.hide(\"unMute\")):this._videoControls.disableMuteUnmuteControls(),this._videoControls.hide(\"pause\",\"replay\",\"play\"),this._opts.isAutoplay||this._videoControls.show(\"play\"),this._spinnerHide(),this._videoPlayer.isDesktop&&this._videoControls.setDTMouseMoveHandler(this._videoPlayer.getWrapperElement())},adjustSizes:function(e){e<80?this._videoControls.addClass(\"spinner\",\"video-player-spinner-small\"):this._videoControls.addClass(\"spinner\",\"video-player-spinner-big\")},reset:function(){this._controlsInitiallyDisabled=!1,this._videoControls.hide(\"pause\",\"replay\"),this._videoControls.show(\"play\"),this._spinnerHide(),defer(function(){this._videoControls.hide(\"duringPlaySet\")}.bind(this),500)},destroy:function(){this._videoControls.destroy()},handleUserInteraction:function(e,i){switch(e){case\"vidWrapper\":this._changeVisibilityTimed(!0),this._makeControlsDisapear();break;case\"wrapper\":this._videoPlayer._spinnerShown?this._changeVisibilityTimed(!0):(!this.isVisible||this._videoPlayer.ended||this._spinnerShown||i?this._changeVisibilityTimed(!0):this._videoPlayer.paused?(this._videoPlayer.play(),this.emit(\"userInteraction\",{isUserEngaged:!0})):(this._videoPlayer.pause(),this.emit(\"userInteraction\")),this._makeControlsDisapear());break;case\"mute\":this._videoPlayer.mute(),this._videoControls.pendingStateOn(\"mute\"),this._makeControlsDisapear(),this.emit(\"userInteraction\");break;case\"unMute\":this._videoPlayer.unMute(),this._videoControls.pendingStateOn(\"unMute\"),this._makeControlsDisapear(),this.emit(\"userInteraction\",{isUserEngaged:!0});break;case\"enterFullScreen\":this._videoPlayer.enterFullScreen(),this._makeControlsDisapear(),this.emit(\"userInteraction\",{isUserEngaged:!0});break;case\"exitFullScreen\":this._videoPlayer.exitFullScreen(),this._makeControlsDisapear(),this.emit(\"userInteraction\");break;case\"pause\":this._controlsInitiallyDisabled||(this._videoPlayer.pause(),this.emit(\"userInteraction\"));break;case\"play\":this._controlsInitiallyDisabled||(this.emit(\"userInteraction\",{isUserEngaged:!0}),this.emit(\"playButtonPressed\"));break;case\"replay\":this._changeVisibilityTimed(!1),this._videoPlayer.replay(),this.emit(\"userInteraction\",{isUserEngaged:!0});break;case\"close\":this._videoPlayer.close(),this.emit(\"userInteraction\")}},onVPPlayerSourceLoading:function(){this._spinnerShow()},onVPCanUnmute:function(e){},onVPMuted:function(e){this._isMuted=!0,this._videoControls.hide(\"mute\"),this._videoControls.show(\"unMute\"),this._videoControls.pendingStateOff(\"unMute\"),this._videoControls.pendingStateOff(\"mute\")},onVPUnmuted:function(e){this._isMuted=!1,this._videoControls.hide(\"unMute\"),this._videoControls.show(\"mute\"),this._videoControls.pendingStateOff(\"unMute\"),this._videoControls.pendingStateOff(\"mute\")},onVPPlay:function(){this._videoControls.hideAndShowWrapper(),this._isFirstPlay&&(this._isFirstPlay=!1,this._controlsInitiallyDisabled=!0,this._spinnerShow()),this._changeVisibility(!0),this._videoControls.hide(\"play\")},onVPPlaying:function(){var e=this._opts.autohideTime;this._videoControls.hide(\"play\",\"pause\",\"replay\"),e&&this._changeVisibilityTimed(!1,1e3*e)},onVPPause:function(){this._changeVisibilityTimed(!0),this._spinnerShown||this._videoControls.show(\"play\")},onVPAutoplayRejected:function(){this.reset()},onVPForceMuted:function(){this.onVPMuted()},onVPBuffering:function(){this._spinnerShow(),this._videoControls.hide(\"play\")},onVPEnded:function(){this._changeVisibilityTimed(!1),this._videoControls.hide(\"play\",\"pause\"),this._opts.pauseOnEnd||this._videoControls.show(\"replay\")},onVPProgress:function(e){this._progressRatio=e,this._videoControls.setProgressBarWidth(e)},onVPTimeupdate:function(e){var i=this._videoPlayer.duration;this._videoControls.setTimeBarWidth(i&&e/i||0),this._controlsInitiallyDisabled&&(this._controlsInitiallyDisabled=!1),this._videoPlayer.paused||(this._videoControls.hide(\"play\"),this._videoControls.hide(\"pause\"),this._videoControls.hide(\"replay\")),this._spinnerHide()},onVPEnterFullscreen:function(e){this._videoControls.hideAndShowWrapper(),this._videoControls.hide(\"enterFullScreen\"),this._videoControls.show(\"exitFullScreen\"),this._makeControlsDisapear()},onVPOrientationChange:function(e){this._opts.fullscreenLandscapeButtonsHack&&this._videoControls.orientationChange(function(e){e===CeltraPlayerUtils.LANDSCAPE&&this._videoPlayer.isFullScreen?this._elements.close.style.bottom=this._elements.exitFullScreen.style.bottom=this._elements.mute.style.bottom=this._elements.unMute.style.bottom=\"26px\":this._elements.close.style.bottom=this._elements.exitFullScreen.style.bottom=this._elements.mute.style.bottom=this._elements.unMute.style.bottom=\"\"})},onVPExitFullscreen:function(e){this._videoControls.hideAndShowWrapper(),this._videoControls.hide(\"exitFullScreen\"),this._videoControls.show(\"enterFullScreen\"),this._makeControlsDisapear()},onVPSeekto:function(e){this._videoControls.setTimeBarWidth(e)},_changeVisibility:function(e){this._opts.controlsHidden?(this.isVisible=!0,this._videoControls.hide(\"duringPlaySet\")):e?(this._videoControls.show(\"duringPlaySet\"),this._videoPlayer.isDesktop&&this._cursorHidden&&(this._videoControls.showCursor(this._videoPlayer.getWrapperElement()),this._cursorHidden=!1),this.isVisible=!0):(this._videoControls.hide(\"duringPlaySet\"),this._videoPlayer.isDesktop&&this._videoPlayer.isFullScreen&&(this._videoControls.hideCursor(this._videoPlayer.getWrapperElement()),this._cursorHidden=!0),this.isVisible=!1,this._videoPlayer.isDesktop&&(this._videoControls.hasMouseMoved=!0))},_changeVisibilityTimed:function(e,i){clearTimeout(this._changeVisibilityTimeout),i?this._changeVisibilityTimeout=setTimeout(this._changeVisibility.bind(this,e),i):this._changeVisibility(e)},_makeControlsDisapear:function(){var e=this._opts.autohideTime;e&&this._changeVisibilityTimed(!1,1e3*e)},_spinnerHide:function(){this._spinnerShown&&(this._spinnerShown=!1,this._videoControls.hide(\"spinner\"))},_spinnerShow:function(){!this._spinnerShown&&this._opts.canShowSpinner&&(this._spinnerShown=!0,defer(function(){this._spinnerShown&&(this._videoControls.hide(\"play\"),this._videoControls.show(\"spinner\"))}.bind(this),300))}}),this.BasicVideoControlsController=s}();;\n!function(){function o(t){this._container=null,this._userInteractionHandler=t,this.onTapHandler=CeltraPlayerUtils.deduplicate(this.onTapHandler,400,this),this.hasMouseMoved=!1}extend(o.prototype,EventEmitter),o.TEMPLATE=['<div class=\"video-basic-controls-wrapper <%= desktop %> touchable\" data-bind=\"wrapper\">','  <div class=\"video-controls-hiddable\" data-bind=\"duringPlaySet\">','    <div class=\"video-controls-button video-controls-button-shadow video-controls-button-bottom-right touchable\" data-bind=\"mute\"><div class=\"celtra-icon-mute\"></div></div>','    <div class=\"video-controls-button video-controls-button-shadow video-controls-button-bottom-right touchable\" data-bind=\"unMute\"><div class=\"celtra-icon-unmute\"></div></div>','    <div class=\"video-controls-button video-controls-button-shadow video-controls-button-top-right touchable\" data-bind=\"close\"><div class=\"celtra-icon-close\"></div></div>','    <div class=\"video-controls-button video-controls-button-shadow video-controls-button-top-right touchable\" data-bind=\"enterFullScreen\"><div class=\"celtra-icon-enter-full-screen\"></div></div>','    <div class=\"video-controls-button video-controls-button-shadow video-controls-button-top-right touchable\" data-bind=\"exitFullScreen\"><div class=\"celtra-icon-exit-full-screen\"></div></div>','    <div class=\"video-controls-slider-container touchable\"  data-bind=\"sliderContainer\">','        <div class=\"video-controls-progress-bar\" data-bind=\"progressBar\"></div>','        <div class=\"video-controls-time-bar\" data-bind=\"timeBar\"></div>',\"    </div>\",\"  </div>\",'  <div class=\"video-controls-unhiddable\" data-bind=\"outOfPlaySet\">','    <div class=\"video-sizable\"><div class=\"video-player-spinner-big\" data-bind=\"spinner\"></div></div>','    <div class=\"center-button touchable\" data-bind=\"pause\"><div class=\"celtra-icon-pause\"></div></div>','    <div class=\"center-button touchable\" data-bind=\"play\"><div class=\"celtra-icon-play\"></div></div>','    <div class=\"center-button touchable\" data-bind=\"replay\"><div class=\"celtra-icon-replay\"></div></div>',\"  </div>\",\"</div>\"],extend(o.prototype,{render:function(t,n){var e,i=n.isMasterVideo?[]:[\"play\",\"replay\"];return this._container||(e={},CeltraPlayerUtils.createDom(t,o.TEMPLATE,{desktop:windows(\"10\")?\"video-controls-desktop ie-click-event\":desktop()?\"video-controls-desktop\":\"\"},this._getWrapper(e)),this._container=e.wrapper,n.isMasterVideo&&this.hide(\"outOfPlaySet\"),this._container.querySelector(\"[data-bind=progressBar]\").style.backgroundColor=n.progressbarColour,this._container.querySelector(\"[data-bind=timeBar]\").style.backgroundColor=n.progressbarColour,(t=\"undefined\"!=typeof creative?creative:window.creative)&&(e=\"MRAIDAdapter\"===t.adapter.constructor.name,\"crop\"===n.fitting&&e&&(this._container.querySelector(\"[data-bind=exitFullScreen]\").style.top=\"50px\")),i.forEach(function(t){var e,i;n[t]&&(e=n[t],i=this._getElements(t)[0],removeClass(i,\"center-button\"),addClass(i,\"custom-button\"),i.innerHTML='<img src=\"'+e.getUrl()+'\" data-bind=\"'+t+'\" style=\"width: '+e.width+\"px; height: \"+e.height+'px;\" />')}.bind(this)),this._container.addEventListener(\"tap\",this.onTapHandler)),this._container},show:function(){CeltraPlayerUtils.show.apply(null,this._getElements.apply(this,arguments))},hide:function(){CeltraPlayerUtils.hide.apply(null,this._getElements.apply(this,arguments))},showCursor:function(t){CeltraPlayerUtils.showCursor([t])},hideCursor:function(t){CeltraPlayerUtils.hideCursor([t])},setProgressBarWidth:function(t){t=Math.min(t,1),this._container&&this._container.querySelector(\"[data-bind=progressBar]\")&&(this._container.querySelector(\"[data-bind=progressBar]\").style.width=100*t+\"%\")},setTimeBarWidth:function(t){t=Math.min(t,1),this._container&&this._container.querySelector(\"[data-bind=timeBar]\")&&(this._container.querySelector(\"[data-bind=timeBar]\").style.width=100*t+\"%\")},hideAndShowWrapper:function(){this.hide(\"wrapper\"),defer(function(){this.show(\"wrapper\")}.bind(this))},disableMuteUnmuteControls:function(){this._getElements(\"mute\")[0].innerHTML=\"\",this._getElements(\"unMute\")[0].innerHTML=\"\"},pendingStateOn:function(t){addClass(this._getElements(t)[0],\"video-controls-pending\")},pendingStateOff:function(t){this._container&&removeClass(this._getElements(t)[0],\"video-controls-pending\")},addClass:function(t,e){t=this._getElements(t)[0];t&&addClass(t,e)},removeClass:function(t,e){t=this._getElements(t)[0];t&&removeClass(t,e)},orientationChange:function(t){t()},onTapHandler:function(t){t.stopPropagation();t=t.target.getAttribute(\"data-bind\");t&&this._userInteractionHandler(t)},setDTMouseMoveHandler:function(e){e.addEventListener(\"mousemove\",function(t){t.stopPropagation();t=t.target,t=t!==e?t.getAttribute(\"data-bind\"):\"vidWrapper\";this.hasMouseMoved?this.hasMouseMoved=!1:\"wrapper\"!==t&&\"vidWrapper\"!==t||this._userInteractionHandler(t,!0)}.bind(this))},_getWrapper:function(i){return function(t){var e=t.getAttribute(\"data-bind\");e&&(i[e]=t)}},_getElements:function(){if(this._container){for(var t,e=[],i=0;i<arguments.length;i++)(t=this._container.querySelector(\"[data-bind=\"+arguments[i]+\"]\"))&&e.push(t);return e}},destroy:function(){this._container&&(this._container.removeEventListener(\"tap\",this.onTapHandler),CeltraPlayerUtils.removeElements(this._container),this._container=null)}}),this.BasicVideoControls=o}();;\n!function(){function s(t,e,i){this._videoPlayer=t,this._opts=i,this._uiTheme=this._opts.uiTheme,this._customIcons=this._opts.customIcons,this._autohideTime=this._opts.autohideTime,this._disableControls=this._opts.disableControls,this._initialLongitude=this._opts.initialLongitude,this._resetToInitialOrientation=this._opts.resetToInitialOrientation,this._state=new StateObject({controlsVisible:!0,vignetteEnabled:!1,celtraSignatureEnabled:!1,introAnimationEnabled:!1,playIconEnabled:!1,loaderIconEnabled:!1,countdownIconEnabled:!1,resumeIconEnabled:!1,replayIconEnabled:!1,radarIconEnabled:!1,permissionEnabled:!1,fullscreenIconEnabled:!1,inFullscreenMode:!1,soundIconEnabled:!1,isMuted:!1}),t=!(this._opts.loaderIconEnabled||this._opts.countdownIconEnabled||this._opts.resumeIconEnabled||this._opts.replayIconEnabled),this._opts.soundBoxToEdge=this._opts.soundIconEnabled&&!this._opts.fullscreenIconEnabled,this._opts.radarBoxToEdge=this._opts.radarIconEnabled&&t,this._couldHideIntroAnimationSpinner=this._opts.playIconEnabled,this._firstPlayingEmitted=!1,this._initialLoadingInProgress=!1,i=window.DeviceMotionEvent&&window.DeviceMotionEvent.requestPermission,this._state.permissionEnabled=this._opts.permissionEnabled&&i,this._state.vignetteEnabled=this._opts.vignetteEnabled,this._state.celtraSignatureEnabled=this._opts.celtraSignatureEnabled,this._state.introAnimationEnabled=this._opts.introAnimationEnabled,defer(this._handleIntroAnimation.bind(this),1800),this._showPlayIcon(),this._state.isMuted=!0,CeltraPlayerUtils.attachHandlers(this,this._videoPlayer,s.PLAYER_EVENTS,!0),this._changeVisibility=this._changeVisibility.bind(this),this.handleUserInteraction=this.handleUserInteraction.bind(this),this._updateInterface=this._updateInterface.bind(this),this._videoControls=e(this.handleUserInteraction),Ticker.frame(this._updateInterface,\"render\")}inherit(s,BasicVideoControlsController),s.PLAYER_EVENTS={muted:\"onVPMuted\",unmuted:\"onVPUnmuted\",play:\"onVPPlay\",playing:\"onVPPlaying\",pause:\"onVPPause\",autoplayrejected:\"onVPAutoplayRejected\",forcemuted:\"onVPForceMuted\",ended:\"onVPEnded\",buffering:\"onVPBuffering\",timeupdate:\"onVPTimeupdate\",enterfullscreen:\"onVPEnterFullscreen\",exitfullscreen:\"onVPExitFullscreen\",playersourceloading:\"onVPPlayerSourceLoading\"},extend(s.prototype,{render:function(t){return this._videoControls.render(t,this._opts)},adjustSizes:function(t){},reset:function(){},destroy:function(){Ticker.removeFrame(this._updateInterface,\"render\"),this._videoControls.destroy()},handleUserInteraction:function(t,e){if(Logger(\"VideoUI\").log(\"Target: \"+t),!this._disableControls)if(this._state.controlsVisible){switch(t){case\"wrapper\":if(this._initialLoadingInProgress)return void this._makeControlsAppear();if(this._opts.hasTapTriggers)return void this._opts.tapCallback();this._videoPlayer.ended?this._makeControlsAppear():this._videoPlayer.paused?(this._videoPlayer.play(),this.emit(\"userInteraction\",{isUserEngaged:!0})):(this._videoPlayer.pause(),this.emit(\"userInteraction\"));break;case\"sound\":if(!this._state.soundIconEnabled)return;Logger(\"VideoUI\").log(\"Target executed \"+t),this._state.isMuted?(this._videoPlayer.unMute(),this.emit(\"userInteraction\",{isUserEngaged:!0})):(this._videoPlayer.mute(),this.emit(\"userInteraction\"));break;case\"fullscreen\":if(!this._state.fullscreenIconEnabled)return;Logger(\"VideoUI\").log(\"Target executed \"+t),this._state.inFullscreenMode?(this._videoPlayer.exitFullScreen(),this.emit(\"userInteraction\")):(this._videoPlayer.enterFullScreen(),this.emit(\"userInteraction\",{isUserEngaged:!0}));break;case\"play\":case\"custom_play\":if(!this._state.playIconEnabled)return;Logger(\"VideoUI\").log(\"Target executed \"+t),this.emit(\"userInteraction\",{isUserEngaged:!0}),this.emit(\"playButtonPressed\"),this._state.playIconEnabled=!1;break;case\"resume\":if(!this._state.resumeIconEnabled)return;Logger(\"VideoUI\").log(\"Target executed \"+t),this._videoPlayer.play(),this.emit(\"userInteraction\",{isUserEngaged:!0});break;case\"replay\":case\"custom_replay\":if(!this._state.replayIconEnabled)return;Logger(\"VideoUI\").log(\"Target executed \"+t),this._videoPlayer.replay(),this.emit(\"userInteraction\",{isUserEngaged:!0});break;case\"radar\":if(!this._state.radarIconEnabled)return;Logger(\"VideoUI\").log(\"Target executed \"+t),this._resetToInitialOrientation(),this.emit(\"userInteraction\",{isUserEngaged:!0});break;case\"permission\":this._requestPermission()}this._makeControlsDisappear()}else this._makeControlsAppear(),this._makeControlsDisapear()},onVPPlayerSourceLoading:function(){},onVPMuted:function(t){this._state.isMuted=!0},onVPUnmuted:function(t){this._state.isMuted=!1},onVPPlay:function(){this._initialLoadingInProgress=!0,this._state.loaderIconEnabled=this._opts.loaderIconEnabled,this._state.radarIconEnabled=this._opts.radarIconEnabled,this._state.resumeIconEnabled=!1,this._state.replayIconEnabled=!1,this._makeControlsAppear()},onVPPlaying:function(){this._initialLoadingInProgress=!1,this._firstPlayingEmitted||(this._handleIntroAnimation(),this._firstPlayingEmitted=!0),this._state.playIconEnabled=!1,this._state.loaderIconEnabled=!1,this._state.resumeIconEnabled=!1,this._state.replayIconEnabled=!1,this._state.fullscreenIconEnabled=this._opts.fullscreenIconEnabled,this._state.soundIconEnabled=this._opts.soundIconEnabled,this._state.countdownIconEnabled=this._opts.countdownIconEnabled,this._makeControlsDisapear()},onVPPause:function(){this._state.resumeIconEnabled=this._opts.resumeIconEnabled&&!this._videoPlayer.ended,this._state.resumeIconEnabled&&(this._customIcons.custom_play?(this._state.playIconEnabled=!0,this._state.resumeIconEnabled=!1):this._state.countdownIconEnabled=!1),this._makeControlsAppear()},onVPAutoplayRejected:function(){this._state.playIconEnabled=!0},onVPForceMuted:function(){this._state.isMuted=!0},onVPBuffering:function(){},onVPEnded:function(){this._state.countdownIconEnabled=!1,this._state.playIconEnabled=!1,this._state.resumeIconEnabled=!1,this._state.replayIconEnabled=this._opts.replayIconEnabled,this._makeControlsAppear()},onVPTimeupdate:function(t){this._videoControls.updateCountdown(1e3*t)},onVPEnterFullscreen:function(t){this._state.inFullscreenMode=!0,this._state.loaderIconEnabled=!1,this._makeControlsDisappear()},onVPExitFullscreen:function(t){this._state.inFullscreenMode=!1,this._makeControlsDisappear()},_requestPermission:function(t){window.DeviceMotionEvent&&window.DeviceMotionEvent.requestPermission&&window.DeviceMotionEvent.requestPermission().then(function(t){\"granted\"!=t&&alert(\"Device orientation or motion access is disallowed for this page.\")}),this._state.permissionEnabled=!1,this._requestPermission=noop},_showPlayIcon:function(){defer(function(){var t=!this._initialLoadingInProgress&&!this._firstPlayingEmitted&&(this._opts.playIconEnabled||this._opts.showPlayIconAsFallback)&&!this._state.introAnimationEnabled;this._state.playIconEnabled=!!t}.bind(this),400)},_updateInterface:function(){var t;this._state.isDirty(\"vignetteEnabled\")&&this._state.vignetteEnabled&&this._videoControls.show(\"vignette\"),this._state.isDirty(\"celtraSignatureEnabled\")&&this._state.celtraSignatureEnabled&&this._videoControls.show(\"signature\"),this._disableControls?Ticker.removeFrame(this._updateInterface,\"render\"):(this._state.isDirty(\"controlsVisible\")&&(this._state.controlsVisible?this._videoControls.show(\"hideable_controls\"):this._videoControls.hide(\"hideable_controls\")),this._state.isDirty(\"introAnimationEnabled\")&&(this._state.introAnimationEnabled?this._videoControls.show(\"spinner\"):this._videoControls.hide(\"spinner\")),this._state.isDirty(\"playIconEnabled\")&&(t=this._customIcons.custom_play?\"custom_play\":\"play\",this._state.playIconEnabled?this._videoControls.show(t):this._videoControls.hide(t)),this._state.isDirty(\"loaderIconEnabled\")&&(this._state.loaderIconEnabled?this._videoControls.show(\"loader\"):this._videoControls.hide(\"loader\")),this._state.isDirty(\"fullscreenIconEnabled\")&&this._state.fullscreenIconEnabled&&this._videoControls.show(\"fullscreen\"),this._state.isDirty(\"inFullscreenMode\")&&(this._state.inFullscreenMode?this._videoControls.switchToInFullscreenMode():this._videoControls.switchToInlineMode()),this._state.isDirty(\"soundIconEnabled\")&&this._state.soundIconEnabled&&this._videoControls.show(\"sound\"),this._state.isDirty(\"isMuted\")&&(this._state.isMuted?this._videoControls.showUnmuteButton():this._videoControls.showMuteButton()),this._state.isDirty(\"countdownIconEnabled\")&&(this._state.countdownIconEnabled?(this._videoControls.startCountdown(1e3*this._videoPlayer.getDuration(),this._uiTheme),this._videoControls.show(\"countdown\")):this._videoControls.hide(\"countdown\")),this._state.isDirty(\"resumeIconEnabled\")&&(this._state.resumeIconEnabled?this._videoControls.show(\"resume\"):this._videoControls.hide(\"resume\")),this._state.isDirty(\"replayIconEnabled\")&&(t=this._customIcons.custom_replay?\"custom_replay\":\"replay\",this._state.replayIconEnabled?this._videoControls.show(t):this._videoControls.hide(t)),this._state.isDirty(\"radarIconEnabled\")&&(this._state.radarIconEnabled?(this._videoControls.setRadarInitialLongitude(this._initialLongitude),this._videoControls.show(\"radar\")):this._videoControls.hide(\"radar\")),this._state.isDirty(\"permissionEnabled\")&&(this._state.permissionEnabled?this._videoControls.show(\"permission\"):this._videoControls.hide(\"permission\")),this._state.radarIconEnabled&&(t=180/Math.PI,t=this._videoPlayer.getOrientation().lon*t,this._videoControls.setRadarOrientation(t)),this._state.markClean())},_changeVisibility:function(t){this._disableControls||(this._state.controlsVisible=t)},_changeVisibilityTimed:function(t,e){clearTimeout(this._changeVisibilityTimeout),e?this._changeVisibilityTimeout=setTimeout(this._changeVisibility.bind(this,t),e):this._changeVisibility(t)},_makeControlsDisappear:function(){this._autohideTime&&this._changeVisibilityTimed(!1,1e3*this._autohideTime)},_makeControlsAppear:function(){this._changeVisibilityTimed(!0)},_handleIntroAnimation:function(){this._couldHideIntroAnimationSpinner&&(this._state.introAnimationEnabled=!1,this._showPlayIcon()),this._couldHideIntroAnimationSpinner=!0}}),this.VideoControlsController=s}();;\n!function(){function s(t){this._container=null,this._countdown=null,this._userInteractionHandler=t,this.onTapHandler=CeltraPlayerUtils.deduplicate(this.onTapHandler,400,this)}inherit(s,BasicVideoControls),s.TEMPLATE=['<div class=\"video-controls-wrapper <%= desktop %> touchable\" data-bind=\"wrapper\">','<div id=\"vignette\" data-bind=\"vignette\"></div>','<div id=\"bottom-tagline\" data-bind=\"signature\">Powered by Celtra</div>','<div id=\"hideable-controls\" data-bind=\"hideable_controls\">','<div id=\"top_left_box\" data-bind=\"top_left_box\">','<div id=\"loader\" data-bind=\"loader\">','<div class=\"loader_rotator\">','<svg class=\"loader-circular\" viewBox=\"0 0 32 32\"><circle class=\"loader-path\" cx=\"16\" cy=\"16\" r=\"12\" fill=\"none\" stroke-width=\"2\" /></svg>','<svg class=\"loader-circular-bg\" viewBox=\"0 0 32 32\"><circle class=\"loader-path-bg\" cx=\"16\" cy=\"16\" r=\"12\"  fill=\"none\" stroke-width=\"6\" /></svg>',\"</div>\",\"</div>\",'<div id=\"countdown\" data-bind=\"countdown\"></div>','<div id=\"replay\" class=\"touchable\" data-bind=\"replay\">','<div class=\"replayer\">','<svg viewBox=\"0 0 32 32\"><path d=\"M25.9,6.1l-1.4-1.4l-4.2,4.2l1.4,1.4c3.1,3.1,3.1,8.2,0,11.3C20.1,23.2,18.1,24,16,24s-4.1-0.8-5.7-2.3 c-1.8-1.8-2.6-4.2-2.3-6.5l1.3,1.3l4.4-13.2L0.6,7.6l2.3,2.3l0.4,0.4C0.9,15.5,2,21.8,6.1,25.9C8.7,28.5,12.3,30,16,30 s7.3-1.5,9.9-4.1C31.4,20.4,31.4,11.6,25.9,6.1z\" class=\"st0\"/><path id=\"path-1_1_\" d=\"M23.1,8.9c3.9,3.9,3.9,10.2,0,14.1S12.9,26.9,9,23c-3.2-3.2-3.8-7.9-1.8-11.7l1.4,1.4l2.1-6.4 L4.3,8.5l1.4,1.4C3,14.5,3.6,20.5,7.5,24.5c4.7,4.7,12.3,4.7,17,0s4.7-12.3,0-17L23.1,8.9z\" class=\"st1\"/></svg>',\"</div>\",\"</div>\",'<div id=\"resume\" class=\"touchable\" data-bind=\"resume\">','<div class=\"resumebg\"></div>','<div class=\"resume_ring\"></div>','<div class=\"resume_triangle\"><svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 320 320\"><path class=\"st1\" d=\"M120 100L120 220 230 160 120 100z\"/></svg></div>',\"</div>\",\"</div>\",'<div id=\"inner_top_left_box\" data-bind=\"inner_top_left_box\">','<div id=\"radar\" class=\"touchable\" data-bind=\"radar\">','<div class=\"radarbg\"></div>','<div class=\"radardot\"></div>','<div class=\"radarring\"></div>','<div class=\"radarpizza\" data-bind=\"radar_orientation\"></div>','<div class=\"radardotYcontainer\" data-bind=\"radar_longitude\">','<div class=\"radarYdot\"></div>',\"</div>\",\"</div>\",\"</div>\",'<div id=\"inner_top_left_center_box\" data-bind=\"inner_top_left_center_box\">','<div id=\"permission\" class=\"touchable\" data-bind=\"permission\">','<div class=\"permission-ring\">','<div class=\"permission-content\">enable motion</div>',\"</div>\",\"</div>\",\"</div>\",'<div id=\"center_box\">','<div id=\"spinner360\" data-bind=\"spinner\">','<div class=\"t360\">','<svg xmlns=\"http://www.w3.org/2000/svg\" viewBox=\"0 0 100 100\"><path d=\"M55.9,55.2c0,3.6-2.4,5.9-5.9,5.9c-3.8,0-5.9-2.9-5.9-5.8c0-3.6,2.4-5.9,5.9-5.9 C53.4,49.4,55.9,51.7,55.9,55.2 M51.6,43.6c-0.7,0-1.4,0-2.1,0.1l8.4-12h-8.7l-8.7,13.2c-2.4,3.7-3.7,6.4-3.7,10.3 c0,6.8,5.7,12.1,13.2,12.1c7.6,0,13.2-5.1,13.2-12.4C63.2,48.6,58,43.6,51.6,43.6\" class=\"st1\"/><path d=\"M30,48.7c2.9-1.3,4.6-4.1,4.6-7.4c0-5.8-4.5-9.6-11.3-9.6c-5.5,0-10.1,3.3-11.4,7.9l-0.1,0.7 l6.6,1.7l0.1-0.7c0.7-2.4,2.4-3.7,4.6-3.7s4.6,1.3,4.6,4.1c0,2.9-2.1,4.2-6.4,4.2h-2.1v5.7h2.1c1.2,0,7.4,0.3,7.4,4.9 c0,3.6-2.8,4.7-5.4,4.7c-2.9,0-5.1-1.6-5.9-4.1l-0.1-0.7l-6.6,2l0.3,0.7c1.7,5.3,6.2,8.2,12.6,8.2c6.1,0,12.2-3.7,12.2-10.7 C35.5,53.1,33.4,50,30,48.7\" class=\"st1\"/><path d=\"M94.1,40c-1.3,0-2.4-1.1-2.4-2.4s1.1-2.4,2.4-2.4s2.4,1.1,2.4,2.4S95.4,40,94.1,40 M94.1,31.7 c-3.3,0-5.9,2.6-5.9,5.9s2.6,5.9,5.9,5.9s5.9-2.6,5.9-5.9S97.4,31.7,94.1,31.7\" class=\"st1\"/><path d=\"M77,61.2c-5,0-5.4-8.9-5.4-11.7c0-2,0.3-11.8,5.4-11.8c4.9,0,5.3,9.1,5.3,11.8 C82.4,52.3,82,61.2,77,61.2 M85.7,35.7c-2.1-2.6-5-3.9-8.6-3.9c-7.9,0-12.5,6.6-12.5,17.8C64.5,60.7,69.2,67.3,77,67.3 s12.5-6.6,12.5-17.8c0-1.3-0.1-2.6-0.3-3.9C86.8,44.1,84.2,40.3,85.7,35.7z\" class=\"st1\"/></svg>',\"</div>\",'<div class=\"disk\"><div class=\"ring spin\"></div></div>','<div class=\"circleBg\"></div>','<div class=\"outliner\"></div>',\"</div>\",'<div id=\"play\" class=\"touchable\" data-bind=\"play\">','<div class=\"circleBg\"></div>','<div class=\"playtriangle\"></div>','<div class=\"outliner\"></div>',\"</div>\",\"</div>\",'<div id=\"custom_center_box\">','<div class=\"touchable\" data-bind=\"custom_play\"></div>','<div class=\"touchable\" data-bind=\"custom_replay\"></div>',\"</div>\",'<div id=\"inner_bottom_right_box\" data-bind=\"inner_bottom_right_box\">','<div id=\"sound_loader\" class=\"\" data-bind=\"sound_loader\">','<div class=\"sound_loader_rotator\">','<svg class=\"sound-loader-circular-bg\" viewBox=\"0 0 32 32\"><circle class=\"sound-loader-path-bg\"cx=\"16\"cy=\"16\"r=\"10\"fill=\"none\"stroke-width=\"6\"/></svg>','<svg class=\"sound-loader-circular\" viewBox=\"0 0 32 32\"><circle class=\"sound-loader-path\"cx=\"16\"cy=\"16\"r=\"10\"fill=\"none\"stroke-width=\"2\"/></svg>',\"</div>\",\"</div>\",'<div id=\"sound\" class=\"touchable\" data-bind=\"sound\">','<div id=\"unmute\" class=\"switch_off\" data-bind=\"unmute_icon\">','<svg viewBox=\"0 0 32 32\"><path  d=\"M27.5 23.3l1.1 1.1-4.2 4.2-4.4-4.4v4.3L11 22H4V10h1.8L3.2 7.4l4.2-4.2 5.5 5.5L20 3.5V11l5-5 1.5 1.5C28.8 9.7 30 12.8 30 16c0 2.7-.9 5.2-2.5 7.3z\" opacity=\".4\"/><path class=\"st1\" d=\"M25.1 8.9l-1.4 1.4c1.4 1.4 2.3 3.4 2.3 5.7 0 1.7-.5 3.2-1.3 4.4l1.4 1.4c1.2-1.6 1.9-3.7 1.9-5.9 0-2.7-1.1-5.2-2.9-7zm-3.4 8.6l1.5 1.5c.5-.9.8-1.9.8-2.9 0-1.6-.7-3.2-1.8-4.2l-1.4 1.4c.7.7 1.2 1.7 1.2 2.8 0 .4-.1.9-.3 1.4zM18 7.5l-3.6 2.7 3.6 3.6zM6 12v8h6l6 4.5v-2.3L7.8 12z\"/><path transform=\"rotate(-45.001 15.9 15.9)\" class=\"st1\" d=\"M14.9 2.9h2v26h-2z\"/></svg>',\"</div>\",'<div id=\"mute\" class=\"switch_off\" data-bind=\"mute_icon\">','<svg viewBox=\"0 0 32 32\"><path d=\"M26.5 7.5L25 6l-5 5V3.5L11 10H4v12h7l9 6.5V21l5 5 1.5-1.5c2.2-2.2 3.5-5.3 3.5-8.5s-1.2-6.3-3.5-8.5z\" opacity=\".4\"/><path d=\"M18 7.5L12 12H6v8h6l6 4.5v-17zm4.2 4.3l-1.4 1.4c.7.7 1.2 1.7 1.2 2.8s-.4 2.1-1.2 2.8l1.4 1.4c1.1-1.1 1.8-2.6 1.8-4.2s-.7-3.2-1.8-4.2zm2.9-2.9l-1.4 1.4c1.4 1.4 2.3 3.4 2.3 5.7s-.9 4.2-2.3 5.7l1.4 1.4c1.8-1.8 2.9-4.3 2.9-7.1s-1.1-5.3-2.9-7.1z\" fill=\"#fff\"/></svg>',\"</div>\",\"</div>\",\"</div>\",'<div id=\"bottom_right_box\" data-bind=\"bottom_right_box\">','<div id=\"fullscreen_arrow\" class=\"touchable\" data-bind=\"fullscreen\">','<div class=\"topright_arrow corner\" data-bind=\"topright_arrow\">','<svg viewBox=\"0 0 32 32\"><path id=\"Shape\" d=\"M16 6L6 16 13 16 13 22 19 22 19 16 26 16z\" class=\"st0\"/><path id=\"path-1_2_\" d=\"M17,14v6h-2v-6h-4l5-5l5,5H17z\" class=\"st1\"/></svg>',\"</div>\",'<div class=\"botleft_arrow corner\" data-bind=\"botleft_arrow\">','<svg viewBox=\"0 0 32 32\"><path id=\"Shape\" d=\"M16 6L6 16 13 16 13 22 19 22 19 16 26 16z\" class=\"st0\"/><path id=\"path-1_2_\" d=\"M17,14v6h-2v-6h-4l5-5l5,5H17z\" class=\"st1\"/></svg>',\"</div>\",\"</div>\",\"</div>\",\"</div>\",\"</div>\"],s.prototype.render=function(t,i){return this._container||(this.elements={},CeltraPlayerUtils.createDom(t,s.TEMPLATE,{desktop:celtra.windows(\"10\")?\"video-controls-desktop ie-click-event\":celtra.desktop()?\"video-controls-desktop\":\"\"},this._getWrapper(this.elements)),this._container=this.elements.wrapper,i.enableVerticalVideoUIOffset&&addClass(this._container,\"vertical-UI-offset\"),i.radarBoxToEdge&&(addClass(this.elements.inner_top_left_box,\"align_to_left\"),addClass(this.elements.top_left_box,\"disable\")),i.soundBoxToEdge&&(addClass(this.elements.inner_bottom_right_box,\"align_to_right\"),addClass(this.elements.bottom_right_box,\"disable\")),\"dark\"===i.uiTheme&&addClass(this._container,\"dark-theme\"),[\"custom_play\",\"custom_replay\"].forEach(function(t){var s,e;i.customIcons&&i.customIcons[t]&&(s=i.customIcons[t],e=this._getElements(t)[0],addClass(e,\"custom-button\"),e.innerHTML='<img src=\"'+s.getUrl()+'\" class=\"touchable\" data-bind=\"'+t+'\" style=\"width: '+s.width+\"px; height: \"+s.height+'px;\" />')}.bind(this)),this._container.addEventListener(\"tap\",this.onTapHandler)),this._container},s.prototype.setRadarInitialLongitude=function(t){this.elements.radar_longitude.style.transform=\"translate3d(-50%, -50%, 0) rotate(\"+(90+t)+\"deg)\"},s.prototype.setRadarOrientation=function(t){this.elements.radar_orientation.style.transform=\"translate3d(-50%, -50%, 0) rotate(\"+t+\"deg)\"},s.prototype._removeAnimationClasses=function(){removeClass(this.elements.play,\"hide\"),removeClass(this.elements.spinner,\"hide\")},s.prototype.switchToInFullscreenMode=function(){this._removeAnimationClasses(),removeClass(this.elements.topright_arrow,\"toprightANIMA\"),removeClass(this.elements.botleft_arrow,\"botleftANIMA\"),addClass(this.elements.topright_arrow,\"toprightANIMAout\"),addClass(this.elements.botleft_arrow,\"botleftANIMAout\")},s.prototype.switchToInlineMode=function(){this._removeAnimationClasses(),removeClass(this.elements.topright_arrow,\"toprightANIMAout\"),removeClass(this.elements.botleft_arrow,\"botleftANIMAout\"),addClass(this.elements.topright_arrow,\"toprightANIMA\"),addClass(this.elements.botleft_arrow,\"botleftANIMA\")},s.prototype.showMuteButton=function(){removeClass(this.elements.mute_icon,\"switch_on\"),removeClass(this.elements.mute_icon,\"switch_off\"),removeClass(this.elements.unmute_icon,\"switch_on\"),removeClass(this.elements.unmute_icon,\"switch_off\"),addClass(this.elements.mute_icon,\"switch_on\"),addClass(this.elements.unmute_icon,\"switch_off\")},s.prototype.showUnmuteButton=function(){removeClass(this.elements.mute_icon,\"switch_on\"),removeClass(this.elements.mute_icon,\"switch_off\"),removeClass(this.elements.unmute_icon,\"switch_on\"),removeClass(this.elements.unmute_icon,\"switch_off\"),addClass(this.elements.mute_icon,\"switch_off\"),addClass(this.elements.unmute_icon,\"switch_on\")},s.prototype.startCountdown=function(t,s){this._countdown||(this._countdown=CountdownProvider.create(!0,this.elements.countdown,t,\"countdown\",{countdownType:\"normal\",bgCircleColor:\"dark\"===s?\"rgba(255,255,255,1)\":\"rgba(0,0,0,.4)\",barColor:\"dark\"===s?\"rgba(0,0,0,.4)\":\"rgba(255,255,255,1)\"}),this._countdown.show())},s.prototype.updateCountdown=function(t){this._countdown&&this._countdown.setCurrentTime(t)},s.prototype.show=function(){for(var t,s=this._getElements.apply(this,arguments),e=0;e<s.length;e+=1)(t=s[e])&&(removeClass(t,\"hide\"),addClass(t,\"show\"))},s.prototype.hide=function(){for(var t,s=this._getElements.apply(this,arguments),e=0;e<s.length;e+=1)(t=s[e])&&(removeClass(t,\"show\"),addClass(t,\"hide\"))},s.prototype.destroy=function(){s.uber.destroy.call(this),this._countdown&&this._countdown.destroy()},this.VideoControls=s}();;\n!function(){function i(e,t,n){this.corsLengthHack=celtra.webkit(\"534.30\"),this.skipInternalCacheHack=ios(),this.opts=merge({},i.DEFAULTS,n),this._getChunkLength=t,this.corsLengthHack?this._src=i.withCorsLengthHack(e):this._src=e,this._xhr=null,this._requestByteHandle=null,this._requestNumber=0,this.fileLength=null,this.nextFrom=0,this._loading=!1}extend(i.prototype,EventEmitter),i.withCorsLengthHack=function(e){var t=-1!==e.indexOf(\"?\")?\"&\":\"?\";return e+t+\"corsLengthHack=1\"},i.prototype.load=function(){this._loading||this.fileLength&&this.nextFrom>=this.fileLength-1||0<this._getChunkLength(this._requestNumber)&&(this._loading=!0,this._requestBytes(this._getChunkLength(this._requestNumber),this._onLoad),this._requestNumber++)},i.prototype._requestBytes=function(e,t){var n=this.nextFrom,e=this.fileLength?Math.min(this.nextFrom+e,this.fileLength):this.nextFrom+e,i=this._xhr=new XMLHttpRequest;this.corsLengthHack&&(this.from=n,this.to=e),i.open(\"GET\",this._src,!0),this.skipInternalCacheHack&&i.setRequestHeader(\"Cache-control\",\"no-cache\"),i.setRequestHeader(\"Range\",\"bytes=\"+(n||0)+\"-\"+(e||\"\")),i.responseType=\"arraybuffer\",i.onload=this._onLoad.bind(this),this.nextFrom=e,i.send(),this._lastRequestTime=Date.now()},i.prototype._onLoad=function(e){this._loading=!1,this._xhr=null;var t,n,e=e.target;200<=e.status&&e.status<400?(t=this._getContentRangeParts(e),n=e.response,this.fileLength=t.fileLength,this.emit(\"data\",{data:new Uint8Array(n,0,Math.min(t.receivedTo,t.fileLength)-t.receivedFrom),receivedFrom:t.receivedFrom,receivedTo:t.receivedTo,fileLength:t.fileLength}),t.receivedTo>=t.fileLength-1?this.emit(\"loadend\"):this.load()):this.emit(\"error\",e.status)},i.prototype._getContentRangeParts=function(e){return this.corsLengthHack?(contentLength=e.getResponseHeader(\"Content-Type\").split(\";\")[1],{receivedFrom:this.from,receivedTo:this.to,fileLength:parseInt(contentLength,10)}):(e=e.getResponseHeader(\"Content-Range\"),e=/(\\d+)-(\\d+)\\/(\\d+)$/.exec(e),{receivedFrom:parseInt(e[1],10),receivedTo:parseInt(e[2],10),fileLength:parseInt(e[3],10)})},i.prototype.destroy=function(){clearTimeout(this._requestByteHandle),this._xhr&&this._xhr.abort(),this._xhr=null},window.NetStream=i}();;\n!function(){function t(t,e){this._opts=e||{},this.buffer={writePos:0,bytes:null},this.fileLength=0,this.receivedTo=0,this.doneBuffering=!1,this.onLoad=this.onLoad.bind(this),t&&(t.on(\"data\",this.onLoad),t.on(\"loadend\",function(){this._opts.onLoadend&&this._opts.onLoadend(),this.doneBuffering=!0,this.emit(\"loadend\")}.bind(this)))}extend(t.prototype,EventEmitter),t.prototype.onLoad=function(t){var e;this.fileLength=t.fileLength,this.receivedTo=t.receivedTo,this.buffer.bytes||(e=new Uint8Array(t.fileLength),this.buffer.bytes=e.subarray(0),this.buffer.writePos=0),this.buffer.bytes.set(t.data,this.buffer.writePos),this.buffer.writePos+=t.data.byteLength,this.emit(\"data\",t)},t.prototype.getAllData=function(){0<this.receivedTo&&this.emit(\"data\",{data:this.buffer.bytes,fileLength:this.fileLength,receivedFrom:0,receivedTo:this.receivedTo})},window.Accumulator=t}();;\n!function(){this.Html5VideoEngine=function(e,t){var n=merge({},{playsInline:!1,maxTimeupdatesPerSecond:15},t.options),t=(n.startMuted=!!t.startMuted,n.crossOrigin=!!t.crossOrigin,n.videoElement),r=!!t,i=r?t:document.createElement(\"video\"),t=i,o=n;t.setAttribute(\"x-celtra-media\",\"\"),o.playsInline&&(t.setAttribute(\"webkit-playsinline\",\"\"),t.setAttribute(\"playsinline\",\"\"),o.startMuted&&(t.muted=!0)),o.crossOrigin&&(t.crossOrigin=\"anonymous\"),t.preload=o.preload?\"auto\":\"none\",CeltraPlayerUtils.setMediaElementSource(document,t,e);for(var u={},a=(extend(u,EventEmitter),function(e){u.emit(e.type,e)}.bind(this)),d=[\"loadstart\",\"loadedmetadata\",\"durationchange\",\"progress\",\"canplay\",\"play\",\"playing\",\"pause\",\"ended\",\"buffering\",\"endbuffering\",\"muted\",\"unmuted\",\"seeking\",\"seeked\",\"exitfullscreen\",\"webkitendfullscreen\",\"error\",\"canunmute\",\"custominfo\",\"playersourceloading\"],s=d.length-1;0<=s;s--)i.addEventListener(d[s],a);function c(){m!==i.volume&&(m=i.volume,u.emit(\"volumechange\",m))}function l(){var e=u.currentTime;g%Math.round(60/n.maxTimeupdatesPerSecond)==0&&u.isPlaying&e!==f&&(f=e,u.emit(\"timeupdate\",e)),g++}var m=i.volume,f=0,g=0,p=(Ticker.frame(c),Ticker.frame(l),Object.defineProperties(u,{currentTime:{get:function(){return this.getCurrentTime()},set:function(e){this.setCurrentTime(e)}},reportsSeeking:{get:function(){return!!i.reportsSeeking}},buffered:{get:function(){return i.buffered}},duration:{get:function(){return i.duration}},muted:{get:function(){return i.muted},set:function(e){i.muted=e}},isPlaying:{get:function(){return!(!(0<this.getCurrentTime())||i.paused||i.ended)}},paused:{get:function(){return i.paused}},activeSrc:{get:function(){return i.currentSrc||i.src}}}),r||(i.videoWidth=n.videoWidth,i.videoHeight=n.videoHeight),u.continuePlaying=function(){i.play()}.bind(this),u.render=function(){!r&&android(\"4.0\",\"4.2\")&&(i.style.height=\"auto\",i.style.minHeight=\"auto\");try{i.load()}catch(e){}return i},i.play?i.play.bind(i):noop),y=i.pause?i.pause.bind(i):noop,v=!0,h=function(){u.emit(\"autoplaynotpossible\")},b=function(){var e;u.pauseEnforced||void 0!==(e=p())&&e.catch(function(e){u.emit(\"autoplayrejected\"),\"NotAllowedError\"!==e.name&&\"AbortError\"!==e.name||(Logger(\"Html5VideoEngine\").error(\"Video Engine - \",e),i.muted=!0,u.emit(\"forcemuted\"),v?(v=!1,b()):(h(),h=noop))})};return u.mute=function(){i.muted=!0},u.unMute=function(){i.muted=!1},u.setCurrentTime=function(e){u.isReady()&&(i.currentTime=e)},u.getCurrentTime=function(){return u.isReady()&&0<i.currentTime?i.currentTime:0},u.isReady=function(){return i&&0!==i.readyState},u.play=function(){u.pauseEnforced=!1,celtra.webkit(\"534.30\")||celtra.webkit(\"537.36\")||fakeclick(function(){b()}.bind(this)),defer(function(){b()}.bind(this)),CeltraPlayerUtils.isNode(i)&&i.addEventListener(\"progress\",i.continuePlaying)},u.pause=function(){CeltraPlayerUtils.isNode(i)&&i.removeEventListener(\"progress\",i.continuePlaying,{},{}),y(),u.pauseEnforced=!0},u.destroy=function(){y();for(var e=d.length-1;0<=e;e--)i.removeEventListener(d[e],a);r||CeltraPlayerUtils.removeElements(i),Ticker.removeFrame(c),Ticker.removeFrame(l)},u}}();;\n!function(e){var s=Object.create(EventEmitter),t=window.AudioContext||window.webkitAudioContext,o=null,i=!1,n=!1;extend(s,{init:function(e,t,i){return this._sourceNode=null,this._buffer=null,this._bufferSilence=null,this._encodedBuffer=null,this._emitTimeUpdateInterval=null,this._sourceUrl=e,this._paused=!0,this._pausedAt=0,this._timeDiff=0,this._loadStatus=\"unstarted\",this.loop=!!i,t&&this.load(),this},load:function(){var e;\"unstarted\"===this._loadStatus&&(this._loadStatus=\"pending\",(e=new XMLHttpRequest).open(\"GET\",this._sourceUrl,!0),e.responseType=\"arraybuffer\",e.onload=function(){this._encodedBuffer=e.response,o&&this._decodeAudioData()}.bind(this),e.send())},unlock:function(){i||(this._iosHack(),this._createAudioContext(),this._createNodeAndPlayFrom(this._bufferSilence,0,this.loop))},playAudio:function(e){this._iosHack(),this._createAudioContext(),this.load();e=null==e?this._pausedAt:e;this.pause(),this._timeDiff=o.currentTime-e,this._paused=!this._buffer,this._createNodeAndPlayFrom(this._buffer||this._bufferSilence,e,this.loop),this._emitTimeUpdate(),i||setTimeout(function(){var e,t;this._sourceNode&&(e=this._sourceNode.playbackState===this._sourceNode.PLAYING_STATE,t=this._sourceNode.playbackState===this._sourceNode.FINISHED_STATE,i=i||!!this._buffer&&(e||t))}.bind(this),0)},pause:function(){clearInterval(this._emitTimeUpdateInterval),this._pausedAt=this.currentTime,this._paused=!0,this._clearSourceNode()},destroy:function(){this.pause(),\"function\"==typeof o.close&&o.close(),this._sourceNode=null,this._buffer=null,this._bufferSilence=null,this._encodedBuffer=null},_iosHack:function(){ios()&&!n&&(this._createAudioContext(),this._createNodeAndPlayFrom(this._bufferSilence,0,this.loop),\"function\"==typeof o.close&&(o.close(),o=null),this._createAudioContext(),n=!0)},_createAudioContext:function(){o||(o=new t,this._bufferSilence=o.createBuffer(1,1,o.sampleRate),this._encodedBuffer&&!this._buffer&&this._decodeAudioData())},_createNodeAndPlayFrom:function(e,t,i){this._clearSourceNode(),this._sourceNode=o.createBufferSource(),this._sourceNode.connect(o.destination),this._sourceNode.buffer=e,this._sourceNode.loop=i,\"function\"==typeof this._sourceNode.noteGrainOn?this._sourceNode.noteGrainOn(0,t,e.duration-t):\"function\"==typeof this._sourceNode.start?this._sourceNode.start(0,t):this._sourceNode.noteOn(0,t)},_clearSourceNode:function(){if(this._sourceNode){try{\"function\"==typeof this._sourceNode.stop?this._sourceNode.stop(0):this._sourceNode.noteOff(0)}catch(e){}this._sourceNode.disconnect(),this._sourceNode=null}},_emitTimeUpdate:function(){this.paused||this._buffer&&this.currentTime>=this._buffer.duration?clearInterval(this._emitTimeUpdateInterval):this._emitTimeUpdateInterval=setInterval(this.emit.bind(this,\"timeupdate\"),250)},_decodeAudioData:function(){o.decodeAudioData(this._encodedBuffer,function(e){this._buffer=e,this._loadStatus=\"loaded\",this.emit(\"canplay\"),this.emit(\"canplaythrough\")}.bind(this))}}),Object.defineProperties(s,{paused:{get:function(){return this._paused}},ended:{get:function(){return!1}},ready:{get:function(){return\"loaded\"===this._loadStatus}},playedBefore:{get:function(){return i}},currentTime:{get:function(){return this._paused?this._pausedAt:o.currentTime-this._timeDiff},set:function(e){this._pausedAt=e,\"loaded\"!==this._loadStatus||this._paused||(this.pause(),this.playAudio(e))}}}),e.WebAudioEngine=function(e,t,i){return Object.create(s).init(e,t,!!i)}}(this);;\n!function(){this.Html5AudioEngine=function(e,t){var n,o,r;return\"undefined\"==typeof Audio?null:((n=new Audio).isUnlocked=!1,t?(n.preload=\"auto\",CeltraPlayerUtils.setMediaElementSource(document,n,e),n.start=noop):(n.preload=\"none\",n.start=function(){CeltraPlayerUtils.setMediaElementSource(document,n,e),n.start=noop}),n.ready=!1,r=function(){.3<n.buffered/n.duration&&o()},n.addEventListener(\"canplay\",o=function(e){n.ready=!0}),n.addEventListener(\"canplaythrough\",o),n.addEventListener(\"progress\",r),n.unlock=noop,n.on=n.addEventListener,n.off=n.removeEventListener,n.playedBefore=!1,n.unlock=function(){this.isUnlocked||this.playedBefore||(fakeclick(function(){celtra.webkit(\"537.36\")&&n.play(),n.pause()}),this.isUnlocked=!0)},n.playFrom=function(e){n.start(),n.ready&&(fakeclick(function(){n.play()}),void 0!==e&&(n.currentTime=e,n.playedBefore=!0))},n.destroy=function(){for(this.pause(),this.src=null;this.firstElementChild;)this.removeChild(this.lastElementChild);n.removeEventListener(\"progress\",r),n.removeEventListener(\"canplay\",o),n.removeEventListener(\"canplaythrough\",o)},n.setAttribute(\"x-celtra-media\",\"\"),n)}}();;\n!function(){function e(e,t,i){this._opts=merge(n,i.options),this._opts._isTeaser=i.isTeaser||!1,this._srcProvider=e,this._engineType=t,this._isReady=!1,this._listenersAttached=!1,this._playAttempted=!1,this._pausedBeforePlayAttempted=!1,creative.waitingOnPlayerSource=!!creative.waitingOnPlayerSource,this.onResourceLoad=null,this.reset(),this.duration=0,this.buffered=0,this._canSync=!0,this._preload=this._opts.preload}var n={netStreamOpts:{},preload:!1},t={loadmetadata:\"onLoadMetaData\",durationchange:\"onDurationChange\",progress:\"onProgress\",buffering:\"onBuffering\",endbuffering:\"onEndBuffering\",canplay:\"onCanPlay\",play:\"onPlay\",playing:\"onPlaying\",pause:\"onPause\",seeking:\"onSeeking\",seeked:\"onSeeked\",ended:\"onEnded\",custominfo:\"onCustomInfo\",timeupdate:\"onTimeUpdate\"},i=(extend(e.prototype,StatefulEventEmitter),e.prototype.reportsSeeking=!0,Object.defineProperty(e.prototype,\"paused\",{get:function(){return!(!this._decoder||!this._decoder.paused)}}),Object.defineProperty(e.prototype,\"duration\",{get:function(){return this._decoder&&this._decoder.duration||0}}),Object.defineProperty(e.prototype,\"currentTime\",{get:function(){return this._decoder&&this._decoder.currentTime||0},set:function(e){this._decoder&&this._decoder.setCurrentTime(e)}}),Object.defineProperty(e.prototype,\"activeSrc\",{get:function(){return this._srcProvider}}),e.prototype);e.SOUND_UNINITIALIZED=0,e.SOUND_PLAYING=1,e.SOUND_PAUSED=2,e.SOUND_ENDED=3,e.SOUND_ERROR=4,i.render=function(e){return this.div||(this.div=e.createElement(\"div\"),this.div.className=\"canvasContainer\"),this._createEngines(),this.div},i._createAvailableEngine=function(){this.emit(\"playersourceloading\"),this.emit(\"loadstart\"),this._netStream||(this._netStream=new NetStream(this._srcProvider,function(e){return this._playAttempted?393216:0==e?262144:0}.bind(this),this._opts.netStreamOpts),this._accumulator=new Accumulator(this._netStream,this._opts),this._preload&&this._netStream.load());var e=creative.resourceUrl+(\"ogv\"===this._engineType?\"runner-min/FullscreenVideoPlayer/OgvPlayer.js\":\"runner-min/FullscreenVideoPlayer/Mpeg1Player.js\");\"ogv\"==this._engineType&&\"function\"==typeof OgvDecoder&&\"function\"==typeof OgvContainerDecoder||\"mpeg1\"==this._engineType&&\"function\"==typeof Mpeg1Decoder&&\"function\"==typeof ContainerDecoder?(this._containerDecoder=\"ogv\"==this._engineType?new OgvContainerDecoder(this._accumulator,this._opts):new ContainerDecoder(this._accumulator),this._decoder=\"ogv\"==this._engineType?new OgvDecoder(this._containerDecoder,this.div):new Mpeg1Decoder(this._containerDecoder,this.div,this._opts),this.attachListeners(),this.onResourceLoad&&this.onResourceLoad()):creative.waitingOnPlayerSource||(creative.waitingOnPlayerSource=!0,loadJS(e,function(){creative.waitingOnPlayerSource=!1,this._pausedBeforePlayAttempted||this._createAvailableEngine()}.bind(this)))},i._createEngines=function(){this._decoder||(this._pausedBeforePlayAttempted=!1,this._createAvailableEngine())},i._startPlaying=function(){this._playAttempted||(this._playAttempted=!0),this._netStream.load(),this.emit(\"userplayed\"),this._decoder.play()},i._processListeners=function(e,i){CeltraPlayerUtils.forEach(e,function(e,t){i(e,this[t].bind(this))},this)},i.attachListeners=function(){var e;this._listenersAttached||(e=this._decoder.addListener.bind(this._decoder),this._processListeners(t,e),this._listenersAttached=!0)},i.removeListeners=function(){var e;this._listenersAttached&&(e=this._decoder.removeListener.bind(this._decoder),this._processListeners(t,e),this._listenersAttached=!1)},i.onLoadMetaData=function(){this.emit(\"loadmetadata\")},i.onDurationChange=function(){this._isReady=!0,this.emit(\"durationchange\")},i.onProgress=function(){this.buffered=this._decoder.buffered,this.emit(\"progress\")},i.onBuffering=function(){this.emit(\"buffering\")},i.onEndBuffering=function(){this.emit(\"endbuffering\")},i.onCanPlay=function(){this.emit(\"canplay\")},i.onPlay=function(){this.emit(\"play\")},i.onPlaying=function(){this.emit(\"playing\")},i.onPause=function(){this.emit(\"pause\")},i.onSeeking=function(){this.emit(\"seeking\")},i.onSeeked=function(){this.emit(\"seeked\")},i.onEnded=function(){this.emit(\"ended\")},i.onCustomInfo=function(e){this.emit(\"custominfo\",e)},i.onTimeUpdate=function(){this.emit(\"timeupdate\",this.currentTime)},i.onBeforeResize=function(e){this._decoder&&this._decoder.isState(CeltraPlayerUtils.PLAYING)&&(this._decoder.pause(),defer(function(){this._decoder&&this._decoder.play()}.bind(this),1500)),e(!0)},i.destroy=function(){this._netStream&&this._netStream.destroy(),this._netStream=null,this._containerDecoder&&this._containerDecoder.destroy(),this._containerDecoder=null,this.removeListeners(),this._decoder&&this._decoder.destroy(),this._decoder=null},i.play=function(){creative.waitingOnPlayerSource&&this.emit(\"playersourceloading\",!0),this.onResourceLoad=this._startPlaying,this._createEngines(),this._decoder&&this._startPlaying()},i.pause=function(){this._decoder?this._decoder.pause():this._pausedBeforePlayAttempted=!0},i.getVideoWidth=function(){return this._decoder?this._decoder.width:NaN},i.getVideoHeight=function(){return this._decoder?this._decoder.height:NaN},i.getCurrentTime=function(){return this.currentTime},i.setCurrentTime=function(e){this.currentTime=e},i.seekToRatio=function(e){e=Math.min(this.buffered/this.duration-.1,e),e=Math.min(.95,Math.max(0,e)),this._decoder.seekTo(e)},i.reset=function(){this._decoder&&this._decoder.reset(),this.currentTime=0,this._playAttempted=!1},i.isReady=function(){return this._isReady},i.getFPS=function(){return this._decoder.getFPS()},window.JsVideoEngine=e}();;\n!function(){this.AVSyncAudio={init:function(i,t,e){this.onPause=this.onPause.bind(this),this.onEnded=this.onEnded.bind(this),this.onPlay=this.onPlay.bind(this),this.onReplayed=this.onReplayed.bind(this),this.onMuted=this.onMuted.bind(this),this.onUnMuted=this.onUnMuted.bind(this),this.onTimeUpdate=this.onTimeUpdate.bind(this),this.onFirstPlay=this.onFirstPlay.bind(this),this.destroy=this.destroy.bind(this),this.video=i,this.audio=t,this.muted=e,this._lastSyncTime=0,this.video.once(\"destroy\",this.destroy),this.video.once(\"userplayed\",this.onFirstPlay),this.video.on(\"muted\",this.onMuted),this.video.on(\"unmuted\",this.onUnMuted),this.video.on(\"replayed\",this.onReplayed)},attach:function(){this.video.on(\"pause\",this.onPause),this.video.on(\"ended\",this.onEnded),this.video.on(\"userpaused\",this.onPause),this.video.on(\"seeking\",this.onPause),this.video.on(\"play\",this.onPlay),this.video.on(\"playing\",this.onPlay),this.video.on(\"seeked\",this.onPlay),this.video.on(\"timeupdate\",this.onTimeUpdate)},destroy:function(){this.audio.pause(),this.video.off(\"userplayed\",this.onFirstPlayed),this.video.off(\"userpaused\",this.onPause),this.video.off(\"pause\",this.onPause),this.video.off(\"seeking\",this.onPause),this.video.off(\"ended\",this.onEnded),this.video.off(\"play\",this.onPlay),this.video.off(\"playing\",this.onPlay),this.video.off(\"seeked\",this.onPlay),this.video.off(\"muted\",this.onMuted),this.video.off(\"unmuted\",this.onUnMuted),this.video.off(\"timeupdate\",this.onTimeUpdate),this.video.off(\"replayed\",this.onReplayed)},onFirstPlay:function(){var e,s,o,d;this.video.off(\"userplayed\",this.onFirstPlay),this.audio.start(),this.muted||this.audio.ready?(fakeclick(function(){this.audio.load()}.bind(this)),this.attach()):(s=e=!1,o=celtra.webkit(\"534.30\"),d=function(){var i=isMediaPlaying(this.video),t=isMediaPlaying(this.audio);if(t&&i||t&&s||i&&e||e&&s||this.audio.ready){this.attach(),this.video.silentPlay();try{this.video.currentTime=this.audio.currentTime}catch(i){}}else i&&!t&&(this.video.silentPause(),s=!0),!t||i||o||(this.audio.pause(),e=!0),defer(d.bind(this),120)}.bind(this),o?this.audio.playFrom():fakeclick(function(){this.audio.load()}.bind(this)),defer(d,500))},onPause:function(){this.audio.pause()},onEnded:function(){this.audio.pause(),this.audio.ready&&(this.audio.currentTime=0)},onPlay:function(){this.muted||this.audio.playFrom()},onReplayed:function(){this.muted||(this.audio.playFrom(0),this._lastSyncTime=Date.now())},onMuted:function(){this.muted=!0,this.audio.pause()},onUnMuted:function(){this.muted=!1,isMediaPlaying(this.video)&&this.audio.ready?this.audio.playFrom(this.video.currentTime):this.audio.unlock()},onTimeUpdate:function(){var i=isMediaPlaying(this.audio);if(!(!i&&this.audio.playedBefore||this.muted))if(!this.audio.paused||this.muted||this.video.paused){var i=this.video.currentTime-this.audio.currentTime,t=Math.abs(i);if(!(t<.6||this.video.currentTime<1||Date.now()-this._lastSyncTime<3e3))if(this._lastSyncTime=Date.now(),i<0&&t<2)try{this.video.currentTime=this.audio.currentTime}catch(i){}else try{this.audio.currentTime=this.video.currentTime+.3}catch(i){}}else this.audio.playFrom(this.video.currentTime)}}}();;\n!function(){this.AVSyncWebAudio={init:function(i,t,e){this.onPause=this.onPause.bind(this),this.onEnded=this.onEnded.bind(this),this.onMuted=this.onMuted.bind(this),this.onUnMuted=this.onUnMuted.bind(this),this.onTimeUpdate=this.onTimeUpdate.bind(this),this.onFirstPlay=this.onFirstPlay.bind(this),this.destroy=this.destroy.bind(this),this.video=i,this.audio=t,this.muted=e,this._lastSyncTime=0,i.once(\"destroy\",this.destroy),i.once(\"userplayed\",this.onFirstPlay),this.video.on(\"muted\",this.onMuted),this.video.on(\"unmuted\",this.onUnMuted)},attach:function(){this.video.on(\"pause\",this.onPause),this.video.on(\"ended\",this.onEnded),this.video.on(\"userpaused\",this.onPause),this.video.on(\"seeking\",this.onPause),this.video.on(\"timeupdate\",this.onTimeUpdate)},destroy:function(){this.audio.pause(),this.video.off(\"userplayed\",this.onFirstPlayed),this.video.off(\"pause\",this.onPause),this.video.off(\"userpaused\",this.onPause),this.video.off(\"seeking\",this.onPause),this.video.off(\"ended\",this.onEnded),this.video.off(\"muted\",this.onMuted),this.video.off(\"unmuted\",this.onUnMuted),this.video.off(\"timeupdate\",this.onTimeUpdate)},onFirstPlay:function(){this.video.off(\"userplayed\",this.onFirstPlay);function i(){t&&!s&&e.silentPause()}var t=!0,e=this.video,s=celtra.webkit(\"534.30\");this.muted||this.audio.ready?(this.audio.load(),this.attach()):(this.video.once(\"canplay\",i),this.video.once(\"playing\",i),this.video.once(\"timeupdate\",i),this.audio.once(\"canplaythrough\",function(){t=!1,this.video.off(\"canplay\",i),this.video.off(\"playing\",i),this.video.off(\"timeupdate\",i),isMediaPlaying(this.video)||this.video.silentPlay(),!isMediaPlaying(this.audio)&&android()&&this.audio.playAudio(),this.attach()}.bind(this)),this.audio.load())},onPause:function(){this.audio.pause()},onEnded:function(){this.audio.pause(),this.audio.currentTime=0},onMuted:function(){this.muted=!0,this.audio.pause()},onUnMuted:function(){this.muted=!1,isMediaPlaying(this.video)?this.audio.playAudio(this.video.currentTime):this.audio.unlock()},onTimeUpdate:function(){if(!this.muted)if(this.audio.paused&&!this.video.paused)this.audio.playAudio(this.video.currentTime);else{var i=this.video.currentTime-this.audio.currentTime,t=Math.abs(i);if(!(t<.6||this.video.currentTime<1||Date.now()-this._lastSyncTime<3e3))if(this._lastSyncTime=Date.now(),i<0&&t<2)try{this.video.currentTime=this.audio.currentTime}catch(i){}else try{this.audio.currentTime=this.video.currentTime}catch(i){}}}}}();;\nvar CuePointObserver={init:function(t){return this._video=t,this._VIDEO_START_TIME=-1,this._attached=!1,this._lastTriggeredTime=this._VIDEO_START_TIME,this._observeCuePoints=this._observeCuePoints.bind(this),this._observe=this._observe.bind(this),this.start=this.start.bind(this),this.stop=this.stop.bind(this),this},_observe:function(t){var e=t.time;return!((e=t.hasOwnProperty(\"cuepointType\")&&\"percentage\"===t.cuepointType?e/100*this._video._player.getDuration():e)<=this._lastTriggeredTime)&&((t=this._video._player.getCurrentTime()>=e)&&(this._lastTriggeredTime=e),t)},_observeCuePoints:function(){this._video._player&&this._video.fireTriggers(\"videoCuepoint\",this._observe)},attach:function(){this._attached||(this._attached=!0,Ticker.frame(this._observeCuePoints,\"update\"))},detach:function(){this._attached&&(this._attached=!1,Ticker.removeFrame(this._observeCuePoints,\"update\"))},start:function(){this.attach()},stop:function(t){this.detach(),t&&(this._lastTriggeredTime=this._VIDEO_START_TIME)}};;\n!function(t){var r={name:\"Countdown\",init:function(t,e,i,s){if(this._duration=e,this._displayTime=null,this._mode=i,this._opts=s||{},this._countdownType=s.countdownType,-1===[\"kinetic\",\"countdown\"].indexOf(this._mode))throw this._mode+\" is not available as a mode for Countdown component!\";\"kinetic\"===this._mode&&(this._duration=.95*this._duration);e=\"undefined\"==typeof InstaAdUtility?celtra.isHighDensityDisplay():InstaAdUtility.isHighDensityDisplay,i=celtra.desktop()&&!e,s=celtra.desktop()&&e,e=celtra.desktop()&&celtra.gecko();switch(this._retinaFactor=i?1:s||e?2:4,this._countdownType){case\"small\":this._fontSize=0,this._size=26*this._retinaFactor,this._barWidth=2*this._retinaFactor;break;case\"normal\":this._fontSize=13*this._retinaFactor,this._size=32*this._retinaFactor,this._barWidth=2*this._retinaFactor;break;case\"big\":this._fontSize=14*this._retinaFactor,this._size=40*this._retinaFactor,this._barWidth=2.5*this._retinaFactor}return this._halfSize=this._size/2,this._barColor=this._opts.barColor||\"rgba(255, 255, 255, 1)\",this._bgCircleColor=this._opts.bgCircleColor||\"rgba(0, 0, 0, 0.08)\",this._circ=2*Math.PI,this._quart=Math.PI/2,this._radius=.5*this._size,this._radiusCountdown=.5*(this._size-4*this._barWidth),this._barPercentage=0,this._kineticBarSpeed=.01,this._introTransitionEnabled=this._opts.introTransitionEnabled||!1,this._opacity=null,this.node=t.appendChild(this._createCountdown()),this._offscreenCanvas=document.createElement(\"canvas\"),this._offscreenContext=this._offscreenCanvas.getContext(\"2d\"),this._state=new StateObject({show:null}),this._time=new StateObject({currentTime:0}),this._update=this._update.bind(this),this._render=this._render.bind(this),Ticker.frame(this._update,\"update\"),Ticker.frame(this._render,\"render\"),this.setCurrentTime(0),this},destroy:function(){Ticker.removeFrame(this._update,\"update\"),Ticker.removeFrame(this._render,\"render\"),this._offscreenCanvas=null,this._offscreenContext=null,this._prebufferedBg=null},_createCountdown:function(){var t=document.createElement(\"div\"),e=document.createElement(\"canvas\"),i=document.createElement(\"div\"),e=(this._text=document.createElement(\"div\"),this._ctx=e.getContext(\"2d\"),addClass(t,\"celtra-countdown\"),this._opts.cssClass&&addClass(t,this._opts.cssClass),\"kinetic\"===this._mode&&addClass(t,\"quick-transition\"),addClass(e,\"celtra-countdown-canvas\"),addClass(i,\"celtra-countdown-text-wrapper\"),i.style.fontSize=this._fontSize+\"px\",e.setAttribute(\"width\",this._size+\"px\"),e.setAttribute(\"height\",this._size+\"px\"),t.appendChild(e),i.style.color=this._barColor,i.style.width=this._size+\"px\",i.style.height=this._size+\"px\",\"scale(\"+1/this._retinaFactor+\")\");return t.style.transform=e,t.style.webkitTransform=e,t.appendChild(i),i.appendChild(this._text),t},show:function(){this._state.show=!0},hide:function(){this._state.show=!1},_update:function(){var t,e;this._barPercentage=this._time.currentTime/this._duration,this._state.show&&this._time.isDirty()&&(this._time.markClean(),this._offscreenContext.clearRect(0,0,this._size,this._size),this._prebufferedBg||(this._offscreenContext.beginPath(),this._offscreenContext.arc(this._halfSize,this._halfSize,this._radius,0,this._circ),this._offscreenContext.fillStyle=this._bgCircleColor,this._offscreenContext.fill(),this._offscreenContext.beginPath(),this._offscreenContext.strokeStyle=this._barColor,this._offscreenContext.lineCap=\"square\",this._offscreenContext.closePath(),this._offscreenContext.fill(),this._offscreenContext.lineWidth=this._barWidth,this._prebufferedBg=this._offscreenContext.getImageData(0,0,this._size,this._size)),t=-this._quart,e=this._circ*(1-this._barPercentage)-this._quart,0===this._barPercentage?e=t=0:1<=this._barPercentage&&(t=0,e=2*Math.PI),this._offscreenContext.putImageData(this._prebufferedBg,0,0),this._offscreenContext.beginPath(),t!==e&&this._offscreenContext.arc(this._halfSize,this._halfSize,this._radiusCountdown,t,e,!0),this._offscreenContext.stroke())},_render:function(){var t;this._introTransitionEnabled&&this._opacity&&this._state.show&&(this.node.style.opacity=this._opacity+.2),this._state.isDirty()&&(this._state.show?(removeClass(this.node,\"celtra-hide\"),this._introTransitionEnabled||addClass(this.node,\"celtra-show\")):(this._introTransitionEnabled&&this._opacity&&(this.node.style.opacity=0,this._introTransitionEnabled=!1),removeClass(this.node,\"celtra-show\"),addClass(this.node,\"celtra-hide\")),this._state.markClean()),this._state.show&&(this._ctx.clearRect(0,0,this._size,this._size),this._ctx.drawImage(this._offscreenCanvas,0,0),\"countdown\"===this._mode&&(t=Math.round(this._time.currentTime/1e3),this._displayTime!==t&&(this._displayTime=t,this._text.innerText=this._displayTime)))},setOpacity:function(t){this._opacity=t},setCurrentTime:function(t){if(\"countdown\"===this._mode){if(t=this._duration-t,this._duration<=0)return;this._time.currentTime=t=t<150?0:t}\"kinetic\"===this._mode&&(this._time.currentTime=t)}};t.CountdownProvider={create:function(t,e,i,s,n){return t?Object.create(r).init(e,i,s,n):{show:noop,hide:noop,setCurrentTime:noop,destroy:noop}}}}(this);;\nfunction InstaAdContext(t,e){if(this._id=randInt(),this.initiationTimestamp=new Date/1e3,this.initiator=t,this.consideredUserInitiatedByBrowser=!!e.consideredUserInitiatedByBrowser,this.certainlyNotCausedByUserBehavior=!!e.certainlyNotCausedByUserBehavior,this.inUserInitiatedIteration=this.consideredUserInitiatedByBrowser,this.consideredUserInitiatedByBrowser&&this.certainlyNotCausedByUserBehavior)throw new Error(\"Unable to create an InstaAdContext where both consideredUserInitiatedByBrowser and certainlyNotCausedByUserBehavior are true!\");this.inUserInitiatedIteration&&setTimeout(function(){this.inUserInitiatedIteration=!1}.bind(this),0)}InstaAdContext.prototype.extendEvent=function(t){return extend({localId:this.initiator?this.initiator.localId:null,clazz:this.initiator?this.initiator.constructor.name:null,initiationTimestamp:this.initiationTimestamp},t)},InstaAdContext.prototype.track=function(t,e){creative.track(this.extendEvent(t),e)},InstaAdContext.prototype.trackUserInteraction=function(){var t;creative.getUserInteracted()?creative.interactionTrackingTimeout||(this.track({name:\"interaction\"}),creative.interactionTrackingTimeout=setTimeout(function(){creative.interactionTrackingTimeout=null},1e3)):(creative.runtimeParams.userInteracted=1,creative.experiments.get(\"TrackViewableAreaChange\")&&(t=creative.adapter).canMeasureViewportPlacementGeometry&&\"function\"==typeof t.trackViewableAreaRatio&&t.trackViewableAreaRatio(),this.track({name:\"firstInteraction\"})),defer(creative.trackingCenter.batchFlush)},InstaAdContext.prototype.trackClickThrough=function(t){t=t||noop,creative.runtimeParams.clickedThrough?setTimeout(t,0):(creative.runtimeParams.clickedThrough=1,this.track({name:\"firstClickThrough\"},t)),defer(creative.trackingCenter.batchFlush)};;\nfunction Creative(e,t,r,n,i,a,o,s,u,c,d,m,p){if(e instanceof Creative)throw\"This JSON has already been unfreezed. We do it inline, to improve performance. Duplicate it yourself before unfreezing, if you need to use it again.\";var f=p.start(\"Creative.new\"),s=extend({adapter:a,runtimeParams:t||{},urlOpenedOverrideUrls:s,urlOpenedUrlAppendage:d,clickThroughDestinationUrl:m,storeOpenedOverrideUrls:u,macros:c},o),d=p.start(\"Creative.unfreeze\");return e=Freezer.unfreeze(e,window,s),d.end(),e.instantiation=randInt(),e.batcher=new Batcher({protoLoading:a.protoLoading}),e.trackingCenter=r,e.universalInteractionId=\"\",e.aggregatorTracking=n,e.experiments=i,e.perf=p,e.platformAdvId=t.platformAdvId,e.platformAdvIdTrackingLimited=t.platformAdvIdTrackingLimited,null!=a&&(a.openBrowserSameWindowInHostileIFrame=function(e){window.top.location.href=e}),f.end(),e}Creative.toString=function(){return\"[Clazz Creative]\"},Creative.prototype.toString=function(){return\"[Creative \"+this.name+\"]\"},window.creative=null,extend(Creative.prototype,EventEmitter),Creative.init=function(e,t,r,n,i,a,o,s,u,c,d,m,p){if(window.creative)throw\"creative is a singleton and was already initialized.\";Logger.initFromRuntimeParams(t),window.creative=new Creative(e,t,r,n,i,a,o,s,u,c,d,m,p)},Creative.prototype.userInteracted=function(e){this.emit(\"interaction\",e)},Creative.prototype.clickedThrough=function(e){this.emit(\"clickThrough\",e)},Creative.prototype._getRuntimeParamsWithPrefix=function(e){var t,r={};for(t in this.runtimeParams)0===t.indexOf(e)&&(r[t.slice(e.length)]=this.runtimeParams[t]);return r},Object.defineProperties(Creative.prototype,{sessionId:{enumerable:!0,get:function(){return this.runtimeParams.sessionId}},placementId:{enumerable:!0,get:function(){return this.runtimeParams.placementId||null}},folderId:{enumerable:!0,get:function(){return this.runtimeParams.folderId}},supplierId:{enumerable:!0,get:function(){return this.runtimeParams.supplierId||null}},secure:{enumerable:!0,get:function(){return 1==this.runtimeParams.secure}},userParams:{enumerable:!0,get:function(){return this._getRuntimeParamsWithPrefix(\"user.\")}},userIdentifiers:{enumerable:!0,get:function(){return this.runtimeParams.userIdentifiers}},authTokenUrlParam:{enumerable:!1,get:function(){return\"authBasis=\"+creative.runtimeParams.authBasis+\"&authToken=\"+creative.runtimeParams.authToken}},customAudiences:{enumerable:!0,get:function(){return function(t){if(creative.runtimeParams.customAudiences[t])return{userExists:creative.runtimeParams.customAudiences[t].userExists,userData:creative.runtimeParams.customAudiences[t].userData,addUser:function(e){return CustomAudiences.addUser(t,e)},removeUser:function(){return CustomAudiences.removeUser(t)}};throw new Error('Custom audience \"'+t+'\" is not used by the creative.')}}},acceptLanguage:{enumerable:!0,get:function(){return this.runtimeParams.acceptLanguage}}}),Creative.prototype.awake=function(){function o(e,t,r,n){e.forEach(function(e){e.parentUnit=n,e.parentScreen=r,e.parentContainer=t})}for(var e in this.units){var t=this.units[e],r=(t.name=e,[]);t.variants?t.variants.forEach(function(e){r=r.concat(e.master,e.screens),e.loadingScreen&&(r=[e.loadingScreen].concat(r))}):(r=[t.master].concat(t.screens),t.loadingScreen&&(r=[t.loadingScreen].concat(r))),r.forEach(function(e){e.parentUnit=t,function r(e,n,i,a){e.forEach(function(t){t.parentUnit=a,t.parentScreen=i,t.parentContainer=n,t.getNestedContainers().forEach(function(e){e.parentUnit=a,e.parentScreen=i,e.parentObject=t,r(e.objects,e,i,a),e.scenes&&o(e.scenes,e,i,a)})})}(e.objects,e,e,t),e.scenes&&o(e.scenes,e,e,t)})}},Creative.prototype.track=function(e,t){0<=[\"firstInteraction\",\"interaction\",\"endCardInteraction\",\"unitCollapsed\",\"creativeDismissed\",\"clickThroughDestinationOpened\",\"firstClickThrough\",\"itemSwiped\"].indexOf(e.name)&&(e=extend(e,{universalInteractionId:this.universalInteractionId})),this.trackingCenter.track(e,t)},Creative.prototype.setUniversalInteractionId=function(e){this.universalInteractionId=e},Creative.prototype.wrapRedirectPageUrl=function(e,t){return this.trackingCenter.wrapRedirectPageUrl(e,t)},Object.defineProperty(Creative.prototype,\"sdk\",{get:function(){return this.adapter},enumerable:!0}),Creative._throw=function(e){throw e},Creative.prototype.getUserInteracted=function(){return 1==this.runtimeParams.userInteracted},Creative.prototype.trackCreativeRendered=function(){creative.adapter.notifycreativeRendered(),this._getCreativeRendered()||(this.track({name:\"creativeRendered\"}),this.runtimeParams.creativeRendered=1,defer(this.trackingCenter.batchFlush))},Creative.prototype._getCreativeRendered=function(){return 1===this.runtimeParams.creativeRendered},Creative.prototype.trackCreativeRenderedOnScreenShow=Creative.prototype.trackCreativeRendered,Creative.prototype.trackCreativeRenderedOnVideoStart=noop,Creative.trackCustomEventAction=function(e,t,r){e.track({name:\"custom\",label:t.name}),creative.adapter.sendToEventMonitor(\"custom\",t.triggerId,e.screen.name,null,t.name,e.initiatedBeforeScreenShown()),r&&r()},Creative.prototype.trackCreativeLoaded=function(e){this.track({name:\"creativeLoaded\",viewability00Measurable:e.observingViewability(\"00\"),viewability501Measurable:e.observingViewability(\"501\"),viewableTimeMeasurable:e.observingViewableTime(),cdnVariant:this.runtimeParams.variantChoices.CdnTiming||\"none\"}),defer(this.trackingCenter.batchFlush),this.perf._stopTrackingDefers=!0,this.perf.mark(\"creativeLoaded\")},Creative.prototype.createUserLocation=function(e){if(\"undefined\"!=typeof UserLocation)return new UserLocation({lat:this.runtimeParams.gpsLat,lng:this.runtimeParams.gpsLng},e)},Creative.prototype.getPageUrl=function(e){return(this.secure?this.creativeUrl:this.insecureCreativeUrl)+e+\"?base64json=\"+encodeURIComponent(btoa(to_utf8(JSON.stringify(this.runtimeParams))))},Creative.prototype.shouldClickThroughToNewWindow=function(e){var t=0<=[\"same\",\"new\"].indexOf(this.runtimeParams.preferredClickThroughWindow)?this.runtimeParams.preferredClickThroughWindow:\"new\",e=(creative&&creative.adapter.nesting.hostileIframe&&(t=\"new\"),e&&e.inUserInitiatedIteration),r=deviceInfo.os.android()&&deviceInfo.browser.facebookApp();return\"new\"==t&&e&&!r},Creative.prototype.getFeedFields=function(){return FeedData.getCustomFields()},Creative.prototype.getFeedFieldByName=function(e){return FeedData.getCustomFieldByName(e)},Creative.prototype.getAllFeedFieldsByName=function(t){return FeedData.getCustomFields().filter(function(e){return e.name===t})};;\nfunction UniversalInteractiveVideo(){}inherit(UniversalInteractiveVideo,Creative),UniversalInteractiveVideo.toString=function(){return\"[Clazz UniversalInteractiveVideo]\"},UniversalInteractiveVideo.prototype.toString=function(){return\"[UniversalInteractiveVideo \"+this.name+\"]\"},UniversalInteractiveVideo.prototype.awake=function(){UniversalInteractiveVideo.uber.awake.call(this),this.units.banner.dismissAction=function(e,t,i){e.track({name:\"creativeDismissed\"}),t.consideredUserInitiatedByBrowser=e.consideredUserInitiatedByBrowser,creative.adapter.dismiss(i,t)},this.units.banner.userLocation=this.createUserLocation(!0)},UniversalInteractiveVideo.prototype.trackCreativeRenderedOnScreenShow=noop,UniversalInteractiveVideo.prototype.trackCreativeRenderedOnVideoStart=Creative.prototype.trackCreativeRendered;;\nvar Kernel={toString:function(){return\"[Clazz Kernel]\"},runInParallelAction:function(t,e,n){e.actions.waitForEach(function(e,n){e(t,n)},n)}};Kernel.runInParallelActionDryRun=Kernel.runInParallelAction,Kernel.runInBackgroundAction=function(e,n,t){n.action&&n.action(e,noop),t()},Kernel.runInSequenceAction=function(t,e,n){for(var r=n,i=e.actions.length-1;0<=i;i--)r=function(e,n){return function(){n(t,e)}}(r,e.actions[i]);r()},Kernel.runInSequenceActionDryRun=Kernel.runInSequenceAction,Kernel.runNTimesAction=function(e,n,t){n.caller.hasOwnProperty(\"executionsLeft\")||(n.caller.executionsLeft=n.repeat),0<n.caller.executionsLeft?(n.caller.executionsLeft--,(n.action||nullai)(e,t)):t&&t()},Kernel.runNTimesActionDryRun=function(e,n,t){(n.action||nullai)(e,t)},Kernel.loopAction=function(n,t,r){var i=t.repeat;!function e(){if(0==i)return r();-1!=i&&i--;(t.action||nullai)(n,\"thumb\"===creative.runtimeParams.purpose?r:e)}()},Kernel.loopActionDryRun=function(e,n,t){(n.action||nullai)(e,t)},Kernel.sleepAction=function(e,n,t){setTimeout(t,n.duration)},Kernel.executeJavascriptAction=function(e,n,t){try{var r=e.initiator,i=e.screen.parentUnit,c=e.screen,a=new Function(\"ctx\",\"c\",\"unit\",\"screen\",\"variant\",n.script.code),o=!1,u=i.getVariantForScreen?i.getVariantForScreen(c):void 0,l=(null!==n.script.code.match(/http:/)&&this._track(\"executeJavascriptAction.code\"),function(){o?console.warn('Multiple calls made to \"c()\" from \"'+e.initiator.fieldName+'\" Execute JS Action'):(t(),o=!0)}.bind(this));a.call(r,e,l,i,c,u)}catch(e){console.error(e.message+\"\\n\"+e.stack)}},Kernel.randomAction=function(e,n,t){var r=0;if(n.outcomes.forEach(function(e){r+=e.weight}),!r)return t();for(var i=Math.random()*r,c=0;c<n.outcomes.length;c++){var a=n.outcomes[c];if((i-=a.weight)<0)return(a.action||nullai)(e,t)}},Kernel.randomActionDryRun=function(n,e,t){e.outcomes.forEach(function(e){(e.action||nullai)(n,noop)}),t()},Kernel.bestAction=function(e,n,t){var r=creative.experiments.get(\"Best\",[creative.id,n.experimentName]),i=(r.trackExposure(),n.outcomes.filter(function(e){return e.experimentVariant==r.chosenVariant})[0]);((i||n.outcomes[0]).action||nullai)(e,t)},Kernel.bestActionDryRun=function(e,n,t){var r=creative.experiments.get(\"Best\",[creative.id,n.experimentName]),i=n.outcomes.filter(function(e){return e.experimentVariant==r.chosenVariant})[0];((i||n.outcomes[0]).action||nullai)(e,t)},Kernel.trackBestVariantSuccessAction=function(e,n,t){var r=creative.experiments.get(\"Best\",[creative.id,n.experimentName]);r.trackSuccess(),creative.adapter.sendToEventMonitor(\"trackBestVariantSuccess\",n.triggerId,e.screen.name,r.chosenVariant,\"\",e.initiatedBeforeScreenShown()),t()},Kernel._track=function(e){this._privateApiTracked=this._privateApiTracked||{},this._privateApiTracked[e]||(this._privateApiTracked[e]=!0,creative.track({name:\"retiredFeatureUsed\",featureType:\"loadingInsecureContent\",featureName:e}))};;\n;\nvar StaticContentMixin={requiresBakingAnimatedClass:!1,getContentDrive(){return\"static\"},prepareFrame:function(){},hasBakingAnimatedClass(a){return hasClass(a,\"baking-animated\")},setBakingAnimatedClass(a,i){this.requiresBakingAnimatedClass&&!i?addClass(a,\"baking-animated\"):!this.requiresBakingAnimatedClass&&i&&removeClass(a,\"baking-animated\")}};;\nfunction Scene(){}Scene.instances=[],Scene.prototype.awake=function(){this._framesPerSecond=this.framesPerSecond&&null!==this.framesPerSecond?this.framesPerSecond:20,this._framesPerPixel=.1,this._durationInFrames=0,this._durationInMilliseconds=0,this._durationInPixels=0,this._isTimeScene=\"time\"===this.type,this._isReactiveScene=\"reactiveBanner\"===this.type,this._lastUpdateObjectsProgress=0,this._objectVisibilityStates=[],this._claimedObjects=[],this._keyframeAnimations=Object.create(null),this._rawInitialValues=[],this._initSceneOnRenderApiCall=!0,this._suspended=!1,this.fallbackFrameShown=!1,this.forceFallbackFrame=!1,this.intersection=void 0!==this.intersection?this.intersection:50,this.endingMode=\"string\"==typeof this.endingMode?this.endingMode:\"lastKeyframe\",this.duration=\"number\"==typeof this.duration?this.duration:10,this._appearedBefore=!1,this._isAppearing=!1,this._calculateDurations=this._calculateDurations.bind(this),this._buildKeyframeAnimations=this._buildKeyframeAnimations.bind(this),this._removeAnimatedClass=this._removeAnimatedClass.bind(this),this._renderOnSizeChange=this._renderOnSizeChange.bind(this),this._initPlayer(),this.parentContainer.on(\"appeared\",this._appear.bind(this)),this.parentContainer.on(\"disappeared\",this._dissapear.bind(this)),this.parentContainer.on(\"resize\",this._calculateDurations),this.parentContainer.on(\"resize\",this._buildKeyframeAnimations),this.parentContainer.on(\"resize\",this._renderOnSizeChange),this.parentUnit.on(\"layoutChanged\",this._calculateDurations),this.parentUnit.on(\"layoutChanged\",this._buildKeyframeAnimations),this.parentUnit.on(\"layoutChanged\",this._renderOnSizeChange),Scene.instances.push(this)},Scene._sizeProperties=[\"width\",\"height\"],Scene._positionProperties={horizontal:[\"left\",\"hcenter\",\"right\"],vertical:[\"top\",\"vcenter\",\"bottom\"]},Object.defineProperties(Scene.prototype,{paused:{get:function(){return this._player.paused}},_scenePlayerClazz:{get:function(){return window[this.type.charAt(0).toUpperCase()+this.type.slice(1)+\"ScenePlayer\"]},configurable:!0}}),Scene.prototype._initPlayer=function(){this._player=new this._scenePlayerClazz(this),this._player.on(\"stop\",this._resetRawInitialValues.bind(this)),this._player.on(\"end\",this._updateRawFinalValuesForAllLayouts.bind(this)),this._player.on(\"pause\",this._removeAnimatedClass),this._player.on(\"stop\",this._removeAnimatedClass)},Scene.prototype._removeAnimatedClass=function(){for(var e in this._claimedObjects)removeClass(this._claimedObjects[e].node,\"animated\")},Scene.prototype._getMaxFrameNumber=function(){if(this._isTimeScene&&\"timelineDuration\"===this.endingMode)return this.duration*this._framesPerSecond;var e,t=0;for(e in this.objects){var i,n=this.objects[e],a=this.parentUnit.layoutCurrentIndex;for(i in n.layoutSpecificValues[a])var s=n.layoutSpecificValues[a][i],t=Math.max(t,s.frame)}return t},Scene.prototype._calculateDurations=function(){this._durationInFrames=this._getMaxFrameNumber(),this._durationInMilliseconds=this._durationInFrames/this._framesPerSecond*1e3,this._durationInPixels=this._durationInFrames/this._framesPerPixel},Scene.prototype._captureRawInitialValues=function(e){this._rawInitialValues[e.localId]=[];for(var t=this.parentScreen.parentContainer.layouts.length,i=0;i<t;i++){var n=e.layoutSpecificValues[i];this._rawInitialValues[e.localId][i]=extend({rotation:n.rotation,opacity:n.opacity,hidden:n.hidden},n.position,n.size)}},Scene.prototype._calculateFinalValuesForObject=function(e){var t,i=[];for(t in this.objects){var n=this.objects[t];if(n.target.localId==e.localId)for(var a in n.layoutSpecificValues)for(var s in i[a]=Object.create(null),n.layoutSpecificValues[a]){var r,o=n.layoutSpecificValues[a][s];for(r in o.properties)null!==o.properties[r].value&&(i[a][r]=o.properties[r].value)}}return i},Scene.prototype._updateRawFinalValuesForAllLayouts=function(){if(\"reverse\"!==this.onEnd)for(var e in this._claimedObjects)for(var t=this._claimedObjects[e],i=this._calculateFinalValuesForObject(t),n=this.parentScreen.parentContainer.layouts.length,a=0;a<n;a++){var s=t.layoutSpecificValues[a],r=i[a];Scene._updateLayoutWithValues(s,r)}},Scene.prototype._buildKeyframeAnimations=function(){this._keyframeAnimations=Object.create(null);for(var e=this.parentUnit.layoutCurrentIndex,t=0;t<this.objects.length;t++){for(var i=this.objects[t],n=i.layoutSpecificValues[e],a=(this._keyframeAnimations[i.target.localId]={firstFrameAnimated:!1},n.sort(function(e,t){return e.frame-t.frame}),0),s=Object.create(null),r=0;r<n.length;r++){var o=n[r],a=0===this._durationInFrames?0:o.frame/this._durationInFrames*100;s[String(a)]=Scene._extractPropertyValues(o.properties),0==~~r&&(this._keyframeAnimations[i.target.localId].startKeyframe=a)}this._keyframeAnimations[i.target.localId].endKeyframe=a;var l,c=KeyframeAnimation.getUsedProperties(s),h=(this._rawInitialValues[i.target.localId]||this._captureRawInitialValues(i.target),this._rawInitialValues[i.target.localId][e]),p=(Scene._fillInInitialAndDynamicKeyframeValues(s,h,c),i.target.parentContainer.size);for(l in s)Scene._keyframeWithUnitsToKeyframePx(s[l],p);h=new KeyframeAnimation(s,null,c);this._keyframeAnimations[i.target.localId].animation=h}},Scene.prototype._dissapear=function(){this._player.paused&&!this.fallbackFrameShown||this._suspendScene(),this._isAppearing=!1},Scene.prototype._appear=function(){this._appearedBefore||(creative.adapter.canMeasureViewportPlacementGeometry?this.parentScreen.parentContainer.inView.on(\"areaInViewRatioChanged\",this._handleAreaInViewRatioChanged.bind(this)):creative.adapter.containerViewabilityObserver&&creative.adapter.on(\"containerViewableChange\",this._handleContainerViewableChange.bind(this))),(this._player.supportsAutoplay&&this.autoPlay&&!this._appearedBefore||this._suspended)&&this._playSceneOrDisplayFallbackFrame(),this._appearedBefore=!0,this._isAppearing=!0},Scene.prototype._initAnimations=function(){for(var e in this.objects){e=this.objects[e].target;this.claimObject(e)}this._calculateDurations(),this._buildKeyframeAnimations()},Scene.prototype._playSceneOrDisplayFallbackFrame=function(){this._initSceneOnRenderApiCall||this._resetRawInitialValues(),this._initSceneOnRenderApiCall=!0,this.fallbackFrameShown=this._player.supportsFallback&&(!creative.adapter.assumeScrollSupported||this.forceFallbackFrame),!this._player.paused&&this.fallbackFrameShown&&this._player.pause(),this._suspended||this._initAnimations(),this.fallbackFrameShown?this._player.displayFallbackFrame(this.fallbackFrame/this._durationInFrames*100):this._player.play(),this._suspended=!1},Scene.prototype._pauseScene=function(){this._player.pause()},Scene.prototype._stopScene=function(){this._player.stop()},Scene.prototype._resetRawInitialValues=function(){for(var e in this.objects)delete this._rawInitialValues[this.objects[e].target.localId]},Scene.prototype._suspendScene=function(){this._player.pause(),this._suspended=!0},Scene.prototype._handleAreaInViewRatioChanged=function(e){0<e&&this._suspended&&this._isAppearing?this._playSceneOrDisplayFallbackFrame():0!==e||this._player.paused||this._suspendScene()},Scene.prototype._handleContainerViewableChange=function(e){e&&this._suspended&&this._isAppearing?this._playSceneOrDisplayFallbackFrame():e||this._player.paused||this._suspendScene()},Scene.prototype.getDuration=function(){return{frames:this._durationInFrames,pixels:this._durationInPixels,milliseconds:this._durationInMilliseconds}},Scene.prototype._calculateKeyframeStyleAttributes=function(e){var t=this._claimedObjects.indexOf(e);return-1===t?{}:(t=this._objectVisibilityStates[t],e=e._calculateNewStyleAttributes(),t.isDirty(\"transition\")&&(\"show\"===t.transition?e.display=\"block\":\"hide\"===t.transition&&(e.display=\"none\")),e)},Scene.prototype.updateObjects=function(e){for(var t in this._claimedObjects){var i=this._claimedObjects[t];if(i.node){if(i.isAnimated){var n=this._keyframeAnimations[i.localId],a=e>=n.startKeyframe,s=e<=n.endKeyframe||!n.firstFrameAnimated,r=this._lastUpdateObjectsProgress>n.startKeyframe&&e<n.startKeyframe,o=this._lastUpdateObjectsProgress<n.endKeyframe&&e>n.endKeyframe,r=this._isReactiveScene&&(r||o);if(a&&s||this.fallbackFrameShown||r)n.firstFrameAnimated=!0,hasClass(i.node,\"animated\")||addClass(i.node,\"animated\");else{if(!hasClass(i.node,\"animated\"))continue;removeClass(i.node,\"animated\")}}o=this._keyframeAnimations[i.localId].animation.getValuesAt(e);Scene._updateLayoutWithValues(i.layoutCurrent,o,this._objectVisibilityStates[t]),i._keyframeStyle=this._calculateKeyframeStyleAttributes(i)}}this._lastUpdateObjectsProgress=e},Scene.prototype.renderObjects=function(){for(var e in this._claimedObjects){var t=this._claimedObjects[e];t.node&&((e=this._objectVisibilityStates[e]).isDirty(\"transition\")&&(\"show\"===e.transition?t.showAction(null,{},noop):\"hide\"===e.transition&&t.hideAction(null,{},noop),e.markClean(\"transition\")),t._applyStyles(t.node,t._keyframeStyle),t.redrawSceneLayout())}},Scene.prototype.getPlayer=function(){return this._player},Scene.prototype.claimObject=function(e){if(-1===this._claimedObjects.indexOf(e)){for(var t=0;t<Scene.instances.length;t++){var i=Scene.instances[t];i!==this&&(i.releaseObject(e),delete this._rawInitialValues[e.localId])}this._claimedObjects.push(e),this._objectVisibilityStates.push(new StateObject({transition:void 0}))}e._keyframeStyle=e._calculateNewStyleAttributes(),Scene._stopScenePlayersWithNoClaimedObjects()},Scene.prototype.releaseObject=function(e){e=this._claimedObjects.indexOf(e);this._initSceneOnRenderApiCall=!0,-1!==e&&(delete this._claimedObjects[e]._keyframeStyle,this._claimedObjects=this._claimedObjects.slice(0,e).concat(this._claimedObjects.slice(e+1)),this._objectVisibilityStates=this._objectVisibilityStates.slice(0,e).concat(this._objectVisibilityStates.slice(e+1)))},Scene.prototype.renderFirstFrame=function(){this._initAnimations(),this._renderAtProgress(0)},Scene.prototype._renderAtProgress=function(e){e=this.fallbackFrameShown?this.fallbackFrame/this._durationInFrames*100:void 0!==e?e:this._player.progress,this.updateObjects(e),this.renderObjects()},Scene.prototype._renderOnSizeChange=function(e){this._player.paused&&0<this._claimedObjects.length&&(this._renderAtProgress(e),this._removeAnimatedClass())},Scene.prototype.renderAtProgress=function(e){this._player.paused&&void 0!==e&&(this._initSceneOnRenderApiCall&&(this._initSceneOnRenderApiCall=!1,this._initAnimations()),this._renderAtProgress(e))},Scene.prototype.playSceneAction=function(e,t,i){var n=-1===[\"repeat\",\"reverse\"].indexOf(this.onEnd),a=\"repeat\"==this.onEnd&&!isNaN(this.onEndRepeatCount)&&0<this.onEndRepeatCount,s=\"reverse\"==this.onEnd&&!isNaN(this.onEndReverseCount)&&0<this.onEndReverseCount,a=this.onEndWaitForCount&&(a||s);n||a?(this._player.once(\"stop\",i),this._playSceneOrDisplayFallbackFrame()):(this._playSceneOrDisplayFallbackFrame(),i())},Scene.prototype.pauseSceneAction=function(e,t,i){\"time\"===this.type&&(this._pauseScene(),i())},Scene.prototype.stopSceneAction=function(e,t,i){\"time\"===this.type&&(this._stopScene(),i())},Scene.prototype.resetAction=function(e,t,i){this._player&&(this._stopScene(),this._player.destroy()),this._initPlayer(),this._suspended=!1,this._claimedObjects.forEach(function(e){this.releaseObject(e)},this),i()},Scene._extractPropertyValues=function(e){var t,i=Object.create(null);for(t in e){var n=e[t].timingFunction;n&&(n.instance=Scene._getTimingFunctionByName(n.id)),i[t]={value:e[t].value,timingFunction:n}}return i},Scene._keyframeWithUnitsToKeyframePx=function(e,t){for(var i,n=0;n<Scene._sizeProperties.length;n++){var a,s,r=Scene._sizeProperties[n];r in e&&(a=e[r].value,s=parseFloat(a),isNaN(s)||(-1<a.indexOf(\"%\")&&(s=s/100*t[r]),e[r].value=s))}for(i in Scene._positionProperties)for(var o=\"horizontal\"==i?\"width\":\"height\",l=Scene._positionProperties[i],c=0;c<l.length;c++){var h,p,d=l[c];d in e&&(h=e[d].value,p=parseFloat(h),isNaN(p)||(-1<h.indexOf(\"%\")&&(p=p/100*t[o]),e[d].value=p))}},Scene._getTimingFunctionByName=function(e){return e?AnimationTimingFunctions[camelize(e)]:null},Scene._fillInInitialAndDynamicKeyframeValues=function(e,t,i){var n,a,s=Object.keys(e),r=Math.min.apply(null,s);for(n in 0<r&&(e[r=0]=Object.create(null)),t)!e[r][n]&&-1<i.indexOf(n)&&(e[r][n]={value:t[n],timingFunction:{id:\"none\",instance:Scene._getTimingFunctionByName(\"none\")}});for(a in e)for(var o in e[a])e[a][o]&&null===e[a][o].value&&(e[a][o].value=t[o])},Scene._ensureSizeOrPositionValueHasUnits=function(e){return-1<(e=String(e)).indexOf(\"%\")||-1<e.indexOf(\"px\")?e:e+\"px\"},Scene._calcChangedPosition=function(e,t){var i,n=extend({},e),a=!1;for(i in Scene._positionProperties)for(var s=Scene._positionProperties[i],r=0;r<s.length;r++){var o=s[r];if(o in t){for(var l=0;l<s.length;l++)delete n[s[l]];n[o]=Scene._ensureSizeOrPositionValueHasUnits(t[o]),a=!0}}return a?n:null},Scene._updateLayoutWithValues=function(e,t,i){var n=Scene._calcChangedPosition(e.position,t);n&&(e.position=n),\"width\"in t&&(e.size.width=Scene._ensureSizeOrPositionValueHasUnits(t.width)),\"height\"in t&&(e.size.height=Scene._ensureSizeOrPositionValueHasUnits(t.height)),\"rotation\"in t&&(e.rotation=t.rotation),\"opacity\"in t&&(e.opacity=t.opacity),\"hidden\"in t&&(i?e.hidden&&!t.hidden?i.transition=\"show\":!e.hidden&&t.hidden&&(i.transition=\"hide\"):e.hidden=t.hidden)},Scene._stopScenePlayersWithNoClaimedObjects=function(){for(var e=0;e<Scene.instances.length;e++){var t=Scene.instances[e];0!=t._claimedObjects.length||t.paused||t._player.stop()}};;\nfunction View(){}extend(View.prototype,EventEmitter),View.prototype.awake=function(){this.node=null,this._loadingCount=null,this._waitingForLoadCallbacks=[],this._isAppearing=!1,this._hasAppearedAtLeastOnce=!1,this._shouldAppear=!1,this.incrementLoadingCount=this.incrementLoadingCount.bind(this),this.decrementLoadingCount=this.decrementLoadingCount.bind(this),this._preloadImage=this._preloadImage.bind(this),this._loadImage=this._loadImage.bind(this),this._trackViewShown=this._trackViewShown.bind(this),this.isTrackable&&this.once(\"appeared\",this._trackViewShown),this.hacks={}},View.prototype.incrementLoadingCount=function(){if(this.loaded)throw\"Starting loads after the view has already finished loading?\";this._loadingCount++},View.prototype.decrementLoadingCount=function(){if(!this.loading)throw\"decrementLoadingCount called while not loading!\";if(!--this._loadingCount){this.finishedLoading();for(var e=this._waitingForLoadCallbacks,t=0;t<e.length;t++)e[t]();this._waitingForLoadCallbacks=[]}},View.prototype._checkAndEnhanceParamsForFileLoading=function(e,t,i){if(e instanceof File)return\"function\"==typeof t?(i=t,t={}):void 0===t&&(t={}),{settings:t,callback:i||noop};throw\"file is not a File\"},View.prototype._preloadImage=function(e,t,i){var n=this._checkAndEnhanceParamsForFileLoading(e,t,i),a=(this.incrementLoadingCount(),creative.perf.start(\"View.preloadImage\",{uri:e.getUrl(n.settings.creativeUnitVariantId)}));this._loadImage(e,n.settings,function(e,t){a.end(),n.callback(e,t),this.decrementLoadingCount()}.bind(this))},View.prototype._loadImage=function(e,t,i){var n=this._checkAndEnhanceParamsForFileLoading(e,t,i),t=\"batching\"===creative.runtimeParams.variantChoices.BatchComponentAssets||n.settings.preferBatching,a=new Image,o=!1;return e.getDataUri({preferBatching:t,creativeUnitVariantId:n.settings.creativeUnitVariantId},function(e){o||(n.settings.crossOrigin&&(a.crossOrigin=n.settings.crossOrigin),a.onload=a.onerror=function(){a.onload=a.onerror=null,n.callback(a,e)},a.src=e)}),function(){o=!0,a.onload=a.onerror=null,a.src=\"\"}},View.prototype.finishedLoading=function(){this.emit(\"loaded\")},Object.defineProperty(View.prototype,\"loaded\",{get:function(){return this.node&&!this.loading}}),Object.defineProperty(View.prototype,\"loading\",{get:function(){return 0<this._loadingCount}}),View.prototype.createNode=function(){this.on(\"loaded\",creative.perf.start(\"View.load\",{clazz:this.constructor.name,name:this.name}).end);var e=\"undefined\"!=typeof creative&&creative.runtimeParams&&\"1\"==creative.runtimeParams.useComponentNameAsDataAttribute,t=document.createElement(\"div\");\"undefined\"!=typeof creative&&creative.adapter&&creative.adapter.useNativeClickForTapDetection&&(t.style.touchAction=\"manipulation\"),this.localId&&(t.id=\"celtra-object-\"+this.localId);for(var i=this.constructor;i;i=i.uber?i.uber.constructor:void 0){var n=i.name;n.endsWith(\"ECD\")&&(n=n.substring(0,n.length-3)),addClass(t,\"celtra\"+n.replace(/[A-Z]/g,function(e){return\"-\"+e.toLowerCase()}))}return e&&\"string\"==typeof this.name&&t.setAttribute(\"data-object-name\",this.name),t},View.prototype.getNode=function(e){return this.loaded?e&&defer(e,0,\"View.getNode defer finishedLoadingCallback\",useAsap()):(e&&this._waitingForLoadCallbacks.push(e),this.node||(this.incrementLoadingCount(),this.node=this.createNode(),defer(this.decrementLoadingCount,0,\"View.getNode defer decrementLoadingCount\",useAsap()))),this.node},Object.defineProperty(View.prototype,\"isAppearing\",{get:function(){return this._isAppearing}}),Object.defineProperty(View.prototype,\"hasAppearedAtLeastOnce\",{get:function(){return this._hasAppearedAtLeastOnce}}),View.prototype.appear=function(){var e=\"View APPEAR called on \"+this.constructor.name+\" \"+this.localId+\" (name=\"+this.name+\")\";this.parentContainer&&(e+=\" from \"+this.parentContainer.constructor.name+\" \"+this.parentContainer.localId+\" (name=\"+this.parentContainer.name+\")\"),Logger(\"events.appear\").log(e),this.hidden?this._shouldAppear=!0:this.isAppearing||(this._appear(),this._isAppearing=!0,this._hasAppearedAtLeastOnce=!0,this.emit(\"appeared\"))},View.prototype.disappear=function(){var e=\"View DISAPPEAR called on \"+this.constructor.name+\" \"+this.localId+\" (name=\"+this.name+\")\";this.parentContainer&&(e+=\" from \"+this.parentContainer.constructor.name+\" \"+this.parentContainer.localId+\" (name=\"+this.parentContainer.name+\")\"),Logger(\"events.appear\").log(e),this.isAppearing&&(this._disappear(),this._isAppearing=!1,this._shouldAppear=!1,this.emit(\"disappeared\"))},View.prototype.reset=function(){this.isAppearing&&(this._shouldAppear=this.hidden,this._isAppearing=!this.hidden)},View.prototype._appear=function(){},View.prototype._disappear=function(){},View.prototype.appearNestedObjects=function(){this.objects.forEach(function(e){e.appear()},this)},View.prototype.disappearNestedObjects=function(){this.objects.forEach(function(e){e.disappear()},this)},View.prototype._trackViewShown=function(e){var t={name:\"viewShown\",viewName:this.name,clazz:this.constructor.name};new ActionContext(this,!1).track(extend(t,e))};;\nBaseCreativeUnit.prototype.alert=function(e,t){var a=e.boxWidth||BaseCreativeUnit._defaultModalBoxWidth,i=(e.message=removeHtmlTags(e.message),this._calculateModalDialogActualHeight(e));a<=this.size.width&&i<=this.size.height?this.showModalDialogInPlacement(\"alert\",e,t):(alert(e.message),t())},BaseCreativeUnit.prototype.prompt=function(e,t){var a=e.boxWidth||BaseCreativeUnit._defaultModalBoxWidth,i=this._calculateModalDialogActualHeight(e);a<=this.size.width&&i<=this.size.height?this.showModalDialogInPlacement(\"prompt\",e,t):t(prompt(removeHtmlTags(e.message)))},BaseCreativeUnit.prototype._getUniqueId=function(){return(new Date).getTime()+Math.random()},BaseCreativeUnit.prototype.showModalDialogInPlacement=function(r,s,o){var d=this._getUniqueId(),e={};e[d]={attachment:\"node\",parentNode:this.node,positioning:\"screen\",type:\"layer\"},creative.adapter.createPlacements(e,function(){var t,e,a,i,l,n;window&&((t=creative.adapter.placements[d]).setZIndex(\"max\"),t.setSize(\"100%\",\"100%\"),t.setPosition(0,0),(e=t.createElement(\"link\")).rel=\"stylesheet\",e.type=\"text/css\",e.href=creative.resourceUrl+\"runner-min/clazzes/ModalDialogs-Overlay.css\",t.getContainer().appendChild(e),e=t.createElement(\"div\"),addClass(e,\"celtra-alert\"),e.style.display=\"none\",l=t.createElement(\"div\"),addClass(l,\"celtra-alert-dark-overlay\"),e.appendChild(l),this.preventScroll(e),a=t.createElement(\"div\"),addClass(a,\"celtra-alert-box\"),s.title&&\"\"!=s.title&&(l=t.createElement(\"h1\"),addClass(l,\"celtra-alert-title\"),l.innerHTML=s.title,a.appendChild(l)),s.title&&\"\"!=s.title&&s.message&&\"\"!=s.message&&(l=t.createElement(\"hr\"),addClass(l,\"celtra-alert-dark-line\"),a.appendChild(l),l=t.createElement(\"hr\"),addClass(l,\"celtra-alert-light-line\"),a.appendChild(l)),s.message&&\"\"!=s.message&&(l=t.createElement(\"div\"),addClass(l,\"celtra-alert-message\"),l.innerHTML=s.message,l.setAttribute(\"dir\",\"auto\"),\"auto\"!==l.dir&&(l.dir=guessWritingDirection(s.message)||\"ltr\"),a.appendChild(l)),\"prompt\"==r&&(i=t.createElement(\"input\"),addClass(i,\"celtra-alert-input\"),i.value=s.defaultValue||\"\",a.appendChild(i),attach(i,\"keypress\",function(e){13==e.keyCode&&(creative.adapter.destroyPlacement(d),t=null,o(i.value))})),l=t.createElement(\"div\"),addClass(l,\"celtra-alert-ok\"),l.innerHTML=\"OK\",a.appendChild(l),this.preventScroll(a),once(l,\"touchend\",function(e){e.preventDefault(),e.stopPropagation(),creative.adapter.destroyPlacement(d),t=null,\"prompt\"==r?o(i.value):o()},!1),\"prompt\"==r&&s.showCancel&&(addClass(l,\"with-cancel\"),n=t.createElement(\"div\"),addClass(n,\"celtra-alert-cancel\"),n.innerHTML=\"CANCEL\",a.appendChild(n),attach(l,\"touchstart\",function(e){e.preventDefault(),e.stopPropagation()},!1),once(n,\"touchend\",function(e){e.preventDefault(),e.stopPropagation(),creative.adapter.destroyPlacement(d),o(t=null)},!1)),e.appendChild(a),t.populate(e,function(){t.show();var e=setInterval(function(){\"absolute\"===celtra.styler.computedCSS(a,\"position\")&&(computedBoxHeight=parseInt(celtra.styler.computedCSS(a,\"height\"),10)+parseInt(celtra.styler.computedCSS(a,\"paddingTop\"),10)+parseInt(celtra.styler.computedCSS(a,\"paddingBottom\"),10),a.style.marginTop=-computedBoxHeight/2+\"px\",a.style.opacity=1,clearInterval(e),e=null)},100)}))}.bind(this))},BaseCreativeUnit.prototype._calculateModalDialogActualHeight=function(e){var t=document.createElement(\"div\");t.style.cssText=\"visibility:hidden; font-family:Helvetica,Arial,sans-serif; font-size:14px; padding-bottom:10px; text-shadow:0 1px 0 rgba(0,0,0,1); width:220px;\",t.innerText=e.message,this.node.appendChild(t);e=parseInt(window.getComputedStyle(t).getPropertyValue(\"height\"),10)+BaseCreativeUnit._defaultModalBoxHeightWithoutMessage;return this.node.removeChild(t),e},BaseCreativeUnit.prototype.preventScroll=function(t){[\"mousewheel\",\"touchmove\"].forEach(function(e){attach(t,e,function(e){e.preventDefault()},!1)})},BaseCreativeUnit._defaultModalBoxWidth=242,BaseCreativeUnit._defaultModalBoxHeightWithoutMessage=75;;\nfunction CreativeUnit(){}inherit(CreativeUnit,BaseCreativeUnit),extend(CreativeUnit.prototype,ScreenTransitioner),extend(CreativeUnit.prototype,FeedImageOptimizationTrait),CreativeUnit.IGNORABLES=BaseCreativeUnit.IGNORABLES,CreativeUnit.DEBUG_EXTRA_PHASE_DELAY=0,CreativeUnit.toString=function(){return\"[Clazz CreativeUnit]\"},CreativeUnit.prototype.toString=function(){return\"[CreativeUnit \"+this.name+\" (\"+this.localId+\")]\"},Object.defineProperty(CreativeUnit.prototype,\"size\",{get:function(){var t=this.getAvailableSize();return this.layoutCurrent||this._updateActiveLayout(this._getOrientation(),t.width,t.height),\"responsive\"===this.sizing?{width:t.width,height:t.height}:{width:this.layoutCurrent.unitSize.width,height:this.layoutCurrent.unitSize.height}},set:function(){}}),Object.defineProperty(CreativeUnit.prototype,\"unitAlignment\",{get:function(){return void 0===this.layoutCurrent.unitAlignment?{}:this.layoutCurrent.unitAlignment}}),Object.defineProperty(CreativeUnit.prototype,\"layoutCurrent\",{get:function(){return this.layouts[this.layoutCurrentIndex]}}),Object.defineProperty(CreativeUnit.prototype,\"hasOverflow\",{get:function(){return 0<this.screens.concat([this.master]).filter(function(t){return t.showOverflow}).length}}),Object.defineProperty(CreativeUnit.prototype,\"hasScreens\",{get:function(){return 0<this.screens.length}}),Object.defineProperty(CreativeUnit.prototype,\"currentScreen\",{set:function(t){this._currentScreen!==t&&(this._currentScreen=t,this.emit(\"shareableStateChanged\"))},get:function(){return this._currentScreen}}),CreativeUnit.prototype._getDestinationScreen=function(){var t=this.hasScreens?this.screens[0]:this.master;if(\"modal\"==this.name&&void 0!==creative.runtimeParams.goToPageAfterExpand){var e=creative.runtimeParams.goToPageAfterExpand;if(\"string\"!=typeof e)throw new TypeError(\"Only string type is allowed to reference destination screen!\");var i=this.screens.filter(function(t){return t.title===e});0<i.length&&(t=i[0])}return t},CreativeUnit.prototype.awake=function(){var t=this;CreativeUnit.uber.awake.call(this),this._currentScreen=null,this.visibleScreen=null,this.overflowsY=!1,this.layoutCurrentIndex=null,this._preloadReachableScreens=this._preloadReachableScreens.bind(this),this.screens.forEach(function(t,e){t.objects.forEach(function(t){t.zIndex+=ScreenObject._zIndexOffset})}),void 0===this.backgroundColor&&(this.backgroundColor=\"#000000\"),void 0===this.usePlaceboBar&&(this.usePlaceboBar=!1),void 0===this.showCloseButton&&(this.showCloseButton=!1),void 0===this.alignBackButtonHorizontal&&(this.alignBackButtonHorizontal=\"left\"),this.on(\"_firstScreenLoaded\",function(){t.placeboBar&&(t.placeboBar.parentNode.removeChild(t.placeboBar),t.placeboBar=null),this.initiateScreenTransition(this._getDestinationScreen())}.bind(this)),this.onAll(\"_firstScreenLoaded _unitFirstShown\",function(){this.goToScreen(this._getDestinationScreen(),null)}.bind(this)),this.triggerOrientationChange=this.triggerOrientationChange.bind(this),this.updateWindowMetrics=this.updateWindowMetrics.bind(this),this.hacks.triggerBlurWhenTappingOutsideOfInputs=ios()},CreativeUnit.prototype.setAvailableSize=function(t,e){var i=CreativeUnit.uber.setAvailableSize.apply(this,arguments),n=this._getOrientation(),o=this.layouts[this.layoutCurrentIndex],r=(this._updateActiveLayout(n,t,e),this.updateWindowMetrics(n,t,e),this._previousOrientation&&n!=this._previousOrientation),o=o&&o.orientation!=this.layoutCurrent.orientation;(r||o)&&this.triggerOrientationChange(),this._previousOrientation=n,(i||o)&&this.emit(\"resize\")},CreativeUnit.prototype._getOrientation=function(){return creative.adapter.orientation%180==0?\"portrait\":\"landscape\"},CreativeUnit.prototype._updateActiveLayout=function(e,i,n){var t=this.layouts.slice(),o=this.layoutCurrentIndex;this.layouts[o];t.forEach(function(t){t.sortIndex=0,\"independent\"==t.orientation?t.sortIndex+=1:t.orientation!=e&&(t.sortIndex+=4e3),t.minSize.width>i&&(t.sortIndex+=2e3),t.minSize.height>n&&(t.sortIndex+=2e3),t.sortIndex+=Math.abs(t.minSize.width-i)+Math.abs(t.minSize.height-n)}),t.sort(function(t,e){return t.sortIndex-e.sortIndex}),this.layoutCurrentIndex=this.layouts.indexOf(t[0]),o!=this.layoutCurrentIndex&&this.emit(\"layoutChanged\")},CreativeUnit.prototype.enterRenderTree=function(){var t;this._isRendering||(this._isRendering=!0,t=this.getAvailableSize(),this.updateWindowMetrics(this._getOrientation(),t.width,t.height),this.master&&(this.master.loaded?defer(this.master.enterRenderTree.bind(this.master),void 0,void 0,useAsap()):this.once(\"_firstScreenLoaded\",this.master.enterRenderTree.bind(this.master))),this.visibleScreen&&defer(this.visibleScreen.enterRenderTree.bind(this.visibleScreen),void 0,void 0,useAsap()),this.hacks.triggerBlurWhenTappingOutsideOfInputs&&(attach(this.node,\"touchstart\",function(t){-1==[\"INPUT\",\"TEXTAREA\",\"SELECT\"].indexOf(t.target.nodeName)&&null!=this.lastInputElementFocused&&this.lastInputElementFocused.blur()}.bind(this),!0),attach(this.node,\"focus\",function(t){-1<[\"INPUT\",\"TEXTAREA\",\"SELECT\"].indexOf(t.target.nodeName)&&(this.lastInputElementFocused=t.target)}.bind(this),!0),attach(this.node,\"blur\",function(t){-1<[\"INPUT\",\"TEXTAREA\",\"SELECT\"].indexOf(t.target.nodeName)&&(this.lastInputElementFocused=null)}.bind(this),!0)))},CreativeUnit.prototype.exitRenderTree=function(){this._isRendering&&(this._isRendering=!1,this.master&&this.master.exitRenderTree(),this.visibleScreen&&this.visibleScreen.exitRenderTree())},CreativeUnit.prototype._appear=function(){this.loaded||Creative._throw(\"Cannot show the unit before it has finished loading.\"),this.isAppearing&&Creative._throw(\"The unit is already appearing.\"),this.node.parentNode||Creative._throw(\"Creative unit must be added to DOM before show() is called. Animations will start on show().\"),Logger(\"unit\").log(\"Showing \"+this),this.inView&&(this.inView.start(),this.currentScreen&&this.currentScreen.inView&&this.currentScreen.inView.start()),this.hasAppearedAtLeastOnce||(this.placeboBar&&this.placeboBar.start(),this.orientationLockIsOn?this._emitUnitFirstShownAfterOrientationPrompt=!0:this.emit(\"_unitFirstShown\"))},CreativeUnit.prototype._disappear=function(){this.isAppearing||Creative._throw(\"The unit is not appearing, can not disappear.\"),Logger(\"unit\").log(\"Hiding \"+this),this.inView&&this.inView.stop()},CreativeUnit.prototype.createNode=function(){var i,n,t=this.unitDiv=CreativeUnit.uber.createNode.call(this),e=(t.id=\"celtra-\"+this.name.replace(/[A-Z]/g,function(t){return\"-\"+t.toLowerCase()}),Color.isGradient(this.backgroundColor)?addCssRule(\"#\"+t.id,Color.createGradientStyle(this.backgroundColor)):t.style.backgroundColor=this.backgroundColor,t.style.overflow=this.master.showOverflow?\"visible\":\"hidden\",this.screenContainer=document.createElement(\"div\"),this.screenContainer.className=\"celtra-screen-container\",this.screenContainer.style.position=\"absolute\",t.appendChild(this.screenContainer),this.screenHolder=document.createElement(\"div\"),this.screenHolder.className=\"celtra-screen-holder\",this.screenHolder.style.width=\"100%\",this.screenHolder.style.height=\"100%\",this.screenContainer.appendChild(this.screenHolder),this.borderColor&&0<this.borderSize&&((e=document.createElement(\"div\")).className=\"celtra-creative-border\",e.style.borderColor=this.borderColor,e.style.borderWidth=this.borderSize+\"px\",t.appendChild(e)),t.style.position=\"absolute\",this.usePlaceboBar&&(e=this.loadingScreen&&this.loadingScreen.loaderColor||\"white\",this.placeboBar=new PlaceboBar(5e3,.9,200,e),this.screenContainer.appendChild(this.placeboBar)),this.showCloseButton&&(this.closeAction||Creative._throw(\"Close button requested, but no close action defined?\"),this.closeButtonType&&\"text\"===this.closeButtonType?((i=document.createElement(\"div\")).className=\"celtra-close-button touchable celtra-close-button-text\",i.textContent=this.closeButtonText,i.style.color=this.closeButtonTextColor,this.unitDiv.appendChild(i)):(i=document.createElement(\"img\"),n=document.createElement(\"img\"),this.closeButtonUp?(this.closeButtonDown||(this.closeButtonDown=this.closeButtonUp),this.closeButtonWidth&&this.closeButtonHeight&&(i.style.width=n.style.width=this.closeButtonWidth+\"px\",i.style.height=n.style.height=this.closeButtonHeight+\"px\")):(this.closeButtonUp=new File(\"runner/clazzes/CreativeUnit/close-up.svg\"),this.closeButtonDown=new File(\"runner/clazzes/CreativeUnit/close-down.svg\")),i.className=\"celtra-close-button touchable celtra-close-button-up\",this._preloadImage(this.closeButtonUp,function(t,e){i.src=e}),n.className=\"celtra-close-button celtra-close-button-down\",this._preloadImage(this.closeButtonDown,function(t,e){n.src=e}),this.closeButtonImage=i,this.closeButtonImagePressed=n,this.unitDiv.appendChild(this.closeButtonImage),this.unitDiv.appendChild(this.closeButtonImagePressed)),attach(i,\"tap\",function(){var t=this.currentScreen||this.visibleScreen;this.closeAction(t?new ActionContext(t,!0):null,{},noop)}.bind(this),!1)),[]),o=(e.push({name:\"loading\",fun:function(t){var e;this.loadingScreen?(e=creative.perf.start(\"CreativeUnit.loadingScreen\"),this.loadingScreen.getNode(function(){defer(function(){e.end(),this.initiateScreenTransition(this.loadingScreen),t(!0)}.bind(this),CreativeUnit.DEBUG_EXTRA_PHASE_DELAY,\"CreativeUnit.createNode defer loading phase completion\",useAsap())}.bind(this))):t(!1)}.bind(this)}),e.push({name:\"interactive\",fun:function(t){var e=creative.perf.start(\"CreativeUnit.firstScreenLoaded\"),i=this.hasScreens?2:1,n=function(){--i||defer(function(){e.end(),defer(this.emits(\"_firstScreenLoaded\"),0,\"CreativeUnit.createNode emit _firstScreenLoaded\",useAsap()),t(!0)}.bind(this),CreativeUnit.DEBUG_EXTRA_PHASE_DELAY,\"CreativeUnit.createNode defer onScreenLoaded\",useAsap())}.bind(this);this.hasScreens&&this._getDestinationScreen().getNode(n),this.master.getNode(n)}.bind(this)}),this.incrementLoadingCount(),!1),r=function(){o||Creative._throw(\"Unit still not ready to show after last phase?!\")};return e.reverse().forEach(function(e){var i=r;r=function(){Logger(\"unit\").log(\"Phase '\"+e.name+\"' started.\"),e.fun(function(t){Logger(\"unit\").log(\"Phase '\"+e.name+\"' finished, \"+(t?\"DID\":\"DID NOT\")+\" draw something.\"),t&&!o&&(o=!0,this.decrementLoadingCount()),i()}.bind(this))}.bind(this)}.bind(this)),r(),t},CreativeUnit.prototype.getAccessibilityProperties=function(){return this.accessibility},CreativeUnit.prototype._repositionCloseButton=function(t,e,i){var n,o,r;function s(t){return{left:t.offsetLeft,top:t.offsetTop,width:t.offsetWidth,height:t.offsetHeight}}this.closeButtonImage&&((o=this.hasOverflow||this.orientationPrompt&&\"none\"!=this.orientationPrompt.style.display||(r=this.closeButtonImage,o=this.screenContainer,n=s(n=this.unitDiv),o=s(o),(r=s(r)).left=o.left+o.width-r.width,r.top=o.top,!(n.left<=r.left&&r.left+r.width-1<n.left+n.width&&n.top<=r.top&&r.top+r.height-1<n.top+n.height))?this.unitDiv:this.screenContainer)!==(r=this.closeButtonImage.parentNode)&&(r&&(r.removeChild(this.closeButtonImage),r.removeChild(this.closeButtonImagePressed)),o.appendChild(this.closeButtonImage),o.appendChild(this.closeButtonImagePressed)),window.navigator.userAgent.match(/iP(hone|od)/)&&ios(\"7\")&&creative.adapter.hasDeadZones&&(this.closeButtonImage.style.top=this.closeButtonImagePressed.style.top=(\"landscape\"==t?20:0)+\"px\"))},CreativeUnit.prototype.triggerOrientationChange=function(){this.currentScreen&&this.currentScreen.triggerOrientationChange(),android()&&this.hideKeyboard()},CreativeUnit.prototype.hideKeyboard=function(){this.unitDiv&&[].slice.call(this.unitDiv.querySelectorAll(\"input,select,textarea\")).forEach(function(t){t.blur()})},CreativeUnit.prototype.enableWrongOrientationPrompt=function(){var s;this.unitDiv&&!this.orientationPrompt&&\"thumb\"!==creative.runtimeParams.purpose&&(this.orientationPrompt=document.createElement(\"img\"),this.orientationPrompt.style.cssText=\"display: none; position: absolute; left: 50%; top: 50%; z-index: 999999\",(s=this).orientationPromptImage?this.loaded||this._preloadImage(this.orientationPromptImage,function(t,e){var i=s.orientationPromptImage.width,n=s.orientationPromptImage.height,o=Math.round(i/2),r=Math.round(n/2);s.orientationPrompt.style.cssText+=\"margin-left: -\"+o+\"px; margin-top: -\"+r+\"px; width: \"+i+\"px; height: \"+n+\"px;\",s.orientationPrompt.src=e}):(this.orientationPrompt.style.cssText+=\"margin-left: -640px; margin-top: -640px;\",celtra.styler.css(this.orientationPrompt,\"transformOrigin\",\"50% 50%\"),celtra.styler.css(this.orientationPrompt,\"transform\",\"scale(0.80)\"),this.loaded||this._preloadImage(new File(\"runner/rotate-screen_tablet_01.png\"),function(t,e){s.orientationPrompt.src=e})),this.unitDiv.appendChild(this.orientationPrompt))},CreativeUnit.prototype._updateSize=function(){var t=function(t,e,i){\"left\"==e?(t.style.left=\"0\",t.style.marginLeft=\"\",t.style.right=\"auto\"):\"right\"==e?(t.style.left=\"auto\",t.style.marginLeft=\"\",t.style.right=\"0\"):(t.style.left=\"50%\",t.style.marginLeft=\"-\"+this.size.width/2+\"px\",t.style.right=\"auto\"),\"top\"==i?(t.style.top=\"0\",t.style.marginTop=\"\",t.style.bottom=\"auto\"):\"bottom\"==i?(t.style.top=\"auto\",t.style.marginTop=\"\",t.style.bottom=\"0\"):(t.style.top=\"50%\",t.style.marginTop=\"-\"+this.size.height/2+\"px\",t.style.bottom=\"auto\")}.bind(this);\"responsive\"===this.sizing?(this.unitDiv.style.width=\"100%\",this.unitDiv.style.height=\"100%\",this.screenContainer.style.width=\"100%\",this.screenContainer.style.height=\"100%\"):this.fillsContainer?(this.unitDiv.style.width=\"100%\",this.unitDiv.style.height=this.overflowsY?this.size.height+\"px\":\"100%\",this.screenContainer.style.width=this.size.width+\"px\",this.screenContainer.style.height=this.size.height+\"px\",t(this.screenContainer,this.unitAlignment.horizontal,this.unitAlignment.vertical)):(this.unitDiv.style.width=this.size.width+\"px\",this.unitDiv.style.height=this.size.height+\"px\",t(this.unitDiv,this.unitAlignment.horizontal,this.unitAlignment.vertical),this.screenContainer.style.width=\"100%\",this.screenContainer.style.height=\"100%\")},CreativeUnit.prototype.updateWindowMetrics=function(t,e,i){this._isRendering&&(this._updateSize(),this._toggleWrongOrientationPrompt(t,e,i),setTimeout(function(t,e,i){this._repositionCloseButton(t,e,i)}.bind(this),0,t,e,i))},CreativeUnit.prototype.setCorrectOrientation=function(t){this._correctOrientation=t},CreativeUnit.prototype._toggleWrongOrientationPrompt=function(t,e,i){var n;this.orientationPrompt&&(n=this.orientation,(n=this._correctOrientation?this._correctOrientation:n)!==t?(this.orientationPrompt.style.display=\"block\",this.orientationPrompt.offsetWidth,!0!==this.orientationLockIsOn&&this.emit(\"orientationLockChanged\",!0),this.orientationLockIsOn=!0):(!(this.orientationPrompt.style.display=\"none\")!==this.orientationLockIsOn&&this.emit(\"orientationLockChanged\",!1),this.orientationLockIsOn=!1,this._emitUnitFirstShownAfterOrientationPrompt&&(this._emitUnitFirstShownAfterOrientationPrompt=!1,this.emit(\"_unitFirstShown\"))))},CreativeUnit.prototype.getMasterVideo=function(){var e=null;return this.master.objects.some(function(t){if(\"MasterVideo\"===t.constructor.name)return e=t,!0}),e},CreativeUnit.prototype.playMasterVideoAction=function(t,e,i){this.getMasterVideo().playAction(t,e,i)},CreativeUnit.prototype.pauseMasterVideoAction=function(t,e,i){this.getMasterVideo().pauseAction(t,e,i)},CreativeUnit.prototype.muteMasterVideoAction=function(t,e,i){this.getMasterVideo().muteAction(t,e,i)},CreativeUnit.prototype.unMuteMasterVideoAction=function(t,e,i){this.getMasterVideo().unMuteAction(t,e,i)},CreativeUnit.prototype.goToScreenAction=function(t,e,i){if(!e.screen)return console.warn(\"Go to screen action is called without a destination page!\"),i&&i();extend(e,e.animation),\"LeanLoading\"===this._getDestinationScreen().title?creative.adapter.waitForWindowLoad(function(){this.goToScreen(e.screen,e,i)}.bind(this)):this.goToScreen(e.screen,e,i)},CreativeUnit.prototype.findAll=function(e){var i=[];return this.screens.forEach(function(t){void 0!==e&&t.title!=e||i.push(t),t.findAll(e).forEach(function(t){i.push(t)})}),i},CreativeUnit.prototype.findByType=function(e){var i=[];return void 0===e?result:(this.screens.forEach(function(t){t.findByType(e).forEach(function(t){i.push(t)})}),i)};;\nfunction ScreenObjectContainer(){}inherit(ScreenObjectContainer,View),ScreenObjectContainer.toString=function(){return\"[Clazz ScreenObjectContainer]\"},ScreenObjectContainer.prototype.toString=function(){return\"[ScreenObjectContainer \"+this.localId+\"]\"},ScreenObjectContainer.prototype.appear=function(){ScreenObjectContainer.uber.appear.call(this),ScreenObjectContainer.uber.appearNestedObjects.call(this)},ScreenObjectContainer.prototype.disappear=function(){ScreenObjectContainer.uber.disappearNestedObjects.call(this),ScreenObjectContainer.uber.disappear.call(this)};;\nfunction Screen(){}inherit(Screen,ScreenObjectContainer),extend(Screen.prototype,TriggerHost),extend(Screen.prototype,SceneHost),extend(Screen.prototype,DiscoverableTrait),Screen.toString=function(){return\"[Clazz Screen]\"},Screen.prototype.toString=function(){return\"[Screen \"+this.localId+\" (name=\"+this.title+\")]\"},Object.defineProperty(Screen.prototype,\"name\",{get:function(){return this.title}}),Object.defineProperty(Screen.prototype,\"size\",{get:function(){return this.parentContainer.size}}),Screen.prototype.awake=function(){ScreenObjectContainer.uber.awake.apply(this,arguments),this._isRendering=!1,this.appearedAtLeastOnce=!1,this._updateSize=this._updateSize.bind(this),this.handleShake=this.handleShake.bind(this),this.handleShakeFallback=this.handleShakeFallback.bind(this)},Screen.prototype.isFirstScreen=function(){return this===this.parentContainer.screens[0]},Screen.prototype.isLoadingScreen=function(){return this===this.parentContainer.loadingScreen},Screen.prototype.isMasterScreen=function(){return this===this.parentContainer.master},Screen.prototype.isNormalScreen=function(){return!this.isMasterScreen()&&!this.isLoadingScreen()},Object.defineProperty(Screen.prototype,\"parentUnitVariant\",{get:function(){return\"CreativeUnitWithVariants\"==this.parentUnit.constructor.name||\"CreativeUnitWithVariantsECD\"==this.parentUnit.constructor.name?(this._parentContainer||(this._parentContainer=this.parentUnit.getVariantForScreen(this)),this._parentContainer):null}}),Object.defineProperty(Screen.prototype,\"parentContainer\",{get:function(){return this.parentUnitVariant||this.parentUnit}}),Screen.prototype.createNode=function(){Logger(\"unit\").log(\"Starting to load \"+this),!this.isLoadingScreen()&&creative.experiments.get(\"TrackingLoadingTimes\")&&this.once(\"loaded\",creative.aggregatorTracking.trackAggregatorMagicTriplet(\"screenLoad\",{unitName:this.parentUnit.name,screenRank:this.isFirstScreen()?\"First\":this.isMasterScreen()?\"Master\":\"Other\"},3e4)),this.nestedSceneHosts=[];var t=Screen.uber.createNode.call(this);return t.style.overflow=this.showOverflow?\"visible\":\"hidden\",this.objects.forEach(function(e){this.incrementLoadingCount(),t.appendChild(e.getNode(this.decrementLoadingCount))},this),this.parentContainer.on(\"resize\",this._updateSize),t.style.width=t.style.height=\"100%\",this.parentContainer.inView&&(this.inView=new InViewObject(this,this.parentContainer.inView),this.inViewTriggerSetup()),this._updateSize(),t},Screen.prototype.initImmediatelyPlayedScenes=function(){this._initImmediatelyPlayedScenes(),this.nestedSceneHosts.forEach(function(e){e._initImmediatelyPlayedScenes()})},Screen.prototype._appear=function(){var e=!this.appearedAtLeastOnce,t=(this.appearedAtLeastOnce=!0,new ActionContext(this,{certainlyNotCausedByUserBehavior:!!this.parentUnit.firstAppearWithoutUserInteraction&&e&&(this.isFirstScreen()||this.isMasterScreen())}));this!==this.parentContainer.loadingScreen&&this!==this.parentContainer.master&&(t.track({name:\"screenShown\"}),creative.trackCreativeRenderedOnScreenShow()),e?this.fireTriggers(\"firstAppear\",null,function(){defer(function(){this.fireTriggers(\"appear\",null,noop,t)}.bind(this),void 0,void 0,useAsap())}.bind(this),t):this.fireTriggers(\"appear\",null,noop,t),this.inView&&this.inView.start(),this._shakeListenerStart()},Screen.prototype._shakeListenerStart=function(){this.hasTriggers(\"shake\")&&(this.shakeListener||(this.shakeListener=new ShakeListener),this.shakeListener.on(\"shake\",this.handleShake),this.shakeListener.on(\"shakeFallback\",this.handleShakeFallback),this.shakeListener.start())},Screen.prototype._shakeListenerStop=function(){this.shakeListener&&(this.shakeListener.off(\"shake\",this.handleShake),this.shakeListener.off(\"shakeFallback\",this.handleShakeFallback),this.shakeListener.stop())},Screen.prototype._disappear=function(){this.inView&&this.inView.stop(),this._shakeListenerStop()},Screen.prototype.handleShakeFallback=function(){var n;this.parentUnit.currentScreen===this&&(n=new ActionContext(this,!1),this.fireTriggers(\"shake\",function(e,t){var i=1e3*(void 0!==e.fallbackEmitDelay?e.fallbackEmitDelay:2);return void 0===e.fallbackCustomActionSet||\"event-actions\"===e.fallbackCustomActionSet?setTimeout(function(){this.triggerActionByLocalId(n,{localId:t.localId},noop)}.bind(this),i):setTimeout(function(){this.triggerActionByLocalId(n,{localId:e.fallbackCustomActionSet},noop)}.bind(this),i),!1}.bind(this),null,n))},Screen.prototype.handleShake=function(n){var e;this.parentUnit.currentScreen===this&&(e=new ActionContext(this,!1),this.fireTriggers(\"shake\",function(e,t){var i=!1;return!!(e.direction&&-1<e.direction.indexOf(n))&&(clearTimeout(t.shakeTimeout),t.shakeStartTime||(t.shakeStartTime=new Date),e=e.duration&&(new Date).getTime()-t.shakeStartTime.getTime()>=e.duration,t.shakeTimeout=setTimeout(function(){delete t.shakeStartTime,t.triggered=!1},500),e&&!t.triggered&&(t.triggered=i=!0),i)}.bind(this),null,e))},Screen.prototype.enterRenderTree=function(){this._isRendering||(this._isRendering=!0,this.emit(\"enteredRenderTree\"))},Screen.prototype.exitRenderTree=function(){this._isRendering&&(this._isRendering=!1,this.emit(\"exitedRenderTree\"))},Screen.prototype._updateSize=function(){this.emit(\"resize\")},Screen.prototype.finishedLoading=function(){Screen.uber.finishedLoading.call(this),Logger(\"unit\").log(\"Finished loading \"+this)},Screen.prototype.triggerBeforeAppear=function(e){var t=new ActionContext(this,{certainlyNotCausedByUserBehavior:!!this.parentUnit.firstAppearWithoutUserInteraction&&!this.appearedAtLeastOnce&&(this.isFirstScreen()||this.isMasterScreen())});this.fireTriggers(\"beforeAppear\",null,e,t)},Screen.prototype.triggerOrientationChange=function(e){this.fireTriggers(\"orientationChange\",null,e)},Screen.prototype.getMaxZIndex=function(){var t=0;return this.objects.forEach(function(e){e.zIndex>t&&(t=e.zIndex)}),t},Screen.prototype.inViewTriggerSetup=function(){var t=!1,i=creative&&creative.adapter&&creative.adapter.adBehavior.subpixelScrollable;this.triggers.forEach(function(e){i&&\"enterView\"===e.type&&e.parameters&&100==e.parameters.area&&(e.parameters.area=99.9),\"enterView\"!==e.type&&\"leaveView\"!==e.type||(t=!0,this.inViewAreaRatio=0)},this),t&&this.inView.on(\"areaInViewRatioChanged\",this.triggerInView.bind(this))},Screen.prototype.triggerInView=function(e){var t=new ActionContext(this),i=\"enterView\",n=this.inViewAreaRatio,r=e,a=function(e,t){return e.parameters.area-t.parameters.area};e<this.inViewAreaRatio&&(i=\"leaveView\",r=this.inViewAreaRatio,n=e,a=function(e,t){return t.parameters.area-e.parameters.area}),this.fireSortedTriggers(i,function(e){e=e.area/100;return n<=e&&e<=r},null,t,a),this._getTriggers(i,function(e){e=e.area/100;return n<=e&&e<=r}),this.inViewAreaRatio=e},Screen.prototype.getActionsExecutedOnAppear=function(){var e=!this.appearedAtLeastOnce,t=new ActionContext(this,{certainlyNotCausedByUserBehavior:!!this.parentUnit.firstAppearWithoutUserInteraction&&e&&(this.isFirstScreen()||this.isMasterScreen())}),i=this.fireTriggersDryRun(\"beforeAppear\",null,noop,t);return(i=e?i.concat(this.fireTriggersDryRun(\"firstAppear\",null,noop,t)):i).concat(this.fireTriggersDryRun(\"appear\",null,noop,t))},Screen.prototype.resetAction=function(t,i,e){var n=this.findAll(),r=n.length,a=function(){this._shakeListenerStop(),this._shakeListenerStart(),e()}.bind(this);r?n.forEach(function(e){e.resetAction(t,i,function(){--r||a()})}):a()},Screen.prototype.shuffleAction=function(e,t,i){var n=function(e){var t,i,n=e.length;for(;0!==n;)i=Math.floor(Math.random()*n),t=e[--n],e[n]=e[i],e[i]=t;return e}(t.objects.map(function(e){return JSON.parse(JSON.stringify(e.layoutSpecificValues))}));t.objects.forEach(function(e,i){e.layoutSpecificValues.forEach(function(e,t){e.position=n[i][t].position}),e._refreshDisplayState()}),i&&i()},Screen.prototype._trackViewShown=function(){Screen.uber._trackViewShown.call(this,{isMasterScreen:this.isMasterScreen(),isLoadingScreen:this.isLoadingScreen()})};;\nfunction ScreenObject(){}inherit(ScreenObject,View),extend(ScreenObject.prototype,TriggerHost),extend(ScreenObject.prototype,DiscoverableTrait),extend(ScreenObject.prototype,ClickPreventer),ScreenObject.requiresRedrawToTriggerAnimationEvents=android(\"4.0\"),ScreenObject.redrawTriggerInterval=60,ScreenObject.toString=function(){return\"[Clazz ScreenObject]\"},ScreenObject.prototype.toString=function(){return\"[ScreenObject \"+this.localId+\" (name=\"+this.name+\")]\"},ScreenObject.allowPositioningAndSizingUsingTransforms=!android(\"2.2\",\"2.3\")||celtra.iframe(),ScreenObject._zIndexOffset=1e4,ScreenObject._resettableProperties=[\"position\",\"size\",\"rotation\",\"zIndex\",\"opacity\",\"hidden\"],ScreenObject._3dTransformBlacklisted=function(t){return/Silk\\/\\d|Kindle Fire| KFTT | KFOT | KFJWI | KFJWA |\\bGT-P3100\\b| HTC[ _]One[ _]S /.test(t)||/\\bGT-I9300\\b/.test(t)&&android(\"4.0\")}(navigator.userAgent),ScreenObject.supports3d=function(){return(\"WebKitCSSMatrix\"in window&&\"m11\"in new WebKitCSSMatrix||\"MSCSSMatrix\"in window||/Gecko\\//.test(navigator.userAgent))&&!this._3dTransformBlacklisted}(),ScreenObject.requires3dTranslateForPositioning=/GT-I9500|GT-I9505|SM-N900|GT-P5210|SM-T520/.test(navigator.userAgent),ScreenObject.dummyImageSpacer=windows(\"10\"),ScreenObject._toPositionPx=function(e,t,i){var n,r,o,s,a=i.width,i=i.height,c=t.width,t=t.height;return[\"left\",\"right\",\"hcenter\"].forEach(function(t){t in e&&(n=t)}),[\"bottom\",\"top\",\"vcenter\"].forEach(function(t){t in e&&(r=t)}),o=e[n],s=e[r],o=-1<o.indexOf(\"%\")?a*parseFloat(o)/100:parseFloat(o),\"hcenter\"===n?o=(a-c)/2+o:\"right\"===n&&(o=a-(o+c)),s=-1<s.indexOf(\"%\")?i*parseFloat(s)/100:parseFloat(s),\"vcenter\"===r?s=(i-t)/2+s:\"bottom\"===r&&(s=i-(s+t)),{x:ScreenObject._roundIfNeeded(o),y:ScreenObject._roundIfNeeded(s)}},ScreenObject._toSizePx=function(t,e){var i=parseFloat(t.width),n=parseFloat(t.height),r=e.width,e=e.height;return-1<t.width.toString().indexOf(\"%\")&&(i=r*i/100),-1<t.height.toString().indexOf(\"%\")&&(n=e*n/100),{width:ScreenObject._roundIfNeeded(i),height:ScreenObject._roundIfNeeded(n)}},ScreenObject._roundIfNeeded=function(t){return Math.round(t)},Object.defineProperty(ScreenObject.prototype,\"layoutCurrent\",{get:function(){return null==this.layoutCurrentIndex?this.layoutSpecificValues[0]:this.layoutSpecificValues[this.layoutCurrentIndex]},enumerable:!0,configurable:!1}),Object.defineProperty(ScreenObject.prototype,\"layoutCurrentIndex\",{get:function(){return this.parentUnit.layoutCurrentIndex},enumerable:!0,configurable:!1}),ScreenObject._Position=function(t,e){this._instance=t,this._position=e},Object.defineProperties(ScreenObject._Position.prototype,{x:{get:function(){return this._position.x},set:function(t){delete this._instance.layoutCurrent.right,delete this._instance.layoutCurrent.hcenter,this._instance.layoutCurrent.position.left=t+(\"number\"==typeof t?\"px\":\"\")}},y:{get:function(){return this._position.y},set:function(t){delete this._instance.layoutCurrent.bottom,delete this._instance.layoutCurrent.vcenter,this._instance.layoutCurrent.position.top=t+(\"number\"==typeof t?\"px\":\"\")}}}),ScreenObject._Size=function(t,e){this._instance=t,this._size=e},Object.defineProperties(ScreenObject._Size.prototype,{width:{get:function(){return this._size.width},set:function(t){this._instance.layoutCurrent.size.width=t+(\"number\"==typeof t?\"px\":\"\")}},height:{get:function(){return this._size.height},set:function(t){this._instance.layoutCurrent.size.height=t+(\"number\"==typeof t?\"px\":\"\")}}}),Object.defineProperties(ScreenObject.prototype,{position:{get:function(){var t=ScreenObject._toPositionPx(this.layoutCurrent.position,this.size,this.parentContainer.size);return new ScreenObject._Position(this,t)},set:function(t){delete this.layoutCurrent.position.right,delete this.layoutCurrent.position.hcenter,delete this.layoutCurrent.position.bottom,delete this.layoutCurrent.position.vcenter,this.layoutCurrent.position.left=t.left,this.layoutCurrent.position.top=t.top}},originalSize:{get:function(){var t=this.layoutCurrentIndex||0;return ScreenObject._toSizePx(JSON.parse(this.originalLayoutSpecificValues)[t].size,this.parentContainer.size)},set:function(t){throw new Error(\"originalSize is not settable!\")}},size:{get:function(){var t=ScreenObject._toSizePx(this.layoutCurrent.size,this.parentContainer.size);return new ScreenObject._Size(this,t)},set:function(t){this.layoutCurrent.size.width=t.width,this.layoutCurrent.size.height=t.height}},opacity:{get:function(){return this.layoutCurrent.opacity},set:function(t){this.layoutCurrent.opacity=t}},rotation:{get:function(){return this.layoutCurrent.rotation},set:function(t){this.layoutCurrent.rotation=t}},hidden:{get:function(){return this.layoutCurrent.hidden},set:function(e){this.layoutSpecificValues.forEach(function(t){t.hidden=e})}}}),ScreenObject.getCSSTranslate=function(t,e){return ScreenObject.requires3dTranslateForPositioning?\"translate3d(\"+t+\"px,\"+e+\"px, 0)\":\"translate(\"+t+\"px,\"+e+\"px)\"},ScreenObject.prototype.awake=function(){ScreenObject.uber.awake.call(this),this.originalLayoutSpecificValues=JSON.stringify(this.layoutSpecificValues),this.originalZIndex=this.zIndex,this.finishAnimation=this.finishAnimation.bind(this),this._refreshDisplayState=this._refreshDisplayState.bind(this)},ScreenObject.prototype.enterRenderTree=function(){this._isRendering||(this._isRendering=!0,this.emit(\"enteredRenderTree\"))},ScreenObject.prototype.exitRenderTree=function(){this._isRendering&&(this._isRendering=!1,this._isInMouseOver&&trigger(this.node,\"mouseout\",!1),this.emit(\"exitedRenderTree\"))},ScreenObject.prototype.react=function(t){var e=this.parentUnit.getRxStateObject(),i=function(){this._isRendering&&t.call(this,e)}.bind(this);null!==e&&(this._rxListeners.push(i),Ticker.frame(i,\"render\"))},ScreenObject.prototype.appearNestedObjects=function(){this.getNestedContainers().forEach(function(t){t.appear(),t.appearNestedObjects(t.objects)},this)},ScreenObject.prototype.disappearNestedObjects=function(){this.getNestedContainers().forEach(function(t){t.disappearNestedObjects(t.objects),t.disappear()},this)},ScreenObject.prototype.getScreenObjectPositionRelativeToScreen=function(){for(var t=this,e=this.getVisualPositionRelativeToParent();t.parentContainer!==t.parentScreen&&(t=t.parentContainer&&t.parentContainer.parentObject);){var i=t.getVisualPositionRelativeToParent();e.x+=i.x,e.y+=i.y}return e},ScreenObject.prototype.getVisualPositionRelativeToParent=function(){return{x:this.position.x,y:this.position.y}},ScreenObject.prototype.initTemplates=function(){var t=this.templates={},e=this.constructor.templates;if(e){for(var i in e)\"string\"==typeof e[i]&&(e[i]=tmpl(e[i]));extend(t,e)}},ScreenObject.prototype.tmpl=function(t,e,i){this.templates||this.initTemplates();var n=this.templates[t];return n?n.call(i||this,e):\"Missing template: \"+t},ScreenObject.prototype.template=function(t,e,i){var n,r=document.createElement(\"div\"),t=(r.innerHTML=this.tmpl(t,e),r.firstChild);if(i)for(;n=r.firstChild;)i.appendChild(n);return t},ScreenObject.prototype.ifNotClosing=function(t,e,i){var t=this.fireTriggersDryRun(t,null,noop,e)._actions,n=[\"dismiss\",\"collapse\"];t.some(function(t){return-1<n.indexOf(t.method)})||i()},ScreenObject.prototype.createNode=function(){this._isInMouseOver=!1;var r,o,t,e,s,a=this,i=ScreenObject.uber.createNode.call(a);return i.style.position=\"absolute\",this.hasTriggers(\"tap\")&&(addClass(i,\"touchable\"),attach(i,\"tap\",function(t){var e=new ActionContext(a,!0);a.ifNotClosing(\"tap\",e,e.trackUserInteraction),a.fireTriggers(\"tap\",null,null,e)},!1)),deviceInfo.deviceType.desktop()&&(o=[],this.hasTriggers(r=\"mouseOver\")&&(t=this._getTriggers(r,null),e=[],s=noop,t.forEach(function(t){t.parameters&&t.parameters.delay&&e.indexOf(t.parameters.delay)<0&&e.push(t.parameters.delay)}),attach(i,\"mouseover\",function(t){var n=new ActionContext(a,!0);a._isInMouseOver=!0,e.forEach(function(t,e){var i;i=t,s=function(){a.fireTriggers(r,function(t){return t.delay==i},null,n)},o[e]=setTimeout(s,t)})},!1)),attach(i,\"mouseout\",function(t){var e=new ActionContext(a,!0);a._isInMouseOver=!1,o.forEach(clearTimeout),o=[],a.hasTriggers(\"mouseOut\")&&a.fireTriggers(\"mouseOut\",null,null,e)},!1)),attach(i,\"touchstart\",function(t){var e;a.hasTriggers(\"touchstart\")&&(e=new ActionContext(a,!0),a.ifNotClosing(\"touchstart\",e,e.trackUserInteraction),a.fireTriggers(\"touchstart\",null,null,e))},!1),attach(i,\"touchend\",function(t){var e;a.hasTriggers(\"touchend\")&&(e=new ActionContext(a,!0),a.ifNotClosing(\"touchend\",e,e.trackUserInteraction),a.fireTriggers(\"touchend\",null,null,e))},!1),this.hasTriggers(\"swipe\")&&(attach(i,\"touchstart\",function(t){t.stopPropagation()}),new SwipeListener({target:i,swipeNodeGetter:function(){return this.parentUnit.node}.bind(this),swipe:function(e,i){var n=new ActionContext(a,!0);a.fireTriggers(\"swipe\",function(t){t=e==t.direction&&i>=t.length;return t&&a.ifNotClosing(\"swipe\",n,n.trackUserInteraction),t},null,n)}})),attach(i,\"transitionend\",function(t){t.stopPropagation()},!1),this.disableComponentRedrawOnEvent||this.parentContainer.on(\"resize\",this._refreshDisplayState),this.parentContainer.on(\"enteredRenderTree\",function(){this.hidden||this.enterRenderTree()}.bind(this)),this.parentContainer.on(\"exitedRenderTree\",function(){this.hidden||this.exitRenderTree()}.bind(this)),this.layoutPrevious=this.layoutCurrent,this.disableComponentRedrawOnEvent||this.parentUnit.on(\"layoutChanged\",this.handleLayoutChanged.bind(this)),this.initClickPrevention(function(){return this.node}.bind(this)),this._applyStyles(i,this._calculateNewStyleAttributes()),this.positionUsingTransforms()||deviceInfo.browser.safari(\"9.0.0\",null)||(i.style.outline=\"1px solid transparent\"),this._rxListeners=[],i},ScreenObject.prototype.positionUsingTransforms=retFalse,ScreenObject.prototype.sizeUsingTransforms=retFalse,ScreenObject.prototype.isValid=retTrue,ScreenObject.prototype.getNaturalSize=function(){return this.originalSize},ScreenObject.prototype._scaleNaturalSize=function(t,e){return t},ScreenObject.prototype._calculateNewStyleAttributes=function(){var t={transform:\"\",transformOrigin:\"50% 50%\"},e=this._scaleNaturalSize(this.getNaturalSize(),this.size),i=this.position.x,n=this.position.y;return this.sizeUsingTransforms()&&(i+=ScreenObject._roundIfNeeded((this.size.width-e.width)/2),n+=ScreenObject._roundIfNeeded((this.size.height-e.height)/2)),this.positionUsingTransforms()?(t.left=\"0\",t.top=\"0\",(i||n)&&(t.transform+=ScreenObject.getCSSTranslate(i,n))):(t.left=i+\"px\",t.top=n+\"px\"),!this.isAnimated&&0==this.rotation||(t.transform+=\" rotate(\"+this.rotation+\"deg) \"),this.sizeUsingTransforms()?(t.width=e.width+\"px\",t.height=e.height+\"px\",i=this.size.width/e.width,n=this.size.height/e.height,t.transform+=\" scale(\"+i+\", \"+n+\")\"):(t.width=this.size.width+\"px\",t.height=this.size.height+\"px\"),t.zIndex=this.zIndex,t.opacity=this.opacity,this.hidden?t.display=\"none\":t.display=\"block\",t},ScreenObject.prototype._applyStyles=function(e,i){i&&Object.keys(i).forEach(function(t){celtra.styler.css(e,t,i[t])})},ScreenObject.prototype._refreshDisplayState=function(){if(this.node){var t,e=this._calculateNewStyleAttributes(),i=!1;for(t in e){var n=String(e[t]).replace(/^\\s*|\\s*$/g,\"\"),r=celtra.styler.css(this.node,t);celtra.styler.css(this.node,t)!==n&&r!=n&&\"zIndex\"!=t&&\"display\"!=t&&(i=!0)}return this._applyStyles(this.node,e),this.redrawLayout(),i}},ScreenObject.prototype.finishAnimation=function(){this.node&&void 0!==celtra.styler.css(this.node,\"transitionDuration\")&&(celtra.styler.css(this.node,\"transitionDuration\",\"1ms\"),this.node.offsetWidth,celtra.styler.css(this.node,\"transitionDuration\",\"\"))},ScreenObject.prototype.handleLayoutChanged=function(){var t=function(){this.layoutPrevious.hidden&&!this.layoutCurrent.hidden?this.enterRenderTreeAndAppear(!0):!this.layoutPrevious.hidden&&this.layoutCurrent.hidden&&this.parentContainer._isRendering&&this.exitRenderTree(),this.layoutPrevious=this.layoutCurrent}.bind(this);ios()?defer(t):t(),this.finishAnimation(),this._refreshDisplayState()},ScreenObject.prototype.redrawLayout=function(){},ScreenObject.prototype.redrawSceneLayout=function(){this.redrawLayout()},ScreenObject.prototype._checkAndEnhanceParamsForFileLoading=function(t,e,i){t=ScreenObject.uber._checkAndEnhanceParamsForFileLoading.call(this,t,e,i);return t.settings.creativeUnitVariantId=this.parentScreen.parentContainer.localId,t},ScreenObject.prototype._preloadImage=function(t,e,i){e=this._checkAndEnhanceParamsForFileLoading(t,e,i);ScreenObject.uber._preloadImage.call(this,t,e.settings,e.callback)},ScreenObject.prototype._loadImage=function(t,e,i){e=this._checkAndEnhanceParamsForFileLoading(t,e,i);ScreenObject.uber._loadImage.call(this,t,e.settings,e.callback)},ScreenObject.prototype.setPosition=function(t,e){this.position={left:t+\"px\",top:e+\"px\"},this._refreshDisplayState()},ScreenObject.prototype.getPosition=function(){return{x:this.position.x,y:this.position.y}},ScreenObject.prototype.setSize=function(t,e){this.size.width=t,this.size.height=e,this._refreshDisplayState()},ScreenObject.prototype.getSize=function(){return{width:this.size.width,height:this.size.height}},ScreenObject.prototype.setOpacity=function(t){this.opacity=t,this._refreshDisplayState()},ScreenObject.prototype.getOpacity=function(){return this.opacity},ScreenObject.prototype.animateAction=function(t,e,i){i=i||noop,this.isAnimated=!0;var n=void 0!==e.duration?e.duration:1e3,r=e.timingFunction||\"linear\";function o(t){return!isNaN(parseFloat(t))&&isFinite(t)}function s(t){var e={position:{},size:{width:null,height:null},zIndex:null,opacity:null,rotation:null};return deep(e,t),e}function a(t,e){var i=e.size,n=t.size,r=(null==i.width&&(i.width=n.width),null==i.height&&(i.height=n.height),t.size=i,t.position),o=e.position,s=null,a=null,c=null,h=null;[\"left\",\"right\",\"hcenter\"].forEach(function(t){t in r&&(s=t),t in o&&(c=t)}),[\"bottom\",\"top\",\"vcenter\"].forEach(function(t){t in r&&(a=t),t in o&&(h=t)}),c&&null!==o[c]&&(delete r[s],r[c]=o[c]),h&&null!==o[h]&&(delete r[a],r[h]=o[h]),null!=e.rotation&&(t.rotation=e.rotation),null!=e.opacity&&(t.opacity=e.opacity),null!=e.zIndex&&(t.zIndex=e.zIndex)}e.layoutSpecificValues?e.layoutSpecificValues.forEach(function(t,e){this.layoutSpecificValues[e]&&a(this.layoutSpecificValues[e],s(t))}.bind(this)):(null!=(e=s(e.properties)).position.x&&(e.position.left=e.position.x+\"px\"),null!=e.position.y&&(e.position.top=e.position.y+\"px\"),null!=e.zIndex&&(e.zIndex=e.zIndex+ScreenObject._zIndexOffset,this.zIndex=e.zIndex),null!=e.size.width&&o(e.size.width)&&(e.size.width+=\"px\"),null!=e.size.height&&o(e.size.height)&&(e.size.height+=\"px\"),a(this.layoutCurrent,e));var c,h,u,l=this.node,e=(celtra.styler.css(l,\"transition\",\"\"),celtra.styler.css(l,\"transitionProperty\",\"all\"),celtra.styler.css(l,\"transitionDuration\",n+\"ms\"),celtra.styler.css(l,\"transitionTimingFunction\",r),this._refreshDisplayState());function p(){flash(),Date.now()<u&&setTimeout(p,ScreenObject.redrawTriggerInterval)}0<n&&e?(c=!1,h=function(){c||(c=!0,i())},ScreenObject.requiresRedrawToTriggerAnimationEvents&&(u=Date.now()+(Number(n)||0),p()),setTimeout(h,(Number(n)||0)+500,!0),once(l,\"transitionend\",function(t){celtra.styler.css(l,\"transition\",\"\"),setTimeout(h,0,!1)},!1)):(celtra.styler.css(l,\"transition\",\"\"),setTimeout(i,0<n?n:0))},ScreenObject.prototype.enterRenderTreeAndAppear=function(t){t&&this.parentScreen._isRendering&&this.enterRenderTree(),t&&!this.isAppearing&&this._shouldAppear&&this.appear()},ScreenObject.prototype.scaleAction=function(t,e,i){var n=e.useOriginalSize?this.originalSize:this.size,r=e.scale/100,n={width:n.width*r,height:n.height*r},r={x:this.position.x+this.size.width/2-n.width/2,y:this.position.y+this.size.height/2-n.height/2};this.animateAction(t,{properties:{position:r,size:n},duration:e.duration,timingFunction:e.timingFunction},i)},ScreenObject.prototype.showAction=function(t,e,i){var n=this.hidden;this.hidden=!1,this.node.style.display=\"block\",this.enterRenderTreeAndAppear(n),this.node.offsetWidth,nextFrame(i)},ScreenObject.prototype.hideAction=function(t,e,i){var n=this.hidden;this.hidden=!0,this.node.style.display=\"none\",!n&&this.parentScreen._isRendering&&(this.isAppearing&&(this.disappear(),this._shouldAppear=!0),this.exitRenderTree()),setTimeout(i,0)},ScreenObject.prototype.isHidden=function(){return this.hidden},ScreenObject.prototype.resetAction=function(t,e,i){this.layoutSpecificValues=JSON.parse(this.originalLayoutSpecificValues),this.reset(),this.zIndex=this.originalZIndex,this.finishAnimation(),this._refreshDisplayState(),defer(i)},ScreenObject.prototype.addStyle=function(t){var e,i=\"#celtra-object-\"+this.localId,n=this.node,r=/^:(link|visited|active|hover|focus|first-child|nth-child|nth-last-child|nth-of-type|first-of-type|last-of-type|empty|target|checked|enabled|disabled)/,o=/,\\s*/g,s=(this.styleNode||(this.styleNode=document.createElement(\"style\"),(n=n||document.getElementsByTagName(\"head\")[0]).appendChild(this.styleNode)),[]);for(e in t)s.push({selector:e.split(o).map(function(t){return i+(r.test(t)?\"\":\" \")+t}).join(\",\"),attributes:t[e]});n=this._styleSheetTemplate({rules:s});this.styleNode.textContent+=n},ScreenObject.prototype._styleSheetTemplate=tmpl(\"<% for (var i = 0; i < rules.length; i++) { %><%= rules[i].selector %> { <%= rules[i].attributes %> }\\n<% } %>\"),ScreenObject.prototype.css=function(e,i){void 0===i&&(i=e,e=this.getNode()),Object.keys(i).forEach(function(t){celtra.styler.css(e,t,i[t])})},ScreenObject.prototype.getNestedContainers=function(){return[]},ScreenObject.prototype.getActiveContainers=function(){return this.getNestedContainers()};;\nvar composeUrl=CeltraPlayerUtils.composeUrl,isTruthy=CeltraPlayerUtils.isTruthy,getUrl=CeltraPlayerUtils.getUrl,ifDefined=CeltraPlayerUtils.ifDefined,minVideoSize={width:50,height:50};function Video(){}inherit(Video,ScreenObject),Video.toString=function(){return\"[Clazz Video]\"},Video.prototype.toString=function(){return\"[Video \"+this.localId+\"]\"},Video.prototype.transcodingGroup=\"inline\",Video.TRANSCODING_IN_PROGRESS=\"We\u2019re processing this video. After a few moments, please refresh your browser.\",Video.TRANSCODING_ERROR=\"Transcoding failed. Try uploading a different video or contact support.\",Video.AUTOPLAY_REJECTED=\"Please enable Allow All Auto-Play in your Safari Preferences to preview this creative.\",Video.SIZE_PROPERTY_STEP_MULTIPLIER=100,Video.prototype.awake=function(){Video.uber.awake.call(this),this._player=null,this._spec=null,this.posterImageSource=null,this.posterDisabled=!1,this._externalEngineProvided=null,this._customUrlParameters={},this._inViewAreaRatio=0,this._delayVideoAutoplay=!0,this._fallbackToMPEG1=!1,this._viewabilityStartTriggerRatio=.05,this._viewabilityPauseTriggerRatio=.05,this.isTeaser=\"teaser\"===this.purpose,this.fullscreenMode=this.fullscreenButton?\"both\":\"disabled\",this.isConsideredInView=!1,this.isUserEngaged=!1,this._handleAreaInViewRatioChanged=this._handleAreaInViewRatioChanged.bind(this),this._handleContainerViewableChange=this._handleContainerViewableChange.bind(this),this._handleSkinViewportChangeTimeout=null,this._handleAreaCoveredOrAreaInViewRatioChanged=this._handleAreaCoveredOrAreaInViewRatioChanged.bind(this),this._handleAreaCoveredOrAreaInViewRatioChangedThrottled=this._handleAreaCoveredOrAreaInViewRatioChangedThrottled.bind(this),this._handleStopRequest=this._handleStopRequest.bind(this),this._originalVideo=this.video},Video.prototype.redrawLayout=function(){this._player&&this._player.setDimensions(this._round(this.size.width),this._round(this.size.height))},Video.prototype.createNode=function(){\"string\"==typeof this.posterImageFeedFieldKey&&FeedData.checkAndTrackValue(this.posterImageFeedFieldKey,this.posterImageFeedRowIndex,this.constructor.name,\"posterImageFeedFieldKey\"),\"string\"==typeof this.videoSourceFeedFieldKey&&(FeedData.checkAndTrackValue(this.videoSourceFeedFieldKey,this.videoSourceFeedRowIndex,this.constructor.name,\"videoSourceFeedFieldKey\"),this.video=null,FeedData.isCorrectFieldType(this.videoSourceFeedFieldKey,this.constructor.name,\"videoSourceFeedFieldKey\")&&(i=FeedData.getFieldRowByKey(this.videoSourceFeedFieldKey,this.videoSourceFeedRowIndex))&&\"string\"==typeof i.value&&(this.video=new File,this.video.blobHash=i.value,this.video.meta=i.metadata&&i.metadata.video?i.metadata.video:{hasAudio:!0}));var e,t,i=Video.uber.createNode.call(this);return this._isSnapchatFormat=!!creative.constructor.name.match(/^Snapchat.*/),this._isDesktop=deviceInfo.deviceType.desktop()&&\"Desktop\"===creative.intendedDeviceType,this.posterDisabled=this.posterDisabled||isTruthy(creative.runtimeParams.disablePoster),this._isSnapchatFormat&&(this.transcodingGroup=\"snapchat\"),this._initializeCustomStartStopRatios(),this.on(\"enteredRenderTree\",function(){this._createPlayer()}.bind(this)),this.on(\"exitedRenderTree\",this._exitedRenderTree.bind(this)),this.video&&!this.isTeaser&&this._hasPosterImage()&&!this.posterDisabled&&(e={crossOrigin:\"anonymous\"},\"responsive\"!=this.parentUnit.sizing||this._isSnapchatFormat?(t=this._getPosterUrl())&&this._preloadImage(new File(t),e,function(e){this.posterImageSource=e}.bind(this)):this.parentScreen.on(\"appeared\",function(){this._loadImage(new File(this._getPosterUrl(),e))}.bind(this))),addClass(i,\"touchable\"),attach(i,\"tap\",this.handleTap),creative.adapter.on(\"stateChange\",function(e){this._player&&(\"dismissed\"==e&&this.emit(\"ended\"),\"default\"==e&&this.isTeaser&&this.playAction(new ActionContext(this,!1)))}.bind(this)),creative.adapter.on(\"orientationchange\",function(e){this._player&&this._player.redraw(),setTimeout(function(){this._player&&this._player.redraw()}.bind(this),800)}.bind(this)),creative.adapter.on(\"resize\",function(){this._player&&this._player.redraw()}.bind(this)),i},Object.defineProperty(Video.prototype,\"isTrackable\",{get:function(){return!0}}),Video.prototype._leaveView=function(){var e=this._player&&this._player.isState(CeltraPlayerUtils.PLAYING),t=this._player&&this._player.isState(CeltraPlayerUtils.BUFFERING);this.playWhenInView=e||t||this.playWhenInView,(e||t)&&this._player.pause()},Video.prototype._comeInView=function(){this._player&&this.playWhenInView&&(this._player.play(),this.playWhenInView=!1)},Video.prototype._initializeCustomStartStopRatios=function(){var e=parseFloat(creative.runtimeParams.viewabilityStartTriggerRatio),t=parseFloat(creative.runtimeParams.viewabilityPauseTriggerRatio),i=parseFloat(this.viewabilityStartTriggerRatio)/100,a=parseFloat(this.viewabilityPauseTriggerRatio)/100,n=null,s=null;0<=e&&e<=1&&0<=t&&t<=1?(n=e,s=t):0<=i&&i<=1&&0<=a&&a<=1&&(n=i,s=a),n&&n<=1&&0<=n&&(this._viewabilityStartTriggerRatio=n=.95<=n?.95:n),s&&s<=1&&0<=s&&(this._viewabilityPauseTriggerRatio=s=.95<=s?.95:s)},Video.prototype._getVideoControlsController=function(e){var a,n;return this.isTeaser||e.campaignExplorer&&!e.massProductionPreview?null:(a=isTruthy(creative.runtimeParams.enableVerticalVideoUIOffset),n=function(e){return new VideoControls(e)},function(e,t){var i=\"never\"===this.controlsMode,e=new VideoControlsController(e,n,extend({autohideTime:\"hide\"===this.controlsMode?3:0,showPlayIconAsFallback:!0,playIconEnabled:!this.autoplay,hasTapTriggers:this.hasTriggers(\"tap\"),tapCallback:function(){var e=new ActionContext(this,!0);this.ifNotClosing(\"tap\",e,e.trackUserInteraction),this.fireTriggers(\"tap\",null,null,e)}.bind(this),loaderIconEnabled:void 0===this.loader||this.loader,countdownIconEnabled:this.countdown&&!i,resumeIconEnabled:void 0===this.resumeButton||this.resumeButton,replayIconEnabled:!0,fullscreenIconEnabled:\"both\"===this.fullscreenMode&&!i,soundIconEnabled:this.video.meta.hasAudio&&!i,customIcons:{custom_play:this.playIcon||null,custom_replay:this.replayIcon||null},vignetteEnabled:!1,celtraSignatureEnabled:!1,introAnimationEnabled:!1,radarIconEnabled:!1,enableVerticalVideoUIOffset:a},t));return e.on(\"playButtonPressed\",function(){this.playAction(new ActionContext(this,{consideredUserInitiatedByBrowser:!0}))}.bind(this)),e}.bind(this))},Video.prototype._showFullscreenButton=function(){return deviceInfo.deviceType.desktop()||ifDefined(this.parentUnit.fillsContainer,!1)},Video.prototype._getPosterUrl=function(){var e,t,i;return\"string\"==typeof this.posterImageFeedFieldKey?\"string\"==typeof(i=FeedData.getFieldValueByKey(this.posterImageFeedFieldKey,this.posterImageFeedRowIndex))&&FeedData.isCorrectFieldType(this.posterImageFeedFieldKey,this.constructor.name,\"posterImageFeedFieldKey\")?i:null:this.posterImage?this.posterImage.getUrl():this.posterImageSource&&!this.video.useRaw?this.posterImageSource.src:this.video&&this.video.blobHash?(i={width:this.size.width,height:this.size.height},e=1<this.size.width/this.size.height,t=Math.ceil(i[e?\"height\":\"width\"]/Video.SIZE_PROPERTY_STEP_MULTIPLIER)*Video.SIZE_PROPERTY_STEP_MULTIPLIER,null!==(i={width:e?null:t,height:e?t:null}).width&&(i.width=(i.width>=minVideoSize.width?i:minVideoSize).width),null!==i.height&&(i.height=(i.height>=minVideoSize.height?i:minVideoSize).height),composeUrl(getUrl(\"cachedApi\"),\"videoThumb/\",this.video.blobHash,{position:ifDefined(this.posterImageGenerateAt,null),transform:\"crush\",resize:i.width+\"x\"+i.height,quality:\"90\",colorAccurate:\"true\"})):null},Video.prototype._createWarningMessage=function(){var e,t=function(e){var i=e.getContext(\"2d\"),t=[\"#ffffff\",\"#ffff00\",\"#00ffff\",\"#00ff00\",\"#ff00ff\",\"#ff0000\",\"#0000ff\"],a=t.slice().reverse(),n=(e.height=this.size.height,e.width=this.size.width,Math.ceil(.5*this.size.height)),s=Math.ceil(.5*this.size.height),r=Math.ceil(this.size.width/t.length);t.forEach(function(e,t){i.fillStyle=e,i.fillRect(r*t,0,r,n)}),a.forEach(function(e,t){i.fillStyle=e,i.fillRect(r*t,n,r,s)}),i.fillStyle=\"rgba(0,0,0,0.6)\",i.fillRect(0,0,this.size.width,this.size.height)}.bind(this);this._overlay||(this._overlay=document.createElement(\"div\"),this._overlay.style.display=\"none\",this._overlay.className=\"warning-wrapper\",(e=document.createElement(\"canvas\")).className=\"warning-canvas\",t(e),this._overlay.appendChild(e),(t=document.createElement(\"div\")).className=\"warning-message-container\",this._warningTriangle=document.createElement(\"div\"),this._warningTriangle.className=\"warning-triangle\",this._warningHourglass=document.createElement(\"div\"),this._warningHourglass.className=\"warning-hourglass\",this._warningMessage=document.createElement(\"div\"),this._warningMessage.className=\"warning-message\",t.appendChild(this._warningTriangle),t.appendChild(this._warningHourglass),t.appendChild(this._warningMessage),this._overlay.appendChild(t),this.node.appendChild(this._overlay))},Video.prototype.showWarningMessage=function(e){this._transcodingMsgShown||(this._transcodingMsgShown=!0,this._createWarningMessage(),removeClass(this._warningHourglass,\"show\"),removeClass(this._warningTriangle,\"show\"),\"TRANSCODING_IN_PROGRESS\"===e?(this._overlay.style.display=\"block\",this._warningMessage.innerText=Video.TRANSCODING_IN_PROGRESS,addClass(this._warningHourglass,\"show\")):\"TRANSCODING_ERROR\"===e?(this._overlay.style.display=\"block\",this._warningMessage.innerText=Video.TRANSCODING_ERROR,addClass(this._warningTriangle,\"show\")):\"AUTOPLAY_REJECTED\"===e&&(this._overlay.style.display=\"block\",this._warningMessage.innerText=Video.AUTOPLAY_REJECTED,addClass(this._warningTriangle,\"show\")))},Video.prototype._getNewStreamPresets=function(){return[\"x265_served_480p\",\"x265_served_1080p\",\"vp9_served_480p\",\"vp9_served_720p\"]},Video.prototype._isVideoTranscoding=function(){var e;this._isRendering&&!this.video.useRaw&&(e=composeUrl(creative.secure?creative.apiUrl:creative.insecureApiUrl,\"videoStream/status/\",this.video.blobHash),loadJSONP(e,function(e){var t=!1,i=!1,a=function(){this._player.pause(),this._player.hide()}.bind(this),n=e[this.transcodingGroup];isDefAndNotNull(n)&&(i=n.hasTranscodingError,t=this.canUseNewVideoStreams?this._getNewStreamPresets().every(function(e){return isDefAndNotNull(n[e+\"BlobHash\"])}):n.isFullyTranscoded),i?(a(),this.showWarningMessage(\"TRANSCODING_ERROR\")):t||(a(),this.showWarningMessage(\"TRANSCODING_IN_PROGRESS\"))}.bind(this)))},Video.prototype._appear=function(){creative.adapter.canMeasureViewportPlacementGeometry?(this._inViewAreaRatio=this.parentUnit.inView.areaInViewRatio,this.isConsideredInView=!0,this._skinPageContentElement=creative.adapter.skinGetters&&creative.adapter.skinGetters.getPageContentElement(),this._skinPageContentElement?(this._inViewAreaRatio=Math.min(this._inViewAreaRatio,this._getAreaNotCoveredByContentRatio()),this._clearSkinViewportChangeTimeout(),creative.adapter.on(\"viewportChange\",this._handleAreaCoveredOrAreaInViewRatioChangedThrottled),this.parentUnit.inView.on(\"areaInViewRatioChanged\",this._handleAreaCoveredOrAreaInViewRatioChanged)):this.parentUnit.inView.on(\"areaInViewRatioChanged\",this._handleAreaInViewRatioChanged),this._inViewAreaRatio>=this._viewabilityStartTriggerRatio&&(this._delayVideoAutoplay=!1)):creative.adapter.canMeasureContainerAreaInViewRatio?(this._inViewAreaRatio=creative.adapter.getContainerInViewAreaRatio(),this.isConsideredInView=!0,this._inViewAreaRatio>=this._viewabilityStartTriggerRatio&&(this._delayVideoAutoplay=!1),creative.adapter.on(\"containerAreaInViewRatioChanged\",this._handleAreaInViewRatioChanged)):creative.adapter.containerViewabilityObserver&&(this.isConsideredInView=creative.adapter.containerViewabilityObserver.isViewable,this.isConsideredInView&&(this._delayVideoAutoplay=!1),creative.adapter.on(\"containerViewableChange\",this._handleContainerViewableChange)),this.video&&(this.autoplay||this.isTeaser)&&!this._delayVideoAutoplay&&(this.playAction(new ActionContext(this,!1)),this.isTeaser&&this.on(\"enteredRenderTree\",function(){defer(this.playAction(new ActionContext(this,!1)))}.bind(this)))},Video.prototype._getAreaNotCoveredByContentRatio=function(){var e=0,t=CRect.adopt(this.getNode().getBoundingClientRect()),i=CRect.adopt(this._skinPageContentElement.getBoundingClientRect()),i=t.intersect(i),t=t.area;return e=0<t?(t-i.area)/t:e},Video.prototype._handleAreaCoveredOrAreaInViewRatioChanged=function(e){var t=this._getAreaNotCoveredByContentRatio();this._handleAreaInViewRatioChanged(Math.min(e,t))},Video.prototype._handleAreaCoveredOrAreaInViewRatioChangedThrottled=function(){this._handleSkinViewportChangeTimeout||(this._handleSkinViewportChangeTimeout=setTimeout(function(){this._handleSkinViewportChangeTimeout=null,this._handleAreaCoveredOrAreaInViewRatioChanged(this.parentUnit.inView.areaInViewRatio)}.bind(this),500))},Video.prototype._clearSkinViewportChangeTimeout=function(){this._handleSkinViewportChangeTimeout&&(clearTimeout(this._handleSkinViewportChangeTimeout),this._handleSkinViewportChangeTimeout=null)},Video.prototype._handleAreaInViewRatioChanged=function(e){this._player&&this._player.fullscreen&&this._isDesktop?(e=1,this.isConsideredInView=!0):this._inViewAreaRatio>=e&&e<=this._viewabilityPauseTriggerRatio?(this.isConsideredInView=!1,this._leaveView()):this._inViewAreaRatio<e&&e>=this._viewabilityStartTriggerRatio&&(this.isConsideredInView=!0,this._playActionCalled?this._comeInView():this.video&&this.autoplay&&this.playAction(new ActionContext(this,!1)),this.playWhenInView=!1),this._inViewAreaRatio=e},Video.prototype._handleContainerViewableChange=function(e){this.isConsideredInView!==e&&((this.isConsideredInView=e)?this._comeInView():this._leaveView())},Video.prototype._videoEngineOptionsProvider=function(){return{hasAudio:this.video.meta.hasAudio,isTeaser:this.isTeaser,fallbackToMPEG1:this._fallbackToMPEG1,forceMPEG1Video:isTruthy(creative.runtimeParams.forceMPEG1Video),forceMPEG4Video:isTruthy(creative.runtimeParams.forceMPEG4Video),campaignExplorer:isTruthy(creative.runtimeParams.campaignExplorer),massProductionPreview:isTruthy(creative.runtimeParams.massProductionPreview),thumbnailMode:\"thumb\"===creative.runtimeParams.purpose,isSnapchatFormat:this._isSnapchatFormat,hqQuality:!!this.hqQuality,transcodingGroup:this.transcodingGroup,skipCodecs:this._getSkippedCodecs()}},Video.prototype._getSkippedCodecs=function(){var e=this.canUseNewVideoStreams?[]:[\"vp9\",\"h265\"];return e=creative.runtimeParams.skipCodecs?e.concat(String(decodeURIComponent(creative.runtimeParams.skipCodecs)).split(\",\").map(function(e){return e.toLowerCase()})):e},Video.prototype._getVideoOptions=function(){return{preload:this.preload||this.isTeaser||this.video.useRaw,pauseOnEnd:this.pauseOnEnd,playsInline:!0,componentName:this.name,canShowSpinner:!this.isTeaser,fullscreenMode:this._showFullscreenButton()?this.fullscreenMode:\"disabled\",repeatTimes:this.indefinitely||this.loop?2147483647:this.repeatTimes,closeFSOnEnd:this.closeOnEnd,fitting:this.fitting,barColor:\"fit\"===this.fitting&&this.barColor?this.barColor:\"#000000\",videoWidth:this.video.meta.width,videoHeight:this.video.meta.height,anchoringPoint:this.parentUnit.anchoringPoint,duration:parseFloat(this.video.meta.duration),hasAudio:this.video.meta.hasAudio,startMuted:this.muted,videoStream:{blobHash:this.video.blobHash,useRaw:this.video.useRaw,from:this.isTeaser?0:null,to:this.isTeaser?5:null,customUrlParameters:this._customUrlParameters},isDesktop:this._isDesktop}},Video.prototype.showEmptyVideoPlaceholder=function(){this.barColor&&(this.getNode().style.background=this.barColor),CeltraPlayerUtils.insertStyleTag(\"celtra-video-player-style\",VideoPlayerCss,this.getNode().ownerDocument),addClass(this.getNode(),\"video-player-wrapper-empty\")},Video.prototype._createPlayer=function(){if(!this._player)if(this.video){creative.adapter.checkVideoTranscodeStatus&&this._isVideoTranscoding();var i=this._videoEngineOptionsProvider(),a=(this._externalEngineProvided=!!i.videoElement,merge(this._getVideoOptions(),{videoElement:i.videoElement})),e=(this.posterDisabled?a.poster=null:this._hasPosterImage()&&(a.poster=this._getPosterUrl()),function(e,t){return VideoEngineProvider.createVideoEngineSpec(e,extend(i,{startMuted:t,preload:a.preload}))}.bind(this)),t=function(){return this.isUserEngaged&&creative.adapter.viewabilityMeasurable.viewable00&&this.isConsideredInView}.bind(this);if(this._player=new VideoPlayer(this.getNode(),e,this._getVideoControlsController(i),t,a),!this.isTeaser&&\"MasterVideo\"!==this.constructor.name)for(var n in this._player=QuartileEventsEmitter(this._player),this._player.QUARTILE_EVENTS)this._player.on(n,function(e){this.emit(e.name,e)}.bind(this));this._player.setDimensions(this._round(this.size.width),this._round(this.size.height)),this._attachListeners(),this._orientationLockHandler=CeltraPlayerUtils.orientationLockHandler.bind(null,this._player),this.parentUnit.addListener(\"orientationLockChanged\",this._orientationLockHandler)}else\"string\"!=typeof this.videoSourceFeedFieldKey&&this.showEmptyVideoPlaceholder()},Video.prototype._attachListeners=function(){var e;this._player&&(e=function(){this._cuePointObserver||(this._cuePointObserver=Object.create(CuePointObserver).init(this)),defer(this._cuePointObserver.start)}.bind(this),this._player.on(\"muted\",function(){this._videoContext&&this._videoContext.track({name:\"videoMuted\"}),this.fireTriggers(\"videoMute\"),this.emit(\"muted\")}.bind(this)),this._player.on(\"unmuted\",function(){this._videoContext&&this._videoContext.track({name:\"videoUnmuted\"}),this.fireTriggers(\"videoUnMute\"),this.emit(\"unmuted\")}.bind(this)),this._player.on(\"volumechange\",function(e){this.emit(\"volumechange\",e)}.bind(this)),this._player.on(\"play\",function(){e(),this.emit(\"play\")}.bind(this)),this._player.on(\"playing\",function(){e(),this.fireTriggers(\"videoPlaying\"),this.emit(\"playing\")}.bind(this)),this._player.on(\"enterfullscreen\",function(){this.fireTriggers(\"videoEnterFullScreen\"),this.emit(\"enterfullscreen\")}.bind(this)),this._player.on(\"custominfo\",function(e){new ActionContext(this,!1).track(e)}.bind(this)),this._player.on(\"exitfullscreen\",function(){this.fireTriggers(\"videoExitFullScreen\"),this.emit(\"exitfullscreen\")}.bind(this)),this._player.on(\"autoplayrejected\",function(){this.emit(\"autoplayrejected\")}.bind(this)),this._player.on(\"autoplaynotpossible\",function(){this.fallbackToImageSequence&&(this._fallbackToMPEG1=!0,this.resetAction(),this.playAction(new ActionContext(this,!1)))}.bind(this)),this._player.on(\"ended\",function(){this._cuePointObserver&&this._cuePointObserver.stop(!0),this.fireTriggers(\"videoComplete\"),this.emit(\"ended\")}.bind(this)),this._player.on(\"pause\",function(){this._cuePointObserver&&this._cuePointObserver.stop(),this._videoContext&&this._videoContext.track({name:\"videoPause\"}),this._player&&!this._player.silentPauseActive&&this.fireTriggers(\"videoPause\"),this._player&&this.emit(\"pause\",this._player.getCurrentTime())}.bind(this)),this._player.on(\"repeat\",function(){this._cuePointObserver&&this._cuePointObserver.stop(!0),this.fireTriggers(\"videoComplete\"),this.emit(\"repeat\")}.bind(this)),this._player.on(\"timeupdate\",function(){var e;this._player&&(e=this._player.getCurrentTime(),(this._previousTime||0)!==e&&this.emit(\"timeupdate\",e))}.bind(this)),this._player.on(\"durationchange\",function(){this._player&&this.emit(\"durationchange\",this._player.getDuration())}.bind(this)),this._player.on(\"userInteraction\",function(e){void 0!==e&&void 0!==e.isUserEngaged&&(this.isUserEngaged=e.isUserEngaged)}.bind(this)),this._player.on(\"loadstart\",function(){creative.adapter.manuallyAddResourceToResourceObserver&&this._player.activeSrc&&\"MasterVideo\"!==this.constructor.name&&creative.adapter.manuallyAddResourceToResourceObserver(this._player.activeSrc)}.bind(this)))},Video.prototype._getRealState=function(e,t,i){var a,t=e.videoEngineSpec.preload&&!deviceInfo.deviceType.desktop()?(a=!(!t&&i),i):(a=e.videoEngineSpec.startMuted,e.videoEngineSpec.inUserInitiatedThread);return{startMuted:a,inUserInitiatedThread:t}},Video.prototype._trackViewShown=function(e,t){e=this.appliedState?this.appliedState.inUserInitiatedThread:void 0,t=this.appliedState?this.appliedState.startMuted:void 0;if(!this.isTeaser){for(var i=this.parentScreen,a=this;a.parentContainer.parentObject;){if(\"undefined\"!=typeof DynamicContent&&a.parentContainer.parentObject instanceof DynamicContent){i=a.parentContainer;break}a=a.parentContainer.parentObject}var n=i.getActionsExecutedOnAppear().contains(\"play\",this.localId);new ActionContext(this,!1).track({name:\"viewShown\",viewName:this.name,clazz:this.constructor.name,filename:this.video&&this.video.name||null,label:this.name,source:this.video&&this.video.blobHash||null,sourceType:\"File\",userInitiated:void 0!==e?e:!n&&!this.autoplay,startMuted:void 0!==t?t:this.muted,videoPlayerMode:\"inline\",viewabilityStartTriggerRatio:this._viewabilityStartTriggerRatio,viewabilityPauseTriggerRatio:this._viewabilityPauseTriggerRatio,duration:this.getDuration()})}this._trackViewShown=noop},Video.prototype._emitVideoPlayAttempted=function(e,t,i,a){this._videoContext&&(this._videoContext.track(this._getVideoPlayAttemptedEvent(e,t,i,a)),this._player.activePreset?this._videoContext.track({name:\"videoPresetSelected\",videoPreset:this._player.activePreset}):this._player.once(\"loadstart\",function(){this._videoContext.track({name:\"videoPresetSelected\",videoPreset:this._player.activePreset})}.bind(this))),this._emitVideoPlayAttempted=noop},Video.prototype._getVideoPlayAttemptedEvent=function(e,t,i,a){return{name:\"videoPlayAttempted\",filename:this.video.name,trackable:!0,userInitiated:e,autoReplay:!!this.loop,startMuted:t,videoPlayerMode:\"inline\",engineType:i}},Video.prototype._exitedRenderTree=function(e){this.parentUnit.inView&&(this._skinPageContentElement?(this._clearSkinViewportChangeTimeout(),this.parentUnit.inView.off(\"areaInViewRatioChanged\",this._handleAreaCoveredOrAreaInViewRatioChanged),creative.adapter.off(\"viewportChange\",this._handleAreaCoveredOrAreaInViewRatioChangedThrottled)):this.parentUnit.inView.off(\"areaInViewRatioChanged\",this._handleAreaInViewRatioChanged)),creative.adapter.canMeasureContainerAreaInViewRatio&&creative.adapter.off(\"containerAreaInViewRatioChanged\",this._handleAreaInViewRatioChanged),creative.adapter.containerViewabilityObserver&&creative.adapter.off(\"containerViewableChange\",this._handleContainerViewableChange),this._destroyPlayer()},Video.prototype._destroyPlayer=function(){this._cuePointObserver&&(this._cuePointObserver.detach(),this._cuePointObserver=null),this._playActionCalled=!1,this._delayVideoAutoplay=!0,this._spec=null,this._player&&(this._player.destroy(),this._player=null,this.parentUnit.off(\"mediaStopRequested\",this._handleStopRequest),this.parentUnit.removeListener(\"orientationLockChanged\",this._orientationLockHandler),this._orientationLockHandler=null,this._videoContext=null,this._trackingEventsEmitter=null,this._trackingEventsRecorder=null),this._videoEventsAdapter&&(this._videoEventsAdapter&&this._videoEventsAdapter.destroy(),this._videoEventsAdapter=null)},Video.prototype._createVideoContext=function(e){this.isTeaser||!this._player||this._videoContext||(this._videoContext=new VideoContext(this,this.video,this.name),this._videoEventsAdapter=new VideoEventsAdapter(this._player),this._videoContext.registerSource(this._videoEventsAdapter),this._player.on(\"userInteraction\",this._videoContext.trackUserInteraction.bind(this._videoContext)))},Video.prototype._round=function(e){return Math.round(e)},Video.prototype._hasPosterImage=function(){return\"string\"==typeof this.posterImageFeedFieldKey||this.posterImageGenerate||isDefAndNotNull(this.posterImage)},Video.prototype.handleTap=function(e){e.stopPropagation()},Video.prototype.positionUsingTransforms=retFalse,Video.prototype.sizeUsingTransforms=retFalse,Video.prototype.getNaturalSize=function(){return this.originalSize},Video.prototype.getDuration=function(){var e=NaN;try{e=parseFloat(this.video.meta.duration)}catch(e){}return isNaN(e)?this._player?this._player.duration:0:e},Video.prototype.getCurrentTime=function(){return this._player?this._player.getCurrentTime():0},Video.prototype.setCurrentTime=function(e){return this._player?this._player.setCurrentTime(e):0},Video.prototype.playAction=function(e,t,i){var a;this._player&&(e||(console.warn(\"Context object has to be passed when calling playAction.\"),e=new ActionContext(this,{consideredUserInitiatedByBrowser:!1})),this._createPlayer(),this._createVideoContext(e.inUserInitiatedIteration),this.isUserEngaged=e.inUserInitiatedIteration,creative.adapter._stopAllMedia(\"toPlayVideo\"),a=t&&void 0!==t.startMuted?t.startMuted:this.muted,t&&\"touchstart\"===t.triggerId&&creative.adapter.muteSoundIfVideoStartedOnTouchStartEvent&&(a=!0),this._spec||(this._spec=this._player.createSpecs(e.inUserInitiatedIteration,a)),this._player.engineInitialized||this._player.initialize(this._spec),this.appliedState=this._getRealState(this._spec,a,e.inUserInitiatedIteration),this._player.play(this.appliedState.startMuted),this.appliedState.inUserInitiatedThread&&this._videoContext.trackUserInteraction(),this._emitVideoPlayAttempted(e.consideredUserInitiatedByBrowser,this.appliedState.startMuted,this._spec.videoEngineSpec.engineType,this._spec.videoEngineSpec.preset),this._playActionCalled=!0,this.parentUnit.off(\"mediaStopRequested\",this._handleStopRequest),this.parentUnit.on(\"mediaStopRequested\",this._handleStopRequest)),(i||noop)()},Video.prototype._handleStopRequest=function(e){\"toPlayVideo\"===e?(this._player.pause(),this.playWhenInView=!1):this._leaveView()},Video.prototype.pauseAction=function(e,t,i){this._player&&this._player.isState(CeltraPlayerUtils.PLAYING)&&this._player.pause(),(i||noop)()},Video.prototype.rewindAction=function(e,t,i){this._player&&this._player.replay(),(i||noop)()},Video.prototype.resetAction=function(e,t,i){this._destroyPlayer(),Video.uber.resetAction.call(this,e,t,function(){this._isRendering&&this._createPlayer(),this._emitVideoPlayAttempted=Video.prototype._emitVideoPlayAttempted.bind(this),i&&i()}.bind(this))},Video.prototype.muteAction=function(e,t,i){this._player&&this._player.mute(),this.muted=!0,(i||noop)()},Video.prototype.unMuteAction=function(e,t,i){this._player&&this._player.unMute(),this.muted=!1,(i||noop)()},Video.prototype.enterFullScreenAction=function(e,t,i){this._player&&this._player.enterFullScreen(),(i||noop)()},Video.prototype.exitFullScreenAction=function(e,t,i){this._player&&this._player.exitFullScreen(),(i||noop)()},Video.prototype.setCustomUrlParameter=function(e,t){this._customUrlParameters[e]=t};;\nfunction MasterVideo(){}inherit(MasterVideo,Video),MasterVideo.PRELOAD_TIMEOUT_MILLISECONDS=3e3,MasterVideo.prototype.transcodingGroup=\"vpaid\",MasterVideo.prototype._videoCapabilities=null,Object.defineProperty(MasterVideo.prototype,\"videoCapabilities\",{get:function(){return this._videoCapabilities||(this._videoCapabilities=creative.adapter.getVideoCapabilities()),this._videoCapabilities}}),MasterVideo.prototype.createNode=function(){var e,t,i,r=MasterVideo.uber.createNode.call(this),o=this.videoCapabilities.videoElement;return o&&(e=VideoEngineProvider.createVideoEngineSpec(!1,extend(this._videoEngineOptionsProvider(),{startMuted:this.muted,preload:!0})),e=VideoEngineProvider.createVideoSourceObjects(e,{blobHash:this.video.blobHash,customUrlParameters:this._customUrlParameters}),this.incrementLoadingCount(),t=function(){i&&(clearTimeout(i),i=null,o.removeEventListener(\"canplay\",t),o.pause(),this.decrementLoadingCount())}.bind(this),i=setTimeout(t,MasterVideo.PRELOAD_TIMEOUT_MILLISECONDS),o.addEventListener(\"canplay\",t),CeltraPlayerUtils.setMediaElementSource(document,o,e),o.load()),r},MasterVideo.prototype._videoEngineOptionsProvider=function(){return merge(this.videoCapabilities,{hasAudio:this.video.meta.hasAudio,thumbnailMode:\"thumb\"===creative.runtimeParams.purpose,capStreamAtHQPlus:isTruthy(creative.runtimeParams.capStreamAtHQPlus),campaignExplorer:isTruthy(creative.runtimeParams.campaignExplorer),forceMPEG4Video:!0,isMasterVideo:!0,transcodingGroup:this.transcodingGroup,skipCodecs:this._getSkippedCodecs()})},MasterVideo.prototype._createVideoContext=function(){this._player&&!this._videoContext&&(this._videoContext=new InstaAdContext(this,{certainlyNotCausedByUserBehavior:!1,consideredUserInitiatedByBrowser:!1}),this._trackingEventsEmitter=new PlaybackTrackingEventsEmitter(this._player),this._trackingEventsRecorder=new PlaybackTrackingEventsRecorder(this._trackingEventsEmitter,this._videoContext),[\"start\",\"firstQuartile\",\"midpoint\",\"thirdQuartile\",\"complete\"].forEach(function(t){this._trackingEventsEmitter.on(t,function(e){this.emit(\"video\"+ucfirst(t),e)}.bind(this))},this),this._player.on(\"userInteraction\",this._videoContext.trackUserInteraction.bind(this._videoContext)))},MasterVideo.prototype._getVideoPlayAttemptedEvent=function(e,t,i,r){return merge(MasterVideo.uber._getVideoPlayAttemptedEvent.apply(this,arguments),{name:\"videoPlayInitiated\",duration:this._player?this._player.getDuration():null})},MasterVideo.prototype._getVideoControlsController=function(e){return this.isTeaser||e.campaignExplorer?null:function(e,t){var i=ios()&&/Safari\\/\\d+/.test(navigator.userAgent),i=this._externalEngineProvided&&i&&!t.startMuted,i=extend({isAutoplay:this.autoplay,autohideTime:\"hide\"===this.controlsMode?3:0,progressbar:this.progressbar,progressbarColour:this.progressbarColour,startInFullScreen:!1,controlsHidden:\"never\"===this.controlsMode,muteUnmuteEnabled:this.video.meta.hasAudio&&!i,isMasterVideo:!0},t),t=\"preview\"===creative.adapter.runtimeParams.purpose?new VPAIDPreviewControlsController(e,function(e){return new VPAIDPreviewControls(e)},i):null;return t&&t.on(\"playButtonPressed\",function(){this.playAction(new ActionContext(this,{consideredUserInitiatedByBrowser:!0}))}.bind(this)),t}.bind(this)},MasterVideo.prototype._getNewStreamPresets=function(){return[\"x265_served_1080p\",\"vp9_served_720p\",\"vp9_served_1080p\"]},MasterVideo.prototype._trackViewShown=noop;;\nfunction Picture(){}inherit(Picture,ScreenObject),extend(Picture.prototype,FeedImageCreator),extend(Picture.prototype,StaticContentMixin),Picture.toString=function(){return\"[Clazz Picture]\"},Picture.prototype.toString=function(){return\"[Picture \"+this.localId+\"]\"},Picture.prototype.createNode=function(){\"string\"==typeof this.imageSourceFeedFieldKey&&(FeedData.checkAndTrackValue(this.imageSourceFeedFieldKey,this.imageSourceFeedRowIndex,this.constructor.name,\"imageSourceFeedFieldKey\"),this.file=this._createFeedImageFile(\"imageSource\",this.imageSourceFeedRowIndex));var t=Picture.uber.createNode.call(this);return this.file===this.placeholderFile?\"string\"==typeof this.imageSourceFeedFieldKey||this._newFileWasSet||(addClass(t,\"empty\"),this._preloadImage(new File(\"runner/image-placeholder.png\"),function(e,i){this._isSettingFileInProgress||(t.style.backgroundImage=\"url(\"+i+\")\")}.bind(this))):this.file&&this._preloadImage(this.file,{},function(e,i){this._newFileWasSet||this._handlePreloadedImage(t,e)}.bind(this)),t},Picture.prototype.awake=function(){Picture.uber.awake.call(this),this.placeholderFile=new File(\"runner/image-placeholder.png\"),this.placeholderFile.meta={height:28,width:28},this.file||(this.file=this.placeholderFile),this._originalFile=this.file,this._newFileWasSet=!1},Picture.prototype._handlePreloadedImage=function(e,i){this.file.dynamic&&(this.file.meta={height:i.naturalHeight,width:i.naturalWidth});var t=\"\",r=\"\",s=\"no-repeat\",a=(this.imagePosition&&(t=this.imagePosition.replace(\"-\",\" \")),this.sizeUsingTransforms()?this.getNaturalSize():this.size),n=this.fittingSize;switch(n=\"fitUpToOriginal\"===this.fittingSize?a.width<this.file.width||a.height<this.file.height?\"fit\":\"original\":n){case\"original\":r=this.file.width+\"px \"+this.file.height+\"px\";break;case\"fit\":r=\"contain\";break;case\"fill\":r=\"cover\";break;case\"repeat\":r=this.file.width+\"px \"+this.file.height+\"px\",s=\"repeat\";break;default:r=\"100% 100%\"}a=document.createElement(\"div\");a.style.cssText=\"position: absolute; top: 0; left: 0; width: 100%; height: 100%;\",a.style.backgroundImage=\"url('\"+(i.src||\"\").replace(\"'\",\"-\")+\"')\",a.style.backgroundPosition=t,a.style.backgroundSize=r,a.style.backgroundRepeat=s,ScreenObject.dummyImageSpacer&&((n=document.createElement(\"img\")).style.cssText=\"position: absolute; top: 0; left: 0; width: 100%; height: 100%;\",n.src=\"data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==\",a.appendChild(n)),e.appendChild(a)},Picture.prototype.positionUsingTransforms=function(){return!(ios(\"8\",null)||desktop()&&/^((?!chrome|android).)*safari/i.test(navigator.userAgent))&&this.isAnimated},Picture.prototype.sizeUsingTransforms=Picture.prototype.positionUsingTransforms,Picture.prototype.getNaturalSize=function(){var e,i,t,r,s,a,n,h,l;return this.file?(h=(e=this.file.width)/(i=this.file.height),n=(t=this.originalSize.width)/(r=this.originalSize.height),s=2048,\"original\"==this.fittingSize||\"fitUpToOriginal\"==this.fittingSize||\"repeat\"==this.fittingSize?{width:t,height:r}:\"stretch\"==this.fittingSize?{width:e,height:i}:(a=n<h&&\"fit\"==this.fittingSize||h<n&&\"fill\"==this.fittingSize,n=h<n&&\"fit\"==this.fittingSize||n<h&&\"fill\"==this.fittingSize,h=e,l=i,a?l=e/t*r:n&&(h=i/r*t),s<h&&(l*=s/h,h=s),s<l&&(h*=s/l,l=s),{width:h,height:l})):Picture.uber.getNaturalSize.call(this)},Picture.prototype.prepareFrame=function(){const e=this._calculateNewStyleAttributes();return function(){this._applyStyles(this.node,e)}.bind(this)},Picture.prototype.getImageUrl=function(){if(this.file)return this.file.getUrl()};;\nfunction CustomCode(){}inherit(CustomCode,ScreenObject),CustomCode.toString=function(){return\"[Clazz CustomCode]\"},CustomCode.prototype.toString=function(){return\"[CustomCode \"+this.localId+\"]\"},CustomCode.prototype.awake=function(){CustomCode.uber.awake.call(this),this._insertHtml=this._insertHtml.bind(this),this._insertCss=this._insertCss.bind(this)},CustomCode.prototype.createNode=function(){var e=CustomCode.uber.createNode.call(this),t=(\"string\"==typeof this.htmlContentFeedFieldKey&&FeedData.checkAndTrackValue(this.htmlContentFeedFieldKey,this.htmlContentFeedRowIndex,this.constructor.name,\"htmlContentFeedFieldKey\"),this._element=document.createElement(\"div\"),this._element.style.cssText=\"position: absolute; top: 0; left: 0; width: 100%; height: 100%; border: 0;\",this._privateApiTracked={},e.appendChild(this._element),this.incrementLoadingCount(),!1),i=new ActionContext(this),n=new Function(\"div\",\"ctx\",\"c\",\"unit\",\"screen\",this.javascriptScript.code),o=function(){t?console.warn('Multiple calls made to \"c()\" from \"'+this.name+'\" CustomCode JavaScript code'):(this.loading&&this.decrementLoadingCount(),t=!0)}.bind(this);this._insertCss(e),this._insertHtml();try{n.call(this,this._element,i,o,this.parentUnit,this.parentScreen)}catch(e){console.error(e.message+\"\\n\"+e.stack)}return e},CustomCode.prototype._insertCss=function(e){if(this.cssScript.cssCodeCompileError)return console.warn('Error compiling \"'+this.name+'\" CustomCode CSS code\\n'+this.cssScript.cssCodeCompileError);var t=document.head||document.getElementsByTagName(\"head\")[0],i=document.createElement(\"style\"),e=(i.type=\"text/css\",this.cssScript.code.replace(/#celtra--namespace--id/g,\"#\"+e.id));i.appendChild(document.createTextNode(e)),t.appendChild(i)},CustomCode.prototype._insertHtml=function(){var e,t=this.htmlScript.code;return\"string\"==typeof this.htmlContentFeedFieldKey&&(t=\"\",\"string\"==typeof(e=FeedData.getFieldValueByKey(this.htmlContentFeedFieldKey,this.htmlContentFeedRowIndex))&&FeedData.isCorrectFieldType(this.htmlContentFeedFieldKey,this.constructor.name,\"htmlContentFeedFieldKey\")&&(t=e)),document.createRange&&(e=document.createRange()).setStart&&e.createContextualFragment?(e.setStart(this._element,0),void this._element.appendChild(e.createContextualFragment(t))):this._element.insertAdjacentHTML(\"afterbegin\",t)},CustomCode.prototype._trackPrivateApiUsage=function(e){this._privateApiTracked[e]||(this._privateApiTracked[e]=!0,creative.track({name:\"retiredFeatureUsed\",featureType:\"apifunction\",featureName:e}))},Object.defineProperties(CustomCode.prototype,{elem:{get:function(){return this._trackPrivateApiUsage(\"CustomCode.elem.getter\"),this._element},set:function(e){this._trackPrivateApiUsage(\"CustomCode.elem.setter\"),this._element=e}}});;\nfunction File(t){if(t)if(\"string\"==typeof t)this.url=t;else{if(!t.url)throw\"Url parameter must be set.\";deep(this,t),0!==this.url.indexOf(creative.cachedApiUrl)&&0!==this.url.indexOf(creative.insecureCachedApiUrl)||(t=this.url.split(\"/\"),void 0===this.name&&(this.name=t[t.length-1]||\"unknown\"),void 0===this.blobHash&&(this.blobHash=t[t.length-2]),void 0===this.retinaScaleFactor&&(this.retinaScaleFactor=File.getRetinaScaleFactor(this.name)),void 0===this.quality&&(this.quality=File.getDefaultQuality(this.name)))}}File.toString=function(){return\"[Clazz File]\"},File.prototype.toString=function(){return\"[File \"+this.localId+\"(\"+this.name+\"/\"+this.url+\")]\"},File.prototype.getOriginalUrl=function(){if(this.url){if(-1!==this.url.indexOf(\"://\"))return this.url;if(\"/\"===this.url[0])throw\"An absolute path as URL?\";return creative.resourceUrl+this.url}if(this.blobHash)return(creative.secure?creative.cachedApiUrl:creative.insecureCachedApiUrl)+\"blobs/\"+this.blobHash+\"/\"+encodeURIComponent(this.name);throw\"Neither url nor blobHash set?\"},File.prototype.getUrl=function(t){var i=this.getOriginalUrl();return this.optimizable&&(i+=\"?transform=crush\",-1<this.quality&&(i+=\"&quality=\"+this.quality),t&&0<this.optimizedWidth(t)&&0<this.optimizedHeight(t)&&(i+=\"&resize=\"+this.optimizedWidth(t)+\"x\"+this.optimizedHeight(t)),this.dynamic&&\"string\"==typeof this.fitting&&0<this.fitting.length&&(i+=\"&fitting=\"+this.fitting)),i},File.prototype.getDataUri=function(t,i){t instanceof Function&&(i=t,t={});var e=!this.dynamic&&(void 0!==this.blobHash||0<=this.url.indexOf(\"api/animateySprites\")),r=creative.adapter.protoLoading.dataURIsSupported,a=creative.adapter.protoLoading.blobURIsSupported,n=\"treatment\"===(creative.experiments.get(\"UseDataURIsGlobally\")||{}).chosenVariant,h=1==creative.runtimeParams.dataURIsEnabled||1==creative.runtimeParams.turnOnBatching,r=(r||a)&&!creative.adapter.autoDecodesURLParams,a=(!!t.preferBatching||n||h)&&r&&e,o=this.getUrl(t.creativeUnitVariantId);void 0!==this.dataUri?defer(function(){i(this.dataUri)}.bind(this),0,\"File.getDataUri defer callback, has dataUri\"):a?creative.batcher.getDataUri(o,i):defer(function(){i(o)},0,\"File.getDataUri defer callback, url\")},Object.defineProperties(File.prototype,{dynamic:{get:function(){return void 0!==this._dynamic&&this._dynamic},set:function(t){this._dynamic=t},enumerable:!0,configurable:!1},optimizable:{get:function(){var t=this.getOriginalUrl(),t=0===t.indexOf(creative.cachedApiUrl)||0===t.indexOf(creative.insecureCachedApiUrl),i=\"on\"===(creative.experiments&&creative.experiments.get(\"FeedImageOptimization\")||{}).chosenVariant,t=t&&(!this.dynamic||i),i=!!String(this.name).match(/\\.(gif|png|jpe?g)$/i);return t&&i},enumerable:!0,configurable:!1},retina:{get:function(){return 1<this.retinaScaleFactor},enumerable:!0,configurable:!1},width:{get:function(){return this.retina?Math.round(this.meta.width/this.retinaScaleFactor):this.meta.width},enumerable:!0,configurable:!1},height:{get:function(){return this.retina?Math.round(this.meta.height/this.retinaScaleFactor):this.meta.height},enumerable:!0,configurable:!1},optimizedWidth:{get:function(){return function(t){if(\"number\"!=typeof t||!this.optimizationSettings)return null;for(var i=null,e=0;e<this.optimizationSettings.length;e++){var r=this.optimizationSettings[e];if(r.creativeUnitVariantId===t&&r.optimizedWidth){i=r;break}}return i&&(i.optimizedWidth<this.meta.width||this.dynamic)?i.optimizedWidth:null}.bind(this)},enumerable:!0,configurable:!1},optimizedHeight:{get:function(){return function(t){if(\"number\"!=typeof t||!this.optimizationSettings)return null;for(var i=null,e=0;e<this.optimizationSettings.length;e++){var r=this.optimizationSettings[e];if(r.creativeUnitVariantId===t&&r.optimizedHeight){i=r;break}}return i&&(i.optimizedHeight<this.meta.height||this.dynamic)?i.optimizedHeight:null}.bind(this)},enumerable:!0,configurable:!1}}),File.MAXIMUM_SUPPORTED_RETINA_SCALE=4,File.getDefaultQuality=function(t){t=String(t);var i=/\\.png$/i.test(t),e=/\\.jpe?g$/i.test(t),r=/\\.gif$/i.test(t),t=1<File.getRetinaScaleFactor(t),a=-1;return i?a=t?150:256:r?a=t?75:85:e&&(a=t?65:85),a},File.getRetinaScaleFactor=function(t){t=String(t);var i=new RegExp(\"^.*(@([2-\"+File.MAXIMUM_SUPPORTED_RETINA_SCALE+\"]+)x)\"),t=t.toLowerCase().match(i);return t&&t[2]?parseInt(t[2],10):1};;\nfunction Font(){}inherit(Font,FontBase),Font.toString=function(){return\"[Clazz Font]\"},Font.prototype.toString=function(){return\"[Font \"+this.localId+\"]\"},Font.prototype.hasFeedFields=function(){return this.feedFields instanceof Array},Font.prototype.getSubsetString=function(){var n,o,t=Font.uber.getSubsetString.apply(this);return this.hasFeedFields()?(\"string\"!=typeof this.extendedSubsetString&&(n=t||\"\",o=this.feedTextTransforms||[\"none\"],this.feedFields.forEach(function(t){FeedData.getFieldRowsByKey(t).forEach(function(e){\"string\"==typeof e.value&&o.forEach(function(t){n+=function(t,e){switch(t=String(t),e){case\"uppercase\":return t.toUpperCase();case\"lowercase\":return t.toLowerCase();default:return t}}(e.value,t)})})}),this.extendedSubsetString=n.replace(/[\\r\\n\\t\\f\\v]/g,\"\").split(\"\").filter(function(t,e,n){return n.indexOf(t)===e}).sort().join(\"\")),this.extendedSubsetString):t},Font.prototype.getUnicodeRange=function(){return this.hasFeedFields()?\"\":Font.uber.getUnicodeRange.apply(this)};;\nvar Form={toString:function(){return\"[Clazz Form]\"},submitAction:function(e,a,t){this.submitInProgress||(this.submitInProgress=!0,this.hasFailureCallback=!a.onFailure,e.track({name:\"formSubmissionAttempted\",label:a.reportLabel}),Form.validateAction(e,{formElements:a.formElements,onSuccess:function(t,n){var e;if(\"table\"==a.submitTarget&&a.tableId)e=creative.apiUrl+\"formTables/\"+a.tableId+\"/rows\";else{if(\"url\"!=a.submitTarget||!a.url)return(a.onFailure||nullai)(t,n);e=a.url}var r={fields:{}},i=(a.formElements.forEach(function(e){r.fields[e.fieldName]=e.getField()}),new XMLHttpRequest);i.open(\"POST\",e),i.setRequestHeader(\"Content-Type\",\"application/json; charset=utf-8\"),i.onreadystatechange=function(){var e;4===i.readyState&&((e=200<=i.status&&i.status<300)&&t.track({name:\"formSubmissionSucceeded\",label:a.reportLabel}),((e?a.onSuccess:a.onFailure)||nullai)(t,n),this.submitInProgress=!1)}.bind(this),i.send(JSON.stringify(r))}.bind(this),onFailure:function(e,t){(a.onFailure||nullai)(e,t),this.submitInProgress=!1}.bind(this)},t))}};Form.validateAction=function(e,t,n){for(var r=0;r<t.formElements.length;r++){var i=t.formElements[r],a=(i.getField(),t.formElements[r].isValid());if(!0!==a){if(this.hasFailureCallback||!t.onFailure){if(android(\"4.1\"))for(var o in s=s=u=l=o=o=void 0,c=[],creative.units){o=creative.units[o];if(o.isAppearing){var l=o.currentScreen.node.querySelectorAll(\"input, textarea\");if(l)for(var u=0;u<l.length;u++){var s=l[u],s={input:s,disabled:s.disabled};l[u].blur(),l[u].disabled=!0,c.push(s)}break}}i.parentUnit.alertAction(e,{text:a},function(){(t.onFailure||nullai)(e,d)})}else(t.onFailure||nullai)(e,n);return void(this.hasFailureCallback=null)}}this.hasFailureCallback=null,(t.onSuccess||nullai)(e,n);var c=[];function f(){c.forEach(function(e){e.input.disabled=e.disabled})}function d(){android(\"4.1\")&&defer(f,450),n()}},Form.setValueAction=function(e,t,n){if(!t.target)return defer(n);var r=t.target,i=t.value;!function(e,t){switch(e.constructor.name){case\"CheckBox\":return\"boolean\"==typeof t;case\"Input\":return\"string\"==typeof t}return}(r,i)?defer(n):defer(function(){!function(e,t){switch(e.constructor.name){case\"Input\":return e.setValue(t);case\"CheckBox\":e.setCheck(t)}}(r,i),n()})},Form.checkValueAction=function(e,t,n){if(!t.target)return n();var r=t.target.getField().value?\"Checked\":\"Not Checked\",t=t.outcomes.filter(function(e){return e.outcomeName===r})[0];(t&&t.action||nullai)(e,n)};;\n!function(){var t,e,i=1,n=2;function a(){return this.sizeUsingTransforms()?n:i}function h(t,e,i){return function(){return(this.isAnimated&&(\"string\"!=typeof i||this[i])?e:t).apply(this,arguments)}}function s(t,e,i){var n=t[e];t[e]=h(n,function(){return function e(i,n){var h;return i instanceof Object&&i.constructor===Object?(h={},Object.keys(i).forEach(function(t){h[t]=e(i[t],n)}),h):\"number\"==typeof i?i*n:i}(n.apply(this,arguments),a.call(this))},i)}function o(t,e,i){s(t,\"get\"+ucfirst(e),i)}function r(){return this.isAnimated}ScreenObject.prototype.positionUsingTransforms=r,ScreenObject.prototype.sizeUsingTransforms=r,\"undefined\"!=typeof Picture&&(Picture.prototype.positionUsingTransforms=r,Picture.prototype.sizeUsingTransforms=r,t=Picture.prototype.awake,Picture.prototype.awake=function(){t.apply(this,arguments),this._imageElement=null,this.on(\"appear\",this._drawImage.bind(this))},e=Picture.prototype.createNode,Picture.prototype.createNode=function(){return this.parentContainer.on(\"resize\",this._drawImage.bind(this)),e.apply(this,arguments)},Picture.prototype._drawImage=function(){var t,e,i,n,h,a,s,o,r;this._imageElement&&(\"stretch\"==this.fittingSize?(this._imageElement.style.position=\"absolute\",this._imageElement.style.left=\"0px\",this._imageElement.style.top=\"0px\",this._imageElement.style.width=\"100%\",this._imageElement.style.height=\"100%\"):(1===(t=this.imagePosition?this.imagePosition.split(\"-\"):[\"left\",\"top\"]).length&&(-1<[\"left\",\"right\"].indexOf(t[0])?t.push(\"center\"):t.unshift(\"center\")),n=this.file.width/this.file.height,r=(h=this.sizeUsingTransforms()?this.getNaturalSize():this.size).width/h.height,a=this.fittingSize,\"original\"===(a=\"fitUpToOriginal\"===this.fittingSize?h.width<this.file.width||h.height<this.file.height?\"fit\":\"original\":a)?(e=this.file.width,i=this.file.height):\"repeat\"===a?(e=this.file.meta.width,i=this.file.meta.height):\"stretch\"===a?(e=h.width,i=h.height):\"fit\"===a?i=r<n?(e=h.width,this.file.height*(h.width/this.file.width)):(e=this.file.width*(h.height/this.file.height),h.height):\"fill\"===a&&(i=r<n?(e=this.file.width*(h.height/this.file.height),h.height):(e=h.width,this.file.height*(h.width/this.file.width))),\"repeat\"===a?(s={left:0,center:Math.round((h.width*this._patternPixelRatio-e)/2),right:h.width*this._patternPixelRatio-e},o={top:0,center:Math.round((h.height*this._patternPixelRatio-i)/2),bottom:h.height*this._patternPixelRatio-i},this._imageElementsCtx.canvas.width=h.width*this._patternPixelRatio,this._imageElementsCtx.canvas.height=h.height*this._patternPixelRatio,this._imageElement.style.width=h.width+\"px\",this._imageElement.style.height=h.height+\"px\",this._imageElementsCtx.save(),this._imageElementsCtx.fillStyle=this._pattern,this._imageElementsCtx.translate(s[t[0]],o[t[1]]),this._imageElementsCtx.fillRect(-s[t[0]],-o[t[1]],h.width*this._patternPixelRatio,h.height*this._patternPixelRatio),this._imageElementsCtx.restore()):(s={left:\"left: 0px;\",center:\"left: \"+Math.round((h.width-e)/2)+\"px;\",right:\"right: 0px;\"},o={top:\"top: 0px;\",center:\"top: \"+Math.round((h.height-i)/2)+\"px;\",bottom:\"bottom: 0px;\"},r=s[t[0]]+\" \"+o[t[1]]+\" \",this._imageElement.style.cssText=\"position: absolute; \"+r+(\"width: \"+e+\"px; height: \"+i+\"px; \"))))},Picture.prototype._handlePreloadedImage=function(t,e){!this.file.dynamic&&\"repeat\"!==this.fittingSize||(this.file.meta={height:e.naturalHeight,width:e.naturalWidth}),\"repeat\"===this.fittingSize?(this._imageElement=document.createElement(\"canvas\"),this._imageElementsCtx=this._imageElement.getContext(\"2d\"),this._pattern=this._imageElementsCtx.createPattern(e,\"repeat\"),this._patternPixelRatio=e.naturalWidth/this.file.width):this._imageElement=e,this._drawImage(),t.appendChild(this._imageElement)});var l=Screen.prototype.resetAction,p=(Screen.prototype.resetAction=function(e,t,i){Scene.instances.forEach(function(t){t.parentScreen===this&&t.resetAction(e,{},noop)}.bind(this)),l.apply(this,arguments)},ScreenObject._roundIfNeeded=function(t){return t},[\"fontSize\",\"lineSpacing\",\"letterSpacing\",\"scaleBaseHeight\",\"scaleBaseWidth\",\"textShadowBlur\",\"textShadowOffset\"]),p=(\"undefined\"!=typeof Texty&&(s(Texty.prototype,\"getNaturalSize\"),[\"textPaddingTop\",\"textPaddingRight\",\"textPaddingBottom\",\"textPaddingLeft\"].forEach(function(t){o(Texty.prototype,t,\"useFontScaleFactorForPadding\")}),p.forEach(function(t){o(Texty.prototype,t)})),[\"borderWidth\",\"shadowDistance\",\"shadowBlur\"].concat(p));function c(t,e){var i=RichTexty.prototype[t];RichTexty.prototype[t]=h(i,function(t){return t=i.call(this,t),function(t,h){t.forEach(function(t){var e=h[t];if(\"none\"!==e)switch(t){case\"textShadow\":var i=e.split(\" \");i[0]=parseInt(i[0])*a.call(this)+\"px\",i[1]=parseInt(i[1])*a.call(this)+\"px\",h[t]=i.join(\" \");break;case\"textStroke\":i=e.split(\" \");i[0]=parseInt(i[0])*a.call(this)+\"px\",h[t]=i.join(\" \");break;default:var i=e.slice(-2),n=parseFloat(e)*a.call(this);h[t]=n+i}},this)}.call(this,e,t),t})}\"undefined\"!=typeof Button&&(s(Button.prototype,\"getNaturalSize\"),p.forEach(function(t){o(Button.prototype,t)})),\"undefined\"!=typeof RichTexty&&(s(RichTexty.prototype,\"getNaturalSize\"),c.call(this,\"transformContextualStyle\",[\"fontSize\",\"letterSpacing\",\"lineSpacing\",\"textShadow\",\"textStroke\"]),c.call(this,\"transformGlobalStyle\",[\"textPaddingTop\",\"textPaddingBottom\",\"textPaddingLeft\",\"textPaddingRight\"]))}();;\n!function(n){function r(n,e,t){n=n.filter(function(n){return n.id===e})[0];return n&&n.value?n.value:t}function e(n,e,t,u){function o(n){return((s*n+f)*n+c)*n}function r(n){for(var e,t,u,r,i=n,a=0;a<8;a++){if(u=o(i)-n,Math.abs(u)<.001)return i;if(r=(3*s*i+2*f)*i+c,Math.abs(r)<1e-6)break;i-=u/r}if((i=n)<(e=0))return e;if((t=1)<i)return t;for(;e<t;){if(u=o(i),Math.abs(u-n)<.001)return i;u<n?e=i:t=i,i=.5*(t-e)+e}return i}var c=3*n,f=3*(t-n)-c,s=1-c-f,i=3*e,a=3*(u-e)-i,I=1-i-a;return function(n){return n=r(n),((I*n+a)*n+i)*n}}function t(n){return Math.pow(n,2)}function u(n){return Math.pow(n,3)}function i(n){return Math.pow(n,4)}function a(n){return Math.pow(n,5)}function o(n){return 1-Math.cos(n*Math.PI*.5)}function c(n){return Math.pow(2,10*(n-1))}function f(n){return 1-Math.sqrt(1-n*n)}var s={none:function(n){return 0},steps:function(n,e){var t=r(e,\"steps\",3),e=r(e,\"jump-position\",\"end\"),u=1/t;return Math[\"end\"===e?\"floor\":\"ceil\"](n*t)*u},linear:function(n){return n},easeIn:e(.42,0,1,1),easeOut:e(0,0,.58,1),easeInOut:e(.42,0,.58,1),easeInQuad:t,easeOutQuad:function(n){return 1-t(1-n)},easeInOutQuad:function(n){return n<.5?.5*t(2*n):1-.5*t(2*(1-n))},easeInCubic:u,easeOutCubic:function(n){return 1-u(1-n)},easeInOutCubic:function(n){return n<.5?.5*u(2*n)/2:1-.5*u(2*(1-n))},easeInQuart:i,easeOutQuart:function(n){return 1-i(1-n)},easeInOutQuart:function(n){return n<.5?.5*i(2*n):1-.5*i(2*(1-n))},easeInQuint:a,easeOutQuint:function(n){return 1-a(1-n)},easeInOutQuint:function(n){return n<.5?.5*a(2*n):1-.5*a(2*(1-n))},easeInSine:o,easeOutSine:function(n){return 1-o(1-n)},easeInOutSine:function(n){return.5*o(2*n)},easeInExpo:c,easeOutExpo:function(n){return 1-c(1-n)},easeInOutExpo:function(n){return n<.5?.5*c(2*n):1-.5*c(2*(1-n))},easeInCirc:f,easeOutCirc:function(n){return 1-f(1-n)},easeInOutCirc:function(n){return n<.5?.5*f(2*n):1-.5*f(2*(1-n))},easeInBack:e(.6,-.28,.735,.045),easeOutBack:e(.175,.885,.32,1.275),easeInOutBack:e(.68,-.55,.265,1.55),cubicBezier:e};\"undefined\"!=typeof module&&null!==module?Object.keys(s).forEach(function(n){module.exports[n]=s[n]}):n.AnimationTimingFunctions=s}(this);;\n!function(e){var u=1e5,a=100*u;function v(e,t,n){this._propertyTweens={},n=n||v.getUsedProperties(e);var i,r=function(e){var t,n=[];for(t in e){var i=parseFloat(t);n[Math.round(i*u)]=e[t]}return n}(e);for(i in n){var o=n[i],s=function(e,t){var n,i,r={};for(i of Object.keys(t).sort(function(e,t){return parseInt(e)-parseInt(t)})){var o;e in t[i]&&(void 0===r[i]&&(r[i]={}),(o=t[i][e])instanceof Object?(void 0!==o.value&&null!==o.value&&(n=o.value,r[i].value=o.value),o.timingFunction&&(r[i].timingFunction=o.timingFunction)):(n=o,r[i].value=o,t[i].timingFunction&&(r[i].timingFunction=t[i].timingFunction)))}void 0===r[a]&&(r[a]={value:n});return r}(o,r);this._propertyTweens[o]=new p(s,t)}}function p(e,t){this._keyframes=e,this._defaultTimingFunction=t||AnimationTimingFunctions.linear,this._tweenable=function(e){for(var t in e)return\"number\"==typeof e[t].value}(this._keyframes)}v.prototype.getValuesAt=function(e){var t,n={};for(t in this._propertyTweens)n[t]=this._propertyTweens[t].getValueAt(e);return n},v.prototype.getPropertyTweens=function(){return this._propertyTweens},p.prototype.getKeyframes=function(){return Object.entries(this._keyframes).map(function(e){return{progress:e[0]/u,properties:e[1]}})},v.getUsedProperties=function(e){var t,n={};for(t in e)for(var i in e[t])\"timingFunction\"!=i&&(n[i]=!0);return Object.keys(n)},p.prototype.getValueAt=function(e){if(e<0)return null;var t,n,i,r,o;e=Math.round(e*u);for(i of Object.keys(this._keyframes).sort(function(e,t){return parseInt(e)-parseInt(t)}))i==e?(t={position:i,values:this._keyframes[i]},n={position:i,values:this._keyframes[i]}):i<e?t={position:i,values:this._keyframes[i]}:e<i&&!n&&(n={position:i,values:this._keyframes[i]});return this._tweenable?(o=n.position-t.position,r=e-t.position,r=(t.values.timingFunction&&t.values.timingFunction.instance?t.values.timingFunction.instance:this._defaultTimingFunction)(r=0==o?0:r/o,t.values.timingFunction&&t.values.timingFunction.parameters?t.values.timingFunction.parameters:[]),o=n.values.value-t.values.value,t.values.value+o*r):t.values.value},e.KeyframeAnimation=v}(this);;\nfunction BaseScenePlayer(e){this._scene=e,this._state=new StateObject({progress:-1}),this._update=this._update.bind(this),this._render=this._render.bind(this),this._end=this._end.bind(this),this.paused=!0,this.ended=!1,this.supportsAutoplay=!1,this.supportsFallback=!0}extend(BaseScenePlayer.prototype,EventEmitter),Object.defineProperty(BaseScenePlayer.prototype,\"progress\",{get:function(){return-1!==this._state.progress?this._state.progress:this.ended?100:0}}),BaseScenePlayer.prototype._update=function(){this._state.isDirty(\"progress\")&&(this._scene.updateObjects(this._state.progress),this.emit(\"progress\",this._state.progress))},BaseScenePlayer.prototype._render=function(){this._state.isDirty(\"progress\")&&(this._scene.renderObjects(),this.emit(\"render\"),this._state.markClean())},BaseScenePlayer.prototype._cancel=function(){Ticker.removeFrame(this._update,\"update\"),Ticker.removeFrame(this._render,\"render\"),Ticker.removeFrame(this._end,\"next\"),this._state.markClean(),this.paused=!0},BaseScenePlayer.prototype._end=function(){this.stop(),this.emit(\"end\")},BaseScenePlayer.prototype._stop=noop,BaseScenePlayer.prototype._pause=noop,BaseScenePlayer.prototype._play=function(){return!0},BaseScenePlayer.prototype.play=function(){this._cancel(),this._play()&&(this.paused=!1,Ticker.frame(this._update,\"update\"),Ticker.frame(this._render,\"render\"),this.emit(\"play\"))},BaseScenePlayer.prototype.displayFallbackFrame=function(e){this._scene.updateObjects(Math.max(0,Math.min(e,100))),this._scene.renderObjects(),this._state.progress=e,this._state.markClean()},BaseScenePlayer.prototype.pause=function(e){this.silentPause(),this.emit(\"pause\")},BaseScenePlayer.prototype.silentPause=function(e){this.ended=100===this._state.progress,0!==this._state.progress&&100!==this._state.progress||(this._state.progress=-1),this._cancel(),this._pause()},BaseScenePlayer.prototype.stop=function(){this._stop(),this.silentPause(),this.emit(\"stop\")},BaseScenePlayer.prototype.destroy=function(){this._cancel()};;\nfunction TimeScenePlayer(e){TimeScenePlayer.uberConstructor.call(this,e),this._state.registerValue(\"loopCount\",0),this._elapsedTime=null,this._startTime=null,this._lastUpdateTime=null,this.supportsFallback=!1}inherit(TimeScenePlayer,BaseScenePlayer),Object.defineProperty(TimeScenePlayer.prototype,\"_loopEnded\",{get:function(){return\"stop\"===this._scene.onEnd&&100<=this._state.progress||\"repeat\"===this._scene.onEnd&&this._scene.onEndRepeatCount&&this._state.loopCount>=this._scene.onEndRepeatCount||\"reverse\"===this._scene.onEnd&&this._scene.onEndReverseCount&&this._state.loopCount>=2*this._scene.onEndReverseCount||0===this._scene.getDuration().milliseconds}}),Object.defineProperty(TimeScenePlayer.prototype,\"_newLoopStarted\",{get:function(){return this._state.isDirty(\"loopCount\")}}),TimeScenePlayer.prototype._update=function(e){var t=this._scene.getDuration().milliseconds,t=(this._startTime=this._startTime||e,this._lastUpdateTime&&(this._startTime-=this._lastUpdateTime,this._lastUpdateTime=null),this._elapsedTime=e-this._startTime,this._state.loopCount=Math.floor(0<t?this._elapsedTime/t:1),100*(0<t?this._elapsedTime/t:1)),s=t%100,s=\"repeat\"===this._scene.onEnd?this._loopEnded||this._newLoopStarted?100:s:\"reverse\"===this._scene.onEnd?this._newLoopStarted&&this._state.loopCount%2?100:this._loopEnded?0:this._state.loopCount%2?100-s:s:t;this._state.progress=Math.max(0,Math.min(s,100)),TimeScenePlayer.uber._update.apply(this,arguments),this._loopEnded&&Ticker.frame(this._end,\"next\")},TimeScenePlayer.prototype._pause=function(){this._lastUpdateTime||(this._lastUpdateTime=this._elapsedTime),this._elapsedTime=null,this._startTime=null},TimeScenePlayer.prototype._stop=function(){this._lastUpdateTime=null,this._elapsedTime=null,this._state.loopCount=0};;\n";
            head.appendChild(js);





            // Run!
            // Check for runtimeParams to prevent IE 10 from initializing the creative early when host page is refreshed
            if (typeof runtimeParams === 'undefined') return;

            Creative.init({
                "id": "041f1fd0",
                "clazz": "UniversalInteractiveVideo",
                "intendedDeviceType": "Any",
                "name": "SA Test Interactive Video hotspot",
                "units": {
                    "banner": {
                        "localId": 1,
                        "clazz": "CreativeUnit",
                        "master": {
                            "hiddenInBuilder": false,
                            "lockedInBuilder": false,
                            "triggers": [{
                                "localId": 907,
                                "type": "firstAppear",
                                "onFire": null,
                                "isCustom": false,
                                "isAnimated": false
                            }],
                            "scenes": [{
                                "clazz": "Scene",
                                "localId": 301,
                                "name": "pop1",
                                "type": "time",
                                "duration": 5,
                                "endingMode": "lastKeyframe",
                                "onEnd": "stop",
                                "onEndRepeatCount": 0,
                                "onEndReverseCount": 0,
                                "onEndWaitForCount": true,
                                "autoPlay": null,
                                "intersection": null,
                                "fallbackFrame": null,
                                "initialScene": null,
                                "framesPerSecond": 20,
                                "objects": [{
                                    "targetLocalId": 47,
                                    "layoutSpecificValues": [
                                        [{
                                            "frame": 0,
                                            "properties": {
                                                "hidden": {
                                                    "localId": 332,
                                                    "value": null,
                                                    "timingFunction": {
                                                        "id": "ease-in-out-back"
                                                    },
                                                    "isAnimated": false
                                                },
                                                "hcenter": {
                                                    "localId": 314,
                                                    "value": null,
                                                    "timingFunction": {
                                                        "id": "ease-out-circ"
                                                    },
                                                    "isAnimated": false
                                                },
                                                "vcenter": {
                                                    "localId": 315,
                                                    "value": null,
                                                    "timingFunction": {
                                                        "id": "ease-out-circ"
                                                    },
                                                    "isAnimated": false
                                                },
                                                "width": {
                                                    "localId": 593,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 595,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "opacity": {
                                                    "localId": 609,
                                                    "value": null,
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 10,
                                            "properties": {
                                                "hcenter": {
                                                    "localId": 316,
                                                    "value": "-38.87%",
                                                    "isAnimated": false
                                                },
                                                "vcenter": {
                                                    "localId": 317,
                                                    "value": "37.33%",
                                                    "isAnimated": false
                                                },
                                                "width": {
                                                    "localId": 603,
                                                    "value": "13%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 604,
                                                    "value": "17.27%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 19,
                                            "properties": {
                                                "width": {
                                                    "localId": 594,
                                                    "value": "15%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 596,
                                                    "value": "19.93%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 28,
                                            "properties": {
                                                "width": {
                                                    "localId": 597,
                                                    "value": "13%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 598,
                                                    "value": "17.27%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 36,
                                            "properties": {
                                                "width": {
                                                    "localId": 607,
                                                    "value": "13%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 608,
                                                    "value": "17.27%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 45,
                                            "properties": {
                                                "width": {
                                                    "localId": 599,
                                                    "value": "15%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 600,
                                                    "value": "19.93%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 54,
                                            "properties": {
                                                "width": {
                                                    "localId": 601,
                                                    "value": "13%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 602,
                                                    "value": "17.27%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 68,
                                            "properties": {
                                                "opacity": {
                                                    "localId": 610,
                                                    "value": 1,
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 75,
                                            "properties": {
                                                "hidden": {
                                                    "localId": 333,
                                                    "value": true,
                                                    "isAnimated": false
                                                },
                                                "opacity": {
                                                    "localId": 611,
                                                    "value": 0,
                                                    "isAnimated": false
                                                }
                                            }
                                        }]
                                    ]
                                }],
                                "isAnimated": false
                            }, {
                                "clazz": "Scene",
                                "localId": 302,
                                "name": "pop2",
                                "type": "time",
                                "duration": 5,
                                "endingMode": "lastKeyframe",
                                "onEnd": "stop",
                                "onEndRepeatCount": 0,
                                "onEndReverseCount": 0,
                                "onEndWaitForCount": true,
                                "autoPlay": null,
                                "intersection": null,
                                "fallbackFrame": null,
                                "initialScene": null,
                                "framesPerSecond": 20,
                                "objects": [{
                                    "targetLocalId": 56,
                                    "layoutSpecificValues": [
                                        [{
                                            "frame": 0,
                                            "properties": {
                                                "hidden": {
                                                    "localId": 412,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "hcenter": {
                                                    "localId": 403,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "vcenter": {
                                                    "localId": 404,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "opacity": {
                                                    "localId": 656,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "width": {
                                                    "localId": 709,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 710,
                                                    "value": null,
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 8,
                                            "properties": {
                                                "hcenter": {
                                                    "localId": 405,
                                                    "value": "34.75%",
                                                    "isAnimated": false
                                                },
                                                "vcenter": {
                                                    "localId": 406,
                                                    "value": "28.62%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 14,
                                            "properties": {
                                                "width": {
                                                    "localId": 692,
                                                    "value": "13.00%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 693,
                                                    "value": "21.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 23,
                                            "properties": {
                                                "width": {
                                                    "localId": 711,
                                                    "value": "15%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 712,
                                                    "value": "24.36%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 32,
                                            "properties": {
                                                "width": {
                                                    "localId": 696,
                                                    "value": "13.00%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 697,
                                                    "value": "21.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 40,
                                            "properties": {
                                                "width": {
                                                    "localId": 698,
                                                    "value": "13.00%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 699,
                                                    "value": "21.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 49,
                                            "properties": {
                                                "width": {
                                                    "localId": 700,
                                                    "value": "15%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 701,
                                                    "value": "24.36%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 58,
                                            "properties": {
                                                "width": {
                                                    "localId": 702,
                                                    "value": "13.00%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 703,
                                                    "value": "21.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 72,
                                            "properties": {
                                                "opacity": {
                                                    "localId": 704,
                                                    "value": 1,
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 79,
                                            "properties": {
                                                "opacity": {
                                                    "localId": 705,
                                                    "value": 0,
                                                    "isAnimated": false
                                                },
                                                "hidden": {
                                                    "localId": 706,
                                                    "value": true,
                                                    "isAnimated": false
                                                }
                                            }
                                        }]
                                    ]
                                }],
                                "isAnimated": false
                            }, {
                                "clazz": "Scene",
                                "localId": 303,
                                "name": "pop3",
                                "type": "time",
                                "duration": 5,
                                "endingMode": "lastKeyframe",
                                "onEnd": "stop",
                                "onEndRepeatCount": 0,
                                "onEndReverseCount": 0,
                                "onEndWaitForCount": true,
                                "autoPlay": null,
                                "intersection": null,
                                "fallbackFrame": null,
                                "initialScene": null,
                                "framesPerSecond": 20,
                                "objects": [{
                                    "targetLocalId": 62,
                                    "layoutSpecificValues": [
                                        [{
                                            "frame": 0,
                                            "properties": {
                                                "hidden": {
                                                    "localId": 427,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "vcenter": {
                                                    "localId": 424,
                                                    "value": null,
                                                    "timingFunction": {
                                                        "id": "ease-out-circ"
                                                    },
                                                    "isAnimated": false
                                                },
                                                "hcenter": {
                                                    "localId": 423,
                                                    "value": null,
                                                    "timingFunction": {
                                                        "id": "ease-out-circ"
                                                    },
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 750,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "opacity": {
                                                    "localId": 751,
                                                    "value": null,
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 10,
                                            "properties": {
                                                "vcenter": {
                                                    "localId": 426,
                                                    "value": "34.00%",
                                                    "isAnimated": false
                                                },
                                                "hcenter": {
                                                    "localId": 425,
                                                    "value": "6.00%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 15,
                                            "properties": {
                                                "width": {
                                                    "localId": 753,
                                                    "value": "13.00%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 754,
                                                    "value": "21.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 24,
                                            "properties": {
                                                "width": {
                                                    "localId": 755,
                                                    "value": "15%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 756,
                                                    "value": "24.36%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 33,
                                            "properties": {
                                                "width": {
                                                    "localId": 757,
                                                    "value": "13.00%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 758,
                                                    "value": "21.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 41,
                                            "properties": {
                                                "width": {
                                                    "localId": 759,
                                                    "value": "13.00%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 760,
                                                    "value": "21.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 50,
                                            "properties": {
                                                "width": {
                                                    "localId": 761,
                                                    "value": "15%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 762,
                                                    "value": "24.36%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 59,
                                            "properties": {
                                                "width": {
                                                    "localId": 763,
                                                    "value": "13.00%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 764,
                                                    "value": "21.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 73,
                                            "properties": {
                                                "opacity": {
                                                    "localId": 765,
                                                    "value": 1,
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 80,
                                            "properties": {
                                                "hidden": {
                                                    "localId": 767,
                                                    "value": true,
                                                    "isAnimated": false
                                                },
                                                "opacity": {
                                                    "localId": 766,
                                                    "value": 0,
                                                    "isAnimated": false
                                                }
                                            }
                                        }]
                                    ]
                                }],
                                "isAnimated": false
                            }, {
                                "clazz": "Scene",
                                "localId": 304,
                                "name": "pop4",
                                "type": "time",
                                "duration": 10,
                                "endingMode": "lastKeyframe",
                                "onEnd": "stop",
                                "onEndRepeatCount": 0,
                                "onEndReverseCount": 0,
                                "onEndWaitForCount": true,
                                "autoPlay": null,
                                "intersection": null,
                                "fallbackFrame": null,
                                "initialScene": {
                                    "sceneLocalId": 335,
                                    "hostLocalId": 2
                                },
                                "framesPerSecond": 20,
                                "objects": [{
                                    "targetLocalId": 51,
                                    "layoutSpecificValues": [
                                        [{
                                            "frame": 0,
                                            "properties": {
                                                "vcenter": {
                                                    "localId": 492,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "hcenter": {
                                                    "localId": 491,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 772,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "opacity": {
                                                    "localId": 773,
                                                    "value": null,
                                                    "isAnimated": false
                                                },
                                                "hidden": {
                                                    "localId": 1036,
                                                    "value": null,
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 10,
                                            "properties": {
                                                "vcenter": {
                                                    "localId": 494,
                                                    "value": "-21.34%",
                                                    "isAnimated": false
                                                },
                                                "hcenter": {
                                                    "localId": 493,
                                                    "value": "-31.00%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 15,
                                            "properties": {
                                                "height": {
                                                    "localId": 775,
                                                    "value": "21.11%",
                                                    "isAnimated": false
                                                },
                                                "width": {
                                                    "localId": 776,
                                                    "value": "13.00%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 24,
                                            "properties": {
                                                "height": {
                                                    "localId": 777,
                                                    "value": "24.36%",
                                                    "isAnimated": false
                                                },
                                                "width": {
                                                    "localId": 778,
                                                    "value": "15%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 33,
                                            "properties": {
                                                "height": {
                                                    "localId": 779,
                                                    "value": "21.11%",
                                                    "isAnimated": false
                                                },
                                                "width": {
                                                    "localId": 780,
                                                    "value": "13.00%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 41,
                                            "properties": {
                                                "height": {
                                                    "localId": 781,
                                                    "value": "21.11%",
                                                    "isAnimated": false
                                                },
                                                "width": {
                                                    "localId": 782,
                                                    "value": "13.00%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 50,
                                            "properties": {
                                                "height": {
                                                    "localId": 783,
                                                    "value": "24.36%",
                                                    "isAnimated": false
                                                },
                                                "width": {
                                                    "localId": 784,
                                                    "value": "15%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 59,
                                            "properties": {
                                                "height": {
                                                    "localId": 785,
                                                    "value": "21.11%",
                                                    "isAnimated": false
                                                },
                                                "width": {
                                                    "localId": 786,
                                                    "value": "13.00%",
                                                    "isAnimated": false
                                                },
                                                "hidden": {
                                                    "localId": 1037,
                                                    "value": true,
                                                    "isAnimated": false
                                                }
                                            }
                                        }]
                                    ]
                                }, {
                                    "targetLocalId": 360,
                                    "layoutSpecificValues": [
                                        [{
                                            "frame": 0,
                                            "properties": {
                                                "opacity": {
                                                    "localId": 791,
                                                    "value": null,
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 10,
                                            "properties": {
                                                "opacity": {
                                                    "localId": 792,
                                                    "value": 1,
                                                    "isAnimated": false
                                                }
                                            }
                                        }]
                                    ]
                                }],
                                "isAnimated": false
                            }, {
                                "clazz": "Scene",
                                "localId": 335,
                                "name": "learn more",
                                "type": "time",
                                "duration": 10,
                                "endingMode": "lastKeyframe",
                                "onEnd": "stop",
                                "onEndRepeatCount": 0,
                                "onEndReverseCount": 0,
                                "onEndWaitForCount": true,
                                "autoPlay": null,
                                "intersection": null,
                                "fallbackFrame": null,
                                "initialScene": null,
                                "framesPerSecond": 20,
                                "objects": [{
                                    "targetLocalId": 360,
                                    "layoutSpecificValues": [
                                        [{
                                            "frame": 0,
                                            "properties": {
                                                "width": {
                                                    "localId": 383,
                                                    "value": null,
                                                    "timingFunction": {
                                                        "id": "ease-out-expo"
                                                    },
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 385,
                                                    "value": null,
                                                    "timingFunction": {
                                                        "id": "ease-out-expo"
                                                    },
                                                    "isAnimated": false
                                                },
                                                "hcenter": {
                                                    "localId": 387,
                                                    "value": null,
                                                    "timingFunction": {
                                                        "id": "ease-out-expo"
                                                    },
                                                    "isAnimated": false
                                                },
                                                "top": {
                                                    "localId": 388,
                                                    "value": null,
                                                    "timingFunction": {
                                                        "id": "ease-out-expo"
                                                    },
                                                    "isAnimated": false
                                                },
                                                "hidden": {
                                                    "localId": 393,
                                                    "value": false,
                                                    "timingFunction": {
                                                        "id": "ease-out-expo"
                                                    },
                                                    "isAnimated": false
                                                },
                                                "opacity": {
                                                    "localId": 629,
                                                    "value": null,
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 10,
                                            "properties": {
                                                "width": {
                                                    "localId": 384,
                                                    "value": "25%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 386,
                                                    "value": "15.11%",
                                                    "isAnimated": false
                                                },
                                                "hcenter": {
                                                    "localId": 389,
                                                    "value": "-0.50%",
                                                    "isAnimated": false
                                                },
                                                "top": {
                                                    "localId": 390,
                                                    "value": "1.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 52,
                                            "properties": {
                                                "hcenter": {
                                                    "localId": 612,
                                                    "value": "-0.50%",
                                                    "isAnimated": false
                                                },
                                                "top": {
                                                    "localId": 613,
                                                    "value": "1.11%",
                                                    "isAnimated": false
                                                },
                                                "width": {
                                                    "localId": 614,
                                                    "value": "25%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 615,
                                                    "value": "15.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 59,
                                            "properties": {
                                                "width": {
                                                    "localId": 617,
                                                    "value": "28%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 618,
                                                    "value": "16.92%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 66,
                                            "properties": {
                                                "width": {
                                                    "localId": 619,
                                                    "value": "25%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 620,
                                                    "value": "15.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 125,
                                            "properties": {
                                                "width": {
                                                    "localId": 621,
                                                    "value": "25%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 622,
                                                    "value": "15.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 132,
                                            "properties": {
                                                "width": {
                                                    "localId": 623,
                                                    "value": "28%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 624,
                                                    "value": "16.92%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 140,
                                            "properties": {
                                                "width": {
                                                    "localId": 625,
                                                    "value": "25%",
                                                    "isAnimated": false
                                                },
                                                "height": {
                                                    "localId": 626,
                                                    "value": "15.11%",
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 163,
                                            "properties": {
                                                "opacity": {
                                                    "localId": 630,
                                                    "value": 1,
                                                    "isAnimated": false
                                                }
                                            }
                                        }, {
                                            "frame": 172,
                                            "properties": {
                                                "opacity": {
                                                    "localId": 631,
                                                    "value": 0,
                                                    "isAnimated": false
                                                }
                                            }
                                        }]
                                    ]
                                }],
                                "isAnimated": false
                            }],
                            "showOverflow": false,
                            "title": "Video",
                            "localId": 2,
                            "objects": [{
                                "layoutSpecificValues": [{
                                    "position": {
                                        "left": "0%",
                                        "top": "0%"
                                    },
                                    "size": {
                                        "width": "100%",
                                        "height": "100%"
                                    },
                                    "rotation": 0,
                                    "opacity": 1,
                                    "hidden": false
                                }],
                                "triggers": [{
                                    "localId": 148,
                                    "type": "videoPause",
                                    "parameters": {},
                                    "onFire": null,
                                    "isCustom": false,
                                    "isAnimated": false
                                }, {
                                    "localId": 150,
                                    "type": "videoCuepoint",
                                    "parameters": {
                                        "name": "Cue1",
                                        "time": 1,
                                        "cuepointType": "time"
                                    },
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 331,
                                                        "disabled": false,
                                                        "actionClazz": "Scene",
                                                        "instanceLocalId": "301",
                                                        "method": "playScene",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "301"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }, {
                                    "localId": 396,
                                    "type": "videoCuepoint",
                                    "parameters": {
                                        "name": "Cue2",
                                        "time": 6.17,
                                        "cuepointType": "time"
                                    },
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 402,
                                                        "disabled": false,
                                                        "actionClazz": "Scene",
                                                        "instanceLocalId": "335",
                                                        "method": "playScene",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "335"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }, {
                                    "localId": 399,
                                    "type": "videoCuepoint",
                                    "parameters": {
                                        "name": "Cue3",
                                        "time": 7.05,
                                        "cuepointType": "time"
                                    },
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 414,
                                                        "disabled": false,
                                                        "actionClazz": "Scene",
                                                        "instanceLocalId": "302",
                                                        "method": "playScene",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "302"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }, {
                                    "localId": 400,
                                    "type": "videoCuepoint",
                                    "parameters": {
                                        "name": "Cue4",
                                        "time": 11.02,
                                        "cuepointType": "time"
                                    },
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 496,
                                                        "disabled": false,
                                                        "actionClazz": "Scene",
                                                        "instanceLocalId": "303",
                                                        "method": "playScene",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "303"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }, {
                                    "localId": 401,
                                    "type": "videoCuepoint",
                                    "parameters": {
                                        "name": "Cue5",
                                        "time": 18.07,
                                        "cuepointType": "time"
                                    },
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 497,
                                                        "disabled": false,
                                                        "actionClazz": "Scene",
                                                        "instanceLocalId": "304",
                                                        "method": "playScene",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "304"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }, {
                                    "localId": 1001,
                                    "type": "videoComplete",
                                    "parameters": {},
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 1002,
                                                        "disabled": false,
                                                        "actionClazz": "Screen",
                                                        "instanceLocalId": "2",
                                                        "method": "reset",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "2",
                                                            "actionClazz": "Screen"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }, {
                                    "localId": 1139,
                                    "type": "videoCuepoint",
                                    "parameters": {
                                        "name": "Cue6",
                                        "time": 25,
                                        "cuepointType": "percentage"
                                    },
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 1140,
                                                        "disabled": false,
                                                        "actionClazz": "Kernel",
                                                        "instanceLocalId": null,
                                                        "method": "executeJavascript",
                                                        "isStatic": true,
                                                        "args": {
                                                            "scriptLocalId": 1141,
                                                            "name": "inf_video_25pc_video_completion"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }, {
                                    "localId": 1159,
                                    "type": "videoCuepoint",
                                    "parameters": {
                                        "name": "Cue7",
                                        "time": 50,
                                        "cuepointType": "percentage"
                                    },
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 1163,
                                                        "disabled": false,
                                                        "actionClazz": "Kernel",
                                                        "instanceLocalId": null,
                                                        "method": "executeJavascript",
                                                        "isStatic": true,
                                                        "args": {
                                                            "scriptLocalId": 1162,
                                                            "name": "inf_video_50pc_video_completion copy"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }, {
                                    "localId": 1160,
                                    "type": "videoCuepoint",
                                    "parameters": {
                                        "name": "Cue8",
                                        "time": 75.00000000000001,
                                        "cuepointType": "percentage"
                                    },
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 1165,
                                                        "disabled": false,
                                                        "actionClazz": "Kernel",
                                                        "instanceLocalId": null,
                                                        "method": "executeJavascript",
                                                        "isStatic": true,
                                                        "args": {
                                                            "scriptLocalId": 1164,
                                                            "name": "inf_video_75pc_video_completion copy"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }, {
                                    "localId": 1161,
                                    "type": "videoComplete",
                                    "parameters": {},
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 1167,
                                                        "disabled": false,
                                                        "actionClazz": "Kernel",
                                                        "instanceLocalId": null,
                                                        "method": "executeJavascript",
                                                        "isStatic": true,
                                                        "args": {
                                                            "scriptLocalId": 1166,
                                                            "name": "inf_video_100pc_video_completion copy"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }],
                                "name": "Main Video",
                                "aspectRatioLocked": false,
                                "zIndex": 1,
                                "hiddenInBuilder": false,
                                "lockedInBuilder": false,
                                "videoSourceFeedFieldKey": null,
                                "videoLocalId": 17,
                                "canUseNewVideoStreams": true,
                                "posterImageLocalId": 33,
                                "posterImageGenerate": "asset",
                                "posterImageGenerateAt": 50,
                                "purpose": "full",
                                "fitting": "fit",
                                "barColor": "#000000",
                                "hqQuality": false,
                                "autoplay": true,
                                "fallbackToImageSequence": false,
                                "muted": false,
                                "preload": true,
                                "loop": false,
                                "pauseOnEnd": false,
                                "closeOnEnd": false,
                                "indefinitely": false,
                                "repeatTimes": 0,
                                "teaserPlayFrom": 0,
                                "teaserPlayTo": 5,
                                "loader": true,
                                "countdown": true,
                                "resumeButton": true,
                                "fullscreenButton": false,
                                "controlsMode": "never",
                                "clazz": "MasterVideo",
                                "localId": 18,
                                "fieldName": "Main Video",
                                "isAnimated": false
                            }, {
                                "layoutSpecificValues": [{
                                    "position": {
                                        "hcenter": "-64.38%",
                                        "vcenter": "44.63%"
                                    },
                                    "size": {
                                        "width": "13.00%",
                                        "height": "17.27%"
                                    },
                                    "rotation": 0,
                                    "opacity": 1,
                                    "hidden": false
                                }],
                                "triggers": [{
                                    "localId": 322,
                                    "type": "tap",
                                    "parameters": {},
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 819,
                                                        "disabled": false,
                                                        "actionClazz": "Creative",
                                                        "instanceLocalId": null,
                                                        "method": "trackCustomEvent",
                                                        "isStatic": true,
                                                        "args": {
                                                            "name": "Hotspot 1 Tap"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 323,
                                                        "disabled": false,
                                                        "actionClazz": "ScreenObject",
                                                        "instanceLocalId": "254",
                                                        "method": "show",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "254"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 518,
                                                        "disabled": false,
                                                        "actionClazz": "CreativeUnit",
                                                        "instanceLocalId": 1,
                                                        "method": "pauseMasterVideo",
                                                        "isStatic": false,
                                                        "args": {},
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 1172,
                                                        "disabled": false,
                                                        "actionClazz": "Kernel",
                                                        "instanceLocalId": null,
                                                        "method": "executeJavascript",
                                                        "isStatic": true,
                                                        "args": {
                                                            "scriptLocalId": 1173,
                                                            "name": "eng_hotspot_1_first_clicked"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }],
                                "name": "Explore_Btn1",
                                "aspectRatioLocked": true,
                                "zIndex": 5,
                                "hiddenInBuilder": false,
                                "lockedInBuilder": false,
                                "imageSourceFeedFieldKey": null,
                                "fileLocalId": 38,
                                "fittingSize": "fit",
                                "imagePosition": "center",
                                "importedFromLayoutFile": false,
                                "clazz": "Picture",
                                "localId": 47,
                                "isAnimated": true
                            }, {
                                "layoutSpecificValues": [{
                                    "position": {
                                        "vcenter": "-19.89%",
                                        "hcenter": "-58.56%"
                                    },
                                    "size": {
                                        "width": "13.00%",
                                        "height": "21.11%"
                                    },
                                    "rotation": 0,
                                    "opacity": 1,
                                    "hidden": false
                                }],
                                "triggers": [{
                                    "localId": 329,
                                    "type": "tap",
                                    "parameters": {},
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 822,
                                                        "disabled": false,
                                                        "actionClazz": "Creative",
                                                        "instanceLocalId": null,
                                                        "method": "trackCustomEvent",
                                                        "isStatic": true,
                                                        "args": {
                                                            "name": "Hotspot 4 Tap"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 330,
                                                        "disabled": false,
                                                        "actionClazz": "ScreenObject",
                                                        "instanceLocalId": "264",
                                                        "method": "show",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "264"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 521,
                                                        "disabled": false,
                                                        "actionClazz": "CreativeUnit",
                                                        "instanceLocalId": 1,
                                                        "method": "pauseMasterVideo",
                                                        "isStatic": false,
                                                        "args": {},
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 1179,
                                                        "disabled": false,
                                                        "actionClazz": "Kernel",
                                                        "instanceLocalId": null,
                                                        "method": "executeJavascript",
                                                        "isStatic": true,
                                                        "args": {
                                                            "scriptLocalId": 1178,
                                                            "name": "eng_hotspot_4_first_clicked"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }],
                                "name": "Explore_Btn4",
                                "aspectRatioLocked": false,
                                "zIndex": 2,
                                "hiddenInBuilder": false,
                                "lockedInBuilder": false,
                                "imageSourceFeedFieldKey": null,
                                "fileLocalId": 37,
                                "fittingSize": "fit",
                                "imagePosition": "center",
                                "importedFromLayoutFile": false,
                                "clazz": "Picture",
                                "localId": 51,
                                "isAnimated": true
                            }, {
                                "layoutSpecificValues": [{
                                    "position": {
                                        "hcenter": "62.44%",
                                        "vcenter": "27.89%"
                                    },
                                    "size": {
                                        "width": "13.00%",
                                        "height": "21.11%"
                                    },
                                    "rotation": 0,
                                    "opacity": 1,
                                    "hidden": false
                                }],
                                "triggers": [{
                                    "localId": 327,
                                    "type": "tap",
                                    "parameters": {},
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 820,
                                                        "disabled": false,
                                                        "actionClazz": "Creative",
                                                        "instanceLocalId": null,
                                                        "method": "trackCustomEvent",
                                                        "isStatic": true,
                                                        "args": {
                                                            "name": "Hotspot 2 Tap"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 328,
                                                        "disabled": false,
                                                        "actionClazz": "ScreenObject",
                                                        "instanceLocalId": "288",
                                                        "method": "show",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "288"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 519,
                                                        "disabled": false,
                                                        "actionClazz": "CreativeUnit",
                                                        "instanceLocalId": 1,
                                                        "method": "pauseMasterVideo",
                                                        "isStatic": false,
                                                        "args": {},
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 1175,
                                                        "disabled": false,
                                                        "actionClazz": "Kernel",
                                                        "instanceLocalId": null,
                                                        "method": "executeJavascript",
                                                        "isStatic": true,
                                                        "args": {
                                                            "scriptLocalId": 1174,
                                                            "name": "eng_hotspot_2_first_clicked"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }],
                                "name": "Explore_Btn2",
                                "aspectRatioLocked": true,
                                "zIndex": 4,
                                "hiddenInBuilder": false,
                                "lockedInBuilder": false,
                                "imageSourceFeedFieldKey": null,
                                "fileLocalId": 36,
                                "fittingSize": "fit",
                                "imagePosition": "center",
                                "importedFromLayoutFile": false,
                                "clazz": "Picture",
                                "localId": 56,
                                "isAnimated": true
                            }, {
                                "layoutSpecificValues": [{
                                    "position": {
                                        "vcenter": "63.44%",
                                        "hcenter": "15.69%"
                                    },
                                    "size": {
                                        "width": "13.00%",
                                        "height": "21.11%"
                                    },
                                    "rotation": 0,
                                    "opacity": 1,
                                    "hidden": false
                                }],
                                "triggers": [{
                                    "localId": 325,
                                    "type": "tap",
                                    "parameters": {},
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 821,
                                                        "disabled": false,
                                                        "actionClazz": "Creative",
                                                        "instanceLocalId": null,
                                                        "method": "trackCustomEvent",
                                                        "isStatic": true,
                                                        "args": {
                                                            "name": "Hotspot 3 Tap"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 326,
                                                        "disabled": false,
                                                        "actionClazz": "ScreenObject",
                                                        "instanceLocalId": "276",
                                                        "method": "show",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "276"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 520,
                                                        "disabled": false,
                                                        "actionClazz": "CreativeUnit",
                                                        "instanceLocalId": 1,
                                                        "method": "pauseMasterVideo",
                                                        "isStatic": false,
                                                        "args": {},
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 1177,
                                                        "disabled": false,
                                                        "actionClazz": "Kernel",
                                                        "instanceLocalId": null,
                                                        "method": "executeJavascript",
                                                        "isStatic": true,
                                                        "args": {
                                                            "scriptLocalId": 1176,
                                                            "name": "eng_hotspot_3_first_clicked "
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }],
                                "name": "Explore_Btn3",
                                "aspectRatioLocked": false,
                                "zIndex": 3,
                                "hiddenInBuilder": false,
                                "lockedInBuilder": false,
                                "imageSourceFeedFieldKey": null,
                                "fileLocalId": 35,
                                "fittingSize": "fit",
                                "imagePosition": "center",
                                "importedFromLayoutFile": false,
                                "clazz": "Picture",
                                "localId": 62,
                                "isAnimated": true
                            }, {
                                "layoutSpecificValues": [{
                                    "position": {
                                        "left": "3.38%",
                                        "top": "53.11%"
                                    },
                                    "size": {
                                        "width": "24.00%",
                                        "height": "42.67%"
                                    },
                                    "rotation": 0,
                                    "opacity": 1,
                                    "hidden": true
                                }],
                                "triggers": [{
                                    "localId": 262,
                                    "type": "tap",
                                    "parameters": {},
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 263,
                                                        "disabled": false,
                                                        "actionClazz": "ScreenObject",
                                                        "instanceLocalId": "254",
                                                        "method": "hide",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "254"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 522,
                                                        "disabled": false,
                                                        "actionClazz": "CreativeUnit",
                                                        "instanceLocalId": 1,
                                                        "method": "playMasterVideo",
                                                        "isStatic": false,
                                                        "args": {},
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }],
                                "name": "Image1",
                                "aspectRatioLocked": true,
                                "zIndex": 9,
                                "hiddenInBuilder": false,
                                "lockedInBuilder": false,
                                "imageSourceFeedFieldKey": null,
                                "fileLocalId": 41,
                                "fittingSize": "fit",
                                "imagePosition": "center",
                                "importedFromLayoutFile": false,
                                "clazz": "Picture",
                                "localId": 254,
                                "isAnimated": false
                            }, {
                                "layoutSpecificValues": [{
                                    "position": {
                                        "left": "3.38%",
                                        "top": "6.67%"
                                    },
                                    "size": {
                                        "width": "24.00%",
                                        "height": "42.67%"
                                    },
                                    "rotation": 0,
                                    "opacity": 1,
                                    "hidden": true
                                }],
                                "triggers": [{
                                    "localId": 274,
                                    "type": "tap",
                                    "parameters": {},
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 275,
                                                        "disabled": false,
                                                        "actionClazz": "ScreenObject",
                                                        "instanceLocalId": "264",
                                                        "method": "hide",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "264"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 525,
                                                        "disabled": false,
                                                        "actionClazz": "CreativeUnit",
                                                        "instanceLocalId": 1,
                                                        "method": "playMasterVideo",
                                                        "isStatic": false,
                                                        "args": {},
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }],
                                "name": "Image3",
                                "aspectRatioLocked": true,
                                "zIndex": 7,
                                "hiddenInBuilder": false,
                                "lockedInBuilder": false,
                                "imageSourceFeedFieldKey": null,
                                "fileLocalId": 39,
                                "fittingSize": "fit",
                                "imagePosition": "center",
                                "importedFromLayoutFile": false,
                                "clazz": "Picture",
                                "localId": 264,
                                "isAnimated": false
                            }, {
                                "layoutSpecificValues": [{
                                    "position": {
                                        "left": "43.00%",
                                        "top": "49.78%"
                                    },
                                    "size": {
                                        "width": "24.00%",
                                        "height": "42.67%"
                                    },
                                    "rotation": 0,
                                    "opacity": 1,
                                    "hidden": true
                                }],
                                "triggers": [{
                                    "localId": 286,
                                    "type": "tap",
                                    "parameters": {},
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 287,
                                                        "disabled": false,
                                                        "actionClazz": "ScreenObject",
                                                        "instanceLocalId": "276",
                                                        "method": "hide",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "276"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 526,
                                                        "disabled": false,
                                                        "actionClazz": "CreativeUnit",
                                                        "instanceLocalId": 1,
                                                        "method": "playMasterVideo",
                                                        "isStatic": false,
                                                        "args": {},
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }],
                                "name": "Image4",
                                "aspectRatioLocked": true,
                                "zIndex": 6,
                                "hiddenInBuilder": false,
                                "lockedInBuilder": false,
                                "imageSourceFeedFieldKey": null,
                                "fileLocalId": 40,
                                "fittingSize": "fit",
                                "imagePosition": "center",
                                "importedFromLayoutFile": false,
                                "clazz": "Picture",
                                "localId": 276,
                                "isAnimated": false
                            }, {
                                "layoutSpecificValues": [{
                                    "position": {
                                        "left": "71.63%",
                                        "top": "47.74%"
                                    },
                                    "size": {
                                        "width": "24.00%",
                                        "height": "42.67%"
                                    },
                                    "rotation": 0,
                                    "opacity": 1,
                                    "hidden": true
                                }],
                                "triggers": [{
                                    "localId": 523,
                                    "type": "tap",
                                    "parameters": {},
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 527,
                                                        "disabled": false,
                                                        "actionClazz": "ScreenObject",
                                                        "instanceLocalId": "288",
                                                        "method": "hide",
                                                        "isStatic": false,
                                                        "args": {
                                                            "target": "288"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 524,
                                                        "disabled": false,
                                                        "actionClazz": "CreativeUnit",
                                                        "instanceLocalId": 1,
                                                        "method": "playMasterVideo",
                                                        "isStatic": false,
                                                        "args": {},
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }],
                                "name": "Image2",
                                "aspectRatioLocked": true,
                                "zIndex": 8,
                                "hiddenInBuilder": false,
                                "lockedInBuilder": false,
                                "imageSourceFeedFieldKey": null,
                                "fileLocalId": 42,
                                "fittingSize": "fit",
                                "imagePosition": "center",
                                "importedFromLayoutFile": false,
                                "clazz": "Picture",
                                "localId": 288,
                                "isAnimated": false
                            }, {
                                "layoutSpecificValues": [{
                                    "position": {
                                        "top": "-18.44%",
                                        "hcenter": "0.00%"
                                    },
                                    "size": {
                                        "width": "150.00%",
                                        "height": "90.65%"
                                    },
                                    "rotation": 0,
                                    "opacity": 1,
                                    "hidden": true
                                }],
                                "triggers": [{
                                    "localId": 823,
                                    "type": "tap",
                                    "parameters": {},
                                    "onFire": {
                                        "clazz": "ActionInvocation",
                                        "actionClazz": "Kernel",
                                        "instanceLocalId": null,
                                        "method": "runInSequence",
                                        "isStatic": true,
                                        "args": {
                                            "actions": [{
                                                "clazz": "ActionInvocation",
                                                "actionClazz": "Kernel",
                                                "instanceLocalId": null,
                                                "method": "runInParallel",
                                                "isStatic": true,
                                                "args": {
                                                    "actions": [{
                                                        "clazz": "ActionInvocation",
                                                        "localId": 1168,
                                                        "disabled": false,
                                                        "actionClazz": "Kernel",
                                                        "instanceLocalId": null,
                                                        "method": "executeJavascript",
                                                        "isStatic": true,
                                                        "args": {
                                                            "scriptLocalId": 1169,
                                                            "name": "click / event / engagement"
                                                        },
                                                        "isAnimated": false
                                                    }, {
                                                        "clazz": "ActionInvocation",
                                                        "localId": 837,
                                                        "disabled": false,
                                                        "actionClazz": "CreativeUnit",
                                                        "instanceLocalId": 1,
                                                        "method": "goToURL",
                                                        "isStatic": false,
                                                        "args": {
                                                            "name": "Open Website 1",
                                                            "url": "http://localhost:8080/mock_website",
                                                            "reportLabel": "Open Website Button Tap"
                                                        },
                                                        "isAnimated": false
                                                    }]
                                                }
                                            }]
                                        }
                                    },
                                    "isCustom": false,
                                    "isAnimated": false
                                }],
                                "name": "CTA_Btn",
                                "aspectRatioLocked": true,
                                "zIndex": 10,
                                "hiddenInBuilder": false,
                                "lockedInBuilder": true,
                                "imageSourceFeedFieldKey": null,
                                "fileLocalId": 34,
                                "fittingSize": "fit",
                                "imagePosition": "center",
                                "importedFromLayoutFile": false,
                                "clazz": "Picture",
                                "localId": 360,
                                "isAnimated": true
                            }, {
                                "layoutSpecificValues": [{
                                    "position": {
                                        "left": "10.75%",
                                        "top": "-46.44%"
                                    },
                                    "size": {
                                        "width": "12.50%",
                                        "height": "22.22%"
                                    },
                                    "rotation": 0,
                                    "opacity": 1,
                                    "hidden": false
                                }],
                                "triggers": [],
                                "name": "tracking",
                                "aspectRatioLocked": false,
                                "zIndex": 11,
                                "hiddenInBuilder": false,
                                "lockedInBuilder": false,
                                "htmlContentFeedFieldKey": null,
                                "htmlScriptLocalId": 1039,
                                "javascriptScriptLocalId": 1040,
                                "cssScriptLocalId": 1041,
                                "clazz": "CustomCode",
                                "localId": 1038,
                                "fieldName": "tracking",
                                "isAnimated": false
                            }, {
                                "layoutSpecificValues": [{
                                    "position": {
                                        "left": "240.00%",
                                        "top": "0.50%"
                                    },
                                    "size": {
                                        "width": "184.00%",
                                        "height": "135.00%"
                                    },
                                    "rotation": 0,
                                    "opacity": 1,
                                    "hidden": false
                                }],
                                "triggers": [],
                                "name": "SOS_ONYX_TheWrap_040722_460x1080_Right_FM2",
                                "aspectRatioLocked": false,
                                "zIndex": 12,
                                "hiddenInBuilder": false,
                                "lockedInBuilder": false,
                                "imageSourceFeedFieldKey": null,
                                "fileLocalId": 1143,
                                "fittingSize": "fill",
                                "imagePosition": "center",
                                "importedFromLayoutFile": false,
                                "clazz": "Picture",
                                "localId": 1142,
                                "isAnimated": false
                            }],
                            "clazz": "Screen",
                            "guidelines": [{
                                "localId": 1053,
                                "orientation": "h",
                                "position": "-50.89%",
                                "isAnimated": false
                            }],
                            "isAnimated": false,
                            "immediatelyPlayedScenes": {
                                "appear": {
                                    "scenesLocalIds": []
                                },
                                "firstAppear": {
                                    "scenesLocalIds": []
                                }
                            },
                            "reachableScreensLocalIds": [],
                            "dynamicReachableScreenConditions": {}
                        },
                        "screens": [{
                            "hiddenInBuilder": false,
                            "lockedInBuilder": false,
                            "triggers": [{
                                "localId": 1125,
                                "type": "appear",
                                "onFire": {
                                    "clazz": "ActionInvocation",
                                    "actionClazz": "Kernel",
                                    "instanceLocalId": null,
                                    "method": "runInSequence",
                                    "isStatic": true,
                                    "args": {
                                        "actions": [{
                                            "clazz": "ActionInvocation",
                                            "actionClazz": "Kernel",
                                            "instanceLocalId": null,
                                            "method": "runInParallel",
                                            "isStatic": true,
                                            "args": {
                                                "actions": [{
                                                    "clazz": "ActionInvocation",
                                                    "localId": 1126,
                                                    "disabled": false,
                                                    "actionClazz": "Kernel",
                                                    "instanceLocalId": null,
                                                    "method": "executeJavascript",
                                                    "isStatic": true,
                                                    "args": {
                                                        "scriptLocalId": 1127,
                                                        "name": "inf_video_played"
                                                    },
                                                    "isAnimated": false
                                                }]
                                            }
                                        }]
                                    }
                                },
                                "isCustom": false,
                                "isAnimated": false
                            }],
                            "scenes": [],
                            "showOverflow": false,
                            "title": "Start",
                            "localId": 3,
                            "objects": [],
                            "clazz": "Screen",
                            "guidelines": [],
                            "isAnimated": false,
                            "immediatelyPlayedScenes": {
                                "appear": {
                                    "scenesLocalIds": []
                                },
                                "firstAppear": {
                                    "scenesLocalIds": []
                                }
                            },
                            "reachableScreensLocalIds": [],
                            "dynamicReachableScreenConditions": {}
                        }],
                        "backgroundColor": "rgba(255,255,255,0)",
                        "borderColor": "#000000",
                        "borderSize": 0,
                        "scale": 0.5,
                        "sizing": "responsive",
                        "defaultUnits": "%",
                        "layoutAspectRatiosLocked": {
                            "independent": false
                        },
                        "layoutCustomUnitSizes": {
                            "independent": true
                        },
                        "orientation": "independent",
                        "forceResponsivePositioningAndSizing": false,
                        "layouts": [{
                            "orientation": "independent",
                            "minSize": {
                                "width": 0,
                                "height": 0
                            },
                            "unitSize": null,
                            "designTimeSize": {
                                "width": 800,
                                "height": 450
                            },
                            "unitAlignment": {
                                "horizontal": "center",
                                "vertical": "center"
                            }
                        }],
                        "layoutsLocked": {
                            "portrait": true,
                            "landscape": true,
                            "independent": true
                        },
                        "feedImageOptimizationSettings": [],
                        "accessibility": {},
                        "closeButtonType": "image",
                        "isAnimated": false
                    }
                },
                "unitSizes": {
                    "banner": null
                },
                "files": [{
                    "clazz": "File",
                    "retina": false,
                    "retinaScaleFactor": 1,
                    "localId": 17,
                    "name": "video.mp4",
                    "blobHash": "904f09042139a0e47995201e72ab21f226461edfaffade463e3c79bcccd4b18d",
                    "quality": -1,
                    "autoResize": false,
                    "optimizationSettings": null,
                    "isAsset": true,
                    "meta": {
                        "size": 21003533,
                        "contentType": "video/mp4",
                        "duration": 22,
                        "hasAudio": true,
                        "hasVideo": true,
                        "container": {
                            "duration": 22.037333,
                            "bitrate": 7624709,
                            "size": 21003533
                        },
                        "width": 1920,
                        "height": 1080,
                        "framerate": 30,
                        "bitrate": 7614747,
                        "codec": "h264"
                    }
                }, {
                    "clazz": "File",
                    "retina": false,
                    "retinaScaleFactor": 1,
                    "localId": 33,
                    "name": "VideoPoster.jpg",
                    "blobHash": "447bbd1f4a0e71777f43f1b6595a313ea5b3e2ad899da6a72b698f441530d11f",
                    "quality": 85,
                    "autoResize": true,
                    "optimizationSettings": [{
                        "creativeUnitVariantId": "default",
                        "optimizedSize": 6457
                    }, {
                        "creativeUnitVariantId": 1,
                        "optimizedWidth": 960,
                        "optimizedHeight": 540,
                        "optimizedSize": 1867
                    }],
                    "isAsset": true,
                    "meta": {
                        "size": 16533,
                        "contentType": "image/jpeg",
                        "width": 1920,
                        "height": 1080
                    }
                }, {
                    "clazz": "File",
                    "retina": false,
                    "retinaScaleFactor": 1,
                    "localId": 34,
                    "name": "CTA_Btn.png",
                    "blobHash": "8209cb2962999d7b004559aaa86266ff912157280f0de9c2b3a44820ac02997f",
                    "quality": 256,
                    "autoResize": true,
                    "optimizationSettings": [{
                        "creativeUnitVariantId": "default",
                        "optimizedSize": 2623
                    }],
                    "isAsset": true,
                    "meta": {
                        "size": 2623,
                        "contentType": "image/png",
                        "width": 203,
                        "height": 69
                    }
                }, {
                    "clazz": "File",
                    "retina": false,
                    "retinaScaleFactor": 1,
                    "localId": 35,
                    "name": "Explore_Btn3.png",
                    "blobHash": "b7d203942fe2057f1a26a2b7feb53f0245a0599c844d401dc4ecb43c7cd0ee42",
                    "quality": 256,
                    "autoResize": true,
                    "optimizationSettings": [{
                        "creativeUnitVariantId": "default",
                        "optimizedSize": 2600
                    }],
                    "isAsset": true,
                    "meta": {
                        "size": 2601,
                        "contentType": "image/png",
                        "width": 127,
                        "height": 95
                    }
                }, {
                    "clazz": "File",
                    "retina": false,
                    "retinaScaleFactor": 1,
                    "localId": 36,
                    "name": "Explore_Btn2.png",
                    "blobHash": "6baacbd5732635b95a8e90b5182df13b9cc4c631563c153a5dd87cb130243112",
                    "quality": 256,
                    "autoResize": true,
                    "optimizationSettings": [{
                        "creativeUnitVariantId": "default",
                        "optimizedSize": 2664
                    }],
                    "isAsset": true,
                    "meta": {
                        "size": 2664,
                        "contentType": "image/png",
                        "width": 127,
                        "height": 95
                    }
                }, {
                    "clazz": "File",
                    "retina": false,
                    "retinaScaleFactor": 1,
                    "localId": 37,
                    "name": "Explore_Btn4.png",
                    "blobHash": "d0f4027d7d31814b155218da9c135b18ffdb07478e47f36a154b7f4904d9c7dc",
                    "quality": 256,
                    "autoResize": true,
                    "optimizationSettings": [{
                        "creativeUnitVariantId": "default",
                        "optimizedSize": 2698
                    }],
                    "isAsset": true,
                    "meta": {
                        "size": 2698,
                        "contentType": "image/png",
                        "width": 127,
                        "height": 95
                    }
                }, {
                    "clazz": "File",
                    "retina": false,
                    "retinaScaleFactor": 1,
                    "localId": 38,
                    "name": "Explore_Btn1.png",
                    "blobHash": "b971e41f7d18d654e5d68ff0ee0006a62ae2369f39c99a86bdbdad8b6e950f80",
                    "quality": 256,
                    "autoResize": true,
                    "optimizationSettings": [{
                        "creativeUnitVariantId": "default",
                        "optimizedSize": 2658
                    }],
                    "isAsset": true,
                    "meta": {
                        "size": 2659,
                        "contentType": "image/png",
                        "width": 127,
                        "height": 95
                    }
                }, {
                    "clazz": "File",
                    "retina": false,
                    "retinaScaleFactor": 1,
                    "localId": 39,
                    "name": "Image3.png",
                    "blobHash": "a39af63f69edd9ca7ec57d3e0e75e33c25b9c4805493a7cc89ef848d6a410e9b",
                    "quality": 256,
                    "autoResize": true,
                    "optimizationSettings": [{
                        "creativeUnitVariantId": "default",
                        "optimizedSize": 66199
                    }, {
                        "creativeUnitVariantId": 1,
                        "optimizedWidth": 240,
                        "optimizedHeight": 240,
                        "optimizedSize": 13235
                    }],
                    "isAsset": true,
                    "meta": {
                        "size": 66533,
                        "contentType": "image/png",
                        "width": 686,
                        "height": 686
                    }
                }, {
                    "clazz": "File",
                    "retina": false,
                    "retinaScaleFactor": 1,
                    "localId": 40,
                    "name": "Image4.png",
                    "blobHash": "500432adc5c877a93973a3a324e82230973466a53f26c359f463188362c8afa9",
                    "quality": 256,
                    "autoResize": true,
                    "optimizationSettings": [{
                        "creativeUnitVariantId": "default",
                        "optimizedSize": 67298
                    }, {
                        "creativeUnitVariantId": 1,
                        "optimizedWidth": 240,
                        "optimizedHeight": 240,
                        "optimizedSize": 12691
                    }],
                    "isAsset": true,
                    "meta": {
                        "size": 68593,
                        "contentType": "image/png",
                        "width": 686,
                        "height": 686
                    }
                }, {
                    "clazz": "File",
                    "retina": false,
                    "retinaScaleFactor": 1,
                    "localId": 41,
                    "name": "Image1.png",
                    "blobHash": "2f85577e85832f917c2fd819ef18775cc8acf6c72ac817fe5b35b15b573abdf9",
                    "quality": 256,
                    "autoResize": true,
                    "optimizationSettings": [{
                        "creativeUnitVariantId": "default",
                        "optimizedSize": 115411
                    }, {
                        "creativeUnitVariantId": 1,
                        "optimizedWidth": 240,
                        "optimizedHeight": 240,
                        "optimizedSize": 21621
                    }],
                    "isAsset": true,
                    "meta": {
                        "size": 115411,
                        "contentType": "image/png",
                        "width": 686,
                        "height": 686
                    }
                }, {
                    "clazz": "File",
                    "retina": false,
                    "retinaScaleFactor": 1,
                    "localId": 42,
                    "name": "Image2.png",
                    "blobHash": "fc729fafd38c0825da5153a6ef984343cd17254a5cc342299491d5f9bd73c87e",
                    "quality": 256,
                    "autoResize": true,
                    "optimizationSettings": [{
                        "creativeUnitVariantId": "default",
                        "optimizedSize": 71862
                    }, {
                        "creativeUnitVariantId": 1,
                        "optimizedWidth": 240,
                        "optimizedHeight": 240,
                        "optimizedSize": 13718
                    }],
                    "isAsset": true,
                    "meta": {
                        "size": 73121,
                        "contentType": "image/png",
                        "width": 686,
                        "height": 686
                    }
                }, {
                    "clazz": "File",
                    "retina": false,
                    "retinaScaleFactor": 1,
                    "localId": 1143,
                    "name": "SOS_ONYX_TheWrap_040722_460x1080_Right_FM2.jpg",
                    "blobHash": "b18137a59c7c2cc6c471a16698971fdfae7a0be885bef7d4a6056de1161a316c",
                    "quality": 85,
                    "autoResize": true,
                    "optimizationSettings": [{
                        "creativeUnitVariantId": "default",
                        "optimizedSize": 39158
                    }],
                    "isAsset": true,
                    "meta": {
                        "size": 176244,
                        "contentType": "image/jpeg",
                        "width": 460,
                        "height": 1080
                    }
                }],
                "fonts": [],
                "scripts": [{
                    "localId": 1039,
                    "language": "html",
                    "lockedInBuilder": false,
                    "code": ""
                }, {
                    "localId": 1040,
                    "language": "javascript",
                    "lockedInBuilder": false,
                    "code": "window.KSA = [\n    {\n        type: 'SET_PULSE',\n        value: 500\n    }];   \nconsole.log(\"SET PULSE : 500\");\n//load the superawesome SDK\nloadJS('https://ads.superawesome.tv/v2/ksa.js', function () {\n    console.log('Superawesome SDK loaded');\n    //define hotspot counter variables for first and secondary click\n    window.hotspot1Clicks = 0;\n    window.hotspot2Clicks = 0;\n    window.hotspot3Clicks = 0;\n    window.hotspot4Clicks = 0;\n    //listen for celtraLoaded prior to calling loaded event and setting campaign identifiers\n    creative.adapter.tagElement.addEventListener('celtraLoaded', function(){\n        window.KSA.push({\n            type: 'CUSTOM.inf_creative_loaded',\n            value: 1\n        });\n        console.log(\"Custom INF Creative Loaded\");\n        window.KSA.push({\n            type: 'SET_CREATIVE_ID',\n            value: 396645\n        });\n        console.log(\"Set Creative Id: 396645\");\n        window.KSA.push({\n            type: 'SET_PLACEMENT_ID',\n            value: 47560\n        });\n        console.log(\"Set Placement Id: 47560\");\n        window.KSA.push({\n            type: 'SET_LINEITEM_ID',\n            value: 186357\n        });\n        console.log(\"Set Line Item Id: 186357\");\n    } );\n    c();\n}, function () {\n    console.log('Superawesome SDK Request failed');\n    // Call 'c' function when the screen is ready to be shown.\n    // Until 'c' is called, creative will remain in loading state.\n    c();\n});"
                }, {
                    "localId": 1041,
                    "language": "css",
                    "lockedInBuilder": false,
                    "code": ""
                }, {
                    "localId": 1127,
                    "language": "javascript",
                    "lockedInBuilder": false,
                    "code": "//call video played event\nwindow.KSA.push({\n    type: 'CUSTOM.inf_video_played',\n    value: 1\n});\nconsole.log(\"Custom INF Video Played\");\n// Call 'c' when the action is considered \"completed\".\nc();"
                }, {
                    "localId": 1141,
                    "language": "javascript",
                    "lockedInBuilder": false,
                    "code": "// call 25% video completion event\nwindow.KSA.push({\n    type: 'CUSTOM.inf_video 25pc_video_completion',\n    value: 1\n});\nconsole.log(\"Custom INF Video 25pc Video Completion\");\n// Call 'c' when the action is considered \"completed\".\nc();"
                }, {
                    "localId": 1162,
                    "language": "javascript",
                    "lockedInBuilder": false,
                    "code": "// call 50% video completion event\n window.KSA.push({\n    type: 'CUSTOM.inf_video 50pc_video_completion',\n    value: 1\n});\nconsole.log(\"Custom INF Video 50pc Video Completion\");\n// Call 'c' when the action is considered \"completed\".\nc();"
                }, {
                    "localId": 1164,
                    "language": "javascript",
                    "lockedInBuilder": false,
                    "code": "// call 75% video completion event\nwindow.KSA.push({\n    type: 'CUSTOM.inf_video 75pc_video_completion',\n    value: 1\n});\nconsole.log(\"Custom INF Video 75pc Video Completion\");\n// Call 'c' when the action is considered \"completed\".\nc();"
                }, {
                    "localId": 1166,
                    "language": "javascript",
                    "lockedInBuilder": false,
                    "code": "//call 100% video completion event\n window.KSA.push({\n    type: 'CUSTOM.inf_video 100pc_video_completion',\n    value: 1\n});\nconsole.log(\"Custom INF Video 100pc Video Completion\");\n// Call 'c' when the action is considered \"completed\".\nc();"
                }, {
                    "localId": 1169,
                    "language": "javascript",
                    "lockedInBuilder": false,
                    "code": "//call click event\nwindow.KSA.push({\n    type: 'CLICK',\n    value: 1\n});\nconsole.log(\"CLICK Event\");\n//call CTA click \nwindow.KSA.push({\n    type: 'CUSTOM.eng_CTA_clicked\"',\n    value: 1\n});\nconsole.log(\"Custom ENG CTA Clicked\");\n//call engagement\nwindow.KSA.push({\n    type: 'CUSTOM.engagement',\n    value: 1\n});\nconsole.log(\"Custom Engagement on Click\");\n// Call 'c' when the action is considered \"completed\".\nc();"
                }, {
                    "localId": 1173,
                    "language": "javascript",
                    "lockedInBuilder": false,
                    "code": "//increment counter hotspot 1 variable\nwindow.hotspot1Clicks +=1;\n\n//check for first click or secondary click\nif (window.hotspot1Clicks === 1){\n    //track first click\n    window.KSA.push({\n        type: 'CUSTOM.eng_hotspot_1_first_clicked',\n        value: 1\n    });\n    console.log(\"Hotspot 1: First Clicked\");\n    //track first engagement\n    window.KSA.push({\n        type: 'CUSTOM.engagement',\n        value: 1\n    });\n    console.log(\"Hotspot 1: Custom Engagment\");\n} else {\n    //track secondary click\n    window.KSA.push({\n        type: 'CUSTOM.inf_hotspot_1_later_clicked\"',\n        value: 1\n    });\n    console.log(\"Hotspot 1: Later Clicked\");\n}\n\n// Call 'c' when the action is considered \"completed\".\nc();"
                }, {
                    "localId": 1174,
                    "language": "javascript",
                    "lockedInBuilder": false,
                    "code": "//increment counter hotspot 2 variable\nwindow.hotspot2Clicks +=1;\n\n//check for first click or secondary click\nif (window.hotspot2Clicks === 1){\n    //track first click\n    window.KSA.push({\n        type: 'CUSTOM.eng_hotspot_2_first_clicked',\n        value: 1\n    });\n    console.log(\"Hotspot 2: First Clicked\");\n    //track first engagement\n    window.KSA.push({\n        type: 'CUSTOM.engagement',\n        value: 1\n    });\n    console.log(\"Hotspot 2: Custom Engagement\");\n} else {\n    //track secondary click\n     window.KSA.push({\n        type: 'CUSTOM.inf_hotspot_2_later_clicked\"',\n        value: 1\n    });\n    console.log(\"Hotspot 2: Later Clicked\");\n}\n\n// Call 'c' when the action is considered \"completed\".\nc();"
                }, {
                    "localId": 1176,
                    "language": "javascript",
                    "lockedInBuilder": false,
                    "code": "//increment counter hotspot 3 variable\nwindow.hotspot3Clicks +=1;\n\n//check for first click or secondary click\nif (window.hotspot3Clicks === 1){\n    //track first click\n    window.KSA.push({\n        type: 'CUSTOM.eng_hotspot_3_first_clicked',\n        value: 1\n    });\n    console.log(\"Hotspot 3: First Clicked\");\n    //track first engagement\n    window.KSA.push({\n        type: 'CUSTOM.engagement',\n        value: 1\n    });\n    console.log(\"Hotspot 3: Custom Engagement\");\n} else {\n    //track secondary click\n    window.KSA.push({\n        type: 'CUSTOM.inf_hotspot_3_later_clicked\"',\n        value: 1\n    });\n    console.log(\"Hotspot 3: Later Clicked\");\n}\n// Call 'c' when the action is considered \"completed\".\nc();"
                }, {
                    "localId": 1178,
                    "language": "javascript",
                    "lockedInBuilder": false,
                    "code": "//increment counter hotspot4 variable\nwindow.hotspot4Clicks +=1;\n\n//check for first click or secondary click\nif (window.hotspot4Clicks === 1){\n    //track first click\n    window.KSA.push({\n        type: 'CUSTOM.eng_hotspot_4_first_clicked',\n        value: 1\n        });\n    console.log('Hotspot 4: First Clicked');\n    //track first engagement\n    window.KSA.push({\n        type: 'CUSTOM.engagement',\n        value: 1\n    });\n    console.log(\"Hotspot 4: Custom Engagment\");\n} else {\n    //track secondary click\n     window.KSA.push({\n        type: 'CUSTOM.inf_hotspot_4_later_clicked\"',\n        value: 1\n     });\n    console.log(\"Hotspot 4: Later Clicked\");\n}\n\n// Call 'c' when the action is considered \"completed\".\nc();"
                }],
                "version": 30,
                "hostedFiles": null,
                "googleMapsJavaScriptVersion": "3.52",
                "shareFacebookAppId": "1596868677011707",
                "videoTranscodingPresets": {
                    "mpeg1LQVideo": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/avi"
                        }
                    },
                    "mpeg1SHQVideo": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/avi"
                        }
                    },
                    "mpeg1InstaVideo": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/avi"
                        }
                    },
                    "aacAudio": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "audio/mp4",
                            "codecs": {
                                "audio": "mp4a.40"
                            }
                        }
                    },
                    "mpeg4HQ": {
                        "algoVersion": 3,
                        "mediaType": {
                            "mime": "video/mp4",
                            "codecs": {
                                "video": "avc1",
                                "audio": "mp4a.40"
                            }
                        }
                    },
                    "mpeg4HQPlus": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/mp4",
                            "codecs": {
                                "video": "avc1",
                                "audio": "mp4a.40"
                            }
                        }
                    },
                    "mpeg4HD": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/mp4",
                            "codecs": {
                                "video": "avc1",
                                "audio": "mp4a.40"
                            }
                        }
                    },
                    "webmHQ": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/webm",
                            "codecs": {
                                "video": "vp8",
                                "audio": "vorbis"
                            }
                        }
                    },
                    "webmHD": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/webm",
                            "codecs": {
                                "video": "vp8",
                                "audio": "vorbis"
                            }
                        }
                    },
                    "mpeg4InstaVideo": {
                        "algoVersion": 2,
                        "mediaType": {
                            "mime": "video/mp4",
                            "codecs": {
                                "video": "avc1"
                            }
                        }
                    },
                    "vp9_1080p": {
                        "algoVersion": 1,
                        "mediaType": null
                    },
                    "x264_1080p": {
                        "algoVersion": 1,
                        "mediaType": null
                    },
                    "vp9_720p": {
                        "algoVersion": 1,
                        "mediaType": null
                    },
                    "x264_videoAsset": {
                        "algoVersion": 1,
                        "mediaType": null
                    },
                    "x264_inline480p": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/mp4",
                            "codecs": {
                                "video": "avc1",
                                "audio": "mp4a.40"
                            }
                        }
                    },
                    "x264_inline720p": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/mp4",
                            "codecs": {
                                "video": "avc1",
                                "audio": "mp4a.40"
                            }
                        }
                    },
                    "vp9_inline720p": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/webm",
                            "codecs": {
                                "video": "vp9",
                                "audio": "vorbis"
                            }
                        }
                    },
                    "x265_served_480p": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/mp4",
                            "codecs": {
                                "video": "hvc1",
                                "audio": "mp4a.40"
                            }
                        }
                    },
                    "x265_served_1080p": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/mp4",
                            "codecs": {
                                "video": "hvc1",
                                "audio": "mp4a.40"
                            }
                        }
                    },
                    "vp9_served_480p": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/webm",
                            "codecs": {
                                "video": "vp9",
                                "audio": "opus"
                            }
                        }
                    },
                    "vp9_served_720p": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/webm",
                            "codecs": {
                                "video": "vp9",
                                "audio": "opus"
                            }
                        }
                    },
                    "vp9_served_1080p": {
                        "algoVersion": 1,
                        "mediaType": {
                            "mime": "video/webm",
                            "codecs": {
                                "video": "vp9",
                                "audio": "opus"
                            }
                        }
                    },
                    "custom": {
                        "algoVersion": 1,
                        "mediaType": null
                    }
                },
                "videoTranscodingGroups": {
                    "insta": ["mpeg4InstaVideo", "mpeg1SHQVideo", "mpeg1InstaVideo"],
                    "inline": ["vp9_served_480p", "vp9_served_720p", "x265_served_480p", "x265_served_1080p", "webmHQ", "mpeg4HQPlus", "mpeg1SHQVideo", "aacAudio", "mpeg4HQ", "mpeg1LQVideo"],
                    "builder": ["vp9_720p", "aacAudio"],
                    "vast": ["custom"],
                    "vpaid": ["vp9_served_720p", "vp9_served_1080p", "x265_served_1080p", "mpeg4HQPlus", "mpeg4HD", "webmHQ", "webmHD", "mpeg1SHQVideo", "aacAudio", "vp9_served_480p", "x265_served_480p", "mpeg4HQ"],
                    "snapchat": ["mpeg4HQPlus", "custom"],
                    "twitter": ["mpeg4HQPlus"],
                    "facebook": ["mpeg4HQPlus"],
                    "youTube": ["mpeg4HQPlus"],
                    "videoAsset": ["x264_videoAsset"],
                    "exportableBanner": ["x264_inline720p", "x264_inline480p", "vp9_inline720p"],
                    "fastLoadingAds": ["vp9_served_480p", "vp9_served_720p", "vp9_served_1080p", "x265_served_480p", "x265_served_1080p", "mpeg4HQ", "mpeg4HQPlus", "mpeg4HD", "webmHQ"]
                },
                "isMoatVideoEnabled": false,
                "firstAppStoreUrlForInmobi": null
            }, runtimeParams, trackingCenter, aggregatorTracking, experiments, adapter, urls, urlOpenedOverrideUrls, storeOpenedOverrideUrls, macros, urlOpenedUrlAppendage, clickThroughDestinationUrl, perf);
        })();
    };
})();

var AwesomeAds = (function(window) {
    window['awesomeads_host'] = "https://127.0.0.1:8443";
    window['cdnUrl'] = "https://127.0.0.1:8443";
    window['isHTTPS'] = "no";
    window['aa_sdkVersion'] = "ios_9.1.0";
    window['awesomeads_interstitial'] = "true";
    window['awesomeads_skippable'] = "false";
    window['awesomeads_smallclick'] = "false";
    window['awesomeads_muted'] = "false";
    window['awesomeads_show'] = "";
    window['awesomeads_domain'] = "";
    window['awesomeads_bundle'] = "com.superawesome.example";
    window['dor'] = "";
    window['load_start_time'] = performance.now();

    var AwesomeAdManager = function() {
        function i() {}
        var c = function() {
            function e() {}
            return e.sendAsyncGET = function(e, n, t) {
                var a = !!window.XDomainRequest,
                    r = new(a ? window.XDomainRequest : XMLHttpRequest);

                function o(e) {
                    var n;
                    return document.implementation && document.implementation.createDocument ? n = (new DOMParser).parseFromString(e, "text/xml") : window.ActiveXObject && (n = new ActiveXObject("Microsoft.XMLDOM")).loadXML(e), n
                }
                a ? (r.onload = function() {
                    var e = r.responseXML;
                    (e = (e = e || o(r.responseText)) || o(r.response)) ? n && n(e): t && t()
                }, r.open("GET", e, !0)) : (r.open("GET", e, !0), r.onreadystatechange = function() {
                    var e;
                    4 === r.readyState && 200 === r.status && ((e = (e = r.responseXML) || o(r.responseText || r.response)) ? n && n(e) : t && t())
                }, r.onerror = function(e) {
                    t && t(e)
                }), r.send()
            }, e.sendEvent = function(n) {
                var t, e = "jsonp_callback_" + this.createUUID();
                window[e] = function(e) {
                    t.parentNode.removeChild(t), console.info("[AA :: Info] Send Event to: " + n)
                }, (t = document.createElement("script")).src = n, document.getElementsByTagName("head")[0].appendChild(t)
            }, e.generateClickEvent = function(e) {
                var n = this.createUUID(),
                    t = window.awesomeads_host,
                    a = window.awesomeads_bundle ? "&bundle=" + window.awesomeads_bundle : "";
                return t + "/event_click" + ("?placement=" + e.placement_id) + ("&rnd=" + n) + ("&line_item=" + e.line_item_id) + ("&creative=" + e.creative.id) + ("&sdkVersion=" + window.aa_sdkVersion) + a
            }, e.generateCPIClick = function(e) {
                var n = "utm_source=",
                    n = (n = (n = (n = (n = (n = (n += "" + window.awesomeads_host.indexOf("staging") != -1 ? 1 : 0) + ("&utm_campaign=" + e.campaign_id)) + ("&utm_term=" + e.line_item_id)) + ("&utm_content=" + e.creative.id)) + ("&utm_medium=" + e.placement_id)).replace(new RegExp("&", "g"), "%26")).replace(new RegExp("=", "g"), "%3D");
                return e.creative.click_url + "&referrer=" + n
            }, e.getBumperSettings = function() {
                var e = "";
                try {
                    WebSDK && WebSDK.bumperSettings && (e = "&" + WebSDK.bumperSettings.getBumperDataAsParam())
                } catch (e) {}
                return e
            }, e.createUUID = function() {
                var t = (new Date).getTime();
                return "xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx".replace(/[xy]/g, function(e) {
                    var n = (t + 16 * Math.random()) % 16 | 0;
                    return t = Math.floor(t / 16), ("x" === e ? n : 3 & n | 8).toString(16)
                })
            }, e
        }.call();
        return i.createBasicQueryString = function(e, n) {
            return "?rand=" + c.createUUID() + "&callback=" + e + "&sdkVersion=" + n.aa_sdkVersion + "&bundle=" + n.awesomeads_bundle
        }, i.get_ad = function(e, n, t) {
            var a, r = "jsonp_callback_" + c.createUUID(),
                o = window.location !== window.parent.location ? document.referrer : document.location.href;
            window[r] = function(e) {
                a.parentNode.removeChild(a), void 0 !== e.error ? t(e) : t(null, e)
            }, r = i.createBasicQueryString(r, window), ("in" === window.dor || -1 < o.indexOf("dor=in")) && (r += "&dor=in"), n.test && (r += "&test=true"), n.keywords && (r += "&keywords=" + n.keywords), n.preload && (r += "&preload=" + n.preload), (a = document.createElement("script")).src = window.awesomeads_host + "/ad/" + e + r, document.getElementsByTagName("head")[0].appendChild(a)
        }, i.send_event = function(e, n) {
            var t, a = "jsonp_callback_" + c.createUUID(),
                r = (window[a] = function(e) {
                    t.parentNode.removeChild(t), n && (void 0 !== e.error ? n(e) : n(null, e))
                }, "event");
            "impressionDownloaded" === e.type && (r = "impression"), t = document.createElement("script"), r = window.awesomeads_host + "/" + r, r += "?data=" + encodeURIComponent(JSON.stringify(e)) + "&callback=" + a + "&sdkVersion=" + window.aa_sdkVersion + "&bundle=" + window.awesomeads_bundle, t.src = r, document.getElementsByTagName("head")[0].appendChild(t)
        }, i.track_render_time = function(e, n) {
            this.track_performance("sa.ad.sdk.performance.render.time.web", e, n)
        }, i.track_load_time = function(e, n) {
            this.track_performance("sa.ad.sdk.performance.load.time.web", e, n)
        }, i.track_performance = function(e, n, t) {
            var a = this.calculate_performance_delta();
            this.track_performance_data(n, {
                value: a,
                metricName: e,
                metricType: "gauge"
            }, t)
        }, i.calculate_performance_delta = function() {
            return performance.now() - window.load_start_time
        }, i.track_performance_data = function(e, n, t) {
            var a, r = document.createElement("script");
            n && (a = window.awesomeads_host + "/sdk/performance", a = (a = (a += "?value=" + n.value) + "&metricName=" + n.metricName) + "&metricType=" + n.metricType), t && (a += "&metricTags=" + encodeURIComponent(JSON.stringify(t))), r.src = a, e.appendChild(r)
        }, i.send_moat = function(e, n) {
            var t = n.moat_url + "/",
                n = (t = (t = (t = (t = (t = (t = (t = (t += n.identifier + "/moatad.js") + ("?moatClientLevel1=" + n.moatClientLevel1)) + ("&moatClientLevel2=" + n.moatClientLevel2)) + ("&moatClientLevel3=" + n.moatClientLevel3)) + ("&moatClientLevel4=" + n.moatClientLevel4)) + ("&moatClientSlicer1=" + n.moatClientSlicer1)) + ("&moatClientSlicer2=" + n.moatClientSlicer2)) + ("&moatClientSlicer3=" + n.moatClientSlicer3), document.createElement("script"));
            n.src = t, e.appendChild(n)
        }, i.send_ias = function(e, n) {
            var t = n.ias_url,
                n = (t = (t = (t = (t = (t = (t = (t += "?anId=" + n.anId) + ("&chanId=" + n.chanId)) + ("&campId=" + n.campId)) + ("&pubId=" + n.pubId)) + ("&placementId=" + n.placementId)) + ("&pubOrder=" + n.pubOrder)) + ("&pubCreative=" + n.pubCreative), document.createElement("script")),
                a = document.createElement("script");
            n.src = "mraid.js", a.src = t, e.appendChild(n), e.appendChild(a)
        }, i
    }.call();
    var AwesomeDisplay = function() {
        function a(t, e, n, i, r, a) {
            a = a || {};
            var o = this,
                s = (o.id = "aa_display_ad_" + ~~(1e7 * Math.random()) + "-" + t, o.click_tracking_url = "", o.isInterstitial = !1, o.isInterstitial = null != i ? i : "true" === p, o.muted = a.muted || !1, o.autoplaying = a.autoplaying || !1, o.startdelay = a.startdelay || 0, o.playbackmethod = a.playbackmethod || [2, 3], o.skippable = a.skippable || !1, o.openRtbPartnerId = a.openRtbPartnerId || void 0, window.aa_sdkVersion ? window.aa_sdkVersion.toLowerCase() : "unknown");
            if (o.isMobileSdk = 0 < d.filter(function(e) {
                    return s.startsWith(e)
                }).length, o.loaded = !1, o.test_ads = !1, o.viewable_impression_registered = !1, o.moat_event_registered = !1, o.on_load_callbacks = [], o.onEmptyCallback = null, o.keywords = [], o.add_keyword("websdk"), n && n.length)
                for (var l = 0; l < n.length; l++) o.add_keyword(n[l]);
            o.placement_id = t, o.element = e || document.createElement("div"), o.element.id = o.id, i = "false", r && r.creative && r.creative.hasOwnProperty("bumper") && (i = r.creative.bumper), o.element.setAttribute("bumper", i), r ? r.creative && o.init(r) : setTimeout(function() {
                var e = {};
                o.test_ads && (e.test = !0), o.get_keywords_string() && (e.keywords = o.get_keywords_string()), AwesomeAdManager.get_ad(t, e, function(e, t) {
                    e ? console.error(e) : t.creative ? o.init(t) : null != o.onEmptyCallback && o.onEmptyCallback()
                })
            }, 1)
        }
        var e, t, c = function() {
                function e() {}
                return e.sendAsyncGET = function(e, t, n) {
                    var i = !!window.XDomainRequest,
                        r = new(i ? window.XDomainRequest : XMLHttpRequest);

                    function a(e) {
                        var t;
                        return document.implementation && document.implementation.createDocument ? t = (new DOMParser).parseFromString(e, "text/xml") : window.ActiveXObject && (t = new ActiveXObject("Microsoft.XMLDOM")).loadXML(e), t
                    }
                    i ? (r.onload = function() {
                        var e = r.responseXML;
                        (e = (e = e || a(r.responseText)) || a(r.response)) ? t && t(e): n && n()
                    }, r.open("GET", e, !0)) : (r.open("GET", e, !0), r.onreadystatechange = function() {
                        var e;
                        4 === r.readyState && 200 === r.status && ((e = (e = r.responseXML) || a(r.responseText || r.response)) ? t && t(e) : n && n())
                    }, r.onerror = function(e) {
                        n && n(e)
                    }), r.send()
                }, e.sendEvent = function(t) {
                    var n, e = "jsonp_callback_" + this.createUUID();
                    window[e] = function(e) {
                        n.parentNode.removeChild(n), console.info("[AA :: Info] Send Event to: " + t)
                    }, (n = document.createElement("script")).src = t, document.getElementsByTagName("head")[0].appendChild(n)
                }, e.generateClickEvent = function(e) {
                    var t = this.createUUID(),
                        n = window.awesomeads_host,
                        i = window.awesomeads_bundle ? "&bundle=" + window.awesomeads_bundle : "";
                    return n + "/event_click" + ("?placement=" + e.placement_id) + ("&rnd=" + t) + ("&line_item=" + e.line_item_id) + ("&creative=" + e.creative.id) + ("&sdkVersion=" + window.aa_sdkVersion) + i
                }, e.generateCPIClick = function(e) {
                    var t = "utm_source=",
                        t = (t = (t = (t = (t = (t = (t += "" + window.awesomeads_host.indexOf("staging") != -1 ? 1 : 0) + ("&utm_campaign=" + e.campaign_id)) + ("&utm_term=" + e.line_item_id)) + ("&utm_content=" + e.creative.id)) + ("&utm_medium=" + e.placement_id)).replace(new RegExp("&", "g"), "%26")).replace(new RegExp("=", "g"), "%3D");
                    return e.creative.click_url + "&referrer=" + t
                }, e.getBumperSettings = function() {
                    var e = "";
                    try {
                        WebSDK && WebSDK.bumperSettings && (e = "&" + WebSDK.bumperSettings.getBumperDataAsParam())
                    } catch (e) {}
                    return e
                }, e.createUUID = function() {
                    var n = (new Date).getTime();
                    return "xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx".replace(/[xy]/g, function(e) {
                        var t = (n + 16 * Math.random()) % 16 | 0;
                        return n = Math.floor(n / 16), ("x" === e ? t : 3 & t | 8).toString(16)
                    })
                }, e
            }.call(),
            d = (e = this, t = function() {
                return i = [function(e, t, n) {
                    "use strict";
                    var n = n(1),
                        n = (n = n) && n.__esModule ? n : {
                            default: n
                        };
                    e.exports = n.default
                }, function(e, t, n) {
                    "use strict";

                    function i(e) {
                        if (e && e.__esModule) return e;
                        var t = {};
                        if (null != e)
                            for (var n in e) Object.prototype.hasOwnProperty.call(e, n) && (t[n] = e[n]);
                        return t.default = e, t
                    }
                    t.__esModule = !0;
                    var r, l = i(n(2)),
                        a = i(n(3)),
                        o = n(6),
                        d = (o = o) && o.__esModule ? o : {
                            default: o
                        },
                        s = n(5),
                        c = {
                            comment: /^<!--/,
                            endTag: /^<\//,
                            atomicTag: /^<\s*(script|style|noscript|iframe|textarea)[\s\/>]/i,
                            startTag: /^</,
                            chars: /^[^<]/
                        },
                        p = (u.prototype.append = function(e) {
                            this.stream += e
                        }, u.prototype.prepend = function(e) {
                            this.stream = e + this.stream
                        }, u.prototype._readTokenImpl = function() {
                            var e = this._peekTokenImpl();
                            if (e) return this.stream = this.stream.slice(e.length), e
                        }, u.prototype._peekTokenImpl = function() {
                            for (var e in c)
                                if (c.hasOwnProperty(e) && c[e].test(this.stream)) {
                                    e = a[e](this.stream);
                                    if (e) return "startTag" === e.type && /script|style/i.test(e.tagName) ? null : (e.text = this.stream.substr(0, e.length), e)
                                }
                        }, u.prototype.peekToken = function() {
                            return this._peekToken()
                        }, u.prototype.readToken = function() {
                            return this._readToken()
                        }, u.prototype.readTokens = function(e) {
                            for (var t; t = this.readToken();)
                                if (e[t.type] && !1 === e[t.type](t)) return
                        }, u.prototype.clear = function() {
                            var e = this.stream;
                            return this.stream = "", e
                        }, u.prototype.rest = function() {
                            return this.stream
                        }, u);

                    function u() {
                        var e = this,
                            t = 0 < arguments.length && void 0 !== arguments[0] ? arguments[0] : "",
                            n = 1 < arguments.length && void 0 !== arguments[1] ? arguments[1] : {},
                            i = this,
                            r = u;
                        if (!(i instanceof r)) throw new TypeError("Cannot call a class as a function");
                        this.stream = t;
                        var a, o = !1,
                            s = {};
                        for (a in l) l.hasOwnProperty(a) && (n.autoFix && (s[a + "Fix"] = !0), o = o || s[a + "Fix"]);
                        o ? (this._readToken = (0, d.default)(this, s, function() {
                            return e._readTokenImpl()
                        }), this._peekToken = (0, d.default)(this, s, function() {
                            return e._peekTokenImpl()
                        })) : (this._readToken = this._readTokenImpl, this._peekToken = this._peekTokenImpl)
                    }
                    for (r in (t.default = p).tokenToString = function(e) {
                            return e.toString()
                        }, p.escapeAttributes = function(e) {
                            var t, n = {};
                            for (t in e) e.hasOwnProperty(t) && (n[t] = (0, s.escapeQuotes)(e[t], null));
                            return n
                        }, p.supports = l) l.hasOwnProperty(r) && (p.browserHasFlaw = p.browserHasFlaw || !l[r] && r)
                }, function(e, t) {
                    "use strict";
                    var n = !(t.__esModule = !0),
                        i = !1,
                        r = window.document.createElement("div");
                    try {
                        var a = "<P><I></P></I>";
                        r.innerHTML = a, t.tagSoup = n = r.innerHTML !== a
                    } catch (e) {
                        t.tagSoup = n = !1
                    }
                    try {
                        r.innerHTML = "<P><i><P></P></i></P>", t.selfClose = i = 2 === r.childNodes.length
                    } catch (e) {
                        t.selfClose = i = !1
                    }
                    r = null, t.tagSoup = n, t.selfClose = i
                }, function(e, t, n) {
                    "use strict";

                    function i(e) {
                        if (-1 !== e.indexOf(">")) {
                            var n, i, r, e = e.match(o.startTag);
                            if (e) return n = {}, i = {}, r = e[2], e[2].replace(o.attr, function(e, t) {
                                arguments[2] || arguments[3] || arguments[4] || arguments[5] ? arguments[5] ? (n[arguments[5]] = "", i[arguments[5]] = !0) : n[t] = arguments[2] || arguments[3] || arguments[4] || o.fillAttr.test(t) && t || "" : n[t] = "", r = r.replace(e, "")
                            }), new a.StartTagToken(e[1], e[0].length, n, i, !!e[3], r.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, ""))
                        }
                    }
                    t.__esModule = !0, t.comment = function(e) {
                        var t = e.indexOf("--\x3e");
                        if (0 <= t) return new a.CommentToken(e.substr(4, t - 1), t + 3)
                    }, t.chars = function(e) {
                        var t = e.indexOf("<");
                        return new a.CharsToken(0 <= t ? t : e.length)
                    }, t.startTag = i, t.atomicTag = function(e) {
                        var t = i(e);
                        if (t) {
                            e = e.slice(t.length);
                            if (e.match(new RegExp("</\\s*" + t.tagName + "\\s*>", "i"))) {
                                e = e.match(new RegExp("([\\s\\S]*?)</\\s*" + t.tagName + "\\s*>", "i"));
                                if (e) return new a.AtomicTagToken(t.tagName, e[0].length + t.length, t.attrs, t.booleanAttrs, e[1])
                            }
                        }
                    }, t.endTag = function(e) {
                        if (e = e.match(o.endTag)) return new a.EndTagToken(e[1], e[0].length)
                    };
                    var a = n(4),
                        o = {
                            startTag: /^<([\-A-Za-z0-9_!:]+)((?:\s+[\w\-]+(?:\s*=?\s*(?:(?:"[^"]*")|(?:'[^']*')|[^>\s]+))?)*)\s*(\/?)>/,
                            endTag: /^<\/([\-A-Za-z0-9_:]+)[^>]*>/,
                            attr: /(?:([\-A-Za-z0-9_]+)\s*=\s*(?:(?:"((?:\\.|[^"])*)")|(?:'((?:\\.|[^'])*)')|([^>\s]+)))|(?:([\-A-Za-z0-9_]+)(\s|$)+)/g,
                            fillAttr: /^(checked|compact|declare|defer|disabled|ismap|multiple|nohref|noresize|noshade|nowrap|readonly|selected)$/i
                        }
                }, function(e, t, n) {
                    "use strict";

                    function o(e, t) {
                        if (!(e instanceof t)) throw new TypeError("Cannot call a class as a function")
                    }
                    t.__esModule = !0, t.EndTagToken = t.AtomicTagToken = t.StartTagToken = t.TagToken = t.CharsToken = t.CommentToken = t.Token = void 0;
                    var a = n(5),
                        i = (t.Token = function e(t, n) {
                            o(this, e), this.type = t, this.length = n, this.text = ""
                        }, t.CommentToken = (l.prototype.toString = function() {
                            return "\x3c!--" + this.content
                        }, l), t.CharsToken = (r.prototype.toString = function() {
                            return this.text
                        }, r), t.TagToken = (s.formatTag = function(e) {
                            var t, n, i = 1 < arguments.length && void 0 !== arguments[1] ? arguments[1] : null,
                                r = "<" + e.tagName;
                            for (t in e.attrs) e.attrs.hasOwnProperty(t) && (r += " " + t, n = e.attrs[t], void 0 !== e.booleanAttrs && void 0 !== e.booleanAttrs[t] || (r += '="' + (0, a.escapeQuotes)(n) + '"'));
                            return e.rest && (r += " " + e.rest), r += e.unary && !e.html5Unary ? "/>" : ">", null != i && (r += i + "</" + e.tagName + ">"), r
                        }, s));

                    function s(e, t, n, i, r) {
                        o(this, s), this.type = e, this.length = n, this.text = "", this.tagName = t, this.attrs = i, this.booleanAttrs = r, this.unary = !1, this.html5Unary = !1
                    }

                    function r(e) {
                        o(this, r), this.type = "chars", this.length = e, this.text = ""
                    }

                    function l(e, t) {
                        o(this, l), this.type = "comment", this.length = t || (e ? e.length : 0), this.text = "", this.content = e
                    }

                    function d(e, t) {
                        o(this, d), this.type = "endTag", this.length = t, this.text = "", this.tagName = e
                    }

                    function c(e, t, n, i, r) {
                        o(this, c), this.type = "atomicTag", this.length = t, this.text = "", this.tagName = e, this.attrs = n, this.booleanAttrs = i, this.unary = !1, this.html5Unary = !1, this.content = r
                    }

                    function p(e, t, n, i, r, a) {
                        o(this, p), this.type = "startTag", this.length = t, this.text = "", this.tagName = e, this.attrs = n, this.booleanAttrs = i, this.html5Unary = !1, this.unary = r, this.rest = a
                    }
                    t.StartTagToken = (p.prototype.toString = function() {
                        return i.formatTag(this)
                    }, p), t.AtomicTagToken = (c.prototype.toString = function() {
                        return i.formatTag(this, this.content)
                    }, c), t.EndTagToken = (d.prototype.toString = function() {
                        return "</" + this.tagName + ">"
                    }, d)
                }, function(e, t) {
                    "use strict";
                    t.__esModule = !0, t.escapeQuotes = function(e) {
                        var t = 1 < arguments.length && void 0 !== arguments[1] ? arguments[1] : "";
                        return e ? e.replace(/([^"]*)"/g, function(e, t) {
                            return /\\/.test(t) ? t + '"' : t + '\\"'
                        }) : t
                    }
                }, function(e, t) {
                    "use strict";

                    function l(e) {
                        return e && "startTag" === e.type && (e.unary = n.test(e.tagName) || e.unary, e.html5Unary = !/\/>$/.test(e.text)), e
                    }

                    function d(e, t) {
                        t = t.pop();
                        e.prepend("</" + t.tagName + ">")
                    }
                    t.__esModule = !0, t.default = function(i, n, r) {
                        function a() {
                            var e, t, n;
                            t = r, n = (e = i).stream, t = l(r()), e.stream = n, t && s[t.type] && s[t.type](t)
                        }(e = []).last = function() {
                            return this[this.length - 1]
                        }, e.lastTagNameEq = function(e) {
                            var t = this.last();
                            return t && t.tagName && t.tagName.toUpperCase() === e.toUpperCase()
                        }, e.containsTagName = function(e) {
                            for (var t, n = 0; t = this[n]; n++)
                                if (t.tagName === e) return !0;
                            return !1
                        };
                        var e, o = e,
                            s = {
                                startTag: function(e) {
                                    var t = e.tagName;
                                    "TR" === t.toUpperCase() && o.lastTagNameEq("TABLE") ? (i.prepend("<TBODY>"), a()) : n.selfCloseFix && c.test(t) && o.containsTagName(t) ? o.lastTagNameEq(t) ? d(i, o) : (i.prepend("</" + e.tagName + ">"), a()) : e.unary || o.push(e)
                                },
                                endTag: function(e) {
                                    o.last() ? n.tagSoupFix && !o.lastTagNameEq(e.tagName) ? d(i, o) : o.pop() : n.tagSoupFix && (r(), a())
                                }
                            };
                        return function() {
                            return a(), l(r())
                        }
                    };
                    var n = /^(AREA|BASE|BASEFONT|BR|COL|FRAME|HR|IMG|INPUT|ISINDEX|LINK|META|PARAM|EMBED)$/i,
                        c = /^(COLGROUP|DD|DT|LI|OPTIONS|P|TD|TFOOT|TH|THEAD|TR)$/i
                }], r = {}, n.m = i, n.c = r, n.p = "", n(0);

                function n(e) {
                    var t;
                    return (r[e] || (t = r[e] = {
                        exports: {},
                        id: e,
                        loaded: !1
                    }, i[e].call(t.exports, t, t.exports, n), t.loaded = !0, t)).exports
                }
                var i, r
            }, "object" == typeof exports && "object" == typeof module ? module.exports = t() : "function" == typeof define && define.amd ? define([], t) : "object" == typeof exports ? exports.Prescribe = t() : e.Prescribe = t(), e = this, t = function() {
                return i = [function(e, t, n) {
                    "use strict";
                    var n = n(1),
                        n = (n = n) && n.__esModule ? n : {
                            default: n
                        };
                    e.exports = n.default
                }, function(e, t, n) {
                    "use strict";

                    function s() {}

                    function l() {
                        var e, t = o.shift();
                        t && ((e = r.last(t)).afterDequeue(), t.stream = function(e, t, i) {
                            function r(e) {
                                e = i.beforeWrite(e), h.write(e), i.afterWrite(e)
                            }(h = new p.default(e, i)).id = u++, h.name = i.name || h.id, d.streams[h.name] = h;
                            var n = e.ownerDocument,
                                a = {
                                    close: n.close,
                                    open: n.open,
                                    write: n.write,
                                    writeln: n.writeln
                                },
                                o = (c(n, {
                                    close: s,
                                    open: s,
                                    write: function() {
                                        for (var e = arguments.length, t = Array(e), n = 0; n < e; n++) t[n] = arguments[n];
                                        return r(t.join(""))
                                    },
                                    writeln: function() {
                                        for (var e = arguments.length, t = Array(e), n = 0; n < e; n++) t[n] = arguments[n];
                                        return r(t.join("") + "\n")
                                    }
                                }), h.win.onerror || s);
                            return h.win.onerror = function(e, t, n) {
                                i.error({
                                    msg: e + " - " + t + ": " + n
                                }), o.apply(h.win, [e, t, n])
                            }, h.write(t, function() {
                                c(n, a), h.win.onerror = o, i.done(), h = null, l()
                            }), h
                        }.apply(void 0, t), e.afterStreamStart())
                    }

                    function d(e, t, n) {
                        if (r.isFunction(n)) n = {
                            done: n
                        };
                        else if ("clear" === n) return o = [], h = null, void(u = 0);
                        n = r.defaults(n, a);
                        var i = [e = /^#/.test(e) ? window.document.getElementById(e.substr(1)) : e.jquery ? e[0] : e, t, n];
                        return e.postscribe = {
                            cancel: function() {
                                i.stream ? i.stream.abort() : i[1] = s
                            }
                        }, n.beforeEnqueue(i), o.push(i), h || l(), e.postscribe
                    }
                    t.__esModule = !0;
                    var c = Object.assign || function(e) {
                            for (var t = 1; t < arguments.length; t++) {
                                var n, i = arguments[t];
                                for (n in i) Object.prototype.hasOwnProperty.call(i, n) && (e[n] = i[n])
                            }
                            return e
                        },
                        t = (t.default = d, n(2)),
                        p = (t = t) && t.__esModule ? t : {
                            default: t
                        },
                        r = function(e) {
                            if (e && e.__esModule) return e;
                            var t = {};
                            if (null != e)
                                for (var n in e) Object.prototype.hasOwnProperty.call(e, n) && (t[n] = e[n]);
                            return t.default = e, t
                        }(n(4)),
                        a = {
                            afterAsync: s,
                            afterDequeue: s,
                            afterStreamStart: s,
                            afterWrite: s,
                            autoFix: !0,
                            beforeEnqueue: s,
                            beforeWriteToken: function(e) {
                                return e
                            },
                            beforeWrite: function(e) {
                                return e
                            },
                            done: s,
                            error: function(e) {
                                throw new Error(e.msg)
                            },
                            releaseAsync: !1
                        },
                        u = 0,
                        o = [],
                        h = null;
                    c(d, {
                        streams: {},
                        queue: o,
                        WriteStream: p.default
                    })
                }, function(e, t, n) {
                    "use strict";

                    function i(e, t) {
                        e = e.getAttribute(c + t);
                        return l.existy(e) ? String(e) : e
                    }

                    function r(e, t, n) {
                        n = 2 < arguments.length && void 0 !== n ? n : null, t = c + t;
                        l.existy(n) && "" !== n ? e.setAttribute(t, n) : e.removeAttribute(t)
                    }
                    t.__esModule = !0;
                    var s = Object.assign || function(e) {
                            for (var t = 1; t < arguments.length; t++) {
                                var n, i = arguments[t];
                                for (n in i) Object.prototype.hasOwnProperty.call(i, n) && (e[n] = i[n])
                            }
                            return e
                        },
                        a = n(3),
                        o = (a = a) && a.__esModule ? a : {
                            default: a
                        },
                        l = function(e) {
                            if (e && e.__esModule) return e;
                            var t = {};
                            if (null != e)
                                for (var n in e) Object.prototype.hasOwnProperty.call(e, n) && (t[n] = e[n]);
                            return t.default = e, t
                        }(n(4)),
                        c = "data-ps-",
                        p = "ps-style",
                        u = "ps-script";

                    function d(e) {
                        var t = 1 < arguments.length && void 0 !== arguments[1] ? arguments[1] : {},
                            n = this,
                            i = d;
                        if (!(n instanceof i)) throw new TypeError("Cannot call a class as a function");
                        this.root = e, this.options = t, this.doc = e.ownerDocument, this.win = this.doc.defaultView || this.doc.parentWindow, this.parser = new o.default("", {
                            autoFix: t.autoFix
                        }), this.actuals = [e], this.proxyHistory = "", this.proxyRoot = this.doc.createElement(e.nodeName), this.scriptStack = [], this.writeQueue = [], r(this.proxyRoot, "proxyof", 0)
                    }
                    d.prototype.write = function() {
                        var e;
                        for ((e = this.writeQueue).push.apply(e, arguments); !this.deferredRemote && this.writeQueue.length;) {
                            var t = this.writeQueue.shift();
                            l.isFunction(t) ? this._callFunction(t) : this._writeImpl(t)
                        }
                    }, d.prototype._callFunction = function(e) {
                        var t = {
                            type: "function",
                            value: e.name || e.toString()
                        };
                        this._onScriptStart(t), e.call(this.win, this.doc), this._onScriptDone(t)
                    }, d.prototype._writeImpl = function(e) {
                        this.parser.append(e);
                        for (var t = void 0, n = void 0, i = void 0, r = [];
                            (t = this.parser.readToken()) && !(n = l.isScript(t)) && !(i = l.isStyle(t));)(t = this.options.beforeWriteToken(t)) && r.push(t);
                        0 < r.length && this._writeStaticTokens(r), n && this._handleScriptToken(t), i && this._handleStyleToken(t)
                    }, d.prototype._writeStaticTokens = function(e) {
                        e = this._buildChunk(e);
                        return e.actual ? (e.html = this.proxyHistory + e.actual, this.proxyHistory += e.proxy, this.proxyRoot.innerHTML = e.html, this._walkChunk(), e) : null
                    }, d.prototype._buildChunk = function(e) {
                        for (var t = this.actuals.length, n = [], i = [], r = [], a = e.length, o = 0; o < a; o++) {
                            var s, l = e[o],
                                d = l.toString();
                            n.push(d), l.attrs ? /^noscript$/i.test(l.tagName) || (s = t++, i.push(d.replace(/(\/?>)/, " " + c + "id=" + s + " $1")), l.attrs.id !== u && l.attrs.id !== p && r.push("atomicTag" === l.type ? "" : "<" + l.tagName + " " + c + "proxyof=" + s + (l.unary ? " />" : ">"))) : (i.push(d), r.push("endTag" === l.type ? d : ""))
                        }
                        return {
                            tokens: e,
                            raw: n.join(""),
                            actual: i.join(""),
                            proxy: r.join("")
                        }
                    }, d.prototype._walkChunk = function() {
                        for (var e, t = [this.proxyRoot]; l.existy(e = t.shift());) {
                            var n = 1 === e.nodeType;
                            !(n && i(e, "proxyof")) && (n && r(this.actuals[i(e, "id")] = e, "id"), n = e.parentNode && i(e.parentNode, "proxyof")) && this.actuals[n].appendChild(e), t.unshift.apply(t, l.toArray(e.childNodes))
                        }
                    }, d.prototype._handleScriptToken = function(e) {
                        var t = this,
                            n = this.parser.clear();
                        n && this.writeQueue.unshift(n), e.src = e.attrs.src || e.attrs.SRC, (e = this.options.beforeWriteToken(e)) && (e.src && this.scriptStack.length ? this.deferredRemote = e : this._onScriptStart(e), this._writeScriptToken(e, function() {
                            t._onScriptDone(e)
                        }))
                    }, d.prototype._handleStyleToken = function(e) {
                        var t = this.parser.clear();
                        t && this.writeQueue.unshift(t), e.type = e.attrs.type || e.attrs.TYPE || "text/css", (e = this.options.beforeWriteToken(e)) && this._writeStyleToken(e), t && this.write()
                    }, d.prototype._writeStyleToken = function(e) {
                        var t = this._buildStyle(e);
                        this._insertCursor(t, p), e.content && (t.styleSheet && !t.sheet ? t.styleSheet.cssText = e.content : t.appendChild(this.doc.createTextNode(e.content)))
                    }, d.prototype._buildStyle = function(e) {
                        var n = this.doc.createElement(e.tagName);
                        return n.setAttribute("type", e.type), l.eachKey(e.attrs, function(e, t) {
                            n.setAttribute(e, t)
                        }), n
                    }, d.prototype._insertCursor = function(e, t) {
                        this._writeImpl('<span id="' + t + '"/>');
                        t = this.doc.getElementById(t);
                        t && t.parentNode.replaceChild(e, t)
                    }, d.prototype._onScriptStart = function(e) {
                        e.outerWrites = this.writeQueue, this.writeQueue = [], this.scriptStack.unshift(e)
                    }, d.prototype._onScriptDone = function(e) {
                        return e !== this.scriptStack[0] ? void this.options.error({
                            msg: "Bad script nesting or script finished twice"
                        }) : (this.scriptStack.shift(), this.write.apply(this, e.outerWrites), void(!this.scriptStack.length && this.deferredRemote && (this._onScriptStart(this.deferredRemote), this.deferredRemote = null)))
                    }, d.prototype._writeScriptToken = function(e, t) {
                        var n = this._buildScript(e),
                            i = this._shouldRelease(n),
                            r = this.options.afterAsync;
                        e.src && (n.src = e.src, this._scriptLoadHandler(n, i ? r : function() {
                            t(), r()
                        }));
                        try {
                            this._insertCursor(n, u), n.src && !i || t()
                        } catch (e) {
                            this.options.error(e), t()
                        }
                    }, d.prototype._buildScript = function(e) {
                        var n = this.doc.createElement(e.tagName);
                        return l.eachKey(e.attrs, function(e, t) {
                            n.setAttribute(e, t)
                        }), e.content && (n.text = e.content), n
                    }, d.prototype._scriptLoadHandler = function(t, n) {
                        function i() {
                            t = t.onload = t.onreadystatechange = t.onerror = null
                        }

                        function e() {
                            i(), null != n && n(), n = null
                        }

                        function r(e) {
                            i(), o(e), null != n && n(), n = null
                        }

                        function a(e, t) {
                            var n = e["on" + t];
                            null != n && (e["_on" + t] = n)
                        }
                        var o = this.options.error;
                        a(t, "load"), a(t, "error"), s(t, {
                            onload: function() {
                                if (t._onload) try {
                                    t._onload.apply(this, Array.prototype.slice.call(arguments, 0))
                                } catch (e) {
                                    r({
                                        msg: "onload handler failed " + e + " @ " + t.src
                                    })
                                }
                                e()
                            },
                            onerror: function() {
                                if (t._onerror) try {
                                    t._onerror.apply(this, Array.prototype.slice.call(arguments, 0))
                                } catch (e) {
                                    return void r({
                                        msg: "onerror handler failed " + e + " @ " + t.src
                                    })
                                }
                                r({
                                    msg: "remote script failed " + t.src
                                })
                            },
                            onreadystatechange: function() {
                                /^(loaded|complete)$/.test(t.readyState) && e()
                            }
                        })
                    }, d.prototype._shouldRelease = function(e) {
                        return !/^script$/i.test(e.nodeName) || !!(this.options.releaseAsync && e.src && e.hasAttribute("async"))
                    }, t.default = d
                }, function(e, t, n) {
                    function i(e) {
                        var t;
                        return (a[e] || (t = a[e] = {
                            exports: {},
                            id: e,
                            loaded: !1
                        }, r[e].call(t.exports, t, t.exports, i), t.loaded = !0, t)).exports
                    }
                    var r, a;
                    e.exports = (r = [function(e, t, n) {
                        "use strict";
                        var n = n(1),
                            n = (n = n) && n.__esModule ? n : {
                                default: n
                            };
                        e.exports = n.default
                    }, function(e, t, n) {
                        "use strict";

                        function i(e) {
                            if (e && e.__esModule) return e;
                            var t = {};
                            if (null != e)
                                for (var n in e) Object.prototype.hasOwnProperty.call(e, n) && (t[n] = e[n]);
                            return t.default = e, t
                        }
                        t.__esModule = !0;
                        var r, l = i(n(2)),
                            a = i(n(3)),
                            o = n(6),
                            d = (o = o) && o.__esModule ? o : {
                                default: o
                            },
                            s = n(5),
                            c = {
                                comment: /^<!--/,
                                endTag: /^<\//,
                                atomicTag: /^<\s*(script|style|noscript|iframe|textarea)[\s\/>]/i,
                                startTag: /^</,
                                chars: /^[^<]/
                            },
                            p = (u.prototype.append = function(e) {
                                this.stream += e
                            }, u.prototype.prepend = function(e) {
                                this.stream = e + this.stream
                            }, u.prototype._readTokenImpl = function() {
                                var e = this._peekTokenImpl();
                                if (e) return this.stream = this.stream.slice(e.length), e
                            }, u.prototype._peekTokenImpl = function() {
                                for (var e in c)
                                    if (c.hasOwnProperty(e) && c[e].test(this.stream)) {
                                        e = a[e](this.stream);
                                        if (e) return "startTag" === e.type && /script|style/i.test(e.tagName) ? null : (e.text = this.stream.substr(0, e.length), e)
                                    }
                            }, u.prototype.peekToken = function() {
                                return this._peekToken()
                            }, u.prototype.readToken = function() {
                                return this._readToken()
                            }, u.prototype.readTokens = function(e) {
                                for (var t; t = this.readToken();)
                                    if (e[t.type] && !1 === e[t.type](t)) return
                            }, u.prototype.clear = function() {
                                var e = this.stream;
                                return this.stream = "", e
                            }, u.prototype.rest = function() {
                                return this.stream
                            }, u);

                        function u() {
                            var e = this,
                                t = 0 < arguments.length && void 0 !== arguments[0] ? arguments[0] : "",
                                n = 1 < arguments.length && void 0 !== arguments[1] ? arguments[1] : {},
                                i = this,
                                r = u;
                            if (!(i instanceof r)) throw new TypeError("Cannot call a class as a function");
                            this.stream = t;
                            var a, o = !1,
                                s = {};
                            for (a in l) l.hasOwnProperty(a) && (n.autoFix && (s[a + "Fix"] = !0), o = o || s[a + "Fix"]);
                            o ? (this._readToken = (0, d.default)(this, s, function() {
                                return e._readTokenImpl()
                            }), this._peekToken = (0, d.default)(this, s, function() {
                                return e._peekTokenImpl()
                            })) : (this._readToken = this._readTokenImpl, this._peekToken = this._peekTokenImpl)
                        }
                        for (r in (t.default = p).tokenToString = function(e) {
                                return e.toString()
                            }, p.escapeAttributes = function(e) {
                                var t, n = {};
                                for (t in e) e.hasOwnProperty(t) && (n[t] = (0, s.escapeQuotes)(e[t], null));
                                return n
                            }, p.supports = l) l.hasOwnProperty(r) && (p.browserHasFlaw = p.browserHasFlaw || !l[r] && r)
                    }, function(e, t) {
                        "use strict";
                        var n = !(t.__esModule = !0),
                            i = !1,
                            r = window.document.createElement("div");
                        try {
                            var a = "<P><I></P></I>";
                            r.innerHTML = a, t.tagSoup = n = r.innerHTML !== a
                        } catch (e) {
                            t.tagSoup = n = !1
                        }
                        try {
                            r.innerHTML = "<P><i><P></P></i></P>", t.selfClose = i = 2 === r.childNodes.length
                        } catch (e) {
                            t.selfClose = i = !1
                        }
                        r = null, t.tagSoup = n, t.selfClose = i
                    }, function(e, t, n) {
                        "use strict";

                        function i(e) {
                            var n, i, r;
                            if (-1 !== e.indexOf(">")) {
                                e = e.match(s.startTag);
                                if (e) {
                                    n = {}, i = {}, r = e[2], e[2].replace(s.attr, function(e, t) {
                                        arguments[2] || arguments[3] || arguments[4] || arguments[5] ? arguments[5] ? (n[arguments[5]] = "", i[arguments[5]] = !0) : n[t] = arguments[2] || arguments[3] || arguments[4] || s.fillAttr.test(t) && t || "" : n[t] = "", r = r.replace(e, "")
                                    });
                                    e = {
                                        v: new o.StartTagToken(e[1], e[0].length, n, i, !!e[3], r.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, ""))
                                    };
                                    if ("object" === (void 0 === e ? "undefined" : a(e))) return e.v
                                }
                            }
                        }
                        t.__esModule = !0;
                        var a = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function(e) {
                                return typeof e
                            } : function(e) {
                                return e && "function" == typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : typeof e
                            },
                            o = (t.comment = function(e) {
                                var t = e.indexOf("--\x3e");
                                if (0 <= t) return new o.CommentToken(e.substr(4, t - 1), t + 3)
                            }, t.chars = function(e) {
                                var t = e.indexOf("<");
                                return new o.CharsToken(0 <= t ? t : e.length)
                            }, t.startTag = i, t.atomicTag = function(e) {
                                var t = i(e);
                                if (t) {
                                    e = e.slice(t.length);
                                    if (e.match(new RegExp("</\\s*" + t.tagName + "\\s*>", "i"))) {
                                        e = e.match(new RegExp("([\\s\\S]*?)</\\s*" + t.tagName + "\\s*>", "i"));
                                        if (e) return new o.AtomicTagToken(t.tagName, e[0].length + t.length, t.attrs, t.booleanAttrs, e[1])
                                    }
                                }
                            }, t.endTag = function(e) {
                                if (e = e.match(s.endTag)) return new o.EndTagToken(e[1], e[0].length)
                            }, n(4)),
                            s = {
                                startTag: /^<([\-A-Za-z0-9_]+)((?:\s+[\w\-]+(?:\s*=?\s*(?:(?:"[^"]*")|(?:'[^']*')|[^>\s]+))?)*)\s*(\/?)>/,
                                endTag: /^<\/([\-A-Za-z0-9_]+)[^>]*>/,
                                attr: /(?:([\-A-Za-z0-9_]+)\s*=\s*(?:(?:"((?:\\.|[^"])*)")|(?:'((?:\\.|[^'])*)')|([^>\s]+)))|(?:([\-A-Za-z0-9_]+)(\s|$)+)/g,
                                fillAttr: /^(checked|compact|declare|defer|disabled|ismap|multiple|nohref|noresize|noshade|nowrap|readonly|selected)$/i
                            }
                    }, function(e, t, n) {
                        "use strict";

                        function o(e, t) {
                            if (!(e instanceof t)) throw new TypeError("Cannot call a class as a function")
                        }
                        t.__esModule = !0, t.EndTagToken = t.AtomicTagToken = t.StartTagToken = t.TagToken = t.CharsToken = t.CommentToken = t.Token = void 0;
                        var a = n(5),
                            i = (t.Token = function e(t, n) {
                                o(this, e), this.type = t, this.length = n, this.text = ""
                            }, t.CommentToken = (l.prototype.toString = function() {
                                return "\x3c!--" + this.content
                            }, l), t.CharsToken = (r.prototype.toString = function() {
                                return this.text
                            }, r), t.TagToken = (s.formatTag = function(e) {
                                var t, n, i = 1 < arguments.length && void 0 !== arguments[1] ? arguments[1] : null,
                                    r = "<" + e.tagName;
                                for (t in e.attrs) e.attrs.hasOwnProperty(t) && (r += " " + t, n = e.attrs[t], void 0 !== e.booleanAttrs && void 0 !== e.booleanAttrs[t] || (r += '="' + (0, a.escapeQuotes)(n) + '"'));
                                return e.rest && (r += " " + e.rest), r += e.unary && !e.html5Unary ? "/>" : ">", null != i && (r += i + "</" + e.tagName + ">"), r
                            }, s));

                        function s(e, t, n, i, r) {
                            o(this, s), this.type = e, this.length = n, this.text = "", this.tagName = t, this.attrs = i, this.booleanAttrs = r, this.unary = !1, this.html5Unary = !1
                        }

                        function r(e) {
                            o(this, r), this.type = "chars", this.length = e, this.text = ""
                        }

                        function l(e, t) {
                            o(this, l), this.type = "comment", this.length = t || (e ? e.length : 0), this.text = "", this.content = e
                        }

                        function d(e, t) {
                            o(this, d), this.type = "endTag", this.length = t, this.text = "", this.tagName = e
                        }

                        function c(e, t, n, i, r) {
                            o(this, c), this.type = "atomicTag", this.length = t, this.text = "", this.tagName = e, this.attrs = n, this.booleanAttrs = i, this.unary = !1, this.html5Unary = !1, this.content = r
                        }

                        function p(e, t, n, i, r, a) {
                            o(this, p), this.type = "startTag", this.length = t, this.text = "", this.tagName = e, this.attrs = n, this.booleanAttrs = i, this.html5Unary = !1, this.unary = r, this.rest = a
                        }
                        t.StartTagToken = (p.prototype.toString = function() {
                            return i.formatTag(this)
                        }, p), t.AtomicTagToken = (c.prototype.toString = function() {
                            return i.formatTag(this, this.content)
                        }, c), t.EndTagToken = (d.prototype.toString = function() {
                            return "</" + this.tagName + ">"
                        }, d)
                    }, function(e, t) {
                        "use strict";
                        t.__esModule = !0, t.escapeQuotes = function(e) {
                            var t = 1 < arguments.length && void 0 !== arguments[1] ? arguments[1] : "";
                            return e ? e.replace(/([^"]*)"/g, function(e, t) {
                                return /\\/.test(t) ? t + '"' : t + '\\"'
                            }) : t
                        }
                    }, function(e, t) {
                        "use strict";

                        function l(e) {
                            return e && "startTag" === e.type && (e.unary = n.test(e.tagName) || e.unary, e.html5Unary = !/\/>$/.test(e.text)), e
                        }

                        function d(e, t) {
                            t = t.pop();
                            e.prepend("</" + t.tagName + ">")
                        }
                        t.__esModule = !0, t.default = function(i, n, r) {
                            function a() {
                                var e, t, n;
                                t = r, n = (e = i).stream, t = l(r()), e.stream = n, t && s[t.type] && s[t.type](t)
                            }(e = []).last = function() {
                                return this[this.length - 1]
                            }, e.lastTagNameEq = function(e) {
                                var t = this.last();
                                return t && t.tagName && t.tagName.toUpperCase() === e.toUpperCase()
                            }, e.containsTagName = function(e) {
                                for (var t, n = 0; t = this[n]; n++)
                                    if (t.tagName === e) return !0;
                                return !1
                            };
                            var e, o = e,
                                s = {
                                    startTag: function(e) {
                                        var t = e.tagName;
                                        "TR" === t.toUpperCase() && o.lastTagNameEq("TABLE") ? (i.prepend("<TBODY>"), a()) : n.selfCloseFix && c.test(t) && o.containsTagName(t) ? o.lastTagNameEq(t) ? d(i, o) : (i.prepend("</" + e.tagName + ">"), a()) : e.unary || o.push(e)
                                    },
                                    endTag: function(e) {
                                        o.last() ? n.tagSoupFix && !o.lastTagNameEq(e.tagName) ? d(i, o) : o.pop() : n.tagSoupFix && (r(), a())
                                    }
                                };
                            return function() {
                                return a(), l(r())
                            }
                        };
                        var n = /^(AREA|BASE|BASEFONT|BR|COL|FRAME|HR|IMG|INPUT|ISINDEX|LINK|META|PARAM|EMBED)$/i,
                            c = /^(COLGROUP|DD|DT|LI|OPTIONS|P|TD|TFOOT|TH|THEAD|TR)$/i
                    }], a = {}, i.m = r, i.c = a, i.p = "", i(0))
                }, function(e, t) {
                    "use strict";

                    function i(e) {
                        return null != e
                    }

                    function r(e, t, n) {
                        for (var i = void 0, r = e && e.length || 0, i = 0; i < r; i++) t.call(n, e[i], i)
                    }

                    function a(e, t, n) {
                        for (var i in e) e.hasOwnProperty(i) && t.call(n, i, e[i])
                    }

                    function n(e, t) {
                        return !(!e || "startTag" !== e.type && "atomicTag" !== e.type || !("tagName" in e) || !~e.tagName.toLowerCase().indexOf(t))
                    }
                    t.__esModule = !0;
                    var o = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function(e) {
                        return typeof e
                    } : function(e) {
                        return e && "function" == typeof Symbol && e.constructor === Symbol && e !== Symbol.prototype ? "symbol" : typeof e
                    };
                    t.existy = i, t.isFunction = function(e) {
                        return "function" == typeof e
                    }, t.each = r, t.eachKey = a, t.defaults = function(n, e) {
                        return n = n || {}, a(e, function(e, t) {
                            i(n[e]) || (n[e] = t)
                        }), n
                    }, t.toArray = function(t) {
                        try {
                            return Array.prototype.slice.call(t)
                        } catch (e) {
                            n = [], r(t, function(e) {
                                n.push(e)
                            });
                            t = {
                                v: n
                            };
                            if ("object" === (void 0 === t ? "undefined" : o(t))) return t.v
                        }
                        var n
                    }, t.last = function(e) {
                        return e[e.length - 1]
                    }, t.isTag = n, t.isScript = function(e) {
                        return n(e, "script")
                    }, t.isStyle = function(e) {
                        return n(e, "style")
                    }
                }], r = {}, n.m = i, n.c = r, n.p = "", n(0);

                function n(e) {
                    var t;
                    return (r[e] || (t = r[e] = {
                        exports: {},
                        id: e,
                        loaded: !1
                    }, i[e].call(t.exports, t, t.exports, n), t.loaded = !0, t)).exports
                }
                var i, r
            }, "object" == typeof exports && "object" == typeof module ? module.exports = t() : "function" == typeof define && define.amd ? define([], t) : "object" == typeof exports ? exports.postscribe = t() : e.postscribe = t(), ["android", "ios", "unity"]),
            p = window.awesomeads_interstitial,
            g = 99999998;
        return a.check = function(e, n) {
            var t = {
                preload: !0
            };
            AwesomeAdManager.get_ad(e, t, function(e, t) {
                e ? null != n && n(!1) : null != n && n(null != t.creative)
            })
        }, a.prototype.click = function() {
            var e = this,
                t = {
                    rnd: c.createUUID(),
                    placement: e.placement_id,
                    line_item: e.line_item_id,
                    creative: e.creative.id,
                    prog: e.ad.programmaticUuid,
                    aua: e.ad.aua,
                    tip: e.ad.tip
                },
                e = e.openRtbPartnerId ? "&openRtbPartnerId=" + e.openRtbPartnerId : "",
                n = window.awesomeads_bundle ? "&bundle=" + window.awesomeads_bundle : "",
                t = window.awesomeads_host + "/click?data=" + encodeURIComponent(JSON.stringify(t)) + "&sdkVersion=" + window.aa_sdkVersion + n + e;
            t += c.getBumperSettings(), window.open(t, "_blank")
        }, a.prototype.add_keyword = function(e) {
            for (var t = e.split(" "), n = 0; n < t.length; n++) this.keywords.push(encodeURIComponent(t[n].toLowerCase()));
            return this
        }, a.prototype.get_keywords_string = function() {
            return this.keywords.join("+")
        }, a.prototype.insert = function(e) {
            e.parentNode.insertBefore(this.element, e)
        }, a.MINIMUM_VISIBILITY = .5, a.prototype.write = function() {
            for (var e = window.awesomeads_host, t = e.substring(e.indexOf("ads.")) + "/ad.js", n = "placement=" + this.placement_id, i = null, r = document.getElementsByTagName("script"), a = 0; a < r.length; a++)
                if (-1 !== r[a].src.indexOf(t) && -1 !== r[a].src.indexOf(n)) {
                    i = r[a];
                    break
                }
            return null == i || null == i.getAttribute("async") || null == i.parentNode ? this.writeSync() : (console.info("[AA :: INFO] Managed to write tag Async!"), i.parentNode.appendChild(this.element), this)
        }, a.prototype.writeSync = function() {
            console.info("[AA :: INFO] Trying to write the tag Sync.");
            var e = this.element.outerHTML;
            return document.write(e), this.element = null, this
        }, a.prototype.on_load = function(e) {
            this.loaded ? e() : this.on_load_callbacks.push(e)
        }, a.prototype.set_click_tracking_url = function(e) {
            return this.click_tracking_url = e, this
        }, a.prototype.displaySkin = function() {
            var e, t, n, i, r = this;
            if (top != self) try {
                t = top.document, document.onclick = function() {
                    t.dispatchEvent(new Event("pageSkinClickThrough"))
                }
            } catch (e) {
                t = document
            } else t = document;
            t.addEventListener("pageSkinClickThrough", function(e) {
                r.click()
            });
            try {
                n = JSON.parse(r.creative.customPayload)
            } catch (e) {
                n = {}
            }
            return n.element ? (i = t.querySelector(n.element)) ? (e = i, console.log("Applying skin to " + n.element)) : (e = t.body, console.log('Element "' + n.element + '" not found. Applying skin to body.')) : (e = t.body, console.log("Applying skin to body")), (r.skinElement = e).style.backgroundImage = "url(" + r.creative.details.image + ")", e.style.backgroundPosition = "center top", e.style.backgroundRepeat = "no-repeat", e.style.backgroundAttachment = "fixed", e.style.backgroundColor = n.colour || "rgba(0, 0, 0, 0)", r.clickEnabled && (e.onclick = function(e) {
                var e = e || event;
                e && (e = e.target || e.srcElement, Element.prototype.matches || (Element.prototype.matches = Element.prototype.matchesSelector || Element.prototype.mozMatchesSelector || Element.prototype.msMatchesSelector || Element.prototype.oMatchesSelector || Element.prototype.webkitMatchesSelector), n.element && e.matches(n.element) || "BODY" == e.tagName) && r.click()
            }), document.createElement("div")
        }, a.prototype.displayImage = function() {
            var i = this,
                e = document.createElement("a"),
                t = (e.style.position = "relative", e.style.display = "inline-block", e.style.cursor = "pointer", i.creative.details && !1 === i.creative.details.new_window || (e.target = "_blank"), c.createUUID()),
                n = i.openRtbPartnerId ? "&openRtbPartnerId=" + i.openRtbPartnerId : "",
                r = window.awesomeads_bundle ? "&bundle=" + window.awesomeads_bundle : "",
                t = window.awesomeads_host + "/click?placement=" + i.placement_id + "&rnd=" + t + "&line_item=" + i.line_item_id + "&creative=" + i.creative.id + "&sdkVersion=" + window.aa_sdkVersion + r + n,
                a = (t += c.getBumperSettings(), ""),
                a = i.click_tracking_url ? i.click_tracking_url + encodeURIComponent(t) : t,
                r = document.createElement("img");
            return r.src = i.creative.details.image, r.style.width = i.creative.details.width + "px", r.style.height = i.creative.details.height + "px", i.clickEnabled && r.addEventListener("click", function(e) {
                var t, n;
                1 == i.campaign_type ? (t = c.generateClickEvent(i.ad), n = c.generateCPIClick(i.ad), c.sendAsyncGET(t, function() {
                    console.log("Click event OK: " + t)
                }, null), window.open(n, "_blank")) : window.open(a, "_blank")
            }), e.appendChild(r), e
        }, a.prototype.displayFlash = function() {
            var e = this,
                t = document.createElement("div"),
                n = (t.style.position = "relative", t.style.width = e.creative.details.width, t.style.height = e.creative.details.height, document.createElement("object")),
                i = (n.data = e.creative.details.swf_url, n.width = e.creative.details.width, n.height = e.creative.details.height, t.appendChild(n), document.createElement("embed"));
            return i.src = e.creative.details.swf_url, i.width = e.creative.details.width, i.height = e.creative.details.height, n.appendChild(i), t
        }, a.prototype.displayRichMedia = function() {
            var h = this,
                m = document.createElement("div"),
                e = encodeURIComponent(JSON.stringify({
                    sdkVersion: window.aa_sdkVersion,
                    show: window.awesomeads_show,
                    bundle: window.awesomeads_bundle,
                    domain: window.awesomeads_domain
                })),
                t = (m.style.position = "relative", m.id = "iframe_container", m.style.width = h.creative.details.width + "px", m.style.height = h.creative.details.height + "px", m.style.bottom = "0px", m.style.zIndex = 1, h.openRtbPartnerId ? "&openRtbPartnerId=" + h.openRtbPartnerId : ""),
                f = h.creative.details.url += "?placement=" + h.placement_id + "&line_item=" + h.line_item_id + "&creative=" + h.creative.id + "&daid=" + h.id + "&aa_sdkData=" + e + t,
                y = document.createElement("iframe");
            if (y.addEventListener("DOMNodeInserted", () => {
                    AwesomeAdManager.track_load_time(h.element, {
                        sdkVersion: window.aa_sdkVersion,
                        lineItemId: h.creative.line_item_id,
                        format: h.creative.format
                    })
                }), y.onload = function() {
                    AwesomeAdManager.track_render_time(h.element, {
                        sdkVersion: window.aa_sdkVersion,
                        lineItemId: h.creative.line_item_id,
                        format: h.creative.format
                    })
                }, y.src = h.creative.details.url, y.width = h.creative.details.width, y.height = h.creative.details.height, y.orig_width = h.creative.details.width, y.orig_height = h.creative.details.height, y.style.position = "relative", y.style.border = "0px", y.scrolling = "no", y.frameBorder = 0, m.appendChild(y), !h.creative.details.placement_format || "floor" !== h.creative.details.placement_format && "rich_media" !== h.creative.format) window.addEventListener("message", n, !1);
            else try {
                top.window.addEventListener("message", n, !1)
            } catch (e) {
                window.addEventListener("message", n, !1)
            }

            function n(t) {
                if (!(y.src.indexOf(t.origin) < 0) && t.data.daid === h.id) {
                    if (t.data.stickSportsHack) {
                        function n(e, t) {
                            e.style.setProperty("max-width", t.width, "important"), e.style.setProperty("margin-left", t.marginLeft, "important"), e.style.setProperty("margin-right", t.marginRight, "important"), e.style.setProperty("margin-top", t.marginTop, "important"), e.querySelector(".takeover").style.display = "none"
                        }
                        if (window.top != self) try {
                            n(window.top.document.body, t.data.stickSportsHack)
                        } catch (e) {
                            n(document.body, t.data.stickSportsHack)
                        } else n(document.body, t.data.stickSportsHack)
                    }
                    if (t.data.resizeIframe) {
                        m.style.zIndex = 500;
                        var e = (0 <= t.data.resizeIframe.height ? t.data.resizeIframe : y).height,
                            i = (0 <= t.data.resizeIframe.width ? t.data.resizeIframe : y).width,
                            r = "0px",
                            a = "0px";
                        switch (t.data.resizeIframe.direction) {
                            case "up":
                                y.style.bottom = "0px", a = m.style.top && "0px" !== m.style.top ? "0px" : -1 * (t.data.resizeIframe.height - y.height) + "px";
                                break;
                            case "down":
                                y.style.top = "0px";
                                break;
                            case "left":
                                y.style.right = "0px", r = m.style.left && "0px" !== m.style.left ? "0px" : -1 * (t.data.resizeIframe.width - y.width) + "px";
                                break;
                            case "right":
                                y.style.left = "0px"
                        }
                        t.data.resizeIframe.pushContent && (m.style.width = t.data.resizeIframe.width + "px", m.style.height = t.data.resizeIframe.height + "px"), h.padlock.style.visibility = "hidden", m.style.top = a, m.style.left = r, h.padlock.style.visibility = "visible", y.height = e, y.width = i, 0 != e && 0 != i || (h.padlock.style.visibility = "hidden")
                    } else if ("resetIframe" === t.data) y.height = y.orig_height, y.width = y.orig_width, m.width = y.orig_width, m.width = y.orig_width, m.style.removeProperty("bottom"), m.style.removeProperty("top"), m.style.removeProperty("right"), m.style.removeProperty("left"), y.style.removeProperty("bottom"), y.style.removeProperty("top"), y.style.removeProperty("right"), y.style.removeProperty("left");
                    else {
                        function o(e) {
                            var e = e || t;
                            !e || "BODY" != (e = e.target || e.srcElement).tagName && "HTML" != e.tagName || h.click()
                        }

                        function s(e, t) {
                            console.log("Apply background image", t.img), e.body.style.backgroundImage = "url(" + t.img + ")", e.body.style.backgroundPosition = "center top", e.body.style.backgroundRepeat = "no-repeat", e.body.style.backgroundAttachment = "fixed", e.body.style.backgroundColor = t.colour || t.color, h.clickEnabled && (e.onclick = o), h.padlock && e.body.appendChild(h.padlock)
                        }
                        var l, d, c;
                        if (t.data.siteSkin)
                            if (window.top != self) try {
                                s(window.top.document, t.data.siteSkin)
                            } catch (e) {
                                s(document, t.data.siteSkin)
                            } else s(document, t.data.siteSkin);
                        if (t.data.fullscreenIframe) {
                            var e = t.data.fullscreenIframe.width,
                                i = t.data.fullscreenIframe.height,
                                p = t.data.fullscreenIframe.extra,
                                p = f.replace("index.html", p),
                                u = document.createElement("iframe");
                            if (u.src = p, u.id = "awesome_ads_generated_floor", u.style.position = "fixed", u.style.width = e + "px", u.style.height = i + "px", u.style.bottom = -i + "px", u.style.left = "50%", u.style.marginLeft = -e / 2 + "px", u.style.zIndex = g, a != self) try {
                                window.top.document.body.appendChild(u)
                            } catch (e) {
                                document.body.appendChild(u)
                            }
                            l = -1 * i, d = setInterval(function() {
                                0 <= l && d ? (clearInterval(d), d = null) : (l += 5, u.style.bottom = l + "px")
                            }, 5)
                        }
                        t.data.closeFullscreenExtra && (p = window.top.document.getElementById("awesome_ads_generated_floor"), (c = p.parentNode).removeChild(p)), t.data.closeExtra && (c = h.element.parentElement, h.element.style.display = "none", setTimeout(function() {
                            c.removeChild(h.element)
                        }, 1500))
                    }
                }
            }
            return m
        }, a.prototype.displayTag = function() {
            var e = this,
                t = (e.is_tag = !0, document.createElement("div")),
                n = "aa_tag_container_" + ~~(1e7 * Math.random());
            t.id = n, t.style.position = "relative", t.style.width = e.creative.details.width, t.style.height = e.creative.details.height;
            var i = (i = e.creative.details.tag).replace("[timestamp]", (new Date).getTime()),
                r = c.createUUID(),
                a = e.openRtbPartnerId ? "&openRtbPartnerId=" + e.openRtbPartnerId : "",
                r = window.awesomeads_host + "/click?placement=" + e.placement_id + "&rnd=" + r + "&line_item=" + e.line_item_id + "&creative=" + e.creative.id + "&sdkVersion=" + window.aa_sdkVersion + a;
            r += c.getBumperSettings(), i = (i = (i = i.replace("[click]", r += "&redir=")).replace("[click_enc]", encodeURIComponent(r))).replace("[keywords]", e.get_keywords_string());
            for (var a = (new DOMParser).parseFromString(i, "text/html"), o = a && a.getElementsByTagName("script") || [], s = !1, l = 0; l < o.length; l++) {
                var d = o[0];
                if (-1 < d.nodeName.indexOf("script") && null != (d = d.getAttribute("src")) && -1 < d.indexOf("googletagservices") && ((script = document.createElement("script")).src = "https://www.googletagservices.com/tag/js/gpt.js", document.getElementsByTagName("head")[0].appendChild(script), s = !0), s) break
            }
            return setTimeout(function() {
                window.postscribe("#" + n, i, {
                    error: function(e) {
                        console.error(e)
                    },
                    done: function() {},
                    beforeWriteToken: function(e) {
                        return e.attrs && e.attrs["data-json"] && (e.attrs["data-json"] = e.attrs["data-json"].replace(/"/g, "&quot;")), e
                    }
                })
            }, 500), t
        }, a.prototype.displayVideo = function() {
            var e, t = this,
                n = (t.setBackgroundColourForVPAID(), document.createElement("div")),
                i = (t.element.style.width = "100%", t.element.style.height = "100%", n.style.position = "relative", n.style.width = "100%", n.style.height = "100%", n.width = "100%", n.height = "100%", document.createElement("div"));
            return i.style.top = "0", i.style.width = "100%", i.style.height = "100%", n.appendChild(i), t.isVpaidDisplay() && (e = t.getProgressBar(), n.appendChild(e), window._vpaidProgressBarId = "vpaidProgressBar"), t.afterWrite = function() {
                var e = {
                        muted: t.muted,
                        autoplaying: t.autoplaying,
                        startdelay: t.startdelay,
                        skippable: t.skippable,
                        interstitial: t.isInterstitial,
                        playbackmethod: t.playbackmethod,
                        foldPosition: t.foldPosition,
                        openRtbPartnerId: t.openRtbPartnerId
                    },
                    e = new AwesomeVideo(t.placement_id, !1, i, !1, t.ad, e).writePreloaded();
                e.onFinished(function() {
                    var e = document.getElementById(t.id);
                    e.parentNode && e.parentNode.removeChild(e)
                }), e.onError(function() {
                    var e = document.getElementById(t.id);
                    e && e.parentNode && e.parentNode.removeChild(e), console.error("[AA :: ERROR] - Client encountered error when playing preroll - switching to content")
                }), e.onEmpty(function() {
                    var e = document.getElementById(t.id);
                    e && e.parentNode && e.parentNode.removeChild(e), console.error("[AA :: ERROR] - Client encountered empty ad - switching to content")
                })
            }, n
        }, a.prototype.init = function(e) {
            var y = this;

            function a() {
                try {
                    return window.self !== window.top
                } catch (e) {
                    return 1
                }
            }
            y.ad = e, y.ad.placement_id = y.placement_id, y.is_fill = e.is_fill, y.is_fallback = e.is_fallback, y.moat = e.moat || 0, y.is_house = e.is_house || !1, y.safe_ad_approved = e.safe_ad_approved, y.show_padlock = e.show_padlock, y.app = e.app, y.campaign_type = e.campaign_type, y.creative = e.creative, y.clickEnabled = y.creative && y.creative.clickUrl, y.creative && (y.creative_id = y.creative.id, y.line_item_id = e.line_item_id, y.advertiser_id = e.advertiserId, y.publisher_id = e.publisherId, y.campaign_id = e.campaign_id, y.publisher_id = e.publisherId, y.advertiser_id = e.advertiserId, e = document.getElementById(y.id || y.element && y.element.id), y.foldPosition = y.getFoldPosition(e), y.register_download_impression(), e = function() {
                y.element = document.getElementById(y.id) || y.element;
                for (var h, e = y.element; e && e.parentNode;) e = e.parentNode;
                if (e === document) {
                    if (clearInterval(y.ready_interval), "page_skin" === y.creative.details.placement_format && "image_with_link" === y.creative.format ? h = y.displaySkin() : "image_with_link" === y.creative.format ? h = y.displayImage() : "flash" === y.creative.format ? h = y.displayFlash() : "rich_media" === y.creative.format ? h = y.displayRichMedia() : "fallback_tag" === y.creative.format || "tag" === y.creative.format ? h = y.displayTag() : "video" === y.creative.format && (h = y.displayVideo()), y.padlock = y.shouldShowPadlock() ? ((n = document.createElement("img")).src = window.cdnUrl + "/images/watermark_2.png", n.style.position = "absolute", n.addEventListener("click", function(e) {
                            window.open(window.awesomeads_host + "/safead", "_blank")
                        }), n.style.top = "0px", n.style.left = "0px", n.style.setProperty("width", "67px", "important"), n.style.setProperty("height", "25px", "important"), n) : null, y.padlock && "page_skin" !== y.creative.details.placement_format && h.appendChild(y.padlock), y.creative.details.placement_format && "floor" === y.creative.details.placement_format) {
                        y.element.style.position = "fixed", y.element.style.bottom = "0px";
                        var t = y.creative.details.width / 2;
                        if (y.element.style.left = "calc(50% - " + t + "px)", y.element.style.zIndex = 9999999, y.element.appendChild(h), !y.is_tag && top != self) try {
                            top.document.body.appendChild(y.element)
                        } catch (e) {}
                    } else if (y.creative.details.placement_format && "popup" === y.creative.details.placement_format) {
                        y.element.style.position = "fixed", y.element.style.top = 0, y.element.style.bottom = 0, y.element.style.left = 0, y.element.style.right = 0, y.element.style.width = "100%", y.element.style.height = "100%", y.element.style.zIndex = g;
                        var t = document.createElement("div"),
                            n = (t.style.position = "absolute", t.style.top = 0, t.style.bottom = 0, t.style.left = 0, t.style.right = 0, t.style.width = "100%", t.style.height = "100%", t.style.backgroundColor = "black", t.style.opacity = .6, t.style.filter = "alpha(60%)", t.onclick = function() {
                                var e = y.element.parentElement;
                                y.element.style.display = "none", setTimeout(function() {
                                    e.removeChild(y.element)
                                }, 1500)
                            }, y.creative.details.width / 2),
                            i = y.creative.details.height / 2;
                        if (h.style.left = "calc(50% - " + n + "px)", h.style.top = "calc(50% - " + i + "px)", y.element.appendChild(t), y.element.appendChild(h), a()) try {
                            window.top.document.body.appendChild(y.element)
                        } catch (e) {
                            console.log(e)
                        }
                    } else if (!0 === y.isInterstitial || !0 === p || "true" === p) {
                        var m = document.createElement("div"),
                            f = (m.id = "interAd", m.style.position = "fixed", m.style.top = "0", m.style.left = "0", m.style.width = "100%", m.style.height = "100%", m.style.background = "rgba(0, 0, 0, 1)", m.style.zIndex = 99999999, m.style.padding = "0px", m.style.margin = "0px", h.style.position = "absolute", h.style.backgroundColor = "black", setInterval(function() {
                                var e, t, n, i, r, a, o, s, l, d, c, p = m.offsetWidth,
                                    u = m.offsetHeight;
                                1 < p && 1 < u && (clearInterval(f), d = r = c = i = n = 0, d = p / u < (a = (e = y.creative.details.width) / (t = y.creative.details.height)) ? (n = 0, i = (u - (r = (c = p) / a)) / 2, c / e) : (i = 0, n = (p - (c = (r = u) * a)) / 2, r / t), h.style.left = n + e / 2 * (d - 1) + "px", h.style.top = i + t / 2 * (d - 1) + "px", h.style.width = e + "px", h.style.height = t + "px", h.style.webkitTransform = "scale(" + d + ")", h.style.MozTransform = "scale(" + d + ")", h.style.oTransform = "scale(" + d + ")", h.style.msTransform = "scale(" + d + ")", y.padlock && (s = p / 320, l = u / 480, a = 1 / (d = Math.max(Math.min(s, l), 1)) * Math.min(1.25, d), y.padlock.style.setProperty("width", 67 * a + "px", "important"), y.padlock.style.setProperty("height", 25 * a + "px", "important")), o = document.createElement("img"), s = p / 320, l = u / 480, d = Math.max(Math.min(s, l), 1), c = Math.min(1.85, d), o.src = window.cdnUrl + "/images/sa_close.png", o.style.position = "absolute", o.style.right = "5px", o.style.top = "5px", o.style.width = Math.floor(25 * c) + "px", o.style.height = Math.floor(25 * c) + "px", o.addEventListener("click", function() {
                                    m.removeChild(o), m.removeChild(h), y.element.removeChild(m);
                                    try {
                                        window.top.postMessage({
                                            function: "aa_close_button",
                                            message: "Close button has been clicked."
                                        }, "*")
                                    } catch (e) {
                                        console.error("PostMessage exception: ", e)
                                    }
                                }), m.appendChild(h), y.isMobileSdk || m.appendChild(o))
                            }, 50));
                        if (y.element.appendChild(m), !y.is_tag && top != self) try {
                            top.document.body.appendChild(y.element)
                        } catch (e) {}
                    } else if ("rich_media" === y.creative.format)
                        if (a()) {
                            function r() {
                                var e, t = window.frameElement,
                                    n = 0,
                                    i = 0;
                                window.parent && window.parent != window.top && window.parent.frameElement && (t && (n += (e = t.getBoundingClientRect()).left, i += e.top), t = window.parent.frameElement), t && (n += (e = t.getBoundingClientRect()).left + window.top.scrollX, i += e.top + window.top.scrollY, y.element.style.position = "absolute", y.element.style.top = i + "px", y.element.style.left = n + "px")
                            }
                            if (r(), y.element.appendChild(h), window.top && window.top.addEventListener("scroll", r), top != self) try {
                                window.top.document.body.appendChild(y.element)
                            } catch (e) {
                                console.log(e)
                            }
                        } else y.element.appendChild(h);
                    else y.element.appendChild(h);
                    "function" == typeof y.afterWrite && y.afterWrite(), y.displayElement = h, y.on_visibility_change(), Math.random() < y.moat && y.register_moat_events()
                }
            }, clearInterval(y.ready_interval), y.ready_interval = setInterval(e, 50), e())
        }, a.prototype.test = function() {
            return this.test_ads = !0, this
        }, a.prototype.shouldShowPadlock = function() {
            return !this.is_fallback && !this.is_tag && "video" !== this.creative.format && !!this.show_padlock
        }, a.prototype.getFoldPosition = function(e) {
            try {
                var t = e.getBoundingClientRect(),
                    n = this.creative.details.height * this.creative.details.width
            } catch (e) {
                return 0
            }
            var e = Math.max(0, t.top),
                t = Math.max(0, t.left),
                i = t + this.creative.details.width,
                r = e + this.creative.details.height;
            return .9 < Math.max(0, Math.min(window.innerHeight - e, r - e)) * Math.max(0, Math.min(window.innerWidth - t, i - t)) / n ? 1 : 3
        }, a.prototype.is_element_visible = function(e, t) {
            t = t || this.displayElement;
            try {
                var n = t.getBoundingClientRect()
            } catch (e) {
                return !1
            }
            0 in t;
            var t = n.height * n.width,
                e = e || a.MINIMUM_VISIBILITY,
                i = Math.max(0, n.top),
                r = Math.max(0, n.left);
            return e < Math.max(0, Math.min(window.innerHeight - i, n.bottom - i)) * Math.max(0, Math.min(window.innerWidth - r, n.right - r)) / t
        }, a.prototype.startCountingViewTime = function() {
            function t() {
                var e;
                1800 <= i || (n.is_element_visible() && ++i % 5 == 0 && !n.test_ads && (e = n.getDimensions(), AwesomeAdManager.send_event({
                    prog: n.ad.programmaticUuid,
                    placement: n.placement_id,
                    creative: n.creative_id,
                    line_item: n.line_item_id,
                    type: "viewTime",
                    value: 5,
                    instl: n.interstitial,
                    pos: n.foldPosition || 0,
                    startdelay: n.startdelay,
                    skip: n.skippable,
                    playbackmethod: n.playbackmethod,
                    w: e.width,
                    h: e.height,
                    rnd: c.createUUID(),
                    aua: n.ad.aua,
                    tip: n.ad.tip,
                    show: n.ad.show
                })), setTimeout(t, 1e3))
            }
            var n = this,
                i = 0;
            t()
        }, a.prototype.on_visibility_change = function() {
            var e = this;
            e.is_element_visible() ? (e.register_viewable_impression(), e.startCountingViewTime()) : setTimeout(function() {
                e.on_visibility_change()
            }, 1e3)
        }, a.prototype.register_download_impression = function() {
            var e, t = this;
            t.test_ads || (e = t.getDimensions(), AwesomeAdManager.send_event({
                prog: t.ad.programmaticUuid,
                placement: t.placement_id,
                creative: t.creative_id,
                line_item: t.line_item_id,
                type: "impressionDownloaded",
                instl: t.isInterstitial,
                pos: t.foldPosition || 0,
                startdelay: t.startdelay,
                skip: t.skippable,
                playbackmethod: t.playbackmethod,
                w: e.width,
                h: e.height,
                rnd: c.createUUID(),
                aua: t.ad.aua,
                tip: t.ad.tip,
                show: t.ad.show
            }))
        }, a.prototype.register_viewable_impression = function() {
            var e, t = this;
            t.viewable_impression_registered || (t.viewable_impression_registered = !0, t.test_ads) || "video" === t.creative.format || (e = t.getDimensions(), AwesomeAdManager.send_event({
                prog: t.ad.programmaticUuid,
                placement: t.placement_id,
                creative: t.creative_id,
                line_item: t.line_item_id,
                type: "viewable_impression",
                instl: t.isInterstitial,
                pos: t.foldPosition || 0,
                w: e.width,
                h: e.height,
                rnd: c.createUUID(),
                aua: t.ad.aua,
                tip: t.ad.tip
            }))
        }, a.prototype.register_rex_render_time = function() {
            var e, t, n = this;
            n.test_ads || (e = n.getDimensions(), t = Math.round(n.endRenderTime - n.startRenderTime), AwesomeAdManager.send_event({
                prog: n.ad.programmaticUuid,
                placement: n.placement_id,
                creative: n.creative_id,
                line_item: n.line_item_id,
                type: "rex_render_time",
                value: t,
                instl: n.isInterstitial,
                pos: n.foldPosition || 0,
                w: e.width,
                h: e.height,
                rnd: c.createUUID(),
                aua: n.ad.aua,
                tip: n.ad.tip
            }))
        }, a.prototype.register_moat_events = function() {
            var e, t = this;
            t.moat_event_registered || (t.moat_event_registered = !0, t.test_ads) || "video" == t.creative.format || (AwesomeAdManager.send_moat(t.element, {
                moat_url: "https://z.moatads.com",
                identifier: "superawesomedisplay222485717612",
                moatClientLevel1: t.advertiser_id,
                moatClientLevel2: t.campaign_id,
                moatClientLevel3: t.line_item_id,
                moatClientLevel4: t.creative_id,
                moatClientSlicer1: t.app,
                moatClientSlicer2: t.placement_id,
                moatClientSlicer3: t.publisher_id
            }), e = t.getDimensions(), AwesomeAdManager.send_ias(t.element, {
                ias_url: "https://pixel.adsafeprotected.com/jload",
                anId: "931553",
                chanId: t.placement_id,
                campId: e.width + "x" + e.height,
                pubOrder: t.campaign_id,
                pubCreative: t.creative_id,
                placementId: t.line_item_id,
                pubId: t.publisher_id
            }))
        }, a.prototype.onEmpty = function(e) {
            this.onEmptyCallback = e
        }, a.prototype.getDimensions = function() {
            var e = {
                width: 0,
                height: 0
            };
            return this.creative && this.creative.details && (e.width = this.creative.details.width, e.height = this.creative.details.height), e
        }, a.prototype.isVpaidDisplay = function() {
            return !!this.creative && "video" === this.creative.format && !!this.ad && !0 === this.ad.is_vpaid
        }, a.prototype.setBackgroundColourForVPAID = function() {
            this.isMobileSdk && this.isVpaidDisplay() && (document.body.style.background = "#000000")
        }, a.prototype.getProgressBar = function() {
            var e = document.createElement("div"),
                t = (e.classList.add("progress-bar-animation"), e.style.height = "100%", e.style.width = "0", e.style.borderRadius = "0.4rem", e.style.background = "#256eff", e.style.transition = "#width 0.4s ease", document.createElement("img")),
                n = (t.setAttribute("src", window.cdnUrl + "/images/watermark_2.png"), t.style.width = "67px", t.style.height = "25px", t.style.position = "absolute", t.style.right = "5px", t.style.bottom = "5px", document.createElement("div")),
                i = (n.classList.add("progress-container"), n.style.height = "1.4rem", n.style.width = "32rem", n.style.borderRadius = "0.6rem", n.style.background = "#000", n.style.padding = "0.3rem", document.createElement("div"));
            return i.classList.add("progress-block"), i.id = "vpaidProgressBar", i.style.height = "100%", i.style.width = "100%", i.style.display = "flex", i.style.alignItems = "center", i.style.justifyContent = "center", i.style.border = "none", i.style.position = "absolute", i.style.top = "0", i.style.background = "white", n.appendChild(e), i.appendChild(n), i.appendChild(t), setTimeout(() => {
                let e = 0;
                const t = document.querySelector(".progress-bar-animation"),
                    n = e => {
                        e = String(e) + "%";
                        t.style.width = e
                    };
                const i = setInterval(() => {
                    e += 11.25, n(e), 99 < e + 11.25 && (n(99), clearInterval(i))
                }, 450)
            }, 60), i
        }, a
    }.call();
    var AwesomeInterstitial = function() {
        return function(l, n) {
            var e = this;
            e.ad = n ? new AwesomeDisplay(l, null, null, !0, null, {}).test() : new AwesomeDisplay(l, null, null, !0, null, {}), document.body.appendChild(e.ad.element)
        }
    }.call();

    var _a, aaHost = null != (_a = window.awesomeads_host) ? _a : "",
        VIDEO_SCRIPTS = {
            MOAT: "".concat(aaHost, "/moat/moatvideo.js"),
            INLINE_VIDEO: "".concat(aaHost, "/videojs/inlinevideo.js")
        },
        VPAID_LIBRARY_SCRIPTS = {
            VIDEOJS: "".concat(aaHost, "/videojs/video.min.js"),
            VPAID_PLUGIN: "".concat(aaHost, "/videojs/vpaid/videojs_5.vast.vpaid.min.js")
        },
        VPAID_CSS_SCRIPTS = {
            VIDEOJS: "".concat(aaHost, "/videojs/video-js.min.css"),
            VPAID_PLUGIN: "".concat(aaHost, "/videojs/vpaid/videojs.vast.vpaid.min.css")
        };

    function addScript(t, e) {
        var a = document.createElement("script");
        a.setAttribute("src", t), e && a.addEventListener("load", function() {
            setTimeout(function() {
                return addScript(e)
            }, 10)
        }), document.body.append(a)
    }

    function addStyleSheet(t) {
        var e = document.createElement("link");
        e.setAttribute("rel", "stylesheet"), e.setAttribute("type", "text/css"), e.setAttribute("href", t), document.body.append(e)
    }
    addScript(VIDEO_SCRIPTS.MOAT);
    addScript(VIDEO_SCRIPTS.INLINE_VIDEO);
    addScript(VPAID_LIBRARY_SCRIPTS.VIDEOJS, VPAID_LIBRARY_SCRIPTS.VPAID_PLUGIN);
    addStyleSheet(VPAID_CSS_SCRIPTS.VIDEOJS);
    addStyleSheet(VPAID_CSS_SCRIPTS.VPAID_PLUGIN);
    var AwesomeVideo = function() {
        var d = function() {
            function e() {}
            return e.sendAsyncGET = function(e, t, i) {
                var n = !!window.XDomainRequest,
                    a = new(n ? window.XDomainRequest : XMLHttpRequest);

                function r(e) {
                    var t;
                    return document.implementation && document.implementation.createDocument ? t = (new DOMParser).parseFromString(e, "text/xml") : window.ActiveXObject && (t = new ActiveXObject("Microsoft.XMLDOM")).loadXML(e), t
                }
                n ? (a.onload = function() {
                    var e = a.responseXML;
                    (e = (e = e || r(a.responseText)) || r(a.response)) ? t && t(e): i && i()
                }, a.open("GET", e, !0)) : (a.open("GET", e, !0), a.onreadystatechange = function() {
                    var e;
                    4 === a.readyState && 200 === a.status && ((e = (e = a.responseXML) || r(a.responseText || a.response)) ? t && t(e) : i && i())
                }, a.onerror = function(e) {
                    i && i(e)
                }), a.send()
            }, e.sendEvent = function(t) {
                var i, e = "jsonp_callback_" + this.createUUID();
                window[e] = function(e) {
                    i.parentNode.removeChild(i), console.info("[AA :: Info] Send Event to: " + t)
                }, (i = document.createElement("script")).src = t, document.getElementsByTagName("head")[0].appendChild(i)
            }, e.generateClickEvent = function(e) {
                var t = this.createUUID(),
                    i = window.awesomeads_host,
                    n = window.awesomeads_bundle ? "&bundle=" + window.awesomeads_bundle : "";
                return i + "/event_click" + ("?placement=" + e.placement_id) + ("&rnd=" + t) + ("&line_item=" + e.line_item_id) + ("&creative=" + e.creative.id) + ("&sdkVersion=" + window.aa_sdkVersion) + n
            }, e.generateCPIClick = function(e) {
                var t = "utm_source=",
                    t = (t = (t = (t = (t = (t = (t += "" + window.awesomeads_host.indexOf("staging") != -1 ? 1 : 0) + ("&utm_campaign=" + e.campaign_id)) + ("&utm_term=" + e.line_item_id)) + ("&utm_content=" + e.creative.id)) + ("&utm_medium=" + e.placement_id)).replace(new RegExp("&", "g"), "%26")).replace(new RegExp("=", "g"), "%3D");
                return e.creative.click_url + "&referrer=" + t
            }, e.getBumperSettings = function() {
                var e = "";
                try {
                    WebSDK && WebSDK.bumperSettings && (e = "&" + WebSDK.bumperSettings.getBumperDataAsParam())
                } catch (e) {}
                return e
            }, e.createUUID = function() {
                var i = (new Date).getTime();
                return "xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx".replace(/[xy]/g, function(e) {
                    var t = (i + 16 * Math.random()) % 16 | 0;
                    return i = Math.floor(i / 16), ("x" === e ? t : 3 & t | 8).toString(16)
                })
            }, e
        }.call();

        function e(e, t, i, n, a, r) {
            var l = this;
            r = r || {}, l.id = null, l.ad = a, l.muted = r.muted, l.autoplaying = r.autoplaying, l.startdelay = r.startdelay, l.skippable = r.skippable, l.interstitial = r.isInterstitial, l.playbackmethod = r.playbackmethod, l.foldPosition = r.foldPosition, l.openRtbPartnerId = r.openRtbPartnerId, l.placement_id = e, l.config = {}, l.config.test = t || !1, l.config.isSkippable = l.skippable, l.config.has_small_button = null != n ? n : "true" === c, l.config.parentalGate = r.parentalGate || !1, l.element = i, l.padlock = null, l.manager = null, l.player = null, l.onReadyCallback = null, l.onEmptyCallback = null, l.onErrorCallback = null, l.onFinishedCallback = null
        }
        var a = function() {
                function e() {}
                return e.isValidURL = function(e) {
                    return !!new RegExp("(https?://(?:www.|(?!www))[^s.]+.[^s]{2,}|www.[^s]+.[^s]{2,})").test(e)
                }, e.returnExtension = function(e) {
                    e = e.split(".");
                    return 1 < e.length ? e.pop() : null
                }, e.findFirstInstanceInSiblingsAndChildren = function(e, t) {
                    e = e.getElementsByTagName(t);
                    return 1 <= e.length ? e[0] : null
                }, e.searchSiblingsAndChildrenWithIterator = function(e, t, i) {
                    for (var n = e.getElementsByTagName(t), a = 0; a < n.length; a++) i(n[a])
                }, e.checkSiblingsAndChildrenOf = function(e, t) {
                    return 0 < e.getElementsByTagName(t).length
                }, e.removeAllButFirst = function(e) {
                    return 1 < e.length ? [e[0]] : e
                }, e
            }.call(),
            o = function() {
                function e(e) {
                    var t = this;
                    t.player = e, t.addHandlersForPlayer(), t.parser = new i, t.vastObject = null, t.adQueue = [], t.currentAdIndex = 0, t.currentCreativeIndex = -1, t._cAd = null, t._cCreative = null, t.didParseVASTAndHasAdsResponse = null, t.didParseVASTButDidNotFindAnyAds = null, t.didFindInvalidVASTResponse = null, t.didStartAd = null, t.didStartCreative = null, t.didReachFirstQuartileOfCreative = null, t.didReachMidpointOfCreative = null, t.didReachThirdQuartileOfCreative = null, t.didEndCreative = null, t.didEndAd = null, t.didEndAllAds = null, t.didFindError = null, t.didGoToURL = null, t.didSkipAd = null, t.dispatchToJSBridge = null
                }
                var n = function() {
                    function e() {}
                    return e.sendAsyncGET = function(e, t, i) {
                        var n = !!window.XDomainRequest,
                            a = new(n ? window.XDomainRequest : XMLHttpRequest);

                        function r(e) {
                            var t;
                            return document.implementation && document.implementation.createDocument ? t = (new DOMParser).parseFromString(e, "text/xml") : window.ActiveXObject && (t = new ActiveXObject("Microsoft.XMLDOM")).loadXML(e), t
                        }
                        n ? (a.onload = function() {
                            var e = a.responseXML;
                            (e = (e = e || r(a.responseText)) || r(a.response)) ? t && t(e): i && i()
                        }, a.open("GET", e, !0)) : (a.open("GET", e, !0), a.onreadystatechange = function() {
                            var e;
                            4 === a.readyState && 200 === a.status && ((e = (e = a.responseXML) || r(a.responseText || a.response)) ? t && t(e) : i && i())
                        }, a.onerror = function(e) {
                            i && i(e)
                        }), a.send()
                    }, e.sendEvent = function(t) {
                        var i, e = "jsonp_callback_" + this.createUUID();
                        window[e] = function(e) {
                            i.parentNode.removeChild(i), console.info("[AA :: Info] Send Event to: " + t)
                        }, (i = document.createElement("script")).src = t, document.getElementsByTagName("head")[0].appendChild(i)
                    }, e.generateClickEvent = function(e) {
                        var t = this.createUUID(),
                            i = window.awesomeads_host,
                            n = window.awesomeads_bundle ? "&bundle=" + window.awesomeads_bundle : "";
                        return i + "/event_click" + ("?placement=" + e.placement_id) + ("&rnd=" + t) + ("&line_item=" + e.line_item_id) + ("&creative=" + e.creative.id) + ("&sdkVersion=" + window.aa_sdkVersion) + n
                    }, e.generateCPIClick = function(e) {
                        var t = "utm_source=",
                            t = (t = (t = (t = (t = (t = (t += "" + window.awesomeads_host.indexOf("staging") != -1 ? 1 : 0) + ("&utm_campaign=" + e.campaign_id)) + ("&utm_term=" + e.line_item_id)) + ("&utm_content=" + e.creative.id)) + ("&utm_medium=" + e.placement_id)).replace(new RegExp("&", "g"), "%26")).replace(new RegExp("=", "g"), "%3D");
                        return e.creative.click_url + "&referrer=" + t
                    }, e.getBumperSettings = function() {
                        var e = "";
                        try {
                            WebSDK && WebSDK.bumperSettings && (e = "&" + WebSDK.bumperSettings.getBumperDataAsParam())
                        } catch (e) {}
                        return e
                    }, e.createUUID = function() {
                        var i = (new Date).getTime();
                        return "xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx".replace(/[xy]/g, function(e) {
                            var t = (i + 16 * Math.random()) % 16 | 0;
                            return i = Math.floor(i / 16), ("x" === e ? t : 3 & t | 8).toString(16)
                        })
                    }, e
                }.call();
                return e.prototype.addHandlersForPlayer = function() {
                    var i = this;
                    i.player.setDidFindPlayerReady(function() {}), i.player.setDidStartPlayer(function() {
                        if (null != i._cAd) {
                            for (var e = 0; e < i._cAd.impressions.length; e++) !i._cAd.impressions[e].isSent && i._cAd.impressions[e].url && n.sendEvent(i._cAd.impressions[e].url);
                            i.sendCurrentCreativeTrackers("start"), i.sendCurrentCreativeTrackers("creativeView"), null != i.didStartCreative && i.didStartCreative()
                        }
                    }), i.player.setDidReachFirstQuartile(function() {
                        i.sendCurrentCreativeTrackers("firstQuartile"), null != i.didReachFirstQuartileOfCreative && i.didReachFirstQuartileOfCreative()
                    }), i.player.setDidReachMidpoint(function() {
                        i.sendCurrentCreativeTrackers("midpoint"), null != i.didReachMidpointOfCreative && i.didReachMidpointOfCreative()
                    }), i.player.setDidReachThirdQuartile(function() {
                        i.sendCurrentCreativeTrackers("thirdQuartile"), null != i.didReachThirdQuartileOfCreative && i.didReachThirdQuartileOfCreative()
                    }), i.player.setDidReachEnd(function() {
                        i.sendCurrentCreativeTrackers("complete"), null != i.didEndCreative && i.didEndCreative(), i.progressThroughAds()
                    }), i.player.setDidReachEndOfVPAID(function() {
                        null != i.didEndAllAds && i.didEndAllAds()
                    }), i.player.setDidPlayWithError(function() {
                        if (null != i.didFindError && i.didFindError(), null != i._cAd)
                            for (var e = 0; e < i._cAd.errors.length; e++) n.sendEvent(i._cAd.errors[e])
                    }), i.player.setDidGoToURL(function() {
                        if (null != i._cCreative) {
                            for (var e = 0; e < i._cCreative.clickTracking.length; e++) n.sendEvent(i._cCreative.clickTracking[e]);
                            var t = null;
                            if (i._cCreative.clickThrough) t = i._cCreative.clickThrough;
                            else
                                for (e = 0; e < i._cCreative.clickTracking.length; e++)
                                    if (a.isValidURL(i._cCreative.clickTracking[e])) {
                                        t = i._cCreative.clickTracking[e];
                                        break
                                    }
                            null != i.didGoToURL && null != t && i.didGoToURL(t)
                        }
                    }), i.player.setDidSkipAd(function() {
                        null != i.didSkipAd && i.didSkipAd()
                    })
                }, e.prototype.parseVASTURL = function(e) {
                    var t = this;
                    t.parser.parseVAST(e, e, null, function(e) {
                        t.vastObject = e, !t.vastObject.isVPAID && 0 < t.vastObject.ads.length ? (t.adQueue = t.vastObject.ads, t.currentAdIndex = 0, t.currentCreativeIndex = -1, t._cAd = t.adQueue[t.currentAdIndex], null != t.didParseVASTAndHasAdsResponse && t.didParseVASTAndHasAdsResponse(), null != t.didStartAd && t.didStartAd(), t.progressThroughAds()) : t.vastObject.isVPAID ? t.playWholeAdAsVPAID() : null != t.didParseVASTButDidNotFindAnyAds && t.didParseVASTButDidNotFindAnyAds()
                    }, function() {
                        null != t.didFindInvalidVASTResponse && (t.dispatchToJSBridge("adFailedToLoad"), t.didParseVASTButDidNotFindAnyAds())
                    })
                }, e.prototype.progressThroughAds = function() {
                    var e = this,
                        t = e.adQueue[e.currentAdIndex].creatives.length;
                    e.currentCreativeIndex < t - 1 ? (e.currentCreativeIndex++, e._cCreative = e._cAd.creatives[e.currentCreativeIndex], e.playCurrentAdWithCurrentCreative()) : (null != e.didEndAd && e.didEndAd(), e.currentAdIndex < e.adQueue.length - 1 ? (e.currentCreativeIndex = 0, e.currentAdIndex++, e._cAd = e.adQueue[e.currentAdIndex], e._cCreative = e._cAd.creatives[e.currentCreativeIndex], null != e.didStartAd && e.didStartAd(), e.playCurrentAdWithCurrentCreative()) : (0 === t && null != e.dispatchToJSBridge && e.dispatchToJSBridge("adEmpty"), null != e.didEndAllAds && e.didEndAllAds()))
                }, e.prototype.playCurrentAdWithCurrentCreative = function() {
                    var e = this._cCreative.playableMediaFile;
                    this.player.play(e.url, e.type, !1)
                }, e.prototype.playWholeAdAsVPAID = function() {
                    this.player.play(this.vastObject.urlVPAID, null, !0)
                }, e.prototype.sendCurrentCreativeTrackers = function(e) {
                    if (null != this._cCreative)
                        for (var t = this._cCreative.trackingEvents, i = 0; i < t.length; i++) t[i].event && t[i].url && t[i].event == e && n.sendEvent(t[i].url)
                }, e
            }.call(),
            i = function() {
                var t = function() {
                        function e() {}
                        return e.sendAsyncGET = function(e, t, i) {
                            var n = !!window.XDomainRequest,
                                a = new(n ? window.XDomainRequest : XMLHttpRequest);

                            function r(e) {
                                var t;
                                return document.implementation && document.implementation.createDocument ? t = (new DOMParser).parseFromString(e, "text/xml") : window.ActiveXObject && (t = new ActiveXObject("Microsoft.XMLDOM")).loadXML(e), t
                            }
                            n ? (a.onload = function() {
                                var e = a.responseXML;
                                (e = (e = e || r(a.responseText)) || r(a.response)) ? t && t(e): i && i()
                            }, a.open("GET", e, !0)) : (a.open("GET", e, !0), a.onreadystatechange = function() {
                                var e;
                                4 === a.readyState && 200 === a.status && ((e = (e = a.responseXML) || r(a.responseText || a.response)) ? t && t(e) : i && i())
                            }, a.onerror = function(e) {
                                i && i(e)
                            }), a.send()
                        }, e.sendEvent = function(t) {
                            var i, e = "jsonp_callback_" + this.createUUID();
                            window[e] = function(e) {
                                i.parentNode.removeChild(i), console.info("[AA :: Info] Send Event to: " + t)
                            }, (i = document.createElement("script")).src = t, document.getElementsByTagName("head")[0].appendChild(i)
                        }, e.generateClickEvent = function(e) {
                            var t = this.createUUID(),
                                i = window.awesomeads_host,
                                n = window.awesomeads_bundle ? "&bundle=" + window.awesomeads_bundle : "";
                            return i + "/event_click" + ("?placement=" + e.placement_id) + ("&rnd=" + t) + ("&line_item=" + e.line_item_id) + ("&creative=" + e.creative.id) + ("&sdkVersion=" + window.aa_sdkVersion) + n
                        }, e.generateCPIClick = function(e) {
                            var t = "utm_source=",
                                t = (t = (t = (t = (t = (t = (t += "" + window.awesomeads_host.indexOf("staging") != -1 ? 1 : 0) + ("&utm_campaign=" + e.campaign_id)) + ("&utm_term=" + e.line_item_id)) + ("&utm_content=" + e.creative.id)) + ("&utm_medium=" + e.placement_id)).replace(new RegExp("&", "g"), "%26")).replace(new RegExp("=", "g"), "%3D");
                            return e.creative.click_url + "&referrer=" + t
                        }, e.getBumperSettings = function() {
                            var e = "";
                            try {
                                WebSDK && WebSDK.bumperSettings && (e = "&" + WebSDK.bumperSettings.getBumperDataAsParam())
                            } catch (e) {}
                            return e
                        }, e.createUUID = function() {
                            var i = (new Date).getTime();
                            return "xxxxxxxx_xxxx_4xxx_yxxx_xxxxxxxxxxxx".replace(/[xy]/g, function(e) {
                                var t = (i + 16 * Math.random()) % 16 | 0;
                                return i = Math.floor(i / 16), ("x" === e ? t : 3 & t | 8).toString(16)
                            })
                        }, e
                    }.call(),
                    e = -1,
                    r = 0;

                function l() {
                    this.ads = [], this.isVPAID = !1, this.urlVPAID = null
                }

                function a() {
                    var n = this;
                    n.id = null, n.type = e, n.sequence = null, n.errors = [], n.impressions = [], n.creatives = [], a.prototype.sumAd = function(e) {
                        n.id = e.id, n.sequence = e.sequence, n.errors = n.errors.concat(e.errors), n.impressions = n.impressions.concat(e.impressions);
                        for (var t = 0; t < n.creatives.length; t++)
                            for (var i = 0; i < e.creatives.length; i++) n.creatives[t].sumLinearCreative(e.creatives[i])
                    }
                }

                function n() {
                    this.isSent = !1, this.url = null
                }

                function c() {
                    var t = this;
                    t.id = null, t.type = r, t.sequence = null, t.duration = null, t.clickThrough = null, t.playableMediaFile = null, t.mediaFiles = [], t.trackingEvents = [], t.clickTracking = [], t.customClicks = [], c.prototype.sumLinearCreative = function(e) {
                        t.id = e.id, t.sequence = e.sequence, t.duration = e.duration, null != t.clickThrough && (t.clickThrough = t.clickThrough), null != e.clickThrough && (t.clickThrough = e.clickThrough), t.playableMediaFile && null != t.playableMediaFile.url && (t.playableMediaFile.url = t.playableMediaFile.url), e.playableMediaFile && null != e.playableMediaFile.url && (t.playableMediaFile.url = e.playableMediaFile.url), t.playableMediaFile && null != t.playableMediaFile.type && (t.playableMediaFile.type = t.playableMediaFile.type), e.playableMediaFile && null != e.playableMediaFile.type && (t.playableMediaFile.type = e.playableMediaFile.type), t.playableMediaFile && null != t.playableMediaFile.apiFramework && (t.playableMediaFile.apiFramework = t.playableMediaFile.apiFramework), e.playableMediaFile && null != e.playableMediaFile.apiFramework && (t.playableMediaFile.apiFramework = e.playableMediaFile.apiFramework), t.mediaFiles = t.mediaFiles.concat(e.mediaFiles), t.trackingEvents = t.trackingEvents.concat(e.trackingEvents), t.clickTracking = t.clickTracking.concat(e.clickTracking), t.customClicks = t.customClicks.concat(e.customClicks)
                    }
                }

                function u() {
                    this.event = null, this.url = null
                }

                function p() {
                    var e = this;
                    e.width = null, e.height = null, e.type = null, e.apiFramework = null, e.url = null
                }

                function i() {}
                var h = function() {
                    function e() {}
                    return e.isValidURL = function(e) {
                        return !!new RegExp("(https?://(?:www.|(?!www))[^s.]+.[^s]{2,}|www.[^s]+.[^s]{2,})").test(e)
                    }, e.returnExtension = function(e) {
                        e = e.split(".");
                        return 1 < e.length ? e.pop() : null
                    }, e.findFirstInstanceInSiblingsAndChildren = function(e, t) {
                        e = e.getElementsByTagName(t);
                        return 1 <= e.length ? e[0] : null
                    }, e.searchSiblingsAndChildrenWithIterator = function(e, t, i) {
                        for (var n = e.getElementsByTagName(t), a = 0; a < n.length; a++) i(n[a])
                    }, e.checkSiblingsAndChildrenOf = function(e, t) {
                        return 0 < e.getElementsByTagName(t).length
                    }, e.removeAllButFirst = function(e) {
                        return 1 < e.length ? [e[0]] : e
                    }, e
                }.call();
                return i.prototype.parseVAST = function(e, i, n, a, r) {
                    var l = this;
                    t.sendAsyncGET(e, function(e) {
                        var t = l.parseAdXML(e),
                            e = (null != n && t.sumAd(n), h.findFirstInstanceInSiblingsAndChildren(e, "VASTAdTagURI"));
                        e ? (e = e.firstChild.nodeValue, l.parseVAST(e, i, t, a, r)) : null != a && (e = l.formVASTObject(t, i), a(e))
                    }, function() {
                        r && r()
                    })
                }, i.prototype.formVASTObject = function(e, t) {
                    var i = new l;
                    i.ads = [e];
                    for (var n = 0; n < i.ads.length; n++)
                        for (var e = i.ads[n], a = 0; a < e.creatives.length; a++) "VPAID" === e.creatives[a].playableMediaFile.apiFramework && (i.isVPAID = !0, i.urlVPAID = t);
                    return i.isVPAID && (i.ads = null), i
                }, i.prototype.parseAdXML = function(e) {
                    var t = this,
                        i = new a;
                    return h.searchSiblingsAndChildrenWithIterator(e, "Error", function(e) {
                        null != e.firstChild && i.errors.push(e.firstChild.nodeValue)
                    }), h.searchSiblingsAndChildrenWithIterator(e, "Impression", function(e) {
                        var t = new n;
                        t.isSent = !1, null != e.firstChild && (t.url = e.firstChild.nodeValue, i.impressions.push(t))
                    }), h.searchSiblingsAndChildrenWithIterator(e, "Creative", function(e) {
                        e = t.parseCreativeXML(e);
                        e && i.creatives.push(e)
                    }), i
                }, i.prototype.parseCreativeXML = function(e) {
                    var t = h.checkSiblingsAndChildrenOf(e, "Linear"),
                        d = ["mp4", "flv", "swf"],
                        o = ["video/mp4", "video/x-flv", "application/x-shockwave-flash", "application/javascript"];
                    if (t) {
                        var s = new c;
                        if (s.type = r, s.id = e.getAttribute("id"), s.sequence = e.getAttribute("sequence"), h.searchSiblingsAndChildrenWithIterator(e, "Duration", function(e) {
                                s.duration = e.nodeValue
                            }), h.searchSiblingsAndChildrenWithIterator(e, "ClickThrough", function(e) {
                                s.clickThrough = e && e.firstChild ? e.firstChild.nodeValue : null
                            }), h.searchSiblingsAndChildrenWithIterator(e, "ClickTracking", function(e) {
                                e && e.firstChild && s.clickTracking.push(e.firstChild.nodeValue)
                            }), h.searchSiblingsAndChildrenWithIterator(e, "CustomClicks", function(e) {
                                s.customClicks.push(e.firstChild.nodeValue)
                            }), h.searchSiblingsAndChildrenWithIterator(e, "Tracking", function(e) {
                                var t = new u;
                                t.event = e.getAttribute("event"), t.url = e.firstChild.nodeValue, s.trackingEvents.push(t)
                            }), h.searchSiblingsAndChildrenWithIterator(e, "MediaFile", function(e) {
                                for (var t = new p, i = (t.width = e.getAttribute("width"), t.height = e.getAttribute("height"), t.type = e.getAttribute("type"), t.apiFramework = e.getAttribute("apiFramework"), t.url = null, e.firstChild ? t.url = e.firstChild.nodeValue : e.firstChild.nextSibling && (t.url = e.firstChild.nextSibling.nodeValue), !1), n = !1, a = h.returnExtension(t.url), r = 0; r < d.length; r++)
                                    if (a && -1 < a.indexOf(d[r])) {
                                        i = !0;
                                        break
                                    }
                                for (var l = 0; l < o.length; l++)
                                    if (-1 < t.type.indexOf(o[l])) {
                                        n = !0;
                                        break
                                    }(i || n) && s.mediaFiles.push(t)
                            }), 0 < s.mediaFiles.length) {
                            for (var i = 0; i < s.mediaFiles.length; i++)
                                if ("video/mp4" === (n = s.mediaFiles[i]).type) {
                                    s.playableMediaFile = n;
                                    break
                                }
                            if (null == s.playableMediaFile)
                                for (var n, a = 0; a < s.mediaFiles.length; a++)
                                    if ("video/x-flv" === (n = s.mediaFiles[a]).type) {
                                        s.playableMediaFile = n;
                                        break
                                    }
                            null == s.playableMediaFile && (s.playableMediaFile = s.mediaFiles[0])
                        }
                        return s
                    }
                    return null
                }, i
            }.call(),
            s = function() {
                function o() {
                    this._window = null, this._backlog = [], this._backlogItemLimit = 10, this._messageEventHandler = null, this._isReadyToSendMessages = !1
                }
                o.prototype.start = function(e) {
                    var t = this;
                    t._window = e, t._messageEventHandler = function(e) {
                        "object" == typeof e.data && "ready" === e.data.status && (t._isReadyToSendMessages = !0, t._window.postMessage({
                            status: "ready"
                        }, "*"), t._window.removeEventListener("message", t._messageEventHandler), t._retryMessages(), t._backlog = [])
                    }, t._window.parent.addEventListener("message", t._messageEventHandler)
                }, o.prototype.stop = function() {
                    var e = this;
                    e._messageEventHandler && (e._window.removeEventListener("message", e._messageEventHandler), e._messageEventHandler = null), e._isReadyToSendMessages = !1
                }, o.prototype.changeVolume = function(e) {
                    this._sendMessage("changeVolume", e)
                }, o.prototype.changePlayState = function(e) {
                    this._sendMessage("changePlayState", e)
                }, o.prototype._retryMessages = function() {
                    for (var e = 0; e < this._backlog.length; e++) {
                        var t = this._backlog[e];
                        this._sendMessage(t.command, t.value)
                    }
                }, o.prototype._sendMessage = function(e, t) {
                    this._isReadyToSendMessages ? this._window.postMessage({
                        command: e,
                        value: t
                    }, "*") : this._addToBacklog(e, t)
                }, o.prototype._addToBacklog = function(e, t) {
                    this._backlogItemLimit === this._backlog.length && this._backlog.shift(), this._backlog.push({
                        command: e,
                        value: t
                    })
                };

                function e(e, t, i, n, a, r, l) {
                    var d = this;
                    d.parent = i, d.url = null, d.ad = t, d.muted = l.muted || !1, d.autoplaying = l.autoplaying || !1, d.startdelay = l.startdelay || 0, d.isSkippable = l.skippable || !1, d.interstitial = l.isInterstitial, d.foldPosition = l.foldPosition, d.openRtbPartnerId = l.openRtbPartnerId, l.playbackmethod && 0 < l.playbackmethod.length ? d.playbackmethod = l.playbackmethod : d.playbackmethod = [2], d.ad.placement_id || (d.placement_id = l.placement_id), d.type = null, d.isVPAID = !1, d.MoatApiReference = null, d.id = e, d.hasPadlock = n, d.has_small_button = null != a && a, d.scriptsLoaded = 0, d.cMAX_SCRIPTS_LOADED = 4, d.crossPlayerEventDispatcher = new o, d.div = null, d.video = null, d.cronographBg = null, d.cronograph = null, d.clicker = null, d.skip = null, d.mask = null, d.padlock = null, d.playbtn = null, d.muteBtn = null, d.isReadyHandled = !1, d.isStartHandled = !1, d.isFirstQuartileHandled = !1, d.isMidpointHandled = !1, d.isThirdQuartileHandled = !1, d.isEndHandled = !1, d.isSkipHandled = !1, d.isErrorHandled = !1, d.didFindPlayerReady = null, d.didStartPlayer = null, d.didReachFirstQuartile = null, d.didReachMidpoint = null, d.didReachThirdQuartile = null, d.didReachEnd = null, d.didReachEndOfVPAID = null, d.didPlayWithError = null, d.didGoToURL = null, d.didSkipAd = null, d.dispatchToJSBridge = null, d.listenToJSBridge = null
                }
                var a = window.cdnUrl;
                return e.prototype.playInit = function() {
                    var t = this,
                        i = !1;
                    t.playbackmethod.some(function(e) {
                        return i = t.playMethod(e)
                    }), i || t.playMethod(2)
                }, e.prototype.playMethod = function(e) {
                    var t = this,
                        i = !0;
                    switch (e) {
                        case 1:
                            t.autoplayAllowed = t.muted && t.autoplaying, t.autoplayAllowed ? t.playDefault() : i = !1;
                            break;
                        case 2:
                            t.playMuted();
                            break;
                        case 3:
                            t.playOnClick();
                            break;
                        case 4:
                            t.playOnHover();
                            break;
                        case 5:
                            t.playOnVisible();
                            break;
                        case 6:
                            t.playOnVisibleMuted();
                            break;
                        default:
                            t.playDefault()
                    }
                    return i
                }, e.prototype.playDefault = function() {
                    (this.customPlayer || this.video).play(), this.handleStartedPlayer()
                }, e.prototype.playMuted = function() {
                    var e = this;
                    e.muted = !0, e.configureVolume(), e.div.appendChild(e.muteBtn), e.playDefault()
                }, e.prototype.playOnClick = function() {
                    var t = this;
                    t.div.appendChild(t.playbtn), t.playbtn.addEventListener("click", function(e) {
                        t.div.removeChild(t.playbtn), t.playbtn = null, t.playDefault()
                    }, !1)
                }, e.prototype.configureVolume = function() {
                    var e = this,
                        t = e.muted,
                        i = e.muted ? 0 : 1;
                    try {
                        e.video.muted = t, e.video.volume = i, e.customPlayer.muted(t), e.customPlayer.volume(i)
                    } catch (e) {
                        console.log("[VIDEOJS VOLUME]: ", e)
                    }
                    try {
                        e.crossPlayerEventDispatcher.changeVolume(i)
                    } catch (e) {
                        console.log("[CrossPlayerEventDispatcher] Failed to change volume: ", e)
                    }
                }, e.prototype.playOnHover = function() {
                    var t = this;
                    t.div.appendChild(t.playbtn), t.playbtn.addEventListener("mouseover", function(e) {
                        t.div.removeChild(t.playbtn), t.playbtn = null, t.playDefault()
                    }, !1)
                }, e.prototype.playOnVisibleMuted = function() {
                    this.visibilityCallback = this.playMuted, this.awaitVisibility(.1)
                }, e.prototype.playOnVisible = function() {
                    this.visibilityCallback = this.playDefault, this.awaitVisibility(.1)
                }, e.prototype.play = function(e, t, i) {
                    var n, a = this;
                    a.url = e, a.type = t, a.isVPAID = i, a.div = a.createHoldingDiv(), a.cronographBg = a.createCronographBackground(), a.cronograph = a.createCronograph(), a.clicker = a.createClicker(), a.mask = a.createMask(), a.video = a.isVPAID ? a.createVPAIDVideo() : a.createNormalVideo(), a.skip = a.createSkip(), a.padlock = a.createPadlock(), a.playbtn = a.createPlay(), a.muteBtn = a.createMuteButton(), a.div.appendChild(a.video), a.parent.appendChild(a.div), a.isVPAID ? n = setInterval(function() {
                        window.videojs && (a.playWithDelay(), clearInterval(n))
                    }, 150) : a.playWithDelay()
                }, e.prototype.playWithDelay = function() {
                    var n = this;
                    n.registerMoatEvents(), n.isVPAID ? (videojs.plugin("ads-setup", function(e) {
                        var t = this;
                        t.vastClient({
                            playAdAlways: !0,
                            adTagUrl: n.url,
                            adCancelTimeout: 1e4,
                            adsEnabled: !0,
                            vpaidFlashLoaderPath: a + "videojs/vpaid/VPAIDFlash.swf",
                            AAGlobalData: {
                                placement: n.ad.placement_id,
                                campaign: n.ad.campaign_id,
                                line_item: n.ad.line_item_id,
                                creative: n.ad.creative.id,
                                openRtbPartnerId: n.openRtbPartnerId,
                                show: window.awesomeads_show || "",
                                sdkData: encodeURIComponent(JSON.stringify({
                                    sdkVersion: window.aa_sdkVersion,
                                    show: window.awesomeads_show,
                                    bundle: window.awesomeads_bundle,
                                    domain: window.awesomeads_domain
                                }))
                            }
                        });
                        t.on("firstplay", function() {
                            AwesomeAdManager.track_load_time(n.div, {
                                sdkVersion: window.aa_sdkVersion,
                                lineItemId: n.ad.line_item_id,
                                format: n.ad.creative.format
                            }), null != n.dispatchToJSBridge && n.dispatchToJSBridge("webSDKReady")
                        }), t.on("vast.adStart", function() {
                            AwesomeAdManager.track_render_time(n.div, {
                                sdkVersion: window.aa_sdkVersion,
                                lineItemId: n.ad.line_item_id,
                                format: n.ad.creative.format
                            }), null != n.dispatchToJSBridge && n.dispatchToJSBridge("adShown");
                            try {
                                var e = document.querySelector(`#${n.id} .VPAID-container iframe`);
                                e && n.crossPlayerEventDispatcher.start(e.contentWindow)
                            } catch (e) {
                                console.log("[CrossPlayerEventDispatcher] Could not start the dispatcher:", e)
                            }
                        }), t.on("vast.adEnd", function() {
                            null != n.dispatchToJSBridge && n.dispatchToJSBridge("adEnded"), null == n.didReachEndOfVPAID || n.isEndHandled || (n.isEndHandled = !0, n.didReachEndOfVPAID())
                        }), t.on("vast.adsCancel", function() {
                            null != n.dispatchToJSBridge && n.dispatchToJSBridge("adFailedToLoad"), null != n.didPlayWithError && (n.isErrorHandled = !0, n.didPlayWithError())
                        }), t.on("vast.adError", function() {
                            null != n.dispatchToJSBridge && n.dispatchToJSBridge("adFailedToShow"), null != n.didPlayWithError && (n.isErrorHandled = !0, n.didPlayWithError())
                        }), null != n.listenToJSBridge && (n.listenToJSBridge("appRequestedPause", function() {
                            t.vast && t.vast.adUnit && t.vast.adUnit.pauseAd(), n.crossPlayerEventDispatcher.changePlayState(0), null != n.dispatchToJSBridge && n.dispatchToJSBridge("adPaused")
                        }), n.listenToJSBridge("appRequestedPlay", function() {
                            t.vast && t.vast.adUnit && t.vast.adUnit.resumeAd(), n.crossPlayerEventDispatcher.changePlayState(1), null != n.dispatchToJSBridge && n.dispatchToJSBridge("adPlaying")
                        }))
                    }), videojs(n.id, {
                        autoplay: !0,
                        plugins: {
                            "ads-setup": {}
                        }
                    }).ready(function() {
                        n.div.appendChild(n.mask), n.div.appendChild(n.cronographBg), n.div.appendChild(n.cronograph), null == n.didFindPlayerReady || n.isReadyHandled || (n.isReadyHandled = !0, n.removeProgressBar(), n.didFindPlayerReady()), n.customPlayer = this, n.playInit()
                    })) : (n.video.setAttribute("src", n.url), n.div.appendChild(n.mask), n.div.appendChild(n.cronographBg), n.div.appendChild(n.cronograph), n.div.appendChild(n.clicker), n.playInit(), n.hasPadlock && n.div.appendChild(n.padlock), n.video.addEventListener("ended", function(e) {
                        null == n.didReachEnd || n.isEndHandled || (n.isEndHandled = !0, n.didReachEnd()), n.interval && clearInterval(n.interval), n.cronograph && (n.cronograph.innerHTML = "Ad: 0")
                    }, !1), n.video.onerror = function(e) {
                        null != n.didPlayWithError && (n.isErrorHandled = !0, n.didPlayWithError()), n.cronograph && (n.cronograph.innerHTML = "Error")
                    }, n.video.addEventListener("durationchange", function() {
                        null == n.didFindPlayerReady || n.isReadyHandled || (n.isReadyHandled = !0, n.didFindPlayerReady()), n.interval = setInterval(function() {
                            var e, t, i;
                            (null == n.video || (e = parseInt(n.video.currentTime), t = parseInt(n.video.duration), i = parseInt(t - e), 30 <= e && n.isSkippable && (n.div.appendChild(n.skip), n.isSkippable = !1), .25 * t <= e && !n.isFirstQuartileHandled && null != n.didReachFirstQuartile && (n.isFirstQuartileHandled = !0, n.didReachFirstQuartile()), .5 * t <= e && !n.isMidpointHandled && null != n.didReachMidpoint && (n.isMidpointHandled = !0, n.didReachMidpoint()), .75 * t <= e && !n.isThirdQuartileHandled && null != n.didReachThirdQuartile && (n.isThirdQuartileHandled = !0, n.didReachThirdQuartile()), n.cronograph && (n.cronograph.innerHTML = "Ad: " + i), i <= 0)) && clearInterval(n.interval)
                        }, 1e3)
                    }))
                }, e.prototype.createHoldingDiv = function() {
                    var e = document.createElement("div"),
                        t = (e.style.width = "100%", e.style.height = "100%", e.style.position = "relative", document.createElement("div"));
                    return t.style.width = "100%", t.style.width = "100%", t.style.position = "absolute", t.style.top = "0", e.appendChild(t), e
                }, e.prototype.createVPAIDVideo = function() {
                    var e = document.createElement("video");
                    return e.style.width = "100%", e.style.height = "100%", e.style.backgroundColor = "#000000", e.setAttribute("id", this.id), e.setAttribute("playsinline", !0), e.muted = this.muted, e.setAttribute("class", "video-js vjs-default-skin"), e
                }, e.prototype.createNormalVideo = function() {
                    var e = document.createElement("video");
                    return e.style.width = "100%", e.style.height = "100%", e.style.backgroundColor = "#000000", e.muted = this.muted, e.setAttribute("id", this.id), e.setAttribute("playsinline", !0), e
                }, e.prototype.createCronographBackground = function() {
                    var e = document.createElement("img");
                    return e.style.width = "50px", e.style.height = "20px", e.style.position = "absolute", e.style.left = "5px", e.style.zIndex = 998, e.setAttribute("src", a + "/images/sa_cronograph.png"), e.style.bottom = "5px", e
                }, e.prototype.createCronograph = function() {
                    var e = document.createElement("div");
                    return e.style.width = "50px", e.style.height = "20px", e.style.position = "absolute", e.style.left = "5px", e.style.bottom = "5px", e.style.fontFamily = "Arial", e.innerHTML = "Ad", e.style.color = "white", e.style.zIndex = 999, e.style.fontSize = "10px", e.style.lineHeight = "20px", e.style.textAlign = "center", e
                }, e.prototype.createClicker = function() {
                    var e, t = this;
                    return t.has_small_button ? ((e = document.createElement("div")).style.width = "100px", e.style.height = "20px", e.style.position = "absolute", e.style.left = "60px", e.style.bottom = "5px", e.innerHTML = "Find out more »", e.style.fontFamily = "Arial", e.style.color = "white", e.style.zIndex = 999, e.style.fontSize = "10px", e.style.background = "transparent", e.style.lineHeight = "20px") : ((e = document.createElement("div")).style.width = "100%", e.style.height = "100%", e.style.left = "0", e.style.top = "0", e.style.position = "absolute", e.style.background = "transparent"), e.style.cursor = "pointer", e.addEventListener("click", function(e) {
                        null != t.didGoToURL && t.didGoToURL()
                    }), e
                }, e.prototype.createMuteButton = function() {
                    var t = this,
                        i = document.createElement("img");
                    return i.style.backgroundColor = 16724991, i.style.width = "16px", i.style.height = "16px", i.style.bottom = "6px", i.style.right = "6px", i.style.zIndex = 999, i.style.position = "absolute", i.setAttribute("data-testid", "mute-button"), i.setAttribute("data-state", "muted"), i.setAttribute("src", a + "/images/sa_muted.png"), i.addEventListener("click", function(e) {
                        t.muted = !t.muted, t.configureVolume(), t.muted ? (i.setAttribute("data-state", "muted"), i.setAttribute("src", new URL("/images/sa_muted.png", a).toString())) : (i.setAttribute("data-state", "unmuted"), i.setAttribute("src", new URL("/images/sa_not_muted.png", a).toString()))
                    }), i
                }, e.prototype.createSkip = function() {
                    var t = this,
                        e = document.createElement("div");
                    return e.style.width = "100px", e.style.height = "30px", e.style.position = "absolute", e.style.right = "0px", e.style.bottom = "25px", e.innerHTML = "Skip Ad »", e.style.textAlign = "center", e.style.color = "white", e.style.zIndex = 999, e.style.fontSize = "12px", e.style.background = "rgba(0, 0, 0, 0.5)", e.style.borderTop = "1px solid #afafaf", e.style.borderBottom = "1px solid #afafaf", e.style.borderLeft = "1px solid #afafaf", e.style.lineHeight = "30px", e.style.cursor = "pointer", e.addEventListener("click", function(e) {
                        null != t.didSkipAd && t.didSkipAd()
                    }), e
                }, e.prototype.createMask = function() {
                    var e = document.createElement("img");
                    return e.src = window.cdnUrl + "/images/sa_mark.png", e.style.width = "100%", e.style.height = "30px", e.style.position = "absolute", e.style.left = "0px", e.style.bottom = "0px", e
                }, e.prototype.createPadlock = function() {
                    var e = document.createElement("img");
                    return e.src = window.cdnUrl + "/images/watermark_2.png", e.style.position = "absolute", e.style.zIndex = 1e3, e.style.top = "0px", e.style.left = "0px", e.addEventListener("click", function(e) {
                        window.open(window.awesomeads_host + "/safead", "_blank")
                    }), e.style.setProperty("width", "67px", "important"), e.style.setProperty("height", "25px", "important"), e
                }, e.prototype.createPlay = function() {
                    (playBtnBack = document.createElement("div")).style.width = "100%", playBtnBack.style.height = "100%", playBtnBack.style.left = "0", playBtnBack.style.top = "0", playBtnBack.style.backgroundColor = "rgba(0, 0, 0, 0.25)", playBtnBack.style.zIndex = 1500, playBtnBack.style.position = "absolute", playBtnBack.style.cursor = "pointer";
                    var e = document.createElement("img");
                    return e.src = window.cdnUrl + "/images/play_red.png", e.style.setProperty("width", "48px", "important"), e.style.setProperty("height", "48px", "important"), e.style.position = "absolute", e.style.left = "50%", e.style.top = "50%", e.style.marginLeft = "-24px", e.style.marginTop = "-24px", playBtnBack.appendChild(e), playBtnBack
                }, e.prototype.handleStartedPlayer = function() {
                    var e = this;
                    null == e.didStartPlayer || e.isStartHandled || (e.isStartHandled = !0, e.checkVisibility(), e.didStartPlayer())
                }, e.prototype.isVideoVisible = function(e) {
                    var t = this.div.getBoundingClientRect(),
                        i = t.height * t.width,
                        e = e || .5,
                        n = Math.max(0, t.top),
                        a = Math.max(0, t.left);
                    return e < Math.max(0, Math.min(window.innerHeight - n, t.bottom - n)) * Math.max(0, Math.min(window.innerWidth - a, t.right - a)) / i
                }, e.prototype.isVideoRendering = function() {
                    try {
                        var e = this.div.getBoundingClientRect();
                        return 0 < e.height * e.width
                    } catch (e) {
                        return !1
                    }
                }, e.prototype.checkVisibility = function() {
                    var e = this;
                    e.isVideoRendering() && (e.isVideoVisible() ? e.videoViewableImpression() : setTimeout(function() {
                        e.checkVisibility()
                    }, 1e3))
                }, e.prototype.awaitVisibility = function(e) {
                    var t, i = this;
                    i.isVideoRendering() ? i.isVideoVisible(e) ? i.visibilityCallback() : setTimeout(function() {
                        i.awaitVisibility()
                    }, 1e3) : t = setInterval(function() {
                        i.isVideoRendering() ? clearInterval(t) : setTimeout(clearInterval(t), 5e3)
                    }, 200)
                }, e.prototype.videoViewableImpression = function() {
                    var e, t = this;
                    t.ad.test || (t.foldPosition || (e = document.getElementById(t.id), t.foldPosition = t.getFoldPosition(e)), e = t.getDimensions(), AwesomeAdManager.send_event({
                        prog: t.ad.programmaticUuid,
                        placement: t.ad.placement_id || t.placement_id,
                        creative: t.ad.creative.id,
                        line_item: t.ad.line_item_id,
                        type: "viewable_impression",
                        instl: t.interstitial,
                        pos: t.foldPosition || 0,
                        startdelay: t.startdelay,
                        skip: t.isSkippable,
                        playbackmethod: t.playbackmethod,
                        w: e.width,
                        h: e.height,
                        rnd: d.createUUID(),
                        aua: t.ad.aua,
                        tip: t.ad.tip,
                        show: t.ad.show
                    }))
                }, e.prototype.getFoldPosition = function(e) {
                    try {
                        var t = e.getBoundingClientRect(),
                            i = this.ad.creative.details.height * this.ad.creative.details.width
                    } catch (e) {
                        return 0
                    }
                    var e = Math.max(0, t.top),
                        t = Math.max(0, t.left),
                        n = t + this.ad.creative.details.width,
                        a = e + this.ad.creative.details.height;
                    return .9 < Math.max(0, Math.min(window.innerHeight - e, a - e)) * Math.max(0, Math.min(window.innerWidth - t, n - t)) / i ? 1 : 3
                }, e.prototype.getDimensions = function() {
                    var e = {
                        width: 0,
                        height: 0
                    };
                    return this.creative && this.creative.details && (e.width = this.creative.details.width, e.height = this.creative.details.height), e
                }, e.prototype.removeProgressBar = function() {
                    var e = document.getElementById(window._vpaidProgressBarId);
                    e && (e.style.display = "none")
                }, e.prototype.remove = function() {
                    var e = this,
                        t = e.parent,
                        i = e.parentNode;
                    null != t && t.removeChild && null != e.div && t.removeChild(e.div), null != i && i.removeChild && null != e.div && i.removeChild(e.div), e.div = null
                }, e.prototype.registerMoatEvents = function() {
                    var e = this,
                        t = {
                            level1: e.ad.advertiserId,
                            level2: e.ad.campaign_id,
                            level3: e.ad.line_item_id,
                            level4: e.ad.creative.id,
                            slicer1: e.ad.app,
                            slicer2: e.ad.placement_id,
                            slicer3: e.ad.publisherId
                        };
                    e.MoatApiReference = initMoatTracking(e.video, t, 30, "superawesomejsvideo335241036558")
                }, e.prototype.setDidFindPlayerReady = function(e) {
                    this.didFindPlayerReady = e
                }, e.prototype.setDidStartPlayer = function(e) {
                    this.didStartPlayer = e
                }, e.prototype.setDidReachFirstQuartile = function(e) {
                    this.didReachFirstQuartile = e
                }, e.prototype.setDidReachMidpoint = function(e) {
                    this.didReachMidpoint = e
                }, e.prototype.setDidReachThirdQuartile = function(e) {
                    this.didReachThirdQuartile = e
                }, e.prototype.setDidReachEnd = function(e) {
                    this.didReachEnd = e
                }, e.prototype.setDidReachEndOfVPAID = function(e) {
                    this.didReachEndOfVPAID = e
                }, e.prototype.setDidPlayWithError = function(e) {
                    this.didPlayWithError = e
                }, e.prototype.setDidGoToURL = function(e) {
                    this.didGoToURL = e
                }, e.prototype.setDidSkipAd = function(e) {
                    this.didSkipAd = e
                }, e
            }.call(),
            c = (window.awesomeads_host, window.awesomeads_skippable, window.isHTTPS, window.awesomeads_smallclick);
        return e.prototype.write = function() {
            var i = this;
            AwesomeAdManager.get_ad(i.placement_id, i.config, function(e, t) {
                i.ad = t, i.writePreloaded()
            })
        }, e.prototype.writePreloaded = function() {
            var e, t, i, r, n, l = this;
            if (null != l.ad && null != l.ad.creative && null != l.ad.creative.details) return e = null == l.ad.is_fallback || l.ad.is_fallback, t = l.ad.show_padlock, i = l.ad.device, r = l.ad.campaign_type, l.id = "aa_video_ad_" + ~~(1e7 * Math.random()), n = {
                muted: l.muted,
                autoplaying: l.autoplaying,
                startdelay: l.startdelay,
                skippable: l.skippable,
                interstitial: l.isInterstitial,
                playbackmethod: l.playbackmethod,
                foldPosition: l.foldPosition,
                placement_id: l.placement_id,
                openRtbPartnerId: l.openRtbPartnerId
            }, l.player = new s(l.id, l.ad, l.element, !e && !!t, l.config.has_small_button, i, n), l.manager = new o(l.player), l.manager.dispatchToJSBridge = function(e) {
                null != l.dispatchToJSBridge && l.dispatchToJSBridge(e)
            }, l.player.dispatchToJSBridge = function(e) {
                null != l.dispatchToJSBridge && l.dispatchToJSBridge(e)
            }, l.player.listenToJSBridge = function(e, t) {
                null != l.listenToJSBridge && l.listenToJSBridge(e, t)
            }, l.manager.parseVASTURL(l.ad.creative.details.vast), l.manager.didParseVASTAndHasAdsResponse = function() {}, l.manager.didParseVASTButDidNotFindAnyAds = function() {
                l.removeAll(), l.onEmptyCallback()
            }, l.manager.didFindInvalidVASTResponse = function() {
                l.removeAll(), l.onErrorCallback()
            }, l.manager.didStartAd = function() {
                null != l.onReadyCallback && l.onReadyCallback()
            }, l.manager.didStartCreative = function() {
                l.player.MoatApiReference.dispatchEvent({
                    adVolume: 1,
                    type: "AdPlaying"
                }), l.player.MoatApiReference.dispatchEvent({
                    adVolume: 1,
                    type: "AdVideoStart"
                })
            }, l.manager.didReachFirstQuartileOfCreative = function() {
                l.player.MoatApiReference.dispatchEvent({
                    adVolume: 1,
                    type: "AdVideoFirstQuartile"
                })
            }, l.manager.didReachMidpointOfCreative = function() {
                l.player.MoatApiReference.dispatchEvent({
                    adVolume: 1,
                    type: "AdVideoMidpoint"
                })
            }, l.manager.didReachThirdQuartileOfCreative = function() {
                l.player.MoatApiReference.dispatchEvent({
                    adVolume: 1,
                    type: "AdVideoThirdQuartile"
                })
            }, l.manager.didEndCreative = function() {
                l.player.MoatApiReference.dispatchEvent({
                    adVolume: 1,
                    type: "AdVideoComplete"
                })
            }, l.manager.didEndAd = function() {}, l.manager.didEndAllAds = function() {
                l.removeAll(), null != l.onFinishedCallback && l.onFinishedCallback()
            }, l.manager.didFindError = function() {
                l.removeAll(), null != l.onErrorCallback && l.onErrorCallback()
            }, l.manager.didGoToURL = function(i) {
                function e() {
                    var e, t;
                    1 == r ? (e = d.generateClickEvent(l.ad), t = d.generateCPIClick(l.ad), d.sendAsyncGET(e, function() {
                        console.log("Click event OK: " + e)
                    }, null), window.open(t, "_blank")) : (i += d.getBumperSettings(), window.open(i, "_blank"))
                }
                var t, n, a;
                l.config.parentalGate && (t = Math.floor(99 * Math.random()) + 10, n = Math.floor(99 * Math.random()) + 10, !(a = window.prompt(["Please solve the following problem to continue: ", t, " + ", n, " = ?"].join(""))) || parseInt(a) !== t + n) || e()
            }, l.manager.didSkipAd = function() {
                l.removeAll()
            }, this;
            l.removeAll(), l.onEmptyCallback && l.onEmptyCallback()
        }, e.prototype.removeAll = function() {
            this.player && this.player.remove(), this.ad = null
        }, e.prototype.isEmpty = function(e) {
            for (var t in e)
                if (e.hasOwnProperty(t)) return !1;
            return !0
        }, e.prototype.dispatchToJSBridge = function(e) {
            if (window.SA_AD_JS_BRIDGE) try {
                window.SA_AD_JS_BRIDGE[e]()
            } catch (e) {
                console.log("[AwesomeVideo]: dispatchToJSBridge error: ", e)
            }
        }, e.prototype.listenToJSBridge = function(e, t) {
            try {
                window.addEventListener("SA_AD_JS_BRIDGE." + e, function() {
                    t()
                })
            } catch (e) {
                console.log("[AwesomeVideo]: listenToJSBridge error: ", e)
            }
        }, e.prototype.onReady = function(e) {
            this.onReadyCallback = e
        }, e.prototype.onFinished = function(e) {
            this.onFinishedCallback = e
        }, e.prototype.onEmpty = function(e) {
            this.onEmptyCallback = e
        }, e.prototype.onError = function(e) {
            this.onErrorCallback = e
        }, e
    }.call();

    var AwesomeAds_classes = {
        AwesomeAdManager: AwesomeAdManager,
        AwesomeDisplay: AwesomeDisplay,
        AwesomeVideo: AwesomeVideo,
        AwesomeInterstitial: AwesomeInterstitial
    };

    return AwesomeAds_classes;

}).call(this, window);

var AwesomeAdManager = AwesomeAds.AwesomeAdManager;
var AwesomeDisplay = AwesomeAds.AwesomeDisplay;
var AwesomeVideo = AwesomeAds.AwesomeVideo;
var AwesomeInterstitial = AwesomeAds.AwesomeInterstitial;
var AwesomeAds_Version = 2;


var params = {
    muted: false,
    autoplaying: false,
    startdelay: 0,
    skippable: false,
    playbackmethod: [5],
    openRtbPartnerId: 0
}

new AwesomeDisplay(90636, null, null, false, {
        "programmaticUuid": null,
        "creative": {
            "id": 514375,
            "format": "video",
            "details": {
                "transcodedVideos": [],
                "url": "",
                "image": "",
                "video": "",
                "placement_format": "video",
                "width": 600,
                "height": 480,
                "duration": 0,
                "tag": "",
                "_tag": "tag",
                "vast": "https://eu-west-1-ads.superawesome.tv/v2/video/vast/90636/185516/514375/?sdkVersion=android_9.2.3_admob&rnd=59e5fc0e-4b74-4c7a-b739-ed127fc8b25f&dauid=28812812&bundle=com.superawesome.example&device=phone&country=GB&flow=normal&aua=eyJhbGciOiJIUzI1NiJ9.TW96aWxsYS81LjAgKExpbnV4OyBBbmRyb2lkIDEwKSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBDaHJvbWUvODMuMC40MTAzLjk2IE1vYmlsZSBTYWZhcmkvNTM3LjM2.I_tn7-WYamd5_dlbbCVEZM0gj0A0s3akPbjkX2p2v6E&tip=eyJhbGciOiJIUzI1NiJ9.MzEuNTIuMjQ3LjA.4lsUemcECZfKK9cRSPTt7M3815ZOW6mMqQjNYllHrwQ&lang=en_GB&ct=2"
            },
            "isKSF": true
        },
        "advertiserId": 407,
        "publisherId": 407,
        "is_fill": false,
        "is_fallback": false,
        "campaign_type": 0,
        "is_house": true,
        "safe_ad_approved": false,
        "show_padlock": false,
        "line_item_id": 185516,
        "moat": 0.1,
        "test": false,
        "campaign_id": 51164,
        "app": 43002,
        "device": "phone",
        "aua": "eyJhbGciOiJIUzI1NiJ9.TW96aWxsYS81LjAgKExpbnV4OyBBbmRyb2lkIDEwKSBBcHBsZVdlYktpdC81MzcuMzYgKEtIVE1MLCBsaWtlIEdlY2tvKSBDaHJvbWUvODMuMC40MTAzLjk2IE1vYmlsZSBTYWZhcmkvNTM3LjM2.I_tn7-WYamd5_dlbbCVEZM0gj0A0s3akPbjkX2p2v6E",
        "tip": "eyJhbGciOiJIUzI1NiJ9.MzEuNTIuMjQ3LjA.4lsUemcECZfKK9cRSPTt7M3815ZOW6mMqQjNYllHrwQ",
        "is_vpaid": true,
        "rnd": "7814afb4-d825-4b43-85d7-0dc1d84fd191",
        "show": ""
    }, params)


    .write();


setTimeout(function() {
    var scripts;
    if (document.querySelectorAll) {
        scripts = document.querySelectorAll(".awesome_ad_script");
    } else {
        scripts = document.getElementsByTagName("script");
    }
    for (var i = 0; i < scripts.length; i++) {
        var script = scripts[i];

        if (script.className.indexOf("awesome_ad_script") != -1) {

            var new_script = document.createElement("script");
            new_script.text = script.text;
            window.awesome_ad_script = new_script;
            script.parentNode.insertBefore(new_script, script);
            script.parentNode.removeChild(script);
            window.awesome_ad_script = null;
        }
    }
}, 1);

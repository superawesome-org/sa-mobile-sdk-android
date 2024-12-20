/*! npm.im/iphone-inline-video */
var makeVideoPlayableInline = function() {
    "use strict"; /*! npm.im/intervalometer */
    function e(e, r, n, i) {
        function t(n) {
            d = r(t, i), e(n - (a || n)), a = n
        }
        var d, a;
        return {
            start: function() {
                d || t(0)
            },
            stop: function() {
                n(d), d = null, a = 0
            }
        }
    }

    function r(r) {
        return e(r, requestAnimationFrame, cancelAnimationFrame)
    }

    function n(e, r, n, i) {
        function t(r) {
            Boolean(e[n]) === Boolean(i) && r.stopImmediatePropagation(), delete e[n]
        }
        return e.addEventListener(r, t, !1), t
    }

    function i(e, r, n, i) {
        function t() {
            return n[r]
        }

        function d(e) {
            n[r] = e
        }
        i && d(e[r]), Object.defineProperty(e, r, {
            get: t,
            set: d
        })
    }

    function t(e, r, n) {
        n.addEventListener(r, function() {
            return e.dispatchEvent(new Event(r))
        })
    }

    function d(e, r) {
        Promise.resolve().then(function() {
            e.dispatchEvent(new Event(r))
        })
    }

    function a(e) {
        var r = new Audio;
        return t(e, "play", r), t(e, "playing", r), t(e, "pause", r), r.crossOrigin = e.crossOrigin, r.src = e.src || e.currentSrc || "data:", r
    }

    function o(e, r, n) {
        (m || 0) + 200 < Date.now() && (e[b] = !0, m = Date.now()), n || (e.currentTime = r), A[++k % 3] = 100 * r | 0
    }

    function u(e) {
        return e.driver.currentTime >= e.video.duration
    }

    function s(e) {
        var r = this;
        r.video.readyState >= r.video.HAVE_FUTURE_DATA ? (r.hasAudio || (r.driver.currentTime = r.video.currentTime + e * r.video.playbackRate / 1e3, r.video.loop && u(r) && (r.driver.currentTime = 0)), o(r.video, r.driver.currentTime)) : r.video.networkState !== r.video.NETWORK_IDLE || r.video.buffered.length || r.video.load(), r.video.ended && (delete r.video[b], r.video.pause(!0))
    }

    function c() {
        var e = this,
            r = e[h];
        return e.webkitDisplayingFullscreen ? void e[E]() : ("data:" !== r.driver.src && r.driver.src !== e.src && (o(e, 0, !0), r.driver.src = e.src), void(e.paused && (r.paused = !1, e.buffered.length || e.load(), r.driver.play(), r.updater.start(), r.hasAudio || (d(e, "play"), r.video.readyState >= r.video.HAVE_ENOUGH_DATA && d(e, "playing")))))
    }

    function v(e) {
        var r = this,
            n = r[h];
        n.driver.pause(), n.updater.stop(), r.webkitDisplayingFullscreen && r[T](), n.paused && !e || (n.paused = !0, n.hasAudio || d(r, "pause"), r.ended && (r[b] = !0, d(r, "ended")))
    }

    function p(e, n) {
        var i = e[h] = {};
        i.paused = !0, i.hasAudio = n, i.video = e, i.updater = r(s.bind(i)), n ? i.driver = a(e) : (e.addEventListener("canplay", function() {
            e.paused || d(e, "playing")
        }), i.driver = {
            src: e.src || e.currentSrc || "data:",
            muted: !0,
            paused: !0,
            pause: function() {
                i.driver.paused = !0
            },
            play: function() {
                i.driver.paused = !1, u(i) && o(e, 0)
            },
            get ended() {
                return u(i)
            }
        }), e.addEventListener("emptied", function() {
            var r = !i.driver.src || "data:" === i.driver.src;
            i.driver.src && i.driver.src !== e.src && (o(e, 0, !0), i.driver.src = e.src, r ? i.driver.play() : i.updater.stop())
        }, !1), e.addEventListener("webkitbeginfullscreen", function() {
            e.paused ? n && !i.driver.buffered.length && i.driver.load() : (e.pause(), e[E]())
        }), n && (e.addEventListener("webkitendfullscreen", function() {
            i.driver.currentTime = e.currentTime
        }), e.addEventListener("seeking", function() {
            A.indexOf(100 * e.currentTime | 0) < 0 && (i.driver.currentTime = e.currentTime)
        }))
    }

    function l(e) {
        var r = e[h];
        e[E] = e.play, e[T] = e.pause, e.play = c, e.pause = v, i(e, "paused", r.driver), i(e, "muted", r.driver, !0), i(e, "playbackRate", r.driver, !0), i(e, "ended", r.driver), i(e, "loop", r.driver, !0), n(e, "seeking"), n(e, "seeked"), n(e, "timeupdate", b, !1), n(e, "ended", b, !1)
    }

    function f(e, r, n) {
        void 0 === r && (r = !0), void 0 === n && (n = !0), n && !g || e[h] || (p(e, r), l(e), e.classList.add("IIV"), !r && e.autoplay && e.play(), /iPhone|iPod|iPad/.test(navigator.platform) || console.warn("iphone-inline-video is not guaranteed to work in emulated environments"))
    }
    var m, y = "undefined" == typeof Symbol ? function(e) {
            return "@" + (e || "@") + Math.random()
        } : Symbol,
        g = /iPhone|iPod/i.test(navigator.userAgent) && !matchMedia("(-webkit-video-playable-inline)").matches,
        h = y(),
        b = y(),
        E = y("nativeplay"),
        T = y("nativepause"),
        A = [],
        k = 0;
    return f.isWhitelisted = g, f
}();
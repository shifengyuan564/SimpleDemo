/**
 * 公共js封装组件
 *
 * @author NIXIANG
 *
 * {@link Commons.parent()} 功能页中获取父级页面, 直接用<b>parent</b>会获取到最外层的frameset
 * {@link Commons.random()} 获取随机数
 * {@link Commons.replace()} 跳转页面
 *
 * @type {*}
 */
Commons = (function() {

    return {

        /**
         * 功能页中获取父级页面, 直接用<b>parent</b>会获取到最外层的frameset
         *
         * @return {*}
         */
        parent : function() {
            return parent.frames["main"];
        },

        /**
         * 获取随机数
         *
         * @return {Number}
         */
        random : function() {
            return parseInt(10000 * Math.random());
        },

        /**
         * 跳转页面
         *
         * @param _url_ {@link window.location} href
         * @param _address_ 地址
         */
        replace : function(_url_, _address_) {
            _url_.replace(_address_);
        },

        /**
         * 弹出窗口
         *
         * @param _context_ 内容
         * @param _title_ 标题
         * @param _width_ 长度
         * @param _type_ 类型
         * @param _call_back_ 回调函数
         */
        alert : function(_context_, _title_, _width_, _type_, _call_back_) {
            if (undefined == _width_) {
                _width_ = 350;
            }

            $.jqm.alert({
                w : _width_,
                title : _title_,
                type : _type_,
                content : _context_,
                self : self,
                onHide : _call_back_
            });
        },
        /**
         * 弹出窗口
         *
         * @param _context_ 内容
         * @param _title_ 标题
         * @param _width_ 长度
         * @param _type_ 类型
         * @param _call_back_ 回调函数
         */
        configAlert : function(_context_, _title_, _width_, _type_, _call_back_) {
            if (undefined == _width_) {
                _width_ = 260;
            }

            $.jqm.oneConfirm({
                w : _width_,
                title : _title_,
                type : _type_,
                content : _context_,
                self : self,
                onHide : _call_back_
            });
        },


        /**
         * 错误提示
         *
         * @param _context_ 内容
         * @param _title_ 标题
         * @param _width_ 长度
         * @param _call_back_ 回调函数
         */
        showError : function(_context_, _title_, _width_, _call_back_) {
            if (undefined == _title_) {
                _title_ = "错误";
            } else {
                if ($.isFunction(_title_)) {
                    _call_back_ = _title_;
                    _title_ = "错误";
                } else if (typeof(_title_) == "number") {
                    if (undefined != _width_) {
                        if ($.isFunction(_width_)) {
                            _call_back_ = _width_;
                        }
                    }
                    _width_ = _title_;
                    _title_ = "错误";
                }
            }
            if (undefined != _width_) {
                if ($.isFunction(_width_)) {
                    _call_back_ = _width_;
                    _width_ = undefined;
                }
            }

            Commons.alert(_context_, _title_, _width_, "error", _call_back_);
        },

        /**
         * 警告提示
         *
         * @param _context_ 内容
         * @param _title_ 标题
         * @param _width_ 长度
         * @param _call_back_ 回调函数
         */
        showWarn : function(_context_, _title_, _width_, _call_back_) {
            if (undefined == _title_) {
                _title_ = "警告";
            } else {
                if ($.isFunction(_title_)) {
                    _call_back_ = _title_;
                    _title_ = "警告";
                } else if (typeof(_title_) == "number") {
                    if (undefined != _width_) {
                        if ($.isFunction(_width_)) {
                            _call_back_ = _width_;
                        }
                    }
                    _width_ = _title_;
                    _title_ = "警告";
                }
            }
            if (undefined != _width_) {
                if ($.isFunction(_width_)) {
                    _call_back_ = _width_;
                    _width_ = undefined;
                }
            }

            Commons.alert(_context_, _title_, _width_, "alert", _call_back_);
        },

        /**
         * 普通提示
         *
         * @param _context_ 内容
         * @param _title_ 标题
         * @param _width_ 长度
         * @param _call_back_ 回调函数
         */
        showInfo : function(_context_, _title_, _width_, _call_back_) {
            if (undefined == _title_) {
                _title_ = "提示";
            } else {
                if ($.isFunction(_title_)) {
                    _call_back_ = _title_;
                    _title_ = "提示";
                } else if (typeof(_title_) == "number") {
                    if (undefined != _width_) {
                        if ($.isFunction(_width_)) {
                            _call_back_ = _width_;
                        }
                    }
                    _width_ = _title_;
                    _title_ = "提示";
                }
            }
            if (undefined != _width_) {
                if ($.isFunction(_width_)) {
                    _call_back_ = _width_;
                    _width_ = undefined;
                }
            }

            Commons.alert(_context_, _title_, _width_, "attention", _call_back_);
        },

        /**
         * 成功提示
         *
         * @param _context_ 内容
         * @param _title_ 标题
         * @param _width_ 长度
         * @param _call_back_ 回调函数
         */
        showSuccess : function(_context_, _title_, _width_, _call_back_) {
            if (undefined == _title_) {
                _title_ = "成功";
            } else {
                if ($.isFunction(_title_)) {
                    _call_back_ = _title_;
                    _title_ = "成功";
                } else if (typeof(_title_) == "number") {
                    if (undefined != _width_) {
                        if ($.isFunction(_width_)) {
                            _call_back_ = _width_;
                        }
                    }
                    _width_ = _title_;
                    _title_ = "成功";
                }
            }
            if (undefined != _width_) {
                if ($.isFunction(_width_)) {
                    _call_back_ = _width_;
                    _width_ = undefined;
                }
            }

            Commons.alert(_context_, _title_, _width_, "success", _call_back_);
        },
        showConfigSuccess : function(_context_, _title_, _width_, _call_back_) {
            if (undefined == _title_) {
                _title_ = "成功";
            } else {
                if ($.isFunction(_title_)) {
                    _call_back_ = _title_;
                    _title_ = "成功";
                } else if (typeof(_title_) == "number") {
                    if (undefined != _width_) {
                        if ($.isFunction(_width_)) {
                            _call_back_ = _width_;
                        }
                    }
                    _width_ = _title_;
                    _title_ = "成功";
                }
            }
            if (undefined != _width_) {
                if ($.isFunction(_width_)) {
                    _call_back_ = _width_;
                    _width_ = undefined;
                }
            }

            Commons.configAlert(_context_, _title_, _width_, "success", _call_back_);
        }
    }
})();

$(document).ready(function() {
    Date.prototype.format = function(format) {

        var o = {
            "M+" :this.getMonth() + 1,
            "d+" :this.getDate(),
            "h+" :this.getHours(),
            "m+" :this.getMinutes(),
            "s+" :this.getSeconds(),
            "q+" :Math.floor((this.getMonth() + 3) / 3),
            "S" :this.getMilliseconds()
        }

        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
        }

        for (var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                    : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }
});

function replaceWindowOpen(url,target){
    var e = document.createElement("a");
    e.href = url;
//    e.target = "_blank";
    if(null != target && ""!=target){
        e.target = target
    }
    document.body.appendChild(e);
    if (document.dispatchEvent) {
        var o = document.createEvent('MouseEvents');
        o.initMouseEvent('click', true, true, window, 1, 1, 1, 1, 1, false, false, false, false, 0, e);
        e.dispatchEvent(o)
    }else{
        e.click()
    }

//    if (document.dispatchEvent) {
//        var o = document.createEvent('MouseEvents');
//        o.initMouseEvent('click', true, true, window, 1, 1, 1, 1, 1, false, false, false, false, 0, e);
//        e.dispatchEvent(o)
//    } else if (document.fireEvent) {
//        e.fireEvent('onclick');
//    } else if (e.click()) {
//        e.click()
//    }

}

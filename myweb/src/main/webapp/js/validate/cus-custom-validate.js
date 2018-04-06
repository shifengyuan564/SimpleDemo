/**
 * Created by sfy on 2017/2/9.
 */

Array.prototype.Exists = function (val) {
    var result = false;
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) {
            result = true;
            break;
        }
    }
    return result;
};

// 中文字两个字节
$.validator.addMethod("checkTwoCN", function (value, element, param) {
    var length = value.length;
    for (var i = 0; i < value.length; i++) {
        if (value.charCodeAt(i) > 127) {
            length++;
        }
    }
    return this.optional(element) || ( length >= param[0] && length <= param[1] );
}, $.validator.format("请确保输入的值在{0}-{1}个字节之间(一个中文字算2个字节)"));


// 能输一个字母,范围是a-f
$.validator.addMethod("A_TO_F", function (value, element, params) {
    if (value.length > 1) {
        return false;
    }

    var result = (value >= params[0] && value <= params[1]) ? true : false;
    return this.optional(element) || resutl;

}, function (param, element) {
    return '输入是a-f';
});


/*征免性质规则：进出口标志等于E，监管方式等于4019，征免性质应为101、898、998、999*/
$.validator.addMethod("A005", function (value, element, params) {

    var field_1 = $('input[name="' + params[0] + '"]').val(),
        field_2 = $('input[name="' + params[1] + '"]').val();

    var arr = [101,898,998,999];
    var result = true;

    if (field_1 == "E" && field_2 == "4019" && !arr.Exists(value)) {
        result = false;
    }
    return this.optional(element) || result;

}, function (param, element) {
    return '进出口标志等于E，监管方式等于4019时，征免性质应为：101、898、998、999';
});
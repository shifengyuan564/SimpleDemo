$(document).ready(function () {
    "use strict";

    // 校验
    validatorDefine.insertLinkValidator();
    validatorDefine.cusRuleValidator();


    $('#saveBtn2').on('click', function (e) {
        e.preventDefault();
        if ($("#insertForm2").valid()) {
            alert("保存成功");
        }
    });

    $('#saveBtn').on('click', function (e) {
        e.preventDefault();
        if ($("#cusRuleForm").valid()) {
            alert("保存成功");
        }
    });
});

var validatorDefine = {
    insertLinkValidator: function () {
        $('#insertForm2').validate({
            rules: {
                title2: {required: true},
                address2: {
                    required: true,
                    minlength: 5
                },
                num2: {
                    required: true,
                    A_TO_F: ["a", "f"]
                }
            },
            messages: {
                title2: {required: "请填写名称"},
                address2: {
                    required: "请填写地址",
                    minlength: "地址不能小于 5 个字母"
                },
                num2: {
                    required: "请填写号码",
                    A_TO_F: "输入的号码不行"
                }
            },
            errorPlacement: function (error, element) {
                // 去重
                var isDuplicated = false;

                $.each($('.alertValidate label'), function (_index, _obj) {
                    if (error[0].textContent === $(_obj).text()) {
                        isDuplicated = true;
                    }
                });
                if (!isDuplicated) {
                    error.appendTo($(".alertValidate"));
                }
            },
            success: function (label) {
                var labelId = $(label)[0].id;
                $.each($('.wrongInput label'), function (_index, _obj) {
                    if ($('.wrongInput label#'+labelId).text() === $(_obj).text()) {
                        $(_obj).remove();
                    }
                });
            },
            errorContainer: ".alertValidate",
            errorLabelContainer: $("#insertForm2 .alertValidate")
        });
    },

    cusRuleValidator: function () {
        $('#cusRuleForm').validate({
            rules: {
                inoutLabel: {
                    required: true
                },
                supervise: {
                    required: true
                },
                noNeed: {
                    required: true,
                    number: true,
                    A005: ["inoutLabel", "supervise"]
                }
            },
            messages: {
                inoutLabel: {
                    required: "请填写进出口标志"
                },
                supervise: {
                    required: "请填写监管方式"
                },
                noNeed: {
                    required: "请填写征免性质 ",
                    number: "征免性质应该为数字"
                }
            },
            errorPlacement: function (error, element) {
                // 去重
                var isDuplicated = false;

                $.each($('.wrongInput label'), function (_index, _obj) {
                    if (error[0].textContent === $(_obj).text()) {
                        isDuplicated = true;
                    }
                });
                if (!isDuplicated) {
                    error.appendTo($(".wrongInput"));
                }
            },
            success: function (label) {
                var labelId = $(label)[0].id;
                $.each($('.wrongInput label'), function (_index, _obj) {
                    if ($('.wrongInput label#'+labelId).text() === $(_obj).text()) {
                        $(_obj).remove();
                    }
                });

            },
            errorContainer: ".wrongInput",
            errorLabelContainer: $("#cusRuleForm .wrongInput")
        });

    }
};



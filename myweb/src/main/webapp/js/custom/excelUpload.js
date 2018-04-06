var uploadData = [], uploadDataCount = 0;


$(document).ready(function () {
    $('#fileupload').fileupload({
        dataType: 'json',
        acceptFileTypes: /(\.|\/)(gif|jpe?g|png|doc|xls|xlsx|ppt|rar|zip)$/i,
        maxNumberOfFiles: 5,
        maxFileSize: 5000000,
        add: function (e, data) {

            $('.progress').hide(function () {
                $('#progressBar').css({'width': '10%'})
                $('#progressBar').attr('aria-valuenow', 10);
                $('#progressBar').text('10%');
            });
            uploadData.push(data);
            uploadDataCount = uploadDataCount + 1;

            $(".fileinput-button").css("pointer-events", "none");
            $(".fileinput-button").css("background-color", "rgba(92, 184, 92, 0.5)");
            $(".fileinput-button").css("color", "rgba(255, 255, 255, 0.5)");
        },
        stop:function(e,data){
            $('.shadowContainer').hide();
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            console.log(progress);
            $('.progress').show(function () {
                $('#progressBar').css({'width': progress + '%'});
                $('#progressBar').attr('aria-valuenow', progress);
                $('#progressBar').text(progress + '%');
            });
        }
    });

    $('#fileupload').on('change', function () {
        if ($(this)[0] && $(this)[0].files && $(this)[0].files.length == 1) {
            console.log($(this)[0].files[0].name)
            $("#selectedFilesName").text("已选择文件： " + $(this)[0].files[0].name);
            
            $('#uploadBtn').removeAttr("disabled");
            $('#downloadBtn').removeAttr("disabled");

            $('#uploadedInput').val('');
        }
    });


    $('#uploadBtn').on('click', function () {
        $('.shadowContainer').show();
        $.each(uploadData, function (_index, _obj) {
            _obj.submit();
        });
        $('#uploadBtn').attr("disabled", "disabled");
        uploadData = [];
    });

    // 跳转到下载页面
    $('#downloadBtn').on('click', function () {
        window.location.href = '/excel/download';
    });

});
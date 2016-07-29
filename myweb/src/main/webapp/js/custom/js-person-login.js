/**
 * Created by shifengyuan on 2016/1/7.
 */


$(document).ready(function () {
    $("#profile").click(function () {
        profile();
    });
    $("#login").click(function () {
        login();
    });
    $("#expt").click(function () {
        jumpToException();
    });
});

function profile() {
    "use strict";

    var url = 'http://localhost/person/profile/';
    var query = $('#id').val() + '/' + $('#name').val() + '/'
        + $('#status').val()+ '/'+$('#birth').val();
    url += query;
    alert(url);
    $.get(url, function (data) {
        alert("id: " + data.id + "\nname: " + data.name + "\nstatus: " + data.status+"\nbirth: "+$('#birth').val());
    });
}


function login() {
    var mydata = '{"name":"' + $('#name').val() + '","id":"'+ $('#id').val() + '","status":"' + $('#status').val()
         + '","score":"' + $('#score').val()+'","birth":"' + $('#birth').val()+'"}';

    //var mydata = '{"name":"' + $('#name').val() + '","id":"'+ $('#id').val()+'"}';

    alert(mydata);
    $.ajax({
        type: 'POST',
        contentType: 'application/json', //x-www-form-urlencoded; charset=utf-8
        url: 'http://localhost/person/login',
        processData: false,
        dataType: 'json',
        data: mydata,
        success: function (data) {
            alert("id: " + data.id + "\nname: " + data.name + "\nstatus: "
                + data.status +  "\nscore: " + data.score +"\nbirth: " + data.birth);
        },
        error: function () {
            alert('ajax 返回 Error...');
        }
    });
}

function jumpToException() {
    var curDate = new Date();
    var curTime = curDate.toLocaleTimeString(); //获取当前时间

    $.ajax({
        type: 'POST',
        contentType: 'application/json',
        url: 'http://localhost/person/expt',
        processData: false,
        dataType: 'json',
        success: function (data) {
            alert("异常返回信息：\nresultCode: " + data.resultCode + "\nresultMsg: " + data.resultMsg);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            Commons.showError("ajax 返回 Error...（controller层抛异常）",function(){$("#jqmConfirm").css("margin-top", "100px");});
            //document.write(jqXHR.responseText);             // 把 responseText作为html内容覆盖现有的页面, 或者 $(body).html(jqXHR.responseText);
        }
    });


}

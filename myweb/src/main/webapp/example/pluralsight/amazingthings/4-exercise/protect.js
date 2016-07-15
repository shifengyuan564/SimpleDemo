// ==UserScript==
// @name       test monkey插件安装后，可以把这个脚本加到任何页面里
// @namespace  http://use.i.E.your.homepage/
// @version    0.1
// @description  enter something useful
// @match      http://www.pluralsight.com/
// @copyright  You
// ==/UserScript==

var timeout = 10 * 1000;

function time() {
    return new Date().getTime();
}

function updateTime() {
    localStorage["time"] = time().toString();
}

function askForPass() {
    if (prompt("Password:") === "abc") {
        updateTime();
    } else {
        document.body.innerHTML = "";
    }
}

function checkValid() {
    if (time() - parseInt(localStorage["time"]) > timeout) {
        askForPass();
    }
}

if (localStorage["time"] === undefined) {
    localStorage["time"] = time().toString();
}

document.body.onblur = updateTime;
window.onfocus = checkValid;
checkValid();
const $ = layui.$;
let layer = layui.layer
let upload = layui.upload;
let table = layui.table;
let dropdown = layui.dropdown;
let layEdit = layui.layedit;
let form = layui.form;//引入form模块


function setActive(id) {
    $("#" + id).addClass("layui-this");
}


function downloadByUrl({
                           url,
                           target = '_blank',
                           fileName,
                       }) {
    const isChrome = window.navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
    const isSafari = window.navigator.userAgent.toLowerCase().indexOf('safari') > -1;

    if (/(iP)/g.test(window.navigator.userAgent)) {
        console.error('Your browser does not support download!');
        return false;
    }
    if (isChrome || isSafari) {
        const link = document.createElement('a');
        link.href = url;
        link.target = target;

        if (link.download !== undefined) {
            link.download = fileName || url.substring(url.lastIndexOf('/') + 1, url.length);
        }

        if (document.createEvent) {
            const e = document.createEvent('MouseEvents');
            e.initEvent('click', true, true);
            link.dispatchEvent(e);
            return true;
        }
    }
    if (url.indexOf('?') === -1) {
        url += '?download';
    }

    openWindow(url, {target});
    return true;
}

function openWindow(url, opt) {
    const {target = '__blank', noopener = true, noreferrer = true} = opt || {};
    const feature = [];
    noopener && feature.push('noopener=yes');
    noreferrer && feature.push('noreferrer=yes');
    window.open(url, target, feature.join(','));
}
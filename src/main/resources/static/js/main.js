const $ = layui.$;
let layer = layui.layer
let upload = layui.upload;
let table = layui.table;
let dropdown = layui.dropdown;
let layEdit = layui.layedit;
let form = layui.form;//引入form模块



function setActive(id){
    $("#" + id).addClass("layui-this");
}
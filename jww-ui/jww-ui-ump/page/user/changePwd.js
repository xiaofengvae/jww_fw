layui.config({
    base: "../../js/"
}).use(['base', 'form', 'layer', 'jquery', 'laydate', 'laypage'], function () {
    var base = layui.base,
        form = layui.form,
        layer = parent.layer === undefined ? layui.layer : parent.layer,
        $ = layui.jquery;

    var initData = function () {
        var account = layui.data("JWW_UMP").CUURENT_USER.account;
        $("#account").val(account);
    };
    initData();

    // 添加验证规则
    form.verify({
        oldPwd: function (value, item) {
        },
        newPwd: function (value, item) {
            if (value.length < 6) {
                return "密码长度不能小于6位";
            }
        },
        confirmPwd: function (value, item) {
            var password = $("#password").val();
            if (password !== value) {
                return "两次输入密码不一致，请重新输入！";
            }
        }
    });

    // 表单提交
    form.on('submit(changePwd)', function (data) {
        var loadingIndex = base.loading(layer);
        $.ajax({
            type: 'POST',
            url: 'user/modifyPassword',
            data: JSON.stringify(data.field),
            success: function (data) {
                layer.close(loadingIndex);
                if (data.code === 200) {
                    layer.msg('密码修改成功', {icon: 1});
                    $("#oldPassword").val("");
                    $("#password").val("");
                    $("#confirmPwd").val("");
                } else {
                    layer.msg(data.message, {icon: 2});
                }
            }
        });

        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });
});


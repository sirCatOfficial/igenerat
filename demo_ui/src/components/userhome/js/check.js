var validateUsername = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入用户名'));
    }else {
        callback();
    }
};
var validatePass = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请输入密码'));
    } else {
        callback();
    }
};
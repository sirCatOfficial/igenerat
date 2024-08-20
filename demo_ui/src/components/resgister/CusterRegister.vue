<template>
    <div id="register_bg">
        <!-- <div id="register_info">
            <div id="register_info_title">
                <div id="register_logo">
                </div>
                <h1>欢迎来到公司业务系统</h1>
            </div>
        </div> -->
        <div id="register_form">
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="单位名" prop="name">
                    <el-input type="text" v-model="ruleForm.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="手机号" prop="phone">
                    <el-input type="text" v-model="ruleForm.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="验证码" prop="code">
                    <el-input type="text" v-model="ruleForm.code"></el-input>
                    <el-button @click="getCode(ruleForm.phone)">获取验证码</el-button>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="ruleForm.password" autocomplete="off" show-password></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="checkPassword">
                    <el-input type="password" v-model="ruleForm.checkPassword" autocomplete="off" show-password></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>

                </el-form-item>
                <el-form-item>
                    <router-link to="/clogin">
                        <el-button type="warning" @click="resetForm('ruleForm')"><span
                                id="tologin">已有账号？</span>去登录</el-button>
                    </router-link>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
<script>
import request from '@/utils/request';
import axios from 'axios';
import './css/register.css'

export default {
    data() {
        var validateName = (rule, value, callback) => {
            if (!value) {
                return callback(new Error("员工号不能为空"))
            } else {
                callback();
            }
        }
        var validatePhone = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('账号不能为空'));
            } else {
                callback();
            }
        };
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.ruleForm.checkPass !== '') {
                    this.$refs.ruleForm.validateField('checkPassword');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.ruleForm.password) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            ruleForm: {
                name: '',
                phone: '',
                code: '',
                password: '',
                checkPassword: '',
            },
            rules: {
                name: [{ validator: validateName, trigger: 'blur' }],
                phone: [{ validator: validatePhone, trigger: 'blur' }],
                password: [
                    { validator: validatePass, trigger: 'blur' },
                    { min: 8, max: 12, message: '长度在 8 到 12 个字符' }
                ],
                checkPassword: [
                    { validator: validatePass2, trigger: 'blur' },
                    { min: 8, max: 12, message: '长度在 8 到 12 个字符' }
                ]
            }
        };
    },
    methods: {
        submitForm(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    request({
                        method: 'post',
                        url: '/customer/register',
                        params: {
                            name: this.ruleForm.name,
                            phone: this.ruleForm.phone,
                            code: this.ruleForm.code,
                            password: this.ruleForm.password
                        }
                    }).then(res => {
                        if (res.data.isSuccess) {
                            this.$message.success("注册成功！");
                            this.$router.push("/login");
                        } else {
                            this.$message.warning(res.data.results);
                        }
                        console.log(res);
                    })
                    // alert('submit!');
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        getCode(phone) {
            if(phone==null||phone==''){
                this.$message.warning("输入手机号")
            }else{
                request({
                    url: '/msm/send',
                    method: 'get',
                    params: {
                        phone: phone,
                    }
                }).then(res=>{
                    console.log(res.data)
                })
            }
        }
    }
}
</script>
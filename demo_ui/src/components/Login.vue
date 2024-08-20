<template>
    <div id="login_bg"> 
        <!-- <div id="login_info">
            <div id="login_info_title">
                <div id="login_logo">
                </div>   
                <h1>公司业务系统</h1>
            </div>
        </div> -->
        <div id="login_form">
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="手机号" prop="phone">
                    <el-input type="text" v-model="ruleForm.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="ruleForm.password" autocomplete="off" show-password></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
                <el-form-item>
                    <router-link to="/register">
                        <el-button type="warning" >没有账号？<span id="register">请注册</span></el-button>
                    </router-link>     
                </el-form-item>
            </el-form>
        </div>
        
    </div>
</template>
<script>
import request from '@/utils/request';
import axios, { HttpStatusCode } from 'axios';
import '../assets/css/login.css'

export default {
    data(){
        var validatePhone = (rule, value, callback) => {
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
        return {
            ruleForm: {
                password: '',
                phone: ''
            },
            rules: {
                phone: [
                    { validator: validatePhone, trigger: 'blur' }
                ],
                password: [
                    { validator: validatePass, trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            // var username = this.ruleForm.username
            // var password = this.ruleForm.password
            request({
                method: 'get',
                url:`/employee/login`,
                params: {"phone":this.ruleForm.phone,"password":this.ruleForm.password}
            }).then(res=>{
                console.log(res);
                if(res.data.isSuccess==true){
                    this.$message.success("登录成功！")
                    this.$router.push("/home");
                }else{
                    this.$message.warning(res.data.results)
                }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
      
    }
}
</script>
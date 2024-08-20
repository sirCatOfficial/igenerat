<template>
    <div id="login_bg"> 
        <div id="login_info">
            <div id="login_info_title">
                <div id="login_logo">
                    <!-- <img src="../assets/img/logo.png" alt=""> -->
                </div>   
                <h1>公司业务系统</h1>
            </div>
            <div id="login_info_ewm">
                <img src="../assets/img/ewm.png" alt="">
            </div>
        </div>
        <div id="login_form">
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="账号" prop="username">
                    <el-input type="text" v-model="ruleForm.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="ruleForm.password" autocomplete="off" show-password></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
                <el-form-item>
                    <router-link to="/login">
                        <el-button type="danger">返回用户登录</el-button>
                    </router-link>     
                </el-form-item>
            </el-form>
        </div>
        
    </div>
</template>
<script>
import axios, { HttpStatusCode } from 'axios';
import '../assets/css/login.css'

export default {
    data(){
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
        return {
            ruleForm: {
                password: '',
                username: ''
            },
            rules: {
                username: [
                    { validator: validateUsername, trigger: 'blur' }
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
            axios({
                method: 'get',
                url:`/api/managerlogin`,
                params: {"username":this.ruleForm.username,"password":this.ruleForm.password}
            }).then(res=>{
                console.log(res);
                if(res.data){
                    this.$message.success("登录成功！")
                    this.$router.push("/managerhome");
                }else{
                    this.$message.warning("登录失败！不是管理员！")
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
<template>
    <div>
        <div id="header">
            <el-breadcrumb id="breadcrumb" separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>个人中心</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <el-descriptions class="margin-top" title="我的资料" :column="3" :size="size" border>
            <template slot="extra">
                <el-button type="primary" size="small" @click="checkUpd">修改</el-button>
            </template>
            <template slot="extra">
                <el-button type="primary" size="small" @click="phoneFormVisible=true">修改手机号</el-button>
            </template>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-location-outline"></i>
                    员工号
                </template>
                {{ user.id }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-user"></i>
                    用户名
                </template>
                {{ user.name }}
            </el-descriptions-item>
            <el-descriptions-item>
                <template slot="label">
                    <i class="el-icon-mobile-phone"></i>
                    手机号
                </template>
                {{ user.phone }}
            </el-descriptions-item>
            
            <el-descriptions-item>
                <template slot="label">
                    密码
                </template>
                {{ user.password }}
            </el-descriptions-item>
        </el-descriptions>
        <el-dialog :visible.sync="phoneFormVisible">
            <el-form :model="phoneForm" status-icon ref="pnoneForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="手机号" prop="phone">
                    <el-input type="text" v-model="phoneForm.phone" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="getCode()">获取验证码</el-button>
                </el-form-item>
                <el-form-item label="验证码" prop="code">
                    <el-input type="text" v-model="phoneForm.code" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="updatePhone()">修改</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
        <el-dialog :visible.sync="updateFormVisible">
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="姓名" prop="name">
                    <el-input type="text" v-model="ruleForm.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="ruleForm.password" autocomplete="off" show-password></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="checkPassword">
                    <el-input type="password" v-model="ruleForm.checkPassword" autocomplete="off" show-password></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">修改</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
import './css/formfather.css'
import './css/inputstyle.css'
// import axios from 'axios';
import request from '@/utils/request';
export default {
    data(){
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
        return{
            user:{
                id: null,
                name: '',
                phone:'',
                password: ''
            },
            phoneForm:{
                phone: '',
                code: '',
            },
            ruleForm: {
                id: null,
                name: '',
                password: '',
                checkPassword: '',
            },
            rules: {
                password: [
                    { validator: validatePass, trigger: 'blur' },
                    { min: 8, max: 12, message: '长度在 8 到 12 个字符' }
                ],
                checkPassword: [
                    { validator: validatePass2, trigger: 'blur' },
                    { min: 8, max: 12, message: '长度在 8 到 12 个字符' }
                ]
            },
            phoneFormVisible:false,
            updateFormVisible:false
        }
    },
    methods:{
        refresh(){
            request({
                url: '/employee/findMyself',
                method: 'get',
            }).then(res=>{
                if(res.data.isSuccess){
                    this.user=res.data.results
                }else{
                    this.$message.warning(res.data.results)
                }
            })
        },
        checkUpd(){
            this.updateFormVisible=true;
            this.ruleForm.id=this.user.id
            this.ruleForm.name=this.user.name
            this.ruleForm.password=this.user.password
        },
        submitForm(ruleForm){
            this.$refs[ruleForm].validate((valid)=>{
                if(valid){
                    request({
                        url: 'employee/updateMyself',
                        method: 'post',
                        params:{
                            id:this.ruleForm.id,
                            name:this.ruleForm.name,
                            password:this.ruleForm.password,
                        }
                    }).then(res=>{
                        if(res.data.isSuccess){
                            this.refresh();
                            this.updateFormVisible=false
                        }
                        else{
                            this.$message.warning(res.data.results)
                        }
                    })
                }
            })
        },
        getCode() {
            if(this.phoneForm.phone==null||this.phoneForm.phone==''){
                this.$message.warning("输入手机号")
            }else{
                request({
                    url: '/msm/send',
                    method: 'get',
                    params: {
                        phone: this.phoneForm.phone,
                    }
                }).then(res=>{
                    console.log(res.data)
                })
            }
        },
        updatePhone(){
            request({
                url:'/employee/updatePhone',
                method: 'post',
                params:{
                    id: this.user.id,
                    phone: this.phoneForm.phone,
                    code:this.phoneForm.code,
                }
            }).then(res=>{
                if(res.data.isSuccess){
                    this.refresh();
                    this.phoneFormVisible=false;
                }else{
                    this.$message.warning(res.data.results);
                }
            })
        }
    },
    created() {
        this.refresh();
    },
};
</script>

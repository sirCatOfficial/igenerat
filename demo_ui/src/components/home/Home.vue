<template>
<div>
  <div>
        <nav>
            <div id="logo">
                <!-- <img src="./img/logo.png" class="logo"> -->
                <h1 class="logo">业务管理系统</h1>
            </div>
            
            <div id="head_dropdown" >
            <el-dropdown trigger="click" @command="handleCommand">
                    <span class="el-dropdown-link">
                        我的<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item command="exit">退出登录</el-dropdown-item>
                        <el-dropdown-item command="myself">个人中心</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </nav>
    </div>
    <el-container style="height:630px">
        <el-aside width="200px" style="background-color: rgb(238, 241, 246);">
            <el-menu :router="true">
                <el-menu-item :index="'/employ'">
                    <i class="el-icon-arrow-right"></i>
                    <span slot="title">员工管理</span>
                </el-menu-item>
                <el-menu-item :index="'/depart'">
                    <i class="el-icon-arrow-right"></i>
                    <span slot="title">部门管理</span>
                </el-menu-item>
                <el-menu-item :index="'/job'">
                    <i class="el-icon-arrow-right"></i>
                    <span slot="title">职务管理</span>
                </el-menu-item>
                <el-menu-item :index="'/product'">
                    <i class="el-icon-arrow-right"></i>
                    <span slot="title">产品管理</span>
                </el-menu-item>
                <el-menu-item :index="'/produce'">
                    <i class="el-icon-arrow-right"></i>
                    <span slot="title">产品生产管理</span>
                </el-menu-item>
                <el-menu-item :index="'/customer'">
                    <i class="el-icon-arrow-right"></i>
                    <span slot="title">客户列表</span>
                </el-menu-item>
                <el-menu-item :index="'/order'">
                    <i class="el-icon-arrow-right"></i>
                    <span slot="title">订单管理</span>
                </el-menu-item>
                <el-menu-item :index="'/material'">
                    <i class="el-icon-arrow-right"></i>
                    <span slot="title">原料管理</span>
                </el-menu-item>
                <el-menu-item :index="'/supplier'">
                    <i class="el-icon-arrow-right"></i>
                    <span slot="title">供货厂商</span>
                </el-menu-item>
                <el-menu-item :index="'/porder'">
                    <i class="el-icon-arrow-right"></i>
                    <span slot="title">供货订单</span>
                </el-menu-item>
            </el-menu>
        </el-aside>
        <el-container>
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</div>
    
  
</template>
<script>
import request from '@/utils/request';
import "./css/managerhome.css"
export default {
    data(){
        return{
        }
    },
    methods:{
        handleCommand(command){
            // this.$message("click on item" + command);
            if(command === 'userpersoncenter'){
                this.$router.push(command);
            }else if(command === 'exit'){
                var flag = confirm("确定要退出吗？");
                if(flag){
                    request({
                        url: '/employee/logout',
                        method: 'post',
                    })
                    this.$router.push("/login");
                }
            }else if(command =="myself"){
                this.$router.push("/myself");
            }
        }
    }
}
</script>
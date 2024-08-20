<template>
    <div>
        <div>
            <nav>
                <!-- <img src="./img/logo.png"> -->
                <div id="head_dropdown" >
                <el-dropdown trigger="click" @command="handleCommand">
                        <span class="el-dropdown-link">
                            我的<i class="el-icon-arrow-down el-icon--right"></i>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="exit">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </nav>
        </div>
        <div id="form-father">
            <el-form :inline="true" :model="formInline" ref="formInline" class="demo-form-inline">
                <div id="form-item" style="margin:10px 10px 0px;">
                    <el-form-item label="名称" prop="name">
                        <el-input v-model="formInline.name" placeholder="名称"></el-input>
                    </el-form-item>
                    <el-form-item >
                        <el-button type="primary" @click="qurey()">搜索</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <el-container >
            <el-table
                :data="tableData"
                style="width: 100%">
                <el-table-column
                label="序号"
                width="250">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.id }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                label="品名"
                width="350">
                    <template slot-scope="scope">
                        <div slot="reference" class="name-wrapper">
                            <el-tag size="medium">{{ scope.row.name }}</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column
                label="生产日期"
                width="350">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.birthday }}</span>
                    </template>
                </el-table-column>
                <el-table-column
                label="生产日期"
                width="350">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.birthplace }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                            size="mini"
                            @click="handleEdit(scope.row.id)">购买</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-container>
    </div>
</template>
<script>
import axios from 'axios'
import "./css/userhome.css"
export default {
    data(){
        return{
            tableData: [],
            formInline: {
                name:''
            },
            formLabelWidth: '100px'
        }
    },
    methods:{
        handleEdit(row) {
            var flag = confirm("是否购买");
            if(flag){
                axios({
                    method: 'put',
                    url:`/api/buy`,
                    params: {"id":row}
                }).then(res=>{
                    console.log(res);
                    if(res.data){
                        this.$message.success("购买成功！")
                        this.getData();
                    }
                })
            }
            console.log(row);
        },
        getData(){
            axios.get(`/api/allUps`).then(res=>{
                console.log(res.data);
                this.tableData = res.data;
                console.log(this.tableData);
            })
        },
        qurey(){
            axios({
                method:'post',
                url:`/api/searchUps`,
                params: {"name":this.formInline.name}
            }).then(res=>{
                console.log(res);
                this.tableData = res.data
            })
            // axios.post(`/api/searchUps`,{"name":this.formInline.name}).then(res=>{
            //     console.log(res);
            //     if(res.data!=""){
            //         this.tableData = res.data
            //     }
            // })
            this.formInline.name = "";
        },
        handleCommand(command){
            if(command === 'userpersoncenter'){
                this.$router.push(command);
            }else if(command === 'exit'){
                var flag = confirm("确定要退出吗？");
                if(flag){
                    this.$router.push("/login");
                }
            }
        }
    },
    created(){
        axios.get(`/api/allUps`).then(res=>{
            console.log(res.data);
            this.tableData = res.data;
            console.log(this.tableData);
        })
    }
}
</script>
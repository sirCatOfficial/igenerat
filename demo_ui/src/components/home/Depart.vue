<template>
    <div>
        <div id="header">
            <el-breadcrumb id="breadcrumb" separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>部门管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div id="form-father">
            <el-form :inline="true" :model="formInline" class="demo-form-inline">
                <el-form-item label="部门">
                    <el-input v-model="formInline.name" placeholder="部门名"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="find">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button @click="dialogFormVisible = true">添加</el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-container>
            <el-table :data="departData" style="width: 100%">
                <el-table-column prop="id" label="编号" width="180">
                </el-table-column>
                <el-table-column prop="name" label="部门" width="180">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="openUpdate(scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="confirm(scope.row)">删除</el-button>
                        <el-button size="mini" @click="handleturn(scope.row)">详情</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-container>
        <el-dialog title="修改部门" :visible.sync="dialogUpdateVisible">
            <el-form :model="formupdate" ref="formupdate">
                <el-form-item label="部门名" :label-width="formLabelWidth" prop="name" :rules="[{ required: true, message: '请输入部门名', trigger: 'blur' },]">
                    <el-input v-model="formupdate.name" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogUpdateVisible = false">取 消</el-button>
                <el-button type="primary" @click="handleEdit(`formupdate`)">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="添加部门" :visible.sync="dialogFormVisible">
            <el-form :model="formadd" ref="formadd">
                <el-form-item label="部门名" :label-width="formLabelWidth" prop="name" :rules="[{ required: true, message: '请输入部门名', trigger: 'blur' },]">
                    <el-input v-model="formadd.name" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="add(`formadd`)">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import './css/formfather.css'
import './css/inputstyle.css'
// import axios from 'axios';
import request from '@/utils/request';
export default {
    data() {
        var validateName = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入部门名'));
            } else {
                callback();
            }
        };
        return {
            departData: [],
            formInline: {
                user: "",
                region: "",
            },
            formupdate: {
                id:null,
                name: "",
            },
            formadd: {
                name: "",
            },
            rule: {
                name: [{
                    validator: validateName, trigger: 'blur'
                }]
            },
            dialogUpdateVisible:false,
            dialogFormVisible: false,
            formLabelWidth: '120px'
        };
    },
    methods: {
        confirm(row) {//确认删除
            this.$confirm('是否确认删除？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                request({
                    method: 'post',
                    url: 'depart/delete',
                    params:{
                        id:row.id,
                    }
                }).then((res)=>{
                    if(res.data.isSuccess==true){
                        this.$message.success("删除成功")
                        this.refreshDepart();
                    }else{
                        this.$message.warning(res.data.results);
                    }
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        find() {
            request({
                method: 'get',
                url: 'depart/find',
                params: {
                    name: this.formInline.name,
                }
            }).then(res=>{
                if(res.data.isSuccess== true){
                    this.departData=res.data.results
                    this.$message.success("查询成功")
                }else{
                    this.$message.warning(res.data.results)
                }
            })
        },
        add(formadd) {
            this.$refs[formadd].validate((valid) => {
                if (valid) {
                    request({
                        method: 'post',
                        url: '/depart/addDepart',
                        params: {
                            name: this.formadd.name,
                        }
                    }).then(res => {
                        if (res.data.isSuccess == true) {
                            this.$message.success("添加成功");
                            this.dialogFormVisible = false
                            this.refreshDepart();
                        } else {
                            this.$message.warning(res.data.results)
                        }
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        openUpdate(row){
            this.formupdate=Object.assign({}, row);
            this.dialogUpdateVisible=true;
        },
        handleEdit(formupdate) {
            this.$refs[formupdate].validate((valid)=>{
                if(valid){
                    request({
                        method: 'post',
                        url: 'depart/update',
                        params:{
                            id:this.formupdate.id,
                            name: this.formupdate.name,
                        }
                    }).then(res=>{
                        if(res.data.isSuccess==true){
                            this.$message.success("修改成功")
                            this.dialogUpdateVisible=false;
                            this.refreshDepart()
                        }else{
                            this.$message.warning(res.data.results)
                        }
                    })
                }else{
                    console.log('error submit!!');
                    return false;
                }
            })
        },
        handleturn(row) {
            this.$router.push({
                path: `/job`,
                query: { dep: JSON.stringify(row) }
            })
        },
        refreshDepart() {
            if(this.formInline.name==""){
                find();
                return;
            }
            request({
                method: 'get',
                url: '/depart/findall'
            }).then(res => {
                this.departData = res.data.results;
                // console.log(res.data)
            })
        }
    },
    created() {
        this.refreshDepart();
    },
};
</script>

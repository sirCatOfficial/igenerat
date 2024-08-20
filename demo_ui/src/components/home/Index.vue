<template>
    <div>
        <div id="header">
            <el-breadcrumb id="breadcrumb" separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>员工管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div id="form-father">
            <el-form :inline="true" :model="formInline" ref="formInline" class="demo-form-inline">
                <div id="form-item">
                    <el-form-item label="姓名" prop="name">
                        <el-input v-model="formInline.name" placeholder="姓名"></el-input>
                    </el-form-item>
                    <el-form-item label="职务" prop="jid">
                        <el-select v-model="formInline.jid" clearable placeholder="请选择">
                            <el-option v-for="(item, index) in job" :key="index" :label="item.name" :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="refreshEmployee()">搜索</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button @click="handleAdd()">添加</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </div>
        <el-container>
            <el-table :data="tableData" style="width: 100%">
                <el-table-column label="编号" width="350">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.id }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="姓名" width="350">
                    <template slot-scope="scope">
                        <span size="medium">{{ scope.row.name }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="手机号" width="350">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.phone }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-container>


        <el-dialog title="添加" :visible.sync="dialogFormVisibleAdd">
            <el-form :model="form" status-icon :rules="rules" ref="form">
                <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
                    <el-input type="text" v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="职务" prop="jid" :label-width="formLabelWidth">
                    <el-select v-model="form.jid" clearable placeholder="请选择">
                        <el-option v-for="(item, index) in job" :key="index" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="resetForm('form')">重 置</el-button>
                <el-button type="primary" @click="uploadDate('form')">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="修改" :visible.sync="dialogFormVisibleEdit">
            <el-form :model="formUpdate" status-icon :rules="rules" ref="formUpdate">
                <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
                    <el-input type="text" v-model="formUpdate.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="职务" prop="jid" :label-width="formLabelWidth">
                    <el-select v-model="formUpdate.jid" clearable placeholder="请选择">
                        <el-option v-for="(item, index) in job" :key="index" :label="item.name" :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisibleEdit = false">取消</el-button>
                <el-button type="primary" @click="edit('formUpdate')">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>
<script>
import axios from 'axios';
import './css/formfather.css'
import request from '@/utils/request';

export default {
    data() {
        var validateName = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入姓名'));
            } else {
                callback();
            }
        };
        var validateJid = (rule, value, callback) => {
            if (value === 0) {
                callback(new Error('权限不能为空'));
            } else {
                callback();
            }
        };
        return {
            tableData: [],
            formInline: {
                name: "",
                jid: null,
            },
            dialogFormVisibleAdd: false,
            dialogFormVisibleEdit: false,
            form: {
                name: '',
                jid: 0,
            },
            formUpdate: {
                id: null,
                name: '',
                jid: 0,
            },
            formLabelWidth: '100px',
            rules: {
                name: [
                    { validator: validateName, trigger: 'blur' }
                ],
                jid: [
                    { validator: validateJid, trigger: 'blur' }
                ]
            },
            job: []
        }
    },
    methods: {
        handleDelete(row) {
            this.$confirm("确认删除吗？", "提示", {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                axios({
                    method: 'post',
                    url: '/api/employee/delete',
                    params: { id: row.id }
                }).then(res => {
                    if (res.data.isSuccess) {
                        this.$message.success(res.data.results)
                        this.refreshEmployee();
                    }else
                        this.$message.warning(res.data.results);
                })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        getData() {
            axios.get(`/api/allWork`).then(res => {
                console.log(res.data);
                this.tableData = res.data;
                console.log(this.tableData);
            })
        },
        onSubmit() {
            console.log('submit!');
        },
        uploadDate(form) {
            // console.log(this.form)
            this.$refs[form].validate((valid) => {
                if (valid) {
                    request({
                        method: 'post',
                        url: '/employee/addemployee',
                        params: {
                            name: this.form.name,
                            jid: this.form.jid,
                        }
                    }).then(res => {
                        if (res.data.isSuccess == true) {
                            this.$message.success(res.data.results)
                            this.dialogFormVisibleAdd = false
                            this.refreshEmployee();
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
        resetForm(form) {
            this.$refs[form].resetFields();
        },
        handleAdd() {
            this.dialogFormVisibleAdd = true;
        },
        handleEdit(row) {
            this.formUpdate = Object.assign({}, row);
            this.dialogFormVisibleEdit = true;
        },
        edit(formUpdate) {
            this.$refs[formUpdate].validate((valid) => {
                if (valid) {
                    request({
                        method: 'post',
                        url: '/employee/update',
                        params: {
                            id: this.formUpdate.id,
                            name: this.formUpdate.name,
                            jid: this.formUpdate.jid,
                        }
                    }).then(res => {
                        if (res.data.isSuccess) {
                            this.$message.success(res.data.results)
                            this.dialogFormVisibleEdit = false;
                            this.refreshEmployee();
                        }
                        else
                            this.$message.warning(res.data.results)
                    })
                } else {
                    console.log('error submit!!');
                    return false;
                }
            })
        },
        refreshEmployee() {
            request({
                method: 'get',
                url: 'employee/find',
                params:{
                    name:this.formInline.name,
                    jid:this.formInline.jid,
                }
            }).then(res => {
                if (res.data.isSuccess) {
                    this.tableData = res.data.results;
                } else {
                    this.$message.warning(res.data.results)
                }
            })
        },
        refreshJob() {
            request({
                method: 'get',
                url: '/job/findjob',
            }).then(res => {
                if (res.data.isSuccess)
                    this.job = res.data.results;
                else
                    this.$message.warning(res.data.results)
            })
        },
    },
    created() {
        this.refreshEmployee();
        this.refreshJob();
    }
}

</script>
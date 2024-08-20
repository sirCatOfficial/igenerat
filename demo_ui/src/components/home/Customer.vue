<template>
    <div>
        <div id="header">
            <el-breadcrumb id="breadcrumb" separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>客户列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div>
            <el-form :inline="true" :model="findForm" ref="findForm" class="demo-form-inline">
                <el-form-item label="客户名">
                    <el-input v-model="findForm.name"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="refresh()">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button @click="insertFormVisible = true">添加</el-button>
                </el-form-item>

            </el-form>

            <el-dialog title="添加客户" :visible.sync="insertFormVisible">
                <el-form :model="insertForm" :rules="rules" ref="insertForm">
                    <el-form-item label="客户名" prop="name" :label-width="formLabelWidth">
                        <el-input v-model="insertForm.name"></el-input>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="insert('insertForm')">确定</el-button>
                </div>

            </el-dialog>
            <el-dialog title="修改信息" :visible.sync="updateFormVisible">
                <el-form :model="updateForm" :rules="rules" ref="updateForm">
                    <el-form-item label="客户名" prop="name" :label-width="formLabelWidth">
                        <el-input v-model="updateForm.name"></el-input>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="update('updateForm')">确定</el-button>
                </div>

            </el-dialog>
        </div>
        <el-container>
            <el-table :data="tableData">
                <el-table-column label="编号" width="180" prop="id"></el-table-column>
                <el-table-column label="客户名" width="180" prop="name"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="clickUpdate(scope.row)">修改</el-button>
                        <el-button size="mini" type="danger" @click="confirm(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-container>
    </div>
</template>
<script type=“text/javascript”>
import request from '@/utils/request'
import './css/formfather.css'
import './css/inputstyle.css'
export default {
    data() {
        var checkNumber = (rule, value, callback) => {
            if (!Number.isInteger(value)) {
                    callback(new Error('请输入数字值'));
                } else {
                    if (value <= 0) {
                        callback(new Error('必须输入正整数'));
                    } else {
                        callback();
                    }
                }
        }
        return {
            findForm: {
                name: null,
            },
            insertForm: {
                name: null,
            },
            updateForm: {
                id: null,
                name: null,
            },
            tableData: [],
            insertFormVisible: false,
            updateFormVisible: false,
            formLabelWidth: '120px',
            rules: {
                name: [
                    { required: true, message: '请输入客户名', trigger: 'blur' },
                ],
            },
        }
    },
    methods: {
        refresh() {
            request({
                url: '/customer/find',
                method: 'get',
                params: {
                    name: this.findForm.name,
                }
            }).then(res => {
                if (res.data.isSuccess)
                    this.tableData = res.data.results
                else
                    this.$message.warning(res.data.results)
            })
        },
        insert(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    request({
                        method: "post",
                        url: "customer/insert",
                        params: this.insertForm,
                    }).then(res => {
                        if (res.data.isSuccess) {
                            this.insertFormVisible = false;
                            this.refresh();
                        }
                        else
                            this.$message.warning(res.data.results);
                    })

                } else {
                    console.log('error submit!!');
                    return false;
                }
            })
        },
        clickUpdate(row){
            this.updateForm=Object.assign({}, row);
            this.updateFormVisible=true;
        },
        update(formName){
            this.$refs[formName].validate((valid) => {
                if(valid){
                    request({
                        method: "post",
                        url: "customer/update",
                        params: this.updateForm,
                    }).then(res => {
                        if (res.data.isSuccess) {
                            this.updateFormVisible = false;
                            this.refresh();
                        }
                        else
                            this.$message.warning(res.data.results);
                    })
                }
                else{
                    console.log('error submit!!');
                    return false;
                }
            })
        },
        confirm(row) {//确认删除
            this.$confirm('是否确认删除？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                request({
                    method: 'post',
                    url: 'customer/delete',
                    params:{
                        id:row.id,
                    }
                }).then((res)=>{
                    if(res.data.isSuccess==true){
                        this.$message.success("删除成功")
                        this.refresh();
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
    },
    created() {
        this.refresh();
    },
}
</script>

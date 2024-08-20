<template>
    <div>
        <div id="header">
            <el-breadcrumb id="breadcrumb" separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>产品管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div>
            <el-form :inline="true" :model="findForm" ref="findForm" class="demo-form-inline">
                <el-form-item label="产品名称">
                    <el-input v-model="findForm.name" placeholder="产品名"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="refresh()">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button @click="insertFormVisible = true">添加</el-button>
                </el-form-item>

            </el-form>

            <el-dialog title="添加产品" :visible.sync="insertFormVisible">
                <el-form :model="insertForm" :rules="rules" ref="insertForm">
                    <el-form-item label="产品名" prop="name" :label-width="formLabelWidth">
                        <el-input v-model="insertForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="单价" prop="price" :label-width="formLabelWidth">
                        <el-input v-model="insertForm.price"></el-input>
                    </el-form-item>
                    <el-form-item label="单位" prop="unit" :label-width="formLabelWidth">
                        <el-input v-model="insertForm.unit"></el-input>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="insert('insertForm')">确定</el-button>
                </div>

            </el-dialog>
            <el-dialog title="添加库存" :visible.sync="addFormVisible">
                <el-form :model="addForm" :rules="ruleAdd" ref="addForm">
                    <el-form-item label="数量" prop="number">
                        <el-input v-model.number="addForm.number"></el-input>
                    </el-form-item>
                    <el-form-item label="生产日期" prop="date">
                        <el-date-picker type="date" v-model="addForm.date"></el-date-picker>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="submitAdd('addForm')">确定</el-button>
                </div>
            </el-dialog>
            <el-dialog title="修改产品信息" :visible.sync="editFormVisible">
                <el-form :model="editForm" :rules="rules" ref="editForm">
                    <el-form-item label="产品名" prop="name" :label-width="formLabelWidth">
                        <el-input v-model="editForm.name"></el-input>
                    </el-form-item>
                    <el-form-item label="单价" prop="price" :label-width="formLabelWidth">
                        <el-input v-model="editForm.price"></el-input>
                    </el-form-item>
                    <el-form-item label="单位" prop="unit" :label-width="formLabelWidth">
                        <el-input v-model="editForm.unit"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="submitEdit('editForm')">确定</el-button>
                </div>
            </el-dialog>
        </div>
        <el-container>
            <el-table :data="productData">
                <el-table-column label="编号" width="180" prop="id"></el-table-column>
                <el-table-column label="产品名" width="180" prop="name"></el-table-column>
                <el-table-column label="单价" width="180" prop="price"></el-table-column>
                <el-table-column label="库存" width="180" prop="inventory"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="clickAdd(scope.row.id)">添加</el-button>
                        <el-button size="mini" @click="clickEdit(scope.row)">修改</el-button>
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
                price: 0,
                unit: null,
            },
            editForm: {
                id: null,
                name: null,
                price: 0,
                unit: null,
            },
            addForm: {
                id: null,
                number: null,
                date:null,
            },
            productData: [],
            editFormVisible: false,
            addFormVisible: false,
            insertFormVisible: false,
            formLabelWidth: '120px',
            rules: {
                name: [
                    { required: true, message: '请输入产品名', trigger: 'blur' },
                ],
                unit: [
                    { required: true, message: '请输入单位', trigger: 'blur' },
                ]
            },
            ruleAdd: {
                number: [
                    { required: true, message: '请输入数量', trigger: 'blur' },
                    { validator: checkNumber, trigger: 'blur' }
                ]
            }
        }
    },
    methods: {
        refresh() {
            request({
                url: '/product/find',
                method: 'get',
                params: {
                    name: this.findForm.name,
                }
            }).then(res => {
                if (res.data.isSuccess)
                    this.productData = res.data.results
                else
                    this.$message.warning(res.data.results)
            })
        },
        insert(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    request({
                        method: "post",
                        url: "product/insert",
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
        clickAdd(id) {
            this.addForm.id = id;
            this.addFormVisible = true;
        },
        clickEdit(item){
            this.editForm=Object.assign({}, item);
            this.editFormVisible=true;
        },
        submitAdd(formName){
            this.$refs[formName].validate((valid) => {
                if(valid){
                    request({
                        method: 'post',
                        url:'/product/add',
                        params: this.addForm,
                    }).then(res=>{
                        if(res.data.isSuccess){
                            this.refresh();
                            this.addFormVisible=false
                        }
                        else
                            this.$message.warning(res.data.results)
                    })
                }else{
                    console.log('error submit!!');
                    return false;
                }
            })
        },
        submitEdit(formName){
            this.$refs[formName].validate((valid) => {
                if(valid){
                    request({
                        method: 'post',
                        url:'/product/update',
                        params: this.editForm,
                    }).then(res=>{
                        if(res.data.isSuccess){
                            this.refresh();
                            this.editFormVisible=false
                        }
                        else
                            this.$message.warning(res.data.results)
                    })
                }else{
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
                    url: 'product/delete',
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

<template>
    <div>
        <div id="header">
            <el-breadcrumb id="breadcrumb" separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>原料商管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div id="form-father">
            <el-form :inline="true" :model="formInline" ref="formInline" class="demo-form-inline">
                <el-form-item label="原料名">
                    <el-input v-model="findName"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="fetchData">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button @click="insertFormVisible = true">添加</el-button>
                </el-form-item>
            </el-form>
        </div>
        <el-container>
            <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="id" label="序号" width="180"></el-table-column>
                <el-table-column prop="name" label="厂家" width="180"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="checkEdit(scope.row)">修改</el-button>
                        <el-button size="mini" type="danger" @click="confineDel(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-container>

        <el-dialog title="添加厂商" :visible.sync="insertFormVisible">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="厂商名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="addItem">添加</el-button>
            </div>
        </el-dialog>
        <el-dialog title="修改" :visible.sync="editFormVisible">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="厂商名">
                    <el-input v-model="editForm.name"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="editItem">修改</el-button>
            </div>
        </el-dialog>
        <el-dialog title="确定删除吗？" :visible.sync="delVisible" width="20%">
            <el-button type="primary" @click="deleteItem" slot="footer" class="dialog-footer">删除</el-button>
        </el-dialog>

    </div>
</template>  
    
<script>
import './css/formfather.css'
import './css/inputstyle.css'
import request from '@/utils/request';
export default {
    data() {
        return {
            tableData: [],  // 用于展示的数据，应该从后端获取并赋值给这个变量。这里只是示例，所以为空。  
            form: { // 用于表单编辑的数据。这个变量也应该从后端获取初始值。这里只是示例，所以为空。  
                name: '',
            },
            editForm: {
                id: null,
                name: '',
            },
            findName: '',
            insertFormVisible: false,
            editFormVisible:false,
            delVisible:false,
            delId:null,
        };
    },
    methods: {
        fetchData() { // 从后端获取数据并赋值给tableData的函数。这里需要实现从后端获取数据的逻辑。由于这里只是示例，所以这个函数为空。  
            request({
                url: '/supplier/find',
                method: 'get',
                params: {
                    name: this.findName,
                }
            }).then(res => {
                if (res.data.isSuccess)
                    this.tableData = res.data.results;
                else
                    this.$message.warning(res.data.results)
            })
        },
        confineDel(item){
            this.delVisible=true;
            this.delId=item.id;
        },
        checkEdit(item){
            this.editForm=Object.assign({}, item);
            this.editFormVisible=true;
        },
        editItem() { // 编辑单个项目的函数。这里需要实现更新后端数据的逻辑。由于这里只是示例，所以这个函数为空。 
            request({
                url:'/supplier/update',
                method: 'post',
                params:this.editForm,
            }).then(res=>{
                this.$message(res.data.results)
                if(res.data.isSuccess){
                    this.editFormVisible=false;
                    this.fetchData()
                }
            })
        },
        deleteItem() { // 删除单个项目的函数。这里需要实现删除后端数据的逻辑。由于这里只是示例，所以这个函数为空。这个函数会弹出一个确认对话框。你可以使用Element UI的对话框组件来实现这个功能。由于对话框组件比较复杂，这里就不给出具体的代码了。你可以查阅Element UI的文档来了解如何使用对话框组件。   
            request({
                url:'/supplier/delete',
                method: 'post',
                params:{
                    id: this.delId,
                }
            }).then(res=>{
                this.delVisible=false;
                this.$message(res.data.results);
                if(res.data.isSuccess)
                    this.fetchData();
            })
        },
        addItem() { // 添加新项目的函数。这里需要实现添加后端数据的逻辑。由于这里只是示例，所以这个函数为空。这个函数会弹出一个对话框来收集新项目的信息，并将信息添加到表格中以及后端数据中。你可以使用Element UI的对话框组件来实现这个功能。由于对话框组件比较复杂，这里就不给出具体的代码了。你可以查阅Element UI的文档来了解如何使用对话框组件。
            request({
                url: '/supplier/insert',
                method: 'post',
                params: this.form,
            }).then(res => {
                if (res.data.isSuccess) {
                    this.insertFormVisible = false;
                    this.form = Object.assign({},this.$options.data().form);
                    this.fetchData();
                } else
                    this.$message.warning(res.data.results)
            })
        }
    },
    mounted() { // 在组件挂载后调用fetchData函数获取数据。由于这里只是示例，所以这个函数为空。你需要实现从后端获取数据的逻辑。   
        this.fetchData();
    }
};
</script>
<template>
    <div>
        <div id="header">
            <el-breadcrumb id="breadcrumb" separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>产品生产管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div>
            <el-form :inline="true" :model="findForm" ref="findForm" class="demo-form-inline">
                <el-form-item label="产品名称">
                    <el-input v-model="findForm.pname" placeholder="产品名"></el-input>
                </el-form-item>
                <el-form-item label="员工名">
                    <el-input v-model="findForm.name"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="refresh()">查询</el-button>
                </el-form-item>

            </el-form>
        </div>
        <div>
            <el-table :data="tableData">
                <el-table-column label="产品名" width="180" prop="pname"></el-table-column>
                <el-table-column label="员工" width="180" prop="name"></el-table-column>
                <el-table-column label="日期" width="180" prop="date"></el-table-column>
                <el-table-column label="数量" width="180" prop="number"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" type="danger" @click="confirm(scope.row,scope.row.number)">撤销</el-button>
                        <el-button size="mini" type="danger" @click="confirm(scope.row,null)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>
<script type=“text/javascript”>
import request from '@/utils/request'
import './css/formfather.css'
import './css/inputstyle.css'
export default{
    data(){
        return{
            findForm: {
                pname: null,
                name: null,
            },
            tableData: [],
        }
    },
    methods:{
        refresh() {
            request({
                url: '/produce/find',
                method: 'get',
                params: this.findForm,
            }).then(res => {
                if (res.data.isSuccess)
                    this.tableData = res.data.results
                else
                    this.$message.warning(res.data.results)
            })
        },
        confirm(row,number) {//确认删除
            this.$confirm('是否确认？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                request({
                    method: 'post',
                    url: 'produce/delete',
                    params:{
                        pid:row.pid,
                        id:row.id,
                        date:row.date,
                        number:number,
                    }
                }).then((res)=>{
                    if(res.data.isSuccess==true){
                        this.$message.success("成功")
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
<template>
    <div id="welcome">
        <div id="header">
            <el-breadcrumb id="breadcrumb" separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>首页</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div>
            <el-table :data="prolist" style="width: 100%" :row-class-name="tableRowClassName">
                <el-table-column prop="id" label="序号" width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.id }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="id" label="编号" width="180">
                    <template slot-scope="scope">
                        <span style="margin-left: 10px">{{ scope.row.index }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="good_name" label="货物名称" width="180">
                    <template slot-scope="scope">
                        <el-tag >{{scope.row.good_name}}</el-tag>
                    </template>   
                </el-table-column>
                <el-table-column  label="货物存储情况">
                    <template slot-scope="scope">
                         <el-progress :text-inside="true" :stroke-width="24" :percentage="Math.ceil(scope.row.good_count_dis_buy/scope.row.good_count_all*100)" :color="colores"></el-progress>
                    </template>  
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>
<script>
import axios from 'axios';
import './css/welcome.css'
export default {
    data(){
        return{
            prolist:[],
            colores: [
                {color: 'bluegreen', percentage: 50},
                {color: 'pink', percentage: 80},
                {color: 'red', percentage: 100}
            ]
        }
    },
    methods:{
        tableRowClassName({row, rowIndex}) {
        if (rowIndex  % 2 === 1) {
          return 'warning-row';
        } else if (rowIndex  % 2 === 0) {
          return 'success-row';
        }
        return '';
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
      }
    },
    created(){
    }
}
</script>
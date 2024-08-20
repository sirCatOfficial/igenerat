<template>
    <div>
        <div id="header">
            <el-breadcrumb id="breadcrumb" separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>职务管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div id="form-father">
            <el-form :inline="true" :model="formInline" ref="formInline" class="demo-form-inline">
                <el-form-item label="职务名">
                    <el-input v-model="formInline.name" placeholder="职务名"></el-input>
                </el-form-item>
                <el-form-item label="部门">
                    <el-select v-model="formInline.did" clearable placeholder="部门">
                        <el-option v-for="(item,index) in departData" :key="index" :label="item.name" :value="item.id"> </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="refreshjob()">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button @click="dialogFormVisible = true">添加</el-button>
                </el-form-item>
            </el-form>
        </div>

        <el-container>
            <el-table :data="jobData" style="width: 100%">
                <el-table-column prop="name" label="职务" width="180">
                </el-table-column>
                <el-table-column prop="root" label="权限" width="180">
                </el-table-column>
                <el-table-column prop="pay" label="工资" width="180">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                        <!-- <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button> -->
                    </template>
                </el-table-column>
            </el-table>
        </el-container>
        <el-dialog title="添加职务" :visible.sync="dialogFormVisible">
            <el-form :model="formadd" ref="formadd">
                <el-form-item label="职务名" prop="name" :label-width="formLabelWidth" :rules="[{ required: true, message: '请输入职务名', trigger: 'blur' },]">
                    <el-input v-model="formadd.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="部门" prop="did" :label-width="formLabelWidth">
                    <el-select v-model="formadd.did" placeholder="部门">
                        <el-option v-for="(item,index) in departData" :key="index" :label="item.name" :value="item.id"> </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="权限" prop="root" :label-width="formLabelWidth">
                    <el-input v-model="formadd.root" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="工资" prop="pay" :label-width="formLabelWidth">
                    <el-input v-model.trim="number" v-model="formadd.pay" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="add(`formadd`)">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="修改职务" :visible.sync="updateFormVisible" >
            <el-form :model="formupdate" ref="formupdate">
                <el-form-item label="职务名" :label-width="formLabelWidth" prop="name" :rules="[{ required: true, message: '请输入职务名', trigger: 'blur' },]">
                    <el-input v-model="formupdate.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="部门" :label-width="formLabelWidth">
                    <el-select v-model="formupdate.did" placeholder="部门">
                        <el-option v-for="(item,index) in departData" :key="index" :label="item.name" :value="item.id"> </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="权限" :label-width="formLabelWidth">
                    <el-input v-model="formupdate.root" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="工资" :label-width="formLabelWidth">
                    <el-input v-model.trim="number" v-model="formupdate.pay" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="updateFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="edit(`formupdate`)">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import './css/formfather.css'
// import './css/inputstyle.css'
import request from '@/utils/request';
export default {
    data() {
        return {
            departData: [],
            depart: {
                id: null,
                depart: "",
            },
            jobData: [],
            formInline: {
                name: "",
                did: null,
            },
            formadd: {
                did: null,
                name: '',
                root: '',
                pay: '',
            },
            formupdate: {
                did: null,
                name: '',
                root: '',
                pay: '',
            },
            updateFormVisible:false,
            dialogFormVisible: false,
            formLabelWidth: '120px'
        };
    },
    methods: {
        onSubmit() {
            console.log("submit!");
        },
        add(formadd) {
            this.$refs[formadd].validate((valid) => {
                if (valid) {
                    request({
                        method: 'post',
                        url: '/job/addjob',
                        params: {
                            did: this.formadd.did,
                            name:this.formadd.name,
                            root: this.formadd.root,
                            pay: this.formadd.pay,
                        }
                    }).then(res => {
                        if (res.data.isSuccess == true) {
                            this.$message.success("添加成功");
                            this.refreshjob;
                            this.dialogFormVisible = false
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
        handleEdit(row) {
            this.formupdate=Object.assign({}, row);
            this.updateFormVisible=true;
        },
        edit(formupdate){
            this.$refs[formupdate].validate((valid) => {
                if(valid){
                    request({
                        method: 'post',
                        url: 'job/update',
                        params: this.formupdate,
                    }).then(res=>{
                        if (res.data.isSuccess == true) {
                            this.$message.success("修改成功");
                            this.refreshjob();
                            this.updateFormVisible = false
                        } else {
                            this.$message.warning(res.data.results)
                        }
                    })
                }else{
                    console.log('error submit!!');
                    return false;
                }
            })
        },
        handleDelete(index) {

        },
        refreshjob() {
            request({
                method: 'get',
                url: '/job/findjob',
                params: {
                    name:this.formInline.name,
                    did: this.formInline.did
                }
            }).then(res => {
                this.jobData = res.data.results;
            })
        },
        refreshdep() {
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
        this.refreshdep();
        console.log(this.$route.query.dep)
        if (this.$route.query.dep !=null)
            this.formInline.did=JSON.parse(this.$route.query.dep).id 
        this.refreshjob();
        console.log(this.formInline)
    },

};
</script>

<template>
    <div>
        <div id="header">
            <el-breadcrumb id="breadcrumb" separator-class="el-icon-arrow-right">
                <el-breadcrumb-item>订单管理</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div>
            <el-form :inline="true" :model="findForm" ref="findForm" class="demo-form-inline">
                <el-form-item label="客户名">
                    <el-input v-model="findForm.cname"></el-input>
                </el-form-item>
                <el-form-item label="产品名">
                    <el-input v-model="findForm.pname"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="refresh()">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button @click="insertFormVisible = true">添加</el-button>
                </el-form-item>

            </el-form>

            <el-dialog title="添加订单" :visible.sync="insertFormVisible">
                <el-form :model="insertForm" :rules="rules" ref="insertForm">
                    <el-form-item label="客户" prop="cname" :label-width="formLabelWidth">
                        <el-autocomplete v-model="insertForm.cname" value-key="name" value="id"
                            :fetch-suggestions="querySearch" placeholder="请输入内容" @select="handleSelect($event, insertForm)">
                            <!-- <template slot-scope="{ item }">
                                <div class="name">{{ item.name }}</div>
                            </template> -->
                        </el-autocomplete>
                    </el-form-item>
                    <el-form-item label="产品" prop="pname" :label-width="formLabelWidth">
                        <el-autocomplete v-model="insertForm.pname" :fetch-suggestions="querySearch2" placeholder="请输入内容"
                            @select="handleSelect2($event, insertForm)">
                            <template slot-scope="{ item }">
                                <div class="name">{{ item.name }}</div>
                            </template>
                        </el-autocomplete>
                    </el-form-item>
                    <el-form-item label="数量" prop="number" :label-width="formLabelWidth">
                        <el-input v-model.number="insertForm.number"></el-input>
                    </el-form-item>
                    <el-form-item label="总价" prop="amount" :label-width="formLabelWidth">
                        <el-input v-model.number="insertForm.amount"></el-input>
                    </el-form-item>
                    <el-form-item label="交货状态" prop="delivery" :label-width="formLabelWidth">
                        <el-radio v-model="insertForm.delivery" label=true>是</el-radio>
                        <el-radio v-model="insertForm.delivery" label=false>否</el-radio>
                    </el-form-item>
                    <el-form-item label="付款状态" prop="payment" :label-width="formLabelWidth">
                        <el-radio v-model="insertForm.payment" label=true>是</el-radio>
                        <el-radio v-model="insertForm.payment" label=false>否</el-radio>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="insertOrder('insertForm')">确定</el-button>
                </div>

            </el-dialog>
            <el-dialog title="修改信息" :visible.sync="updateFormVisible">
                <el-form :model="updateForm" :rules="rules" ref="updateForm">
                    <el-form-item label="客户" prop="cname" :label-width="formLabelWidth">
                        <el-autocomplete v-model="updateForm.cname" value-key="name" value="id" :disabled="able"
                            :fetch-suggestions="querySearch" placeholder="请输入内容" @select="handleSelect($event, updateForm)">
                            <!-- <template slot-scope="{ item }">
                                <div class="name">{{ item.name }}</div>
                            </template> -->
                        </el-autocomplete>
                    </el-form-item>
                    <el-form-item label="产品" prop="pname" :label-width="formLabelWidth">
                        <el-autocomplete v-model="updateForm.pname" :fetch-suggestions="querySearch2" placeholder="请输入内容"
                            @select="handleSelect2($event, updateForm)" :disabled="able">
                            <template slot-scope="{ item }">
                                <div class="name">{{ item.name }}</div>
                            </template>
                        </el-autocomplete>
                    </el-form-item>
                    <el-form-item label="数量" prop="number" :label-width="formLabelWidth">
                        <el-input v-model.number="updateForm.number" :disabled="able"></el-input>
                    </el-form-item>
                    <el-form-item label="总价" prop="amount" :label-width="formLabelWidth">
                        <el-input v-model.number="updateForm.amount" :disabled="able"></el-input>
                    </el-form-item>
                    <el-form-item label="交货状态" prop="delivery" :label-width="formLabelWidth">
                        <el-radio v-model="updateForm.delivery" label=true :disabled="!able">是</el-radio>
                        <el-radio v-model="updateForm.delivery" label=false :disabled="!able">否</el-radio>
                    </el-form-item>
                    <el-form-item label="付款状态" prop="payment" :label-width="formLabelWidth">
                        <el-radio v-model="updateForm.payment" label=true :disabled="!able">是</el-radio>
                        <el-radio v-model="updateForm.payment" label=false :disabled="!able">否</el-radio>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="handleUpdate('updateForm')">确定</el-button>
                </div>

            </el-dialog>
        </div>
        <el-container>
            <el-table :data="tableData">
                <!-- <el-table-column label="编号" width="80" prop="id"></el-table-column> -->
                <el-table-column label="客户名" width="80" prop="cname"></el-table-column>
                <el-table-column label="产品名" width="180" prop="pname"></el-table-column>
                <el-table-column label="数目" width="80" prop="number"></el-table-column>
                <el-table-column label="总价" width="80" prop="amount"></el-table-column>
                <el-table-column label="交货状态" width="100" prop="delivery">
                    <template slot-scope="scope">
                        <i class="el-icon-circle-check" v-if="scope.row.delivery" ></i>
                        <i class="el-icon-circle-close" v-else ></i>
                    </template>
                </el-table-column>
                <el-table-column label="付款状态" width="100" prop="payment" >
                    <template slot-scope="scope">
                        <i class="el-icon-circle-check" v-if="scope.row.payment" ></i>
                        <i class="el-icon-circle-close" v-else ></i>
                    </template>
                    
                </el-table-column>
                <el-table-column label="操作" >
                    <template slot-scope="scope">
                        <el-button size="mini" @click="clickUpdate(scope.row)">修改信息</el-button>
                        <el-button size="mini" @click="clickUpdate2(scope.row)">更改状态</el-button>
                        <el-button size="mini" type="danger" @click="confirm(scope.row,'是否确认删除？',true)">删除</el-button>
                        <el-button size="mini" @click="confirm(scope.row,'是否确认取消？',false)">取消</el-button>
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
                cname: null,
                pname: null,
            },
            insertForm: {
                cid: null,
                cname: null,
                pid: null,
                pname: null,
                number: null,
                amount: null,
                delivery: null,
                payment: null,
            },
            updateForm: {
                id: null,
                cid: null,
                cname: null,
                pid: null,
                pname: null,
                number: null,
                amount: null,
                delivery: null,
                payment: null,
            },
            able: false,
            tableData: [],
            insertFormVisible: false,
            updateFormVisible: false,
            formLabelWidth: '120px',
            rules: {
                number: [
                    { required: true, message: '请输入数量', trigger: 'blur' },
                    { validator: checkNumber, trigger: 'blur' },
                ],
                amount: [
                    { required: true, message: '请输入数量', trigger: 'blur' },
                    { validator: checkNumber, trigger: 'blur' },
                ]
            },
            restaurants: [],
        }
    },
    methods: {
        refresh() {
            request({
                url: '/order/find',
                method: 'get',
                params: {
                    cname: this.findForm.cname,
                    pname: this.findForm.pname,
                }
            }).then(res => {
                if (res.data.isSuccess)
                    this.tableData = res.data.results
                else
                    this.$message.warning(res.data.results)
            })
        },
        clickUpdate(row) {
            if(row.delivery||row.payment){
                this.$message.warning("订单信息已无法修改")
                return ;
            }
            this.updateForm = Object.assign({}, row);
            this.updateFormVisible = true;
            console.log(this.updateForm)
            this.able = false
        },
        clickUpdate2(row) {
            this.updateForm = Object.assign({}, row);
            this.updateFormVisible = true;
            this.updateForm.delivery = row.delivery.toString()
            this.updateForm.payment = row.payment.toString()
            this.able = true
        },
        update() {
            request({
                method: "post",
                url: "order/update",
                params: {
                    id: this.updateForm.id,
                    cid: this.updateForm.cid,
                    pid: this.updateForm.pid,
                    number: this.updateForm.number,
                    amount: this.updateForm.amount,
                }
            }).then(res => {
                if (res.data.isSuccess) {
                    this.updateFormVisible = false;
                    this.refresh();
                }
                else
                    this.$message.warning(res.data.results);
            })
        },
        upstate() {
            request({
                method: "post",
                url: "order/upstate",
                params: {
                    id: this.updateForm.id,
                    delivery: this.updateForm.delivery,
                    payment: this.updateForm.payment,
                }
            }).then(res => {
                if (res.data.isSuccess) {
                    this.updateFormVisible = false;
                    this.refresh();
                }
                else
                    this.$message.warning(res.data.results);
            })
        },
        handleUpdate(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    if (this.able) {
                        this.upstate();
                        return;
                    }
                    else {
                        if (this.updateForm.cid == null) {
                            this.$message.warning("未选择客户")
                            return;
                        }
                        if (this.updateForm.pid == null) {
                            this.$message.warning("未选择产品")
                            return;
                        }
                        this.update();
                        return;
                    }
                }
                else {
                    console.log('error submit!!');
                    return false;
                }
            })
        },
        confirm(row,str,flag) {//确认删除
            if(flag&&(row.delivery!=row.payment)){
                this.$message.warning("无法删除订单");
                return ;
            }
            this.$confirm(str, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                request({
                    method: 'post',
                    url: 'order/delete',
                    params: {
                        id: row.id,
                    }
                }).then((res) => {
                    if (res.data.isSuccess == true) {
                        this.$message.success("成功")
                        this.refresh();
                    } else {
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
        querySearch(queryString, cb) {
            request({
                method: 'get',
                url: 'customer/find',
                params: {
                    name: queryString,
                }
            }).then(res => {
                if (res.data.isSuccess) {
                    var products = res.data.results;
                    cb(products);
                }

            })
        },
        querySearch2(queryString, cb) {
            request({
                method: 'get',
                url: 'product/find',
                params: {
                    name: queryString,
                }
            }).then(res => {
                if (res.data.isSuccess) {
                    var products = res.data.results;
                    cb(products);
                }

            })
        },
        handleSelect(item, form) {
            console.log(form)
            form.cid = item.id;
        },
        handleSelect2(item, form) {
            form.pid = item.id;
            form.pname = item.name;
        },
        insertOrder(form) {
            this.$refs[form].validate((valid) => {
                if (valid) {
                    if (this.insertForm.cid == null) {
                        this.$message.warning("未选择客户")
                        return;
                    }
                    if (this.insertForm.pid == null) {
                        this.$message.warning("未选择产品")
                        return;
                    }
                    request({
                        url: 'order/insert',
                        method: 'post',
                        params: {
                            cid: this.insertForm.cid,
                            pid: this.insertForm.pid,
                            number: this.insertForm.number,
                            amount: this.insertForm.amount,
                            delivery: this.insertForm.delivery,
                            payment: this.insertForm.payment,
                        }
                    }).then(res=>{
                        if (res.data.isSuccess) {
                            this.insertFormVisible = false;
                            this.insertFormVisible=false;
                            
                            this.refresh();
                        }
                        else
                            this.$message.warning(res.data.results);
                    })
                }
            })

        }
    },
    created() {
        this.refresh();
    },
}
</script>

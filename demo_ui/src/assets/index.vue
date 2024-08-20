<template>
    <body>

        <main>

            <nav class="navbar navbar-expand-lg">
                <div class="container">
                    <a class="navbar-brand" href="index.html">
                        <i class="navbar-brand-icon bi-book me-2"></i>
                        <span>O_o服装集团</span>
                    </a>


                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <div class="collapse navbar-collapse" id="navbarNav">
                        <ul class="navbar-nav ms-lg-auto me-lg-4">
                            <li class="nav-item">
                                <a class="nav-link click-scroll" href="#section_1">顶部</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link click-scroll" href="#section_2">产品</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link click-scroll" href="#section_3">创始人简介</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link click-scroll" href="#section_4">团队</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link click-scroll" href="#section_5">电话</a>
                            </li>
                        </ul>

                        <div class="d-none d-lg-block">
                            <el-button v-if="islogin" @click="logout" class="btn custom-btn custom-border-btn btn-naira ">
                                <sqan class="btn-p">{{ name }}</sqan>
                                <sqan class="btn-hov">注销</sqan>
                            </el-button>
                            <el-button v-else @click="loginVisible = true"
                                class="btn custom-btn custom-border-btn btn-naira btn-inverted">
                                <i class="btn-icon bi-cloud-download"></i>
                                <span>登录/注册</span><!-- duplicated above one for mobile -->
                            </el-button>

                        </div>
                    </div>
                </div>
            </nav>


            <section class="hero-section d-flex justify-content-center align-items-center" id="section_1">
                <div class="container">
                    <div class="row">

                        <div class="col-lg-6 col-12 mb-5 pb-5 pb-lg-0 mb-lg-0">

                            <h6>x办公</h6>

                            <h1 class="text-white mb-4">为更高效而生</h1>

                            <a href="#section_2" class="btn custom-btn smoothscroll me-3">了解产品</a>

                            <a href="#section_3" class="link link--kale smoothscroll">Meet the CEO</a>
                        </div>

                        <!-- <div class="hero-image-wrap col-lg-6 col-12 mt-3 mt-lg-0">
                            <img src="./images/education-online-books.png" class="hero-image img-fluid"
                                alt="education online books">
                        </div> -->

                    </div>
                </div>
            </section>


            <section class="featured-section">
                <div class="container">
                    <div class="row">

                        <div class="col-lg-8 col-12">
                            <div class="avatar-group d-flex flex-wrap align-items-center">
                                <img src="./images/lsx.jpg" class="img-fluid avatar-image" alt="">

                                <img src="./images/avatar/portrait-young-redhead-bearded-male.jpg"
                                    class="img-fluid avatar-image avatar-image-left" alt="">

                                <img src="./images/avatar/pretty-blonde-woman.jpg"
                                    class="img-fluid avatar-image avatar-image-left" alt="">

                                <img src="./images/avatar/studio-portrait-emotional-happy-funny-smiling-boyfriend.jpg"
                                    class="img-fluid avatar-image avatar-image-left" alt="">

                                <!-- <div class="reviews-group mt-3 mt-lg-0">
                                    <strong>4.5</strong>

                                    <i class="bi-star-fill"></i>
                                    <i class="bi-star-fill"></i>
                                    <i class="bi-star-fill"></i>
                                    <i class="bi-star-fill"></i>
                                    <i class="bi-star"></i>

                                    <small class="ms-3">2,564 reviews</small>
                                </div> -->
                            </div>
                        </div>
                    </div>
                </div>
            </section>


            <section class="py-lg-5"></section>


            <section class="book-section section-padding" id="section_2">
                <div class="container">
                    <div>
                        <el-form :inline="true" :model="findForm" ref="findForm" class="demo-form-inline">
                            <el-form-item label="产品名称">
                                <el-input v-model="findForm.name" placeholder="产品名"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="refresh()">查询</el-button>
                            </el-form-item>
                        </el-form>

                    </div>
                    <el-container>
                        <el-table :data="productData">
                            <el-table-column label="编号" width="180" prop="id"></el-table-column>
                            <el-table-column label="产品名" width="180" prop="name"></el-table-column>
                            <el-table-column label="单价" width="180" prop="price"></el-table-column>
                            <el-table-column label="操作">
                                <template slot-scope="scope">
                                    <el-button size="mini" @click="setInsert(scope.row)">添加订单</el-button>
                                    <!-- <el-button size="mini" type="danger" @click="confirm(scope.row)">删除</el-button> -->
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-container>
                    <div>我的订单</div>
                    <el-container>
                        <el-table :data="tableData">
                            <el-table-column label="编号" width="80" prop="id"></el-table-column>
                            <el-table-column label="客户名" width="80" prop="cname"></el-table-column>
                            <el-table-column label="产品名" width="180" prop="pname"></el-table-column>
                            <el-table-column label="数目" width="80" prop="number"></el-table-column>
                            <el-table-column label="总价" width="80" prop="amount"></el-table-column>
                            <el-table-column label="交货状态" width="100" prop="delivery">
                                <template slot-scope="scope">
                                    <i class="el-icon-circle-check" v-if="scope.row.delivery"></i>
                                    <i class="el-icon-circle-close" v-else></i>
                                </template>
                            </el-table-column>
                            <el-table-column label="付款状态" width="100" prop="payment">
                                <template slot-scope="scope">
                                    <i class="el-icon-circle-check" v-if="scope.row.payment"></i>
                                    <i class="el-icon-circle-close" v-else></i>
                                </template>

                            </el-table-column>
                            <el-table-column label="操作">
                                <template slot-scope="scope">
                                    <el-button size="mini" type="danger" @click="confirm(scope.row)">删除</el-button>
                                </template>
                            </el-table-column>
                        </el-table>
                    </el-container>

                </div>
            </section>


            <section>
                <div class="container">
                    <div class="row">

                        <div class="col-lg-12 col-12 text-center">

                            <h2 class="mb-5">公司简介</h2>
                        </div>

                        <div class="col-lg-4 col-12">
                            <nav id="navbar-example3" class="h-100 flex-column align-items-stretch">
                                <nav class="nav nav-pills flex-column">
                                    <a class="nav-link smoothscroll" href="#item-1">顶部</a>

                                    <a class="nav-link smoothscroll" href="#item-2">产品</a>

                                    <a class="nav-link smoothscroll" href="#item-3">成立时间</a>

                                    <a class="nav-link smoothscroll" href="#item-4">公司规模</a>

                                    <!-- <a class="nav-link smoothscroll" href="#item-5">未来发展</a> -->
                                </nav>
                            </nav>
                        </div>

                        <div class="col-lg-8 col-12">
                            <div data-bs-spy="scroll" data-bs-target="#navbar-example3" data-bs-smooth-scroll="true"
                                class="scrollspy-example-2" tabindex="0">
                                <div class="scrollspy-example-item" id="item-1">
                                    <h5>O_o服装集团</h5>

                                    <p>O_o服装集团成立与2025年1月1日,创始人为xjt,在2025年3月完成了第一条生产流水线开始生产,此后直到2026年1月所有流水线完工,于2027年1月上市</p>

                                    <blockquote class="blockquote">O_o科技有限公司</blockquote>

                                    <p>O_o科技有限公司成立于2030年5月9日,是O_o服装集团旗下的子公司,为扩大营收业务范围,提升我司在行业中的竞争力O_o服装集团时任CEO兼实际控股人决定成立O_o科技有限公司
                                    </p>
                                </div>

                                <div class="scrollspy-example-item" id="item-2">
                                    <h5>公司规模</h5>

                                    <p>目前O_o服装集团员工数量已达20000余人,其中生产岗位15000余人,技术岗1000余人,销售岗3000余人</p>

                                    <p>O_o服装集团于2026年净收入已达100000000000000000000津巴布韦,其中子公司占收入10%</p>

                                    <p>O_o服装集团及其子公司业务范围已包含30多个行业</p>

                                    <div class="row">
                                        <div class="col-lg-6 col-12 mb-3">
                                            <img src="./images/lsx.jpg" class="scrollspy-example-item-image img-fluid"
                                                alt="">
                                        </div>

                                        <div class="col-lg-6 col-12 mb-3">
                                            <img src="./images/businessman-sitting-by-table-cafe.jpg"
                                                class="scrollspy-example-item-image img-fluid" alt="">
                                        </div>
                                    </div>

                                </div>

                                <div class="scrollspy-example-item" id="item-3">
                                    <h5>未来发展</h5>


                                    <p>O_o服装集团有巨大的发展潜力</p>

                                    <div class="row align-items-center">
                                        <div class="col-lg-6 col-12">
                                            <img src="./images/tablet-screen-contents.jpg" class="img-fluid" alt="">
                                        </div>

                                        <div class="col-lg-6 col-12">
                                        </div>
                                    </div>
                                </div>

                                <div class="scrollspy-example-item" id="item-4">
                                    <!-- <h5>Delegate</h5>

                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor
                                        incididunt ut labore et dolore magna aliqua.</p>

                                    <p>Lorem ipsum dolor sit amet, consive adipisicing elit, sed do eiusmod. tempor
                                        incididunt ut labore.</p>

                                    <p>You are not allowed to redistribute this template ZIP file on any other template
                                        collection website. Please contact TemplateMo for more information.</p>

                                    <img src="./images/portrait-mature-smiling-authoress-sitting-desk.jpg"
                                        class="scrollspy-example-item-image img-fluid mb-3" alt=""> -->

                                    <p>You may want to contact us for more information about this template.</p>
                                </div>

                                <!-- <div class="scrollspy-example-item" id="item-5">
                                    <h5>Habits</h5>

                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor
                                        incididunt ut labore et dolore magna aliqua.</p>

                                    <p>You are not allowed to redistribute this template ZIP file on any other template
                                        collection website. Please contact TemplateMo for more information.</p>

                                    <p><strong>What is Free CSS Templates?</strong> Free CSS Templates are a variety of
                                        ready-made web pages designed for different kinds of websites.</p>

                                    <blockquote class="blockquote">Lorem Ipsum dolor sit amet, consectetur adipsicing kengan
                                        omeg kohm tokito</blockquote>

                                    <p>You may browse TemplateMo website for more CSS templates. Thank you for visiting our
                                        website.</p>
                                </div> -->
                            </div>
                        </div>

                    </div>
                </div>
            </section>


            <section class="author-section section-padding" id="section_3">
                <div class="container">
                    <div class="row">

                        <div class="col-lg-6 col-12">
                            <img src="./images/xjt.png" class="author-image img-fluid" alt="">
                        </div>

                        <div class="col-lg-6 col-12 mt-5 mt-lg-0">
                            <h6>创始人简介</h6>

                            <h2 class="mb-4">xjt</h2>

                            <p>2020年毕业于nbufe2022年开始创业...</p>


                        </div>

                    </div>
                </div>
            </section>


            <section class="reviews-section section-padding" id="section_4">
                <div class="container">
                    <div class="row">

                        <div class="col-lg-12 col-12 text-center mb-5">
                            <h6>重要团队</h6>

                        </div>

                        <div class="col-lg-4 col-12">
                            <div class="custom-block d-flex flex-wrap">
                                <div class="custom-block-image-wrap d-flex flex-column">
                                    <img src="./images/avatar/portrait-beautiful-young-woman-standing-grey-wall.jpg"
                                        class="img-fluid avatar-image" alt="">

                                    <div class="text-center mt-3">
                                        <span class="text-white">xjt</span>

                                        <strong class="d-block text-white">CEO</strong>
                                    </div>
                                </div>

                                <div class="custom-block-info">
                                    <div class="reviews-group mb-3">
                                    </div>

                                    <p class="mb-0">xjt，公司的创始人兼容CEO,吹牛就是第一生产力</p>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-4 col-12 my-5 my-lg-0">
                            <div class="custom-block d-flex flex-wrap">
                                <div class="custom-block-image-wrap d-flex flex-column">
                                    <img src="./images/xjt.png" class="img-fluid avatar-image avatar-image-left" alt="">

                                    <div class="text-center mt-3">
                                        <span class="text-white">xtj</span>

                                        <strong class="d-block text-white">COE</strong>
                                    </div>
                                </div>

                                <div class="custom-block-info">
                                    <div class="reviews-group mb-3">
                                    </div>

                                    <p class="mb-0">xtj，公司的创始人兼容COE,吹牛就是第一生产力</p>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-4 col-12">
                            <div class="custom-block d-flex flex-wrap">
                                <div class="custom-block-image-wrap d-flex flex-column">
                                    <img src="./images/avatar/pretty-blonde-woman.jpg" class="img-fluid avatar-image"
                                        alt="">

                                    <div class="text-center mt-3">
                                        <span class="text-white">jxt</span>

                                        <strong class="d-block text-white">ECO</strong>
                                    </div>
                                </div>

                                <div class="custom-block-info">
                                    <div class="reviews-group mb-3">
                                    </div>

                                    <p class="mb-0">jxt，公司的创始人兼容ECO,吹牛就是第一生产力</p>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </section>


            <section class="contact-section section-padding" id="section_5">
                <div class="container">
                    <div class="row">

                        <div class="col-lg-5 col-12 mx-auto">
                            <form class="custom-form ebook-download-form bg-white shadow" action="#" method="post"
                                role="form">
                                <div class="text-center mb-5">
                                    <h2 class="mb-1">Get your free ebook</h2>
                                </div>

                                <div class="ebook-download-form-body">
                                    <div class="input-group mb-4">
                                        <input type="text" name="ebook-form-name" id="ebook-form-name" class="form-control"
                                            aria-label="ebook-form-name" aria-describedby="basic-addon1"
                                            placeholder="Your Name" required>

                                        <span class="input-group-text" id="basic-addon1">
                                            <i class="custom-form-icon bi-person"></i>
                                        </span>
                                    </div>

                                    <div class="input-group mb-4">
                                        <input type="email" name="ebook-email" id="ebook-email" pattern="[^ @]*@[^ @]*"
                                            class="form-control" placeholder="your@company.com"
                                            aria-label="ebook-form-email" aria-describedby="basic-addon2" required="">

                                        <span class="input-group-text" id="basic-addon2">
                                            <i class="custom-form-icon bi-envelope"></i>
                                        </span>
                                    </div>

                                    <div class="col-lg-8 col-md-10 col-8 mx-auto">
                                        <button type="submit" class="form-control">Download ebook</button>
                                    </div>
                                </div>
                            </form>
                        </div>

                        <div class="col-lg-6 col-12">
                            <h6 class="mt-5">联系我们</h6>

                            <p class="mb-3">
                                <i class="bi-geo-alt me-2"></i>
                                联系电话
                            </p>

                            <p class="mb-2">
                                <a href="tel: 010-020-0340" class="contact-link">
                                    010-020-0340
                                </a>
                            </p>

                            <p class="copyright-text">更多资讯
                                <br><br><a rel="nofollow" href="https://templatemo.com"
                                    target="_blank">www.xjtOfficial.com</a>
                            </p>
                        </div>

                    </div>
                </div>
            </section>
            <el-dialog title="登录/注册" :visible.sync="loginVisible">
                <div>
                    <el-radio-group v-model="isCollapse" style="margin: 0;height: 0;">
                        <el-menu :router="true" mode="horizontal">
                            <el-menu-item :index="'/clogin'">我是客户</el-menu-item>
                            <el-menu-item :index="'/login'">我是员工</el-menu-item>
                        </el-menu>
                    </el-radio-group>
                    <el-container>
                        <el-main>
                            <router-view></router-view>
                        </el-main>
                    </el-container>
                </div>

            </el-dialog>
            <el-dialog title="添加订单" :visible.sync="insertFormVisible">
                <el-form :model="insertForm" :rules="ruleAdd" ref="insertForm">

                    <el-form-item label="产品" prop="pname" :label-width="formLabelWidth">
                        <el-input v-model="insertForm.pname" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="数量" prop="number" :label-width="formLabelWidth">
                        <el-input v-model.number="insertForm.number"></el-input>
                    </el-form-item>
                    <el-form-item label="总价" prop="amount" :label-width="formLabelWidth">
                        <el-input v-model.number="insertForm.amount" :disabled="true"></el-input>
                    </el-form-item>
                    <el-form-item label="销售员" prop="ename" :label-width="formLabelWidth">
                        <el-autocomplete v-model="insertForm.ename" value-key="name" value="id"
                            :fetch-suggestions="querySearch" placeholder="请输入内容" @select="handleSelect($event, insertForm)">
                        </el-autocomplete>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="insertOrder('insertForm')">确定</el-button>
                </div>

            </el-dialog>
        </main>


    </body>
</template>
<script>
// import "./images/businessman-sitting-by-table-cafe.jpg"
// import "./images/education-online-books.png"
import "./css/bootstrap.min.css"
import "./css/bootstrap-icons.css"
import "./css/templatemo-ebook-landing.css"
// import "./js/jquery.min.js"
import "./js/bootstrap.bundle.min.js"
import "./js/jquery.sticky.js"
// import "./js/click-scroll.js"
// import "./js/custom.js"
import request from '@/utils/request'
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
            islogin: false,
            price: 0,
            id: null,
            name: null,
            isCollapse: true,
            findForm: {
                name: null,
            },
            insertForm: {
                pid: null,
                pname: null,
                eid: null,
                ename: null,
                number: 0,
                amount: null,
                delivery: false,
                payment: false,
            },

            productData: [],
            tableData:[],
            insertFormVisible: false,
            loginVisible: false,

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
        refreshOrder() {
            request({
                url: '/order/findMyOrder',
                method: 'get',
            }).then(res => {
                if (res.data.isSuccess)
                    this.tableData = res.data.results
                else
                    this.$message.warning(res.data.results)
            })
        },
        findMe() {
            request({
                url: 'customer/myself',
                method: 'get',
            }).then(res => {
                if (res.data.isSuccess) {
                    this.name = res.data.results.name;
                    this.id = res.data.results.id;
                    this.islogin = true
                }
                console.log(this.name)
            })
        },
        setInsert(row) {
            if (!this.islogin) {
                this.$message.warning("请先登录")
                return;
            }
            this.insertForm.pid = row.id;
            this.insertForm.pname = row.name;
            this.insertFormVisible = true;
            this.price = row.price;
        },
        logout() {
            request({
                url: '/employee/logout',
                method: 'post',
            }).then(res => {
                this.islogin = false;
                this.name = null;
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
        querySearch(queryString, cb) {
            request({
                method: 'get',
                url: 'employee/find',
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
            form.eid = item.id;
        },
        insertOrder(from) {
            this.$refs[from].validate((valid) => {
                if (valid) {
                    request({
                        method: "post",
                        url: "/order/insert",
                        params: {
                            number: this.insertForm.number,
                            amount: this.insertForm.amount,
                            delivery: false,
                            payment: false,
                            pid: this.insertForm.pid,
                            cid: this.id,
                            eid: this.insertForm.eid,
                        }
                    }).then(res => {
                        this.$message.success(res.data.results);
                        this.insertFormVisible = false;
                        this.refreshOrder();
                    })
                }
            })
        },
        confirm(row) {//确认删除
            this.$confirm('是否确认该订单？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                request({
                    method: 'post',
                    url: 'order/deleteMyOrder',
                    params: {
                        id: row.id,
                    }
                }).then((res) => {
                    if (res.data.isSuccess == true) {
                        this.$message.success("删除成功")
                        this.refreshOrder();
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
    },
    watch: {
        // 监视numA的变化，并在其发生改变时手动修改numB的值
        'insertForm.number': function (newVal) {
            this.insertForm.amount = newVal * this.price;
        }
    },
    created() {
        this.findMe()
        this.refresh();
        this.refreshOrder();
    },
}
</script>

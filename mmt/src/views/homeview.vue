<template>
	<body>
		<!--头部区域-->
		<div class="header">
			<div class="wrapper">
				<!--logo-->
				<div class="logo">
					<h1><a href="#">数学建模工具箱</a></h1>
				</div>
				<!--导航-->
				<div class="nav">
					<ul>
						<li><a href="/homeview/mainv" class="active">首页</a></li>
						<li><a href="/homeview/paper_sug">关于论文</a></li>
						<li><a href="/homeview/match">比赛流程</a></li>
						<li><a href="/homeview/search">模型搜索</a></li>
						<li><a href="/homeview/ai">AI情感分析</a></li>
					</ul>
				</div>
				<!--搜索-->


				<!--用户-->

			</div>
		</div>


		<!--banner区域-->
		<div class="banner">
			<div class="wrapper">
				<div>
					<el-row>
						<el-col style="width: 245px;">
							<el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen"
								@close="handleClose" background-color="slategray" text-color="#fff"
								active-text-color="#ffd04b">

								<el-submenu index="1">
									<template slot="title">

										<span style="font-size: 17px;">回归模型</span>
									</template>
									<!--分组-->
									<el-menu-item-group>
										<el-menu-item index="1-1"><span><router-link to="/homeview/m11"
													class="modelname">一元线性回归</router-link></span></el-menu-item>
										<el-menu-item index="1-2"><span><router-link to="/homeview/m12"
													class="modelname">多项式回归</router-link></span></el-menu-item>
										
									</el-menu-item-group>
								</el-submenu>


								<el-submenu index="2">
									<template slot="title">

										<span style="font-size: 17px;">分类模型</span>
									</template>
									<!--分组-->
									<el-menu-item-group>
										<el-menu-item index="2-1"><span><router-link to="/homeview/m13"
													class="modelname">逻辑回归二分类</router-link></span></el-menu-item>
									</el-menu-item-group>
								</el-submenu>


								<el-submenu index="3">
									<template slot="title">

										<span style="font-size: 17px;">聚类模型</span>
									</template>
									<!--分组-->
									<el-menu-item-group>
										<el-menu-item index="3-1"><span><router-link to="/homeview/m31"
													class="modelname">kmeans++聚类</router-link></span></el-menu-item>
									</el-menu-item-group>
								</el-submenu>
								<el-submenu index="4">
									<template slot="title">

										<span style="font-size: 17px;">评价类算法</span>
									</template>
									<!--分组-->
									<el-menu-item-group>
										<el-menu-item index="4-1"><span><router-link to="/homeview/m41"
													class="modelname">一致性检验</router-link></span></el-menu-item>
									</el-menu-item-group>
								</el-submenu>


								<el-submenu index="5">
									<template slot="title">

										<span style="font-size: 17px;">求解定积分</span>
									</template>
									<!--分组-->
									<el-menu-item-group>
										<el-menu-item index="5-1"><span><router-link to="/homeview/m51"
													class="modelname">数值积分</router-link></span></el-menu-item>
									</el-menu-item-group>
								</el-submenu>


								<el-submenu index="6">
									<template slot="title">

										<span style="font-size: 17px;">时间序列模型</span>
									</template>
									<!--分组-->
									<el-menu-item-group>
										<el-menu-item index="6-1"><span><router-link to="/homeview/m61"
													class="modelname">时间序列预测</router-link></span></el-menu-item>
									</el-menu-item-group>
								</el-submenu>

								<el-submenu index="7">
									<template slot="title">

										<span style="font-size: 17px;">智能优化类算法</span>
									</template>
									<!--分组-->
									<el-menu-item-group>
										<el-menu-item index="7-1"><span><router-link to="/homeview/m71"
													class="modelname">粒子群</router-link></span></el-menu-item>
									</el-menu-item-group>
								</el-submenu>
								<el-submenu index="8">
									<template slot="title">

										<span style="font-size: 17px;">其他数学工具</span>
									</template>
									<!--分组-->
									<el-menu-item-group>
										<el-menu-item index="8-1"><span><router-link to="/homeview/o1"
													class="modelname">求解Pearson相关矩阵</router-link></span></el-menu-item>

									</el-menu-item-group>
								</el-submenu>

								<el-submenu index="9">
									<template slot="title">

										<span style="font-size: 17px;">论文</span>
									</template>
									<!--分组-->
									<el-menu-item-group>
										<el-menu-item index="9-1"><span><router-link to="/homeview/paper"
													class="modelname">论文模板</router-link></span></el-menu-item>
										<el-menu-item index="9-2"><span><router-link to="/homeview/paper_sug"
													class="modelname">论文建议</router-link></span></el-menu-item>
										<el-menu-item index="9-3"><span><router-link to="/homeview/literature"
													class="modelname">文献网站</router-link></span></el-menu-item>
									</el-menu-item-group>
								</el-submenu>

								<el-submenu index="10">
									<template slot="title">
										<span style="font-size: 17px;">数模比赛流程</span>
									</template>
									<!--分组-->
									<el-menu-item-group>
										<el-menu-item index="10-1"><span><router-link to="/homeview/match"
													class="modelname">数模比赛流程</router-link></span></el-menu-item>
									</el-menu-item-group>
								</el-submenu>


							</el-menu>
						</el-col>
						<el-col :span="20">
							<router-view>

							</router-view>
						</el-col>
					</el-row>
				</div>
			</div>
		</div>
	</body>
</template>

<script>
	export default {
		methods: {
			handleOpen(key, keyPath) {
				console.log(key, keyPath);
			},
			handleClose(key, keyPath) {
				console.log(key, keyPath);
			},
			modelSearch() {
				console.log(this.mname)
				let _this = this
				this.$http.get("http://81.70.53.188:8080/modelSearch", {
					params: {
						mname: this.mname
					}
				}).then(res => {
					_this.tableData = res.data.data
				})
			},
			///////////
			querySearch(queryString, cb) {
				var restaurants = this.restaurants;
				var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
				// 调用 callback 返回建议列表的数据
				cb(results);
			},
			createFilter(queryString) {
				return (restaurant) => {
					return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
				};
			},

			handleSelect(item) {
				console.log(item);
			},
			mounted() {
				this.restaurants = this.loadAll();
			}
			////////
		},

		data() {
			return {
				mname: "",
				tableData: [],
				value: '',
				restaurants: [],
				state1: '',
				state2: ''

			}
		},
	}
</script>

<style scoped>
	body {
		background-color: white;
	}

	/*头部区域*/
	.header {
		height: 100px;
		background-color: #ffffff;
		background-color: slategray;
	}

	.header .wrapper {
		display: flex;
		padding-top: 10px;
	}

	/*logo区域样式*/
	.logo {
		color: rgb(70, 53, 132);
		width: 250px;
		height: 50px;
		margin-left: 100px;
		margin-top: 5px;
		background-image: url("./images/logo.png");
		font-size: 0;
	}

	/*导航区域样式*/
	.nav {
		margin-left: 20px;
	}

	.nav ul {
		display: flex;
	}

	.nav li {
		margin-right: 24px;
	}

	.nav li a {
		display: block;
		padding: 10px 8px;
		line-height: 10px;
		padding-bottom: 12px;
		font-size: 19px;
		color: white;
		text-decoration: none;
		list-style-type: none;
	}

	/*active 类选择器,表示默认选中的a*/

	.nav li a:hover {
		border-bottom: 2px solid #00a4ff;
	}

	/*搜索框*/



	/*banner区域*/
	.banner {
		height: 815px;

	}

	.el-menu {
		height: 815px;
	}

	.modelname {
		color: white;
		font-size: 14px;
		text-decoration: none;
	}

	.modelname:hover {
		color: palevioletred;
	}

	.other {
		color: white;
		font-size: 14px;
	}
</style>
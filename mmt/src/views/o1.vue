<template>
	<div class="bg">
		<div class="m">
			<h1>求解Pearson相关矩阵</h1>
			<!--选择文件上传后端-->
			<div class="upload">
				<strong style="font-size: 20px;">点击此处上传excel文件:</strong> <input class="ipt" type="file"
					@change="handleFileUpload" />
				<button class="btn" @click="uploadFile">上传文件</button>
				<div style="color: red;">
					请确保excel中只含有数字类型的数据,且文件为xlsx类型
				</div>
				可点击此处下载测试数据excel表格-><a href="https://pan.baidu.com/s/1Zblx95XCLRPsZsnH14DSoQ?pwd=juzh"
					target="_blank">点此下载</a>
			</div>
			<!---->

			<h2>将在下方输出计算结果:</h2>
			<div v-if="uploadResult">
				<p><strong>行数:</strong> {{ uploadResult['Row count'] }}</p>
				<p><strong>列数:</strong> {{ uploadResult['Column count'] }}</p>
				<p><strong>行均值:</strong> {{ uploadResult['Row means'] }}</p>
				<p><strong>列均值:</strong> {{ uploadResult['Column means'] }}</p>
				<p><strong>列标准差:</strong> {{ uploadResult['Column standard deviations'] }}</p>
				<h3>Pearson相关矩阵:</h3>
				<div v-for="(row, rowIndex) in uploadResult['Pearson correlation matrix']" :key="rowIndex">
					<span v-for="(value, colIndex) in row" :key="colIndex">{{ value.toFixed(2) }} </span>
				</div>
			</div>
			<div v-if="loading" style=" padding: 0px;">
				<h3 class="load">正在计算中，请稍候...</h3>
				<img src="./images/loading.gif" alt="Loading" />
			</div>
		</div>
		<div class="baka">
			<h3 style="padding-left: 310px;">模型搜索</h3>
		
			<!--搜索框-->
			<el-col :offset="4" :span="5">
				<el-input v-model="mname" placeholder="请输入要搜索的模型名称" @input="modelSearch()"
					style="width: 300px; padding-left:90px ;"></el-input>
			</el-col>
		
			<!--表格-->
			<el-col :span="20" :offset="4">
				<br />
				<el-table :data="tableData" style="width: 85%;" max-height="400px" @row-click="handleRowClick"
					class="tablem">
					<el-table-column fixed prop="mid" label="模型编号" width="125">
					</el-table-column>
					<el-table-column prop="mname" label="模型" width="125">
					</el-table-column>
					<el-table-column prop="modeltype" label="模型类型" width="125">
					</el-table-column>
					<el-table-column prop="mtid" label="类型编号" width="125">
					</el-table-column>
		
				</el-table>
			</el-col>
		</div>
	</div>
</template>
<script>
	import axios from "axios";
	import gsap from 'gsap';

	export default {
		data() {
			return {
				selectedFile: null,
				uploadResult: null,
				loading: false,
				mname: "",
				tableData: [],
				value: ''
			};
		},
		created() {
			this.getAllModel()
		},
		mounted() {
			gsap.fromTo(
				'.m', {
					duration: 0.4,
					x: 0,
					delay: 0,
					opacity: 0
				}, {
					duration: 0.4,
					x: 50,
					delay: 0,
					opacity: 1
				})
		},
		methods: {
			handleRowClick(row, column, event) {
				if (row.mid === 2) {
					this.$router.push('/homeview/m12');
				} else if (row.mid === 3) {
					this.$router.push('/homeview/m13');
				} else if (row.mid === 4) {
					this.$router.push('/homeview/m31');
				} else if (row.mid === 5) {
					this.$router.push('/homeview/m41');
				} else if (row.mid === 6) {
					this.$router.push('/homeview/m51');
				} else if (row.mid === 7) {
					this.$router.push('/homeview/m61');
				} else if (row.mid === 8) {
					this.$router.push('/homeview/m71');
				} else if (row.mid === 9) {
					this.$router.push('/homeview/o1');
				}
			},
			
			getAllModel() {
				let _this = this
				this.$http.get("http://81.70.53.188:8080/getAllModel").then(res => {
					console.log(res)
					_this.tableData = res.data.data
				})
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
			
			handleFileUpload(event) {
				this.file = event.target.files[0];
			},
			//上传数据并计算传回数据
			async uploadFile() {
				this.loading = true;
				let formData = new FormData();
				formData.append("file", this.file);
				// 发送文件到后端
				try {
					const response = await axios.post('http://81.70.53.188:8080/upload', formData);
					this.uploadResult = response.data; // 假设后端返回的是直接可用的数据格式  
				} catch (error) {
					console.error('上传文件并处理时出错:', error);
				} finally {
					this.loading = false; // 无论成功还是失败，都将加载状态设置为false  
				}
			},
		}
	}
</script>

<style scoped>
	.baka {
	
		padding-top: 30px;
	}
	
	.bg {
		height: 795px;
		background-image: url("images/md.png");
		display: flex;
	}

	.m {
		padding-left: 0px;

	}

	.upload {
		height: 100px;
		width: 670px;
		background-color: #d3dce6;
		border-radius: 10px;
		padding-left: 20px;

	}

	.btn {
		color: white;
		font-size: 16px;
		height: 50px;
		background-color: #2D6EAF;
		border-radius: 5px;
	}

	.btn:hover {
		background-color: skyblue;
		color: deeppink;
	}

	.load {
		color: orangered;
	}
</style>
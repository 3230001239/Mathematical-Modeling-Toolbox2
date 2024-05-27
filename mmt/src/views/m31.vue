<template>
	<div class="bg">
		<div class="m">
			<h1>kmeans++聚类</h1>
			<div class="upload">
				<strong style="font-size: 20px;">点击此处上传csv文件:</strong> <input class="ipt" type="file" ref="fileInput"
					@change="handleFileChange" accept=".csv" />
				<button class="btn" @click="uploadFilem31">上传文件</button>
				<div style="color: red;">
					请上传csv文件
				</div>
				可点击此处下载测试数据csv文件-><a href="https://pan.baidu.com/s/1CdaRleLtZSyXTXbwluzMDw?pwd=jlmx"
					target="_blank">点此下载</a>
				<h2>将在下方输出计算结果:</h2>
				<div v-if="uploadResult" style="width:550px;">
					<p><strong>计算结果:</strong><br /> {{ uploadResult['a1'] }}</p>

				</div>
				<div v-if="loading" style=" padding: 0px;">
					<h3 class="load">正在计算中，请稍候...</h3>
					<img src="./images/loading.gif" alt="Loading" />
				</div>


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
	import axios from 'axios'; // 假设你使用axios作为HTTP客户端  
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
		created() {
			this.getAllModel()
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
			handleFileChange(event) {
				this.selectedFile = event.target.files[0];
			},

			async uploadFilem31() {
				this.loading = true;
				if (!this.selectedFile) {
					alert('请先选择一个文件！');
					return;
				}

				const formData = new FormData();
				formData.append('file', this.selectedFile);



				try {
					const response = await axios.post('http://81.70.53.188:8080/uploadm31', formData, {

						headers: {
							'Content-Type': 'multipart/form-data',
						},
					});
					this.uploadResult = response.data;
					console.log(response.data); // 处理响应数据  

				} catch (error) {
					console.error('文件上传失败：', error);
					alert('文件上传失败！');
				} finally {
					this.loading = false; // 无论成功还是失败，都将加载状态设置为false  
				}
			},
		},
	};
</script>

<style scoped>
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
	.baka {
	
		padding-top: 30px;
	}
	
	.bg {
		height: 795px;
		background-image: url("images/md.png");
		display: flex;
	}
	

	.load {
		color: orangered;
	}
</style>
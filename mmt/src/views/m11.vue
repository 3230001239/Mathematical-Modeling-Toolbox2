<template>
	<div class="bg">
		<!--实现功能-->
		<div class="m">
			<h1>一元线性回归</h1>

			<!--选择文件上传后端-->
			<div class="upload">
				<strong style="font-size: 20px;">点击此处上传excel文件:</strong> <input class="ipt" type="file"
					@change="handleFileUpload" />
				<button class="btn" @click="uploadFilem11">上传文件</button>
				<div style="color: red;">
					请确保excel中只含有数字类型的数据,且文件为xlsx类型
				</div>
				可点击此处下载测试数据excel表格-><a href="https://pan.baidu.com/s/1OrOxVjMRFwHpW9zDiNusrA?pwd=dyhg"
					target="_blank">点此下载</a>
			</div>
			<!--显示计算结果-->

			<h2>将在下方输出计算结果及图像:</h2>

			<div v-if="uploadResult">
				<p><strong>斜率:</strong> {{ uploadResult['k'] }}</p>
				<p><strong>截距:</strong> {{ uploadResult['b'] }}</p>
				<p><strong>R-squared 值(表示模型的拟合程度):</strong> {{ uploadResult['R2'] }}</p>
			</div>

			<!--显示缓冲动画-->
			<div v-if="loading" style=" padding: 0px;">
				<h3 class="load">正在计算中，请稍候...</h3>
				<img src="./images/loading.gif" alt="Loading" />
			</div>
			<div ref="chartContainer" style="width: 600px; height: 400px;"></div>
		</div>

		<!--右侧模型搜索-->
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
	import * as echarts from 'echarts';
	import axios from "axios";
	import gsap from 'gsap';

	export default {
		data() {
			return {
				selectedFile: null,
				uploadResult: null,
				chartInstance: null,
				xValues: [],
				yValues: [],
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
			this.initChart();
			//this.fetchData();  
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

			initChart() {
				this.chartInstance = echarts.init(this.$refs.chartContainer);
			},

			updateChart() {
				const fittedLineData = this.generateFittedLineData(this.uploadResult['k'], this.uploadResult['b'], this
					.xValues);
				const option = {
					backgroundColor: '#f4f4f4',
					title: {
						text: '散点图与拟合曲线',
						left: 'left',
					},
					tooltip: {
						trigger: 'item',
						formatter: '{a} <br/>{b}: {c}'
					},
					legend: {
						data: ['散点图', '拟合曲线'],
					},
					grid: {
						left: '3%',
						right: '4%',
						bottom: '3%',
						containLabel: true
					},
					xAxis: {
						type: 'value',
						name: 'X轴',
						axisLabel: {
							formatter: '{value}'
						},
						splitLine: {
							show: false
						}
					},
					yAxis: {
						type: 'value',
						name: 'Y轴',
						axisLabel: {
							formatter: '{value}'
						},
						splitLine: {
							show: false
						}
					},
					series: [{
						name: '散点图',
						type: 'scatter',
						data: this.xValues.map((xValue, index) => [xValue, this.yValues[index]]),
						symbolSize: 10,
						label: {
							show: true,
							formatter: '{b}',
							position: 'top'
						},
						itemStyle: {
							color: '#ff0000' // 红色散点  
						},
						emphasis: {
							label: {
								show: true
							},
							itemStyle: {
								color: '#ff0000' // 高亮时的颜色  
							}
						}
					}, {
						name: '拟合曲线',
						type: 'line',
						smooth: true,
						data: fittedLineData,
						lineStyle: {
							color: 'blue', // 绿色拟合曲线  
							width: 3, // 线条宽度
							type: 'solid' // 线条类型  
						},
						areaStyle: { // 填充区域样式
							color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
								offset: 0,
								color: 'rgba(0,0,255,0.13)' // 起始颜色  
							}, {
								offset: 1,
								color: 'rgba(0,0,255,0)' // 结束颜色  
							}])
						}
						// 可以添加填充区域样式  
					}]
				};

				this.chartInstance.setOption(option);
			},

			generateFittedLineData(slope, intercept, xValues) {
				return xValues.map(x => [x, slope * x + intercept]);
			},

			beforeDestroy() {
				if (this.chartInstance) {
					this.chartInstance.dispose(); // 销毁图表实例，释放内存  
				}
			},

			//上传文件
			handleFileUpload(event) {
				this.file = event.target.files[0];
			},

			async uploadFilem11() {
				this.loading = true;
				if (!this.file) {
					alert('请先选择一个文件！');
					return;
				}
				let formData = new FormData();
				formData.append("file", this.file);
				// 发送文件到后端
				try {
					const response = await axios.post('http://81.70.53.188:8080/uploadm11', formData);
					this.uploadResult = response.data;
					this.xValues = response.data.x;
					this.yValues = response.data.y;

					this.updateChart(); // 假设后端返回的是直接可用的数据格式  
				} catch (error) {
					console.error('上传文件并处理时出错:', error);
				} finally {
					this.loading = false; // 无论成功还是失败，都将加载状态设置为false  
				}
			}
		},
	}
</script>

<style scoped>
	.baka {

		padding-top: 30px;
	}
	.bg {
		height: 795px;
		background-image: url("images/m11d.png");
		display: flex;
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
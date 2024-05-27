<template>
	<div class="bg">
		<div class="m">
			<h1>多项式回归</h1>
			<div class="upload">
				<strong style="font-size: 20px;">点击此处上传excel文件:</strong> <input class="ipt" type="file"
					@change="handleFileUpload" />
				<button class="btn" @click="uploadFilem12">上传文件</button>
				<div style="color: red;">
					请确保excel中只含有数字类型的数据,且文件为xlsx类型
				</div>
				可点击此处下载测试数据excel表格-><a href="https://pan.baidu.com/s/1xilQDsAXA22PJ2dmFxVJ6g?pwd=dxsr"
					target="_blank">点此下载</a>
			</div>

			<h2>将在下方输出计算结果及图像:</h2>
			<div v-if="uploadResult">
				<p><strong>Coefficient:</strong> {{ uploadResult['Coefficient'] }}</p>
			</div>
			<div v-if="loading" style=" padding: 0px;">
				<h3 class="load">正在计算中，请稍候...</h3>
				<img src="./images/loading.gif" alt="Loading" />
			</div>
			<div ref="chartContainer" style="width: 600px; height: 400px;"></div>
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
	import * as echarts from 'echarts';
	import gsap from 'gsap';
	export default {
		data() {
			return {
				selectedFile: null,
				uploadResult: null,
				chart: null,
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
				const chartContainer = this.$refs.chartContainer;
				this.chart = echarts.init(chartContainer);

			},
			handleFileUpload(event) {
				this.file = event.target.files[0];
			},
			async uploadFilem12() {
				this.loading = true;
				if (!this.file) {
					alert('请先选择一个文件！');
					return;
				}
				let formData = new FormData();
				formData.append("file", this.file);
				try {
					const response = await axios.post('http://81.70.53.188:8080/uploadm12', formData);
					this.uploadResult = response.data;
					this.xValues = this.uploadResult.x;
					this.yValues = this.uploadResult.y;
					this.updateChartData();
				} catch (error) {
					console.error('上传文件并处理时出错:', error);
				} finally {
					this.loading = false; // 无论成功还是失败，都将加载状态设置为false  
				}
			},
			updateChartData() {
				const option = {
					backgroundColor: '#f4f4f4', // 添加背景色  
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
						splitLine: {
							show: true,
							lineStyle: {
								type: 'dashed'
							}
						}
					},
					yAxis: {
						type: 'value',
						name: 'Y轴',
						splitLine: {
							show: true,
							lineStyle: {
								type: 'dashed'
							}
						}
					},
					series: [{
						name: '散点图',
						type: 'scatter',
						data: this.uploadResult.x.map((x, index) => [x, this.uploadResult.y[index]]),
						symbolSize: 10,
						label: {
							show: true,
							formatter: '{b}',
							position: 'top'
						},
						itemStyle: {
							color: '#ff0000', // 红色散点  
							borderColor: '#fff', // 散点边框颜色  
							borderWidth: 1 // 散点边框宽度  
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
						data: this.generateFittedCurveData(this.uploadResult.x, this.uploadResult.Coefficient),
						lineStyle: {
							color: 'blue', // 蓝色拟合曲线  
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
					}]
				};
				this.chart.setOption(option);
			},
			generateFittedCurveData(xValues, coefficients) {
				return xValues.map(x => {
					let y = 0;
					for (let i = 0; i < coefficients.length; i++) {
						y += coefficients[i] * Math.pow(x, i);
					}
					return [x, y];
				});
			},
			beforeDestroy() {
				if (this.chart) {
					this.chart.dispose();
				}

			}
		}
	}
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
		background-image: url("images/m12d.png");
		display: flex;
	}

	.load {
		color: orangered;
	}
</style>
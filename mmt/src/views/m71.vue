<template>
	<div class="tt">
		<div class="m">
		<h1>粒子群</h1>
		<div v-for="(input, index) in inputs" :key="index" class="box">
			<input type="number" v-model.number="inputs[index]" placeholder="请输入数字">
		</div>
		<br />
		<button @click="submitData" style="margin-left: 210px;" class="btn">提交数据</button>
		<h2>将在下方输出计算结果:</h2>
		<div v-if="uploadResult">
			<p><strong>最佳的位置是:</strong> {{ uploadResult['bestposition'] }}</p>
			<p><strong>此时最优值是:</strong> {{ uploadResult['bestvalue'] }}</p>
		</div>
		<div ref="chartContainer" style="width: 600px; height: 300px;"></div>
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
		name: 'ECharts3DScatterPlot',
		mounted() {
			this.initChart(); // 初始化图表，如果需要的话也可以在这里调用 this.fetchData() 来获取数据并显示第一组散点图。  
			// 获取后端数据，并启动自动轮播。确保此行在initChart之后，因为需要在图表初始化后才能绘制散点图。  
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
		data() {
			return {
				inputs: [10, 4, 4, 0.95, 90, 1.9, -10, 10],
				selectedFile: null,
				uploadResult: null,
				allData: [], // 存储从后端接收到的所有数据  
				currentGroupIndex: 0, // 当前显示的散点图组索引  
				groupSize: 10,
				mname: "",
				tableData: [],
				value: ''


			};
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

			showGroup() {
				const startIndex = this.currentGroupIndex * this.groupSize;
				const endIndex = startIndex + this.groupSize;
				const currentGroupData = this.allData.slice(startIndex, endIndex);
				const xValues = currentGroupData.map(item => item[0]);
				const yValues = currentGroupData.map(item => item[1]);
				const zValues = currentGroupData.map(item => item[2]);
				this.drawScatterPlot(xValues, yValues, zValues); // 绘制当前组的散点图  
			},
			startRotation() {
				setInterval(() => {
					this.currentGroupIndex = (this.currentGroupIndex + 1) % (this.allData.length / this.groupSize);
					this.showGroup(); // 更新并显示下一组散点图  
				}, 2000); // 每2秒更新一次散点图组，你可以根据需要调整这个时间间隔  
			},
			initChart() {
				// 基于准备好的dom，初始化echarts实例  
				const myChart = echarts.init(this.$refs.chartContainer);

				// 定义x, y的范围和步长  
				const minX = -10,
					maxX = 10;
				const minY = -10,
					maxY = 10;
				const step = 0.5;

				// 生成网格数据  
				let data = [];
				for (let x = minX; x <= maxX; x += step) {
					for (let y = minY; y <= maxY; y += step) {
						let z = -(x * x + y * y + 5 * x - 6 * y);
						data.push([x, y, z]);
					}
				}

				// 指定图表的配置项和数据  
				const option = {
					tooltip: {},
					xAxis3D: {},
					yAxis3D: {},
					zAxis3D: {},
					grid3D: {
						boxWidth: 200,
						boxDepth: 80,
						viewControl: {
							// 投影模式为正交投影  
							projection: 'orthographic'
						},
						light: {
							main: {
								intensity: 1.2,
								shadow: true
							},
							ambient: {
								intensity: 0.3
							}
						}
					},
					series: [{
						type: 'surface', // 使用曲面图来表示函数图像  
						data: data,
						shading: 'color', // 添加颜色阴影效果  
						color: 'pink', // 设置曲面颜色  
						wireframe: { // 线框效果设置  
							show: true // 是否显示线框  
						}
					}]
				};

				// 使用刚指定的配置项和数据显示图表  
				myChart.setOption(option);
			},

			submitData() {
				
				if (this.inputs.every(input => !isNaN(input))) {
					axios.post('http://81.70.53.188:8080/uploadm71', {
							numbers: this.inputs
						})
						.then(response => {
							// 处理后端返回的数据  
							const {
								x,
								y,
								z
							} = response.data;
							this.drawScatterPlot(x, y, z);
							this.uploadResult = response.data; // 将返回的数据赋值给 uploadResult  
							this.allData = response.data; // 假设后端返回的是一个包含所有三维坐标点的数组
							this.showGroup(); // 显示第一组散点图  
							this.startRotation();
						})
						.catch(error => {
							// 处理错误  
							console.error(error);
						});
				} else {
					alert('请输入有效的数字');
				}
			},
			drawScatterPlot(xValues, yValues, zValues) {
				const myChart = echarts.init(this.$refs.chartContainer);
				// 转换成ECharts需要的散点数据格式  
				const scatterData = xValues.map((x, index) => [x, yValues[index], zValues[index]]);

				const option = {
					tooltip: {},
					xAxis3D: {},
					yAxis3D: {},
					zAxis3D: {},
					grid3D: {
						boxWidth: 200,
						boxDepth: 80,
						viewControl: {
							projection: 'orthographic'
						},
						light: {
							main: {
								intensity: 1.2,
								shadow: true
							},
							ambient: {
								intensity: 0.3
							}
						}
					},
					series: [{
						type: 'scatter3D',
						data: scatterData,
						symbolSize: 7,
						itemStyle: {
							color: 'rgba(255,0,0,1)' // 可以自定义散点的颜色  
						}
					}]
				};

				// 使用配置项更新图表  
				myChart.setOption(option);

			},

		}
	};
</script>

<style scoped>
	.baka {
	padding-left: 90px;
		padding-top: 30px;
	}
	

	.tt {

		height: 795px;
		background-image: url("images/md.png");
		display: flex;
	}

	.box {
		margin-left: 170px;
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
</style>
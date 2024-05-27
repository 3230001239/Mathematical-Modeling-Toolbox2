<template>
	<div class="bg">
		<div class="m">
			<h1>AI情感分析</h1>
			<div class="upload">
				<strong style="font-size: 20px;">点击此处上传excel文件:</strong> <input class="ipt" type="file" ref="fileInput"
					@change="handleFileUpload" />
				<button class="btn" @click="uploadFileai" type="primary">上传文件</button>
				<div style="color: red;">
					请上传xlsx文件
				</div>
				可点击此处下载测试数据xlsx文件-><a href="https://pan.baidu.com/s/1PnZ_mGmYb2zqwIOuvvtdOQ?pwd=jlmx"
					target="_blank">点此下载</a>

				<h2>将在下方输出计算结果:</h2>
				<div v-if="uploadResult" style="width:550px;">
					<p><strong>情感积极的评论:</strong> {{ uploadResult['02'] }}</p>
					<p><strong>情感中立的评论:</strong> {{ uploadResult['01'] }}</p>
					<p><strong>情感消极的评论:</strong> {{ uploadResult['00'] }}</p>
				</div>

				<div v-if="loading" style=" padding: 0px;">
					<h3 class="load">正在计算中，请稍候...</h3>
					<img src="./images/loading.gif" alt="Loading" />
				</div>

				<div ref="chart" style="width: 600px; height: 400px;"></div>

			</div>
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
				chart: null,
				loading: false,
			};
		},
		mounted() {
			this.chart = echarts.init(this.$refs.chart); // 初始化图表  
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
			handleFileUpload(event) {
				this.file = event.target.files[0];
			},
			//上传数据并计算传回数据
			async uploadFileai() {
				this.loading = true;
				let formData = new FormData();
				formData.append("file", this.file);
				// 发送文件到后端
				try {
					const response = await axios.post('http://localhost:8080/uploadai', formData);
					this.renderChart(response.data);
					this.uploadResult = response.data; // 假设后端返回的是直接可用的数据格式  
				} catch (error) {
					console.error('上传文件并处理时出错:', error);
				} finally {
					this.loading = false; // 无论成功还是失败，都将加载状态设置为false  
				}
			},
			renderChart(data) {

				const chartData = [{
						name: '消极',
						value: parseFloat(data['00'])
					},
					{
						name: '中立',
						value: parseFloat(data['01'])
					},
					{
						name: '积极',
						value: parseFloat(data['02']),
					}
				];
				const option = {  
				        title: {  
				          text: '评论情感分布',  
				          left: 'center',  
				        },  
				        tooltip: {  
				          trigger: 'item',  
				          formatter: '{a} <br/>{b}: {c} ({d}%)',  
				        },  
				        legend: {  
				          orient: 'vertical',  
				          left: 'left',  
				        },  
				        series: [  
				          {  
				            name: '情感',  
				            type: 'pie',  
				            radius: '75%',  
				            center: ['50%', '60%'],  
				            data: chartData,  
				            emphasis: {  
				              itemStyle: {  
				                shadowBlur: 10,  
				                shadowOffsetX: 0,  
				                shadowColor: 'rgba(0, 0, 0, 0.5)',  
				              },  
				            },  
				            label: {  
				              show: true,  
				              position: 'left',  
				              formatter: '{d}%',  
				              fontSize: 18,  
				              fontWeight: 'bold',  
				            },  
				            labelLine: {  
				              show: false,  
				            },  
				            itemStyle: {  
				              borderColor: '#fff',  
				              borderWidth: 1,  
				            },  
				          },  
				        ],  
				      };  
				this.chart.setOption(option); // 渲染图表  
			},
		},
		beforeDestroy() {
			if (this.chart) {
				this.chart.dispose(); // 销毁图表实例，释放资源  
			}
		},
	}
</script>

<style scoped>

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

	.bg {
		height: 795px;
		background-image: url("images/umaru_bg.png");
		

	}
	.load{
		color: orangered;
	}
</style>
<template>
	<div>
		<h1>模型搜索</h1>
		<el-row>
			<el-col :offset="4" :span="5">
				<el-input v-model="mname" placeholder="请输入要搜索的模型名称" @input="modelSearch()"></el-input>
			</el-col>
			<el-col :span="5" :offset="1">
				<el-select v-model="value" placeholder="请选择模型类型" @change="modelType()">
					<el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
					</el-option>
				</el-select>
			</el-col>

		</el-row>
		<br />
		<el-row>

			<el-col :span="16" :offset="4">
				<el-table :data="tableData" style="width: 85%" max-height="400px" @row-click="handleRowClick">
					<el-table-column fixed prop="mid" label="模型编号" width="150">
					</el-table-column>
					<el-table-column prop="mname" label="模型" width="200">
					</el-table-column>
					<el-table-column prop="modeltype" label="模型类型" width="200">
					</el-table-column>
					<el-table-column prop="mtid" label="类型编号" width="200">
					</el-table-column>

				</el-table>
			</el-col>
		</el-row>
	</div>
</template>

<script>
	export default {
		methods: {
			handleRowClick(row, column, event) {
				if (row.mid === 1) {
					// 当点击的行的mid为1时，进行页面跳转  
					this.$router.push('/homeview/m11');
				} else if (row.mid === 2) {
					// 当点击的行的mid为1时，进行页面跳转  
					this.$router.push('/homeview/m12');
				} else if (row.mid === 3) {
					// 当点击的行的mid为1时，进行页面跳转  
					this.$router.push('/homeview/m13');
				} else if (row.mid === 4) {
					// 当点击的行的mid为1时，进行页面跳转  
					this.$router.push('/homeview/m31');
				} else if (row.mid === 5) {
					// 当点击的行的mid为1时，进行页面跳转  
					this.$router.push('/homeview/m41');
				} else if (row.mid === 6) {
					// 当点击的行的mid为1时，进行页面跳转  
					this.$router.push('/homeview/m51');
				} else if (row.mid === 7) {
					// 当点击的行的mid为1时，进行页面跳转  
					this.$router.push('/homeview/m61');
				} else if (row.mid === 8) {
					// 当点击的行的mid为1时，进行页面跳转  
					this.$router.push('/homeview/m71');
				} else if (row.mid === 9) {
					// 当点击的行的mid为1时，进行页面跳转  
					this.$router.push('/homeview/o1');
				}
				// 可以在这里添加其他mid值的处理逻辑  
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

			modelType() {
				console.log(this.value)
				let _this = this
				this.$http.get("http://81.70.53.188:8080/modelType", {
					params: {
						mtid: this.value
					}
				}).then(res => {
					_this.tableData = res.data.data
				})
			}
		},
		data() {
			return {

				mname: "",
				tableData: [],
				options: [{
					value: 1,
					label: '回归模型'
				}, {
					value: 2,
					label: '分类模型'
				}, {
					value: 3,
					label: '聚类模型'
				}, {
					value: 4,
					label: '评价类模型'
				}, {
					value: 5,
					label: '求解定积分'
				}, {
					value: 6,
					label: '时间序列模型'
				}, {
					value: 7,
					label: '智能优化类模型'
				}, {
					value: 8,
					label: '其他数学工具'
				}],
				value: ''

			}
		},
		created() {
			this.getAllModel()
		}
	}
</script>

<style scoped>
	
	h1 {
		padding-left: 530px;
	}
</style>
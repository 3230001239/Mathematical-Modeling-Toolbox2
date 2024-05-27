<template>
	<div class="aa">
		<div class="register">
			<div style="text-align: center;color: white;font-size: 30px;"><strong>注册系统</strong></div>
			<br />
			<div style="color: white;"><strong>用户名:</strong><el-input v-model="uname" placeholder="请输入您的用户名"></el-input>
			</div>
			<div style="color: white;"><strong>手机号:</strong><el-input v-model="utele" placeholder="请输入您的手机号"></el-input>
			</div>
			<div style="color: white;"><strong>密码:</strong><el-input v-model="upwd" placeholder="请输入您的密码"></el-input>
			</div>
			<br /><br />

			<div style="text-align: center;"><button class="btnreg" @click="reg()">注册</button></div>
		</div>
	</div>

</template>

<script>
	export default {
		data() {
			return {
				uname: "",
				utele: "",
				upwd: ""
			}
		},
		methods: {
			reg() {
				this.$http.get(`http://81.70.53.188:8080/addUser?uname=${this.uname}&utele=${this.utele}&upwd=${this.upwd}`)
					.then(res => {
						console.log(res)
						if (res.data.code == 200) {
							alert("注册成功")
							this.$router.push('/')
						}
					})
			}
		}
	}
</script>

<style scoped>
	.register {
		margin-top: 100px;
		margin-left: 600px;
		height: 500px;
		width: 400px;
		background-color: #0092cb;
		padding: 40px;
		line-height: 40px;
		border-radius: 20px;
	}

	.btnreg {
		height: 60px;
		width: 150px;
		font-size: 30px;
		border-radius: 10px;
		border: none;
	}

	.btnreg:hover {
		background-color: powderblue;
		border: none;
		color: white;
	}

	.aa {}
</style>
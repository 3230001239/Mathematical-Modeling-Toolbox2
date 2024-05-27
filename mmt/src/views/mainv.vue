<template>
	<div>
		<div style="text-align: center;">
			<h1>WELCOME TO OUR WEB</h1>
		</div>
		<br />
		<div class="show">
			<el-carousel :interval="4000" type="card" height="330px">
				<el-carousel-item v-for="item in 6" :key="item">
				</el-carousel-item>
			</el-carousel>
		</div>
		<div style="text-align: center;">
			<h2>本网站实现的功能</h2>

		</div>
		<div class="gn">
			<strong>网站功能:</strong><br />
			<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本网站致力于为用户提供便捷的数学建模服务。我们为网站用户提供了基础的数学建模算法、论文写作模板及建议、文献网站和数模比赛流程。核心功能在于，用户能够轻松上传包含数据的Excel文件至网页端，随后，我们的网站将分析这些数据，并利用这些数据代入算法进行数学建模计算。经过计算后，网站会即时展示清晰的计算结果，助力用户更好地理解数据，助力用户更便捷地进行数学建模。同时还会生成相应的清晰易懂的图像供用户使用,方便用户更好地理解数学模型。我们也支持模型查找功能，方便用户更好地寻找自己需要的算法和模型。如果您是数学建模爱好者，本网站将是您得力的助手。为方便测试网站功能,我们为您提供了简单的测试数据供您使用。
			</p>
		</div>

	</div>
	</div>
</template>

<script>
</script>

<style scoped>
	.el-carousel__item h3 {
		color: #475669;
		font-size: 14px;
		opacity: 0.75;
		line-height: 200px;
		margin: 0;
	}

	.el-carousel__item:nth-child(6n) {
		background-image: url("images/starbgi(3).jpg");
	}

	.el-carousel__item:nth-child(6n+1) {
		background-image: url("images/bgi (1).jpg");
	}

	.el-carousel__item:nth-child(6n+2) {
		background-image: url("images/bgbg.jpg");
	}

	.el-carousel__item:nth-child(6n+3) {
		background-image: url("images/background_pic_1_11.png");
	}

	.el-carousel__item:nth-child(6n+4) {
		background-image: url("images/math.jpg");
	}

	.el-carousel__item:nth-child(6n+5) {
		background-image: url("images/stars.jpg");
	}

	.gn {
		width: 1320px;
		height: 250px;
		background-color: slategray;
		color: white;
		padding-left: 20px;
		padding-right: 20px;
		padding-top: 20px;
		margin-left: 20px;
		border-radius: 10px;
		font-size: 18px;
	}
</style>
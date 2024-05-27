<template>
	<div class="total">
		<h1>论文模板</h1>
		<div>
			<h2>国赛论文模板</h2>
			<div class="cu">
			Word版本: <a href="https://pan.baidu.com/s/160bpdTtPpud8w0jFrTZFLg?pwd=cmwd" target="_blank">点击此处下载国赛论文word版本</a>
			&nbsp;&nbsp;&nbsp;注:提取码为cmwd
			<br /><br />
			Latex版本: <a href="https://pan.baidu.com/s/16eRxbZeSFPg4k1k4KoDJ5Q?pwd=cmlt" target="_blank">点击此处下载国赛论文latex版本</a>
			&nbsp;&nbsp;&nbsp;注:提取码为cmlt
			</div>
			
			<h2>美赛论文模板</h2>
			<div class="cu">
			Word版本: <a href="https://pan.baidu.com/s/13nr9U1KDcNp_jTo7DAadJg?pwd=word" target="_blank">点击此处下载美赛论文word版本</a>
			&nbsp;&nbsp;&nbsp;注:提取码为word
			<br /><br />
			Latex版本: <a href="https://pan.baidu.com/s/1jvHyaHLber-Z3RQ9PDUXxg?pwd=latx"  target="_blank">点击此处下载美赛论文latex版本</a>
			&nbsp;&nbsp;&nbsp;注:提取码为latx	
			</div>
		</div>
		<div class="block">
		   <span class="demonstration"></span>
		   <el-carousel height="300px">
		     <el-carousel-item v-for="item in 4" :key="item">
		       
		     </el-carousel-item>
		   </el-carousel>
		 </div>
	</div>
	
</template>

<script>


</script>

<style scoped>
	.total{
		padding-top: 20px;
		text-align: center;
	}
	.cu{
		width: 1000px;
		height: 90px;
		background-color: slategray;
		color: white;
		margin-left: 200px;
		border-radius: 10px;
		padding-top: 20px;
	}
	a{
		color: white;
	}
	a:hover{
		color: hotpink;
	}
	.el-carousel__item h3 {
	   color: #475669;
	   font-size: 14px;
	   opacity: 0.75;
	   line-height: 150px;
	   margin: 0;
	 }
	
  .el-carousel__item:nth-child(4n) {
     background-image: url("images/57934e86-3702-4056-84fc-38414e592f1b(1).jpg");
  }
  
  .el-carousel__item:nth-child(4n+1) {
     background-image:url("images/dedeb137-0ad3-4b40-b882-f2f2d4eb313b(1).jpg");
  }
  .el-carousel__item:nth-child(4n+2) {
     background-image:url("images/e4013092-6d88-413c-92c0-92abfd76a1f4(1).jpg");
  }
  .el-carousel__item:nth-child(4n+3) {
     background-image:url("images/b0e4bb20-cca1-4ceb-b3ac-e93ee943e85f(1).jpg");
  }
	.block{
		padding-left: 150px;
		padding-right: 150px;
		margin-top: 50px;
	}
</style>
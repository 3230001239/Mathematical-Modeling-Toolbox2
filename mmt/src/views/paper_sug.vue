<template>
<body>
	<el-tabs v-model="activeName" @tab-click="handleClick" type="border-card" class="tab">
		<el-tab-pane label="摘要" name="first">
			<h3 class="tt">关于摘要的建议:</h3>
			<span >
				<div class="tt"><h4>摘要:</h4></div>
				<div class="tab1">
				摘要——评委对摘要非常重视，在评奖时，往往根据摘要的质量来评级。摘要是论文的重要组成部分，可以置换下角度，想象你是评委，看了你的摘要会不会选择继续阅读论文正文。注意：摘要中的竞赛问题重述，不要完全从题目复制粘贴，这样通常会影响评委评分，拿奖概率低.
				</div>
			</span>
		
		</el-tab-pane>
		
		<el-tab-pane label="重述问题" name="second">
			<h3 class="tt">关于重述问题的建议:</h3>
				<span >
					<div class="tt"><h4>重述问题:</h4></div>
					<div class="tab1">
					酌情简述问题，结合背景解释解决问题的实际意义，必要性，解决了会怎么样，不解决会怎么样.
					</div>
				</span>
		</el-tab-pane>
		
		<el-tab-pane label="模型假设与符号说明" name="third">
			<h3 class="tt">关于模型假设与符号说明的建议:</h3>
			<span >
				<div class="tt"><h4>模型假设:</h4></div>
				<div class="tab1">
				模型假设 : 对所有变量和假设进行清晰阐述，说明或证明和问题相关的合理假设.
				</div>
				<br />
				<div class="tt"><h4>符号说明:</h4></div>
				<div class="tab1">
				符号说明 : 将解决问题时计算、推导所涉及的数学符号，在附录中说明.
				</div>
			</span>
			
		</el-tab-pane>
		
		<el-tab-pane label="模型建立" name="fourth">
			<h3 class="tt">关于模型建立的建议:</h3>
			<span >
				<div class="tt"><h4>模型建立:</h4></div>
				<div class="tab1">
				根据问题描述选用合适算法，写明算法的使用步骤、使用原理、列出必要项，模型建立一般有两种方法，一是借鉴别人的模型来解决问题，二是基于现有的模型加以改进，从而达到解决问题的目的。后者创新度更高但难度稍大.
				</div>
			</span>
		</el-tab-pane>
		<el-tab-pane label="模型检验与模型讨论" name="fifith">
			<h3 class="tt">关于模型检验与模型讨论的建议:</h3>
			<span >
				<div class="tt"><h4>模型检验:</h4></div>
				<div class="tab1">
				模型检验 : 即测试模型，包括误差分析、灵敏度、稳定性等.
				</div>
				<br />
				<div class="tt"><h4>模型讨论:</h4></div>
				<div class="tab1">
				模型讨论 : 讨论模型或方法的明显优势或劣势，摘要中只写优点.
				</div>
			</span>
		</el-tab-pane>
		
		<el-tab-pane label="总结与附录" name="sixth">
			<h3 class="tt">关于总结与附录的建议:</h3>
			<span >
				<div class="tt"><h4>总结:</h4></div>
				<div class="tab1">
				总结 : 提供结论，并输出明确报告结果.
				</div>
				<br />
				<div class="tt"><h4>附录:</h4></div>
				<div class="tab1">
				附录 : 记录所参考的文献、使用过的算法程序.
				</div>
			</span>
		</el-tab-pane>
	
	</el-tabs>
	<div class="block">
	   <span class="demonstration"></span>
	   <el-carousel height="300px">
	     <el-carousel-item v-for="item in 4" :key="item">
	       
	     </el-carousel-item>
	   </el-carousel>
	 </div>

</body>
</template>
<script>
  export default {
    data() {
      return {
        activeName: 'first'
      };
    },
    methods: {
      handleClick(tab, event) {
        console.log(tab, event);
      }
    }
  };
</script>

<style scoped>
body{
	padding-left: 0px;
	padding-right: 10px;
}
.tt{
	padding-left: 50px;
}
.tab{
	height: 486px;
}
.tab1{
	height: 86px;
	width: 1180px;
	border-radius: 10px;
	margin-left: 50px;
	padding-left: 20px;
	padding-right: 20px;
	padding-top: 15px;
	background-color: #d3dce6;
	color: black;
	font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
}
 .el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 150px;
    margin: 0;
  }
  .el-carousel__item:nth-child(4n) {
     background-image: url("images/b0e4bb20-cca1-4ceb-b3ac-e93ee943e85f(1)(1).jpg");
  }
  .el-carousel__item:nth-child(4n+1) {
     background-image:url("images/dedeb137-0ad3-4b40-b882-f2f2d4eb313b(1)(1).jpg");
  }
  .el-carousel__item:nth-child(4n+2) {
     background-image:url("images/e4013092-6d88-413c-92c0-92abfd76a1f4(1)(1).jpg");
  }
  .el-carousel__item:nth-child(4n+3) {
     background-image:url("images/57934e86-3702-4056-84fc-38414e592f1b(1)(1).jpg");
  }

  .block{
	  margin-top: 20px;
  }

</style>
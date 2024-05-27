<template>
<body>
		
	<el-tabs v-model="activeName" @tab-click="handleClick" type="border-card" class="tab">
		<el-tab-pane label="中国知网" name="first">
			<div class="zw">
			<div><h3>中国知网:</h3></div>
			<div class="bg"> <a href="http://www.cnki.net" target="_blank">点击此处前往中国知网</a></div>
			<br /><br />
			
			<div class="intro">
				<h4>中国知网的介绍:</h4>
				<p>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					知网即中国知网，或常被称为“中国期刊网”，是中国最大的学术论文数据库和学术电子资源集成商，收录了 95%以上正式出版的中文学术资源，没有其它数据库能出其右。 中国知网是为响应世界银行提出的“国家知识基础设施”概念而设立，是“中国知识基础设施工程”的组成部分，其根本的目的在于为知识生产和理论创新提供数据资源和工具，具有很强的普惠性和公益性。
				</p>
			</div>
			</div>
			
		</el-tab-pane>
		
		<el-tab-pane label="万方数据" name="second">
			<div class="zw">
			<div><h3>万方数据:</h3></div>
			<div class="bg"><a href="http://www.wanfangdata.com.cn" target="_blank">点击此处前往万方数据</a></div>
			<br /><br />
			<div class="intro">
				<h4>万方数据的介绍:</h4>
				<p>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					万方数据库是由万方数据公司开发的，涵盖期刊、会议纪要、论文、学术成果、学术会议论文的大型网络数据库；也是和中国知网齐名的中国专业的学术数据库。其开发公司——万方数据股份有限公司是国内第一家以信息服务为核心的股份制高新技术企业，是在互联网领域，集信息资源产品、信息增值服务和信息处理方案为一体的综合信息服务商。
				</p>
			</div>
			</div>
		</el-tab-pane>
		
		<el-tab-pane label="维普资讯" name="third">
			<div class="zw">
			<div><h3>维普咨询:</h3></div>
			<div class="bg"><a href="http://www.cqvip.com" target="_blank">点击此处前往维普资讯</a></div>
			<br /><br />
			<div class="intro">
				<h4>维普资讯的介绍:</h4>
				<p>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					维普资讯是科学技术部西南信息中心下属的一家大型的专业化数据公司，是中文期刊数据库建设事业的奠基人，公司全称重庆维普资讯有限公司。已经成为中国最大的综合文献数据库。从1989年开始，一直致力于对海量的报刊数据进行科学严谨的研究、分析，采集、加工等深层次开发和推广应用。自1993年成立以来，公司的业务范围已涉及数据库出版发行、知识网络传播、期刊分销、电子期刊制作发行、网络广告、文献资料数字化工程以及基于电子信息资源的多种个性化服务。
				</p>
			</div>
			</div>
		</el-tab-pane>
		
		<el-tab-pane label="中国学术期刊网络出版总库" name="fourth">
			<div class="zw">
			<div><h3>中国学术期刊网络出版总库:</h3></div>
			<div class="bg"><a href="http://www.nssd.org" target="_blank">点击此处前往中国学术期刊网络出版总库</a></div>
			<br /><br />
			<div class="intro">
				<h4>中国学术期刊网络出版总库的介绍:</h4>
				<p>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					中国学术期刊网络出版总库是由清华大学学术期刊电子杂志社和清华同方知网技术有限公司承担的《中国学术期刊网络出版总库》，是国家“十一五”重大网络出版工程《中国学术文献网络出版总库》的最重要组成部分，其“雏形”是清华大学于1995年创办的《中国学术期刊(光盘版)》和在1999年全文上网的《中国学术期刊全文数据库》。
				</p>
			</div>
			</div>
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
.tab{
	height: 486px;
}
.zw{
	padding-left: 50px;
}
a:hover{
	color: red;
}
.bg{
	height: 40px;
	width: 1200px;
	background-color: #d3dce6;
	border-radius: 11px;
	padding-left: 20px;
	padding-top: 12px;
}
.intro{
	width: 1200px;
	height: 250px;
	background-color: #d3dce6;
	border-radius: 11px;
	padding-left: 20px;
	padding-top: 5px;
	line-height: 30px;
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
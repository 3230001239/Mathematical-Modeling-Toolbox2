import Vue from 'vue';
import XLSX from 'xlsx';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import axios from 'axios';
import * as echarts from 'echarts';  
import 'echarts-gl';  

Vue.config.productionTip = false

Vue.prototype.$echarts = echarts 

Vue.use(ElementUI)

Vue.prototype.$http = axios;
axios.defaults.baseURL = ""

new Vue({
  router,
  store,
  el:'#app',
  render: h => h(App)
}).$mount('#app')

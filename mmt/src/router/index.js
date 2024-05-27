import Vue from 'vue'
import XLSX from 'xlsx'
import VueRouter from 'vue-router'
import login from '../views/login.vue'
import homeview from '../views/homeview.vue'
import reg from '../views/reg.vue'
import literature from '../views/literature.vue'
import match from '../views/match.vue'
import paper from '../views/paper.vue'
import paper_sug from '../views/paper_sug.vue'
import m13 from '../views/m13.vue'
import o1 from '../views/o1.vue'
import m11 from '../views/m11.vue'
import m12 from '../views/m12.vue'
import mainv from '../views/mainv.vue'
import m41 from '../views/m41.vue'
import m51 from '../views/m51.vue'
import search from '../views/search.vue'
import m31 from '../views/m31.vue'
import m71 from '../views/m71.vue'
import m61 from '../views/m61.vue'
import ai from '../views/ai.vue'




Vue.use(VueRouter)



const routes = [
	{
    path: '/',
    name: 'home',
    component: login
	},{
		path:'/homeview',
		component:homeview,
		children:[{
			path:'mainv',
			component:mainv
		},{
			path:'o1',
			component:o1
		},{
			path:'paper',
			component:paper
		},{
			path:'paper_sug',
			component:paper_sug
		},{
			path:'literature',
			component:literature
		},{
			path:'match',
			component:match
		},{
			path:'m11',
			component:m11
		},{
			path:'m12',
			component:m12
		},{
			path:'m41',
			component:m41
		},{
			path:'m51',
			component:m51
		},{
			path:'m13',
			component:m13
		},{
			path:'search',
			component:search
		},{
			path:'m31',
			component:m31
		},{
			path:'m71',
			component:m71
		},{
			path:'m61',
			component:m61
		},{
			path:'ai',
			component:ai
		}]
	},{
		path:'/reg',
		component:reg,
	}
	
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router

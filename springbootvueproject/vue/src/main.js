import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import Element from 'element-ui';
import router from "./router/index";
import 'element-ui/lib/theme-chalk/index.css';
import store from './store';

Vue.use(Element, { size: 'small', zIndex: 3000, });

Vue.config.productionTip = false


new Vue({
  router,
  store,
  beforeCreate() {
    this.$store.dispatch('getMemberInfo')
  },
  render: h => h(App),
}).$mount('#app')

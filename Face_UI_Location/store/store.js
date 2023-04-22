import  Vue  from "vue"
import Vuex from 'vuex'
import checkCount from './check.js'

Vue.use(Vuex)
const store = new Vuex.Store({
  modules: {
      c_count:checkCount
  }
})

export default store
export default {
  // 开启命名空间
  namespaced: true,

  // 数据
  state: () => ({
        todayCheckCount:0
  }),

  // 方法
  mutations: {
    updateCheckCount(state,num){
      console.log("进入了vuex页面");
      state.todayCheckCount=num
      console.log('vuex中的todaycheckCount:'+state.todayCheckCount);
    }

  },

  getters: {
    getTodayCheckCount(state){
      return state.todayCheckCount
    }
  }
}

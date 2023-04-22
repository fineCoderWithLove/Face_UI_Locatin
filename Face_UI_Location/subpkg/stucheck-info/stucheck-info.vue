<template>
  <view>
    我是学的课程签到信息页面，包含了所有的签到。
  </view>
</template>

<script>
  export default {
    data() {
      return {
        cno:0,
        sno:0
      };
    },
    onLoad(options) {
      this.cno = options.cno
      this.sno = options.sno
      this.getStuCheckInfo()
    },
    methods:{
    async getStuCheckInfo(){
      //这一块是没有问题的，但是稍后再进行修改
       const data = await uni.$http.get('/checkinfo/getstucheckinfo?cno=' + this.cno+'&sno='+this.sno)
       console.log(data)
     }
    }
  }
</script>

<style lang="scss">

</style>

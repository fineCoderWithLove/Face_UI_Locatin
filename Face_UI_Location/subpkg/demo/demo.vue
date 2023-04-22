<template>
  <view>
    <button @click="chooseImage()">选择人脸验证图片</button>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        src: '',
        base: '',
        isTrueBase: ''
      };
    },
    onLoad() {
     this.getAllCourseInfo()
    },
    methods: {
     async getAllCourseInfo() {
       console.log("jinru");
       const data = await uni.$http.get('/course/allcourse')
       console.log(data);
       if (data.data.code !== 200) {
         return uni.showToast({
           title: '数据请求失败！',
           duration: 1500,
           icon: 'none',
         })
       } else {
         //请求数据成功，给data数据赋值,已经检测过数据没有问题
         this.allCourseInfo = data.data.data
         console.log(this.allCourseInfo)
       }
     }
    }
  }
</script>

<style lang="scss">

</style>

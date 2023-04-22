<template>
  <view class="body">
    <view style="    background-image: url('https://img.zcool.cn/community/01adb85d4d4515a8012187f4ae7e60.jpg@1280w_1l_2o_100sh.jpg'); height: 10px ;"></view>
    <uni-swipe-action>
      <view class="item-list" v-for="(item,i) in courseList" :key="i">
        <!-- 为每个子节点绑定 -->
        <uni-swipe-action-item :right-options="options" @click="uploadMyCourse(item)">
          <view class="info">
            <view class="item-cno">课程编号:{{item.cno}}</view>
            <view class="item-tname">课程名称:{{item.courseName}}</view>
            <view class="item-location">上课地点:{{item.location}}</view>
            <view class="item-date">上课日期:{{item.courseDate}}</view>
            <view class="words">=></view>
          </view>
        </uni-swipe-action-item>
      </view>
    </uni-swipe-action>
    
    <view class="buttonsubmit" @click="publishcourse()">发布课程</view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        tno: 0,
        courseList: [],
        options: [{
          text: '管理班级', // 显示的文本内容
          style: {
            backgroundColor: '#ffbe93', // 按钮的背景颜色
          }
        }]
      }
    },
    onLoad(options) {
      // 参数赋值没有任何问题,携带了参数的id那就要查出来这个老师所带的所有课程信息，老师通过点击这个课程信息可以查出来所有选课的学生
      this.tno = options.tno
      this.getTeacherCourse()
    },
    methods: {
      publishcourse (){
        console.log("点击发布课程信息");
        uni.navigateTo({
          url:'../publish-course/publish-course?tno='+this.tno
        })
      },
    async  uploadMyCourse (item){
        console.log(item.cno)
        //利用这个一块的课程信息来做跳转从而查出所有学生和学生的签到信息
        //这个请求不应该写在这，应该体现的是，点击按钮后跳转，然后跳转到学生信息的页面中再来实现请求学生列表德数据
         // const data = await uni.$http.get('/teacher/coursestulist?cno=' + item.cno)
         // console.log(data)
        uni.navigateTo({
          url:'../admin-teacher-stulist/admin-teacher-stulist?cno='+item.cno
        })
      },
      async getTeacherCourse() {
        const data = await uni.$http.get('/teacher/teachercourse?tno=' + this.tno)
        console.log(data)
        if (data.data.code != 200) {
          uni.showToast({
            title: '服务器繁忙！',
            duration: 1500,
            icon: 'none',
          })
        } else {
          this.courseList = data.data.data
        }

      }


    }
  }
</script>

<style lang="scss">
  .words {
    position: absolute;
    font-weight: 800;
    top: 97px;
    right: 20px;
  }
  .buttonsubmit {
    width: 95%;
    margin: 0 auto;
    text-align: center;
    background-color: #defffc;
    color: #000000;
    font-family: "楷体";
    height: 80rpx;
    line-height: 80rpx;
    border-radius: 20rpx;
    position: absolute;
    bottom: 10px;
    left: 9px;
  }
  .body {
    background-image: url('https://img.zcool.cn/community/01adb85d4d4515a8012187f4ae7e60.jpg@1280w_1l_2o_100sh.jpg');
    background-size: 100% 100%;
    background-attachment: fixed;
    height: 100vh;
  }
.item-list {
    border-radius: 25px 0px 25px 0px;
    display: flex;
    background-color: #defffc;
    height: 270rpx;
    margin-right: 20px;
    margin-left: 20px;
    margin-bottom: 20px;
    justify-content: space-around;
    flex-wrap: wrap;
    flex-direction: column;
    border: 1px solid #efefef;
    box-shadow: 6px 6px #efefef;
    opacity: 80%;
} 
.info {
  display: flex;
    justify-content: space-around;
    font-family: "楷体";
    flex-wrap: nowrap;
    flex-direction: column;
    text-align: center;
}
.item-cno,
.item-tname,
.item-location,
.item-date
{
  padding-left: 50rpx;
  margin-bottom: 20rpx;
}
</style>

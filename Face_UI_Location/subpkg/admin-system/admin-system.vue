<template>
  <view class="body">
    <view style="height: 10px;"></view>
    <view class="container">
      <view class="stu-item" v-for="(item,i) in studentList" :key="i">
        <!-- 左边盒子区域 -->
        <view class="left-box">
          <image style="width: 100%; height: 100%; border-radius: 100%; border: 2px solid #A5A4A4;" :src="item.src"></image>
        </view>
        <!-- 右边盒子区域 -->
        <view class="right-box">
          <view class="stu-sno">学号:{{item.sno}}</view>
          <view class="stu-name">姓名：{{item.stuName}}</view>
          <view class="stu-gender">性别:{{item.gender}}</view>
          <view class="stu-stuage">年龄:{{item.stuAge}}</view>
                <view class="words">右滑重置密码</view>
        </view>
  
      </view>
    </view>

    <u-tabbar :value="value6" @change="name => value6 = name" :fixed="true" :placeholder="true"
      :safeAreaInsetBottom="true">
      <block v-if="count!==0"> <view class="littlepoint">{{count}}</view></block>
      <u-tabbar-item text="管理学生" icon="home" @click="leftclick()"></u-tabbar-item>
      <u-tabbar-item text="管理老师" icon="account" @click="rightclick()"></u-tabbar-item>
    </u-tabbar>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        studentList: [],
        active: 0,
        value6: 0,
        count:0
      };
    },
    onLoad() {
      this.value6 = 0;
      this.getStudentInfo();
      this.getNoChecKTeacherCount()
    },
    methods: {
      // 查询所有通过审批的老师信息
     async getNoChecKTeacherCount(){
        const data = await uni.$http.get('/teacher/nocheckteachercount')
        this.count = data.data.data
      },
      async getStudentInfo() {
        const data = await uni.$http.post('/student/allstudent')
        if (data.statusCode !== 200) {
          return uni.showToast({
            title: '数据请求失败！',
            duration: 1500,
            icon: 'none',
          })
        }
        this.studentList = data.data.data
      },
      leftclick() {
        console.log("点击到左边了");
      },
      rightclick() {
        console.log("点击到右边了")
        uni.navigateTo({
          url: '../guanli-teacher/guanli-teacher'
        })
      }
    }
  }
</script>

<style lang="scss">
  .words{
    float: right;
    position: absolute;
    right: 30px;
    font-size: 12px;
    font-family: "楷体";
    margin-top: 10px;

  }
    .littlepoint {
      text-align: center;
      height: 40rpx;
      width: 40rpx;
      border-radius: 100%;
      float: left;
      position: absolute;
      z-index: 2;
      background-color: #E43D33;
      right: 120rpx;
      top: -1px;
      color: #efefef;
    }
  .body {
    background-color: #398ADE
  }

  .container {
    height: 100%;
    width: 95%;
    background-color: #efefef;
    margin: 0 auto;
  }

  .stu-item {
    display: flex;
    height: 170px;
    width: 100%;
    margin-bottom: 20px;
    background-color: #f9f9f9;
    border-radius: 10px;
    
  }

  .left-box {
    margin: 10px;
    background-color: #333;
    width: 140px;
    height: 90%;
    border-radius: 100%;
  }
  .right-box {
    margin-top:40px;
    margin-left: 20px;
  }
  .stu-sno ,
  .stu-name,
  .stu-gender,
  .stu-stuage {
    font-size: 20px;
   margin-left: 20px;
   color: #333;
   font-weight: 800;
   font-family: "楷体";
  }
</style>

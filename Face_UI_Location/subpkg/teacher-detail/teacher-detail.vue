<template>
  <view class="body" style="height: 100vh;">
    <!-- 外部包裹容器 -->
    <view style="height: 5px; background-color: #007AFF;"></view>
    <view class="container" style="height: 96%;">
      <view style="height: 10px; background-color: #f9f9f9;"></view>
      <block v-if="courseList.length!==0">
        <uni-swipe-action>
          <view class="item-list" v-for="(item,i) in courseList" :key="i">
            <!-- 为每个子节点绑定 -->
            <uni-swipe-action-item :right-options="options" @click="stuListInfo(item)">
              <view class="info">
                <view class="courseName">课程:{{item.courseName}}</view>
                <view class="location">上课地点:{{item.location}}</view>
                <view class="date">上课时间:{{item.courseDate}}</view>
              </view>
            </uni-swipe-action-item>
          </view>

        </uni-swipe-action>
      </block>
      <block v-else>
        <view class="nocourse">该老师还没有发布课程</view>
      </block>

    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        tno: 0,
        courseList: [],
        options: [{
          text: '选课学生', // 显示的文本内容
          style: {
            backgroundColor: '#ffbe93', // 按钮的背景颜色
          }
        }]
      };
    },
    onLoad(options) {
      this.tno = options.tno
      this.getTeacherDetail()
    },
    methods: {
      stuListInfo(item) {
        uni.navigateTo({
          url: '../course-student/course-student?cno=' + item.cno
        })
      },
      async getTeacherDetail() {
        const data = await uni.$http.get('/admin/teacherdetail?tno=' + this.tno)
        this.courseList = data.data.data
      }
    }
  }
</script>

<style lang="scss">
  .nocourse {
    font-family: "楷体";
    width: 100%;
text-align: center;
    font-size: 60rpx;
  }
  .words {
    float: right;
    position: absolute;
    right: 20px;
    font-size: 13px;
    top: 70px;
  }

  .pop {
    font-family: "楷体";
  }

  .buttons {
    display: flex;
    justify-content: space-around;
  }

  .button {
    width: 80px;
    margin-top: 80px;
    background-color: #18BC37;
    text-align: center;

  }

  * {
    font-family: "楷体";
    margin: 0px;
    padding: 0px;
  }

  .body {
    background-color: #398ADE;
  }

  .container {
    margin: 5px;
    background-color: #efefef;
    border-radius: 5px;
  }

  .info {
    margin-bottom: 20px;
    background-color: #f9f9f9;
    height: 100px;
    font-family: "楷体";
    font-size: 20px;
    border-radius: 20px;
    margin-left: 10px;
    margin-right: 10px;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
  }

  .isregister {
    float: right;
    position: absolute;
    right: 20px;
    top: 40px;
    font-size: 25px;
  }

  .tno,
  .name,
  .age {
    margin-bottom: 12px;
  }
</style>

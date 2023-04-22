<template>
  <view class="body">
    <view style="height: 10px;"></view>
    <view class="container">
      <view style="height: 10px;"></view>
      <uni-swipe-action>
        <view class="item-list" v-for="(item,i) in stuList" :key="i">
          <!-- 为每个子节点绑定 -->
          <!-- 图片把盒子撑大了 -->

          <uni-swipe-action-item :right-options="options" @click="historycheck(item)">
            <!-- 最外部容器 -->
            <view class="info">
              <!-- 左边盒子区域 -->
              <view class="left-box">
                <image :src="item.src"
                  style="width: 100%; height: 100%; border-radius: 100%; border: 2px solid #A5A4A4;"></image>
              </view>
              <!-- 右边盒子区域 -->
              <view class="right-box">
                <view class="tno">学号:{{item.sno}}</view>
                <view class="name">姓名:{{item.stuName}}</view>
                <view class="age">年龄:{{item.stuAge}}</view>
                <view class="age">性别:{{item.gender}}</view>
              </view>
            </view>
          </uni-swipe-action-item>
        </view>


      </uni-swipe-action>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        cno: 0,
        stuList: [],
        options: [{
          text: '历史签到信息', // 显示的文本内容
          style: {
            backgroundColor: '#ffbe93', // 按钮的背景颜色
          }
        }]
      };
    },
    onLoad(options) {
      this.cno = options.cno
      this.getStuList()
    },
    methods: {
      historycheck(item) {
        uni.navigateTo({
          url: '../history-check/history-check?sno=' + item.sno
        })
      },
      async getStuList() {
        const data = await uni.$http.get('/admin/coursestulist?cno=' + this.cno)
        this.stuList = data.data.data
      }
    }
  }
</script>

<style lang="scss">
  .container {
    margin-left: 10px;
    margin-right: 10px;
    background-color: #efefef;
    height: 100%;
    border-radius: 20px;
  }

  .body {
    background-color: #007AFF;
  }

  .item-list {
    margin-left: 5px;
    background-color: #f9f9f9;
    width: 96%;
    height: 96%;
    margin-bottom: 30px;
        border-radius: 20px;
  }

  .info {
    display: flex;
    height: 170px;
  }

  .left-box {
    margin: 10px;
    background-color: #333;
    width: 140px;
    height: 90%;
    border-radius: 100%;
  }

  .right-box {
    margin-top: 40px;
    margin-left: 20px;
    font-family: "楷体";
    font-size: 20px;
  }

  .stu-sno,
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

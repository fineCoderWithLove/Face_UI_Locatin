<template>
  <view class="body" style="height: 100vh;">
    <view style="height: 20px; width: 100%; " ></view>
    <!-- 签到的历史信息页面 -->
    <!-- 外部容器 -->
    <block v-if="historyList.length!==0">
      <view class="stu-item" v-for="(item,i) in historyList" :key="i">
        <!-- 为每个子节点绑定 -->
        <view class="info">
          <view class="stu-sno">日期:{{item.checkdate}}</view>
          <view class="stu-name">签到地点:{{item.checklocation}}</view>
          <view class="stu-gender">{{item.course}}</view>
          <view class="stu-stuage">{{item.teaname}}</view>
          <block v-if="item.ischeck==1&&item.checkinfo==2">
            <view class="checkinfo" style="color: #18BC37;">成功签到</view>
          </block>
          <block v-if="item.ischeck==1&&item.checkinfo!==2">
            <view class="checkinfo" style="color: #F9AE3D;">签到过期</view>
          </block>
          <block v-if="item.ischeck==0&&item.checkinfo==2">
            <view class="checkinfo" style="color: #E43D33;">未签到</view>
          </block>
          
        </view>
      </view>
    </block>
    
    <block v-else>
      <view class="infowords">暂时还没有签到信息</view>
    </block>


  </view>
</template>

<script>
  export default {
    data() {
      return {
        sno: 0,
        historyList: []
      };
    },
    onLoad(options) {
      this.sno = options.sno
      this.getHistoryInfo()
    },
    methods: {
      async getHistoryInfo() {
        const data = await uni.$http.get('/admin/checkinfo?sno=' + this.sno)
        this.historyList = data.data.data
      }
    }
  }
</script>

<style lang="scss">
  .checkinfo {
    font-family: "楷体";
    font-weight: 800;
    font-size: 80rpx;
    position: absolute;
    float: right;
    right: 20rpx;
  }
  .infowords {
    width: 100%;
    text-align: center;
    font-family: "楷体";
    font-size: 80rpx;
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
    margin-top: 40px;
    margin-left: 20px;
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

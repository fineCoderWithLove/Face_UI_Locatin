<template>
  <view>

    <view v-if="myCourseInfo.length !== 0">

      <view class="body">
        <scroll-view scroll-y="true">
          <uni-swipe-action>
            <view class="item-list" v-for="(item,i) in myCourseInfo" :key="i">
              <!-- 为每个子节点绑定 -->
              <uni-swipe-action-item :right-options="options" @click="showCheck(item)">
                <view class="info">
                  <view class="item-name">{{item.courseName}}</view>
                  <view class="item-tname">{{item.teaName}}</view>
                  <view class="item-location">{{item.location}}</view>
                  <view class="item-course">{{item.courseDate}}</view>
                </view>
              </uni-swipe-action-item>
            </view>
          </uni-swipe-action>
        </scroll-view>
        <view class="buttons">
          <view class="firstbutton">
            <u-button type="primary" style="border: 1px solid #38ded1; color: #38ded1;" size="medium" plain="true"
              ripple="true" @click="chooseCourse()">进行选课</u-button>
          </view>

          <view class="secondbutton">
            <block v-if="count!==0">
              <view class="littlepoint">{{count}}</view>
            </block>
            <u-button type="primary" size="medium" plain="true" ripple="true" @click="todayCheckInfo()">今日签到信息
            </u-button>
          </view>

          <view class="thirdbutton">
            <u-button type="primary" size="medium" plain="true" ripple="true" @click="turnMySelfInfo()">个人信息页面
            </u-button>
          </view>
        </view>
      </view>
    </view>
    <view v-else style="background-color: #efefef; height: 100vh;">
      <view class="warning-info">
        <image src="https://tse3-mm.cn.bing.net/th/id/OIP-C.5t_4pWZqPOOJkeAFGpTz9gEsEs?pid=ImgDet&rs=1"></image>
        <view class="info-nocourse"></view>
      </view>
      <view class="buttonchoose" @click="chooseCourse()">进行选课</view>

    </view>


  </view>

</template>

<script>
  import {
    mapGetters
  } from 'vuex'
  export default {
    data() {
      return {
        myCourseInfo: [],
        mysno: 0,
        noCourseImage: '',
        options: [{
          text: '签到信息', // 显示的文本内容
          style: {
            backgroundColor: '#ffbe93', // 按钮的背景颜色
          }
        }],
        cno: 0,
        count: 0
      };
    },
    computed: {
      // 把 m_cart 模块中名称为 total 的 getter 映射到当前页面中使用
      // ...mapGetters('c_count', ['getTodayCheckCount']),
      // checkCount() {
      //   return this.getTodayCheckCount
      // }
    },
    onLoad(options) {
      console.log(options)
      this.mysno = options.mysno
      console.log(this.mysno)
      // this.getLocalInfo()
      this.getMyCourseInfo()
      this.getPersonInfo()
      // 请求没有签到的信息;
      this.getCountNoCheck()
    },
    methods: {
      async getCountNoCheck() {
        const data = await uni.$http.get('/checkinfo/countofnocheck?sno=' + this.mysno)
        this.count = data.data.data
      },
      async getPersonInfo() {
        const data = await uni.$http.get('/student/studetails?sno=' + this.mysno)
        if (data.data.data.src === '') {
          uni.showToast({
            title: '您还没有上传图像信息，请在个人中心上传！',
            duration: 1500,
            icon: 'none',
          })
        }
      },
      turnMySelfInfo() {
        uni.navigateTo({
          url: '../../subpkg/personal-info/personal-info?sno=' + this.mysno
        })
      },
      todayCheckInfo() {
        //点击查看今日签到信息,参数只要携带学号查询该学生所有的当天签到信息
        uni.navigateTo({
          url: '../../subpkg/today-checkinfo/today-checkinfo?sno=' + this.mysno
        })
      },
      showCheck(item) {
        console.log(item)
        this.cno = item.cno;
        uni.navigateTo({
          url: '../../subpkg/stucheck-info/stucheck-info?cno=' + this.cno + '&sno=' + this.mysno
        })
      },
      async getMyCourseInfo() {
        console.log("获取数据时候的:" + this.mysno)
        const data = await uni.$http.get('/student/courseinfo?sno=' + this.mysno)
        console.log(data)
        if (data.statusCode !== 200) {
          return uni.showToast({
            title: '数据请求失败！',
            duration: 1500,
            icon: 'none',
          })
        }
        this.myCourseInfo = data.data.data
      },
      chooseCourse() {
        uni.navigateTo({
          url: '../choose-course/choose-course?mysno=' + this.mysno
        })
      }
    }

  }
</script>

<style lang="scss">
  .buttonchoose {
    height: 100rpx;
    background-color: #007AFF;
    text-align: center;
    font-size: 30px;
    line-height: 50px;
    font-family: "楷体";
    border-radius: 20px;
    position: absolute;
    width: 100%;
    bottom: 0px;
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
    right: 250rpx;
    top: -8px;
    color: #efefef;
  }

  .buttons {
    flex-direction: row;
    display: flex;
    position: fixed;
    bottom: 5px;
    justify-content: space-between;
    font-size: 16px;
        font-family: "楷体";
  }

  .firstbutton,
  .secondbutton,
  .thirdbutton {
    border: 1rpx solid #38ded1;
    color: #38ded1;
    width: 206rpx;
    margin-right: 30rpx;

  }

  .firstbutton {
    margin-left: 40rpx;
  }

  .warning-info {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    flex-wrap: wrap;
    position: absolute;
    left: 30px;

  }

  .info-nocourse {
    display: flex;
    color: #ffbe93;
  }

  * {
    margin: 0px;
    padding: 0px;
  }

  .body {
    background-color: #f9f9f9;
    background-attachment: fixed;
    width: 100%;
    height: 100vh;
    height: 100%;
    margin-bottom: 100rpx;
    font-size: 18px;
        font-family: "楷体";
  }

  .info {
    border-radius: 15px;
    display: flex;
    background-color: #defffc;
    height: 230rpx;
    margin: 20px;
    justify-content: space-around;
    flex-wrap: wrap;
    flex-direction: column;
    border: 1px solid #efefef;
    box-shadow: 6px 6px  1px 8px#efefef;
    border: 1px solid #333;
  }

  .item-name {
        font-weight: 800;
    padding-top: 20rpx;
    padding-left: 20rpx;
    width: 400px;
  }

  .item-tname {
    padding-left: 550rpx;
    color: #615f4a;
    font-weight: 800;
  }

  .item-location {
    margin-bottom: 10rpx;
    padding-left: 20rpx;
  }

  .item-course {
    padding-left: 20rpx;
  }
</style>

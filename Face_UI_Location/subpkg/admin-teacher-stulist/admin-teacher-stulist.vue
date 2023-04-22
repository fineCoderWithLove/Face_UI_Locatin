<template>

  <view class="body" style="height: 100vh; ">
    <!-- 容器区域 -->
    <view class="container">
      <uni-swipe-action>
        <view class="item-list" v-for="(item,i) in stuList" :key="i">
          <!-- 为每个子节点绑定 -->
          <uni-swipe-action-item :right-options="options" @click="uploadMyCourse(item)">
            <view class="info">
              <view class="item-sname">{{item.stuName}}</view>
              <view class="item-sno">学号:{{item.sno}}</view>
              <view class="bottom-info">
                <view class="item-sgender">性别:{{item.gender}}</view>
                <view class="item-sage">年龄:{{item.stuAge}}</view>
                <view class="words">右滑动查看详情</view>
              </view>
            </view>
          </uni-swipe-action-item>
        </view>
      </uni-swipe-action>
    </view>
    <!-- 底部按钮区域 -->
    <view class="buttons">
      <view class="firstbutton">
        <button @click="turnToCheckin()" type="default" plain="true" style="color: #000000;">发起签到</button>
        <!-- 点击发起签到，应该跳转到签到页面 -->
      </view>

      <view class="secondbutton">
        <button type="default" plain="true" @click="turnTodayInfo()" style="color: #000000;">签到信息</button>
        <!-- 点击查看签到信息，应该跳转到签到信息页面 -->
      </view>
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
          text: '学生详情', // 显示的文本内容
          style: {
            backgroundColor: '#ffbe93', // 按钮的背景颜色
          }
        }]
      };
    },
    onLoad(options) {
      console.log(options)
      this.cno = options.cno
      this.getStuList()

    },
    methods: {
      //点击签到按钮，进行跳转
      turnTodayInfo() {
        console.log("faqila");
        uni.navigateTo({
          url: '../tsee-checkinfo/tsee-checkinfo?cno=' + this.cno
        })
      },
      turnToCheckin() {
        uni.navigateTo({
          url: '../publish-checkin/publish-checkin?cno=' + this.cno
        })
      },
      getlocation() {
        console.log("点击到了")
        wx.getFuzzyLocation({
          type: 'wgs84',
          success(res) {
            console.log(res.latitude)
            console.log(res.longitude)
          },
          fail() {
            console.log("接口调用失败")
          }
        })
      },
      uploadMyCourse(item) {
        console.log(item.sno)
        uni.navigateTo({
          // 导航需要携带两个参数，因为查询签到信息的时候是要知道学生的学号和课程号才能知道学生的签到信息
          url: '../student-info/student-info?sno=' + item.sno + '&cno=' + this.cno
        })
      },
      async getStuList() {
        const data = await uni.$http.get('/teacher/coursestulist?cno=' + this.cno)
        console.log(data)
        this.stuList = data.data.data
      },


    }
  }
</script>

<style lang="scss">
  * {
    margin: 0px;
    padding: 0px;
  }

  .words {
    position: absolute;
    right: 32rpx;
    font-size: 26rpx;
    top: 140rpx;
  }

  .container {
    margin-top: 20rpx;
    background-image: url('https://ts1.cn.mm.bing.net/th/id/R-C.78566756beabf175650829edb3501e31?rik=i57%2fID6Fhr%2bnoQ&riu=http%3a%2f%2fimg95.699pic.com%2fdesgin_photo%2f40052%2f7247_detail.jpg!detail860%2ffw%2f820%2fcrop%2f0x0a0a1995%2fquality%2f90&ehk=R6k0KCf%2fHY2S5kO3nQaFCUVooo4De7j%2bBd9JY1NqeSo%3d&risl=&pid=ImgRaw&r=0');
    background-size: 100% 100%;
    background-color: #007AFF;
    background-attachment: fixed;
  }

  .buttons {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    background-image: url('https://ts1.cn.mm.bing.net/th/id/R-C.78566756beabf175650829edb3501e31?rik=i57%2fID6Fhr%2bnoQ&riu=http%3a%2f%2fimg95.699pic.com%2fdesgin_photo%2f40052%2f7247_detail.jpg!detail860%2ffw%2f820%2fcrop%2f0x0a0a1995%2fquality%2f90&ehk=R6k0KCf%2fHY2S5kO3nQaFCUVooo4De7j%2bBd9JY1NqeSo%3d&risl=&pid=ImgRaw&r=0');
    background-attachment: fixed;
  }

  .firstbutton,
  .secondbutton {
    width: 40%;
    position: fixed;
    bottom: 5px;
    border: 1px solid #d5b892;
    color: #000000;
    margin-left: 2.5%;
  }

  .firstbutton {
    padding-left: 400rpx;
  }

  .item-list {
    border-radius: 10px;
    display: flex;
    background-color: #d5b892;
    height: 230rpx;
    margin-right: 30px;
    margin-left: 30px;
    margin-bottom: 20px;
    justify-content: space-around;
    flex-wrap: wrap;
    flex-direction: column;
    border: 1px solid #A18C75;
    box-shadow: 1px 1px 2px 4px #a19488;
    opacity: 90%;
  }

  .item-sname {
    padding-left: 30rpx;
    font-size: 35px;
    font-weight: 800;
  }

  .info {
    font-family: "楷体";
  }

  .item-sno {
    display: inline-block;
    float: right;
    color: #efefef;
    padding-right: 20rpx;
    font-size: 16px;
    font-weight: 800;
  }

  .bottom-info {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }

  .item-sgender,
  .item-sage {
    display: flex;
    padding-left: 30rpx;
    font-size: 15px;
    margin-bottom: 15rpx;
  }

  button {}
</style>

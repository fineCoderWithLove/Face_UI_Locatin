<template>

  <view class="body">
    <block v-if="checkInfoList.length!==0">
      
      <view class="bodycard">
        <view style="height: 20rpx; background-color: 	#1989fa;"></view>
        <view v-for="(item,i) in checkInfoList" :key="i">
          <view class="checkinfocard">
            <view class="name">姓名:{{item.stuName}}</view>
            <block v-if="ischeckin===true">
              <block v-if="item.ischeck===1&&item.checkinfo===2">
                <view class="ischeckstring">{{item.ischeckstring}}</view>
              </block>
              <block v-else>
                <view class="ischeckstring" style="width: 60%; padding-left: 62%; font-size: 30px; color: #F9AE3D;">
                  {{item.checkinfostring}}
                </view>
              </block>
            </block>
            <block v-else>
              <block v-if="item.checkinfo===2&&item.ischeck===0">
                <view class="ischeckstring" style="color: #E43D33;">{{item.ischeckstring}}</view>
              </block>
            </block>
            <view class="sno">{{item.sno}}</view>
          </view>
        </view>
      </view>
      
    </block>
    <block v-else>
      <view style="height: 100vh;">
        <view class="boxcontainer">
          <view class="img">
            <image src="http://rsc7h9qf2.hn-bkt.clouddn.com/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20230411144847.png">
            </image>
          </view>

        </view>
      </view>
    </block>
    <u-tabbar :value="value6" @change="name => value6 = name" :fixed="true" :placeholder="true"
      :safeAreaInsetBottom="true">
      <u-tabbar-item text="已签同学" icon="checkmark-circle" @click="checkin()"></u-tabbar-item>
      <u-tabbar-item text="未签同学" icon="close-circle" @click="nocheckin()"></u-tabbar-item>
    </u-tabbar>
  </view>
</template>
<script>
  export default {
    data() {
      return {
        cno: 0,
        checkInfoList: [],
        value6: 0,
        ischeckin: true
      };
    },
    onLoad(options) {
      this.cno = options.cno
      this.checkin()
    },
    methods: {
      click1(e) {
        console.log('click1', e);
      },
      async checkin() {
        this.ischeckin = true
        const data = await uni.$http.get('/checkinfo/getischeckinfo?cno=' + this.cno)
        console.log("点击到了已经签到同学");
        console.log(data.data.data)
        this.checkInfoList = data.data.data
      },
      async nocheckin() {
        this.ischeckin = false
        const data = await uni.$http.get('/checkinfo/getnocheckstu?cno=' + this.cno)
        console.log(data.data.data)
        console.log("点击未签到到同学");
        this.checkInfoList = data.data.data
      }
    }
  }
</script>

<style lang="scss">
  .img {
    margin: 0 auto;
    margin-left: 25px;
  }
  .boxcontainer {
    width: 100%;
    height: 100vh;
    background-color: #efefef;
  }

  .ischeckstring {
    color: #18BC37;
    padding-left: 75%;
    font-size: 70rpx;

  }

  .body {
    background-color: #1989fa;
    height: 100%;
    background-size: 100% 100%;
    background-attachment: fixed;
    width: 100%;
  }

  .bodycard {
    background-attachment: fixed;
    margin: 0 20rpx;
    background-color: #efefef;
    height: 100%;
  }

  .checkinfocard {
    height: 300rpx;
    background-color: #cdebf9;
    margin: 20rpx;
    border-radius: 20px;
    font-family: "楷体";
    // box-shadow: ;
  }

  .name {
    padding: 30rpx 0rpx 0rpx 50rpx;
    height: 100rpx;
    font-size: 30px;

  }

  .sno {
    font-size: 14px;
    padding: 10px 0px 0px 77%;
    color: #bfbfbf;
  }
</style>

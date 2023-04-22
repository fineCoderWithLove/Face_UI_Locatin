<template>
  <view class="body">
    <!--    todaycheckinfo
    根据课程的签到状态来决定是否能签上到，也就是ischeck，如果ischeck为0的话就可以滑动来进行签到。 -->
    <scroll-view scroll-x="true">
      <view style="height: 30rpx;"></view>
      <view class="todayCheckInfoList" v-for="(item,i) in todayCheckInfoList" :key="i">
        <view class="course">{{item.course}}</view>
        <!-- 判断ischeck的值来判断签到的颜色 -->
        <block v-if="item.ischeck===1">
          <!-- 如果签上到了还要判断签到是否过期 -->
          <block v-if="item.checkinfo===0">
            <view class="teaname">{{item.teaname}}</view>
            <view class="ischeckstring" style="color: #F9AE3D; font-size: 30px; width: 40%; padding-left: 450rpx;">
              {{item.checkinfostring}}
            </view>
          </block>

          <block v-else>
            <view class="teaname">{{item.teaname}}</view>
            <view class="ischeckstring" style="color: #18BC37;">{{item.ischeckstring}}</view>
          </block>

        </block>
        <block v-else>
          <view class="teaname">{{item.teaname}}</view>
          <view class="ischeckstring" style="color: #E43D33;" @click="checkthiscourse(item)">{{item.ischeckstring}}
          </view>
        </block>
        <!--        <view class="checkinfostring">{{item.checkinfostring}}</view> -->
        <view class="coursebegin">{{item.coursebegin}}</view>
        <view class="checklocation">{{item.checklocation}}</view>
      </view>
    </scroll-view>
  </view>
</template>

<script>
  import {
    mapState
  } from 'vuex'
  import {
    mapMutations
  } from 'vuex'

  export default {
    data() {
      return {
        sno: 0,
        cno: 0,
        todayCheckInfoList: [],
        jingdu: 0,
        weidu: 0
      };
    },
    onLoad(options) {
      this.sno = options.sno
      this.getTodaycheckInfo()
      this.cno = options.cno
    },
    methods: {
      // ...mapMutations('c_count', ['updateCheckCount']),

      // getCheckCount() {
      //   console.log("调用了此函数");
      //   console.log("数组的长度为"+this.todayCheckInfoList.length);
      //   this.updateCheckCount(this.todayCheckInfoList.length)
      // },
      checkthiscourse(item) {
        // 应该先调用摄像头，r人脸识别成功的时候再获取位置信息
        let that = this
        uni.chooseMedia({
          count: 1,
          mediaType: 'image',
          sourceType: 'camera',
          maxDuration: 30,
          camera: 'back',
          success(res) {
            console.log("是否获取图片成功");
            that.src = res.tempFiles[0].tempFilePath
            const fs = wx.getFileSystemManager()
            // 成功之后将数据的baseurl进行上传
            fs.readFile({
              filePath: that.src,
              encoding: 'base64',
              position: 0,
              success(res) {
                console.log(res.data)
                that.base = res.data
                uni.request({
                  url: 'http://43.143.44.118:8089/face/vef',
                  method: 'POST',
                  data: {
                    imageBase: that.base
                  },
                  success(res) {
                    console.log("把图片上传之后进行比较")
                    //应该再上传图片成功之后再进行地点和日期的判断
                    console.log(res);
                    if(res.data.code==200){
                      wx.getFuzzyLocation({
                        type: 'wgs84',
                        success(res) {
                          //获取经纬度信息
                          let those = that
                          console.log(res.latitude)
                          console.log(res.longitude)
                          that.jingdu = res.longitude
                          that.weidu = res.latitude
                          uni.request({
                            url: 'http://43.143.44.118:8888/checkinfo/submitcheckinfo',
                            method: 'POST',
                            data: {
                              checkid: item.checkid,
                              jingdu: that.jingdu,
                              weidu: that.weidu,
                              sno: that.sno,
                              cno: item.cno
                            },
                            success(res) {
                              console.log("成功后的回调函数")
                              that.todayCheckInfoList = res.data.data
                              console.log(that.todayCheckInfoList);
                              for (let i = 0; i < that.todayCheckInfoList.length; i++) {
                                if (res.data.data[i].checkid === item.checkid) {
                                  //说明点击到了同一个签到信息
                                  console.log(item.checkid);
                                  console.log("通过了判断")
                                  console.log(that.todayCheckInfoList[i].checkinfo);
                                  if (res.data.data[i].checkinfo === 0 && res.data.data[i].ischeck ===
                                    1) {
                                    uni.showToast({
                                      title: '签到过期了下次来咯!',
                                      duration: 1500,
                                      icon: 'none',
                                    })
                                  }
                                  if (res.data.data[i].ischeck === 1 && res.data.data[i].checkinfo ===
                                    2) {
                                    uni.showToast({
                                      title: '签到成功咯>_<!',
                                      duration: 1500,
                                      icon: 'none',
                                    })
                                  }else {
                                    uni.showToast({
                                      title: '签到距离不够啊!',
                                      duration: 1500,
                                      icon: 'none',
                                    })
                                  }
                                }
                              }
                              setTimeout(() => {
                                that.getTodaycheckInfo()
                              }, 1500)
                            }
                          })
                        },
                        fail() {
                          console.log("接口调用失败")
                        }
                      })
                    }else{
                      uni.showToast({
                        title: '人脸识别失败!',
                        duration: 1500,
                        icon: 'none',
                      })
                    }
                  }
                })
              },
              fail(res) {
                console.error(res)
              }
            })
          },fail(){
            uni.showToast({
              title: '人脸识别失败!',
              duration: 1500,
              icon: 'none',
            })
          }
        })
        //发送请求到后端对比，请求的参数是学号，课程号，date(这个参数在后端获取)，通过这三个参数就可以唯一确定老师的签到信息和学生的签到信息。
        console.log("签到成功！并且获取课程编号" + item.cno + "获取老师发起签到的唯一主键" + item.checkid)
        //通过唯一主键可以确定需要的老师发送的签到表，然后在后端进行比对
        //向后端发送请求请求参数应该有经纬度，学号和点击的tcno唯一主键信息(还是拿post请求交把)
      },
      async getTodaycheckInfo() {
        let that = this
        console.log("执行了getTodaycheckInfo方法")
        const data = await uni.$http.get('/checkinfo/getstucheckinfo?sno=' + this.sno)
        console.log(data)
        this.todayCheckInfoList = data.data.data

      }
    }
  }
</script>

<style lang="scss">
  .body {
    background-color: #007AFF;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    background-size: 100% 100%;
    background-attachment: fixed;
    width: 100%;
    height: 100%;
    height: 100vh;
  }

  .todayCheckInfoList {
    border-radius: 30px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    margin: 0rpx 15rpx 10rpx 15rpx;
    background-color: #efefef;
    height: 370rpx;
    border: 2px solid #BEF5C8;
  }

  .course,
  .teaname,
  .ischeckstring,
  .checkinfostring,
  .coursebegin,
  .checklocation {
    padding: 0px 30rpx 0px 30rpx;
    display: flex;
  }

  .course {
    font-size: 22px;
    margin-top: 20rpx;
  }

  .teaname {
    margin-top: 2rpx;
  }

  .ischeckstring {
    font-size: 30px;
    padding-left: 550rpx;
  }

  .coursebegin,
  .checklocation {
    padding-left: 545rpx;
  }
</style>

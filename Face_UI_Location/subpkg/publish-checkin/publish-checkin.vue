<template>
  <view class="body">
    <view class="publish">
      <form @submit="formSubmit" @reset="formReset">
        <view class="uni-form-item uni-column">
          <!-- 签到的开始时间 -->
          <view class="course">{{coursename}}签到</view>
          <view class="begin-time">
            <view class="text-begin">签到开始时间</view>
            <view class="uni-list">
              <view class="uni-list-cell">
                <view class="choose">
                  当前选择
                </view>
                <view class="uni-list-cell-db">
                  <picker name="begintime" mode="time" :value="starttime" @change="bindTimeChangebegin">
                    <view class="begintime">{{starttime}}</view>
                  </picker>
                </view>
              </view>
            </view>
          </view>
          <!-- 签到结束的时间 -->
          <view class="over-time">
            <view class="text-end">签到结束时间</view>
            <view class="uni-list">
              <view class="uni-list-cell">
                <view class="choose">
                  当前选择
                </view>
                <view class="uni-list-cell-db">
                  <picker name="endtime" mode="time" :value="endtime" @change="bindTimeChangeend">
                    <view class="endtime">{{endtime}}</view>
                  </picker>
                </view>
              </view>
            </view>
          </view>
          <view class="maxloc">允许签到的最大距离</view>
          <slider value="50" name="allowmaxloc" show-value max="150" min="50"></slider>
        </view>
        <!-- 点击发布签到的时候要获取老师的经纬度 -->
        <view class="button-loc">
          <button type="primary" form-type="reset" size="mini" class="button2">重置签到</button>
          <!-- 老师发布签到信息的时候应该给所有属于她的课程的学生插入一张签到的信息表，这样后面的学生签到的时候就只需要做修改的操作 -->
          <button form-type="submit" type="primary" size="mini" class="button1">发布签到</button>
        </view>
      </form>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        show: false,
        value1: Number(new Date()),
        starttime: '07:55',
        endtime: '08:30',
        cno: 0,
        coursename: null,
        jingdu: 0,
        weidu: 0
      }
    },
    onLoad(options) {
      this.cno = options.cno
      this.getCourseName()
    },
    methods: {
      async getCourseName() {
        //获取签到的课程信息
        const data = await uni.$http.get('/checkinfo/getcoursename?cno=' + this.cno)
        console.log(data)
        this.coursename = data.data.data
      },
      bindPickerChange: function(e) {
        console.log('picker发送选择改变，携带值为', e.detail.value)
        this.index = e.detail.value
      },
      bindDateChange: function(e) {
        this.date = e.detail.value
      },
      bindTimeChangebegin: function(e) {
        console.log(e.detail.value)
        this.starttime = e.detail.value
      },
      bindTimeChangeend: function(e) {
        console.log(e.detail.value)
        this.endtime = e.detail.value
      },
      getDate(type) {
        const date = new Date();
        let year = date.getFullYear();
        let month = date.getMonth() + 1;
        let day = date.getDate();

        if (type === 'start') {
          year = year - 60;
        } else if (type === 'end') {
          year = year + 2;
        }
        month = month > 9 ? month : '0' + month;
        day = day > 9 ? day : '0' + day;
        return `${year}-${month}-${day}`;
      },
      changeshow() {
        this.show = true
      },
      formSubmit: function(e) {
        let that = this
        wx.getFuzzyLocation({
            type: 'wgs84',
            success(res) {
              console.log(res.latitude)
              console.log(res.longitude)
              that.jingdu = res.latitude
              that.weidu = res.longitude
              uni.request({
                url: 'http://43.143.44.118:8888/checkinfo/publishcheck',
                method: 'POST',
                data: {
                  begintime: e.detail.value.begintime,
                  endtime: e.detail.value.endtime,
                  allowmaxloc: e.detail.value.allowmaxloc,
                  cno: that.cno,
                  jingdu: that.jingdu,
                  weidu: that.weidu
                },
                success(res) {
                  uni.showToast({
                    title: '发布签到成功！！',
                    duration: 1500,
                    icon: 'none',
                  })
                }
              })
            },
            fail() {
              console.log("接口调用失败")
            }
          })
      },
      formReset: function(e) {
        console.log('清空数据')
      }
    }
  }
</script>

<style lang="scss">
  .course {
    font-size: 50rpx;
    text-align: center;
    // height: 400rpx;
    // line-height: 400rpx;
    height: 200rpx;
    width: 100%;
    background-color: #efefef;
  }

  .body {
    background-color: #398ADE;
    height: 100vh;
  }

  .publish {
    height: 98vh;
    margin: 0rpx 21rpx 21rpx 21rpx;
    background-color: #efefef;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }

  .text-begin,
  .text-end,
  .choose,
  .begintime,
  .endtime,
  .maxloc {
    display: flex;
    margin-bottom: 30rpx;
    justify-content: space-around;
  }

  .begintime,
  .endtime {
    height: 100rpx;
    background-color: #e7e7e7;
    line-height: 100rpx;
  }

  .text-begin {
    color: #398ADE;
  }

  .text-end {
    color: #398ADE;
  }

  .maxloc {
    color: #398ADE;
  }

  .choose {
    text-align: center;
    color: #398ADE;
  }

  .button-loc {
    display: flex;
  }

  .button1 {
    padding-top: 50rpx;
  }
</style>

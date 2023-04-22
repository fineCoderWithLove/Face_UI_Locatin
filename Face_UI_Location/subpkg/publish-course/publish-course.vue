<template>
  <view class="body">
    <view class="publish">
      <form @submit="formSubmit" @reset="formReset">
        <view class="uni-form-item uni-column">
          <!-- 签到的开始时间 -->
          <view class="course">发布新课程</view>
          <view class="begin-time">
            <view class="uni-list">
              <view class="uni-list-cell">
                <view class="choose">
                  课程名称
                </view>
                <view class="uni-list-cell-db">
                  <view class="containerbox"> <input class="uni-input" name="courseName" placeholder="请输入课程名称" /></view>

                </view>
              </view>
            </view>
          </view>
          <!-- 签到结束的时间 -->
          <view class="over-time">
            <view class="uni-list">
              <view class="uni-list-cell">
                <view class="choose">
                  上课地点
                </view>
                <view class="uni-list-cell-db">
                  <view class="containerbox"> <input class="uni-input" name="location" placeholder="请输入上课地点" /></view>

                </view>
              </view>
            </view>
          </view>
          <view class="choose">上课时间</view>
          <view class="containerbox"> <input class="uni-input" name="time" placeholder="请输入上课时间" /></view>

        </view>
        <!-- 点击发布签到的时候要获取老师的经纬度 -->
        <view class="button-loc">
          <button type="primary" form-type="reset" size="mini" class="button2">重置课程</button>
          <!-- 老师发布签到信息的时候应该给所有属于她的课程的学生插入一张签到的信息表，这样后面的学生签到的时候就只需要做修改的操作 -->
          <button form-type="submit" type="primary" size="mini" class="button1" >发布课程</button>
        </view>
      </form>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        tno:0
      };
    },
    onLoad(options) {
      this.tno = options.tno
      console.log(this.tno);
    },
    methods: {
      formSubmit: function(e) {
        let that = this
        console.log('form发生了submit事件，携带数据为：' + JSON.stringify(e.detail.value))
        var formdata = e.detail.value
        console.log(e);
        console.log("数据格式为"+e.detail.value.courseName);
        console.log(formdata);
        if(e.detail.value.courseName!==''&&e.detail.value.location!==''&&e.detail.value.time!==''){
          uni.request({
            url: 'http://43.143.44.118:8888/course/submitcourse',
            method: 'POST',
            data: {
              courseName: e.detail.value.courseName,
              location: e.detail.value.location,
              courseDate: e.detail.value.time,
              tno:this.tno
            },
            success(res) {
              uni.showToast({
                title: '课程发布成功',
                duration: 1500,
                icon: 'none',
              })
              setTimeout(function() {
                uni.navigateTo({
                  url:'../admin-teacher/admin-teacher?tno='+that.tno
                })
              }, 1500);
            }
          })
        }else{
          uni.showModal({
            content: "表单中任意一项不能为空!",
            showCancel: false
          });
        }
      },
      formReset: function(e) {
        console.log('清空数据')
      },

    }
  }
</script>

<style lang="scss">
  .containerbox {
    width: 100%;
    margin: 0 auto;
    background-color: #f9f9f9;
    height: 40px;
    line-height: 40px;
    font-size: 20px;
  }
  .course {
    font-size: 50rpx;
    text-align: center;
    // height: 400rpx;
    // line-height: 400rpx;
    height: 200rpx;
    width: 100%;
    background-color: #efefef;
    margin-bottom: 100rpx;
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
    font-size: 25px;
  }

  .button-loc {
    margin-top: 100rpx;
    display: flex;
  }

  .button1 {
    padding-top: 50rpx;
  }
</style>

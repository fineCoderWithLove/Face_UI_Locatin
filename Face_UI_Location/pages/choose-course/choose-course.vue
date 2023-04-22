<template>
  <view style="        background: url('https://img.zmtc.com/2019/0815/20190815095829163.jpeg');     
  background-size: 100% 100%;
    background-attachment: fixed;
    width: 100%;
    height: 100vh;
    height: 100%;">
    <scroll-view scroll-y="true">
      <uni-swipe-action>
        <view class="item-list" v-for="(item,i) in allCourseInfo" :key="i">
          <!-- 为每个子节点绑定 -->
          <uni-swipe-action-item :right-options="options" @click="uploadMyCourse(item)">
            <view class="info">
              <view class="item-name">{{item.courseName}}</view>
              <view class="item-tname">{{item.teaName}}</view>
              <view class="item-location">{{item.location}}</view>
              <view class="item-course">{{item.courseDate}}</view>
              <view class="choose">右滑进行选课</view>
            </view>
          </uni-swipe-action-item>
        </view>
      </uni-swipe-action>
    </scroll-view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        mysno: 0,
        allCourseInfo: [],
        options: [{
          text: '添加', // 显示的文本内容
          style: {
            backgroundColor: '#ffbe93', // 按钮的背景颜色
          }
        }]
      }
    },
    onLoad(options) {
      // console.log(options),将数据存储到当前页面,也就是学生的唯一标识，后面通过此唯一标识来添加课程
      this.mysno = options.mysno
      console.log(options)
      console.log("mysno的值")
      console.log(this.mysno)
      this.getAllCourseInfo()
    },
    methods: {
      // 点击按钮进行添加
      uploadMyCourse(item) {
        uni.request({
          url: 'http://43.143.44.118:8888/student/selectstucourse',
          method: 'POST',
          data: {
            cno: item.cno,
            sno: this.mysno
          },
          //成功请求数据后的操作
          success(res) {
            console.log("成功后的回调函数")
            console.log(res)
            uni.showToast({
              title: '选课成功！',
              duration: 1500,
              icon: 'none',
            })
            //其实已经添加进去了，只不过导航不出来，因为函数的回调太多了
            uni.navigateTo({
              url: '../person-info/person-info?mysno=' + res.data.data.sno
            })
          },

        })
      },
      async getAllCourseInfo() {
        const data = await uni.$http.get('/course/allcourse')
        if (data.data.code !== 200) {
          return uni.showToast({
            title: '数据请求失败！',
            duration: 1500,
            icon: 'none',
          })
        } else {
          //请求数据成功，给data数据赋值,已经检测过数据没有问题
          this.allCourseInfo = data.data.data
          console.log(this.allCourseInfo)
        }
      }
    }
  }
</script>

<style>
  .choose {
    float: right;
    position: absolute;
    top: 100px;
    right: 20px;
    font-size: 12px;
    color: #333;
    font-weight: 100;
  }
  .warning-info {
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    flex-wrap: wrap;
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
    background-color: #dedede;
    height: 100vh;
    height: 100%;

  }

  .info {
    border-radius: 25px 0px 25px 0px;
    display: flex;
    background-color: #defffc;
    height: 250rpx;
    margin: 20px;
    justify-content: space-around;
    flex-wrap: wrap;
    flex-direction: column;
    border: 1px solid #efefef;
    box-shadow: 6px 6px #efefef;
    font-family: "楷体";

  }

  .item-name {
    padding-top: 20rpx;
    padding-left: 20rpx;
    width: 400px;
    font-weight: 800;
    font-size: 20px;
  }

  .item-tname {
    font-size: 18px;
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

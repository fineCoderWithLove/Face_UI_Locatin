<template>
  <view class="body">
    <view
      style=" background-image: url('https://tse4-mm.cn.bing.net/th/id/OIP-C.BDtLxeuvHuL6h_BKH8_CwQHaNK?pid=ImgDet&rs=1'); height: 40rpx;">
    </view>
    <view class="example-body custom-image-box">
      <view class="image">
        <!-- 若果为空就显示上传头像 -->
        <uni-file-picker class="photo" limit="1" :del-icon="false" disable-preview :imageStyles="imageStyles"
          file-mediatype="image" v-if="src === null">
          点击上传上传</uni-file-picker>
        <!-- 如果不为空就直接展示头像 -->
        <view class="hava-image" v-else>
          <u-avatar :src="src" size="200"></u-avatar>
        </view>
      </view>

      <view
        style=" background-image: url('https://tse4-mm.cn.bing.net/th/id/OIP-C.BDtLxeuvHuL6h_BKH8_CwQHaNK?pid=ImgDet&rs=1'); height: 40rpx;">
      </view>
    </view>
    <!-- 个人剩余信息页面 -->
    <view class="my-info">
      <view class="sno">学号：{{sno}}</view>
      <view class="stu-name">姓名：{{stuName}}</view>
      <view class="stu-gender">性别：{{gender}}</view>
      <view class="stu-stuage">年龄：{{stuAge}}</view>
    </view>
    <!-- 查看签到信息按钮 -->
    <view class="fotter">
      <u-popup :show="show" @close="close" @open="open" mode="right" closeable="true" safeAreaInsetTop="false">
        <view>
          <view style="height: 30rpx;"></view>
          <view v-for="(item,i) in myCheckInfoList" :key="i">
            <view class="checkdate">{{item.checkdate}}</view>
            <view class="checkinfo">{{item.checkinfo}}</view>
            <view class="checklocation">{{item.checklocation}}</view>
            <view class="ischeckstring">{{item.ischeckstring}}</view>
            <view class="checkinfostring">{{item.checkinfostring}}</view>
          </view>
        </view>
      </u-popup>
      <u-button class="checkin" style="width: 100%; border-radius: 20px;" plain="true" @click="show = true">查看签到信息</u-button>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        myCheckInfoList: [],
        cno: 0,
        show: false,
        src: 'https://tupian.qqw21.com/article/UploadPic/2019-9/201991021554113660.jpg',
        sno: 0,
        gender: null,
        stuName: null,
        stuAge: 0,
        imageStyles: {
          width: 200,
          height: 200,
          border: {
            radius: '50%'
          }
        },
        listStyles: {
          // 是否显示边框
          border: true,
          // 是否显示分隔线
          dividline: true,
          // 线条样式
          borderStyle: {
            width: 1,
            color: '#ef871f',
            style: 'dashed',
            radius: 2
          }
        },
      }
    },
    onLoad(options) {
      //通过传过来的sno来请求学生的详情数据，再通过学生的学号和课程的cno来查询该学生的所有签到信息。
      console.log(options.sno)
      console.log(options.cno)
      this.cno = options.cno
      this.sno = options.sno
      this.getStuInfo()
      //应该是页面已加载就请求签到的数据然后封装，而不是等到点击按钮的时候再请求数据
      this.getCheckInfo()
    },
    methods: {
      async getCheckInfo() {
        const data = await uni.$http.get('/checkinfo/stuallcheckinfo?sno=' + this.sno + '&cno=' + this.cno)
        console.log(data)
        this.myCheckInfoList = data.data.data
      },
      open() {
        // console.log('open');
      },
      close() {
        this.show = false
        // console.log('close');
      },
      async getStuInfo() {
        const data = await uni.$http.get('/student/studetails?sno=' + this.sno)
        console.log(data)
        if (data.data.code === 200) {
          this.src = data.data.data.src
          this.gender = data.data.data.gender
          this.sno = data.data.data.sno
          this.stuAge = data.data.data.stuAge
          this.stuName = data.data.data.stuName
        }
      }
    }
  }
</script>

<style lang="scss">
  .checkdate,
  .checkinfo,
  .checklocation,
  .ischeck,
  .ischeckstring,
  .checkinfostring{
    color: #000000;
  }

  .fotter {
    margin-top: 60rpx;
  }

  .checkin {
    position: absolute;
    bottom: 0px;
    border: 2px solid #007AFF;
  }

  .my-info {
    color: #efefef;
    height: 600rpx;
    display: flex;
    flex-direction: column;
    justify-content: space-around;
  }

  .sno,
  .stu-name,
  .stu-gender,
  .stu-name,
  .stu-stuage {
    display: flex;
    padding-left: 40rpx;
    background-image: url('https://tse4-mm.cn.bing.net/th/id/OIP-C.BDtLxeuvHuL6h_BKH8_CwQHaNK?pid=ImgDet&rs=1');
    background-size: 100% 100%;
    background-attachment: fixed;
    width: 80%;
    margin-left: 55rpx;
    height: 150rpx;
    line-height: 150rpx;
    border: 1px solid #2A3C70;

  }



  .hava-image {
    padding-right: 170px;
  }

  .example-body {
    padding: 10px;
    padding-top: 0;
  }

  .custom-image-box {
    /* #ifndef APP-NVUE */
    display: flex;
    /* #endif */
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
  }

  .text {
    font-size: 14px;
    color: #333;
  }

  .body {
    display: flex;
    height: 100vh;
    flex-direction: column;
    background-image: url('https://tse4-mm.cn.bing.net/th/id/OIP-C.BDtLxeuvHuL6h_BKH8_CwQHaNK?pid=ImgDet&rs=1');
    color: #EFEFEF;
  }

  .image {
    display: flex;
    justify-content: space-around;
    padding-left: 180rpx;
    height: 400rpx;
    background-image: url('https://tse4-mm.cn.bing.net/th/id/OIP-C.BDtLxeuvHuL6h_BKH8_CwQHaNK?pid=ImgDet&rs=1');
    width: 100%;
  }
</style>

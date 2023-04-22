<template>
  <view class="body">
    <view style="height: 5px; color: #007AFF;"></view>
    <!-- 最外层包裹的容器 -->
    <view class="container">
      <view style="height: 18px;"></view>
      <uni-swipe-action>
        <view class="item-list" v-for="(item,i) in TeacherList" :key="i">
          <!-- 为每个子节点绑定 -->
          <uni-swipe-action-item :right-options="options" @click="TeacherCourse(item)">
            <view class="info">
              <view class="tno">工号:{{item.tno}}</view>
              <view class="name">{{item.teaName}}老师</view>
              <view class="age">年龄:{{item.teaAge}}</view>
              <block v-if="item.isregister==1">
                <view class="isregister" style="color: #18BC37;">已注册</view>
                <span class="words">右滑查看详情</span>
              </block>
              <block v-else>
                <view class="isregister" style="color: #E43D33;" @click="pass(item)">未注册</view>
                <span class="words">点击进行审批</span>
              </block>
            </view>
          </uni-swipe-action-item>
        </view>

      </uni-swipe-action>
    </view>
    <!-- 弹出层 -->
    <u-popup :show="show" mode="center">
      <view style="height: 150px; width: 250px;" class="pop">
        <view style="margin: 10px;">
          <text>您确定要通过该老师的申请嘛？</text>
          <view class="buttons">
            <view class="button" @click="updateInfo()">确定</view>
            <view class="button" style="background-color: #E43D33; color: #efefef;" @click="cancel()">取消</view>
          </view>

        </view>

      </view>
    </u-popup>
    <u-tabbar :value="value6" @change="name => value6 = name" :fixed="true" :placeholder="true"
      :safeAreaInsetBottom="true">
      <u-tabbar-item text="管理学生" icon="home" @click="leftclick()"></u-tabbar-item>
      <u-tabbar-item text="管理老师" icon="account" @click="rightclick()"></u-tabbar-item>
    </u-tabbar>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        noregister: 0,
        show: false,
        active: 0,
        value6: 1,
        TeacherList: [],
        options: [{
          text: '老师详情', // 显示的文本内容
          style: {
            backgroundColor: '#ffbe93', // 按钮的背景颜色
          }
        }]
      };
    },
    onLoad() {
      this.getTeacherList()
    },
    methods: {
      updateInfo() {
        let that = this
        uni.request({
          url: 'http://43.143.44.118:8888/admin/passregister',
          method: 'GET',
          data: {
              tno:this.noregister
          },
          success(res) {
            console.log(res);
            that.getTeacherList()
          }
        })
        this.show = false
      },
      pass(item) {
        console.log(item);
        this.show = true
        this.noregister = item.tno
      },
      cancel() {
        console.log("取消");
        this.show = false
      },
      async getTeacherList() {
        const data = await uni.$http.get('/admin/allteacher')
        this.TeacherList = data.data.data
      },
      TeacherCourse(item) {
        console.log("查看老师的课程信息");
        uni.navigateTo({
          url: '../teacher-detail/teacher-detail?tno=' + item.tno
        })
      },
      leftclick() {
        console.log("点击到左边了");
        uni.navigateTo({
          url: '../admin-system/admin-system'
        })
      },
      rightclick() {
        console.log("点击到右边了")
      }
    }
  }
</script>

<style lang="scss">
  .words {
    float: right;
    position: absolute;
    right: 20px;
    font-size: 13px;
    top: 70px;
  }
  .pop {
    font-family: "楷体";
  }

  .buttons {
    display: flex;
    justify-content: space-around;
  }

  .button {
    width: 80px;
    margin-top: 80px;
    background-color: #18BC37;
    text-align: center;

  }

  * {
    font-family: "楷体";
    margin: 0px;
    padding: 0px;
  }

  .body {
    background-color: #398ADE;
  }

  .container {
    margin: 5px;
    background-color: #efefef;
    border-radius: 5px;
  }

  .info {
    margin-bottom: 20px;
    background-color: #f9f9f9;
    height: 120px;
    font-family: "楷体";
    font-size: 20px;
    border-radius: 20px;
    margin-left: 10px;
    margin-right: 10px;
  }

  .isregister {
    float: right;
    position: absolute;
    right: 20px;
    top: 40px;
    font-size: 25px;
  }

  .tno,
  .name,
  .age {
    margin-bottom: 12px;
    margin-left: 20px;
    margin-top: 12px;
  }
</style>

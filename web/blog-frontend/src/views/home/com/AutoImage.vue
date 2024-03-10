<template>
  <div>
    <!--  走马灯  -->
    <el-carousel :interval="4000" type="card" height="320px">
      <el-carousel-item v-for="item in topArticles" :key="item">
        <div class="img-group">
          <router-link :to="'/ArticleDetail/' + item.id" class="a-link">
            <el-image :src="item.thumbnail" v-if="item.thumbnail"/>
            <el-image v-else src="https://pic1.zhimg.com/v2-d58ce10bf4e01f5086c604a9cfed29f3_r.jpg?source=1940ef5c"/>
            <div class="img-tip">{{item.title}}</div>
          </router-link>
        </div>
      </el-carousel-item>
    </el-carousel>
  </div>

</template>

<script setup>

const props = defineProps({
  topArticles: {
  },
})

</script>

<style lang="scss">

.img-group {
  position: relative;
  display: inline-block;
  width: 100%;
  height: inherit;
}
.img-tip {
  position: absolute;
  top: 0;
  width: 100%;
  text-align: center;
  background: rgba(0, 0, 0, 0.6);
  color: #fff;
  font-size: 30px;
  opacity: 0;
  transition: 0.7s ease-in;
}
.img-group:hover .img-tip {
  opacity: 0.9;
  display: block;
}

//走马灯
.el-carousel__item{
  text-align: center;

  //图片适应宽度
  .el-image {
    width: 100%;
    height: inherit;
  }
}
.el-carousel__item h3 {
  color: #475669;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
  text-align: center;
}
.el-carousel__item:nth-child(2n) {
  background-color: black;
}
.el-carousel__item:nth-child(2n + 1) {
  background-color: black;
}
</style>
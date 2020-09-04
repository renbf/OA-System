<template>
  <div id="tags-view-container" class="tags-view-container">
    <div class="left-menu">
      <div v-for="item in activeName2" v-bind:key="item">
        <router-link :to="{path: '/business/mywork', query: {code: item.code }}">
          <img v-if="lay_type == 0" src="@/assets/image/footprint.png" alt />
          <img v-else src="@/assets/image/footprint.png" alt />{{
            item.name
          }}</router-link>
      </div>
      <!-- <el-tabs type="border-card">
        <el-tab-pane v-for="item in activeName2" v-bind:key="item">
          <div slot="label">
            <span>
              <span>
                 <img v-if="lay_type" src="@/assets/image/footprint.png" alt />
                  <img v-else src="@/assets/image/footprint_white.png" alt />

              <span class="el-tab-span-style">{{ item.name }}</span>
            </span>
          </div>
        </el-tab-pane>
      </el-tabs> -->
    </div>
    <div class="rightmenu">
      <div><img :src="imgUrl" alt="" /><router-link to>通知</router-link></div>
      <div>
        <img :src="imgUrl" alt="" /><router-link to>迈克尔</router-link>
      </div>
      <div>
        <router-link to><i class="el-icon-more"></i></router-link>
      </div>
    </div>
  </div>
  <!-- <ul
      v-show="visible"
      :style="{ left: left + 'px', top: top + 'px' }"
      class="contextmenu"
    >
      <li @click="refreshSelectedTag(selectedTag)">刷新页面</li>
      <li v-if="!isAffix(selectedTag)" @click="closeSelectedTag(selectedTag)">
        关闭当前
      </li>
      <li @click="closeOthersTags">关闭其他</li>
      <li @click="closeAllTags(selectedTag)">关闭所有</li>
    </ul> -->
  <!-- </div> -->
</template>

<script>
import ScrollPane from "./ScrollPane";
import path from "path";
import imgUrl from "@/assets/image/footprint.png";
import imgUrl_white from "@/assets/image/footprint_white.png";
export default {
  components: { ScrollPane},
  data() {
    return {
      visible: false,
      top: 0,
      left: 0,
      imgUrl: imgUrl,
      selectedTag: {},
      lay_type: 0,
      affixTags: [],
      name: "迈克尔",
      activeName2: [
        {
          code:"技术部日常界面",
          name: "技术部"
        },
        {
            code:"软件部日常界面",
          name: "软件部"
        }
      ]
    };
  },
  computed: {
    visitedViews() {
      return this.$store.state.tagsView.visitedViews;
    },
    routes() {
      return this.$store.state.permission.routes;
    }
  },
  watch: {
    $route() {
      this.addTags();
      this.moveToCurrentTag();
    },
    visible(value) {
      if (value) {
        document.body.addEventListener("click", this.closeMenu);
      } else {
        document.body.removeEventListener("click", this.closeMenu);
      }
    }
  },
  mounted() {
    this.initTags();
    this.addTags();
  },
  methods: {
    // enter() {
    //   if (this.lay_type == 0) {
    //     this.lay_type = 1;
    //   //  debugger
    //   } else {
    //     this.lay_type = 0;
    //     // debugger
    //   }
    // },
    isActive(route) {
      return route.path === this.$route.path;
    },
    isAffix(tag) {
      return tag.meta && tag.meta.affix;
    },
    filterAffixTags(routes, basePath = "/") {
      let tags = [];
      routes.forEach(route => {
        if (route.meta && route.meta.affix) {
          const tagPath = path.resolve(basePath, route.path);
          tags.push({
            fullPath: tagPath,
            path: tagPath,
            name: route.name,
            meta: { ...route.meta }
          });
        }
        if (route.children) {
          const tempTags = this.filterAffixTags(route.children, route.path);
          if (tempTags.length >= 1) {
            tags = [...tags, ...tempTags];
          }
        }
      });
      return tags;
    },
    initTags() {
      const affixTags = (this.affixTags = this.filterAffixTags(this.routes));
      for (const tag of affixTags) {
        // Must have tag name
        if (tag.name) {
          this.$store.dispatch("tagsView/addVisitedView", tag);
        }
      }
    },
    addTags() {
      const { name } = this.$route;
      if (name) {
        this.$store.dispatch("tagsView/addView", this.$route);
      }
      return false;
    },
    moveToCurrentTag() {
      const tags = this.$refs.tag;
      this.$nextTick(() => {
        for (const tag of tags) {
          if (tag.to.path === this.$route.path) {
            this.$refs.scrollPane.moveToTarget(tag);
            // when query is different then update
            if (tag.to.fullPath !== this.$route.fullPath) {
              this.$store.dispatch("tagsView/updateVisitedView", this.$route);
            }
            break;
          }
        }
      });
    },
    refreshSelectedTag(view) {
      this.$store.dispatch("tagsView/delCachedView", view).then(() => {
        const { fullPath } = view;
        this.$nextTick(() => {
          this.$router.replace({
            path: "/redirect" + fullPath
          });
        });
      });
    },
    closeSelectedTag(view) {
      this.$store
        .dispatch("tagsView/delView", view)
        .then(({ visitedViews }) => {
          if (this.isActive(view)) {
            this.toLastView(visitedViews, view);
          }
        });
    },
    closeOthersTags() {
      this.$router.push(this.selectedTag);
      this.$store
        .dispatch("tagsView/delOthersViews", this.selectedTag)
        .then(() => {
          this.moveToCurrentTag();
        });
    },
    closeAllTags(view) {
      this.$store.dispatch("tagsView/delAllViews").then(({ visitedViews }) => {
        if (this.affixTags.some(tag => tag.path === view.path)) {
          return;
        }
        this.toLastView(visitedViews, view);
      });
    },
    toLastView(visitedViews, view) {
      const latestView = visitedViews.slice(-1)[0];
      if (latestView) {
        this.$router.push(latestView.fullPath);
      } else {
        // now the default is to redirect to the home page if there is no tags-view,
        // you can adjust it according to your needs.
        if (view.name === "Dashboard") {
          // to reload home page
          this.$router.replace({ path: "/redirect" + view.fullPath });
        } else {
          this.$router.push("/");
        }
      }
    },
    openMenu(tag, e) {
      const menuMinWidth = 105;
      const offsetLeft = this.$el.getBoundingClientRect().left; // container margin left
      const offsetWidth = this.$el.offsetWidth; // container width
      const maxLeft = offsetWidth - menuMinWidth; // left boundary
      const left = e.clientX - offsetLeft + 15; // 15: margin right

      if (left > maxLeft) {
        this.left = maxLeft;
      } else {
        this.left = left;
      }

      this.top = e.clientY;
      this.visible = true;
      this.selectedTag = tag;
    },
    closeMenu() {
      this.visible = false;
    }
  }
};
</script>
<style lang="scss" scoped>
.tags-view-container {
  height: 34px;
  width: 100%;
  background: #fff;
  border-bottom: 1px solid #d8dce5;
  box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.12), 0 0 3px 0 rgba(0, 0, 0, 0.04);
  .tags-view-wrapper {
    // .el-icon-fs {
    //   display: inline-block;
    //   width: 100px;
    //   height: 20px;
    //   background-image: url(imgUrl);
    // }
    // .imgUrl_style {
    //   width: 16px;
    // }
    // .el-tab-span-style {
    //   display: inline-block;
    //   height: 40px;
    //   width: auto;
    // }
    .tags-view-item {
      display: inline-block;
      position: relative;
      cursor: pointer;
      height: 26px;
      line-height: 26px;
      border: 1px solid #d8dce5;
      color: #495060;
      background: #fff;
      padding: 0 8px;
      font-size: 12px;
      margin-left: 5px;
      margin-top: 4px;
      &:first-of-type {
        margin-left: 15px;
      }
      &:last-of-type {
        margin-right: 15px;
      }
      &.active {
        background-color: #42b983;
        color: #fff;
        border-color: #42b983;
        &::before {
          content: "";
          background: #fff;
          display: inline-block;
          width: 8px;
          height: 8px;
          border-radius: 50%;
          position: relative;
          margin-right: 2px;
        }
      }
    }
  }
  .contextmenu {
    margin: 0;
    background: #fff;
    z-index: 3000;
    position: absolute;
    list-style-type: none;
    padding: 5px 0;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 400;
    color: #333;
    box-shadow: 2px 2px 3px 0 rgba(0, 0, 0, 0.3);
    li {
      margin: 0;
      padding: 7px 16px;
      cursor: pointer;
      &:hover {
        background: #eee;
      }
    }
  }
}
</style>

<style lang="scss">
//reset element css of el-icon-close
.tags-view-wrapper {
  .tags-view-item {
    .el-icon-close {
      width: 16px;
      height: 16px;
      vertical-align: 2px;
      border-radius: 50%;
      text-align: center;
      transition: all 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
      transform-origin: 100% 50%;
      &:before {
        transform: scale(0.6);
        display: inline-block;
        vertical-align: -3px;
      }
      &:hover {
        background-color: #b4bccc;
        color: #fff;
      }
    }
  }
}
.el-tabs--border-card > .el-tabs__header .el-tabs__item {
  color: black;
}
.imgUrl_style {
  width: 12px;
}
.tags-view-container[data-v-fac8ca64] {
  height: 39px;
}
.left-menu {
  float: left;
  height: 100%;
  line-height: 100%;
  .el-tabs--border-card > .el-tabs__header .el-tabs__item.is-active {
    background-color: #137ad1;
    color: white;
  }
  div {
    width: 100px;
    height: 39px;
    float: left;
    font-size: 14px;
    color: black;
    line-height: 39px;
    text-align: center;
    a {
      display: inline-block;
      width: 100%;
    }
    img {
      width: 14px;
      margin-bottom: -2px;
      margin-right: 4px;
    }
  }
}
.left-menu div a:hover {
  color: white;
  background-color: #137ad1;
}
.rightmenu {
  float: right;
  div {
    width: 100px;
    height: 39px;
    float: left;
    font-size: 14px;
    color: black;
    line-height: 39px;
    text-align: center;
    a {
      display: inline-block;
    }
    img {
      width: 14px;
      margin-bottom: -2px;
      margin-right: 4px;
    }
  }
}
</style>
